package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.fl.noodleschedule.console.service.JobExecutorService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobExecutorVo;
import org.fl.noodleschedule.console.web.mvc.annotation.RequestParam;
import org.fl.noodleschedule.console.web.mvc.annotation.ResponseBody;
import org.fl.noodleschedule.console.web.mvc.util.VoidVo;

@Controller
@RequestMapping(value = "console/job/executor")
public class JobExecutorController {
	
	@Autowired
	private JobExecutorService jobExecutorService;
	
	@RequestMapping(value = "/querypage")
	@ResponseBody
	public PageVo<JobExecutorVo> queryPage(@RequestParam JobExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobExecutorService.queryJobExecutorPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@ResponseBody
	public List<JobExecutorVo> queryList(@RequestParam JobExecutorVo vo) throws Exception {
		return jobExecutorService.queryJobExecutorList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@ResponseBody
	public VoidVo insert(@RequestParam JobExecutorVo vo) throws Exception {
		jobExecutorService.insertJobExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@ResponseBody
	public VoidVo inserts(@RequestParam JobExecutorVo[] vos) throws Exception {
		jobExecutorService.insertsJobExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public VoidVo update(@RequestParam JobExecutorVo vo) throws Exception {
		jobExecutorService.updateJobExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@ResponseBody
	public VoidVo updates(@RequestParam JobExecutorVo[] vos) throws Exception {
		jobExecutorService.updatesJobExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public VoidVo delete(@RequestParam JobExecutorVo vo) throws Exception {
		jobExecutorService.deleteJobExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@ResponseBody
	public VoidVo deletes(@RequestParam JobExecutorVo[] vos) throws Exception {
		jobExecutorService.deletesJobExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/queryincludepage")
	@ResponseBody
	public PageVo<JobExecutorVo> queryIncludePage(@RequestParam JobExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobExecutorService.queryJobExecutorIncludePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/queryexcludepage")
	@ResponseBody
	public PageVo<JobExecutorVo> queryExcludePage(@RequestParam JobExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobExecutorService.queryJobExecutorExcludePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
}
