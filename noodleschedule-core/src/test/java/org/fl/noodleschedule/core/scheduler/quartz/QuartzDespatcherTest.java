package org.fl.noodleschedule.core.scheduler.quartz;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.core.scheduler.Despatcher;
import org.fl.noodleschedule.core.trigger.ExecuteTrigger;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/core/scheduler/quartz/noodleschedule-core-quartz-despatcher-bean.xml"
})
public class QuartzDespatcherTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	Despatcher quartzDespatcher;
	
	@Test
	public void testAddJob() {
		JobVo jobVo = new JobVo();
		jobVo.setJob_Id(1L);
		jobVo.setCron("0/1 * * * * ?");
		jobVo.setLastModified_Time(new Date());
		quartzDespatcher.addJob(jobVo);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
	}

	@Test
	public void testContainJob() {
		assertTrue(quartzDespatcher.containJob(1L));
	}

	@Test
	public void testRemoveJob() {
		quartzDespatcher.removeJob(1L);
		assertFalse(quartzDespatcher.containJob(1L));
	}

	@Test
	public void testClearJob() {
		JobVo jobVo1 = new JobVo();
		jobVo1.setJob_Id(1L);
		jobVo1.setCron("0/1 * * * * ?");
		jobVo1.setLastModified_Time(new Date());
		quartzDespatcher.addJob(jobVo1);
		JobVo jobVo2 = new JobVo();
		jobVo2.setJob_Id(2L);
		jobVo2.setCron("0/1 * * * * ?");
		jobVo2.setLastModified_Time(new Date());
		quartzDespatcher.addJob(jobVo2);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		quartzDespatcher.clearJob();
		assertFalse(quartzDespatcher.containJob(1L));
		assertFalse(quartzDespatcher.containJob(2L));
	}
	
	public static class ExecuteTriggerTest implements ExecuteTrigger {

		private final static Logger logger = LoggerFactory.getLogger(ExecuteTriggerTest.class);
		
		@Autowired
		Despatcher quartzDespatcher;
		
		@Override
		public boolean execute(JobVo jobVo, int exeType) {
			logger.info("Execute Trigger Running, jobId: {}, time: {}", jobVo.getJob_Id(), System.currentTimeMillis());
			quartzDespatcher.callback(jobVo.getJob_Id());
			return true;
		}

		@Override
		public boolean isRunning(long logId, String method) {
			return false;
		}

		@Override
		public boolean stop(long jobId, long logId) {
			return false;
		}

		@Override
		public boolean executeOther(JobVo jobVo, int exeType, long parentLogId) {
			return false;
		}
	}
}
