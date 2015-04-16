package org.fl.noodleschedule.core.scheduler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.fl.noodleschedule.console.vo.JobVo;

public abstract class AbstractDespatcher implements Despatcher {

	protected ConcurrentMap<Long, JobVo> jobContainerMap = new ConcurrentHashMap<Long, JobVo>();
	
	@Override
	public boolean addJob(JobVo jobVo) {
		
		if(!jobContainerMap.containsKey(jobVo.getJob_Id())) {
			jobContainerMap.put(jobVo.getJob_Id(), jobVo);
			return doAddJob(jobVo);
		} else {
			JobVo jobVoRunning = jobContainerMap.get(jobVo.getJob_Id());
			if (jobVoRunning.getLastModified_Time().getTime() != jobVo.getLastModified_Time().getTime()) {
				jobContainerMap.put(jobVo.getJob_Id(), jobVo);
				return doChangeJob(jobVo);
			}
		}
		return false;
	}

	@Override
	public boolean removeJob(long jobId) {
		return doRemoveJob(jobId) && jobContainerMap.remove(jobId) != null;
	}

	@Override
	public boolean containJob(long jobId) {
		return jobContainerMap.containsKey(jobId);
	}

	@Override
	public void clearJob() {
		jobContainerMap.clear();
		doClearJob();
	}
	
	@Override
	public Set<Long> getAllJobId() {
		return jobContainerMap.keySet();
	}
	
	protected abstract boolean doAddJob(JobVo jobVo);
	protected abstract boolean doRemoveJob(long jobId);
	protected abstract boolean doChangeJob(JobVo jobVo);
	protected abstract void doClearJob();
}
