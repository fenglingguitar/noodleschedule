package org.fl.noodleschedule.console.vo;

import java.util.Date;

public class LogVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long log_Id;
	private Date trigger_Time;
	private Date trigger_Time_Start;
	private Date trigger_Time_End;
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

	public Date getTrigger_Time_Start() {
		return trigger_Time_Start;
	}
	public void setTrigger_Time_Start(Date trigger_Time_Start) {
		this.trigger_Time_Start = trigger_Time_Start;
	}
	
	public Date getTrigger_Time_End() {
		return trigger_Time_End;
	}
	public void setTrigger_Time_End(Date trigger_Time_End) {
		this.trigger_Time_End = trigger_Time_End;
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
	
	public Long getJob_Id() {
		return job_Id;
	}
	public void setJob_Id(Long job_Id) {
		this.job_Id = job_Id;
	}
	
	public Long getParent_Log_Id() {
		return parent_Log_Id;
	}
	public void setParent_Log_Id(Long parent_Log_Id) {
		this.parent_Log_Id = parent_Log_Id;
	}
}
