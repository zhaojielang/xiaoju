<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
</head>
<body>
	<form action="<%=request.getContextPath() %>/user/regist.action" method="post">
		用户名:<input id="username" name="userForm.username">
		密码:<input id="password" name="userForm.password">
		<input type="submit" value="提交">
	</form>
</body>
</html>