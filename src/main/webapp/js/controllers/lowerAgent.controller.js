'user strict';

//系统设置模块
var lowerAgentModule = angular.module("lowerAgentModule",[]);

var dataBaseCopyController = function ($scope, $http, LoginService){
	$http.post("api/dataBase/init",null).success(function (data) { 
        
    });
};

//下级代理商列表
var lowerAgentlistController = function ($scope, $http, LoginService){
	//首页
	// 上一页
   	$scope.prev = function() {
   		if ($scope.req.indexPage > 1) {
   			$scope.req.indexPage--;
   			$scope.list();
   		}
   	};

   	// 当前页
   	$scope.loadPage = function(currentPage) {
   		$scope.req.indexPage = currentPage;
   		$scope.list();
   	};

   	// 下一页
   	$scope.next = function() {
   		if ($scope.req.indexPage < $scope.req.totalPage) {
   			$scope.req.indexPage++;
   			$scope.list();
   		}
   	};

   	// 跳转到XX页
   	$scope.getPage = function() {
   		$scope.req.indexPage = Math.ceil($scope.req.gotoPage);
   		$scope.list();
   	};
	
	
	$scope.init=function(){
		$scope.req={};
		initSystemPage($scope.req);// 初始化分页参数
		$scope.req.agentsId=LoginService.agentid;
		$scope.pwdTabSign=1;
		$scope.list();
		$http.post("api/lowerAgent/getDefaultProfit", $scope.req).success(function (data) {  //绑定
			if (data.code==1) {
            	$scope.tabProfit=data.result;
            }else{
            }
        });
	};
	$scope.sonlist=function(){
		$http.post("api/lowerAgent/getsonagent", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.son=data.result.list;
            }
        });
	};
	$scope.list=function(){
		$scope.req.page=$scope.req.indexPage;
		$scope.req.lowerAgentName=$scope.lowerAgentName;
		$http.post("api/lowerAgent/list", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.lowerAgentList=data.result.list;
            	$scope.totalPage=data.result.total;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            }
        });
	};
	
	$scope.searchByName = function(){
		 $scope.indexPage = 1;
		 
		$scope.init();
	}
	
	$scope.changeStatus=function(val,status){
		$scope.req.sonAgentsId=val;
		$scope.req.status=status;
		$http.post("api/lowerAgent/changeStatus", $scope.req).success(function (data) {  //绑定
			if (data.code==1) {
            	location.reload();
            }else{
            	alert("修改状态出错，错误信息为："+data.message);
            }
        });
	};
	
	$scope.changePwd=function(){
		var pwd1=$scope.tabPwd1;
		var pwd2=$scope.tabPwd2;
		if(pwd1=="" || pwd1==" " || pwd1==undefined){
			alert("输入密码不能为空");
			return;
		}
		//校验密码位数大于6
		 if(pwd1.length<6){
		 	alert("请至少输入6位数密码");
		 	return;
		 }
		
		if(pwd1!=pwd2){
			alert("输入的两次密码不一致");
			return;
		}
		$scope.req.pwd=pwd1;
		$scope.req.pwd1=pwd2;
		$http.post("api/lowerAgent/changePwd", $scope.req).success(function (data) {  //绑定
			if (data.code==1) {
            	location.reload();
            }else{
            	alert("修改密码出错，错误信息为："+data.message);
            }
        });
	}
	
	$scope.showPwdTab=function(val){
		$scope.req.sonAgentsId=val;
		popup(".resetPassword_tab",".resetPassword_a");
	}
	
	$scope.changeDefaultProfit=function(){
		var defaultTemp=$scope.tabProfit;
		if(defaultTemp=="" ||defaultTemp==" " ||defaultTemp==undefined){
			alert("输入的默认分润比例不能为空！");
			return;
		}
		if( isNaN( defaultTemp ) )
	    {
	     alert("输入的默认分润比例数值必须为数字");
	     return;
	    }else if(defaultTemp>100 ||defaultTemp<0){
		   alert("输入的默认分润比例数值必须介于0-100之间");
		   return;
	    }else{
	    	$scope.req.defaultProfit=defaultTemp;
	    	$http.post("api/lowerAgent/changeProfit", $scope.req).success(function (data) {  //绑定
				if (data.code==1) {
	            	location.reload();
	            }else{
	            	alert("修改默认分润比例出错，错误信息为："+data.message);
	            }
	        });
	    }
	}
	
	
	$scope.showProfitTab=function(){
		var doc_height = $(document).height();
		var win_height = $(window).height();
		var win_width = $(window).width();
		
		var layer_height = $(".defaultRatio_tab").height();
		var layer_width = $(".defaultRatio_tab").width();
		$(".mask").css({display:'block',height:doc_height});
		$(".defaultRatio_tab").css('top',(win_height-layer_height)/2);
		$(".defaultRatio_tab").css('left',(win_width-layer_width)/2);
		$(".defaultRatio_tab").css('display','block');
		popup(".defaultRatio_tab",".defaultRatio_a");
	}
	$scope.init();
};

