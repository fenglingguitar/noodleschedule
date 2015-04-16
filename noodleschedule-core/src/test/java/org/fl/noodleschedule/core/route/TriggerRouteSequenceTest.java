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
public class TriggerRouteSequenceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	TriggerRoute triggerRouteSequence;
	
	@Test
	public void testgetExecutor() {
		
		List<JobExecutorVo> jobExecutorVoListNoTried = new ArrayList<JobExecutorVo>();
		
		JobExecutorVo jobExecutorVo1 = new JobExecutorVo();
		jobExecutorVo1.setExecutor_Id(1L);
		jobExecutorVoListNoTried.add(jobExecutorVo1);
		
		List<JobExecutorVo> jobExecutorVoListTried = new ArrayList<JobExecutorVo>();
		
		assertTrue(triggerRouteSequence.getExecutor(jobExecutorVoListNoTried, jobExecutorVoListTried, 0) == jobExecutorVo1);

		jobExecutorVoListTried.add(jobExecutorVo1);

		assertTrue(triggerRouteSequence.getExecutor(jobExecutorVoListNoTried, jobExecutorVoListTried, 0) == null);

		JobExecutorVo jobExecutorVo2 = new JobExecutorVo();
		jobExecutorVo2.setExecutor_Id(2L);
		jobExecutorVoListNoTried.add(jobExecutorVo2);
		
		assertTrue(triggerRouteSequence.getExecutor(jobExecutorVoListNoTried, jobExecutorVoListTried, jobExecutorVo2.getExecutor_Id()) == null);
		
		JobExecutorVo jobExecutorVo3 = new JobExecutorVo();
		jobExecutorVo3.setExecutor_Id(3L);
		jobExecutorVoListNoTried.add(jobExecutorVo3);
		
		assertTrue(triggerRouteSequence.getExecutor(jobExecutorVoListNoTried, jobExecutorVoListTried, jobExecutorVo2.getExecutor_Id()) == jobExecutorVo3);
	}
}
