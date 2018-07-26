<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
  <div class="col-xs-12">
    
<form id="register_form" action="<spring:url value="submit-register.action"></spring:url>" method="POST">
  <div class="form-group">
    <label for="exampleInputEmail1">用户名</label>
    <input type="text" class="form-control" maxlength="15" name="username" placeholder="输入用户名">
  </div>
  <div class="form-group">
    <label for="exampleInputEmail1">密码</label>
    <input type="text" class="form-control"  maxlength="15"  name="pass" placeholder="输入密码">
  </div>
  <div class="form-group">
    <label for="exampleInputEmail1">重复输入密码</label>
    <input type="text" class="form-control"  maxlength="15"  name="repass" placeholder="输入密码">
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">联系电话</label>
    <input type="tel" class="form-control"  maxlength="25"  name="telphone" placeholder="输入联系电话">
  </div>
  <!-- 
  <div class="form-group" >
    <label for="exampleInputPassword1">验证码 <a href="#">59秒后重发</a></label>
    <input type="tel" class="form-control"  maxlength="5"  name="validNum" placeholder="输入短信验证码">
    
  </div>
 -->					
	<div class="form-group text-center">
  		<input type="submit" class="btn btn-primary" value="立即注册">
	</div>

</form>

   </div>
</div>

<script type="text/javascript" src="<spring:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js"></spring:url>"></script>
<script type="text/javascript" src="<spring:url value="/resources/plugins/bootstrapvalidator/language/zh_CN.js"></spring:url>"></script>
<script type="text/javascript">

function formValidator(){
	
	$('#register_form').bootstrapValidator({
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
			pass: {
				validators: {
					notEmpty: {
						message: '密码不能为空！'
					},
					identical: {
	                    field: 'repass',
	                    message: '两次输入密码不一致'
	            	}
				}
			},
			repass: {
				validators: {
					notEmpty: {
						message: '重复密码不能为空！'
					},
					identical: {
		                 field: 'pass',
		                 message: '两次输入密码不一致'
		            }
				}
			},
			telphone: {
				validators: {
					notEmpty: {
						message: '电话不能为空！'
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
        	  
            	console.log(result);

                if(result.success){
                	go2page(result.data);
                }else{
                	 message(result.msg);
                }
          		
          }
      });
    });
}
$(function(){
	
	formValidator();
});

</script>
