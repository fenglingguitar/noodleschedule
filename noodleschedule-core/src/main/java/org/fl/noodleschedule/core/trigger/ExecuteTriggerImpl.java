package org.fl.noodleschedule.core.trigger;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fl.noodleschedule.alarm.sender.SendLogAlarm;
import org.fl.noodleschedule.client.execute.JobClient;
import org.fl.noodleschedule.client.pojo.JobParam;
import org.fl.noodleschedule.client.pojo.JobResult;
import org.fl.noodleschedule.console.service.CoreService;
import org.fl.noodleschedule.console.vo.JobExecutorVo;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.console.vo.LogExecutorVo;
import org.fl.noodleschedule.console.vo.LogVo;
import org.fl.noodleschedule.core.client.JobClientFactory;
import org.fl.noodleschedule.core.route.TriggerRoute;
import org.fl.noodleschedule.util.common.Constant;

public class ExecuteTriggerImpl implements ExecuteTrigger {

	Logger logger = LoggerFactory.getLogger(ExecuteTriggerImpl.class);
	
	private CoreService coreService;
	
	private Map<String, JobClientFactory> jobClientFactoryMap;
	private Map<String, TriggerRoute> triggerRouteMap;
	
	private int triggerTimeout = 20000;
	
	private SendLogAlarm sendLogAlarm;
	
	@Override
	public boolean execute(JobVo jobVo, int exeType) {
		return executeOther(jobVo, exeType, 0);
	}
	
	@Override
	public boolean executeOther(JobVo jobVo, int exeType, long parentLogId) {
		
		JobVo jobVoNow = getJobInfo(jobVo.getJob_Id());
		if (jobVoNow == null) {
			logger.error("execute -> getJobInfo -> return null -> jobId: {}, exeType: {}", jobVo.getJob_Id(), exeType);
			return false;
		}
		
		long logId = initLog(jobVoNow.getJob_Id(), exeType, parentLogId);
		if (logId == Constant.RETURN_INVALID) {
			logger.error("execute -> initLog -> return invalid -> jobId: {}, exeType: {}", jobVo.getJob_Id(), exeType);
			return false;
		}
		
		if (jobVoNow.getExe_Status() == Constant.JOB_EXE_STATUS_TRIGGERING) {
			
			if (!isTriggerTimeout(jobVoNow.getLog_Id())) {
				otherTriggeringSkipTrigger(logId);
				return false;
			}
			
			if (!expireTrigger(jobVoNow.getJob_Id(), jobVoNow.getLog_Id())) {
				logger.error("execute -> expireTrigger -> return invalid -> jobId: {}, exeType: {}, logId: {}", jobVo.getJob_Id(), exeType, jobVoNow.getLog_Id());
				return false;
			}
			
		} else if (jobVoNow.getExe_Status() == Constant.JOB_EXE_STATUS_RUNNING) {
			
			if (isRunning(jobVoNow.getLog_Id(), jobVoNow.getMethod())) {
				stillRunningSkipTrigger(logId);
				return false;
			}
			
			if(!jobDisappeareAndRestore(jobVoNow.getJob_Id(), jobVoNow.getLog_Id())) {
				logger.error("execute -> jobDisappeareAndRestore -> return false -> jobId: {}, logId: {}", jobVo.getJob_Id(), jobVoNow.getLog_Id());
				return false;
			}
		} 
		
		if(!catchJob(jobVoNow.getJob_Id(), logId)) {
			noCatchJobSkipTrigger(logId);
			return false;
		}
		
		List<JobExecutorVo> jobExecutorVoList = getExecutorList(jobVoNow.getJob_Id());
		if (jobExecutorVoList == null) {
			logger.error("execute -> getExecutorList -> return null -> jobId: {}", jobVo.getJob_Id());
			return false;
		}
		
		if (jobExecutorVoList.size() == 0) {
			noExecutorSkipTrigger(jobVoNow.getJob_Id(), logId);
			return false;
		}
		
		return triggerExecutor(jobVoNow.getRoute_Type(), jobVoNow.getJob_Id(), logId, jobVoNow.getMethod(), jobExecutorVoList, jobVo);
	}
	
