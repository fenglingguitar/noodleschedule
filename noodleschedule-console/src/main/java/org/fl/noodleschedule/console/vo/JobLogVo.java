package org.fl.noodleschedule.console.vo;

import java.util.Date;

public class JobLogVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long job_Id;
	private String job_Name;
	private String job_Type;
	private String cron;
	private Long delay_Time;
	private String route_Type;
	private Long exe_Timeout;
	private String description;
	private Date lastModified_Time;
	private String method;
	private Integer exe_Status_Job;
	private Integer exe_Retry;
	private Integer timeout_Retry;
	private Long parent_Job_Id;
	
	private Long log_Id;
	private Date trigger_Time;
	private Date start_Time;
	private Date end_Time;
	private Long consume_Time;
	private Integer exe_Status_Log;
	private Integer plan_Num;
	private Integer run_Num;
	private Integer complete_Num;
	private String exception; 
	private Integer timeout_Status;
	private Integer exe_Type;
	
	private Long parent_Log_Id;
	
	public Long getJob_Id() {
		return job_Id;
	}
	public void setJob_Id(Long job_Id) {
		this.job_Id = job_Id;
	}
	
	public String getJob_Name() {
		return job_Name;
	}
	public void setJob_Name(String job_Name) {
		this.job_Name = job_Name;
	}
	
	public String getJob_Type() {
		return job_Type;
	}
	public void setJob_Type(String job_Type) {
		this.job_Type = job_Type;
	}
	
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	
	public Long getDelay_Time() {
		return delay_Time;
	}
	public void setDelay_Time(Long delay_Time) {
		this.delay_Time = delay_Time;
	}
	
	public String getRoute_Type() {
		return route_Type;
	}
	public void setRoute_Type(String route_Type) {
		this.route_Type = route_Type;
	}
	
	public Long getExe_Timeout() {
		return exe_Timeout;
	}
	public void setExe_Timeout(Long exe_Timeout) {
		this.exe_Timeout = exe_Timeout;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getLastModified_Time() {
		return lastModified_Time;
	}
	public void setLastModified_Time(Date lastModified_Time) {
		this.lastModified_Time = lastModified_Time;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public Integer getExe_Status_Job() {
		return exe_Status_Job;
	}
	public void setExe_Status_Job(Integer exe_Status_Job) {
		this.exe_Status_Job = exe_Status_Job;
	}
	
	public Integer getExe_Retry() {
		return exe_Retry;
	}
	public void setExe_Retry(Integer exe_Retry) {
		this.exe_Retry = exe_Retry;
	}
	
	public Integer getTimeout_Retry() {
		return timeout_Retry;
	}
	public void setTimeout_Retry(Integer timeout_Retry) {
		this.timeout_Retry = timeout_Retry;
	}
	
	public Long getParent_Job_Id() {
		return parent_Job_Id;
	}
	public void setParent_Job_Id(Long parent_Job_Id) {
		this.parent_Job_Id = parent_Job_Id;
	}
	
	public Long getLog_Id() {
		return log_Id;
	}
	public void setLog_Id(Long log_Id) {
		this.log_Id = log_Id;
	}
	
	public Date getTrigger_Time() {
		return trigger_Time;
	}
	public void setTrigger_Time(Date trigger_Time) {
		this.trigger_Time = trigger_Time;
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

	public Integer getExe_Status_Log() {
		return exe_Status_Log;
	}
	public void setExe_Status_Log(Integer exe_Status_Log) {
		this.exe_Status_Log = exe_Status_Log;
	}

	public Integer getPlan_Num() {
		return plan_Num;
	}
	public void setPlan_Num(Integer plan_Num) {
		this.plan_Num = plan_Num;
	}
	
	public Integer getRun_Num() {
		return run_Num;
	}
	public void setRun_Num(Integer run_Num) {
		this.run_Num = run_Num;
	}

	public Integer getComplete_Num() {
		return complete_Num;
	}
	public void setComplete_Num(Integer complete_Num) {
		this.complete_Num = complete_Num;
	}
	
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}

	public Integer getTimeout_Status() {
		return timeout_Status;
	}
	public void setTimeout_Status(Integer timeout_Status) {
		this.timeout_Status = timeout_Status;
	} 
	
	public Integer getExe_Type() {
		return exe_Type;
	}
	public void setExe_Type(Integer exe_Type) {
		this.exe_Type = exe_Type;
	}
	
	public Long getParent_Log_Id() {
		return parent_Log_Id;
	}
	public void setParent_Log_Id(Long parent_Log_Id) {
		this.parent_Log_Id = parent_Log_Id;
	}
}
