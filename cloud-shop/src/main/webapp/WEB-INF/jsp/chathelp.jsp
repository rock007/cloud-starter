<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>咨询帮助</title>
   
   <script src="/resources/plugins/react/react.js"></script>
   <!-- -->  
   <script src="/resources/plugins/react/JSXTransformer.js"></script>
   
</head>
<body>

this is a page

<div id="msg_placehoid"></div>
<script src="/resources/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/resources/shop/js/build/chat.js"></script>
<script type="text/javascript">

$(function(){
	
	React.render(React.createElement(MsgView, {toUser: "${to_user}", fromUser: "${from_user}", gid:"${gid}" }), document.getElementById("msg_placehoid"));
})
	
</script>
</body>
</html>

