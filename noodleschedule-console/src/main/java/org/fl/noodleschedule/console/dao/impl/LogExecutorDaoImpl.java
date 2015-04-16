package org.fl.noodleschedule.console.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.fl.noodleschedule.console.dao.LogExecutorDao;
import org.fl.noodleschedule.console.domain.LogExecutorMd;
import org.fl.noodle.common.dynamicsql.DynamicSqlTemplate;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.LogExecutorVo;
import org.fl.noodleschedule.util.common.Constant;

@Repository("logExecutorDao")
public class LogExecutorDaoImpl implements LogExecutorDao {

	@Autowired
	private DynamicSqlTemplate dynamicSqlTemplate;

	@Override
	public PageVo<LogExecutorVo> queryLogExecutorPage(LogExecutorVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		return dynamicSqlTemplate.queryPage("logexecutor-query-page", paramsMap, page, rows, LogExecutorVo.class);
	}

	@Override
	public List<LogExecutorVo> queryLogExecutorList(LogExecutorVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		return dynamicSqlTemplate.queryList("logexecutor-query-list", paramsMap, LogExecutorVo.class);
	}

	@Override
	public void insertLogExecutor(LogExecutorVo vo) throws Exception {
		dynamicSqlTemplate.insert(vo, LogExecutorMd.class);
	}

	@Override
	public void insertsLogExecutor(LogExecutorVo[] vos) throws Exception {
		for (LogExecutorVo vo : vos) {
			dynamicSqlTemplate.insert(vo, LogExecutorMd.class);
		}
	}

	@Override
	public void updateLogExecutor(LogExecutorVo vo) throws Exception {
		dynamicSqlTemplate.updateNonull(vo, LogExecutorMd.class);
	}

	@Override
	public void updatesLogExecutor(LogExecutorVo[] vos) throws Exception {
		for (LogExecutorVo vo : vos) {
			dynamicSqlTemplate.updateNonull(vo, LogExecutorMd.class);
		}
	}

	@Override
	public void deleteLogExecutor(LogExecutorVo vo) throws Exception {
		dynamicSqlTemplate.delete(vo, LogExecutorMd.class);
	}

	@Override
	public void deletesLogExecutor(LogExecutorVo[] vos) throws Exception {
		for (LogExecutorVo vo : vos) {
			dynamicSqlTemplate.delete(vo, LogExecutorMd.class);
		}
	}

	@Override
	public void deletesLogExecutorByTime(LogExecutorVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("start_Time", vo.getStart_Time());
		dynamicSqlTemplate.updateSql("logexecutor-delete-by-time", paramsMap);
	}
	
	@Override
	public PageVo<LogExecutorVo> queryExecutorByLogPage(LogExecutorVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("log_Id", vo.getLog_Id() != null ? vo.getLog_Id() : null);
		return dynamicSqlTemplate.queryPage("logexecutor-query-executor-by-log-page", paramsMap, page, rows, LogExecutorVo.class);
	}
	
	@Override
	public List<LogExecutorVo> queryLogExecutorListByLog(LogExecutorVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("log_Id", vo.getLog_Id());
		paramsMap.put("exe_Status", vo.getExe_Status());
		return dynamicSqlTemplate.queryList("logexecutor-query-list-by-log", paramsMap, LogExecutorVo.class);
	}

	@Override
	public List<LogExecutorVo> queryCompleteExecutorList(LogExecutorVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("log_Id", vo.getLog_Id());
		paramsMap.put("exe_Status_Run_Success", Constant.EXECUTOR_EXE_STATUS_RUN_SUCCESS);
		paramsMap.put("exe_Status_Run_Fail", Constant.EXECUTOR_EXE_STATUS_RUN_FAIL);
		paramsMap.put("exe_Status_Run_Disappear", Constant.EXECUTOR_EXE_STATUS_RUN_DISAPPEAR);
		paramsMap.put("exe_Status_No_Response", Constant.EXECUTOR_EXE_STATUS_NO_RESPONSE);
		paramsMap.put("exe_Status_Manual_Stop", Constant.EXECUTOR_EXE_STATUS_MANUAL_STOP);
		return dynamicSqlTemplate.queryList("logexecutor-query-list-complete", paramsMap, LogExecutorVo.class);
	}

	@Override
	public long updateLogExecutorAndStatisticalTime(LogExecutorVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("log_Id", vo.getLog_Id());
		paramsMap.put("executor_Id", vo.getExecutor_Id());
		paramsMap.put("end_Time", vo.getEnd_Time());
		paramsMap.put("exe_Status", vo.getExe_Status());
		paramsMap.put("exception", vo.getException());
		return dynamicSqlTemplate.updateSql("logexecutor-update-statistical-time", paramsMap);
	}
}