//下级代理商详细
var lowerInfoController = function ($scope, $http,$location, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.sonAgentsId=$location.search()['id'];
		$scope.info();
	};
	$scope.info=function(){
		$http.post("api/lowerAgent/info", $scope.req).success(function (data) {  //绑定
	            if (data.code==1) {
	            	$scope.info=data.result;
	            	$scope.req.cityId=data.result.cityId;
	            	$http.post("api/lowerAgent/getProCity",$scope.req).success(function (data) {  //绑定
	    	            if (data.code==1) {
	    	            	$scope.cityName=data.result.city;
	    	            	$scope.provinceName=data.result.province;
	    	            }
	    	        });
	            	if($scope.info.types==2){
	            		//为个人
	            		$("#companyNameLi").hide();
	            		$("#companyIdLi").hide();
	            	}else{
	            		//为公司
	            	}
	            }
	        });
		};
	$scope.init();
};

//新增下级代理商
var lowerAgentAddController = function ($scope, $http, LoginService) {
	$scope.init=function(){
		$scope.req={};
		$scope.req.agentsId=LoginService.agentid;
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
	
	$scope.radioCheck=function(obj){
		if(obj==2){
			//个人
			$("#companyNameLi").hide();
			$("#companyIdLi").hide();
		}else{
			//公司
			$("#companyNameLi").show();
			$("#companyIdLi").show();
		}
	};
	
	$scope.list=function(){
		$http.post("api/lowerAgent/getProvince", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.provinceList=data.result.list;
            }
        });
	};
	
	$scope.selChange=function(){
		$http.post("api/lowerAgent/getCity", $scope.proModel.id).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.cityList=data.result.list;
            }
        });
	};
	//校验输入的登陆名是否已经存在
	$scope.checkIsIn=function(){
		$scope.req.loginId=$scope.loginId;
		//登陆ID校验是否已经存在
		$http.post("api/lowerAgent/check", $scope.req).success(function (data) {  //绑定
			if(data.code==-2){
				alert("错误信息为："+data.message);
			}else if (data.code==-1) {
            	//已经存在
				alert("错误信息为："+data.message);
            	$scope.loginId="";
            }
        });
	};
	
	$scope.isNull=function(val,valDetail){
		if(val=="" || val==" " || val==undefined){
			alert("输入的【"+valDetail+"】一栏不能为空，请重新输入");
			return false;
		}else{
			return true;
		}
	};
	
	$scope.createNew=function(){
		//验证为空
		if(!$scope.isNull($scope.agentName,"负责人姓名")){
			return;
		}
		if(!$scope.isNull($scope.agentCardId,"负责人身份证号")){
			return;
		}
		if($scope.agentType==1){
			if(!$scope.isNull($scope.companyName,"公司全称")){
				return;
			}
			if(!$scope.isNull($scope.companyId,"公司营业执照登记号")){
				return;
			}
		}
		if(!$scope.isNull($scope.phoneNum,"手机号")){
			return;
		}
		if(!$scope.isNull($scope.emailStr,"邮箱")){
			return;
		}
		if(!$scope.isNull($scope.addDetail,"所在地")){
			return;
		}
		//验证城市
		if($scope.cityModel==undefined || $scope.proModel==undefined){
			alert("请选择你所在的省市！");
			return;
		}
		if(!$scope.isNull($scope.loginId,"登陆ID")){
			return;
		}
		
		if(!$scope.isNull($scope.pwd,"密码")){
			return;
		}
		
		//校验手机号码
		if(!checkPhone($scope.phoneNum)){
			return;
		};
		//校验邮箱
		if(!checkEmail($scope.emailStr)){
			return;
		};
		//校验身份证号
		var cardIdTemp=$scope.agentCardId;
		if(cardIdTemp.length!=18){
			alert("输入的身份证号码长度必须为18位,请重新输入！");
			return;
		}
		 //校验密码位数大于6
		 if($scope.pwd.length<6){
		 	alert("请至少输入6位数密码");
		 	return;
		 }
		 
		//校验两次输入的密码是否一致
		if($scope.pwd !=$scope.pwd1){
			alert("两次输入的密码不一致，请重新输入");
			$scope.pwd="";
			$scope.pwd1="";
			return;
		}
		
		$scope.req.loginId=$scope.loginId;
		$scope.req.agentType=$scope.agentType;
		$scope.req.agentName=$scope.agentName;
		$scope.req.agentCardId=$scope.agentCardId;
		$scope.req.companyName=$scope.companyName;
		$scope.req.companyId=$scope.companyId;
		$scope.req.phoneNum=$scope.phoneNum;
		$scope.req.emailStr=$scope.emailStr;
		$scope.req.addressStr=$scope.addDetail;
		$scope.req.loginId=$scope.loginId;
		$scope.req.pwd=$scope.pwd;
		$scope.req.pwd1=$scope.pwd1;
		$scope.req.isProfit=$scope.isProfit;
		$scope.req.cityId=$scope.cityModel.id;
		
		$http.post("api/lowerAgent/createNew", $scope.req).success(function (data) {  //绑定
            if (data.code==-1) {
            	alert("新增下级代理商失败！错误信息为："+data.message);
            }else{
            	window.location.href="#/lowerAgent";
            }
        });
	};
	
	$scope.init();
	
	
};
function clearDefault(){
	$("#addDetail").val("");
}


