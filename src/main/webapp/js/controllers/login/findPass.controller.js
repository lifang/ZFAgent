
var findpassController=function($scope, $location, $http, LoginService,$timeout){
	$scope.usernameLocal=$location.search()['sendusername'];
	$scope.sendStatus=Math.ceil($location.search()['sendStatus']);
	//检验邮箱格式
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	//手机格式
	var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
	//手机边框错误显示/消息
	$scope.usernameinputboolean = false;
	$scope.usernamemessageboolean = false;
	$scope.codeinputboolean = false;
	$scope.codeboolean = false;
	
	$scope.passinputone = false;
	$scope.passinputtwo = false;
	$scope.passmessagecoolean = false;
	
	$scope.successpass = false;
	//图片校验码
	$scope.imgcodeboolean = false;
	//手机成功注册
	$scope.gotosuccess = false;
	//隐藏想邮箱发送邮件状态
	$scope.songToEmail = false;
	$scope.intDiff = 120;
	window.clearInterval(window.a);
	window.clearInterval(window.b);
	
	// 初始化图片验证码
	$scope.reGetRandCodeImg = function() {
		$(".loginRandCodeImg").attr("src", "api/agent/getRandCodeImg?id=" + Math.random());
	};
	
	$scope.init=function() {
		//隐藏中间搜索
		$scope.$emit('changesearchview',false);
		if($scope.sendStatus == -1){
			$scope.phone_email = $scope.usernameLocal;
			$scope.threestep();
		}else{
			$scope.onestep();
		}
	};
	
	
	$scope.onestep=function() {
		$scope.one=true;
		$scope.two=false;
		$scope.three=false;
	};
	$scope.twostep=function() {
		$scope.one=false;
		$scope.two=true;
		$scope.three=false;	
	};
	$scope.threestep=function() {
		$scope.one=false;
		$scope.two=false;
		$scope.three=true;
	};
	//从新获得验证码
	$scope.codeStatus = false;
	$scope.sendnumber = 0;
	$scope.newCode = function(){
		if($scope.codeStatus == true){
			$scope.codeStatus = false;
			if($scope.sendnumber == 1){
				$scope.$scope.imgcode;
				$scope.reGetRandCodeImg();
				$(".mask").css('display','block');
				$(".re_alert_tab").css('display','block');
			}else{
				$http.post("api/agent/sendPhoneVerificationCode", {
					codeNumber : $scope.phone_email,
				}).success(function(data) {
					if (data.code == 4) {
						$scope.sendnumber = $scope.sendnumber + 1;
						window.clearInterval(window.a);
						window.clearInterval(window.b);
						$scope.code = data.result;
						$scope.codeNumber = "";
						$scope.intDiff = 119;
						window.b = window.setInterval(function(){
					    	if($scope.intDiff == 0){
					    		$('#day_show').html("点击获得验证码！");
					    		$scope.codeStatus = true;
					    		window.clearInterval(window.b);
					    	}else{
					    		$('#day_show').html("重新发送验证码（"+$scope.intDiff+"秒）");
					    	    $scope.intDiff--;
					    	}
					    }, 1000);
						intervalThree;
					} else {
						alert(data.message);
					}
				})
			}
		}
	}
	
	//移除样式
	//$("link[href='style/global.css']").remove();
	
	// 找回密码第一步
	$scope.findPassOnes = function() {
		//$scope.twostep();
		
		$scope.intDiff = 120;
		if(!reg.test($scope.phone_email)&&!myreg.test($scope.phone_email)){
			$scope.usernameinputboolean = true;
			$scope.usernamemessageboolean = true;
		}else{
			$http.post("api/agent/getFindUser", {
				username : $scope.phone_email
			}).success(function(data) {
				if (data.code == -1) {//检验账号存在
					alert(data.message);
				} else {
							if (myreg.test($scope.phone_email)) {
								// 向邮箱发送密码重置邮件！
								$http.post("api/agent/sendEmailVerificationCode", {
									codeNumber : $scope.phone_email,
								}).success(function(data) {
									if (data.code == 1) {
										$scope.songToEmail = true;
										$scope.twostep();
									} else {
										alert(data.message);
									}
								})
							} else {
								$http.post("api/agent/sendPhoneVerificationCode", {
									codeNumber : $scope.phone_email,
								}).success(function(data) {
									if (data.code == 1) {
										window.clearInterval(window.a);
										$scope.code = data.result;
										$scope.codeNumber = "";
										//倒计时
										$scope.intDiff = 119;
										$scope.twostep();
										window.a = window.setInterval(function(){
									    	if($scope.intDiff == 0){
									    		$('#day_show').html("点击获得验证码！");
									    		$scope.codeStatus = true;
									    		window.clearInterval(window.a);
									    	}else{
									    		$('#day_show').html("重新发送验证码（"+$scope.intDiff+"秒）");
									    	    $scope.intDiff--;
									    	}
									    }, 1000);
									} else {
										alert(data.message);
									}
								})
							}
				
				}
			})
		}
	};
	
	// 找回密码第三步
	$scope.findPassThree = function() {
		//$scope.threestep();
		
		if($scope.code == $scope.codeNumber){
			$http.post("api/agent/webFicationCode", {
			code : $scope.codeNumber,
			username : $scope.phone_email
			}).success(function(data) {
			if (data.code == 1) {
				$scope.threestep();
			} else if (data.code == -1) {
				$scope.codeinputboolean = true;
				$scope.codeboolean = true;
			}
			})
		}else{
			$scope.codeinputboolean = true;
			$scope.codeboolean = true;
		}
	};
	
	//键盘事件
	$scope.keyupdownpas1 = function(){
		if($scope.password1==''||$scope.password1==null){
			$scope.passinputone = true;
		}else if ($scope.password1.length<6||$scope.password1.length>20) {
			$scope.passinputone = true;
		}else{
			$scope.passinputone = false;
			$scope.successpass = true;
		}
	}
	$scope.keyupdownpas2 = function(){
		if($scope.password2==''||$scope.password2==null){
			$scope.passinputtwo = true;
		}else if ($scope.password2.length<6||$scope.password2.length>20) {
			$scope.passinputtwo = true;
		}else if ($scope.password1 != $scope.password2) {
			$scope.passinputtwo = true;
			$scope.passmessagecoolean = true;
		}else{
			$scope.passinputtwo = false;
			$scope.passmessagecoolean = false;
		}
	}
	
	//跳转邮箱登陆
	$scope.gotoemail = function(){
		var t=$scope.phone_email.lastIndexOf('@')+1;
		var str = "http://mail."+$scope.phone_email.substring(t);
		window.location.href=str;
	}
	
	// 开始找回(手机)
	$scope.findPassEnd = function() {
		if($scope.password1==''||$scope.password1==null){
			$scope.passinputone = true;
		}else if ($scope.password1.length<6||$scope.password1.length>20) {
			$scope.passinputone = true;
		}else{
			$scope.successpass = true;
			if($scope.password2==''||$scope.password2==null){
				$scope.passinputtwo = true;
			}else if ($scope.password2.length<6||$scope.password2.length>20) {
				$scope.passinputtwo = true;
			}else if ($scope.password1 != $scope.password2) {
				$scope.passinputtwo = true;
				$scope.passmessagecoolean = true;
			} else {
				$http.post("api/agent/webUpdatePass", {
					password : $scope.password1,
					username : $scope.phone_email
				}).success(function(data) {
					if (data.code == 1) {
						$scope.gotosuccess = true;
						// window.location.href = '#/login';
					} else if (data.code == -1) {
						alert(data.message);
					}
				})
			}
		}
	};
	// 开始找回(邮箱)
	$scope.findPassEmailEnd = function() {
		if($scope.password1==''||$scope.password1==null||$scope.password2==''||$scope.password2==null){
			alert("密码不能为空！");
		}else if ($scope.password1.length<6||$scope.password1.length>20||$scope.password2.length<6||$scope.password2.length>20) {
			alert("密码由6-20位，英文字符组成！");
		}else if ($scope.password1 != $scope.password2) {
			alert("密码不一致！");
		} else {
			$http.post("api/agent/webUpdateEmailPass", {
				password : $scope.password1,
				username : $scope.phone_email
			}).success(function(data) {
				if (data.code == 1) {
					window.location.href = '#/login';
				} else if (data.code == -1) {
					alert(data.message);
				}
			})
		}
	};
	
	//第二步校验图片验证码
	$scope.judgecode = function(){
		$http.post("api/agent/sizeUpImgCode", {
			imgnum : $scope.imgcode
		}).success(function(data) {
			if (data.code == -1) {
				$scope.imgcodeboolean = true;
				$scope.reGetRandCodeImg();
			} else {
					$scope.codeStatus = true;
					$scope.sendnumber = 0;
					$(".mask").css('display','none');
					$(".re_alert_tab").css('display','none');
					$scope.newCode();
			}
		});
	}
	
	$scope.reGetRandCodeImg();
	$scope.init();
};

indexModule.controller("findpassController", findpassController);