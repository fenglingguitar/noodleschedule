<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../../../global.jsp"%>
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
	    	if (trnId == 'INSERTS') {
    			if (data.result == 'false') {
    				alert('新增失败');
    			} else {
    				alert('新增成功');
    				query();
    			}
			} else if (trnId == 'DELETES') {
				if (data.result == 'false') {
					alert('删除失败');
				} else {
					alert('删除成功');
					query();
				}
			}
		}
    
		function init() {
			
			$('#query_left').button().click(function() {
				queryLeft();
			});
			$('#close_left').button().click(function() {
				top.closeDialog(false);
			});
			$('#query_right').button().click(function() {
				queryRight();
			});
			$('#close_right').button().click(function() {
				top.closeDialog(false);
			});
			$('#inserts').button().click(function() {
				inserts();
			});
			$('#deletes').button().click(function() {
				deletes();
			});
			$('#update').button().click(function() {
				update();
			});
			
			var urlParamObject = getURLParamObject();
			if (urlParamObject != null) {				
				$('#form_left :input').each(function(i){
					if (urlParamObject[$(this).attr('id')] != null) {					
						$(this).val(urlParamObject[$(this).attr('id')]);
					}
				});
				$('#job_Id').attr('disabled', 'disabled');
			}
			
			var paramObject = {'job_Id': urlParamObject.job_Id};
			
			$('#list_left').jqGrid({
		   		url: '<%=request.getContextPath()%>/console/job/executor/queryincludepage',
				datatype: 'json',
				mtype: 'post',
				postData:{'input': jsonToString(paramObject)}, 
				colNames: [
					'任务ID', 
					'执行机器ID', 
					'排序',
					'权重',
					'执行机器名称',
					'RPC类型',
					'IP',
					'PORT',
					'URL',
					'有效状态'
					],
			   	colModel: [
					{name:'job_Id', index:'job_Id', width:80, align: 'center'},
					{name:'executor_Id', index:'executor_Id', width:80, align: 'center'},
					{name:'exe_Index', index:'exe_Index', width:80, align: 'center'},
					{name:'weight', index:'weight', width:80, align: 'center'},
					{name:'executor_Name', index:'executor_Name', width:280, align: 'center'},
					{name:'rpc_Type', index:'rpc_Type', width:100, align: 'center'},
					{name:'ip', index:'ip', width:220, align: 'center'},
					{name:'port', index:'port', width:80, align: 'center'},
					{name:'url', index:'url', width:280, align: 'center'},
					{name:'status', index:'status', width:100, align: 'center', formatter:'select', editoptions:{value:'1:有效;2:无效'}},
			   	],
			   	rowNum: 10,
			   	rowList: [10,20,30,40,50,100],
			   	pager: '#pager_left',
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
			    	repaintGrid(247, "query_left_div", "list_left", "button_left_div");
			    }
			});
				
			$('#list_left').jqGrid('navGrid', '#pager_left', {search:false, edit:false, add:false, del:false});
			
			$('#list_right').jqGrid({
		   		url: '<%=request.getContextPath()%>/console/job/executor/queryexcludepage',
				datatype: 'json',
				mtype: 'post',
				postData:{'input': jsonToString(paramObject)}, 
				colNames: [
					'执行机器ID', 
					'执行机器名称',
					'RPC类型',
					'IP',
					'PORT',
					'URL',
					'有效状态'
					],
			   	colModel: [
					{name:'executor_Id', index:'executor_Id', width:80, align: 'center'},
					{name:'executor_Name', index:'executor_Name', width:280, align: 'center'},
					{name:'rpc_Type', index:'rpc_Type', width:100, align: 'center'},
					{name:'ip', index:'ip', width:220, align: 'center'},
					{name:'port', index:'port', width:80, align: 'center'},
					{name:'url', index:'url', width:280, align: 'center'},
					{name:'status', index:'status', width:100, align: 'center', formatter:'select', editoptions:{value:'1:有效;2:无效'}},
			   	],
			   	rowNum: 10,
			   	rowList: [10,20,30,40,50,100],
			   	pager: '#pager_right',
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
			    	repaintGrid(247, "query_right_div", "list_right", "button_right_div");
			    }
			});
				
			$('#list_right').jqGrid('navGrid', '#pager_right', {search:false, edit:false, add:false, del:false});
			
			$(window).resize(function(){ 
				$("#list_left").setGridWidth(Number($(window).width()) * (45 / 100));
				$("#list_right").setGridWidth(Number($(window).width()) * (45 / 100));
			});
		}
		
		function query() {
			
			queryLeft();
			queryRight();
		}
		
		function queryLeft() {
			
			var vo = new Object();
			$('#form_left :input').each(function(i){
				if ($(this).val() != '') {					
					vo[$(this).attr('id').replace(/_left/, '')] = $(this).val();
				}
			 });
			
			$('#list_left').jqGrid('setGridParam', {   
				url: '<%=request.getContextPath()%>/console/job/executor/queryincludepage',
				postData:{'input': jsonToString(vo)}, 
		        page: 1   
		    }).trigger('reloadGrid');
		}
		
		function queryRight() {
			
			var vo = new Object();
			$('#form_right :input').each(function(i){
				if ($(this).val() != '') {					
					vo[$(this).attr('id').replace(/_right/, '')] = $(this).val();
				}
			 });
			
			vo['job_Id'] = $('#job_Id').val();
			
			$('#list_right').jqGrid('setGridParam', {   
				url: '<%=request.getContextPath()%>/console/job/executor/queryexcludepage',
				postData:{'input': jsonToString(vo)}, 
		        page: 1   
		    }).trigger('reloadGrid');
		}
		
		function inserts() {
			
			var index = jQuery('#list_right').jqGrid('getGridParam', 'selarrrow');
			if (index.toString() == '') {
				alert('请选择');
				return;
			}
			
			var retArray = new Array();
			var indexArray = index.toString().split(',');
			for (var i=0; i<indexArray.length; i++) {				
				var ret = jQuery('#list_right').jqGrid('getRowData', indexArray[i]);
				ret['job_Id'] = $('#job_Id').val();
				ret['weight'] = 1;
				ret['exe_Index'] = 1;
				retArray.push(ret);
			}
			
			var jsonSet = new JsonSet();
			jsonSet.put('input', retArray);
			
			transaction({
				id: 'INSERTS',
				url: '<%=request.getContextPath()%>/console/job/executor/inserts',
				jsonSet: jsonSet
			});	
		}
		
		function deletes() {
			
			var index = jQuery('#list_left').jqGrid('getGridParam', 'selarrrow');
			if (index.toString() == '') {
				alert('请选择');
				return;
			}
			
			var retArray = new Array();
			var indexArray = index.toString().split(',');
			for (var i=0; i<indexArray.length; i++) {				
				var ret = jQuery('#list_left').jqGrid('getRowData', indexArray[i]);
				retArray.push(ret);
			}
			
			var jsonSet = new JsonSet();
			jsonSet.put('input', retArray);
			
			transaction({
				id: 'DELETES',
				url: '<%=request.getContextPath()%>/console/job/executor/deletes',
				jsonSet: jsonSet
			});	
		}
		
		function update() {
			
			var index = jQuery('#list_left').jqGrid('getGridParam', 'selarrrow');
			if (index.toString() == '') {
				alert('请选择');
				return;
			}
			
			var indexArray = index.toString().split(',');
			if (indexArray.length > 1) {
				alert('只能选择一行');
				return;
			}
			
			var ret = jQuery('#list_left').jqGrid('getRowData', index);
			top.openDialogChild('修改', '<%=request.getContextPath()%>/view/console/job/job-executor/job_executor_edit.jsp', ret, 230, 700, queryLeft);					
		}
		
	</script>
  </head>

  <body onload="init();" onkeydown="onEnterDown(query);" >
	<div style="width: auto;">
		<table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
			<colgroup>
				<col width="45%" />
				<col width="10%" />
				<col width="45%" />
			</colgroup>
			<tr>
				<td valign="top">
					<div id="button_left_div">
						<button id="query_left">查询</button>
						<button id="close_left">关闭</button>
						<button id="update">修改</button>
					</div>
					<div id="query_left_div">
						<div id="form_left" style="width: auto;">
							<table width="100%" border="1" cellspacing="0" cellpadding="0" class="s_layout">
								<colgroup>
									<col width="20%" />
									<col width="30%" />
									<col width="20%" />
									<col width="30%" />
								</colgroup>					
							    <tr>
							    	<th><label>任务ID</label></th>
			    					<td><input type="text" id="job_Id" maxlength="32"/></td>
							    	<th><label>执行机器ID</label></th>
			    					<td><input type="text" id="executor_Id_left" maxlength="10"/></td>
							    </tr>		
							    <tr>
			    					<th><label>执行机器名称</label></th>
							    	<td><input type="text" id="executor_Name_left" maxlength="32"/></td>
							    	<th><label>RPC类型</label></th>
							    	<td>
							    		<select id="rpc_Type_left">
							    			<option value="">--all--</option>
							    			<option value="http">HTTP</option>
							    		</select>
							    	</td>
							    </tr>
							    <tr>
							    	<th><label>IP</label></th>
							    	<td><input type="text" id="ip_left" maxlength="15"/></td>
							    	<th><label>PORT</label></th>
							    	<td><input type="text" id="port_left" maxlength="5"/></td>
								</tr>
							    <tr>
							    	<th><label>URL</label></th>
							    	<td><input type="text" id="url_left" maxlength="128"/></td>
							    	<th><label>状态</label></th>
							    	<td>
							    		<select id="status_left">
							    			<option value="">--all--</option>
							    			<option value="1">有效</option>
							    			<option value="2">无效</option>
							    		</select>
							    	</td>
								</tr>
							</table>
						</div>
					</div>
					<div id="list_left_div" style="width:auto;">
						<table id="list_left"></table>
						<div id="pager_left"></div>
					</div>
				</td>
				<td align="center">
					<div>
						<button id="inserts">&nbsp;新增&nbsp;</button>
					</div>
					<br/>
					<div>
						<button id="deletes">&nbsp;删除&nbsp;</button>
					</div>
				</td>
				<td valign="top">
					<div id="button_right_div">
						<button id="query_right">查询</button>
						<button id="close_right">关闭</button>
					</div>
					<div id="query_right_div">
						<div id="form_right" style="width: auto;">
							<table width="100%" border="1" cellspacing="0" cellpadding="0" class="s_layout">
								<colgroup>
									<col width="10%" />
									<col width="15%" />
									<col width="10%" />
									<col width="15%" />
								</colgroup>			
								<tr>
							    	<th><label>执行机器ID</label></th>
			    					<td><input type="text" id="executor_Id_right" maxlength="10"/></td>
			    					<th><label>执行机器名称</label></th>
							    	<td><input type="text" id="executor_Name_right" maxlength="32"/></td>
							    </tr>		
							    <tr>
							    	<th><label>RPC类型</label></th>
							    	<td>
							    		<select id="rpc_Type_right">
							    			<option value="">--all--</option>
							    			<option value="http">HTTP</option>
							    		</select>
							    	</td>
							    	<th><label>IP</label></th>
							    	<td><input type="text" id="ip_right" maxlength="15"/></td>
							    </tr>
							    <tr>
							    	<th><label>PORT</label></th>
							    	<td><input type="text" id="port_right" maxlength="5"/></td>
							    	<th><label>URL</label></th>
							    	<td><input type="text" id="url_right" maxlength="128"/></td>
								</tr>
							    <tr>
							    	<th><label>状态</label></th>
							    	<td>
							    		<select id="status_right">
							    			<option value="">--all--</option>
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
					<div id="list_right_div" style="width:auto;">
						<table id="list_right"></table>
						<div id="pager_right"></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
  </body>
</html>
