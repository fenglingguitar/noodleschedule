package org.fl.noodleschedule.client.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JobEmbedServerDemo9002 {
	
	public static void main(String args[]) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:org/fl/noodleschedule/client/demo/noodleschedule-client-demo-9002-bean.xml");
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		context.destroy();
	}
}
