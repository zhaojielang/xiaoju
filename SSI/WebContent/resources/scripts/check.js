// 1、数字 
function isNum(object)
{
 	// var s = $('#' + id).val();
 	var s = object.value;
     if(s !== "")
     {
         if(isNaN(s))
         {
			// $('#' + id).attr('value', '');
			// object.value = '';
			// object.focus();
			return false;
         }
     }
     return true;
}

// 2、电话号码，传真
// 校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-”
function isTel(object)
{
	//国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"
	// var s = $('#' + id).val();
	var s = object.value;
	var pattern =/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	//var pattern =/(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/; 
	if(s !== "")
	{
		if(!pattern.exec(s))
		{
			// alert('请输入正确的电话号码:电话号码格式为国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"');
	        // $('#' + id).attr('value', '');
	        // object.value = '';
	        // $('#' + id).focus();
	        // object.focus();
	        return false;
         }
     }
     return true;
}
// 2.1区号
function isTelDist(object)
{
	//国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"
	// var s = $('#' + id).val();
	var s = object.value;
	var pattern =/^(0\d{2,3})$/;
	//var pattern =/(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/; 
	if(s !== "")
	{
		if(!pattern.exec(s))
		{
	        return false;
         }
     }
     return true;
}
// 2.2 电话号码
function isTelNo(object)
{
	//国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"
	// var s = $('#' + id).val();
	var s = object.value;
	var pattern =/^\d{7,8}$/;
	//var pattern =/(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/; 
	if(s !== "")
	{
		if(!pattern.exec(s))
		{
	        return false;
         }
     }
     return true;
}
// 2.3 分机号
function isTelExt(object)
{
	//国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"
	// var s = $('#' + id).val();
	var s = object.value;
	var pattern =/^\d{3,}$/;
	//var pattern =/(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/; 
	if(s !== "")
	{
		if(!pattern.exec(s))
		{
	        return false;
         }
     }
     return true;
}
// 3、邮箱
/**
 * 增加对情况：example.test@gmail.com支持
 * @param object
 * @return
 */
function isMail(object)
{ 
	// var s = $('#' + id).val();
	var s =object.value;
    var pattern =/^[a-zA-Z0-9_\-.]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/;
    if(s !== "")
    {
        if(!pattern.exec(s))
        {
	         // alert('请输入正确的邮箱地址');
	         // $('#' + id).attr('value', '');
	         // $('#' + id).focus();
			// object.value="";
			// object.focus();
			return false;
        }
    }
	return true;
}

