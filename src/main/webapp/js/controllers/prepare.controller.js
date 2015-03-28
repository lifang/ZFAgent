'user strict';

//系统设置模块
var prepareModule = angular.module("prepareModule",[]);

var preparelistController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		initSystemPage($scope.req);// 初始化分页参数
		$scope.req.agentId=LoginService.agentid;
		$scope.sonlist();
		$scope.list();
	};
	$scope.list=function(){
		$scope.req.page=$scope.req.indexPage;
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

var prepareaddController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={son_agents_id:0};
		$scope.req.agentId=LoginService.agentid;
		$scope.sonlist();
		$scope.getglist();
		$scope.getpclist();
	};
	$scope.sonlist=function(){
		$http.post("api/preparegood/getsonagent", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.son=data.result;
            }
        });
	};
	$scope.getglist=function(){
		$http.post("api/preparegood/getgoodlist", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.goodlist=data.result;
            }
        });
	};
	$scope.getpclist=function(){
		$http.post("api/preparegood/getpaychannellist", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.pclist=data.result;
            }
        });
	};
	$scope.checkson=function(id){
		$(this).addClass("hover").siblings().removeClass("hover");
		$scope.req.son_agents_id=id;
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
