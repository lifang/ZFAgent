'user strict';

//系统设置模块
var terminalBindingModule = angular.module("terminalBindingModule",['loginServiceModule']);

var agentBinTerminalController = function ($scope, $http, LoginService) {
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
		 $http.post('api/webTerminal/searchUser',{name:$scope.agentToUserName}).success(function(data){
			 if(data.code == 1){
				 $scope.agentToUsers = data.result;
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("用户列表请求失败！");
		 })
	 }
	 //创建新用户
	 $scope.establish = function(){
		 alert( $scope.binobject.address.id);
	 }
	 
	 $scope.test = function(num){
		 $scope.userId = num;
	 }
	 
	
	 //初始化数据
	 $scope.bininit();
};

terminalBindingModule.$inject = ['$scope', '$http', '$cookieStore'];
terminalBindingModule.controller("agentBinTerminalController", agentBinTerminalController);
