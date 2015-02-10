     <tr id="row_${good.id}"> 
      <td>${good.title!}</td> 
      <td><#if good.goodBrand??>${good.goodBrand.name!}</#if>&nbsp;${good.modelNumber!}</td> 
      <td>${good.quantity}</td> 
      <td><#if good.belongsTo??>是</#if></td> 
      <td><strong class="strong_status">
       <#if good.status=1>待审核
       <#elseif good.status=2>初审不通过
       <#elseif good.status=3>初审通过   
       <#elseif good.status=4>审核不通过
       <#elseif good.status=5>正常
       <#elseif good.status=6>已停用
       </#if>
      </strong></td> 
      <td><#if good.isPublished??>
      	<#if good.isPublished>是<#else>否</#if>
      </#if></td> 
      <td><#if good.hasLease??>
      	<#if good.hasLease>是<#else>否</#if>
      </#if></td> 
      <td><#if good.hasPurchase??>
      	<#if good.hasPurchase>是<#else>否</#if>
      </#if></td> 
      <td>
	   <#if good.status=1>
       		<a onClick="firstCheck(${good.id})" class="a_btn">初审通过</a>
       		<a onClick="firstUnCheck(${good.id})" class="a_btn">初审不通过</a>
       		<a onClick="check(${good.id})" class="a_btn">审核通过</a> 
       		<a onClick="unCheck(${good.id})" class="a_btn">审核不通过</a>
       		<a href="<@spring.url "/pos/${good.id}/edit" />" class="a_btn">编辑</a> 
       		<a href="<@spring.url "/pos/${good.id}/info" />" class="a_btn">查看详情</a>
       		
       <#elseif good.status=2>
            <a onClick="firstCheck(${good.id})" class="a_btn">初审通过</a>
       		<a onClick="check(${good.id})" class="a_btn">审核通过</a> 
       		<a href="<@spring.url "/pos/${good.id}/edit" />" class="a_btn">编辑</a> 
       		<a href="<@spring.url "/pos/${good.id}/info" />" class="a_btn">查看详情</a>
       		
       <#elseif good.status=3>
       		<a onClick="check(${good.id})" class="a_btn">审核通过</a> 
       		<a onClick="unCheck(${good.id})" class="a_btn">审核不通过</a>
       		<a href="<@spring.url "/pos/${good.id}/edit" />" class="a_btn">编辑</a> 
       		<a href="<@spring.url "/pos/${good.id}/info" />" class="a_btn">查看详情</a>
       		
       <#elseif good.status=4>
       		<a onClick="check(${good.id})" class="a_btn">审核通过</a> 
       		<a href="<@spring.url "/pos/${good.id}/edit" />" class="a_btn">编辑</a> 
       		<a href="<@spring.url "/pos/${good.id}/info" />" class="a_btn">查看详情</a>
       		
       <#elseif good.status=5>
			<#if good.isPublished?? && good.isPublished>
           	<a onClick="unPublish(${good.id})" class="a_btn">下架</a> 
      		<#else>
           	<a onClick="publish(${good.id})" class="a_btn">上架</a>		          		
      		</#if>
			<#if good.hasLease?? && good.hasLease>
           	<a onClick="unLease(${good.id})" class="a_btn">不可租赁</a> 
      		<#else>
           	<a onClick="lease(${good.id})" class="a_btn">可租赁</a> 		          		
      		</#if>		                   	
			<#if good.hasPurchase?? && good.hasPurchase>
           	<a onClick="unPurchase(${good.id})" class="a_btn">不可批购</a> 
      		<#else>
           	<a onClick="purchase(${good.id})" class="a_btn">可批购</a> 		          		
      		</#if>
           	<a href="#" class="a_btn">入库</a> 
           	<a href="#" class="a_btn">评论管理</a> 
       		<a onClick="stop(${good.id})" class="a_btn">停用</a> 
       		<a href="<@spring.url "/pos/${good.id}/info" />" class="a_btn">查看详情</a>
       		
       <#elseif good.status=6>
       		<a onClick="start(${good.id})" class="a_btn">启用</a> 
       		<a href="<@spring.url "/pos/${good.id}/edit" />" class="a_btn">编辑</a> 
       		<a href="<@spring.url "/pos/${good.id}/info" />" class="a_btn">查看详情</a> 
       </#if>

	 </td> 
   </tr>