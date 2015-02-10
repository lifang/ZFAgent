<#import "../page.ftl" as pager>
<div class="uesr_table"> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table"> 
        <colgroup> 
         <col width="200"> 
         <col width="150"> 
         <col> 
         <col> 
         <col> 
         <col width="80"> 
         <col width="80"> 
         <col width="80"> 
         <col width="150"> 
        </colgroup> 
        <thead> 
         <tr> 
          <th>POS机名称</th> 
          <th>品牌型号</th> 
          <th>库存数</th> 
          <th>第三方供货</th> 
          <th>状态</th> 
          <th>上架</th> 
          <th>可租赁</th> 
          <th>可批购</th> 
          <th>操作</th> 
         </tr> 
        </thead> 
        <tbody> 
         <#if (goods.content)??>
		  <#list goods.content as good>
       		<#include "pageRowPos.ftl" />
		  </#list>
		</#if>
        </tbody> 
       </table> 
	</div> 
      
	<@pager.p page=goods.currentPage totalPages=goods.totalPage functionName="posPageChange"/>	