<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
  <div class="col-xs-12">
 
<div class="panel panel-default">
  <div class="panel-body">
    正在操作，请稍后...

    <div class="progress">
  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
    <span class="sr-only">40% Complete (success)</span>
  </div>
</div>

  </div>
</div>

<div style="text-align:center;">
  <a href="ajax/orders.html" class="btn btn-primary ajax-link">支付完成，查看</a>
  <a href="" class="btn btn-default ajax-link">支付未完成，帮助</a>
</div>

   </div>
</div>

<c:redirect url="/pay/ali-pay-create.action">
	<c:param name="out_trade_no" value="200001" /> 
	<c:param name="subject" value="订单标题" /> 
  	<c:param name="total_fee" value="0.01" /> 
  	<c:param name="body" value="备注" /> 
</c:redirect>