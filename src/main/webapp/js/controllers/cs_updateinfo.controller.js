'user strict';

//系统设置模块
var cs_updateinfoModule = angular.module("cs_updateinfoModule",[]);

var cs_updateinfoController = function ($scope,$location, $http, LoginService) {
	$("#leftRoute").show();
	if(LoginService.userid == 0){
		window.location.href = '#/login';
	}else{
		//显示用户登录部分
		$scope.$emit('changeshow',false);
	}
	$scope.req={};
	$scope.req.id=$location.search()['infoId'];
    $scope.getInfo = function () {
    	$http.post("api/update/info/getInfoById", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.info=data.result;
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
    };
    //取消
    $scope.cancelApply = function(o){
    	$scope.req={id:o.id};
		$http.post("api/update/info/cancelApply", $scope.req).success(function (data) {  
            if (data != null && data != undefined) {
            	$scope.getInfo();
//                $scope.list = data.message;
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
				$scope.getInfo();
//                $scope.list = data.message;
			}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
    $scope.getInfo();

};

cs_updateinfoModule.controller("cs_updateinfoController", cs_updateinfoController);
