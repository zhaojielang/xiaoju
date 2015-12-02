<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/functions" prefix="fn" %>

<script type="text/javascript" src="<c:url value='/resources/scripts/jquery/jquery-1.3.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/scripts/jquery/jquery.form.js'/>"></script>
<link href="<c:url value="/resources/styles/common/public.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value='/resources/scripts/index/index.js'/>"></script>

<SCRIPT type="text/javascript">
$.ajaxSetup({cache : false});
var contextPath = "<c:out value='${pageContext.request.contextPath}' />";
</SCRIPT>
<html:xhtml />