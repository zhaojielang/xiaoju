//查看修改历史记录
function lookLog(hisId){
	$('#showHistoryForm').attr("action",contextPath + "/log/detailQuery.jhtml?hisId="+hisId);
	//$.blockUI();
	$('#showHistoryForm').ajaxForm({
		target:"#formShowHistoryDetail",
		error: function () {
			//$.unblockUI();
		    alert("操作异常,请刷新后重试!");
		    return false;
		},
		success: function(data){
			//$.unblockUI();	
			$('#list').hide();
			$('#formShowHistoryDetail').show();
			return false;
		}
	}).submit();
	return false;
}

function lookUp(hisId){
	$('#detailHistoryForm').attr("action",contextPath + "/log/regDetail.jhtml?hisId="+hisId);
	//$.blockUI();
	$('#detailHistoryForm').ajaxForm({
		target:"#formRegHistoryDetail",
		error: function () {
			//$.unblockUI();
		    alert("操作异常,请刷新后重试!");
		    return false;
		},
		success: function(data){	
			//$.unblockUI();
			$('#list').hide();
			$('#formShowHistoryDetail').hide();
			$('#formRegHistoryDetail').show();
			return false;
		}
	}).submit();
	return false;
}

//注册历史详情返回
function regGoBack(){
	$('#list').hide();
	$('#formRegHistoryDetail').html("");
	$('#formRegHistoryDetail').hide();
	$('#formShowHistoryDetail').show();
}

//历史返回
function hisGoBack(){
	$('#list').show();
	$('#formShowHistoryDetail').html("");
	$('#formShowHistoryDetail').hide();
}

function showImage(imageId,type){
    
	if('' == imageId){
		alert("该影像文件不存在！");
		return false;
	}
	window.open(contextPath+"/log/openImages.jhtml?imageId="+imageId+"&type="+type);
	/*
	var u=contextPath+"/log/openImages.jhtml?imageId="+imageId+"&type="+type;
	$.get(u,function(data){
		if(data == "no"){
			alert("该影像文件不存在2！") ;
		} 
	}) ;
	*/
}
