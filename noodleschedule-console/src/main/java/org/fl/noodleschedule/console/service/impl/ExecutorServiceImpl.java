package org.fl.noodleschedule.console.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.fl.noodleschedule.console.dao.ExecutorDao;
import org.fl.noodleschedule.console.dao.JobExecutorDao;
import org.fl.noodleschedule.console.service.ExecutorService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.ExecutorVo;
import org.fl.noodleschedule.console.vo.JobExecutorVo;

@Service("executorService")
public class ExecutorServiceImpl implements ExecutorService {

	@Autowired
	private ExecutorDao executorDao;

	@Autowired
	private JobExecutorDao jobExecutorDao;
	
	@Override
	public PageVo<ExecutorVo> queryExecutorPage(ExecutorVo vo, int page, int rows) throws Exception {
		return executorDao.queryExecutorPage(vo, page, rows);
	}

	@Override
	public List<ExecutorVo> queryExecutorList(ExecutorVo vo) throws Exception {
		return executorDao.queryExecutorList(vo);
	}

	@Override
	public void insertExecutor(ExecutorVo vo) throws Exception {
		executorDao.insertExecutor(vo);
	}

	@Override
	public void insertsExecutor(ExecutorVo[] vos) throws Exception {
		executorDao.insertsExecutor(vos);
	}

	@Override
	public void updateExecutor(ExecutorVo vo) throws Exception {
		executorDao.updateExecutor(vo);
	}

	@Override
	public void updatesExecutor(ExecutorVo[] vos) throws Exception {
		executorDao.updatesExecutor(vos);
	}

	@Override
	public void deleteExecutor(ExecutorVo vo) throws Exception {
		JobExecutorVo jobExecutorVo = new JobExecutorVo();
		jobExecutorVo.setExecutor_Id(vo.getExecutor_Id());
		jobExecutorDao.deletesJobExecutorByExecutor(jobExecutorVo);
		executorDao.deleteExecutor(vo);
	}

	@Override
	public void deletesExecutor(ExecutorVo[] vos) throws Exception {
		for (ExecutorVo vo : vos) {
			JobExecutorVo jobExecutorVo = new JobExecutorVo();
			jobExecutorVo.setExecutor_Id(vo.getExecutor_Id());
			jobExecutorDao.deletesJobExecutorByExecutor(jobExecutorVo);
			executorDao.deleteExecutor(vo);
		}
	}
}
