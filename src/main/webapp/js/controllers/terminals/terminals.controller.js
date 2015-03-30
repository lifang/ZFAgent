'user strict';

//系统设置模块
var terminalModule = angular.module("agentTerminalModule",['loginServiceModule']);

var agentTerminalController = function ($scope, $http, LoginService) {
	  initSystemPage($scope);// 初始化分页参数
	  //$scope.customersId = LoginService.userid;
	  $scope.customersId = 80;
	  $scope.total = 0;
	  //付款筛选状态
	  $scope.frontStatus = null;
	  //根据终端号筛选
	  $scope.serialNum = null;
	
	//获得终端列表
	$scope.getInfo = function () {
		
		/*if(LoginService.userid == 0){
			window.location.href = '#/login';
		}else{
			//显示用户登录部分
			$scope.$emit('changeshow',false);
		} */
      $scope.req={
    		  customersId:$scope.customersId,
    		  page:$scope.indexPage,
    		  rows:$scope.rows,
    		  frontStatus:$scope.frontStatus,
    		  serialNum:$scope.serialNum
    		  };
      
      $http.post("api/webTerminal/getAgentApplyList", $scope.req).success(function (data) {  //绑定
          if (data != null && data != undefined) {
              $scope.list = data.result.list;
              $scope.total = data.result.total;
              $scope.appstatus = data.result.frontPayStatus;
              //所有通道
              //$scope.channels = data.result.channels;
          }
          $scope.pages = [];
          calcSystemPage($scope, $scope.total);// 计算分页
      }).error(function (data) {
    	  alert("获取列表失败");
      });
	}  
	
	/*$scope.payChannelId = null;
	//添加終端是通道Id
	$scope.channelId = function(){
		//$scope.payChannelId = Math.ceil(chanId);
	}*/
	//添加終端$scope.channels
	/*$scope.addChannel = function() {
		if ($scope.payChannelId == null) {
			alert("请选择通道！");
		} else if ($scope.title == undefined) {
			alert("请填写商户名！");
		} else if ($scope.serialNums == undefined) {
			alert("请填写终端号！");
		} else {
			$scope.addChan = {
				customerId : $scope.customersId,
				title : $scope.title,
				payChannelId : $scope.payChannelId,
				serialNum : $scope.serialNums
			};
			$http.post("api/terminal/addTerminal", $scope.addChan).success(
					function(data) { // 绑定
						if (data != null && data != undefined) {
							if (data.code == 1) {
								// location.reload();
								$("#closeWin").css('display', 'none');
								$(".mask").css('display', 'none');

								$scope.serialNum = null;
								$scope.list = [];
								$scope.total = null;
								$scope.getInfo();
								location.reload();

							} else {
								alert(data.message);
							}
						}
					}).error(function(data) {
				alert("获取列表失败");
			});
		}
	}
	*/
	// 筛选状态
	$scope.screening = function(){
		$scope.indexPage = 1;
		//再一次初始化分页
		initSystemPage($scope);
		$scope.frontStatus = Math.ceil($scope.screeningStatus);
		//取消终端号的筛选
		$scope.serialNum = null;
		$scope.getInfo();
	}
	
	//筛选终端号
	$scope.screeningSerialNum = function(){
		 $scope.indexPage = 1;
		//取消终端状态的筛选
		$scope.frontStatus = null;
		$scope.getInfo();
	}

	//go to page
	$scope.tiaoPage = 1;
	$scope.getPage = function(){
		$scope.indexPage = Math.ceil($scope.tiaoPage);
		$scope.getInfo();
	};

	//下一页
  $scope.next = function () {
      if ($scope.indexPage < $scope.totalPage) {
          $scope.indexPage++;
          $scope.getInfo();
      }
  };

  //上一页
  $scope.prev = function () {
      if ($scope.indexPage > 1) {
          $scope.indexPage--;
          $scope.getInfo();
      }
  };

  $scope.loadPage = function (page) {
      $scope.indexPage = page;
      $scope.getInfo();
  };
  
  $scope.getInfo();

};

