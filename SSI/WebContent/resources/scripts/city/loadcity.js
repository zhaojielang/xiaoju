//动态加载市级
function getCitys(provCode){
   if("" != provCode){
      $("#city").load("<c:url value='/areaCode/getCitys.jhtml?bizType=getCitys&provCode='/>"+provCode);
      $("#district").html("<option value=''>请选择</option>");
   }else{
      $("#city,#district").html("<option value=''>请选择</option>");
   }
}
//加载县级
function getCountrys(cityCode){
   if("" != cityCode){
       $("#district").load("<c:url value='/areaCode/getCountrys.jhtml?bizType=getCountrys&cityCode='/>"+cityCode);
   }else{
      $("#district").html("<option value=''>请选择</option>");
   }
}
//动态加载社区
function getShequ(district){
   if("" != district){
       $("#shequ").load("<c:url value='/areaCode/getShequ.jhtml?bizType=getShequ&district='/>"+district);
   }else{
       $("#shequ").html("<option value=''>请选择</option>");
   }
}



//动态加载市级
function getCityss(provCode){
   if("" != provCode){
      $("#citys").load("<c:url value='/areaCode/getCitys.jhtml?bizType=getCitys&provCode='/>"+provCode);
      $("#districts").html("<option value=''>请选择</option>");
   }else{
      $("#citys,#districts").html("<option value=''>请选择</option>");
   }
}
//加载县级
function getCountryss(cityCode){
   if("" != cityCode){
       $("#districts").load("<c:url value='/areaCode/getCountrys.jhtml?bizType=getCountrys&cityCode='/>"+cityCode);
   }else{
      $("#districts").html("<option value=''>请选择</option>");
   }
}
//动态加载社区
function getShequs(district){
   if("" != district){
       $("#shequs").load("<c:url value='/areaCode/getShequ.jhtml?bizType=getShequ&district='/>"+district);
   }else{
       $("#shequs").html("<option value=''>请选择</option>");
   }
}



