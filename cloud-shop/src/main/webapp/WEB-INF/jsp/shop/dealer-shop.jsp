<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
  <div class="col-xs-12">
  		<input type="hidden" name="search_dealer_id" id="search_dealer_id" value="${dealer.id }">
		${dealer.name }
  </div>
  <div class="col-xs-12 fit-size" id="products-placehold">
  
  </div>
</div>

<script type="text/javascript" src="<spring:url value="/resources/js/build/products.js"></spring:url>"></script>

