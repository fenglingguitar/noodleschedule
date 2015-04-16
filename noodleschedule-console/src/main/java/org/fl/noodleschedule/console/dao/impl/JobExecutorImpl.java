package org.fl.noodleschedule.console.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.fl.noodleschedule.console.dao.JobExecutorDao;
import org.fl.noodleschedule.console.domain.JobExecutorMd;
import org.fl.noodle.common.dynamicsql.DynamicSqlTemplate;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobExecutorVo;

@Repository("jobExecutorDao")
public class JobExecutorImpl implements JobExecutorDao {

	@Autowired
	private DynamicSqlTemplate dynamicSqlTemplate;

	@Override
	public PageVo<JobExecutorVo> queryJobExecutorPage(JobExecutorVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		return dynamicSqlTemplate.queryPage("jobexecutor-query-page", paramsMap, page, rows, JobExecutorVo.class);
	}

	@Override
	public List<JobExecutorVo> queryJobExecutorList(JobExecutorVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		return dynamicSqlTemplate.queryList("jobexecutor-query-list", paramsMap, JobExecutorVo.class);
	}

	@Override
	public void insertJobExecutor(JobExecutorVo vo) throws Exception {
		dynamicSqlTemplate.insert(vo, JobExecutorMd.class);
	}

	@Override
	public void insertsJobExecutor(JobExecutorVo[] vos) throws Exception {
		for (JobExecutorVo vo : vos) {
			dynamicSqlTemplate.insert(vo, JobExecutorMd.class);
		}
	}

	@Override
	public void updateJobExecutor(JobExecutorVo vo) throws Exception {
		dynamicSqlTemplate.updateNonull(vo, JobExecutorMd.class);
	}

	@Override
	public void updatesJobExecutor(JobExecutorVo[] vos) throws Exception {
		for (JobExecutorVo vo : vos) {
			dynamicSqlTemplate.updateNonull(vo, JobExecutorMd.class);
		}
	}

	@Override
	public void deleteJobExecutor(JobExecutorVo vo) throws Exception {
		dynamicSqlTemplate.delete(vo, JobExecutorMd.class);
	}

	@Override
	public void deletesJobExecutor(JobExecutorVo[] vos) throws Exception {
		for (JobExecutorVo vo : vos) {
			dynamicSqlTemplate.delete(vo, JobExecutorMd.class);
		}
	}

	@Override
	public void deletesJobExecutorByJob(JobExecutorVo vo) throws Exception {
		dynamicSqlTemplate.deleteNoById(vo, JobExecutorMd.class, new String[]{"job_Id"});
	}
	
	@Override
	public void deletesJobExecutorByExecutor(JobExecutorVo vo) throws Exception {
		dynamicSqlTemplate.deleteNoById(vo, JobExecutorMd.class, new String[]{"executor_Id"});
	}
	
	@Override
	public PageVo<JobExecutorVo> queryJobExecutorIncludePage(JobExecutorVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id());
		paramsMap.put("executor_Id", vo.getExecutor_Id() != null ? vo.getExecutor_Id() : null);
		paramsMap.put("executor_Name", vo.getExecutor_Name() != null ? (new StringBuilder().append("%").append(vo.getExecutor_Name()).append("%")).toString() : null);
		paramsMap.put("rpc_Type", vo.getRpc_Type() != null ? vo.getRpc_Type() : null);
		paramsMap.put("ip", vo.getIp() != null ? (new StringBuilder().append("%").append(vo.getIp()).append("%")).toString() : null);
		paramsMap.put("port", vo.getPort() != null ? vo.getPort() : null);
		paramsMap.put("url", vo.getUrl() != null ? (new StringBuilder().append("%").append(vo.getUrl()).append("%")).toString() : null);
		paramsMap.put("status", vo.getStatus() != null ? vo.getStatus() : null);
		return dynamicSqlTemplate.queryPage("jobexecutor-query-include-page", paramsMap, page, rows, JobExecutorVo.class);
	}

	@Override
	public PageVo<JobExecutorVo> queryJobExecutorExcludePage(JobExecutorVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id());
		paramsMap.put("executor_Id", vo.getExecutor_Id() != null ? vo.getExecutor_Id() : null);
		paramsMap.put("executor_Name", vo.getExecutor_Name() != null ? (new StringBuilder().append("%").append(vo.getExecutor_Name()).append("%")).toString() : null);
		paramsMap.put("rpc_Type", vo.getRpc_Type() != null ? vo.getRpc_Type() : null);
		paramsMap.put("ip", vo.getIp() != null ? (new StringBuilder().append("%").append(vo.getIp()).append("%")).toString() : null);
		paramsMap.put("port", vo.getPort() != null ? vo.getPort() : null);
		paramsMap.put("url", vo.getUrl() != null ? (new StringBuilder().append("%").append(vo.getUrl()).append("%")).toString() : null);
		paramsMap.put("status", vo.getStatus() != null ? vo.getStatus() : null);
		return dynamicSqlTemplate.queryPage("jobexecutor-query-exclude-page", paramsMap, page, rows, JobExecutorVo.class);
	}
	
	@Override
	public List<JobExecutorVo> queryExecutorListByJob(JobExecutorVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id());
		paramsMap.put("status", vo.getStatus());
		return dynamicSqlTemplate.queryList("jobexecutor-query-executor-list-by-job", paramsMap, JobExecutorVo.class);
	}
}
