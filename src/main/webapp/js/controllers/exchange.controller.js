'user strict';

//系统设置模块
var exchangeModule = angular.module("exchangeModule",[]);

var exchangelistController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		initSystemPage($scope.req);// 初始化分页参数
		$scope.req.agentId=LoginService.agentid;
		$scope.sonlist();
		$scope.list();
	};
	$scope.list=function(){
		$scope.req.page=$scope.req.indexPage;
		$http.post("api/exchangegood/list", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.exchangeList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            }
        });
	};
	$scope.search=function(){
		$scope.req.indexPage=1;
		$scope.list();
	};
	$scope.sonlist=function(){
		$http.post("api/preparegood/getsonagent", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.son=data.result;
            }
        });
	};
	
	$scope.init();
	
	// 上一页
   	$scope.prev = function() {
   		if ($scope.req.indexPage > 1) {
   			$scope.req.indexPage--;
   			$scope.list();
   		}
   	};

   	// 当前页
   	$scope.loadPage = function(currentPage) {
   		$scope.req.indexPage = currentPage;
   		$scope.list();
   	};

   	// 下一页
   	$scope.next = function() {
   		if ($scope.req.indexPage < $scope.req.totalPage) {
   			$scope.req.indexPage++;
   			$scope.list();
   		}
   	};

   	// 跳转到XX页
   	$scope.getPage = function() {
   		$scope.req.indexPage = Math.ceil($scope.req.gotoPage);
   		$scope.list();
   	};
};

var exchangeaddController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.agentId=LoginService.agentid;
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
