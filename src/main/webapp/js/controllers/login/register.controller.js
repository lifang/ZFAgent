<<<<<<< HEAD
var registerAgentController = function($scope, $location, $http, LoginService) {
	// 检验邮箱格式
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	// 手机格式
	var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
	// 英文数字校验
	var numCh = /[^a-zA-Z0-9]/g;
	var cardReg = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;// 身份证号码验证
	var licenseCodeReg = /^[1-9]\d*|0$/;// 验证营业执照为数字
	// 初始化代理商对象
	$scope.agent = {};

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
					$("#time_show_agent").attr("style","background-color:#AAAAAA");
					window.agentSendCode = window.setInterval(function() {
						if ($scope.intDiff == 0) {
							$('#time_show_agent').html("获取验证码！");
							$scope.registreTime = true;
							window.clearInterval(window.agentSendCode);
							$("#time_show_agent").attr("style","background-color:#AAAAAA");
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
			alert("负责人姓名需在10个中文字符或20个英文字符之间");
			return false;
		}else if($scope.checkCard()==false){
			return false;
		}/* else if ($scope.agent.card == undefined || $scope.agent.card == "") {
			alert("请输入负责人身份证！");
			return false;
		} else if (!cardReg.test($scope.agent.card)) {
			alert("身份证格式错误！");
			return false;
		}*/

		// alert($scope.agent.types);
		if ($scope.agent.types == 1) {
			$scope.agent.types = 1;// 公司
			if ($scope.agent.companyName == undefined || $scope.agent.companyName == "" || $scope.agent.companyName == null) {
				alert("公司名称不能为空");
				return false;
			} else if ($scope.agent.companyName != undefined && $scope.agent.companyName != "" && $scope.agent.companyName != null) {
				if (strlen($scope.agent.companyName) > 40) {
					alert("公司名称需在20个中文字符或40个英文字符之间");
					return false;
				}
				// 如果营业执照为空
				if ($scope.agent.licenseCode == undefined || $scope.agent.licenseCode == "" || $scope.agent.licenseCode == null) {
					alert("营业执照不能为空");
					return false;
				} else if ($scope.agent.licenseCode != undefined && $scope.agent.licenseCode != "" && $scope.agent.licenseCode != null) {
					if (!licenseCodeReg.test($scope.agent.licenseCode)) {
						alert("营业执照号必须为数字,请重新输入");
						return false;
					} else if ($scope.agent.licenseCode.length.trim > 40) {
						alert("营业执照登记号需在40个数字之间");
						return false;
					}
				}
			}

		}
		if ($scope.agent.email == undefined || $scope.agent.email == "") {
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
			alert("请输入用户名！");
			return false;
		} else if ($scope.agent.passworda == undefined || $scope.agent.passworda == "") {
			$scope.passclassa = "input_false";
			return false;
		} else if ($scope.agent.passwordb == undefined || $scope.agent.passwordb == "") {
			$scope.passclassb = "input_false";
			return false;
		} else if ($scope.topassa() == false) {
			$scope.passclassa = "input_false";
			return false;
		}  else if ($scope.topassb() == false) {
			$scope.passclassb = "input_false";
			return false;
		}else if ($scope.agent.phone == undefined || $scope.agent.phone == "") {
			alert("请输入手机号码！");
			return false;
		} else if (!reg.test($scope.agent.phone)) {
			alert("请输入合法手机号！");
			return false;
		} else if ($scope.agent.code == undefined || $scope.agent.code == "") {
			alert("请输入验证码！");
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
					$scope.reGetRandCodeImg();
				}
			});
		}else{
			alert("请勾选《华尔街金融平台代理商使用协议》");
		}
	}
	
	//注册优化
	$scope.topassa = function(){
		if($scope.agent.passworda.length < 6 || $scope.agent.passworda.length > 20){
			$scope.passclassa = "input_false";
			return false;
		}else{
			$scope.passclassa = "input_true";
			return true;
		}
	}
	
	$scope.topassb = function(){
		if($scope.agent.passwordb.length < 6 || $scope.agent.passwordb.length > 20 || $scope.agent.passworda != $scope.agent.passwordb){
			$scope.passclassb = "input_false";
			return false;
		}else{
			$scope.passclassb = "input_true";
			return true;
		}
	}
	
	//清空数据
	$scope.clkickmessa = function(){
		$scope.reGetRandCodeImg();
		window.clearInterval(window.agentSendCode);
		$("#time_show_agent").html("获取验证码");
		$scope.registreTime = true;
	}
	$scope.clkickmessb = function(){
		$scope.reGetRandCodeImg();
		window.clearInterval(window.agentSendCode);
		$("#time_show_agent").html("获取验证码");
		$scope.registreTime = true;
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
				alert("您的申请信息已经提交，静候我们的工作人员处理。");
				window.location.href = '#/login';
			} else if (data.code == -1) {
				alert(data.message);
			}
		})
	};

	$scope.agentInit = function() {
		// 获得省级
		$scope.getShengcit();
		// 初始化图片验证码
		$scope.reGetRandCodeImg();
		// 单选按钮初始化（1.公司 2.个人）
		$scope.agent.types = 2;
		// alert($scope.agent.types);
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
	
	//==========================================
	$scope.checkCard = function() {
		var card = $("#card_no").val();
		// 是否为空
		if (card === '') {
			alert('请输入身份证号，身份证号不能为空');
			//setTimeout(function () { $("#card_no").focus(); }, 0);
			return false;
		}
		// 校验长度，类型
		if (isCardNo(card) === false) {
			alert('您输入的身份证号码不正确，请重新输入');
			//setTimeout(function () { $("#card_no").focus(); }, 0);
			return false;
		}
		// 检查省份
		if (checkProvince(card) === false) {
			alert('您输入的身份证号码不正确,请重新输入');
			//setTimeout(function () { $("#card_no").focus(); }, 0);
			return false;
		}
		// 校验生日
		if (checkBirthday(card) === false) {
			alert('您输入的身份证号码生日不正确,请重新输入');
			//setTimeout(function () { $("#card_no").focus(); }, 0);
			return false;
		}
		// 检验位的检测
		if (checkParity(card) === false) {
			alert('您的身份证校验位不正确,请重新输入');
			//setTimeout(function () { $("#card_no").focus(); }, 0);
			return false;
		}
		//alert('OK');
		return true;
	};

	// 检查号码是否符合规范，包括长度，类型
	isCardNo = function(card) {
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
		var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
		if (reg.test(card) === false) {
			// alert("身份证号码不合乎规范");
			return false;
		}

		return true;
	};

	// 取身份证前两位,校验省份
	checkProvince = function(card) {
		var province = card.substr(0, 2);
		if (vcity[province] == undefined) {
			return false;
		}
		return true;
	};

	// 检查生日是否正确
	checkBirthday = function(card) {
		var len = card.length;
		// 身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
		if (len == '15') {
			var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
			var arr_data = card.match(re_fifteen);
			var year = arr_data[2];
			var month = arr_data[3];
			var day = arr_data[4];
			var birthday = new Date('19' + year + '/' + month + '/' + day);
			return verifyBirthday('19' + year, month, day, birthday);
		}
		// 身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
		if (len == '18') {
			var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
			var arr_data = card.match(re_eighteen);
			var year = arr_data[2];
			var month = arr_data[3];
			var day = arr_data[4];
			var birthday = new Date(year + '/' + month + '/' + day);
			return verifyBirthday(year, month, day, birthday);
		}
		return false;
	};

	// 校验日期
	verifyBirthday = function(year, month, day, birthday) {
		var now = new Date();
		var now_year = now.getFullYear();
		// 年月日是否合理
		if (birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day) {
			// 判断年份的范围（3岁到100岁之间)
			var time = now_year - year;
			if (time >= 3 && time <= 100) {
				return true;
			}
			return false;
		}
		return false;
	};

	// 校验位的检测
	checkParity = function(card) {
		// 15位转18位
		card = changeFivteenToEighteen(card);
		var len = card.length;
		if (len == '18') {
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
			var cardTemp = 0, i, valnum;
			for (i = 0; i < 17; i++) {
				cardTemp += card.substr(i, 1) * arrInt[i];
			}
			valnum = arrCh[cardTemp % 11];
			if (valnum == card.substr(17, 1)) {
				return true;
			}
			return false;
		}
		return false;
	};

	// 15位转18位身份证号
	changeFivteenToEighteen = function(card) {
		if (card.length == '15') {
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
			var cardTemp = 0, i;
			card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
			for (i = 0; i < 17; i++) {
				cardTemp += card.substr(i, 1) * arrInt[i];
			}
			card += arrCh[cardTemp % 11];
			return card;
		}
		return card;
	};
	
	//==========================================

	$scope.agentInit();
}

