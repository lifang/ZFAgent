'user strict';

// 系统模块设置

var modifypasswordModule = angular.module("modifypasswordModule", []);

var agentinformController = function($scope, $http, LoginService,$rootScope) {
	$rootScope.global.headTitle =$rootScope.global.title + "代理商资料";
	var v1;// 倒计时1
	var v2;// 倒计时2
	var v3;// 邮箱

	$scope.save = function() {
		// agents_id : LoginService.agentid,
	};

	$scope.info = function() {
		$rootScope.global.headTitle =$rootScope.global.title + "修改密码";
		var oldPassword = $scope.passwordOld;
		var newPassword = $scope.password;
		var newPasswordAgain = $scope.password2;

		if (oldPassword == null || oldPassword == '') {
			alert("原密码不能为空！");
			return false;
		}

		if (newPassword == null || newPassword == '') {
			alert("新密码不能为空！");
			return false;
		}

		if (newPassword.length < 6 || newPassword.length > 20) {
			alert("新密码长度至少为6到20位之间！");
			return false;
		}

		if (newPasswordAgain == null || newPasswordAgain == '') {
			alert("确认新密码不能为空！");
			return false;
		}

		if (newPasswordAgain.length < 6 || newPasswordAgain.length > 20) {
			alert("确认新密码长度在6到20位之间！");
			return false;
		}

		if (newPassword != newPasswordAgain) {
			alert("新密码与确认新密码不相同！");
			return false;
		}
		$http.post("api/agent/modifyPassword", {
			passwordOld : oldPassword,
			password : newPassword,
			customerId : LoginService.agentUserId
		}).success(function(data) {
			if (data.code == 1) {
				$scope.passwordOld = "";
				$scope.password = "";
				$scope.password2 = "";
			}
			alert(data.message);
		});
	};

	$scope.query = function() {
		var id = LoginService.agentid;
		$scope.intDiff = 0;
		$scope.intTwoDiff = 0;
		$scope.intMailDiff = 0;
		$http.post("api/agents/query/" + id).success(function(data) {
	
			if (data.result != null) {
				$scope.one = data.result;
				var s_phone = data.result.sphone;
				$scope.one.s_phone =s_phone;
				// alert($scope.one.business_license);
			}
		});
	};

	$scope.menuState = {
		show : false
	}

	$scope.toggleMenu = function() {
		$scope.menuState.show = !$scope.menuState.show;
	}

	$scope.getEmail = function() {
		$http.post("api/agent/getEmail").success(function(data) {
			if (data != null || data == 0) {
				alert(data.message);
			} else {
				$scope.show();
			}
		});

	}

	// 修改邮箱
	$scope.up_save = function() {
		// var mail = document.getElementById("emailValue").value;
		var mail = $scope.email;
		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		if (!reg.test(mail)) {
			alert("请输入合法的邮箱地址");
			return false;
		}
		// id暂定15
		$scope.req = {
			id : LoginService.agentid,
			email : mail
		};
		$http.post("api/agents/updateEmailAddr", $scope.req).success(function(data) {
			if (data != null && data != undefined) {
				alert("修改成功");
				window.location.href = '#/agentinform';
				location.reload();
			}
		});
	};

	// 根据手机号发送验证码
	$scope.sendPhoneCode = function() {
		var sMobile = $scope.i_phone_new;
		if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(sMobile))) {
			alert("不是完整的11位手机号或者正确的手机号前七位");
			$("#i_phone_new").focus();
			return false;
		} else {
		 
			if ($scope.intTwoDiff == 0) {
				$scope.getPhoneCode(sMobile);
				$scope.intTwoDiff = 120;
				clearInterval(v2);
				v2 = window.setInterval(function() {
					$('#show_phone_input_my_o_btn').html();
					if ($scope.intTwoDiff == 0) {
						$('#show_phone_input_my_o_btn').html("发送验证码");
						clearInterval(v2);
					} else {
						$('#show_phone_input_my_o_btn').html("重新发送（" + $scope.intTwoDiff + "秒）");
						$scope.intTwoDiff--;
					}
				}, 1000);
			} else{
				alert("再次点击获取发送验证码时间未到");
			}
		}
	};

	// 根据手机号发送并获取验证码
	$scope.getPhoneCode = function(sMobile) {
		$scope.req = {
			phone : sMobile
		};
		$http.post("api/index/getPhoneCodeAgent", $scope.req).success(function(data) {
			if (data != null && data != undefined) {
				// alert(data.result);
				$scope.phone_code = data.result;
			}
		});
	}
	// 确认验证码，（第一次）
	$scope.yz_phone_code = function() {
		var p_code = $scope.phone_code;
		var i_code = $scope.phone_code_i_o;// 输入的验证码
		if (p_code == i_code) {
			
				$("#show_phone_input_my_o").css('display', 'none');
				$(".mask").css('display', 'none');

				var doc_height = $(document).height();
				var doc_width = $(document).width();
				var win_height = $(window).height();
				var win_width = $(window).width();

				var layer_height = $("#show_phone_input_my_t").height();
				var layer_width = $("#show_phone_input_my_t").width();

				var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;

				$(".mask").css({
					display : 'block',
					height : doc_height
				});
				$("#show_phone_input_my_t").css('top', (win_height - layer_height) / 2);
				$("#show_phone_input_my_t").css('left', (win_width - layer_width) / 2);
				$("#show_phone_input_my_t").css('display', 'block');

				// 第二个验证框显示
				$('#show_phone_input_my_o_btn').html("发送验证码");
				clearInterval(v1);
				$scope.getPhoneCode(sMobile);
				$scope.intDiff = 120;
				v2 = window.setInterval(function() {
					$('#show_phone_input_my_o_btn').html();
					if ($scope.intDiff == 0) {
						$('#show_phone_input_my_o_btn').html("发送验证码");
						clearInterval(v2);
					} else {
						$('#show_phone_input_my_o_btn').html("重新发送验证码（" + $scope.intDiff + "秒）");
						$scope.intDiff--;
					}
				}, 1000);
		 
		} else {
			alert("验证码错误");
		}
	};

	// 确认验证码，更新手机号
	$scope.change_phone_btn = function() {
		
			var p_code = $scope.phone_code;
			var i_code = $scope.i_phone_code;// 输入的验证码
			if (p_code == i_code) {
				var sMobile = $scope.i_phone_new;
				// id暂定15
				$scope.req = {
					phone : sMobile,
					id : LoginService.agentid
				};
				$http.post("api/agents/updatePhoneNumber", $scope.req).success(function(data) {
					if (data != null && data != undefined) {
						// 重新刷新
						$("#show_phone_input_my_t").css('display', 'none');
						$(".mask").css('display', 'none');
						$scope.query();
						alert("修改成功");
					}
				});
			} else {
				alert("验证码错误");
			}
		
	};

	// 第一次发送验证码 //
	$scope.send_code_one = function(t) {
		if (t == 1) {
			$('#send_code_one').html("发送验证码");
			$scope.phone_code_i_o = "";
			$scope.i_phone_new = "";
			$scope.i_phone_code = "";
			// 显示提示
			var doc_height = $(document).height();
			var doc_width = $(document).width();
			var win_height = $(window).height();
			var win_width = $(window).width();

			var layer_height = $("#show_phone_input_my_o").height();
			var layer_width = $("#show_phone_input_my_o").width();

			var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;

			$(".mask").css({
				display : 'block',
				height : doc_height
			});
			$("#show_phone_input_my_o").css('top', (win_height - layer_height) / 2);
			$("#show_phone_input_my_o").css('left', (win_width - layer_width) / 2);
			$("#show_phone_input_my_o").css('display', 'block');
		} else if (t = 2) {// 再次点击获取
			if ($scope.intDiff == 0) {
				$scope.intDiff = 120;
				v1 = window.setInterval(function() {
					$('#send_code_one').html();
					if ($scope.intDiff == 0) {
						$('#send_code_one').html("发送验证码");
						clearInterval(v1);
					} else {
						$('#send_code_one').html("重新发送验证码(" + $scope.intDiff + "秒)");
						$scope.intDiff--;
					}
				}, 1000);
				var sMobile = $scope.one.phone;
				$scope.getPhoneCode(sMobile);
			} else {
				alert("再次点击获取发送验证码时间未到");
			}
		}
	};

	// 修改邮箱
	$scope.up_email = function() {
		if(window.confirm("您确认修改吗？")){

			// email_send_btn
			if ($scope.intMailDiff == 0) {
				$scope.intMailDiff = 120;
				v3 = window.setInterval(function() {
					$('#email_send_btn').html();
					if ($scope.intMailDiff > 0) {
						$("#email_send_btn").attr("disabled", true);
						$("#email_send_btn").css("cursor", "default");
						$("#email_send_btn").css("background", "#ABABA5");
						$("#email_send_btn").css("color", "#000");
					} else {
						// 移除样式
						$("#email_send_btn").removeAttr("disabled");
						$("#email_send_btn").css("cursor", "pointer");
						$("#email_send_btn").css("background", "#0071cf");
						$("#email_send_btn").css("color", "#FFF");
					}
					if ($scope.intMailDiff == 0) {
						$('#email_send_btn').html("修改邮箱");
						clearInterval(v3);
					} else {
						$('#email_send_btn').html("等待(" + $scope.intMailDiff + "秒)");
						$scope.intMailDiff--;
					}

				}, 1000);

				var email = $scope.one.email;
				$scope.req = {
					id : LoginService.agentid,
					content : email,
					q : $scope.one.company_name
				};
				$http.post("api/index/change_email_check", $scope.req).success(function(data) {
					if (data != null && data != undefined) {
					}
				});

				// 显示提示
				var doc_height = $(document).height();
				var doc_width = $(document).width();
				var win_height = $(window).height();
				var win_width = $(window).width();

				var layer_height = $("#email_send_tab").height();
				var layer_width = $("#email_send_tab").width();

				var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;

				$(".mask").css({
					display : 'block',
					height : doc_height
				});
				$("#email_send_tab").css('top', (win_height - layer_height) / 2);
				$("#email_send_tab").css('left', (win_width - layer_width) / 2);
				$("#email_send_tab").css('display', 'block');
			} else {
				alert("再次点击获取发送验证码时间未到");
				return false;
			}
			return true;
		}else{
			return false;
		}

	};

	$scope.colose_email = function() {
		$("#email_send_tab").css('display', 'none');
		$(".mask").css('display', 'none');
	};

	$scope.close_show_two = function() {
		$("#show_phone_input_my_o").css('display', 'none');
		$("#show_phone_input_my_t").css('display', 'none');
		$(".mask").css('display', 'none');
	};

	$scope.getEmail = function() {
		$http.post("api/agent/getEmail").success(function(data) {
			if (data != null || data == 0) {
				alert(data.message);
			} else {
				$scope.show();
			}
		});

	}

	$scope.updatePhone = function() {
		$scope.req = {
			dentcode : dentcode
		};
		$http.post("api/agent/updatePhone", $scope.req).success(function(data) {
			alert(data.message);
		});
		$scope.save();
	};
	$scope.query();
};

