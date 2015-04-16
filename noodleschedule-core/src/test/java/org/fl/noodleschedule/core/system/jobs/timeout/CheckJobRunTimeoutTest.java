package org.fl.noodleschedule.core.system.jobs.timeout;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/core/system/jobs/timeout/noodleschedule-core-system-jobs-run-timout-bean.xml"
})
public class CheckJobRunTimeoutTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	CheckJobRunTimeout checkJobRunTimeout;
	
	@Test
	public void testExecute() throws JobExecutionException {
		assertTrue(checkJobRunTimeout.execute());
	}
}
