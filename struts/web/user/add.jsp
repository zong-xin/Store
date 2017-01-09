<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="${pageContext.request.contextPath}/css/Style.css" type="text/css" rel="stylesheet">
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<script language="javascript" src="${pageContext.request.contextPath}/js/check.js"></script>
		<!-- 日期插件，使用jquery -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.4.2.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery/jquery.datepick.css" type="text/css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.datepick.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.datepick-zh-CN.js"></script>
	</HEAD>
	<script type="text/javascript">
		$(document).ready(function(){
			//使用class属性处理  'yy-mm-dd' 设置格式"yyyy/mm/dd"
			$('#birthday').datepick({dateFormat: 'yy-mm-dd'}); 
			$('#userAction_save_do_logonName').blur(function () {
				var logonName=$(this).val();
				var url="${pageContext.request.contextPath}/user_checkLogonName";
				$.post(url,{"logonName":logonName},function (data) {
					//alert(data.ifExist);
					if(data.ifExist){
						$("#check").html("<font color='green'>用户名可以用！</font>")
						$("#userAction_save_do_submit").removeAttr("disabled");
					}else {
						$("#check").html("<font color='red'>用户名不可用！</font>")
						$("#userAction_save_do_submit").attr({"disabled":"disabled"});
					}
				});
			});
		});
	</script>
	<body>
		<%--<form id="userAction_save_do" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post" enctype="multipart/form-data">--%>
			<s:form id="userAction_save_do" namespace="/" action="user_add" method="post"  name="form1" enctype="multipart/form-data">
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>添加用户</STRONG>&nbsp;&nbsp;&nbsp;<font color="red"><s:actionerror/><s:fielderror/> </font>
						</strong>
					</td>
				</tr>

				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						登录名：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<%--<input type="text" name="logonName" value="" id="userAction_save_do_logonName" class="bg"/>--%>
						<s:textfield  name="logonName" value="" id="userAction_save_do_logonName" cssClass="bg"/>
						<span id="check" >  </span>
					</td>
				</tr>
				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">
						 密码：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<%--<input type="password" name="logonPwd" value="" id="logonPwd"/>--%>
						<s:password  name="logonPwd"  id="logonPwd"/>
					</td>
					<td align="center" bgColor="#f5fafe" class="ta_01">
						用户姓名：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<%--<input type="text" name="userName" value="" id="userAction_save_do_userName" class="bg"/>--%>
						<s:textfield name="userName"  id="userAction_save_do_userName" cssClass="bg"/>
					</td>
				</tr>
				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">
						性别：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<%--<input type="radio" name="sex" id="sex男" value="男"/><label for="sex男">男</label>
						<input type="radio" name="sex" id="sex女" value="女"/><label for="sex女">女</label>--%>
						<s:radio list="{'男','女'}" name="sex "/>
					</td>
					<td align="center" bgColor="#f5fafe" class="ta_01">
						学历：
					</td>
					<td class="ta_01" bgColor="#ffffff">

						<s:select list="{'研究生','本科','专科','高中'}"  name="education" id="education" headerKey="" headerValue="--选择学历--" />

					</td>
				</tr>
				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">
						出生日期：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:textfield name="birthday" size="20" value="" readonly="readonly" id="birthday"/>
					</td>
					<td align="center" bgColor="#f5fafe" class="ta_01">
						电话：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:textfield  name="telephone" value="" id="telephone"/>
					</td>
				</tr>
				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">
						兴趣爱好：
					</td>
					<td>
						<s:checkboxlist list="{'看电影','旅游','健身','购物','睡觉'}" name="interest" />
						<s:hidden id="__multiselect_userAction_save_do_interest" name="__multiselect_interest" value="" />
					</td>
				</tr>
				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">
						简历资料：
					</td>
					<td class="ta_01" bgColor="#ffffff" colSpan="3">
						<%--<input type="file" name="upload" size="30" value="" id="userAction_save_do_upload"/>--%>
						<s:file name="upload" size="30" id="userAction_save_do_upload"/>
					</td>
				</tr>
				<TR>
					<TD class="ta_01" align="center" bgColor="#f5fafe">
						备注：
					</TD>
					<TD class="ta_01" bgColor="#ffffff" colSpan="3">
						<s:textarea name="remark" cols="30" rows="3" id="userAction_save_do_remark" cssStyle="WIDTH: 96%"/>
					</TD>
				</TR>
				<TR>
					<td align="center" colSpan="4" class="sep1">
						<img src="${pageContext.request.contextPath}/images/shim.gif">
					</td>
				</TR>


				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<%--<button type="submit" id="userAction_save_do_submit" name="submit" value="&#30830;&#23450;" class="button_ok">
							&#30830;&#23450;
						</button>--%>
						<s:submit id="userAction_save_do_submit" value="提交" cssClass="button_ok"  />

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<%--<button type="reset" value="&#37325;&#32622;" name="reset" class="button_cancel">&#37325;&#32622;</button>--%>
						<s:reset value="重置" name="reset" cssClass="button_cancel" />

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<INPUT class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
						<span id="Label1"></span>
					</td>
				</tr>
			</table>
				</s:form>
	</body>
</HTML>