//校验邮箱
function checkEmail(str){
   var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
   if(re.test(str)){
	   return true;
   }else{
	   alert("输入的【邮箱】一栏格式不正确，请重新输入");
       return false;
   }
}


//校验手机号
function checkPhone(phone){
	var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
	 if (reg.test(phone)) {
		 return true;
	 }else{
	      alert("输入的【手机】一栏格式不正确，请重新输入");
	      return false;
	 };
}

//修改编辑下级代理商
var lowerAgentEditController=function($scope, $http,$location, LoginService){
	$scope.init=function(){
		$scope.req={};
		$scope.req.sonAgentsId=$location.search()['id'];
		$scope.list();
		$scope.info();
	};
	$scope.info=function(){
		$http.post("api/lowerAgent/info", $scope.req).success(function (data) {  //绑定
	            if (data.code==1) {
	            	$scope.agentType=data.result.types;
	            	if($scope.agentType==2){
	            		//为个人
	            		$("#companyNameLi").hide();
	            		$("#companyIdLi").hide();
	            	}
	            	
	            	$scope.agentName=data.result.name;
	            	$scope.agentCardId=data.result.card_id;
	        		$scope.companyName=data.result.company_name;
	        		$scope.companyId=data.result.business_license;
	        		$scope.phoneNum=data.result.phone;
	        		$scope.emailStr=data.result.email;
	        		$scope.addDetail=data.result.address;
	        		$scope.loginId=data.result.loginId;
	        		$scope.isProfit=data.result.is_have_profit;
	        		
	        		$scope.req.cityId=data.result.cityId;
	        		$http.post("api/lowerAgent/getProCity",$scope.req).success(function (data) {  //绑定
	    	            if (data.code==1) {
	    	            	$scope.proModel=data.result.provinceId;
	    	            	
	    	            	$http.post("api/lowerAgent/getCity", $scope.proModel).success(function (data) {  //绑定
	    	                    if (data.code==1) {
	    	                    	$scope.cityList=data.result.list;
	    	                    }
	    	                });
	    	            	$scope.cityModel=data.result.cityId;
	    	            }
	    	        });
	        		
	            }
	    });
		
		
	};
	
	$scope.radioCheck=function(obj){
		if(obj==2){
			//个人
			$("#companyNameLi").hide();
			$("#companyIdLi").hide();
		}else{
			//公司
			$("#companyNameLi").show();
			$("#companyIdLi").show();
		}
	};
	
	$scope.list=function(){
		$http.post("api/lowerAgent/getProvince", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.provinceList=data.result.list;
            }
        });
	};
	
	$scope.selChange=function(){
		$http.post("api/lowerAgent/getCity", $scope.proModel).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.cityList=data.result.list;
            }
        });
	};
	$scope.isNull=function(val,valDetail){
		if(val=="" || val==" " || val==undefined){
			alert("输入的【"+valDetail+"】一栏不能为空，请重新输入");
			return false;
		}else{
			return true;
		}
	};
	
	$scope.save=function(){
		//验证为空
		if(!$scope.isNull($scope.agentName,"负责人姓名")){
			return;
		}
		if(!$scope.isNull($scope.agentCardId,"负责人身份证号")){
			return;
		}
		if(!$scope.isNull($scope.companyName,"公司全称")){
			return;
		}
		if(!$scope.isNull($scope.companyId,"公司营业执照登记号")){
			return;
		}
		if(!$scope.isNull($scope.phoneNum,"手机号")){
			return;
		}
		if(!$scope.isNull($scope.emailStr,"邮箱")){
			return;
		}
		if(!$scope.isNull($scope.addDetail,"所在地")){
			return;
		}
		//验证城市
		if($scope.cityModel==undefined || $scope.proModel==undefined){
			alert("请选择你所在的省市！");
			return;
		}
		if(!$scope.isNull($scope.loginId,"登陆ID")){
			return;
		}
		
		if(!$scope.isNull($scope.pwd,"密码")){
			return;
		}
		//验证
		if($scope.pwd !=$scope.pwd1){
			alert("两次输入的密码不一致，请重新输入");
			return;
		}
		
		//校验手机号码
		if(!checkPhone($scope.phoneNum)){
			return;
		};
		//校验邮箱
		if(!checkEmail($scope.emailStr)){
			return;
		};
		//校验身份证号
		var cardIdTemp=$scope.agentCardId;
		if(cardIdTemp.length!=18){
			alert("输入的身份证号码长度必须为18位,请重新输入！");
			return;
		}
		 //校验密码位数大于6
		 if($scope.pwd.length<6){
		 	alert("请至少输入6位数密码");
		 	return;
		 }
		
		
		$scope.req.agentType=$scope.agentType;
		$scope.req.agentName=$scope.agentName;
		$scope.req.agentCardId=$scope.agentCardId;
		$scope.req.companyName=$scope.companyName;
		$scope.req.companyId=$scope.companyId;
		$scope.req.phoneNum=$scope.phoneNum;
		$scope.req.emailStr=$scope.emailStr;
		$scope.req.addressStr=$scope.addDetail;
		$scope.req.loginId=$scope.loginId;
		$scope.req.pwd=$scope.pwd;
		$scope.req.pwd1=$scope.pwd1;
		if($scope.isProfit==true){
			$scope.isProfit=1;
		}
		$scope.req.isProfit=$scope.isProfit;
		$scope.req.cityId=$scope.cityModel;
		
		$http.post("api/lowerAgent/save", $scope.req).success(function (data) {  //绑定
			
			if (data.code==1) {
            	//alert("修改下级代理商成功！");
            	window.location.href="#/lowerAgent";
            }else{
            	alert("修改下级代理商失败！");
            }
        });
	};
	$scope.init();
};

