package org.fl.noodleschedule.console.web.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.fl.noodle.common.mvc.annotation.NoodleRequestParam;
import org.fl.noodle.common.mvc.annotation.NoodleResponseBody;
import org.fl.noodleschedule.client.callback.JobCallback;
import org.fl.noodleschedule.client.pojo.JobResult;

@Controller
public class CallbackController {
	
	@Autowired(required = false)
	private JobCallback jobCallback;
	
	@RequestMapping(value = "/callback")
	@NoodleResponseBody
	public JobResult callback(@NoodleRequestParam JobResult jobResult) throws Exception {
		
		return jobCallback.callback(jobResult);
	}
}
