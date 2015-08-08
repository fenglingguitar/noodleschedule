package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.fl.noodleschedule.console.service.LogService;
import org.fl.noodle.common.mvc.annotation.NoodleRequestParam;
import org.fl.noodle.common.mvc.annotation.NoodleResponseBody;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodle.common.mvc.vo.VoidVo;
import org.fl.noodleschedule.console.vo.JobLogVo;
import org.fl.noodleschedule.console.vo.LogVo;

@Controller
@RequestMapping(value = "console/log")
public class LogController {
	
	@Autowired
	private LogService logService;
	
	@RequestMapping(value = "/querypage")
	@NoodleResponseBody
	public PageVo<LogVo> queryPage(@NoodleRequestParam LogVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return logService.queryLogPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@NoodleResponseBody
	public List<LogVo> queryList(@NoodleRequestParam LogVo vo) throws Exception {
		return logService.queryLogList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@NoodleResponseBody
	public VoidVo insert(@NoodleRequestParam LogVo vo) throws Exception {
		logService.insertLog(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@NoodleResponseBody
	public VoidVo inserts(@NoodleRequestParam LogVo[] vos) throws Exception {
		logService.insertsLog(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@NoodleResponseBody
	public VoidVo update(@NoodleRequestParam LogVo vo) throws Exception {
		logService.updateLog(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@NoodleResponseBody
	public VoidVo updates(@NoodleRequestParam LogVo[] vos) throws Exception {
		logService.updatesLog(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@NoodleResponseBody
	public VoidVo delete(@NoodleRequestParam LogVo vo) throws Exception {
		logService.deleteLog(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@NoodleResponseBody
	public VoidVo deletes(@NoodleRequestParam LogVo[] vos) throws Exception {
		logService.deletesLog(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/queryjoblogpage")
	@NoodleResponseBody
	public PageVo<JobLogVo> queryJobLogPage(@NoodleRequestParam JobLogVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return logService.queryJobLogPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
}
