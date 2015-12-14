<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
	<script type="text/javascript">
		var _menus = {"menus":[
							{"menuid":"1","icon":"icon-sys","menuname":"控件使用",
								"menus":[
										{"menuid":"12","menuname":"疯狂秀才","icon":"icon-add","url":"http://hxling.cnblogs.com"},
										{"menuid":"13","menuname":"用户管理","icon":"icon-users","url":"user/user_list.jsp"},
										{"menuid":"14","menuname":"角色管理","icon":"icon-role","url":"demo2.html"},
										{"menuid":"15","menuname":"权限设置","icon":"icon-set","url":"demo.html"},
										{"menuid":"16","menuname":"系统日志","icon":"icon-log","url":"demo1.html"}
									]
							},{"menuid":"8","icon":"icon-sys","menuname":"员工管理",
								"menus":[{"menuid":"21","menuname":"员工列表","icon":"icon-nav","url":"demo.html"},
										{"menuid":"22","menuname":"视频监控","icon":"icon-nav","url":"demo1.html"}
									]
							},{"menuid":"56","icon":"icon-sys","menuname":"部门管理",
								"menus":[{"menuid":"31","menuname":"添加部门","icon":"icon-nav","url":"demo1.html"},
										{"menuid":"32","menuname":"部门列表","icon":"icon-nav","url":"demo2.html"}
									]
							},{"menuid":"28","icon":"icon-sys","menuname":"财务管理",
								"menus":[{"menuid":"41","menuname":"收支分类","icon":"icon-nav","url":"demo.html"},
										{"menuid":"42","menuname":"报表统计","icon":"icon-nav","url":"demo1.html"},
										{"menuid":"43","menuname":"添加支出","icon":"icon-nav","url":"demo2.html"}
									]
							},{"menuid":"39","icon":"icon-sys","menuname":"商城管理",
								"menus":[{"menuid":"51","menuname":"商品分类","icon":"icon-nav","url":"demo.html"},
										{"menuid":"52","menuname":"商品列表","icon":"icon-nav","url":"demo1.html"},
										{"menuid":"53","menuname":"商品订单","icon":"icon-nav","url":"demo2.html"}
									]
							}
					]};
		
		$(document).ready(function(){
			$("#nav").accordion({animate:false});
		    $.each(_menus.menus, function(i, n) {
				var menulist ='';
				menulist +='<ul>';
		        $.each(n.menus, function(j, o) {
					menulist += '<li><div><a ref="'+o.menuid+'" href="#" rel="' + o.url + '" ><span class="icon '+o.icon+'" >&nbsp;</span><span class="nav">' + o.menuname + '</span></a></div></li> ';
		        });
				menulist += '</ul>';
	
				$('#nav').accordion('add', {
		            title: n.menuname,
		            content: menulist,
		            iconCls: 'icon ' + n.icon
		        });
		    });
		    initMenu();
		});
		
		
		function initMenu(){
			$('#nav li a').click(function(){
				var tabTitle = $(this).children('.nav').text();
				var url = $(this).attr("rel");
				var menuid = $(this).attr("ref");
				var icon = getIcon(menuid,icon);
				addTab(tabTitle,url,icon);
			});
		}
		
		function createFrame(url){
			var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			return s;
		}
		
		function addTab(subtitle,url,icon){
			if(!$('#tabs').tabs('exists',subtitle)){
				$('#tabs').tabs('add',{
					title:subtitle,
					content:createFrame(url),
					closable:true,
					icon:icon
				});
			}else{
				$('#tabs').tabs('select',subtitle);
				$('#mm-tabupdate').click();
			}
			tabClose();
		}
		
		function tabClose(){
			/*双击关闭TAB选项卡*/
			$(".tabs-inner").dblclick(function(){
				var subtitle = $(this).children(".tabs-closable").text();
				$('#tabs').tabs('close',subtitle);
			});
			/*为选项卡绑定右键*/
			$(".tabs-inner").bind('contextmenu',function(e){
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
				var subtitle =$(this).children(".tabs-closable").text();
				$('#mm').data("currtab",subtitle);
				$('#tabs').tabs('select',subtitle);
				return false;
			});
		}
	
		//获取左侧导航的图标
		function getIcon(menuid){
			var icon = 'icon ';
			$.each(_menus.menus, function(i, n) {
				 $.each(n.menus, function(j, o) {
				 	if(o.menuid==menuid){
						icon += o.icon;
					}
				 });
			});
			return icon;
		}
	</script>
</head>
<body>
	<div id="cc" class="easyui-layout" style="width: 100%; height: 100%;">
		<div data-options="region:'north',split:true"
			style="height: 100px;">
			xxx管理系统
		</div>
		<div id="nav" data-options="region:'west',title:'West'" style="width: 23%;">
			
		</div>
<!-- 		<div data-options="region:'south',split:true"style="height: 100px;" > -->
<!-- 		</div> -->
		<div data-options="region:'center'" style="padding: 5px; background: #eee;">
			<div id="tabs" class="easyui-tabs" fit="true" border="false">
			
			</div>
		</div>
	</div>
</body>
</html>