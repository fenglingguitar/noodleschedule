package org.fl.noodleschedule.console.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JC_LOCK")
public class LockMd implements java.io.Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long overtime;
	private Long set_Id;
	private String ip;
	
	@Id
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "OVERTIME", nullable = false)
	public Long getOvertime() {
		return overtime;
	}
	public void setOvertime(Long overtime) {
		this.overtime = overtime;
	}
	
	@Column(name = "SET_ID", nullable = false)
	public Long getSet_Id() {
		return set_Id;
	}
	public void setSet_Id(Long set_Id) {
		this.set_Id = set_Id;
	}
	
	@Column(name = "IP", nullable = true, length = 16)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
