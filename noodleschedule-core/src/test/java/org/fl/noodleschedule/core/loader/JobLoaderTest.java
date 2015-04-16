package org.fl.noodleschedule.core.loader;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/core/loader/noodleschedule-core-loader-bean.xml"
})
public class JobLoaderTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	JobLoader jobLoader;
	
	@Test
	public void testOnMessageGetLock() {
		jobLoader.onMessageGetLock();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
	}

	@Test
	public void testOnMessageLossLock() {
		jobLoader.onMessageLossLock();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
	}

	@Test
	public void testOnMessageStop() {
		jobLoader.onMessageStop();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
	}
}
