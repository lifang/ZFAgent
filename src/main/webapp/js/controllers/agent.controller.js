'user strict';

// 系统模块设置

var modifypasswordModule = angular.module("modifypasswordModule", []);

var modifypasswordController = function($scope, $http, LoginService) {

	$scope.save = function() {
		// agents_id : LoginService.agentid,
	};

	$scope.info = function() {
		$http.post("api/agent/modifyPassword", {
			passwordOld : $scope.passwordOld,
			password : $scope.password,
			id : 17
		}).success(function(data) {
			alert(data.message);
		});
	};
	$scope.query = function() {
		var id = 15;
		$http.post("api/agents/query/" + id).success(function(data) {
			if (data.result != null) {
				$scope.one = data.result;
				// alert($scope.one.name);
			}
		});
	};
	
	$scope.menuState = {
		show : false
	}
	$scope.toggleMenu = function() {
		$scope.menuState.show = !$scope.menuState.show;
	}

	$scope.getEmail = function() {
		$http.post("api/agent/getEmail").success(function(data) {
			if (data != null || data == 0) {
				alert(data.message);
			} else {
				$scope.show();
			}
		});

	}

	$scope.updatePhone = function() {
		$scope.req = {
			dentcode : dentcode
		};
		$http.post("api/agent/updatePhone", $scope.req).success(function(data) {
			alert(data.message);
		});
		$scope.save();
	};
	$scope.query();
};

modifypasswordController.$inject = [ '$scope', '$http', 'LoginService' ];
modifypasswordModule.controller("modifypasswordController", modifypasswordController);
