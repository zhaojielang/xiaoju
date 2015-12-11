<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/cmsTaglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<c:url value="/resources/styles/index/index.css"/>"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<table width="946" border="0" align="center" cellpadding="0"
		cellspacing="1" bgcolor="#0269AC">
		<tr>
			<td height="273" align="center" valign="middle" bgcolor="#FFFFFF"><table
					width="934" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="413" height="264"><img
							src="<c:url value='/resources/images/index/index_r4_c5.jpg'/>"
							width="414" height="263" />
						</td>
						<td width="521" align="right" valign="top"><ghost:infoFind
								var="news" type="12589405140001" size="1">
								<table width="512" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td height="60" align="center" valign="middle"><span
											class="STYLE7"><c:out value="${news.cmsTitle}" />
										</span>
										</td>
									</tr>
									<tr>
										<td height="145" align="left" valign="top" class="STYLE1"><c:out
												value="${news.indexDesc}" /> <c:if
												test="${not empty news.indexDesc}">……<a href="#"
													onclick="javascript:parent.changeUrl('<c:url value='/info/view.jhtml'/>?id=<c:out value='${news.cmsId}'/>')"><span
													class="STYLE4">[详细]</span>
												</a>
											</c:if></td>
									</tr>
									<tr>
										<td height="1" bgcolor="#b1b1b5"><img
											src="<c:url value='/resources/images/index/1.jpg'/>"
											width="1" height="1" />
										</td>
									</tr>
									<tr>
										<td height="57" align="center"><ghost:infoFind var="news"
												type="12589405140001" size="4" varStatus="status">
												<span class="title_x"> <c:if
														test="${news.cmsId != 1}">
														<a title='<c:out value="${news.cmsTitle}" />'
															href="javascript:parent.changeUrl('<c:url value='/info/view.jhtml'/>?id=<c:out value='${news.cmsId}'/>')">
															·<c:choose>
																<c:when test="${fn:length(news.cmsTitle) > 14}">
																	<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />
																</c:when>
																<c:otherwise>
																	<c:out value="${news.cmsTitle}" />
																</c:otherwise>
															</c:choose> </a>
													</c:if> <c:if test="${news.cmsId == 1}">
              		·<c:choose>
															<c:when test="${fn:length(news.cmsTitle) > 14}">
																<c:out value="${fn:substring(news.cmsTitle, 0, 14)}..." />
															</c:when>
															<c:otherwise>
																<c:out value="${news.cmsTitle}" />
															</c:otherwise>
														</c:choose>
													</c:if> </span>
												<c:if test="${0 == (status.index+1)%2}">
													<br />
												</c:if>
											</ghost:infoFind></td>
									</tr>
								</table>
							</ghost:infoFind></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>