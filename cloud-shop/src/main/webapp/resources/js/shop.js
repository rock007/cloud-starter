/**
shop 全局函数
**/

"use strict";

var menu_list=[
{"url":"ajax/address.html","title":"地址","back":""},
{"url":"ajax/category.html","title":"产品分类","back":"ajax/dashboard.html"},
{"url":"ajax/chat.html","title":"咨询","back":""},
{"url":"ajax/dashboard.html","title":"首页","back":""},
{"url":"ajax/msg.html","title":"消息","back":""},
{"url":"ajax/myboard.html","title":"个人中心","back":""},
{"url":"ajax/order-","title":"订单详细","back":""},
{"url":"ajax/orders.html","title":"订单列表","back":"ajax/dashboard.html"},
{"url":"ajax/pay-result.html","title":"支付结果","back":""},
{"url":"ajax/pay.html","title":"支付方式","back":""},
{"url":"ajax/product-","title":"产品详细","back":""},
{"url":"ajax/products.html","title":"产品列表","back":"ajax/dashboard.html"},
{"url":"ajax/search.html","title":"搜索","back":""},
{"url":"ajax/shop-cart.html","title":"购物车","back":""},
{"url":"ajax/register.html","title":"用户注册","back":""},
{"url":"ajax/login.html","title":"用户登录","back":""},
{"url":"ajax/submit-order.html","title":"确认订单","back":""},
{"url":"ajax/order-reqback.html","title":"申请退款","back":""},
{"url":"ajax/dealer-","title":"经销商","back":""}
];

function LoadAjaxContent(url){

	$('.preloader').show();
	$.ajax({
		mimeType: 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
		url: url,
		type: 'GET',
		success: function(data) {
			
			$('#ajax-content').html(data);
			$('.preloader').hide();
		},
		error: function (jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		dataType: "html",
		async: true
	});
}

function LoadAjaxResult(url){
	
	$.ajax({
		dataType: "json",
		url: url,
		type: 'GET',
		success: function(result) {
			
          	console.log(result);

            if(result.success&&result.data!=null){
            	go2page(result.data);
            }else{
            	 message(result.msg);
            }
		},
		error: function (jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
	
}

var link_list=[];

$(document).ready(function () {

	var ajax_url = location.hash.replace(/^#/, '');
	if (ajax_url.length < 1) {
		ajax_url = 'ajax/dashboard.html';

		//initNavBar("首页","",ajax_url);
	}

	link_list.push(ajax_url);
	initNavBar();

	LoadAjaxContent(ajax_url);

	$('#main').on('click', 'a', function (e) {
		
		var li = $(this).parents('li');
		var ul = $(this).parents('li').parents('ul');

		/***
		if(li!=null){

			if ($(li).hasClass('active') == false) {

				$(li).addClass('active')
			}
		}
		**/
		var url = $(this).attr('href');
		
		if ($(this).hasClass('ajax-link')) {
			e.preventDefault();
			
			window.location.hash = url;

			if($(this).attr("id")!="back-btn"){

				if(url=='ajax/dashboard.html'){
					link_list=[];
				}
				
				if(link_list[link_list.length-1]!=url){
					
					link_list.push(url);
				}
					
			}else{
				link_list.pop();
			}	

			initNavBar();
			LoadAjaxContent(url);

			if(ul.hasClass('nav navbar-nav')){

				$('#menu_btn').click();
			}

		}
		if ($(this).attr('href') == '#') {
			e.preventDefault();
		}
		
		//
		if($(this).hasClass('ajax-result')){
			
			e.preventDefault();
			
			if($(this).attr('flag')=='del'){
				
				if(show_confirm()){
					
					LoadAjaxResult(url);		
				}
			}else{
				LoadAjaxResult(url);
			}
		}
		
	});
	var height = window.innerHeight - 49;
	$('#main').css('min-height', height);
	//$('#main').css('max-width', 1024);
		
	asyn_img();
	
});

function asyn_img(){
	
	 $('img').each(function(m){
	    	
	    	var img=$(this);
	    	
	    	var link=$(this).attr('value');
	    	
	    	if(link){
	    		
	    		 $.get(link, function() {

	    			  img.attr('src',link);
	    			})
	    			.fail(function() {
	    				
	    				//img.attr('src','/resources/shop/images/empty.jpg');
	    			});
	    	}
	    });
}

function initNavBar(){

	var one;
	if(link_list.length==1){

		//$("#nav_title").text("首页");
		//$("#back-btn").attr('href',"#");
		//$("#back-btn").hide();

		one=findMenu(link_list[0]);

		if(one!=null){

			$("#nav_title").text(one.title);
			$("#back-btn").attr('href',one.back);
			$("#back-btn").hide();
		}

	}else if(link_list.length>=2){

		//var url=link_list[link_list.length-2];
		//console.log(url);
		
		$("#back-btn").attr('href',link_list[link_list.length-2]);	
		$("#back-btn").show();

		one=findMenu(link_list[link_list.length-1]);
		if(one!=null){

			$("#nav_title").text(one.title);
		}

	}
}

function findMenu(url){

	for (var i = 0; i < menu_list.length; i++) {
		var one= menu_list[i];

		if(one.url==url||url.indexOf(one.url)!=-1){
			return one;
		}
	};

	return null;
}

function go2page(url){
	
	window.location.hash = url;
	
	link_list.push(url);
	
	initNavBar();
	
	LoadAjaxContent(url);
}

function message(msg){
	$('#msg_info .modal-body').html(msg);
	$('#msg_info').modal('show');
}

function show_confirm()
{
	return confirm("确定删除操作吗？");
}

function readmore(id){
	
	var dom=$('#'+id);
	
	if(dom.css('height') != '140px'){
	
		$('#'+id).css('height','140px');
	}else{
		
		$('#'+id).css('height','auto');
	}
	
}
