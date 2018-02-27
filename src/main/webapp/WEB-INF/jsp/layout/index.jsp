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
	<title>无标题文档</title>
	<link rel="stylesheet" href="css/base.css"/>
</head>

<body>
<div class="layout">
	<header class="header">
    	<div class="indexLink">
        	<a href="#" class="iconfont icon-fanhui"></a>  
        </div>
         <h3>泽易智慧报餐系统</h3>
    </header>
   
   	<section class="main">
    	<div class="indexImg">
        	<img src="images/indexImg.png"/>
        </div>
        <div class="indexCount">
        	<p class="countTit">
            	<a href="javascript:;">今日报餐人数</a>
            </p>
            <div class="countMsg">	
                <span>早餐：<strong>15</strong>人</span>
                <span>午餐：<strong>25</strong>人</span>
                <span>晚餐：<strong>20</strong>人</span>
            </div>
            <p class="countTx">18:00开始明天的报餐</p>
        	<p class="bcStatus">您今天没有订餐</p>
            
        </div>
    	
    </section> 
   	
</div>
<footer>
   		<p>
        	<a href="#">我的历史记录</a>
        </p>
   </footer>

</body>
</html>
