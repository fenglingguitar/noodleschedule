package org.fl.noodleschedule.console.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JC_JOB_ALARMPEOPLE")
public class JobAlarmPeopleMd implements java.io.Serializable {

	private static final long serialVersionUID = -1L;

	private JobMd jobMd;
	private AlarmPeopleMd alarmPeopleMd;
	
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
	@JoinColumn(name = "ALARMPEOPLE_ID", referencedColumnName = "ALARMPEOPLE_ID") 
	public AlarmPeopleMd getAlarmPeopleMd() {
		return alarmPeopleMd;
	}
	public void setAlarmPeopleMd(AlarmPeopleMd alarmPeopleMd) {
		this.alarmPeopleMd = alarmPeopleMd;
	}
}
