<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>测试系统</title>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="0">
	</head>
	<script type="text/javascript">
		try{
			if (parent != window){
				top.location.href = location.href;
			}
		} catch(e){
			top.location.reload();
		}
	</script>
		
	<frameset id="work" rows="78,39,*,23" frameborder="no" framespacing="0" >
  	<frame id="header" name="header" frameborder="no" noresize="noresize" scrolling="no" src="<%=request.getContextPath()%>/frame/header.jsp" />
  	<frame id="info" name="info" frameborder="no" noresize="noresize" scrolling="no" src="<%=request.getContextPath()%>/frame/info.jsp" />
  	<frameset id="main" name="main" cols="190,*" bordercolor="#B9D4E5" frameborder="yes" framespacing="1">
    <frame id="nav" name="nav" frameborder="no" noresize="noresize" scrolling="auto" src="<%=request.getContextPath()%>/frame/nav.jsp" />
    <frame id="framecontent" name="framecontent" frameborder="no" src="<%=request.getContextPath()%>/content.jsp" />
  	</frameset>
  	<frame id="footer" name="footer" noresize="noresize" scrolling="no" src="<%=request.getContextPath()%>/frame/footer.jsp" />
	</frameset><noframes></noframes>
	<body>
	</body>
</html>
