'user strict';

//系统设置模块
var shopModule = angular.module("shopModule",[]);

var shopController = function ($scope, $http, LoginService) {
	
	$scope.req={};
	$scope.req.keys=LoginService.keys;
	$scope.req.cityId=LoginService.city;
	$scope.req.orderType=1;
	
	$scope.req.hasLease=false;
	//$scope.req.keys="";
	//$scope.req.minPrice=0;
	//$scope.req.maxPrice=0;
	
	$scope.req.brandsId=[];
	$scope.req.category=[];
	$scope.req.payChannelId=[];
	$scope.req.payCardId=[];
	$scope.req.tradeTypeId=[];
	$scope.req.saleSlipId=[];
	$scope.req.tDate=[];
	
	$scope.xxx="";
	$scope.sb=function(one){
		$('#xx').hide();
		$scope.xxx=one.value;
		$scope.req.tDate=[];
		$scope.req.tDate.push(one.id);
		$scope.list();
	}
	
	$scope.index=function() {
		window.location.href = '#/';
	};
	$scope.init = function () {
		initSystemPage($scope.req);// 初始化分页参数
		$scope.searchinfo();
		$scope.list();
		
    };
    $scope.searchinfo=function(){
    	$http.post("api/good/search", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.brands=data.result.brands;
            	$scope.category=data.result.webcategory;
            	$scope.sale_slip=data.result.sale_slip;
            	$scope.pay_card=data.result.pay_card;
            	$scope.pay_channel=data.result.pay_channel;
            	$scope.trade_type=data.result.trade_type;
            	$scope.tDate=data.result.tDate;
            }
        });
    }
    $scope.search=function () {
	    $scope.req.indexPage=1
	    $scope.list();
    };
    $scope.list = function () {
    	if($scope.req.hasLease){
    		$scope.req.hasLease=1;
    	}else{
    		$scope.req.hasLease=0;
    	}
    	$scope.req.page=$scope.req.indexPage;
		$http.post("api/good/list", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.goodList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            	if($scope.req.hasLease==1){
            		$scope.req.hasLease=true;
            	}else{
            		$scope.req.hasLease=false;
            	}
            	
            }
        });
    };
    
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
    
    //POS机品牌
    $scope.check1=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli1val="";
    		$scope.req.brandsId=[];
    		angular.forEach($scope.brands, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli1val=$scope.chli1val+one.value+",";
       			$scope.req.brandsId.push(one.id);
       		  }
            });
    		if($scope.chli1val==""){
    			$scope.chli1show=false;
    		}else{
    			var s=$scope.chli1val;
    			s=s.substring(0,s.length-1);
    			$scope.chli1val=s;
    		}
    	}else{
    		if($scope.chli1show){
    			$scope.chli1val=$scope.chli1val+","+p.value;
    		}else{
    			$scope.chli1val=p.value;
    		}
    		$scope.chli1show=true;
    		$scope.req.brandsId.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli1del=function () {
    	$scope.chli1show=false;
    	$scope.req.brandsId=[];
    	 angular.forEach($scope.brands, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
    
  //POS机类型
    $scope.check2=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli2val="";
    		$scope.req.category=[];
    		angular.forEach($scope.category, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli2val=$scope.chli2val+one.value+",";
       			$scope.req.category.push(one.id);
       		  }
            });
    		if($scope.chli2val==""){
    			$scope.chli2show=false;
    		}else{
    			var s=$scope.chli2val;
    			s=s.substring(0,s.length-1);
    			$scope.chli2val=s;
    		}
    	}else{
    		if($scope.chli2show){
    			$scope.chli2val=$scope.chli2val+","+p.value;
    		}else{
    			$scope.chli2val=p.value;
    		}
    		$scope.chli2show=true;
    		$scope.req.category.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli2del=function () {
    	$scope.chli2show=false;
    	$scope.req.category=[];
    	 angular.forEach($scope.category, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
  //支付通道
    $scope.check3=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli3val="";
    		$scope.req.payChannelId=[];
    		angular.forEach($scope.pay_channel, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli3val=$scope.chli3val+one.value+",";
       			$scope.req.payChannelId.push(one.id);
       		  }
            });
    		if($scope.chli3val==""){
    			$scope.chli3show=false;
    		}else{
    			var s=$scope.chli3val;
    			s=s.substring(0,s.length-1);
    			$scope.chli3val=s;
    		}
    	}else{
    		if($scope.chli3show){
    			$scope.chli3val=$scope.chli3val+","+p.value;
    		}else{
    			$scope.chli3val=p.value;
    		}
    		$scope.chli3show=true;
    		$scope.req.payChannelId.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli3del=function () {
    	$scope.chli3show=false;
    	$scope.req.payChannelId=[];
    	 angular.forEach($scope.pay_channel, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
    
  //支持卡类型
    $scope.check4=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli4val="";
    		$scope.req.payCardId=[];
    		angular.forEach($scope.pay_card, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli4val=$scope.chli4val+one.value+",";
       			$scope.req.payCardId.push(one.id);
       		  }
            });
    		if($scope.chli4val==""){
    			$scope.chli4show=false;
    		}else{
    			var s=$scope.chli4val;
    			s=s.substring(0,s.length-1);
    			$scope.chli4val=s;
    		}
    	}else{
    		if($scope.chli4show){
    			$scope.chli4val=$scope.chli4val+","+p.value;
    		}else{
    			$scope.chli4val=p.value;
    		}
    		$scope.chli4show=true;
    		$scope.req.payCardId.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli4del=function () {
    	$scope.chli4show=false;
    	$scope.req.payCardId=[];
    	 angular.forEach($scope.pay_card, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
    
    //支持交易类型
    $scope.check5=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli5val="";
    		$scope.req.tradeTypeId=[];
    		angular.forEach($scope.trade_type, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli5val=$scope.chli5val+one.value+",";
       			$scope.req.tradeTypeId.push(one.id);
       		  }
            });
    		if($scope.chli5val==""){
    			$scope.chli5show=false;
    		}else{
    			var s=$scope.chli5val;
    			s=s.substring(0,s.length-1);
    			$scope.chli5val=s;
    		}
    	}else{
    		if($scope.chli5show){
    			$scope.chli5val=$scope.chli5val+","+p.value;
    		}else{
    			$scope.chli5val=p.value;
    		}
    		$scope.chli5show=true;
    		$scope.req.tradeTypeId.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli5del=function () {
    	$scope.chli5show=false;
    	$scope.req.tradeTypeId=[];
    	 angular.forEach($scope.trade_type, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
    
    //签购单方式
    $scope.check6=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli6val="";
    		$scope.req.saleSlipId=[];
    		angular.forEach($scope.sale_slip, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli6val=$scope.chli6val+one.value+",";
       			$scope.req.saleSlipId.push(one.id);
       		  }
            });
    		if($scope.chli6val==""){
    			$scope.chli6show=false;
    		}else{
    			var s=$scope.chli6val;
    			s=s.substring(0,s.length-1);
    			$scope.chli6val=s;
    		}
    	}else{
    		if($scope.chli6show){
    			$scope.chli6val=$scope.chli6val+","+p.value;
    		}else{
    			$scope.chli6val=p.value;
    		}
    		$scope.chli6show=true;
    		$scope.req.saleSlipId.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli6del=function () {
    	$scope.chli6show=false;
    	$scope.req.saleSlipId=[];
    	 angular.forEach($scope.sale_slip, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
    
    $scope.order=function (p) {
    	$scope.req.orderType=p;
    	$scope.list();
    }
    $scope.shopinfo=function (p) {
    	LoginService.poscd=p.id;
    	$scope.poscd=p.id;
    	window.location.href = '#/shopinfo';
    }
    

    $scope.init();

};

var shopinfoController = function ($scope, $http,$location, LoginService) {
	$scope.req={};
	$scope.creq={};
	$scope.quantity=1;
	$scope.req.goodId=$location.search()['goodId'];
	$scope.creq.goodId=$scope.req.goodId;
	$scope.req.cityId=LoginService.city;
	$scope.init = function () {
		initSystemPage($scope.creq);// 初始化分页参数
		//LoginService.hadLoginShow();
		//$("#leftRoute").hide();
		$scope.getGoodInfo();
		
    };
    $scope.getGoodInfo = function () {
    	$http.post("api/good/goodinfo", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.good=data.result;
            	$scope.paychannel=data.result.paychannelinfo;
            }
        });
    };
    $scope.getPayChannelInfo = function (id) {
    	$http.post("api/paychannel/info", {pcid:id}).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.paychannel=data.result;
            }
        });
    };
    $scope.count = function(type) {
		if ($scope.quantity != 1 || type != -1) {
			$scope.quantity += type;
		}
	}
    $scope.commentList = function() {
    	$scope.creq.page=$scope.creq.indexPage;
    	$http.post("api/comment/list",$scope.creq ).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.comment=data.result.list;
            	calcSystemPage($scope.creq, data.result.total);// 计算分页
            }
        });
	}
    $scope.init();

    
    // 上一页
   	$scope.prev = function() {
   		if ($scope.creq.indexPage > 1) {
   			$scope.creq.indexPage--;
   			$scope.commentList();
   		}
   	};

   	// 当前页
   	$scope.loadPage = function(currentPage) {
   		$scope.creq.indexPage = currentPage;
   		$scope.commentList();
   	};

   	// 下一页
   	$scope.next = function() {
   		if ($scope.creq.indexPage < $scope.creq.totalPage) {
   			$scope.creq.indexPage++;
   			$scope.commentList();
   		}
   	};

   	// 跳转到XX页
   	$scope.getPage = function() {
   		$scope.req.indexPage = Math.ceil($scope.req.gotoPage);
   		$scope.commentList();
   	};
   	
 // 跳转到XX页
   	$scope.picnb=2;
   	$scope.tt = function(nb) {
   		$scope.picnb=nb;
   	};
};

