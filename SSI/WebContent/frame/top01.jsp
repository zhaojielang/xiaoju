<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/cmsTaglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<c:url value="/resources/styles/index/index.css"/>"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<c:url value='/resources/scripts/dateUtil.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/scripts/index/index.js'/>"></script>
<script type="text/javascript">
$(document).ready(function(){
	var date = new Date();
	
	$('#weekId').html(date.pattern("yyyy年MM月dd日 EEE"));
	return false;	
});
</script>

</head>
<body>

	<table width="100%" border="0" align="center" cellpadding="0"
		cellspacing="0" id="nav">
		<tr>
			<td bgcolor="#56B1E6">
				<center>
					<table width="1259" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="97" align="right"
								background="<c:url value='/resources/images/index/index_r1_c1.jpg'/>">
								<table width="365" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="82" height="35" align="center" valign="bottom"
											class="sc"><a href="javascript:void(0);"
											onclick="SetHome(this)">设为首页</a>
										</td>
										<td width="84" height="35" align="center" valign="bottom"
											class="sc"><a href="javascript:void(0);"
											onclick="addFav()">加入收藏</a>
										</td>
										<td width="199">&nbsp;</td>
									</tr>
									<tr>
										<td height="34" colspan="2" align="center" valign="middle"
											id="weekId" class="STYLE5">&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table width="1259" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="156"><img
								src="<c:url value='/resources/images/index/index_r2_c1.jpg'/>"
								width="156" height="35" />
							</td>
							<td width="126" height="35" style="cursor: pointer" title="进入首页"
								onclick="parent.changeLocalUrl('<c:url value='/index.jsp'/>')"
								background="<c:url value='/resources/images/index/index_r2_c3.jpg'/>"></td>
							<td width="124" align="center" valign="middle"
								background="<c:url value='/resources/images/index/index_r2_c10.jpg'/>"
								class="dh"><a href="#"
								onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9dtzx9list&type=12589405140001'/>')"><ghost:subjectTitle
										channelId="12589405140001" />
							</a></td>
							<td width="124" align="center" valign="middle"
								background="<c:url value='/resources/images/index/index_r2_c19.jpg'/>"
								class="dh"><a href="#"
								onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9zcfg9list&type=12589405130001'/>')"><ghost:subjectTitle
										channelId="12589405130001" />
							</a></td>
							<td width="124" align="center"
								background="<c:url value='/resources/images/index/index_r2_c22.jpg'/>"
								class="dh"><a href="#"
								onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9aqybx9list&type=12566221800001'/>')"><ghost:subjectTitle
										channelId="12566221800001" />
							</a></td>
							<td width="124" align="center"
								background="<c:url value='/resources/images/index/index_r2_c26.jpg'/>"
								class="dh"><a href="#"
								onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9jdal9list&type=12566245330001'/>')"><ghost:subjectTitle
										channelId="12566245330001" />
							</a></td>
							<td width="140" align="center"
								background="<c:url value='/resources/images/index/index_r2_c27.jpg'/>"
								class="dh"><a href="#"
								onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9bxsc9list&type=12566222460001'/>')"><ghost:subjectTitle
										channelId="12566222460001" />
							</a></td>
							<td width="145" align="center"
								background="<c:url value='/resources/images/index/index_r2_c30.jpg'/>"
								class="dh"><a href="#"
								onclick="parent.changeUrl('<c:url value='/info/find.jhtml?pageName=news9gyjt9list&type=12571468430001'/>')"><ghost:subjectTitle
										channelId="12571468430001" />
							</a></td>
							<td width="196"
								background="<c:url value='/resources/images/index/index_r2_c33.jpg'/>">&nbsp;</td>
						</tr>
					</table>
				</center></td>
		</tr>
	</table>
</body>
</html>