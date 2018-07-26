<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	

<!-- ads -->
<div class="row">
  <div class="col-xs-12">
      <div id="slides">
      <a href="ajax/product-55.html" class="ajax-link">
      	<img src="<spring:url value="/resources/images/home/init/sy-005-1.png"></spring:url>" alt="iphone6 全系列降价,描述在这里">
      </a>
      <a href="ajax/product-75.html" class="ajax-link">
      	<img src="<spring:url value="/resources/images/home/init/sy-005-2.png"></spring:url>" alt="魅蓝，最佳性价比">
      </a>
      
      <a href="ajax/product-61.html" class="ajax-link">
      	<img src="<spring:url value="/resources/images/home/init/sy-005-5.png"></spring:url>" alt="华为经典，商务典范">
      </a>
      <!--  
      <a href="ajax/product-55.html" class="ajax-link">
      	<img src="<spring:url value="/resources/images/home/init/sy-005-6.png"></spring:url>" alt="网龄优惠套餐">
      </a>
      <a href="ajax/product-55.html" class="ajax-link">
      	<img src="<spring:url value="/resources/images/home/init/sy-005-3.png"></spring:url>" alt="高清盒子，视频无极限">
      </a>
      <a href="ajax/product-55.html" class="ajax-link">
      	<img src="<spring:url value="/resources/images/home/init/sy-005-4.png"></spring:url>" alt="购手机大礼包">
      </a>
      -->
    </div>
  </div>
</div>

<!-- Columns are always 50% wide, on mobile and desktop -->
<div class="row">
  <div class="col-xs-6 metro">
    <a class="ajax-link" href="ajax/products.html"><img class="img-responsive" src="<spring:url value="/resources/images/home/init/sy-006.png"></spring:url>" alt="热门产品 "></a>
  </div>
  <div class="col-xs-6 metro"><a  class="ajax-link"  href="ajax/category.html">
    <img class="img-responsive" src="<spring:url value="/resources/images/home/init/sy-007.png"></spring:url>" alt="产品分类 ">
  </a> </div>
</div>

<div class="row">
  <div class="col-xs-6 metro"><a class="ajax-link" href="ajax/product-56.html"><img class="img-responsive"  src="<spring:url value="/resources/images/home/init/sy-008.png"></spring:url>" alt="强烈推荐 "></a></div>
  <div class="col-xs-6 metro_half"><a class="ajax-link" href="ajax/product-55.html"><img class="img-responsive" src="<spring:url value="/resources/images/home/init/sy-009.png"></spring:url>" alt="Iphone plus"></a></div>
  <div class="col-xs-6 metro_half"><a class="ajax-link" href="ajax/products.html?k=华为"><img class="img-responsive" src="<spring:url value="/resources/images/home/init/sy-010.png"></spring:url>" alt="华为"></a></div>
</div>

<div class="row">
  <div class="col-xs-12">
  	<a class="ajax-link" href="ajax/category.html">
  		<img class="img-responsive" src="<spring:url value="/resources/images/home/init/sy-011.png"></spring:url>" alt="手机配件，手机套、充电宝等等 "/>
  		</a>
  </div>
</div>
<!-- 
<div class="row">
  <div class="col-xs-12"><a class="ajax-link" href="ajax/products.html"><img class="img-responsive" src="<spring:url value="/resources/images/home/init/sy-013.png"></spring:url>" alt="描述在这里 "/></a></div>
</div>
 -->
<script>
$(function() {
      
      $('#slides').slidesjs({
      
        play: {
          active: true,
          auto: true,
          interval: 4000,
          swap: true
        }
      });
      
      
    });
</script>