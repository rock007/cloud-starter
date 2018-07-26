<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="row">
  <div class="col-xs-12">
      
    <div id="slides">
    	<c:forEach var="img" items="${product.images}">
    		<c:if test="${img.postion == 0}">
    			<img src="/${img.src }" alt="${img.alt }" >
    		</c:if>
    	</c:forEach>
    	
    	<c:if test="${fn:length(product.images)==0}">
    		<img src="<spring:url value="/resources/images/empty.jpg"></spring:url>" alt="" >
    		<img src="<spring:url value="/resources/images/empty.jpg"></spring:url>" alt="" >
    	</c:if>
    	
    </div>
      
  </div> 
  <div class="col-xs-12 fit-size">
    <table class="products-table">
      <tr>
        <td>
          <ul class="list-unstyled product-info-ul">
            <li> <span class="title">${product.title}</span>${product.keywords}</li>
            
            <!-- 
            <li>
              <div style="text-align:right;width:30%;float:right;"> 
                <span class="label label-success">折扣</span>
                <span class="label label-info">限量</span>
               </div>
                <div class="clearfix">
               </div>  
            </li>
             -->
             
<c:if test="${fn:length(product.attrs) >0 }">
           <li>
             <div style="text-align:left;width:100%;float:right;"> 
             	<div class="btn-group" data-toggle="buttons">
             		<c:forEach var="attr" items="${product.attrs}">
  						<label class="btn btn-default">
    						<input type="radio" name="product_attr_option" key1="${attr.attr_name}" value="${attr.attr_name}" price1="${attr.price_normal}" price2="${attr.price_now}" validNum="${attr.stock - attr.sales_num}" id="option_${attr.id}" > ${attr.attr_name } 
  						</label>             
             		</c:forEach>
               </div>
             </div>                    
           </li>
</c:if>
           <li>
              <div  style="text-align:left;width:100%;">
              	<s class="product-normal">￥${product.price_normal }</s>&nbsp;
              	现价:<span class="product-price">￥${product.price_now }</span>
              </div>
                            
           </li>
           <li>    
           		<ul class="list-inline" style="text-align:right;">
           			<li>
           			    <form role="form" action="<spring:url value="/add-cart.action"></spring:url>" method="post">
    						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    						<input type="hidden" name="productId" value="${product.id}"/>
    						<input type="hidden" name="attr_id" />
    						<input type="hidden" name="attr_name" />
    						<input type="hidden" name="attr_value" />
    						<input type="hidden" name="attr_price_normal" value="${product.price_now}"/>
    						<input type="hidden" name="price" value="${product.price_now}"/>
    						
           					<input type="submit" class="btn btn-default" value="加入购物车">
           				 </form>
           			</li>
           			<li>
           				  <form role="form" action="<spring:url value="/pay-now.action"></spring:url>" method="post">
    						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    						<input type="hidden" name="productId" value="${product.id}"/>
    						<input type="hidden" name="attr_id" />
    						<input type="hidden" name="attr_name" />
    						<input type="hidden" name="attr_value" />
    						<input type="hidden" name="attr_price_normal" value="${product.price_now}"/>
    						<input type="hidden" name="price" value="${product.price_now}"/>
    						
           					<input type="submit" class="btn btn-primary" value="购买">
           				 </form>
           			</li>
           		</ul>
          
           </li>
 
          </ul>
          
        </td>
      </tr>
    </table>
    
  </div>
</div>

<div class="row">
    <div class="col-xs-12" >
    	<a class="ajax-link" href="ajax/dealer-${product.dealerId}.html">${dealer.name }</a>
    </div>
</div>    

<div class="row">
  
    <div class="col-xs-12" >
        
    <ul id="myTab" class="nav nav-pills" role="tablist">
      <li role="presentation" class="active"><a href="#product-desc" id="desc-tab" role="tab" data-toggle="tab" aria-controls="product-desc" aria-expanded="true">描述</a></li>
      <li role="presentation" class=""><a href="#product-args" role="tab" id="args-tab" data-toggle="tab" aria-controls="product-args" aria-expanded="false">参数</a></li>
      
      <c:if test="${not empty curUser }">
      		<li role="presentation" class="" ><a class="ajax-link"  href="ajax/chat.html?seller=${product.create_user}" role="tab" id="chat-tab">咨询</a></li>
      </c:if>
      
    </ul>
    
    <div id="myTabContent" class="tab-content">
      <div role="tabpanel" class="tab-pane fade active in" id="product-desc" aria-labelledby="desc-tab">
        ${product.html_desc}
      </div>
      <div role="tabpanel" class="tab-pane fade" id="product-args" aria-labelledby="args-tab">
        <table class="table table-condensed">
        	<c:forEach var ="arg" items="${product.args }">
        		<tr>
        			<td>${arg.arg_name }</td><td>${arg.arg_text }</td>
        		</tr>
        	</c:forEach>
        </table>
      </div>    
    </div>
        
    </div>
</div>
   <script>
    $(function() {
      
      $('#slides').slidesjs();
      
      $('input[type="radio"][name="product_attr_option"]').change(function(){
    	  
    	  var id=$(this).attr('id').substr(7);
    	  
    	  var attr_name=$(this).attr('key1');
    	  //var attr_value=$(this).attr('value');
    	  
    	  var price_normal=$(this).attr('price1');
    	  var price_now=$(this).attr('price2');
    	  
    	  $('input[type="hidden"][name="attr_id"]').val(id);
    	  $('input[type="hidden"][name="attr_name"]').val(attr_name);
    	  $('input[type="hidden"][name="attr_value"]').val(attr_name);
    	  
    	  $('input[type="hidden"][name="attr_price_normal"]').val(price_normal);
    	  $('input[type="hidden"][name="price"]').val(price_now);
    	  
    	  $('.product-normal').text('￥'+price_now);
    	  $('.product-price').text('￥'+price_now);
      });
      
      $('form').submit(function() {

    	  var radios=$('input[type="radio"][name="product_attr_option"]');
    	  var isFound=false;
    	  
    	  for(var i=0;i<radios.length;i++){
    		  
    		  if(radios[i].checked==true){
    			  
    			  isFound=true;
    			  break;
    		  }
    	  }
    	  
    	  if(radios.length>0 && isFound==false){
    		  
    		  message('请选择产品类别');
    		  
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