var purchaseshopController = function ($scope, $http, LoginService) {
	
	
	
	
	$scope.req={};
	$scope.req.type=1;
	$scope.req.keys=LoginService.keys;
	$scope.req.cityId=LoginService.city;
	$scope.req.orderType=1;
	
	$scope.req.hasLease=false;
	//$scope.req.keys="";
	//$scope.req.minPrice=0;
	//$scope.req.maxPrice=0;
	
	$scope.req.brandsId=[];
	$scope.req.category=[];
	$scope.req.payChannelId=[];
	$scope.req.payCardId=[];
	$scope.req.tradeTypeId=[];
	$scope.req.saleSlipId=[];
	$scope.req.tDate=[];
	
	$scope.xxx="";
	$scope.sb=function(one){
		$('#xx').hide();
		$scope.xxx=one.value;
		$scope.req.tDate=[];
		$scope.req.tDate.push(one.id);
		$scope.list();
	}
	
	$scope.index=function() {
		window.location.href = '#/';
	};
	$scope.init = function () {
		initSystemPage($scope.req);// 初始化分页参数
		$scope.searchinfo();
		$scope.list();
		
    };
    $scope.searchinfo=function(){
    	$http.post("api/good/search", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.brands=data.result.brands;
            	$scope.category=data.result.webcategory;
            	$scope.sale_slip=data.result.sale_slip;
            	$scope.pay_card=data.result.pay_card;
            	$scope.pay_channel=data.result.pay_channel;
            	$scope.trade_type=data.result.trade_type;
            	$scope.tDate=data.result.tDate;
            }
        });
    }
    $scope.search=function () {
	    $scope.req.indexPage=1
	    $scope.list();
    };
    $scope.list = function () {
    	if($scope.req.hasLease){
    		$scope.req.hasLease=1;
    	}else{
    		$scope.req.hasLease=0;
    	}
    	$scope.req.page=$scope.req.indexPage;
		$http.post("api/good/list", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.goodList=data.result.list;
            	calcSystemPage($scope.req, data.result.total);// 计算分页
            	if($scope.req.hasLease==1){
            		$scope.req.hasLease=true;
            	}else{
            		$scope.req.hasLease=false;
            	}
            	
            }
        });
    };
    
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
    
    //POS机品牌
    $scope.check1=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli1val="";
    		$scope.req.brandsId=[];
    		angular.forEach($scope.brands, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli1val=$scope.chli1val+one.value+",";
       			$scope.req.brandsId.push(one.id);
       		  }
            });
    		if($scope.chli1val==""){
    			$scope.chli1show=false;
    		}else{
    			var s=$scope.chli1val;
    			s=s.substring(0,s.length-1);
    			$scope.chli1val=s;
    		}
    	}else{
    		if($scope.chli1show){
    			$scope.chli1val=$scope.chli1val+","+p.value;
    		}else{
    			$scope.chli1val=p.value;
    		}
    		$scope.chli1show=true;
    		$scope.req.brandsId.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli1del=function () {
    	$scope.chli1show=false;
    	$scope.req.brandsId=[];
    	 angular.forEach($scope.brands, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
    
  //POS机类型
    $scope.check2=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli2val="";
    		$scope.req.category=[];
    		angular.forEach($scope.category, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli2val=$scope.chli2val+one.value+",";
       			$scope.req.category.push(one.id);
       		  }
            });
    		if($scope.chli2val==""){
    			$scope.chli2show=false;
    		}else{
    			var s=$scope.chli2val;
    			s=s.substring(0,s.length-1);
    			$scope.chli2val=s;
    		}
    	}else{
    		if($scope.chli2show){
    			$scope.chli2val=$scope.chli2val+","+p.value;
    		}else{
    			$scope.chli2val=p.value;
    		}
    		$scope.chli2show=true;
    		$scope.req.category.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli2del=function () {
    	$scope.chli2show=false;
    	$scope.req.category=[];
    	 angular.forEach($scope.category, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
  //支付通道
    $scope.check3=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli3val="";
    		$scope.req.payChannelId=[];
    		angular.forEach($scope.pay_channel, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli3val=$scope.chli3val+one.value+",";
       			$scope.req.payChannelId.push(one.id);
       		  }
            });
    		if($scope.chli3val==""){
    			$scope.chli3show=false;
    		}else{
    			var s=$scope.chli3val;
    			s=s.substring(0,s.length-1);
    			$scope.chli3val=s;
    		}
    	}else{
    		if($scope.chli3show){
    			$scope.chli3val=$scope.chli3val+","+p.value;
    		}else{
    			$scope.chli3val=p.value;
    		}
    		$scope.chli3show=true;
    		$scope.req.payChannelId.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli3del=function () {
    	$scope.chli3show=false;
    	$scope.req.payChannelId=[];
    	 angular.forEach($scope.pay_channel, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
    
  //支持卡类型
    $scope.check4=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli4val="";
    		$scope.req.payCardId=[];
    		angular.forEach($scope.pay_card, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli4val=$scope.chli4val+one.value+",";
       			$scope.req.payCardId.push(one.id);
       		  }
            });
    		if($scope.chli4val==""){
    			$scope.chli4show=false;
    		}else{
    			var s=$scope.chli4val;
    			s=s.substring(0,s.length-1);
    			$scope.chli4val=s;
    		}
    	}else{
    		if($scope.chli4show){
    			$scope.chli4val=$scope.chli4val+","+p.value;
    		}else{
    			$scope.chli4val=p.value;
    		}
    		$scope.chli4show=true;
    		$scope.req.payCardId.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli4del=function () {
    	$scope.chli4show=false;
    	$scope.req.payCardId=[];
    	 angular.forEach($scope.pay_card, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
    
    //支持交易类型
    $scope.check5=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli5val="";
    		$scope.req.tradeTypeId=[];
    		angular.forEach($scope.trade_type, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli5val=$scope.chli5val+one.value+",";
       			$scope.req.tradeTypeId.push(one.id);
       		  }
            });
    		if($scope.chli5val==""){
    			$scope.chli5show=false;
    		}else{
    			var s=$scope.chli5val;
    			s=s.substring(0,s.length-1);
    			$scope.chli5val=s;
    		}
    	}else{
    		if($scope.chli5show){
    			$scope.chli5val=$scope.chli5val+","+p.value;
    		}else{
    			$scope.chli5val=p.value;
    		}
    		$scope.chli5show=true;
    		$scope.req.tradeTypeId.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli5del=function () {
    	$scope.chli5show=false;
    	$scope.req.tradeTypeId=[];
    	 angular.forEach($scope.trade_type, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
    
    //签购单方式
    $scope.check6=function (p) {
    	if(p.clazz=="hover"){
    		p.clazz="";
    		$scope.chli6val="";
    		$scope.req.saleSlipId=[];
    		angular.forEach($scope.sale_slip, function (one) {
       		  if(one.clazz=="hover"){
       			$scope.chli6val=$scope.chli6val+one.value+",";
       			$scope.req.saleSlipId.push(one.id);
       		  }
            });
    		if($scope.chli6val==""){
    			$scope.chli6show=false;
    		}else{
    			var s=$scope.chli6val;
    			s=s.substring(0,s.length-1);
    			$scope.chli6val=s;
    		}
    	}else{
    		if($scope.chli6show){
    			$scope.chli6val=$scope.chli6val+","+p.value;
    		}else{
    			$scope.chli6val=p.value;
    		}
    		$scope.chli6show=true;
    		$scope.req.saleSlipId.push(p.id);
    		p.clazz="hover";
    	}
    	$scope.search();
    }
    $scope.chli6del=function () {
    	$scope.chli6show=false;
    	$scope.req.saleSlipId=[];
    	 angular.forEach($scope.sale_slip, function (one) {
    		 one.clazz="";
         });
    	 $scope.search();
    }
    
    $scope.order=function (p) {
    	$scope.req.orderType=p;
    	$scope.list();
    }
    $scope.shopinfo=function (p) {
    	LoginService.poscd=p.id;
    	$scope.poscd=p.id;
    	window.location.href = '#/shopinfo';
    }
    

    $scope.init();

};

var purchaseshopinfoController = function ($scope, $http,$location, LoginService) {
	$scope.req={};
	$scope.req.type=1;
	$scope.creq={};
	
	$scope.req.goodId=$location.search()['goodId'];
	$scope.creq.goodId=$scope.req.goodId;
	$scope.req.cityId=LoginService.city;
	$scope.init = function () {
		initSystemPage($scope.creq);// 初始化分页参数
		//LoginService.hadLoginShow();
		//$("#leftRoute").hide();
		$scope.getGoodInfo();
		
		
    };
    $scope.getGoodInfo = function () {
    	$http.post("api/good/goodinfo", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.good=data.result;
            	$scope.paychannel=data.result.paychannelinfo;
            	$scope.minquantity=$scope.good.goodinfo.floor_purchase_quantity;
            	$scope.quantity=$scope.minquantity;
            }
        });
    };
    $scope.getPayChannelInfo = function (id) {
    	$http.post("api/paychannel/info", {pcid:id}).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.paychannel=data.result;
            }
        });
    };
    $scope.count = function(type) {
		if ($scope.quantity != $scope.minquantity || type != -1) {
			$scope.quantity += type;
		}
	}
    $scope.commentList = function() {
    	$scope.creq.page=$scope.creq.indexPage;
    	$http.post("api/comment/list",$scope.creq ).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.comment=data.result.list;
            	calcSystemPage($scope.creq, data.result.total);// 计算分页
            }
        });
	}
    $scope.init();

    
    // 上一页
   	$scope.prev = function() {
   		if ($scope.creq.indexPage > 1) {
   			$scope.creq.indexPage--;
   			$scope.commentList();
   		}
   	};

   	// 当前页
   	$scope.loadPage = function(currentPage) {
   		$scope.creq.indexPage = currentPage;
   		$scope.commentList();
   	};

   	// 下一页
   	$scope.next = function() {
   		if ($scope.creq.indexPage < $scope.creq.totalPage) {
   			$scope.creq.indexPage++;
   			$scope.commentList();
   		}
   	};

   	// 跳转到XX页
   	$scope.getPage = function() {
   		$scope.req.indexPage = Math.ceil($scope.req.gotoPage);
   		$scope.commentList();
   	};
   	
 // 跳转到XX页
   	$scope.picnb=2;
   	$scope.tt = function(nb) {
   		$scope.picnb=nb;
   	};
};


