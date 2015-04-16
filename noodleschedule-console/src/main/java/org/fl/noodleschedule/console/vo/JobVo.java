package org.fl.noodleschedule.console.vo;

import java.util.Date;

public class JobVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long job_Id;
	private String job_Name;
	private String job_Type;
	private String cron;
	private Long delay_Time;
	private String route_Type;
	private Long exe_Timeout;
	private Integer status;
	private String description;
	private Date lastModified_Time;
	private String method;
	private Integer exe_Status;
	private Integer exe_Status_New;
	private Integer exe_Status_Old;
	private Integer exe_Retry;
	private Integer timeout_Retry;
	private Long parent_Job_Id;
	
	private Long log_Id;
	
	private Date last_Alarm_Time;
	private Integer is_Alarm;
	private Integer is_Mail_Alarm;
	private Integer is_Sms_Alarm;
	private Long max_Interval_Time;
	
	private long lastExecutorId;
	private int result;
	private int exeType;
	
	public JobVo() {
	}
	
	public JobVo(Long job_Id) {
		this.job_Id = job_Id;
	}
	
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
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
	public Integer getExe_Status() {
		return exe_Status;
	}
	public void setExe_Status(Integer exe_Status) {
		this.exe_Status = exe_Status;
	}

	public Integer getExe_Status_New() {
		return exe_Status_New;
	}
	public void setExe_Status_New(Integer exe_Status_New) {
		this.exe_Status_New = exe_Status_New;
	}

	public Integer getExe_Status_Old() {
		return exe_Status_Old;
	}
	public void setExe_Status_Old(Integer exe_Status_Old) {
		this.exe_Status_Old = exe_Status_Old;
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
	
	public long getLastExecutorId() {
		return lastExecutorId;
	}
	public void setLastExecutorId(long lastExecutorId) {
		this.lastExecutorId = lastExecutorId;
	}
	
	public Date getLast_Alarm_Time() {
		return last_Alarm_Time;
	}
	public void setLast_Alarm_Time(Date last_Alarm_Time) {
		this.last_Alarm_Time = last_Alarm_Time;
	}
	
	public Integer getIs_Alarm() {
		return is_Alarm;
	}
	public void setIs_Alarm(Integer is_Alarm) {
		this.is_Alarm = is_Alarm;
	}

	public Integer getIs_Mail_Alarm() {
		return is_Mail_Alarm;
	}
	public void setIs_Mail_Alarm(Integer is_Mail_Alarm) {
		this.is_Mail_Alarm = is_Mail_Alarm;
	}

	public Integer getIs_Sms_Alarm() {
		return is_Sms_Alarm;
	}
	public void setIs_Sms_Alarm(Integer is_Sms_Alarm) {
		this.is_Sms_Alarm = is_Sms_Alarm;
	}

	public Long getMax_Interval_Time() {
		return max_Interval_Time;
	}
	public void setMax_Interval_Time(Long max_Interval_Time) {
		this.max_Interval_Time = max_Interval_Time;
	}
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	public int getExeType() {
		return exeType;
	}
	public void setExeType(int exeType) {
		this.exeType = exeType;
	}
	
	public String toString() {
		return (new StringBuilder())
				.append("jobId: ").append(job_Id).append(", ")
				.append("jobName: ").append(job_Name).append(", ")
				.append("jobType: ").append(job_Type).append(", ")
				.append("cron: ").append(cron).append(", ")
				.append("delayTime: ").append(delay_Time).append(", ")
				.append("routeType: ").append(route_Type).append(", ")
				.append("exeTimeout: ").append(exe_Timeout).append(", ")
				.append("lastModifiedTime: ").append(lastModified_Time).append(", ")
				.append("method: ").append(method).append(", ")
				.append("exeRetry: ").append(exe_Retry).append(", ")
				.append("timeoutRetry: ").append(timeout_Retry).append(", ")
				.append("parentJobId: ").append(parent_Job_Id).toString();
	}
}
