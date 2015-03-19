'user strict';

var shopinfoController = function ($scope,$location, $http, LoginService) {
	$scope.req={};
	$scope.creq={};
	$scope.quantity=1;
	$scope.req.goodId=$location.search()['goodId'];
	$scope.creq.goodId=$scope.req.goodId;
	$scope.req.city_id=LoginService.city;
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
    $scope.addCart = function () {
    	$scope.cartreq={customerId:LoginService.userid,goodId:$scope.good.goodinfo.id,
    			paychannelId:$scope.paychannel.id,quantity:$scope.quantity};
    	$http.post("api/cart/add",$scope.cartreq ).success(function (data) {  //绑定
            if (data.code==1) {
            	//$scope.paychannel=data.result;
            	//LoginService.shopcount+=1;
            	$scope.$emit('shopcartcountchange');
            	window.location.href = '#/shopcart';
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


shopModule.controller("shopinfoController", shopinfoController);
