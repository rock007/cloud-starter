<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<div class="row">
  <div class="col-xs-12">   

<ul class="list-unstyled">
  <li> 
  	<strong>${one.orderStatuStr}</strong>
  </li>
  <li>   
      <ul class="list-inline">
<li>订单金额:￥${one.price }</li>
<li></li>
      </ul>

  </li>
  <li>
      <div style="text-align:right;">
      <c:if test="${one.orderStatus == 'nopay' }">
      	<a href="ajax/pay.html?orderid=${one.id }" class="btn btn-default ajax-link">立即支付</a>
      </c:if>
      
      </div>
  </li>
</ul>

  </div>
</div>

<div class="row">
  <div class="col-xs-12 ">   

<ul class="list-unstyled">
  <li>   
      <ul class="list-inline">
<li>收货人:${one.address_user }</li>
<li>${one.address_phone }</li>
      </ul>
  </li>
   <li><strong>收货地址：</strong>
${one.address_full }
  </li>
</ul>

  </div>
</div>
 
<div class="row">
  <div class="col-xs-12 col-md-8 fit-size">
    <table class="products-table">
    <tr>
    <td colspan="2">
      <span class="title">
       
       <c:if test="${not empty dealer }">
       		${dealer.name }
       </c:if>
       
       </span>
    </td>
    </tr>
      <tr>
        <td>
          <img src="<spring:url value="/resources/images/empty.jpg"></spring:url>" value="/product-img-${one.orderProduct.productId }.action" class="product-img" alt="产品图片"></img>
            
        </td>
        <td>
          <ul class="list-unstyled">
            <li><a class="ajax-link" href="ajax/product-${one.orderProduct.productId }.html">${one.orderProduct.title }</a></li>
            <li>
              <small>
            ${one.attrs_text} 
          </small>
          <small>${one.remarks}</small>
           
            </li>
            <li>
              <div  style="text-align:left;width:90%;float:left;">
              	<s>${one.orderProduct.price_normal }</s> &nbsp;
              	<span class="product-price">￥${one.orderProduct.price_now }</span>
              </div>
              
            </li>
          </ul>
          
        </td>
      </tr>
</table>
</div>
</div>


<!--物流信息-->
<c:if test="${not empty goodsPackage}">
<div class="row">
  <div class="col-xs-12 ">    
	<ul class="list-unstyled" id="sms_info" style="display:block;height: 140px; overflow: hidden;">

    	<li><strong>${goodsPackage.ems_name}</strong> 订单号:<strong> ${goodsPackage.ems_no}</strong></li>
    	${goodsPackage.ems_logs}

	</ul>
    <div style="text-align:right;">
    	<a href="javascript:readmore('sms_info')">查看详细</a>  
    </div>
  </div>
</div>
</c:if>

<!-- Columns start at 50% wide on mobile and bump up to 33.3% wide on desktop -->
<div class="row">
  <div class="col-xs-12" style="text-align:center">
    <a href="ajax/chat.html?seller=${one.orderProduct.create_user }" class="btn btn-default ajax-link">咨询</a>
    <c:if test="${one.orderStatus == 'pay' }">
    	<a href="javascript:sendReqPack(${one.id })" class="btn btn-default">提醒发货</a>
    </c:if>
  </div>
</div>

<c:if test="${one.orderStatus == 'packonline' || one.orderStatus == 'packarrive' }">

<div class="row">
  <div class="col-xs-12" style="text-align:center">
   
    <a href="" class="btn btn-default" data-toggle="modal" data-target="#submitSaveGoodsModal">确认收货</a>
    
    <c:if test="${ empty reqBack }"> 
      <a href="ajax/order-reqback.html?orderId=${ one.id }" class="btn btn-default ajax-link">申请退款</a>
    </c:if>
  </div>

</div>

</c:if>


<div class="row">

<c:if test="${not empty reqBack }">
  <div class="col-xs-12">
<ul class="list-unstyled">
  
  <li><span class="title">退款情况</span></li>
  <li><span class="font-blue">发起时间：</span>${reqBack.create_date }</li>
  <li><span class="font-blue">剩余时间：</span>3天12小时 </li>
  <li><span class="font-blue">申请理由：</span><span>${reqBack.req_text }</span> </li>
  <li><span class="font-blue">店长操作：</span><span class="font-red">${reqBack.status }</span></li>
  <li><span class="font-blue">拒绝理由：</span><span class="font-red">${reqBack.answer_text }</span></li>
</ul>
  
  </div>
  
 </c:if> 
  
</div>

 <!-- Modal -->
<div class="modal fade" id="submitSaveGoodsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">确认收货</h4>
      </div>
      <div class="modal-body">
        我已经收到货物，确认？
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">否</button>
        <button type="button" class="btn btn-primary">是</button>
      </div>
    </div>
  </div>
</div>

<script>
    $(function() {
    	asyn_img();
    });
    
    function sendReqPack(orderid){
    	
    	LoadAjaxResult('/send-req-pack.action?orderId='+orderid);
    }
    
</script>