var shopmakeorderController = function($scope,$http ,$location , LoginService) {
	
	$scope.init = function() {
		$scope.order={invoice_type:1};
		//$scope.order.customerId=LoginService.userid;
		//$scope.order.addressId=1;
		$scope.order.goodId=$location.search()['goodId'];
		$scope.order.orderType=parseInt($location.search()['type']);
		//$scope.order.quantity=$location.search()['quantity'];
		$scope.order.paychannelId=$location.search()['paychannelId'];
		$scope.getGood();
		$scope.min=1;
	};
	$scope.getGood = function() {
		$http.post("api/shop/getShop", $scope.order).success(function(data) {
			if (data.code == 1) {
				$scope.shop = data.result;
				$scope.order.quantity=parseInt($location.search()['quantity']);
				if($scope.order.orderType==5){
					$scope.min=data.result.floor_purchase_quantity;
					if($scope.order.quantity<$scope.min){
						$scope.order.quantity=$scope.min;
					}
				}
				
			}
		});
	};
	$scope.upadteCart = function(type) {
			if ($scope.order.quantity != $scope.min || type != -1) {
				$scope.order.quantity += type;
			}
	};
	$scope.submit = function() {
		if($scope.order.is_need_invoice){
			$scope.order.is_need_invoice=1;
		}else{
			$scope.order.is_need_invoice=0;
		}
		$scope.order.agentId=LoginService.agentid;
		$scope.order.customerId=80;
		$scope.order.addressId=1;
		$http.post("api/order/agent", $scope.order).success(function(data) {
			if (data.code == 1) {
				if($scope.order.orderType==5){
					window.location.href = '#/wholesaleOrder';
				}else{
					window.location.href = '#/pay?id='+data.result;
				}
			}else if(data.code == -2){
				window.location.href = '#/lowstocks';
			}
		});
		
	};
	$scope.ctype=function(v){
		$scope.order.invoice_type=v;
	}
	
	$scope.init();
};


