package org.fl.noodleschedule.console.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.fl.noodleschedule.console.dao.ExecutorDao;
import org.fl.noodleschedule.console.domain.ExecutorMd;
import org.fl.noodle.common.dynamicsql.DynamicSqlTemplate;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.ExecutorVo;

@Repository("executorDao")
public class ExecutorDaoImpl implements ExecutorDao {

	@Autowired
	private DynamicSqlTemplate dynamicSqlTemplate;

	@Override
	public PageVo<ExecutorVo> queryExecutorPage(ExecutorVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("executor_Id", vo.getExecutor_Id() != null ? vo.getExecutor_Id() : null);
		paramsMap.put("executor_Name", vo.getExecutor_Name() != null ? (new StringBuilder().append("%").append(vo.getExecutor_Name()).append("%")).toString() : null);
		paramsMap.put("rpc_Type", vo.getRpc_Type() != null ? vo.getRpc_Type() : null);
		paramsMap.put("ip", vo.getIp() != null ? (new StringBuilder().append("%").append(vo.getIp()).append("%")).toString() : null);
		paramsMap.put("port", vo.getPort() != null ? vo.getPort() : null);
		paramsMap.put("url", vo.getUrl() != null ? (new StringBuilder().append("%").append(vo.getUrl()).append("%")).toString() : null);
		paramsMap.put("status", vo.getStatus() != null ? vo.getStatus() : null);
		return dynamicSqlTemplate.queryPage("executor-query-page", paramsMap, page, rows, ExecutorVo.class);
	}

	@Override
	public List<ExecutorVo> queryExecutorList(ExecutorVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("executor_Id", vo.getExecutor_Id() != null ? vo.getExecutor_Id() : null);
		paramsMap.put("executor_Name", vo.getExecutor_Name() != null ? vo.getExecutor_Name() : null);
		paramsMap.put("rpc_Type", vo.getRpc_Type() != null ? vo.getRpc_Type() : null);
		paramsMap.put("ip", vo.getIp() != null ? vo.getIp() : null);
		paramsMap.put("port", vo.getPort() != null ? vo.getPort() : null);
		paramsMap.put("url", vo.getUrl() != null ? vo.getUrl() : null);
		paramsMap.put("status", vo.getStatus() != null ? vo.getStatus() : null);
		return dynamicSqlTemplate.queryList("executor-query-list", paramsMap, ExecutorVo.class);
	}

	@Override
	public void insertExecutor(ExecutorVo vo) throws Exception {
		dynamicSqlTemplate.insert(vo, ExecutorMd.class);
	}

	@Override
	public void insertsExecutor(ExecutorVo[] vos) throws Exception {
		for (ExecutorVo vo : vos) {
			dynamicSqlTemplate.insert(vo, ExecutorMd.class);
		}
	}

	@Override
	public void updateExecutor(ExecutorVo vo) throws Exception {
		dynamicSqlTemplate.updateNonull(vo, ExecutorMd.class);
	}

	@Override
	public void updatesExecutor(ExecutorVo[] vos) throws Exception {
		for (ExecutorVo vo : vos) {
			dynamicSqlTemplate.updateNonull(vo, ExecutorMd.class);
		}
	}

	@Override
	public void deleteExecutor(ExecutorVo vo) throws Exception {
		dynamicSqlTemplate.delete(vo, ExecutorMd.class);
	}

	@Override
	public void deletesExecutor(ExecutorVo[] vos) throws Exception {
		for (ExecutorVo vo : vos) {
			dynamicSqlTemplate.delete(vo, ExecutorMd.class);
		}
	}
}
