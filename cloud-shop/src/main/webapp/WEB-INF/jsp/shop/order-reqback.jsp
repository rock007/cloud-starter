<% /************************退货申请************************/ %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${ empty reqBack }">
  
<div class="row">
  <div class="col-xs-12 ">   
	<div class="radio">
  		<label>
    		<input type="radio" name="is_received" id="optionsRadios1" value="0">
         	货未收到
  		</label>
	</div>
	<div class="radio">
  		<label>
    		<input type="radio" name="is_received" id="optionsRadios2" value="1">
           	货已经收到
  		</label>
	</div>

  </div>
  
</div>

<div class="row" id="no_received">

<form role="form" name="no_received_form" action="order-reqback.action" method="post">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <input type="hidden" name="orderId" value="${orderId }">
  <input type="hidden" name="is_received" value="0">
    	
  <div class="col-xs-12" >
		<div class="form-group">
    		<label for="reson_text">原因</label>
    		<textarea name="req_text"  maxlength="250" class="form-control" rows="3"></textarea>
  		</div>
  </div>
  
    <div class="col-xs-12" style="text-align:center">
    	<input type="submit" class="btn btn-default" value="提交退款申请">
  	</div>
</form>

</div>

<div class="row" id="do_received">

<form role="form" name="no_received_form" action="order-reqback.action" method="post">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <input type="hidden" name="orderId" value="${orderId }">
  <input type="hidden" name="is_received" value="1">
  
  <div class="col-xs-12" >

		<div class="form-group">
    		<label for="reson_text">退货快递</label>
  			<select class="form-control" name="ems_name" id="ems_name">
    			<option value="">(选择快递)</option>
  				<option value="zhongtong">中通快递</option>
  				<option value="shengtong">申通快递</option>
  				<option value="yuantong">圆通快递</option>
  				<option value="youzheng">邮政</option>
			</select>
  		</div>
  		
  		<div class="form-group">
    		<label for="reson_text">快递订单</label>
    		<input type="text" class="form-control"  maxlength="25"  id="ems_no" name="ems_no" placeholder="请输入快递单号">
  		</div>  
		<div class="form-group">
    		<label for="reson_text">原因</label>
    		<textarea name="req_text"  maxlength="250"  class="form-control" rows="3"></textarea>
  		</div>
  </div>
  
    <div class="col-xs-12" style="text-align:center">
    	<input type="submit" class="btn btn-default" value="提交退款申请">
  	</div>
  	
  </form>
</div>
</c:if>

<div class="row">

<c:if test="${not empty reqBack }">
  <div class="col-xs-12">
<ul class="list-unstyled">
  
  <li><span class="title">退款情况</span></li>
  <li><span class="font-blue">发起时间：</span>${reqBack.create_date }</li>
  <li><span class="font-blue">剩余时间：</span>3天12小时 </li>
  <li><span class="font-blue">申请理由：</span><span>${reqBack.req_text }</span> </li>
  <li><span class="font-blue">店长操作：</span><span class="font-red">${reqBack.status }</span></li>
  <li><span class="font-blue">拒绝理由：</span><span class="font-red">${reqBack.answer_text }</span></li>
</ul>
  
  </div>
  
 </c:if> 
  
</div>

<script>
    $(function() {
    	$('#no_received').hide();
		$('#do_received').hide();   
		
    	asyn_img();
    	
    	$('input[name="is_received"]').click(function(){
    		
    		var flag=$(this).val()
    		if(flag==0){    			
    			//未收到
    			$('#no_received').show();
    			$('#do_received').hide();    			
    			
    		}else if(flag==1){    			
    			//已经收到
    			$('#no_received').hide();
    			$('#do_received').show();
    		}
    	});
    	
    	 $('form').submit(function() {
    		 
    		 var req_text =$(this).find('textarea[name="req_text"]').val();
    		 
    		 var ems_name =$('#ems_name').val();
    		 
    		 var ems_no =$('#ems_no').val();
    		 
    		 var is_received =$(this).find('input[name="is_received"]').val();

    		 if(req_text==''){
    	   		  
    	   		  message('请填写退款理由!');
    	   		  
    	   		  return false;
    	   	  }

    		 if(is_received==1){
    			 
        		 if(ems_name==''){
       	   		  
       	   			message('请填写退货快递!');
       	   		  
       	   		  	return false;
       	   	  	}
        		 
        		 if(ems_no==''){
       	   		  
       	   		     message('请填写退货快递单!');
       	   		  
       	   		     return false;
       	   	    }
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