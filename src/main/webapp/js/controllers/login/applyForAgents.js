var registerAgentController = function($scope, $location, $http, LoginService) {

	var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;// 手机格式

	$scope.agent = {};// 初始化代理商对象
	$scope.registreTime = true;// 初始化重新发送验证码
	window.clearInterval(window.agentSendCode);// 清除倒计时

	$scope.agentInit = function() {

		$("#headDiv_index").removeClass();
		$("#headDiv_index").css("padding", "20px 0");
		$("#headDiv_index").css("border-bottom", "2px #0071cf solid");
		$("#headDiv_index").css("background", "rgba(255,255,255,0.8)");
		$("#agentDiv_index").html("申请成为代理商");
		$scope.getShengcit();// 获得省级

	};

	// 获取手机验证码
	$scope.getAgentRegisterCode = function() {
		if (!reg.test($scope.agent.phone)) {
			alert("请输入合法手机号！");
		} else if ($scope.registreTime == true) {
			window.clearInterval(window.agentSendCode);
			$scope.registreTime = false;
			$http.post("api/agent/sendPhoneVerificationCodeReg", {
				codeNumber : $scope.agent.phone
			}).success(function(data) {
				if (data.code == 1) {
					$scope.code = data.result;
					setCookie("agent_send_phone_code", $scope.code);
					$scope.intDiff = 120;
					window.agentSendCode = window.setInterval(function() {
						if ($scope.intDiff == 0) {
							$('#time_show_agent').html("获取验证码！");
							$scope.registreTime = true;
							window.clearInterval(window.agentSendCode);
						} else {
							$('#time_show_agent').html("重新发送（" + $scope.intDiff + "秒）");
							$scope.intDiff--;
						}
					}, 1000);
				} else {
					$scope.registreTime = true
					alert(data.message);
				}
			})
		}
	};

	// 提交代理商注册
	$scope.addAgentRegister = function() {
		if ($scope.agent.name == undefined || $scope.agent.name == "") {
			alert("请输入负责人姓名！");
			return false;
		} else if (strlen($scope.agent.name) > 20) {
			alert("负责人姓名需在10个中文字符或20个英文字符");
			return false;
		} else if ($scope.agent.phone == undefined || $scope.agent.phone == "") {
			alert("请输入手机号码！");
			return false;
		} else if (!reg.test($scope.agent.phone)) {
			alert("请输入合法手机号！");
			return false;
		} else if ($scope.agselect == undefined) {
			alert("请选择城市！");
			return false;
		} else if ($scope.agcitis == undefined) {
			alert("请选择城市！");
			return false;
		} else if (getCookie("agent_send_phone_code") != $scope.agent.code) {
			alert("验证码错误！");
			return false;
		}
		$scope.addAgent();
	}

	// 注册代理商
	$scope.addAgent = function() {
		$scope.agent.cityId = Math.ceil($scope.agcitis.id);
		$http.post("api/agent/applyForAgents", $scope.agent).success(function(data) {
			if (data.code == 1) {
				$scope.codeNumber = "";
				$scope.code = "";
				$scope.codeBei = "";
				window.location.href = '#/login';
			} else if (data.code == -1) {
				alert(data.message);
			}
		})
	};
	// 获得省级
	$scope.getShengcit = function() {
		$http.post("api/comment/getCity").success(function(data) {
			if (data.code == 1) {
				$scope.cities = data.result;
			} else {
				alert("城市加载失败！");
			}
		})
	};

	// 写cookies
	function setCookie(name, value) {
		var Days = 30;
		var exp = new Date();
		exp.setTime(exp.getTime() + 30 * 60 * 1000);
		document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
	}
	// 读取cookies
	function getCookie(name) {
		var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
		if (arr = document.cookie.match(reg))
			return unescape(arr[2]);
		else
			return null;
	}
	// 删除cookies
	function delCookie(name) {
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval = getCookie(name);
		if (cval != null)
			document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
	}

	$scope.agentInit();
};

indexModule.controller("registerAgentController", registerAgentController);