var modifypasswordController = function($scope, $http, LoginService,$rootScope) {

	var v1;// 倒计时1
	var v2;// 倒计时2
	var v3;// 邮箱

	$scope.save = function() {
		// agents_id : LoginService.agentid,
	};

	$scope.info = function() {
		$rootScope.global.headTitle =$rootScope.global.title + "修改密码";
		var oldPassword = $scope.passwordOld;
		var newPassword = $scope.password;
		var newPasswordAgain = $scope.password2;

		if (oldPassword == null || oldPassword == '') {
			alert("原密码不能为空！");
			return false;
		}

		if (newPassword == null || newPassword == '') {
			alert("新密码不能为空！");
			return false;
		}

		if (newPassword.length < 6 || newPassword.length > 20) {
			alert("新密码长度至少为6到20位之间！");
			return false;
		}

		if (newPasswordAgain == null || newPasswordAgain == '') {
			alert("确认新密码不能为空！");
			return false;
		}

		if (newPasswordAgain.length < 6 || newPasswordAgain.length > 20) {
			alert("确认新密码长度在6到20位之间！");
			return false;
		}

		if (newPassword != newPasswordAgain) {
			alert("新密码与确认新密码不相同！");
			return false;
		}
		$http.post("api/agent/modifyPassword", {
			passwordOld : oldPassword,
			password : newPassword,
			customerId : LoginService.agentUserId
		}).success(function(data) {
			if (data.code == 1) {
				$scope.passwordOld = "";
				$scope.password = "";
				$scope.password2 = "";
			}
			alert(data.message);
		});
	};

	$scope.query = function() {
		var id = LoginService.agentid;
		$scope.intDiff = 0;
		$scope.intTwoDiff = 0;
		$scope.intMailDiff = 0;
		$http.post("api/agents/query/" + id).success(function(data) {
			$rootScope.global.headTitle =$rootScope.global.title + "修改密码";
			if (data.result != null) {
				$scope.one = data.result;
				var s_phone = data.result.sphone;
				$scope.one.s_phone =s_phone;
				// alert($scope.one.business_license);
			}
		});
	};

	$scope.menuState = {
		show : false
	}

	$scope.toggleMenu = function() {
		$scope.menuState.show = !$scope.menuState.show;
	}

	$scope.getEmail = function() {
		$http.post("api/agent/getEmail").success(function(data) {
			if (data != null || data == 0) {
				alert(data.message);
			} else {
				$scope.show();
			}
		});

	}

	// 修改邮箱
	$scope.up_save = function() {
		// var mail = document.getElementById("emailValue").value;
		var mail = $scope.email;
		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		if (!reg.test(mail)) {
			alert("请输入合法的邮箱地址");
			return false;
		}
		// id暂定15
		$scope.req = {
			id : LoginService.agentid,
			email : mail
		};
		$http.post("api/agents/updateEmailAddr", $scope.req).success(function(data) {
			if (data != null && data != undefined) {
				alert("修改成功");
				window.location.href = '#/agentinform';
				location.reload();
			}
		});
	};

	// 根据手机号发送验证码
	$scope.sendPhoneCode = function() {
		var sMobile = $scope.i_phone_new;
		if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(sMobile))) {
			alert("不是完整的11位手机号或者正确的手机号前七位");
			$("#i_phone_new").focus();
			return false;
		} else {
		 
			if ($scope.intTwoDiff == 0) {
				$scope.getPhoneCode(sMobile);
				$scope.intTwoDiff = 120;
				clearInterval(v2);
				v2 = window.setInterval(function() {
					$('#show_phone_input_my_o_btn').html();
					if ($scope.intTwoDiff == 0) {
						$('#show_phone_input_my_o_btn').html("发送验证码");
						clearInterval(v2);
					} else {
						$('#show_phone_input_my_o_btn').html("重新发送（" + $scope.intTwoDiff + "秒）");
						$scope.intTwoDiff--;
					}
				}, 1000);
			} else{
				alert("再次点击获取发送验证码时间未到");
			}
		}
	};

	// 根据手机号发送并获取验证码
	$scope.getPhoneCode = function(sMobile) {
		$scope.req = {
			phone : sMobile
		};
		$http.post("api/index/getPhoneCodeAgent", $scope.req).success(function(data) {
			if (data != null && data != undefined) {
				// alert(data.result);
				$scope.phone_code = data.result;
			}
		});
	}
	// 确认验证码，（第一次）
	$scope.yz_phone_code = function() {
		var p_code = $scope.phone_code;
		var i_code = $scope.phone_code_i_o;// 输入的验证码
		if (p_code == i_code) {
			
				$("#show_phone_input_my_o").css('display', 'none');
				$(".mask").css('display', 'none');

				var doc_height = $(document).height();
				var doc_width = $(document).width();
				var win_height = $(window).height();
				var win_width = $(window).width();

				var layer_height = $("#show_phone_input_my_t").height();
				var layer_width = $("#show_phone_input_my_t").width();

				var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;

				$(".mask").css({
					display : 'block',
					height : doc_height
				});
				$("#show_phone_input_my_t").css('top', (win_height - layer_height) / 2);
				$("#show_phone_input_my_t").css('left', (win_width - layer_width) / 2);
				$("#show_phone_input_my_t").css('display', 'block');

				// 第二个验证框显示
				$('#show_phone_input_my_o_btn').html("发送验证码");
				clearInterval(v1);
				$scope.getPhoneCode(sMobile);
				$scope.intDiff = 120;
				v2 = window.setInterval(function() {
					$('#show_phone_input_my_o_btn').html();
					if ($scope.intDiff == 0) {
						$('#show_phone_input_my_o_btn').html("发送验证码");
						clearInterval(v2);
					} else {
						$('#show_phone_input_my_o_btn').html("重新发送验证码（" + $scope.intDiff + "秒）");
						$scope.intDiff--;
					}
				}, 1000);
		 
		} else {
			alert("验证码错误");
		}
	};

	// 确认验证码，更新手机号
	$scope.change_phone_btn = function() {
		
			var p_code = $scope.phone_code;
			var i_code = $scope.i_phone_code;// 输入的验证码
			if (p_code == i_code) {
				var sMobile = $scope.i_phone_new;
				// id暂定15
				$scope.req = {
					phone : sMobile,
					id : LoginService.agentid
				};
				$http.post("api/agents/updatePhoneNumber", $scope.req).success(function(data) {
					if (data != null && data != undefined) {
						// 重新刷新
						$("#show_phone_input_my_t").css('display', 'none');
						$(".mask").css('display', 'none');
						$scope.query();
						alert("修改成功");
					}
				});
			} else {
				alert("验证码错误");
			}
		
	};

	// 第一次发送验证码 //
	$scope.send_code_one = function(t) {
		if (t == 1) {
			$('#send_code_one').html("发送验证码");
			$scope.phone_code_i_o = "";
			$scope.i_phone_new = "";
			$scope.i_phone_code = "";
			// 显示提示
			var doc_height = $(document).height();
			var doc_width = $(document).width();
			var win_height = $(window).height();
			var win_width = $(window).width();

			var layer_height = $("#show_phone_input_my_o").height();
			var layer_width = $("#show_phone_input_my_o").width();

			var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;

			$(".mask").css({
				display : 'block',
				height : doc_height
			});
			$("#show_phone_input_my_o").css('top', (win_height - layer_height) / 2);
			$("#show_phone_input_my_o").css('left', (win_width - layer_width) / 2);
			$("#show_phone_input_my_o").css('display', 'block');
		} else if (t = 2) {// 再次点击获取
			if ($scope.intDiff == 0) {
				$scope.intDiff = 120;
				v1 = window.setInterval(function() {
					$('#send_code_one').html();
					if ($scope.intDiff == 0) {
						$('#send_code_one').html("发送验证码");
						clearInterval(v1);
					} else {
						$('#send_code_one').html("重新发送验证码(" + $scope.intDiff + "秒)");
						$scope.intDiff--;
					}
				}, 1000);
				var sMobile = $scope.one.phone;
				$scope.getPhoneCode(sMobile);
			} else {
				alert("再次点击获取发送验证码时间未到");
			}
		}
	};

	// 修改邮箱
	$scope.up_email = function() {
		if(window.confirm("您确认修改吗？")){

			// email_send_btn
			if ($scope.intMailDiff == 0) {
				$scope.intMailDiff = 120;
				v3 = window.setInterval(function() {
					$('#email_send_btn').html();
					if ($scope.intMailDiff > 0) {
						$("#email_send_btn").attr("disabled", true);
						$("#email_send_btn").css("cursor", "default");
						$("#email_send_btn").css("background", "#ABABA5");
						$("#email_send_btn").css("color", "#000");
					} else {
						// 移除样式
						$("#email_send_btn").removeAttr("disabled");
						$("#email_send_btn").css("cursor", "pointer");
						$("#email_send_btn").css("background", "#0071cf");
						$("#email_send_btn").css("color", "#FFF");
					}
					if ($scope.intMailDiff == 0) {
						$('#email_send_btn').html("修改邮箱");
						clearInterval(v3);
					} else {
						$('#email_send_btn').html("等待(" + $scope.intMailDiff + "秒)");
						$scope.intMailDiff--;
					}

				}, 1000);

				var email = $scope.one.email;
				$scope.req = {
					id : LoginService.agentid,
					content : email,
					q : $scope.one.company_name
				};
				$http.post("api/index/change_email_check", $scope.req).success(function(data) {
					if (data != null && data != undefined) {
					}
				});

				// 显示提示
				var doc_height = $(document).height();
				var doc_width = $(document).width();
				var win_height = $(window).height();
				var win_width = $(window).width();

				var layer_height = $("#email_send_tab").height();
				var layer_width = $("#email_send_tab").width();

				var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;

				$(".mask").css({
					display : 'block',
					height : doc_height
				});
				$("#email_send_tab").css('top', (win_height - layer_height) / 2);
				$("#email_send_tab").css('left', (win_width - layer_width) / 2);
				$("#email_send_tab").css('display', 'block');
			} else {
				alert("再次点击获取发送验证码时间未到");
				return false;
			}
			return true;
		}else{
			return false;
		}

	};

	$scope.colose_email = function() {
		$("#email_send_tab").css('display', 'none');
		$(".mask").css('display', 'none');
	};

	$scope.close_show_two = function() {
		$("#show_phone_input_my_o").css('display', 'none');
		$("#show_phone_input_my_t").css('display', 'none');
		$(".mask").css('display', 'none');
	};

	$scope.getEmail = function() {
		$http.post("api/agent/getEmail").success(function(data) {
			if (data != null || data == 0) {
				alert(data.message);
			} else {
				$scope.show();
			}
		});

	}

	$scope.updatePhone = function() {
		$scope.req = {
			dentcode : dentcode
		};
		$http.post("api/agent/updatePhone", $scope.req).success(function(data) {
			alert(data.message);
		});
		$scope.save();
	};
	$scope.query();
};
// 弹出层
function popup(t, b) {
	var doc_height = $(document).height();
	var doc_width = $(document).width();
	var win_height = $(window).height();
	var win_width = $(window).width();

	var layer_height = $(t).height();
	var layer_width = $(t).width();

	var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;

	// tab
	$(b).bind('click', function() {
		$(".mask").css({
			display : 'block',
			height : doc_height
		});
		$(t).css('top', (win_height - layer_height) / 2);
		$(t).css('left', (win_width - layer_width) / 2);
		$(t).css('display', 'block');
		return false;
	})
	$(".close").click(function() {
		$(t).css('display', 'none');
		$(".mask").css('display', 'none');
	})
}
$(function() {
	popup("#show_phone_input_my_o", "#show_phone_input_my_btn");// 我的信息
	// 根据原来手机号发送验证码
})

modifypasswordController.$inject = [ '$scope', '$http', 'LoginService','$rootScope' ];
modifypasswordModule.controller("agentinformController", agentinformController);
modifypasswordModule.controller("modifypasswordController", modifypasswordController);
