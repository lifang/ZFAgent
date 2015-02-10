<#macro html>
<!DOCTYPE html>
<html>
    <@head />
<@body>
    <#nested />
</@body>
</html>
</#macro>

<#macro html_head>
<!DOCTYPE html>
<html>
    <@head>
        <#nested "head"/>
    </@head>
    <@body>
        <#nested "body"/>
    </@body>
</html>
</#macro>

<#macro head>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <title>运营中心</title>
    <link href="<@spring.url "/resources/style/style.css"/>" rel="stylesheet" type="text/css"/>
    <script src="<@spring.url "/resources/js/jquery-1.11.2.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/jquery.form.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/main.js"/>"></script>
    <#nested />
</head>
</#macro>

<#macro body>
<body>
<@top />
<@body_head />
<@main>
<#nested />
</@main>
<@foot />
</body>
</#macro>

<#macro top>
<div class="topInfo clear">
    <div class="box">
        <div class="top_user"><a href="#">${customer.name!"未命名"}</a><a href="<@spring.url "/logout"/>">退出</a></div>
    </div>
    <div class="clear"></div>
</div>
</#macro>

<#macro body_head>
<div class="head clear">
    <div class="box">
        <div class="logo">华尔街金融</div>
        <div class="systemName">运营中心</div>
    </div>
</div>
</#macro>

<#macro main>
<div class="main">
    <div class="box">
        <@left />
        <div class="right">
            <#nested />
        </div>
    </div>
</div>
</#macro>

<#macro left>
<div class="left">
    <ul>
        <li><a href="<@spring.url "/index"/>" class="hover">运营中心首页</a></li>
        <li><a href="<@spring.url "/real/trade"/>" target="map">全国交易实时统计</a></li>
        <li class="second"><a href="javascript:void(0);">商品<i class="off"></i></a>
            <ol>
                <li><a href="#">POS机管理</a></li>
                <li><a href="#">支付通道</a></li>
            </ol>
        </li>
        <li><a href="#">用户</a></li>
        <li><a href="#">终端</a></li>
        <li><a href="#">交易</a></li>
        <li class="second"><a href="javascript:void(0);">订单<i class="off"></i></a>
            <ol>
                <li><a href="#">用户订单</a></li>
                <li><a href="#">代理商批购</a></li>
                <li><a href="#">代理商代购</a></li>
            </ol>
        </li>
        <li class="second"><a href="javascript:void(0);">售后<i class="off"></i></a>
            <ol>
                <li><a href="#">资料更新</a></li>
                <li><a href="#">代理商售后</a></li>
                <li><a href="#">维修</a></li>
                <li><a href="#">换货</a></li>
                <li><a href="#">退货</a></li>
                <li><a href="#">租赁退还</a></li>
                <li><a href="#">注销</a></li>
            </ol>
        </li>
        <li class="second"><a href="javascript:void(0);">任务<i class="off"></i></a>
            <ol>
                <li><a href="#">售后库存管理</a></li>
                <li><a href="#">认证开通</a></li>
                <li><a href="#">积分兑换</a></li>
                <li><a href="#">出库</a></li>
                <li><a href="#">退款</a></li>
            </ol>
        </li>
        <li><a href="#">购买意向</a></li>
        <li class="second"><a href="javascript:void(0);">系统<i class="off"></i></a>
            <ol>
                <li><a href="#">运营账号</a></li>
                <li><a href="#">系统消息</a></li>
                <li><a href="#">系统参数</a></li>
                <li><a href="#">数据字典</a></li>
                <li><a href="#">第三方机构</a></li>
                <li><a href="#">代理商</a></li>
                <li><a href="#">网站内容</a></li>
                <li><a href="#">账户设置</a></li>
            </ol>
        </li>
    </ul>
</div>
</#macro>

<#macro foot>
<div class="foot">
    <div class="box">
        <ul class="foot_nav">
            <li><a href="#">关于我们</a></li>
            <li>/</li>
            <li><a href="#">企业文化</a></li>
            <li>/</li>
            <li><a href="#">帮助中心</a></li>
            <li>/</li>
            <li><a href="#">企业招聘</a></li>
        </ul>
        <div class="copyright">版权所有 &copy; 2011-2014 上海掌富网络技术有限公司（备案号 1236548）</div>
    </div>
</div>
</#macro>