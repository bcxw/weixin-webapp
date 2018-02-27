<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"
	+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0"/>
	<title>登录_泽易报餐后台管理系统</title>
	<style>
			*,
		::before,
		::after{ padding: 0; margin: 0; -webkit-tap-highlight-color: transparent;-webkit-box-sizing:border-box; box-sizing: border-box;}
		body{ font-size: 14px; color: #333; font-family: "MicroSoft YaHei","sans-serif"*	}
		ul,ol,li{ list-style: none; }
		a{ text-decoration: none; color: #333; }
		a:hover{ color: #333;}
		input,textarea{ border: none; outline: none; resize: none;-webkit-appearance: none;}
		
		@font-face {font-family: "iconfont";
		  src: url('font/iconfont.eot?t=1493951929307'); /* IE9*/
		  src: url('font/iconfont.eot?t=1493951929307#iefix') format('embedded-opentype'), /* IE6-IE8 */
		  url('font/iconfont.woff?t=1493951929307') format('woff'), /* chrome, firefox */
		  url('font/iconfont.ttf?t=1493951929307') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
		  url('font/iconfont.svg?t=1493951929307#iconfont') format('svg'); /* iOS 4.1- */
		}
		
		.iconfont {
		  font-family:"iconfont" !important;
		  font-size:16px;
		  font-style:normal;
		  -webkit-font-smoothing: antialiased;
		  -moz-osx-font-smoothing: grayscale;
		}
		
		.icon-fanhui:before { content: "\e604"; }
		
		.icon-Secret:before { content: "\e602"; }
		
		.icon-account:before { content: "\e6a3"; }
		.layout{ position:relative; max-width:640px; min-width:300px; margin:0 auto;}
		.backIndex{ width:100%; height:45px; line-height:45px;}
		.indexLink{ position:absolute; top:0; left:5px;}
		.indexLink a{ cursor:pointer; font-size:14px; color:#999;}
		.login{ width:80%; margin:0 auto;}
		.logo{ width:95%; text-align:center; margin:30px auto;}
		.logo img{ width:100%;}
		.layout form{ width:100%; overflow:hidden;}
		.layout form p{ margin-bottom:20px; height:35px; width:100%; position:relative; z-index:98;}
		.layout form p span{ position:absolute; top:6px; left:0; font-family:'iconfont'; font-size:20px;}
		.layout form p input{ font-size:14px; height:35px; line-height:35px; width:100%; padding-left:30px; border: 0; border-bottom: 1px solid #f0f0f0; outline:none;}
		.layout form p input:focus{ border-bottom:1px solid #3f68c1;}
		.layout form button{ width:100%; height:45px; line-height:45px; background:#3f68c1; text-align:center; color:#fff; border-radius:8px; border:none; font-size:14px; margin-top:10px;}
		.layout form p .forget{ position:absolute; right:0; top:0; height:35px; line-height:35px; color:#999;}
		.layout .goRegister{ text-align:right; height:45px; line-height:45px;} 
		.layout .goRegister span{ color:#999;}
		.layout .goRegister a{ color:#3f68c1; text-decoration:underline;}
	
	</style>
</head>

<body>

<div class="layout">
	<div class="backIndex">
    	
    </div>
	<div class="login">
    	<div class="logo"><img src="images/logo.jpg"/></div>
    	<form method="post" action="<%=basePath%>admin/login">
        
        	<p>
            	<span class="iconfont icon-account"></span>
            	<input name="username" type="text" placeholder="请输入用户名" />
            </p>
            <p>
            	<span class="iconfont icon-Secret"></span>
            	<input name="password" type="password" placeholder="请输入密码"/>
                
            </p>
            <button type="submit">登录</button>
            <p style="padding-top:10px;color:red;text-align:center">${errMsg} </p>
        </form>
        <div class="goRegister">
        	
        </div>
	</div>
</div>



</body>
</html>
