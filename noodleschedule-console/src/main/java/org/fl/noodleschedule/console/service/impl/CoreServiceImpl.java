package org.fl.noodleschedule.console.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.fl.noodleschedule.console.service.CoreService;
import org.fl.noodleschedule.console.service.JobAlarmPeopleService;
import org.fl.noodleschedule.console.service.JobExecutorService;
import org.fl.noodleschedule.console.service.JobService;
import org.fl.noodleschedule.console.service.LockService;
import org.fl.noodleschedule.console.service.LogExecutorService;
import org.fl.noodleschedule.console.service.LogService;
import org.fl.noodleschedule.console.vo.JobAlarmPeopleVo;
import org.fl.noodleschedule.console.vo.JobExecutorVo;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.console.vo.LockVo;
import org.fl.noodleschedule.console.vo.LogExecutorVo;
import org.fl.noodleschedule.console.vo.LogVo;
import org.fl.noodleschedule.util.common.Constant;

@Service("coreService")
public class CoreServiceImpl implements CoreService {

	Logger logger = LoggerFactory.getLogger(CoreServiceImpl.class);
	
	private JobService jobService;
	private JobExecutorService jobExecutorService;
	private LogService logService;
	private LogExecutorService logExecutorService;
	private JobAlarmPeopleService jobAlarmPeopleService;
	private LockService lockService;

	@Override
	public JobVo queryJobById(long jobId) throws Exception {
		
		JobVo jobVo = new JobVo();
		jobVo.setJob_Id(jobId);
		List<JobVo> jobVoList = jobService.queryJobList(jobVo);
		if (jobVoList != null && jobVoList.size() > 0) {
			return jobVoList.get(0);
		}
		return null;
	}
	
	@Override
	public long insertInitLog(long jobId, int exeType, long parentLogId) throws Exception {
		
		LogVo logVo = new LogVo();
		logVo.setExe_Status(Constant.LOG_EXE_STATUS_TRIGGERING);
		logVo.setTrigger_Time(new Date());
		logVo.setExe_Type(exeType);
		logVo.setPlan_Num(0);
		logVo.setRun_Num(0);
		logVo.setComplete_Num(0);
		logVo.setConsume_Time(0L);
		logVo.setTimeout_Status(Constant.TIMEOUT_STATUS_NO);
		logVo.setJob_Id(jobId);
		if (parentLogId > 0) {
			logVo.setParent_Log_Id(parentLogId);
		}
		return logService.insertLogReturnId(logVo);
	}
	
	@Override
	public LogVo queryLogById(long logId) throws Exception {
		
		LogVo logVoParam = new LogVo();
		logVoParam.setLog_Id(logId);
		List<LogVo> logVoList = logService.queryLogList(logVoParam);
		if (logVoList != null && logVoList.size() > 0) {
			return logVoList.get(0);
		}
		return null;
	}

	@Override
	public void updateLogExeStatus(long logId, int exeStatus, String exception) throws Exception {
		
		LogVo logVo = new LogVo();
		logVo.setLog_Id(logId);
		logVo.setExe_Status(exeStatus);
		logVo.setException(exception);
		logService.updateLog(logVo);
	}
	
	@Override
	public int updateLogAndJobExeStatus(long jobId, long logId, int exeStatus, String exception) throws Exception {
		
		LogVo logVo = new LogVo();
		logVo.setLog_Id(logId);
		logVo.setExe_Status(exeStatus);
		logVo.setException(exception);
		logService.updateLog(logVo);
		
		JobVo jobVo = new JobVo();
		jobVo.setJob_Id(jobId);
		jobVo.setLog_Id(logId);
		jobVo.setExe_Status(Constant.JOB_EXE_STATUS_WAITING);
		return jobService.updateJobExeStatusByLog(jobVo);
	}
	
	@Override
	public List<LogExecutorVo> queryExecutorByLog(long logId, int exeStatus) throws Exception {
		LogExecutorVo logExecutorVo = new LogExecutorVo();
		logExecutorVo.setLog_Id(logId);
		logExecutorVo.setExe_Status(exeStatus);
		return logExecutorService.queryLogExecutorListByLog(logExecutorVo);
	}
	
	@Override
	public int updateJobExeStatusAndSpinLock(Long jobId, int exeStatusNew, int exeStatusOld, Long logId) throws Exception {
		JobVo jobVo = new JobVo();
		jobVo.setJob_Id(jobId);
		jobVo.setLog_Id(logId);
		jobVo.setExe_Status_New(exeStatusNew);
		jobVo.setExe_Status_Old(exeStatusOld);
		return jobService.updateJobExeStatus(jobVo);
	}

