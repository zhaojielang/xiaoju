function changeImg(obj) {
	//var imgSrc = document.getElementById(obj);
	var oldsrc = $("#"+obj).attr("src");//imgSrc.src;
	var newsrc = chgUrl(oldsrc);
	$("#"+obj).attr("src",newsrc);
}
function chgUrl(url) {
	var timestamp = (new Date()).valueOf();
	var postion = url.indexOf("?");
	if (postion >= 0) {
		var suburl = "";
		var timeS=url.indexOf("timestamp=");
		if(timeS>0) {
			
			suburl = url.substr(0, timeS);
			/*
			if(suburl.match(/\?$/)) {
			}*/
		} else {
			suburl = url+"&";
		}
		url = suburl + "timestamp=" + timestamp;
	} else {
		url = url + "?timestamp=" + timestamp;
	}
	return url;
}

function verifyCodeCheck(obj) {
	//var imgSrc = document.getElementById(obj);
	var oldsrc = $("#"+obj).attr("src");
	var checkSrc=oldsrc.replace(/=verifyCode/g,"=checkVerifyCode");
	var inputV=$("#veirfyCodeTextId").val();
	if(!$("#veirfyCodeTextId").isEmpty()) {
		jAlert("请输入验证码后再试","操作提示");
		return false;
	}
	
	var serverCheck=false;
	/*
	$.ajaxSetup({ async: false});//设置成同步
	$.getJSON(checkSrc, { clientRandom: inputV}, function(json){
		  //alert("JSON Data: " + json.users[3].name);
			if(json && json.checkResult && json.checkResult=='0') {
				serverCheck=true;
			}
		});
		*/
	$.ajax({
		   type: "GET",
		   async: false,
		   dataType:'json',
		   url: checkSrc,		   
		   data: "clientRandom="+ inputV,
		   error:function (XMLHttpRequest, textStatus, errorThrown) {
			   // typically only one of textStatus or errorThrown 
			   // will have info
				jAlert("验证校验码时出错","操作提示");
				serverCheck=false;
			   //this; // the options for this ajax request
			 },
		   success: function(response){
				 if(response && response.checkResult && response.checkResult=='0') {
						serverCheck=true;
				 }
		   }
		 });

	return serverCheck;

}