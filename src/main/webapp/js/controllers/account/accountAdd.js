'user strict';

// 创建商户
var empAddModule = angular.module("empAddModule", []);
var empAddController = function($scope, $http, $location, LoginService) {

	// $scope.customer = {};

	$scope.accountAdd = function() {
		var name = $scope.customer.name;
		var username = $scope.customer.username;
		var password = $scope.customer.password;
		var comfirmpwd = $scope.customer.comfirmpwd;

		if (typeof ($scope.customer.name) == "undefined" || $scope.customer.name == "") {
			alert("姓名不能为空");
			return false;
		} else if(strlen($scope.customer.name) > 16){
			alert("最多支持8个汉字或16个字母");
			return false;
		}
		
		if (typeof ($scope.customer.username) == "undefined" || $scope.customer.username == "") {
			alert("登陆ID不能为空");
			return false;
		} else if(strlen($scope.customer.username) > 40){
			alert("登陆ID最多填写40个字符或20 个汉字");
			return false;
		}

		if (typeof ($scope.customer.password) == "undefined" || typeof ($scope.customer.comfirmpwd) == "undefined") {
			alert("密码不能为空！");
			return false;
		} else if ($scope.customer.password.length < 6 || $scope.customer.password.length > 20 || $scope.customer.comfirmpwd.length < 6 || $scope.customer.comfirmpwd.length > 20) {
			alert("密码由6-20位，英文字符组成！");
			return false;
		} else if ($scope.customer.password != $scope.customer.comfirmpwd) {
			alert("密码不一致！");
			return false;
		}

		$scope.customer.rightIds = getCheckboxValue();

		// alert($scope.customer.rights);
		
		// $scope.customer.agent_Id = LoginService.userid;
		$scope.customer.agent_id = 5;

		$http.post("api/account/addCustomer", $scope.customer).success(function(data) {
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