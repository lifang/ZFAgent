'user strict';

// 系统设置模块
var systemSetModule = angular.module("systemSetModule", []);

// 代理商获取所有账户列表
var emplistController = function($scope, $http, LoginService) {
	$scope.init = function() {
		$scope.req = {};
		$scope.req.agentId = 5;
		// $scope.req.agents_id=LoginService.agentid;
		$scope.list();
	};

	$scope.list = function() {
		$http.post("api/empAccount/getAllAccountlist", $scope.req).success(
				function(data) {
					if (data.code == 1) {

						$scope.accountList = data.result.content;
						// calcSystemPage($scope.req, data.result.total);// 计算分页
					}
				});
	};

	$scope.init();
};

// 获取员工信息详情
var empInfoController = function($scope, $http, $location, LoginService) {
	$scope.init = function() {
		$scope.req = {};
		$scope.req.id = $location.search()['id'];
		// alert($location.search()['id']);
		$scope.info();
	};

	$scope.info = function() {
		$http.post("api/empAccount/info", $scope.req).success(function(data) { // 绑定
			if (data.code == 1) {
				$scope.info = data.result;
			}
		});
	};

	$scope.init();
};

// 创建用户信息
// var empAddController = function($scope, $http, LoginService) {
//
// alert("aaaaaa");
// $scope.add = function() {
// $scope.req = {
// name : $scope.name,
// username : $scope.username,
// password : $scope.password
// };
// $http.post("api/empAccount/addCustomer", $scope.req).success(
// function(data) {
// if (data != null && data != undefined) {
// }
// }).error(function(data) {
// });
//
// }
//
// $scope.add();
// };

emplistController.$inject = [ '$scope', '$http', 'LoginService' ];
systemSetModule.controller("emplistController", emplistController);

empInfoController.$inject = [ '$scope', '$http', '$location', 'LoginService' ];
systemSetModule.controller("empInfoController", empInfoController);

// empAddController.$inject = [ '$scope', '$http', 'LoginService' ];
// systemSetModule.controller("empAddController", empAddController);
