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
		
		//校验手机号码
		if(!checkPhone($scope.phoneNum)){
			return;
		};
		//校验邮箱
		if(!checkEmail($scope.emailStr)){
			return;
		};
		//校验身份证号
		if(!checkCardId($scope.agentCardId)){
			return;
		};
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

//校验身份证
function checkCardId(socialNo){  
	  
    if(socialNo == "")  
    {  
      alert("输入身份证号码不能为空!");  
      return (false);  
    }  

    if (socialNo.length != 15 && socialNo.length != 18)  
    {  
      alert("输入身份证号码格式不正确!");  
      return (false);  
    }  
        
   var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};   
       
     if(area[parseInt(socialNo.substr(0,2))]==null) {  
      alert("身份证号码不正确(地区非法)!");  
          return (false);  
     }   
            
    if (socialNo.length == 15)  
    {  
       pattern= /^\d{15}$/;  
       if (pattern.exec(socialNo)==null){  
          alert("15位身份证号码必须为数字！");  
          return (false);  
      }  
      var birth = parseInt("19" + socialNo.substr(6,2));  
      var month = socialNo.substr(8,2);  
      var day = parseInt(socialNo.substr(10,2));  
      switch(month) {  
          case '01':  
          case '03':  
          case '05':  
          case '07':  
          case '08':  
          case '10':  
          case '12':  
              if(day>31) {  
                  alert('输入身份证号码不格式正确!');  
                  return false;  
              }  
              break;  
          case '04':  
          case '06':  
          case '09':  
          case '11':  
              if(day>30) {  
                  alert('输入身份证号码不格式正确!');  
                  return false;  
              }  
              break;  
          case '02':  
              if((birth % 4 == 0 && birth % 100 != 0) || birth % 400 == 0) {  
                  if(day>29) {  
                      alert('输入身份证号码不格式正确!');  
                      return false;  
                  }  
              } else {  
                  if(day>28) {  
                      alert('输入身份证号码不格式正确!');  
                      return false;  
                  }  
              }  
              break;  
          default:  
              alert('输入身份证号码不格式正确!');  
              return false;  
      }  
      var nowYear = new Date().getYear();  
      if(nowYear - parseInt(birth)<15 || nowYear - parseInt(birth)>100) {  
          alert('输入身份证号码不格式正确!');  
          return false;  
      }  
      return (true);  
    }  
      
    var Wi = new Array(  
              7,9,10,5,8,4,2,1,6,  
              3,7,9,10,5,8,4,2,1  
              );  
    var   lSum        = 0;  
    var   nNum        = 0;  
    var   nCheckSum   = 0;  
      
      for (i = 0; i < 17; ++i)  
      {  
            

          if ( socialNo.charAt(i) < '0' || socialNo.charAt(i) > '9' )  
          {  
              alert("输入身份证号码格式不正确!");  
              return (false);  
          }  
          else  
          {  
              nNum = socialNo.charAt(i) - '0';  
          }  
           lSum += nNum * Wi[i];  
      }  

      
      if( socialNo.charAt(17) == 'X' || socialNo.charAt(17) == 'x')  
      {  
          lSum += 10*Wi[17];  
      }  
      else if ( socialNo.charAt(17) < '0' || socialNo.charAt(17) > '9' )  
      {  
          alert("输入身份证号码格式不正确!");  
          return (false);  
      }  
      else  
      {  
          lSum += ( socialNo.charAt(17) - '0' ) * Wi[17];  
      }  

        
        
      if ( (lSum % 11) == 1 )  
      {  
          return true;  
      }  
      else  
      {  
          alert("输入身份证号码格式不正确!");  
          return (false);  
      }  
        
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
		if(!checkCardId($scope.agentCardId)){
			return;
		};
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
            		if(!tempVal.match( /^\d+$/)){
            			alert("输入的分润数值不正确，请输入0~100之间的数字");
            			break;
            		}
            		if(tempVal<0 || tempVal>100){
            			alert("输入的分润数值不正确，请输入0~100之间的数字");
            			break;
            		}
            		else{
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
		
		$scope.isShow=1;
		return false;
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
            		if(!tempVal.match( /^\d+$/)){
            			alert("输入的分润数值不正确，请输入0~100之间的数字");
            			return false;
            		}
            		if(tempVal<0 || tempVal>100){
            			alert("输入的分润数值不正确，请输入0~100之间的数字");
            			return false;
            		}
            		else{
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
