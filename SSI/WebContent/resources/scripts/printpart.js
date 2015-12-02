// JavaScript Document
var frameLoadCount=0;


function resizeIframe(iframeID) {
	var height=document.getElementById(iframeID).contentWindow.document.body.scrollHeight;

	var width=document.getElementById(iframeID).contentWindow.document.body.scrollWidth;
	
	document.getElementById(iframeID).height=height;
	document.getElementById(iframeID).width=width;

}

function printIframe(fid)
{
    var iframe = document.frames ? document.frames[fid] : document.getElementById(fid);
    var ifWin = iframe.contentWindow || iframe;
    //iframe.focus();
	ifWin.focus();
	/*
	var frameId=fid;
	
	
	var isIE = document.all && window.external;
	if(isIE) {
			//document.frames("barcodeFrameId").window.focus();  
			document.getElementById(fid).focus();
	}*/
	
    ifWin.print();
    return false;
}


function doPrintById(pDynamicFrameId) {
	//alert("print begin...");
  if (window.frames[pDynamicFrameId]== null) {
       document.getElementById(pDynamicFrameId).focus();
       document.getElementById(pDynamicFrameId).contentWindow.print();
     }
  else {
      document.frames(pDynamicFrameId).window.focus();       
      //window.print();                      
	   document.getElementById(pDynamicFrameId).contentWindow.print();
       }
 }
 
function printById(toPrintPartId) {
	var el = document.getElementById(toPrintPartId);
               var dynamicIframe = document.createElement('IFRAME');
               var doc = null;
               dynamicIframe.setAttribute('style', 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
			   dynamicIframe.setAttribute('id', 'embedIframeId');
               document.body.appendChild(dynamicIframe);
               doc = dynamicIframe.contentWindow.document;
               doc.write('<div>' + el.innerHTML + '</div>');
               doc.close(); 
			   resizeIframe("embedIframeId");
              
			  
			  
			   if (navigator.userAgent.indexOf("MSIE") > 0)
               {
                   // document.body.removeChild(dynamicIframe);
				   //dynamicIframe.focus();
               		//dynamicIframe.contentWindow.print();
               } else {
				  //  dynamicIframe.contentWindow.focus();
               		//dynamicIframe.contentWindow.print();
			   }
			   
			  
               if (navigator.userAgent.indexOf("MSIE") > 0)
               {
                   // document.body.removeChild(dynamicIframe);
               }
			  
			   
}


function printByContent(toPrintPartContent) {
	//var el = document.getElementById(toPrintPartId);
               var dynamicIframe = document.createElement('IFRAME');
               var doc = null;
               dynamicIframe.setAttribute('style', 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
			   //dynamicIframe.setAttribute('id', 'embedIframeId');
			   //dynamicIframe.setAttribute('name', 'embedIframeId');
			   //dynamicIframe.style="position:absolute;width:0px;height:0px;left:-500px;top:-500px;";
			   dynamicIframe.id="embedIframeId";
			   dynamicIframe.name="embedIframeId";
			   
			   
			   var isIE = document.all && window.external;
			   if(isIE) {
				   if(dynamicIframe.attachEvent) {
					   dynamicIframe.attachEvent("onload",function(){
						   //alert("load");															  
						   printIframe("embedIframeId");
						   document.body.removeChild(dynamicIframe);
						});
				   } else {
					   dynamicIframe.onload=function() {
						   alert("not load");
					   };
				   }
			   }
			   
			   
               document.body.appendChild(dynamicIframe);
			   window.frames.embedIframeId.name = "embedIframeId";
			   
			   
               doc = dynamicIframe.contentWindow.document;
               //doc.write('<div>' + el.innerHTML + '</div>');
			   doc.open();
			   doc.write('<div>' +toPrintPartContent+'</div>');

               doc.close();

			   //resizeIframe("embedIframeId");
			   
				 if(!isIE) {					 
					printIframe("embedIframeId");
				 } else {
					 
				 }
			

}