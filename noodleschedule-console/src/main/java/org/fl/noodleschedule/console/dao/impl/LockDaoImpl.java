package org.fl.noodleschedule.console.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.fl.noodleschedule.console.dao.LockDao;
import org.fl.noodleschedule.console.domain.LockMd;
import org.fl.noodle.common.dynamicsql.DynamicSqlTemplate;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.LockVo;

@Repository("lockDao")
public class LockDaoImpl implements LockDao {

	@Autowired
	private DynamicSqlTemplate dynamicSqlTemplate;

	@Override
	public PageVo<LockVo> queryLockPage(LockVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", vo.getId());
		return dynamicSqlTemplate.queryPage("lock-query-page", paramsMap, page, rows, LockVo.class);
	}

	@Override
	public List<LockVo> queryLockList(LockVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", vo.getId());
		return dynamicSqlTemplate.queryList("lock-query-list", paramsMap, LockVo.class);
	}

	@Override
	public void insertLock(LockVo vo) throws Exception {
		dynamicSqlTemplate.insert(vo, LockMd.class);
	}

	@Override
	public void insertsLock(LockVo[] vos) throws Exception {
		for (LockVo vo : vos) {
			dynamicSqlTemplate.insert(vo, LockMd.class);
		}
	}

	@Override
	public void updateLock(LockVo vo) throws Exception {
		dynamicSqlTemplate.updateNonull(vo, LockMd.class);
	}

	@Override
	public void updatesLock(LockVo[] vos) throws Exception {
		for (LockVo vo : vos) {
			dynamicSqlTemplate.updateNonull(vo, LockMd.class);
		}
	}

	@Override
	public void deleteLock(LockVo vo) throws Exception {
		dynamicSqlTemplate.delete(vo, LockMd.class);
	}

	@Override
	public void deletesLock(LockVo[] vos) throws Exception {
		for (LockVo vo : vos) {
			dynamicSqlTemplate.delete(vo, LockMd.class);
		}
	}
}
