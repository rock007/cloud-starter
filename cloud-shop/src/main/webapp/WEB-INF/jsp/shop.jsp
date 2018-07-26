<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<!DOCTYPE html>
<html  lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> 
	  <title>电信商城</title>
	  
   <link href="<spring:url value="/resources/plugins/bootstrap/bootstrap.min.css"></spring:url>" rel="stylesheet">
   <link href="<spring:url value="/resources/plugins/bootstrap/bootstrap-theme.min.css"></spring:url>" rel="stylesheet">
   
   <link href="<spring:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.css"></spring:url>" rel="stylesheet">
   
   <link href="<spring:url value="/resources/css/shop.css"></spring:url>" rel="stylesheet">  

   <script src="<spring:url value="/resources/plugins/react/react.js"></spring:url>"></script>
   <script src="<spring:url value="/resources/plugins/react/JSXTransformer.js"></spring:url>"></script>
   
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
          <a id="back-btn" class="navbar-brand ajax-link" href="../shop.html"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>返回</a>
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


	
<div class="container" style="padding-top: 55px;max-width:800px;" >
		
	<div class="preloader">
  		<img src="<spring:url value="/resources/images/getdata_loading.gif"></spring:url>" class="getdata-loading" alt="preloader"/>
	</div>

	<div id="ajax-content"></div>
</div>

</div>

<div class="modal fade" id="msg_info" tabindex="-1" role="dialog" aria-labelledby="msg_info_ModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="msg_info_ModalLabel">信息</h4>
      </div>
      <div class="modal-body">
        xxx，确认？
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
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
<script src="<spring:url value="/resources/plugins/jquery-lib/jquery.slides.min.js"></spring:url>"></script>  

<script src="<spring:url value="/resources/js/shop.js"></spring:url>"></script>

</body>
</html>