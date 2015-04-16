package org.fl.noodleschedule.console.vo;

public class LockVo implements java.io.Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long overtime;
	private Long set_Id;
	private String ip;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getOvertime() {
		return overtime;
	}
	public void setOvertime(Long overtime) {
		this.overtime = overtime;
	}
	
	public Long getSet_Id() {
		return set_Id;
	}
	public void setSet_Id(Long set_Id) {
		this.set_Id = set_Id;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
