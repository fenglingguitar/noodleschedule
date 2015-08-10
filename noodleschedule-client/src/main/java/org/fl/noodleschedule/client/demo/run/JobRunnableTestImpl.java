package org.fl.noodleschedule.client.demo.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fl.noodleschedule.client.pojo.JobResult;
import org.fl.noodleschedule.client.run.AbstractJobRunnable;
import org.fl.noodleschedule.util.common.Constant;

public class JobRunnableTestImpl extends AbstractJobRunnable {

	private final static Logger logger = LoggerFactory.getLogger(JobRunnableTestImpl.class);
	
	private static int diff = (int) Math.round(Math.random() * 100000000);
	
	@Override
	public void run() {
		
		int sleepTime = (int) Math.round(Math.random() * 30);
		logger.info("{}: Start -> method: {}, param: {}, logId: {}, executorId: {}, sleepTime: {}", diff, method, param, logId, executorId, sleepTime);
		try {
			Thread.sleep(sleepTime * 1000);
		} catch (InterruptedException e) {
			logger.info("{}: Manual Stop -> method: {}, logId: {}, executorId: {}, sleepTime: {}", diff, method, logId, executorId, sleepTime);
			return;
		}
		logger.info("{}: Stop -> method: {}, logId: {}, executorId: {}, sleepTime: {}", diff, method, logId, executorId, sleepTime);
		
		int result = (int) Math.round(Math.random() * 5);
		
		if (result != 4) {			
			JobResult jobResult = callback(Constant.CLIENT_EXE_SUCCESS, "成功");
			if (!jobResult.getResult()) {
				logger.error("callback fail -> result: {}, exception: {} -> Exception: {}", Constant.CLIENT_EXE_SUCCESS, "成功", jobResult.getException());
			}
		} else {
			JobResult jobResult = callback(Constant.CLIENT_EXE_FAIL, "失败");
			if (!jobResult.getResult()) {
				logger.error("callback fail -> result: {}, exception: {} -> Exception: {}", Constant.CLIENT_EXE_FAIL, "失败", jobResult.getException());
			}
		}
	}

	
}
