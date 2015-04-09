'user strict';
var messageDetailsModule = angular.module("messageDetailsModule", []);
var messageDetailsController = function($scope, $location, $http, LoginService){
	$scope.init = function() {
		$scope.req = {};
		if (LoginService.agentid == 0) {
			window.location.href = '#/login';
		}
		$scope.req.id = $location.search()['id']; 
		$scope.req.customerId = LoginService.agentUserId; 
		$http.post("api/message/receiver/getById", $scope.req).success(function(data) {
			if (data.code == 1) {
				$scope.content = data.result.content;
				$scope.title = data.result.title;
				$scope.create_at = data.result.create_at;
			}else{
				alert(data.message);
			}
		});
	} 

	$scope.init();
	
	$scope.deleteMsg = function() {
		$http.post("api/message/receiver/deleteById", $scope.req).success(function(data) {
			if (data.code == 1) {
				$location.url("/myMessage");
			}
		});

	};
	
}

messageDetailsModule.controller("messageDetailsController",messageDetailsController);