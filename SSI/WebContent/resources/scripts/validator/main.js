$(document).ready(function(){	
	$.formValidator.initConfig({formid:"regform",onsuccess:function(){return true ;} ,onerror:function(){alert("校验没有通过，具体错误请看错误提示")}});	
	//系统用到的校验
/**
	$("#userName").formValidator({tipid:"loginNameText",onshow:"请输入用户名",oncorrect:"用户名可用"}).inputValidator({min:1,onerror:"用户名不能为空"})
		.ajaxValidator({			 
			 type : "get", 
	         url :  contextPath+"/user/userIsReg.jhtml?bizType=isLogin",
	         data:{loginName:$("#userName").val()} ,
	         success : function(data){ 					
	        	 if(data == "nullErrr"){
	        		this.onerror = "请输入用户名" ;
	 				return false;
		 		 }else if(data == "haveReged"){
		 			this.onerror = "该用户名已经被注册了，请重新输入" ;	 				
		 			return false ;
		 		 }else if(data == "canReg"){
		 			var loginName = $("#userName").val() ;
		 			return true;
		 		}				
	         },	         
	         error: function(){alert("服务器没有返回数据，可能服务器忙，请重试");}, 
		     onerror : "用户名输入不正确，请仔细检查后再次输入", 
		     onwait : "正在对用户名进行合法性校验，请稍候..." 
					
		});	
*/
	$("#email").formValidator({tipid:"emailText",onshow:"请输入真实有效的邮箱",onfocus:"请注意你输入的邮箱格式,例如:xxx@sina.com",oncorrect:"邮箱格式正确"}).regexValidator({regexp:"email",datatype:"enum",onerror:"邮箱格式不正确"})
		.ajaxValidator({			 
			 type : "get", 
	         url :  contextPath+"/user/userIsReg.jhtml?bizType=isEmail",
	         data:{email:$("#email").val()} ,
	         success : function(data){ 					
	        	 if(data == "nullErrr"){
	        		this.onerror = "请输入邮箱" ;
	 				return false;
		 		 }else if(data == "haveReged"){
		 			this.onerror = "该邮箱已经被注册了，请重新输入" ;	 				
		 			return false ;
		 		 }else if(data == "canReg"){
		 			var email = $("#email").val() ;
		 			return true;
		 		}				
	         },	         
	         error: function(){alert("服务器没有返回数据，可能服务器忙，请重试");}, 
		     onerror : "邮箱输入不正确，请仔细检查后再次输入", 
		     onwait : "正在对邮箱进行合法性校验，请稍候..." 
					
		});	
		$("#mobilePhone").formValidator({tipid:"mobilePhoneText",onshow:"请输入手机号码",onfocus:"手机号码必须是11位数字",oncorrect:"手机号正确"}).inputValidator({min:11,max:11,onerror:"手机号码必须是11位,请确认"}).regexValidator({regexp:"^[0-9]*$",onerror:"请输入11位数字类型的手机号"})
		.ajaxValidator({			 
			 type : "get", 
	         url :  contextPath+"/user/userIsReg.jhtml?bizType=isMobile",
	         data:{mobilePhone:$("#mobilePhone").val()} ,
	         success : function(data){ 					
	        	 if(data == "nullErrr"){
	        		this.onerror = "请输入手机号码" ;
	 				return false;
		 		 }else if(data == "haveReged"){
		 			this.onerror = "该手机号码已经被注册了，请重新输入" ;	 				
		 			return false ;
		 		 }else if(data == "canReg"){
		 			var mobilePhone = $("#mobilePhone").val() ;
		 			return true;
		 		}				
	         },	         
	         error: function(){alert("服务器没有返回数据，可能服务器忙，请重试");}, 
		     onerror : "手机号输入不正确，请仔细检查后再次输入", 
		     onwait : "正在对手机号进行合法性校验，请稍候..." 
					
		});	
	/**	$("#bankNo").formValidator({tipid:"bankNoText",onshow:"请输入银行卡号",oncorrect:"银行卡号正确",empty:true}).inputValidator({min:1,onerror:"银行卡号不能为空"})
		.ajaxValidator({			 
			 type : "get", 
	         url :  contextPath+"/user/userIsReg.jhtml?bizType=bankNoisExits",
	         data:{userNo:$("#bankNo").val()} ,
	         success : function(data){ 					
	        	 if(data == "nullErrr"){
	        		this.onerror = "请输入银行卡号" ;
	 				return false;
		 		 }else if(data == "haveReged"){
		 			this.onerror = "该银行卡号已经存在了，请重新输入" ;	 				
		 			return false ;
		 		 }else if(data == "canReg"){
		 			var bankNo = $("#bankNo").val() ;
		 			return true;
		 		}				
	         },	         
	         error: function(){alert("服务器没有返回数据，可能服务器忙，请重试");}, 
		     onerror : "银行卡号输入不正确，请仔细检查后再次输入", 
		     onwait : "正在对银行卡号进行合法性校验，请稍候..." 
					
		});	*/
	$("#cardNo").formValidator({tipid:"cardNoText",onshow:"请输入卡号",onfocus:"会员卡号必须是9位数字类型",oncorrect:"会员卡号正确"}).inputValidator({min:9,max:9,onerror:"会员卡号必须是9位数字类型,请确认"}).regexValidator({regexp:"^[0-9]*$",onerror:"请输入9位数字类型的卡号"})
			.ajaxValidator({			 
				 type : "get", 
		         url :  contextPath+"/user/userIsReg.jhtml?bizType=isExists",
		         data:{cardNo:$("#cardNo").val()} ,
		         success : function(data){ 					
		        	 if(data == "nullErrr"){
		        		this.onerror = "请输入会员卡号" ;
		 				return false;
			 		 }else if(data == "ok"){
			 			var cardNo = $("#cardNo").val() ;
			 			return true;
			 		 }else if(data == "no"){
			 			this.onerror = "该会员卡号不合法，请重新输入" ;	 				
			 			return false ;
			 		}				
		         },	         
		         error: function(){alert("服务器没有返回数据，可能服务器忙，请重试");}, 
			     onerror : "会员卡号输入不正确，请仔细检查后再次输入", 
			     onwait : "正在对会员卡号进行合法性校验，请稍候..." 
						
			});		
//  $("#password").formValidator({tipid:"passwordText",onshow:"至少6位字母或数字组合",onfocus:"密码不能为空",oncorrect:"密码合法"}).inputValidator({min:6,max:16,empty:{leftempty:false,rightempty:false,emptyerror:"密码两边不能有空符号"},onerror:"请确定输入的字符数在6-16之间"});
//	$("#confirmPassword").formValidator({tipid:"confirmPasswordText",onshow:"请输入重复密码",onfocus:"两次密码必须一致",oncorrect:"密码一致"}).inputValidator({min:6,max:16,empty:{leftempty:false,rightempty:false,emptyerror:"重复密码两边不能有空符号"},onerror:"请确定输入的字符数在6-16之间"}).compareValidator({desid:"password",operateor:"=",onerror:"2次密码不一致,请确认"});
//	$("#passwdQuestion").formValidator({tipid:"passwdQuestionText",onshow:"请选择密码提示问题"}).inputValidator({min:1, onerror:"密码提示问题不能为空"}) ;
//	$("#passwdAnswer").formValidator({tipid:"passwdAnswerText",onshow:"请输入密码问题答案"}).inputValidator({min:1, onerror:"密码问题答案不能为空"}) ;
	$("#realName").formValidator({tipid:"realNameText",onshow:"请输入用户姓名"}).inputValidator({min:1, onerror:"用户姓名不能为空"}) ;
//	$("#birthday").formValidator({tipid:"birthdayText",onshow:"请选择出生日期",oncorrect:"出生日期正确",empty:true});
	$("#sex").formValidator({tipid:"sexText",onshow:"请选择性别"}).inputValidator({min:1, onerror:"性别不能为空"}) ;
//	$("#consignee").formValidator({tipid:"consigneeText",onshow:"请输入收货人"}).inputValidator({min:1, onerror:"收货人不能为空"}) ;
	$("#addressEmail").formValidator({tipid:"addressEmailText",onshow:"请输入真实有效的邮箱",onfocus:"请注意你输入的邮箱格式",oncorrect:"邮箱格式正确"}).regexValidator({regexp:"email",datatype:"enum",onerror:"收货人邮箱格式不正确"});
//	$("#address").formValidator({tipid:"addressText",onshow:"请输入收货人地址"}).inputValidator({min:1, onerror:"收货人地址不能为空"}) ;
//	$("#zipcode").formValidator({tipid:"zipcodeText",onshow:"请输入邮编",onfocus:"6位数字组成",oncorrect:"邮编正确"}).regexValidator({regexp:"zipcode",datatype:"enum",onerror:"邮编格式不正确"});
//	$("#tel").formValidator({tipid:"telText",onshow:"请输入联系人电话，电话、手机至少要填写一个",onfocus:"010-88888888或88888888",oncorrect:"联系人电话格式正确",empty:true}).regexValidator({regexp:"tel",datatype:"enum",onerror:"联系人电话格式不正确"});
	$("#mobile").formValidator({tipid:"mobileText",onshow:"请输入联系人手机，电话、手机至少要填写一个",onfocus:"手机号码必须是11位",oncorrect:"手机号正确",empty:true}).inputValidator({min:11,max:11,onerror:"手机号码必须是11位的,请确认"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"你输入的手机号码格式不正确"});
//	$("#signBuilding").formValidator({tipid:"signBuildingText",onshow:"请输入标志性建筑"}).inputValidator({min:1, onerror:"标志性建筑不能为空"}) ;
//	$("#bestTime").formValidator({tipid:"bestTimeText",onshow:"请选择最佳送货时间"}).inputValidator({min:1, onerror:"最佳送货时间不能为空"}) ;
//	$("#addressEmail").formValidator({tipid:"addressEmailText",onshow:"请输入真实有效的邮箱",onfocus:"请注意你输入的邮箱格式",oncorrect:"邮箱格式正确"}).regexValidator({regexp:"email",datatype:"enum",onerror:"邮箱格式不正确"});
//	$("#identifyNo").formValidator({tipid:"identifyNoText",onshow:"请输入身份证号码"}).inputValidator({min:1, onerror:"身份证号不能为空"}) ;
//	$("#identifyNo").formValidator({tipid:"identifyNoText",onshow:"请输入15或18位的身份证",onfocus:"输入15或18位的身份证",onCorrect:"身份证输入正确"}).functionValidator({fun:isIdCardNo,onerror:"身份证号不允许为空"});
//  $("#cardNo").formValidator({tipid:"cardNoText",onshow:"请输入9位的会员卡号",onfocus:"输入9位的会员卡号",onCorrect:"卡号输入正确"}).inputValidator({min:9,max:9,onerror:"会员卡号不允许为空"});
	$("#province,#city,#district,#shequ").formValidator({tipid:"unitText",onshow:"请点击选择所处地区",oncorrect:"地址输入正确"}).functionValidator({fun:checkArea,onerror:"地区不允许为空"}) ;
	//$("#bankName").formValidator({tipid:"bankNameText",onshow:"请输入开户行名称",oncorrect:"开户行名称正确",empty:true}).inputValidator({min:2,max:100,onerror:"开户行名称的长度不正确"});
	//$("#company").formValidator({tipid:"companyText",onshow:"请输入工作单位",oncorrect:"工作单位正确",empty:true}).inputValidator({min:2,max:100,onerror:"工作单位的长度不正确"});
	$("#homeAddress").formValidator({tipid:"homeAddressText",onshow:"请输入家庭住址",oncorrect:"家庭住址正确"}).functionValidator({fun:checkAddress,onerror:"家庭住址不能为空"});
});

