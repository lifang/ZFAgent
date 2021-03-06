'use strict';

// 主页面路由模块，用于控制主页面的菜单导航(注入了登陆服务LoginService)
var routeModule = angular.module("routeModule", [ 'loginServiceModule',
		'ngRoute' ]);

// 路由器的具体分发
function routeConfig($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'views/index/login.html'                //'views/index/myapp.html'
	}).when('/myapp', {
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
	}).when('/wholesaleOrder', {
		templateUrl : 'views/order/wholesaleOrder.html'
	}).when('/proxyOrder', {
		templateUrl : 'views/order/proxyOrder.html'
	}).when('/wholesaleOrderinfo', {
		templateUrl : 'views/order/wholesaleOrderinfo.html'
	}).when('/proxyOrderinfo', {
		templateUrl : 'views/order/proxyOrderinfo.html'
	}).when('/email_up', {
		templateUrl : 'views/systemset/up_email.html'
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
		templateUrl : 'views/index/register.html'
	}).when('/registerSucc', {
		templateUrl : 'views/index/registerSucc.html'
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
	}).when('/address', {
		templateUrl : 'views/systemset/address.html'
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
	}).when('/topay', {
		templateUrl : 'views/order/topay.html'
	}).when('/order_pay', {
		templateUrl : 'views/order/order_pay.html'
	}).when('/deposit_pay', {
		templateUrl : 'views/order/deposit_pay.html'
	}).when('/lowstocks', {
		templateUrl : 'views/shop/lowstocks.html'
	}).when('/noshop', {
		templateUrl : 'views/shop/noshop.html'
	}).when('/manageuser', {
		templateUrl : 'views/customer/manageuser.html'
	}).when('/changeemail', {
		templateUrl : 'views/systemset/changeemail.html'
	}).when('/commercialOne', {
		templateUrl : 'views/customer/viewCustomer.html'
	}).when('/posprofit', {
		templateUrl : 'views/posprofit/posprofit.html' 
	}).when('/messageinfo', {
		templateUrl : 'views/management/messageDetails.html'
	}).when('/web', {
		templateUrl : 'views/index/webinfo.html'
	}).when('/msg', {
		templateUrl : 'views/index/msginfo.html'
	}).when('/dataBaseCopy', {
		templateUrl : 'views/agent/dataBaseCopy.html'
	}).when('/app', {
		templateUrl : 'views/index/app.html'
	}).otherwise({
		redirectTo : '/'
	});
};
routeModule.$inject = [ 'LoginService' ];
routeModule.config(routeConfig).run([ '$rootScope', '$http', function($rootScope, $http) {
	$rootScope.global = {};
	$rootScope.global.title = "华尔街金融平台-";
	$rootScope.global.headTitle = $rootScope.global.title + "最全最优质POS选购平台";
} ]);