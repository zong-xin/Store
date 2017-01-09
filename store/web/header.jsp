<%--
  Created by IntelliJ IDEA.
  User: Rain
  Date: 2016-12-11
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
//加载商品分类列表
	$(function(){
		var ul = $("#clist");
		var url="${pageContext.request.contextPath}/product";
		var param={methodName:"searchAllClist"};
		$.post(url,param,function(data){
			var arr=$(data);
			arr.each(function(index,dom){
				//alert(dom["cname"]);
				var str = "<li><a href='${pageContext.request.contextPath}/product?methodName=searchPlistByCid&pageNumber=1&cid="+dom["cid"]+"'>"+dom["cname"]+"</a></li>";
				ul.append(str);
			});

		},"json");
	});

	//搜索栏搜索商品信息
	function run3(){
				var val2=$("#itemul").val();
				if(val2!=null){
				$("#completeShow").slideDown(500);
			}
			}
			function run2(){//输入框失去焦点时，预览框隐藏
				$("#completeShow").slideUp(500);
			}

		  function run1(nodex){
			  var val=nodex.value;//获取输入值
			  var show=$("#completeShow");
			  if(!val==""){//如果输入框里有值，则显示
			  var url="${pageContext.request.contextPath}/product";
			  var param={methodName:"searchProduct",pname:val};
			  $.post(url,param,function(data){
				  var arr=$(data);
				  //如果返回来的json里面有数据，那么就显示搜索待选栏
				  if(arr.size()>0){
					  show.slideDown(500);
				  }
				  var ul=$("#itemul");
				  //查询完要复位ul
					ul.html("");
				  //将获取到的json数组遍历添加到ul中区
				  arr.each(function(index,dom){
					 // alert();
					  var str="<li class='list-group-item'><a href='${pageContext.request.contextPath}/product?methodName=searchProductByPid&pid="+dom["pid"]+"'>"+dom["pname"]+"</a></li>";
					  ul.append(str);
				  });
			  },"json");
			  }else{//若输入框为空，则隐藏
				  show.slideUp(500);
			  }
		  }
</script>
<!--
         	描述：菜单栏
         -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="${pageContext.request.contextPath}/img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<c:choose>
				<c:when test="${not empty user}">
					<li><h4>用户名：${sessionScope.user.username}</h4></li>
					<li><a href="${pageContext.request.contextPath}/order?methodName=searchOlistByUid&pageNumber=1">我的订单</a></li>
					<li><a href="${pageContext.request.contextPath}/user?methodName=logout">退出登录</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${pageContext.request.contextPath}/login.jsp">登录</a></li>
					<li><a href="${pageContext.request.contextPath}/register.jsp">注册</a></li>
				</c:otherwise>
			</c:choose>
			<li><a href="${pageContext.request.contextPath}/cart.jsp">购物车</a></li>
		</ol>
	</div>
</div>
<!--
         	描述：导航条
         -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">首页</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="clist">

					<!-- <li class="active"><a href="#">手机数码<span class="sr-only">(current)</span></a></li> -->
				</ul>
				<!-- 搜索栏 -->
				<form class="navbar-form navbar-right" role="search" action="${pageContext.request.contextPath}/product" method="post">
				<input type="hidden" name="methodName" value="searchProductByKey">
				<input type="hidden" name="pageNumber" value="1">
			        <div class="form-group">
			          <input type="text" class="form-control" name="pname" placeholder="Search" id="d1" onKeyUp="run1(this)" onblur="run2()" onfocus="run3()" >
			        </div>
			        <!--手机设备隐藏按钮，手机输入法“确定”按钮-->
			        <button type="submit" class="btn btn-default hidden-xs">Submit</button>
			        <!-- 显示查询信息的div -->
			        <div id="completeShow" style="display:none;" >
			        	<ul id='itemul' class='list-group'>
			        		<!-- <li class='list-group-item'><a href='#'>查询结果1</a></li>-->
			        	</ul>
			        </div>
			      </form>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</div>
