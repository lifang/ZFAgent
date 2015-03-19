'use strict';

//登陆服务模块
var loginServiceModule = angular.module("loginServiceModule", []);


var loginService = function ($http, $rootScope, $cookieStore) {
    return {
        //定义当前用户是否被授权
        //isAuthorized: typeof($cookieStore.get("loginInfo")) == 'undefined' ? false : true,
    	isAuthorized:true,
    	//当前登陆的用户名
        loginUserName: typeof($cookieStore.get("loginUserName")) == 'undefined' ? "" : $cookieStore.get("loginUserName"),
		agentid: 1,//typeof($cookieStore.get("loginUserId")) == 'undefined' ? 0 : $cookieStore.get("loginUserId"),
        city:1,
        goods: [],
        tradeTypeId: 0,
        //用户登陆功能
        login: function ($scope,$http) {
   		 $http.post("api/user/sizeUpImgCode", {imgnum:$scope.code}).success(function(data){
   			 if(data.code == -1){
   				$scope.imgMessage = data.message;
   				$scope.imgClass = true;
   			 }else{
   				 $http.post("api/user/studentWebLogin", {username:$scope.username,password:$scope.password1}).success(function (data) {  //绑定
   			           if(data.code == -1){//用户或者密码错误！
   			        	$scope.nameMessage = data.message; 
   			        	$scope.unameClass = true;
   			           }else{
   			        	   $scope.nameMessag = "";
   			        	   $scope.code = "";
   			        	   //记住密码
   			        	   if($scope.RememberPass == true){
   			        		   $cookieStore.put("loginPass",data.result.password);
   			        	   }else{
   			        		   $cookieStore.remove("loginPass");
   			        	   }
   			        	   $cookieStore.put("loginUserName",data.result.username);
   			        	   $cookieStore.put("loginUserId",data.result.id);
   			        	   //刷新
   			        	   
   			        	   $scope.message = data.message; //登陆成功，跳转页面
   			        	   window.location.href = '#/';
   			        	location.reload();
   			           }
   			        }).error(function (data) {
   			        	$scope.message = "登陆异常！"
   			        });
   			 }
   		 }).error(function(data){
   			 $scope.message = "获取验证码失败！"
   		 });
        	

        },

        //用户登出功能
        logout: function () {
            $cookieStore.remove("loginInfo");
            $cookieStore.remove("loginUserName");
            $cookieStore.remove("loginSmsPauseFlag");
            $cookieStore.remove("shopLogo");
            $cookieStore.remove("shopName");
            $cookieStore.remove("userCoverPicCD");
            this.isAuthorized = false;
            $("#loginModal").modal({keyboard:false,backdrop:'static'}); //登出之后，则显示登陆界面，并隐藏主页面
            $("#resetPwd-success-msg").hide();
            $("#indexDiv").hide();
        },
        
        //创建订单传值
        tomakeorder: function (val) {
            var self = this;
            self.goods=val;
        },
        //隐藏所有
        hideAll: function () {
        	$('#login').hide();
    		$('#findPassOne').hide();
    		$('#findPassTwo').hide();
    		$('#findPassThree').hide();
    		$('#retrieveHtml').hide();
    		$('#emailRetrieveHtml').hide();
    		$('#maintop').hide();
    		$('#maintopTwo').hide();
    		$('#headClear').hide();
    		$('#shopmain').hide();
    		$('#mainuser').hide();
    		$('#mainindex').hide();
        },
       
        
        //检验当前是否为已登录状态，或Cookie中仍存在登陆记录
        checkAuthorization: function () {
            var self = this;
            //必须从Cookie中校验(Cookie)
            this.isAuthorized = typeof($cookieStore.get("loginInfo")) == 'undefined' ? false : true,
                //根据是否已登录，设置登陆窗口和主页面的显示与否
                $("#loginModal").modal(this.isAuthorized == true ? 'hide' : {keyboard:false,backdrop:'static'});
            if (this.isAuthorized == true) {
                self.mobile = $cookieStore.get("loginInfo");
                self.fullName = $cookieStore.get("loginUserName");
               // self.loginSmsPauseFlag = $cookieStore.get("loginSmsPauseFlag");
                self.shopLogo = $cookieStore.get("shopLogo");
               // self.shopName = $cookieStore.get("shopName");
                //self.userCoverPicCD = $cookieStore.get("userCoverPicCD");
                $("#indexDiv").show();
            } else {
                $("#indexDiv").hide();
            }
        }
    };
};

loginServiceModule.$inject = ['$http', '$rootScope', '$cookieStore'];
loginServiceModule.service("LoginService", loginService);