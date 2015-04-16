package org.fl.noodleschedule.core.callback;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import org.fl.noodleschedule.client.callback.JobCallback;
import org.fl.noodleschedule.client.pojo.JobResult;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/core/callback/noodleschedule-core-callback-bean.xml"
})
public class JobCallbackTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	JobCallback jobCallback;
	
	@Test
	public void testCallback() {
		assertTrue(jobCallback.callback(new JobResult(12567, 1, 1)).getResult());
	}
}
