'user strict';

var customerListViewModule = angular.module("customerListViewModule", []);
var customerListViewController = function($scope, $location, $http, LoginService) {
	var customerId = $location.search()['id'];
	$scope.customerId = customerId;
	initSystemPage($scope);// 初始化分页参数
	$scope.list = function() {

		var page = $scope.indexPage;
		$scope.rows = 5;
		var rows = $scope.rows;
		var query = customerId + "/" + page + "/" + rows;
		$http.post("api/user/getList/" + query).success(function(data) {
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

	$scope.init = function() {
	
		// 获取商户信息
		$http.post("api/user/queryMerchantInfo/" + customerId).success(function(data) {
			if (data.code == 1) {
				$scope.customer = data.result;
			} else {
				alert(data.message);
			}
		});
		
		$http.post("api/index/getCustomerMarks/" + customerId).success(function(data) {
			if (data != null && data != undefined) {
				$scope.markList = data.result.list;
			}
		});

		$scope.list();
	};

	$scope.init();

	$scope.marksSave = function() {
		var marksContent = $scope.marks_content;
		if (marksContent == null || marksContent == '') {
			$("#markCheck").html("*备注内容不能为空");
			return false;
		}
		$scope.req = {
			content : marksContent,
			customerId : customerId,
			agentId : 22
		};
		$http.post("api/index/saveViewCustomerViews", $scope.req).success(function(data) {
			if (data != null && data != undefined) {
				alert("添加备注成功！");
				$scope.init();
				$scope.marks_content = "";
			}
		});
		
	};

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
customerListViewModule.controller("customerListViewController", customerListViewController);