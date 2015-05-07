'use strict';

// 主页面路由模块，用于控制主页面的菜单导航(注入了登陆服务LoginService)
var indexModule = angular.module("indexModule", ['loginServiceModule','routeModule', 'ngCookies',  'ngCsv']);

var indexController = function($scope, $location, $http, LoginService,
		$cookieStore) {
	$scope.loginAgentName = LoginService.loginAgentName;
	$scope.identity=LoginService.identity;//0为一级，1为二级 ,2为普通用户   by yyb
	$scope.agentId=LoginService.agentid;
	$scope.right = false;
	$scope.shop = true;
	$scope.init=function(){
		var identity=$scope.identity;
		//var identity=2;
		$scope.menu1=true;
		$scope.menu2=true;
		$scope.menu3=true;
		$scope.menu4=true;
		$scope.menu5=true;
		$scope.menu6=true;
		$scope.menu7=true;
		$scope.menu8=true;
		$scope.menu9=true;
		if(identity==1){
			//一级代理商权限菜单
		}else if(identity==2){
			//二级代理商权限菜单
			$scope.menu1=false;//代购
			$scope.menu2=false;//批购
		}else if(identity==3){
			$scope.menu1=false;
			$scope.menu2=false;
			$scope.menu3=false;
			$scope.menu4=false;
			$scope.menu5=false;
			$scope.menu6=false;
			$scope.menu7=false;
			$scope.menu8=false;
			$scope.menu9=false;
			$scope.machtigingen=LoginService.machtigingen;
			if($scope.machtigingen.length>0 && $scope.machtigingen != ""){
				for(var i=0;i<$scope.machtigingen.length;i++){
            		if($scope.machtigingen[i].role_id=="1"){
            			$scope.menu1=true;
            		}else if($scope.machtigingen[i].role_id=="2"){
            			$scope.menu2=true;
            		}else if($scope.machtigingen[i].role_id=="3"){
            			$scope.menu3=true;
            		}else if($scope.machtigingen[i].role_id=="4"){
            			$scope.menu4=true;
            		}else if($scope.machtigingen[i].role_id=="5"){
            			$scope.menu5=true;
            		}else if($scope.machtigingen[i].role_id=="6"){
            			$scope.menu6=true;
            		}else if($scope.machtigingen[i].role_id=="7"){
            			$scope.menu7=true;
            		}else if($scope.machtigingen[i].role_id=="8"){
            			$scope.menu8=true;
            		}else if($scope.machtigingen[i].role_id=="9"){
            			$scope.menu9=true;
            		}
            	}
			}
			
			//普通用户
				/**
				 * {"code":1,"message":"处理成功","result":
				 * {"phone":"12347829384","cityId":383,"status":2,
				 * "machtigingen":[{"role_id":1},{"role_id":2},{"role_id":3},{"role_id":4},{"role_id":5},{"role_id":6},{"role_id":7}],
				 * "agentUserId":1,"agentId":1,"integral":0,"id":1,"updatedAt":"2015-3-16","username":"admin","lastLoginedAt":"2015-4-03",
				 * "email":"1242@qq.com","agentCityId":383,"createdAt":"2015-2-03","name":"zhangsan","accountType":0,
				 * "is_have_profit":true,"types":2,"parent_id":0}}
				 * 
				 * 
				 * 
				 */
		}
	}
	
	$scope.$on('$locationChangeStart', function(scope, next, current) {
		$("#yyy").show();
		var strs = new Array(); // 定义一数组
		strs = next.split("/#/"); // 字符分割

		if (strs.length == 2) {
			strs = strs[1].split("?");
			    if(checkLogin(strs[0])){
			    	$scope.right = false;
					$scope.shop = true;
			    }else{
			    	if(LoginService.loginid>0&&LoginService.agentid>0){
			    		if (check(strs[0])) {
							// alert("check(strs[0]) == " + check(strs[0]));
							$scope.right = false;
							$scope.shop = true;
						} else {
							$scope.right = true;
							$scope.shop = false;
						}
					}else{
						window.location.href="#/login";
					}
			    }
			} else {
				if(LoginService.loginid>0&&LoginService.agentid>0){
					$scope.right = true;
					$scope.shop = false;
				}else{
					window.location.href="#/login";
				}
				
			}
    });
	
	var check = function(str) {
		// alert(str + "====index.controller");
		var arry = [ "app","shop", "shopinfo", "purchaseShop", "purchaseShopinfo",'login','register','findpass','findpassEmail',
		             "shopmakeorder","leasemakeorder","purchasemakeorder", "pay", "lowstocks","deposit_pay","order_pay","topay" ];
		for (var i = 0; i < arry.length; i++) {
			if (str == arry[i]) {
				return true;
			}
		}
		return false;
	}
	
	var checkLogin = function(str) {
		// alert(str + "====index.controller");
		var arry = [ "app", "login",'register','findpass','findpassEmail' ];
		for (var i = 0; i < arry.length; i++) {
			if (str == arry[i]) {
				return true;
			}
		}
		return false;
	}

	$scope.index = function() {
		window.location.href = '#/';
	};
	
	$scope.loginout = function(){
		LoginService.logout();
	}
	$scope.init();

};


indexModule.directive('onFinishRenderFilters', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function() {
                	$(".su_s_box a").click(function(){
                		if( !$(this).hasClass("hover")){
                			$(this).addClass("hover").siblings().removeClass("hover");
                		} else {
                			$(this).removeClass("hover");
                		}
                	});
                });
            }
        }
    };
});

indexModule.directive('onFinishRender2Filters', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function() {
                	$("#test1 a").click(function(){
                		if( !$(this).hasClass("hover")){
                			$(this).addClass("hover").siblings().removeClass("hover");
                		} else {
                			$(this).removeClass("hover");
                		}
                	});
                });
            }
        }
    };
});

indexModule.directive('onFinishRender3Filters', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function() {
                	$("#test2 a").click(function(){
                		if( !$(this).hasClass("hover")){
                			$(this).addClass("hover").siblings().removeClass("hover");
                		} else {
                			$(this).removeClass("hover");
                		}
                	});
                });
            }
        }
    };
});


indexModule.controller("indexController", indexController);
