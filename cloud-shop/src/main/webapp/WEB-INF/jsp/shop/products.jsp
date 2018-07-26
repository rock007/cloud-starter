<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
<c:if test="${not empty keyword }">
<div class="col-xs-12">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  	搜索关键字 :<span class="title font-blue" id="search_key_title">${keyword}</strong>
</div>
</c:if>
<c:if test="${not empty cate }">
<div class="col-xs-12">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  	搜索分类 :<span class="title font-blue" id="search_key_cate">${cate}</strong>
</div>
</c:if>

  <div class="col-xs-12 fit-size" id="products-placehold">
  
  </div>
</div>
<script type="text/javascript" src="<spring:url value="/resources/js/build/products.js"></spring:url>"></script>