function checkArea(){
	if(''== $('#shequ').val()){
        return false;
   }else{
   		return true;
   }
}
function checkAddress(){
	if(''== $('#homeAddress').val()){
        return false;
   }else{
   		return true;
   }
}
// 9. 身份证号码校验
//这个可以验证15位和18位的身份证，并且包含生日和校验位的验证。   
//如果有兴趣，还可以加上身份证所在地的验证，就是前6位有些数字合法有些数字不合法。 
function isIdCardNo()
{
	// var num = $('#' + id).val();
	var num = $('#identifyNo').val();
	num = num.toUpperCase();  
	//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
	if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))   
	{ 
		// alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。'); 
		// tips += '输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。\n';
		
		// return false; 
		return '输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。';
	}
	//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
	//下面分别分析出生日期和校验位 
	var len, re; 
	len = num.length;
	if (len == 15) 
	{ 
		re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/); 
		var arrSplit = num.match(re); 
		 
		//检查生日日期是否正确 
		var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]); 
		var bGoodDay; 
		bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4])); 
		if (!bGoodDay) 
		{ 
			// alert('输入的身份证号里出生日期不对！'); 
			// tips += '输入的身份证号里出生日期不对！\n';  
			//return false;
			return '输入的身份证号里出生日期不对！';  
		} 
		else 
		{ 
			//将15位身份证转成18位 
			//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
			var nTemp = 0, i;   
			num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6); 
			for(i = 0; i < 17; i++) 
			{ 
			      nTemp += num.substr(i, 1) * arrInt[i]; 
			} 
			num += arrCh[nTemp % 11];  
			// alert(num); 
			return true;
			//return num; 
		}
	}
	if (len == 18) 
	{ 
		re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/); 
		var arrSplit = num.match(re); 
		 
		//检查生日日期是否正确 
		var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]); 
		var bGoodDay; 
		bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4])); 
		if (!bGoodDay) 
		{ 
			// alert(dtmBirth.getYear()); 
			// alert(arrSplit[2]); 
			// alert('输入的身份证号里出生日期不对！'); 
			// return false; 
			return '输入的身份证号里出生日期不对！';
		} 
		else 
		{ 
			//检验18位身份证的校验码是否正确。 
			//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
			var valnum; 
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
			var nTemp = 0, i; 
			for(i = 0; i < 17; i ++) 
			{ 
				nTemp += num.substr(i, 1) * arrInt[i]; 
			} 
			valnum = arrCh[nTemp % 11]; 
			if (valnum != num.substr(17, 1)) 
			{ 
				// alert('18位身份证的校验码不正确！应该为：' + valnum); 
				// tips = tips + '18位身份证的校验码不正确！应该为：' + valnum + '\n';
				// return false; 
				return '18位身份证的校验码不正确！'; 
			} 
			//alert(num);
			return true; 
		} 
	}
	return false; 
}

function getCitys(provCode){
   if("" != provCode){
      $("#city").load(contextPath + "/areaCode/getCitys.jhtml?bizType=getCitys&provCode="+provCode);
      $("#district").html("<option value=''>请选择</option>");
   }else{
      $("#city,#district").html("<option value=''>请选择</option>");
   }
}
//加载县级
function getCountrys(cityCode){
   if("" != cityCode){
       $("#district").load(contextPath + "/areaCode/getCountrys.jhtml?bizType=getCountrys&cityCode="+cityCode);
   	   $("#shequ").html("<option value=''>请选择</option>");
   }else{
      $("#district").html("<option value=''>请选择</option>");
      $("#shequ").html("<option value=''>请选择</option>");
   }
}
//动态加载社区
function getShequ(district){
   if("" != district){
       $("#shequ").load(contextPath + "/areaCode/getShequ.jhtml?bizType=getShequ&district="+district,function(){
       		if($("#shequ").find("option").size()<=1){
       			$("#shequ").attr('disabled','disabled');
       		}else{
       			$("#shequ").attr('disabled','');
       		}
       });
   }else{
       $("#shequ").html("<option value=''>请选择</option>");
   }
}
