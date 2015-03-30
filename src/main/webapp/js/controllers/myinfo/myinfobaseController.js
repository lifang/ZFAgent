'user strict';

// 系统设置模块
var myinfobaseModule = angular.module("myinfobaseModule", []);
var myinfobaseController = function($scope, $http,$location, LoginService) {
	var sid = $location.search()['id'];
	var customerId = LoginService.userid;
	var v1;//倒计时1
	var v2;//倒计时2
	var v3;//邮箱
	$scope.init = function() {
		$scope.intDiff=0;
		// 判断是否已登录
		if (LoginService.userid == 0) {
			window.location.href = '#/login';
		} else {
			$scope.$emit('changeshow', false);
			if(sid == LoginService.userid ){
				$('#base_box').hide();
				$('#upemail_box').show();
			}
			
		}
		$scope.req = {
			customer_id : LoginService.userid
		};
		 $scope.selected={};
		 $scope.selected_city={};
		 $http.post("api/customers/findCustById", $scope.req).success(function (data) {
			if (data.code == 1) {
				console.log("根据id获取用户信息"+data.result.parent_id);
				$scope.customer = data.result;
				$scope.customer.i_phone =data.result.phone;
				$scope.customer.i_email =data.result.email;
				$scope.cus_type = data.result.account_type;
			    $scope.selected.id = data.result.parent_id;
			    $scope.selected.name = data.result.p_name;
		        $scope.selected_city.id = data.result.id;
		        $scope.selected_city.name = data.result.c_name;
			}
		}).error(function(data) {

		});

	};
	
	$scope.city_list = function(){
		$http.post("api/index/getCity").success(function (data) {   
            if (data != null && data != undefined) {
                $scope.city_list = data.result;
            }
        });
	};
	//修改邮箱
	$scope.up_save = function(){
		var mail = $scope.customer.new_email;
		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
		if(!reg.test(mail)){
			alert("请输入合法的邮箱地址");
			return false;
		}
		$scope.req = {id:LoginService.userid,
					  email:mail};
		$http.post("api/customers/cust_update",$scope.req).success(function (data) {   
			if (data != null && data != undefined) {
				alert("修改成功");
				window.location.href = '#/myinfobase';
				location.reload();
			}
		});
	};
	
	$scope.ch_city = function(){
		$scope.selected_city = "";
	};
	$scope.colose_email = function(){
		$("#email_send_tab").css('display','none');
		$(".mask").css('display','none');
	};
	$scope.close_show_two = function(){
		$("#show_phone_input_my_t").css('display','none');
		$(".mask").css('display','none');
	};
	//根据手机号发送验证码
	$scope.sendPhoneCode = function(){
		var sMobile = $scope.i_phone_new; 
		if($scope.intDiff == 0){
			console.log("第二个  获取 验证码  开始");
			$scope.getPhoneCode(sMobile);
			$scope.intDiff = 120;
			clearInterval(v2);
			v2 = window.setInterval(function(){
				$('#show_phone_input_my_o_btn').html();
		    	if($scope.intDiff == 0){
		    		$('#show_phone_input_my_o_btn').html("发送验证码");
		    		clearInterval(v2);
		    	}else{
		    		$('#show_phone_input_my_o_btn').html("重新发送验证码（"+$scope.intDiff+"秒）");
		    	    $scope.intDiff--;
		    	}
		    }, 1000);
		}else{
			console.log("第二个  获取 验证码   时间未到");
		}
	};
 
	
	//第一次发送验证码   //
	$scope.send_code_one = function(t){
		if(t==1){
			$('#send_code_one').html("发送验证码");
			$scope.phone_code_i_o = "";
			$scope.i_phone_new = "";
			$scope.i_phone_code = "";
//			console.log(t+"第一次发送验证码");
//			 v1= window.setInterval(function(){
//				$('#send_code_one').html();
//		    	if($scope.intDiff == 0){
//		    		$('#send_code_one').html("发送验证码");
//		    		clearInterval(v1);
//		    	}else{
//		    		$('#send_code_one').html("重新发送验证码（"+$scope.intDiff+"秒）");
//		    	    $scope.intDiff--;
//		    	}
//		    }, 1000);
////			var sMobile = $scope.customer.phone; 
////			$scope.getPhoneCode(sMobile);
		}else if(t=2){//再次点击获取
			if($scope.intDiff == 0){
				$scope.intDiff =120;
				console.log(t+"再次点击获取发送验证码");
				v1= window.setInterval(function(){
					$('#send_code_one').html();
			    	if($scope.intDiff == 0){
			    		$('#send_code_one').html("发送验证码");
			    		clearInterval(v1);
			    	}else{
			    		$('#send_code_one').html("重新发送验证码（"+$scope.intDiff+"秒）");
			    	    $scope.intDiff--;
			    	}
			    }, 1000);
				var sMobile = $scope.customer.phone; 
				$scope.getPhoneCode(sMobile);
			}else{
				console.log(t+"再次点击获取发送验证码时间未到");
			}
		}
	};
	
	//根据手机号发送并获取验证码
	$scope.getPhoneCode = function(sMobile){
		$scope.req ={phone:sMobile};
		$http.post("api/index/getPhoneCode",$scope.req).success(function (data) {   
			if (data != null && data != undefined) {
				$scope.phone_code = data.result;
				console.log("code ==>"+$scope.phone_code);
			}
		});
	}
	//确认验证码，（第一次）
	$scope.yz_phone_code = function(){
		var p_code = $scope.phone_code;
		var i_code = $scope.phone_code_i_o;//输入的验证码
		if(p_code == i_code){
			var sMobile = $scope.i_phone_new; 
		    if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(sMobile))){ 
		        alert("不是完整的11位手机号或者正确的手机号前七位"); 
		        $("#i_phone_new").focus(); 
		        return false; 
		    }else{
				$("#show_phone_input_my_o").css('display','none');
				$(".mask").css('display','none');
				
				var doc_height = $(document).height();
				var doc_width = $(document).width();
				var win_height = $(window).height();
				var win_width = $(window).width();
				
				var layer_height = $("#show_phone_input_my_t").height();
				var layer_width = $("#show_phone_input_my_t").width();
				
				var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
					
			    $(".mask").css({display:'block',height:doc_height});
				$("#show_phone_input_my_t").css('top',(win_height-layer_height)/2);
				$("#show_phone_input_my_t").css('left',(win_width-layer_width)/2);
				$("#show_phone_input_my_t").css('display','block');
				
				
				//第二个验证框显示
				$('#show_phone_input_my_o_btn').html("发送验证码");
				clearInterval(v1);
				$scope.getPhoneCode(sMobile);
				$scope.intDiff = 120;
				console.log("确认获取验证码  开始倒计时" + $scope.intDiff);
				v2 = window.setInterval(function(){
					$('#show_phone_input_my_o_btn').html();
			    	if($scope.intDiff == 0){
			    		$('#show_phone_input_my_o_btn').html("发送验证码");
			    		clearInterval(v2);
			    	}else{
			    		$('#show_phone_input_my_o_btn').html("重新发送验证码（"+$scope.intDiff+"秒）");
			    	    $scope.intDiff--;
			    	}
			    }, 1000);
		    }
		}else{
			alert("验证码错误");
		}
	};
	
	//确认验证码，更新手机号
	$scope.change_phone_btn = function(){
		var p_code = $scope.phone_code;
		var i_code = $scope.i_phone_code;//输入的验证码
		if(p_code == i_code){
			var sMobile = $scope.i_phone_new; 
			$scope.req ={phone:sMobile,customer_id:LoginService.userid};
			$http.post("api/index/changePhone",$scope.req).success(function (data) {   
				if (data != null && data != undefined) {
					//重新刷新
					$("#show_phone_input_my_t").css('display','none');
					$(".mask").css('display','none');
					$scope.init();
					alert("修改成功");
				}
			});
		}else{
			alert("验证码错误");
		}
	};
	
	//修改邮箱
	$scope.up_email = function(){
		console.log("修改邮箱 start==》》"+$scope.intDiff);
//		email_send_btn
		if($scope.intDiff == 0){
    		$scope.intDiff =120;
			 v3 = window.setInterval(function(){
				$('#email_send_btn').html();
		    	if($scope.intDiff == 0){
		    		$('#email_send_btn').html("修改邮箱");
		    		clearInterval(v3);
		    	}else{
		    		$('#email_send_btn').html("等待（"+$scope.intDiff+"秒）");
		    	    $scope.intDiff--;
		    	}
		    }, 1000);
				var email = $scope.customer.email;
				$scope.req ={id:LoginService.userid,content:email,q:$scope.customer.name};
				$http.post("api/index/change_email",$scope.req).success(function (data) {   
					if (data != null && data != undefined) {
//						alert("发送成功,请注意查收!");
					}
				});
				
				//显示提示
				var doc_height = $(document).height();
				var doc_width = $(document).width();
				var win_height = $(window).height();
				var win_width = $(window).width();
				
				var layer_height = $("#email_send_tab").height();
				var layer_width = $("#email_send_tab").width();
				
				var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
				
			    $(".mask").css({display:'block',height:doc_height});
				$("#email_send_tab").css('top',(win_height-layer_height)/2);
				$("#email_send_tab").css('left',(win_width-layer_width)/2);
				$("#email_send_tab").css('display','block');
		}else{
			return false;
		}
	};
	
	$scope.save = function() {
		if($scope.selected_city==""){
			alert("请选择城市");
			return ;
		}
	/*	var email = $scope.customer.email;
		var phone = $scope.customer.phone;
		if(email==""){
			alert("请输入合法的邮箱地址");
			return ;
		}else{
			var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
			if(!reg.test(mail)){
				alert("请输入合法的邮箱地址");
				  $("#in_email").focus(); 
				return ;
			} 
		}
		if(phone==""){
			alert("请输入正确的手机号");
			return ;
		}else{
			 if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(sMobile))){ 
			        alert("请输入正确的手机号"); 
			        $("#in_phone").focus(); 
			        return false;
			 }
		}*/
		
		$scope.req = { 
			"accountType":$scope.cus_type,
			"id" : LoginService.userid,
			"name" : $scope.customer.name,
			"phone" : $scope.customer.phone,
			"email" : $scope.customer.email,
			"cityId" : $scope.selected_city.id
		};
		$http.post("api/customers/cust_update", $scope.req).success(function(data) {
			if (data.code == 1) {
				alert("保存成功");
			} else {
//				alert(data.message);
			}
		}).error(function(data) {

		});
	};
	$scope.init();
	$scope.city_list();
};
myinfobaseModule.controller("myinfobaseController", myinfobaseController);