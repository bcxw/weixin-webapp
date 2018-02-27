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
<body style="margin:0px;">

<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
    
    <div data-options="region:'west',title:'部门',split:true,border:false" style="width:200px;">
    
    	<ul id="departmentTree" class="easyui-tree">
		    
		</ul>
    
    </div>
    <div data-options="region:'center'," style="">
    	<table id="dg"  class="easyui-datagrid" border=false singleSelect="true" pagination="true" rownumbers="true" url="<%=basePath%>employeeManage/getEmployeeByDepart" pageSize=20 fit="true" toolbar="#tb">
		    <thead>
		    	<tr>
		    		<th field="cb" checkbox="true" ></th>
		    		<th field="wxName" width="100" >姓名</th>
		    		<th field="wxUserId" width="200" >用户名</th>
		    		
		    	</tr>
		    </thead>
		</table>
		<div id="tb">
			<div>
				<input id="searchText" class="easyui-textbox" data-options="prompt:'请输入姓名'" style="width:150px"> 
				<a id="searchButton"  class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
				<a id="fireButton"  class="easyui-linkbutton" iconCls="icon-remove" plain="true">标记离职</a>
			</div>
			
		</div>
    </div>
</div>
<script>
$(function(){
	$('#departmentTree').tree({
		url:"<%=basePath%>employeeManage/getTreeDepartments",
		onClick: function(node){
			searchEmployee(node.id,"");
		}
	});
	
	$('#searchButton').linkbutton({
		onClick:function(){
			var node = $('#departmentTree').tree('getSelected');
			if(node){
				searchEmployee(node.id,$("#searchText").textbox('getValue'));
			}else{
				$.messager.alert('提示',"请选择部门！");
			}
			
		}
	});
	
	$('#fireButton').linkbutton({
		onClick:function(){
			var selectRow = $('#dg').datagrid('getSelected');
			
			if(selectRow){
				$.messager.confirm('确认','离职人员将终止报餐,请谨慎操作,您确认要继续吗？',function(r){    
				    if (r){    
				    	$.ajax({
				    		type:"POST",
				    		url:"<%=basePath%>employeeManage/fireEmployee",
				    		data:{
				    			wxUserId:selectRow.wxUserId
				    		},
				    		success:function(data, textStatus, jqXHR){
				    			if(data.statusCode==0){
				    				$.messager.alert('提示',"操作成功！"); 
				    				$('#dg').datagrid('reload');  
				    			}else{
				    				$.messager.alert('提示',data.message); 
				    			}
				    		},
				    		error:function(request,errMsg,errObj){
				    			$.messager.alert('提示',errMsg); 
				    			$.messager.progress('close');
				    		}
				    	});
				    }    
				}); 
			}else{
				$.messager.alert('提示',"请选择要操作的人员！");
			}
			
			
		}
	});
	
	


});


function searchEmployee(departmentId,searchName){
	$('#dg').datagrid('load',{
		departmentId:departmentId,
		searchName:searchName
	});
}
</script>
</body>
</html>