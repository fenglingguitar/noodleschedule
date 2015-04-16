package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.fl.noodleschedule.console.service.JobAlarmPeopleService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobAlarmPeopleVo;
import org.fl.noodleschedule.console.web.mvc.annotation.RequestParam;
import org.fl.noodleschedule.console.web.mvc.annotation.ResponseBody;
import org.fl.noodleschedule.console.web.mvc.util.VoidVo;

@Controller
@RequestMapping(value = "alarm/job/alarmpeople")
public class JobAlarmPeopleController {
	
	@Autowired
	private JobAlarmPeopleService jobAlarmPeopleService;
	
	@RequestMapping(value = "/querypage")
	@ResponseBody
	public PageVo<JobAlarmPeopleVo> queryPage(@RequestParam JobAlarmPeopleVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobAlarmPeopleService.queryJobAlarmPeoplePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@ResponseBody
	public List<JobAlarmPeopleVo> queryList(@RequestParam JobAlarmPeopleVo vo) throws Exception {
		return jobAlarmPeopleService.queryJobAlarmPeopleList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@ResponseBody
	public VoidVo insert(@RequestParam JobAlarmPeopleVo vo) throws Exception {
		jobAlarmPeopleService.insertJobAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@ResponseBody
	public VoidVo inserts(@RequestParam JobAlarmPeopleVo[] vos) throws Exception {
		jobAlarmPeopleService.insertsJobAlarmPeople(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public VoidVo update(@RequestParam JobAlarmPeopleVo vo) throws Exception {
		jobAlarmPeopleService.updateJobAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@ResponseBody
	public VoidVo updates(@RequestParam JobAlarmPeopleVo[] vos) throws Exception {
		jobAlarmPeopleService.updatesJobAlarmPeople(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public VoidVo delete(@RequestParam JobAlarmPeopleVo vo) throws Exception {
		jobAlarmPeopleService.deleteJobAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@ResponseBody
	public VoidVo deletes(@RequestParam JobAlarmPeopleVo[] vos) throws Exception {
		jobAlarmPeopleService.deletesJobAlarmPeople(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/queryincludepage")
	@ResponseBody
	public PageVo<JobAlarmPeopleVo> queryIncludePage(@RequestParam JobAlarmPeopleVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobAlarmPeopleService.queryJobAlarmPeopleIncludePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/queryexcludepage")
	@ResponseBody
	public PageVo<JobAlarmPeopleVo> queryExcludePage(@RequestParam JobAlarmPeopleVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobAlarmPeopleService.queryJobAlarmPeopleExcludePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
}
