package org.fl.noodleschedule.alarm.pojo;

public class AlarmInfo {
	
	private long jobId;
	
	private String title;
	private String summary;
	private String content;
	
	public AlarmInfo() {	
	}
		
	public AlarmInfo(long jobId, String title, String summary, String content) {
		this.jobId = jobId;
		this.title = title;
		this.summary = summary;
		this.content = content;
	}
	
	public long getJobId() {
		return jobId;
	}
	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
