package org.fl.noodleschedule.console.service;

import java.util.List;

import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.ExecutorVo;

public interface ExecutorService {
	
	public PageVo<ExecutorVo> queryExecutorPage(ExecutorVo vo, int page, int rows) throws Exception;

	public List<ExecutorVo> queryExecutorList(ExecutorVo vo) throws Exception;

	public void insertExecutor(ExecutorVo vo) throws Exception;

	public void insertsExecutor(ExecutorVo[] vos) throws Exception;

	public void updateExecutor(ExecutorVo vo) throws Exception;

	public void updatesExecutor(ExecutorVo[] vos) throws Exception;

	public void deleteExecutor(ExecutorVo vo) throws Exception;

	public void deletesExecutor(ExecutorVo[] vos) throws Exception;
}
