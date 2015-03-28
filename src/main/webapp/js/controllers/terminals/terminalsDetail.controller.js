'user strict';

//系统设置模块
var terminalDetailModule = angular.module("terminalDetailModule",['loginServiceModule']);

var terminalDetailController = function ($scope, $http,$location, LoginService) {
	$scope.terminalId=Math.ceil($location.search()['terminalId']);
	$scope.customerId = LoginService.userid;;
	$(".leaseExplain_tab").hide();
	$("#pass").hide();
	//查看终端详情
	$scope.terminalDetail = function () {
		if(LoginService.userid == 0){
			window.location.href = '#/login';
		}else{
			//显示用户登录部分
			$scope.$emit('changeshow',false);
		}
		//0 注销， 1 更新
		  $scope.types = 0;
      $http.post("api/terminal/getWebApplyDetails", {types:$scope.types,terminalsId:$scope.terminalId,customerId:$scope.customerId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
              //终端信息
              $scope.applyDetails = data.result.applyDetails;
              //交易
              $scope.rateList = data.result.rates;
              //租赁
              $scope.tenancy  = data.result.tenancy;
              //追踪记录
              $scope.trackRecord = data.result.trackRecord;
              //资料
              $scope.openingDetails = data.result.openingDetails;
          }
      }).error(function (data) {
    	  alert("获取列表失败");
          /*$("#serverErrorModal").modal({show: true});*/
      });
  };
  
//租借說明弹出层
  $scope.popup = function(t,b){
	  /*$(".mask").show();
	  $(".leaseExplain_tab").show();*/
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
  }
  
  //关闭弹出框
  $scope.closeDocument = function(obj){
	  $("#"+obj).hide();
	  $(".mask").hide ();
  }
//同步
  $scope.synchronous = function(){
	  $http.post("api/terminal/synchronous").success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  alert(data.code);
          }
      }).error(function (data) {
    	  alert("同步失败");
      });
  }
  
  //找回POS机密码
  $scope.findPassword = function(){
	  $http.post("api/terminal/Encryption", {terminalid:$scope.terminalId}).success(function (data) {  //绑定
          if (data != null && data != undefined) {
        	  $scope.pass = data.result;
        	  $(".mask").show();
        	  $("#pass").show();
          }
      }).error(function (data) {
    	  alert("获取列表失败");
      });
  }
  
  //申请换货判断
  $scope.judgeChang = function(){
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
  }
  
  //申请跟新判断
  $scope.judgeUpdate = function(){
	  $http.post("api/terminal/judgeUpdate", {terminalid:$scope.terminalId}).success(function (data) {  //绑定
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
  
//申请注销判断
  $scope.judgeRentalReturn = function(){
	  $http.post("api/terminal/judgeRentalReturn", {terminalid:$scope.terminalId}).success(function (data) {  //绑定
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
  
  //申请维修判断
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
  }
  
  //申请退货判断
  $scope.judgeReturn = function(){
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
  }
  
//申请租赁退还
  $scope.terminalsRentalReturn = function(){
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
  }
  $scope.terminalDetail();

};





terminalDetailModule.$inject = ['$scope', '$http', '$cookieStore'];
terminalDetailModule.controller("terminalDetailController", terminalDetailController);
