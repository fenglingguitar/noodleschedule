package org.fl.noodleschedule.console.dao;

import java.util.List;

import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobExecutorVo;

public interface JobExecutorDao {
	
	public PageVo<JobExecutorVo> queryJobExecutorPage(JobExecutorVo vo, int page, int rows) throws Exception;

	public List<JobExecutorVo> queryJobExecutorList(JobExecutorVo vo) throws Exception;

	public void insertJobExecutor(JobExecutorVo vo) throws Exception;

	public void insertsJobExecutor(JobExecutorVo[] vos) throws Exception;

	public void updateJobExecutor(JobExecutorVo vo) throws Exception;

	public void updatesJobExecutor(JobExecutorVo[] vos) throws Exception;

	public void deleteJobExecutor(JobExecutorVo vo) throws Exception;

	public void deletesJobExecutor(JobExecutorVo[] vos) throws Exception;
	
	public void deletesJobExecutorByJob(JobExecutorVo vo) throws Exception;
	public void deletesJobExecutorByExecutor(JobExecutorVo vo) throws Exception;
	
	public PageVo<JobExecutorVo> queryJobExecutorIncludePage(JobExecutorVo vo, int page, int rows) throws Exception;
	public PageVo<JobExecutorVo> queryJobExecutorExcludePage(JobExecutorVo vo, int page, int rows) throws Exception;
	
	public List<JobExecutorVo> queryExecutorListByJob(JobExecutorVo vo) throws Exception;
}
