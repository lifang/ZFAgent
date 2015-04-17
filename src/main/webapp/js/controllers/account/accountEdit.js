'user strict';

// 商户详情
var accountEditModule = angular.module("accountEditModule", []);

var accountEditController = function($scope, $http, $location, LoginService) {
	$scope.init = function() {
		// alert(LoginService.agentid);
		var customerId = $location.search()['id'];
		$scope.info(customerId);
	};

	$scope.info = function(customerId) {
		$http.post("api/account/info/" + customerId).success(function(data) {
			if (data.code == 1) {
				$scope.info = data.result;
				var rightIds = data.result.rightIds;
				for (var i = 0; i < rightIds.length; i++) {
					// alert(i);
					isCheckBoxChecked(rightIds[i]);
				}
				// alert(rightIds[0]);

			} else {
				alert(data.message);
			}
		}).error(function(data) {

		});
	};

	$scope.accountEdit = function() {
		var name = $scope.info.name;
		var password = $scope.info.password;

		if (typeof (name) == "undefined" || name == null) {
			alert("姓名不能为空");
			return false;
		}

		$scope.info.rightIds = [];
		$scope.info.rightIds = getCheckboxValue();
		// alert($scope.info.rightIds);

		$scope.info.agent_id = LoginService.agentid;
		// $scope.info.agent_id = 5;
		$scope.info.customer_id = $location.search()['id'];

		$http.post("api/account/editCustomer", $scope.info).success(function(data) {
			if (data.code == 1) {
				window.location.href = '#/accountOne?id=' + $scope.info.customer_id;
			} else {
				alert(data.message);
			}
		}).error(function(data) {

		});
	};

	$scope.init();
};

accountEditModule.controller("accountEditController", accountEditController);