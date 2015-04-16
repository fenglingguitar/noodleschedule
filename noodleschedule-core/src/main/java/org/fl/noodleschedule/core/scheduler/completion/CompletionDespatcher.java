package org.fl.noodleschedule.core.scheduler.completion;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fl.noodle.common.util.thread.ExecutorThreadFactory;
import org.fl.noodleschedule.console.system.jobs.ExecuteSystem;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.core.scheduler.AbstractDespatcher;
import org.fl.noodleschedule.core.trigger.ExecuteTrigger;
import org.fl.noodleschedule.util.common.Constant;

public class CompletionDespatcher extends AbstractDespatcher {

	private final static Logger logger = LoggerFactory.getLogger(CompletionDespatcher.class);
	
	private ExecuteTrigger executeTrigger;

	private DelayQueue<Delayed> delayQueue = new DelayQueue<Delayed>();
	
	private int threadCount = 5;
	
	private volatile boolean stopSign = false;
	private ExecutorService executorServiceSingle;
	private ExecutorService executorServiceFixd;	
	
	public void start() throws Exception {
		
		ExecutorThreadFactory executorThreadFactoryFixd = new ExecutorThreadFactory("CompletionDespatcher_Worker");
		executorServiceFixd = Executors.newFixedThreadPool(threadCount, executorThreadFactoryFixd);

		ExecutorThreadFactory executorThreadFactorySingle = new ExecutorThreadFactory("CompletionDespatcher_Distribute");
		executorServiceSingle = Executors.newSingleThreadExecutor(executorThreadFactorySingle);
		
		executorServiceSingle.execute(new Runnable() {
			
			@Override
			public void run() {
				
				while (!stopSign) {	
					
					try {
						Delayed delayed = delayQueue.poll(5, TimeUnit.SECONDS);
						if (delayed != null) {
							if (delayed instanceof DelayElement) {
								DelayElement delayElement = (DelayElement) delayed;
								if (delayElement != null && jobContainerMap.containsKey(delayElement.getJobVo().getJob_Id())) {							
									executorServiceFixd.submit(delayElement);
								}
							} else {
								SystemDelayElement systemDelayElement = (SystemDelayElement) delayed;
								executorServiceFixd.submit(systemDelayElement);
							}
						}						
					} catch (InterruptedException e) {
						if (!stopSign) {
							logger.error("Thread sleep -> InterruptedException");							
						}
					}
				}
			}
		});		
	}
	
	public void destroy() {	
		stopSign = true;
		executorServiceSingle.shutdownNow();
		executorServiceFixd.shutdownNow();
	}
	
	@Override
	protected boolean doAddJob(JobVo jobVo) {
		return delayQueue.add(new DelayElement(jobVo));
	}

	@Override
	protected boolean doRemoveJob(long jobId) {
		return true;
	}
	
	@Override
	protected boolean doChangeJob(JobVo jobVo) {
		return true;
	}

	@Override
	protected void doClearJob() {
		delayQueue.clear();
	}

	@Override
	public void callback(long jobId) {
		JobVo jobVo = jobContainerMap.get(jobId);
		if (jobVo != null) {							
			delayQueue.add(new DelayElement(jobVo));
		}
	}
	
	@Override
	public boolean addSystemJob(ExecuteSystem executeSystem) {
		return delayQueue.add(new SystemDelayElement(executeSystem));
	}
	
	private void systemCallback(ExecuteSystem executeSystem) {
		delayQueue.add(new SystemDelayElement(executeSystem));
	}
	
	private abstract class AbstractDelayElement implements Delayed {
		
		private long delayTime;
		
		public AbstractDelayElement(long delayTime) {
			this.delayTime = System.currentTimeMillis() + delayTime;
		}
		
		@Override
		public int compareTo(Delayed o) {
			if (this.delayTime < ((AbstractDelayElement) o).delayTime) {				
				return -1;
			} else if (this.delayTime > ((AbstractDelayElement) o).delayTime) {				
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
	
	private class DelayElement extends AbstractDelayElement implements Runnable {

		private JobVo jobVo;
		
		public DelayElement(JobVo jobVo) {
			super(jobVo.getDelay_Time() * 1000);
			this.jobVo = jobVo;
		}
		
		@Override
		public void run() {
			if (!executeTrigger.execute(jobVo, Constant.LOG_EXE_STATUS_TRIGGERING)) {
				callback(jobVo.getJob_Id());
			}
		}

		public JobVo getJobVo() {
			return jobVo;
		}
	}

	private class SystemDelayElement extends AbstractDelayElement implements Runnable {

		private ExecuteSystem executeSystem;		
		
		public SystemDelayElement(ExecuteSystem executeSystem) {
			super(executeSystem.getDelayTime() * 1000);
			this.executeSystem = executeSystem;
		}
		
		@Override
		public void run() {
			executeSystem.execute();
			systemCallback(executeSystem);
		}
	}
	
	public void setExecuteTrigger(ExecuteTrigger executeTrigger) {
		this.executeTrigger = executeTrigger;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
}
