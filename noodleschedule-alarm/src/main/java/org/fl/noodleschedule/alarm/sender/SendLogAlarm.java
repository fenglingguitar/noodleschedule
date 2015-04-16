package org.fl.noodleschedule.alarm.sender;

import org.fl.noodleschedule.alarm.device.AlarmDevice;
import org.fl.noodleschedule.alarm.pojo.AlarmInfo;

public class SendLogAlarm {

	private AlarmDevice alarmDevice;
	
	public void sendTriggerAlarm(long jobId, long logId, long executorId, String exception) {
		alarmDevice.addAlarm(getAlarmInfo("定时任务触发失败", jobId, logId, executorId, exception));
	}
	
	public void sendRunAlarm(long jobId, long logId, long executorId, String exception) {
		alarmDevice.addAlarm(getAlarmInfo("定时任务执行失败", jobId, logId, executorId, exception));
	}
	
	public void sendTimeoutAlarm(long jobId, long logId) {
		alarmDevice.addAlarm(getAlarmInfo("定时任务执行超时", jobId, logId, 0, null));
	}
	
	private AlarmInfo getAlarmInfo(String title, long jobId, long logId, long executorId, String exception) {
		title += "，执行ID：" + logId;
		String summary = title + "，任务ID：" + jobId;
		if (executorId > 0) {
			summary += "，执行机器ID：" + executorId;
		}
		String content = summary;
		if (exception != null) {
			content += "，异常信息：" + exception;
		}
		return new AlarmInfo(jobId, title, summary, content);
	}
	
	public void setAlarmDevice(AlarmDevice alarmDevice) {
		this.alarmDevice = alarmDevice;
	}
}
