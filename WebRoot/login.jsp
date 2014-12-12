<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>外卖系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<!-- 新 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
	
	<!-- 可选的Bootstrap主题文件（一般不用引入） -->
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
	
	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
	
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>	
  </head>
  
  <body>
    <form role="form" method="post" action="<%=path%>/LoginServlet">
	  <div class="form-group">
	    <label class="col-sm-2 control-label">Username</label>
	    <div class="col-sm-10">
	    	<input type="text" class="form-control" id="username" name="username" placeholder="Text input">	    	
	    </div>
	  </div>
	  <div class="form-group">
	    <label class="col-sm-2 control-label">Password</label>
	    <div class="col-sm-10">
	    <input type="password" class="form-control" id="password" name="password" placeholder="Password">
	  	</div>
	  </div>
	  <div class="btn-group">
	  	  <button type="button" class="btn btn-primary">Reset</button>
		  <button type="submit" class="btn btn-primary">Submit</button>
		</div>	  
	</form>
  </body>
</html>
