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
			$('#insert').button().click(function() {
				insert();
			});
			$('#update').button().click(function() {
				update();
			});
			$('#deletes').button().click(function() {
				deletes();
			});
			$('#insertChild').button().click(function() {
				insertChild();
			});
			$('#updateChild').button().click(function() {
				updateChild();
			});
			$('#setExecutor').button().click(function() {
				setExecutor();
			});
			
			$('#list').jqGrid({
		   		url: '<%=request.getContextPath()%>/console/job/querypage',
				datatype: 'local',
				mtype: 'post',
			   	colNames: [
					'任务ID', 
					'任务名称', 
					'调度类型',
					'cron表达式',
					'执行间隔(秒)',
					'路由规则',
					'执行方法',
					'超时时间(秒)',
					'执行失败重试',
					'超时重试',
					'父任务ID',
					'最后修改时间',
					'有效状态',
					'描述'
					],
			   	colModel: [
					{name:'job_Id', index:'job_Id', width:80, align: 'center'},
					{name:'job_Name', index:'job_Name', width:280, align: 'center'},
					{name:'job_Type', index:'job_Type', width:100, align: 'center', formatter:'select', editoptions:{value:'quartz:普通调度;completion:完成后调度;child:子任务调度'}},
					{name:'cron', index:'cron', width:160, align: 'center'},
					{name:'delay_Time', index:'delay_Time', width:120, align: 'center'},
					{name:'route_Type', index:'route_Type', width:100, align: 'center', formatter:'select', editoptions:{value:'first:第一台开始;random:随机;sequence:循环顺序;all:全部执行'}},
					{name:'method', index:'method', width:220, align: 'center'},
					{name:'exe_Timeout', index:'exe_Timeout', width:120, align: 'center'},
					{name:'exe_Retry', index:'exe_Retry', width:100, align: 'center', formatter:'select', editoptions:{value:'1:是;2:否'}},
					{name:'timeout_Retry', index:'timeout_Retry', width:100, align: 'center', formatter:'select', editoptions:{value:'1:是;2:否'}},
					{name:'parent_Job_Id', index:'parent_Job_Id', width:80, align: 'center'},
					{name:'lastModified_Time', index:'lastModified_Time', width:200, align:'center', formatter:'date', formatoptions:{srcformat:'Y-m-d H:i:s.sss', newformat:'Y-m-d H:i:s'}},
					{name:'status', index:'status', width:100, align: 'center', formatter:'select', editoptions:{value:'1:有效;2:无效'}},
					{name:'description', index:'description', width:500, align: 'left'}
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
				url: '<%=request.getContextPath()%>/console/job/querypage',
				datatype: 'json',
				postData:{'input': jsonToString(vo)}, 
		        page: 1   
		    }).trigger('reloadGrid');
		}
		
		function insert() {
			top.openDialog('任务新增', '<%=request.getContextPath()%>/view/console/job/job_edit.jsp', null, 410, 700, query);
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
			top.openDialog('任务修改', '<%=request.getContextPath()%>/view/console/job/job_edit.jsp', ret, 410, 700, query);					
		}
		
		function deletes() {
			
			var index = jQuery('#list').jqGrid('getGridParam', 'selarrrow');
			if (index.toString() == '') {
				alert('请选择');
				return;
			}
			
			if(!window.confirm('你确定要删除吗？')){
				return false;
            }
			
			var retArray = new Array();
			var indexArray = index.toString().split(',');
			for (var i=0; i<indexArray.length; i++) {				
				var ret = jQuery('#list').jqGrid('getRowData', indexArray[i]);
				ret.register_Dt = null;
				ret.last_Login_Dt = null;
				retArray.push(ret);
			}
			
			var jsonSet = new JsonSet();
			jsonSet.put('input', retArray);
			
			transaction({
				id: 'DELETES',
				url: '<%=request.getContextPath()%>/console/job/deletes',
				jsonSet: jsonSet
			});	
		}
		
		function insertChild() {
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
			top.openDialog('子任务新增', '<%=request.getContextPath()%>/view/console/job/job_child_edit.jsp?parent_Job_Id=' + ret['job_Id'], null, 410, 700, query);
		}
		
		function updateChild() {
			
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
			if (ret['job_Type'] != 'child') {
				alert('记录不是子任务类型');
				return;
			}
			top.openDialog('子任务修改', '<%=request.getContextPath()%>/view/console/job/job_child_edit.jsp', ret, 440, 700, query);					
		}
		
		function setExecutor() {
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
			top.openDialog('设置执行机器', '<%=request.getContextPath()%>/view/console/job/job-executor/job_executor_main.jsp', ret, 600, 1200, null);					
		}
	</script>
  </head>

  <body onload="init();" onkeydown="onEnterDown(query);" >
	<div id="button_div">
		<button id="query">查询</button>
		<button id="insert">新增</button>
		<button id="update">修改</button>
		<button id="deletes">删除</button>
		<button id="insertChild">新增子任务</button>
		<button id="updateChild">修改子任务</button>
		<button id="setExecutor">设置执行机器</button>
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
			    	<th><label>任务调度类型</label></th>
			    	<td>
			    		<select id="job_Type">
			    			<option value="">--all--</option>
			    			<option value="quartz">普通调度</option>
			    			<option value="completion">完成后调度</option>
			    			<option value="child">子任务调度</option>
			    		</select>
			    	</td>
			    	<th><label>路由规则</label></th>
			    	<td>
			    		<select id="route_Type">
			    			<option value="">--all--</option>
			    			<option value="first">第一台开始</option>
			    			<option value="random">随机</option>
			    			<option value="sequence">循环顺序</option>
			    			<option value="all">全部执行</option>
			    		</select>
			    	</td>
				</tr>					
			    <tr>
			    	<th><label>执行方法</label></th>
			    	<td><input type="text" id="method" maxlength="32"/></td>
			    	<th><label>执行状态</label></th>
			    	<td>
			    		<select id="exe_Status">
			    			<option value="">--all--</option>
			    			<option value="1">等待调度</option>
			    			<option value="2">正在触发</option>
			    			<option value="3">正在执行</option>
			    		</select>
			    	</td>
			    	<th><label>父任务ID</label></th>
			    	<td><input type="text" id="parent_Job_Id" maxlength="32"/></td>
			    	<th><label>有效状态</label></th>
			    	<td>
			    		<select id="status">
			    			<option value="">--all--</option>
			    			<option value="1">有效</option>
			    			<option value="2">无效</option>
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
