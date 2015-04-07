'user strict';

// 用户列表
var accountListModule = angular.module("accountListModule", []);
var accountListController = function($scope, $location, $http, LoginService) {

	$scope.list = function() {
		$scope.req.page = $scope.req.indexPage;
		$http.post("api/account/getAccountList", $scope.req).success(function(data) {
			if (data.code == 1) {
				$scope.accountList = data.result.list;
				calcSystemPage($scope.req, data.result.total);// 计算分页
			} else {
				// 提示错误信息
				alert(data.message);
			}
		}).error(function(data) {

		});
	};
	$scope.accountDelete = function(e) {
		var idsReq = {
			ids : e.customer_id
		};
		if (confirm('确定删除？')) {
			$http.post("api/account/delete", idsReq).success(function(data) {
				if (data.code == 1) {
					window.location.href = '#/accountList';
					$scope.init();
				} else {
					alert(data.message);
				}
			}).error(function(data) {

			});
		}
	};
	$scope.init = function() {
		$scope.req = {};
		$scope.req.agentId = LoginService.agentid;
		
		initSystemPage($scope.req);// 初始化分页参数
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
accountListModule.controller("accountListController", accountListController);