package org.fl.noodleschedule.console.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "JC_LOG")
public class LogMd implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long log_Id;
	private Date trigger_Time;
	private Date start_Time;
	private Date end_Time;
	private Long consume_Time;
	private Integer exe_Status;
	private Integer plan_Num;
	private Integer run_Num;
	private Integer complete_Num;
	private String exception; 
	private Integer timeout_Status;
	private Integer exe_Type;
	
	private Long job_Id;
	
	private Long parent_Log_Id;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "LOG_ID", nullable = false)
	public Long getLog_Id() {
		return log_Id;
	}
	public void setLog_Id(Long log_Id) {
		this.log_Id = log_Id;
	}

	@Column(name = "TRIGGER_TIME", nullable = false)
	public Date getTrigger_Time() {
		return trigger_Time;
	}
	public void setTrigger_Time(Date trigger_Time) {
		this.trigger_Time = trigger_Time;
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

	@Column(name = "PLAN_NUM", nullable = true)
	public Integer getPlan_Num() {
		return plan_Num;
	}
	public void setPlan_Num(Integer plan_Num) {
		this.plan_Num = plan_Num;
	}
	
	@Column(name = "RUN_NUM", nullable = true)
	public Integer getRun_Num() {
		return run_Num;
	}
	public void setRun_Num(Integer run_Num) {
		this.run_Num = run_Num;
	}

	@Column(name = "COMPLETE_NUM", nullable = true)
	public Integer getComplete_Num() {
		return complete_Num;
	}
	public void setComplete_Num(Integer complete_Num) {
		this.complete_Num = complete_Num;
	}

	@Type(type="text")
	@Column(name = "EXCEPTION", nullable = true, length = 512)
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}

	@Column(name = "TIMEOUT_STATUS", nullable = true)
	public Integer getTimeout_Status() {
		return timeout_Status;
	}
	public void setTimeout_Status(Integer timeout_Status) {
		this.timeout_Status = timeout_Status;
	}
	
	@Column(name = "EXE_TYPE", nullable = false)
	public Integer getExe_Type() {
		return exe_Type;
	}
	public void setExe_Type(Integer exe_Type) {
		this.exe_Type = exe_Type;
	}
	
	@Column(name = "JOB_ID", nullable = false)
	public Long getJob_Id() {
		return job_Id;
	}
	public void setJob_Id(Long job_Id) {
		this.job_Id = job_Id;
	}
	
	@Column(name = "PARENT_LOG_ID", nullable = true)
	public Long getParent_Log_Id() {
		return parent_Log_Id;
	}
	public void setParent_Log_Id(Long parent_Log_Id) {
		this.parent_Log_Id = parent_Log_Id;
	}
}
