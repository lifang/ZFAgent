'user strict';

var userManageModule = angular.module("userManageModule", []);
var userManageListController = function($scope, $http, LoginService) {
	// $scope.rows = 5;
	// alert($scope.rows);
	initSystemPage($scope);// 初始化分页参数
	$scope.init = function() {
		// 判断是否已登录
		if (LoginService.userid == 0) {
			window.location.href = '#/login';
		} else {
			$scope.$emit('changeshow', false);
		}

		$scope.list();
	};

	// 商户列表显示
	$scope.list = function() {
		$http.post("api/agents/queryCommercials", {
			// agent_id:LoginService.userid
			agent_id : 5,
			offset : $scope.indexPage,
			rows : $scope.rows
		}).success(function(data) {
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
		if ($scope.indexPage > 1) {
			$scope.indexPage--;
			$scope.list();
		}
	};

	// 当前页
	$scope.loadPage = function(currentPage) {
		$scope.indexPage = currentPage;
		$scope.list();
	};

	// 下一页
	$scope.next = function() {
		if ($scope.indexPage < $scope.totalPage) {
			$scope.indexPage++;
			$scope.list();
		}
	};

	// 跳转到XX页
	$scope.getPage = function() {
		$scope.indexPage = Math.ceil($scope.gotoPage);
		$scope.list();
	};

	$scope.init();

};

userManageModule.controller("userManageListController", userManageListController);
