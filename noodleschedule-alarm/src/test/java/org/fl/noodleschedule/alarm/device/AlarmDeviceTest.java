package org.fl.noodleschedule.alarm.device;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import org.fl.noodleschedule.alarm.pojo.AlarmInfo;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/alarm/device/noodleschedule-alarm-device-bean.xml"
})
public class AlarmDeviceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	AlarmDevice alarmDevice;
	
	@Test
	public void testAddAlarm() {
		alarmDevice.addAlarm(new AlarmInfo(1L, "NoodleSchedule测试", "NoodleSchedule测试", "NoodleSchedule测试"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
	}
}