	@Override
	public boolean isRunning(long logId, String method) {
		
		List<LogExecutorVo> logExecutorVoList = null;
		
		try {
			logExecutorVoList = coreService.queryExecutorByLog(logId, Constant.EXECUTOR_EXE_STATUS_TRIGGER_SUCCESS);
		} catch (Exception e) {
			logger.error("getLogRunningExecutor -> coreService.queryExecutorByLog -> logId: {} -> Exception: {}", logId, e);
			return true;
		}
		
		if (logExecutorVoList != null && logExecutorVoList.size() > 0) {
			if(checkRunningExecutor(logExecutorVoList, method)) {
				return true;
			}
		} 
		
		return false;
	}	
	
	@Override
	public boolean stop(long jobId, long logId) {
		
		JobVo jobVo = getJobInfo(jobId);
		if (jobVo == null) {
			logger.error("stop -> getJobInfo -> return null -> jobId: {}", jobId);
			return false;
		}
		
		if (jobVo.getExe_Status() != Constant.JOB_EXE_STATUS_RUNNING) {
			return false;
		}
		
		List<JobExecutorVo> jobExecutorVoList = getExecutorList(jobId);
		if (jobExecutorVoList == null) {
			logger.error("stop -> getExecutorList -> return null -> jobId: {}", jobId);
			return false;
		}
		
		return stopAllJob(jobId, logId, jobVo.getMethod(), jobExecutorVoList);
	}
	
	public boolean isTriggerTimeout(long logId) {
		
		LogVo logVo = null;
		
		try {
			logVo = coreService.queryLogById(logId);
		} catch (Exception e) {
			logger.error("getTriggeringLog -> coreService.queryLogById -> logId: {} -> Exception: {}", logId, e);
			return false;
		}
		
		if (logVo == null) {
			return true;
		} else {			
			if ((new Date()).getTime() - logVo.getTrigger_Time().getTime() > triggerTimeout) {
				return true;
			} 
		}

		return false;
	}
	
	private JobVo getJobInfo(long jobId) {
		
		try {
			return coreService.queryJobById(jobId);
		} catch (Exception e) {
			logger.error("getJobInfo -> coreService.queryJobById -> jobId: {} -> Exception: {}", jobId, e);
		}
		return null;
	}

	private long initLog(long jobId, int exeType, long parentLogId) {
		try {
			return coreService.insertInitLog(jobId, exeType, parentLogId);
		} catch (Exception e) {
			logger.error("initLog -> coreService.insertInitLog -> jobId: {}, exeType: {} -> Exception: {}", jobId, exeType, e);
		}
		return Constant.RETURN_INVALID;
	}
	
	private boolean catchJob(long jobId, long logId) {
		try {
			return coreService.updateJobExeStatusAndSpinLock(jobId, Constant.JOB_EXE_STATUS_TRIGGERING, Constant.JOB_EXE_STATUS_WAITING, logId) > 0;
		} catch (Exception e) {
			logger.error("catchJob -> coreService.updateJobExeStatusAndSpinLock -> jobId: {}, exeStatusNew: {}, exeStatusOld: {}, logId: {} -> Exception: {}", jobId, Constant.JOB_EXE_STATUS_TRIGGERING, Constant.JOB_EXE_STATUS_WAITING, logId, e);
			return false;
		}
	}
	
	private List<JobExecutorVo> getExecutorList(long jobId) {
		
		try {
			return coreService.queryExecutorListByJob(jobId);
		} catch (Exception e) {
			logger.error("getExecutorList -> coreService.queryExecutorListByJob -> jobId: {} -> Exception: {}", jobId, e);
		}
		return null;
	}
	
	private boolean skipTriggerWithReleaseJob(final long jobId, final long logId, final int exeStatus, final String exception) {
		
		try {
			return coreService.updateLogAndJobExeStatus(jobId, logId, exeStatus, exception) > 0;
		} catch (Exception e) {
			logger.error("skipTriggerWithReleaseJob -> coreService.updateLogAndJobExeStatus -> jobId: {}, logId: {}, exeStatus: {}, exception: {} -> Exception: {}", jobId, logId, exeStatus, exception, e);
			return false;
		}
	}

	private boolean expireTrigger(long jobId, long logId) {
		return skipTriggerWithReleaseJob(jobId, logId, Constant.LOG_EXE_STATUS_TRIGGER_TIMEOUT, Constant.EXCEPTION_EXPIRE_TRIGGER);
	}
	
	private boolean jobDisappeareAndRestore(final long jobId, final long logId) {
		return skipTriggerWithReleaseJob(jobId, logId, Constant.LOG_EXE_STATUS_RUN_DISAPPEAR, Constant.EXCEPTION_RUN_DISAPPEAR);
	}
	
