<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<%@ page
	import="java.util.Enumeration,java.util.Map,java.util.Set,java.util.Iterator"%>
	
<link href="<c:url value='/resources/styles/main.css'/>" rel="stylesheet" type="text/css" />
<script type="text/javascript"
			src="<c:url value='/resources/scripts/jquery/jquery-1.3.2.min.js'/>"></script>

<table id="body_info" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%">
		<table id="info_t" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top" width="100%">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
					<%
						String un=(String)session.getAttribute("operName");
						String username = "未正常登录用户";
						if (null != un) {
							username=un;
						}
						String userRole  =(String)session.getAttribute("userRole");
						String userRoleDesc  =(String)session.getAttribute("userRoleDesc");
						String paltEntName=(String)session.getAttribute("paltEntName");
					%>	
						<input type="hidden" id="getUserName" name="getUserName" value="<%=username%>" />
						<input type="hidden" id="getUserRoleDesc" name="getUserRoleDesc" value="<%=userRoleDesc%>" />
						<input type="hidden" id="userRole" name="userRole" value="<%=userRole%>" />	
						<input type="hidden" id="paltEntName" name="paltEntName" value="<%=paltEntName%>" />	
						<td height="24"><label id="info_Users" style="width: 100%"></label></td>
						<td align="right" id="Exitt" style="font-size:1.2em;padding: 1px 0px 0px 0px"><a
							href="#"
							onclick="window.parent.document.location.replace('<c:url value='/'/>')">首页</a>&nbsp;&nbsp;&nbsp;
						</td>
						<td width="80" align="left"><img src="<c:url value='/resources/images/main/exit.gif'/>" style="border: none; cursor: pointer" onclick="closeWindow()" /></td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<script type="text/javascript">
	
	var userRole	 = $("#userRole").val();
	var paltEntName	 = $("#paltEntName").val();
	var userName	 = $("#getUserName").val();
	var userRoleDesc = $("#getUserRoleDesc").val();	
	
	var date = new Date();
	var dateStr = date.getFullYear()+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日";//+date.getHours()+"时"+date.getMinutes()+"分"
	
	if(userRole=="1"){
	  	$('#info_Users').html(userName + "，您好！您的角色是:["+userRoleDesc+"]，可以查看所有企业，今天是"+dateStr+"，欢迎使用本系统！");
	}else{
	    $('#info_Users').html(userName + "，您好！您的角色是:["+userRoleDesc+"]，所属企业为：["+paltEntName+"]，今天是"+dateStr+"，欢迎使用本系统！");
	}
	
	function closeWindow() {
		if(confirm("您确定要退出当前系统吗？")) {
			$.post("<c:url value='/logout.jhtml?bizType=logout'/>", {}, function(data){
				
				window.parent.parent.location.href = "<%=request.getContextPath()%>"+data;
				
			});
		}
	}
</script>