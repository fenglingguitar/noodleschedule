package org.fl.noodleschedule.core.trigger;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.util.common.Constant;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/core/trigger/noodleschedule-core-trigger-bean.xml"
})
public class ExecuteTriggerImplTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	ExecuteTrigger executeTrigger;
	
	@Test
	public void testExecuteQuartz() {
		
		JobVo jobVo = new JobVo();
		jobVo.setJob_Id(1L);
		assertTrue(executeTrigger.execute(jobVo, Constant.TRIGGER_TYPE_ORDINARY));
	}
	
	@Test
	public void testExecuteCompletion() {
		
		JobVo jobVo = new JobVo();
		jobVo.setJob_Id(2L);
		assertTrue(executeTrigger.execute(jobVo, Constant.TRIGGER_TYPE_ORDINARY));
	}
	
	@Test
	public void testStop() {
		
		assertTrue(executeTrigger.stop(12, 30261));
	}
}
