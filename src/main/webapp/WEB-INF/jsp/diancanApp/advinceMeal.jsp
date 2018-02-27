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
<title>修改默认报餐</title>
<link rel="stylesheet" href="css/dcApp_base.css"/>
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
    display:none;   ;   
    height:100%;width:100%;background-color: #fff;   
    text-align:center;
}




</style>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">

var mealDefaultInfo={
	week0:"${mealDefaultInfo.week0}",
	week1:"${mealDefaultInfo.week1}",
	week2:"${mealDefaultInfo.week2}",
	week3:"${mealDefaultInfo.week3}",
	week4:"${mealDefaultInfo.week4}",
	week5:"${mealDefaultInfo.week5}",
	week6:"${mealDefaultInfo.week6}"	
};

</script>
</head>

<body>
<div class="layout">
	<header class="header">
    	<div class="indexLink">
        	<a href="javascript:history.back(-1);" class="iconfont icon-fanhui"></a>  
        </div>
         <h3>修改默认报餐</h3>
    </header>
    <!-- <section class="main">
        <div class="indexCount" id="advinceUser">
        	<div class="bcDate">
            	<p>默认就餐情况修改后n+3天后生效</p>
            </div>
        </div>
    </section>  -->
   	<section id="advinceMeal">
    <form>
    	<ul>
        	<li>
            	<div class="hisLeft">
                	<span class="hisDay">星期一</span>
                </div>
            	<div class="hisRight">
                     <input type="checkbox" id="checkbox_a1" class="chk_1" checked=0 /><label for="checkbox_a1">早</label> 
                     <input type="checkbox" id="checkbox_b1" class="chk_1" checked/><label for="checkbox_b1">中</label> 
                     <input type="checkbox" id="checkbox_c1" class="chk_1" checked/><label for="checkbox_c1">晚</label> 
                </div>
            </li>
            <li>
            	<div class="hisLeft">
                	<span class="hisDay">星期二</span>
                </div>
            	<div class="hisRight">
                	 <input type="checkbox" id="checkbox_a2" class="chk_1" checked/><label for="checkbox_a2">早</label> 
                     <input type="checkbox" id="checkbox_b2" class="chk_1" checked/><label for="checkbox_b2">中</label> 
                     <input type="checkbox" id="checkbox_c2" class="chk_1" checked/><label for="checkbox_c2">晚</label>
                </div>
            </li>
            <li>
            	<div class="hisLeft">
                	<span class="hisDay">星期三</span>
                </div>
            	<div class="hisRight">
                	 <input type="checkbox" id="checkbox_a3" class="chk_1" checked/><label for="checkbox_a3">早</label> 
                     <input type="checkbox" id="checkbox_b3" class="chk_1" checked/><label for="checkbox_b3">中</label> 
                     <input type="checkbox" id="checkbox_c3" class="chk_1" checked/><label for="checkbox_c3">晚</label>
                </div>
            </li>
            <li>
            	<div class="hisLeft">
                	<span class="hisDay">星期四</span>
                </div>
            	<div class="hisRight">
                	 <input type="checkbox" id="checkbox_a4" class="chk_1" checked/><label for="checkbox_a4">早</label> 
                     <input type="checkbox" id="checkbox_b4" class="chk_1" checked/><label for="checkbox_b4">中</label> 
                     <input type="checkbox" id="checkbox_c4" class="chk_1" checked/><label for="checkbox_c4">晚</label>
                </div>
            </li>
            <li>
            	<div class="hisLeft">
                	<span class="hisDay">星期五</span>
                </div>
            	<div class="hisRight">
					 <input type="checkbox" id="checkbox_a5" class="chk_1" checked/><label for="checkbox_a5">早</label> 
                     <input type="checkbox" id="checkbox_b5" class="chk_1" checked/><label for="checkbox_b5">中</label> 
                     <input type="checkbox" id="checkbox_c5" class="chk_1" checked/><label for="checkbox_c5">晚</label>
                </div>
            </li>
            <li>
            	<div class="hisLeft">
                	<span class="hisDay">星期六</span>
                </div>
            	<div class="hisRight">
                	 <input type="checkbox" id="checkbox_a6" class="chk_1" checked/><label for="checkbox_a6">早</label> 
                     <input type="checkbox" id="checkbox_b6" class="chk_1" checked/><label for="checkbox_b6">中</label> 
                     <input type="checkbox" id="checkbox_c6" class="chk_1" checked/><label for="checkbox_c6">晚</label>
                </div>
            </li>
            <li>
            	<div class="hisLeft">
                	<span class="hisDay">星期日</span>
                </div>
            	<div class="hisRight">
                	 <input type="checkbox" id="checkbox_a0" class="chk_1" checked/><label for="checkbox_a0">早</label> 
                     <input type="checkbox" id="checkbox_b0" class="chk_1" checked/><label for="checkbox_b0">中</label> 
                     <input type="checkbox" id="checkbox_c0" class="chk_1" checked/><label for="checkbox_c0">晚</label>
                </div>
            </li>
        </ul>
    	<p class="bcStatus">再次点击可以取消报餐</p> 
    	<p class="bcBtn"><button type="button" onclick="submitMealDefault()">提交</button></p>
      </form>
    </section>
    <section>
        <div style="text-align:center;color:#666;padding:15px;">
            	<p>默认就餐情况修改后第三天生效</p>
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
$(function(){
for(var i=0;i<=6;i++){
	var mealKey="week"+i;
	var oneMeal=mealDefaultInfo[mealKey];
	if(oneMeal&&oneMeal!=null&&oneMeal!=""&&oneMeal!="null"){
		var defaultMealStrs=oneMeal.split(",");
		
		var breakfristChkId="checkbox_a"+i;
		var lunchChkId="checkbox_b"+i;
		var dinnerChkId="checkbox_c"+i;
		
		if(defaultMealStrs.length==3){
			$("#"+breakfristChkId).attr("checked",defaultMealStrs[0]=="0"?false:true);
			$("#"+lunchChkId).attr("checked",defaultMealStrs[1]=="0"?false:true);
			$("#"+dinnerChkId).attr("checked",defaultMealStrs[2]=="0"?false:true);
		}
	}
	
	
}

});


function submitMealDefault(){
	var newMealDefaultInfo={};
	for(var i=0;i<=6;i++){
		var breakfristChkId="checkbox_a"+i;
		var lunchChkId="checkbox_b"+i;
		var dinnerChkId="checkbox_c"+i;
		
		var breakfristValue = $("#"+breakfristChkId).is(':checked')?"1":"0";
		var lunchValue = $("#"+lunchChkId).is(':checked')?"1":"0";
		var dinnerValue = $("#"+dinnerChkId).is(':checked')?"1":"0";
		
		newMealDefaultInfo["week"+i]=breakfristValue+","+lunchValue+","+dinnerValue;
	}
	
	if(newMealDefaultInfo==mealDefaultInfo){
		$(".bcStatus").html("您没有做任何修改！");
	}else{
		
		$.ajax({
			url:"weixin/modifyMealDefault",
			data:newMealDefaultInfo,
			success:function(data){
				showPopTip();
			}
			
		});
		
		
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
</script>





</body>
</html>
