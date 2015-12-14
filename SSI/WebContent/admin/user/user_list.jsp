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
				          {field:'id', title:'序号',width:fixWidth(0.2)},  
				          {field:'id', title:'姓名',width:fixWidth(0.2)},  
				          {field:'id', title:'手机号',width:fixWidth(0.2)},  
				          {field:'id', title:'最后登录时间',width:fixWidth(0.2)},
				          {field:'id', title:'状态',width:fixWidth(0.2)}
				          ]],
			});
		}
		
	    function fixWidth(percent)  
	    {  
	        return document.body.clientWidth * percent ; //这里你可以自己做调整  
	    }  
	</script>
</head>
    <body>
    	<div style="width: 100%; height: 100%;">
	        	<form action="">
	        		<table class="table_all_border" style="border-bottom: 0px;height: 10%;">
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
			        			<input class="input_240" type="text" name="name" data-options="required:'请输入'" />    
	        				</td>
	        				<td>状 态：</td>
	        				<td>
			        			<input class="easyui-validatebox" type="text" name="email" data-options="validType:'email'" />    
	        				</td>
	        			</tr>
	        		</table>
	        	</form>
	        	<table id="dgTable" style="height: 70%;"></table>
    	</div>
    </body>
</html>