'user strict';

var addressModule = angular.module("addressModule", []);

// 系统模块
var addressController = function ($scope, $http, LoginService) {
	
	$scope.init = function() {
		// var agent_id = LoginService.userid;
		// alert(LoginService.agentid);
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
		var customer_id = LoginService.agentid;
		// var customer_id = 1;
		$http.post("api/address/countValidAddress/" + customer_id).success(function(data){
			if(data.code == 1){
				$scope.address = {};
				$scope.selected = "";// 省份置空
				$scope.selected_city = "";// 城市置空
				$("#address").val("");
				$("#zipCode").val("");
				$("#receiver").val("");
				$("#moblephone").val("");
				$("#telphone1").val("");
				$("#setDefault").removeAttr("checked");// 移除radio的选中属性
				$scope.address.isDefault = 2;
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
		var customer_id = LoginService.agentid;
		// var customer_id = 1;
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
		$scope.address.telphone = one.telphone;
		var arr = $scope.address.telphone.split("-");
		if(arr != null && arr != "" && arr != undefined) {
			if(arr.length == 3) {
				$scope.address.telphone1 = arr[0];
				$scope.address.telphone2 = arr[1];
				$scope.address.telphone3 = arr[2];
			} else if (arr.length == 2) {
				$scope.address.telphone1 = arr[0];
				$scope.address.telphone2 = arr[1];
			}
		}
		
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
		var reg1 = /^[0-9]*$/;
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
		
		if($scope.address.telphone3 != null && $scope.address.telphone3 != "" && $scope.address.telphone3 != undefined){
			$scope.address.telphone = $scope.address.telphone1 + "-" + $scope.address.telphone2 + "-"+ $scope.address.telphone3;
			if($scope.address.telphone != null && $scope.address.telphone != "" && typeof($scope.address.telphone) != "undefined") {
				if(!phoneReg.test($scope.address.telphone)) {
					alert("电话号码不合乎规范,请重新输入");
					return false;
				}
			}
		} else if($scope.address.telphone3 == null || $scope.address.telphone3 == "" || $scope.address.telphone3 == undefined) {
			if($scope.address.telphone1 != null && $scope.address.telphone1 != "" && $scope.address.telphone1 != undefined){
				if(!reg1.test($scope.address.telphone1)){
					alert("区号不合乎规范,请重新输入");
					return false;
				}
				if($scope.address.telphone1.length<3 || $scope.address.telphone1.length >4){
					alert("区号需在3位或者是4位数字");
					return false;
				}
				
				if($scope.address.telphone2 != null && $scope.address.telphone2 != "" && $scope.address.telphone2 != undefined){
					if(!reg1.test($scope.address.telphone2)) {
						alert("电话号码不合乎规范,请重新输入");
						return false;
					}
					
					if($scope.address.telphone2.length < 7 || $scope.address.telphone2.length > 8){
						alert("电话号码需在7位或者是8位数字");
						return false;
					}
					
				} else {
					alert("电话号码不能为空");
					return false;
				}
			}
			
			$scope.address.telphone = $scope.address.telphone1 + "-" + $scope.address.telphone2;
		}
		
		if ($scope.address.id == undefined) {// 插入新地址信息
			$scope.address.cityId = $scope.selected_city.id;
			$scope.address.customerId = LoginService.agentid;
			// $scope.address.customerId = 1;
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
			});
		} else {// 修改地址信息
			$scope.address.cityId = $scope.selected_city.id;
			$http.post("api/address/updateAddress", $scope.address).success(function(data) {
				if (data.code == 1) {
					$scope.init();
				} else {
					alert(data.message);
				}
			});
		}
		
	}
	
	$scope.changeDefault = function(){
		// alert($scope.address.isDefault);
		if($scope.address.isDefault == 1){
			$("#setDefault").prop("checked", false);// 移除radio的选中属性
			$scope.address.isDefault = 2;
			// alert($scope.address.isDefault);
		} else {
			$("#setDefault").prop("checked", "checked");
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