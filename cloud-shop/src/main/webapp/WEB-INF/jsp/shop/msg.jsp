<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  
  <c:if test="${fn:length(list)==0}">
  
  <p class="text-info"> 没有数据 </p>
  
  </c:if>
  
  <c:forEach var ='item' items="${list }">
   <div class="row">
  <div class="col-xs-12">
<div style="float:left;width:30%">
  <img src="resources/images/example-slide-2.jpg" style="width:60px;height:60px;" >
</div>
 <div style="float:right; width:70%">
<ul class="list-unstyled">
  <li> <a href="ajax/chat.html?seller=${item.mainUser}" class="ajax-link"><span class="title"> ${item.name}</span> </a>
  	<!--  
  	<span class="badge">14</span>
  	-->
  </li>
  <li> <abbr title="Phone">电话：</abbr> ${item.link_mobile}</li>
  <!--  
  <li> <span class="font-blue">我：</span>  十分感谢 </li>
  -->
</ul>   

 </div>     

  </div>
</div>

  </c:forEach>

