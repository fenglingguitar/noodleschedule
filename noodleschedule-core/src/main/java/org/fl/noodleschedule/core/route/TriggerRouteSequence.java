package org.fl.noodleschedule.core.route;

import java.util.List;

import org.fl.noodleschedule.console.vo.JobExecutorVo;

public class TriggerRouteSequence implements TriggerRoute {

	@Override
	public JobExecutorVo getExecutor(
			List<JobExecutorVo> jobExecutorVoListNoTried,
			List<JobExecutorVo> jobExecutorVoListTried, long lastExecutorId) {
		
		if (lastExecutorId == 0 && jobExecutorVoListNoTried.size() > 0 && !jobExecutorVoListTried.contains(jobExecutorVoListNoTried.get(0))) {
			return jobExecutorVoListNoTried.get(0);
		}
		
		if (jobExecutorVoListNoTried.size() > 0) {
			int index = 0;				
			for (JobExecutorVo jobExecutorVo : jobExecutorVoListNoTried) {
				if (jobExecutorVo.getExecutor_Id() == lastExecutorId) {
					JobExecutorVo jobExecutorVoNext = null;
					if (index < jobExecutorVoListNoTried.size() - 1) {
						jobExecutorVoNext = jobExecutorVoListNoTried.get(index + 1);
					} else {
						jobExecutorVoNext = jobExecutorVoListNoTried.get(0);
					}
					if (!jobExecutorVoListTried.contains(jobExecutorVoNext)) {
						return jobExecutorVoNext;
					}
					break;
				}
				index++;
			}			
		}
		
		return null;
	}

}
