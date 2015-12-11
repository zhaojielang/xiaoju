<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>

&nbsp;&nbsp;&nbsp;&nbsp;第${pagingBaseModel.currentPage}页 /
共${pagingBaseModel.totalPage}页
${pagingBaseModel.totalRow}条记录&nbsp;&nbsp;&nbsp;&nbsp;
<c:choose>
	<c:when test="${pagingBaseModel.currentPage>1}">
		<a href="#" onclick="paging(1,${pagingBaseModel.totalRow})">首页</a>
	</c:when>
	<c:otherwise>首页</c:otherwise>
</c:choose>
&nbsp;&nbsp;&nbsp;&nbsp;
<c:choose>
	<c:when test="${pagingBaseModel.currentPage>1}">
		<a href="#"
			onclick="paging(${pagingBaseModel.previousPage},${pagingBaseModel.totalRow})">上一页</a>
	</c:when>
	<c:otherwise>上一页</c:otherwise>
</c:choose>
&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
<c:choose>
	<c:when test="${pagingBaseModel.currentPage<pagingBaseModel.totalPage}">
		<a href="#"
			onclick="paging(${pagingBaseModel.nextPage},${pagingBaseModel.totalRow})">下一页</a>
	</c:when>
	<c:otherwise>下一页</c:otherwise>
</c:choose>
&nbsp;&nbsp;&nbsp;&nbsp;
<c:choose>
	<c:when test="${pagingBaseModel.currentPage<pagingBaseModel.totalPage}">
		<a href="#"
			onclick="paging(${pagingBaseModel.totalPage},${pagingBaseModel.totalRow})">末页</a>
	</c:when>
	<c:otherwise>末页</c:otherwise>
</c:choose>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
