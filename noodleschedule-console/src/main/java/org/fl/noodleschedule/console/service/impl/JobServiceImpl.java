package org.fl.noodleschedule.console.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.fl.noodleschedule.console.dao.JobAlarmPeopleDao;
import org.fl.noodleschedule.console.dao.JobDao;
import org.fl.noodleschedule.console.dao.JobExecutorDao;
import org.fl.noodleschedule.console.service.JobService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobAlarmPeopleVo;
import org.fl.noodleschedule.console.vo.JobExecutorVo;
import org.fl.noodleschedule.console.vo.JobVo;

@Service("jobService")
public class JobServiceImpl implements JobService {

	@Autowired
	private JobDao jobDao;
	
	@Autowired
	private JobExecutorDao jobExecutorDao;
	
	@Autowired
	private JobAlarmPeopleDao jobAlarmPeopleDao;

	@Override
	public PageVo<JobVo> queryJobPage(JobVo vo, int page, int rows) throws Exception {
		return jobDao.queryJobPage(vo, page, rows);
	}

	@Override
	public List<JobVo> queryJobList(JobVo vo) throws Exception {
		return jobDao.queryJobList(vo);
	}

	@Override
	public void insertJob(JobVo vo) throws Exception {
		jobDao.insertJob(vo);
	}

	@Override
	public void insertsJob(JobVo[] vos) throws Exception {
		jobDao.insertsJob(vos);
	}

	@Override
	public void updateJob(JobVo vo) throws Exception {
		jobDao.updateJob(vo);
	}
	
	@Override
	public void updateJobIncludeNull(JobVo vo) throws Exception {
		jobDao.updateJobIncludeNull(vo);
	}

	@Override
	public void updatesJob(JobVo[] vos) throws Exception {
		jobDao.updatesJob(vos);
	}

	@Override
	public void deleteJob(JobVo vo) throws Exception {
		
		JobExecutorVo jobExecutorVo = new JobExecutorVo();
		jobExecutorVo.setJob_Id(vo.getJob_Id());
		jobExecutorDao.deletesJobExecutorByJob(jobExecutorVo);
		JobAlarmPeopleVo jobAlarmPeopleVo = new JobAlarmPeopleVo();
		jobAlarmPeopleVo.setJob_Id(vo.getJob_Id());
		jobAlarmPeopleDao.deletesJobAlarmPeopleByJob(jobAlarmPeopleVo);
		//jobDao.deleteChildJob(vo);
		
		jobDao.deleteJob(vo);
	}

	@Override
	public void deletesJob(JobVo[] vos) throws Exception {
		for (JobVo vo : vos) {
			
			JobExecutorVo jobExecutorVo = new JobExecutorVo();
			jobExecutorVo.setJob_Id(vo.getJob_Id());
			jobExecutorDao.deletesJobExecutorByJob(jobExecutorVo);
			JobAlarmPeopleVo jobAlarmPeopleVo = new JobAlarmPeopleVo();
			jobAlarmPeopleVo.setJob_Id(vo.getJob_Id());
			jobAlarmPeopleDao.deletesJobAlarmPeopleByJob(jobAlarmPeopleVo);
			//jobDao.deleteChildJob(vo);
			
			jobDao.deleteJob(vo);
		}
	}

	@Override
	public Integer updateJobExeStatus(JobVo vo) throws Exception {
		return jobDao.updateJobExeStatus(vo);
	}

	@Override
	public Integer updateJobExeStatusByLog(JobVo vo) throws Exception {
		return jobDao.updateJobExeStatusByLog(vo);
	}
}
