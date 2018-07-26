<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="row">
  <div class="col-xs-12">
<form id="login_form" action="<spring:url value="/login.html"></spring:url>" method="POST">
<div class="box-content">
					<div class="text-center">
						<h3 class="page-header">电信商城登陆</h3>
					</div>
					<div class="form-group">
						<label class="control-label">用户名</label>
						<input type="text" class="form-control" name="username" id="username"  maxlength="20" />
					</div>
					<div class="form-group">
						<label class="control-label">密码</label>
						<input type="password" class="form-control" name="password" maxlength="20" id="password"/>
					</div>
					<div class="form-group" style="text-align:right;">
						没有账号，立即<a href="ajax/register.html" class="ajax-link" >注册</a>
					</div>
					<div class="text-center">
					<input type="hidden"                        
							name="${_csrf.parameterName}"
							value="${_csrf.token}"/>
						<input type="submit" class="btn btn-primary" value="登  陆">
					</div>
					<p class="text-danger">
						<c:if test="${param.error != null}">
							用户名密码不正确.
						</c:if>
						<c:if test="${param.logout != null}">
							您已经成功注销登陆.
						</c:if>
					</p>
</div>
</form>

	</div>
</div>

<p>

</p>

<script type="text/javascript" src="<spring:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js"></spring:url>"></script>
<script type="text/javascript" src="<spring:url value="/resources/plugins/bootstrapvalidator/language/zh_CN.js"></spring:url>"></script>
<script type="text/javascript">


function formValidator(){
	
	var ajax_url = location.hash.replace(/^#/, '');
	
	$('#login_form').bootstrapValidator({
		message: '信息填写不正确！',
		fields: {
			username: {
				message: '用户名信息不正确',
				validators: {
					notEmpty: {
						message: '用户名不能为空！'
					},
					stringLength: {
						min: 2,
						max: 15,
						message: '用户名不能小于2个字，也不能大于15个字'
					}
				}
			},
			password: {
				validators: {
					notEmpty: {
						message: '密码不能为空！'
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
        e.preventDefault();
       
   	  	$.ajax({
          url: $(this).attr('action'),
          type: 'POST',
          data: $(this).serialize(),
          success: function(result) {
          	  	//console.log(result);
          	  
          		if(result.indexOf('<title>电信商城</title>')>0){
          		
          			go2page(ajax_url);
          		
          		}else if(result.indexOf('<title>欢迎登陆系统</title>')>0){
          			
          			$('.text-danger').html("用户名密码不正确！");
          			
          		}else{
          			
          			$('.text-danger').html("登录失败，请稍后重试！");
          		} 
          }
      });
    });
}
$(function(){
	
	$("#nav_title").text('登录');
	
	formValidator();
});

</script>

