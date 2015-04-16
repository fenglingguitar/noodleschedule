package org.fl.noodleschedule.console.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.fl.noodleschedule.console.dao.LogExecutorDao;
import org.fl.noodleschedule.console.service.LogExecutorService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.LogExecutorVo;

@Service("logExecutorService")
public class LogExecutorServiceImpl implements LogExecutorService {

	@Autowired
	private LogExecutorDao logExecutorDao;

	@Override
	public PageVo<LogExecutorVo> queryLogExecutorPage(LogExecutorVo vo, int page, int rows) throws Exception {
		return logExecutorDao.queryLogExecutorPage(vo, page, rows);
	}

	@Override
	public List<LogExecutorVo> queryLogExecutorList(LogExecutorVo vo) throws Exception {
		return logExecutorDao.queryLogExecutorList(vo);
	}

	@Override
	public void insertLogExecutor(LogExecutorVo vo) throws Exception {
		logExecutorDao.insertLogExecutor(vo);
	}

	@Override
	public void insertsLogExecutor(LogExecutorVo[] vos) throws Exception {
		logExecutorDao.insertsLogExecutor(vos);
	}

	@Override
	public void updateLogExecutor(LogExecutorVo vo) throws Exception {
		logExecutorDao.updateLogExecutor(vo);
	}

	@Override
	public void updatesLogExecutor(LogExecutorVo[] vos) throws Exception {
		logExecutorDao.updatesLogExecutor(vos);
	}

	@Override
	public void deleteLogExecutor(LogExecutorVo vo) throws Exception {
		logExecutorDao.deleteLogExecutor(vo);
	}

	@Override
	public void deletesLogExecutor(LogExecutorVo[] vos) throws Exception {
		for (LogExecutorVo vo : vos) {
			logExecutorDao.deleteLogExecutor(vo);
		}
	}
	
	@Override
	public void deletesLogExecutorByTime(LogExecutorVo vo) throws Exception {
		logExecutorDao.deletesLogExecutorByTime(vo);
	}
	
	@Override
	public PageVo<LogExecutorVo> queryExecutorByLogPage(LogExecutorVo vo, int page, int rows) throws Exception {
		return logExecutorDao.queryExecutorByLogPage(vo, page, rows);
	}

	@Override
	public List<LogExecutorVo> queryLogExecutorListByLog(LogExecutorVo vo) throws Exception {
		return logExecutorDao.queryLogExecutorListByLog(vo);
	}

	@Override
	public List<LogExecutorVo> queryCompleteExecutorList(LogExecutorVo vo) throws Exception {
		return logExecutorDao.queryCompleteExecutorList(vo);
	}

	@Override
	public long updateLogExecutorAndStatisticalTime(LogExecutorVo vo) throws Exception {
		return logExecutorDao.updateLogExecutorAndStatisticalTime(vo);
	}
}
