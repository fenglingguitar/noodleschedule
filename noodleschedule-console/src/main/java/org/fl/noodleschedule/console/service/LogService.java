package org.fl.noodleschedule.console.service;

import java.util.List;

import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobLogVo;
import org.fl.noodleschedule.console.vo.LogVo;

public interface LogService {
	
	public PageVo<LogVo> queryLogPage(LogVo vo, int page, int rows) throws Exception;

	public List<LogVo> queryLogList(LogVo vo) throws Exception;

	public void insertLog(LogVo vo) throws Exception;

	public void insertsLog(LogVo[] vos) throws Exception;

	public void updateLog(LogVo vo) throws Exception;

	public void updatesLog(LogVo[] vos) throws Exception;

	public void deleteLog(LogVo vo) throws Exception;

	public void deletesLog(LogVo[] vos) throws Exception;
	
	public void deleteLogByTime(LogVo vo) throws Exception;
	
	
	public PageVo<JobLogVo> queryJobLogPage(JobLogVo vo, int page, int rows) throws Exception;
	
	
	public Long insertLogReturnId(LogVo vo) throws Exception;
	
	public int updateLogCumulativeComplete(LogVo vo) throws Exception;
	
	public int queryLogCount(LogVo vo) throws Exception;
}
