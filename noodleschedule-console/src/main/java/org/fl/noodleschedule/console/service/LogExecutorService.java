package org.fl.noodleschedule.console.service;

import java.util.List;

import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.LogExecutorVo;

public interface LogExecutorService {
	
	public PageVo<LogExecutorVo> queryLogExecutorPage(LogExecutorVo vo, int page, int rows) throws Exception;

	public List<LogExecutorVo> queryLogExecutorList(LogExecutorVo vo) throws Exception;

	public void insertLogExecutor(LogExecutorVo vo) throws Exception;

	public void insertsLogExecutor(LogExecutorVo[] vos) throws Exception;

	public void updateLogExecutor(LogExecutorVo vo) throws Exception;

	public void updatesLogExecutor(LogExecutorVo[] vos) throws Exception;

	public void deleteLogExecutor(LogExecutorVo vo) throws Exception;

	public void deletesLogExecutor(LogExecutorVo[] vos) throws Exception;
	
	public void deletesLogExecutorByTime(LogExecutorVo vo) throws Exception;
	
	
	public PageVo<LogExecutorVo> queryExecutorByLogPage(LogExecutorVo vo, int page, int rows) throws Exception;
	
	
	public List<LogExecutorVo> queryLogExecutorListByLog(LogExecutorVo vo) throws Exception;
	
	public List<LogExecutorVo> queryCompleteExecutorList(LogExecutorVo vo) throws Exception;
	
	public long updateLogExecutorAndStatisticalTime(LogExecutorVo vo) throws Exception;
}
