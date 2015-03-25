'user strict';

//系统设置模块
var tradeModule = angular.module("tradeModule",[]);

var tradelistController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		initSystemPage($scope.req);// 初始化分页参数
		$scope.req.agentId=LoginService.agentid;
		if(LoginService.tradeTypeId==0){
			$scope.req.tradeTypeId=1;
		}else{
			$scope.req.tradeTypeId=LoginService.tradeTypeId;
		}
		$scope.req.is_have_profit=LoginService.is_have_profit;
		$scope.getTradeType();
		$scope.list();
	};
	$scope.list=function(){
		$scope.req.page=$scope.req.indexPage;
		$http.post("api/trade/getTradeRecords", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.tradeList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            	LoginService.trade=$scope.req;
            }
        });
	};
	$scope.getTradeType = function() {
		$http.post("api/trade/getTradeType").success(function(data) {
			if (data.code == 1) {
				$scope.tradeType=data.result;
				if(LoginService.tradeTypeId==0){
					$scope.req.typeName=$scope.tradeType[0].value;
				}else{
					$scope.req.typeName=$scope.tradeType[LoginService.tradeTypeId-1].value;
				}
			} else {
				// 提示错误信息
				alert(data.message);
			}
		});
	};
	$scope.changeType = function(one) {
		$scope.req={startTime:"",endTime:"",terminalNumber:""};
		$scope.req.agentId=LoginService.agentid;
		$scope.req.tradeTypeId=one.id;
		LoginService.tradeTypeId=one.id;
		$scope.req.is_have_profit=LoginService.is_have_profit;
		initSystemPage($scope.req);// 初始化分页参数
		$scope.req.typeName=one.value;
		$scope.list();
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

var statisticsController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req=LoginService.trade;
		if($scope.req==undefined){
			window.location.href = '#/trade';
		}else{
			$scope.list();
		}
		
	};
	$scope.list=function(){
		$http.post("api/trade/getTradeStatistics", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.tradeList=data.result;
            }
        });
	};
	$scope.init();
};

var tradeinfoController = function ($scope, $http,$location, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.id=$location.search()['id'];
		$scope.info();
	};
	$scope.info=function(){
		$http.post("api/tradegood/info", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.info=data.result;
            }
        });
	};
	$scope.init();
};




tradelistController.$inject = ['$scope','$http','LoginService'];
tradeModule.controller("tradelistController", tradelistController);
statisticsController.$inject = ['$scope','$http','LoginService'];
tradeModule.controller("statisticsController", statisticsController);
tradeinfoController.$inject = ['$scope','$http','$location','LoginService'];
tradeModule.controller("tradeinfoController", tradeinfoController);
