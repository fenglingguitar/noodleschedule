package org.fl.noodleschedule.alarm.jobs;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fl.noodleschedule.alarm.device.AlarmDevice;
import org.fl.noodleschedule.alarm.pojo.AlarmInfo;
import org.fl.noodleschedule.console.service.CoreService;
import org.fl.noodleschedule.console.system.jobs.AbstractExecuteSystem;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.util.common.Constant;

public class CheckIntervalAlarm extends AbstractExecuteSystem {

	private final static Logger logger = LoggerFactory.getLogger(CheckIntervalAlarm.class);
	
	private CoreService coreService;
	
	private AlarmDevice alarmDevice;
	
	public CheckIntervalAlarm() {
		jobId = Long.MAX_VALUE - 2;
		jobName = "CheckIntervalAlarm";
	}

	@Override
	public boolean execute() {
		
		List<JobVo> jobVoList = getJobs();
		if (jobVoList == null) {
			logger.error("execute -> getJobs -> return null");
			return false;
		}
		
		for (JobVo jobVo : jobVoList) {
			if (jobVo.getIs_Alarm() == Constant.IS_ALARM_YES && jobVo.getMax_Interval_Time() != null) {
				int count = getLogCount(jobVo.getJob_Id(), new Date((new Date()).getTime() - jobVo.getMax_Interval_Time()));
				if (count != Constant.RETURN_INVALID) {
					if (count == 0) {
						alarmDevice.addAlarm(getAlarmInfo("定时任务调度异常，最大间隔时间内没有成功调度", jobVo));
					}
				} else {
					logger.error("execute -> getLogCount -> return invalid");
				}
			}
		}
		
		return true;
	}
	
	private List<JobVo> getJobs() {
		try {
			return coreService.queryAllJob();
		} catch (Exception e) {
			logger.error("getJobs -> coreService.queryAllJob -> Exception: {}", e);
		}
		return null;
	}
	
	private int getLogCount(long jobId, Date triggerTimeStart) {
		try {
			return coreService.queryLogCount(jobId, triggerTimeStart);
		} catch (Exception e) {
			logger.error("getJobs -> coreService.queryAllJob -> Exception: {}", e);
		}
		return Constant.RETURN_INVALID;
	}
	
	private AlarmInfo getAlarmInfo(String title, JobVo jobVo) {
		title += ", 任务ID：" + jobVo.getJob_Id();
		String summary = title + "，任务名称：" + jobVo.getJob_Name() + "，最大间隔时间(小时)：" + jobVo.getMax_Interval_Time() / 1000 / 60 /60;
		String content = summary;
		return new AlarmInfo(jobVo.getJob_Id(), title, summary, content);
	}
	
	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}
	
	public void setAlarmDevice(AlarmDevice alarmDevice) {
		this.alarmDevice = alarmDevice;
	}
}
