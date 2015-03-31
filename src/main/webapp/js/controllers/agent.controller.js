'user strict';

// 系统模块设置

var modifypasswordModule = angular.module("modifypasswordModule", []);

var modifypasswordController = function($scope, $http, LoginService) {

	$scope.save = function() {
		// agents_id : LoginService.agentid,
	};

	$scope.info = function() {
		var oldPassword = $scope.passwordOld;
		var newPassword = $scope.password;
		var newPasswordAgain = $scope.password2;
		
		if(oldPassword==null||oldPassword==''){
			alert("原密码不能为空！");
			return false;
		}
		
		if(newPassword==null||newPassword==''){
			alert("新密码不能为空！");
			return false;
		}
		
		if(newPasswordAgain==null||newPasswordAgain==''){
			alert("确认新密码不能为空！");
			return false;
		}
		
		if(newPassword!=newPasswordAgain){
			alert("新密码与确认新密码不相同！");
			return false;
		}
		
		$http.post("api/agent/modifyPassword", {
			passwordOld : oldPassword,
			password : newPassword,
			id : 17
		}).success(function(data) {
			if(data.code==1){
				$scope.passwordOld = "";
				$scope.password = "";
				$scope.password2 = "";
			}
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
