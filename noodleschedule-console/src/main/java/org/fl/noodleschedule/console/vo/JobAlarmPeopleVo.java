package org.fl.noodleschedule.console.vo;

public class JobAlarmPeopleVo implements java.io.Serializable {

	private static final long serialVersionUID = -1L;

	private Long job_Id;
	private Long alarmPeople_Id;
	private String alarmPeople_Name;
	private String email;
	private String phone;
	private Integer status;
	
	public Long getJob_Id() {
		return job_Id;
	}
	public void setJob_Id(Long job_Id) {
		this.job_Id = job_Id;
	}
	
	public Long getAlarmPeople_Id() {
		return alarmPeople_Id;
	}
	public void setAlarmPeople_Id(Long alarmPeople_Id) {
		this.alarmPeople_Id = alarmPeople_Id;
	}
	
	public String getAlarmPeople_Name() {
		return alarmPeople_Name;
	}
	public void setAlarmPeople_Name(String alarmPeople_Name) {
		this.alarmPeople_Name = alarmPeople_Name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
