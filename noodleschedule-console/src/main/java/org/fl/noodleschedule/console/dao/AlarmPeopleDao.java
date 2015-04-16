package org.fl.noodleschedule.console.dao;

import java.util.List;

import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.AlarmPeopleVo;

public interface AlarmPeopleDao {
	
	public PageVo<AlarmPeopleVo> queryAlarmPeoplePage(AlarmPeopleVo vo, int page, int rows) throws Exception;

	public List<AlarmPeopleVo> queryAlarmPeopleList(AlarmPeopleVo vo) throws Exception;

	public void insertAlarmPeople(AlarmPeopleVo vo) throws Exception;

	public void insertsAlarmPeople(AlarmPeopleVo[] vos) throws Exception;

	public void updateAlarmPeople(AlarmPeopleVo vo) throws Exception;

	public void updatesAlarmPeople(AlarmPeopleVo[] vos) throws Exception;

	public void deleteAlarmPeople(AlarmPeopleVo vo) throws Exception;

	public void deletesAlarmPeople(AlarmPeopleVo[] vos) throws Exception;
}
