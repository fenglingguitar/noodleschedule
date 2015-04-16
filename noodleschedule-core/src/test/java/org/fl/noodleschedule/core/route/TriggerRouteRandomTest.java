package org.fl.noodleschedule.core.route;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import org.fl.noodleschedule.console.vo.JobExecutorVo;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/core/route/noodleschedule-core-route-bean.xml"
})
public class TriggerRouteRandomTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	TriggerRoute triggerRouteRandom;
	
	@Test
	public void testgetExecutor() {
		
		List<JobExecutorVo> jobExecutorVoListNoTried = new ArrayList<JobExecutorVo>();
		
		JobExecutorVo jobExecutorVo1 = new JobExecutorVo();
		jobExecutorVo1.setExecutor_Id(1L);
		jobExecutorVo1.setWeight(1);
		jobExecutorVoListNoTried.add(jobExecutorVo1);
		
		List<JobExecutorVo> jobExecutorVoListTried = new ArrayList<JobExecutorVo>();
		
		assertTrue(triggerRouteRandom.getExecutor(jobExecutorVoListNoTried, jobExecutorVoListTried, 0) == jobExecutorVo1);

		jobExecutorVoListTried.add(jobExecutorVo1);

		assertTrue(triggerRouteRandom.getExecutor(jobExecutorVoListNoTried, jobExecutorVoListTried, 0) == null);

		JobExecutorVo jobExecutorVo2 = new JobExecutorVo();
		jobExecutorVo2.setExecutor_Id(2L);
		jobExecutorVo2.setWeight(1);
		jobExecutorVoListNoTried.add(jobExecutorVo2);
		
		assertTrue(triggerRouteRandom.getExecutor(jobExecutorVoListNoTried, jobExecutorVoListTried, 0) == jobExecutorVo2);
		
		JobExecutorVo jobExecutorVo3 = new JobExecutorVo();
		jobExecutorVo3.setExecutor_Id(3L);
		jobExecutorVo3.setWeight(1);
		jobExecutorVoListNoTried.add(jobExecutorVo3);
		
		JobExecutorVo jobExecutorVo = triggerRouteRandom.getExecutor(jobExecutorVoListNoTried, jobExecutorVoListTried, 0);
		
		assertTrue(jobExecutorVo == jobExecutorVo2 || jobExecutorVo == jobExecutorVo3);
		
		JobExecutorVo jobExecutorVo4 = new JobExecutorVo();
		jobExecutorVo4.setExecutor_Id(3L);
		jobExecutorVo4.setWeight(8);
		jobExecutorVoListNoTried.add(jobExecutorVo4);
		
		int num = 0;
		for (int i=1; i<100; i++) {
			if (triggerRouteRandom.getExecutor(jobExecutorVoListNoTried, jobExecutorVoListTried, 0) == jobExecutorVo4) {
				num++;
			}
		}
		assertTrue(num > 70 && num < 90);
	}
}
