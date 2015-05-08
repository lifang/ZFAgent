'user strict';

//系统设置模块
var cs_updateModule = angular.module("cs_updateModule",[]);

var cs_updateController = function ($scope, $http, LoginService) {
	$("#leftRoute").show();
	if(LoginService.agentUserId == 0){
		window.location.href = '#/login';
	}else{
		//显示用户登录部分
		$scope.$emit('changeshow',false);
		//左侧样式调整
		$("#left_common li").unbind("click").bind("click", function(){
			$(this).children('a').addClass("hover");
			$(this).siblings().children('a').removeClass("hover");
			if (!$(this).hasClass("second") ){ //判断是否有子节点
				if ( !$(this).parents().hasClass("second") ){
					$(".second").children('ol').children('li').children('a').removeClass("hover");
				}
			}
	   });
		
		/*------用户后台导航菜单--------*/
		$("li.second > a").click(function(){
			$(this).parent().find("ol").toggle();
			if(!$(this).parent().find("ol").is(":visible")){
				$(this).find("i").removeClass("on").addClass("off");
			}else{
				$(this).find("i").removeClass("off").addClass("on");
			}
		});
	}
	//搜索
	$scope.submitSearch = function(){
		initSystemPage($scope);// 初始化分页参数
		$scope.req={customerId:LoginService.agentUserId,search:$scope.search,
				page : $scope.indexPage,
				rows : $scope.rows};
		$http.post("api/update/info/search", $scope.req).success(function (data) {  //绑定
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
		$scope.req={customerId:LoginService.agentUserId,search:$scope.search,q:$scope.screen,
				page : $scope.indexPage,
				rows : $scope.rows};
		$http.post("api/update/info/search", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result;
                calcSystemPage($scope, data.result.total);// 计算分页
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	$scope.submitPage = function(){
		$scope.req={customerId:LoginService.agentUserId,search:$scope.search,q:$scope.screen,
				page : $scope.indexPage,
				rows : $scope.rows};
		$http.post("api/update/info/search", $scope.req).success(function (data) {  //绑定
			if (data != null && data != undefined) {
				$scope.list = data.result;
				calcSystemPage($scope, data.result.total);// 计算分页
			}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
	//订单列表
	$scope.orderlist = function () {
		initSystemPage($scope);// 初始化分页参数
        $scope.req={customerId:LoginService.agentUserId,
				page : $scope.indexPage,
				rows : $scope.rows};
        $http.post("api/update/info/getAll", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result;
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
    	window.location.href = '#/cs_updateinfo';
    };
    //取消
    $scope.cancelApply = function(o){
    	$scope.req={id:o.id};
		$http.post("api/update/info/cancelApply", $scope.req).success(function (data) {  
            if (data != null && data != undefined) {
            	$scope.orderlist();
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	//重新提交
	$scope.resubmitCancel = function(o){
		$scope.req={id:o.id};
		$http.post("api/update/info/resubmitCancel", $scope.req).success(function (data) {   
			if (data != null && data != undefined) {
				$scope.orderlist();
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

cs_updateModule.controller("cs_updateController", cs_updateController);
