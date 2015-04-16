package org.fl.noodleschedule.console.dao;

import java.util.List;

import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobAlarmPeopleVo;

public interface JobAlarmPeopleDao {
	
	public PageVo<JobAlarmPeopleVo> queryJobAlarmPeoplePage(JobAlarmPeopleVo vo, int page, int rows) throws Exception;

	public List<JobAlarmPeopleVo> queryJobAlarmPeopleList(JobAlarmPeopleVo vo) throws Exception;

	public void insertJobAlarmPeople(JobAlarmPeopleVo vo) throws Exception;

	public void insertsJobAlarmPeople(JobAlarmPeopleVo[] vos) throws Exception;

	public void updateJobAlarmPeople(JobAlarmPeopleVo vo) throws Exception;

	public void updatesJobAlarmPeople(JobAlarmPeopleVo[] vos) throws Exception;

	public void deleteJobAlarmPeople(JobAlarmPeopleVo vo) throws Exception;

	public void deletesJobAlarmPeople(JobAlarmPeopleVo[] vos) throws Exception;
	
	public void deletesJobAlarmPeopleByJob(JobAlarmPeopleVo vo) throws Exception;
	public void deletesJobAlarmPeopleByAlarmPeople(JobAlarmPeopleVo vo) throws Exception;
	
	
	public PageVo<JobAlarmPeopleVo> queryJobAlarmPeopleIncludePage(JobAlarmPeopleVo vo, int page, int rows) throws Exception;
	public PageVo<JobAlarmPeopleVo> queryJobAlarmPeopleExcludePage(JobAlarmPeopleVo vo, int page, int rows) throws Exception;
	
	
	public List<JobAlarmPeopleVo> queryJobAlarmPeopleListByJob(JobAlarmPeopleVo vo) throws Exception;
}