// 4、手机号码
//校验手机号码：必须以数字开头，除数字外，可含有“-”
function isMobile(object)
{
	// var s =$('#' + id).val();
	var s = object.value;
	var reg0 = /^13\d{5,9}$/;
	var reg1 = /^15\d{5,9}$/;
	var reg2 = /^18\d{5,9}$/;
	var reg3 = /^0\d{10,11}$/;
	var my = false;
	if (reg0.test(s)) my = true;
	if (reg1.test(s)) my = true;
	if (reg2.test(s)) my = true;
	if (reg3.test(s)) my = true;
    if(s !== "")
    {
        if (!my)
        {
			// alert('请输入正确的手机号码');
	        // $('#' + id).attr('value', '');
	        // $('#' + id).focus();
			// object.value="";
			//object.focus(); 
			return false;	        
        }
    }
    return true;
}
// 5、邮编
function isPostalCode(object)
{
	 // var s =$('#' + id).val();
	 var s = object.value;
	 var pattern =/^[0-9]{6}$/;
     if(s!="")
     {
         if(!pattern.exec(s))
         {
			// alert('请输入正确的邮政编码');
	        // $('#' + id).attr('value', '');
	        //object.value="";
	        // $('#' + id).focus();
	        return false;
         }
     }
     return true;
}
// 6、日期
function isdate(object)
{
	// var s =$('#' + id).val(); 
	var s = object.value;
	var pattern =/^((\d{2}(([02468][048])|([13579][26]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|([1-2][0-9])))))|(\d{2}(([02468][1235679])|([13579][01345789]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\s(((0?[0-9])|([1-2][0-3]))\:([0-5]?[0-9])((\s)|(\:([0-5]?[0-9])))))?$/;
    if(s !== "")
    {
        if(!pattern.exec(s))
        {
			//  alert('请输入正确的日期');
	        // $('#' + id).attr('value', '');
	        // $('#' + id).focus();
	        object.value="";
	        return false;
        }
    }    
    return true;     
}
// 7、判空
function checkEmpty(object){
	if(object == undefined || object == null)
	{
		return false;
	}
	trimAll(object);
	if(object.value==''){
		return false;
	}
	return true;
}
// 7.1 剪掉前、中、后空格
function trimAll(object){
	object.value = object.value.replace(/(^\s*)|(\s*$)/g, '');
	object.value = object.value.replace(/\s/g, '');
}
// 7.2 剪掉空格、转半角
function isEmpty(object){
	if(object == undefined || object == null){
		return false;
	}
	toSbc(object);
	return checkEmpty(object);
}
// 8、长度判断
function isInLength(object, start, end){
	var s = object.value;
	if(s.length < start || s.length > end){
		// object.value = '';
		return false;
	}
	return true;
}
// 9. 身份证号码校验
//这个可以验证15位和18位的身份证，并且包含生日和校验位的验证。   
//如果有兴趣，还可以加上身份证所在地的验证，就是前6位有些数字合法有些数字不合法。 
function isIdCardNo(object)
{
	// var num = $('#' + id).val();
	var num = object.value;
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
			// alert(num);
			return true; 
		} 
	}
	return false; 
}
// 10. 护照校验
function isPassport(object){
	if(!isNum(object)){
		return false;
	}
	if(!isInLength(object, 8, 9)){
		return false;
	}
	return true;
}
// 11. 军官证校验
function isMilOffNo(object){
	if(!isNum(object)){
		return false;
	}
	if(object.value.length !== 7){
		return false;
	}
	return true;
}
// 12 全角转半角
function toSbc(object){
	if(object == undefined || object == null){
		return;
	}
	object.value = object.value.dbc2sbc();
}
// 12.1 全角转半角
String.prototype.dbc2sbc=function(){
	return this.replace(/[\uff01-\uff5e]/g, function(a){
		return String.fromCharCode(a.charCodeAt(0)-65248);
	}).replace(/\u3000/g, ' ');
}
// 13. 企业组织机构代码校验
function isValidEntpCode(object) {
	var code = object.value;
	//alert(code);
	var ws = [3, 7, 9, 10, 5, 8, 4, 2];
	var str = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
	var reg = /^([0-9A-Z]){8}[0-9|X]*$/;
	if (!reg.test(code)) {
		return false;
	}
	var sum = 0;
	for (var i = 0; i < 8; i++) {
		sum += str.indexOf(code.charAt(i)) * ws[i];
	}
	//alert("sum:" + sum);
	var c9 = 11 - (sum % 11);
	c9 = c9 == 10 ? 'X' : c9
	// alert(c9);
	return c9 == code.charAt(8);
}

/* 控制输入框中只能输入数字 */
// 14. 控制输入框职能输入数字--通过id
function numOnlyById(id){
	$('#' + id).bind("keypress", function(){
		var k = event.keyCode;
		return k >= 48 && k <= 57;
	}).bind('paste', function(){
		return !clipboardData.getData('text').match(/\D/);
	}).bind('dragEnter', function(){
		return false;
	}).css('ime-mode', 'disabled');
}
//15. 控制输入框职能输入数字--通过name
function numOnlyByName(name){
	$("input[name='" + name +"']").bind("keypress", function(){
		var k = event.keyCode;
		return k >= 48 && k <= 57;
	}).bind("paste", function(){
		return !clipboardData.getData("text").match(/\D/);
	}).bind('dragEnter', function(){
		return false;
	}).css('ime-mode', 'disabled');
}

