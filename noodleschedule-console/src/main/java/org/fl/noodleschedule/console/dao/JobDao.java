package org.fl.noodleschedule.console.dao;

import java.util.List;

import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobVo;

public interface JobDao {
	
	public PageVo<JobVo> queryJobPage(JobVo vo, int page, int rows) throws Exception;

	public List<JobVo> queryJobList(JobVo vo) throws Exception;

	public void insertJob(JobVo vo) throws Exception;

	public void insertsJob(JobVo[] vos) throws Exception;

	public void updateJob(JobVo vo) throws Exception;
	
	public void updateJobIncludeNull(JobVo vo) throws Exception;

	public void updatesJob(JobVo[] vos) throws Exception;

	public void deleteJob(JobVo vo) throws Exception;

	public void deletesJob(JobVo[] vos) throws Exception;
	
	public void deleteChildJob(JobVo vo) throws Exception;
	
	
	public Integer updateJobExeStatus(JobVo vo) throws Exception;
	
	public Integer updateJobExeStatusByLog(JobVo vo) throws Exception;
}
