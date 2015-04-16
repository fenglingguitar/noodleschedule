package org.fl.noodleschedule.console.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.fl.noodleschedule.console.dao.JobAlarmPeopleDao;
import org.fl.noodleschedule.console.service.JobAlarmPeopleService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobAlarmPeopleVo;

@Service("jobAlarmPeopleService")
public class JobAlarmPeopleServiceImpl implements JobAlarmPeopleService {

	@Autowired
	private JobAlarmPeopleDao jobAlarmPeopleDao;

	@Override
	public PageVo<JobAlarmPeopleVo> queryJobAlarmPeoplePage(JobAlarmPeopleVo vo, int page, int rows) throws Exception {
		return jobAlarmPeopleDao.queryJobAlarmPeoplePage(vo, page, rows);
	}

	@Override
	public List<JobAlarmPeopleVo> queryJobAlarmPeopleList(JobAlarmPeopleVo vo) throws Exception {
		return jobAlarmPeopleDao.queryJobAlarmPeopleList(vo);
	}

	@Override
	public void insertJobAlarmPeople(JobAlarmPeopleVo vo) throws Exception {
		jobAlarmPeopleDao.insertJobAlarmPeople(vo);
	}

	@Override
	public void insertsJobAlarmPeople(JobAlarmPeopleVo[] vos) throws Exception {
		jobAlarmPeopleDao.insertsJobAlarmPeople(vos);
	}

	@Override
	public void updateJobAlarmPeople(JobAlarmPeopleVo vo) throws Exception {
		jobAlarmPeopleDao.updateJobAlarmPeople(vo);
	}

	@Override
	public void updatesJobAlarmPeople(JobAlarmPeopleVo[] vos) throws Exception {
		jobAlarmPeopleDao.updatesJobAlarmPeople(vos);
	}

	@Override
	public void deleteJobAlarmPeople(JobAlarmPeopleVo vo) throws Exception {
		jobAlarmPeopleDao.deleteJobAlarmPeople(vo);
	}

	@Override
	public void deletesJobAlarmPeople(JobAlarmPeopleVo[] vos) throws Exception {
		for (JobAlarmPeopleVo vo : vos) {
			jobAlarmPeopleDao.deleteJobAlarmPeople(vo);
		}
	}


	@Override
	public PageVo<JobAlarmPeopleVo> queryJobAlarmPeopleIncludePage(JobAlarmPeopleVo vo, int page, int rows) throws Exception {
		return jobAlarmPeopleDao.queryJobAlarmPeopleIncludePage(vo, page, rows);
	}

	@Override
	public PageVo<JobAlarmPeopleVo> queryJobAlarmPeopleExcludePage(JobAlarmPeopleVo vo, int page, int rows) throws Exception {
		return jobAlarmPeopleDao.queryJobAlarmPeopleExcludePage(vo, page, rows);
	}
	
	@Override
	public List<JobAlarmPeopleVo> queryJobAlarmPeopleListByJob(JobAlarmPeopleVo vo) throws Exception {
		return jobAlarmPeopleDao.queryJobAlarmPeopleListByJob(vo);
	}
}
