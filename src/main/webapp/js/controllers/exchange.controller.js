'user strict';

// 系统设置模块
var exchangeModule = angular.module("exchangeModule", []);

var exchangelistController = function($scope, $http, LoginService) {
	$scope.init = function() {
		$scope.req = {};
		initSystemPage($scope.req);// 初始化分页参数
		$scope.req.agentId = LoginService.agentid;
		$scope.sonlist();
		$scope.list();
	};
	$scope.list = function() {
		$scope.req.page = $scope.req.indexPage;
		$http.post("api/exchangegood/list", $scope.req).success(function(data) { // 绑定
			if (data.code == 1) {
				$scope.exchangeList = data.result.list;
				calcSystemPage($scope.req, data.result.total);// 计算分页
			}
		});
	};
	$scope.search = function() {
		if ($scope.req.startTime != undefined && $scope.req.endTime!= undefined) {
			var arr = new Array();
			var arr2 = new Array();
			var arr = $scope.req.startTime.split("-");
			var arr2 = $scope.req.endTime.split("-");
			var sDate = new Date(arr[0], arr[1] - 1, arr[2]);
			var eDate = new Date(arr2[0], arr2[1] - 1, arr2[2]);
			if (eDate < sDate) {
				alert("开始日期不能大于结束日期！");
				return;
			}
		}
		$scope.req.indexPage = 1;
		$scope.list();
	};

	$scope.sonlist = function() {
		$http.post("api/preparegood/getsonagent", $scope.req).success(function(data) { // 绑定
			if (data.code == 1) {
				$scope.son = data.result;
				$scope.all={id:0,company_name:"全部"};
            	$scope.son.unshift($scope.all);
			}
		});
	};

	$scope.init();

	// 上一页
	$scope.prev = function() {
		if ($scope.req.indexPage > 1) {
			$scope.req.indexPage--;
			$scope.list();
		}
	};

	// 当前页
	$scope.loadPage = function(currentPage) {
		$scope.req.indexPage = currentPage;
		$scope.list();
	};

	// 下一页
	$scope.next = function() {
		if ($scope.req.indexPage < $scope.req.totalPage) {
			$scope.req.indexPage++;
			$scope.list();
		}
	};

	// 跳转到XX页
	$scope.getPage = function() {
		$scope.req.indexPage = Math.ceil($scope.req.gotoPage);
		$scope.list();
	};
};

var exchangeaddController = function($scope, $http, LoginService) {
	$scope.init = function() {
		$scope.req = {};
		$scope.req.agentId = LoginService.agentid;
		$scope.sonlist(0);
	};

	$scope.sonlist = function(type) {
		if (type == 1) {
			$scope.req.keys = $scope.keys1;
		} else if (type == 2) {
			$scope.req.keys = $scope.keys2;
		}
		$http.post("api/preparegood/getsonagent", $scope.req).success(function(data) { // 绑定
			if (data.code == 1) {
				if (type == 0) {
					$scope.son1 = data.result;
					$scope.son2 = data.result;
				} else if (type == 1) {
					$scope.son1 = data.result;
				} else if (type == 2) {
					$scope.son2 = data.result;
				}

			}
		});
	};
	$scope.checkson1 = function(id) {
		$scope.req.fromAgentId = id;
	};
	$scope.checkson2 = function(id) {
		$scope.req.toAgentId = id;
	};

	$scope.check = function() {
		if ($scope.req.serialNum == undefined) {
			alert("请输入终端号!");
			return;
		}
		if ($scope.req.fromAgentId == undefined) {
			alert("请选择下级代理商!");
			return;
		}
		if ($scope.req.toAgentId == undefined) {
			alert("请选择下级代理商!");
			return;
		}
		if ($scope.req.serialNum.trim().length > 11) {
			$http.post("api/exchangegood/checkTerminals", $scope.req).success(function(data) { // 绑定
				if (data.code == 1) {
					if (data.result.errorCount == 0) {
						$scope.add();
					} else {
						$scope.errorlist = data.result.errorList;
						$('.tab').show();
					}
				}
			});
		} else {
			alert("终端号输入不合法!");
		}
	};

	$scope.add = function() {
		$scope.req.web = 1;
		$scope.req.customerId = LoginService.loginid;
		$http.post("api/exchangegood/add", $scope.req).success(function(data) { // 绑定
			if (data.code == 1) {
				window.location.href = "#/exchange"
			}
		});
	};
	$scope.close = function() {
		$('.tab').hide();
	};

	$scope.init();
};

var exchangeinfoController = function($scope, $http, $location, LoginService) {
	$scope.init = function() {
		$scope.req = {};
		$scope.req.id = $location.search()['id'];
		$scope.info();
	};
	$scope.info = function() {
		$http.post("api/exchangegood/info", $scope.req).success(function(data) { // 绑定
			if (data.code == 1) {
				$scope.info = data.result;
			}
		});
	};
	$scope.init();
};

exchangelistController.$inject = [ '$scope', '$http', 'LoginService' ];
exchangeModule.controller("exchangelistController", exchangelistController);
exchangeaddController.$inject = [ '$scope', '$http', 'LoginService' ];
exchangeModule.controller("exchangeaddController", exchangeaddController);
exchangeinfoController.$inject = [ '$scope', '$http', '$location', 'LoginService' ];
exchangeModule.controller("exchangeinfoController", exchangeinfoController);
