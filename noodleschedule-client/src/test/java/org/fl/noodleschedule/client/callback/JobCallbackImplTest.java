package org.fl.noodleschedule.client.callback;

import static org.junit.Assert.*;

import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import org.fl.noodleschedule.client.pojo.JobResult;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/client/callback/noodleschedule-client-callback-bean.xml"
})
public class JobCallbackImplTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	JobCallback JobCallback;
	
	@Test
	public void testCallback() {
		assertTrue(JobCallback.callback(new JobResult(1, 1, 1, "test")).getResult());
	}
}
