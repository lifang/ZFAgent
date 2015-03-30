'user strict';

//系统设置模块
var terminalCancellationModule = angular.module("terminalCancellationModule",['loginServiceModule']);

var terminalCancellationController = function ($scope, $http,$location, LoginService) {
	$scope.terminalId=Math.ceil($location.search()['terminalId']);
	$scope.customerId = LoginService.userid;
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
              //注销模板
              $scope.ReModel = data.result.ReModel;
          }
      }).error(function (data) {
    	  alert("获取列表失败");
      });
  };
  
//提交注销申请
	$scope.subRentalReturn = function(){
		
		
		$scope.array = [];
		 for(var i=0;i<$scope.ReModel.length;i++){
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
		 $http.post("api/terminal/subRentalReturn", $scope.map).success(function (data) {  //绑定
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


//改变上传按钮
function setSpanName(obj){
	//改变下载模板初始状态
	$(obj).parent("a").children("span").html("重新上传")
	$(obj).siblings("span").parent("a").siblings("i").attr("class","on");
	$(obj).parent("a").parent("form").ajaxSubmit({
		success : function(data) {
			$(obj).siblings("input").val(data.result);
		}
	});
}


terminalCancellationModule.$inject = ['$scope', '$http', '$cookieStore'];
terminalCancellationModule.controller("terminalCancellationController", terminalCancellationController);
