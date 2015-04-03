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
		$(".myInfoBox").hide();// 隐藏编辑区域
		$("#addCheck").html("");
		$scope.addressList();
		
		$http.post("api/index/getCity").success(function (data) {   
            if (data != null && data != undefined) {
                $scope.city_list = data.result;
            }
        });
	};
	
	// 显示编辑区域
	$scope.useNewAddr = function(){
		// var customer_id = LoginService.userid;
		var customer_id = 1;
		$http.post("api/address/countValidAddress/" + customer_id).success(function(data){
			if(data.code == 1){
				$(".myInfoBox").show();
				$scope.selected = "";// 省份置空
				$scope.selected_city = "";// 城市置空

			} else {
				$("#addCheck").html("每个账号最多拥有10条有效地址信息，请删除或修改原地址");
			}
		}).error(function(data){
			
		}); 
	}
	
	// 清空选中
	$scope.ch_city = function() {
		$scope.selected_city = "";
	}
	
	// 显示代理商收获地址信息
	$scope.addressList = function(){
		// var customer_id = LoginService.userid;
		var customer_id = 1;
		$http.post("api/address/query/" + customer_id).success(function(data){
			if(data.code == 1){
				$scope.list = data.result;
			} else {// 提示错误信息
				alert(data.message);
			}
		}).error(function(data) {

		});
	};
	
	// 删除(逻辑删除)
	$scope.dele = function(one){
		var idsReq = {
				ids : [ one.id ]
		};
		if (confirm('确定删除？')) {
			$http.post("api/address/delete", idsReq).success(function(data) {
				if (data.code == 1) {
					$scope.init();
				} else {
					alert(data.message);
				}
			}).error(function(data) {

			});
		}
	};
	
	// 修改
	$scope.openUpdateAddress = function(one){
		$(".myInfoBox").show();
		$scope.address = one;
		$scope.address.id = one.id;
		$scope.selected = { 
			id : one.city_parent_id,
			name : one.city_parent_name
		};
		$scope.selected_city = {
			id : one.cityId,
			name : one.city_name
		};
	};
	
	// 保存
	$scope.save = function(){
		if (typeof($scope.selected) == "undefined" || ($scope.selected) == "" || ($scope.selected) == null) { 
			alert("请选择省份");
			return false;
		} else if (typeof($scope.selected_city) == "undefined"  || ($scope.selected_city) == ""  || ($scope.selected_city) == null){
			alert("请选择城市");
			return false;
		} else if ($scope.address.address.length > 100){
			alert("请填写正确的地址，最多50个汉字");
			return false;
		} else if ($scope.address.receiver.length > 16){
			alert("最多支持8个汉字或16个字母");
			return false;
		} else {
			if ($scope.address.id == undefined) {
				$scope.address.cityId = $scope.selected_city.id;
				// $scope.address.customerId = LoginService.userid;
				$scope.address.customerId = 1;
				// alert($scope.address.isDefault);
				if($scope.address.isDefault == undefined){
					$scope.address.isDefault = 2;
				}
				$http.post("api/address/insert", $scope.address).success(function(data) {
					if (data.code == 1) {
						alert("保存成功");
						$scope.init();
					} else {
						alert("请填写正确的数据");
					}
				}).error(function(data) {

				});
			} else {
				$scope.address.cityId = $scope.selected_city.id;
				$http.post("api/address/updateAddress", $scope.address).success(function(data) {
					if (data.code == 1) {
						$scope.init();
					} else {
						alert(data.message);
					}
				}).error(function(data) {

				});
			}
		}
	}
	
	// 设置默认地址
	$scope.setDefaultAddress = function(one) {
		$http.post("api/address/setDefaultAddress", one).success(function(data) {
			if (data.code == 1) {
				$scope.init();
			} else {
				alert(data.message);
			}
		}).error(function(data) {

		});
	};
	
	$scope.init();
};


addressController.$inject = ['$scope','$http', 'LoginService'];
addressModule.controller=("addressController", addressController);