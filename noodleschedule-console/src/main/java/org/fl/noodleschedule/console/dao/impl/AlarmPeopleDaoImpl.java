package org.fl.noodleschedule.console.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.fl.noodle.common.dynamicsql.DynamicSqlTemplate;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.dao.AlarmPeopleDao;
import org.fl.noodleschedule.console.domain.AlarmPeopleMd;
import org.fl.noodleschedule.console.vo.AlarmPeopleVo;

@Repository("alarmPeopleDao")
public class AlarmPeopleDaoImpl implements AlarmPeopleDao {

	@Autowired
	private DynamicSqlTemplate dynamicSqlTemplate;

	@Override
	public PageVo<AlarmPeopleVo> queryAlarmPeoplePage(AlarmPeopleVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("alarmPeople_Id", vo.getAlarmPeople_Id() != null ? vo.getAlarmPeople_Id() : null);
		paramsMap.put("alarmPeople_Name", vo.getAlarmPeople_Name() != null ? (new StringBuilder().append("%").append(vo.getAlarmPeople_Name()).append("%")).toString() : null);
		paramsMap.put("email", vo.getEmail() != null ? (new StringBuilder().append("%").append(vo.getEmail()).append("%")).toString() : null);
		paramsMap.put("phone", vo.getPhone() != null ? (new StringBuilder().append("%").append(vo.getPhone()).append("%")).toString() : null);
		paramsMap.put("status", vo.getStatus() != null ? vo.getStatus() : null);
		return dynamicSqlTemplate.queryPage("alarmpeople-query-page", paramsMap, page, rows, AlarmPeopleVo.class);
	}

	@Override
	public List<AlarmPeopleVo> queryAlarmPeopleList(AlarmPeopleVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("alarmPeople_Id", vo.getAlarmPeople_Id() != null ? vo.getAlarmPeople_Id() : null);
		paramsMap.put("alarmPeople_Name", vo.getAlarmPeople_Name() != null ? vo.getAlarmPeople_Name() : null);
		paramsMap.put("email", vo.getEmail() != null ? vo.getEmail() : null);
		paramsMap.put("phone", vo.getPhone() != null ? vo.getPhone() : null);
		paramsMap.put("status", vo.getStatus() != null ? vo.getStatus() : null);
		return dynamicSqlTemplate.queryList("alarmpeople-query-list", paramsMap, AlarmPeopleVo.class);
	}

	@Override
	public void insertAlarmPeople(AlarmPeopleVo vo) throws Exception {
		dynamicSqlTemplate.insert(vo, AlarmPeopleMd.class);
	}

	@Override
	public void insertsAlarmPeople(AlarmPeopleVo[] vos) throws Exception {
		for (AlarmPeopleVo vo : vos) {
			dynamicSqlTemplate.insert(vo, AlarmPeopleMd.class);
		}
	}

	@Override
	public void updateAlarmPeople(AlarmPeopleVo vo) throws Exception {
		dynamicSqlTemplate.updateNonull(vo, AlarmPeopleMd.class);
	}

	@Override
	public void updatesAlarmPeople(AlarmPeopleVo[] vos) throws Exception {
		for (AlarmPeopleVo vo : vos) {
			dynamicSqlTemplate.updateNonull(vo, AlarmPeopleMd.class);
		}
	}

	@Override
	public void deleteAlarmPeople(AlarmPeopleVo vo) throws Exception {
		dynamicSqlTemplate.delete(vo, AlarmPeopleMd.class);
	}

	@Override
	public void deletesAlarmPeople(AlarmPeopleVo[] vos) throws Exception {
		for (AlarmPeopleVo vo : vos) {
			dynamicSqlTemplate.delete(vo, AlarmPeopleMd.class);
		}
	}
}
