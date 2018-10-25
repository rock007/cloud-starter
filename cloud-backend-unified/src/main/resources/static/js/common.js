var backend_host="";

$(function() {

	backend_host=$('#sso_host').val();
	
	// Waves初始化
	Waves.displayEffect();
	// 数据表格动态高度
	$(window).resize(function () {
		$('#table').bootstrapTable('resetView', {
			height: getHeight()
		});
	});
	
	// select2初始化
	//$('select').select2();
	
	if(self==top){
		window.top.location.href=backend_host+"/dashboard?p=system";
	}
});
// 动态高度
function getHeight() {
	return $(window).height() - 20;
}
// 数据表格展开内容
function detailFormatter(index, row) {
	var html = [];
	$.each(row, function (key, value) {
		html.push('<p><b>' + key + ':</b> ' + value + '</p>');
	});
	return html.join('');
}
// 初始化input特效
function initMaterialInput() {
	$('form input[type="text"]').each(function () {
		if ($(this).val() != '') {
			$(this).parent().find('label').addClass('active');
		}
	});
}


/**
 * 特殊字符验证
 * author lj-20161229
 * @param n 控件名称
 */
function checkIllegalChar() {

	$('input[type="text"]').bind('keyup',function(e){
		
		var me=$(this);
		var IllegalString = "１２３４５６７８９０ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ，．，。‘＇；；～＠＃＄％＾＆＊（）＿＼｜｛｝［］＜＞＂＂；×——【】、\|》《“”——……\*·`~!！@@##\$￥%\^\&\*\\_\<>\"\{\}\\\\;\[\]";
		var reg=/[１２３４５６７８９０ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ，．，。‘＇；；～＠＃＄％＾＆＊（）＿＼｜｛｝［］＜＞＂＂；×——【】、\|》《“”——……\*·`~!！@@##\$￥%\^\&\*\\_\<>\"\{\}\\\\;\[\]]/img; 
		var textboxvalue = me.val();
		var index = textboxvalue.length - 1;
		var s = textboxvalue.charAt(index);
		
		if (reg.test(textboxvalue)) {
			if (IllegalString.indexOf(s) >= 0) {

				//$.alert('请勿输入特殊字符: ' + s);
				var exist= me.parent().find('p.text-danger');
				
				if(exist.length > 0){
					exist.text('请勿输入特殊字符: ' + s);
				}else{
					me.parent().append('<p class="text-danger">'+'请勿输入特殊字符: ' + s+'</p>');					
				}

				textboxvalue=textboxvalue.replace(reg,"");
				
				me.val(textboxvalue);
				
				setTimeout(function(){
					me.parent().find('p.text-danger').remove();	
				},3000);
			}
		}else{
			//me.parent().find('p.text-danger').remove();
		}
		
	});
	
}

function msgbox(content){
	
	$.confirm({
		theme: 'dark',
		animation: 'rotateX',
		closeAnimation: 'rotateX',
		title: false,
		content: content,
		buttons: {
			confirm: {
				text: '确认',
				btnClass: 'waves-effect waves-button waves-light'
			}
		}
	});
}