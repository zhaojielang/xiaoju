<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-2.1.4.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/easyui/jquery-easyui-1.4.4/jquery.easyui.min.js'/>"></script>
<link href="<c:url value='/resources/easyui/jquery-easyui-1.4.4/themes/default/easyui.css'/>" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/easyui/jquery-easyui-1.4.4/themes/icon.css'/>">

<link href="<c:url value='/resources/styles/style.css'/>" rel="stylesheet" type="text/css" />



<SCRIPT type="text/javascript">
// $.ajaxSetup({cache : false});
var contextPath = "<c:out value='${pageContext.request.contextPath}' />";
</SCRIPT>
<html:xhtml />