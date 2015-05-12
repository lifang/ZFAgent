
var agentLoginController = function($scope, $location, $http, LoginService){
	 
	if(LoginService.loginid>0&&LoginService.agentid>0){
		window.location.href = '#/myapp';
	}

	//定义代理商对象
	$scope.agent = {};
	//勾选记住密码
	$scope.agentRememberPass = false;
	//
	//代理商登陆
	$scope.agentLogin = function() {
		LoginService.agentLogin($scope,$http);
	};
	
	$scope.letitgo = function(url) {
		window.open(url+id);
};
	
	//删除错误提示消息
	$scope.deleteerror = function(){
		$scope.agentNameClass = false;
		$scope.agentNameMessage = null;
	}
	$scope.deleteerrord = function(){
		$scope.agentNameClass = false;
		$scope.agentPassClass = false;
		$scope.agentNameMessage = null;
	}
	//跳转代理商注册页面
	$scope.goToRegister = function(){
		//移除样式
		//$("link[href='style/global.css']").remove();
		window.location.href = '#/register';
	}
	
	// 初始化图片验证码
	$scope.reGetRandCodeImg = function() {
		$(".loginRandCodeImg").attr("src", "api/agent/getRandCodeImg?id=" + Math.random());
	};
	
	//cooke初始化
	$scope.cookeStark = function(){
		$scope.agent.agentName = getCookie("agentName");
	    $scope.agent.agentPass = getCookie("agentPass");  
	}
	
	//跳转普通用户登陆页面
	$scope.goToUserLogin = function(){
		$http.post("api/agent/goToUserLogin").success(function(data){
			if(data.code == 1){
				window.location.href = data.result;
			}
			if(data.code == -1){
				alert(data.message);
			}
		})
	}
	
      //登陆回车事件 
	 document.onkeydown=function(event){
         var e = event || window.event || arguments.callee.caller.arguments[0];
          if(e && e.keyCode==13){ // enter 键
              //要做的事情
        	  $('#denglu').click();
         }
     }; 
	$scope.reGetRandCodeImg();
	$scope.cookeStark();
}


//读取cookies
function getCookie(name)
{
var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
if(arr=document.cookie.match(reg)) return unescape(arr[2]);
else return null;
}
indexModule.controller("agentLoginController", agentLoginController);