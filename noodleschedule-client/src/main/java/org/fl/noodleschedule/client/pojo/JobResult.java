package org.fl.noodleschedule.client.pojo;

public class JobResult implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean result;
	private long logId;
	private long executorId;
	private int code;
	private String exception;
	
	public JobResult() {
	}
	
	public JobResult(boolean result) {
		this.result = result;
	}
	
	public JobResult(boolean result, String exception) {
		this.result = result;
		this.exception = exception;
	}
	
	public JobResult(long logId, int code) {
		this.result = true;
		this.logId = logId;
		this.code = code;
	}
	
	public JobResult(long logId, long executorId, int code) {
		this.result = true;
		this.logId = logId;
		this.executorId = executorId;
		this.code = code;
	}
	
	public JobResult(long logId, long executorId, int code, String exception) {
		this.result = true;
		this.logId = logId;
		this.executorId = executorId;
		this.code = code;
		this.exception = exception;
	}
	
	public JobResult(long logId, int code, String exception) {
		this.result = true;
		this.logId = logId;
		this.code = code;
		this.exception = exception;
	}
	
	public JobResult(int code) {
		this.result = true;
		this.code = code;
	}
	
	public JobResult(int code, String exception) {
		this.result = true;
		this.code = code;
		this.exception = exception;
	}
	
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public long getLogId() {
		return logId;
	}
	public void setLogId(long logId) {
		this.logId = logId;
	}

	public long getExecutorId() {
		return executorId;
	}
	public void setExecutorId(long executorId) {
		this.executorId = executorId;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}	
}
