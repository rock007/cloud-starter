<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
  <div class="col-xs-12">

    <div  style="text-align:right;width:80%;float:left;vertical-align:middle;">
        <input type="text" id="searck_key" name="searck_key"  class="form-control" placeholder="输入想要查找的产品"/>&nbsp;
    </div>
    <div style="text-align:left;width:20%;float:right;">
        <button class="btn btn-default" onclick="postSearch()">搜索</button> 
    </div>

  </div>
</div>

<div class="row">
  <div class="col-xs-12">
    <span class="title">手机品牌</span>
    <ul class="list-inline">
      <li> <a  class="ajax-link"  href="ajax/products.html?k=联想">联想</a> </li>
      <li> <a  class="ajax-link"  href="ajax/products.html?k=华为">华为</a> </li>
      <li> <a  class="ajax-link"  href="ajax/products.html?k=酷派">酷派</a> </li>
      <li> <a  class="ajax-link"  href="ajax/products.html?k=诺基亚">诺基亚</a> </li>

      <li> <a  class="ajax-link"  href="ajax/products.html?k=魅族">魅族</a> </li>
      <li> <a  class="ajax-link"  href="ajax/products.html?k=VoVo">VoVo</a> </li>
      <li> <a  class="ajax-link"  href="ajax/products.html?k=微软">微软</a> </li>
      <li> <a  class="ajax-link"  href="ajax/products.html?k=Apple">Apple</a> </li>


    </ul>
  </div>
</div>

<div class="row">
  <div class="col-xs-12">
    <span class="title">手机配件</span>
    <ul class="list-inline">
      <li> <a class="ajax-link"  href="ajax/products.html?c=手机套">手机套</a> </li>
      <li> <a class="ajax-link"  href="ajax/products.html?c=移动硬盘">U盘、移动硬盘</a> </li>
      <li> <a  class="ajax-link"  href="ajax/products.html?c=耳机">耳机</a> </li>
      <li> <a  class="ajax-link"  href="ajax/products.html?c=手机吊饰">手机吊饰</a> </li>

      <li> <a  class="ajax-link"  href="ajax/products.html?c=充电宝">充电宝</a> </li>
      <li> <a  class="ajax-link"  href="ajax/products.html?c=贴膜">贴膜</a> </li>

    </ul>
  </div>
</div>

<script type="text/javascript">

function postSearch(){
	
	var key= $("#searck_key").val();
	
	key=$.trim(key);
	
	if(key==''){
		
		message('搜索关键字不能为空！');
		return;
	}
	go2page('ajax/products.html?k='+key);
}

</script>

