<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!--  
<c:if test="${empty sessionScope.SHOP_FUCK_PAY_CARTS}">

没有什么东西需要支付

</c:if>
-->

<div class="row">
  <div class="col-xs-12">
    
 <ul  class="list-unstyled">
   <li>商品名称：<strong>${order.orderProduct.title }</strong></li>
   <li>商品描述： <span> 为 <strong>${order.createUser }</strong>  付款<strong>￥${order.price }</strong> 用于商品购买</span></li>
   <li>留言:${order.remarks }</li>
 </ul>

   </div>
</div>

<div class="row">
  <div class="col-xs-12">
  <strong>选择支付方式</strong>
<!-- 
 <div class="radio">
  <label>
    <input type="radio" name="optionsRadios" id="optionsRadios1" value="alipay" checked>
    支付宝钱包支付
  </label>
</div>
 -->
 
<div class="radio">
  <label>
    <input type="radio" name="optionsRadios" id="optionsRadios2" value="alipay_web">
    支付宝钱包浏览器支付
  </label>
</div>
<!-- 
<div class="radio">
  <label>
    <input type="radio" name="optionsRadios" id="optionsRadios2" value="weixin">
    微信支付
  </label>
</div>
<div class="radio">
  <label>
    <input type="radio" name="optionsRadios" id="optionsRadios2" value="yixin">
    电信翼支付支付
  </label>
</div>
 -->
<div class="radio">
  <label>
    <input type="radio" name="optionsRadios" id="optionsRadios2" value="carsh">
    货到付款
  </label>
</div>

<div style="text-align:center;">

   	<form role="form" name="pay_type_form" action="pay-order.action" method="post">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  		
    	<input type="hidden" name="orderId" value="${order.id }">
    	
    	<input type="hidden" name="pay_type">
    	
    	<input type="submit" class="btn btn-primary" value="确认支付">
    	
    	<a href="ajax/order-${order.id }.html" class="btn btn-default ajax-link">查看订单，稍后支付</a>
    	   
    </form>
   
</div>

  </div>
</div>

<form name="alipayment" action="/pay/ali-pay-create.action" method="post" >
	<input size="30" type="hidden" name="id" value="${order.id }"/>
<!--  
	<input size="30" type="hidden" name="WIDout_trade_no" value="20150802000${order.id }"/>
	<input size="30" type="hidden"  name="WIDsubject" value=" ${order.createUser}用于购买${order.orderProduct.title }付款"/>
	<input size="30" type="hidden"  name="WIDtotal_fee" value="0.01"/>
	<input size="30" type="hidden"  name="WIDshow_url"  value="www.hs-dx.com/shop/index.html?ajax/product-${order.orderProduct.productId }.html"/>
	<input size="30" type="hidden"  name="WIDbody"  value="商品描述"/>
	-->
</form>

<script type="text/javascript">
var pay_type='';

$(function(){
	
	$('form[name="pay_type_form"]').submit(function() {

  	  var radios=$('input[type="radio"][name="optionsRadios"]');
  	  var isFound=false;
  	
  	  for(var i=0;i<radios.length;i++){
  		  
  		  if(radios[i].checked==true){
  			
  			  $('input[type="hidden"][name="pay_type"]').val(radios[i].value)
  			  pay_type=radios[i].value;
  			  
  			  isFound=true;
  			  break;
  		  }
  	  }
  	  
  	  if(radios.length>0 && isFound==false){
  		  
  		  message('请选择支付类别');
  		  
  		  return false;
  	  }

  	  $.ajax({
              url: $(this).attr('action'),
              type: 'POST',
              data: $(this).serialize(),
              success: function(result) {
              	console.log(result);

                  if(result.success){
                	  
                	  //go2page(result.data);
                	  
                	  if(pay_type == 'alipay_web')
                	  	document.forms['alipayment'].submit();
                	  else{
                		  
                		  //message('暂时仅支持支付宝钱包浏览器支付');
                		  go2page(result.data);
                	  }
                	  
                  }else{
                  	 message(result.msg);
                  	 
                  	 if(result.msg=='请登录系统'){
                  		 go2page(result.data);
                  	 }
                  }
              }
          });
  	  
  	  return false;

  	});
});

</script>
