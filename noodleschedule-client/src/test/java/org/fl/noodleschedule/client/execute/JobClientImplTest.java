package org.fl.noodleschedule.client.execute;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import org.fl.noodleschedule.client.pojo.JobParam;
import org.fl.noodleschedule.util.common.Constant;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/client/execute/noodleschedule-client-execute-bean.xml"
})
public class JobClientImplTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	JobClient jobClient;
	
	@Test
	public void testTriggerJob() {
		JobParam jobParam = new JobParam();
		jobParam.setLogId(1L);
		jobParam.setMethod("jobRunnable");
		assertTrue(jobClient.triggerJob(jobParam).getCode() == Constant.CLIENT_EXE_TRIGGER_SUCCESS);
		jobParam.setLogId(2L);
		assertTrue(jobClient.triggerJob(jobParam).getCode() == Constant.CLIENT_EXE_TRIGGER_RUNNING);
		jobParam.setLogId(3L);
		jobParam.setMethod("jobRunnable-NoHave");
		assertTrue(jobClient.triggerJob(jobParam).getCode() == Constant.CLIENT_EXE_TRIGGER_NOEXIST);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
	}

	@Test
	public void testCheckJobStatus() {
		JobParam jobParam = new JobParam();
		jobParam.setLogId(1L);
		jobParam.setMethod("jobRunnable");
		assertTrue(jobClient.checkJobStatus(jobParam).getCode() == Constant.CLIENT_EXE_STATUS_RUNNING);
	}

	@Test
	public void testStopJob() {
		JobParam jobParam = new JobParam();
		jobParam.setLogId(1L);
		jobParam.setMethod("jobRunnable");
		assertTrue(jobClient.stopJob(jobParam).getCode() == Constant.CLIENT_EXE_STOP_SUCCESS);
		assertTrue(jobClient.checkJobStatus(jobParam).getCode() == Constant.CLIENT_EXE_STATUS_NORUN);
	}

}
