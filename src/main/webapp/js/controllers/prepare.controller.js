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
	$scope.search=function(){
		if ($scope.req.startTime != undefined && $scope.req.endTime!= undefined) {
			var arr = new Array();
			var arr2 = new Array();
			var arr = $scope.req.startTime.split("-");
			var arr2 = $scope.req.endTime.split("-");
			var sDate = new Date(arr[0], arr[1] - 1, arr[2]);
			var eDate = new Date(arr2[0], arr2[1] - 1, arr2[2]);
			if (eDate < sDate) {
				alert("开始日期不能大于结束日期！");
				return;
			}
		}
		$scope.req.indexPage=1;
		$scope.list();
	};
	$scope.sonlist=function(){
		$http.post("api/preparegood/getsonagent", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.son=data.result;
            	$scope.all={id:0,company_name:"全部"};
            	$scope.son.unshift($scope.all);
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
		$scope.req={sonAgentId:0};
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
		$scope.req.sonAgentId=id;
	};
	
	$scope.check=function(){
		if($scope.req.serialNum==undefined){
			alert("请输入终端号!");
			return;
		}
		if($scope.req.sonAgentId==0){
			alert("请选择下级代理商!");
			return;
		}
		if($scope.req.goodId==undefined){
			alert("请选择商品!");
			return;
		}
		if($scope.req.paychannelId==undefined){
			alert("请选择支付通道!");
			return;
		}
		if($scope.req.serialNum.trim().length>11){
			$http.post("api/preparegood/checkTerminals", $scope.req).success(function (data) {  //绑定
	            if (data.code==1) {
	            	if(data.result.errorCount==0){
	            		$scope.add();
	            	}else{
	            		$scope.errorlist=data.result.errorList;
	            		$('.tab').show();
	            	}
	            }
	        });
		}else{
			alert("终端号输入不合法!");
		}
	};
	$scope.add=function(){
		$scope.req.web=1;
		$scope.req.customerId=LoginService.loginid
		$http.post("api/preparegood/add", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	window.location.href="#/prepare"
            }
        });
	};
	$scope.close=function(){
		$('.tab').hide();
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
