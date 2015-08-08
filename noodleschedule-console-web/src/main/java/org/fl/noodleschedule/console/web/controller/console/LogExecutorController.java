package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.fl.noodleschedule.console.service.LogExecutorService;
import org.fl.noodle.common.mvc.annotation.NoodleRequestParam;
import org.fl.noodle.common.mvc.annotation.NoodleResponseBody;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodle.common.mvc.vo.VoidVo;
import org.fl.noodleschedule.console.vo.LogExecutorVo;

@Controller
@RequestMapping(value = "console/log/executor")
public class LogExecutorController {
	
	@Autowired
	private LogExecutorService logExecutorService;
	
	@RequestMapping(value = "/querypage")
	@NoodleResponseBody
	public PageVo<LogExecutorVo> queryPage(@NoodleRequestParam LogExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return logExecutorService.queryLogExecutorPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@NoodleResponseBody
	public List<LogExecutorVo> queryList(@NoodleRequestParam LogExecutorVo vo) throws Exception {
		return logExecutorService.queryLogExecutorList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@NoodleResponseBody
	public VoidVo insert(@NoodleRequestParam LogExecutorVo vo) throws Exception {
		logExecutorService.insertLogExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@NoodleResponseBody
	public VoidVo inserts(@NoodleRequestParam LogExecutorVo[] vos) throws Exception {
		logExecutorService.insertsLogExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@NoodleResponseBody
	public VoidVo update(@NoodleRequestParam LogExecutorVo vo) throws Exception {
		logExecutorService.updateLogExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@NoodleResponseBody
	public VoidVo updates(@NoodleRequestParam LogExecutorVo[] vos) throws Exception {
		logExecutorService.updatesLogExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@NoodleResponseBody
	public VoidVo delete(@NoodleRequestParam LogExecutorVo vo) throws Exception {
		logExecutorService.deleteLogExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@NoodleResponseBody
	public VoidVo deletes(@NoodleRequestParam LogExecutorVo[] vos) throws Exception {
		logExecutorService.deletesLogExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/queryexecutorbylogpage")
	@NoodleResponseBody
	public PageVo<LogExecutorVo> queryExecutorByLogPage(@NoodleRequestParam LogExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return logExecutorService.queryExecutorByLogPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
}
