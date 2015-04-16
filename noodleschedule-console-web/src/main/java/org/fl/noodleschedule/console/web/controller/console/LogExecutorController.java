package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.fl.noodleschedule.console.service.LogExecutorService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.LogExecutorVo;
import org.fl.noodleschedule.console.web.mvc.annotation.RequestParam;
import org.fl.noodleschedule.console.web.mvc.annotation.ResponseBody;
import org.fl.noodleschedule.console.web.mvc.util.VoidVo;

@Controller
@RequestMapping(value = "console/log/executor")
public class LogExecutorController {
	
	@Autowired
	private LogExecutorService logExecutorService;
	
	@RequestMapping(value = "/querypage")
	@ResponseBody
	public PageVo<LogExecutorVo> queryPage(@RequestParam LogExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return logExecutorService.queryLogExecutorPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@ResponseBody
	public List<LogExecutorVo> queryList(@RequestParam LogExecutorVo vo) throws Exception {
		return logExecutorService.queryLogExecutorList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@ResponseBody
	public VoidVo insert(@RequestParam LogExecutorVo vo) throws Exception {
		logExecutorService.insertLogExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@ResponseBody
	public VoidVo inserts(@RequestParam LogExecutorVo[] vos) throws Exception {
		logExecutorService.insertsLogExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public VoidVo update(@RequestParam LogExecutorVo vo) throws Exception {
		logExecutorService.updateLogExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@ResponseBody
	public VoidVo updates(@RequestParam LogExecutorVo[] vos) throws Exception {
		logExecutorService.updatesLogExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public VoidVo delete(@RequestParam LogExecutorVo vo) throws Exception {
		logExecutorService.deleteLogExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@ResponseBody
	public VoidVo deletes(@RequestParam LogExecutorVo[] vos) throws Exception {
		logExecutorService.deletesLogExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/queryexecutorbylogpage")
	@ResponseBody
	public PageVo<LogExecutorVo> queryExecutorByLogPage(@RequestParam LogExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return logExecutorService.queryExecutorByLogPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
}
