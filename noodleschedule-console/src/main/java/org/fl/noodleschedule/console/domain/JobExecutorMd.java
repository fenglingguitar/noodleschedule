package org.fl.noodleschedule.console.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JC_JOB_EXECUTOR")
public class JobExecutorMd implements java.io.Serializable {

	private static final long serialVersionUID = -1L;

	private JobMd jobMd;
	private ExecutorMd executorMd;
	private Integer weight;
	private Integer exe_Index;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID") 
	public JobMd getJobMd() {
		return jobMd;
	}
	public void setJobMd(JobMd jobMd) {
		this.jobMd = jobMd;
	}
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXECUTOR_ID", referencedColumnName = "EXECUTOR_ID") 
	public ExecutorMd getExecutorMd() {
		return executorMd;
	}
	public void setExecutorMd(ExecutorMd executorMd) {
		this.executorMd = executorMd;
	}
	
	@Column(name = "WEIGHT", nullable = false)
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	@Column(name = "EXE_INDEX", nullable = false)
	public Integer getExe_Index() {
		return exe_Index;
	}
	public void setExe_Index(Integer exe_Index) {
		this.exe_Index = exe_Index;
	}
}
