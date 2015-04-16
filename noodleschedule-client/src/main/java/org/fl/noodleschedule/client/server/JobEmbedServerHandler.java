package org.fl.noodleschedule.client.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.fl.noodle.common.util.json.JsonTranslator;
import org.fl.noodleschedule.client.execute.JobClient;
import org.fl.noodleschedule.client.pojo.JobParam;
import org.fl.noodleschedule.client.pojo.JobResult;
import org.fl.noodleschedule.util.common.Constant;

public class JobEmbedServerHandler extends AbstractHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(JobEmbedServerHandler.class);
	
	private String url = "/noodleschedule";
	
	private JobClient JobClient;
	
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException {
		response.setContentType("application/x-www-form-urlencoded;charset=utf-8");  
        response.setStatus(HttpServletResponse.SC_OK);  
        baseRequest.setHandled(true);  
		request.setCharacterEncoding("utf-8");
		if (target.equals(url)) {
			doReceive(request, response);
		}
	}

	private void doReceive(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String json = request.getParameter("input");
		
		if (json != null) {
			try {
				JobParam jobParam = JsonTranslator.fromString(json, JobParam.class);
				JobResult jobResult = null;
				
				if (Constant.CLIENT_ACTION_TYPE_TRIGGER.equals(jobParam.getActionType())) {
					jobResult = JobClient.triggerJob(jobParam);
				} else if (Constant.CLIENT_ACTION_TYPE_CHECK.equals(jobParam.getActionType())) {
					jobResult = JobClient.checkJobStatus(jobParam);
				} else if (Constant.CLIENT_ACTION_TYPE_STOP.equals(jobParam.getActionType())) {
					jobResult = JobClient.stopJob(jobParam);
				} else {
					jobResult = new JobResult(jobParam.getLogId(), Constant.RETURN_INVALID, Constant.EXCEPTION_NO_ACTION_TYPE);
				}
				response.getWriter().print(JsonTranslator.toString(jobResult));
			} catch (Exception receiveException) {
				if (logger.isErrorEnabled()) {
					logger.error("DoReceive -> ObjectJsonTranslator fromString -> josn format error -> Exception: {}", receiveException);
				}
				try {
					response.getWriter().print(JsonTranslator.toString(new JobResult(Constant.RETURN_INVALID, Constant.EXCEPTION_READ_JSON_FAIL)));
				} catch (Exception e) {
					if (logger.isErrorEnabled()) {
						logger.error("DoReceive -> ObjectJsonTranslator fromString -> ObjectJsonTranslator toString -> josn tostring and print 'josn format error' exception fail -> Exception: {}", e);
					}
				}
			}
		} else {
			if (logger.isErrorEnabled()) {
				logger.error("DoReceive -> request getParameter -> input is null");
			}
			try {
				response.getWriter().print(JsonTranslator.toString(new JobResult(Constant.RETURN_INVALID, Constant.EXCEPTION_URL_PARAM_NULL)));
			} catch (Exception e) {
				if (logger.isErrorEnabled()) {
					logger.error("DoReceive -> request getParameter -> ObjectJsonTranslator toString -> josn tostring and print 'input is null' exception fail -> Exception: {}", e);
				}
			}
		}
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setJobClient(JobClient jobClient) {
		JobClient = jobClient;
	}
}