indexModule.controller("registerAgentController", registerAgentController);
=======
var registerAgentController = function($scope, $location, $http, LoginService) {
	// 检验邮箱格式
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	// 手机格式
	var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
	// 英文数字校验
	var numCh = /[^a-zA-Z0-9]/g;
	var cardReg = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;// 身份证号码验证
	var licenseCodeReg = /^[1-9]\d*|0$/;// 验证营业执照为数字
	// 初始化代理商对象
	$scope.agent = {};

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
					$("#time_show_agent").attr("style","background-color:#AAAAAA");
					window.agentSendCode = window.setInterval(function() {
						if ($scope.intDiff == 0) {
							$('#time_show_agent').html("获取验证码！");
							$scope.registreTime = true;
							window.clearInterval(window.agentSendCode);
							$("#time_show_agent").attr("style","background-color:#AAAAAA");
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
			alert("负责人姓名需在10个中文字符或20个英文字符之间");
			return false;
		}else if($scope.checkCard()==false){
			return false;
		}/* else if ($scope.agent.card == undefined || $scope.agent.card == "") {
			alert("请输入负责人身份证！");
			return false;
		} else if (!cardReg.test($scope.agent.card)) {
			alert("身份证格式错误！");
			return false;
		}*/

		// alert($scope.agent.types);
		if ($scope.agent.types == 1) {
			$scope.agent.types = 1;// 公司
			if ($scope.agent.companyName == undefined || $scope.agent.companyName == "" || $scope.agent.companyName == null) {
				alert("公司名称不能为空");
				return false;
			} else if ($scope.agent.companyName != undefined && $scope.agent.companyName != "" && $scope.agent.companyName != null) {
				if (strlen($scope.agent.companyName) > 40) {
					alert("公司名称需在20个中文字符或40个英文字符之间");
					return false;
				}
				// 如果营业执照为空
				if ($scope.agent.licenseCode == undefined || $scope.agent.licenseCode == "" || $scope.agent.licenseCode == null) {
					alert("营业执照不能为空");
					return false;
				} else if ($scope.agent.licenseCode != undefined && $scope.agent.licenseCode != "" && $scope.agent.licenseCode != null) {
					if (!licenseCodeReg.test($scope.agent.licenseCode)) {
						alert("营业执照号必须为数字,请重新输入");
						return false;
					} else if ($scope.agent.licenseCode.length.trim > 40) {
						alert("营业执照登记号需在40个数字之间");
						return false;
					}
				}
			}

		}
		if ($scope.agent.email == undefined || $scope.agent.email == "") {
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
			alert("请输入用户名！");
			return false;
		} else if ($scope.agent.passworda == undefined || $scope.agent.passworda == "") {
			$scope.passclassa = "input_false";
			return false;
		} else if ($scope.agent.passwordb == undefined || $scope.agent.passwordb == "") {
			$scope.passclassb = "input_false";
			return false;
		} else if ($scope.topassa() == false) {
			$scope.passclassa = "input_false";
			return false;
		}  else if ($scope.topassb() == false) {
			$scope.passclassb = "input_false";
			return false;
		}else if ($scope.agent.phone == undefined || $scope.agent.phone == "") {
			alert("请输入手机号码！");
			return false;
		} else if (!reg.test($scope.agent.phone)) {
			alert("请输入合法手机号！");
			return false;
		} else if ($scope.agent.code == undefined || $scope.agent.code == "") {
			alert("请输入验证码！");
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
					$scope.reGetRandCodeImg();
				}
			});
		}else{
			alert("请勾选《华尔街金融平台代理商使用协议》");
		}
	}
	
	//注册优化
	$scope.topassa = function(){
		if($scope.agent.passworda.length < 6 || $scope.agent.passworda.length > 20){
			$scope.passclassa = "input_false";
			return false;
		}else{
			$scope.passclassa = "input_true";
			return true;
		}
	}
	
	$scope.topassb = function(){
		if($scope.agent.passwordb.length < 6 || $scope.agent.passwordb.length > 20 || $scope.agent.passworda != $scope.agent.passwordb){
			$scope.passclassb = "input_false";
			return false;
		}else{
			$scope.passclassb = "input_true";
			return true;
		}
	}
	
	//清空数据
	$scope.clkickmessa = function(){
		$scope.reGetRandCodeImg();
		window.clearInterval(window.agentSendCode);
		$("#time_show_agent").html("获取验证码");
		$scope.registreTime = true;
	}
	$scope.clkickmessb = function(){
		$scope.reGetRandCodeImg();
		window.clearInterval(window.agentSendCode);
		$("#time_show_agent").html("获取验证码");
		$scope.registreTime = true;
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
				alert("您的申请信息已经提交，静候我们的工作人员处理。");
				window.location.href = '#/login';
			} else if (data.code == -1) {
				alert(data.message);
			}
		})
	};

	$scope.agentInit = function() {
		// 获得省级
		$scope.getShengcit();
		// 初始化图片验证码
		$scope.reGetRandCodeImg();
		// 单选按钮初始化（1.公司 2.个人）
		$scope.agent.types = 2;
		// alert($scope.agent.types);
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
	
	//==========================================
	$scope.checkCard = function() {
		var card = $("#card_no").val();
		// 是否为空
		if (card === '') {
			alert('请输入身份证号，身份证号不能为空');
			//setTimeout(function () { $("#card_no").focus(); }, 0);
			return false;
		}
		// 校验长度，类型
		if (isCardNo(card) === false) {
			alert('您输入的身份证号码不正确，请重新输入');
			//setTimeout(function () { $("#card_no").focus(); }, 0);
			return false;
		}
		// 检查省份
		if (checkProvince(card) === false) {
			alert('您输入的身份证号码不正确,请重新输入');
			//setTimeout(function () { $("#card_no").focus(); }, 0);
			return false;
		}
		// 校验生日
		if (checkBirthday(card) === false) {
			alert('您输入的身份证号码生日不正确,请重新输入');
			//setTimeout(function () { $("#card_no").focus(); }, 0);
			return false;
		}
		// 检验位的检测
		if (checkParity(card) === false) {
			alert('您的身份证校验位不正确,请重新输入');
			//setTimeout(function () { $("#card_no").focus(); }, 0);
			return false;
		}
		//alert('OK');
		return true;
	};

	// 检查号码是否符合规范，包括长度，类型
	isCardNo = function(card) {
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
		var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
		if (reg.test(card) === false) {
			// alert("身份证号码不合乎规范");
			return false;
		}

		return true;
	};

	// 取身份证前两位,校验省份
	checkProvince = function(card) {
		var province = card.substr(0, 2);
		if (vcity[province] == undefined) {
			return false;
		}
		return true;
	};

	// 检查生日是否正确
	checkBirthday = function(card) {
		var len = card.length;
		// 身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
		if (len == '15') {
			var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
			var arr_data = card.match(re_fifteen);
			var year = arr_data[2];
			var month = arr_data[3];
			var day = arr_data[4];
			var birthday = new Date('19' + year + '/' + month + '/' + day);
			return verifyBirthday('19' + year, month, day, birthday);
		}
		// 身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
		if (len == '18') {
			var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
			var arr_data = card.match(re_eighteen);
			var year = arr_data[2];
			var month = arr_data[3];
			var day = arr_data[4];
			var birthday = new Date(year + '/' + month + '/' + day);
			return verifyBirthday(year, month, day, birthday);
		}
		return false;
	};

	// 校验日期
	verifyBirthday = function(year, month, day, birthday) {
		var now = new Date();
		var now_year = now.getFullYear();
		// 年月日是否合理
		if (birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day) {
			// 判断年份的范围（3岁到100岁之间)
			var time = now_year - year;
			if (time >= 3 && time <= 100) {
				return true;
			}
			return false;
		}
		return false;
	};

	// 校验位的检测
	checkParity = function(card) {
		// 15位转18位
		card = changeFivteenToEighteen(card);
		var len = card.length;
		if (len == '18') {
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
			var cardTemp = 0, i, valnum;
			for (i = 0; i < 17; i++) {
				cardTemp += card.substr(i, 1) * arrInt[i];
			}
			valnum = arrCh[cardTemp % 11];
			if (valnum == card.substr(17, 1)) {
				return true;
			}
			return false;
		}
		return false;
	};

	// 15位转18位身份证号
	changeFivteenToEighteen = function(card) {
		if (card.length == '15') {
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
			var cardTemp = 0, i;
			card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
			for (i = 0; i < 17; i++) {
				cardTemp += card.substr(i, 1) * arrInt[i];
			}
			card += arrCh[cardTemp % 11];
			return card;
		}
		return card;
	};
	
	//==========================================

	$scope.agentInit();
}

indexModule.controller("registerAgentController", registerAgentController);
>>>>>>> branch 'master' of https://github.com/lifang/ZFAgent
