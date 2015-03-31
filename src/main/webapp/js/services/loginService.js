'use strict';

//登陆服务模块
var loginServiceModule = angular.module("loginServiceModule", []);


var loginService = function ($http, $rootScope, $cookieStore) {
    return {
        //定义当前用户是否被授权
        //isAuthorized: typeof($cookieStore.get("loginInfo")) == 'undefined' ? false : true,
    	isAuthorized:true,
    	
    	//当前登陆的用户名
        loginAgentName: typeof($cookieStore.get("loginAgentName")) == 'undefined' ? "" : $cookieStore.get("loginAgentName"),
        agentid:1,// typeof($cookieStore.get("loginAgentId")) == 'undefined' ? 0 : $cookieStore.get("loginAgentId"),
        loginid:1,// typeof($cookieStore.get("loginAgentId")) == 'undefined' ? 0 : $cookieStore.get("loginAgentId"),
        city:1,
        goods: [],
        tradeTypeId: 0,
      //代理商登陆功能
        agentLogin: function ($scope,$http) {
   		 	if($scope.agent.agentName == undefined){
   		 		$scope.agentNameClass = true;
   		 	}else if($scope.agent.agentPass == undefined){
   		 		$scope.agentPassClass = true;
   		 	}else{
   		 	$http.post("api/agent/sizeUpImgCode", {imgnum:$scope.agent.agentCode}).success(function(data){
      			 if(data.code == -1){
      				$scope.agentImgClass = true;
      			 }else{
      				 $http.post("api/agent/agentLoginWeb", {username:$scope.agent.agentName,password:$scope.agent.agentPass}).success(function (data) {  //绑定
      			           if(data.code == -1){//用户或者密码错误！
      			        	$scope.agentNameMessage = data.message; 
      			        	$scope.agentNameClass = true;
      			           }else{
      			        	   $scope.nameMessag = "";
      			        	   $scope.code = "";
      			        	   //记住密码
      			        	   if($scope.agentRememberPass == true){
      			        		   $cookieStore.put("agentPass",data.result.password);
      			        	   }else{
      			        		   $cookieStore.remove("agentPass");
      			        	   }
      			        	   $cookieStore.put("loginAgentName",data.result.username);//用户名
      			        	   $cookieStore.put("loginAgentId",data.result.id);//用户id
      			        	   $cookieStore.put("agentIsHaveProfit",data.result.is_have_profit);//是否有分润
      			        	   $cookieStore.put("agentTypes",data.result.types);//用户类型
      			        	   $cookieStore.put("agentParentId",data.result.parent_id);//是否为一级代理商
      			        	   $cookieStore.put("agentId",data.result.agentId);//代理商Id
      			        	   //刷新
      			        	   $scope.message = data.message; //登陆成功，跳转页面
      			        	   window.location.href = '#/';
      			        	//location.reload();
      			           }
      			        }).error(function (data) {
      			        	$scope.message = "登陆异常！"
      			        });
      			 }
      		 }).error(function(data){
      			 $scope.message = "获取验证码失败！"
      		 });
   		 	}
        	

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