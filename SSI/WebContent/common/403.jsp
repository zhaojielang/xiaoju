<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
setTimeout(go,5000);
function go() {
	if (top.location != self.location){top.location.replace("<c:url value='/'/>");return;}
	window.location.replace("<c:url value='/'/>");return false;
}
</script>
</head>
<body style="background: #FFF">
	<table
		style="margin-top: 100px; border: 1px dashed rgb(204, 204, 204);"
		align="center" border="0" cellpadding="6" cellspacing="0" width="785">
		<tbody>
			<tr>
				<td
					style="font-family: Arial, Helvetica, sans-serif; font-size: 18px; font-style: italic; color: rgb(204, 0, 0);"><strong>禁止访问</strong>
				</td>
			</tr>
			<tr>
				<td
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: rgb(51, 51, 51); text-indent: 20px"><a
					href="<c:url value='/'/>" onclick="go();return false;">点击进入网站首页</a><br />
					<ul>
						<li>非法访问系统信息</li>
						<li>用户未登录</li>
						<li>登录超时</li>
					</ul>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>