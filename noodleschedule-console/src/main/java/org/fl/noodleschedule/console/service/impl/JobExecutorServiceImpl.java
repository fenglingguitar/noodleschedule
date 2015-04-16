package org.fl.noodleschedule.console.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.fl.noodleschedule.console.dao.JobExecutorDao;
import org.fl.noodleschedule.console.service.JobExecutorService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobExecutorVo;

@Service("jobExecutorService")
public class JobExecutorServiceImpl implements JobExecutorService {

	@Autowired
	private JobExecutorDao jobExecutorDao;

	@Override
	public PageVo<JobExecutorVo> queryJobExecutorPage(JobExecutorVo vo, int page, int rows) throws Exception {
		return jobExecutorDao.queryJobExecutorPage(vo, page, rows);
	}

	@Override
	public List<JobExecutorVo> queryJobExecutorList(JobExecutorVo vo) throws Exception {
		return jobExecutorDao.queryJobExecutorList(vo);
	}

	@Override
	public void insertJobExecutor(JobExecutorVo vo) throws Exception {
		jobExecutorDao.insertJobExecutor(vo);
	}

	@Override
	public void insertsJobExecutor(JobExecutorVo[] vos) throws Exception {
		jobExecutorDao.insertsJobExecutor(vos);
	}

	@Override
	public void updateJobExecutor(JobExecutorVo vo) throws Exception {
		jobExecutorDao.updateJobExecutor(vo);
	}

	@Override
	public void updatesJobExecutor(JobExecutorVo[] vos) throws Exception {
		jobExecutorDao.updatesJobExecutor(vos);
	}

	@Override
	public void deleteJobExecutor(JobExecutorVo vo) throws Exception {
		jobExecutorDao.deleteJobExecutor(vo);
	}

	@Override
	public void deletesJobExecutor(JobExecutorVo[] vos) throws Exception {
		for (JobExecutorVo vo : vos) {
			jobExecutorDao.deleteJobExecutor(vo);
		}
	}

	@Override
	public PageVo<JobExecutorVo> queryJobExecutorIncludePage(JobExecutorVo vo, int page, int rows) throws Exception {
		return jobExecutorDao.queryJobExecutorIncludePage(vo, page, rows);
	}

	@Override
	public PageVo<JobExecutorVo> queryJobExecutorExcludePage(JobExecutorVo vo, int page, int rows) throws Exception {
		return jobExecutorDao.queryJobExecutorExcludePage(vo, page, rows);
	}
	
	@Override
	public List<JobExecutorVo> queryExecutorListByJob(JobExecutorVo vo) throws Exception {
		return jobExecutorDao.queryExecutorListByJob(vo);
	}
}
