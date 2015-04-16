package org.fl.noodleschedule.client.callback;

import org.fl.noodle.common.net.http.HttpConnect;
import org.fl.noodle.common.net.http.jdk.HttpConnectJdk;
import org.fl.noodleschedule.client.pojo.JobResult;

public class JobCallbackImpl implements JobCallback {

	private String url;
	private int connectTimeout = 10000;
	private int readTimeout = 10000;
	
	@Override
	public JobResult callback(JobResult jobResult) {
		HttpConnect httpConnect = new HttpConnectJdk(url, connectTimeout, readTimeout);
		try {
			JobResult jobResultBack = httpConnect.post("input", jobResult, JobResult.class);
			jobResultBack.setResult(true);
			return jobResultBack;
		} catch (Exception e) {
			return new JobResult(false, e.getMessage());
		}
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
}
