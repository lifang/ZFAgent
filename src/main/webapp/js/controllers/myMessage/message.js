'user strict';

var messageModule = angular.module("messageModule", []);

var messageController = function($scope,$location, $http, LoginService) {
	$scope.req={};
	$scope.req.ids=[];
	$scope.req.q=2;
	$scope.isSelectAll=false;
	$scope.req.customer_id=LoginService.userid;
	$scope.init = function() {
		if(LoginService.userid == 0){
			window.location.href = '#/login';
		}else{
			$scope.$emit('changeshow',false);
		}
		initSystemPage($scope.req);
		$scope.req.id=$location.search()['id'];
		if($scope.req.id>0){
			$scope.getinfo();
		}else{
			$scope.getlist();
		}
	};
	$scope.getlist=function(){
		$scope.req.page=$scope.req.indexPage;
		$http.post("api/message/receiver/getAll",$scop.req).success(function(data){
			if (data.code == 1){
				$scope.list=data.result.content;
				calcSystemPage($scope.req,data.result.total);
			}
		});
	};
	$scope.getinfo=function(){
		$http.post("api/message/receiver/getById",$scope.req).success(function(data){
			if (data.code == 1){
				$scope.message=data.result;
			}
		});
	};
	$scope.delone= function(id) {
		$scope.req.id=id;
		$http.post("api/message/receiver/deleteById",$scope.req).success(function(data) {
			if (data.code == 1) {
				window.location.href = '#/myMessage';
			} 
		});
	};
	$scope.delmore= function() {
		$http.post("api/message/receiver/batchDelete",$scope.req).success(function(data) {
			if (data.code == 1) {
				window.location.reload();
			} 
		});
		
	};
	$scope.delall= function() {
		$http.post("api/message/receiver/deleteAll",$scope.req).success(function(data) {
			if (data.code == 1) {
				window.location.reload();
			} 
		});
	};
	
	$scope.checkAll = function() {
		$scope.isSelectAll = !$scope.isSelectAll;
		if ($scope.isSelectAll) {
			angular.forEach($scope.list, function(one) {
				one.checked = true;
			});
		} else {
			angular.forEach($scope.list, function(one) {
				one.checked = false;
			});
		}
		$scope.setCheck();
	};
	$scope.checkOne = function(one) {
		$scope.isSelectAll = false;
		one.checked = !one.checked;
		$scope.setCheck();
	};
	$scope.setCheck = function() {
		$scope.req.ids=[];
		angular.forEach($scope.list, function(one) {
			if (one.checked) {
				$scope.req.ids.push(one.id);
			}
		});
	};
	$scope.unread = function() {
		$scope.req.indexPage=1;
		$scope.req.q=0;
		$scope.getlist();
	};
	$scope.read = function() {
		$scope.req.indexPage=1;
		$scope.req.q=2;
		$scope.getlist();
	};	
	$scope.init();
	 // 上一页
	$scope.prev = function() {
		if ($scope.req.indexPage > 1) {
			$scope.req.indexPage--;
			$scope.getlist();
		}
	};

	// 当前页
	$scope.loadPage = function(currentPage) {
		$scope.req.indexPage = currentPage;
		$scope.getlist();
	};

	// 下一页
	$scope.next = function() {
		if ($scope.req.indexPage < $scope.req.totalPage) {
			$scope.req.indexPage++;
			$scope.getlist();
		}
	};

	// 跳转到XX页
	$scope.getPage = function() {
		$scope.req.indexPage = Math.ceil($scope.req.gotoPage);
		$scope.getlist();
	};
	
};

messageController.$inject = ['$scope','$location','$http','LoginService'];
messageModule.controller("messageController", messageController);