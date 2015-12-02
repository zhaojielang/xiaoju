/**
 * 对Date的扩展，将 Date 转化为指定格式的String 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
 * 可以用 1-2 个占位符 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04 
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04 
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04 
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.pattern = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	var week = {
		"0" : "\u65e5",
		"1" : "\u4e00",
		"2" : "\u4e8c",
		"3" : "\u4e09",
		"4" : "\u56db",
		"5" : "\u4e94",
		"6" : "\u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt
				.replace(
						RegExp.$1,
						((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f"
								: "\u5468")
								: "")
								+ week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

/** 
 * js 对date加减 
 */  
  
Date.prototype.Format = function(fmt) {  
    // author: meizz  
    var o = {  
        "M+" : this.getMonth() + 1, // 月份  
        "d+" : this.getDate(), // 日  
        "h+" : this.getHours(), // 小时  
        "m+" : this.getMinutes(), // 分  
        "s+" : this.getSeconds(), // 秒  
        "q+" : Math.floor((this.getMonth() + 3) / 3), // 季度  
        "S" : this.getMilliseconds()  
    // 毫秒  
    };  
    if (/(y+)/.test(fmt))  
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
                - RegExp.$1.length));  
    for (var k in o)  
        if (new RegExp("(" + k + ")").test(fmt))  
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1)  
                    ? (o[k])  
                    : (("00" + o[k]).substr(("" + o[k]).length)));  
    return fmt;  
}  
  
Date.prototype.addDays = function(d) {  
    this.setDate(this.getDate() + d);  
};  
  
Date.prototype.addWeeks = function(w) {  
    this.addDays(w * 7);  
};  
  
Date.prototype.addMonths = function(m) {  
    var d = this.getDate();  
    this.setMonth(this.getMonth() + m);  
  
    if (this.getDate() < d)  
        this.setDate(0);  
};  
  
Date.prototype.addYears = function(y) {  
    var m = this.getMonth();  
    this.setFullYear(this.getFullYear() + y);  
  
    if (m < this.getMonth()) {  
        this.setDate(0);  
    }  
};  
  
//js格式化时间  
Date.prototype.toDateString = function(formatStr) {  
    var date = this;  
    var timeValues = function() {  
    };  
    timeValues.prototype = {  
        year : function() {  
            if (formatStr.indexOf("yyyy") >= 0) {  
                return date.getYear();  
            } else {  
                return date.getYear().toString().substr(2);  
            }  
        },  
        elseTime : function(val, formatVal) {  
            return formatVal >= 0 ? (val < 10 ? "0" + val : val) : (val);  
        },  
        month : function() {  
            return this.elseTime(date.getMonth() + 1, formatStr.indexOf("MM"));  
        },  
        day : function() {  
            return this.elseTime(date.getDate(), formatStr.indexOf("dd"));  
        },  
        hour : function() {  
            return this.elseTime(date.getHours(), formatStr.indexOf("hh"));  
        },  
        minute : function() {  
            return this.elseTime(date.getMinutes(), formatStr.indexOf("mm"));  
        },  
        second : function() {  
            return this.elseTime(date.getSeconds(), formatStr.indexOf("ss"));  
        }  
    }  
    var tV = new timeValues();  
    var replaceStr = {  
        year : ["yyyy", "yy"],  
        month : ["MM", "M"],  
        day : ["dd", "d"],  
        hour : ["hh", "h"],  
        minute : ["mm", "m"],  
        second : ["ss", "s"]  
    };  
    for (var key in replaceStr) {  
        formatStr = formatStr.replace(replaceStr[key][0], eval("tV." + key  
                + "()"));  
        formatStr = formatStr.replace(replaceStr[key][1], eval("tV." + key  
                + "()"));  
    }  
    return formatStr;  
}  
  
function formatStrDate(date) {  
    var str = date.toDateString("yyyy-MM-dd hh:mm:ss");  
    if (str.indexOf("00:00:00") != -1) {  
        return str.replace("00:00:00", "10:00:00");  
    }  
    return str;  
}  
function dateQuery(){
		var s1 = $('#startTime').val();
		var s2 = $('#endTime').val();
		if(s1 == "" || s1 == null || s2 == "" || s2 == null){
			alert("导出时，开始日期和结束日期均不能为空!");
			return false;
		}
		
		var startDate = new Date(s1.replace(/-/g, "/"));
		var endDate = new Date(s2.replace(/-/g, "/"));;
		var days = endDate.getTime() - startDate.getTime();
		var time = parseInt(days / (1000 * 60 * 60 * 24));
		if(time > 31){
			alert("由于导出数据量过大，请按时间段分别查询后导出，查询时间跨度不超过31天。");
			return false;
		}else{
			return true;
		}
	}