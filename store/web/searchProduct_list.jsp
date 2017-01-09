<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>商品列表</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
	<script type="text/javascript">
		$(function(){
			var ul = $("#plist");
			var url="${pageContext.request.contextPath}/product";
			var c_pid="${cookie.c_pid.value}".split(",");
			$(c_pid).each(function(index,dom){
					//alert(dom);
			if(index<6){
			var param={methodName:"searchAllProductByPid","c_pid":dom };
			 $.post(url,param,function(data){
				//alert(data);
				var str = "<li><a href='${pageContext.request.contextPath}/product?methodName=searchProductByPid&pid="+dom+"'><img src='${pageContext.request.contextPath}/"+data+"' width='170' height='170' style='display: inline-block;'></a></li>";
				ul.append(str);
				
			},"text"); 
			}
				});
		});
	</script>
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
				width: 100%;
			}
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
		
			<!--
            	描述：菜单栏
            	
            -->
			<!--
            	描述：导航条
            -->
            <!-- 
            	因为菜单栏和导航条在任何页面都有使用，
            	
            	所以为了方便统一维护，增强代码的扩展性
            	
            	我们将其抽取成为一个单独的JSP页面--header.jsp
            	
            	然后静态导入进来即可
             -->
			<%@include file="header.jsp" %>

		
		<div class="row" style="width:1210px;margin:0 auto;">
			<div class="col-md-12">
				<ol class="breadcrumb">
					<li><a href="${pageContext.request.contextPath}/index.jsp">首页</a></li>
				</ol>
			</div>
			<!-- 当输出第一或者第七条记录的时候，附带行开始标签
				当输出第六或者第十二或者最后一条记录的时候附带行结束标签
			 -->
			<c:if test="${not empty pageBean && not empty pageBean.list}">
				<c:forEach items="${pageBean.list }" var="pro" varStatus="con">
					<c:if test="${con.first||con.count==7}">
						<div class="row">
					</c:if>
					
						<div class="col-md-2">
							<a href="${pageContext.request.contextPath}/product?methodName=searchProductByPid&pid=${pro.pid}">
								<img src="${pageContext.request.contextPath}/${pro.pimage}" width="170" height="170" style="display: inline-block;">
							</a>
							<p><a href="${pageContext.request.contextPath}/product?methodName=searchProductByPid&pid=${pro.pid}" style='color:green'>${pro.pname}</a></p>
							<p><font color="#FF0000">商城价：&yen;${pro.shop_price}</font></p>
						</div>
						
					<c:if test="${con.count==6||con.count==12||con.last}">
					  </div>
					</c:if>
					
				 </c:forEach>
			</c:if> 
			<br>
			<br>
		<!--
       		商品浏览记录:
        -->
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

			<h4 style="width: 50%;float: left;font: 14px/30px;">浏览记录</h4>
			<div style="width: 50%;float: right;text-align: right;"><a href="">more</a></div>
			<div style="clear: both;"></div>

			<div style="overflow: hidden;">

					<!--浏览记录
					
					  -->
				<ul  class="list-inline style="list-style: none;" id="plist">
				
				</ul>

			</div>
		</div>
		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>

	</body>

</html>