<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html  lang="zh-CN">
<head>
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
          <a id="back-btn" class="navbar-brand ajax-link" href="ajax/dashboard.html"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>返回</a>
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
	<div id="ajax-content">
	<div class="row">
  		<div class="col-xs-12">
    		<div class="panel panel-default">
  				<div class="panel-body">
    				${trade_status }<br>
    				查看订单:<a href="ajax/order-${onePay.orderId }.html" class="btn btn-link ajax-link">${order.orderCode }</a>
  				</div>
			</div>
			
 			<ul  class="list-unstyled">
   				<li><strong><a href="ajax/product-${order.orderProduct.productId }.html" class="ajax-link" >${order.orderProduct.title }</a></strong></li>
   				<li>支付金额：<strong>${onePay.pay_money }</strong></li>
   				<li>留言:${onePay.remarks }</li>
 			</ul>
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