<%@ page contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"
	+request.getServerPort()+path+"/";
%>
<html>
<head>
	<base href="<%=basePath%>" />
	<title>登录</title>
	<meta http-equiv="refresh" content="0;url=<%=basePath%>admin">
</head>
<body>
</body>
</html>
