package org.fl.noodleschedule.core.client.http;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import org.fl.noodleschedule.client.execute.JobClient;
import org.fl.noodleschedule.client.pojo.JobParam;
import org.fl.noodleschedule.core.client.JobClientFactory;
import org.fl.noodleschedule.util.common.Constant;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/core/client/http/noodleschedule-core-client-http-bean.xml"
})
public class JobClientHttpTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	JobClientFactory jobClientHttpFactory;
	
	@Test
	public void testTriggerJob() {
		JobClient jobClient = jobClientHttpFactory.createJobClient("127.0.0.1", 8001);
		JobParam jobParam = new JobParam();
		jobParam.setLogId(1L);
		jobParam.setMethod("jobRunnable");
		assertTrue(jobClient.triggerJob(jobParam).getCode() == Constant.CLIENT_EXE_TRIGGER_SUCCESS);
		jobParam.setLogId(2L);
		assertTrue(jobClient.triggerJob(jobParam).getCode() == Constant.CLIENT_EXE_TRIGGER_RUNNING);
		jobParam.setLogId(3L);
		jobParam.setMethod("jobRunnable-NoHave");
		assertTrue(jobClient.triggerJob(jobParam).getCode() == Constant.CLIENT_EXE_TRIGGER_NOEXIST);
	}

	@Test
	public void testCheckJobStatus() {
		JobClient jobClient = jobClientHttpFactory.createJobClient("127.0.0.1", 8001);
		JobParam jobParam = new JobParam();
		jobParam.setLogId(1L);
		jobParam.setMethod("jobRunnable");
		assertTrue(jobClient.checkJobStatus(jobParam).getCode() == Constant.CLIENT_EXE_STATUS_RUNNING);
	}

	@Test
	public void testStopJob() {
		JobClient jobClient = jobClientHttpFactory.createJobClient("127.0.0.1", 8001);
		JobParam jobParam = new JobParam();
		jobParam.setLogId(1L);
		jobParam.setMethod("jobRunnable");
		assertTrue(jobClient.stopJob(jobParam).getCode() == Constant.CLIENT_EXE_STOP_SUCCESS);
		assertTrue(jobClient.checkJobStatus(jobParam).getCode() == Constant.CLIENT_EXE_STATUS_NORUN);
	}
}
