<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="row">
  <div class="col-xs-12 col-md-8 ">
       <form role="form" action="<spring:url value="/rm-cart.action"></spring:url>" method="post">
    		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    		<input type="hidden" name="productId" value="${product.id}"/>
    		<input type="hidden" name="cart_ids" id="cart_ids"/>
    		
    		<input type="submit" class="btn btn-default" value="删除">
    	</form>
  </div>
</div>

  <div class="row">
    
      <div class="col-xs-12 col-md-8 fit-size">
    <table class="products-table">
     <c:set value="0" var="sum" />  
    <c:forEach var="item" items="${list}">
        <tr>
        <td> <input type="radio" name="del_cbx" value="${item.id }"/> </td>
        <td>
          <img src="<spring:url value="/resources/images/empty.jpg"></spring:url>" value="/product-img-${item.productId }.action" class="product-img" alt="产品图片"></img>
            
        </td>
        <td>
          <ul class="list-unstyled">
            <li><a class="ajax-link" href="ajax/product-${item.productId }.html">${item.product.title }</a>
            	${item.product.keywords }
            </li>
            <c:if test="${ !empty item.attr_name }">
            <li>
            	${item.attr_name}
            </li>
            </c:if>
            
            <li>
              <div  style="text-align:right;width:90%;float:left;"><s>￥${item.attr_price_normal}</s>
              	&nbsp;&nbsp;<span class="product-price">￥${item.price}</span>
              </div>
            </li>
          </ul>
          
        </td>
      </tr>
    	<c:set value="${sum + item.price}" var="sum" />  
    </c:forEach>
    
    </table>
      </div>   

</div>

<div class="row">
  <div class="col-xs-12">
<div style="text-align:right;">
  共<strong>${fn:length(list)}</strong>件，总计 <strong> ￥${sum}</strong>
   <form role="form" name="pay_carts_form" action="<spring:url value="/pay-carts.action"></spring:url>" method="post">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  		<input type="hidden" name="cart_ids">
    	
    	<input type="submit" class="btn btn-primary" value="立即支付">
    </form>
</div>
  </div>
</div>

 <script>
    $(function() {
    	
    	$('form').submit(function() {

      	  var radios=$('input[type="radio"][name="del_cbx"]');
      	  var ids='';
      	  
      	  for(var i=0;i<radios.length;i++){
      		  
      		  if(radios[i].checked==true){
      			
      			ids+=radios[i].value+','
      			  
      		  }
      	  }
      	  
      	  $('input[type="hidden"][name="cart_ids"]').val(ids);
      	  if(radios.length>0 && ids==''){
      		  
      		  message('请选择要处理的产品！');
      		  
      		  return false;
      	  }
      	  
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
                      }
                  }
              });
      	  
      	  return false;

      	});
    	
    	asyn_img();
    });
 </script>
