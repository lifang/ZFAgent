<#import "../common.ftl" as c />
<@c.html>

	<div class="breadcrumb">
        <ul>
            <li><a href="#">商品</a></li>
            <li><a href="#">POS机管理</a></li>
            <li><a href="#">编辑POS机</a></li>
        </ul>
    </div>
    <div class="content clear">
		<div class="user_title"><h1>编辑POS机</h1>
        </div>
        <div class="attributes_box">
        	<h2>基础信息</h2>
            <div class="item_list clear">
                <ul>
                    <li class="b"><span class="labelSpan">标题：</span>
                    	<div class="text"><input name="" type="text"></div></li>
                    <li class="b"><span class="labelSpan">副标题：</span>
                    	<div class="text"><input name="" type="text"></div></li>
                    <li class="b"><span class="labelSpan">关键字：</span>
                    	<div class="text"><input name="" type="text"></div></li>
                    <li class="o"><span class="labelSpan">选择POS机分类：</span>
                    	<div class="text">
                        <select name="">
                        <#if posCategories??>
                          <#list posCategories as posCategory>
                    	  <option value="${posCategory.id}">
                    	  <#if posCategory.parentId??>&nbsp;&nbsp;L</#if>${posCategory.name}</option>
                    	  </#list>
                    	</#if>
                    	</select>
                        </div>
                    </li>
                    <li class="o"><span class="labelSpan">选择厂家：</span>
                    	<div class="text">
                        <select name="">
   						 <#if factories??>
                          <#list factories as factory>
                    	  <option value="${factory.id}">${factory.name}</option>
                    	  </#list>
                    	</#if>
                    	</select>
                        </div>
                    </li>
                    <li><span class="labelSpan">POS机品牌：</span>
                    	<div class="text"><input name="" type="text"></div></li>
                    <li><span class="labelSpan">POS机型号：</span>
                    	<div class="text"><input name="" type="text"></div></li>
                    <li class="o"><span class="labelSpan">加密卡方式：</span>
                    	<div class="text">
                        <select name="">
                    	  <option>111</option>
                    	</select>
                        </div>
                    </li>
                    <li class="o"><span class="labelSpan">签购单打印方式：</span>
                    	<div class="text">
                        <select name="">
                    	  <option>111</option>
                    	</select>
                        </div>
                    </li>
                    <li class="b"><span class="labelSpan">支持银行卡：</span>
                    	<div class="text">
                        	<span class="checkboxRadio_span"><input name="" type="checkbox" value=""> 接触式IC卡</span>
                            <span class="checkboxRadio_span"><input name="" type="checkbox" value=""> 非接触式IC卡</span>
                            <span class="checkboxRadio_span"><input name="" type="checkbox" value=""> 传统磁条卡</span>
                        </div>
                    </li>
                    <li><span class="labelSpan">电池信息：</span>
                    	<div class="text"><input name="" type="text"></div></li>
                    <li><span class="labelSpan">外壳材质：</span>
                    	<div class="text"><input name="" type="text"></div></li>
                </ul>
            </div> 
        </div>
        <div class="attributes_box">
        	<h2>价格信息</h2>
            <div class="item_list clear">
                <ul>
                    <li class=""><span class="labelSpan">原价：</span>
                    	<div class="text"><input name="" type="text"> 元<br>（保留小数点后两位）</div></li>
                    <li><span class="labelSpan">现价：</span>
                    	<div class="text"><input name="" type="text"> 元<br>（保留小数点后两位）</div></li>
                </ul>
            </div> 
        </div>
        
        <div class="attributes_box">
        	<h2>批购信息</h2>
            <div class="item_list clear">
                <ul>
                    <li class=""><span class="labelSpan">批购：</span>
                    	<div class="text"><input name="" type="text"> 元<br>（保留小数点后两位）</div></li>
                    <li><span class="labelSpan">最低限价：</span>
                    	<div class="text"><input name="" type="text"> 元<br>（保留小数点后两位）</div></li>
                    <li><span class="labelSpan">最小批购量：</span>
                    	<div class="text"><input name="" type="text"> 个</div></li>
                </ul>
            </div> 
        </div>
        
        <div class="attributes_box">
        	<h2>租赁设置</h2>
            <div class="item_list clear">
                <ul>
                    <li class=""><span class="labelSpan">租赁押金：</span>
                    	<div class="text"><input name="" type="text"> 元<br>（保留小数点后两位）</div></li>
                    <li><span class="labelSpan">最低租赁时间：</span>
                    	<div class="text"><input name="" type="text"> 月</div></li>
                    <li class="clear"><span class="labelSpan">最长租赁时间：</span>
                    	<div class="text"><input name="" type="text"> 月</div></li>
                    <li class="b"><span class="labelSpan">租赁说明：</span>
                    	<div class="text"><textarea name="" cols="" rows=""></textarea></div></li>
                    <li class="b"><span class="labelSpan">租赁协议：</span>
                    	<div class="text"><textarea name="" cols="" rows=""></textarea></div></li>
                </ul>
            </div> 
        </div>
        
        <div class="attributes_box">
        	<h2>支付通道</h2>
            <div class="item_list clear">
                <ul>
                    <li class=""><span class="labelSpan">添加支付通道：</span>
                    	<div class="text"><input name="" type="text"></div></li>
                </ul>
            </div> 
        </div>
        
        <div class="attributes_box">
        	<h2>其他</h2>
            <div class="item_list clear">
                <ul>
                    <li class="b"><span class="labelSpan">详细说明：</span>
                    	<div class="text"><textarea name="" cols="" rows=""></textarea></div></li>
                    <li><span class="labelSpan">POS机图片：</span>
                    	<div class="text">
                            <div class="item_photoBox">
                                <img src="images/zp.jpg" class="cover">
                                <a href="javascript:void(0);" class="informImg_a">
                                    <span>重新上传</span><input name="" multiple="" type="file">
                                </a>
                            </div>
                            <div class="item_photoBox">
                                <img src="images/zp.jpg" class="cover">
                                <a href="javascript:void(0);" class="informImg_a">
                                    <span>重新上传</span><input name="" multiple="" type="file">
                                </a>
                            </div>
                            <div class="item_photoBox">
                                <a href="javascript:void(0);" class="informImg_a">
                                    <span>上传照片</span><input name="" multiple="" type="file">
                                </a>
                            </div>
                        </div>
                    </li>
                    <li class="b"><span class="labelSpan">关联商品：</span>
                    	<div class="text"><input name="" type="text">
                        	<div class="item_relevance_pro">
                            	<span>超市收银机  M1000</span> <a href="#" class="a_btn">删除</a>
                            </div>
                        </div></li>
                </ul>
                <div class="img_info"><img src="images/mt_big.jpg"></div>
            </div> 
        </div>
        
        <div class="btnBottom"><button class="blueBtn">创建</button></div>
    </div>
    
<script type="text/javascript">
</script>    
</@c.html>