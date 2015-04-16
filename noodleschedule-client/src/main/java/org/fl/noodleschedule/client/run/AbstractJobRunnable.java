package org.fl.noodleschedule.client.run;

import org.fl.noodleschedule.client.callback.JobCallback;
import org.fl.noodleschedule.client.pojo.JobResult;

public abstract class AbstractJobRunnable implements JobRunnable {
	
	protected long logId;
	protected long executorId;
	
	protected JobCallback jobCallback;

	public long getLogId() {
		return logId;
	}
	public void setLogId(long logId) {
		this.logId = logId;
	}
	
	public long getExecutorId() {
		return executorId;
	}
	public void setExecutorId(long executorId) {
		this.executorId = executorId;
	}
	
	protected JobResult callback(int code, String exception) {
		return jobCallback.callback(new JobResult(logId, executorId, code, exception));
	}

	public void setJobCallback(JobCallback jobCallback) {
		this.jobCallback = jobCallback;
	}
}
