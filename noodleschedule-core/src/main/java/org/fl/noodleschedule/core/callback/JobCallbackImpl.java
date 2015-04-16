package org.fl.noodleschedule.core.callback;

import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fl.noodle.common.util.thread.ExecutorThreadFactory;
import org.fl.noodleschedule.alarm.sender.SendLogAlarm;
import org.fl.noodleschedule.client.callback.JobCallback;
import org.fl.noodleschedule.client.pojo.JobResult;
import org.fl.noodleschedule.console.service.CoreService;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.core.scheduler.Despatcher;
import org.fl.noodleschedule.core.trigger.ExecuteTrigger;
import org.fl.noodleschedule.util.common.Constant;

public class JobCallbackImpl implements JobCallback {

	Logger logger = LoggerFactory.getLogger(JobCallbackImpl.class);
	
	private CoreService coreService;
	private Despatcher completionDespatcher;
	private ExecuteTrigger executeTrigger;
	
	private long callbackDelayTime = 200;
	private long retryDelayTime = 100;
	private long childDelayTime = 100;
	
	private DelayQueue<DelayElement> delayQueue = new DelayQueue<DelayElement>();
	private volatile boolean stopSign = false;
	private ExecutorService executorService;
	private int threadNum = 5;

	private SendLogAlarm sendLogAlarm;
	
