$(function() {
	// Waves初始化
	Waves.displayEffect();
	// 数据表格动态高度
	$(window).resize(function () {
		
		$('#table').bootstrapTable('resetView', {
			height: getHeight()
		});
		
	});
	
	//ie 显示日历选择
	if(checkIE()!=0){
		
		$('input[type="date"]').focus(function(){
			
			WdatePicker({dateFmt:'yyyy/MM/dd'});
		});	
		
		$('input[type="time"]').timepicker({ timeFormat: 'HH:mm' });

	}
	
	if(self==top){
		window.top.location.href="http://219.138.150.224:10004/dashboard?p=sms";
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

/**
 * 特殊字符验证
 * author lj-20161229
 * @param n 控件名称
 */
function checkIllegalChar() {

	$('input[type="text"]').bind('keyup',function(e){
		/**
		 * add by lj-20170113
		 * 非法字符验证
		 */

		var me=$(this);
		var IllegalString = "１２３４５６７８９０ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ．。‘＇；；～＠＃＄％＾＆＊（）＿＼｜｛｝［］＜＞＂＂；×——【】、\|》《“”——……\*·`~!！@@##\$￥%\^\&\*\\_\<>\"\{\}\\\\;\[\]";
		var reg=/[１２３４５６７８９０ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ．。‘＇；；～＠＃＄％＾＆＊（）＿＼｜｛｝［］＜＞＂＂；×——【】、\|》《“”——……\*·`~!！@@##\$￥%\^\&\*\\_\<>\"\{\}\\\\;\[\]]/img; 
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

function checkIE() {

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
    {
        return parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)));
    }
    else  // If another browser, return 0
    {
        return 0;
    }

}