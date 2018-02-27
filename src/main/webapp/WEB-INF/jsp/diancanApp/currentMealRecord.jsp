<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"
	+request.getServerPort()+path+"/";
	
	List<Map<String,Object>> mealInfoList=(List<Map<String,Object>>)request.getAttribute("mealInfo");
	
	Map<String,Map<String,Integer>> mealInfoCount=new HashMap<String,Map<String,Integer>>();
	
	for(int i=0;i<mealInfoList.size();i++){
		Map<String,Object> oneMeal=mealInfoList.get(i);
		String department=oneMeal.get("department")+"";
		int breakfast=Integer.parseInt(oneMeal.get("breakfast")+"");
		int lunch=Integer.parseInt(oneMeal.get("lunch")+"");
		int dinner=Integer.parseInt(oneMeal.get("dinner")+"");
		
		
		
		Map<String,Integer> oneMealInfoCount =null;
		if(mealInfoCount.containsKey(department)){
			oneMealInfoCount=mealInfoCount.get(department);
		}else{
			oneMealInfoCount=new HashMap<String,Integer>();
		}
		
		if(oneMealInfoCount.containsKey("breakfast")){
			oneMealInfoCount.put("breakfast", breakfast==1?oneMealInfoCount.get("breakfast")+1:oneMealInfoCount.get("breakfast"));
		}else{
			oneMealInfoCount.put("breakfast", breakfast==1?1:0);
		}
		
		if(oneMealInfoCount.containsKey("lunch")){
			oneMealInfoCount.put("lunch", lunch==1?oneMealInfoCount.get("lunch")+1:oneMealInfoCount.get("lunch"));
		}else{
			oneMealInfoCount.put("lunch", lunch==1?1:0);
		}
		
		if(oneMealInfoCount.containsKey("dinner")){
			oneMealInfoCount.put("dinner", dinner==1?oneMealInfoCount.get("dinner")+1:oneMealInfoCount.get("dinner"));
		}else{
			oneMealInfoCount.put("dinner", dinner==1?1:0);
		}
		
		mealInfoCount.put(department, oneMealInfoCount);
	}
	
	
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0"/>
<title>报餐记录</title>
<link rel="stylesheet" href="css/dcApp_base.css"/>

</head>

<body>
<div class="layout">
	<header class="header">
    	<div class="indexLink">
        	<a href="javascript:history.back(-1);" class="iconfont icon-fanhui"></a>  
        </div>
         <h3>报餐记录(${mealDate})</h3>
    </header>
   	<section id="historyList">
   		
   		<div id="recordCountDiv" style="margin:10px;padding:10px;border:1px dashed #aaa;">
   			<ul>
	   			<%
		   			for (Map.Entry<String,Map<String,Integer>> entry : mealInfoCount.entrySet()) { 
		   				String department=entry.getKey();
		   				Map<String,Integer> oneCount=entry.getValue();
		   				
		   				int breakfast =oneCount.get("breakfast");
		   				int lunch =oneCount.get("lunch");
		   				int dinner =oneCount.get("dinner");
		   			
	   			%>
						<li>
			            	<div style="float:left;width:50%">
			                	<span><%=department %></span>
			                </div>
			            	<div style="float:right;width:50%;">
			                	<div style="width:30%;float:left">早(<%=breakfast %>)</div> 
			                	<div style="width:30%;float:left">中(<%=lunch %>)</div> 
			                    <div style="width:30%;float:left">晚(<%=dinner %>)</div> 
			                </div>
			            </li>
				        
	   			<%
		   			}
	   			%>
   			</ul>
   		</div>
   		
    	<ul>
	        <c:forEach var="item" items="${mealInfo}">
				<li>
	            	<div class="hisLeft" style="overflow:hidden">
	                	<span class="hisDay">${item.department}:${item.user_name}(${item.user_id})</span>
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
