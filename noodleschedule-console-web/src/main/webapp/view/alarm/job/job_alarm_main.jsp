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
    	
	    function callback(trnId, data) {
	    	
			if (trnId == 'DELETES') {
				if (data.result == 'false') {
					alert('删除失败');
				} else {
					alert('删除成功');
					query();
				}
			}
		}
    	
		function init() {
			
			$('#query').button().click(function() {
				query();
			});
			$('#update').button().click(function() {
				update();
			});
			$('#setAlarmPeople').button().click(function() {
				setAlarmPeople();
			});
			
			$('#list').jqGrid({
		   		url: '<%=request.getContextPath()%>/console/job/querypage',
				datatype: 'local',
				mtype: 'post',
			   	colNames: [
					'任务ID', 
					'任务名称', 
					'是否报警',
					'是否邮件报警',
					'是否短信报警',
					'最大间隔时间',
					'最后报警时间'
					],
			   	colModel: [
					{name:'job_Id', index:'job_Id', width:80, align: 'center'},
					{name:'job_Name', index:'job_Name', width:280, align: 'center'},
					{name:'is_Alarm', index:'is_Alarm', width:100, align: 'center', formatter:'select', editoptions:{value:'1:是;2:否'}},
					{name:'is_Mail_Alarm', index:'is_Mail_Alarm', width:100, align: 'center', formatter:'select', editoptions:{value:'1:是;2:否'}},
					{name:'is_Sms_Alarm', index:'is_Sms_Alarm', width:100, align: 'center', formatter:'select', editoptions:{value:'1:是;2:否'}},
					{name:'max_Interval_Time', index:'max_Interval_Time', width:120, align: 'center', formatter:'select', editoptions:{value:'3600000:1小时;21600000:6小时;43200000:12小时;86400000:1天;259200000:3天;432000000:5天;604800000:1周;2592000000:1月(30天)'}},
					{name:'last_Alarm_Time', index:'last_Alarm_Time', width:200, align:'center', formatter:'date', formatoptions:{srcformat:'Y-m-d H:i:s.sss', newformat:'Y-m-d H:i:s'}}
			   	],
			   	rowNum: 10,
			   	rowList: [10,20,30,40,50,100],
			   	pager: '#pager',
			   	sortname: 'id',
			    viewrecords: true,
			    autowidth: true,
			    shrinkToFit: false,
			    height: 231,
			    sortorder: 'desc',
			    multiselect: true,
			    jsonReader: {
					repeatitems : false
				},
				ondblClickRow: false,
			    caption: '查询结果',
			    gridComplete:function(){
			    	repaintGrid(231, "query_div", "list", "button_div");
			    }
			});
				
			$('#list').jqGrid('navGrid', '#pager', {search:false, edit:false, add:false, del:false});
			
			$(window).resize(function(){ 
				$("#list").setGridWidth($(window).width() - 14);
			});
			
			query();
		}
		
		function query() {
			
			var vo = new Object();
			$('#form :input').each(function(i){
				if ($(this).val() != '' 
						&& $(this).attr('id').indexOf("Dt") == -1) {					
					vo[$(this).attr('id')] = $(this).val();
				}
			 });
			
			$('#list').jqGrid('setGridParam', {   
				url: '<%=request.getContextPath()%>/console/job/querypage',
				datatype: 'json',
				postData:{'input': jsonToString(vo)}, 
		        page: 1   
		    }).trigger('reloadGrid');
		}
		
		function update() {
			
			var index = jQuery('#list').jqGrid('getGridParam', 'selarrrow');
			if (index.toString() == '') {
				alert('请选择');
				return;
			}
			
			var indexArray = index.toString().split(',');
			if (indexArray.length > 1) {
				alert('只能选择一行');
				return;
			}
			
			var ret = jQuery('#list').jqGrid('getRowData', index);
			if (ret['job_Type'] == 'child') {
				alert('记录是子任务类型, 请使用修改子任务');
				return;
			}
			top.openDialog('任务报警设置修改', '<%=request.getContextPath()%>/view/alarm/job/job_alarm_edit.jsp', ret, 260, 700, query);					
		}
		
		function setAlarmPeople() {
			var index = jQuery('#list').jqGrid('getGridParam', 'selarrrow');
			if (index.toString() == '') {
				alert('请选择');
				return;
			}
			var indexArray = index.toString().split(',');
			if (indexArray.length > 1) {
				alert('只能选择一行');
				return;
			}
			var ret = jQuery('#list').jqGrid('getRowData', index);
			top.openDialog('设置报警人', '<%=request.getContextPath()%>/view/alarm/job/job-alarmpeople/job_alarmpeople_main.jsp', ret, 590, 1200, null);					
		}
	</script>
  </head>

  <body onload="init();" onkeydown="onEnterDown(query);" >
	<div id="button_div">
		<button id="query">查询</button>
		<button id="update">修改</button>
		<button id="setAlarmPeople">设置报警人</button>
	</div>
	<div id="query_div">
		<div id="form" style="width: auto;">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="s_layout">
				<colgroup>
					<col width="10%" />
					<col width="15%" />
					<col width="10%" />
					<col width="15%" />
					<col width="10%" />
					<col width="15%" />
					<col width="10%" />
					<col width="15%" />
				</colgroup>			
				<tr>
			    	<th><label>任务ID</label></th>
			    	<td><input type="text" id="job_Id" maxlength="32"/></td>
			    	<th><label>任务名称</label></th>
			    	<td><input type="text" id="job_Name" maxlength="32"/></td>
			    	<th><label>是否报警</label></th>
			    	<td>
			    		<select id="is_Alarm">
			    			<option value="">--all--</option>
			    			<option value="1">是</option>
			    			<option value="2">否</option>
			    		</select>
			    	</td>
			    	<th><label>是否邮件报警</label></th>
			    	<td>
			    		<select id="is_Mail_Alarm">
			    			<option value="">--all--</option>
			    			<option value="1">是</option>
			    			<option value="2">否</option>
			    		</select>
			    	</td>
				</tr>					
			    <tr>
			    	<th><label>是否短信报警</label></th>
			    	<td>
			    		<select id="is_Sms_Alarm">
			    			<option value="">--all--</option>
			    			<option value="1">是</option>
			    			<option value="2">否</option>
			    		</select>
			    	</td>
			    	<th><label>&nbsp;</label></th>
			    	<td>&nbsp;</td>
			    	<th><label>&nbsp;</label></th>
			    	<td>&nbsp;</td>
			    	<th><label>&nbsp;</label></th>
			    	<td>&nbsp;</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="list_div" style="width:auto;">
		<table id="list"></table>
		<div id="pager"></div>
	</div>
  </body>
</html>
