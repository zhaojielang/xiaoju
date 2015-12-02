// JavaScript Document
//var contextPath = "/gzhs_citizen";
/*
**initEntName(entName,entNo,id),若不需要返填某个值，则对应id的位置输入undefined
**如：initEntName(entName,undefined,id)
*/
function initEntName(example,exampleNo,exampleId){
	$("#"+example).autocomplete(contextPath+"/ent/common.jhtml?bizType=getEntName",{   
                                   delay:400,//延迟10秒   
                                   max:10,//最多5条记录   
                                   minChars:1,//在触发autoComplete前用户至少需要输入的字符数
                                   multiple:true,//是否允许输入多个值即多次使用autoComplete以输入多个值   
                                   matchSubset:false,
                                   mustMatch:false,   
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
                                           data: data[i],   
                                           value: data[i].itemid,   
                                           result: data[i].itemid //返回的结果显示内容   
                                           };   
                                       }   
                                       return parsed;   
                                   },   
                                   formatItem: function(item) {//显示下拉列表的内容   
									return "<div>"+item.no + "&nbsp;&nbsp;&nbsp;" + item.name+"</div>";   
									 },   
									formatMatch: function(item) {   
									    return item.itemid;   
									},   
									formatResult: function(item) {   
									return item.itemid;   
									}   
									 }).result(function(event, item, formatted) {//把返回的结果内容显示在其他文本框上               
                                       $("#"+example).val(item.name); 
                                       if(exampleNo!=undefined&&exampleNo!=""){
                                       	$("#"+exampleNo).val(item.no);
                                       }
                                       if(exampleId!=undefined&&exampleId!=""){
                                       	$("#"+exampleId).val(item.id);
                                       }  
                                   });
}



