'user strict';

//系统设置模块
var terminalServiceModule = angular.module("terminalServiceModule",['loginServiceModule']);

var agentServiceTerminalController = function ($scope, $http, LoginService) {
	//$scope.customersId = LoginService.userid;
	  $scope.customersId = 80;
	  $scope.butshow = false;//添加新地址显示
	  $scope.serviceObject = {};//数据封装
	  $scope.addressObject = {};//数据封装
	 
	 $scope.serviceInit = function(){
		 $scope.cityList();
		 $scope.getAddress();
	 }
	 
	 //获得联系地址
	 $scope.getAddress = function(){
		  $http.post('api/webTerminal/getAddressee',{customerId:$scope.customersId}).success(function(data){
			 if(data.code == 1){
				 $scope.addressList = data.result;
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("联系地址请求失败！");
		 })
	 }
	 
	 //添加联系地址
	 $scope.addAddress = function(){
		 $scope.addressObject.cityId = $scope.serviceObject.sitys.id;
		 $scope.addressObject.customerId = $scope.customersId;
		 $http.post('api/webTerminal/addCostometAddress',$scope.addressObject).success(function(data){
			 if(data.code == 1){
				 $scope.getAddress();
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 })
	 }
	 
	 $scope.radioId = function(obj){
		 for(var i=0;i< $scope.addressList.length;i++){
			 if(obj == i){
				 $scope.serviceObject.receiver = $scope.addressList[i].receiver;
				 $scope.serviceObject.address = $scope.addressList[i].address;
				 $scope.serviceObject.zipCode = $scope.addressList[i].zipCode;
				 $scope.serviceObject.phone = $scope.addressList[i].moblephone;
				 //$scope.serviceObject.cityId = $scope.addressList[i].cityId;
			 }
		 }
	 };
	 
	 $scope.terminalSub = function(){
		 $scope.serviceObject.customerId = $scope.customersId;
		 $scope.serviceObject.content = $("#comsName").html()+$scope.coms+","+$("#orderName").html()+$scope.order;
		 $http.post('api/webTerminal/submitAgent',$scope.serviceObject).success(function(data){
			 if(data.code == 1){
				 alert(data.code);
			 }else if(data.code == -1){
				 alert(data.code);
				 alert(data.result);
			 }
		 })
	 }
	 //获得省市
	 $scope.cityList = function(){
		 $http.post('api/comment/getCity').success(function(data){
			 if(data.code == 1){
				$scope.cityList = data.result;
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("城市列表请求失败！");
		 })
	 }
	 //初始化数据
	 $scope.serviceInit();
};

terminalServiceModule.$inject = ['$scope', '$http', '$cookieStore'];
terminalServiceModule.controller("agentServiceTerminalController", agentServiceTerminalController);
