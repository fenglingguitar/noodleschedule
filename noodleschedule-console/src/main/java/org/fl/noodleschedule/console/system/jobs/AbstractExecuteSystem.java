package org.fl.noodleschedule.console.system.jobs;

public abstract class AbstractExecuteSystem implements ExecuteSystem {

	protected long jobId;
	protected String jobName;
	protected String jobType = "quartz";
	protected String cron = "0/10 * * * * ?";
	
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
}
