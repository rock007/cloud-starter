<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎登陆系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link href="resources/plugins/bootstrap/bootstrap.css" rel="stylesheet">
<link href="resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">

 <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="resources/plugins/html5shiv/html5shiv.js"></script>
	<script src="resources/plugins/respond/respond.min.js"></script>
<![endif]-->
<style type="text/css">
.page-header {
  margin: 0 0 10px;
  border-bottom: 1px solid #c7c7c7;
}
.box-content .page-header, legend, .full-calendar .page-header {
  margin: 0 0 10px;
  border-bottom: 1px dashed #B6B6B6;
}

#page-500, #page-login {
  position: absolute;
  height: 100%;
  width: 100%;
}
#page-500 {
  background: #ebebeb;
}
#page-500 img {
  display: block;
  margin:30px auto;
}
#page-login .logo {
  position:absolute;
}
#page-login h3 {
  font-size:20px;
  font-family: 'Righteous', cursive;
}
#page-login .text-right {
  margin-top: 15px;
}
#page-login .box {
  margin-top:15%;
}
</style>		

</head>
<body>
<div class="container-fluid">
	<div id="page-login" class="row">
		<div class="col-xs-12 col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
			<form action="<spring:url value="/login.html"></spring:url>" method="POST">
			<div class="box">
				<div class="box-content">
					<div class="text-center">
						<h3 class="page-header">快上网商城登陆</h3>
					</div>
					<div class="form-group">
						<label class="control-label">用户名</label>
						<input type="text" class="form-control" name="username" id="username"  maxlength="20" />
					</div>
					<div class="form-group">
						<label class="control-label">密码</label>
						<input type="password" class="form-control" name="password" maxlength="20" id="password"/>
					</div>
					<div class="text-center">
					<input type="hidden"                        
							name="${_csrf.parameterName}"
							value="${_csrf.token}"/>
						<input type="submit" class="btn btn-primary" value="登  陆">
					</div>
					<p class="text-danger">
						<c:if test="${param.error != null}">
							用户名密码不正确.
						</c:if>
						<c:if test="${param.logout != null}">
							您已经成功注销登陆.
						</c:if>
	
					</p>
				</div>
			</div>
			</form>
		</div>
	</div>
</div>

</body>
</html>