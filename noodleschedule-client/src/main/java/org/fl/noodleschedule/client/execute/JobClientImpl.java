package org.fl.noodleschedule.client.execute;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.fl.noodleschedule.client.pojo.JobParam;
import org.fl.noodleschedule.client.pojo.JobResult;
import org.fl.noodleschedule.client.run.JobRunnable;
import org.fl.noodleschedule.util.common.Constant;

public class JobClientImpl implements JobClient {

	private ConcurrentMap<String, Thread> threadMap = new ConcurrentHashMap<String, Thread>();
	private Map<String, JobRunnable> jobRunnableMap = new HashMap<String, JobRunnable>();

	@Override
	public JobResult triggerJob(JobParam jobParam) {

		Thread thread = threadMap.get(jobParam.getMethod());
		if (thread != null && thread.isAlive()) {
			return new JobResult(Constant.CLIENT_EXE_TRIGGER_RUNNING, Constant.EXCEPTION_STILL_RUNNING);
		}
		
		JobRunnable jobRunnable = jobRunnableMap.get(jobParam.getMethod());
		if (jobRunnable != null) {
			jobRunnable.setLogId(jobParam.getLogId());
			jobRunnable.setExecutorId(jobParam.getExecutorId());
			thread = new Thread(jobRunnable, "noodleschedule-jobclient-" + jobParam.getMethod());
			threadMap.put(jobParam.getMethod(), thread);
			thread.start();
			return new JobResult(Constant.CLIENT_EXE_TRIGGER_SUCCESS);
		} else {
			return new JobResult(Constant.CLIENT_EXE_TRIGGER_NOEXIST, Constant.EXCEPTION_METHOD_NOEXIST);
		}
	}

	@Override
	public JobResult checkJobStatus(JobParam jobParam) {
		Thread thread = threadMap.get(jobParam.getMethod());
		if (thread != null && thread.isAlive()) {
			JobRunnable jobRunnable = jobRunnableMap.get(jobParam.getMethod());
			return new JobResult(jobRunnable.getLogId(), jobRunnable.getExecutorId(), Constant.CLIENT_EXE_STATUS_RUNNING);
		} else {
			return new JobResult(Constant.CLIENT_EXE_STATUS_NORUN);
		}
	}

	@Override
	public JobResult stopJob(JobParam jobParam) {
		Thread thread = threadMap.get(jobParam.getMethod());
		if (thread == null || !thread.isAlive()) {
			return new JobResult(Constant.CLIENT_EXE_STOP_NOEXIST, Constant.EXCEPTION_ALREADY_STOP);
		}
		
		for (int i=0; i<8 && thread.isAlive(); i++) {
			thread.interrupt();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		
		if (!thread.isAlive()) {
			return new JobResult(Constant.CLIENT_EXE_STOP_SUCCESS);
		} else {
			return new JobResult(Constant.CLIENT_EXE_STOP_FAIL, Constant.EXCEPTION_FAIL_STOP);
		}
	}
	
	public void setJobRunnableMap(Map<String, JobRunnable> jobRunnableMap) {
		this.jobRunnableMap = jobRunnableMap;
	}
}
