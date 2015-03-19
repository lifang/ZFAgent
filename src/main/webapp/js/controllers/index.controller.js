'use strict';

// 主页面路由模块，用于控制主页面的菜单导航(注入了登陆服务LoginService)
//var indexModule = angular.module("indexModule", [ 'loginServiceModule', 'loginrouteModule', 'ngRoute' ]);

var indexController = function($scope, $location, $http, LoginService,$cookieStore) {
	$scope.loginUserName=LoginService.loginUserName;

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
	
	$scope.index=function() {
		window.location.href = '#/';
	};
	
};



indexModule.controller("indexController", indexController);

