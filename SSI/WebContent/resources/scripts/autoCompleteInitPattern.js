// JavaScript Document
//function InitAutoCompleteInputBox("supplyNo",undefined,"supplyShortName","http://...",formatListItemExample,formatResultDataExample);

function formatListItemExample(item) {
	var t="<div>"+item.supplyNo + "&nbsp;&nbsp;&nbsp;" + item.supplyShortName+"</div>";
	return t;
}

  function formatResultDataExample(attachInputBoxId,listSelectedKeyId,listSelectedValueId,item) {
	  $("#"+attachInputBoxId).val(item.supplyNo); 
		if(listSelectedKeyId!=undefined&&listSelectedKeyId!=""){
			$("#"+listSelectedKeyId).val(item.supplierId);
		}
		if(listSelectedValueId!=undefined&&listSelectedValueId!=""){
			$("#"+listSelectedValueId).val(item.supplyShortName);
		}  
  }
  
  
function formatGoodsListItem(item) {
	var t="<div>"+item.goodsSn + "&nbsp;&nbsp;&nbsp;" + item.goodsName+"</div>";
	return t;
}

  function formatGoodsResultData(attachInputBoxId,listSelectedKeyId,listSelectedValueId,item) {
	  $("#"+attachInputBoxId).val(item.goodsSn); 
		if(listSelectedKeyId!=undefined&&listSelectedKeyId!=""){
			$("#"+listSelectedKeyId).val(item.goodsId);
		}
		if(listSelectedValueId!=undefined&&listSelectedValueId!=""){
			$("#"+listSelectedValueId).val(item.goodsName);
		}  
		
		$("#shopPriceId").val(item.shopPrice);
		$("#goodsBriefId").val(item.goodsBrief);
		$("#measureUnitId").val(item.measureUnit);
		
  }
  

function InitAutoCompleteInputBox(attachInputBoxId,listSelectedKeyId,listSelectedValueId,actionURL,formatListItemFunc,formatResultDataFunc){
		$("#"+attachInputBoxId).autocomplete(actionURL,{   
	                                   delay:400,//延迟10秒   
	                                   max:10,//最多5条记录   
	                                   minChars:1,//在触发autoComplete前用户至少需要输入的字符数
	                                   multiple:true,//是否允许输入多个值即多次使用autoComplete以输入多个值   
	                                   matchSubset:false,
	                                   mustMatch:false,   
									   matchCase:false,
	                                   matchContains:1,   
	                                   cacheLength:1,//不缓存   
	                                   matchContains: true,      
	                                   scrollHeight: 250,    
	                                   width:250,   
	                                   dataType:'json',//返回的数据类型为JSON类型   
	                                   parse:function(data) {//解释返回的数据，把其存在数组里   
	                                       var parsed = [];   
	                                       for (var i = 0; i < data.length; i++) {   
	                                           parsed[parsed.length] = {   
	                                           data: data[i]/*,   
	                                           value: data[i].itemid,   
	                                           result: data[i].itemid //返回的结果显示内容   */
	                                           };   
	                                       }   
	                                       return parsed;   
	                                   },   
	                                   formatItem: function(item) {//显示下拉列表的内容   
									   	if(formatListItemFunc) {
											return formatListItemFunc(item);   
										} else {
											return "<div> 你还没有定义列表数据项的格式</div>";
										}
										 },   
										formatMatch: function(item) {   
										   // return item.itemid;   
										},   
										formatResult: function(item) {   
										//return item.itemid;   
										}   
										 }).result(function(event, item, formatted) {//把返回的结果内容显示在其他文本框上               
											 if(formatResultDataFunc) {
												 formatResultDataFunc(attachInputBoxId,listSelectedKeyId,listSelectedValueId,item);
											 } else {
												 alert("没有定义结果的输出方法 ");
											 }
	                                   });
		}
		
		
function resizeIframe(iframeEl){
	if(iframeEl.src == '#') return;
	var iframeDocH, iframeDocW;
	var docH, docW;
	iframeEl.style.height = "auto";
	if(document.getElementById && !(document.all)) {
		iframeDocH = iframeEl.contentDocument.body.scrollHeight;
		iframeDocW = iframeEl.contentDocument.body.scrollWidth;
	} else if(document.all) {
		iframeDocH = document.frames(iframeEl.id).document.body.scrollHeight;
		iframeDocW = document.frames(iframeEl.id).document.body.scrollWidth;
	}
	docH = document.body.scrollHeight;
	docW = document.body.scrollWidth;
	iframeEl.style.height = (Math.max(iframeDocH, docH))+'px';
}		