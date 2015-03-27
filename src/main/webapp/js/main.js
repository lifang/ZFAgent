
//input focus所有input焦点效果
$(function(){
	$("input").focus(function(){
		$(this).addClass("focus");
	})
	$("input").blur(function(){
		$(this).removeClass("focus");
	})
})

//input默认值
function focusBlur(e){
	$(e).focus(function(){
		var thisVal = $(this).val();
		if(thisVal == this.defaultValue){
			$(this).val('');
		}	
	})	
	$(e).blur(function(){
		var thisVal = $(this).val();
		if(thisVal == ''){
			$(this).val(this.defaultValue);
		}	
	})	
}

$(function(){
	focusBlur('.login_area input[type=text]');//登录
	focusBlur('.search input');//search input默认值
	focusBlur('.sp_search input');//search input默认值
	focusBlur('.addAddr_box input[type=text]');//添加地址 input默认值
	focusBlur('.modification input[type=text]');//修改手机号
})

//topinfo 地址
$(function(){
	$(".address").hover(
		function(){
			$(this).find(".addr_h").addClass("addr_h_hover");
			$(this).find(".addr_b").css("display","block");
		},
		function(){
			$(this).find(".addr_h").removeClass("addr_h_hover");
			$(this).find(".addr_b").css("display","none");
		}
	)
})



//订单需要发票
$(function(){
	 //alert($(".invoice_checkbox input").prop("checked"));
	 if($(".invoice_checkbox input").prop("checked")==true){
			$(".invoice").css("display","block");
		}else if($(this).prop("checked")!=true) {
			$(".invoice").css("display","none");
	 }
	$(".invoice_checkbox input").bind("click", function(){
		if($(".invoice_checkbox input").prop("checked")==true){
			$(".invoice").css("display","block");
			$(".invoice_area").removeAttr("disabled");
		}else if($(this).prop("checked")!=true) {
			$(".invoice").css("display","none");
			$(".invoice_area").attr("disabled","disabled");
		}
		
     });
	 
	 $(".invoice a").click(function(){
		$(this).addClass("hover").siblings().removeClass("hover");	 
	 });
	 
})


//弹出层
function popup(t,b){
	var doc_height = $(document).height();
	var doc_width = $(document).width();
	var win_height = $(window).height();
	var win_width = $(window).width();
	
	var layer_height = $(t).height();
	var layer_width = $(t).width();
	
	var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
	
	//tab
	$(b).bind('click',function(){
		    $(".mask").css({display:'block',height:doc_height});
			$(t).css('top',(win_height-layer_height)/2);
			$(t).css('left',(win_width-layer_width)/2);
			$(t).css('display','block');
			return false;
		}
	)
	$(".close").click(function(){
		$(t).css('display','none');
		$(".mask").css('display','none');
	})
}
$(function(){
	popup(".rename_tab",".rename_a");//我的库存 商品更名
	popup(".leaseExplain_tab",".leaseExplain_a");//管理终端  查看说明
	popup(".defaultRatio_tab",".defaultRatio_a");//管理下级代理商  默认比例
})


/*--------------------------------------------------------------------------------------*/

/*------用户后台导航菜单--------*/
$(function(){
	$("li.second > a").click(function(){
		$(this).parent().find("ol").toggle();
		if(!$(this).parent().find("ol").is(":visible")){
			$(this).find("i").removeClass("on").addClass("off");
		}else{
			$(this).find("i").removeClass("off").addClass("on");
		}
	});
})

//鼠标经过小图提示大图
function infoTab(i_tab,i_box){
	$(i_tab).hover(
		function(e){
			$(i_box).children("img").attr("src", $(this).attr("value"));

			$(i_box).css('display','block');
			$(i_box).css('top',$(this).offset().top - $(i_box).height() +'px');
			
			if($(this).offset().left+$(i_box).width() > $(document).width()){
				$(i_box).css( 'left',($(this).offset().left)-$(i_box).width()+'px');
			}else {
				$(i_box).css('left',($(this).offset().left)+$(this).width()+'px');
			}
			
		},
		function(e){
			$(i_box).css('display','none');
			$(i_box).css({'top':0+'px', 'left':0+'px'});
		}
	);
}

$(document).ready(function(){
		infoTab('.cover','.img_info');//首页设置弹出框
})


//交易流水 dealNav菜单
$(function(){
	var page = 1;
	var i = 7;	
	var len = $("ul.li_show").find('li').length;
	if(len <= i){
		$('a.dn_next').css("display","none");
		$('a.dn_prev').css("display","none");
	}
	$('a.dn_next').click(function(){
		//alert(0)
		var $parent = $(this).parents('div.dealNav');
		var $pic_show = $parent.find('.li_show')
		var $smallImg = $parent.find('.dealNavBox');
		var small_width = $smallImg.width();
		
		var page_count = Math.ceil(len/i);
		
		if(!$pic_show.is(':animated')){
			
			if(page == page_count){
				$pic_show.animate({left:'0px'},'slow');
				page = 1;
			}else{
				$pic_show.animate({left:'-='+small_width},'slow');
				page++;	
			}
		}
	})
	
	
	$('a.dn_prev').click(function(){
		//alert(0)
		var $parent = $(this).parents('div.dealNav');
		var $pic_show = $parent.find('.li_show')
		var $smallImg = $parent.find('.dealNavBox');
		var small_width = $smallImg.width();
		var page_count = Math.ceil(len/i);
		
		if(!$pic_show.is(':animated')){
			
			if(page == 1){
				$pic_show.animate({left:'-='+small_width*(page_count-1)},'slow');
				page = page_count;
			}else{
				$pic_show.animate({left:'+='+small_width},'slow');
				page--;	
			}
		}
	})
	
})