var orderUserController=function($scope,$http , $location, LoginService){
	$scope.init = function() {
		$scope.req = {};
		$scope.user = {};
		$scope.clist();
		$scope.city_list();
	};
	$scope.clist = function() {
		$scope.req.customerId=LoginService.loginid;
		$http.post("api/user/getUser",$scope.req).success(function(data) {
			if (data.code == 1) {
				$scope.cuslist = data.result;
			}
		});
	};
	$scope.alist = function(cid) {
		$http.post("api/customers/getAddressList/"+LoginService.userid).success(function(data) {
			if (data.code == 1) {
				$scope.addressList = data.result;
			}
		});
	};
	$scope.addad = function(id) {
		$scope.ad.customerId=LoginService.userid;
		$scope.ad.isDefault=2;
		$scope.ad.cityId=$scope.city.id;
		$http.post("api/customers/insertAddress", $scope.ad).success(function(data) {
			if (data.code == 1) {
				$scope.init();
			} else {
				alert(data.message);
			}
		});
	};
	$scope.setDefaultAddress = function(e) {
		$http.post("api/customers/setDefaultAddress/", e).success(function(data) {
			if (data.code == 1) {
				$scope.init();
			} else {
				alert(data.message);
			}
		});
	};
	
	
	$scope.city_list = function(){
		$http.post("api/index/getCity").success(function (data) {   
			if (data != null && data != undefined) {
				$scope.city_list = data.result;
			}
		});
	};
	
	$scope.addUser = function(){
		if($scope.user.pass1!=$scope.user.pass2){
			alert("密码不一致");
		}
		$http.post("api/user/addCustomer",$scope.user).success(function (data) {   
			if (data.code == 1) {
				$scope.clist();
			}
		});
	};
	
	$scope.init();
}


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
		$('#payTab').show();
		if(1==$scope.payway){
			//alert("支付宝");
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
			window.open("alipayapi.jsp?WIDtotal_fee="+
					$scope.order.total_price/100+"&WIDsubject="+$scope.order.title
					+"&WIDout_trade_no="+$scope.order.order_number);  
		}else{
			//alert("银行");
			window.open("http://www.taobao.com");  
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
            	}else{
            		alert("尚未支付,如有疑问请联系888-88888");
            	}
            	
            }
        });
	};
	$scope.init();
};



shopController.$inject = ['$scope','$http','LoginService'];
shopModule.controller("shopController", shopController);
purchaseshopController.$inject = ['$scope','$http','LoginService'];
shopModule.controller("purchaseshopController", purchaseshopController);
shopinfoController.$inject = ['$scope','$http','$location','LoginService'];
shopModule.controller("shopinfoController", shopinfoController);
purchaseshopinfoController.$inject = ['$scope','$http','$location','LoginService'];
shopModule.controller("purchaseshopinfoController", purchaseshopinfoController);
shopmakeorderController.$inject = ['$scope','$http','$location','LoginService'];
shopModule.controller("shopmakeorderController", shopmakeorderController);
orderUserController.$inject = ['$scope','$http','$location','LoginService'];
shopModule.controller("orderUserController", orderUserController);
payController.$inject = ['$scope','$http','$location','LoginService'];
shopModule.controller("payController", payController);