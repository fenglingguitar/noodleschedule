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
			
			if (trnId == 'INSERT') {
    			if (data.result == 'false') {
    				alert('新增失败');
    			} else {
    				alert('新增成功');
    				top.closeDialog(true);
    			}
			} else if (trnId == 'UPDATE') {
    			if (data.result == 'false') {
    				alert('修改失败');
    			} else {
    				alert('修改成功');
    				top.closeDialog(true);
    			}
			} else if (trnId == 'CHECK_PARENT_JOB') {
    			if (data.result == 'false') {
    				alert($('#parent_Job_Id_Label').text() + "不存在");
    				$("#parent_Job_Id").focus();
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
				initModel = 'UPDATE';
			} else {
				$('#parent_Job_Id').val(getURLParamObject('parent_Job_Id'));
				initModel = 'INSERT';
			}
			
			$("#description").focusin(function(){
				isEnter = false;
			});
			
			$("#description").focusout(function(){
				isEnter = true;
			});
			
			if (Sys.firefox) {
				$("textarea").attr('rows', '3');
			}
			
			$("#parent_Job_Id").focusout(function(){
				if ($('#parent_Job_Id').val() != '') {					
					checkParentJob($('#parent_Job_Id').val());
				}
			});
		}
		
		function check() {
			
			var notNullArray = [
			                    'job_Name',
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
			
			if($("#method").val() !== ''){
				var reg = /^[A-Za-z0-9]+$/;
				var r = $("#method").val().match(reg); 
				if(r === null){
					alert('请输入正确的' + $('#method_Label').text() + '(字母、数字)');
					return false;
				}
			}
			
			return true;
		}
		
		function checkParentJob(jobId) {
			
			var jsonSet = new JsonSet();
			jsonSet.put('jobId', jobId);
			
			transaction({
				id: 'CHECK_PARENT_JOB',
				url: '<%=request.getContextPath()%>/console/job/checkparentjob',
				jsonSet: jsonSet
			});
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
			
			if (initModel == 'INSERT') {				
				transaction({
					id: 'INSERT',
					url: '<%=request.getContextPath()%>/console/job/insert',
					jsonSet: jsonSet
				});
			} else {
				transaction({
					id: 'UPDATE',
					url: '<%=request.getContextPath()%>/console/job/updateincludenull',
					jsonSet: jsonSet
				});				
			}
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
					<th><label id="job_Type_Label">调度类型</label></th>
			    	<td>
			    		<select id="job_Type" disabled="disabled">
			    			<option value="child">子任务调度</option>
			    		</select>
			    	</td>
				</tr>
				<tr>
					<th><label id="route_Type_Label">路由规则</label></th>
			    	<td>
			    		<select id="route_Type">
			    			<option value="">--select--</option>
			    			<option value="first">第一台开始</option>
			    			<option value="random">随机</option>
			    			<option value="sequence">循环顺序</option>
			    			<option value="all">全部执行</option>
			    		</select>
			    	</td>
			    	<th><label id="method_Label">执行方法</label></th>
			    	<td><input type="text" id="method" maxlength="64"/></td>
				</tr>
				<tr>
					<th><label id="exe_Timeout_Label">超时时间(秒)</label></th>
			    	<td><input type="text" id="exe_Timeout" maxlength="8" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
			    	<th><label id="exe_Retry_Label">执行失败重试</label></th>
			    	<td>
			    		<select id="exe_Retry">
			    			<option value="">--select--</option>
			    			<option value="1">是</option>
			    			<option value="2">否</option>
			    		</select>
			    	</td>
				</tr>
				<tr>
					<th><label id="timeout_Retry_Label">超时重试</label></th>
			    	<td>
			    		<select id="timeout_Retry">
			    			<option value="">--select--</option>
			    			<option value="1">是</option>
			    			<option value="2">否</option>
			    		</select>
			    	</td>
			    	<th><label id="parent_Job_Id_Label">父任务ID</label></th>
			    	<td><input type="text" id="parent_Job_Id" maxlength="10" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
				</tr>
				<tr>
					<th><label id="status_Label">有效状态</label></th>
			    	<td>
			    		<select id="status">
			    			<option value="">--select--</option>
			    			<option value="1">有效</option>
			    			<option value="2">无效</option>
			    		</select>
			    	</td>
			    	<th><label>&nbsp;</label></th>
			    	<td>&nbsp;</td>
				</tr>
				<tr>
			    	<th><label id="description_Label">描述</label></th>
			    	<td colspan="3"><textarea id="description" rows="5" cols="50" maxlength="128" style="width:94%;"></textarea></td>
				</tr>
			</table>
			<input type="hidden" id="job_Id"/>
		</div>
	</div>
  </body>
</html>