	private boolean noExecutorSkipTrigger(long jobId, long logId) {
		return skipTriggerWithReleaseJob(jobId, logId, Constant.LOG_EXE_STATUS_TRIGGER_SKIP, Constant.EXCEPTION_NO_EXECUTOR);
	}
	
	private boolean skipTriggerNoReleaseJob(long logId, int exeStatus, String exception) {
		try {
			coreService.updateLogExeStatus(logId, exeStatus, exception);
		} catch (Exception e) {
			logger.error("skipTriggerNoReleaseJob -> coreService.updateLogExeStatus -> logId: {}, exeStatus: {}, exception: {} -> Exception: {}", logId, exeStatus, exception, e);
			return false;
		}
		return true;
	}
	
	private boolean otherTriggeringSkipTrigger(long logId) {
		return skipTriggerNoReleaseJob(logId, Constant.LOG_EXE_STATUS_TRIGGER_SKIP, Constant.EXCEPTION_OTHER_TRIGGERING);
	}
	
	private boolean stillRunningSkipTrigger(long logId) {
		return skipTriggerNoReleaseJob(logId, Constant.LOG_EXE_STATUS_TRIGGER_SKIP, Constant.EXCEPTION_STILL_RUNNING);
	}
		
	private boolean noCatchJobSkipTrigger(long logId) {
		return skipTriggerNoReleaseJob(logId, Constant.LOG_EXE_STATUS_TRIGGER_SKIP, Constant.EXCEPTION_NO_CATCH);
	}

	private boolean triggerExecutor(String routeType, long jobId, long logId, String method, List<JobExecutorVo> jobExecutorVoList, JobVo jobVo) {
		if (!routeType.equals(Constant.ROUTE_TYPE_ALL)) {
			return triggerExecutorOne(routeType, jobId, logId, method, jobExecutorVoList, jobVo);
		} else {
			return triggerExecutorAll(jobId, logId, method, jobExecutorVoList);
		}
	}
	