//登录
// 切换
$(function(){
	$(".lr_b > div").not(":first").hide();
	$(".lr_h li").unbind("click").bind("click", function(){
		$(this).addClass("hover").siblings().removeClass("hover");
		var index = $(".lr_h li").index( $(this) );
		$(".lr_b > div").eq(index).siblings(".lr_b > div").hide().end().fadeIn(300);
   });
});

//运营中心用户详情切换
$(function(){
	$(".udt_con > div").not(":first").hide();
	$(".udt_title li").unbind("click").bind("click", function(){
		$(this).addClass("hover").siblings().removeClass("hover");
		var index = $(".udt_title li").index( $(this) );
		$(".udt_con > div").eq(index).siblings(".udt_con > div").hide().end().fadeIn(300);
   });
});



/*----运营中心商品列表------*/

//商品分类 category_item_con
$(function(){
	var a=1;
	$(".category_item a.more").click(function(){
		if(a==1){
			$(this).parent(".category_item").addClass("category_item_maxHeight");
			$(this).addClass("up").html("收起<i></i>");
			a=0;
		}else if(a==0){
			$(this).parent(".category_item").removeClass("category_item_maxHeight");
			$(this).removeClass("up").html("更多<i></i>");
			a =1;
		}
		
	});
	
})




//.sortbar 商品排序
$(function(){
	$(".sortbar li").hover(
		function(){
			$(this).find(".droplist").css("display","block");
		},
		function(){
			$(this).find(".droplist").css("display","none");
		}
	)
	
	var text1 = $(".on_1").find("span").html();

	$(".sortbar li.default_sort").click(function(){
		$(this).addClass("hover").siblings().removeClass("hover");
		$(".on_1").find("span").html(text1);
		return false;
	})
	

	$(".droplist a").click(function(){
		
		$(this).parents(".sortbar li").addClass("hover").siblings().removeClass("hover");
		if($(this).parents(".sortbar li").hasClass("hover")){
			$(this).parents(".sortbar li").find("span").html($(this).html());
		}else{
			$(this).parents(".sortbar li").find("span").html(text1);
		}
		
	});	
})

//商品详细支付通道  购买/租赁
$(function(){
	$(".selected_li a").click(function(){
		$(this).addClass("hover").siblings().removeClass("hover");
		if($(this).hasClass("lease_a")){
			$(".price_li").css("display","none");
			$(".deposit_li").css("display","block");
			
			$("a.buy_btn").css("display","none");
			$("a.shoppingCart_btn").css("display","none");
			$("a.lease_btn").css("display","inline-block");
			
		}
		if($(this).hasClass("buy_a")){
			$(".deposit_li").css("display","none");
			$(".price_li").css("display","block");
			
			$("a.buy_btn").css("display","inline-block");
			$("a.shoppingCart_btn").css("display","inline-block");
			$("a.lease_btn").css("display","none");
		}
	});
})

// pro_detail 商品详细信息
$(function(){
	$(".pro_detail_con > div").not(":first").hide();
	$(".pro_detail_title li").unbind("click").bind("click", function(){
		$(this).addClass("hover").siblings().removeClass("hover");
		var index = $(".pro_detail_title li").index( $(this) );
		$(".pro_detail_con > div").eq(index).siblings(".pro_detail_con > div").hide().end().fadeIn(300);
   });
});



//selectBox div模拟select
$(function(){
	$(".tag_select").click(function() {
		$(this).parent(".selectBox").find("ul").toggle();
	});
	$(".selectBox ul li").click(function() {
		var text = $(this).html();
		var $val = $(this).find("input").val();
		$(this).parents(".selectBox").find(".tag_select span").html(text);
		$(this).parents(".selectBox").find("input.tag_input").val($val);
		
		$(this).parents(".selectBox").find("ul").hide();
	});
	
	$(document).bind('click', function(e) {
		var $clicked = $(e.target);
		if (! $clicked.parents().hasClass("selectBox"))
		$(".selectBox ul").hide();
		
	});
})

//用户确认订单 搜索 创建 切换
$(function(){
	$(".su_con > div").not(":first").hide();
	$(".su_title li").unbind("click").bind("click", function(){
		$(this).addClass("hover").siblings().removeClass("hover");
		var index = $(".su_title li").index( $(this) );
		$(".su_con > div").eq(index).siblings(".su_con > div").hide().end().fadeIn(300);
   });
});

//运营帐号  管理角色  创建
function creationRole(t,b){
	var n = 0;
	$(t).click(function(){
		if(n==0){
			$(b).css("display","none");
			$(this).parent().find("i").addClass("rotate");
			n=1;
		}else if(n==1){
			$(b).css("display","block");
			$(this).parent().find("i").removeClass("rotate");
			n=0;
		}
	});
}
$(function(){
	creationRole(".cr_first span",".cr_first_con");//一级
	creationRole(".cr_second span",".cr_second_con");//二级
	creationRole(".cr_third span",".cr_three");//三级
})

//配货 调货
$(function(){
	$(".ac_area a").click(function(){
		$(this).addClass("hover").siblings().removeClass("hover");
	})
})

