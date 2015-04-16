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
		}
    
		function init() {
			
			$('#save').button().click(function() {
				save()
			});
			$('#cancel').button().click(function() {
				top.closeDialogChild(false);
			});
			
			for (var i=0; i<24; i++) {
				var time = i;
				if (time < 10) {
					time = '0' + i;
				}
				$('#time_Hour').append('<option value="' + i + '">' + time + '</option>');
			}
			for (var i=0; i<60; i++) {	
				var time = i;
				if (time < 10) {
					time = '0' + i;
				}
				$('#time_Minute').append('<option value="' + i + '">' + time + '</option>');
			}
			for (var i=0; i<60; i++) {	
				var time = i;
				if (time < 10) {
					time = '0' + i;
				}
				$('#time_Second').append('<option value="' + i + '">' + time + '</option>');
			}
			
			$('#type').change(function() {
				$('#interval_Type').removeAttr('disabled');
				$('#interval').removeAttr('disabled');
				$('#time_Hour').removeAttr('disabled');
				$('#time_Minute').removeAttr('disabled');
				$('#time_Second').removeAttr('disabled');
				
				$('#interval_Type option').attr('selected', false);
				$('#interval option').attr('selected', false);
				$('#time_Hour option').attr('selected', false);
				$('#time_Minute option').attr('selected', false);
				$('#time_Second option').attr('selected', false);
				
				if ($('#type option:selected').val() != '') {					
					if ($('#type option:selected').val() == '1') {
						$('#interval_Type').attr('disabled', 'disabled');
						$('#interval').attr('disabled', 'disabled');
					} else {
						$('#time_Hour').attr('disabled', 'disabled');
						$('#time_Minute').attr('disabled', 'disabled');
						$('#time_Second').attr('disabled', 'disabled');
					}
				}
			});
			
			$('#interval_Type').change(function() {
				$('#interval').empty();
				$('#interval').append('<option value="">--select--</option>');
				if ($('#interval_Type option:selected').val() != '') {
					if ($('#interval_Type option:selected').val() == '1') {
						for (var i=0; i<24; i++) {
							var time = i;
							if (time < 10) {
								time = '0' + i;
							}
							$('#interval').append('<option value="' + i + '">' + time + '</option>');
						}
					} else {
						for (var i=0; i<60; i++) {	
							var time = i;
							if (time < 10) {
								time = '0' + i;
							}
							$('#interval').append('<option value="' + i + '">' + time + '</option>');
						}
					}
				}
			});
		}
		
		function check() {
			
			var notNullArray = [
			                    'type'
			                    ];
			
			for (var i=0; i<notNullArray.length; i++) {
				var id = notNullArray[i];
				if ($('#' + id).val() == '') {
					alert($('#' + id + '_Label').text() + "不能为空");
					return false;
				}
			}
			
			if ($('#type option:selected').val() == '1') {
				if ($('#time_Hour option:selected').val() == ''
						|| $('#time_Minute option:selected').val() == ''
						|| $('#time_Second option:selected').val() == '') {
					alert('时间不能为空');
					return false;
				}
			} else {
				if ($('#interval_Type option:selected').val() == ''
					|| $('#interval option:selected').val() == '' ) {
					alert('间隔不能为空');
					return false;
				}
			}
			
			return true;
		}
		
		function save() {
			
			if (!check()) {
				return;
			}
			
			top.closeDialogChild(true, getCron());
		}
		
		function getCron() {
			
			if ($('#type option:selected').val() == '1') {
				return $('#time_Second option:selected').val() + ' ' 
						+ $('#time_Minute option:selected').val() + ' '
						+ $('#time_Hour option:selected').val() + ' * * ?'
				
			} else {
				if ($('#interval_Type option:selected').val() == '1') {
					return '0 0 0/' + $('#interval option:selected').val() + ' * * ?'
				} else if ($('#interval_Type option:selected').val() == '2') {
					return '0 0/' + $('#interval option:selected').val() + ' * * * ?'
				} else {
					return '0/' + $('#interval option:selected').val() + ' * * * * ?'
				}
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
					<col width="30%" />
					<col width="70%" />
				</colgroup>					
			    <tr>
					<th><label id="type_Label">类型</label></th>
			    	<td>
			    		<select id="type" style="width: 83.5%;">
			    			<option value="">--select--</option>
			    			<option value="1">每天</option>
			    			<option value="2">每隔</option>
			    		</select>
			    	</td>
				</tr>
				<tr>
			    	<th><label id="time_Label">时间</label></th>
			    	<td>
			    		<select name="time_Hour" id="time_Hour" style="width:20%;"><option value="">--</option></select><span style="width:10px; margin: 0 6px 0 3px;">时</span>
			    		<select name="time_Minute" id="time_Minute" style="width:20%;"><option value="">--</option></select><span style="width:10px; margin: 0 6px 0 3px;">分</span>
			    		<select name="time_Second" id="time_Second" style="width:20%;"><option value="">--</option></select><span style="width:10px; margin: 0 6px 0 3px;">秒</span>
			    	</td>
				</tr>
				<tr>
					<th><label id="interval_Label">间隔</label></th>
			    	<td>
			    		<select id="interval_Type" style="width:26%;">
			    			<option value="">--select--</option>
			    			<option value="1">小时</option>
			    			<option value="2">分钟</option>
			    			<option value="3">秒</option>
			    		</select>
			    		<select name="interval" id="interval" style="width:55%;">
			    			<option value="">--select--</option>
			    		</select>
			    	</td>
				</tr>
			</table>
		</div>
	</div>
  </body>
</html>
