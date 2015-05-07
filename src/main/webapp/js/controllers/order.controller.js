'user strict';

//批购
var wholesaleOrderModule = angular.module("wholesaleOrderModule",[]);

var wholesaleOrderController = function ($scope, $http, LoginService) {
//	$("#leftRoute").show();
//	if(LoginService.agentUserId == 0){
//		window.location.href = '#/login';
//	}else{
//		//显示用户登录部分
//		$scope.$emit('changeshow',false);
//	}
	initSystemPage($scope);// 初始化分页参数
	// 搜索
	$scope.submitSearch = function(){
//		initSystemPage($scope);// 初始化分页参数
		$scope.req = {
			customerId : LoginService.agentUserId,
			search : $scope.search,
			q : $scope.screen,
			p : "5",//  5 批购 3,4代购
			page : $scope.indexPage,
			rows : $scope.rows
		};
	
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  // 绑定
            if (data != null && data != undefined) {
            	if(data.code == 1){
            		  $scope.list = data.result;
                      calcSystemPage($scope, data.result.total);// 计算分页
            	}else {
            		window.location.href = '#/';
            	}
              
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	//筛选
	$scope.submitScreen = function(){
		initSystemPage($scope);// 初始化分页参数
		$scope.req = {
			customerId : LoginService.agentUserId,
			search : $scope.search,
			q : $scope.screen,
			p : "5",//   
			page : $scope.indexPage,
			rows : $scope.rows
		};
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                if(data.code == 1){
                	 $scope.list = data.result;
                     calcSystemPage($scope, data.result.total);// 计算分页
	          	}else {
	          		window.location.href = '#/';
	          	}
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	
	$scope.submitPage = function(){
		$scope.req={customerId:LoginService.agentUserId,search:$scope.search,q:$scope.screen,page:$scope.indexPage,p:"5",
				rows:$scope.rows};
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  //绑定
			if (data != null && data != undefined) {
				 if(data.code == 1){
                	 $scope.list = data.result;
                     calcSystemPage($scope, data.result.total);// 计算分页
	          	}else {
	          		window.location.href = '#/';
	          	}
			}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
	 
	//订单列表
	$scope.orderlist = function () {
		initSystemPage($scope);// 初始化分页参数
        $scope.req={customerId:LoginService.agentUserId,
        		page:$scope.indexPage,
        		rows:$scope.rows};
        $http.post("api/order/getWholesaleOrder", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
            	 if(data.code == 1){
                	 $scope.list = data.result;
                     calcSystemPage($scope, data.result.total);// 计算分页
	          	}else {
	          		window.location.href = '#/';
	          	}
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
    };
    //取消
    $scope.cancelApply = function(id){
    	$scope.req={id:id};
		$http.post("api/order/cancelWholesale", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
            	$scope.submitPage();
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	
    //详情
    $scope.orderinfo=function (p) {
    	LoginService.poscd=p.id;
    	$scope.poscd=p.id;
    	window.location.href = '#/orderinfo';
    };
    
	//显示支付
	$scope.showPay = function(id,i){
//		$scope.req={id:id};
//		console.log(">>>>>>>>>>>pay_price  showpay");
    	$("#o_id").val(id);
    	$("#o_index").val(i);
    	$("#pay_price").val("");
//		popup(".pay_tab",".pay_a");//代理商批购
		var doc_height = $(document).height();
		var doc_width = $(document).width();
		var win_height = $(window).height();
		var win_width = $(window).width();
		
		var layer_height = $(".pay_tab").height();
		var layer_width = $(".pay_tab").width();
		
		var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
		 
	    $(".mask").css({display:'block',height:doc_height});
		$(".pay_tab").css('top',(win_height-layer_height)/2);
		$(".pay_tab").css('left',(win_width-layer_width)/2);
		$(".pay_tab").css('display','block');
		
	};

    
    $scope.close = function(){
    	$(".pay_tab").css('display','none');
		$(".mask").css('display','none');
    }
    //支付 金额
    $scope.orderpay = function(o) {
    	$("#zf_yz").hide();
   		var pay_price = $("#pay_price").val();
//   		console.log(">>>>>>>>>>>"+pay_price);
   		if(isNaN(pay_price)){
   			alert("支付金额必须是数字");
   			return false;
   		}
    	var o_id = $("#o_id").val();
    	var o_index = $("#o_index").val();
    	var sy_price = $("#shenyu_price_"+o_index).val();
    	if(pay_price<0 || pay_price==''){
    		alert("请输入金额");
//    		$("#zf_yz").show();
    		return false;
    	}else if( sy_price  < pay_price ){
//    		console.log(">>>>>>muqian "+pay_price+">>>最多"+sy_price);
        		alert("最多只需支付"+sy_price);
        		return false;
    	}else if(parseInt(pay_price) > parseInt(sy_price)){
    		alert("最多只需支付"+sy_price);
    		return false;
    	}else if( pay_price < 0.01){
    		alert("大哥，至少赏个一分钱吧！");
    		return false;
    	}else{
    		$scope.close();
    	 	window.open("#/order_pay?id="+o_id+"&p="+pay_price) ;  
    	}
//    	window.open("alipayapi.jsp?WIDtotal_fee="+o.order_totalPrice/100+"&WIDsubject="+g_name+"&WIDout_trade_no="+o.order_number);  
	};
	
	
	//定金支付
	 $scope.depositpay = function(o) {
		 var dj = o.price_dingjin;
	    window.open("#/deposit_pay?id="+o.order_id+"&q=1") ;  
	};
	
	// 上一页
	$scope.prev = function() {
		if ($scope.indexPage > 1) {
			$scope.indexPage--;
			$scope.submitPage();
		}
	};

	// 当前页
	$scope.loadPage = function(currentPage) {
		$scope.indexPage = currentPage;
		$scope.submitPage();
	};

	// 下一页
	$scope.next = function() {
		if ($scope.indexPage < $scope.totalPage) {
			$scope.indexPage++;
			$scope.submitPage();
		}
	};

	// 跳转到XX页
	$scope.getPage = function() {
		$scope.indexPage = Math.ceil($scope.gotoPage);
		$scope.submitPage();
	};

    $scope.submitPage();
//    $scope.submitSearch();
//    $scope.orderlist();
};

//代购
var proxyOrderModule = angular.module("proxyOrderModule",[]);

var proxyOrderController = function ($scope, $http, LoginService) {
	$("#leftRoute").show();
	if(LoginService.agentUserId == 0){
		window.location.href = '#/login';
	}else{
		//显示用户登录部分
		$scope.$emit('changeshow',false);
	}
	initSystemPage($scope);// 初始化分页参数
	// 搜索
	$scope.submitSearch = function(){
		initSystemPage($scope);// 初始化分页参数
		$scope.req = {
			customerId : LoginService.agentUserId,
			search : $scope.search,
			q : $scope.screen,
			p : "",//  默认 代购
			page : $scope.indexPage,
			rows : $scope.rows
		};
	
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  // 绑定
            if (data != null && data != undefined) {
            	 if(data.code == 1){
                	 $scope.list = data.result;
                     calcSystemPage($scope, data.result.total);// 计算分页
	          	}else {
	          		window.location.href = '#/';
	          	}
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	//筛选
	$scope.submitScreen = function(){
//		initSystemPage($scope);// 初始化分页参数
		$scope.req = {
			customerId : LoginService.agentUserId,
			search : $scope.search,
			q : $scope.screen,
			p : "",//   
			page : $scope.indexPage,
			rows : $scope.rows
		};
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
            	 if(data.code == 1){
                	 $scope.list = data.result;
                     calcSystemPage($scope, data.result.total);// 计算分页
	          	}else {
	          		window.location.href = '#/';
	          	}
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	
    $scope.close = function(){
    	$(".pay_tab").css('display','none');
		$(".mask").css('display','none');
    }
    
	$scope.submitPage = function(){
		$scope.req={customerId:LoginService.agentUserId,search:$scope.search,q:$scope.screen,page:$scope.indexPage,p:"",
				rows:$scope.rows};
		$http.post("api/order/orderSearch", $scope.req).success(function (data) {  //绑定
			if (data != null && data != undefined) {
				 if(data.code == 1){
                	 $scope.list = data.result;
                     calcSystemPage($scope, data.result.total);// 计算分页
	          	}else {
	          		window.location.href = '#/';
	          	}
			}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
	 
	//订单列表
	$scope.orderlist = function () {
		initSystemPage($scope);// 初始化分页参数
        $scope.req={customerId:LoginService.agentUserId,
        		page:$scope.indexPage,
        		rows:$scope.rows};
        $http.post("api/order/getProxyOrder", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
            	 if(data.code == 1){
                	 $scope.list = data.result;
                     calcSystemPage($scope, data.result.total);// 计算分页
	          	}else {
	          		window.location.href = '#/';
	          	}
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
    };
    //取消
    $scope.cancelApply = function(id){
    	$scope.req={id:id};
		$http.post("api/order/cancelProxy", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
            	$scope.submitPage();
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
    //详情
    $scope.orderinfo=function (p) {
    	LoginService.poscd=p.id;
    	$scope.poscd=p.id;
    	window.location.href = '#/orderinfo';
    };

    $scope.topay = function(o) {
//    	var g_name = $("#g_name").val();
    	window.open("#/topay?id="+o.order_id) ;  
//    	window.open("alipayapi.jsp?WIDtotal_fee="+o.order_totalPrice/100+"&WIDsubject="+g_name+"&WIDout_trade_no="+o.order_number);  
	};
	
	// 上一页
	$scope.prev = function() {
		if ($scope.indexPage > 1) {
			$scope.indexPage--;
			$scope.submitPage();
		}
	};

	// 当前页
	$scope.loadPage = function(currentPage) {
		$scope.indexPage = currentPage;
		$scope.submitPage();
	};

	// 下一页
	$scope.next = function() {
		if ($scope.indexPage < $scope.totalPage) {
			$scope.indexPage++;
			$scope.submitPage();
		}
	};

	// 跳转到XX页
	$scope.getPage = function() {
		$scope.indexPage = Math.ceil($scope.gotoPage);
		$scope.submitPage();
	};

    $scope.submitPage();
//    $scope.submitSearch();
//    $scope.orderinfo();
};

 
var topayModule = angular.module("topayModule",[]);
var payController = function($scope, $http,$location,LoginService) {
	$scope.pay=true;
	$scope.req={};
	$scope.order={};
	$scope.payway=1;
	$scope.req.id=$location.search()['id'];
	$scope.init = function() {
		if(LoginService.userid == 0){
			window.location.href = '#/login';
		}
		$scope.getOrder();
	};
	$scope.getOrder = function() {
		$http.post("api/shop/payOrder", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.order=data.result;
            	if(data.result.paytype>0){
            		$scope.pay=false;
            		$scope.payway=data.result.paytype;
            	}
            }
        });
	};
	$scope.pay= function(){
		$http.post("api/shop/payOrder", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	if(data.result.paytype>0){
            		alert("当前订单已支付成功，请不要重复支付");
            		$scope.pay=false;
            		$scope.payway=data.result.paytype;
            		$('#payTab').hide();
            		$('.mask').hide();
            		window.location.href = '#/proxyOrder';
            		return;
            	}
            }
        });
		$('#payTab').show();
		$scope.order.title="";
    	var count=0;
    	angular.forEach($scope.order.good, function (one) {
    		if(count<2){
    			$scope.order.title+=one.title+" "+one.pcname+"("+one.quantity+"件)";
    		}
    		count++;
    	});
    	if(count>2){
    		$scope.order.title+="..";
    	}
    	if(1==$scope.payway){
			//alert("支付宝");
			window.open("alipayapi.jsp?WIDtotal_fee="+
					$scope.order.actual_price/100+"&WIDsubject="+$scope.order.title
					+"&WIDout_trade_no="+$scope.order.order_number);  
		}else if(2==$scope.payway){
			window.open("unionpay.jsp?WIDtotal_fee="+
					$scope.order.actual_price/100+"&WIDsubject="+$scope.order.title
					+"&WIDout_trade_no="+$scope.order.order_number.replace("_","X"));  
		}else{
			//alert("银行");
			alert("暂不支持，请联系系统管理员。");
		}
	}
	$scope.finish= function(){
		$http.post("api/shop/payOrder", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.order=data.result;
            	if(data.result.paytype>0){
            		$scope.pay=false;
            		$scope.payway=data.result.paytype;
            		$('#payTab').hide();
            		$('.mask').hide();
            		window.location.href = '#/proxyOrder';
            	}else{
            		alert("尚未支付,如有疑问请联系400-009-0876");
            	}
            	
            }
        });
	};
	$scope.init();
};


var orderpayModule = angular.module("orderpayModule",[]);
var orderpayController = function($scope, $http,$location,LoginService) {
	$scope.pay=true;
	$scope.req={};
	$scope.order={};
	$scope.payway=1;
	$scope.req.id=$location.search()['id'];
	var price =$location.search()['p'];//
	var q =$location.search()['q'];//  1　定金支付
	$scope.req.webPrice = price;
	$scope.req.q = q;
	$scope.getOrder = function() {
		$http.post("api/order/payOrder", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.order=data.result;
            	$scope.p=price;
            	if(data.result.is_true==0){
            		alert("不需要支付那么多哦!!!");
            		return false;
            	}
            	
            	if(data.result.paytype>0){
            		$scope.pay=false;
            		$scope.payway=data.result.paytype;
            	}
            }
        });
	};
	
//	$scope.to_pay= function(){
//		$('#payTab').show();
//		if(1==$scope.payway){
//			//alert("支付宝");
//			$scope.order.title="";
//        	var count=0;
//        	 angular.forEach($scope.order.good, function (one) {
//                 if(count<2){
//                	 $scope.order.title+=one.title+" "+one.pcname+"("+one.quantity+"件)";
//                 }
//                 count++;
//             });
//        	 if(count>2){
//        		 $scope.order.title+="..";
//        	 }
//			window.open("alipayapi.jsp?WIDtotal_fee="+
//					$scope.order.total_price/100+"&WIDsubject="+$scope.order.title
//					+"&WIDout_trade_no="+$scope.order.order_number);  
//		}else if(2==$scope.payway){
//			window.open("unionpay.jsp?WIDtotal_fee="+
//					$scope.order.total_price/100+"&WIDsubject="+$scope.order.title
//					+"&WIDout_trade_no="+$scope.order.order_number);  
//		}else{
//			//alert("银行");
//			alert("暂不支持，请联系系统管理员。");
//		}
//	}
	
	//定金支付  跳转至支付宝
	$scope.depositpay= function(){
		$http.post("api/shop/payOrder", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	if(data.result.paytype>0){
            		alert("当前订单已支付成功，请不要重复支付");
            		$scope.pay=false;
            		$scope.payway=data.result.paytype;
            		$('#payTab').hide();
            		$('.mask').hide();
            		window.location.href = '#/proxyOrder';
            		return;
            	}
            }
        });
		$('#payTab').show();
		$('.mask').show();
		var body = "定金付款 "+ $scope.order.body;
		$scope.order.title="";
//    	var count=0;
//    	 angular.forEach($scope.order.good, function (one) {
//             if(count<2){
//            	 $scope.order.title+=one.title+" "+one.pcname+"("+one.quantity+"件)";
//             }
//             count++;
//         });
//    	 if(count>2){
//    		 $scope.order.title+="..";
//    	 }
		if(1==$scope.payway){
			window.open("depositalipayapi.jsp?WIDtotal_fee="+
					$scope.order.price_dingjin/100+"&WIDsubject="+"定金支付"+"&WIDbody="+body
					+"&WIDout_trade_no="+$scope.order.order_number);  
		}else if(2==$scope.payway){
			window.open("unionpay.jsp?WIDtotal_fee="+
					$scope.order.price_dingjin/100+"&WIDsubject="+"定金支付"+"&WIDbody="+body
					+"&WIDout_trade_no="+$scope.order.order_number.replace("_","X"));  
		}else{
			//alert("银行");
			alert("暂不支持，请联系系统管理员。");
		}
	}
	
 
	//订单支付
	$scope.orderpay= function(){
//		console.log(">>>>>去支付");
		$scope.req={};
		$scope.req.id=$location.search()['id'];
		var price =$location.search()['p'];//
		$scope.req.webPrice = price;
	
		$http.post("api/order/payOrder", $scope.req).success(function (data) {  //绑定
	        if (data.code==1) {
	        	if(data.result.is_true==0){
	        		alert("不需要支付那么多哦!!!");
	        		return false;
	        	}else{
	        		if($scope.p < 0.01){
	        			alert("大哥，至少赏个一分钱吧!！");
	        			return false;
	        		}
//	        		console.log(">>>>>>金额正确>>>");
	        		$('#payTab').show();
	        		$('.mask').show();
	        		$scope.order.title="订单付款";
	    			var body = "订单付款  "+ $scope.order.body;
	        		if(1==$scope.payway){
		    			//alert("支付宝");
		    			window.open("depositalipayapi.jsp?WIDtotal_fee="+
		    					$scope.p+"&WIDsubject="+$scope.order.title+"&WIDbody="+body
		    					+"&WIDout_trade_no="+$scope.order.order_number);  
		    		}else if(2==$scope.payway){
		    			window.open("unionpay.jsp?WIDtotal_fee="+
		    					$scope.p+"&WIDsubject="+$scope.order.title+"&WIDbody="+body
		    					+"&WIDout_trade_no="+$scope.order.order_number.replace("_","X"));  
		    		}else{
		    			//alert("银行");
		    			alert("暂不支持，请联系系统管理员。");
		    		}
	        	}
	        }
	    });
	}
	$scope.deposit_finish= function(){  //  批购定金支付
		$http.post("api/order/payFinish", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	var r =data.result;
            	if(r ==1){  //定金支付成功
            		$scope.pay=false;
            		$scope.payway=1;
            		$('#payTab').hide();
            		$('.mask').hide();
            		window.location.href = '#/wholesaleOrder';
            	}else{
            		alert("尚未支付,如有疑问请联系400-009-0876");
            	}
            }
        });
	};
	$scope.order_finish= function(){  //  批购支付
		$http.post("api/order/payFinish", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	var r =data.result;
            	if(r ==2){  // 支付成功
            		$scope.pay=false;
            		$scope.payway=1;
            		$('#payTab').hide();
            		$('.mask').hide();
            		window.location.href = '#/wholesaleOrder';
            	}else{
            		alert("尚未支付,如有疑问请联系400-009-0876");
            	}
            }
        });
	};
	$scope.getOrder();
};


wholesaleOrderModule.$inject = ['$scope', '$http', '$cookieStore'];
proxyOrderModule.$inject = ['$scope', '$http', '$cookieStore'];
wholesaleOrderModule.controller("wholesaleOrderController", wholesaleOrderController); //批购
proxyOrderModule.controller("proxyOrderController", proxyOrderController); //代购
orderpayController.$inject = ['$scope','$http','$location','LoginService'];
orderpayModule.controller("orderpayController", orderpayController);
payController.$inject = ['$scope','$http','$location','LoginService'];
topayModule.controller("payController", payController);
