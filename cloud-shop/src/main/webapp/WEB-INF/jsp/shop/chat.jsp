<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="msg_placehoid" style="padding:10 10 10 10"></div>

<script type="text/javascript" src="<spring:url value="/resources/js/build/chat.js"></spring:url>"></script>
<script type="text/javascript">

$(function(){
	
	React.render(React.createElement(MsgView, {toUser: "${to_user}", fromUser: "${from_user}", gid:"${gid}" }), document.getElementById("msg_placehoid"));
})
	
</script>
