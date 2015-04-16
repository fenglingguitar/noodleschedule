package org.fl.noodleschedule.alarm.jobs;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/alarm/jobs/noodleschedule-alarm-job-checkinterval-bean.xml"
})
public class CheckIntervalAlarmTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	CheckIntervalAlarm checkIntervalAlarm;
	
	@Test
	public void testExecute() {
		assertTrue(checkIntervalAlarm.execute());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
	}
}
