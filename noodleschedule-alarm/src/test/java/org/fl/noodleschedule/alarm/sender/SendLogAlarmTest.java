package org.fl.noodleschedule.alarm.sender;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/alarm/sender/noodleschedule-alarm-sender-log-bean.xml"
})
public class SendLogAlarmTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	SendLogAlarm sendLogAlarm;
	
	@Test
	public void testExecute() {
		
		sendLogAlarm.sendTriggerAlarm(1, 1, 1, "测试");
		sendLogAlarm.sendRunAlarm(1, 1, 1, "测试");
		sendLogAlarm.sendTimeoutAlarm(1, 1);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
	}
}
