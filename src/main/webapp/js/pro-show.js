// JavaScript Document
//产品图片左右点击移动
$(function(){
	var page = 1;
	var i = 5;	
	$('div.next').click(function(){
		//alert(0)
		var $parent = $(this).parents('div.picBox');
		var $pic_show = $parent.find('.pic_show')
		var $smallImg = $parent.find('.smallImg');
		var small_width = $smallImg.width();
		var len = $pic_show.find('li').length;
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
	
	
	$('div.prev').click(function(){
		//alert(0)
		var $parent = $(this).parents('div.picBox');
		var $pic_show = $parent.find('.pic_show')
		var $smallImg = $parent.find('.smallImg');
		var small_width = $smallImg.width();
		var len = $pic_show.find('li').length;
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


//switchImg产品小图点击替换大图效果
//产品图片命名规则，最小图为mt、mt01、mt02等
//大的展示图为mt_big、mt01_big、mt02_big，即在小图的后面加上_big
//放大的图片为mt_show、mt01_show、mt02_show,即在小图后面加上_show
$(function(){
	$('.smallImg > ul > li img').click(function(){
		var imgSrc = $(this).attr('src');
		var i = imgSrc.lastIndexOf('.');
		var jpg = imgSrc.substring(i);
		imgSrc = imgSrc.substring(0,i);
		var imgSrc_big = imgSrc + '_big' + jpg;
		var imgSrc_show = imgSrc + '_show' + jpg;
		$('.bigImg img').attr('src',imgSrc_big);
		$('.bigImg img').attr('jqimg',imgSrc_show);
		//alert(0);
	})	
})



//点击选择当前小图
$(function(){
	$(".smallImg li").click(function(){
		$(this).addClass("hover").siblings().removeClass("hover");
	});	
})