package org.fl.noodleschedule.console.web.controller.console;

import java.util.List;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.fl.noodleschedule.console.service.CoreService;
import org.fl.noodleschedule.console.service.JobService;
import org.fl.noodle.common.mvc.annotation.NoodleRequestParam;
import org.fl.noodle.common.mvc.annotation.NoodleResponseBody;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodle.common.mvc.vo.ResultVo;
import org.fl.noodle.common.mvc.vo.VoidVo;
import org.fl.noodleschedule.console.vo.JobLogVo;
import org.fl.noodleschedule.console.vo.JobVo;
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
	private CoreService coreService;
	
	@RequestMapping(value = "/querypage")
	@NoodleResponseBody
	public PageVo<JobVo> queryPage(@NoodleRequestParam JobVo vo, String page, String rows) throws Exception {
		page = page != null && !page.equals("") ? page : "0";
		rows = rows != null && !rows.equals("") ? rows : "0";
		return jobService.queryJobPage(vo, Integer.parseInt(page), Integer.parseInt(rows));
	}
	
	@RequestMapping(value = "/querylist")
	@NoodleResponseBody
	public List<JobVo> queryList(@NoodleRequestParam JobVo vo) throws Exception {
		return jobService.queryJobList(vo);
	}
	
	@RequestMapping(value = "/insert")
	@NoodleResponseBody
	public VoidVo insert(@NoodleRequestParam JobVo vo) throws Exception {
		jobService.insertJob(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/inserts")
	@NoodleResponseBody
	public VoidVo inserts(@NoodleRequestParam JobVo[] vos) throws Exception {
		jobService.insertsJob(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/update")
	@NoodleResponseBody
	public VoidVo update(@NoodleRequestParam JobVo vo) throws Exception {
		jobService.updateJob(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updateincludenull")
	@NoodleResponseBody
	public VoidVo updateIncludeNull(@NoodleRequestParam JobVo vo) throws Exception {
		jobService.updateJobIncludeNull(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/updates")
	@NoodleResponseBody
	public VoidVo updates(@NoodleRequestParam JobVo[] vos) throws Exception {
		jobService.updatesJob(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/delete")
	@NoodleResponseBody
	public VoidVo delete(@NoodleRequestParam JobVo vo) throws Exception {
		jobService.deleteJob(vo);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/deletes")
	@NoodleResponseBody
	public VoidVo deletes(@NoodleRequestParam JobVo[] vos) throws Exception {
		jobService.deletesJob(vos);
		return VoidVo.VOID;
	}
	
	@RequestMapping(value = "/checkcron")
	@NoodleResponseBody
	public ResultVo checkCron(String cron) throws Exception {
		try {
			CronExpression.validateExpression(cron);
			return new ResultVo("true");
		} catch (Exception e) {
			return new ResultVo("false");
		}
	}
	
	@RequestMapping(value = "/checkparentjob")
	@NoodleResponseBody
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
	@NoodleResponseBody
	public ResultVo triggerJob(@NoodleRequestParam JobVo jobVo) throws Exception {
		if (executeTrigger.execute(jobVo, Constant.TRIGGER_TYPE_MANUAL)) {
			return new ResultVo("true");
		}
		return new ResultVo("false");
	}
	
	@RequestMapping(value = "/stop")
	@NoodleResponseBody
	public ResultVo stopJob(@NoodleRequestParam JobLogVo jobLogVo) throws Exception {
		
		if (executeTrigger.stop(jobLogVo.getJob_Id(), jobLogVo.getLog_Id())) {
			return new ResultVo("true");
		}
		return new ResultVo("false");
	}
}
