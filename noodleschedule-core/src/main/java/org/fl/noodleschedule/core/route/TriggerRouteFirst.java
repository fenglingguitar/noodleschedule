package org.fl.noodleschedule.core.route;

import java.util.LinkedList;
import java.util.List;

import org.fl.noodleschedule.console.vo.JobExecutorVo;

public class TriggerRouteFirst implements TriggerRoute {

	@Override
	public JobExecutorVo getExecutor(
			List<JobExecutorVo> jobExecutorVoListNoTried,
			List<JobExecutorVo> jobExecutorVoListTried, long lastExecutorId) {
		
		List<JobExecutorVo> originalList = new LinkedList<JobExecutorVo>();
		originalList.addAll(jobExecutorVoListNoTried);
		
		originalList.removeAll(jobExecutorVoListTried);
		
		if(originalList.size() > 0) {
			return originalList.get(0);
		}
		
		return null;
	}

}
