'user strict';

// 商户详情
var accountOneModule = angular.module("accountOneModule", []);
var accountOneController = function($scope, $http, $location, LoginService) {
	$scope.merchantOne = function(e) {
		var merchantId = $location.search()['id'];
		$http.post("api/account/info/" + merchantId).success(function(data) {
			if (data.code == 1) {
				$scope.info = data.result;
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

		$scope.merchantOne();
	};
	$scope.init();
};
accountOneModule.controller("accountOneController", accountOneController);