$(document).ready(function(){

	$.formValidator.initConfig({formid:"equmInfoForm",onsuccess:function(){
	return true;} ,onerror:function(){alert("校验没有通过，具体错误请看错误提示1")}});	
	
	$.formValidator.initConfig({formid:"equmInfoForm",validatorgroup:"2",onsuccess:function(){
	return true;} ,onerror:function(){alert("校验没有通过，具体错误请看错误提示2")}});	
	
	//校验组一
	$("#detailType").formValidator({tipid:"detailTypeText",onshow:"请选择客运索道型号",onFocus:"选择型号",oncorrect:"谢谢你的合作"}).inputValidator({min:1, onerror:"客运索道型号不允许为空"}) ;
	
	$("#preyearPassengerNum").formValidator({tipid:"preyearPassengerNumText",onshow:"请输入上年度运送乘客人次",onfocus:"单位是（万）长度在1-10位,小数点后2位",
	oncorrect:"上年度运送乘客人次输入正确"}).functionValidator({fun:isNumlast});
	
    $("#safeLevelId").formValidator({tipid:"safeLevelIdText",onshow:"请选择5S信息评级",onFocus:"请选择",oncorrect:"谢谢你的合作"}).inputValidator({min:1, onerror:"5S信息评级不允许为空"}) ;
	
	$("#licenceNo").formValidator({tipid:"licenceNoText",onshow:"请输入安全使用许可证号",onfocus:"安全使用许可证号若存在则失败",oncorrect:"输入正确"})
	.inputValidator({min:1,max:64,onerror:"请确定输入的长度为1-64位"})
	 .ajaxValidator({ 
	        type : "get", 
	        url : contextPath + '/equinfomanage/uniqueLicenceNoCheck.jhtml' ,  
	        data : {validateCode:$('#licenceNo').val()} ,
	        datatype : "string",
	        success : function(data){ 	
			 	if(data == 'ok'){
					return true ;		
				}else{
					return false ;
				}
	        }, 	        	       
	        error: function(){alert("服务器没有返回数据，可能服务器忙，请重试");}, 
	        onerror : "许可证号已存在", 
	        onwait : "正在安全使用许可证号进行合法性校验，请稍候..."
	    }); 
	$("#licenceRegDate").formValidator({tipid:"licenceRegDateText",onshow:"点击选择",onFocus:"请选择",oncorrect:"开始时间正确"}).inputValidator({min:1,onerror:"请确定是否输入正确"});
	
	$("#licenceValidDate").formValidator({tipid:"licenceValidDateText",onshow:"点击选择",onfocus:"请选择",oncorrect:"输入正确"})
		.inputValidator({min:1,onerror:"请选择"})
		.functionValidator({fun:checkdate,onerror:"结束时间要大于等于开始时间！"});
		
	$("#businessLicence").formValidator({tipid:"businessLicenceText",onshow:"请上传安全使用许可证影印件",onfocus:"请上传安全使用许可证影印件！",oncorrect:"谢谢你的合作"}).inputValidator({min:1,onerror:"请上传安全使用许可证影印件！！"}) ;
	$("#permitNo").formValidator({tipid:"permitNoText",onshow:"请输入特种设备使用登记证编号",onfocus:"请输入1-64位",oncorrect:"输入正确"})
	.inputValidator({min:1,max:64,onerror:"请确定输入的长度为1-64位"})
	
	$("#permitRegDate").formValidator({tipid:"permitRegDateText",onshow:"选择特种设备有效期限-开始时间",onFocus:"请选择",oncorrect:"开始时间正确"}).inputValidator({min:1,onerror:"请确定是否输入正确"});

	$("#permitValidDate").formValidator({tipid:"permitValidDateText",onshow:"选择特种设备有效期限-结束时间",onfocus:"请选择",
		oncorrect:"输入正确"})
		.inputValidator({min:1,onerror:"请选择"})
		.functionValidator({fun:checkdate1,onerror:"结束时间要大于等于开始时间！"});
		
		
		
		//校验组二
	$("#detailType").formValidator({tipid:"detailTypeText",validatorgroup:"2",onshow:"请选择客运索道型号",onFocus:"选择型号",oncorrect:"谢谢你的合作"}).inputValidator({min:1, onerror:"客运索道型号不允许为空"}) ;
	
	$("#preyearPassengerNum").formValidator({tipid:"preyearPassengerNumText",validatorgroup:"2",onshow:"请输入上年度运送乘客人次",onfocus:"单位是（万）长度在1-10位,且小数点之后两位",
	oncorrect:"上年度运送乘客人次输入正确"}).functionValidator({fun:isNumlast});
	
    $("#safeLevelId").formValidator({tipid:"safeLevelIdText",validatorgroup:"2",onshow:"请选择5S信息评级",onFocus:"请选择",oncorrect:"谢谢你的合作"}).inputValidator({min:1, onerror:"5S信息评级不允许为空"}) ;
	
	$("#licenceNo").formValidator({tipid:"licenceNoText",validatorgroup:"2",onshow:"请输入安全使用许可证号",onfocus:"安全使用许可证号若存在则失败",oncorrect:"输入正确"})
	.inputValidator({min:1,max:64,onerror:"请确定输入的长度为1-64位"})
	 .ajaxValidator({ 
	        type : "get", 
	        url : contextPath + '/equinfomanage/uniqueLicenceNoCheck.jhtml' ,  
	        data : {validateCode:$('#licenceNo').val()} ,
	        datatype : "string",
	        success : function(data){ 	
			 	if(data == 'ok'){
					return true ;		
				}else{
					return false ;
				}
	        }, 	        	       
	        error: function(){alert("服务器没有返回数据，可能服务器忙，请重试");}, 
	        onerror : "许可证号已存在", 
	        onwait : "正在安全使用许可证号进行合法性校验，请稍候..."
	    }); 
	    $("#licenceRegDate").formValidator({tipid:"licenceRegDateText",validatorgroup:"2",onshow:"点击选择",onFocus:"请选择",oncorrect:"开始时间正确"}).inputValidator({min:1,onerror:"请确定是否输入正确"});
		$("#licenceValidDate").formValidator({tipid:"licenceValidDateText",validatorgroup:"2",onshow:"点击选择",onfocus:"请选择",
		oncorrect:"输入正确"})
		.inputValidator({min:1,onerror:"请选择"})
		.functionValidator({fun:checkdate,onerror:"结束时间要大于等于开始时间！"});
		
	   $("#businessLicence").formValidator({tipid:"businessLicenceText",validatorgroup:"2",onshow:"请上传安全使用许可证影印件",onfocus:"请上传安全使用许可证影印件！",oncorrect:"谢谢你的合作"}).inputValidator({min:1,onerror:"请上传安全使用许可证影印件！！"}) ;
	   $("#permitNo").formValidator({tipid:"permitNoText",validatorgroup:"2",onshow:"请输入特种设备使用登记证编号",onfocus:"请输入1-64位",oncorrect:"输入正确"})
	.inputValidator({min:1,max:64,onerror:"请确定输入的长度为1-64位"})
	
	  $("#permitRegDate").formValidator({tipid:"permitRegDateText",validatorgroup:"2",onshow:"选择特种设备有效期限-开始时间",onFocus:"请选择",oncorrect:"开始时间正确"}).inputValidator({min:1,onerror:"请确定是否输入正确"});
	  
	  
		$("#permitValidDate").formValidator({tipid:"permitValidDateText",validatorgroup:"2",onshow:"选择特种设备有效期限-结束时间",onfocus:"请选择",
		oncorrect:"输入正确"})
		.inputValidator({min:1,onerror:"请选择"})
		.functionValidator({fun:checkdate1,onerror:"结束时间要大于等于开始时间！"});	
	$("#reparation1").formValidator({tipid:"reparation1Text",validatorgroup:"2",onshow:"请输入此年度赔偿总额",onfocus:"单位是（万）长度在1-15位，小数点后两位",
	oncorrect:"此年度赔偿总额输入正确"}).functionValidator({fun:isNumyear});
	    $("#reparation2").formValidator({tipid:"reparation2Text",validatorgroup:"2",onshow:"请输入此年度赔偿总额",onfocus:"单位是（万）长度在1-15位，小数点后两位",
	oncorrect:"此年度赔偿总额输入正确"}).functionValidator({fun:isNumyear});
	    $("#reparation3").formValidator({tipid:"reparation3Text",validatorgroup:"2",onshow:"请输入此年度赔偿总额",onfocus:"单位是（万）长度在1-15位，小数点后两位",
	oncorrect:"此年度赔偿总额输入正确"}).functionValidator({fun:isNumyear});		
	});
	
	function checkdate(){
	var licenceRegDate=$("#licenceRegDate").val();
	var licenceValidDate=$("#licenceValidDate").val();
	if (licenceValidDate>=licenceRegDate){
	return true;
	}
	else{
	return false;
	}
	}
	
	function checkdate1(){
	var permitRegDate=$("#permitRegDate").val();
	var permitValidDate=$("#permitValidDate").val();
	if (permitValidDate>=permitRegDate){
	return true;
	}
	else{
	return false;
	}
	
	}