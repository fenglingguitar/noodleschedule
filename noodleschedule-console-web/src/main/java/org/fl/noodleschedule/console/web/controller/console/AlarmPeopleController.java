package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.fl.noodleschedule.console.service.AlarmPeopleService;
import org.fl.noodle.common.mvc.annotation.NoodleRequestParam;
import org.fl.noodle.common.mvc.annotation.NoodleResponseBody;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodle.common.mvc.vo.VoidVo;
import org.fl.noodleschedule.console.vo.AlarmPeopleVo;

@Controller
@RequestMapping(value = "alarm/alarmpeople")
public class AlarmPeopleController {
	
	@Autowired
	private AlarmPeopleService alarmPeopleService;
	
	@RequestMapping(value = "/querypage")
	@NoodleResponseBody
	public PageVo<AlarmPeopleVo> queryPage(@NoodleRequestParam AlarmPeopleVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return alarmPeopleService.queryAlarmPeoplePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@NoodleResponseBody
	public List<AlarmPeopleVo> queryList(@NoodleRequestParam AlarmPeopleVo vo) throws Exception {
		return alarmPeopleService.queryAlarmPeopleList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@NoodleResponseBody
	public VoidVo insert(@NoodleRequestParam AlarmPeopleVo vo) throws Exception {
		alarmPeopleService.insertAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@NoodleResponseBody
	public VoidVo inserts(@NoodleRequestParam AlarmPeopleVo[] vos) throws Exception {
		alarmPeopleService.insertsAlarmPeople(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@NoodleResponseBody
	public VoidVo update(@NoodleRequestParam AlarmPeopleVo vo) throws Exception {
		alarmPeopleService.updateAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@NoodleResponseBody
	public VoidVo updates(@NoodleRequestParam AlarmPeopleVo[] vos) throws Exception {
		alarmPeopleService.updatesAlarmPeople(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@NoodleResponseBody
	public VoidVo delete(@NoodleRequestParam AlarmPeopleVo vo) throws Exception {
		alarmPeopleService.deleteAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@NoodleResponseBody
	public VoidVo deletes(@NoodleRequestParam AlarmPeopleVo[] vos) throws Exception {
		alarmPeopleService.deletesAlarmPeople(vos);
		return VoidVo.VOID;
	}
}
