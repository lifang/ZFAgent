'user strict';

var addressModule = angular.module("addressModule", []);

// 系统模块
var addressController = function ($scope, $http, LoginService) {
	
	$scope.init = function() {
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
				$scope.selected = "";// 省份置空
				$scope.selected_city = "";// 城市置空
				$("#address").val("");
				$("#zipCode").val("");
				$("#receiver").val("");
				$("#moblephone").val("");
				$("#telphone").val("");
				$(".myInfoBox").show();
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
		var zipCodeReg = /^[1-9][0-9]{5}$/;// 校验邮政编码
		var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;// 校验手机号码
		var phoneReg = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;// 校验电话号码
		if (typeof($scope.selected) == "undefined" || ($scope.selected) == "" || ($scope.selected) == null) { 
			alert("请选择省份");
			return false;
		} else if (typeof($scope.selected_city) == "undefined"  || ($scope.selected_city) == ""  || ($scope.selected_city) == null){
			alert("请选择城市");
			return false;
		} else if (typeof($scope.address.address) == "undefined" || $scope.address.address == "" || $scope.address.address == null){
			alert("收获地址不能为空");
			return false;
		} else if (strlen($scope.address.address) > 100){
			alert("请填写正确的地址，最多50个汉字");
			return false;
		} 
		
		if(typeof($scope.address.zipCode) != "undefined" && $scope.address.zipCode != "" && $scope.address.zipCode != null){
			
			if(!zipCodeReg.test($scope.address.zipCode)){
				alert("请填写正确的邮政编码");
				return false;
			}
		}
		if (typeof($scope.address.receiver) == "undefined" || $scope.address.receiver == "" || $scope.address.receiver == null){
			alert("收货人不能为空");
			return false;
		} else if (strlen($scope.address.receiver) > 16){
			alert("最多支持8个汉字或16个字母");
			return false;
		} else if(!reg.test($scope.address.moblephone)) {
			alert("请填写正确的手机号码");
			return false;
		} 
		
		if(typeof($scope.address.telphone) != "undefined" && $scope.address.telphone != "" && $scope.address.telphone != null) {
			if($scope.address.telphone.length > 15) {
				alert("最多支持15个数字");
				return false;
			}
		}
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