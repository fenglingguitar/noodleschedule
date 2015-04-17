package org.fl.noodleschedule.util.common;

public final class Constant {
	
	public static final String JOB_TYPE_QUARTZ = "quartz";
	public static final String JOB_TYPE_CHILD = "child";
	
	public static final int STATUS_YES = 1;
	public static final int STATUS_NO = 2;
	
	public static final String ROUTE_TYPE_FIRST = "first";
	public static final String ROUTE_TYPE_RANDOM = "random";
	public static final String ROUTE_TYPE_SEQUENCE = "sequence";
	public static final String ROUTE_TYPE_ALL = "all";
	
	public static final int JOB_EXE_STATUS_WAITING = 1;
	public static final int JOB_EXE_STATUS_TRIGGERING = 2;
	public static final int JOB_EXE_STATUS_RUNNING = 3;
	
	public static final int LOG_EXE_STATUS_TRIGGERING = 1;
	public static final int LOG_EXE_STATUS_TRIGGER_SUCCESS = 2;
	public static final int LOG_EXE_STATUS_TRIGGER_PART_SUCCESS = 3;
	public static final int LOG_EXE_STATUS_TRIGGER_FAIL = 4;
	public static final int LOG_EXE_STATUS_TRIGGER_SKIP = 5;
	public static final int LOG_EXE_STATUS_TRIGGER_TIMEOUT = 6;
	public static final int LOG_EXE_STATUS_RUN_SUCCESS = 7;
	public static final int LOG_EXE_STATUS_RUN_PART_SUCCESS = 8;
	public static final int LOG_EXE_STATUS_RUN_FAIL = 9;
	public static final int LOG_EXE_STATUS_RUN_DISAPPEAR = 10;
	public static final int LOG_EXE_STATUS_MANUAL_STOP = 11;
	
	public static final int EXECUTOR_EXE_STATUS_TRIGGER_SUCCESS = 1;
	public static final int EXECUTOR_EXE_STATUS_TRIGGER_FAIL = 2;
	public static final int EXECUTOR_EXE_STATUS_TRIGGER_RUNNING = 3;
	public static final int EXECUTOR_EXE_STATUS_TRIGGER_NOEXIST = 4;
	public static final int EXECUTOR_EXE_STATUS_TRIGGER_NET_FAIL = 5;
	public static final int EXECUTOR_EXE_STATUS_RUN_SUCCESS = 6;
	public static final int EXECUTOR_EXE_STATUS_RUN_FAIL = 7;
	public static final int EXECUTOR_EXE_STATUS_RUN_DISAPPEAR = 8;
	public static final int EXECUTOR_EXE_STATUS_NO_RESPONSE = 9;
	public static final int EXECUTOR_EXE_STATUS_MANUAL_STOP = 10;
	
	public static final int RESULT_YES = 1;
	public static final int RESULT_NO = 0;
	
	public static final int TRIGGER_TYPE_ORDINARY = 1;
	public static final int TRIGGER_TYPE_CHILD = 2;
	public static final int TRIGGER_TYPE_EXE_FAIL_RETRY = 3;
	public static final int TRIGGER_TYPE_TIMEOUT_RETRY = 4;
	public static final int TRIGGER_TYPE_MANUAL = 5;
	
	public static final int RPC_TYPE_HTTP = 1;
	
	public static final int RETURN_INVALID = -1;
	
	public static final String EXCEPTION_NO_EXECUTOR = "没有配置执行机器列表";
	public static final String EXCEPTION_NO_CATCH = "已经有其他相同调度执行";
	public static final String EXCEPTION_OTHER_TRIGGERING = "上次触发未完成";
	public static final String EXCEPTION_EXPIRE_TRIGGER = "本次触发未完成,但已过期";
	public static final String EXCEPTION_STILL_RUNNING = "上次任务未完成，还在执行中";
	public static final String EXCEPTION_RUN_DISAPPEAR = "本次任务已完成或失效，未正确返回";
	public static final String EXCEPTION_METHOD_NOEXIST = "客户端此任务方法未定义";
	public static final String EXCEPTION_ALREADY_STOP = "任务已经结束";
	public static final String EXCEPTION_FAIL_STOP = "未能成功停止";
	public static final String EXCEPTION_NO_ACTION_TYPE = "没有此种操作类型";
	public static final String EXCEPTION_READ_JSON_FAIL = "JOSN格式错误";
	public static final String EXCEPTION_URL_PARAM_NULL = "URL参数input为空";
	public static final String EXCEPTION_TRIGGER_FAIL = "触发失败";
	public static final String EXCEPTION_EXECUTOR_NO_RESPONSE = "执行机器无响应";
	
	public static final String CLIENT_ACTION_TYPE_TRIGGER = "trigger";
	public static final String CLIENT_ACTION_TYPE_CHECK = "check";
	public static final String CLIENT_ACTION_TYPE_STOP = "stop";

	public static final int CLIENT_EXE_TRIGGER_SUCCESS = 1;
	public static final int CLIENT_EXE_TRIGGER_FAIL = 2;
	public static final int CLIENT_EXE_TRIGGER_RUNNING = 3;
	public static final int CLIENT_EXE_TRIGGER_NOEXIST = 4;
	public static final int CLIENT_EXE_TRIGGER_NET_FAIL = 5;
	
	public static final int CLIENT_EXE_STATUS_RUNNING = 1;
	public static final int CLIENT_EXE_STATUS_NORUN = 2;
	
	public static final int CLIENT_EXE_STOP_SUCCESS = 1;
	public static final int CLIENT_EXE_STOP_FAIL = 2;
	public static final int CLIENT_EXE_STOP_NOEXIST = 3;
	
	public static final int CLIENT_EXE_SUCCESS = 1;
	public static final int CLIENT_EXE_FAIL = 2;
	
	public static final int TIMEOUT_STATUS_NO = 1;
	public static final int TIMEOUT_STATUS_YES = 2;
	public static final int TIMEOUT_STATUS_YES_RETRY_SUCCESS = 3;
	public static final int TIMEOUT_STATUS_YES_RETRY_FAIL = 4;	
	
	public static final int EXE_RETRY_YES = 1;
	public static final int EXE_RETRY_NO = 2;

	public static final int TIMEOUT_RETRY_YES = 1;
	public static final int TIMEOUT_RETRY_NO = 2;
	
	public static final int IS_ALARM_YES = 1;
	public static final int IS_ALARM_NO = 2;
	
	public static final int IS_MAIL_ALARM_YES = 1;
	public static final int IS_MAIL_ALARM_NO = 2;
	
	public static final int IS_SMS_ALARM_YES = 1;
	public static final int IS_SMS_ALARM_NO = 2;
	
	public static final int CALLBACK_RESULT_NO_COMPLETE = 1;
	public static final int CALLBACK_RESULT_ALL_SUCCESS = 2;
	public static final int CALLBACK_RESULT_PART_SUCCESS = 3;
	public static final int CALLBACK_RESULT_ALL_FAIL = 4;
	
	public static final int ALARM_INFO_TRIGGER_FAIL = 1;
	public static final int ALARM_INFO_RUN_FAIL = 2;
	public static final int ALARM_INFO_TIMEOUT = 3;
	
	public static final int CALLBACK_RESULT_YES = 1;
	public static final int CALLBACK_RESULT_NO = 2;
}
