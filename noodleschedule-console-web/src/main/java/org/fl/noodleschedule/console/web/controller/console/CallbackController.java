package org.fl.noodleschedule.console.web.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.fl.noodleschedule.client.callback.JobCallback;
import org.fl.noodleschedule.client.pojo.JobResult;
import org.fl.noodleschedule.console.web.jump.JumpToMaster;
import org.fl.noodleschedule.console.web.mvc.annotation.RequestParam;
import org.fl.noodleschedule.console.web.mvc.annotation.ResponseBody;

@Controller
public class CallbackController {
	
	@Autowired(required = false)
	private JobCallback jobCallback;
	
	@Autowired
	private JumpToMaster jumpToMaster;
	
	@RequestMapping(value = "/callback")
	@ResponseBody
	public JobResult callback(@RequestParam JobResult jobResult) throws Exception {
		
		JobResult jobResultJump = jumpToMaster.callbackJump(jobResult);
		if (jobResultJump != null) {
			return jobResultJump;
		}
		
		return jobCallback.callback(jobResult);
	}
}
