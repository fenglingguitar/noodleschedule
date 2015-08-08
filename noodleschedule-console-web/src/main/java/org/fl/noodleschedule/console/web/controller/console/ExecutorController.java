package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.fl.noodleschedule.console.service.ExecutorService;
import org.fl.noodle.common.mvc.annotation.NoodleRequestParam;
import org.fl.noodle.common.mvc.annotation.NoodleResponseBody;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodle.common.mvc.vo.VoidVo;
import org.fl.noodleschedule.console.vo.ExecutorVo;

@Controller
@RequestMapping(value = "console/executor")
public class ExecutorController {
	
	@Autowired
	private ExecutorService executorService;
	
	@RequestMapping(value = "/querypage")
	@NoodleResponseBody
	public PageVo<ExecutorVo> queryPage(@NoodleRequestParam ExecutorVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return executorService.queryExecutorPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@NoodleResponseBody
	public List<ExecutorVo> queryList(@NoodleRequestParam ExecutorVo vo) throws Exception {
		return executorService.queryExecutorList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@NoodleResponseBody
	public VoidVo insert(@NoodleRequestParam ExecutorVo vo) throws Exception {
		executorService.insertExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@NoodleResponseBody
	public VoidVo inserts(@NoodleRequestParam ExecutorVo[] vos) throws Exception {
		executorService.insertsExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@NoodleResponseBody
	public VoidVo update(@NoodleRequestParam ExecutorVo vo) throws Exception {
		executorService.updateExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@NoodleResponseBody
	public VoidVo updates(@NoodleRequestParam ExecutorVo[] vos) throws Exception {
		executorService.updatesExecutor(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@NoodleResponseBody
	public VoidVo delete(@NoodleRequestParam ExecutorVo vo) throws Exception {
		executorService.deleteExecutor(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@NoodleResponseBody
	public VoidVo deletes(@NoodleRequestParam ExecutorVo[] vos) throws Exception {
		executorService.deletesExecutor(vos);
		return VoidVo.VOID;
	}
}
