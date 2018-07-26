<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<body>
<p>
抱歉，系统出现问题，马上跳转至登陆页。。。。
</p>

<script type="text/javascript">

setTimeout(function(){
	
	window.location.href="/login.html"; 
	
},5000);
</script>
</body>
</html>