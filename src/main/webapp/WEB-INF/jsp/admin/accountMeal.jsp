<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"
	+request.getServerPort()+path+"/";
%>
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>店铺配置管理</title>
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
</head>
<body style="margin: 1px;" class="easyui-layout">

<table id="dg"  class="easyui-datagrid" 
    pagination="true" rownumbers="true" url="<%=basePath%>mealManage/getMealClockAccount" pageSize=30 fit="true" toolbar="#tb">
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" ></th>
    		<th field="userName" width="100" >姓名</th>
    		<th field="weixinid" width="100" >用户名</th>
    		<th field="mealDate" width="100" >日期</th>
    		<th field="breakfast" width="150" data-options="formatter: function(value,row,index){
				return row.breakfast!=row.breakfastClock?'<span style=\'color:red\'>'+value+'-'+row.breakfastClock+'</span>':value+'-'+row.breakfastClock;
			}">早餐报餐-打卡</th>
    		<th field="lunch" width="150" data-options="formatter: function(value,row,index){
				return row.lunch!=row.lunchClock?'<span style=\'color:red\'>'+value+'-'+row.lunchClock+'</span>':value+'-'+row.lunchClock;
			}">中餐报餐-打卡</th>
    		<th field="dinner" width="150" data-options="formatter: function(value,row,index){
				return row.dinner!=row.dinnerClock?'<span style=\'color:red\'>'+value+'-'+row.dinnerClock+'</span>':value+'-'+row.dinnerClock;
			}">晚餐报餐-打卡</th>
    		
    		<th field="clockDetail" width="200" >打卡记录</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<form id="searchForm" class="mySearchForm" style="margin:1px;font-size: 13px;">
		<a id="importButton"  class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入打卡记录</a>
		<a id="exportButton"  class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出乐捐汇总</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<label>姓名：</label><input id="searchNameText" class="easyui-textbox" data-options="prompt:'请输入姓名'" style="width:100px">
		&nbsp;&nbsp;
		<input id="searchYearText" style="width:100px"> <label>年</label>
		<input id="searchMonthText" style="width:100px"> <label>月</label>
		&nbsp;&nbsp;
		<a onclick="searchDg()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<a onclick="$('#searchForm').form('reset')" class="easyui-linkbutton" plain="true">重置</a>
	</form>
	
</div>


<!-- 上传打卡记录 -->
<div id="uploadWin" styple="disply:none;">
	<form enctype="multipart/form-data" id="uploadForm" method="post" style="padding:50px 50px 0px 50px;">   
	    
	    <div style="margin-top:15px">
	        <label for="name">选择文件:</label>
	        <input id="shop_dg_form_file" name='file' class="easyui-filebox" style="width:300px" data-options="buttonText:'选择文件'">
	        
	    </div>
	    <div id="fileTip" style="font-size:12px;color:red;padding-left:55px;">*请选择.xls格式的excel打卡机员工刷卡记录表</div>
	    
	    <div style="text-align:center;margin-top:35px">
    		<input class="easyui-linkbutton" type="submit"  value="提交" style="width:80px">
    		<input class="easyui-linkbutton" type="button"  value="取消" style="width:80px" onclick="closeImportWindow()">
		</div>
	</form>
</div>
<!-- 人员匹配 -->
<div id="employeeWin" styple="disply:none;" data-options="title:'第二步：人员匹配',width:800,height:600,modal:true,footer:'#employeeWinfooter'">
	<table id="employeeDg" border=false class="easyui-datagrid" style="width:100%;height:100%" rownumbers="true"  singleSelect="true" >
        <thead>
            <tr>
            	<th field="clock_name" width="150" data-options="formatter: function(value,row,index){
				return row.clock_name+'('+row.clock_id+')';
				}">打卡记录员工</th>
                <th field="weixin_name" width="300" data-options="formatter: function(value,row,index){
                if(row.weixin_name&&row.weixin_id){
                	return row.weixin_name+'('+row.weixin_id+')';
                }else{
                	return '';
                }
				
				}">点餐系统员工</th>
                <th field="opt" width="150" data-options="formatter: function(value,row,index){
				return '<a href=\'javascript:showEmployeeSelectWin('+index+');\' style=\'text-decoration:none;color:blue\'>修改</a>';
				}">操作</th>
            </tr>
        </thead>
    </table>
