'use strict';

//登陆服务模块
var loginServiceModule = angular.module("loginServiceModule", []);


var loginService = function ($http, $rootScope, $cookieStore) {
    return {
    	//当前登陆的用户名
        loginAgentName: typeof($cookieStore.get("loginAgentName")) == 'undefined' ? "" : $cookieStore.get("loginAgentName"),
        //代理商id
        agentid: typeof($cookieStore.get("agentId")) == 'undefined' ? 0 : $cookieStore.get("agentId"),
        //登录用户id
		loginid: typeof($cookieStore.get("loginId")) == 'undefined' ? 0 : $cookieStore.get("loginId"),
		//身份 1一级代理商   2二级代理商 3普通用户  by yyb
		identity:typeof($cookieStore.get("identity")) == 'undefined' ? 0 : $cookieStore.get("identity"),
		toptop:typeof($cookieStore.get("agentParentId")) == 'undefined' ? 1 : $cookieStore.get("agentParentId"),
        //代理商用户id
        agentUserId:typeof($cookieStore.get("agentUserId")) == 'undefined' ? 0 : $cookieStore.get("agentUserId"),//
		//城市
        city:typeof($cookieStore.get("cityId")) == 'undefined' ? 0 : $cookieStore.get("cityId"),
		//是否有分润
		isHaveProfit:typeof($cookieStore.get("agentIsHaveProfit")) == 'undefined' ? 0 : $cookieStore.get("agentIsHaveProfit"),
		//权限
		machtigingen:typeof($cookieStore.get("machtigingen")) == 'undefined' ? [] : $cookieStore.get("machtigingen"),
        goods: [],
        tradeTypeId: 0,
      //代理商登陆功能
        agentLogin: function ($scope,$http) {
        	$scope.agent.agentName = $("#cookieName").val();
        	$scope.agent.agentPass = $("#cookiePass").val();
   		 	if($scope.agent.agentName == undefined || $scope.agent.agentName == ""){
   		 		$scope.agentNameClass = true;
   		 	}else if($scope.agent.agentPass == undefined || $scope.agent.agentPass == ""){
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
      			        		 setCookie("agentPass",$scope.agent.agentPass);
      			        		 setCookie("agentName",$scope.agent.agentName);
      			        	   }else{
      			        		 delCookie("agentPass");
      			        		 delCookie("agentName");
      			        	   }
      			        	   $cookieStore.put("loginAgentName",data.result.username);//用户名
      			        	   $cookieStore.put("loginId",data.result.id);//登陆用户id
      			        	   $cookieStore.put("agentIsHaveProfit",data.result.is_have_profit?2:1);//是否有分润
      			        	   $cookieStore.put("agentTypes",data.result.types);//用户类型
      			        	   $cookieStore.put("agentParentId",data.result.parent_id);//是否为一级代理商
      			        	   if(data.result.types==6){
      			        		 $cookieStore.put("identity",3);
      			        	   }else{
      			        		 if(data.result.parent_id==0){
      			        			 $cookieStore.put("identity",1);
      			        		 }else{
      			        			 $cookieStore.put("identity",2);
      			        		 }
      			        	   }
      			        	   $cookieStore.put("agentId",data.result.agentId);//代理商Id
      			        	   $cookieStore.put("agentUserId",data.result.agentUserId);//代理商用户ID
      			        	   $cookieStore.put("cityId",data.result.agentCityId);//代理商用户对应城市
      			        	   $cookieStore.put("machtigingen",data.result.machtigingen);//权限
      			        	   //刷新
//    			        	   window.location.href = '#/';
//      			        	   $scope.message = data.message; //登陆成功，跳转页面
      			        	   location.reload();
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
            $cookieStore.remove("loginAgentName");
            $cookieStore.remove("loginId");
            $cookieStore.remove("agentIsHaveProfit");
            $cookieStore.remove("agentTypes");
          //  $cookieStore.remove("agentParentId");
            $cookieStore.remove("identity");
            $cookieStore.remove("agentId");
            $cookieStore.remove("agentUserId");
            $cookieStore.remove("cityId");
            $cookieStore.remove("machtigingen");
            location.reload();
            window.location.href = '#/';
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

//写cookies
function setCookie(name,value)
{
var Days = 30;
var exp = new Date(); 
exp.setTime(exp.getTime() + 30*60*1000);
document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

//删除cookies
function delCookie(name)
{
var exp = new Date();
exp.setTime(exp.getTime() - 1);
var cval=getCookie(name);
if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
loginServiceModule.$inject = ['$http', '$rootScope', '$cookieStore'];
loginServiceModule.service("LoginService", loginService);