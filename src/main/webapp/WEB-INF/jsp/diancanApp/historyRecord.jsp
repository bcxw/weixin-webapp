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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0"/>
<title>我的历史记录</title>
<link rel="stylesheet" href="css/dcApp_base.css"/>

</head>

<body>
<div class="layout">
	<header class="header">
    	<div class="indexLink">
        	<a href="javascript:history.back(-1);" class="iconfont icon-fanhui"></a>  
        </div>
         <h3>我的历史记录</h3>
    </header>
   	<section id="historyList">
    	<ul>
	        <c:forEach var="item" items="${userMealHistory}">
				<li>
	            	<div class="hisLeft">
	                	<span class="hisDay">${item.meal_date}</span>
	                    <span>(周${(item.meal_week eq "0")?"日":item.meal_week})</span>
	                </div>
	            	<div class="hisRight">
	                	<span style='${(item.breakfast eq 1)?"":"background: #fff;color:#ccc"}' class="bf">早</span>
	                	<span style='${(item.lunch eq 1)?"":"background: #fff;color:#ccc"}' class="lh">中</span>
	                    <span style='${(item.dinner eq 1)?"":"background: #fff;color:#ccc"}' class="dr">晚</span>
	                </div>
	            </li>
			</c:forEach>
        </ul>
    
    </section>
</div>

</body>
</html>
