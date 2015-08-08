package org.fl.noodleschedule.console.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JC_JOB")
public class JobMd implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long job_Id;
	private String job_Name;
	private String job_Type;
	private String cron;
	private String route_Type;
	private Long exe_Timeout;
	private Integer status;
	private String description;
	private Date lastModified_Time;
	private String method;
	private String param;
	private Integer exe_Status;
	private Integer exe_Retry;
	private Integer timeout_Retry;
	
	private JobMd jobMd;
	
	private Long log_Id;
	
	private Date last_Alarm_Time;
	private Integer is_Alarm;
	private Integer is_Mail_Alarm;
	private Integer is_Sms_Alarm;
	private Long max_Interval_Time;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "JOB_ID", nullable = false)
	public Long getJob_Id() {
		return job_Id;
	}
	public void setJob_Id(Long job_Id) {
		this.job_Id = job_Id;
	}

	@Column(name = "JOB_NAME", nullable = false, length = 32)
	public String getJob_Name() {
		return job_Name;
	}
	public void setJob_Name(String job_Name) {
		this.job_Name = job_Name;
	}
	
	@Column(name = "JOB_TYPE", nullable = false, length = 32)
	public String getJob_Type() {
		return job_Type;
	}
	public void setJob_Type(String job_Type) {
		this.job_Type = job_Type;
	}
	
	@Column(name = "CRON", nullable = true, length = 128)
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	
	@Column(name = "ROUTE_TYPE", nullable = false, length = 32)
	public String getRoute_Type() {
		return route_Type;
	}
	public void setRoute_Type(String route_Type) {
		this.route_Type = route_Type;
	}
	
	@Column(name = "EXE_TIMEOUT", nullable = false)
	public Long getExe_Timeout() {
		return exe_Timeout;
	}
	public void setExe_Timeout(Long exe_Timeout) {
		this.exe_Timeout = exe_Timeout;
	}
	
	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "DESCRIPTION", nullable = true, length = 128)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "LASTMODIFIED_TIME", nullable = false)
	public Date getLastModified_Time() {
		return lastModified_Time;
	}
	public void setLastModified_Time(Date lastModified_Time) {
		this.lastModified_Time = lastModified_Time;
	}
	
	@Column(name = "METHOD", nullable = false, length = 64)
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	@Column(name = "PARAM", nullable = true, length = 512)
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
	@Column(name = "EXE_STATUS", nullable = false)
	public Integer getExe_Status() {
		return exe_Status;
	}
	public void setExe_Status(Integer exe_Status) {
		this.exe_Status = exe_Status;
	}
	
	@Column(name = "EXE_RETRY", nullable = false)
	public Integer getExe_Retry() {
		return exe_Retry;
	}
	public void setExe_Retry(Integer exe_Retry) {
		this.exe_Retry = exe_Retry;
	}

	@Column(name = "TIMEOUT_RETRY", nullable = false)
	public Integer getTimeout_Retry() {
		return timeout_Retry;
	}
	public void setTimeout_Retry(Integer timeout_Retry) {
		this.timeout_Retry = timeout_Retry;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_JOB_ID", referencedColumnName = "JOB_ID") 
	public JobMd getJobMd() {
		return jobMd;
	}
	public void setJobMd(JobMd jobMd) {
		this.jobMd = jobMd;
	}
	
	@Column(name = "LOG_ID", nullable = true)
	public Long getLog_Id() {
		return log_Id;
	}
	public void setLog_Id(Long log_Id) {
		this.log_Id = log_Id;
	}
	
	@Column(name = "LAST_ALARM_TIME", nullable = false)
	public Date getLast_Alarm_Time() {
		return last_Alarm_Time;
	}
	public void setLast_Alarm_Time(Date last_Alarm_Time) {
		this.last_Alarm_Time = last_Alarm_Time;
	}
	
	@Column(name = "IS_ALARM", nullable = false)
	public Integer getIs_Alarm() {
		return is_Alarm;
	}
	public void setIs_Alarm(Integer is_Alarm) {
		this.is_Alarm = is_Alarm;
	}

	@Column(name = "IS_MAIL_ALARM", nullable = false)
	public Integer getIs_Mail_Alarm() {
		return is_Mail_Alarm;
	}
	public void setIs_Mail_Alarm(Integer is_Mail_Alarm) {
		this.is_Mail_Alarm = is_Mail_Alarm;
	}

	@Column(name = "IS_SMS_ALARM", nullable = false)
	public Integer getIs_Sms_Alarm() {
		return is_Sms_Alarm;
	}
	public void setIs_Sms_Alarm(Integer is_Sms_Alarm) {
		this.is_Sms_Alarm = is_Sms_Alarm;
	}

	@Column(name = "MAX_INTERVAL_TIME", nullable = true)
	public Long getMax_Interval_Time() {
		return max_Interval_Time;
	}
	public void setMax_Interval_Time(Long max_Interval_Time) {
		this.max_Interval_Time = max_Interval_Time;
	}
}
