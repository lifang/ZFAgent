'user strict';

//系统设置模块
var cs_cencelinfoModule = angular.module("cs_cencelinfoModule",[]);

var cs_cencelinfoController = function ($scope,$location, $http, LoginService) {
	$("#leftRoute").show();
	if(LoginService.agentUserId == 0){
		window.location.href = '#/login';
	}else{
		//显示用户登录部分
		$scope.$emit('changeshow',false);
	}
	$scope.req={};
	$scope.req.id=$location.search()['infoId'];
    $scope.getInfo = function () {
    	$http.post("api/cs/cancels/getCanCelById", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.info=data.result;
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
    };
    //取消
    $scope.cancelApply = function(o){
    	if(window.confirm('你确定要取消吗？')){
    		$scope.req={id:o.id};
    		$http.post("api/cs/cancels/cancelApply", $scope.req).success(function (data) {  //绑定
    			if (data != null && data != undefined) {
    				$scope.getInfo();
//                $scope.list = data.message;
    			}
    		}).error(function (data) {
    			$("#serverErrorModal").modal({show: true});
    		});
    		
            return true;
         }else{
            return false;
        }
	};
	//重新提交
	$scope.resubmitCancel = function(o){
		if(window.confirm('您确定要重新提交吗？')){
			$scope.req={id:o.id};
			$http.post("api/cs/cancels/resubmitCancel", $scope.req).success(function (data) {  //绑定
				if (data != null && data != undefined) {
//                $scope.list = data.message;
					$scope.getInfo();
				}
			}).error(function (data) {
				$("#serverErrorModal").modal({show: true});
			});
    		
            return true;
         }else{
            return false;
        }
	};
    $scope.getInfo();

};

cs_cencelinfoModule.controller("cs_cencelinfoController", cs_cencelinfoController);
