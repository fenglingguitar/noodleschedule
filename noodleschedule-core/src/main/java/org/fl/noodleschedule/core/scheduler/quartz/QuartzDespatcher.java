package org.fl.noodleschedule.core.scheduler.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fl.noodleschedule.console.system.jobs.ExecuteSystem;
import org.fl.noodleschedule.console.vo.JobVo;
import org.fl.noodleschedule.core.scheduler.AbstractDespatcher;
import org.fl.noodleschedule.core.trigger.ExecuteTrigger;
import org.fl.noodleschedule.util.common.Constant;

public class QuartzDespatcher extends AbstractDespatcher {

	private final static Logger logger = LoggerFactory.getLogger(QuartzDespatcher.class);
			
	private Scheduler scheduler;
	private ExecuteTrigger executeTrigger;
	
	public void start() throws Exception {
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
	}
	
	public void destroy() {	
		try {
			scheduler.clear();
		} catch (SchedulerException e) {
			logger.error("destroy -> scheduler clear -> SchedulerException: {}", e);
		}
		try {
			scheduler.shutdown(true);
		} catch (SchedulerException e) {
			logger.error("destroy -> scheduler shutdown -> SchedulerException: {}", e);
		}
	}
	
	@Override
	protected boolean doAddJob(JobVo jobVo) {
		
		JobDetail jobDetail = JobBuilder.newJob(ExecuteTriggerJob.class)
				.withIdentity(String.valueOf(jobVo.getJob_Id()), Scheduler.DEFAULT_GROUP).build();
		
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
    	jobDataMap.put("jobVo", jobVo);
    	jobDataMap.put("executeTrigger", executeTrigger);

		CronTrigger trigger = (CronTrigger) TriggerBuilder
				.newTrigger()
				.withIdentity(String.valueOf(jobVo.getJob_Id()), Scheduler.DEFAULT_GROUP)
				.withSchedule(
						CronScheduleBuilder.cronSchedule(jobVo.getCron()))
				.build();

		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			logger.error("doAddJob -> scheduler scheduleJob -> jobId: {} -> SchedulerException: {}", jobVo.getJob_Id(), e);
			return false;
		}
		return true;
	}

	@Override
	protected boolean doRemoveJob(long jobId) {
		try {
			scheduler.deleteJob(new JobKey(String.valueOf(jobId)));
		} catch (SchedulerException e) {
			logger.error("doRemoveJob -> scheduler deleteJob -> jobId: {} -> SchedulerException: {}", jobId, e);
			return false;
		}
		return true;
	}

	@Override
	protected boolean doChangeJob(JobVo jobVo) {
		return doRemoveJob(jobVo.getJob_Id()) && doAddJob(jobVo);
	}
	
	@Override
	protected void doClearJob() {
		try {
			scheduler.clear();
		} catch (SchedulerException e) {
			logger.error("doClearJob -> scheduler clear -> SchedulerException: {}", e);
		}
	}

	@Override
	public void callback(long jobId) {
	}

	@Override
	public boolean addSystemJob(ExecuteSystem executeSystem) {
		
		JobDetail jobDetail = JobBuilder.newJob(ExecuteSystemJob.class)
				.withIdentity(String.valueOf(executeSystem.getJobId()), Scheduler.DEFAULT_GROUP).build();

		JobDataMap jobDataMap = jobDetail.getJobDataMap();
    	jobDataMap.put("executeSystem", executeSystem);
		
		CronTrigger trigger = (CronTrigger) TriggerBuilder
				.newTrigger()
				.withIdentity(String.valueOf(executeSystem.getJobId()), Scheduler.DEFAULT_GROUP)
				.withSchedule(
						CronScheduleBuilder.cronSchedule(executeSystem.getCron()))
				.build();

		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			logger.error("addSystemJob -> scheduler scheduleJob -> jobId: {} -> SchedulerException: {}", executeSystem.getJobId(), e);
			return false;
		}
		return true;
	}
	
	public void setExecuteTrigger(ExecuteTrigger executeTrigger) {
		this.executeTrigger = executeTrigger;
	}
	
	public static class ExecuteTriggerJob implements Job {
		
		@Override
		public void execute(JobExecutionContext context) 
				throws JobExecutionException {
			
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
			
			JobVo jobVo = (JobVo)jobDataMap.get("jobVo");
			ExecuteTrigger executeTrigger = (ExecuteTrigger)jobDataMap.get("executeTrigger");
			if (jobVo != null && executeTrigger != null) {
				executeTrigger.execute(jobVo, Constant.LOG_EXE_STATUS_TRIGGERING);
			}		
		}
	}
	
	public static class ExecuteSystemJob implements Job {
		
		@Override
		public void execute(JobExecutionContext context) 
				throws JobExecutionException {
			
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
			
			ExecuteSystem executeSystem = (ExecuteSystem)jobDataMap.get("executeSystem");
			if (executeSystem != null) {
				executeSystem.execute();
			}		
		}
	}
}
