package org.fl.noodleschedule.client.run;

public interface JobRunnable extends Runnable {
	public long getLogId();
	public void setLogId(long logId);
	public long getExecutorId();
	public void setExecutorId(long executorId);
	public String getParam();
	public void setParam(String param);
}
