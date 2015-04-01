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
	}).when('/email_up', {
		templateUrl : 'views/customer/up_email.html'
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
	}).when('/myMessage',{
		templateUrl : 'views/management/myMessage.html'		
	}).when('/accountEdit', {
		templateUrl : 'views/account/accountEdit.html'
	}).when('/accountResetPassword', {
		templateUrl : 'views/account/accountResetPassword.html'
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
	}).when('/cs_cencel', {
		templateUrl : 'views/cs/cencel.html'
	}).when('/cs_cencelinfo', {
		templateUrl : 'views/cs/cencelinfo.html'
	}).when('/cs_update', {
		templateUrl : 'views/cs/update.html'
	}).when('/cs_updateinfo', {
		templateUrl : 'views/cs/updateinfo.html'
	}).when('/cs_agent', {
		templateUrl : 'views/cs/agent.html'
	}).when('/cs_agentinfo', {
		templateUrl : 'views/cs/agentinfo.html'
	}).when('/binding', {
		templateUrl : 'views/terminals/bindingTerminals.html'
	}).when('/terminalSerivce', {
		templateUrl : 'views/terminals/terminalService.html'
	}).when('/terminalDetail', {
		templateUrl : 'views/terminals/terminalDetail.html'
	}).when('/terminalCancellation', {
		templateUrl : 'views/terminals/terminalCancellation.html'
	}).when('/terminalToUpdate', {
		templateUrl : 'views/terminals/terminalToUpdate.html'
	}).when('/terminalOpening', {
		templateUrl : 'views/terminals/terminalOpening.html'
	}).when('/stock', {
		templateUrl : 'views/agent/stock.html'
	}).when('/stockinfo', {
		templateUrl : 'views/agent/stockinfo.html'
	}).when('/agentinform', {
		templateUrl : 'views/systemset/agentinform.html'
	}).when('/adderss', {
		templateUrl : 'views/systemset/adderss.html'
	}).when('/modifypassword', {
		templateUrl : 'views/systemset/modifypassword.html'
	}).when('/shopmakeorder', {
		templateUrl : 'views/shop/shopmakeorder.html'
	}).when('/leasemakeorder', {
		templateUrl : 'views/shop/leasemakeorder.html'
	}).when('/purchasemakeorder', {
		templateUrl : 'views/shop/purchasemakeorder.html'
	}).when('/pay', {
		templateUrl : 'views/shop/pay.html'
	}).when('/lowstocks', {
		templateUrl : 'views/shop/lowstocks.html'
	}).otherwise({
		redirectTo : "/"
	});
};
routeModule.$inject = [ 'LoginService' ];
routeModule.config(routeConfig);
