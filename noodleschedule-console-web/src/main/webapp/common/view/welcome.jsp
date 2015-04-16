<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>Monitor-Child</title>
       
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/common/css/my.css" />
    
	<script src="<%=request.getContextPath()%>/common/tool/wijmo-open/development-bundle/external/jquery-1.8.2.min.js" type="text/javascript"></script>
  	<script src="<%=request.getContextPath()%>/common/js/common.js" type="text/javascript"></script>

    <script type="text/javascript">
    	
    	function callback(trnId, data, other) {
    	}
    	
    	function init() {
    	}
		
	</script>
  </head>

  <body onload="init();" >
	<div class="page-header">
	    <h2>Welcome</h2>
	</div>
	<div class="page-list">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<colgroup>
				<col width="15%" />
				<col width="15%" />
				<col width="85%" />
			</colgroup>					
		    <tr>
		    	<td valign="top">
			    	<ul>
						<li>1、配置
							<ul>
				  				<li><a href="#" onclick="javascript:top.addTab('mainframe_1_1', '任务配置', '<%=request.getContextPath()%>/view/console/job/job_main.jsp');">任务配置</a></li>
				  				<li><a href="#" onclick="javascript:top.addTab('mainframe_1_2', '执行机器', '<%=request.getContextPath()%>/view/console/executor/executor_main.jsp');">执行机器</a></li>
							</ul>
						</li>
				    </ul>
		    	</td>
		    	<td valign="top">
		    		<ul>
						<li>2、监控
							<ul>
				 	 			<li><a href="#" onclick="javascript:top.addTab('mainframe_2_1', '任务状态', '<%=request.getContextPath()%>/view/monitor/job/job_status_main.jsp');">任务状态</a></li>
				 	 			<li><a href="#" onclick="javascript:top.addTab('mainframe_2_2', '详细日志', '<%=request.getContextPath()%>/view/monitor/job/view_log_main.jsp');">详细日志</a></li>
							</ul>
						</li>						
				    </ul>
		    	</td>
		    	<td valign="top">
		    		<ul>
						<li>3、报警
							<ul>
				 	 			<li><a href="#" onclick="javascript:top.addTab('mainframe_3_1', '任务报警', '<%=request.getContextPath()%>/view/alarm/job/job_alarm_main.jsp');">任务报警</a></li>
				 	 			<li><a href="#" onclick="javascript:top.addTab('mainframe_3_2', '报警人配置', '<%=request.getContextPath()%>/view/alarm/alarmpeople/alarmpeople_main.jsp');">报警人配置</a></li>
							</ul>
						</li>						
				    </ul>
		    	</td>
			</tr>
		</table>
  	</div>
  </body>
</html>
