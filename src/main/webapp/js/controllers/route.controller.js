'use strict';

// 主页面路由模块，用于控制主页面的菜单导航(注入了登陆服务LoginService)
var routeModule = angular.module("routeModule", [ 'loginServiceModule', 'ngRoute' ]);

// 路由器的具体分发
function routeConfig($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'views/index/myapp.html'
	}).when('/prepare', {
		templateUrl : 'views/agent/preparelist.html'
	}).when('/addprepare', {
		templateUrl : 'views/agent/prepareadd.html'
	}).when('/prepareinfo', {
		templateUrl : 'views/agent/prepareinfo.html'
	}).when('/exchange', {
		templateUrl : 'views/agent/exchangelist.html'
	}).when('/addexchange', {
		templateUrl : 'views/agent/exchangeadd.html'
	}).when('/exchangeinfo', {
		templateUrl : 'views/agent/exchangeinfo.html'
	}).otherwise({
		redirectTo : "/"
	});
};
routeModule.$inject = [ 'LoginService' ];
routeModule.config(routeConfig);