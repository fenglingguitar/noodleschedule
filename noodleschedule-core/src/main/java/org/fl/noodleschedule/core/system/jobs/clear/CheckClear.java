package org.fl.noodleschedule.core.system.jobs.clear;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fl.noodleschedule.console.service.CoreService;
import org.fl.noodleschedule.console.system.jobs.AbstractExecuteSystem;

public class CheckClear extends AbstractExecuteSystem {
	
	Logger logger = LoggerFactory.getLogger(CheckClear.class);
	
	private CoreService coreService;
	
	private long retentionTime = 1000 * 60 * 60 * 24;
	
	public CheckClear() {
		jobId = Long.MAX_VALUE - 3;
		jobName = "CheckClear";
	}
	
	@Override
	public boolean execute() {
		
		try {
			coreService.deleteLogs(new Date((new Date()).getTime() - retentionTime));
		} catch (Exception e) {
			logger.error("execute -> coreService.deleteLogs -> retentionTime: {}, -> Exception: {}", retentionTime, e);
			return false;
		}
		
		return true;
	}

	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}
	
	public void setRetentionTime(long retentionTime) {
		this.retentionTime = retentionTime * 1000;
	}
}
