'use strict';

// 主页面路由模块，用于控制主页面的菜单导航(注入了登陆服务LoginService)
var indexModule = angular.module("indexModule", ['loginServiceModule','routeModule', 'ngCookies',  'ngCsv']);

var indexController = function($scope, $location, $http, LoginService,
		$cookieStore) {
	$scope.loginUserName = LoginService.loginUserName;
	$scope.identity=LoginService.identity;//0为一级，1为二级 ,2为普通用户   by yyb
	$scope.agentId=LoginService.agentid;
	$scope.right = true;
	$scope.shop = false;
	$scope.init=function(){
		var identity=$scope.identity;
		//var identity=2;
		if(identity==0){
			//一级代理商权限菜单
			$scope.menu1=true;
			$scope.menu2=true;
			$scope.menu3=true;
			$scope.menu4=true;
			$scope.menu5=true;
			$scope.menu6=true;
			$scope.menu7=true;
			$scope.menu8=true;
			$scope.menu9=true;
			$scope.menu10=true;
			$scope.menu11=true;
			$scope.menu12=true;
		}else if(identity==1){
			//二级代理商权限菜单
			$scope.menu1=true;
			$scope.menu2=true;
			$scope.menu3=false;//代购
			$scope.menu4=false;//批购
			$scope.menu5=true;
			$scope.menu6=true;
			$scope.menu7=true;
			$scope.menu8=true;
			$scope.menu9=true;
			$scope.menu10=true;
			$scope.menu11=true;
			$scope.menu12=true;
		}else if(identity==2){
			//普通用户
			$http.post("api/index/getRoles", $scope.agentId).success(function (data) {  //绑定
	            if (data.code==1) {
	            	var rolesStr=data.result;
	            	var roles=rolesStr.split(",");
	            	$scope.menu1=false;
	    			$scope.menu2=false;
	    			$scope.menu3=false;
	    			$scope.menu4=false;
	    			$scope.menu5=false;
	    			$scope.menu6=false;
	    			$scope.menu7=false;
	    			$scope.menu8=false;
	    			$scope.menu9=false;
	    			$scope.menu10=false;
	    			$scope.menu11=false;
	    			$scope.menu12=false;
	            	for(var i=0;i<roles.length;i++){
	            		if(roles[i]=="1"){
	            			$scope.menu1=true;
	            		}else if(roles[i]=="2"){
	            			$scope.menu2=true;
	            		}else if(roles[i]=="3"){
	            			$scope.menu3=true;
	            		}else if(roles[i]=="4"){
	            			$scope.menu4=true;
	            		}else if(roles[i]=="5"){
	            			$scope.menu5=true;
	            		}else if(roles[i]=="6"){
	            			$scope.menu6=true;
	            		}else if(roles[i]=="7"){
	            			$scope.menu7=true;
	            		}else if(roles[i]=="8"){
	            			$scope.menu8=true;
	            		}else if(roles[i]=="9"){
	            			$scope.menu9=true;
	            		}else if(roles[i]=="10"){
	            			$scope.menu10=true;
	            		}else if(roles[i]=="11"){
	            			$scope.menu11=true;
	            		}else if(roles[i]=="12"){
	            			$scope.menu12=true;
	            		}
	            	}
	            }
	        });
		}
	}
	$scope.init();
	$scope.$on('$locationChangeStart', function(scope, next, current) {

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
		var arry = [ "shop", "shopinfo", "purchaseShop", "purchaseShopinfo",'login','register','findpass','findpassEmail',
		             "shopmakeorder","leasemakeorder","purchasemakeorder", "pay", "lowstocks" ];
		for (var i = 0; i < arry.length; i++) {
			if (str == arry[i]) {
				return true;
			}
		}
		return false;
	}
	
	var checkLogin = function(str) {
		// alert(str + "====index.controller");
		var arry = [ "login" ];
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
