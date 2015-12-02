var speed = 500;
function initMenu() {
  $('#menu ul').hide();
  
  
  $("#menu > li > ul > li > a").each(function() {
		 var checkElement = $(this).next();
      if(checkElement.is('ul')) 
         $(this).addClass('collapsed2');
       else
       	 $(this).addClass('nonesub2');
	});

 $("#menu > li > a").each(function() {
		 var checkElement = $(this).next();
      if(checkElement.is('ul')) 
      {
      	
        $(this).addClass('collapsed1');
      }
       else
       	{
       	$(this).addClass('nonesub1');
      }
	});


  /* 
  $('#menu > li > ul > li > a').click(
    function() {
 //   alert("click menu > li > ul > li > a");

      var checkElement = $(this).next();
      if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
      	checkElement.slideUp('normal');
      	$(this).removeClass();
      	$(this).addClass('collapsed2');
        return false;
        }
      if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
        $('#menu > li > ul > li > ul:visible').prev().removeClass();
        $('#menu > li > ul > li > ul:visible').prev().addClass('collapsed2');
        $('#menu > li > ul > li > ul:visible').slideUp('normal');
        checkElement.slideDown(speed);
        $(this).removeClass();
      	$(this).addClass('expanded2');
        return false;
        }
      }
    );    
   */
  
  $('#menu > li > a').click(
    function() {
      //alert("click menu > li > a");
      var checkElement = $(this).next();
      if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
    
    	  //checkElement.slideUp('normal');
    	  checkElement.slideUp(speed);
    	  $(this).removeClass();
    	  $(this).addClass('collapsed1');

    	  return false;
        }
      if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
        $('#menu > li > ul > li > ul:visible').prev().removeClass();
        $('#menu > li > ul > li > ul:visible').prev().addClass('collapsed2');
        $('#menu > li > ul:visible').prev().removeClass();
        $('#menu > li > ul:visible').prev().addClass('collapsed1');
        
        //$('#menu ul:visible').slideUp('normal');
        $('#menu ul:visible').slideUp(speed);
 
        //checkElement.slideDown('normal');
        checkElement.slideDown(speed);
        $(this).removeClass();
        $(this).addClass('expanded1');
        return false;
        }
      return false;
      }
    );

 }


function showMenu(submenu)
{
	submenu = "#"+submenu;
	if(submenu.length ==5)
	{
		pm =  submenu.substr(0,4);
		$(pm).show();
		$(pm).prev().removeClass();
		$(pm).prev().addClass('expanded1');
		$(submenu).show();
		$(submenu).prev().removeClass();
		$(submenu).prev().addClass('expanded2');
	}
	else
	{
		$(submenu).show();
		$(submenu).prev().removeClass();
		$(submenu).prev().addClass('expanded1');
	}
}
