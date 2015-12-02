;(function($) {
	
	var init_info, showClass, hideClass, mode = "click";
	var info;
	$.fn.proxy = function() {
		
		if ("click" == mode) {
			return;
		}

		id = $(this).attr("id");
		relid = info.data[id];

		if (info.hasUrl[id]) {

			var t = info.target[id] ? info.target[id] : relid;

			$.tab.sendAjax(id, relid, t);
		} else {
			$("#" + id).swapTab();
		}

		return $;
	};
	
	$.fn.swapTab = function() {

		id = $(this).attr("id");
		relid = info.data[id];

		for ( var _id in info.data) {

			$("#" + _id).removeClass(showClass);
			$("#" + _id).addClass(hideClass);
			$("#" + info.data[_id]).hide();
		}

		$("#" + id).removeClass(hideClass);
		$("#" + id).addClass(showClass);

		$("#" + relid).show();
		try {
			$.unblockUI();
		} catch(e){}
		return $;
	};
	
	
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	$.tab = {

		info : {
			data : null,
			style : {
				show : "",
				hide : ""
			},
			mode : "click",
			action : null,
			target : {},
			parameter : {},
			callback : {},
			times : {},
			hasUrl : {}
		},
			
		passParam : function(parameter) {

			parameter = parameter || {};

			$.each(parameter, function(key, value) {
				if ($.tab.info.parameter[key]) {
					$.extend($.tab.info.parameter[key], value);
				}
			});
			
		},

		initTab : function(settings) {

			settings = settings || {};

			if (!settings.style.show) {
				settings.style.show = "";
			}

			if (!settings.style.hide) {
				settings.style.hide = "";
			}

			$.extend($.tab.info, settings);

			if ($.tab.info.action) {

				$.each($.tab.info.action, function(id, url) {

					if (url) {

						$.tab.info.hasUrl[id] = true;

						if ($.tab.info.times
								&& $.tab.info.times[id]
								&& ("auto" == $.trim($.tab.info.times[id])
										.toLowerCase())) {
							$.tab.info.times[id] = "auto";
						} else {
							$.tab.info.times[id] = "";
						}

						if ($.tab.info.parameter[id]) {
							return;
						} else {
							$.tab.info.parameter[id] = {};
						}

						if (!document.getElementById($.tab.info.target[id])) {
							$.tab.info.target[id] = null;
						}
					} else {
						$.tab.info.parameter[id] = {};
						$.tab.info.target[id] = "";
						$.tab.info.callback = {};
						$.tab.info.hasUrl[id] = false;
					}
				});
			} else {
				$.tab.info.action = null;
				$.tab.info.parameter = {};
				$.tab.info.target = {};
				$.tab.info.callback = {};
				$.tab.info.hasUrl = {};
			}

			if (null == init_info) {
				init_info = new Array();
			}

			init_info.push($.tab.info);
			info = $.tab.info;

			if ($.tab.info.style) {
				showClass = ($.tab.info.style.show).replace(/[,#!@%&()]/g, " ")
						.replace(/\s{2,}/g, " ");
				hideClass = ($.tab.info.style.hide).replace(/[,#!@%&()]/g, " ")
						.replace(/\s{2,}/g, " ");
			}

			if ($.tab.info.mode) {
				
				if ("click" != $.trim($.tab.info.mode).toLowerCase()) {
					mode = "proxy";
				}
			}

			$.tab.bindEvent();
		},
			
		sendAjax :	function (thisid, relid, target) {

				if (true == $("#" + relid).data("state")) {
					$("#" + thisid).swapTab();
				} else {

					var nowTab = document.getElementById(thisid);
					$.post(info.action[thisid], info.parameter[thisid],
						function(data, status) {
							setTimeout(function() {
								try {
									$.unblockUI();
								} catch(e){}
								if (info.callback[nowTab.id]) {

									if (!info.callback[nowTab.id](data, status)) {
										return false;
									}
								} else {

									if ("error" != status) {

										$("#" + target).html(data);
									}
								}

								$("#" + thisid).swapTab();

								if ("auto" != info.times[thisid]) {

									$("#" + relid).data("state", true);
								}

							}, 1);
						}
					);
				}
			},

		bindEvent : function() {

			info = init_info.pop();

			$.each(info.data, function(id, relid) {

				if (document.getElementById(id)
						&& document.getElementById(relid)) {

					$("#" + id).css("cursor", "pointer");

					if ("proxy" == mode) {

						return;
					}

					if (info.hasUrl[id]) {

						var t = info.target[id] ? info.target[id] : relid;

						$("#" + id).bind("click", function(event) {
							
							$.tab.sendAjax(id, relid, t);
						});
					} else {
						$("#" + id).bind("click",
							function() {
								$("#" + id).swapTab();
							}
						);
					}
				}
			});

		}
	};

	jQuery.initTab = function(o) {
		$.tab.initTab(o);
	};
		
	jQuery.passParam = function(o) {
		$.tab.passParam(o);
	};

	jQuery.fn.extend( {
		initTab : function(o) {
			$.tab.initTab(o);
		},
		passParam : function(o) {
			$.tab.passParam(o);
		}
	});
})(jQuery);