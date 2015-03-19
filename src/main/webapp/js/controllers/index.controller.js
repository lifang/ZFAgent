'use strict';

// 主页面路由模块，用于控制主页面的菜单导航(注入了登陆服务LoginService)
//var indexModule = angular.module("indexModule", [ 'loginServiceModule', 'loginrouteModule', 'ngRoute' ]);

var indexController = function($scope, $location, $http, LoginService,$cookieStore) {
	$scope.loginUserName=LoginService.loginUserName;
	$scope.city_name = $cookieStore.get("city_name")==null?"上海市":$cookieStore.get("city_name");
	$scope.ngshow=true;
	$scope.ngshow2=false;
	$scope.shopcount=0;
	$scope.shopcartcount=function () {
    	if(LoginService.userid>0){
    		$http.post("api/cart/total", {customerId:LoginService.userid}).success(function (data) {  //绑定
                if (data.code==1) {
                	$scope.shopcount= data.result;
                }
            });
    	}
    };
    $scope.$on('$locationChangeStart', function (scope, next, current) {                          		
		//alert(strs[0]);
		if(LoginService.userid == 0){
			$scope.loginshow=false;
			$scope.ngshow=true;
			$scope.ngshow2=false;
		}else{
			$scope.loginshow=true;
			var strs= new Array(); //定义一数组
			strs=next.split("/#/"); //字符分割
			if(strs.length==2){
				strs=strs[1].split("?")
				if(check(strs[0])){
					$scope.ngshow=false;
					$scope.ngshow2=true;
				}else{
					$scope.ngshow=true;
					$scope.ngshow2=false;
				}
			}else{
				$scope.ngshow=true;
				$scope.ngshow2=false;
			}
		}
		//$scope.searchview=true;
    });
	
	var check=function(str){
		var arry=[];
		for (var i = 0; i < arry.length; i++) {
			if(str==arry[i]){
				return true;
			}
		}
		return false;
	}
	
	
	//退出页面(清除$cookieStore)
	$scope.escLogin = function(){
		$cookieStore.put("loginUserName",null);
    	$cookieStore.put("loginUserId",0);
    	$scope.password1 = "";
    	$scope.code = "";
    	location.reload();
    	window.location.href = '#/';
    	
	}
	
	
	$scope.shopcount=0;
	$scope.$on('shopcartcountchange', function() {
		$scope.shopcartcount();
	});
	$scope.shopcartcount=function () {
    	if(LoginService.userid>0){
    		$http.post("api/cart/total", {customerId:LoginService.userid}).success(function (data) {  //绑定
                if (data.code==1) {
                	$scope.shopcount= data.result;
                }
            });
    	}
    };
    $scope.shopcartcount();
    
    
    
//	$scope.$on('changeshow', function(d,data) {
//		$scope.ngshow=data;
//	});
	$scope.$on('changesearchview', function(d,data) {
		$scope.searchview=data;
	});
	
	$scope.index=function() {
		window.location.href = '#/';
	};
	
};



indexModule.controller("indexController", indexController);

