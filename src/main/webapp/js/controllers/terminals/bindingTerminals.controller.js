'user strict';

//系统设置模块
var terminalBindingModule = angular.module("terminalBindingModule",['loginServiceModule']);

var agentBinTerminalController = function ($scope, $http, LoginService) {
	//$scope.customersId = LoginService.userid;
	  $scope.customersId = 80;
	 $scope.butshow = true;//按钮切换
	 $scope.binobject = {};//数据封装
	 
	 $scope.bininit = function(){
		 $scope.cityList();
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
	 
	 //搜索现有用户 （类型为用户）
	 $scope.agentToUserName = null;
	 $scope.userId = -1;
	 $scope.searchUser = function(){
		 $http.post('api/webTerminal/searchUser',{name:$scope.agentToUserName,customerId:$scope.customersId}).success(function(data){
			 if(data.code == 1){
				 $scope.agentToUsers = data.result;
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("用户列表请求失败！");
		 })
	 }
	 
	 //点击获取用户id
	 $scope.checkUserId = function(num){
		 $scope.userId = num;
	 }
	 
	 //开始绑定
	 $scope.BindingTerminals = function(){
		 $http.post('api/webTerminal/BindingTerminals',{customerId:$scope.customersId,terminalsNum:$scope.terminalsNum,userId:Math.ceil($scope.userId)}).success(function(data){
			 if(data.code == 1){
				 alert(data.result);
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("绑定请求失败！");
		 })
	 }
	 //创建新用户
	 $scope.addShow = false;
	 $scope.establish = function(){
		 $scope.binobject.cityid = Math.ceil($scope.binobject.address.id);
		 $http.post('api/webTerminal/addCustomer',$scope.binobject).success(function(data){
			 if(data.code == 1){
				 $scope.aduser = data.result.username;
				 $scope.binobject = {};
				 $scope.addShow = true;
				 $scope.userId = data.result.id;
			 }else if(data.code == -1){
				 $scope.addShow = false;
				 $scope.binobject = {};
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("绑定请求失败！");
		 })
	 }
	 
	 
	 
	
	 //初始化数据
	 $scope.bininit();
};

terminalBindingModule.$inject = ['$scope', '$http', '$cookieStore'];
terminalBindingModule.controller("agentBinTerminalController", agentBinTerminalController);