	public void start() throws Exception {

		ExecutorThreadFactory executorThreadFactorySingle = new ExecutorThreadFactory("CompletionDespatcher_Distribute");
		executorService = Executors.newFixedThreadPool(threadNum, executorThreadFactorySingle);
		
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				
				while (!stopSign) {	
					
					try {
						DelayElement delayElement = delayQueue.poll(Integer.MAX_VALUE, TimeUnit.SECONDS);
						if (delayElement != null) {							
							delayElement.run();
						}
					} catch (InterruptedException e) {
						if (!stopSign) {
							logger.error("start -> Runnable -> Thread sleep -> InterruptedException: {}", e);							
						}
					}
				}
			}
		});		
	}
	
	public void destroy() {	
		stopSign = true;
		executorService.shutdownNow();
	}
	
	@Override
	public JobResult callback(JobResult jobResult) {
		
		try {
			Thread.sleep(callbackDelayTime);
		} catch (InterruptedException e) {
			logger.error("callback -> Thread.sleep -> InterruptedException: {}", e);
		}
		
		JobVo jobVoResult = null;
		
		if (jobResult.getCode() == Constant.CLIENT_EXE_SUCCESS) {
			try {
				jobVoResult = coreService.saveCallbackResult(jobResult.getLogId(), jobResult.getExecutorId(), Constant.EXECUTOR_EXE_STATUS_RUN_SUCCESS, jobResult.getException());
			} catch (Exception e) {
				logger.error("callback -> coreService.saveCallbackResult -> logId: {}, executorId: {}, code: {} -> Exception: {}", jobResult.getLogId(), jobResult.getExecutorId(), jobResult.getCode(), e);
				return new JobResult(Constant.CALLBACK_RESULT_NO, e.getMessage());
			}
		} else {
			try {
				jobVoResult = coreService.saveCallbackResult(jobResult.getLogId(), jobResult.getExecutorId(), Constant.EXECUTOR_EXE_STATUS_RUN_FAIL, jobResult.getException());
			} catch (Exception e) {
				logger.error("callback -> coreService.saveCallbackResult -> logId: {}, executorId: {}, code: {} -> Exception: {}", jobResult.getLogId(), jobResult.getExecutorId(), jobResult.getCode(), e);
				return new JobResult(Constant.CALLBACK_RESULT_NO, e.getMessage());
			}
			sendAlarm(jobVoResult.getJob_Id(), jobResult.getLogId(), jobResult.getExecutorId(), jobResult.getException());
		}
		
		if (jobVoResult.getResult() != Constant.CALLBACK_RESULT_NO_COMPLETE) {
			
			if (jobVoResult.getResult() == Constant.CALLBACK_RESULT_ALL_SUCCESS) {
				delayQueue.add(new childDelayElement(jobVoResult.getJob_Id(), jobResult.getLogId(), childDelayTime));
			} else if (jobVoResult.getResult() == Constant.CALLBACK_RESULT_PART_SUCCESS || jobVoResult.getResult() == Constant.CALLBACK_RESULT_ALL_FAIL) {
				delayQueue.add(new retryDelayElement(jobVoResult.getJob_Id(), jobResult.getLogId(), retryDelayTime));
			}
			
			if (jobVoResult.getExeType() == Constant.TRIGGER_TYPE_ORDINARY || jobVoResult.getExeType() == Constant.TRIGGER_TYPE_TIMEOUT_RETRY) {
				JobVo jobVo = getJob(jobVoResult.getJob_Id());
				if (jobVo != null) {
					if (jobVo.getJob_Type().equals(Constant.JOB_TYPE_COMPLETION)) {
						completionDespatcher.callback(jobVoResult.getJob_Id());
					}
				} else {					
					logger.error("callback -> getJob -> return null -> No execute despatcher callback and child job -> logId: {}, executorId: {}, code: {}", jobResult.getLogId(), jobResult.getExecutorId(), jobResult.getCode());
				}
			}
		}
		
		return new JobResult(Constant.CALLBACK_RESULT_YES);
	}
	
	private abstract class DelayElement implements Delayed, Runnable {

		private long delayTime;
		
		protected long jobId;
		protected long logId;

		public DelayElement(long jobId, long logId, long delayTime) {
			this.jobId = jobId;
			this.logId = logId;
			this.delayTime = System.currentTimeMillis() + delayTime;
		}
		
		@Override
		public int compareTo(Delayed o) {
			if (this.delayTime < ((DelayElement) o).delayTime) {				
				return -1;
			} else if (this.delayTime > ((DelayElement) o).delayTime) {				
				return 1;
			}
			else {
				return 0;				
			}
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(delayTime - System.currentTimeMillis(), TimeUnit.NANOSECONDS);
		}	
	}
	
	private class retryDelayElement extends DelayElement {

		
		public retryDelayElement(long jobId, long logId, long delayTime) {
			super(jobId, logId, delayTime);
		}

		@Override
		public void run() {
			
			JobVo jobVo = getJob(jobId);
			if (jobVo != null) {	
				if (jobVo.getExe_Retry() == Constant.EXE_RETRY_YES) {
					executeTrigger.executeOther(jobVo, Constant.TRIGGER_TYPE_EXE_FAIL_RETRY, logId);				
				}
			} else {
				logger.error("retry -> run -> getJob -> return null -> No execute retry -> jobId: {}", jobId);
			}
		}
	}
	
	private class childDelayElement extends DelayElement {

		public childDelayElement(long jobId, long logId, long delayTime) {
			super(jobId, logId, delayTime);
		}

		@Override
		public void run() {
			
			List<JobVo> jobVoChildList = getChildJobs(jobId);
			if (jobVoChildList != null) {
				for (JobVo jobVoChild : jobVoChildList) {
					executeTrigger.executeOther(jobVoChild, Constant.TRIGGER_TYPE_CHILD, logId);
				}
			} else {
				logger.error("child -> run -> getChildJobs -> return null -> No execute child job -> jobId: {}", jobId);
			}	
		}
	}
	
	private JobVo getJob(long jobId) {
		try {
			return coreService.queryJobById(jobId);
		} catch (Exception e) {
			logger.error("getJob -> coreService.queryJobById-> jobId: {} -> Exception: ", jobId, e);
			return null;
		}
	}
	
	private List<JobVo> getChildJobs(long jobId) {
		try {
			return coreService.queryChildJobList(jobId);
		} catch (Exception e) {
			logger.error("getChildJobs -> coreService.queryChildJobList -> jobId: {} -> Exception: ", jobId, e);
			return null;
		}
	}
	
	private void sendAlarm(long jobId, long logId, long executorId, String exception) {
		sendLogAlarm.sendRunAlarm(jobId, logId, executorId, exception);			
	}
	
	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}
	
	public Despatcher getCompletionDespatcher() {
		return completionDespatcher;
	}

	public void setCompletionDespatcher(Despatcher completionDespatcher) {
		this.completionDespatcher = completionDespatcher;
	}

	public void setExecuteTrigger(ExecuteTrigger executeTrigger) {
		this.executeTrigger = executeTrigger;
	}
	
	public void setCallbackDelayTime(long callbackDelayTime) {
		this.callbackDelayTime = callbackDelayTime;
	}

	public void setRetryDelayTime(long retryDelayTime) {
		this.retryDelayTime = retryDelayTime;
	}
	
	public void setChildDelayTime(long childDelayTime) {
		this.childDelayTime = childDelayTime;
	}
	
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public void setSendLogAlarm(SendLogAlarm sendLogAlarm) {
		this.sendLogAlarm = sendLogAlarm;
	}
}
