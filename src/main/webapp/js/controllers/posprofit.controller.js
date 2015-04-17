'user strict';

// 系统设置模块
var posprofitModule = angular.module("posprofitModule", []);

var posprofitController = function($scope, $http, LoginService) {

	$scope.init = function() {
		$scope.req = {};
		$scope.req.agent_id = LoginService.agentid;

		initSystemPage($scope.req);// 初始化分页参数
		$scope.req.rows = 10;
		$scope.list();
	};

	$scope.list = function() {
		$scope.req.page = $scope.req.indexPage;
		$http.post("api/posprofit/getPosProfitList", $scope.req).success(function(data) {
			if (data.code == 1) {
				$scope.posProfitList = data.result.list;
				$scope.totalProfit = data.result.totalProfit;// 分润总额
				$scope.startTime = data.result.startTime;
				$scope.endTime = data.result.endTime;
				calcSystemPage($scope.req, data.result.total);// 计算分页
			} else {
				alert(data.message);
			}
		}).error(function(data) {
		});
	};

	// 更新
	$scope.update = function() {
		$scope.list();
	};
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

	$scope.init();
};
posprofitController.$inject = [ '$scope', '$http', 'LoginService' ];
posprofitModule.controller("posprofitController", posprofitController);