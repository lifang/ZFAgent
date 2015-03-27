'use strict';

// 主页面路由模块，用于控制主页面的菜单导航(注入了登陆服务LoginService)
var routeModule = angular.module("routeModule", [ 'loginServiceModule',
		'ngRoute' ]);

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
	}).when('/trade', {
		templateUrl : 'views/trade/trade.html'
	}).when('/tradeinfo', {
		templateUrl : 'views/trade/tradeinfo.html'
	}).when('/tradestatistics', {
		templateUrl : 'views/trade/tradestatistics.html'
	}).when('/purchaseOrder', {
		templateUrl : 'views/order/purchaseOrder.html'
	}).when('/othersOrder', {
		templateUrl : 'views/order/othersOrder.html'
	}).when('/shop', {
		templateUrl : 'views/shop/shop.html'
	}).when('/shopinfo', {
		templateUrl : 'views/shop/shopinfo.html'
	}).when('/purchaseShop', {
		templateUrl : 'views/shop/purchaseShop.html'
	}).when('/purchaseShopinfo', {
		templateUrl : 'views/shop/purchaseShopinfo.html'
	}).when('/accountList', {
		templateUrl : 'views/account/accountList.html'
	}).when('/accountAdd', {
		templateUrl : 'views/account/accountAdd.html'
	}).when('/accountOne', {
		templateUrl : 'views/account/accountOne.html'
	}).when('/empEdit', {
		templateUrl : 'views/systemset/empadd.html'
	}).when('/empdelete', {
		templateUrl : 'views/systemset/empaccount.html'
	}).when('/login', {
		templateUrl : 'views/index/login.html'
	}).when('/register', {
		templateUrl : 'views/index/registerAgent.html'
	}).when('/findpass', {
		templateUrl : 'views/index/findpass.html'
	}).when('/findpassEmail', {
		templateUrl : 'views/index/findpassEmail.html'
	}).when('/lowerAgent', {
		templateUrl : 'views/agent/lowerAgentList.html'
	}).when('/lowerAgentInfo', {
		templateUrl : 'views/agent/lowerAgentInfo.html'
	}).when('/lowerAgentAdd', {
		templateUrl : 'views/agent/lowerAgentAdd.html'
	}).when('/lowerAgentEdit', {
		templateUrl : 'views/agent/lowerAgentEdit.html'
	}).when('/lowerAgentSet', {
		templateUrl : 'views/agent/lowerAgentSet.html'
	}).when('/terminals', {
		templateUrl : 'views/terminals/terminalsList.html'
	}).when('/stock', {
		templateUrl : 'views/agent/stock.html'
	}).when('/stockinfo', {
		templateUrl : 'views/agent/stockinfo.html'
	}).otherwise({
		redirectTo : "/"
	});
};
routeModule.$inject = [ 'LoginService' ];
routeModule.config(routeConfig);
