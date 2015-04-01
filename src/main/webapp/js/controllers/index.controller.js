'use strict';

// 主页面路由模块，用于控制主页面的菜单导航(注入了登陆服务LoginService)
var indexModule = angular.module("indexModule", ['loginServiceModule','routeModule', 'ngCookies',  'ngCsv']);

var indexController = function($scope, $location, $http, LoginService,
		$cookieStore) {
	$scope.loginUserName = LoginService.loginUserName;
	$scope.right = true;
	$scope.shop = false;
	$scope.$on('$locationChangeStart', function(scope, next, current) {

		var strs = new Array(); // 定义一数组
		strs = next.split("/#/"); // 字符分割

		if (strs.length == 2) {
			strs = strs[1].split("?");
				if (check(strs[0])) {
					// alert("check(strs[0]) == " + check(strs[0]));
					$scope.right = false;
					$scope.shop = true;
				} else {
					$scope.right = true;
					$scope.shop = false;
				}
			} else {
				$scope.right = true;
				$scope.shop = false;
			}
    });
	
	var check = function(str) {
		// alert(str + "====index.controller");
		var arry = [ "shop", "shopinfo", "purchaseShop", "purchaseShopinfo",'login','register','findpass','findpassEmail',
		             "shopmakeorder","leasemakeorder","purchasemakeorder", "pay", "lowstocks" ];
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

};

indexModule.controller("indexController", indexController);
