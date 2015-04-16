package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.fl.noodleschedule.console.service.CoreService;
import org.fl.noodleschedule.console.service.JobService;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobLogVo;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.console.web.jump.JumpToMaster;
import org.fl.noodleschedule.console.web.mvc.annotation.RequestParam;
import org.fl.noodleschedule.console.web.mvc.annotation.ResponseBody;
import org.fl.noodleschedule.console.web.mvc.util.ResultVo;
import org.fl.noodleschedule.console.web.mvc.util.VoidVo;
import org.fl.noodleschedule.core.scheduler.Despatcher;
import org.fl.noodleschedule.core.trigger.ExecuteTrigger;
import org.fl.noodleschedule.util.common.Constant;

@Controller
@RequestMapping(value = "console/job")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private ExecuteTrigger executeTrigger;
	
	@Autowired
	private Despatcher completionDespatcher;
	
	@Autowired
	private CoreService coreService;
	
	@Autowired
	private JumpToMaster jumpToMaster;
	
	@RequestMapping(value = "/querypage")
	@ResponseBody
	public PageVo<JobVo> queryPage(@RequestParam JobVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobService.queryJobPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@ResponseBody
	public List<JobVo> queryList(@RequestParam JobVo vo) throws Exception {
		return jobService.queryJobList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@ResponseBody
	public VoidVo insert(@RequestParam JobVo vo) throws Exception {
		jobService.insertJob(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@ResponseBody
	public VoidVo inserts(@RequestParam JobVo[] vos) throws Exception {
		jobService.insertsJob(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public VoidVo update(@RequestParam JobVo vo) throws Exception {
		jobService.updateJob(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updateincludenull")
	@ResponseBody
	public VoidVo updateIncludeNull(@RequestParam JobVo vo) throws Exception {
		jobService.updateJobIncludeNull(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@ResponseBody
	public VoidVo updates(@RequestParam JobVo[] vos) throws Exception {
		jobService.updatesJob(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public VoidVo delete(@RequestParam JobVo vo) throws Exception {
		jobService.deleteJob(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@ResponseBody
	public VoidVo deletes(@RequestParam JobVo[] vos) throws Exception {
		jobService.deletesJob(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/checkcron")
	@ResponseBody
	public ResultVo checkCron(String cron) throws Exception {
		try {
			CronExpression.validateExpression(cron);
			return new ResultVo("true");
		} catch (Exception e) {
			return new ResultVo("false");
		}
	}
	
	@RequestMapping(value = "/checkparentjob")
	@ResponseBody
	public ResultVo checkParentJob(Long jobId) throws Exception {
		JobVo jobVo = new JobVo();
		jobVo.setJob_Id(jobId);
		List<JobVo> jobVoList = jobService.queryJobList(jobVo);
		if (jobVoList == null || jobVoList.size() == 0) {
			return new ResultVo("false");
		}
		return new ResultVo("true");
	}
	
	@RequestMapping(value = "/trigger")
	@ResponseBody
	public ResultVo triggerJob(@RequestParam JobVo jobVo) throws Exception {
		if (executeTrigger.execute(jobVo, Constant.TRIGGER_TYPE_MANUAL)) {
			return new ResultVo("true");
		}
		return new ResultVo("false");
	}
	
	@RequestMapping(value = "/stop")
	@ResponseBody
	public ResultVo stopJob(@RequestParam JobLogVo jobLogVo) throws Exception {
		
		ResultVo resultVoJump = jumpToMaster.stopJobJump(jobLogVo);
		if (resultVoJump != null) {
			return resultVoJump;
		}
		
		if (executeTrigger.stop(jobLogVo.getJob_Id(), jobLogVo.getLog_Id())) {
			if (jobLogVo.getExe_Type() == Constant.TRIGGER_TYPE_ORDINARY || jobLogVo.getExe_Type() == Constant.TRIGGER_TYPE_TIMEOUT_RETRY) {	
				JobVo jobVo = coreService.queryJobById(jobLogVo.getJob_Id());
				if (jobVo != null) {
					if (jobVo.getJob_Type().equals(Constant.JOB_TYPE_COMPLETION)) {
						completionDespatcher.callback(jobLogVo.getJob_Id());
					}
				}
			}
			return new ResultVo("true");
		}
		return new ResultVo("false");
	}
}
