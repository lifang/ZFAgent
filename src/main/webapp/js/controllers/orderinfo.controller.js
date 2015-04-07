'user strict';

//代购
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
            	$scope.o=data.result;
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
    
    $scope.topay = function(o) {
    	window.open("#/pay?id="+o.order_id) ; 
//    	var g_name = $("#g_name").val();
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
				$scope.orderInfo=data.result;
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
		window.open("#/pay?id="+o.order_id) ; 
//    	var g_name = $("#g_name").val();
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
