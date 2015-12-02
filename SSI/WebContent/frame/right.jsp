<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/cmsTaglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<c:url value="/resources/styles/index/index.css"/>" rel="stylesheet" type="text/css" />
</head>
<body>

<table width="707" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="229" height="251" align="left" valign="top" background="<c:url value='/resources/images/index/index_r7_c18.jpg'/>"><table width="229" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="30" height="30">&nbsp;</td>
            <td width="144">&nbsp;</td>
            <td width="55" align="center" valign="middle"><a href="#" onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9dtzx9list&type=12589405140001'/>');"><span class="STYLE4">更多&gt;&gt;</span></a></td>
          </tr>
		  <ghost:infoFind var="news" type="12589405140001" size="8">
		  <tr class="listTable">
			<c:if test="${news.cmsId != 1}">
            <td height="25" align="right" valign="bottom"><img src="<c:url value='/resources/images/index/b1.jpg'/>"/></td>
            <td colspan="2" align="left" valign="bottom">
        		<a title='<c:out value="${news.cmsTitle}" />' href="javascript:parent.changeUrl('<c:url value="/info/view.jhtml"/>?id=<c:out value="${news.cmsId}"/>')"><c:choose>   
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose>
				</a>
			</td>
			</c:if>
			<c:if test="${news.cmsId == 1}">
			<td height="25">&nbsp;</td>
            <td colspan="2" align="left" valign="bottom">
                <span><c:choose>
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose></span>
			</td>
			</c:if>
		  </tr>
		  </ghost:infoFind>
        </table></td>
        <td width="10">&nbsp;</td>
        <td width="229" align="left" valign="top" background="<c:url value='/resources/images/index/index02_r2_c4.jpg'/>"><table width="229" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="30" height="30">&nbsp;</td>
            <td width="144">&nbsp;</td>
            <td width="55" align="center" valign="middle"><a href="#" onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9zcfg9list&type=12589405130001'/>');"><span class="STYLE4">更多&gt;&gt;</span></a></td>
          </tr>
		  <ghost:infoFind var="news" type="12589405130001" size="8">
		  <tr class="listTable">
			<c:if test="${news.cmsId != 1}">
            <td height="25" align="right" valign="bottom"><img src="<c:url value='/resources/images/index/b1.jpg'/>"/></td>
            <td colspan="2" align="left" valign="bottom">
        		<a title='<c:out value="${news.cmsTitle}" />' href="javascript:parent.changeUrl('<c:url value="/info/view.jhtml"/>?id=<c:out value="${news.cmsId}"/>')"><c:choose>   
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose>
				</a>
			</td>
			</c:if>
			<c:if test="${news.cmsId == 1}">
			<td height="25">&nbsp;</td>
            <td colspan="2" align="left" valign="bottom">
                <span><c:choose>
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose></span>
			</td>
			</c:if>
		  </tr>
		  </ghost:infoFind>
        </table></td>
        <td width="10">&nbsp;</td>
        <td width="229" align="left" valign="top" background="<c:url value='/resources/images/index/index02_r2_c6.jpg'/>"><table width="229" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="30" height="30">&nbsp;</td>
            <td width="144">&nbsp;</td>
            <td width="55" align="center" valign="middle"><a href="#" onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9aqybx9list&type=12566221800001'/>');"><span class="STYLE4">更多&gt;&gt;</span></a></td>
          </tr>
		  <ghost:infoFind var="news" type="12566221800001" size="8">
		  <tr class="listTable">
			<c:if test="${news.cmsId != 1}">
            <td height="25" align="right" valign="bottom"><img src="<c:url value='/resources/images/index/b1.jpg'/>"/></td>
            <td colspan="2" align="left" valign="bottom">
        		<a title='<c:out value="${news.cmsTitle}" />' href="javascript:parent.changeUrl('<c:url value="/info/view.jhtml"/>?id=<c:out value="${news.cmsId}"/>')"><c:choose>   
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose>
				</a>
			</td>
			</c:if>
			<c:if test="${news.cmsId == 1}">
			<td height="25">&nbsp;</td>
            <td colspan="2" align="left" valign="bottom">
                <span><c:choose>
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose></span>
			</td>
			</c:if>
		  </tr>
		  </ghost:infoFind>
        </table></td>
      </tr>
      <tr>
        <td height="10" colspan="5"><img src="<c:url value='/resources/images/index/10.gif'/>" width="10" height="10" /></td>
      </tr>
      <tr>
        <td height="251" align="left" valign="top" background="<c:url value='/resources/images/index/index02_r4_c2.jpg'/>"><table width="229" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="30" height="30">&nbsp;</td>
            <td width="144">&nbsp;</td>
            <td width="55" align="center" valign="middle"><a href="#" onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9jdal9list&type=12566245330001'/>');"><span class="STYLE4">更多&gt;&gt;</span></a></td>
          </tr>
		  <ghost:infoFind var="news" type="12566245330001" size="8">
		  <tr class="listTable">
			<c:if test="${news.cmsId != 1}">
            <td height="25" align="right" valign="bottom"><img src="<c:url value='/resources/images/index/b1.jpg'/>"/></td>
            <td colspan="2" align="left" valign="bottom">
        		<a title='<c:out value="${news.cmsTitle}" />' href="javascript:parent.changeUrl('<c:url value="/info/view.jhtml"/>?id=<c:out value="${news.cmsId}"/>')"><c:choose>   
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose>
				</a>
			</td>
			</c:if>
			<c:if test="${news.cmsId == 1}">
			<td height="25">&nbsp;</td>
            <td colspan="2" align="left" valign="bottom">
                <span><c:choose>
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose></span>
			</td>
			</c:if>
		  </tr>
		  </ghost:infoFind>
        </table></td>
        <td>&nbsp;</td>
        <td align="left" valign="top" background="<c:url value='/resources/images/index/index02_r4_c4.jpg'/>"><table width="229" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="30" height="30">&nbsp;</td>
            <td width="144">&nbsp;</td>
            <td width="55" align="center" valign="middle"><a href="#" onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9bxsc9list&type=12566222460001'/>');"><span class="STYLE4">更多&gt;&gt;</span></a></td>
          </tr>
		  <ghost:infoFind var="news" type="12566222460001" size="8">
		  <tr class="listTable">
			<c:if test="${news.cmsId != 1}">
            <td height="25" align="right" valign="bottom"><img src="<c:url value='/resources/images/index/b1.jpg'/>"/></td>
            <td colspan="2" align="left" valign="bottom">
        		<a title='<c:out value="${news.cmsTitle}" />' href="javascript:parent.changeUrl('<c:url value="/info/view.jhtml"/>?id=<c:out value="${news.cmsId}"/>')"><c:choose>   
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose>
				</a>
			</td>
			</c:if>
			<c:if test="${news.cmsId == 1}">
			<td height="25">&nbsp;</td>
            <td colspan="2" align="left" valign="bottom">
                <span><c:choose>
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose></span>
			</td>
			</c:if>
		  </tr>
		  </ghost:infoFind>
        </table></td>
        <td>&nbsp;</td>
        <td align="left" valign="top" background="<c:url value='/resources/images/index/index02_r4_c6.jpg'/>"><table width="229" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="30" height="30">&nbsp;</td>
            <td width="144">&nbsp;</td>
            <td width="55" align="center" valign="middle"><a href="#" onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9gyjt9list&type=12571468430001'/>');"><span class="STYLE4">更多&gt;&gt;</span></a></td>
          </tr>
		  <ghost:infoFind var="news" type="12571468430001" size="8">
		  <tr class="listTable">
			<c:if test="${news.cmsId != 1}">
            <td height="25" align="right" valign="bottom"><img src="<c:url value='/resources/images/index/b1.jpg'/>"/></td>
            <td colspan="2" align="left" valign="bottom">
        		<a title='<c:out value="${news.cmsTitle}" />' href="javascript:parent.changeUrl('<c:url value="/info/view.jhtml"/>?id=<c:out value="${news.cmsId}"/>')"><c:choose>   
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose>
				</a>
			</td>
			</c:if>
			<c:if test="${news.cmsId == 1}">
			<td height="25">&nbsp;</td>
            <td colspan="2" align="left" valign="bottom">
                <span><c:choose>
					<c:when test="${fn:length(news.cmsTitle) > 17}">   
						<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />   
					</c:when>
					<c:otherwise>
						<c:out value="${news.cmsTitle}" />   
					</c:otherwise>
				</c:choose></span>
			</td>
			</c:if>
		  </tr>
		  </ghost:infoFind>
        </table></td>
      </tr>
    </table>
</body>
</html>