var terminalDetailController = function ($scope, $http,$location, LoginService) {
	//$scope.terminalId=Math.ceil($location.search()['terminalId']);
	$scope.terminalId=1;
	//$scope.customerId = LoginService.userid;;
	$scope.customerId = 80;
	$(".leaseExplain_tab").hide();
	$("#pass").hide();
	//查看终端详情
	$scope.terminalDetail = function () {
		/*if(LoginService.userid == 0){
			window.location.href = '#/login';
		}else{
			//显示用户登录部分
			$scope.$emit('changeshow',false);
		}*/
		//0 注销， 1 更新
		  //$scope.types = 0;
	//获取终端详情
      $http.post("api/webTerminal/getWebApplyDetail", {types:$scope.types,terminalsId:$scope.terminalId,customerId:$scope.customerId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  if(data.code == 1){
        		  //终端信息
                  $scope.applyDetails = data.result.applyDetails;
                  //交易
                  $scope.rateList = data.result.rates;
                  //租赁
                  $scope.tenancy  = data.result.tenancy;
                  //租赁
                  $scope.openingInfos  = data.result.openingInfos;
                  //资料
                  $scope.openingDetails = data.result.openingDetails;
                  //追踪记录
                  $scope.trackRecord = data.result.trackRecord;
        	  }
          }
      }).error(function (data) {
    	  alert("获取列表失败");
          /*$("#serverErrorModal").modal({show: true});*/
      });
  };
  
//申请注销判断
  $scope.judgeRentalReturn = function(){
	  $http.post("api/webTerminal/judgeRentalReturn", {terminalid:$scope.terminalId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  if(data.code == -1){
        		  alert("已有该终端注销申请！");
        	  }else if(data.code == 1){
        		  window.location.href = "#/terminalCancellation?terminalId="+$scope.terminalId;
        		  
        	  }
          }
      }).error(function (data) {
    	  alert("操作失败！");
      });
  }
  
//申请更新判断
  $scope.judgeUpdate = function(){
	  $http.post("api/webTerminal/judgeUpdate", {terminalid:$scope.terminalId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  if(data.code == -1){
        		  alert("已有该终端更新申请！");
        	  }else if(data.code == 1){
        		  window.location.href = "#/terminalToUpdate?terminalId="+$scope.terminalId;
        		  
        	  }
          }
      }).error(function (data) {
    	  alert("操作失败！");
      });
  }
  
//找回POS机密码
  $scope.pass =null;
  $scope.findPassword = function(){
	  $http.post("api/webTerminal/Encryption", {terminalid:$scope.terminalId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  if(data.code == 1){
            	  $(".mask").show();
            	  $("#pass").show();
            	  $("#passdiv").html(data.result);
        	  }
          }
      }).error(function (data) {
    	  alert("获取列表失败");
      });
  }
  
  //关闭弹出框
  $scope.closeDocument = function(obj){
	  $("#"+obj).hide();
	  $(".mask").hide ();
  }
  
//租借說明弹出层
  /*$scope.popup = function(t,b){
	  $(".mask").show();
	  $(".leaseExplain_tab").show();
	  var doc_height = $(document).height();
		var doc_width = $(document).width();
		var win_height = $(window).height();
		var win_width = $(window).width();
		
		var layer_height = $(t).height();
		var layer_width = $(t).width();
		
		var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
		
		//tab
		$(b).bind('click',function(){
			    $(".mask").css({display:'block',height:doc_height});
				$(t).css('top',(win_height-layer_height)/2);
				$(t).css('left',(win_width-layer_width)/2);
				$(t).css('display','block');
				return false;
			}
		)
		$(".close").click(function(){
			$(t).css('display','none');
			$(".mask").css('display','none');
		})
  }*/
  
 
