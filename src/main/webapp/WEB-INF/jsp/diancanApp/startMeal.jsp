<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"
	+request.getServerPort()+path+"/";
	
	SimpleDateFormat formatter = new SimpleDateFormat ("MM-dd");
	Date date=new Date();
	String todayDateText=formatter.format(date);
	Calendar  calendar = Calendar.getInstance();
	calendar.setTime(date);
	
	int nowHour=calendar.get(Calendar.HOUR_OF_DAY);
	
	int todayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	todayWeek=todayWeek<0?0:todayWeek;
	
	calendar.add(calendar.DATE,1);
	String tomorrowDateText=formatter.format(calendar.getTime());
	int tomorrowWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	tomorrowWeek=tomorrowWeek<0?0:tomorrowWeek;
	
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0"/>
<title>修改当前报餐</title>
<link rel="stylesheet" href="css/dcApp_base.css"/>
<script type="text/javascript" src="js/jquery.min.js"></script>
<style>



.mealType .iconfont:before{
	content:"✔";font-size: 25px;
	color:#000 !important;
}

.cancelMeal{
	background:#ccc;
}

.cancelMeal .iconfont:before{
	content:"✘";font-size: 25px;
	color:#000 !important;
}



.disableChage{
	border:solid 2px #ccc;
	background:#fff;color:#aaa;
}
.disableChage a{
	background:#fff;color:#aaa;
}

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
    display:none;   ;   
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
	
</script>
</head>

<body>
<div class="layout">
	<header class="header">
    	<div class="indexLink">
        	<a href="javascript:history.back(-1);" class="iconfont icon-fanhui"></a>  
        </div>
         <h3>修改当前报餐</h3>
    </header>
   	<section class="main">
        <div class="indexCount">
        	<div class="bcDate" style="margin:15px auto;font-weight:bold;">
            	<p class="nowDateText" ></p>
               	
            </div>
            <p class="mealType breakfastBtn disableChage " id="mybreakfast1" mealEnable="0" mealValue=""><span class="iconfont icon-webiconzaocan"></span><a href="javascript:;">早餐</a></p>
            <p class="mealType lunchBtn" id="mylunch1"><span class="iconfont icon-wucan"></span><a href="javascript:;">午餐</a></p>
            <p class="mealType dinnerBtn" id="mydinner1"><span class="iconfont icon-shuiguo"></span><a href="javascript:;">晚餐</a></p> 
            
        	<div class="bcDate" style="margin:15px auto;font-weight:bold;margin-top:20px;">
            	<p class="tomorrowDateText"></p>
               	
            </div>
            <p class="mealType breakfastBtn" id="mybreakfast2"><span class="iconfont icon-webiconzaocan"></span><a href="javascript:;">早餐</a></p>
            <p class="mealType lunchBtn" id="mylunch2"><span class="iconfont icon-wucan"></span><a href="javascript:;">午餐</a></p>
            <p class="mealType dinnerBtn" id="mydinner2"><span class="iconfont icon-shuiguo"></span><a href="javascript:;">晚餐</a></p> 
            
        	<p class="bcStatus">再次点击可以取消报餐</p> 
            <p class="bcBtn"><button type="button" onclick="submitMeal()">提交</button></p>
              
        </div>
    </section> 
</div>
   

<div id="popupTipWin">
</div>
<div id="resutltTip">
	<div class="content">
	<br><br>
		<div style="color:#2EACE7;font-size:20px"><span >提交成功！</span></div>
		<div style="color:#2EACE7;font-size:15px"><span class="timer"></span><span>秒后自动返回首页</span></div>
		
		<br>
		<div><a href="weixin/diancanIndex">返回首页</a></div>
	</div>
</div>


<script>




function submitMeal(){
	var isChange=false;
	if($("#mybreakfast1").attr("mealValue")!=mybreakfast1){
		isChange=true;
	}else if($("#mylunch1").attr("mealValue")!=mylunch1){
		isChange=true;
	}else if($("#mydinner1").attr("mealValue")!=mydinner1){
		isChange=true;
	}else if($("#mybreakfast2").attr("mealValue")!=mybreakfast2){
		isChange=true;
	}else if($("#mylunch2").attr("mealValue")!=mylunch2){
		isChange=true;
	}else if($("#mydinner2").attr("mealValue")!=mydinner2){
		isChange=true;
	}
		
	if(isChange){
		$.ajax({
			url:"weixin/modifyMeal",
			data:{
				mybreakfast1:$("#mybreakfast1").attr("mealValue"),
				mylunch1:$("#mylunch1").attr("mealValue"),
				mydinner1:$("#mydinner1").attr("mealValue"),
				mybreakfast2:$("#mybreakfast2").attr("mealValue"),
				mylunch2:$("#mylunch2").attr("mealValue"),
				mydinner2:$("#mydinner2").attr("mealValue")
			},
			success:function(data){
				showPopTip();
			}
			
		});
		
	}else{
		$(".bcStatus").html("您没有做任何修改！");
	}
	
	
}

function showPopTip(){
	$("#popupTipWin").css("display","block");
	$("#resutltTip").css("display","block");
	var num=5;
	$("#resutltTip .timer").html(num);
	var myTimer=window.setInterval(function(){
		$("#resutltTip .timer").html(num);
		num--;
		if(num<=0){
			window.clearInterval(myTimer);
			window.location.href="weixin/diancanIndex";
		}
	},1000);
}




$(function(){
		$(".mealType").click(function(p){
			var me=this;
			if($(me).attr("mealEnable")!="0"){
				var mealValue=$(me).attr("mealValue");
				if(mealValue&&mealValue=="0"){
					$(me).attr("mealValue","1");
					$(me).removeClass("cancelMeal")
				}else{
					$(me).attr("mealValue","0");
					$(me).addClass("cancelMeal")
				}
			}
		});
	

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
		$("#mylunch1").addClass("disableChage");
		$("#mylunch1").attr("mealEnable","0");
	}else if(nowHour>=dinnerTerminalTime&&nowHour<breakfastTerminalTime){
		$("#mylunch1").addClass("disableChage");
		$("#mylunch1").attr("mealEnable","0");
		$("#mydinner1").addClass("disableChage");
		$("#mydinner1").attr("mealEnable","0");
	}else if(nowHour>=breakfastTerminalTime){
		$("#mylunch1").addClass("disableChage");
		$("#mylunch1").attr("mealEnable","0");
		$("#mydinner1").addClass("disableChage");
		$("#mydinner1").attr("mealEnable","0");
		$("#mybreakfast2").addClass("disableChage");
		$("#mybreakfast2").attr("mealEnable","0");
	}
	
	$("#mybreakfast1").addClass(mybreakfast1=="0"?"cancelMeal":"");
	$("#mybreakfast1").attr("mealValue",mybreakfast1);
	
	$("#mylunch1").addClass(mylunch1=="0"?"cancelMeal":"");
	$("#mylunch1").attr("mealValue",mylunch1);
	
	$("#mydinner1").addClass(mydinner1=="0"?"cancelMeal":"");
	$("#mydinner1").attr("mealValue",mydinner1);
	
	$("#mybreakfast2").addClass(mybreakfast2=="0"?"cancelMeal":"");
	$("#mybreakfast2").attr("mealValue",mybreakfast2);
	
	$("#mylunch2").addClass(mylunch2=="0"?"cancelMeal":"");
	$("#mylunch2").attr("mealValue",mylunch2);
	
	$("#mydinner2").addClass(mydinner2=="0"?"cancelMeal":"");
	$("#mydinner2").attr("mealValue",mydinner2);
	
	
});
</script>



</body>
</html>
