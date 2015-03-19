'use strict';

/*angular.element(window).bind('load', function() {
    //alert('1');
});*/

//主页面模块
var indexModule = angular.module("indexModule", ['loginServiceModule','routeModule', 'ngCookies',  'ngCsv']);

indexModule.factory('myInterceptor', [function() {
    var requestInterceptor = {
        request: function(config) {
            $("#ajaxLoading").show();
            return config;
        },
        response: function(response) {
            $("#ajaxLoading").hide();
            var responseData = response.data;
            if(responseData != null && responseData != "" && responseData.resultCode=="401") {
                $("#loginModal").modal({keyboard:false,backdrop:'static'});
                return response;
            }
            if(responseData=="NOT_AUTHORIZED") {
                $("#loginModal").modal({keyboard:false,backdrop:'static'});
                return response;
            }
            return response;
        }
    };
    return requestInterceptor;
}]);
indexModule.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('myInterceptor');
}]);



//登陆功能控制器
var LoginController = function ($scope, LoginService) {
    $scope.user = {loginAuto: false};
    $scope.login = function () {
        LoginService.login($scope.user);
    };
    
    $scope.$on('Login.Success', function (event) {
       $scope.user.pwd = "";
    });

    $scope.emptyPwd = function() {
        $("#userPwd").val("");
        $scope.user.pwd = "";
    };

    $scope.resestOpen = function () {
        $("#loginModal").modal('hide');
        $("#resetPwdModal").modal('show');
    };
    $scope.registerNow = function () {
        $("#loginModal").modal('hide');
        $("#registerModal").modal('show');
    };
};




