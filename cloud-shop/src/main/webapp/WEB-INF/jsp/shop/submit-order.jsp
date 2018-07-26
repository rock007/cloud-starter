<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<c:if test="${!empty oneCart}">

<div class="row">
	<div class="col-xs-12">
    	<ul class="list-unstyled product-info-ul">
        	<li> <strong>收货人：</strong>${address_user } <strong>${address_phone }</strong> </li>
        	<li><strong>收货地址：</strong> ${address_full }</li>
     	</ul>
	</div>
</div>
<div class="row">
  <div class="col-xs-12 col-md-8 fit-size" id="products-placehold">
    <table class="products-table">
    <tr>
    <td colspan="2"> 这里经销商名称 </td>
    </tr>
      <tr>
        <td>
          <img src="<spring:url value="/resources/images/empty.jpg"></spring:url>" value="/product-img-${oneCart.product.id}.action" class="product-img" alt="产品图片"></img>
          
        </td>
        <td>
          <ul class="list-unstyled">
            <li><a class="ajax-link"  href="ajax/product-${oneCart.product.id}.html">${oneCart.product.title }</a></li>
           <c:if test="${ !empty oneCart.attr_name }">
            <li>
              <small>
          		${oneCart.attr_name } 
          	  </small>
            </li>
            </c:if>
            <li>
              <div  style="text-align:right;width:90%;float:left;"><span class="product-price">￥${oneCart.price }</span></div>
            </li>
          </ul>
          
        </td>
      </tr>
      <tr>
      <td colspan="2">
          <textarea class="form-control" placeholder="给卖家留言" maxlength="500" 
    	rows="2" name="order_remarks"  id="order_remarks"></textarea>
      </td>
      </tr>
    </table>
  </div>
</div>

<div class="row">
  <div class="col-xs-12">
 
 <div style="text-align:center;">

   	<form role="form" name="pay_address_form" action="<spring:url value="/submit-order.action"></spring:url>" method="post">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  		<input type="hidden" name="productId" value="${ oneCart.productId}">
  		<input type="hidden" name="attr_price_normal"  value="${ oneCart.attr_price_normal}">
    	<input type="hidden" name="price"  value="${ oneCart.price}">
    	<input type="hidden" name="attr_id"  value="${ oneCart.attr_id}">
    	<input type="hidden" name="attr_name"  value="${ oneCart.attr_name}">
    	<input type="hidden" name="attr_value"  value="${ oneCart.attr_value}">
    	
    	<input type="hidden" name="cartId"  value="${ oneCart.id}">
    	
    	<input type="hidden" name="address_user"  value="${ address_user}">
    	<input type="hidden" name="address_phone"  value="${address_phone }">
    	<input type="hidden" name="address_full" value="${address_full }">
    	
    	<input type="hidden" name="remarks">
    	
    	<input type="submit" class="btn btn-primary" value="提交订单">
    	
    </form>
   
</div>

  </div>
</div>

</c:if>

<script type="text/javascript">

$(function(){
	
	var isLink="${empty oneCart}";
	if(isLink=='true'){
		//go2page('ajax/orders.html');
	}
	
	$('form').submit(function() {

  	  var remarks=$('#order_remarks').val();
  	  $('input[type="hidden"][name="remarks"]').val(remarks)
  	  
  	  $.ajax({
              url: $(this).attr('action'),
              type: 'POST',
              data: $(this).serialize(),
              success: function(result) {
              	console.log(result);

                  if(result.success){
                  	go2page(result.data);
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
	
	asyn_img();
});

</script>
