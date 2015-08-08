package org.fl.noodleschedule.alarm.device.mail;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/alarm/device/mail/noodleschedule-alarm-device-mail-bean.xml"
})
public class MailSenderTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	MailSender mailSender;
	
	@Test
	public void testSendMail() {
		assertTrue(mailSender.sendMail("fenglingguitar@163.com", "NoodleSchedule测试", "NoodleSchedule测试"));
	}
}