	@Override
	public List<JobExecutorVo> queryExecutorListByJob(long jobId) throws Exception {
		JobExecutorVo jobExecutorVo = new JobExecutorVo();
		jobExecutorVo.setJob_Id(jobId);
		jobExecutorVo.setStatus(Constant.STATUS_YES);
		return jobExecutorService.queryExecutorListByJob(jobExecutorVo);
	}
	
	@Override
	public void insertTriggerExecutorAndUpdateLogException(long logId, long executorId, int exeStatus, String exception) throws Exception {
		LogExecutorVo logExecutorVo = new LogExecutorVo();
		logExecutorVo.setLog_Id(logId);
		logExecutorVo.setExecutor_Id(executorId);
		logExecutorVo.setStart_Time(new Date());
		logExecutorVo.setExe_Status(exeStatus);
		logExecutorVo.setException(exception);
		logExecutorService.insertLogExecutor(logExecutorVo);
		
		LogVo logVo = new LogVo();
		logVo.setLog_Id(logId);
		logVo.setException(exception);
		logService.updateLog(logVo);
	}
	
	@Override
	public void updateFinishLogAndJobExeStatus(long jobId, long logId, int logExeStatus, int jobExeStatus, int planNum, int runNum) throws Exception {
		
		LogVo logVo = new LogVo();
		logVo.setLog_Id(logId);
		logVo.setPlan_Num(planNum);
		logVo.setRun_Num(runNum);
		logVo.setExe_Status(logExeStatus);
		if (logExeStatus != Constant.LOG_EXE_STATUS_TRIGGER_FAIL && logExeStatus != Constant.LOG_EXE_STATUS_TRIGGER_SKIP) {			
			logVo.setStart_Time(new Date());
		}
		logService.updateLog(logVo);
		
		JobVo jobVo = new JobVo();
		jobVo.setJob_Id(jobId);
		jobVo.setLog_Id(logId);
		jobVo.setExe_Status(jobExeStatus);
		jobService.updateJobExeStatusByLog(jobVo);
	}
	
	@Override
	public void updateExecutorExeStatus(long logId, long executorId, int exeStatus, String exception) throws Exception {
		
		LogExecutorVo logExecutorVo = new LogExecutorVo();
		logExecutorVo.setLog_Id(logId);
		logExecutorVo.setExecutor_Id(executorId);
		logExecutorVo.setExe_Status(exeStatus);
		logExecutorVo.setException(exception);
		logExecutorService.updateLogExecutor(logExecutorVo);
		
		LogVo logVo = new LogVo();
		logVo.setLog_Id(logId);
		logService.updateLogCumulativeComplete(logVo);
	}
	
