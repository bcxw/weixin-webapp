<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"
	+request.getServerPort()+path+"/";
%>
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>系统主界面</title>
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link href="css/base.css" rel="stylesheet"/>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		$(function(){
			
			$("#tree").tree({
				lines:true,
				url:'<%=basePath%>authorize/menu',
				onBeforeLoad:function(node,param) {
                    param.parentId=-1
        		},
				onLoadSuccess:function(){
					$("#tree").tree('expandAll');
				},
				onClick:function(node){
					if(node.id==10){
						logout();
					}else if(node.id==9){
						openPasswordModifyDialog();
					}else if(node.functionPath){
						openTab(node);
					}
				}
			});
			
			function openTab(node){
				if($("#tabs").tabs("exists",node.text)){
					$("#tabs").tabs("select",node.text);
				}else{
					var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src="+node.functionPath+"></iframe>"
					$("#tabs").tabs("add",{
						title:node.text,
						iconCls:node.iconCls,
						closable:true,
						content:content
					});
				}
			}
		});
		
		function logout(){
			$.messager.confirm('系统提示','您确定要退出系统吗？',function(r){
				if(r){
					window.location.href='<%=basePath%>logout';
				}
			});
		}
		
		function accountInfo(){
			$.messager.alert('账户信息','${currentUser.userName }&nbsp;『${roleName}』');
		}
		
		function openPasswordModifyDialog(){
			url="<%=basePath%>user/updateUserPassword";
			$("#dlg").dialog("open").dialog("setTitle","修改密码");
		}
		
		function modifyPassword(){
			$("#fm").form("submit",{
				url:url,
				onSubmit:function(){
					var oldPassword=$("#oldPassword").val();
					var newPassword=$("#newPassword").val();
					var newPassword2=$("#newPassword2").val();
					if(!$(this).form("validate")){
						return false;
					}
					if(oldPassword!='${currentUser.password}'){
						$.messager.alert('系统提示','用户名密码输入错误！');
						return false;
					}
					if(newPassword!=newPassword2){
						$.messager.alert('系统提示','确认密码输入错误！');
						return false;
					}
					return true;
				},
				success:function(result){
					var result=eval('('+result+')');
					$.messager.alert('系统提示',result.message);
					closePasswordModifyDialog();
				}
			});
		}
		
		function closePasswordModifyDialog(){
			$("#dlg").dialog("close");
			$("#oldPassword").val("");
			$("#newPassword").val("");
			$("#newPassword2").val("");
		}
	</script>
</head>

<body class="easyui-layout">
<style>
	#northHeader .northRbtn{  display:inline-block; float: right; height: 66px;line-height: 66px;font-size: 14px; cursor:pointer; text-decoration:none; color:#000;}
	#northHeader .northRbtn:hover{ color:red;}
	#northHeader table td h2{ font-size:28px;}
	#tree span{font-weight: bold; color: #000;}
</style>
<div region="north" style="height: 68px;background:#E0ECFF;">
<div id="northHeader" style="padding: 0px;margin: 0px auto; width:98%;">
<table style="float:left;">
	<tr>
		<td>
        	<img style="height:55px;width:55px;margin-top:3px;display:block;float:left" src="images/jmlogo.png"/>
        	<p style="display:block;float:left;color:#3671AF;font-weight:bold;font-size:22px;padding:20px 0px 0px 5px;">泽易报餐后台管理系统</p>
        </td>
	</tr>
</table>
	<style>
		@font-face {font-family: "iconfont";
		  src: url('../font/iconfont.eot?t=1493793082843'); /* IE9*/
		  src: url('../font/iconfont.eot?t=1493793082843#iefix') format('embedded-opentype'), /* IE6-IE8 */
		  url('../font/iconfont.woff?t=1493793082843') format('woff'), /* chrome, firefox */
		  url('../font/iconfont.ttf?t=1493793082843') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
		  url('../font/iconfont.svg?t=1493793082843#iconfont') format('svg'); /* iOS 4.1- */
		}
		
		#account .accIcon{ font-family:'iconfont';}
		.icon-yonghu:before { content: "\e624"; font-size:28px; line-height:66px; }
		.icon-account1:before { content: "\e6b8"; }
		.icon-mima:before { content: "\e620"; }
		.icon-xiala:before { content: "\e6b9"; }
		.icon-account:before { content: "\e6a3"; }
		.icon-yanzhengmayanzheng:before { content: "\e604"; }
		#account{ position:absolute; height:66px; line-height:66px; top:0; right:50px; text-align:center; padding:0 10px; cursor:pointer; border-right:1px solid #e0ecff;}
		#accLink{  display:none; padding:10px 0; position:absolute; background:#fff; top:66px; right:50px; z-index:111; width:140px; height:140px; border:none; border-left:1px solid #ccc; border-right:1px solid #ccc; border-bottom:1px solid #ccc;}
		#accLink a{ display:block; width:140px; height:35px; line-height:35px; font-size:14px; text-align:center; color:#444; text-decoration:none;}
		#accLink a:hover{ color:#95B8E7;}
	</style>
    <div id="account">
    	
        <span style="font-size:14px;"> ${admin} </span>
		
    </div>
	
	
	
	 <script>
		$(function(){
			
			var timer=null;
			
			
			$("#accLink").hover(function(){
				clearTimeout(timer);
				},function(){
					$("#accLink").css("display","none");
					$("#account").css({"background":"none","border-right":"1px solid #e0ecff","color":"#000"});
					});

			});
			
    </script>
    
    
   
</div>
</div>
<div region="center">
	<div class="easyui-tabs" fit="true" border="false" id="tabs">
		<div title="首页" data-options="iconCls:'icon-home'">
			<div align="center" style="padding-top: 100px;"><font color="red" size="10">欢迎使用</font></div>
		</div>
	</div>
</div>
<div region="west" style="width: 220px;padding: 5px;" title="导航菜单" split="true">
<ul id="tree" class="easyui-tree">
	<li data-options="functionPath:'<%=basePath%>mealManage/accountMeal'"><span>报餐结算</span></li>
	<li data-options="functionPath:'<%=basePath%>employeeManage/index'"><span>员工管理</span></li>
</ul>
</div>


<div id="dlg" class="easyui-dialog" style="width: 400px;height: 220px;padding: 10px 20px" 
 closed="true" buttons="#dlg-buttons" data-options="iconCls:'icon-modifyPassword'">
 <form id="fm" method="post">
 	<table cellspacing="4px;">
 		<tr>
 			<td>用户名：</td>
 			<td><input type="hidden" name="id" id="id" value="${currentUser.id }"><input type="text" name="userName" id="userName" readonly value="${currentUser.userName }" style="width: 200px;" /></td>
 		</tr>
 		<tr>
 			<td>原密码：</td>
 			<td><input type="password" class="easyui-validatebox" name="oldPassword" id="oldPassword" style="width: 200px;" required /></td>
 		</tr>
 		<tr>
 			<td>新密码：</td>
 			<td><input type="password" class="easyui-validatebox" name="newPassword" id="newPassword" style="width: 200px;" required  /></td>
 		</tr>
 		<tr>
 			<td>确认新密码：</td>
 			<td><input type="password" class="easyui-validatebox" name="newPassword2" id="newPassword2" style="width: 200px;" required /></td>
 		</tr>
 	</table>
 </form>
</div>
<div id="dlg-buttons">
	<a href="javascript:modifyPassword()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closePasswordModifyDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

</body>
</html>