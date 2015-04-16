package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.fl.noodleschedule.console.service.ExecutorService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.ExecutorVo;
import org.fl.noodleschedule.console.web.mvc.annotation.RequestParam;
import org.fl.noodleschedule.console.web.mvc.annotation.ResponseBody;
import org.fl.noodleschedule.console.web.mvc.util.VoidVo;

@Controller
@RequestMapping(value = "console/executor")
public class ExecutorController {
	
	@Autowired
	private ExecutorService executorService;
	
	@RequestMapping(value = "/querypage")
	@ResponseBody
	public PageVo<ExecutorVo> queryPage(@RequestParam ExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return executorService.queryExecutorPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@ResponseBody
	public List<ExecutorVo> queryList(@RequestParam ExecutorVo vo) throws Exception {
		return executorService.queryExecutorList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@ResponseBody
	public VoidVo insert(@RequestParam ExecutorVo vo) throws Exception {
		executorService.insertExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@ResponseBody
	public VoidVo inserts(@RequestParam ExecutorVo[] vos) throws Exception {
		executorService.insertsExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public VoidVo update(@RequestParam ExecutorVo vo) throws Exception {
		executorService.updateExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@ResponseBody
	public VoidVo updates(@RequestParam ExecutorVo[] vos) throws Exception {
		executorService.updatesExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public VoidVo delete(@RequestParam ExecutorVo vo) throws Exception {
		executorService.deleteExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@ResponseBody
	public VoidVo deletes(@RequestParam ExecutorVo[] vos) throws Exception {
		executorService.deletesExecutor(vos);
		return VoidVo.VOID;
	}
}
