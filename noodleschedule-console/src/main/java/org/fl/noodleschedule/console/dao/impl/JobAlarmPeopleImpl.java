package org.fl.noodleschedule.console.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.fl.noodleschedule.console.dao.JobAlarmPeopleDao;
import org.fl.noodleschedule.console.domain.JobAlarmPeopleMd;
import org.fl.noodle.common.dynamicsql.DynamicSqlTemplate;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobAlarmPeopleVo;

@Repository("jobAlarmPeopleDao")
public class JobAlarmPeopleImpl implements JobAlarmPeopleDao {

	@Autowired
	private DynamicSqlTemplate dynamicSqlTemplate;

	@Override
	public PageVo<JobAlarmPeopleVo> queryJobAlarmPeoplePage(JobAlarmPeopleVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		return dynamicSqlTemplate.queryPage("jobalarmPeople-query-page", paramsMap, page, rows, JobAlarmPeopleVo.class);
	}

	@Override
	public List<JobAlarmPeopleVo> queryJobAlarmPeopleList(JobAlarmPeopleVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		return dynamicSqlTemplate.queryList("jobalarmPeople-query-list", paramsMap, JobAlarmPeopleVo.class);
	}

	@Override
	public void insertJobAlarmPeople(JobAlarmPeopleVo vo) throws Exception {
		dynamicSqlTemplate.insert(vo, JobAlarmPeopleMd.class);
	}

	@Override
	public void insertsJobAlarmPeople(JobAlarmPeopleVo[] vos) throws Exception {
		for (JobAlarmPeopleVo vo : vos) {
			dynamicSqlTemplate.insert(vo, JobAlarmPeopleMd.class);
		}
	}

	@Override
	public void updateJobAlarmPeople(JobAlarmPeopleVo vo) throws Exception {
		dynamicSqlTemplate.updateNonull(vo, JobAlarmPeopleMd.class);
	}

	@Override
	public void updatesJobAlarmPeople(JobAlarmPeopleVo[] vos) throws Exception {
		for (JobAlarmPeopleVo vo : vos) {
			dynamicSqlTemplate.updateNonull(vo, JobAlarmPeopleMd.class);
		}
	}

	@Override
	public void deleteJobAlarmPeople(JobAlarmPeopleVo vo) throws Exception {
		dynamicSqlTemplate.delete(vo, JobAlarmPeopleMd.class);
	}

	@Override
	public void deletesJobAlarmPeople(JobAlarmPeopleVo[] vos) throws Exception {
		for (JobAlarmPeopleVo vo : vos) {
			dynamicSqlTemplate.delete(vo, JobAlarmPeopleMd.class);
		}
	}
	
	@Override
	public void deletesJobAlarmPeopleByJob(JobAlarmPeopleVo vo) throws Exception {
		dynamicSqlTemplate.deleteNoById(vo, JobAlarmPeopleMd.class, new String[]{"job_Id"});
	}
	
	@Override
	public void deletesJobAlarmPeopleByAlarmPeople(JobAlarmPeopleVo vo) throws Exception {
		dynamicSqlTemplate.deleteNoById(vo, JobAlarmPeopleMd.class, new String[]{"alarmPeople_Id"});
	}
	
	@Override
	public PageVo<JobAlarmPeopleVo> queryJobAlarmPeopleIncludePage(JobAlarmPeopleVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id());
		paramsMap.put("alarmPeople_Id", vo.getAlarmPeople_Id() != null ? vo.getAlarmPeople_Id() : null);
		paramsMap.put("alarmPeople_Name", vo.getAlarmPeople_Name() != null ? (new StringBuilder().append("%").append(vo.getAlarmPeople_Name()).append("%")).toString() : null);
		paramsMap.put("email", vo.getEmail() != null ? (new StringBuilder().append("%").append(vo.getEmail()).append("%")).toString() : null);
		paramsMap.put("phone", vo.getPhone() != null ? (new StringBuilder().append("%").append(vo.getPhone()).append("%")).toString() : null);
		paramsMap.put("status", vo.getStatus() != null ? vo.getStatus() : null);
		return dynamicSqlTemplate.queryPage("jobalarmpeople-query-include-page", paramsMap, page, rows, JobAlarmPeopleVo.class);
	}

	@Override
	public PageVo<JobAlarmPeopleVo> queryJobAlarmPeopleExcludePage(JobAlarmPeopleVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id());
		paramsMap.put("alarmPeople_Id", vo.getAlarmPeople_Id() != null ? vo.getAlarmPeople_Id() : null);
		paramsMap.put("alarmPeople_Name", vo.getAlarmPeople_Name() != null ? (new StringBuilder().append("%").append(vo.getAlarmPeople_Name()).append("%")).toString() : null);
		paramsMap.put("email", vo.getEmail() != null ? (new StringBuilder().append("%").append(vo.getEmail()).append("%")).toString() : null);
		paramsMap.put("phone", vo.getPhone() != null ? (new StringBuilder().append("%").append(vo.getPhone()).append("%")).toString() : null);
		paramsMap.put("status", vo.getStatus() != null ? vo.getStatus() : null);
		return dynamicSqlTemplate.queryPage("jobalarmpeople-query-exclude-page", paramsMap, page, rows, JobAlarmPeopleVo.class);
	}

	@Override
	public List<JobAlarmPeopleVo> queryJobAlarmPeopleListByJob(JobAlarmPeopleVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id());
		return dynamicSqlTemplate.queryList("jobalarmpeople-query-list-by-job", paramsMap, JobAlarmPeopleVo.class);
	}
}
