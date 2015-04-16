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
				$('#alarmPeople_Id').attr('disabled', 'disabled');
				initModel = 'UPDATE';
			} else {
				initModel = 'INSERT';
			}
		}
		
		function check() {
			
			var notNullArray = [
			                    'alarmPeople_Id',
			                    'alarmPeople_Name',
			                    'email',
			                    'phone',
			                    'status'
			                    ];
			
			for (var i=0; i<notNullArray.length; i++) {
				var id = notNullArray[i];
				if ($('#' + id).val() == '') {
					alert($('#' + id + '_Label').text() + "不能为空");
					return false;
				}
			}
			
			if($("#email").val() !== ''){
				var reg = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
				var r = $("#email").val().match(reg); 
				if(r === null){
					alert('请输入正确的邮箱地址');
					return false;
				}
			}
			
			if($("#phone").val() !== ''){
				var reg = /^((13[0-9])|(14[0-9])|(15[^4,\D])|(17[0-9])|(18[0-9]))\d{8}$/;
				var r = $("#phone").val().match(reg); 
				if(r === null){
					alert('请输入正确的手机号');
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
			
			if (initModel == 'INSERT') {				
				transaction({
					id: 'INSERT',
					url: '<%=request.getContextPath()%>/alarm/alarmpeople/insert',
					jsonSet: jsonSet
				});
			} else {
				transaction({
					id: 'UPDATE',
					url: '<%=request.getContextPath()%>/alarm/alarmpeople/update',
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
			    	<th><label id="alarmPeople_Id_Label">报警人ID</label></th>
			    	<td><input type="text" id="alarmPeople_Id" maxlength="8" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
					<th><label id="alarmPeople_Name_Label">报警人姓名</label></th>
			    	<td><input type="text" id="alarmPeople_Name" maxlength="32"/></td>
				</tr>	
				<tr>
			    	<th><label id="email_Label">邮箱</label></th>
			    	<td><input type="text" id="email" maxlength="128"/></td>
					<th><label id="phone_Label">手机号</label></th>
			    	<td><input type="text" id="phone" maxlength="13" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
				</tr>		
			    <tr>
					<th><label id="status_Label">状态</label></th>
			    	<td>
			    		<select id="status" style="width: 83.5%;">
			    			<option value="">--select--</option>
			    			<option value="1">有效</option>
			    			<option value="2">无效</option>
			    		</select>
			    	</td>
			    	<th><label>&nbsp;</label></th>
			    	<td>&nbsp;</td>
				</tr>
			</table>
		</div>
	</div>
  </body>
</html>
