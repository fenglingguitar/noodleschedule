package org.fl.noodleschedule.client.callback;

import org.fl.noodleschedule.client.pojo.JobResult;

public interface JobCallback {
	public JobResult callback(JobResult jobResult);
}
