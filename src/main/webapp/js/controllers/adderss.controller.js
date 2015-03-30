'user strict';

var addressModule = angular.module("addressModule", []);

// 系统模块
var addressController = function ($scope, $http, LoginService) {
	
	$scope.init = function() {
		// 判断是否已登录
		if (LoginService.userid == 0) {
			window.location.href = '#/login';
		} else {
			$scope.$emit('changeshow', false);
		}
		// var agent_id = LoginService.userid;
		$scope.addressList();
	};
	
	$scope.addressList = function(){
		var customer_id=1;
		$http.post("api/address/query/"+customer_id).success(function(data){
			if(data.code==1)
				{
				$scope.list=data.result;
				}
			else
			{
				alert("失败");
			}
		});
	};
	
	$scope.insert = function(){
		$http.post("api/address/insert",{
			cityId : $scope.cityId,
			address:$scope.address,
			zipCode:$scope.zipCode,
			receiver:$scope.receiver,
			telphone:$scope.telphone,
			moblephone:$scope.moblephone,
			isDefault:$scope.isDefault,
		}).success(function(data){
			if(data.code==1)
				{
					alert("保存成功");
				}
			else
				{
		
					alert("保存失败");
			
				}
		});
	}
	$scope.addres = function(){		
			$scope.radio=true;
	};
	
	$scope.dele = function(one){
		var id=one.id;
		alert(id);
		if (confirm('确定删除？')){
			$http.post("api/address/delete/"+id).success(function(data){
				if(data.code==1){
				alert("删除成功！");
				}
				else {
					alert("删除失败！")
				
				}
			});
			$scope.addressList();
			}
		}
	$scope.setisDefault= function(one){
		$http.post("api/address/setisDefault/",{isDefault:one.isDefault,id:one.id}).success(function(data){
			if(data.code==1){
			alert("设置成功！");
			}
			else {
				alert("设置失败！")
			}
		});
		}
	$scope.init();
};


addressController.$inject = ['$scope','$http', 'LoginService'];
addressModule.controller=("addressController", addressController);