package org.fl.noodleschedule.console.service;

import java.util.List;

import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.LockVo;

public interface LockService {
	
	public PageVo<LockVo> queryLockPage(LockVo vo, int page, int rows) throws Exception;

	public List<LockVo> queryLockList(LockVo vo) throws Exception;

	public void insertLock(LockVo vo) throws Exception;

	public void insertsLock(LockVo[] vos) throws Exception;

	public void updateLock(LockVo vo) throws Exception;

	public void updatesLock(LockVo[] vos) throws Exception;

	public void deleteLock(LockVo vo) throws Exception;

	public void deletesLock(LockVo[] vos) throws Exception;
}
