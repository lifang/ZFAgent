'user strict';

//系统设置模块
var exchangeModule = angular.module("exchangeModule",[]);

var exchangelistController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.agents_id=LoginService.agentid;
		$scope.sonlist();
		$scope.list();
	};
	$scope.list=function(){
		$http.post("api/exchangegood/list", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.exchangeList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            }
        });
	};
	$scope.sonlist=function(){
		$http.post("api/preparegood/getsonagent", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.son=data.result.list;
            }
        });
	};
	
	$scope.init();
};

var exchangeaddController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.agents_id=LoginService.agentid;
	};
	
	$scope.init();
};

var exchangeinfoController = function ($scope, $http,$location, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.id=$location.search()['id'];
		$scope.info();
	};
	$scope.info=function(){
		$http.post("api/exchangegood/info", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.info=data.result;
            }
        });
	};
	$scope.init();
};


exchangelistController.$inject = ['$scope','$http','LoginService'];
exchangeModule.controller("exchangelistController", exchangelistController);
exchangeaddController.$inject = ['$scope','$http','LoginService'];
exchangeModule.controller("exchangeaddController", exchangeaddController);
exchangeinfoController.$inject = ['$scope','$http','$location','LoginService'];
exchangeModule.controller("exchangeinfoController", exchangeinfoController);
