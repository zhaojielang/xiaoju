;(function($) {
	$.china = {
		provCode : "",
		cityCode : "",
		countyCode : ""
	};
	
	$.url = {
		provUrl : contextPath + "/getUserProvinceOption.jhtml",
		cityUrl : contextPath + "/getUserCityOption.jhtml",
		areaUrl : contextPath + "/getUserCountyOption.jhtml"
	};
	
	var url = contextPath + "/getChinaUnitCode.jhtml";
		
	$.fn.getProvince = function(value, callback) {

		$(this).load(url, {
			parentCode : value
		}, function () {
			setTimeout(function(){
				
				if (callback) {
					try {
						eval(callback).call(window, '');
					} catch(e) {}
				}
			},1);
		});
	};

	$.fn.getCity = function(value, countyObj) {

		if (value == '') {
			$(this).html("<option value=''>-- 请选择 --</option>");
			$("#"+countyObj).html("<option value=''>-- 请选择 --</option>");
		} else {
			$(this).load(url, {
				parentCode : value
			});
			
			$("#"+countyObj).html("<option value=''>-- 请选择 --</option>");
		}
	};
	
	$.fn.getCounty = function (value) {

		if (value == '') {
			$(this).html("<option value=''>-- 请选择 --</option>");
		} else {
			
			$(this).load(url, {
				parentCode : value
			});
		}
	};

	$.fn.loadProvince = function(value, callback) {

		$(this).load(url, {
			parentCode : value
		}, function () {
			setTimeout(function(){
				
				if (callback) {
					try {
						eval(callback).call(window, '');
					} catch(e) {}
				} else {
					$(this).val($.china.provCode);
				}
			},1);
		});
	};

	$.fn.loadCity = function(value, callback) {

		if (value == '') {
			$(this).html("<option value=''>-- 请选择 --</option>");
		} else {

			$(this).load(url, {
				parentCode : value
			}, function () {
				setTimeout(function(){
					
					if (callback) {
						try {
							eval(callback).call(window, '');
						} catch(e) {}
					} else {
						$(this).val($.china.cityCode);
					}
				},1);
			});
		}
	};

	$.fn.loadCounty = function(value, callback) {

		if (value == '') {
			$(this).html("<option value=''>-- 请选择 --</option>");
		} else {

			$(this).load(url, {
				parentCode : value
			}, function () {
				setTimeout(function(){
					
					if (callback) {
						try {
							eval(callback).call(window, '');
						} catch(e) {}
					} else {
						$(this).val($.china.countyCode);
					}
				},1);
			});
		}
	};
	jQuery.initCode = function(parameter) {

		parameter = parameter || {};

		$.each(parameter, function(key, value) {
			if ($.china.parameter[key]) {
				$.extend($.china.parameter[key], value);
			}
		});
	};
	

	$.fn.getUserProvince = function() {

		$(this).load($.url.provUrl);
	};
	
	$.fn.getUserCity = function(value, callback) {

		if (value == undefined || value == '') {
			$(this).html("<option value=''>-- 请选择 --</option>");
		} else {

			$(this).load($.url.cityUrl, {
				parentCode : value
			}, function () {
				setTimeout(function(){
					
					if (callback) {
						try {
							eval(callback).call(window, '');
						} catch(e) {}
					} else {
						$(this).val($.china.countyCode);
					}
				},1);
			});
		}
	};
	
	$.fn.getUserCounty = function(value, callback) {

		if (value == undefined || value == '') {
			$(this).html("<option value=''>-- 请选择 --</option>");
		} else {

			$(this).load($.url.areaUrl, {
				parentCode : value
			}, function () {
				setTimeout(function(){
					
					if (callback) {
						try {
							eval(callback).call(window, '');
						} catch(e) {}
					} else {
						$(this).val($.china.countyCode);
					}
				},1);
			});
		}
	};
})(jQuery);