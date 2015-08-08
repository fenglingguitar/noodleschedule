package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.fl.noodleschedule.console.service.JobAlarmPeopleService;
import org.fl.noodle.common.mvc.annotation.NoodleRequestParam;
import org.fl.noodle.common.mvc.annotation.NoodleResponseBody;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodle.common.mvc.vo.VoidVo;
import org.fl.noodleschedule.console.vo.JobAlarmPeopleVo;

@Controller
@RequestMapping(value = "alarm/job/alarmpeople")
public class JobAlarmPeopleController {
	
	@Autowired
	private JobAlarmPeopleService jobAlarmPeopleService;
	
	@RequestMapping(value = "/querypage")
	@NoodleResponseBody
	public PageVo<JobAlarmPeopleVo> queryPage(@NoodleRequestParam JobAlarmPeopleVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobAlarmPeopleService.queryJobAlarmPeoplePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@NoodleResponseBody
	public List<JobAlarmPeopleVo> queryList(@NoodleRequestParam JobAlarmPeopleVo vo) throws Exception {
		return jobAlarmPeopleService.queryJobAlarmPeopleList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@NoodleResponseBody
	public VoidVo insert(@NoodleRequestParam JobAlarmPeopleVo vo) throws Exception {
		jobAlarmPeopleService.insertJobAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@NoodleResponseBody
	public VoidVo inserts(@NoodleRequestParam JobAlarmPeopleVo[] vos) throws Exception {
		jobAlarmPeopleService.insertsJobAlarmPeople(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@NoodleResponseBody
	public VoidVo update(@NoodleRequestParam JobAlarmPeopleVo vo) throws Exception {
		jobAlarmPeopleService.updateJobAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@NoodleResponseBody
	public VoidVo updates(@NoodleRequestParam JobAlarmPeopleVo[] vos) throws Exception {
		jobAlarmPeopleService.updatesJobAlarmPeople(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@NoodleResponseBody
	public VoidVo delete(@NoodleRequestParam JobAlarmPeopleVo vo) throws Exception {
		jobAlarmPeopleService.deleteJobAlarmPeople(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@NoodleResponseBody
	public VoidVo deletes(@NoodleRequestParam JobAlarmPeopleVo[] vos) throws Exception {
		jobAlarmPeopleService.deletesJobAlarmPeople(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/queryincludepage")
	@NoodleResponseBody
	public PageVo<JobAlarmPeopleVo> queryIncludePage(@NoodleRequestParam JobAlarmPeopleVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobAlarmPeopleService.queryJobAlarmPeopleIncludePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/queryexcludepage")
	@NoodleResponseBody
	public PageVo<JobAlarmPeopleVo> queryExcludePage(@NoodleRequestParam JobAlarmPeopleVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobAlarmPeopleService.queryJobAlarmPeopleExcludePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
}