	@Override
	public JobVo saveCallbackResult(long logId, long executorId, int exeStatus, String exception) throws Exception {
		
		LogExecutorVo logExecutorVo = new LogExecutorVo();
		logExecutorVo.setLog_Id(logId);
		logExecutorVo.setExecutor_Id(executorId);
		logExecutorVo.setEnd_Time(new Date());
		logExecutorVo.setExe_Status(exeStatus);
		logExecutorVo.setException(exception);
		if (logExecutorService.updateLogExecutorAndStatisticalTime(logExecutorVo) == 0) {
			logger.error("saveCallbackResult -> logExecutorService.updateLogExecutorAndStatisticalTime -> return 0 -> logId: {}, executorId: {}", logId, executorId);
			throw new Exception("Update executor status exception, logId: " + logId + ", executorId: " + executorId);
		}
		
		LogVo logVo = new LogVo();
		logVo.setLog_Id(logId);
		if (logService.updateLogCumulativeComplete(logVo) == 0) {
			logger.error("saveCallbackResult -> logService.updateLogCumulativeComplete -> return 0 -> logId: {}, executorId: {}", logId, executorId);
			throw new Exception("Update cumulative complete count exception, logId: " + logId);
		}
		
		LogExecutorVo logExecutorVoComplete = new LogExecutorVo();
		logExecutorVoComplete.setLog_Id(logId);
		List<LogExecutorVo> logExecutorVoListComplete = logExecutorService.queryCompleteExecutorList(logExecutorVoComplete);
		if (logExecutorVoListComplete == null) {
			logger.error("saveCallbackResult -> logExecutorService.queryCompleteExecutorList -> return null -> logId: {}, executorId: {}", logId, executorId);
			throw new Exception("Query complete executor exception, logId: " + logId);
		}
		
		LogExecutorVo logExecutorVoSuccess = new LogExecutorVo();
		logExecutorVoSuccess.setLog_Id(logId);
		logExecutorVoSuccess.setExe_Status(Constant.EXECUTOR_EXE_STATUS_RUN_SUCCESS);
		List<LogExecutorVo> logExecutorVoListSuccess = logExecutorService.queryLogExecutorListByLog(logExecutorVoSuccess);
		if (logExecutorVoListSuccess == null) {
			logger.error("saveCallbackResult -> logExecutorService.queryLogExecutorListByLog -> return null -> logId: {}, executorId: {}", logId, executorId);
			throw new Exception("Query success executor exception, logId: " + logId);
		}
		
		List<LogVo> logVoResultList = logService.queryLogList(logVo);
		if (logVoResultList == null || logVoResultList.size() == 0) {
			logger.error("saveCallbackResult -> logService.queryLogList -> return null or 0 -> logId: {}, executorId: {}", logId, executorId);
			throw new Exception("No have this log, logId: " + logId);
		}
		
		LogVo logVoNow = logVoResultList.get(0);
		if (logVoNow.getExe_Status() != Constant.LOG_EXE_STATUS_TRIGGER_SUCCESS && logVoNow.getExe_Status() != Constant.LOG_EXE_STATUS_TRIGGER_PART_SUCCESS) {
			logger.error("saveCallbackResult -> log exestatus != trigger_success -> logId: {}, executorId: {}", logId, executorId);
			throw new Exception("This execute log has bean close, logId: " + logId);
		}
		
		JobVo jobVoResult = new JobVo();
		jobVoResult.setJob_Id(logVoNow.getJob_Id());
		jobVoResult.setResult(Constant.CALLBACK_RESULT_NO_COMPLETE);
		jobVoResult.setExeType(logVoNow.getExe_Type());
		
		if (logExecutorVoListComplete.size() == logVoNow.getRun_Num()) {
			logVoNow.setEnd_Time(new Date());
			logVoNow.setConsume_Time(((new Date()).getTime() - logVoNow.getStart_Time().getTime()) / 1000);
			if (logExecutorVoListSuccess.size() == logExecutorVoListComplete.size()) {
				if (logExecutorVoListSuccess.size() == logVoNow.getPlan_Num()) {
					logVoNow.setExe_Status(Constant.LOG_EXE_STATUS_RUN_SUCCESS);						
				} else {
					logVoNow.setExe_Status(Constant.LOG_EXE_STATUS_RUN_PART_SUCCESS);
				}
				jobVoResult.setResult(Constant.CALLBACK_RESULT_ALL_SUCCESS);
			} else if (logExecutorVoListSuccess.size() > 0) {
				logVoNow.setExe_Status(Constant.LOG_EXE_STATUS_RUN_PART_SUCCESS);
				jobVoResult.setResult(Constant.CALLBACK_RESULT_PART_SUCCESS);
			} else {
				logVoNow.setExe_Status(Constant.LOG_EXE_STATUS_RUN_FAIL);
				jobVoResult.setResult(Constant.CALLBACK_RESULT_ALL_FAIL);
			}

			logService.updateLog(logVoNow);

			JobVo jobVo = new JobVo();
			jobVo.setJob_Id(logVoNow.getJob_Id());
			jobVo.setLog_Id(logVoNow.getLog_Id());
			jobVo.setExe_Status(Constant.JOB_EXE_STATUS_WAITING);
			if(jobService.updateJobExeStatusByLog(jobVo) == 0) {				
				logger.error("saveCallbackResult -> jobService.updateJobExeStatusByLog -> return 0 -> jobId: {}, logId: {}, executorId: {}", logVoNow.getJob_Id(), logId, executorId);
				throw new Exception("Update job status exception, jobId: " + logVoNow.getJob_Id());
			}
		}
		
		return jobVoResult;
	}

	@Override
	public List<JobVo> queryChildJobList(long jobId) throws Exception {
		
		JobVo jobVo = new JobVo();
		jobVo.setParent_Job_Id(jobId);
		jobVo.setStatus(Constant.STATUS_YES);
		return jobService.queryJobList(jobVo);
	}
	