// 
//16. 输入框中只能输入英文字母、数字、下划线--通过id
function letterOnlyById(id){
	$('#' + id).bind('paste', function(){
		return !clipboardData.getData('text').match(/\W/);
	}).bind('dragEnter', function(){
		return false;
	}).css("ime-mode", 'disabled');
}
//17. 输入框中只能输入英文字母、数字、下划线--通过name
function letterOnlyByName(name){
	$("input[name='" +name + "']").css('ime-mode', 'disabled');
}
/**
* session超时后直接跳转到首页 added by liuxinxing 20100203
* @param data
* @return
*/
function sessionCheckForLoad(data){
//	alert(data.substring(0,9));
	if(data.substring(0,9) == "<!DOCTYPE" || data == 'home'){
		alert(sessionError);
		window.location = contextPath;
		return false;
	}
	return true;
}
/**
* session超时后直接跳转到首页 added by liuxinxing 20100203
* @param data
* @return
*/
function sessionCheckForGet(data){
	if(data.length > 20){
		alert(sessionError);
		window.location = contextPath;
		return false;
	}
	return true;
}

function trimText(obj) {
	obj.value = trim(obj.value);
}

/**
 * 去前后空格
 */
function trim(s){
	try{
		return trimRight(trimLeft(s));
	}catch(e){
	  alert("转换出错string=["+s+"]");
	}
	function trimLeft(s) {
		while (s.charAt(0) ==" " ||s.charAt(0) =="　" ){
			s = s.substr(1,s.length-1);
		}
		return s;
	}
	function trimRight(s) {
		while (s.charAt(s.length-1) == " " || s.charAt(s.length-1) == "　")	{
			s = s.substr(0,s.length-1);
		}
		return s;
	}
}

/**
 * 日期校验
 * 
 * @parameter yyyy-dd-mm
 * @return boolean(false:error; true:right)
 */
function checkDate(sDate, eDate) {
	
	if (sDate != "" && eDate != "") {
		var strdt1 = sDate.replace(/-/g,"/");
		var strdt2 = eDate.replace(/-/g,"/");
		
		var dt1 = new Date(Date.parse(strdt1));
		var dt2 = new Date(Date.parse(strdt2));

		if (dt1 > dt2) {
			alert("开始时间不能晚于结束时间");
			return false;
		}
	}
	return true;
}

/**
 * 只允许输入金额
 */
function keypre(obj, event) {
	var event = event?event:window.event;
	var k = event.keyCode;
	var v = obj.value;

	switch(k) {
	case 189:
	case 109:
		if (v.indexOf("-") == -1) {
			if (v.indexOf("-") == 0) {
				return ;
			}
		}
		obj.value = v.replace(/-/g, '');
		obj.value = "-" + obj.value;
		if(window.event)
			event.returnValue = false;
		else
			event.preventDefault();
		break;
	case 190:
	case 110:
		if (v.indexOf(".") != -1) {
			if(window.event)
				event.returnValue = false;
			else
				event.preventDefault();
		}
		break;
	case 35:case 36:case 46:case 8:case 9:
		break;
	case 13:
		event.keyCode = 9;
		break;
	default:
		if ((k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)) {
			return ;
		}

		if(window.event)
			event.returnValue = false;
		else
			event.preventDefault();
	}
}

/**
 * 只允许输入减号与数字
 */
function numpre(obj, event) {
	var event = event?event:window.event;
	var k = event.keyCode;
	var v = obj.value;
	switch(k) {
	case 189:case 109:case 35:case 36:case 46:case 8:case 9:
		break;
	case 13:
		event.keyCode = 9;
		break;
	default:
		if ((k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)) {
			return ;
		}

		if(window.event)
			event.returnValue = false;
		else
			event.preventDefault();
		}
}

function numberpre(obj, event) {
	var event = event?event:window.event;
	var k = event.keyCode;
	var v = obj.value;
	switch(k) {
	case 35:case 36:case 46:case 8:case 9:
		break;
	case 13:
		event.keyCode = 9;
		break;
	default:
		if ((k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)) {
			return ;
		}

		if(window.event)
			event.returnValue = false;
		else
			event.preventDefault();
	}
}