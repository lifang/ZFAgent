'user strict';

//系统设置模块
var cs_agentModule = angular.module("cs_agentModule",[]);

var cs_agentController = function ($scope, $http, LoginService) {
	$("#leftRoute").show();
	if(LoginService.loginid == 0){
		window.location.href = '#/login';
	}else{
		//显示用户登录部分
		$scope.$emit('changeshow',false);
	}
	//搜索
	$scope.submitSearch = function(){
		initSystemPage($scope);// 初始化分页参数
		$scope.req = {
			customerId : LoginService.loginid,
			search : $scope.search,
			page : $scope.indexPage,
			rows : $scope.rows
		};
		$http.post("api/cs/agents/search", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result;
                calcSystemPage($scope, data.result.total);// 计算分页
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	//筛选
	$scope.submitScreen = function(){
		initSystemPage($scope);// 初始化分页参数
		$scope.req = {
			customerId : LoginService.loginid,
			search : $scope.search,
			q : $scope.screen,
			page : $scope.indexPage,
			rows : $scope.rows
		};
		$http.post("api/cs/agents/search", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result;
                calcSystemPage($scope, data.result.total);// 计算分页
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	
	$scope.submitPage = function(){
		$scope.req = {
			customerId : LoginService.loginid,
			search : $scope.search,
			q : $scope.screen,
			page : $scope.indexPage,
			rows : $scope.rows
		};
		$http.post("api/cs/agents/search", $scope.req).success(function (data) {  //绑定
			if (data != null && data != undefined) {
				$scope.list = data.result;
				calcSystemPage($scope, data.result.total);// 计算分页
			}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
	//
	$scope.orderlist = function () {
		console.log("进入列表...");
		initSystemPage($scope);// 初始化分页参数
        $scope.req={customerId:LoginService.loginid,
        		page:$scope.indexPage,
        		rows:$scope.rows};
        $http.post("api/cs/agents/getAll", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result;
                console.log("==>"+$scope.list);
                calcSystemPage($scope, data.result.total);// 计算分页
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
    };
    //详情
    $scope.orderinfo=function (p) {
    	LoginService.poscd=p.id;
    	$scope.poscd=p.id;
    	window.location.href = '#/cs_agentinfo';
    };
    //取消
    $scope.cancelApply = function(o){
    	$scope.req={id:o.id};
		$http.post("api/cs/agents/cancelApply", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
            	$scope.orderlist();
//                $scope.list = data.message;
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	//重新提交
	$scope.resubmitCancel = function(o){
		$scope.req={id:o.id};
		$http.post("api/cs/agents/resubmitCancel", $scope.req).success(function (data) {  //绑定
			if (data != null && data != undefined) {
				$scope.orderlist();
//                $scope.list = data.message;
			}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
	
	// 上一页
	$scope.prev = function() {
		if ($scope.indexPage > 1) {
			$scope.indexPage--;
			$scope.submitPage();
		}
	};

	// 当前页
	$scope.loadPage = function(currentPage) {
		$scope.indexPage = currentPage;
		$scope.submitPage();
	};

	// 下一页
	$scope.next = function() {
		if ($scope.indexPage < $scope.totalPage) {
			$scope.indexPage++;
			$scope.submitPage();
		}
	};

	// 跳转到XX页
	$scope.getPage = function() {
		$scope.indexPage = Math.ceil($scope.gotoPage);
		$scope.submitPage();
	};
	
    $scope.orderlist();
};

cs_agentModule.controller("cs_agentController", cs_agentController);
