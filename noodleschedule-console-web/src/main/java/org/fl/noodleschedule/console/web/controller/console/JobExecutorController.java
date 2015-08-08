package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.fl.noodleschedule.console.service.JobExecutorService;
import org.fl.noodle.common.mvc.annotation.NoodleRequestParam;
import org.fl.noodle.common.mvc.annotation.NoodleResponseBody;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodle.common.mvc.vo.VoidVo;
import org.fl.noodleschedule.console.vo.JobExecutorVo;

@Controller
@RequestMapping(value = "console/job/executor")
public class JobExecutorController {
	
	@Autowired
	private JobExecutorService jobExecutorService;
	
	@RequestMapping(value = "/querypage")
	@NoodleResponseBody
	public PageVo<JobExecutorVo> queryPage(@NoodleRequestParam JobExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobExecutorService.queryJobExecutorPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@NoodleResponseBody
	public List<JobExecutorVo> queryList(@NoodleRequestParam JobExecutorVo vo) throws Exception {
		return jobExecutorService.queryJobExecutorList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@NoodleResponseBody
	public VoidVo insert(@NoodleRequestParam JobExecutorVo vo) throws Exception {
		jobExecutorService.insertJobExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@NoodleResponseBody
	public VoidVo inserts(@NoodleRequestParam JobExecutorVo[] vos) throws Exception {
		jobExecutorService.insertsJobExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@NoodleResponseBody
	public VoidVo update(@NoodleRequestParam JobExecutorVo vo) throws Exception {
		jobExecutorService.updateJobExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@NoodleResponseBody
	public VoidVo updates(@NoodleRequestParam JobExecutorVo[] vos) throws Exception {
		jobExecutorService.updatesJobExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@NoodleResponseBody
	public VoidVo delete(@NoodleRequestParam JobExecutorVo vo) throws Exception {
		jobExecutorService.deleteJobExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@NoodleResponseBody
	public VoidVo deletes(@NoodleRequestParam JobExecutorVo[] vos) throws Exception {
		jobExecutorService.deletesJobExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/queryincludepage")
	@NoodleResponseBody
	public PageVo<JobExecutorVo> queryIncludePage(@NoodleRequestParam JobExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobExecutorService.queryJobExecutorIncludePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/queryexcludepage")
	@NoodleResponseBody
	public PageVo<JobExecutorVo> queryExcludePage(@NoodleRequestParam JobExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobExecutorService.queryJobExecutorExcludePage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
}
