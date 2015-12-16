<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户管理</title>
	<script type="text/javascript">
		$(document).ready(function(){
			queryList();
		});
		
		function queryList(){
			var url = "";
			$("#dgTable").datagrid({
				url : url,
				columns : [[
				          {field:'id', title:'序号',width:fixWidth(0.1)},  
				          {field:'id', title:'姓名',width:fixWidth(0.2)},  
				          {field:'id', title:'手机号',width:fixWidth(0.2)},  
				          {field:'id', title:'最后登录时间',width:fixWidth(0.2)},
				          {field:'id', title:'状态',width:fixWidth(0.2)}
				          ]],
			});
		}
		
		//百分比
	    function fixWidth(percent){
	        return document.body.clientWidth * percent ; //这里你可以自己做调整  
	    }  
	</script>
</head>
    <body>
    	<div style="width: 96%; height: 100%; margin: 15px 2% 10px 2%;">
	        	<form action="">
	        		<table class="table_all_border" style="margin-bottom: -6px; border-bottom: 0px;height: 10%;">
							<tr>
								<td class="td_table_top">
									查询条件
								</td>
							</tr>
					</table>
	        		<table class="table_all_border" style="height: 20%;">
	        			<tr>
	        				<td class="td_table_1">用户名：</td>
	        				<td class="td_table_2">
			        			<input class="input_240" type="text" name="name" />    
	        				</td>
	        				<td class="td_table_1">状 态：</td>
	        				<td class="td_table_2">
			        			<input class="input_240" type="text" name="email" />    
	        				</td>
	        			</tr>
	        		</table>
	        		<div style="width: 98%; text-align: center;">
	        			<a onclick="" class="easyui-linkbutton" data-options="iconCls:'icon-search'" >查询</a>
	        			<a onclick="" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" >重置</a>
	        			<a onclick="" class="easyui-linkbutton" data-options="iconCls:'icon-print'" >导出</a>
	        		</div>
	        	</form>
	        	<table id="dgTable" class="table_all_border" style="height: 70%;"></table>
    	</div>
    </body>
</html>