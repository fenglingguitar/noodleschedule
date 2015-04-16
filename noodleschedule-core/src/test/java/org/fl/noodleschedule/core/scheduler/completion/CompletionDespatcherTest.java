package org.fl.noodleschedule.core.scheduler.completion;

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
		"classpath:org/fl/noodleschedule/core/scheduler/completion/noodleschedule-core-completion-despatcher-bean.xml"
})
public class CompletionDespatcherTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	Despatcher completionDespatcher;
	
	@Test
	public void testAddJob() {
		JobVo jobVo = new JobVo();
		jobVo.setJob_Id(1L);
		jobVo.setDelay_Time(1L);
		jobVo.setLastModified_Time(new Date());
		completionDespatcher.addJob(jobVo);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
	}

	@Test
	public void testContainJob() {
		assertTrue(completionDespatcher.containJob(1L));
	}

	@Test
	public void testRemoveJob() {
		completionDespatcher.removeJob(1L);
		assertFalse(completionDespatcher.containJob(1L));
	}

	@Test
	public void testClearJob() {
		JobVo jobVo1 = new JobVo();
		jobVo1.setJob_Id(1L);
		jobVo1.setDelay_Time(1L);
		jobVo1.setLastModified_Time(new Date());
		completionDespatcher.addJob(jobVo1);
		JobVo jobVo2 = new JobVo();
		jobVo2.setJob_Id(2L);
		jobVo2.setDelay_Time(1000L);
		jobVo2.setLastModified_Time(new Date());
		completionDespatcher.addJob(jobVo2);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		completionDespatcher.clearJob();
		assertFalse(completionDespatcher.containJob(1L));
		assertFalse(completionDespatcher.containJob(2L));
	}
	
	public static class ExecuteTriggerTest implements ExecuteTrigger {

		private final static Logger logger = LoggerFactory.getLogger(ExecuteTriggerTest.class);
		
		@Autowired
		Despatcher completionDespatcher;
		
		@Override
		public boolean execute(JobVo jobVo, int exeType) {
			logger.info("Execute Trigger Running, jobId: {}, time: {}", jobVo.getJob_Id(), System.currentTimeMillis());
			completionDespatcher.callback(jobVo.getJob_Id());
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
