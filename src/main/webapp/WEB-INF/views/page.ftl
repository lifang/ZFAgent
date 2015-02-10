<#--
	这是一个分页模板。在需要使用分页的页面导入 <@pager.p page=groups.number+1 totalPages=groups.totalPages functionName="pagingForward"/>就可以实现分页。使用该模板需要注意一下几点
	1.调用时需传入3个参数 page（当前页） totalPages（总页数）functionName（调用方法名称）
 -->
    <#macro p page totalPages functionName>  
        <#assign pageNo=page?number>  
        <#--这个是分页显示的页数，默认7-->
		<#assign pageCount=7>
		<#assign prePageCount=5>
	<div class="pageTurn"> 
		
		<div class="p_num"> 
			<#if pageNo?? &&  totalPages gt 0>
				<#if totalPages lte pageCount  >
			    	<a href="#" class="disabled">上一页</a> 
					<#list 1..totalPages as a>
				      	<#if a=pageNo>
			    			<a href="#" class="current">${a}</a> 
				      	<#else>
					        <a onclick='${functionName}(${a})'>${a}</a>
				      	</#if>
				     </#list>
			    </#if>
				<#if totalPages gt pageCount  >
				    <#if totalPages-pageNo lt pageCount  >
					    <#assign pageEnd=totalPages>
					    <#assign pageBegin=totalPages-pageCount+1>
					<#elseif pageNo gt 3  >
						<#assign pageBegin=pageNo-3>
					    <#assign pageEnd=pageBegin+prePageCount-1>
					<#else>
						<#assign pageBegin=1>
					    <#assign pageEnd=prePageCount>
					</#if>
					
					<#if pageBegin gt 1  >
						<a onclick='${functionName}(${pageNo-1})'>上一页</a>
					</#if>
					<#list pageBegin..pageEnd as a>
					<#if pageNo gt 3  >
					</#if>
				      	<#if a=pageNo>
				      		<a class='current'>${a}</b>
				      	<#else>
					        <a onclick='${functionName}(${a})'>${a}</a>
				      	</#if>
					</#list>
					
					<#if totalPages -pageEnd gt 0  >
					... 
					<a onclick='${functionName}(${totalPages-1})'>${totalPages-1}</a>
					<a onclick='${functionName}(${totalPages})'>${totalPages}</a>
					</#if>
					
					<#if pageNo lt (totalPages)>
						<a onclick='${functionName}(${pageNo+1})'>下一页</a>
					</#if>
			    </#if> 
			</#if>
		</div> 
		
		
		<div class="p_skip"> 
		    <span>共${totalPages}页</span> 
		    <span>到第&nbsp;&nbsp;<input type="text" id="pageClickNum" onkeyup="value=this.value.replace(/\D+/g,'')"/>&nbsp;&nbsp;页</span> 
		    <button onclick="pageClick()">确定</button> 
		</div> 
		
	<script type="text/javascript">
	
	function pageClick() {
		${functionName}($("#pageClickNum").val());
	}
 
		
	</script> 
	</div>
    </#macro>  