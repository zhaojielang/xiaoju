;(function($) {
	$.fn.getPlatEnt = function(id){
		var url = contextPath+"/basic/platEntAction.jhtml?bizType=getPlatEntList";
		$.ajax({
			url : url,
			dataType : "json",
			success : function(data){
				var html = "<option value='' selected='selected'>请选择</option>";
				$.each(data,function(i,temp){
					html += "<option value='"+temp.platEntNo+"'>"+temp.platEntName+"</option>";
				});
				$("#"+id).html(html);
			}
		});
	}
})(jQuery);