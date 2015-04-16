package org.fl.noodleschedule.console.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JC_ALARMPEOPLE")
public class AlarmPeopleMd implements java.io.Serializable {

	private static final long serialVersionUID = -1L;

	private Long alarmPeople_Id;
	private String alarmPeople_Name;
	private String email;
	private String phone;
	private Integer status;
	
	@Id
	@Column(name = "ALARMPEOPLE_ID", nullable = false)
	public Long getAlarmPeople_Id() {
		return alarmPeople_Id;
	}
	public void setAlarmPeople_Id(Long alarmPeople_Id) {
		this.alarmPeople_Id = alarmPeople_Id;
	}
	
	@Column(name = "ALARMPEOPLE_NAME", nullable = false, length = 32)
	public String getAlarmPeople_Name() {
		return alarmPeople_Name;
	}
	public void setAlarmPeople_Name(String alarmPeople_Name) {
		this.alarmPeople_Name = alarmPeople_Name;
	}
	
	@Column(name = "EMAIL", nullable = false, length = 128)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "PHONE", nullable = false, length = 13)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