	private boolean triggerExecutorOne(String routeType, long jobId, long logId, String method, List<JobExecutorVo> jobExecutorVoList, JobVo jobVo) {
		
		TriggerRoute triggerRoute = triggerRouteMap.get(routeType);
		
		List<JobExecutorVo> jobExecutorVoListTried = new LinkedList<JobExecutorVo>();
		
		JobExecutorVo jobExecutorVo = null;
		while ((jobExecutorVo = triggerRoute.getExecutor(jobExecutorVoList, jobExecutorVoListTried, jobVo.getLastExecutorId())) != null) {
			jobVo.setLastExecutorId(jobExecutorVo.getExecutor_Id());
			JobClient jobClient = getJobClient(jobExecutorVo.getRpc_Type(), jobExecutorVo.getIp(), jobExecutorVo.getPort(), jobExecutorVo.getUrl());
			if (jobClient == null) {		
				logger.error("triggerExecutorOne -> getJobClient -> return null -> rpcType: {}, ip: {}, port: {}, url: {}", jobExecutorVo.getRpc_Type(), jobExecutorVo.getIp(), jobExecutorVo.getPort(), jobExecutorVo.getUrl());
				continue;
			}
			JobResult jobResult = jobClient.triggerJob(new JobParam(logId, jobExecutorVo.getExecutor_Id(), method));
			if (jobResult.getResult()) {
				if (jobResult.getCode() == Constant.CLIENT_EXE_TRIGGER_SUCCESS) {
					triggerExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_TRIGGER_SUCCESS, null);
					triggerResultSuccess(jobId, logId, 1, 1);
					return true;
				} else if (jobResult.getCode() == Constant.CLIENT_EXE_TRIGGER_RUNNING) {
					triggerExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_TRIGGER_RUNNING, Constant.EXCEPTION_STILL_RUNNING);
					break; 
				} else if (jobResult.getCode() == Constant.CLIENT_EXE_TRIGGER_FAIL) {
					triggerExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_TRIGGER_FAIL, Constant.EXCEPTION_TRIGGER_FAIL);		
					sendAlarm(Constant.ALARM_INFO_TRIGGER_FAIL, jobId, logId, jobExecutorVo.getExecutor_Id(), Constant.EXCEPTION_TRIGGER_FAIL);
				} else if (jobResult.getCode() == Constant.CLIENT_EXE_TRIGGER_NOEXIST) {
					triggerExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_TRIGGER_NOEXIST, Constant.EXCEPTION_METHOD_NOEXIST);
					sendAlarm(Constant.ALARM_INFO_TRIGGER_FAIL, jobId, logId, jobExecutorVo.getExecutor_Id(), Constant.EXCEPTION_METHOD_NOEXIST);
				}
			} else {
				triggerExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_TRIGGER_NET_FAIL, jobResult.getException());
				sendAlarm(Constant.ALARM_INFO_TRIGGER_FAIL, jobId, logId, jobExecutorVo.getExecutor_Id(), jobResult.getException());
			}
			jobExecutorVoListTried.add(jobExecutorVo);
		}
		
		triggerResultFail(jobId, logId, 1);
		return false;
	}
	
	private boolean triggerExecutorAll(long jobId, long logId, String method, List<JobExecutorVo> jobExecutorVoList) {
		
		int conutTriggerRun = 0;
		int conutTriggerFail = 0;

		for (JobExecutorVo jobExecutorVo : jobExecutorVoList) {
			JobClient jobClient = getJobClient(jobExecutorVo.getRpc_Type(), jobExecutorVo.getIp(), jobExecutorVo.getPort(), jobExecutorVo.getUrl());
			if (jobClient == null) {
				logger.error("triggerExecutorALL -> getJobClient -> return null -> rpcType: {}, ip: {}, port: {}, url: {}", jobExecutorVo.getRpc_Type(), jobExecutorVo.getIp(), jobExecutorVo.getPort(), jobExecutorVo.getUrl());
				continue;
			}
			JobResult jobResult = jobClient.triggerJob(new JobParam(logId, jobExecutorVo.getExecutor_Id(), method));
			if (jobResult.getResult()) {
				if (jobResult.getCode() == Constant.CLIENT_EXE_TRIGGER_SUCCESS) {
					conutTriggerRun++;
					triggerExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_TRIGGER_SUCCESS, null);
				} else if (jobResult.getCode() == Constant.CLIENT_EXE_TRIGGER_RUNNING) {
					triggerExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_TRIGGER_RUNNING, Constant.EXCEPTION_STILL_RUNNING);
				} else if (jobResult.getCode() == Constant.CLIENT_EXE_TRIGGER_FAIL) {
					conutTriggerFail++;
					triggerExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_TRIGGER_FAIL, Constant.EXCEPTION_TRIGGER_FAIL);			
					sendAlarm(Constant.ALARM_INFO_TRIGGER_FAIL, jobId, logId, jobExecutorVo.getExecutor_Id(), Constant.EXCEPTION_TRIGGER_FAIL);
				} else if (jobResult.getCode() == Constant.CLIENT_EXE_TRIGGER_NOEXIST) {
					conutTriggerFail++;
					triggerExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_TRIGGER_NOEXIST, Constant.EXCEPTION_METHOD_NOEXIST);
					sendAlarm(Constant.ALARM_INFO_TRIGGER_FAIL, jobId, logId, jobExecutorVo.getExecutor_Id(), Constant.EXCEPTION_METHOD_NOEXIST);
				}
			} else {
				conutTriggerFail++;
				triggerExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_TRIGGER_NET_FAIL, jobResult.getException());
				sendAlarm(Constant.ALARM_INFO_TRIGGER_FAIL, jobId, logId, jobExecutorVo.getExecutor_Id(), jobResult.getException());
			}
		}
		
		if (conutTriggerFail == jobExecutorVoList.size()) {
			triggerResultFail(jobId, logId, jobExecutorVoList.size());
		} else {
			if (conutTriggerFail > 0) {
				triggerResultPartSuccess(jobId, logId, jobExecutorVoList.size(), conutTriggerRun);
			} else {
				if (conutTriggerRun == jobExecutorVoList.size()) {					
					triggerResultSuccess(jobId, logId, jobExecutorVoList.size(), conutTriggerRun);
				} else {
					if (conutTriggerRun > 0) {
						triggerResultPartSuccess(jobId, logId, jobExecutorVoList.size(), conutTriggerRun);
					} else {
						triggerResultSkip(jobId, logId, jobExecutorVoList.size());
					}
				}
			}
		}
		
		return conutTriggerRun > 0;
	}
	
	private boolean triggerExecutorRusult(long logId, long executorId, int exeStatus, String exception) {
		try {
			coreService.insertTriggerExecutorAndUpdateLogException(logId, executorId, exeStatus, exception);
		} catch (Exception e) {
			logger.error("triggerExecutorRusult -> coreService.insertTriggerExecutorAndUpdateLogException -> logId: {}, executorId: {}, exeStatus: {}, exception: {} -> Exception: {}", logId, executorId, exeStatus, exception, e);
			return false; 
		}
		return true;
	}
	
	private boolean triggerResultSuccess(long jobId, long logId, int planNum, int runNum) {	
		try {
			coreService.updateFinishLogAndJobExeStatus(jobId, logId, Constant.LOG_EXE_STATUS_TRIGGER_SUCCESS, Constant.JOB_EXE_STATUS_RUNNING, planNum, runNum);
		} catch (Exception e) {
			logger.error("triggerResultSuccess -> coreService.updateFinishLogAndJobExeStatus -> jobId: {}, logId: {}, planNum: {}, runNum: {} -> Exception: {}", jobId, logId, planNum, runNum, e);
			return false;
		}
		return true;
	}
	
	private boolean triggerResultPartSuccess(long jobId, long logId, int planNum, int runNum) {
		try {
			coreService.updateFinishLogAndJobExeStatus(jobId, logId, Constant.LOG_EXE_STATUS_TRIGGER_PART_SUCCESS, Constant.JOB_EXE_STATUS_RUNNING, planNum, runNum);
		} catch (Exception e) {
			logger.error("triggerResultPartSuccess -> coreService.updateFinishLogAndJobExeStatus -> jobId: {}, logId: {}, planNum: {}, runNum: {} -> Exception: {}", jobId, logId, planNum, runNum, e);
			return false;
		}
		return true;
	}

	private boolean triggerResultFail(long jobId, long logId, int planNum) {
		try {
			coreService.updateFinishLogAndJobExeStatus(jobId, logId, Constant.LOG_EXE_STATUS_TRIGGER_FAIL, Constant.JOB_EXE_STATUS_WAITING, planNum, 0);
		} catch (Exception e) {
			logger.error("triggerResultFail -> coreService.updateFinishLogAndJobExeStatus -> jobId: {}, logId: {}, planNum: {}, runNum: {} -> Exception: {}", jobId, logId, planNum, e);
			return false;
		}
		return true;
	}
	
	private boolean triggerResultSkip(long jobId, long logId, int planNum) {
		try {
			coreService.updateFinishLogAndJobExeStatus(jobId, logId, Constant.LOG_EXE_STATUS_TRIGGER_SKIP, Constant.JOB_EXE_STATUS_WAITING, planNum, 0);
		} catch (Exception e) {
			logger.error("triggerResultFail -> coreService.updateFinishLogAndJobExeStatus -> jobId: {}, logId: {}, planNum: {}, runNum: {} -> Exception: {}", jobId, logId, planNum, e);
			return false;
		}
		return true;
	}
	
	private JobClient getJobClient(String rpcType, String ip, int port, String url) {
		JobClientFactory jobClientFactory = jobClientFactoryMap.get(rpcType);
		if (jobClientFactory != null) {			
			return jobClientFactory.createJobClient(ip, port, url);
		} else {
			logger.error("getJobClient -> jobClientFactoryMap.get -> no have this job client factory -> rpcType: {}, ip: {}, port: {}, url: {}", rpcType, ip, port, url);
			return null;
		}
	}
	
	private boolean checkRunningExecutor(List<LogExecutorVo> logExecutorVoList, String method) {
		
		int successCount = 0;
		
		for (LogExecutorVo logExecutorVo : logExecutorVoList) {
			JobClient jobClient = getJobClient(logExecutorVo.getRpc_Type(), logExecutorVo.getIp(), logExecutorVo.getPort(), logExecutorVo.getUrl());
			if (jobClient == null) {				
				logger.error("checkRunningExecutor -> getJobClient -> return null -> rpcType: {}, ip: {}, port: {}, url: {}", logExecutorVo.getRpc_Type(), logExecutorVo.getIp(), logExecutorVo.getPort(), logExecutorVo.getUrl());
				return true;
			}
			JobResult jobResult = jobClient.checkJobStatus(new JobParam(method));
			if (jobResult.getResult()) {
				if (jobResult.getCode() == Constant.CLIENT_EXE_STATUS_NORUN) {
					successCount++;
					logNoRunningExecutor(logExecutorVo.getLog_Id(), logExecutorVo.getExecutor_Id());
				}
			} else {
				successCount++;
				logExecutorNoResponse(logExecutorVo.getLog_Id(), logExecutorVo.getExecutor_Id());
			}
		}
		
		if (successCount == logExecutorVoList.size()) {
			return false;
		}
		
		return true;
	}
	
	private boolean logNoRunningExecutor(long logId, long executorId) {
		try {
			coreService.updateExecutorExeStatus(logId, executorId, Constant.EXECUTOR_EXE_STATUS_RUN_DISAPPEAR, Constant.EXCEPTION_RUN_DISAPPEAR);
		} catch (Exception e) {
			logger.error("logNoRunningExecutor -> coreService.updateExecutorExeStatus -> logId: {}, executorId: {} -> Exception: {}", logId, executorId, e);
			return false;
		}
		return true;
	}
	
	private boolean logExecutorNoResponse(long logId, long executorId) {
		try {
			coreService.updateExecutorExeStatus(logId, executorId, Constant.EXECUTOR_EXE_STATUS_NO_RESPONSE, Constant.EXCEPTION_EXECUTOR_NO_RESPONSE);
		} catch (Exception e) {
			logger.error("logExecutorNoResponse -> coreService.updateExecutorExeStatus -> logId: {}, executorId: {} -> Exception: {}", logId, executorId, e);
			return false;
		}
		return true;
	}

	private boolean stopAllJob(long jobId, long logId, String method, List<JobExecutorVo> jobExecutorVoList) {
		
		int conutTriggerFail = 0;

		for (JobExecutorVo jobExecutorVo : jobExecutorVoList) {
			JobClient jobClient = getJobClient(jobExecutorVo.getRpc_Type(), jobExecutorVo.getIp(), jobExecutorVo.getPort(), jobExecutorVo.getUrl());
			if (jobClient == null) {
				logger.error("triggerExecutorALL -> getJobClient -> return null -> rpcType: {}, ip: {}, port: {}, url: {}", jobExecutorVo.getRpc_Type(), jobExecutorVo.getIp(), jobExecutorVo.getPort(), jobExecutorVo.getUrl());
				continue;
			}
			JobResult jobResult = jobClient.stopJob(new JobParam(logId, jobExecutorVo.getExecutor_Id(), method));
			if (jobResult.getResult()) {
				if (jobResult.getCode() == Constant.CLIENT_EXE_STOP_FAIL) {
					conutTriggerFail++;
				} else if (jobResult.getCode() == Constant.CLIENT_EXE_STOP_SUCCESS) {
					stopExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_MANUAL_STOP, null);
				}
			} else {
				stopExecutorRusult(logId, jobExecutorVo.getExecutor_Id(), Constant.EXECUTOR_EXE_STATUS_TRIGGER_NET_FAIL, jobResult.getException());
			}
		}
		
		if (conutTriggerFail == 0) {
			stopLogRusult(jobId, logId, Constant.LOG_EXE_STATUS_MANUAL_STOP);
		}
		
		return conutTriggerFail == 0;
	}
	
	private boolean stopExecutorRusult(long logId, long executorId, int exeStatus, String exception) {
		try {
			coreService.updateExecutorExeStatus(logId, executorId, exeStatus, exception);
		} catch (Exception e) {
			logger.error("stopExecutorRusult -> coreService.updateExecutorExeStatus -> logId: {}, executorId: {}, exeStatus: {} -> Exception: {}", logId, executorId, exeStatus, e);
			return false; 
		}
		return true;
	}
	
	private boolean stopLogRusult(long jobId, long logId, int exeStatus) {
		try {
			coreService.updateLogAndJobExeStatus(jobId, logId, exeStatus, null);
		} catch (Exception e) {
			logger.error("stopLogRusult -> coreService.updateLogAndJobExeStatus -> jobId: {}, logId: {}, exeStatus: {} -> Exception: {}", jobId, logId, exeStatus, e);
			return false; 
		}
		return true;
	}
	
	private void sendAlarm(int alarmType, long jobId, long logId, long executorId, String exception) {
		sendLogAlarm.sendTriggerAlarm(jobId, logId, executorId, exception);
	}
	
	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}

	public void setJobClientFactoryMap(Map<String, JobClientFactory> jobClientFactoryMap) {
		this.jobClientFactoryMap = jobClientFactoryMap;
	}

	public void setTriggerRouteMap(Map<String, TriggerRoute> triggerRouteMap) {
		this.triggerRouteMap = triggerRouteMap;
	}

	public void setTriggerTimeout(int triggerTimeout) {
		this.triggerTimeout = triggerTimeout;
	}
	
	public void setSendLogAlarm(SendLogAlarm sendLogAlarm) {
		this.sendLogAlarm = sendLogAlarm;
	}
}
