package org.fl.noodleschedule.console.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.fl.noodleschedule.console.dao.AlarmPeopleDao;
import org.fl.noodleschedule.console.dao.JobAlarmPeopleDao;
import org.fl.noodleschedule.console.service.AlarmPeopleService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.AlarmPeopleVo;
import org.fl.noodleschedule.console.vo.JobAlarmPeopleVo;

@Service("alarmPeopleService")
public class AlarmPeopleServiceImpl implements AlarmPeopleService {

	@Autowired
	private AlarmPeopleDao alarmPeopleDao;

	@Autowired
	private JobAlarmPeopleDao jobAlarmPeopleDao;
	
	@Override
	public PageVo<AlarmPeopleVo> queryAlarmPeoplePage(AlarmPeopleVo vo, int page, int rows) throws Exception {
		return alarmPeopleDao.queryAlarmPeoplePage(vo, page, rows);
	}

	@Override
	public List<AlarmPeopleVo> queryAlarmPeopleList(AlarmPeopleVo vo) throws Exception {
		return alarmPeopleDao.queryAlarmPeopleList(vo);
	}

	@Override
	public void insertAlarmPeople(AlarmPeopleVo vo) throws Exception {
		alarmPeopleDao.insertAlarmPeople(vo);
	}

	@Override
	public void insertsAlarmPeople(AlarmPeopleVo[] vos) throws Exception {
		alarmPeopleDao.insertsAlarmPeople(vos);
	}

	@Override
	public void updateAlarmPeople(AlarmPeopleVo vo) throws Exception {
		alarmPeopleDao.updateAlarmPeople(vo);
	}

	@Override
	public void updatesAlarmPeople(AlarmPeopleVo[] vos) throws Exception {
		alarmPeopleDao.updatesAlarmPeople(vos);
	}

	@Override
	public void deleteAlarmPeople(AlarmPeopleVo vo) throws Exception {
		JobAlarmPeopleVo jobAlarmPeopleVo = new JobAlarmPeopleVo();
		jobAlarmPeopleVo.setAlarmPeople_Id(vo.getAlarmPeople_Id());
		jobAlarmPeopleDao.deletesJobAlarmPeopleByAlarmPeople(jobAlarmPeopleVo);
		alarmPeopleDao.deleteAlarmPeople(vo);
	}

	@Override
	public void deletesAlarmPeople(AlarmPeopleVo[] vos) throws Exception {
		for (AlarmPeopleVo vo : vos) {
			JobAlarmPeopleVo jobAlarmPeopleVo = new JobAlarmPeopleVo();
			jobAlarmPeopleVo.setAlarmPeople_Id(vo.getAlarmPeople_Id());
			jobAlarmPeopleDao.deletesJobAlarmPeopleByAlarmPeople(jobAlarmPeopleVo);
			alarmPeopleDao.deleteAlarmPeople(vo);
		}
	}
}
