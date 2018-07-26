<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
  <div class="col-xs-12">
    
<form id="address_form" role="form" action="<spring:url value="/add-address.action"></spring:url>" method="post">
   <input type="hidden" name="id" value="${edit.id }" />
   
  <div class="form-group">
    <label>姓名</label>
    <input type="text" class="form-control"  maxlength="15" name="user_name"  value="${edit.user_name }"  placeholder="输入姓名">
  </div>
  <div class="form-group">
    <label >联系电话</label>
    <input type="tel" class="form-control"  maxlength="25" name="user_phone"  value="${edit.user_phone }"  placeholder="输入联系电话">
  </div>
  <div class="form-group">
    <label >地区</label>
   
<select class="form-control" name="province" >
  <option value="">请选择</option>
  <option value="湖北">湖北</option>
</select>

<select class="form-control" name="city"  >
  <option value="">请选择</option>
  <option value="武汉">武汉</option>
  <option value="黄石">黄石</option>
  <option value="黄冈">黄冈</option>
</select>

<select class="form-control" name="area"  >
  <option value="">请选择</option>
  <option value="石灰窑">石灰窑</option>
  <option value="新下陆">新下陆</option>
  <option value="铁山">铁山</option>
</select>
    <p class="help-block">选择适合的地区.</p>
  </div>
<div class="form-group">
    <label>详细地址</label>
    <textarea class="form-control"  maxlength="500" rows="3" name="rec_address"  id="rec_address">${edit.user_phone } </textarea>
  </div>

<div class="form-group  text-center">
  <button type="submit" class="btn btn-primary">保存</button>
  
  <c:if test="${!empty sessionScope.SHOP_FUCK_PAY_CARTS}">
  <!--  
  <a href="ajax/pay.html" class="btn btn-primary ajax-link">保存并送货到此地址</a>
  -->
   <input type="hidden" name="act_sendto" value="1" />
   
  <button type="submit" class="btn btn-default">保存并送货到此地址</button>
  </c:if>
</div>

</form>

   </div>
</div>

<div class="row">
  <div class="col-xs-12 fit-size">

<table class="products-table">

<c:forEach var="m" items="${list}">
  <tr>
    <td> <input type="radio" name="sel_radio" value="${m.id }" ${m.is_default==1?"checked":"" } />  </td>
    <td>
      <ul class="list-unstyled product-info-ul">
        <li> <strong>${m.user_name }</strong> <strong>${m.user_phone }</strong> </li>
        <li>${m.province } ${m.city } ${m.area }${m.rec_address }</li>
        <li>
        <div style="text-align:right;">

   <c:if test="${m.is_default!=1}">
          <a href="<spring:url value="set-address.action?id=${m.id }"></spring:url>" class="btn btn-default ajax-result">设为默认</a>
    </c:if>      
          <a href="<spring:url value="ajax/address.html?id=${m.id }"></spring:url>" class="btn btn-default ajax-link">修改</a>
          <a href="<spring:url value="rm-address.action?ids=${m.id }"></spring:url>" flag="del" class="btn btn-default ajax-result">删除</a>
          
        </div>
           </li>
      </ul>

    </td>
    <td></td>
  </tr>
</c:forEach>

</table>

  </div>

</div>

<c:if test="${!empty sessionScope.SHOP_FUCK_PAY_CARTS}">

<div class="row">
  <div class="col-xs-12" style="text-align:right;">

	<form role="form" name="pay_address_form" action="<spring:url value="/pay-address.action"></spring:url>" method="post">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  		<input type="hidden" name="address_id">
    	
    	<input type="submit" class="btn btn-primary" value="继续">
    </form>
    
  </div>
</div>

</c:if>

<script type="text/javascript" src="<spring:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js"></spring:url>"></script>
<script type="text/javascript" src="<spring:url value="/resources/plugins/bootstrapvalidator/language/zh_CN.js"></spring:url>"></script>
<script type="text/javascript">

function formValidator(){
	
	$('#address_form').bootstrapValidator({
		message: 'This value is not valid',
		fields: {
			user_name: {
				message: 'The username is not valid',
				validators: {
					notEmpty: {
						message: '收货人姓名不能为空！'
					},
					stringLength: {
						min: 2,
						max: 15,
						message: '收货人姓名不能小于2个字，也不能大于15个字'
					}
				}
			},
			user_phone: {
				validators: {
					notEmpty: {
						message: '收货人联系电话不能为空！'
					},
					digits: {
						message: '电话号码只能输入数字'
					}
				}
			},
			province: {
				validators: {
					notEmpty: {
						message: '省份不能为空！'
					}
				}
			},
			city: {
				validators: {
					notEmpty: {
						message: '城市不能为空！'
					}
				}
			},
			area: {
				validators: {
					notEmpty: {
						message: '区域不能为空！'
					}
				}
			},
			address: {
				validators: {
					notEmpty: {
						message: '详细地址不能为空'
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
	
	$('select[name="province"]').val('${edit.province }');
	$('select[name="city"]').val('${edit.city }');
	$('select[name="area"]').val('${edit.area }');
	
	 $('form[name="pay_address_form"]').submit(function() {

   	  var radios=$('input[type="radio"][name="sel_radio"]');
   	  var isFound=false;
   
   	  for(var i=0;i<radios.length;i++){
   		  
   		  if(radios[i].checked==true){
   			  
   			  $('input[type="hidden"][name="address_id"]').val(radios[i].value);
   			  isFound=true;
   			  break;
   		  }
   	  }
   	  
   	  if(radios.length>0 && isFound==false){
   		  
   		  message('请选择收货地址');
   		  
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
	
});

</script>

