
var agentLoginController = function($scope, $location, $http, LoginService){
	 
	if(LoginService.agentid>0){
		window.location.href = '#/';
	}
	//定义代理商对象
	$scope.agent = {};
	//勾选记住密码
	$scope.agentRememberPass = false;
	//代理商登陆
	$scope.agentLogin = function() {
		LoginService.agentLogin($scope,$http);
	};
	
	//跳转代理商注册页面
	$scope.goToRegister = function(){
		//移除样式
		//$("link[href='style/global.css']").remove();
		window.location.href = '#/register';
	}
	
	// 初始化图片验证码
	$scope.reGetRandCodeImg = function() {
			$scope.agent.agentName = getCookie("agentName");
		    $scope.agent.agentPass = getCookie("agentPass");  
		$(".loginRandCodeImg").attr("src", "api/agent/getRandCodeImg?id=" + Math.random());
	};
	
	$scope.reGetRandCodeImg();
}


//读取cookies
function getCookie(name)
{
var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
if(arr=document.cookie.match(reg)) return unescape(arr[2]);
else return null;
}
indexModule.controller("agentLoginController", agentLoginController);