package org.fl.noodleschedule.alarm.device;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fl.noodle.common.util.thread.ExecutorThreadFactory;
import org.fl.noodleschedule.alarm.device.mail.MailSender;
import org.fl.noodleschedule.alarm.pojo.AlarmInfo;
import org.fl.noodleschedule.console.service.CoreService;
import org.fl.noodleschedule.console.vo.JobAlarmPeopleVo;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.util.common.Constant;

public class AlarmDevice {
	
	private final static Logger logger = LoggerFactory.getLogger(AlarmDevice.class);

	private int threadCount = 5;
	private volatile boolean stopSign = false;
	private ExecutorService executorServiceFixd;
	
	private BlockingQueue<AlarmInfo> alarmInfoQueue = new LinkedBlockingQueue<AlarmInfo>();
	
	private CoreService coreService;
	
	private MailSender mailSender;
	
	public void start() throws Exception {
		
		ExecutorThreadFactory executorThreadFactoryFixd = new ExecutorThreadFactory("AlarmDevice_Worker");
		executorServiceFixd = Executors.newFixedThreadPool(threadCount, executorThreadFactoryFixd);
		
		for (int i=0; i<threadCount; i++) {			
			executorServiceFixd.execute(new SendAlarmRunnable());		
		}
	}
	
	public void destroy() {	
		stopSign = true;
		executorServiceFixd.shutdownNow();
	}
	
	public void addAlarm(AlarmInfo alarmInfo) {
		if(!alarmInfoQueue.offer(alarmInfo)) {
			logger.error("addAlarm -> alarmInfoQueue.offer -> title: {}, summary: {}, content: {}", alarmInfo.getTitle(), alarmInfo.getSummary(), alarmInfo.getContent());
		}
	}
	
	private class SendAlarmRunnable implements Runnable {
				
		@Override
		public void run() {
			
			while (!stopSign) {	
				
				try {
					AlarmInfo alarmInfo = alarmInfoQueue.poll(Integer.MAX_VALUE, TimeUnit.SECONDS);
					JobVo jobVo = getJob(alarmInfo.getJobId());
					if (jobVo != null) {
						if (jobVo.getIs_Alarm() == Constant.IS_ALARM_YES) {
							List<JobAlarmPeopleVo> jobAlarmPeopleVoList = getAlarmPeople(jobVo.getJob_Id());
							if (jobAlarmPeopleVoList != null && jobAlarmPeopleVoList.size() > 0) {
								for (JobAlarmPeopleVo jobAlarmPeopleVo : jobAlarmPeopleVoList) {
									if (jobVo.getIs_Mail_Alarm() == Constant.IS_MAIL_ALARM_YES) {
										mailSender.sendMail(jobAlarmPeopleVo.getEmail(), alarmInfo.getTitle(), alarmInfo.getContent());
									}
									if (jobVo.getIs_Sms_Alarm() == Constant.IS_SMS_ALARM_YES) {
										
									}
								}
							} else {
								logger.error("SendAlarmRunnable -> run -> getAlarmPeople -> return null -> No alarm people be set");
							}
						}
					} else {
						logger.error("SendAlarmRunnable -> run -> getJob -> return null -> No send alarm");
					}
				} catch (InterruptedException e) {
					if (!stopSign) {
						logger.error("SendAlarmRunnable -> run -> poll -> InterruptedException: {}", e);
					}
				}
			}
		}
	}
	
	private JobVo getJob(long jobId) {
		try {
			return coreService.queryJobById(jobId);
		} catch (Exception e) {
			logger.error("getJob -> coreService.queryJobById -> jobId: {} -> Exception: {}", jobId, e);
		}
		return null;
	}
	
	private List<JobAlarmPeopleVo> getAlarmPeople(long jobId) {
		try {
			return coreService.queryAlarmPeopleByJob(jobId);
		} catch (Exception e) {
			logger.error("getAlarmPeople -> coreService.queryAlarmPeopleByJob -> jobId: {} -> Exception: {}", jobId, e);
		}
		return null;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	
	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
}
