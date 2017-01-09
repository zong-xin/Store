<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head></head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员注册</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.validate.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/messages_zh.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
<script type="text/javascript">

	//表格校验
	$(function(){
		//为表单赋予校验规则
		$("#form1").validate({
			rules:{
				username:{
					required:true
				},
				password:{
					required:true,
					rangelength:[6,12]
				},
				repassword:{
					required:true,
					equalTo:"input[name='password']"
				},
				email:{
					required:true,
					email:true
				},
				name:{
					required:true
				},
				sex:{
					required:true
				},
				birthday:{
					required:true,
					data:true
				}
			}
			
		});
	});

	//用户名异步校验
	 function checkUsername(nodex) {
		//当用户名输入框失去叫焦点就对其进行用户名校验
		var val = nodex.value;//获得输入值
		//alert(val);
		//异步请求 post程序入口
		 var url="${pageContext.request.contextPath}/user";
		var param={"methodName":"checkUsername","username":val};
		$.post(url,param,function(data){
			//判断响应体总是否为空，为空说明用户名不存在，即可以使用
			//alert(data);
			if(data=="用户名可用"){
				$("#usernameMsg").html("<span class='label label-success'>用户名可用</span>");
 				$("#sub1").prop("disabled",false);
			}else if(data=="用户名不可用"){
				$("#usernameMsg").html("<span class='label label-danger'>用户名不可用</span>");
 				$("#sub1").prop("disabled",true);
			}
		});
	} 

</script>
<style>
  body{
   margin-top:20px;
   margin:0 auto;
 }
 .carousel-inner .item img{
	 width:100%;
	 height:300px;
 }
 .container .row div{ 
	 /* position:relative;
	 float:left; */
 }
 
font {
    color: #3164af;
    font-size: 18px;
    font-weight: normal;
    padding: 0 10px;
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
<div class="container" style="width:100%;background:url('${pageContext.request.contextPath}/image/regist_bg.jpg');">
<div class="row"> 

	<div class="col-md-2"></div>
	
	


	<div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
		<font>会员注册</font>USER REGISTER
		<form class="form-horizontal" style="margin-top:5px;"  id="form1" action="${pageContext.request.contextPath}/user" method="post">
			 <input type="hidden" name="methodName" value="register"/>
			 <div class="form-group">
			    <label for="username" class="col-sm-2 control-label">用户名</label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名" onblur="checkUsername(this)"/>
			    </div>
			    <div class="col-sm-4" id="usernameMsg">
			    	<!-- <span class="label label-success">用户名可用</span>
					<span class="label label-danger">用户名不可用</span> -->
			    </div>
			  </div>
			   <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="inputPassword3" name="password" placeholder="请输入密码">
			    </div>
			  </div>
			   <div class="form-group">
			    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="confirmpwd" name="repassword" placeholder="请输入确认密码">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-6">
			      <input type="email" class="form-control" id="inputEmail3" name="email" placeholder="Email">
			    </div>
			  </div>
			 <div class="form-group">
			    <label for="usercaption" class="col-sm-2 control-label">姓名</label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" id="usercaption" name="name" placeholder="请输入姓名">
			    </div>
			  </div>
			  <div class="form-group opt">  
			  <label for="inlineRadio1" class="col-sm-2 control-label">性别</label>  
			  <div class="col-sm-6">
			    <label class="radio-inline">
			  <input type="radio" name="sex" id="inlineRadio1" value="man" checked="checked"> 男
			</label>
			<label class="radio-inline">
			  <input type="radio" name="sex" id="inlineRadio2" value="woman"> 女
			</label>
			</div>
			  </div>		
			  <div class="form-group">
			    <label for="date" class="col-sm-2 control-label">出生日期</label>
			    <div class="col-sm-6">
			      <input type="date" class="form-control" name="birthday" >		      
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="date" class="col-sm-2 control-label">验证码</label>
			    <div class="col-sm-3">
			      <input type="text" class="form-control"  >
			      
			    </div>
			    <div class="col-sm-2">
			    <img src="${pageContext.request.contextPath}/image/captcha.jhtml"/>
			    </div>
			    
			  </div>
			 
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <input type="submit"  width="100" value="注册" border="0" id="sub1"
				    style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white;">
			    </div>
			  </div>
			</form>
	</div>
	
	<div class="col-md-2"></div>
  
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
			Copyright &copy; 2005-2016 黑马商城 版权所有
		</div>

</body>
</html>




