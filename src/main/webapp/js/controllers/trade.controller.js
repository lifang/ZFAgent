'user strict';

//系统设置模块
var tradeModule = angular.module("tradeModule",[]);

var tradelistController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.agents_id=LoginService.agentid;
		if(LoginService.tradeTypeId==0){
			$scope.req.tradeTypeId=1;
		}else{
			$scope.req.tradeTypeId=LoginService.tradeTypeId;
		}
		$scope.getTradeType();
		$scope.list();
	};
	$scope.list=function(){
		$http.post("api/tradegood/list", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.tradeList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            }
        });
	};
	$scope.getTradeType = function() {
		$http.post("api/trade/getTradeType").success(function(data) {
			if (data.code == 1) {
				$scope.tradeType=data.result;
				$scope.typeName=$scope.tradeType[0].value;
			} else {
				// 提示错误信息
				alert(data.message);
			}
		});
	};
	$scope.changeType = function(one) {
		$scope.req={startTime:"",endTime:"",terminalNumber:""};
		$scope.req.customerId=LoginService.userid;
		$scope.req.tradeTypeId=one.id;
		LoginService.tradeTypeId=one.id;
		initSystemPage($scope.req);// 初始化分页参数
		$scope.typeName=one.value;
		$scope.list();
	};
	$scope.init();
};

var tradeaddController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.agents_id=LoginService.agentid;
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
tradeaddController.$inject = ['$scope','$http','LoginService'];
tradeModule.controller("tradeaddController", tradeaddController);
tradeinfoController.$inject = ['$scope','$http','$location','LoginService'];
tradeModule.controller("tradeinfoController", tradeinfoController);
