package org.fl.noodleschedule.console.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.fl.noodleschedule.console.dao.LockDao;
import org.fl.noodleschedule.console.service.LockService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.LockVo;

@Service("lockService")
public class LockServiceImpl implements LockService {

	@Autowired
	private LockDao lockDao;
	
	@Override
	public PageVo<LockVo> queryLockPage(LockVo vo, int page, int rows) throws Exception {
		return lockDao.queryLockPage(vo, page, rows);
	}

	@Override
	public List<LockVo> queryLockList(LockVo vo) throws Exception {
		return lockDao.queryLockList(vo);
	}

	@Override
	public void insertLock(LockVo vo) throws Exception {
		lockDao.insertLock(vo);
	}

	@Override
	public void insertsLock(LockVo[] vos) throws Exception {
		lockDao.insertsLock(vos);
	}

	@Override
	public void updateLock(LockVo vo) throws Exception {
		lockDao.updateLock(vo);
	}

	@Override
	public void updatesLock(LockVo[] vos) throws Exception {
		lockDao.updatesLock(vos);
	}

	@Override
	public void deleteLock(LockVo vo) throws Exception {
		lockDao.deleteLock(vo);
	}

	@Override
	public void deletesLock(LockVo[] vos) throws Exception {
		for (LockVo vo : vos) {
			lockDao.deleteLock(vo);
		}
	}
}
