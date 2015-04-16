package org.fl.noodleschedule.core.route;

import java.util.List;

import org.fl.noodleschedule.console.vo.JobExecutorVo;

public interface TriggerRoute {
	JobExecutorVo getExecutor(List<JobExecutorVo> jobExecutorVoListNoTried, List<JobExecutorVo> jobExecutorVoListTried, long lastExecutorId); 
}
