'user strict';

//系统设置模块
var prepareModule = angular.module("prepareModule",[]);

var preparelistController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.agents_id=LoginService.agentid;
		$scope.sonlist();
		$scope.list();
	};
	$scope.list=function(){
		$http.post("api/preparegood/list", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.prepareList=data.result.list;
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

var prepareaddController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.agents_id=LoginService.agentid;
		$scope.sonlist();
		$scope.list();
	};
	
	$scope.init();
};

var prepareinfoController = function ($scope, $http,$location, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.id=$location.search()['id'];
		$scope.info();
	};
	$scope.info=function(){
		$http.post("api/preparegood/info", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.info=data.result;
            }
        });
	};
	$scope.init();
};


preparelistController.$inject = ['$scope','$http','LoginService'];
prepareModule.controller("preparelistController", preparelistController);
prepareaddController.$inject = ['$scope','$http','LoginService'];
prepareModule.controller("prepareaddController", prepareaddController);
prepareinfoController.$inject = ['$scope','$http','$location','LoginService'];
prepareModule.controller("prepareinfoController", prepareinfoController);
