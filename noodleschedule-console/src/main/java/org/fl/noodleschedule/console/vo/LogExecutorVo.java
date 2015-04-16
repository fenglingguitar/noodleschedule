package org.fl.noodleschedule.console.vo;

import java.util.Date;

public class LogExecutorVo implements java.io.Serializable {

	private static final long serialVersionUID = -1L;

	private Long log_Id;
	private Long executor_Id;
	private Date start_Time;
	private Date end_Time;
	private Long consume_Time;
	private Integer exe_Status;
	private String exception;
	
	private String executor_Name;
	private String rpc_Type;
	private String ip;
	private int port;
	private String url;
	private Integer status;
	
	public Long getLog_Id() {
		return log_Id;
	}
	public void setLog_Id(Long log_Id) {
		this.log_Id = log_Id;
	}
	
	public Long getExecutor_Id() {
		return executor_Id;
	}
	public void setExecutor_Id(Long executor_Id) {
		this.executor_Id = executor_Id;
	}
	
	public Date getStart_Time() {
		return start_Time;
	}
	public void setStart_Time(Date start_Time) {
		this.start_Time = start_Time;
	}
	
	public Date getEnd_Time() {
		return end_Time;
	}
	public void setEnd_Time(Date end_Time) {
		this.end_Time = end_Time;
	}
	
	public Long getConsume_Time() {
		return consume_Time;
	}
	public void setConsume_Time(Long consume_Time) {
		this.consume_Time = consume_Time;
	}	
	
	public Integer getExe_Status() {
		return exe_Status;
	}
	public void setExe_Status(Integer exe_Status) {
		this.exe_Status = exe_Status;
	}
	
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	
	public String getExecutor_Name() {
		return executor_Name;
	}
	public void setExecutor_Name(String executor_Name) {
		this.executor_Name = executor_Name;
	}
	
	public String getRpc_Type() {
		return rpc_Type;
	}
	public void setRpc_Type(String rpc_Type) {
		this.rpc_Type = rpc_Type;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
