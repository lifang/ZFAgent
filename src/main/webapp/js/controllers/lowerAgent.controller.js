'user strict';

//系统设置模块
var lowerAgentModule = angular.module("lowerAgentModule",[]);

var lowerAgentlistController = function ($scope, $http, LoginService){
	$scope.init=function(){
		$scope.req={};
		$scope.req.agents_id=LoginService.agentid;
		$scope.list();
	};
	$scope.sonlist=function(){
		$http.post("api/lowerAgent/getsonagent", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.son=data.result.list;
            }
        });
	};
	$scope.list=function(){
		$http.post("api/lowerAgent/list", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.lowerAgentList=data.result.list;
            	$scope.total=data.result.total;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            }
        });
	};
	$scope.init();
};

var lowerInfoController = function ($scope, $http,$location, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.id=$location.search()['id'];
		$scope.info();
	};
	$scope.info=function(){
		$http.post("api/lowerAgent/info", $scope.req).success(function (data) {  //绑定
	            if (data.code==1) {
	            	$scope.info=data.result;
	            }
	        });
		};
	$scope.init();
};

var lowerAgentAddController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.agents_id=LoginService.agentid;
		$scope.list();
		
		$scope.agentType=1;
		$scope.agentName="";
		$scope.agentCardId="";
		$scope.companyName="";
		$scope.companyId="";
		$scope.phoneNum="";
		$scope.emailStr="";
		$scope.addDetail="详细地址";
		$scope.loginId="";
		$scope.pwd="";
		$scope.isProfit=1;
	};
	
	$scope.list=function(){
		$http.post("api/lowerAgent/getProvince", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.provinceList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            }
        });
	};
	
	$scope.selChange=function(){
		$http.post("api/lowerAgent/getCity", $scope.proModel.id).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.cityList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            }
        });
	};
	
	$scope.checkIsIn=function(){
		$scope.req.loginId=$scope.loginId;
		//登陆ID校验是否已经存在
		$http.post("api/lowerAgent/check", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	//已经存在
            	alert("该登陆ID已经存在");
            	$scope.loginId="";
            }else if(data.code==-2){
            	alert(data.message);
            }else if(data.code==-1){
            	
            }
        });
	}
	
	$scope.createNew=function(){
		//验证
		if($scope.pwd !=$scope.pwd1){
			alert("两次输入的密码不一致，请重新输入");
			return;
		}
		$scope.req.agentType=$scope.agentType;
		$scope.req.agentName=$scope.agentName;
		$scope.req.agentCardId=$scope.agentCardId;
		$scope.req.companyName=$scope.companyName;
		$scope.req.companyId=$scope.companyId;
		$scope.req.phoneNum=$scope.phoneNum;
		$scope.req.emailStr=$scope.emailStr;
		$scope.req.addressStr=$scope.proModel.name+""+$scope.cityModel.name+""+$scope.addDetail;
		$scope.req.loginId=$scope.loginId;
		$scope.req.pwd=$scope.pwd;
		$scope.req.isProfit=$scope.isProfit;
		$http.post("api/lowerAgent/createNew", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	alert("新增下级代理商成功！");
            }else{
            	alert("新增下级代理商失败！");
            }
        });
	}
	
	$scope.init();
};

var lowerAgentEditController=function($scope, $http,$location, LoginService){
	$scope.init=function(){
		$scope.req={};
		$scope.req.id=$location.search()['id'];
		$scope.info();
		$scope.list();
	};
	$scope.info=function(){
		$http.post("api/lowerAgent/info", $scope.req).success(function (data) {  //绑定
	            if (data.code==1) {
	            	$scope.agentType=data.result.types;
	            	$scope.agentName=data.result.name;
	            	$scope.agentCardId=data.result.card_id;
	        		$scope.companyName=data.result.company_name;
	        		$scope.companyId=data.result.business_license;
	        		$scope.phoneNum=data.result.phone;
	        		$scope.emailStr=data.result.email;
	        		$scope.addDetail=data.result.address;
	        		$scope.loginId=data.result.loginId;
	        		$scope.isProfit=data.result.is_have_profit;
	            }
	    });
	};
	$scope.list=function(){
		$http.post("api/lowerAgent/getProvince", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.provinceList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            }
        });
	};
	
	$scope.selChange=function(){
		$http.post("api/lowerAgent/getCity", $scope.proModel.id).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.cityList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            }
        });
	};
	
	$scope.save=function(){
		//验证
		if($scope.pwd !=$scope.pwd1){
			alert("两次输入的密码不一致，请重新输入");
			return;
		}
		$scope.req.agentType=$scope.agentType;
		$scope.req.agentName=$scope.agentName;
		$scope.req.agentCardId=$scope.agentCardId;
		$scope.req.companyName=$scope.companyName;
		$scope.req.companyId=$scope.companyId;
		$scope.req.phoneNum=$scope.phoneNum;
		$scope.req.emailStr=$scope.emailStr;
		$scope.req.addressStr=$scope.proModel.name+""+$scope.cityModel.name+""+$scope.addDetail;
		$scope.req.loginId=$scope.loginId;
		$scope.req.pwd=$scope.pwd;
		$scope.req.isProfit=$scope.isProfit;
		
		$http.post("api/lowerAgent/save", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	alert("修改下级代理商成功！");
            }else{
            	alert("修改下级代理商失败！");
            }
        });
	}
	
	$scope.init();
};

