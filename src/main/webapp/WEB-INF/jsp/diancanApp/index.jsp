<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"
	+request.getServerPort()+path+"/";
	
	SimpleDateFormat formatter = new SimpleDateFormat ("MM-dd");
	SimpleDateFormat formatterAll = new SimpleDateFormat ("yyyy-MM-dd");
	Date date=new Date();
	String todayDateText=formatter.format(date);
	String nowDateStr=formatterAll.format(date);
	Calendar  calendar = Calendar.getInstance();
	calendar.setTime(date);
	
	int nowHour=calendar.get(Calendar.HOUR_OF_DAY);
	
	int todayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	todayWeek=todayWeek<0?0:todayWeek;
	
	calendar.add(calendar.DATE,1);
	String tomorrowDateText=formatter.format(calendar.getTime());
	String tomorrowDateStr=formatterAll.format(calendar.getTime());
	int tomorrowWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	tomorrowWeek=tomorrowWeek<0?0:tomorrowWeek;
	
	String todayBreakfirstLink="weixin/currentMealRecord?meal=breakfast&mealDate="+nowDateStr;
	String todayLunchLink="weixin/currentMealRecord?meal=lunch&mealDate="+nowDateStr;
	String todayDinnerLink="weixin/currentMealRecord?meal=dinner&mealDate="+nowDateStr;
	
	String tomorrowBreakfirstLink="weixin/currentMealRecord?meal=breakfast&mealDate="+tomorrowDateStr;
	String tomorrowLunchLink="weixin/currentMealRecord?meal=lunch&mealDate="+tomorrowDateStr;
	String tomorrowDinnerLink="weixin/currentMealRecord?meal=dinner&mealDate="+tomorrowDateStr;
	
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0"/>
<title>泽易智慧报餐系统</title>
<link rel="stylesheet" href="css/dcApp_base.css"/>
<script type="text/javascript" src="js/jquery.min.js"></script>

<style>


#popupTipWin{
	position: absolute;   
    top: 0px;   
    left: 0px;   
    z-index: 10001;   
    display:none;   
    filter:alpha(opacity=60);   
    background-color: #777;   
    opacity: 0.5;   
    -moz-opacity: 0.5;   
    height:100%;width:100%;
}
#resutltTip{
	position: absolute;   
    top: 0px;   
    left: 0px;   
    z-index: 10001;   
    display:none; 
    height:100%;width:100%;background-color: #fff;   
    text-align:center;
}




</style>

<script type="text/javascript">
	var todayDateText="<%=todayDateText%>";
	var todayWeek="<%=todayWeek%>";
	var tomorrowDateText="<%=tomorrowDateText%>";
	var tomorrowWeek="<%=tomorrowWeek%>";
	
	var breakfastTerminalTime=parseInt("${breakfastTerminalTime}");
	var lunchTerminalTime=parseInt("${lunchTerminalTime}");
	var dinnerTerminalTime=parseInt("${dinnerTerminalTime}");
	var nowHour=parseInt("<%=nowHour%>");
	
	var mybreakfast1="${userMealInfo.breakfast1}";
	var mylunch1="${userMealInfo.lunch1}";
	var mydinner1="${userMealInfo.dinner1}";
	
	var mybreakfast2="${userMealInfo.breakfast2}";
	var mylunch2="${userMealInfo.lunch2}";
	var mydinner2="${userMealInfo.dinner2}";
	
	
	var userName="${userName}";
	var restaurantName="${restaurantName}";
	
</script>
</head>

