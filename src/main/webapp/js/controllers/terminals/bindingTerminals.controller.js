'user strict';

//系统设置模块
var terminalBindingModule = angular.module("terminalBindingModule",['loginServiceModule']);

var agentBinTerminalController = function ($scope, $http, LoginService) {
	 $scope.butshow = true;//按钮切换
};

terminalBindingModule.$inject = ['$scope', '$http', '$cookieStore'];
terminalBindingModule.controller("agentBinTerminalController", agentBinTerminalController);
