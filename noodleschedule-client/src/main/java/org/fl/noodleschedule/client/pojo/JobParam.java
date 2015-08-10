package org.fl.noodleschedule.client.pojo;

public class JobParam implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private long logId;
	private long executorId;
	private String method;
	private String param;
	private String actionType;
	
	public JobParam() {
	}
	
	public JobParam(String method) {
		this.method = method;
	}
	
	public JobParam(long logId, long executorId, String method) {
		this.logId = logId;
		this.executorId = executorId;
		this.method = method;
	}
	
	public JobParam(long logId, long executorId, String method, String param) {
		this.logId = logId;
		this.executorId = executorId;
		this.method = method;
		this.param = param;
	}
	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	
	public long getExecutorId() {
		return executorId;
	}
	public void setExecutorId(long executorId) {
		this.executorId = executorId;
	}

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}

	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}