</div>
<div id="employeeWinfooter" style="padding:5px;text-align:center">
	<a id="matchEmpSubmitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'">提交</a>
</div>

<!-- 人员选择 -->
<div id="employeeSelectWin" styple="disply:none;overflow:hidden;" data-options="title:'选择点餐系统人员',width:800,height:600,modal:true,footer:'#employeeSelectWin_footer'">
	<table id="employeeSelectDg" border=false class="easyui-datagrid" style="width:100%;height:100%" rownumbers="true" toolbar='#employeeSelectWin_tb'  singleSelect="true" >
        <thead>
            <tr>
            	<th field="cb" checkbox="true" align="center"></th>
            	<th field="wxUserId" width="150" >微信ID</th>
                <th field="wxName" width="150" >姓名</th>
                
            </tr>
        </thead>
    </table>
    
</div>
<div id="employeeSelectWin_tb">
	<div>
		<label>姓名:</label>
		<input id="empSearchName" class="easyui-textbox"  style="width:200px">
		<a id="empSearchButton"  class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	</div>

</div>
<div id="employeeSelectWin_footer" style="padding:5px;text-align:center">
	<a id="selectedEmpBtn"  class="easyui-linkbutton" data-options="iconCls:'icon-save'">确定</a>
</div>

<!-- 导出窗口 -->
<div id="exportWin" styple="disply:none;" data-options="title:'导出报餐乐捐汇总',width:500,height:250,modal:true">
	<form enctype="multipart/form-data" id="uploadForm" method="post" style="padding:50px 50px 0px 50px;">   
	    
	    <div style="margin-top:15px;padding-left:50px;">
	        <label for="name">选择月份:</label>
	        <input style="width:100px" id="exportYear" name="dept">
	        <input style="width:100px" id="exportMonth" name="dept">
	        
	    </div>
	    
	    <div style="text-align:center;margin-top:65px">
    		<input id="exportSubmitButton" class="easyui-linkbutton" type="button"  value="确定" style="width:80px">
    		<input class="easyui-linkbutton" type="button"  value="取消" style="width:80px" onclick="closeExportWindow()">
		</div>
	</form>
</div>


<script>
$(function(){
	$('#importButton').linkbutton({
		onClick: function(){
			showImportWin();
		}
	});
	$('#empSearchButton').linkbutton({
		onClick: function(){
			serachSelectEmployee();
		}
	});
	$('#selectedEmpBtn').linkbutton({
		onClick: function(){
			selectedSubmit();
		}
	});
	$('#matchEmpSubmitBtn').linkbutton({
		onClick: function(){
			matchEmpSubmit();
		}
	});
	
	$('#exportButton').linkbutton({
		onClick: function(){
			showExportWin();
		}
	});
	
	$('#exportSubmitButton').linkbutton({
		onClick: function(){
			submitExportAccount();
		}
	});

	var yearData=new Array();
    var tempDate=new Date;
    var tempYear=tempDate.getFullYear();
    for(var i=0;i<=5;i++){
        yearData.push({id:tempYear,text:tempYear});
        tempYear--;
	}

    $('#searchYearText').combobox({
        valueField:'id',
        textField:'text',
        data:yearData
    });

    $('#searchMonthText').combobox({
        valueField:'id',
        textField:'text',
        data:[{id:'01',text:'01'},{id:'02',text:'02'},{id:'03',text:'03'},{id:'04',text:'04'},{id:'05',text:'05'},{id:'06',text:'06'},{id:'07',text:'07'},{id:'08',text:'08'},{id:'09',text:'09'},{id:'10',text:'10'},{id:'11',text:'11'},{id:'12',text:'12'}]
    });

	
});

