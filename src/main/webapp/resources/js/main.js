
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
	popup(".leaseExplain_tab",".leaseExplain_a");//租赁说明
	popup(".leaseAgreement_tab",".leaseAgreement_a");//租赁协议
	popup(".description_tab",".description_a");//详细说明
	popup(".approve_tab",".approve_a");//通过审核
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
			$(i_box).css({'top':($(this).offset().top)-$(this).height()-100+'px', 
			              'left':($(this).offset().left)+$(this).width()+'px'
						});
			//alert($(this).find("img").height()/2)
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