package org.fl.noodleschedule.console.system.jobs;

public interface ExecuteSystem {
	public long getJobId();
	public String getJobName();
	public String getJobType();
	public String getCron();
	public long getDelayTime();
	public boolean execute();
}