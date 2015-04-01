'user strict';

var userManageModule = angular.module("userManageModule", []);
var userManageListController = function($scope, $http, LoginService) {

	$scope.init = function() {
		// 判断是否已登录
		if (LoginService.userid == 0) {
			window.location.href = '#/login';
		} else {
			$scope.$emit('changeshow', false);
		}

		$scope.req = {};
		$scope.req.agentId = 5;
		initSystemPage($scope.req);// 初始化分页参数
		$scope.req.rows = 5;
		$scope.list();
	};

	// 商户列表显示
	$scope.list = function() {
		$scope.req.page = $scope.req.indexPage;
		$http.post("api/agents/queryCommercials", $scope.req).success(function(data) {
			if (data.code == 1) {
				$scope.commercialList = data.result.list;
				calcSystemPage($scope, data.result.total);// 计算分页
			} else {
				alert(data.message);
			}
		}).error(function(data) {
		});

	};

	$scope.commercialDelete = function(e) {

		$http.post("api/agents/getTerminalState", {
			customer_id : e.id
		}).success(function(data) {
			if (data.code == 1) {
				if (confirm("是否删除" + e.name + "用户，删除后将无法再查询该用户的所有终端历史流水信息")) {
					$http.post("api/agents/deleteCommercialOne", {
						customer_id : e.id
					}).success(function(data) {
						if (data.code == 1) {
							window.location.href = '#/manageuser';
							$scope.init();
						}

					}).error(function(data) {
					});
				}
			} else {
				alert(data.message);
			}
		}).error(function(data) {
		});
	}
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

userManageModule.controller("userManageListController", userManageListController);
