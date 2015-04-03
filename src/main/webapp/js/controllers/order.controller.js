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
wholesaleOrderModule.$inject = ['$scope', '$http', '$cookieStore'];
proxyOrderModule.$inject = ['$scope', '$http', '$cookieStore'];
wholesaleOrderModule.controller("wholesaleOrderController", wholesaleOrderController); //批购
proxyOrderModule.controller("proxyOrderController", proxyOrderController); //代购
