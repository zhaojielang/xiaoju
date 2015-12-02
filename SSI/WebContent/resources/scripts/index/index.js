function changeUrl(url){
	$("#dataRight").attr('src', url);
	return false;
}

function changeLocalUrl(url){
	window.location.href = url;
	return false;
}

// 进入操作平台
function comeIn(){
	window.document.location.href=contextPath + "/loginSuccess.jhtml";
}

function saveUsername(theForm) {
    var expires = new Date();
    expires.setTime(expires.getTime() + 24 * 30 * 60 * 60 * 1000); // sets it
																	// for
																	// approx 30
																	// days.
    setCookie("username",theForm.j_username.value,expires, contextPath);
}

function validateForm(form) {                                                               
    return validateRequired(form); 
}

function submitForm(form) {   
 	saveUsername(form);
 	if (validateForm(form))
 		form.submit();                                                            
}

/* This function is used to set cookies */
function setCookie(name,value,expires,path,domain,secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") + ((secure) ? "; secure" : "");
}

/* This function is used to get cookies */
function getCookie(name) {
	var prefix = name + "=" 
	var start = document.cookie.indexOf(prefix) 

	if (start==-1) {
		return null;
	}
	
	var end = document.cookie.indexOf(";", start+prefix.length) 
	if (end==-1) {
		end=document.cookie.length;
	}

	var value=document.cookie.substring(start+prefix.length, end) 
	return unescape(value);
}

/* This function is used to delete cookies */
function deleteCookie(name,path,domain) {
  if (getCookie(name)) {
    document.cookie = name + "=" +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}

// This function is used by the login screen to validate user/pass
// are entered.
function validateRequired(form) {                                    
 var bValid = true;
 var focusField = null;
 var i = 0;                                                                                          
 var fields = new Array();                                                                           
 oRequired = new required();                                                                         
                                                                                                     
 for (x in oRequired) {                                                                              
     if ((form[oRequired[x][0]].type == 'text' || form[oRequired[x][0]].type == 'textarea' || form[oRequired[x][0]].type == 'select-one' || form[oRequired[x][0]].type == 'radio' || form[oRequired[x][0]].type == 'password') && form[oRequired[x][0]].value == '') {
        if (i == 0)
           focusField = form[oRequired[x][0]]; 
           
        fields[i++] = oRequired[x][1];
         
        bValid = false;                                                                             
     }                                                                                               
 }                                                                                                   
                                                                                                    
 if (fields.length > 0) {
    focusField.focus();
    alert(fields.join('\n'));                                                                      
 }                                                                                                   
                                                                                                    
 return bValid;                                                                                      
}

function addFav() {   // 加入收藏夹
	if (document.all) {
		window.external.addFavorite(url + contextPath, '配送中心系统v2.0');
	} else if (window.sidebar) {
		window.sidebar.addPanel('配送中心系统v2.0', url + contextPath,  "" );
	}
}
function SetHome(obj){   
	try{
		obj.style.behavior='url(#default#homepage)';
		obj.setHomePage(url + contextPath);
	} catch (e) {
		if(window.netscape){
		   try{
		       netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
		   }catch(e){
		       alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
		   };
		}else{
		   alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将'"+ url + contextPath +"'设置为首页。");
		}
	}
}