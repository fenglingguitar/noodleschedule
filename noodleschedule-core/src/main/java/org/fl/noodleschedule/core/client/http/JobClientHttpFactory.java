package org.fl.noodleschedule.core.client.http;

import org.fl.noodleschedule.client.execute.JobClient;
import org.fl.noodleschedule.core.client.JobClientFactory;

public class JobClientHttpFactory implements JobClientFactory {

	private int connectTimeout = 10000;
	private int readTimeout = 10000;
	private String url = "/noodleschedule";

	@Override
	public JobClient createJobClient(String ip, int port) {
		return new JobClientHttp(ip, port, url, connectTimeout, readTimeout);
	}

	@Override
	public JobClient createJobClient(String ip, int port, String url) {
		return new JobClientHttp(ip, port, url, connectTimeout, readTimeout);
	}
	
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
