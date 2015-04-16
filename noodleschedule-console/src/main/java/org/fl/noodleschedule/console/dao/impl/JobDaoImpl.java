package org.fl.noodleschedule.console.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.fl.noodleschedule.console.dao.JobDao;
import org.fl.noodleschedule.console.domain.JobMd;
import org.fl.noodle.common.dynamicsql.DynamicSqlTemplate;
import org.fl.noodle.common.mvc.vo.PageVo;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.util.common.Constant;

@Repository("jobDao")
public class JobDaoImpl implements JobDao {

	@Autowired
	private DynamicSqlTemplate dynamicSqlTemplate;

	@Override
	public PageVo<JobVo> queryJobPage(JobVo vo, int page, int rows) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id() != null ? vo.getJob_Id() : null);
		paramsMap.put("job_Name", vo.getJob_Name() != null ? (new StringBuilder().append("%").append(vo.getJob_Name()).append("%")).toString() : null);
		paramsMap.put("job_Type", vo.getJob_Type() != null ? vo.getJob_Type() : null);
		paramsMap.put("route_Type", vo.getRoute_Type() != null ? vo.getRoute_Type() : null);
		paramsMap.put("method", vo.getMethod() != null ? vo.getMethod() : null);
		paramsMap.put("exe_Status", vo.getExe_Status() != null ? vo.getExe_Status() : null);
		paramsMap.put("parent_Job_Id", vo.getParent_Job_Id() != null ? vo.getParent_Job_Id() : null);	
		paramsMap.put("status", vo.getStatus() != null ? vo.getStatus() : null);
		paramsMap.put("is_Alarm", vo.getIs_Alarm() != null ? vo.getIs_Alarm() : null);
		paramsMap.put("is_Mail_Alarm", vo.getIs_Mail_Alarm() != null ? vo.getIs_Mail_Alarm() : null);	
		paramsMap.put("is_Sms_Alarm", vo.getIs_Sms_Alarm() != null ? vo.getIs_Sms_Alarm() : null);
		return dynamicSqlTemplate.queryPage("job-query-page", paramsMap, page, rows, JobVo.class);
	}

	@Override
	public List<JobVo> queryJobList(JobVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id() != null ? vo.getJob_Id() : null);
		paramsMap.put("job_Name", vo.getJob_Name() != null ? vo.getJob_Name() : null);
		paramsMap.put("job_Type", vo.getJob_Type() != null ? vo.getJob_Type() : null);
		paramsMap.put("route_Type", vo.getRoute_Type() != null ? vo.getRoute_Type() : null);
		paramsMap.put("method", vo.getMethod() != null ? vo.getMethod() : null);
		paramsMap.put("exe_Status", vo.getExe_Status() != null ? vo.getExe_Status() : null);
		paramsMap.put("parent_Job_Id", vo.getParent_Job_Id() != null ? vo.getParent_Job_Id() : null);	
		paramsMap.put("status", vo.getStatus() != null ? vo.getStatus() : null);
		paramsMap.put("is_Alarm", vo.getIs_Alarm() != null ? vo.getIs_Alarm() : null);
		paramsMap.put("is_Mail_Alarm", vo.getIs_Mail_Alarm() != null ? vo.getIs_Mail_Alarm() : null);	
		paramsMap.put("is_Sms_Alarm", vo.getIs_Sms_Alarm() != null ? vo.getIs_Sms_Alarm() : null);
		return dynamicSqlTemplate.queryList("job-query-list", paramsMap, JobVo.class);
	}

	@Override
	public void insertJob(JobVo vo) throws Exception {
		vo.setExe_Status(Constant.JOB_EXE_STATUS_WAITING);
		vo.setLastModified_Time(new Date());
		vo.setLast_Alarm_Time(new Date());
		vo.setIs_Alarm(Constant.IS_ALARM_NO);
		vo.setIs_Mail_Alarm(Constant.IS_MAIL_ALARM_NO);
		vo.setIs_Sms_Alarm(Constant.IS_SMS_ALARM_NO);
		dynamicSqlTemplate.insert(vo, JobMd.class);
	}

	@Override
	public void insertsJob(JobVo[] vos) throws Exception {
		for (JobVo vo : vos) {
			vo.setExe_Status(Constant.JOB_EXE_STATUS_WAITING);
			vo.setLastModified_Time(new Date());
			vo.setLast_Alarm_Time(new Date());
			vo.setIs_Alarm(Constant.IS_ALARM_NO);
			vo.setIs_Mail_Alarm(Constant.IS_MAIL_ALARM_NO);
			vo.setIs_Sms_Alarm(Constant.IS_SMS_ALARM_NO);
			dynamicSqlTemplate.insert(vo, JobMd.class);
		}
	}

	@Override
	public void updateJob(JobVo vo) throws Exception {
		vo.setLastModified_Time(new Date());
		dynamicSqlTemplate.updateNonull(vo, JobMd.class);
	}
	
	@Override
	public void updateJobIncludeNull(JobVo vo) throws Exception {
		vo.setLastModified_Time(new Date());
		dynamicSqlTemplate.updateNonull(vo, JobMd.class);
		dynamicSqlTemplate.updateInclude(vo, JobMd.class, new String[]{"cron", "delay_Time", "parent_Job_Id", "description"});
	}

	@Override
	public void updatesJob(JobVo[] vos) throws Exception {
		for (JobVo vo : vos) {
			vo.setLastModified_Time(new Date());
			dynamicSqlTemplate.updateNonull(vo, JobMd.class);
		}
	}

	@Override
	public void deleteJob(JobVo vo) throws Exception {
		dynamicSqlTemplate.delete(vo, JobMd.class);
	}

	@Override
	public void deletesJob(JobVo[] vos) throws Exception {
		for (JobVo vo : vos) {
			dynamicSqlTemplate.delete(vo, JobMd.class);
		}
	}

	@Override
	public Integer updateJobExeStatus(JobVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id());
		paramsMap.put("exe_Status_New", vo.getExe_Status_New());
		paramsMap.put("exe_Status_Old", vo.getExe_Status_Old());
		paramsMap.put("log_Id", vo.getLog_Id());
		return dynamicSqlTemplate.updateSql("job-update-exestatus-by-exestatus", paramsMap);
	}

	@Override
	public Integer updateJobExeStatusByLog(JobVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("job_Id", vo.getJob_Id());
		paramsMap.put("exe_Status", vo.getExe_Status());
		paramsMap.put("log_Id", vo.getLog_Id());
		return dynamicSqlTemplate.updateSql("job-update-exestatus-by-log", paramsMap);
	}

	@Override
	public void deleteChildJob(JobVo vo) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("parent_Job_Id", vo.getJob_Id());
		dynamicSqlTemplate.updateSql("job-update-delete-child-job", paramsMap);
	}
}
