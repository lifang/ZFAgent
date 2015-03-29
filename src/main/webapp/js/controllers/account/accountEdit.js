'user strict';

// 商户详情
var accountEditModule = angular.module("accountEditModule", []);

var accountEditController = function($scope, $http, $location, LoginService) {
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
				var rightIds = data.result.rightIds;
				// alert(rightIds);
			} else {
				alert(data.message);
			}
		}).error(function(data) {

		});
	};

	$scope.accountEdit = function() {
		var name = $scope.info.name;
		var password = $scope.password;
		var comfirmpwd = $scope.comfirmpwd;

		if (typeof (name) == "undefined") {
			alert("姓名不能为空");
			return false;
		}

		if (typeof (password) == "undefined" || typeof (comfirmpwd) == "undefined") {
			alert("密码不能为空！");
			return false;
		} else if (password.length < 6 || password.length > 20 || comfirmpwd.length < 6 || comfirmpwd.length > 20) {
			alert("密码由6-20位，英文字符组成！");
			return false;
		} else if (password != comfirmpwd) {
			alert("输入的密码不一致！");
			return false;
		}

		$scope.info.rights = getCheckboxValue();

		if (typeof ($scope.info.rights) == "undefined" || $scope.info.rights == "") {
			alert("权限未填写");
			return false;
		}

		// $scope.info.agent_Id = LoginService.userid;
		$scope.info.agent_id = 5;
		$scope.info.customer_id = $location.search()['id'];

		$http.post("api/account/editCustomer", $scope.info).success(function(data) {
			if (data.code == 1) {
				window.location.href = '#/accountList';
			} else {
				alert(data.message);
			}
		}).error(function(data) {

		});
	};

	$scope.init();
};

accountEditModule.controller("accountEditController", accountEditController);