	@Override
	public List<JobVo> queryQuartzAndCompletionJob() throws Exception {
		
		List<JobVo> jobVoAllList = new LinkedList<JobVo>();
		
		JobVo jobVoQuartz = new JobVo();
		jobVoQuartz.setStatus(Constant.STATUS_YES);
		jobVoQuartz.setJob_Type(Constant.JOB_TYPE_QUARTZ);
		List<JobVo> jobVoQuartzList = jobService.queryJobList(jobVoQuartz);
		jobVoAllList.addAll(jobVoQuartzList);

		JobVo jobVoCompletion = new JobVo();
		jobVoCompletion.setStatus(Constant.STATUS_YES);
		jobVoCompletion.setJob_Type(Constant.JOB_TYPE_COMPLETION);
		List<JobVo> jobVoCompletionList = jobService.queryJobList(jobVoCompletion);
		jobVoAllList.addAll(jobVoCompletionList);		
		
		return jobVoAllList;
	}
	
	@Override
	public List<JobVo> queryRunJob() throws Exception {
		JobVo jobVo = new JobVo();
		jobVo.setExe_Status(Constant.JOB_EXE_STATUS_RUNNING);
		return jobService.queryJobList(jobVo);
	}
	
	@Override
	public void updateTimeoutStatus(long logId, int timeoutStatus) throws Exception {
		LogVo logVo = new LogVo();
		logVo.setLog_Id(logId);
		logVo.setTimeout_Status(timeoutStatus);
		logService.updateLog(logVo);
	}
	
	@Override
	public List<JobAlarmPeopleVo> queryAlarmPeopleByJob(long jobId) throws Exception {
		JobAlarmPeopleVo jobAlarmPeopleVo = new JobAlarmPeopleVo();
		jobAlarmPeopleVo.setJob_Id(jobId);
		return jobAlarmPeopleService.queryJobAlarmPeopleListByJob(jobAlarmPeopleVo);
	}
	
	@Override
	public List<JobVo> queryAllJob() throws Exception {
		JobVo jobVo = new JobVo();
		jobVo.setStatus(Constant.STATUS_YES);
		return jobService.queryJobList(jobVo);
	}
	
	@Override
	public List<LogVo> queryLogListByJob(long jobId, Date triggerTimeStart) throws Exception {
		LogVo logVo = new LogVo();
		logVo.setJob_Id(jobId);
		logVo.setTrigger_Time_Start(triggerTimeStart);
		logVo.setTrigger_Time_End(new Date());
		return logService.queryLogList(logVo);
	}
	
	@Override
	public void updateJobLastAlarmTime(long jobId) throws Exception {
		JobVo jobVo = new JobVo();
		jobVo.setJob_Id(jobId);
		jobVo.setLast_Alarm_Time(new Date());
		jobService.updateJob(jobVo);
	}
	
	@Override
	public int queryLogCount(long jobId, Date triggerTimeStart) throws Exception {
		LogVo logVo = new LogVo();
		logVo.setJob_Id(jobId);
		logVo.setTrigger_Time_Start(triggerTimeStart);
		logVo.setTrigger_Time_End(new Date());
		return logService.queryLogCount(logVo);
	}
	
	@Override
	public void deleteLogs(Date start_Time) throws Exception {
		LogExecutorVo logExecutorVo = new LogExecutorVo();
		logExecutorVo.setStart_Time(start_Time);
		logExecutorService.deletesLogExecutorByTime(logExecutorVo);
		
		LogVo logVo = new LogVo();
		logVo.setStart_Time(start_Time);
		logService.deleteLogByTime(logVo);
	}

	@Override
	public String queryMasterIp() throws Exception {
		LockVo lockVo = new LockVo();
		lockVo.setId(1L);
		List<LockVo> lockVoList = lockService.queryLockList(lockVo);
		if (lockVoList == null || lockVoList.size() == 0) {
			return null;
		}
		return lockVoList.get(0).getIp();
	}
	
	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	public void setJobExecutorService(JobExecutorService jobExecutorService) {
		this.jobExecutorService = jobExecutorService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public void setLogExecutorService(LogExecutorService logExecutorService) {
		this.logExecutorService = logExecutorService;
	}
	
	public void setJobAlarmPeopleService(JobAlarmPeopleService jobAlarmPeopleService) {
		this.jobAlarmPeopleService = jobAlarmPeopleService;
	}

	public void setLockService(LockService lockService) {
		this.lockService = lockService;
	}
}
