package org.fl.noodleschedule.console.service;

import java.util.Date;
import java.util.List;

import org.fl.noodleschedule.console.vo.JobAlarmPeopleVo;
import org.fl.noodleschedule.console.vo.JobExecutorVo;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.console.vo.LogExecutorVo;
import org.fl.noodleschedule.console.vo.LogVo;

public interface CoreService {
	public JobVo queryJobById(long jobId) throws Exception;
	public long insertInitLog(long jobId, int exeType, long parentLogId) throws Exception;
	public LogVo queryLogById(long logId) throws Exception;
	public void updateLogExeStatus(long logId, int exeStatus, String exception) throws Exception;
	public int updateLogAndJobExeStatus(long jobId, long logId, int exeStatus, String exception) throws Exception;
	public List<LogExecutorVo> queryExecutorByLog(long logId, int exeStatus) throws Exception;
	public int updateJobExeStatusAndSpinLock(Long jobId, int exeStatusNew, int exeStatusOld, Long logId) throws Exception;
	public List<JobExecutorVo> queryExecutorListByJob(long jobId) throws Exception;
	public void insertTriggerExecutorAndUpdateLogException(long logId, long executorId, int exeStatus, String exception)throws Exception;
	public void updateFinishLogAndJobExeStatus(long jobId, long logId, int logExeStatus, int jobExeStatus, int planNum, int runNum) throws Exception;
	public void updateExecutorExeStatus(long logId, long executorId, int exeStatus, String exception) throws Exception;
	public JobVo saveCallbackResult(long logId, long executorId, int exeStatus, String exception) throws Exception;
	public List<JobVo> queryChildJobList(long jobId) throws Exception;
	public List<JobVo> queryDispatchJob() throws Exception;
	public List<JobVo> queryRunJob() throws Exception;
	public void updateTimeoutStatus(long logId, int timeoutStatus) throws Exception;
	public List<JobAlarmPeopleVo> queryAlarmPeopleByJob(long jobId) throws Exception;
	public List<JobVo> queryAllJob() throws Exception;
	public List<LogVo> queryLogListByJob(long jobId, Date triggerTimeStart) throws Exception;
	public void updateJobLastAlarmTime(long logId) throws Exception;
	public int queryLogCount(long jobId, Date triggerTimeStart) throws Exception;
	public void deleteLogs(Date start_Time) throws Exception;
	public String queryMasterIp() throws Exception;
}
