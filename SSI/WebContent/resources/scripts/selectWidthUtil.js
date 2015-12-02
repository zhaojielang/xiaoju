//设置终端编号下拉西框的宽度为自适应
	function FixWidth(selectObj)
	{
	    var newSelectObj = document.createElement("select");
	    newSelectObj = selectObj.cloneNode(true);
	    newSelectObj.selectedIndex = selectObj.selectedIndex;
	    newSelectObj.onmouseover = null;
	    
	    var e = selectObj;
	    var absTop = e.offsetTop;
	    var absLeft = e.offsetLeft;
	    while(e = e.offsetParent)
	    {
	        absTop += e.offsetTop;
	        absLeft += e.offsetLeft;
	    }
	    with (newSelectObj.style)
	    {
	        position = "absolute";
	        top = absTop + "px";
	        left = absLeft + "px";
	        width = "auto";
	    }
	    
	    var rollback = function(){ RollbackWidth(selectObj, newSelectObj); };
	    if(window.addEventListener)
	    {
	        newSelectObj.addEventListener("blur", rollback, false);
	        newSelectObj.addEventListener("change", rollback, false);
	    }
	    else
	    {
	        newSelectObj.attachEvent("onblur", rollback);
	        newSelectObj.attachEvent("onchange", rollback);
	    }
	    
	    selectObj.style.visibility = "hidden";
	    document.body.appendChild(newSelectObj);
	    newSelectObj.focus();
	}

	function RollbackWidth(selectObj, newSelectObj)
	{
	    selectObj.selectedIndex = newSelectObj.selectedIndex;
	    selectObj.style.visibility = "visible";
	    document.body.removeChild(newSelectObj);
	}