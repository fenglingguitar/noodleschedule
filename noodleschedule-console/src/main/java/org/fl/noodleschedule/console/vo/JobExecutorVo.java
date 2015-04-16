package org.fl.noodleschedule.console.vo;

public class JobExecutorVo implements java.io.Serializable {

	private static final long serialVersionUID = -1L;

	private Long job_Id;
	private Long executor_Id;
	private Integer weight;
	private Integer exe_Index;
	
	private String executor_Name;
	private String rpc_Type;
	private String ip;
	private Integer port;
	private String url;
	private Integer status;
	
	public Long getJob_Id() {
		return job_Id;
	}
	public void setJob_Id(Long job_Id) {
		this.job_Id = job_Id;
	}
	
	public Long getExecutor_Id() {
		return executor_Id;
	}
	public void setExecutor_Id(Long executor_Id) {
		this.executor_Id = executor_Id;
	}

	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public Integer getExe_Index() {
		return exe_Index;
	}
	public void setExe_Index(Integer exe_Index) {
		this.exe_Index = exe_Index;
	}
	
	public String getExecutor_Name() {
		return executor_Name;
	}
	public void setExecutor_Name(String executor_Name) {
		this.executor_Name = executor_Name;
	}
	
	public String getRpc_Type() {
		return rpc_Type;
	}
	public void setRpc_Type(String rpc_Type) {
		this.rpc_Type = rpc_Type;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Override
	public boolean equals(Object o) {
		JobExecutorVo other = (JobExecutorVo) o;
		return other.getExecutor_Id() == this.getExecutor_Id();
	}
}