function searchDg(){

    var yearMonth="";
    var year=$('#searchYearText').combobox('getValue');
    var month=$('#searchMonthText').combobox('getValue');
    console.log(year);
    if(year&&month){
        yearMonth=$('#searchYearText').combobox('getValue')+"-"+$('#searchMonthText').combobox('getValue');
	}

    $('#dg').datagrid('load',{
        month:yearMonth,
        name:$('#searchNameText').textbox('getValue')
    });

}

/*******员工选择窗口************/
var selectEmpData;
function showEmployeeSelectWin(rowIndex){
	
	$('#employeeSelectWin').window();
	$('#employeeSelectWin').css("overflow","hidden");
	$('#employeeSelectWin').attr("matchRowIndex",rowIndex);
	
}
function serachSelectEmployee(){
	var serchName=$("#empSearchName").val();
	if(serchName){
		var filterData=new Array();
		for(var i=0;i<selectEmpData.length;i++){
			var oneUser=selectEmpData[i];
			if(oneUser.wxName.indexOf(serchName)>=0){
				filterData.push(oneUser);
			}
		}
		$('#employeeSelectDg').datagrid({
			data:filterData
		});
	}else{
		$('#employeeSelectDg').datagrid({
			data:selectEmpData
		});
	}
}
function selectedSubmit(){
	var selectRow=$('#employeeSelectDg').datagrid("getSelected");
	if(selectRow){
		var matchRowIndex=$('#employeeSelectWin').attr("matchRowIndex");
		
		$('#employeeDg').datagrid('updateRow',{
			index:matchRowIndex,
			row: {
				weixin_id: selectRow.wxUserId,
				weixin_name: selectRow.wxName,
				match_status:1
			}
		});
		$('#employeeSelectWin').window('close');
	}else{
		$.messager.alert('提示',"请选择一条人员数据！"); 
	}
}

/*******员工匹配窗口************/

function showEmployeeWin(clockMonth,employeeList,weixinUserList,matchUserList){
	$('#employeeWin').window();
	$('#employeeWin').attr("clockMonth",clockMonth);
	//把匹配没配的都放到表格显示
	var gridData=new Array();
	for(var i=0;i<employeeList.length;i++){
		var oneEmp=employeeList[i];
		
		//0表示本来有匹配不需要修改
		var match_status=0;
		var weixin_id="";
		var weixin_name="";
		var clock_id=oneEmp.code;
		var clock_name=oneEmp.name;
		
		//本来有匹配就用现成的匹配
		for(var m=0;m<matchUserList.length;m++){
			var oneMatchUser=matchUserList[m];
			if(oneMatchUser.clock_id==clock_id&&oneMatchUser.clock_name==clock_name){
				weixin_id=oneMatchUser.wx_id;
				weixin_name=oneMatchUser.wx_name;
				match_status=0;
				break;
			}
		}
		
		
		//没有匹配就自动找匹配
		if(weixin_id==""||weixin_name==""){
			for(var n=0;n<weixinUserList.length;n++){
				var oneUser=weixinUserList[n];
				if(oneUser.wxName==clock_name){
					weixin_id=oneUser.wxUserId;
					weixin_name=oneUser.wxName;
					match_status=1;
					break;
				}
			}
		}
		
		gridData.push({
			weixin_id:weixin_id,
			weixin_name:weixin_name,
			clock_id:clock_id,
			clock_name:clock_name,
			match_status:match_status
		});
		
	}
	
	$('#employeeDg').datagrid({
		data: gridData
	});
	
}
function matchEmpSubmit(){
	var matchEmpData=$('#employeeDg').datagrid('getRows');
	
	for(var i=0;i<matchEmpData.length;i++){
		var oneRow=matchEmpData[i];
		var weixin_id=oneRow.weixin_id;
		var weixin_name=oneRow.weixin_name;
		if(weixin_id&&weixin_name){
			
		}else{
			var rowNum=i+1;
			$.messager.alert('提示',"第"+rowNum+"行"+oneRow.clock_name+"未匹配"); 
			return false;
		}
	}
	
	//将新的匹配保存到数据库,并生成结算显示到前端
	var progressWin= $.messager.progress({
		title:'请稍后',
		msg:'正在提交数据...'
	});
	
	$.ajax({
		type:"POST",
		url:"<%=basePath%>mealManage/submitMatchEmpData",
		data:{
			clockMonth:$('#employeeWin').attr("clockMonth"),
			matchEmpData:JSON.stringify(matchEmpData)
			
		},
		success:function(data, textStatus, jqXHR){
			
			if(data.statusCode==0){
				if(data.data&&data.data.length>0){
					var errData=data.data;
					var errNameStrs="";
					for(var i=0;i<errData.length;i++){
						var oneErrData=errData[i];
						errNameStrs+=oneErrData.userName+oneErrData.weixinid+"("+oneErrData.mealDate+");";
						
					}
					$.messager.alert('提示',"导入成功，出错的有："+errNameStrs+"。请检查人员匹配或是否录入打卡机！"); 
				}else{
					$.messager.alert('提示',"导入打卡记录并生成结算成功，您可以查看或导出报餐结算结果了！"); 
				}
				$('#dg').datagrid('reload');  
			}else{
				$.messager.alert('提示',data.message); 
			}
			$('#employeeWin').window('close');
			$.messager.progress('close');
		},
		error:function(request,errMsg,errObj){
			$.messager.alert('提示',errMsg); 
			$.messager.progress('close');
		}
	});
	
	
}


