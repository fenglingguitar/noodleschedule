package org.fl.noodleschedule.client.execute;

import org.fl.noodleschedule.client.pojo.JobParam;
import org.fl.noodleschedule.client.pojo.JobResult;

public interface JobClient {
	
	public JobResult triggerJob(JobParam jobParam);
	public JobResult checkJobStatus(JobParam jobParam);
	public JobResult stopJob(JobParam jobParam);
}
