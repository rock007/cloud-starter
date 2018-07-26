
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>正在提交支付信息</title>
		<meta charset="utf-8">
    	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> 
		<title>支付结果</title>
	  
   		<link href="<spring:url value="/resources/plugins/bootstrap/bootstrap.min.css"></spring:url>" rel="stylesheet">
   		<link href="<spring:url value="/resources/plugins/bootstrap/bootstrap-theme.min.css"></spring:url>" rel="stylesheet">
		<link href="<spring:url value="/resources/css/shop.css"></spring:url>" rel="stylesheet">  
	</head>
	<body>
	<div id="main">  
<nav  class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button id="menu_btn" type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
       
          <div class="navbar-title">
              <span id="nav_title" >电信商城</span>    
           </div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a class="ajax-link" href="ajax/dashboard.html">首页</a></li>
            <li><a class="ajax-link" href="ajax/shop-cart.html">购物车</a></li>
            <li><a class="ajax-link" href="ajax/myboard.html">个人信息</a></li>
           
          </ul>
        </div><!--/.nav-collapse -->
      </div>
</nav>
	
<div class="container" style="padding-top: 55px;" >
	<div class="row">
  <div class="col-xs-12">
 
<div class="panel panel-default">
  <div class="panel-body">
    正在操作，请稍后...

    <div class="progress">
  <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
    <span class="sr-only">40% 完成</span>
  </div>
</div>
  </div>
</div>

<div style="text-align:center;">
  <a href="ajax/orders.html" class="btn btn-primary ajax-link">支付完成，查看</a>
  <a href="#" class="btn btn-default ajax-link">支付未完成，帮助</a>
</div>
   </div>
</div>
	
</div>

</div>

<footer class="footer">
      <div class="container" style="text-align:center;">
        <p class="text-muted">版权所有 2015.</p>
      </div>
</footer>
${html }
<script src="<spring:url value="/resources/plugins/jquery/jquery.min.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/bootstrap/bootstrap.min.js"></spring:url>"></script>

<script type="text/javascript">

$(function(){
	
	$('#main').on('click', 'a', function (e) {
		
		var li = $(this).parents('li');
		var ul = $(this).parents('li').parents('ul');

		var url = $(this).attr('href');
		
		if ($(this).hasClass('ajax-link')) {
			e.preventDefault();
			
			window.location.href="/index.html#"+url;
			//window.location.reload(); 
		}
		if ($(this).attr('href') == '#') {
			e.preventDefault();
		}
		
	});
	
});

</script>
	</body>
</html>
