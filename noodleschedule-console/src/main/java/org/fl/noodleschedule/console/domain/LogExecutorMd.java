package org.fl.noodleschedule.console.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "JC_LOG_EXECUTOR")
public class LogExecutorMd implements java.io.Serializable {

	private static final long serialVersionUID = -1L;

	private LogMd logMd;
	private Long executor_Id;
	private Date start_Time;
	private Date end_Time;
	private Long consume_Time;
	private Integer exe_Status;
	private String exception;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOG_ID", referencedColumnName = "LOG_ID") 
	public LogMd getLogMd() {
		return logMd;
	}
	public void setLogMd(LogMd logMd) {
		this.logMd = logMd;
	}
	
	@Id
	@Column(name = "EXECUTOR_ID", nullable = false)
	public Long getExecutor_Id() {
		return executor_Id;
	}
	public void setExecutor_Id(Long executor_Id) {
		this.executor_Id = executor_Id;
	}
	
	@Column(name = "START_TIME", nullable = true)
	public Date getStart_Time() {
		return start_Time;
	}
	public void setStart_Time(Date start_Time) {
		this.start_Time = start_Time;
	}
	
	@Column(name = "END_TIME", nullable = true)
	public Date getEnd_Time() {
		return end_Time;
	}
	public void setEnd_Time(Date end_Time) {
		this.end_Time = end_Time;
	}
	
	@Column(name = "CONSUME_TIME", nullable = true)
	public Long getConsume_Time() {
		return consume_Time;
	}
	public void setConsume_Time(Long consume_Time) {
		this.consume_Time = consume_Time;
	}	
	
	@Column(name = "EXE_STATUS", nullable = false)
	public Integer getExe_Status() {
		return exe_Status;
	}
	public void setExe_Status(Integer exe_Status) {
		this.exe_Status = exe_Status;
	}
	
	@Type(type="text")
	@Column(name = "EXCEPTION", nullable = true, length = 512)
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
}
