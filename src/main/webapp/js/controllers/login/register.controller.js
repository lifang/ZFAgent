var registerAgentController = function($scope, $location, $http, LoginService) {
	// 检验邮箱格式
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	// 手机格式
	var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
	// 英文数字校验
	var numCh = /[^a-zA-Z0-9]/g;

	var cardReg = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;// 身份证号码验证
	var licenseCodeReg = /^[1-9]d*$/;// 验证营业执照为数字
	// 初始化代理商对象
	$scope.agent = {};
	// 单选按钮初始化（1.公司 2.个人）
	$scope.agent.types = 2;
	// 初始化重新发送验证码
	$scope.registreTime = true;
	// 清除倒计时
	window.clearInterval(window.agentSendCode);
	// 勾选协议
	$scope.ridel_xy = false;

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
		} else if ($scope.agent.card == undefined || $scope.agent.card == "") {
			alert("请输入负责人身份证！");
			return false;
		} else if (!cardReg.test($scope.agent.card)) {
			alert("身份证含有非法字符！");
			return false;
		}

		// 如果公司名称为空
		if ($scope.agent.companyName == undefined || $scope.agent.companyName == "" || $scope.agent.companyName == null) {
			$scope.agent.types = 2;// 如果公司名称为空，个人必须选中
		} else if ($scope.agent.companyName != undefined && $scope.agent.companyName != "" && $scope.agent.companyName != null) {
			// alert($scope.agent.types);
			if ($scope.agent.types == 2) {// 如果是个人
				$("#radio1").removeAttr("checked");
				$("#radio2").prop("checked", "checked");// 选中公司
			}

			if (strlen($scope.agent.companyName) > 40) {
				alert("公司名称20个中文字符或40个英文字符");
				return false;
			} else {
				// 如果营业执照为空
				if ($scope.agent.licenseCode == undefined || $scope.agent.licenseCode == "" || $scope.agent.licenseCode == null) {
					alert("营业执照不能为空");
					return false;
				} else if ($scope.agent.licenseCode != undefined && $scope.agent.licenseCode != "" && $scope.agent.licenseCode != null) {
					//
					if (!licenseCodeReg.test($scope.agent.licenseCode)) {
						alert("验证营业执照号");
						return false;
					} else if ($scope.agent.licenseCode.length.trim > 40) {
						alert("营业执照登记号需在40个英文字符之间");
						return false;
					}
				}
			}
		}

		if ($scope.agent.phone == undefined || $scope.agent.phone == "") {
			alert("请输入手机号码！");
			return false;
		} else if (!reg.test($scope.agent.phone)) {
			alert("请输入合法手机号！");
			return false;
		} else if ($scope.agent.code == undefined || $scope.agent.code == "") {
			alert("请输入验证码！");
			return false;
		} else if ($scope.agent.email == undefined || $scope.agent.email == "") {
			alert("请输入邮箱号码！");
			return false;
		} else if (!myreg.test($scope.agent.email)) {
			alert("邮箱格式不正确！");
			return false;
		} else if ($scope.agselect == undefined) {
			alert("请选择城市！");
			return false;
		} else if ($scope.agcitis == undefined) {
			alert("请选择城市！");
			return false;
		} else if ($scope.agent.userId == undefined || $scope.agent.userId == "") {
			alert("请输入用户ID！");
			return false;
		} else if ($scope.agent.passworda == undefined || $scope.agent.passworda == "") {
			alert("请输入密码！");
			return false;
		} else if ($scope.agent.passwordb == undefined || $scope.agent.passwordb == "") {
			alert("请输入密码！");
			return false;
		} else if ($scope.agent.passworda.length < 6 || $scope.agent.passworda.length > 20 || $scope.agent.passwordb.length < 6 || $scope.agent.passwordb.length > 20) {
			alert("密码由6-20位，英文字符组成！");
			return false;
		} else if ($scope.agent.passworda != $scope.agent.passwordb) {
			alert("密码不一致！");
			return false;
		} else if ($scope.agent.imgCode == undefined || $scope.agent.imgCode == "") {
			alert("请输入图片验证码！");
			return false;
		} else if (getCookie("agent_send_phone_code") != $scope.agent.code) {
			alert("验证码错误！");
			return false;
		} else if ($scope.ridel_xy != true) {
			$http.post("api/agent/sizeUpImgCode", {
				imgnum : $scope.agent.imgCode
			}).success(function(data) {
				if (data.code == 1) {
					$scope.addAgent();
				} else if (data.code == -1) {
					alert(data.message);
				}
			});
		}
	}

	// 注册代理商
	$scope.addAgent = function() {
		$scope.agent.cityId = Math.ceil($scope.agcitis.id);
		$http.post("api/agent/userRegistrationWeb", $scope.agent).success(function(data) {
			if (data.code == 1) {
				$scope.ridel_xy = false;
				$scope.password1 = "";
				$scope.password2 = "";
				$scope.codeNumber = "";
				$scope.code = "";
				$scope.codeBei = "";
				window.location.href = '#/login';
			} else if (data.code == -1) {
				alert(data.message);
			}
		})
	};

	$scope.agentInit = function() {
		// 移除样式
		// $("link[href='style/global.css']").remove();
		// 隐藏中间搜索
		// $scope.$emit('changesearchview',false);
		// 获得省级
		$scope.getShengcit();
		// 初始化图片验证码
		$scope.reGetRandCodeImg();
	};

	// 图片验证码
	$scope.reGetRandCodeImg = function() {
		$(".loginRandCodeImg").attr("src", "api/agent/getRandCodeImg?id=" + Math.random());
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
}

indexModule.controller("registerAgentController", registerAgentController);