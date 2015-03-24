'user strict';

// 系统设置模块
var empinfobaseModule = angular.module("empinfobaseModule", []);

// 创建用户信息
var empAddController = function($scope, $http, LoginService) {

	$scope.add = function() {
		// alert("aaaaaa");
		$scope.req = {
			name : $scope.name,
			username : $scope.username,
			password : $scope.password,
			// agent_id : LoginService.agentid
			agent_id : 5
		};

		$http.post("api/empAccount/addCustomer", $scope.req).success(

		function(data) {
			// alert($scope.username);
			if (data != null && data != undefined) {
				if (data.code == 1) {
					alert(data.message);
				} else {
					alert(data.message);
				}
			}
		}).error(function(data) {
		});

	}

	$scope.add();
};

// empinfoController.$inject = [ '$scope', '$http', 'LoginService' ];
empinfobaseModule.controller("empAddController", empAddController);