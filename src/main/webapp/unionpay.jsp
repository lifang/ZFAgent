<%@page import="com.comdosoft.financial.user.utils.unionpay.UnionpayHelper"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="com.unionpay.acp.sdk.SDKConfig"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.comdosoft.financial.user.utils.unionpay.Unionpay"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>银联支付页面跳转</title>
</head>
<body>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); 
	////////////////////////////////////请求参数//////////////////////////////////////
	
	//必填，不能修改
	//服务器异步通知页面路径
	String notify_url = basePath+"/unionpay/acp_front_url.do";
	//需http://格式的完整路径，不能加?id=123这类自定义参数
	
	//页面跳转同步通知页面路径
	String return_url = basePath+"/unionpay/acp_back_url.do";
	//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
	
	
	
	//商户订单号
	String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
	//商户网站订单系统中唯一订单号，必填
	
	//订单名称
	String subject =new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
	//必填
	
	//付款金额
	String total_fee =new String(request.getParameter("WIDtotal_fee").getBytes("ISO-8859-1"),"UTF-8");
	total_fee = String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue());
	//必填
	
	//订单描述
	
	String body ="掌富订单"; //new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
	//商品展示地址
	//String show_url ="http://www.商户网址.com/myorder.html"; //new String(request.getParameter("WIDshow_url").getBytes("ISO-8859-1"),"UTF-8");
	//需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html
	
	//防钓鱼时间戳
	String anti_phishing_key = "";
	//若要使用请调用类文件submit中的query_timestamp函数
	
	//客户端的IP地址
	String exter_invoke_ip = "";
	//非局域网的外网IP地址，如：221.0.0.1

	/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "07");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", notify_url);
		// 后台通知地址
		data.put("backUrl", return_url);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UnionpayHelper.merId);
		// 商户订单号，8-40位数字字母
//		data.put("orderId", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
//		data.put("txnAmt", "1");
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		// data.put("orderDesc", "订单描述");
		
		data.put("orderId", out_trade_no);
		data.put("txnAmt", total_fee);
		
		/**
		 * 交易请求url 从配置文件读取
		 */
		 String requestUrl = SDKConfig.getConfig().getFrontRequestUrl();
		 
		Map<String, String> submitFromData = (Map<String, String>) UnionpayHelper.signData(data);
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String responseStr = UnionpayHelper.createHtml(requestUrl,submitFromData);
		out.print(responseStr);
%>
</body>
</html>