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
		}
    	
		function init() {
			
			$('#query').button().click(function() {
				query();
			});
			
			$('#list').jqGrid({
		   		url: '<%=request.getContextPath()%>/console/log/querypage',
				datatype: 'local',
				mtype: 'post',
			   	colNames: [
					'任务ID', 
					'执行ID',
					'父执行ID',
					'执行类型',
					'调度状态',
					'异常信息',
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
					{name:'log_Id', index:'log_Id', width:100, align: 'center'},
					{name:'parent_Log_Id', index:'parent_Log_Id', width:100, align: 'center'},
					{name:'exe_Type', index:'exe_Type', width:130, align: 'center', formatter:'select', editoptions:{value:'1:正常调度;2:子任务调度;3:执行错误重试调度;4:超时重试调度;5:人工调度;'}},
					{name:'exe_Status', index:'exe_Status', width:150, align: 'center', formatter:'select', editoptions:{value:'1:正在触发;2:触发成功,正在执行;3:部分触发成功,正在执行;4:触发失败;5:跳过,未触发;6:触发超时;7:执行成功;8:部分执行成功;9:执行失败;10:执行完成,但未正确返回;11:人工停止'}},
					{name:'exception', index:'exception', width:300, align: 'center'},
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
			    multiselect: false,
			    jsonReader: {
					repeatitems : false
				},
				ondblClickRow: false,
				subGrid: true,
				subGridRowExpanded: function(subgrid_id, row_id) {
					var subgrid_table_id, pager_id;
					subgrid_table_id = subgrid_id + "_t";
					pager_id = "p_" + subgrid_table_id;
					$("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='" + pager_id + "' class='scroll'></div>");
					var ret = jQuery('#list').jqGrid('getRowData', row_id);
					var vo = new Object();
					vo['log_Id'] = ret['log_Id'];
					jQuery("#"+subgrid_table_id).jqGrid({
						url:'<%=request.getContextPath()%>/console/log/executor/queryexecutorbylogpage',
						datatype: "json",
						mtype: 'post',
						postData:{'input': jsonToString(vo)}, 
						colNames: [
						           	'执行ID', 
									'执行机器ID',
									'执行机器名称',
									'开始时间',
									'结束时间',
									'耗时',
									'执行状态',
									'异常信息',
									'RPC类型',
									'IP',
									'PORT',
									'URL'
						],
						colModel: [
									{name:'log_Id', index:'log_Id', width:100, align: 'center'},
									{name:'executor_Id', index:'executor_Id', width:80, align: 'center'},
									{name:'executor_Name', index:'executor_Name', width:280, align: 'center'},
									{name:'start_Time', index:'start_Time', width:200, align:'center', formatter:'date', formatoptions:{srcformat:'Y-m-d H:i:s.sss', newformat:'Y-m-d H:i:s'}},
									{name:'end_Time', index:'end_Time', width:200, align:'center', formatter:'date', formatoptions:{srcformat:'Y-m-d H:i:s.sss', newformat:'Y-m-d H:i:s'}},
									{name:'consume_Time', index:'consume_Time', width:100, align: 'center'},
									{name:'exe_Status', index:'exe_Status', width:150, align: 'center', formatter:'select', editoptions:{value:'1:触发成功,正在执行;2:触发失败;3:上次执行还未完成;4:执行方法不存在;5:网络异常;6:执行成功;7:执行失败;8:执行完成,但未正确返回;9:已无响应;10:人工停止'}},
									{name:'exception', index:'exception', width:300, align: 'center'},
									{name:'rpc_Type', index:'rpc_Type', width:100, align: 'center'},
									{name:'ip', index:'ip', width:220, align: 'center'},
									{name:'port', index:'port', width:80, align: 'center'},
									{name:'url', index:'url', width:280, align: 'center'}
						],
					   	rowNum:10,
					   	rowList: [10,20,30,40,50,100],
					   	pager: pager_id,
					   	sortname: 'executor_Id',
					    sortorder: "desc",
					    height: '100%',
					    jsonReader: {
							repeatitems : false
						}
					});
					jQuery("#"+subgrid_table_id).jqGrid('navGrid', "#" + pager_id,{search:false,edit:false,add:false,del:false})
				},
			    caption: '查询结果',
			    gridComplete:function(){
			    	repaintGrid(248, "query_div", "list", "button_div");
			    }
			});
				
			$('#list').jqGrid('navGrid', '#pager', {search:false, edit:false, add:false, del:false});
			
			$('#trigger_Time_Start').datepicker({
	            dateFormat: 'yy-mm-dd'
	        });
			$('#trigger_Time_End').datepicker({
	            dateFormat: 'yy-mm-dd'
	        });
			for (var i=0; i<24; i++) {
				var time = i;
				if (time < 10) {
					time = '0' + i;
				}
				$('#trigger_Time_Hour_Start').append('<option value="' + time + '">' + time + '</option>');
			}
			for (var i=0; i<60; i++) {	
				var time = i;
				if (time < 10) {
					time = '0' + i;
				}
				$('#trigger_Time_Minute_Start').append('<option value="' + time + '">' + time + '</option>');
			}
			for (var i=0; i<24; i++) {
				var time = i;
				if (time < 10) {			
					time = '0' + i;
				}
				$('#trigger_Time_Hour_End').append('<option value="' + time + '">' + time + '</option>');
			}
			for (var i=0; i<60; i++) {
				var time = i;
				if (time < 10) {			
					time = '0' + i;
				}
				$('#trigger_Time_Minute_End').append('<option value="' + time + '">' + time + '</option>');
			}
			
			if (Sys.ie) {
				$('#form input[id^="trigger_Time_"]').attr('style', 'width:24.8%;');
				$('#form select[id^="trigger_Time_"]').attr('style', 'width:10%;');
			} else if (Sys.chrome) {
				$('#form input[id^="trigger_Time_"]').attr('style', 'width:24.3%;');
				$('#form select[id^="trigger_Time_"]').attr('style', 'width:9.5%;');
			} else if (Sys.firefox) {
				$('#form input[id^="trigger_Time_"]').attr('style', 'width:24.3%;');
				$('#form select[id^="trigger_Time_"]').attr('style', 'width:9.5%;');
			}
			
			$(window).resize(function(){ 
				$("#list").setGridWidth($(window).width() - 14);
			});
			
			var job_Id = getURLParamObject('job_Id');
			if (job_Id != null) {
				$("#job_Id").val(job_Id);
				$("#job_Id").attr('disabled', 'disabled');
			}
			
			query();
		}
		
		function query() {
			
			var vo = new Object();
			$('#form :input').each(function(i){
				if ($(this).val() != '' 
						&& $(this).attr('id').indexOf("Time") == -1) {					
					vo[$(this).attr('id')] = $(this).val();
				}
			});
			
			if ($('#trigger_Time_Start').val() != ''
				&& $('#trigger_Time_End').val() != '') {
				var trigger_Time_Start = 
						$('#trigger_Time_Start').val() + ' ' +
						$('#trigger_Time_Hour_Start').val() + ':' +
						$('#trigger_Time_Minute_Start').val() + ':' +
						'00';
				vo['trigger_Time_Start']= trigger_Time_Start;
				var trigger_Time_End = 
						$('#trigger_Time_End').val() + ' ' +
						$('#trigger_Time_Hour_End').val() + ':' + 
						$('#trigger_Time_Minute_End').val() + ':' +
						'00';
				vo['trigger_Time_End'] = trigger_Time_End;
			}
			
			$('#list').jqGrid('setGridParam', {   
				url: '<%=request.getContextPath()%>/console/log/querypage',
				datatype: 'json',
				postData:{'input': jsonToString(vo)}, 
		        page: 1   
		    }).trigger('reloadGrid');
		}
		
	</script>
  </head>

  <body onload="init();" onkeydown="onEnterDown(query);" >
	<div id="button_div">
		<button id="query">查询</button>
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
			    	<th><label>执行ID</label></th>
			    	<td><input type="text" id="log_Id" maxlength="32"/></td>
			    	<th><label>执行类型</label></th>
			    	<td>
			    		<select id="exe_Type">
			    			<option value="">--all--</option>
			    			<option value="1">正常调度</option>
			    			<option value="2">子任务调度</option>
			    			<option value="3">重试调度</option>
			    			<option value="4">超时重试调度</option>
			    			<option value="5">人工调度</option>
			    		</select>
			    	</td>
			    	<th><label>调度状态</label></th>
			    	<td>
			    		<select id="exe_Status">
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
				<tr>
			    	<th><label>触发时间</label></th>
			    	<td colspan=3>
			    		<input type="text" id="trigger_Time_Start" style="width:23%;"/>
			    		<select name="trigger_Time_Hour_Start" id="trigger_Time_Hour_Start" style="width:10%;"></select>
			    		<select name="trigger_Time_Minute_Start" id="trigger_Time_Minute_Start" style="width:10%;"></select>
			    		~
			    		<input type="text" id="trigger_Time_End" style="width:23%;"/>
			    		<select name="trigger_Time_Hour_End" id="trigger_Time_Hour_End" style="width:10%;"></select>
			    		<select name="trigger_Time_Minute_End" id="trigger_Time_Minute_End" style="width:10%;"></select>
			    	</td>
			    	<th><label>超时状态</label></th>
			    	<td>
			    		<select id="timeout_Status">
			    			<option value="">--all--</option>
			    			<option value="1">未超时</option>
			    			<option value="2">已超时</option>
			    			<option value="3">已超时,重试成功</option>
			    			<option value="4">已超时,重试失败</option>
			    		</select>
			    	</td>
			    	<th><label>父执行ID</label></th>
			    	<td><input type="text" id="parent_Log_Id" maxlength="32"/></td>
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
