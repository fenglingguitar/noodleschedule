package org.fl.noodleschedule.core.client;

import org.fl.noodleschedule.client.execute.JobClient;

public interface JobClientFactory {
	public JobClient createJobClient(String ip, int port);
	public JobClient createJobClient(String ip, int port, String url);
}