var lowerAgentSetController=function($scope,$http,$location,LoginService){
	$scope.init=function(){
		$scope.req={};
		$scope.req.agents_id=LoginService.agentid;
		$scope.req.son_agents_id=$location.search()['id'];
		$scope.list();
		$scope.isShow=1;
	};
	$scope.list=function(){
		$http.post("api/lowerAgent/getProfitlist", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.lowerAgentList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            }
        });
	};
	$scope.init();
	//编辑功能，根据数据查询绑定数据   isShow是否显示编辑框 sign是否显示编辑框下的下拉框
	$scope.edit=function(val){
		$scope.isShow=0;
		if(val==undefined || val==" "||val==""){
			//新增
			$scope.sign=0;
			//填补下拉框
			$http.post("api/lowerAgent/getChannellist",id).success(function (data) {  //绑定
	            if (data.code==1) {
	            	$scope.channelList=data.result.list;
	            }
	        });
		}else{
			//修改
			$scope.sign=1;
			var temp=val.split('_');
			var id=temp[0];
			$scope.channelId=id;
			$scope.curChannel=temp[1];
			
			$http.post("api/lowerAgent/getTradelist",id).success(function (data) {  //绑定
	            if (data.code==1) {
	            	$scope.detailList=data.result.list;
	            }
	        });
		}
		return false;
	};
	
	$scope.selChange=function(){
		$scope.curChannel=$scope.channelModel.id;
		$http.post("api/lowerAgent/getTradelist", $scope.channelModel.id).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.detailList=data.result.list;
            }
        });
		
	};
	
	$scope.saveNew=function(){
		//新增
		//需要校验输入必须为数字
		$http.post("api/lowerAgent/getTradelist", $scope.channelId).success(function (data) {  //绑定
            if (data.code==1) {
            	var list =data.result.list;
            	var editVal="";
            	for(var i=0;i<list.length;i++){
            		var modelTemp=list[i].id+"_model";
            		if(editVal==""){
            			editVal=$("#"+modelTemp).val()+"_"+list[i].id;
            		}else{
            			editVal=editVal+"|"+ $("#"+modelTemp).val()+"_"+list[i].id;
            		}
            	}
            	
            	$scope.req.profitPercent=editVal;
            	$scope.req.channelId=$scope.channelId;
            	$scope.req.sign=1;
            	$http.post("api/lowerAgent/saveOrEdit", $scope.req).success(function (data) {  //绑定
    	            if (data.code==1) {
    	            	alert("修改成功");
		            	//刷新当前页面
		            	$scope.init();
		            }else{
		            	alert("错误信息为："+data.message);
		            }
            	});
            }
        });
		
		$scope.isShow=1;
		return false;
	};
	
	$scope.saveOne=function(){
		//需要校验输入必须为数字
		$http.post("api/lowerAgent/getTradelist", $scope.channelId).success(function (data) {  //绑定
            if (data.code==1) {
            	var list =data.result.list;
            	var editVal="";
            	for(var i=0;i<list.length;i++){
            		var modelTemp=list[i].id+"_model";
            		if(editVal==""){
            			editVal=$("#"+modelTemp).val()+"_"+list[i].id;
            		}else{
            			editVal=editVal+"|"+ $("#"+modelTemp).val()+"_"+list[i].id;
            		}
            	}
            	$scope.req.profitPercent=editVal;
            	$scope.req.channelId=$scope.channelId;
            	$scope.req.sign=0;
            	$http.post("api/lowerAgent/saveOrEdit", $scope.req).success(function (data) {  //绑定
    	            if (data.code==1) {
    	            	alert("修改成功");
    	            	//刷新当前页面
    	            	$scope.init();
    	            }else{
    	            	alert("错误信息为："+data.message);
    	            }
            	});
            }
        });
	}
	
	$scope.save=function(){
		$scope.detailList=null;
		$scope.isShow=1;
		$scope.sign=0;
		$scope.edit();
	};
};

lowerAgentlistController.$inject = ['$scope','$http','LoginService'];
lowerAgentModule.controller("lowerAgentlistController", lowerAgentlistController);


lowerInfoController.$inject = ['$scope','$http','$location','LoginService'];
lowerAgentModule.controller("lowerInfoController", lowerInfoController);

lowerAgentAddController.$inject = ['$scope','$http','LoginService'];
lowerAgentModule.controller("lowerAgentAddController", lowerAgentAddController);

lowerAgentEditController.$inject = ['$scope','$http','$location','LoginService'];
lowerAgentModule.controller("lowerAgentEditController", lowerAgentEditController);

lowerAgentSetController.$inject = ['$scope','$http','$location','LoginService'];
lowerAgentModule.controller("lowerAgentSetController", lowerAgentSetController);