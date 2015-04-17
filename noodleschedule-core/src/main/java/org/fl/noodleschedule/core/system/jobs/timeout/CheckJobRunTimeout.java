package org.fl.noodleschedule.core.system.jobs.timeout;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fl.noodleschedule.alarm.sender.SendLogAlarm;
import org.fl.noodleschedule.console.service.CoreService;
import org.fl.noodleschedule.console.system.jobs.AbstractExecuteSystem;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.console.vo.LogVo;
import org.fl.noodleschedule.core.trigger.ExecuteTrigger;
import org.fl.noodleschedule.util.common.Constant;

public class CheckJobRunTimeout extends AbstractExecuteSystem {

	Logger logger = LoggerFactory.getLogger(CheckJobRunTimeout.class);

	private CoreService coreService;

	private ExecuteTrigger executeTrigger;

	public CheckJobRunTimeout() {
		jobId = Long.MAX_VALUE - 1;
		jobName = "CheckJobRunTimeout";
	}

	private SendLogAlarm sendLogAlarm;

	@Override
	public boolean execute() {
		
		List<JobVo> jobVoList = getRunJobs();
		if (jobVoList == null) {
			return false;
		}
		
		for (JobVo jobVo : jobVoList) {
			
			LogVo logVo = getJobLog(jobVo.getLog_Id());
			if (logVo == null) {
				continue;
			}
			
			if (logVo.getExe_Status() == Constant.LOG_EXE_STATUS_TRIGGER_SUCCESS 
					&& (new Date()).getTime() - logVo.getStart_Time().getTime() > jobVo.getExe_Timeout() * 1000) {
				
				if(!executeTrigger.isRunning(jobVo.getLog_Id(), jobVo.getMethod())) {
					
					LogVo logVoNoRunning = getJobLog(jobVo.getLog_Id());
					
					if (logVoNoRunning != null && logVoNoRunning.getExe_Status() == Constant.LOG_EXE_STATUS_TRIGGER_SUCCESS) {
						
						if (jobVo.getTimeout_Retry() == Constant.TIMEOUT_RETRY_YES) {
							
							if (logVo.getExe_Type() == Constant.TRIGGER_TYPE_ORDINARY || logVo.getExe_Type() == Constant.TRIGGER_TYPE_TIMEOUT_RETRY || logVo.getExe_Type() == Constant.TRIGGER_TYPE_CHILD) {
								
								if(executeTrigger.executeOther(jobVo, Constant.TRIGGER_TYPE_TIMEOUT_RETRY, logVo.getLog_Id())) {
									resultTimeout(jobVo.getLog_Id(), Constant.TIMEOUT_STATUS_YES_RETRY_SUCCESS);
								} else {
									resultTimeout(jobVo.getLog_Id(), Constant.TIMEOUT_STATUS_YES_RETRY_FAIL);
								}
							}
						} else {
							
							resultTimeout(jobVo.getLog_Id(), Constant.TIMEOUT_STATUS_YES);
							
							jobDisappeareAndRestore(jobVo.getJob_Id(), jobVo.getLog_Id(), Constant.LOG_EXE_STATUS_RUN_DISAPPEAR, Constant.EXCEPTION_RUN_DISAPPEAR);
						}
					} else {
						resultTimeout(jobVo.getLog_Id(), Constant.TIMEOUT_STATUS_YES);
					}
				} else {				
					resultTimeout(jobVo.getLog_Id(), Constant.TIMEOUT_STATUS_YES);
				}
				
				sendLogAlarm.sendTimeoutAlarm(logVo.getJob_Id(), jobVo.getLog_Id());
			}
		}
		
		return true;
	}

	private List<JobVo> getRunJobs() {
		try {
			return coreService.queryRunJob();
		} catch (Exception e) {
			logger.error(
					"getRunJob -> coreService.queryRunJob -> Exception: {}", e);
		}
		return null;
	}

	public LogVo getJobLog(long logId) {
		try {
			return coreService.queryLogById(logId);
		} catch (Exception e) {
			logger.error(
					"getLog -> coreService.queryLogById -> logId: {} -> Exception: {}",
					logId, e);
		}
		return null;
	}

	private void resultTimeout(long logId, int timeoutStatus) {
		try {
			coreService.updateTimeoutStatus(logId, timeoutStatus);
		} catch (Exception e) {
			logger.error(
					"getLog -> coreService.queryLogById -> logId: {}, timeoutStatus: {} -> Exception: {}",
					logId, timeoutStatus, e);
		}
	}
	
	private boolean jobDisappeareAndRestore(final long jobId, final long logId, final int exeStatus, final String exception) {
		
		try {
			return coreService.updateLogAndJobExeStatus(jobId, logId, exeStatus, exception) > 0;
		} catch (Exception e) {
			logger.error("jobDisappeareAndRestore -> coreService.updateLogAndJobExeStatus -> jobId: {}, logId: {}, exeStatus: {}, exception: {} -> Exception: {}", jobId, logId, exeStatus, exception, e);
			return false;
		}
	}

	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}

	public void setExecuteTrigger(ExecuteTrigger executeTrigger) {
		this.executeTrigger = executeTrigger;
	}

	public void setSendLogAlarm(SendLogAlarm sendLogAlarm) {
		this.sendLogAlarm = sendLogAlarm;
	}
}
