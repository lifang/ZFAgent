'user strict';

//系统设置模块
var stockModule = angular.module("stockModule",[]);

var stockController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		initSystemPage($scope.req);// 初始化分页参数
		$scope.req.agentId=LoginService.agentid;
		$scope.list();
	};
	$scope.list=function(){
		$scope.req.page=$scope.req.indexPage;
		$http.post("api/stock/list", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.stockList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
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


var stockinfoController = function ($scope, $http,$location, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.goodId=$location.search()['goodid'];
		$scope.req.paychannelId=$location.search()['pcid'];
		$scope.req.agentId=LoginService.agentid;
		$scope.info();
	};
	$scope.info=function(){
		$http.post("api/stock/info", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.info=data.result;
            }
        });
	};
	$scope.init();
};


stockController.$inject = ['$scope','$http','LoginService'];
stockModule.controller("stockController", stockController);

stockinfoController.$inject = ['$scope','$http','$location','LoginService'];
stockModule.controller("stockinfoController", stockinfoController);