//同步
  /*$scope.synchronous = function(){
	  $http.post("api/terminal/synchronous").success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  alert(data.code);
          }
      }).error(function (data) {
    	  alert("同步失败");
      });
  }*/
  
  
  
  //申请换货判断
  /*$scope.judgeChang = function(){
	  $http.post("api/terminal/judgeChang", {terminalid:$scope.terminalId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  if(data.code == -1){
        		  alert("已有该终端换货申请！");
        	  }else if(data.code == 1){
        		  window.location.href = "#/terminalExchangeGoods?terminalId="+$scope.terminalId;
        		  
        	  }
          }
      }).error(function (data) {
    	  alert("操作失败！");
      });
  }*/
  
  
  

  
  /*//申请维修判断
  $scope.judgeRepair = function(){
	  $http.post("api/terminal/judgeRepair", {terminalid:$scope.terminalId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  if(data.code == -1){
        		  alert("已有该终端维修申请！");
        	  }else if(data.code == 1){
        		  window.location.href = "#/terminalRepair?terminalId="+$scope.terminalId;
        		  
        	  }
          }
      }).error(function (data) {
    	  alert("操作失败！");
      });
  }*/
  
  //申请退货判断
  /*$scope.judgeReturn = function(){
	  $http.post("api/terminal/judgeReturn", {terminalid:$scope.terminalId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  if(data.code == -1){
        		  alert("已有该终端退货申请！");
        	  }else if(data.code == 1){
        		  window.location.href = "#/terminalReturnGood?terminalId="+$scope.terminalId;
        		  
        	  }
          }
      }).error(function (data) {
    	  alert("操作失败！");
      });
  }*/
  
//申请租赁退还
 /* $scope.terminalsRentalReturn = function(){
	  $http.post("api/terminal/JudgeLeaseReturn", {terminalid:$scope.terminalId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  if(data.code == -1){
        		  alert("已有该终端租赁退还申请！");
        	  }else if(data.code == 1){
        		  window.location.href = "#/terminalRentalReturn?terminalId="+$scope.terminalId;
        		  
        	  }
          }
      }).error(function (data) {
    	  alert("操作失败！");
      });
  }*/
  $scope.terminalDetail();

};

