package org.fl.noodleschedule.core.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fl.noodle.common.distributedlock.api.LockChangeHandler;
import org.fl.noodle.common.util.thread.ExecutorThreadFactory;
import org.fl.noodle.common.util.thread.Stopping;
import org.fl.noodleschedule.console.service.CoreService;
import org.fl.noodleschedule.console.system.jobs.ExecuteSystem;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.core.scheduler.Despatcher;

public class JobLoader implements LockChangeHandler {

	private final static Logger logger = LoggerFactory.getLogger(JobLoader.class);
	
	private final int RUNSTATUS_START = 1;
	private final int RUNSTATUS_STOP = 2;
	
	private CoreService coreService;
	private Map<String, Despatcher> despatcherMap;
	private long sleepTime = 60 * 1000;
	
	private List<ExecuteSystem> executeSystemList;
	
	private AtomicInteger runStatus = new AtomicInteger(RUNSTATUS_STOP);
	
	private volatile boolean stopSign = false;
	private Stopping stopping = new Stopping();
	private ExecutorService executorServiceSingle;
	
	@Override
	public void onMessageGetLock() {
		doStart();
	}

	@Override
	public void onMessageLossLock() {
		doStop();
	}

	@Override
	public void onMessageReleaseLock() {		
	}

	@Override
	public void onMessageStart() {
	}
	
	@Override
	public void onMessageStop() {
		doStop();
	}
	
	private void doStart() {
		
		if (runStatus.get() == RUNSTATUS_STOP) {			
			
			stopping.stopInit(1);
			
			stopSign = false; 
			
			ExecutorThreadFactory executorThreadFactorySingle = new ExecutorThreadFactory("JobLoaderThread");
			executorServiceSingle = Executors.newSingleThreadExecutor(executorThreadFactorySingle);
			
			executorServiceSingle.execute(new Runnable() {
				
				@Override
				public void run() {
					
					startDo();	
					
					if (executeSystemList != null) {
						addSystemJobs();
					}

					while (!stopSign) {											
						
						List<JobVo> jobVoList = getDispatchJobList();
						
						if (jobVoList != null) {
							removeDeleteJobs(jobVoList);
							addNoHaveAndChangeJobs(jobVoList);
						}
						
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							if (!stopSign) {
								logger.error("Thread sleep -> InterruptedException");							
							}
						}
					}
					
					stopDo();
				}
				
				private void startDo() {
					runStatus.set(RUNSTATUS_START);
					logger.info("**************************Job loader runnable start**************************");
				}
				
				private void stopDo() {
					stopping.stopDo();
					runStatus.set(RUNSTATUS_STOP);
					logger.info("**************************Job loader runnable stop***************************");
				}
			});		
		}
	}
	
	private void doStop() {
		if (runStatus.get() == RUNSTATUS_START) {
			waitLoadRunnableStop();
			clearAllJob();
		}
	}
	
	private void addSystemJobs() {
		for (ExecuteSystem executeSystem : executeSystemList) {
			Despatcher despatcher = despatcherMap.get(executeSystem.getJobType());
			if (despatcher != null) {
				if (despatcher.addSystemJob(executeSystem)) {
					logger.info("Success add a new system job -> jobId: {}, jobName: {}, jobType: {}, cron: {}", executeSystem.getJobId(), executeSystem.getJobName(), executeSystem.getJobType(), executeSystem.getCron());
				}
			} else {
				logger.error("addSystemJobs -> despatcherMap.get -> no have this despatcher -> jobId: {}, jobName: {}, jobType: {}, cron: {}", executeSystem.getJobId(), executeSystem.getJobName(), executeSystem.getJobType(), executeSystem.getCron());
			}
		}
	}
	
	private List<JobVo> getDispatchJobList() {
		try {
			return coreService.queryDispatchJob();
		} catch (Exception e) {
			logger.error("getJobList -> coreService.queryQuartzAndCompletionJob -> Exception: {} ", e);
		}
		return null;
	}
	
	private void addNoHaveAndChangeJobs(List<JobVo> jobVoList) {
		for (JobVo jobVo : jobVoList) {
			Despatcher despatcher = despatcherMap.get(jobVo.getJob_Type());
			if (despatcher != null) {
				if (despatcher.addJob(jobVo)) {
					logger.info("Success add a new job -> {}", jobVo);
				}
			} else {
				logger.error("addNoHaveAndChangeJobs -> despatcherMap.get -> no have this despatcher -> jobId: {}, jobType: {}", jobVo.getJob_Id(), jobVo.getJob_Type());
			}
		}
	}

	private void removeDeleteJobs(List<JobVo> jobVoList) {
		
		Map<Long, JobVo> jobIdMap = new HashMap<Long, JobVo>();
		for (JobVo jobVo : jobVoList) {
			jobIdMap.put(jobVo.getJob_Id(), jobVo);
		}
		
		for (Entry<String, Despatcher> entry : despatcherMap.entrySet()) {
			String jobType = entry.getKey();
			Despatcher despatcher = entry.getValue();
			Set<Long> jobIdSet = despatcher.getAllJobId();
			for (Long jobId : jobIdSet) {
				if (!jobIdMap.containsKey(jobId) || !jobIdMap.get(jobId).getJob_Type().equals(jobType)) {
					if(despatcher.removeJob(jobId)) {
						logger.info("Remove a delete job -> jobId: {}", jobId);
					}
				}
			}
		}
	}
	
	private void clearAllJob() {
		for (Entry<String, Despatcher> entry : despatcherMap.entrySet()) {
			entry.getValue().clearJob();
			logger.info("Clear all job -> Despatcher: {}", entry.getKey());
		}
	}
	
	private void waitLoadRunnableStop() {
		stopSign = true;
		do {				
			executorServiceSingle.shutdownNow();
		} while (!stopping.stopWait(1000));
	}
	
	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}

	public void setDespatcherMap(Map<String, Despatcher> despatcherMap) {
		this.despatcherMap = despatcherMap;
	}
	
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void setExecuteSystemList(List<ExecuteSystem> executeSystemList) {
		this.executeSystemList = executeSystemList;
	}
}