/****导入窗口**/
function showImportWin(){
	var progressWin;
	$('#uploadForm').form({    
	    url:"<%=basePath%>mealManage/importMealRecord",
	    onSubmit: function(){    
	    	
	    	progressWin = $.messager.progress({
	    		title:'请稍后',
	    		msg:'正在提交数据...'
	    	});
	    	
	        
	    }, 
	    success:function(data){
	    	data=$.parseJSON(data);
	    	console.log(data);
	    	if(data.statusCode==0){
    			closeImportWindow();
    			showEmployeeWin(data.data.clockMonth,data.data.employeeList,data.data.weixinUserList,data.data.matchUserList);
    			selectEmpData=data.data.weixinUserList;
    			selectEmpData.unshift({wxName:"其他离职人员",wxUserId:"-1"});
    			$('#employeeSelectDg').datagrid({
    				data:selectEmpData
    			});
    			
    		}else{
    			$.messager.alert('提示',data.message); 
    		}
	    	
	    	if(progressWin){
    			$.messager.progress('close');
    		}
	    	
	    }
	});
	$('#uploadWin').window({ 
	    width:500,    
	    title:"第一步：导入打卡记录",
	    height:250,    
	    modal:true   
	});
	$('#uploadForm').form('reset');
}

function closeImportWindow(){
	$('#uploadWin').window('close');
}


/************导出记录**********/
function showExportWin(){
	var date=new Date;
	var currentYear=date.getFullYear();
	var yearData=new Array();
	for(var i=0;i<10;i++){
		currentYear=currentYear-i;
		yearData.push({
			label: currentYear,
			value: currentYear
		});
	}
	
	var monthData=new Array();
	for(var i=1;i<=12;i++){
		var month=i<10?"0"+i:i;
		monthData.push({
			label: month,
			value: month
		});
	}
	
	$('#exportYear').combobox({
		valueField: 'label',
		textField: 'value',
		data:yearData
	});
	
	$('#exportMonth').combobox({
		valueField: 'label',
		textField: 'value',
		data: monthData
	});
	
	
	$('#exportWin').window();
}
function closeExportWindow(){
	$('#exportWin').window('close');
}

function submitExportAccount(){
	var year=$('#exportYear').val();
	var month=$('#exportMonth').val();
	if(year&&month){
		window.location.href="<%=basePath%>mealManage/exportMealAccount?month="+year+"-"+month;
	}else{
		$.messager.alert('提示',"请选择年份和月份！"); 
	}
}
</script>

</body>
</html>