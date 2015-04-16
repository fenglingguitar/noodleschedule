<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../../global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>NotifyServer-Child</title>
       
    <link href="<%=request.getContextPath()%>/common/tool/wijmo-open/development-bundle/themes/smoothness/jquery-ui.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/common/tool/jqgrid/css/ui.jqgrid.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/common/css/my.css" rel="stylesheet" type="text/css" />
    
    <script src="<%=request.getContextPath()%>/common/tool/wijmo-open/development-bundle/external/jquery-1.8.2.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/common/tool/wijmo-open/development-bundle/external/jquery-ui-1.9.1.custom.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/common/tool/jqgrid/js/i18n/grid.locale-en.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/common/tool/jqgrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/common/js/common.js" type="text/javascript"></script>
	
    <script type="text/javascript">
		
    	var initModel;
    	var isEnter = true;
    
		function callback(trnId, data) {
			
			if (trnId == 'UPDATE') {
    			if (data.result == 'false') {
    				alert('修改失败');
    			} else {
    				alert('修改成功');
    				top.closeDialog(true);
    			}
			} 
		}
    
		function init() {
			
			$('#save').button().click(function() {
				save()
			});
			$('#cancel').button().click(function() {
				top.closeDialog(false);
			});
			
			var urlParamObject = getURLParamObject();
			if (urlParamObject != null) {				
				$('#form :input').each(function(i){
					if (urlParamObject[$(this).attr('id')] != null) {					
						$(this).val(urlParamObject[$(this).attr('id')]);
					}
				});
				$('#job_Name').attr('disabled', 'disabled');
				initModel = 'UPDATE';
			} 
		}
		
		function check() {
			
			var notNullArray = [
			                    'job_Type',
			                    'route_Type',
			                    'method',
			                    'exe_Timeout',
			                    'exe_Retry',
			                    'timeout_Retry',
			                    'parent_Job_Id',
			                    'status'
			                    ];
			
			for (var i=0; i<notNullArray.length; i++) {
				var id = notNullArray[i];
				if ($('#' + id).val() == '') {
					alert($('#' + id + '_Label').text() + "不能为空");
					return false;
				}
			}
			
			return true;
		}
		
		function save() {
			
			if (!check()) {
				return;
			}
			
			var vo = new Object();
			$('#form :input').each(function(i){
				vo[$(this).attr('id')] = $(this).val();
			});
			
			var jsonSet = new JsonSet();
			jsonSet.put('input', vo);
			
			transaction({
				id: 'UPDATE',
				url: '<%=request.getContextPath()%>/console/job/update',
				jsonSet: jsonSet
			});		
		}
		
		function onEnterDownLocal(event) {
    		if (event.keyCode == 13) {
    			if (isEnter) {
    				save();
    			}
    		}
    	}
	</script>
  </head>

  <body onload="init();" onkeydown="onEnterDownLocal(event);" >
	<div>
		<button id="save">保存</button>
		<button id="cancel">取消</button>
	</div>
	<div>
		<div id="form" style="width: auto;">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="s_layout">
				<colgroup>
					<col width="20%" />
					<col width="30%" />
					<col width="20%" />
					<col width="30%" />
				</colgroup>					
			    <tr>
			    	<th><label id="job_Name_Label">任务名称</label></th>
			    	<td><input type="text" id="job_Name" maxlength="32"/></td>
					<th><label id="is_Alarm_Label">是否报警</label></th>
			    	<td>
			    		<select id="is_Alarm">
			    			<option value="">--select--</option>
			    			<option value="1">是</option>
			    			<option value="2">否</option>
			    		</select>
			    	</td>
				</tr>
				<tr>
					<th><label id="is_Mail_Alarm_Label">是否邮件报警</label></th>
			    	<td>
			    		<select id="is_Mail_Alarm">
			    			<option value="">--select--</option>
			    			<option value="1">是</option>
			    			<option value="2">否</option>
			    		</select>
			    	</td>
			    	<th><label id="is_Sms_Alarm_Label">是否短信报警</label></th>
			    	<td>
			    		<select id="is_Sms_Alarm">
			    			<option value="">--select--</option>
			    			<option value="1">是</option>
			    			<option value="2">否</option>
			    		</select>
			    	</td>
				</tr>
				<tr>
			    	<th><label id="max_Interval_Time_Label">最大间隔时间</label></th>
			    	<td>
			    		<select id="max_Interval_Time">
			    			<option value="">--select--</option>
			    			<option value="3600000">1小时</option>
			    			<option value="21600000">6小时</option>
			    			<option value="43200000">12小时</option>
			    			<option value="86400000">1天</option>
			    			<option value="259200000">3天</option>
			    			<option value="432000000">5天</option>
			    			<option value="604800000">1周</option>
			    			<option value="2592000000">1月(30天)</option>
			    		</select>
			    	</td>
			    	<th><label>&nbsp;</label></th>
			    	<td>&nbsp;</td>
				</tr>
			</table>
			<input type="hidden" id="job_Id"/>
		</div>
	</div>
  </body>
</html>
