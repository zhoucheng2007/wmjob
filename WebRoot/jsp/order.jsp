<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.wm.entity.Order" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<Order> orderList=(List<Order>)request.getAttribute("orderList");
%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <base href="<%=basePath%>">
    
    <title>订单首页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!--
	<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
	-->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.spinner.css" />
	<!-- 新 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	
	<!-- 可选的Bootstrap主题文件（一般不用引入） -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
	
	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
	
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.spinner.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>	
	<style type="text/css">
		body{margin:20px;}
		.spinnerExample{margin:10px 0;}
		table.imagetable {
			font-family: verdana,arial,sans-serif;
			font-size:11px;
			color:#333333;
			border-width: 1px;
			border-color: #999999;
			border-collapse: collapse;
		}
		table.imagetable th {
			background:#b5cfd2 url('<%=request.getContextPath()%>/images/cell-blue.jpg');
			border-width: 1px;
			padding: 8px;
			border-style: solid;
			border-color: #999999;
		}
		table.imagetable td {
			background:#dcddc0 url('<%=request.getContextPath()%>/images/cell-grey.jpg');
			border-width: 1px;
			padding: 8px;
			border-style: solid;
			border-color: #999999;
		}
	</style>
	<script type="text/javascript">
	var orderSize=<%=orderList.size()%>;
	$(document).ready(function(){ 							
		$("#caculate").click(function(){
			for(var i=0;i<orderSize;i++){
				var num=$("#ordernum"+i).val();
				var price=$("#price"+i).val();
				var money=num*price;
				$("#ordernum"+i).attr("readonly","readonly");
				$("#money"+i).val(money);
			}
		});
		
		
		$("#submitOrder").click(function(){
			var sum=0;
			var jsonobject={}
			var order=[];		
			for(var i=0;i<orderSize;i++){
				var order1={};
				var id=$("#id"+i).val();
				var num=$("#ordernum"+i).val();
				var price=$("#price"+i).val();
				var goodname=$("#goodname"+i).val();
				var money=num*price;
				$("#money"+i).val(money);
				sum=sum+money;
				order1.id=id;
				order1.num=num;
				order1.price=price;
				order1.money=money;
				order1.goodname=goodname;
				order.push(order1);
			}
			$("#sum").val(sum);
			jsonobject.order=JSON.stringify(order);
			jsonobject.sum=sum;
			//增加操作
			jsonobject.cmd="update";
			console.log(jsonobject);
			$.post(
					"<%=path%>/GoodsServlet",	
					jsonobject,
					function(data){
						console.log("开始"+data);
						alert(data.message);
						$("#goodform").submit();
					},
					"json"					
			);
		});
		
	}) 
	
	function modify(rownum){
		$("#ordernum"+rownum).removeAttr("readonly");
	}
	</script> 
  </head>
  
  <body>
  
  
    <table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>
    	商品名
    	</th>
         <th>
    	价格
    	</th>
    	<th>
    	数量
    	</th>
    	<th>
    	金额
    	</th>    
    	<th>
    	操作
    	</th>  
    </tr>

    </thead>
    	<tbody>
    	<%
    	for(int i=0;i<orderList.size();i++){ 
    		Order order=orderList.get(i); 
    		String goodname=order.getGoodname();
    		String price=order.getPrice();
    		String orderid=order.getId();
    		String num=order.getNum();
    		String money=order.getMoney();
    	%>
	    <tr>
	    <td><input type="hidden" id="id<%=i%>" value=<%=orderid%>><input type="hidden" id="goodname<%=i%>" value=<%=goodname%>><%=goodname%></td>
	    <td><input type="text" class="form-control" id="price<%=i%>" value=<%=price%> readonly></td>
	    <td>
	      <input type="text" class="form-control" id="ordernum<%=i%>" value=<%=num%> readonly>
        </td>
        <td>
	        	 <input type="text" class="form-control" id="money<%=i%>" value=<%=money%> readonly/>	        	 

        </td>
         <td>
	        	 <button type="button" class="btn btn-primary" id="modify<%=i%>" onclick="modify(<%=i%>)">modify</button>	        	 

        </td>
	    </tr>  
	    <%
	    	}   	
	    %>
    	</tbody>
    	
    </table>  
        	<div class="btn-group">
	  	  <button type="button" class="btn btn-primary" id="caculate">caculate</button>
		  <button type="submit" class="btn btn-primary" id="submitOrder" >submit</button>
		</div>
			<form id="goodform" role="form" method="post" action="<%=path%>/GoodsServlet?cmd=query">
	</form>
  </body>
</html>
