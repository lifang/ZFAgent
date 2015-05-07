<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>响应页面</title>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); 
%>
<script>
	var t=10;//设定跳转的时间
	setInterval("refer()",1000); //启动1秒定时
	function refer(){
	if(t==0){
		//location="<%=basePath%>/#/order"; //#设定跳转的链接地址
		var isIE = navigator.appName == "Microsoft Internet Explorer";
        //alert(isIE);
        //window.opener.location="<%=basePath%>/#/order"; 
        //window.opener.finish();
        var browserName=navigator.appName;
        if (browserName=="Netscape") {
         	 window.open('','_parent','');
         	 //window.opener.location.href=window.opener.location.href;
         	 window.close();
        } else if (browserName=="Microsoft Internet Explorer") {
         	window.opener = "whocares"; window.close(); 
        }
	}
	document.getElementById('show').innerHTML=""+t+"秒后关闭"; // 显示倒计时
	t--; // 计数器递减
}
</script>
</head>
<body>
	<span>支付成功</span><br/>
	<span id="show"></span>
</body>
</html>