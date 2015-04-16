package org.fl.noodleschedule.core.scheduler;

import java.util.Set;

import org.fl.noodleschedule.console.system.jobs.ExecuteSystem;
import org.fl.noodleschedule.console.vo.JobVo;

public interface Despatcher {
	
	public boolean addJob(JobVo jobVo);
	public boolean removeJob(long jobId);
	public boolean containJob(long jobId);
	public void clearJob();
	public void callback(long jobId);
	public Set<Long> getAllJobId();
	public boolean addSystemJob(ExecuteSystem executeSystem);
}
