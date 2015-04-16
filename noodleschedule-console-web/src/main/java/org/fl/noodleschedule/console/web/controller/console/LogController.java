package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.fl.noodleschedule.console.service.LogService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobLogVo;
import org.fl.noodleschedule.console.vo.LogVo;
import org.fl.noodleschedule.console.web.mvc.annotation.RequestParam;
import org.fl.noodleschedule.console.web.mvc.annotation.ResponseBody;
import org.fl.noodleschedule.console.web.mvc.util.VoidVo;

@Controller
@RequestMapping(value = "console/log")
public class LogController {
	
	@Autowired
	private LogService logService;
	
	@RequestMapping(value = "/querypage")
	@ResponseBody
	public PageVo<LogVo> queryPage(@RequestParam LogVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return logService.queryLogPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@ResponseBody
	public List<LogVo> queryList(@RequestParam LogVo vo) throws Exception {
		return logService.queryLogList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@ResponseBody
	public VoidVo insert(@RequestParam LogVo vo) throws Exception {
		logService.insertLog(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@ResponseBody
	public VoidVo inserts(@RequestParam LogVo[] vos) throws Exception {
		logService.insertsLog(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public VoidVo update(@RequestParam LogVo vo) throws Exception {
		logService.updateLog(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@ResponseBody
	public VoidVo updates(@RequestParam LogVo[] vos) throws Exception {
		logService.updatesLog(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public VoidVo delete(@RequestParam LogVo vo) throws Exception {
		logService.deleteLog(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@ResponseBody
	public VoidVo deletes(@RequestParam LogVo[] vos) throws Exception {
		logService.deletesLog(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/queryjoblogpage")
	@ResponseBody
	public PageVo<JobLogVo> queryJobLogPage(@RequestParam JobLogVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return logService.queryJobLogPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
}
