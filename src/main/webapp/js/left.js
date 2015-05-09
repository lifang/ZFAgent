
//input focus所有input焦点效果
$(function(){
	 
	//左侧样式调整
	$("#left_common li").unbind("click").bind("click", function(){
		$(this).children('a').addClass("hover");
		$(this).siblings().children('a').removeClass("hover");
		if (!$(this).hasClass("second") ){ //判断是否有子节点
			if ( !$(this).parents().hasClass("second") ){
				$(".second").children('ol').children('li').children('a').removeClass("hover");
			}
		}
   });
	
	/*------用户后台导航菜单--------*/
	$("li.second > a:nth-child(1)").click(function(){
		$(this).parent().find("ol").toggle();
		if(!$(this).parent().find("ol").is(":visible")){
			$(this).find("i").removeClass("on").addClass("off");
		}else{
			$(this).find("i").removeClass("off").addClass("on");
		}
	});
 
})