if (SQI == undefined) {
	var SQI = {
		OPER : {
		}
	};
	
	SQI = {
		Data : {},
		COUNT : 0,
		SOURCE : "#fade_focus",
		LOAD_IMAGE_BLUE : ".RollImages img",
		OPER : {
			loadImages : function () {
				var i = 0;
				$(SQI.LOAD_IMAGE_BLUE).each(function() {
				this.src=$(this).attr("loadSRC");
					var o = {
						index: i++,
						title: $(this).attr("alt"),
						url: $(this).attr("loadURL")
					};	
					SQI.Data[o.index] = o;
				});
				SQI.COUNT = i;
			},
			initRollImages : function (_b) {
			
				SQI.OPER.loadImages(SQI.OPER.LOAD_IMAGE_BLUE);
			
				var a = ".RollImages li";
				var g = ".sqi_c a";
				var b = 5000;
				var d = -1;
				var f = SQI.COUNT;
				var e = ".imgAlt,.sqi_c";
				if (_b) {
					b = _b;
				}
				
				var id = $(SQI.SOURCE);
				var div = $(".d2");
				var button = $("<div class=sqi_c></div>");
				var title = $("<div class=imgAlt align='left'></div>");
				
				id.append(title);
				id.append(button);
				
				id.addClass("main_div_1");
			
				$.each(SQI.Data, function (i,o) {
					
					var b = "<span><a href='javascript:void(0)'>"+(parseInt(i)+1)+"</a></span>";
					button.append(b);
				});
				var t = setTimeout(function () {
					c();
					clearTimeout(t);
				}, 1);
				var h = setInterval(c, b);
					
				$(e).mouseover(function() {
					clearInterval(h);
				});
				
				$(e).mouseout(function() {
					h = setInterval(c, b);
				});
				
				$(g).mouseover(function() {
					clearInterval(h);
					$(this).addClass("on").parent().siblings().children("a")
									.removeClass("on");
									
					d = $(".sqi_c span").index($(this).parent());
					$(a).eq(d).show().siblings().hide();
					var s = "<span><a style='outline: none' href='"+SQI.Data[d].url+"'>"+SQI.Data[d].title+"</a></span>";
					title.html(s);
					title.find("a").hide().fadeIn(b/2);
				});
				
				id.find("div").remove(".loading");
		
				function c() {
					d++;
					var i = d % (f);
					$($(a)[i]).show().siblings().hide();
					$($(g)[i]).addClass("on").parent().siblings().children("a")
								.removeClass("on");
					if (f<=d){
						p=0;d=0;
					} else {
						p = d;
					}
					
					var s = "<span><a href='"+SQI.Data[p].url+"'>"+SQI.Data[p].title+"</a></span>";
					title.html(s);
					title.find("a").hide().fadeIn(b/2);
					
				}
			}
		}
		
	}
}
var SQI_OPER = SQI.OPER;

$(function() {
	SQI_OPER.initRollImages();
});