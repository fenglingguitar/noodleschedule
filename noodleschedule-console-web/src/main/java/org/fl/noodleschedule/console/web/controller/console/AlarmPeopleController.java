package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.fl.noodleschedule.console.service.AlarmPeopleService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.AlarmPeopleVo;
import org.fl.noodleschedule.console.web.mvc.annotation.RequestParam;
import org.fl.noodleschedule.console.web.mvc.annotation.ResponseBody;
import org.fl.noodleschedule.console.web.mvc.util.VoidVo;

@Controller
@RequestMapping(value = "alarm/alarmpeople")
public class AlarmPeopleController {
	
	@Autowired
	private AlarmPeopleService alarmPeopleService;
	
	@RequestMapping(value = "/querypage")
	@ResponseBody
	public PageVo<AlarmPeopleVo> queryPage(@RequestParam AlarmPeopleVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return alarmPeopleService.queryAlarmPeoplePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@ResponseBody
	public List<AlarmPeopleVo> queryList(@RequestParam AlarmPeopleVo vo) throws Exception {
		return alarmPeopleService.queryAlarmPeopleList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@ResponseBody
	public VoidVo insert(@RequestParam AlarmPeopleVo vo) throws Exception {
		alarmPeopleService.insertAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@ResponseBody
	public VoidVo inserts(@RequestParam AlarmPeopleVo[] vos) throws Exception {
		alarmPeopleService.insertsAlarmPeople(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public VoidVo update(@RequestParam AlarmPeopleVo vo) throws Exception {
		alarmPeopleService.updateAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@ResponseBody
	public VoidVo updates(@RequestParam AlarmPeopleVo[] vos) throws Exception {
		alarmPeopleService.updatesAlarmPeople(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public VoidVo delete(@RequestParam AlarmPeopleVo vo) throws Exception {
		alarmPeopleService.deleteAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@ResponseBody
	public VoidVo deletes(@RequestParam AlarmPeopleVo[] vos) throws Exception {
		alarmPeopleService.deletesAlarmPeople(vos);
		return VoidVo.VOID;
	}
}
