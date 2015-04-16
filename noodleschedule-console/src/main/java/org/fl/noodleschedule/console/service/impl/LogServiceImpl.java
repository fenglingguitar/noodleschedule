package org.fl.noodleschedule.console.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.fl.noodleschedule.console.dao.LogDao;
import org.fl.noodleschedule.console.service.LogService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobLogVo;
import org.fl.noodleschedule.console.vo.LogVo;

@Service("logService")
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;

	@Override
	public PageVo<LogVo> queryLogPage(LogVo vo, int page, int rows) throws Exception {
		return logDao.queryLogPage(vo, page, rows);
	}

	@Override
	public List<LogVo> queryLogList(LogVo vo) throws Exception {
		return logDao.queryLogList(vo);
	}

	@Override
	public void insertLog(LogVo vo) throws Exception {
		logDao.insertLog(vo);
	}

	@Override
	public void insertsLog(LogVo[] vos) throws Exception {
		logDao.insertsLog(vos);
	}

	@Override
	public void updateLog(LogVo vo) throws Exception {
		logDao.updateLog(vo);
	}

	@Override
	public void updatesLog(LogVo[] vos) throws Exception {
		logDao.updatesLog(vos);
	}

	@Override
	public void deleteLog(LogVo vo) throws Exception {
		logDao.deleteLog(vo);
	}

	@Override
	public void deletesLog(LogVo[] vos) throws Exception {
		for (LogVo vo : vos) {
			logDao.deleteLog(vo);
		}
	}

	@Override
	public void deleteLogByTime(LogVo vo) throws Exception {
		logDao.deleteLogByTime(vo);
	}

	@Override
	public PageVo<JobLogVo> queryJobLogPage(JobLogVo vo, int page, int rows) throws Exception {
		return logDao.queryJobLogPage(vo, page, rows);
	}

	@Override
	public Long insertLogReturnId(LogVo vo) throws Exception {
		return logDao.insertLogReturnId(vo);
	}

	@Override
	public int updateLogCumulativeComplete(LogVo vo) throws Exception {
		return logDao.updateLogCumulativeComplete(vo);
	}

	@Override
	public int queryLogCount(LogVo vo) throws Exception {
		return logDao.queryLogCount(vo);
	}
}
