package org.fl.noodleschedule.console.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.fl.noodleschedule.console.dao.LogDao;
import org.fl.noodleschedule.console.domain.LogMd;
import org.fl.noodle.common.dynamicsql.DynamicSqlTemplate;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobLogVo;
import org.fl.noodleschedule.console.vo.LogVo;
import org.fl.noodleschedule.util.common.Constant;

@Repository("logDao")
public class LogDaoImpl implements LogDao {

	@Autowired
	private DynamicSqlTemplate dynamicSqlTemplate;

	@Override
	public PageVo<LogVo> queryLogPage(LogVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("log_Id", vo.getLog_Id() != null ? vo.getLog_Id() : null);
		paramsMap.put("exe_Status", vo.getExe_Status() != null ? vo.getExe_Status() : null);
		paramsMap.put("exe_Type", vo.getExe_Type() != null ? vo.getExe_Type() : null);
		paramsMap.put("timeout_Status", vo.getTimeout_Status() != null ? vo.getTimeout_Status() : null);
		paramsMap.put("job_Id", vo.getJob_Id() != null ? vo.getJob_Id() : null);
		paramsMap.put("trigger_Time_Start", vo.getTrigger_Time_Start() != null ? vo.getTrigger_Time_Start() : null);
		paramsMap.put("trigger_Time_End", vo.getTrigger_Time_End() != null ? vo.getTrigger_Time_End() : null);
		paramsMap.put("parent_Log_Id", vo.getParent_Log_Id() != null ? vo.getParent_Log_Id() : null);
		return dynamicSqlTemplate.queryPage("log-query-page", paramsMap, page, rows, LogVo.class);
	}

	@Override
	public List<LogVo> queryLogList(LogVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("log_Id", vo.getLog_Id() != null ? vo.getLog_Id() : null);
		paramsMap.put("exe_Status", vo.getExe_Status() != null ? vo.getExe_Status() : null);
		paramsMap.put("exe_Type", vo.getExe_Type() != null ? vo.getExe_Type() : null);
		paramsMap.put("timeout_Status", vo.getTimeout_Status() != null ? vo.getTimeout_Status() : null);
		paramsMap.put("job_Id", vo.getJob_Id() != null ? vo.getJob_Id() : null);
		paramsMap.put("trigger_Time_Start", vo.getTrigger_Time_Start() != null ? vo.getTrigger_Time_Start() : null);
		paramsMap.put("trigger_Time_End", vo.getTrigger_Time_End() != null ? vo.getTrigger_Time_End() : null);
		paramsMap.put("parent_Log_Id", vo.getParent_Log_Id() != null ? vo.getParent_Log_Id() : null);
		return dynamicSqlTemplate.queryList("log-query-list", paramsMap, LogVo.class);
	}

	@Override
	public void insertLog(LogVo vo) throws Exception {
		dynamicSqlTemplate.insert(vo, LogMd.class);
	}

	@Override
	public void insertsLog(LogVo[] vos) throws Exception {
		for (LogVo vo : vos) {
			dynamicSqlTemplate.insert(vo, LogMd.class);
		}
	}

	@Override
	public void updateLog(LogVo vo) throws Exception {
		dynamicSqlTemplate.updateNonull(vo, LogMd.class);
	}

	@Override
	public void updatesLog(LogVo[] vos) throws Exception {
		for (LogVo vo : vos) {
			dynamicSqlTemplate.updateNonull(vo, LogMd.class);
		}
	}

	@Override
	public void deleteLog(LogVo vo) throws Exception {
		dynamicSqlTemplate.delete(vo, LogMd.class);
	}

	@Override
	public void deletesLog(LogVo[] vos) throws Exception {
		for (LogVo vo : vos) {
			dynamicSqlTemplate.delete(vo, LogMd.class);
		}
	}

	@Override
	public void deleteLogByTime(LogVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("start_Time", vo.getStart_Time());
		dynamicSqlTemplate.updateSql("log-delete-by-time", paramsMap);
	}

	@Override
	public PageVo<JobLogVo> queryJobLogPage(JobLogVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id() != null ? vo.getJob_Id() : null);
		paramsMap.put("job_Name", vo.getJob_Name() != null ? (new StringBuilder().append("%").append(vo.getJob_Name()).append("%")).toString() : null);
		paramsMap.put("exe_Status_Job", vo.getExe_Status_Job() != null ? vo.getExe_Status_Job() : null);
		paramsMap.put("exe_Status_Log", vo.getExe_Status_Log() != null ? vo.getExe_Status_Log() : null);
		return dynamicSqlTemplate.queryPage("log-query-job-log-page", paramsMap, page, rows, JobLogVo.class);
	}
	
	@Override
	public Long insertLogReturnId(LogVo vo) throws Exception {
		LogMd logMd = dynamicSqlTemplate.insert(vo, LogMd.class);
		dynamicSqlTemplate.flush();
		return logMd.getLog_Id();
	}

	@Override
	public int updateLogCumulativeComplete(LogVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("log_Id", vo.getLog_Id());
		int result = dynamicSqlTemplate.updateSql("log-update-cumulativ-complete", paramsMap);
		dynamicSqlTemplate.flush();
		return result;
	}

	@Override
	public int queryLogCount(LogVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id());
		paramsMap.put("exe_Status_Success", Constant.LOG_EXE_STATUS_RUN_SUCCESS);
		paramsMap.put("exe_Status_Part_Success", Constant.LOG_EXE_STATUS_RUN_PART_SUCCESS);
		paramsMap.put("trigger_Time_Start", vo.getTrigger_Time_Start());
		paramsMap.put("trigger_Time_End", vo.getTrigger_Time_End());
		List<CountTemp> list = dynamicSqlTemplate.queryList("log-query-list-count", paramsMap, CountTemp.class);
		if (list != null && list.size() > 0) {
			return list.get(0).num;
		}
		return 0;
	}
	
	public static class CountTemp {
		public int num;
	}
}
