'user strict';

var customerListViewModule = angular.module("customerListViewModule", []);
var customerListViewController = function($scope, $location, $http,
		LoginService) {
	var customerId =  $location.search()['id'];
	$scope.customerId=customerId;
	initSystemPage($scope);// 初始化分页参数
	$scope.list = function() {

		// var customerId = LoginService.userid;
//		var customerId = 80;
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
		// var agentId = LoginService.userid;
		$http.post("api/user/query/" + customerId).success(function(data) {
			if (data.result != null) {
				$scope.test = data.result;
				$scope.queryCity();
				// alert($scope.one.name);
			}
		});
		$http.post("api/index/getCustomerMarks/" + customerId).success(
				function(data) {
					if (data != null && data != undefined) {
						$scope.markList = data.result.list;
					}
				});
		$scope.list();
	};

	$scope.init();

	$scope.queryCity = function() {
		var city_id = $scope.test.city_id;
		$http.post("api/index/getIdCity/" + city_id).success(function(data) {
			if (data != null && data != undefined) {
				$scope.test.address = data.result;
			}
		});
	};

	$scope.marksSave = function() {
		var marksContent = $scope.marks_content;
		if (marksContent == null || marksContent == '') {
			return false;
		}
		$scope.req ={content : marksContent,customerId : customerId,agentId : 17};
		$http.post("api/index/saveViewCustomerViews",$scope.req).success(function (data) {   
			if (data != null && data != undefined) {
				alert("添加备注成功！");
				$scope.init();
				$scope.marks_content = "";
			}
		});
//		$scope.req = {
//			content : marksContent,
//			customerId : customerId,
//			agentId : 17
//		};
//		$http.post("api/index/getIdCity" + city_id).success(
//				function(data) {
//					$scope.marks_content = "";
//				});
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
customerListViewModule.controller("customerListViewController",
		customerListViewController);