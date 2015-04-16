package org.fl.noodleschedule.core.system.jobs.clear;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/core/system/jobs/clear/noodleschedule-core-system-jobs-clear-bean.xml"
})
public class CheckClearTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	CheckClear checkClear;
	
	@Test
	public void testExecute() throws JobExecutionException {
		assertTrue(checkClear.execute());
	}
}
