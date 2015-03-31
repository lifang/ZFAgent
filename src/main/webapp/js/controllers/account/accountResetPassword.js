'use strict';

var resetPasswordModule = angular.module("resetPasswordModule", []);

var resetPasswordController = function($scope, $http, $location, LoginService) {

	$scope.init = function() {
		// 判断是否已登录
		if (LoginService.userid == 0) {
			window.location.href = '#/login';
		} else {
			$scope.$emit('changeshow', false);
		}

		var customerId = $location.search()['id'];
		$scope.info(customerId);
	};

	$scope.info = function(customerId) {
		$http.post("api/account/info/" + customerId).success(function(data) {
			if (data.code == 1) {
				$scope.info = data.result;
				$scope.roleNames = data.result.roleNames;
			} else {
				alert(data.message);
			}
		}).error(function(data) {

		});
	};

	$scope.update = function(customerId) {
		var password = $scope.password;
		var comfirmpwd = $scope.comfirmpwd;
		var originalpassword = $scope.info.password;
		// alert(originalpassword);
		var customerId = $location.search()['id'];
		if (typeof ($scope.password) == "undefined" || typeof ($scope.comfirmpwd) == "undefined") {
			alert("密码不能为空！");
			return false;
		} else if ($scope.password.length < 6 || $scope.password.length > 20 || $scope.comfirmpwd.length < 6 || $scope.comfirmpwd.length > 20) {
			alert("密码由6-20位，英文字符组成！");
			return false;
		} else if ($scope.password != $scope.comfirmpwd) {
			alert("密码不一致！");
			return false;
		}
		$http.post("api/account/resetPassword", {
			customer_id : customerId,
			password : password,
			originalpassword : originalpassword
		}).success(function(data) {
			if (data.code == 1) {
				alert(data.message);
				window.location.href = '#/accountList';
			} else {
				alert(data.message);
			}

		}).error(function(data) {
		});
	}
	$scope.init();
};

resetPasswordModule.controller("resetPasswordController", resetPasswordController);