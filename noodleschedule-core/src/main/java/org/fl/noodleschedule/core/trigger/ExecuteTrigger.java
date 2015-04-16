package org.fl.noodleschedule.core.trigger;

import org.fl.noodleschedule.console.vo.JobVo;

public interface ExecuteTrigger {
	
	public boolean execute(JobVo jobVo, int exeType);
	public boolean executeOther(JobVo jobVo, int exeType, long parentLogId);
	public boolean isRunning(long logId, String method);
	public boolean stop(long jobId, long logId);
}
