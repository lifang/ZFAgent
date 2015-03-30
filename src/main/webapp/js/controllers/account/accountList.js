'user strict';

// 用户列表
var accountListModule = angular.module("accountListModule", []);
var accountListController = function($scope, $location, $http, LoginService) {
	initSystemPage($scope);// 初始化分页参数
	$scope.list = function() {
		// var customerId = LoginService.userid;
		var customerId = 5;
		var page = $scope.indexPage;
		var rows = $scope.rows;
		var query = customerId + "/" + page + "/" + rows;
		$http.post("api/account/getList/" + query).success(function(data) {
			if (data.code == 1) {
				$scope.accountList = data.result.list;
				calcSystemPage($scope, data.result.total);// 计算分页
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
		// 判断是否已登录
		if (LoginService.userid == 0) {
			window.location.href = '#/login';
		} else {
			$scope.$emit('changeshow', false);
		}

		$scope.list();
	};
	$scope.init();

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

};
accountListModule.controller("accountListController", accountListController);