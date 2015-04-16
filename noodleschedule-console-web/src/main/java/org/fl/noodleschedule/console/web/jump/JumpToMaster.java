package org.fl.noodleschedule.console.web.jump;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fl.noodle.common.net.http.HttpConnect;
import org.fl.noodle.common.net.http.jdk.HttpConnectJdk;
import org.fl.noodleschedule.client.pojo.JobResult;
import org.fl.noodleschedule.console.service.CoreService;
import org.fl.noodleschedule.console.vo.JobLogVo;
import org.fl.noodleschedule.console.web.mvc.util.ResultVo;

public class JumpToMaster {

	Logger logger = LoggerFactory.getLogger(JumpToMaster.class);
	
	private CoreService coreService;
	
	private String localIp;
	private int port;
	private String url;
	private int connectTimeout = 5000;
	private int readTimeout = 5000;
	
	public JumpToMaster() throws UnknownHostException {
		localIp = localIp == null ? InetAddress.getLocalHost().getHostAddress() : localIp;
	}
	
	public JobResult callbackJump(JobResult jobResult) {
		
		String masterIp = getMasterIp();
		if (masterIp != null && !masterIp.equals(localIp)) {
			String jumpUrl = "http://" + masterIp + ":" + port + url;
			HttpConnect httpConnect = new HttpConnectJdk(jumpUrl, connectTimeout, readTimeout);
			try {
				return httpConnect.post("input", jobResult, JobResult.class);
			} catch (Exception e) {
				logger.error("callbackJump -> httpConnect.send -> jumpUrl: {}, logId: {}, executorId: {}, code: {} -> Exception: {}", jumpUrl, jobResult.getLogId(), jobResult.getExecutorId(), jobResult.getCode(), e);
				return new JobResult(false, e.getMessage());
			}
		}
		
		return null;
	}
	
	public ResultVo stopJobJump(JobLogVo jobLogVo) {
		
		String masterIp = getMasterIp();
		if (masterIp != null && !masterIp.equals(localIp)) {
			String jumpUrl = "http://" + masterIp + ":" + port + url;
			HttpConnect httpConnect = new HttpConnectJdk(jumpUrl, connectTimeout, readTimeout);
			try {
				return httpConnect.post("input", jobLogVo, ResultVo.class);
			} catch (Exception e) {
				logger.error("stopJobJump -> httpConnect.send -> jumpUrl: {}, jobId: {}, logId: {} -> Exception: {}", jumpUrl, jobLogVo.getJob_Id(), jobLogVo.getLog_Id(), e);
				return new ResultVo("false");
			}
		}
		
		return null;
	}
	
	private String getMasterIp() {
		try {
			return coreService.queryMasterIp();
		} catch (Exception e) {
			logger.error("getMasterIp -> coreService.queryMasterIp -> Exception: ", e);
			return null;
		}
	}

	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUrl(String url) {
		if (!url.startsWith("/")) {
			url = "/" + url;
		}
		this.url = url;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
}
