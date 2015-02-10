<#import "../common.ftl" as c />
<@c.html>
     <div class="breadcrumb"> 
      <ul> 
       <li><a href="#">商品</a></li> 
       <li><a href="#">POS机管理</a></li> 
      </ul> 
     </div> 
     <div class="content clear"> 
      <div class="user_title">
       <h1>POS机列表</h1> 
       <div class="userTopBtnBox"> 
        <a href="#" class="ghostBtn">评论审核</a> 
        <a href="#" class="ghostBtn">管理POS机分类</a> 
        <a href="#" class="ghostBtn">创建POS机分类</a> 
       </div> 
      </div> 
      <div class="seenBox clear"> 
       <ul> 
        <li>
         <div class="user_search">
          <input  id="search_keys" name="" type="text" class="" />
          <input id="hidden_keys" type="hidden" name="keys" value="" />
          <input id="hidden_status" type="hidden" name="status" value="" />
          <button id="btn_search"></button>
         </div></li> 
        <li>
         <div class="user_select"> 
          <label>状态筛选</label> 
          <select id="select_status"> 
	          <option value="0">全部</option> 
	          <option value="1">待审核</option> 
	          <option value="2">初审不通过</option> 
	          <option value="3">初审通过</option> 
	          <option value="4">审核不通过</option> 	         
	          <option value="5">正常</option> 
	          <option value="6">已停用</option> 
          </select> 
         </div></li> 
       </ul> 
      </div> 
      <div id="page_fresh">
      	<#include "pagePos.ftl" />
      </div>
     </div>
<script type="text/javascript">

	$(function(){
		$('#select_status').change(function(){
			var status = $(this).children('option:selected').val();
			$("#hidden_status").val(status);
			posPageChange(1);
		}) 
		
		$("#btn_search").bind("click",
	        function() {
			var keys = $("#search_keys").val();
			$("#hidden_keys").val(keys);
			posPageChange(1);
	    });
       
	});
	
	function posPageChange(page) {
		var keys = $("#hidden_keys").val();
		var status = $("#hidden_status").val();
	    $.get('<@spring.url "/pos/page" />',
	            {"page": page,
	             "keys": keys,
	             "status": status
	            },
	            function (data) {
	                $('#page_fresh').html(data);
	            });
	};
	
	function firstUnCheck(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/firstUnCheck',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });
	};
	
	function firstCheck(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/firstCheck',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });	
	};
	
	function unCheck(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/unCheck',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });	
	};
	
	function check(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/check',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });
	};
	function stop(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/stop',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });
	};	
	function start(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/start',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });
	};	
	
	function publish(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/publish',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });
	};	
	
	function unPublish(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/unPublish',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });
	};
	
	function lease(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/lease',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });
	};	
	
	function unLease(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/unLease',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });
	};
	
	function purchase(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/purchase',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });
	};	
	
	function unPurchase(id){
		$.get('<@spring.url "" />'+'/pos/'+id+'/unPurchase',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
	            });
	};
	
</script>    
</@c.html>