<body>
<div class="layout">
	<header class="header">
    	<div class="indexLink">
        	  
        </div>
         <h3>泽易智慧报餐系统</h3>
    </header>
   	<section class="main" id="bcIndex" style="width:100%;">
   		<div style="text-align:center">${userName}，${restaurantName}欢迎您！</div>
    	<div class="indexImg">
        	<img style="height: 250px;" src="images/indexImg.png"/>
        </div>
        <div style="overflow: hidden;white-space:nowrap; " class="indexCount">
        	<p style="overflow: hidden;white-space:nowrap; " class="countTit">
            	<a href="javascript:;">报餐人数汇总</a>
            </p>
            <div style="overflow: hidden;white-space:nowrap; " class="countMsg">	
            	<font class="nowDateText">今天</font>
                <a style="text-decoration:underline;" href="<%=todayBreakfirstLink %>"><span style="color:#aaa">早:<strong>${countMealInfo.breakfast1 }</strong>人</span></a>
                <a style="text-decoration:underline;" href="<%=todayLunchLink %>"><span id="mealCout2">午:<strong>${countMealInfo.lunch1 }</strong>人</span></a>
                <a style="text-decoration:underline;" href="<%=todayDinnerLink %>"><span id="mealCout3">晚:<strong>${countMealInfo.dinner1 }</strong>人</span></a>
            </div>
            <div style="overflow: hidden;white-space:nowrap; " class="countMsg">	
            	<font class="tomorrowDateText">明天</font>
                <a style="text-decoration:underline;" href="<%=tomorrowBreakfirstLink %>"><span id="mealCout1">早:<strong>${countMealInfo.breakfast2 }</strong>人</span></a>
                <a style="text-decoration:underline;" href="<%=tomorrowLunchLink %>"><span>午:<strong>${countMealInfo.lunch2 }</strong>人</span></a>
                <a style="text-decoration:underline;" href="<%=tomorrowDinnerLink %>"><span>晚:<strong>${countMealInfo.dinner2 }</strong>人</span></a>
            </div>
            <p style="overflow: hidden;white-space:nowrap; " class="countTit" style="margin-top:15px;">
            	<a href="javascript:;">我的当前报餐</a>
            </p>
        	<div style="overflow: hidden;white-space:nowrap; " class="endMealStatus" style="margin:0;">
            	<span class="nowDateText">今天</span><span id="mybreakfast1">早</span><span id="mylunch1">午</span><span id="mydinner1">晚</span>
            </div>
            <div style="overflow: hidden;white-space:nowrap; " class="endMealStatus" style="margin-top:5px;">
            	<span class="tomorrowDateText">明天</span><span id="mybreakfast2">早</span><span id="mylunch2">午</span><span id="mydinner2">晚</span>
            </div>
           <style>
		   	.modify{ height:30px; line-height:30px; margin-top:10px; text-align:center; width:100%; position:relative;} 
           	.modify a{ text-decoration:underline; margin-right:10px;}
			.modify a:nth-of-type(2){ margin-right:0;}
           
           </style>
            <p class="modify">
           		<a href="weixin/startMeal">修改当前报餐</a>
                <a href="weixin/advinceMeal">修改默认报餐</a>
            </p>
        </div>
    	
    </section> 
   	
</div>

<div style="text-align:center; width:100%; height:35px; line-height:35px;">
  	<p style="text-align:center;">
       	<a style="font-size:14px; color:#2eace7;" href="weixin/historyRecord">我的历史记录</a>
    </p>
</div>

   
   
<div id="popupTipWin">
</div>
<div id="resutltTip">
	<div class="content">
	<br><br>
		<div style="color:#2EACE7;font-size:20px"><span >哎呦！管理员还没有为您配置餐厅，请联系管理员！</span></div>
	</div>
</div>



<script>
$(function(){
	function toWeek(num){
		if(num=="0"){
			return "周日";
		}else{
			return "周"+num;
		}
	}
	
	$(".nowDateText").html("今天"+"("+todayDateText+toWeek(todayWeek)+")");
	$(".tomorrowDateText").html("明天"+"("+tomorrowDateText+toWeek(tomorrowWeek)+")");
	
	
	if(nowHour>=lunchTerminalTime&&nowHour<dinnerTerminalTime){
		$("#mealCout2").css("color","#f00");
	}else if(nowHour>=dinnerTerminalTime&&nowHour<breakfastTerminalTime){
		$("#mealCout2").css("color","#aaa");
		$("#mealCout3").css("color","#f00");
	}else if(nowHour>=breakfastTerminalTime){
		$("#mealCout2").css("color","#aaa");
		$("#mealCout3").css("color","#aaa");
		$("#mealCout1").css("color","#f00");
	}
	
	
	if(mybreakfast1!="1"){
		$("#mybreakfast1").css("background-color","#aaa");
	}
	if(mylunch1!="1"){
		$("#mylunch1").css("background-color","#aaa");
	}
	if(mydinner1!="1"){
		$("#mydinner1").css("background-color","#aaa");
	}
	
	if(mybreakfast2!="1"){
		$("#mybreakfast2").css("background-color","#aaa");
	}
	if(mylunch2!="1"){
		$("#mylunch2").css("background-color","#aaa");
	}
	if(mydinner2!="1"){
		$("#mydinner2").css("background-color","#aaa");
	}
	
	
	function showPopTip(){
		$("#popupTipWin").css("display","block");
		$("#resutltTip").css("display","block");
	}
	
	if(!userName||!restaurantName||userName==""||restaurantName==""){
		showPopTip();
	}
});
</script>




</body>
</html>
