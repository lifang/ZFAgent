'user strict';

//批购
var wholesaleOrderinfoModule = angular.module("wholesaleOrderinfoModule",[]);

var wholesaleOrderinfoController = function ($scope,$location, $http, LoginService) {
//	$("#leftRoute").show();
	if(LoginService.userid == 0){
		window.location.href = '#/login';
	}else{
		//显示用户登录部分
		$scope.$emit('changeshow',false);
	}
	$scope.req={};
	$scope.req.id=$location.search()['orderId'];
    $scope.getOrderInfo = function () {
    	$http.post("api/order/getWholesaleById", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.orderInfo=data.result;
            }else {
         		window.location.href = '#/';
         	}
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
    };
    //取消
    $scope.cancelApply = function(id){
    	$scope.req={id:id};
		$http.post("api/order/cancelWholesale", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
            	  $scope.getOrderInfo();
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
    
	//显示支付
	$scope.showPay = function(id){
//		$scope.req={id:id};
    	$("#o_id").val(id);
//		popup(".pay_tab",".pay_a");//代理商批购
		var doc_height = $(document).height();
		var doc_width = $(document).width();
		var win_height = $(window).height();
		var win_width = $(window).width();
		
		var layer_height = $(".pay_tab").height();
		var layer_width = $(".pay_tab").width();
		
		var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
		 
	    $(".mask").css({display:'block',height:doc_height});
		$(".pay_tab").css('top',(win_height-layer_height)/2);
		$(".pay_tab").css('left',(win_width-layer_width)/2);
		$(".pay_tab").css('display','block');
		
	};

    
    $scope.close = function(){
    	$(".pay_tab").css('display','none');
		$(".mask").css('display','none');
    }
    //支付 金额
    $scope.infoorderpay = function() {
		   console.log(">>>>>>>>>>>"+pay_price);
	    	$("#zf_yz").hide();
	   		var pay_price = $("#pay_price").val();
	   		if(isNaN(pay_price)){
	   			alert("支付金额必须是数字");
	   			return false;
	   		}
	    	var o_id = $("#o_id").val();
	    	var sy_price = $("#shenyu_price_").val();
	    	if(pay_price<0 || pay_price==''){
	    		alert("请输入金额");
//	    		$("#zf_yz").show();
	    		return false;
	    	}else if( sy_price  < pay_price ){
	    		console.log(">>>>>>muqian "+pay_price+">>>最多"+sy_price);
	        		alert("最多只需支付"+sy_price);
	        		return false;
	    	}else if(parseInt(pay_price) > parseInt(sy_price)){
	    		alert("最多只需支付"+sy_price);
	    		return false;
	    	}else{
	    		$scope.close();
	    	 	window.open("#/order_pay?id="+o_id+"&p="+pay_price) ;  
	    	}
//	    	window.open("alipayapi.jsp?WIDtotal_fee="+o.order_totalPrice/100+"&WIDsubject="+g_name+"&WIDout_trade_no="+o.order_number);  
		};
		
    $scope.orderpay = function(o) {
    	$("#zf_yz").hide();
   		var pay_price = $("#pay_price").val();
    	var o_id = $("#o_id").val();
    	var sy_price = $("#shenyu_price").val();
    	if(pay_price<0 || pay_price==''){
    		alert("请输入金额");
//    		$("#zf_yz").show();
    		return false;
    	}else if(parseInt(pay_price) > parseInt(sy_price)){
        		alert("最多只需支付"+sy_price);
        		return false;
    	}else{
    		$scope.close();
    	 	window.open("#/order_pay?id="+o_id+"&p="+pay_price) ;  
    	}
//    	window.open("alipayapi.jsp?WIDtotal_fee="+o.order_totalPrice/100+"&WIDsubject="+g_name+"&WIDout_trade_no="+o.order_number);  
	};
	
	//定金支付
	 $scope.depositpay = function(o) {
		 var dj = o.price_dingjin;
	    window.open("#/deposit_pay?id="+o.order_id) ;  
	};
	
	$scope.close_wlxx = function() {
		$("#od_ter_div").css('display', 'none');
	};
	 $scope.t_comment = function (g) {
	    	$("#order_g_t_v").html(g.terminals);
		    $("#od_ter_div").css('display','block');
	    };
    $scope.getOrderInfo();

};

//代购
var proxyOrderinfoModule = angular.module("proxyOrderinfoModule",[]);

var proxyOrderinfoController = function ($scope,$location, $http, LoginService) {
//	$("#leftRoute").show();
	if(LoginService.userid == 0){
		window.location.href = '#/login';
	}else{
		//显示用户登录部分
		$scope.$emit('changeshow',false);
	}
	$scope.req={};
	$scope.req.id=$location.search()['orderId'];
	$scope.getOrderInfo = function () {
		$http.post("api/order/getProxyById", $scope.req).success(function (data) {  //绑定
			if (data.code==1) {
				$scope.o=data.result;
			 }else {
	         		window.location.href = '#/';
	         	}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
	//取消
	$scope.cancelApply = function(id){
		$scope.req={id:id};
		$http.post("api/order/cancelProxy", $scope.req).success(function (data) {  //绑定
			if (data != null && data != undefined) {
				$scope.getOrderInfo();
			}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
	
    $scope.topay = function(o) {
//    	var g_name = $("#g_name").val();
    	window.open("#/topay?id="+o.order_id) ;  
//    	window.open("alipayapi.jsp?WIDtotal_fee="+o.order_totalPrice/100+"&WIDsubject="+g_name+"&WIDout_trade_no="+o.order_number);  
	};
	
	$scope.close_wlxx = function() {
		$("#od_ter_div").css('display', 'none');
	};
	 $scope.t_comment = function (g) {
	    	$("#order_g_t_v").html(g.terminals);
		    $("#od_ter_div").css('display','block');
	    };
	$scope.getOrderInfo();
	
};

wholesaleOrderinfoModule.controller("wholesaleOrderinfoController", wholesaleOrderinfoController);
proxyOrderinfoModule.controller("proxyOrderinfoController", proxyOrderinfoController);
