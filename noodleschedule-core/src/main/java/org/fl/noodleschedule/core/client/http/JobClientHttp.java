package org.fl.noodleschedule.core.client.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fl.noodle.common.net.http.HttpConnect;
import org.fl.noodle.common.net.http.jdk.HttpConnectJdk;
import org.fl.noodleschedule.client.execute.JobClient;
import org.fl.noodleschedule.client.pojo.JobParam;
import org.fl.noodleschedule.client.pojo.JobResult;
import org.fl.noodleschedule.util.common.Constant;

public class JobClientHttp implements JobClient {

	private Logger logger = LoggerFactory.getLogger(JobClientHttp.class);
	
	private HttpConnect httpConnect;
	
	private String url;
	
	public JobClientHttp(String ip, int port, String url, int connectTimeout, int readTimeout) {
		this.url = new StringBuilder("http://").append(ip).append(":").append(port).append(url).toString();
		httpConnect = new HttpConnectJdk(this.url, connectTimeout, readTimeout);
	}
	
	@Override
	public JobResult triggerJob(JobParam jobParam) {
		jobParam.setActionType(Constant.CLIENT_ACTION_TYPE_TRIGGER);
		return send(jobParam);
	}

	@Override
	public JobResult checkJobStatus(JobParam jobParam) {
		jobParam.setActionType(Constant.CLIENT_ACTION_TYPE_CHECK);
		return send(jobParam);
	}

	@Override
	public JobResult stopJob(JobParam jobParam) {
		jobParam.setActionType(Constant.CLIENT_ACTION_TYPE_STOP);
		return send(jobParam);
	}
	
	private JobResult send(JobParam jobParam) {
		try {
			JobResult jobResult = httpConnect.post("input", jobParam, JobResult.class);
			jobResult.setResult(true);
			return jobResult;
		} catch (Exception e) {
			logger.error("send -> httpConnect.send -> url: {} -> Exception: {}", url, e.toString());
			return new JobResult(false, e.getMessage());
		}
	}

	public void setHttpConnect(HttpConnect httpConnect) {
		this.httpConnect = httpConnect;
	}
}