var agentServiceTerminalController = function ($scope, $http, LoginService) {
	//$scope.customersId = LoginService.userid;
	  $scope.customersId = 80;
	  $scope.butshow = false;//添加新地址显示
	  $scope.serviceObject = {};//数据封装
	  $scope.addressObject = {};//数据封装
	 
	 $scope.serviceInit = function(){
		 $scope.cityList();
		 $scope.getAddress();
	 }
	 
	 //获得联系地址
	 $scope.getAddress = function(){
		  $http.post('api/webTerminal/getAddressee',{customerId:$scope.customersId}).success(function(data){
			 if(data.code == 1){
				 $scope.addressList = data.result;
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("联系地址请求失败！");
		 })
	 }
	 
	 //添加联系地址
	 $scope.addAddress = function(){
		 $scope.addressObject.cityId = $scope.serviceObject.sitys.id;
		 $scope.addressObject.customerId = $scope.customersId;
		 $http.post('api/webTerminal/addCostometAddress',$scope.addressObject).success(function(data){
			 if(data.code == 1){
				 $scope.getAddress();
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 })
	 }
	 
	 $scope.radioId = function(obj){
		 for(var i=0;i< $scope.addressList.length;i++){
			 if(obj == i){
				 $scope.serviceObject.receiver = $scope.addressList[i].receiver;
				 $scope.serviceObject.address = $scope.addressList[i].address;
				 $scope.serviceObject.zipCode = $scope.addressList[i].zipCode;
				 $scope.serviceObject.phone = $scope.addressList[i].moblephone;
				 //$scope.serviceObject.cityId = $scope.addressList[i].cityId;
			 }
		 }
	 };
	 
	 $scope.terminalSub = function(){
		 $scope.serviceObject.customerId = $scope.customersId;
		 $scope.serviceObject.content = $("#comsName").html()+$scope.coms+","+$("#orderName").html()+$scope.order;
		 $http.post('api/webTerminal/submitAgent',$scope.serviceObject).success(function(data){
			 if(data.code == 1){
				 alert(data.code);
			 }else if(data.code == -1){
				 alert(data.code);
				 alert(data.result);
			 }
		 })
	 }
	 //获得省市
	 $scope.cityList = function(){
		 $http.post('api/comment/getCity').success(function(data){
			 if(data.code == 1){
				$scope.cityList = data.result;
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("城市列表请求失败！");
		 })
	 }
	 //初始化数据
	 $scope.serviceInit();
};

var agentBinTerminalController = function ($scope, $http, LoginService) {
	//$scope.customersId = LoginService.userid;
	  $scope.customersId = 80;
	 $scope.butshow = true;//按钮切换
	 $scope.binobject = {};//数据封装
	 
	 $scope.bininit = function(){
		 $scope.cityList();
	 }
	 
	 //获得省市
	 $scope.cityList = function(){
		 $http.post('api/comment/getCity').success(function(data){
			 if(data.code == 1){
				$scope.cityList = data.result;
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("城市列表请求失败！");
		 })
	 }
	 
	 //搜索现有用户 （类型为用户）
	 $scope.agentToUserName = null;
	 $scope.userId = -1;
	 $scope.searchUser = function(){
		 $http.post('api/webTerminal/searchUser',{name:$scope.agentToUserName,customerId:$scope.customersId}).success(function(data){
			 if(data.code == 1){
				 $scope.agentToUsers = data.result;
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("用户列表请求失败！");
		 })
	 }
	 
	 //点击获取用户id
	 $scope.checkUserId = function(num){
		 $scope.userId = num;
	 }
	 
	 //开始绑定
	 $scope.BindingTerminals = function(){
		 $http.post('api/webTerminal/BindingTerminals',{customerId:$scope.customersId,terminalsNum:$scope.terminalsNum,userId:Math.ceil($scope.userId)}).success(function(data){
			 if(data.code == 1){
				 alert(data.result);
			 }else if(data.code == -1){
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("绑定请求失败！");
		 })
	 }
	 //创建新用户
	 $scope.addShow = false;
	 $scope.establish = function(){
		 $scope.binobject.cityid = Math.ceil($scope.binobject.address.id);
		 $http.post('api/webTerminal/addCustomer',$scope.binobject).success(function(data){
			 if(data.code == 1){
				 $scope.aduser = data.result.username;
				 $scope.binobject = {};
				 $scope.addShow = true;
				 $scope.userId = data.result.id;
			 }else if(data.code == -1){
				 $scope.addShow = false;
				 $scope.binobject = {};
				 alert(data.message);
			 }
		 }).error(function(){
			 alert("绑定请求失败！");
		 })
	 }
	 
	 
	 
	
	 //初始化数据
	 $scope.bininit();
};

var terminalCancellationController = function ($scope, $http,$location, LoginService) {
	//$scope.terminalId=Math.ceil($location.search()['terminalId']);
	$scope.terminalId = 1;
	//$scope.customerId = LoginService.userid;
	$scope.customerId = 80;
	//查看终端详情
	$scope.terminalDetail = function () {
		/*if(LoginService.userid == 0){
			window.location.href = '#/login';
		}else{
			//显示用户登录部分
			$scope.$emit('changeshow',false);
		}*/
		//0 注销， 1 更新
	  $scope.types = 0;
      $http.post("api/webTerminal/getWebApplyCancellation", {types:$scope.types,terminalsId:$scope.terminalId,customerId:$scope.customerId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  if(data.code == 1){
        		  //终端信息
                  $scope.applyDetails = data.result.applyDetails;
                  //注销模板
                  $scope.reModel = data.result.ReModel;
        	  }
          }
      }).error(function (data) {
    	  alert("获取列表失败");
      });
  };
  
//提交注销申请
	$scope.subRentalReturn = function(){
		
		
		$scope.array = [];
		 for(var i=0;i<$scope.reModel.length;i++){
			$scope.array[i] = {
					"id":$("#upId_"+i).val()+"",
					"path":$("#up_"+i).val()+""
			};
		 }
		
		 $scope.map = {
 				terminalId : $scope.terminalId,
 				status : 1,
 				templeteInfoXml :JSON.stringify($scope.array),
 				type : 3,
 				customerId:$scope.customerId
 		 }
		 $http.post("api/webTerminal/subRentalReturn", $scope.map).success(function (data) {  //绑定
	          if (data != null && data != undefined) {
	        	  if(data.code == 1){
	        		  window.location.href ='#/terminalDetail?terminalId='+$scope.terminalId;
	        	  }else{
	        		alert("提交失败！");
	        	  }
	          }
	      }).error(function (data) {
	    	  alert("获取列表失败");
	      });
	}
  $scope.terminalDetail();
};

var terminalToUpdateController = function ($scope, $http,$location, LoginService) {
	//$scope.terminalId=Math.ceil($location.search()['terminalId']);
	$scope.terminalId = 1;
	//$scope.customerId = LoginService.userid;
	$scope.customerId = 80;
	//$(".leaseExplain_tab").hide();
	//查看终端详情
	$scope.terminalDetail = function () {
		/*if(LoginService.userid == 0){
			window.location.href = '#/login';
		}else{
			//显示用户登录部分
			$scope.$emit('changeshow',false);
		}*/
		//0 注销， 1 更新
	  $scope.types = 1;
      $http.post("api/webTerminal/getWebApplyCancellation", {types:$scope.types,terminalsId:$scope.terminalId,customerId:$scope.customerId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  if(data.code == 1){
        		//终端信息
                  $scope.applyDetails = data.result.applyDetails;
                //下载模板
                  $scope.reModel = data.result.ReModel;
        	  }
          }
      }).error(function (data) {
    	  alert("获取列表失败");
          /*$("#serverErrorModal").modal({show: true});*/
      });
      
  };
  
/*//弹出层
  $scope.popup = function(t,b){
	  $(".mask").show();
	  $(".leaseExplain_tab").show();
  }*/
  
//提交
	$scope.subToUpdate = function () {
		
		$scope.array = [];
		 for(var i=0;i<$scope.reModel.length;i++){
			$scope.array[i] = {
					id:$("#upId_"+i).val(),
					path:$("#up_"+i).val()
			};
		 }
		
		$scope.message = {
				terminalsId:Math.ceil($scope.terminalId),
				customerId:Math.ceil($scope.customerId),
				status:1,
				templeteInfoXml :JSON.stringify($scope.array),
				};
		
  $http.post("api/webTerminal/getApplyToUpdate", $scope.message).success(function (data) {  //绑定
      if (data != null && data != undefined) {
    	  if(data.code == 1){
    		  window.location.href ='#/terminalDetail?terminalId='+$scope.terminalId;
    	  }else{
    		  alert("跟新失败！");
    	  }
        
      }
  }).error(function (data) {
	  alert("操作失败");
  });
  
};
  $scope.terminalDetail();

};

terminalModule.$inject = ['$scope', '$http', '$cookieStore'];
terminalModule.controller("agentTerminalController", agentTerminalController);
terminalModule.controller("terminalDetailController", terminalDetailController);
terminalModule.controller("agentServiceTerminalController", agentServiceTerminalController);
terminalModule.controller("agentBinTerminalController", agentBinTerminalController);
terminalModule.controller("terminalCancellationController", terminalCancellationController);
terminalModule.controller("terminalToUpdateController", terminalToUpdateController);