//重置密码控制器
var ResetPwdController = function ($scope, $http, $timeout) {
    $scope.DynapwdMessage = "获取验证码";//获取验证码按钮显示信息
    $scope.user = {};
    $scope.user.mobile = "";
    $scope.user.pwd = "";


    $scope.timeInMs = 60;
    var countUp = function () {
        if ($scope.timeInMs >= 0) {
            $scope.DynapwdMessage = $scope.timeInMs + " 秒";
            $scope.timeInMs--;
            $timeout(countUp, 1000);
        } else {
            $scope.DynapwdMessage = "获取验证码";
            $("#resetPwdBtn").attr("disabled",false);
            $scope.timeInMs = 60;
        }
    };

    $scope.startTimer = function () {
        $("#resetPwdBtn").attr("disabled",true);
        $timeout(countUp, 1000);
    };
    $("#resetPwdBtn").attr("disabled",true);
    $scope.checkAgree=function () {//鼠标进入范围开始验证
    	 $("#reset-danger-msg").hide();
    	 var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
         var tel = $scope.user.mobile;//电话
         if (reg.test(tel)) {
    		 $("#resetPwdBtn").attr("disabled",false);
    	}else{
    		 $("#resetPwdBtn").attr("disabled",true);
    	}
    	
    	if(!reg.test(tel)&& $scope.user.mobile.length!=0){
    		 $("#resetPwdBtn").attr("disabled",true);
   		 $scope.user.errorMessage = "请输入正确的手机号和密码";//错误信息
            $("#reset-danger-msg").show();
    	}else if ($scope.user.pwdCheck != $scope.user.pwd && $scope.user.pwd.length!=0) {
    		 $("#resetPwdBtn").attr("disabled",true);
           $scope.user.errorMessage = "两次密码不一致";//错误信息
           $("#reset-danger-msg").show();
       } else if ($scope.user.pwd.length < 6 && $scope.user.pwd.length!=0) {
    	   $("#resetPwdBtn").attr("disabled",true);
           $scope.user.errorMessage = "密码过于简单,请输入6~12位密码！";//错误信息
           $("#reset-danger-msg").show();
       } else if ($scope.user.pwd.length > 12) {
    	   $("#resetPwdBtn").attr("disabled",true);
           $scope.user.errorMessage = "密码太长,请输入6~12位密码！";//错误信息
           $("#reset-danger-msg").show();
       } ;
    };
    $scope.getResetDynapwd = function () {
        $scope.UserInfo = {};
        $scope.UserInfo.mobile = $scope.user.mobile;//电话
        $scope.UserInfo.passwd = $scope.user.pwd;//密码

        if ($scope.timeInMs == 60) {
            $http.post("rest/reset/getRestDynapwd", $scope.UserInfo).success(function (data) {
                $scope.dynapwdState = $.parseJSON(data.svccont);
                if ($scope.dynapwdState.strStr == "1003") {
//	     			alert("下发成功");
                }
                if ($scope.dynapwdState.strStr == "1013") {
//	     			alert("手机号码已注册");
                }
            }).error(function (data) {
                    console.log("获取验证信息失败！");
                });
            $scope.startTimer();
        }
        ;
    };

     $scope.close = function () {//点击小X事件
//    	 $("#registerModal").modal('show');
         $("#loginModal").modal({keyboard:false,backdrop:'static'});
         $("#resetPwdModal").modal('hide');
    };
        $scope.resetPassword = function () {
            $scope.BizUserFindPwdReq = {};
            $scope.BizUserFindPwdReq.mobile = $scope.user.mobile;//电话
            $scope.BizUserFindPwdReq.npwd = $scope.user.pwd;//密码
            $scope.BizUserFindPwdReq.dongPwd = $scope.user.dynapwd;//验证码

            var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
            var tel = $scope.user.mobile;//电话
            if (!reg.test(tel)) {
                $scope.user.errorMessage = "请输入正确的手机号和密码";//错误信息
                $("#reset-danger-msg").show();
            } else if ($scope.user.pwdCheck != $scope.user.pwd) {
                $scope.user.errorMessage = "两次密码不一致";//错误信息
                $("#reset-danger-msg").show();
            } else if ($scope.user.pwd.length < 6) {
                $scope.user.errorMessage = "密码过于简单,请输入6~12位密码！";//错误信息
                $("#reset-danger-msg").show();
            } else if ($scope.user.pwd.length > 12) {
                $scope.user.errorMessage = "密码太长,请输入6~12位密码！";//错误信息
                $("#reset-danger-msg").show();
            } else if ($scope.user.dynapwd == null) {
                $scope.user.errorMessage = "请输入验证码";//错误信息
                $("#reset-danger-msg").show();
            } else {
            	if ($scope.timeInMs < 61) {//在验证码生效时间内则可以执行
	                $http.post("rest/reset/resetPwd", $scope.BizUserFindPwdReq).success(function (data) {
	                    $scope.StateInfo = $.parseJSON(data.svccont);
	                    if ($scope.StateInfo.intNum == 1000) {//成功
	                        $('#resetPwdModal').modal('hide');//隐藏重置密码框
	                        $('#loginModal').modal({keyboard:false,backdrop:'static'});//显示登陆框
	                    } else if ($scope.StateInfo.intNum == -1000) {//失败
	                        $scope.DynapwdMessage = "获取验证码";
	                    } else if ($scope.StateInfo.intNum == 1001) {//验证码过期
	                        $scope.user.errorMessage = "验证码已过期";
	                    } else if ($scope.StateInfo.intNum == 1009) {//不存在用户 或 验证码错误
	                        $scope.user.errorMessage = "不存在用户 或 验证码错误";
	                    }
	                }).error(function (data) {
	                        console.log("注册失败！");
	                    });
            	};
            };
            	 if ($scope.timeInMs < 60) {//在验证码生效时间内则可以执行
                $http.post("rest/reset/resetPwd", $scope.BizUserFindPwdReq).success(function (data) {
                    $scope.StateInfo = $.parseJSON(data.svccont);
                    if ($scope.StateInfo.intNum == 1000) {//成功
                        $('#resetPwdModal').modal('hide');//隐藏重置密码框
                        $('#loginModal').modal({keyboard:false,backdrop:'static'});//显示登陆框
                    } else if ($scope.StateInfo.intNum == -1000) {//失败
                        $scope.DynapwdMessage = "获取验证码";
                    } else if ($scope.StateInfo.intNum == 1001) {//验证码过期
                        $scope.user.errorMessage = "验证码已过期";
                    } else if ($scope.StateInfo.intNum == 1009) {//不存在用户 或 验证码错误
                        $scope.user.errorMessage = "不存在用户 或 验证码错误";
                    }
                }).error(function (data) {
                        console.log("注册失败！");
                    });
            	 };
            };
};




indexModule.$inject = ['$scope', '$http', '$rootScope', 'LoginService'];
indexModule.controller('LoginController', LoginController);
indexModule.controller('ResetPwdController', ResetPwdController);
