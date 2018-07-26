<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
  <div class="col-xs-12">
<div style="float:left;width:30%">
  <img src="<spring:url value="/resources/images/example-slide-2.jpg"></spring:url>" style="width:60px;height:60px;" >
</div>
 <div style="float:right; width:70%">
<ul class="list-unstyled">
  <li> 
  	欢迎：${cur.username } 
   </li>
  <li> ${cur.telphone } </li>
  <li> 上次登录：
  <fmt:formatDate value="${cur.lastlogin_date }" pattern="yyyy年MM月dd日" />
  </li>
</ul>   

 </div>     

  </div>
</div>

<div class="row">
  <div class="col-xs-12">

  <a class="ajax-link" href="ajax/orders.html">
      <span class="glyphicon glyphicon-list-alt"  aria-hidden="false"></span>
      <span> 我的订单 </span> 

      <div class="div-right">
          <span class="glyphicon glyphicon-menu-right"  aria-hidden="false"></span>  
      </div>
     </a>
  </div>
</div>

<div class="row">
  <div class="col-xs-12">

  <a class="ajax-link" href="ajax/address.html">
  <span class="glyphicon  glyphicon-book"  aria-hidden="false"></span>
      <span> 收货地址 </span> 

      <div class="div-right">
          <span class="glyphicon glyphicon-menu-right"  aria-hidden="false"></span>  
      </div>
     </a>
  </div>
</div>

<div class="row">
  <div class="col-xs-12">

  <a class="ajax-link" href="ajax/msg.html">
  <span class="glyphicon glyphicon-envelope"  aria-hidden="false"></span>
      <span> 消息 </span> 
<!--  
	<span class="badge">14</span>
-->
      <div class="div-right">
          <span class="glyphicon glyphicon-menu-right"  aria-hidden="false"></span>  
      </div>
     </a>
  </div>
</div>

<div class="row">
  <div class="col-xs-12 text-center">
  <form id="logout_form"  action="<spring:url value="/logout"></spring:url>" method="post">
<input type="submit" value="注销登录"  class="btn btn-default" />
<input type="hidden"
	name="${_csrf.parameterName}"
	value="${_csrf.token}"/>
</form> 

  </div>
</div>


 <script>
    $(function() {
    	
    	$('form[id="logout_form"]').submit(function() {

      	  $.ajax({
                  url: $(this).attr('action'),
                  type: 'POST',
                  data: $(this).serialize(),
                  success: function(result) {
                	  
                	  go2page('ajax/dashboard.html');
                  }
              });
      	  
      	  return false;

      	});
    });
 </script>

