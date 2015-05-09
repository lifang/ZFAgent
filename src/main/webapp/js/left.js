
//input focus所有input焦点效果
$(function(){

	/*------用户后台导航菜单--------*/
	$(function(){
		$(".side_menu > li >a:nth-child(1)").unbind("click").bind("click",function(){
			$(this).parent().find("ol.sub_menu").toggle();
			
			$(this).parent().addClass("hover").siblings().removeClass("hover");
			$(this).parent().siblings().find("ol.sub_menu li").removeClass("hover");
			
			if(!$(this).parent().find("ol.sub_menu").is(":visible")){
				$(this).find("i").removeClass("on").addClass("off");
			}else{
				$(this).find("i").removeClass("off").addClass("on");
			}
		})
		
		$("ol.sub_menu >li > a").unbind("click").bind("click",function(){
			$(this).parent("ol.sub_menu >li").addClass("hover").siblings().removeClass("hover");
			$(this).parents(".side_menu >li").addClass("hover").siblings().removeClass("hover");
			$(this).parents(".side_menu >li").siblings().find("ol.sub_menu >li").removeClass("hover");
		});
	})

 
})