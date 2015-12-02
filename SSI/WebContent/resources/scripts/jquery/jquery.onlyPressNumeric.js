jQuery.fn.onlyPressNumeric = function() { 
$(this).css({imeMode:"disabled",'-moz-user-select':"none"}); 
$(this).bind("keypress",function(e){ 
/*alert(e.which); 
$.each(e,function(i,val){ 
alert(i+"|"+val); 
}); 
ialert(e.ctrlKey);*/ 
//modify by zhuYanchao
var currentInputValue=$(e.target).val();
var hasDot=false;
if(currentInputValue.indexOf(".")>0) {
	hasDot=true;
}
if(e.ctrlKey == true || e.shiftKey == true) 
return false; 
if((
			((e.which >= 48 && e.which <= 57)
					||  (e.which==46 && currentInputValue.length>0 && !hasDot)
					|| (e.which==45 && currentInputValue.length==0)) 
			&& e.ctrlKey == false && e.shiftKey == false) 
	|| e.which == 0 || e.which == 8) 
	return true; 
else if(e.ctrlKey == true && (e.which == 99 || e.which == 118)) 
return false; 
else 
return false; 
}) 
.bind("contextmenu",function(){return false;}) 
.bind("selectstart",function(){return false;}) 
.bind("paste",function(){return false;}); 
}; 
