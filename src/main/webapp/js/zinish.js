
$(function(){
	$(".su_s_box a").click(function(){
		if( !$(this).hasClass("hover")){
			$(this).addClass("hover").siblings().removeClass("hover");
		} else {
			$(this).removeClass("hover");
		}
	});	
	$(".ac_area a").click(function(){
		if( !$(this).hasClass("hover")){
			$(this).addClass("hover").siblings().removeClass("hover");
		} else {
			$(this).removeClass("hover");
		}
	});
})
