'user strict';

//批购
var wholesaleOrderModule = angular.module("wholesaleOrderModule",[]);

var wholesaleOrderController = function ($scope, $http, LoginService) {
//	$("#leftRoute").show();
//	if(LoginService.agentUserId == 0){
//		window.location.href = '#/login';
//	}else{
//		//显示用户登录部分
//		$scope.$emit('changeshow',false);
//	}
	initSystemPage($scope);// 初始化分页参数
	// 搜索
	$scope.submitSearch = function(){
//		initSystemPage($scope);// 初始化分页参数
		$scope.req = {
			customerId : LoginService.agentUserId,
			search : $scope.search,
			q : $scope.screen,
			p : "5",//  5 批购 3,4代购
			page : $scope.indexPage,
			rows : $scope.rows
		};
	
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  // 绑定
            if (data != null && data != undefined) {
                $scope.list = data.result;
                calcSystemPage($scope, data.result.total);// 计算分页
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	//筛选
	$scope.submitScreen = function(){
		initSystemPage($scope);// 初始化分页参数
		$scope.req = {
			customerId : LoginService.agentUserId,
			search : $scope.search,
			q : $scope.screen,
			p : "5",//   
			page : $scope.indexPage,
			rows : $scope.rows
		};
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result;
                calcSystemPage($scope, data.result.total);// 计算分页
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	
	$scope.submitPage = function(){
		$scope.req={customerId:LoginService.agentUserId,search:$scope.search,q:$scope.screen,page:$scope.indexPage,p:"5",
				rows:$scope.rows};
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  //绑定
			if (data != null && data != undefined) {
				$scope.list = data.result;
				calcSystemPage($scope, data.result.total);// 计算分页
			}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
	 
	//订单列表
	$scope.orderlist = function () {
		initSystemPage($scope);// 初始化分页参数
        $scope.req={customerId:LoginService.agentUserId,
        		page:$scope.indexPage,
        		rows:$scope.rows};
        $http.post("api/order/getWholesaleOrder", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result;
                calcSystemPage($scope, data.result.total);// 计算分页
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
            	$scope.submitPage();
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
    //详情
    $scope.orderinfo=function (p) {
    	LoginService.poscd=p.id;
    	$scope.poscd=p.id;
    	window.location.href = '#/orderinfo';
    };
    
    $scope.close = function(){
    	$(".pay_tab").css('display','none');
		$(".mask").css('display','none');
    }
    //支付 金额
    $scope.orderpay = function(o) {
    	$scope.close();
    	var o_id = $("#o_id").val();
    	var pay_price = $("#pay_price").val();
    	window.open("#/order_pay?id="+o_id+"&p="+pay_price) ;  
//    	window.open("alipayapi.jsp?WIDtotal_fee="+o.order_totalPrice/100+"&WIDsubject="+g_name+"&WIDout_trade_no="+o.order_number);  
	};
	
	//定金支付
	 $scope.depositpay = function(o) {
	    	window.open("#/deposit_pay?id="+o.order_id) ;  
		};
	
	// 上一页
	$scope.prev = function() {
		if ($scope.indexPage > 1) {
			$scope.indexPage--;
			$scope.submitPage();
		}
	};

	// 当前页
	$scope.loadPage = function(currentPage) {
		$scope.indexPage = currentPage;
		$scope.submitPage();
	};

	// 下一页
	$scope.next = function() {
		if ($scope.indexPage < $scope.totalPage) {
			$scope.indexPage++;
			$scope.submitPage();
		}
	};

	// 跳转到XX页
	$scope.getPage = function() {
		$scope.indexPage = Math.ceil($scope.gotoPage);
		$scope.submitPage();
	};

    $scope.submitPage();
//    $scope.submitSearch();
//    $scope.orderlist();
};

//代购
var proxyOrderModule = angular.module("proxyOrderModule",[]);

var proxyOrderController = function ($scope, $http, LoginService) {
	$("#leftRoute").show();
	if(LoginService.agentUserId == 0){
		window.location.href = '#/login';
	}else{
		//显示用户登录部分
		$scope.$emit('changeshow',false);
	}
	initSystemPage($scope);// 初始化分页参数
	// 搜索
	$scope.submitSearch = function(){
		initSystemPage($scope);// 初始化分页参数
		$scope.req = {
			customerId : LoginService.agentUserId,
			search : $scope.search,
			q : $scope.screen,
			p : "",//  默认 代购
			page : $scope.indexPage,
			rows : $scope.rows
		};
	
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  // 绑定
            if (data != null && data != undefined) {
                $scope.list = data.result;
                calcSystemPage($scope, data.result.total);// 计算分页
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	//筛选
	$scope.submitScreen = function(){
//		initSystemPage($scope);// 初始化分页参数
		$scope.req = {
			customerId : LoginService.agentUserId,
			search : $scope.search,
			q : $scope.screen,
			p : "",//   
			page : $scope.indexPage,
			rows : $scope.rows
		};
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result;
                calcSystemPage($scope, data.result.total);// 计算分页
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	
	$scope.submitPage = function(){
		$scope.req={customerId:LoginService.agentUserId,search:$scope.search,q:$scope.screen,page:$scope.indexPage,p:"",
				rows:$scope.rows};
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  //绑定
			if (data != null && data != undefined) {
				$scope.list = data.result;
				calcSystemPage($scope, data.result.total);// 计算分页
			}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
	 
	//订单列表
	$scope.orderlist = function () {
		initSystemPage($scope);// 初始化分页参数
        $scope.req={customerId:LoginService.agentUserId,
        		page:$scope.indexPage,
        		rows:$scope.rows};
        $http.post("api/order/getProxyOrder", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result;
                calcSystemPage($scope, data.result.total);// 计算分页
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
            	$scope.submitPage();
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
    //详情
    $scope.orderinfo=function (p) {
    	LoginService.poscd=p.id;
    	$scope.poscd=p.id;
    	window.location.href = '#/orderinfo';
    };
    $scope.topay = function(o) {
//    	var g_name = $("#g_name").val();
    	window.open("#/pay?id="+o.order_id) ;  
//    	window.open("alipayapi.jsp?WIDtotal_fee="+o.order_totalPrice/100+"&WIDsubject="+g_name+"&WIDout_trade_no="+o.order_number);  
	};
	
	// 上一页
	$scope.prev = function() {
		if ($scope.indexPage > 1) {
			$scope.indexPage--;
			$scope.submitPage();
		}
	};

	// 当前页
	$scope.loadPage = function(currentPage) {
		$scope.indexPage = currentPage;
		$scope.submitPage();
	};

	// 下一页
	$scope.next = function() {
		if ($scope.indexPage < $scope.totalPage) {
			$scope.indexPage++;
			$scope.submitPage();
		}
	};

	// 跳转到XX页
	$scope.getPage = function() {
		$scope.indexPage = Math.ceil($scope.gotoPage);
		$scope.submitPage();
	};

    $scope.submitPage();
//    $scope.submitSearch();
//    $scope.orderinfo();
};
var orderpayModule = angular.module("orderpayModule",[]);
var orderpayController = function($scope, $http,$location,LoginService) {
	$scope.pay=true;
	$scope.req={};
	$scope.order={};
	$scope.payway=1;
	$scope.req.id=$location.search()['id'];
	var price =$location.search()['p'];
	$scope.getOrder = function() {
		$http.post("api/shop/payOrder", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.order=data.result;
            	$scope.p=price;
            	if(data.result.paytype>0){
            		$scope.pay=false;
            		$scope.payway=data.result.paytype;
            	}
            	
            }
        });
	};
	$scope.depositpay= function(){
		$('#payTab').show();
		if(1==$scope.payway){
			//alert("支付宝");
			$scope.order.title="";
        	var count=0;
        	 angular.forEach($scope.order.good, function (one) {
                 if(count<2){
                	 $scope.order.title+=one.title+" "+one.pcname+"("+one.quantity+"件)";
                 }
                 count++;
             });
        	 if(count>2){
        		 $scope.order.title+="..";
        	 }
			window.open("depositalipayapi.jsp?WIDtotal_fee="+
					$scope.order.front_money/100+"&WIDsubject="+$scope.order.title
					+"&WIDout_trade_no="+$scope.order.order_number);  
		}else{
			//alert("银行");
			window.open("http://www.taobao.com");  
		}
	}
	$scope.orderpay= function(){
		$('#payTab').show();
		if(1==$scope.payway){
			//alert("支付宝");
			$scope.order.title="";
			var count=0;
			angular.forEach($scope.order.good, function (one) {
				if(count<2){
					$scope.order.title+=one.title+" "+one.pcname+"("+one.quantity+"件)";
				}
				count++;
			});
			if(count>2){
				$scope.order.title+="..";
			}
			window.open("depositalipayapi.jsp?WIDtotal_fee="+
					$scope.p+"&WIDsubject="+$scope.order.title
					+"&WIDout_trade_no="+$scope.order.order_number);  
		}else{
			//alert("银行");
			window.open("http://www.taobao.com");  
		}
	}
	$scope.finish= function(){
		$http.post("api/shop/payOrder", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.order=data.result;
            	if(data.result.paytype>0){
            		$scope.pay=false;
            		$scope.payway=data.result.paytype;
            		$('#payTab').hide();
            		$('.mask').hide();
            	}else{
            		alert("尚未支付,如有疑问请联系888-88888");
            	}
            	
            }
        });
	};
	$scope.getOrder();
};


wholesaleOrderModule.$inject = ['$scope', '$http', '$cookieStore'];
proxyOrderModule.$inject = ['$scope', '$http', '$cookieStore'];
wholesaleOrderModule.controller("wholesaleOrderController", wholesaleOrderController); //批购
proxyOrderModule.controller("proxyOrderController", proxyOrderController); //代购
orderpayController.$inject = ['$scope','$http','$location','LoginService'];
orderpayModule.controller("orderpayController", orderpayController);