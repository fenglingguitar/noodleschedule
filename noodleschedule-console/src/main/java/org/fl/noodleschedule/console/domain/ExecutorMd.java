package org.fl.noodleschedule.console.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JC_EXECUTOR")
public class ExecutorMd implements java.io.Serializable {

	private static final long serialVersionUID = -1L;

	private Long executor_Id;
	private String executor_Name;
	private String rpc_Type;
	private String ip;
	private Integer port;
	private String url;
	private Integer status;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "EXECUTOR_ID", nullable = false)
	public Long getExecutor_Id() {
		return executor_Id;
	}
	public void setExecutor_Id(Long executor_Id) {
		this.executor_Id = executor_Id;
	}
	
	@Column(name = "EXECUTOR_NAME", nullable = false, length = 32)
	public String getExecutor_Name() {
		return executor_Name;
	}
	public void setExecutor_Name(String executor_Name) {
		this.executor_Name = executor_Name;
	}
	
	@Column(name = "RPC_TYPE", nullable = false, length = 8)
	public String getRpc_Type() {
		return rpc_Type;
	}
	public void setRpc_Type(String rpc_Type) {
		this.rpc_Type = rpc_Type;
	}
	
	@Column(name = "IP", nullable = false, length = 128)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name = "PORT", nullable = false)
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	@Column(name = "URL", nullable = false, length = 128)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}	
}
