package org.fl.noodleschedule.client.server;

import org.junit.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {
		"classpath:org/fl/noodleschedule/client/server/noodleschedule-client-server-8002-bean.xml"
})
public class JobEmbedServer8002Test extends AbstractJUnit4SpringContextTests {
	
	@Test
	public void test() {
	}
	
	public static void main(String args[]) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:org/fl/noodleschedule/client/server/noodleschedule-client-server-8002-bean.xml");
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		context.destroy();
	}
}
