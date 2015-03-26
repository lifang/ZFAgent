'user strict';

// 创建商户
var empAddModule = angular.module("empAddModule", []);
var empAddController = function($scope, $http, $location, LoginService) {

	$scope.customer = {};

	$scope.accountAdd = function() {
		var name = $scope.customer.name;
		var username = $scope.customer.username;
		var password = $scope.customer.password;
		var comfirmpwd = $scope.customer.comfirmpwd;
		var rights = $scope.customer.rightid0;

		// alert(rights);

		if (typeof ($scope.customer.name) == "undefined") {
			alert("姓名未填写");
			return false;
		}

		if (typeof ($scope.customer.username) == "undefined") {
			alert("用户名未填写");
			return false;
		}

		if (typeof ($scope.customer.password) == "undefined") {
			alert("密码未填写");
			return false;
		}

		if (typeof ($scope.customer.comfirmpwd) == "undefined") {
			alert("秘密未填写");
			return false;
		}

		if ($scope.customer.password != $scope.customer.comfirmpwd) {
			alert("输入的密码不一致！");
			return false;
		}

		// $scope.customer.agent_Id = LoginService.userid;
		$scope.customer.agent_id = 5;

		$http.post("api/account/addCustomer", $scope.customer).success(
				function(data) {
					if (data.code == 1) {
						window.location.href = '#/accountList';
					} else {
						alert(data.message);
					}
				}).error(function(data) {

		});
	};
	$scope.init = function() {

		// 判断是否已登录
		if (LoginService.userid == 0) {
			window.location.href = '#/login';
		} else {
			$scope.$emit('changeshow', false);
		}

	};
	$scope.init();
};
empAddModule.controller("empAddController", empAddController);