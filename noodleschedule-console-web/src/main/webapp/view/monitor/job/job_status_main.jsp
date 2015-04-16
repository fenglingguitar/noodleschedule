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
	    	if (trnId == 'TRIGGER') {
				if (data.result == 'false') {
					alert('触发失败');
				} else {
					alert('触发成功');
					query();
				}
			} else if (trnId == 'STOP') {
				if (data.result == 'false') {
					alert('停止失败');
				} else {
					alert('停止成功');
					query();
				}
			}
		}
    	
		function init() {
			
			$('#query').button().click(function() {
				query();
			});
			$('#viewLog').button().click(function() {
				viewLog();
			});
			$('#trigger').button().click(function() {
				trigger();
			});
			$('#stop').button().click(function() {
				stop();
			});
			
			$('#list').jqGrid({
		   		url: '<%=request.getContextPath()%>/console/log/queryjoblogpage',
				datatype: 'local',
				mtype: 'post',
			   	colNames: [
					'任务ID', 
					'父任务ID', 
					'任务名称',
					'任务状态',
					'执行类型',
					'最后调度状态',
					'执行ID',
					'父执行ID',
					'触发时间',
					'开始时间',
					'结束时间',
					'总耗时',
					'执行数量',
					'实际执行数量',
					'完成数量',
					'超时状态'
					],
			   	colModel: [
					{name:'job_Id', index:'job_Id', width:80, align: 'center'},
					{name:'parent_Job_Id', index:'job_Id', width:80, align: 'center'},
					{name:'job_Name', index:'job_Name', width:280, align: 'center'},
					{name:'exe_Status_Job', index:'exe_Status_Job', width:100, align: 'center', formatter:'select', editoptions:{value:'1:等待调度;2:正在触发;3:正在执行'}},
					{name:'exe_Type', index:'exe_Type', width:130, align: 'center', formatter:'select', editoptions:{value:'1:正常调度;2:子任务调度;3:执行错误重试调度;4:超时重试调度;5:人工调度;'}},
					{name:'exe_Status_Log', index:'exe_Status_Log', width:150, align: 'center', formatter:'select', editoptions:{value:'1:正在触发;2:触发成功,正在执行;3:部分触发成功,正在执行;4:触发失败;5:跳过,未触发;6:触发超时;7:执行成功;8:部分执行成功;9:执行失败;10:执行完成,但未正确返回;11:人工停止'}},
					{name:'log_Id', index:'log_Id', width:100, align: 'center'},
					{name:'parent_Log_Id', index:'parent_Log_Id', width:100, align: 'center'},
					{name:'trigger_Time', index:'trigger_Time', width:200, align:'center', formatter:'date', formatoptions:{srcformat:'Y-m-d H:i:s.sss', newformat:'Y-m-d H:i:s'}},
					{name:'start_Time', index:'trigger_Time', width:200, align:'center', formatter:'date', formatoptions:{srcformat:'Y-m-d H:i:s.sss', newformat:'Y-m-d H:i:s'}},
					{name:'end_Time', index:'trigger_Time', width:200, align:'center', formatter:'date', formatoptions:{srcformat:'Y-m-d H:i:s.sss', newformat:'Y-m-d H:i:s'}},
					{name:'consume_Time', index:'consume_Time', width:100, align: 'center'},
					{name:'plan_Num', index:'plan_Num', width:100, align: 'center'},
					{name:'run_Num', index:'run_Num', width:100, align: 'center'},
					{name:'complete_Num', index:'complete_Num', width:100, align: 'center'},
					{name:'timeout_Status', index:'timeout_Status', width:100, align: 'center', formatter:'select', editoptions:{value:'1:未超时;2:已超时;3:已超时,重试成功;4:已超时,重试失败'}}
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
			    	repaintGrid(248, "query_div", "list", "button_div");
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
				url: '<%=request.getContextPath()%>/console/log/queryjoblogpage',
				datatype: 'json',
				postData:{'input': jsonToString(vo)}, 
		        page: 1   
		    }).trigger('reloadGrid');
		}
		
		function viewLog() {
			var index = jQuery('#list').jqGrid('getGridParam', 'selarrrow');
			if (index.toString() == '') {
				alert('请选择');
				return;
			}
			
			var retArray = new Array();
			var indexArray = index.toString().split(',');
			for (var i=0; i<indexArray.length; i++) {				
				var ret = jQuery('#list').jqGrid('getRowData', indexArray[i]);
				top.addTab('childframe_job_' + ret['job_Id'],  '任务' + ret['job_Id'] + '-详细日志', '<%=request.getContextPath()%>/view/monitor/job/view_log_main.jsp?job_Id=' + ret['job_Id']);
			}
		}
		
		function trigger() {
			
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
			
			if (ret['exe_Status_Job'] != '1') {
				alert('任务已触发, 或在运行中');
				return;
			}
			
			var jsonSet = new JsonSet();
			jsonSet.put('input', ret);
			
			transaction({
				id: 'TRIGGER',
				url: '<%=request.getContextPath()%>/console/job/trigger',
				jsonSet: jsonSet
			});				
		}
		
		function stop() {
			
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
			if (ret['exe_Status_Job'] != '3') {
				alert('任务不在运行中');
				return;
			}
			
			if(!window.confirm('你确定要停止吗？')){
				return;
            }
			
			var jsonSet = new JsonSet();
			jsonSet.put('input', ret);
			
			transaction({
				id: 'STOP',
				url: '<%=request.getContextPath()%>/console/job/stop',
				jsonSet: jsonSet
			});	
		}
		
	</script>
  </head>

  <body onload="init();" onkeydown="onEnterDown(query);" >
	<div id="button_div">
		<button id="query">查询</button>
		<button id="viewLog">查看详细日志</button>
		<button id="trigger">人工触发</button>
		<button id="stop">人工停止</button>
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
			    	<th><label>任务状态</label></th>
			    	<td>
			    		<select id="exe_Status_Job">
			    			<option value="">--all--</option>
			    			<option value="1">等待调度</option>
			    			<option value="2">正在触发</option>
			    			<option value="3">正在执行</option>
			    		</select>
			    	</td>
			    	<th><label>调度状态</label></th>
			    	<td>
			    		<select id="exe_Status_Log">
			    			<option value="">--all--</option>
			    			<option value="1">正在触发</option>
			    			<option value="2">触发成功,正在执行</option>
			    			<option value="3">部分触发成功,正在执行</option>
			    			<option value="4">触发失败</option>
			    			<option value="5">跳过,未触发</option>
			    			<option value="6">触发过期失效</option>
			    			<option value="7">执行成功</option>
			    			<option value="8">部分执行成功</option>
			    			<option value="9">执行失败</option>
			    			<option value="10">执行完成,但未正确返回</option>
			    			<option value="11">人工停止</option>
			    		</select>
			    	</td>
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
