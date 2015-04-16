package org.fl.noodleschedule.core.route;

import java.util.LinkedList;
import java.util.List;

import org.fl.noodleschedule.console.vo.JobExecutorVo;

public class TriggerRouteRandom implements TriggerRoute {

	@Override
	public JobExecutorVo getExecutor(
			List<JobExecutorVo> jobExecutorVoListNoTried,
			List<JobExecutorVo> jobExecutorVoListTried, long lastExecutorId) {

		List<JobExecutorVo> originalList = new LinkedList<JobExecutorVo>();
		originalList.addAll(jobExecutorVoListNoTried);
		
		originalList.removeAll(jobExecutorVoListTried);
		
		List<JobExecutorVo> weightList = new LinkedList<JobExecutorVo>();
		for (JobExecutorVo jobExecutorVo : originalList) {
			for (int i=0; i<jobExecutorVo.getWeight(); i++) {
				weightList.add(jobExecutorVo);
			}
		}
		
		if (weightList.size() > 0) {
			int index = (int) Math.round(Math.random() * (weightList.size() - 1));
			return weightList.get(index);
		}
		
		return null;
	}

}