//设置代理商分润
var lowerAgentSetController=function($scope,$http,$location,LoginService){
	$scope.init=function(){
		$scope.req={};
		$scope.req.agentsId=LoginService.agentid;
		$scope.req.sonAgentsId=$location.search()['id'];
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
			$scope.req.id=id;
			$http.post("api/lowerAgent/getTradelist",$scope.req).success(function (data) {  //绑定
	            if (data.code==1) {
	            	$scope.detailList=data.result.list;
	            }
	        });
		}
		return false;
	};
	
	$scope.selChange=function(){
		$scope.curChannel=$scope.channelModel.id;
		$scope.req.id=$scope.channelModel.id;
		$http.post("api/lowerAgent/getTradelist", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.detailList=data.result.list;
            }
        });
		
	};
	
	
	
	$scope.saveNew=function(){
		//新增
		//需要校验输入必须为数字
		$scope.req.id=$scope.curChannel;
		$http.post("api/lowerAgent/getTradelist", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	var list =data.result.list;
            	var editVal="";
            	for(var i=0;i<list.length;i++){
            		var modelTemp=list[i].id+"_model";
            		var tempVal=$("#"+modelTemp).val();
            		tempVal=parseInt(tempVal)*10;
            		tempVal=tempVal+"";
            		var reg = /^\d+$/;
            		if(!tempVal.match(reg)||tempVal<0 || tempVal>1000){
            			alert("输入的分润数值不正确，请输入0~100之间的数字");
            			return false;
            		}
            		else{
            			tempVal=tempVal/10;
	            		if(editVal==""){
	            			editVal=tempVal+"_"+list[i].id;
	            		}else{
	            			editVal=editVal+"|"+tempVal+"_"+list[i].id;
	            		}
            		}
            	}
            	$scope.req.profitPercent=editVal;
            	$scope.req.payChannelId=$scope.curChannel;
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
//		$scope.isShow=1;
//		return false;
	};
	
	$scope.saveOne=function(){
		//需要校验输入必须为数字
		$scope.req.id=$scope.channelId;
		$http.post("api/lowerAgent/getTradelist",$scope.req ).success(function (data) {  //绑定
            if (data.code==1) {
            	var list =data.result.list;
            	var editVal="";
            	for(var i=0;i<list.length;i++){
            		var modelTemp=list[i].id+"_model";
            		var tempVal=$("#"+modelTemp).val();
            		tempVal=parseFloat(tempVal)*10;
            		tempVal=tempVal+"";
            		var reg = /^\d+$/;
            		if(!tempVal.match(reg)||tempVal<0 || tempVal>1000){
            			alert("输入的分润数值不正确，请输入0~100之间的数字");
            			return false;
            		}
            		else{
            			tempVal=tempVal/10;
	            		if(editVal==""){
	            			
	            			editVal=tempVal+"_"+list[i].id;
	            		}else{
	            			editVal=editVal+"|"+tempVal+"_"+list[i].id;
	            		}
            		}
            	}
            	$scope.req.profitPercent=editVal;
            	$scope.req.payChannelId=$scope.channelId;
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

dataBaseCopyController.$inject = ['$scope','$http','LoginService'];
lowerAgentModule.controller("dataBaseCopyController", dataBaseCopyController);
