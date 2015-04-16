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
			
			$('#list').jqGrid({
		   		url: '<%=request.getContextPath()%>/console/executor/querypage',
				datatype: 'local',
				mtype: 'post',
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
				url: '<%=request.getContextPath()%>/console/executor/querypage',
				datatype: 'json',
				postData:{'input': jsonToString(vo)}, 
		        page: 1   
		    }).trigger('reloadGrid');
		}
		
		function insert() {
			top.openDialog('执行机器新增', '<%=request.getContextPath()%>/view/console/executor/executor_edit.jsp', null, 260, 700, query);
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
			top.openDialog('执行机器修改', '<%=request.getContextPath()%>/view/console/executor/executor_edit.jsp', ret, 260, 700, query);					
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
				url: '<%=request.getContextPath()%>/console/executor/deletes',
				jsonSet: jsonSet
			});	
		}
	</script>
  </head>

  <body onload="init();" onkeydown="onEnterDown(query);" >
	<div id="button_div">
		<button id="query">查询</button>
		<button id="insert">新增</button>
		<button id="update">修改</button>
		<button id="deletes">删除</button>
	</div>
	<div id="query_div">
		<div id="form" style="width: auto;">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="s_layout">
				<colexecutor>
					<col width="10%" />
					<col width="15%" />
					<col width="10%" />
					<col width="15%" />
					<col width="10%" />
					<col width="15%" />
					<col width="10%" />
					<col width="15%" />
				</colexecutor>					
			    <tr>
			    	<th><label>执行机器ID</label></th>
			    	<td><input type="text" id="executor_Id" maxlength="10"/></td>
			    	<th><label>执行机器名称</label></th>
			    	<td><input type="text" id="executor_Name" maxlength="32"/></td>
			    	<th><label>RPC类型</label></th>
			    	<td>
			    		<select id="rpc_Type">
			    			<option value="">--all--</option>
			    			<option value="http">HTTP</option>
			    		</select>
			    	</td>
			    	<th><label>IP</label></th>
			    	<td><input type="text" id="ip" maxlength="128"/></td>
				</tr>			
			    <tr>
			    	<th><label>PORT</label></th>
			    	<td><input type="text" id="port" maxlength="5"/></td>
			    	<th><label>URL</label></th>
			    	<td><input type="text" id="url" maxlength="128"/></td>
			    	<th><label>状态</label></th>
			    	<td>
			    		<select id="status">
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
	<div id="list_div" style="width:auto;">
		<table id="list"></table>
		<div id="pager"></div>
	</div>
  </body>
</html>
