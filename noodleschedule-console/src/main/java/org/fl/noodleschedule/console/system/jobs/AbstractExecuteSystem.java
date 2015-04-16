package org.fl.noodleschedule.console.system.jobs;

public abstract class AbstractExecuteSystem implements ExecuteSystem {

	protected long jobId;
	protected String jobName;
	protected String jobType;
	protected String cron = "0/10 * * * * ?";
	protected long delayTime = 1;
	
	@Override
	public long getJobId() {
		return jobId;
	}
	public void setJobId(long jobId) {
		this.jobId = jobId;
	}
	
	@Override
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@Override
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	@Override
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	
	@Override
	public long getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}
}
