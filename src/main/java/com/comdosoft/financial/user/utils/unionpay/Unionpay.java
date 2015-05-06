package com.comdosoft.financial.user.utils.unionpay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SecureUtil;
@Controller
public class Unionpay {
   
    //static {
    	/**
		 * 参数初始化
		 * 在java main 方式运行时必须每次都执行加载
		 * 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
    	//String path = Unionpay.class.getResource("/config/").getPath();
    	//SDKConfig.getConfig().loadPropertiesFromPath(path);
    //}
    
    
	@RequestMapping(value="/unionpay.do", method=RequestMethod.POST)
	public void getway(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String wap = request.getParameter("wap");
		if("wap".equals(wap)){
			wapFrontConsume(request,response);
			return;
		}
		String txnType = request.getParameter("txnType");
		if("00".equals(txnType)){
			query(request,response);
		}else if("01".equals(txnType)){
			pay(request,response);
		}else if("31".equals(txnType)){
			consumeUndo(request,response);
		}else if("04".equals(txnType)){
			refund(request,response);
		}else if("32".equals(txnType)){
			authUndo(request,response);
		}else if("03".equals(txnType)){
			authFinish(request,response);
		}else if("33".equals(txnType)){
			authFinishUndo(request,response);
		}else if("02".equals(txnType)){
			auth(request,response);
		}else if("76".equals(txnType)){
			billDwonload(request,response);
		}else{
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
			out.println("系统不支持该交易类型<br/><br/>");
			out.println("</body>");
			out.println("</html>");
			out.flush();
			out.close();
		}
	}
	private void wapFrontConsume(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String orderId = request.getParameter("orderId");
		String txnAmt = request.getParameter("txnAmt");

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
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", UnionpayHelper.frontUrl);
		// 后台通知地址
		data.put("backUrl", UnionpayHelper.backUrl);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UnionpayHelper.merId);
		// 商户订单号，8-40位数字字母
		//data.put("orderId", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
		//data.put("txnAmt", "1");
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		// data.put("orderDesc", "订单描述");
		
		data.put("orderId", orderId);
		data.put("txnAmt", txnAmt);
		
		data = UnionpayHelper.signData(data);

		// 交易请求url 从配置文件读取
		
		String frontOrBack = request.getParameter("frontOrBack");
		if("front".equals(frontOrBack)){
			String requestAppUrl = SDKConfig.getConfig().getFrontRequestUrl();
			Map<String, String> submitFromData = (Map<String, String>) UnionpayHelper.signData(data);
			
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			String responseStr = UnionpayHelper.createHtml(requestAppUrl,submitFromData);
			System.out.println(responseStr);
			PrintWriter out = response.getWriter();
			out.print(responseStr);
			out.flush();
			out.close();
		}else{
			String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();
			Map<String, String> resmap = UnionpayHelper.submitUrl(data, requestAppUrl);
	
			String android = request.getParameter("android");
			if("android".equals(android)){
				PrintWriter out = response.getWriter();
				out.print(resmap.get("tn"));
				out.flush();
			}else{
		//		System.out.println("请求报文=["+data.toString()+"]");
		//		System.out.println("应答报文=["+resmap.toString()+"]");
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
				out.println("请求报文=["+data.toString()+"]<br/><br/>");
				out.println("应答报文=["+resmap.toString()+"]");
				out.println("</body>");
				out.println("</html>");
				out.flush();
			}
		}

	}
	private void billDwonload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String settleDate = request.getParameter("settleDate");
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
		// 交易类型 
		data.put("txnType", "76");
		// 交易子类型 
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000000");
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户代码，请替换实际商户号测试，如使用的是自助化平台注册的商户号，该商户号没有权限测文件下载接口的，请使用测试参数里写的文件下载的商户号和日期测。如需真实交易文件，请使用自助化平台下载文件。
		data.put("merId", "700000000000001");
		// 清算日期
		data.put("settleDate", settleDate);
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 文件类型
		data.put("fileType", "00");

		data = UnionpayHelper.signData(data);

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getFileTransUrl();

		Map<String, String> resmap = UnionpayHelper.submitUrl(data, url);

		// 解析返回报文的文件流
		UnionpayHelper.deCodeFileContent(resmap);
		
//		System.out.println("请求报文=["+data.toString()+"]");
//		System.out.println("应答报文=["+resmap.toString()+"]");

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		out.println("请求报文=["+data.toString()+"]<br/><br/>");
		out.println("应答报文=["+resmap.toString()+"]<br/><br/><br>");
		out.println("内容=[");
		
		String fileContent = resmap.get(SDKConstants.param_fileContent);
		byte[] fileArray = SecureUtil.inflater(SecureUtil
				.base64Decode(fileContent.getBytes(UnionpayHelper.encoding)));
		
		List<byte[]> l = unZipEntryByteList(fileArray);
		try {
			for(byte[] bb:l){
				if(null == bb){
					continue;
				}
				String outContent = new String(bb,"GB2312");
				out.println(outContent.replaceAll(" ", "&nbsp;").replaceAll("\r\t", "<br/>").replaceAll("\n", "<br/>"));
				out.println("<br/><br/>");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		out.println("]<br/><br/><br>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
	/**
	 * 基于字节码的解压缩
	 * @param data
	 * @return
	 */
	private List<byte[]> unZipEntryByteList(byte[] data) {
		List<byte[]> l = new ArrayList<byte[]>();
		
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ZipInputStream zip = new ZipInputStream(bis);
			while (zip.getNextEntry() != null) {
				byte[] b = null;
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = zip.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				b = baos.toByteArray();
				baos.flush();
				baos.close();
				l.add(b);
			}
			zip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return l;
	}
	private void authFinishUndo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String origQryId = request.getParameter("origQryId");
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
		// 交易类型 
		data.put("txnType", "33");
		// 交易子类型 
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", UnionpayHelper.frontUrl);
		// 后台通知地址
		data.put("backUrl", UnionpayHelper.backUrl);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UnionpayHelper.merId);
		// 原预授权完成的queryId，可以从查询接口或者通知接口中获取
		data.put("origQryId", origQryId);
		// 商户订单号，8-40位数字字母，重新产生，不同于原交易
		data.put("orderId", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，撤销时需和原预授权完成一致
		data.put("txnAmt", "1");
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");

		data = UnionpayHelper.signData(data);

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getBackRequestUrl();

		Map<String, String> resmap = UnionpayHelper.submitUrl(data, url);

//		System.out.println("请求报文=["+data.toString()+"]");
//		System.out.println("应答报文=["+resmap.toString()+"]");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		out.println("请求报文=["+data.toString()+"]<br/><br/>");
		out.println("应答报文=["+resmap.toString()+"]");
		out.println("</body>");
		out.println("</html>");
		out.flush();
	}
	private void authFinish(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String origQryId = request.getParameter("origQryId");
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
		// 交易类型 
		data.put("txnType", "03");
		// 交易子类型 
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", UnionpayHelper.frontUrl);
		// 后台通知地址
		data.put("backUrl", UnionpayHelper.backUrl);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UnionpayHelper.merId);
		// 原预授权的queryId，可以从查询接口或者通知接口中获取
		data.put("origQryId", origQryId);
		// 商户订单号，8-40位数字字母，重新产生，不同于原交易
		data.put("orderId", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，小于等于原预授权金额的115%
		data.put("txnAmt", "1");
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");

		data = UnionpayHelper.signData(data);

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getBackRequestUrl();

		Map<String, String> resmap = UnionpayHelper.submitUrl(data, url);

//		System.out.println("请求报文=["+data.toString()+"]");
//		System.out.println("应答报文=["+resmap.toString()+"]");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		out.println("请求报文=["+data.toString()+"]<br/><br/>");
		out.println("应答报文=["+resmap.toString()+"]");
		out.println("</body>");
		out.println("</html>");
		out.flush();
	}
	private void refund(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String origQryId = request.getParameter("origQryId");
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
		// 交易类型 
		data.put("txnType", "04");
		// 交易子类型 
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", UnionpayHelper.frontUrl);
		// 后台通知地址
		data.put("backUrl", UnionpayHelper.backUrl);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UnionpayHelper.merId);
		//原消费的queryId，可以从查询接口或者通知接口中获取
		data.put("origQryId", origQryId);
		// 商户订单号，8-40位数字字母，重新产生，不同于原消费
		data.put("orderId", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额
		data.put("txnAmt", "1");
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");

		data = UnionpayHelper.signData(data);

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getBackRequestUrl();

		Map<String, String> resmap = UnionpayHelper.submitUrl(data, url);

		// resmap 里返回报文中
		// 银联订单号 tn 商户推送订单后银联移动支付系统返回该流水号，商户调用支付控件时使用
//		System.out.println("请求报文=["+data.toString()+"]");
//		System.out.println("应答报文=["+resmap.toString()+"]");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		out.println("请求报文=["+data.toString()+"]<br/><br/>");
		out.println("应答报文=["+resmap.toString()+"]");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();

	}
	private void authUndo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String origQryId = request.getParameter("origQryId");
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
		// 交易类型 
		data.put("txnType", "32");
		// 交易子类型 
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", UnionpayHelper.frontUrl);
		// 后台通知地址
		data.put("backUrl", UnionpayHelper.backUrl);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UnionpayHelper.merId);
		// 原预授权的queryId，可以从查询接口或者通知接口中获取
		data.put("origQryId", origQryId);
		// 商户订单号，8-40位数字字母，重新产生，不同于原交易
		data.put("orderId", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，撤销时需和原预授权一致
		data.put("txnAmt", "1");
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");

		data = UnionpayHelper.signData(data);

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getBackRequestUrl();

		Map<String, String> resmap = UnionpayHelper.submitUrl(data, url);

//		System.out.println("请求报文=["+data.toString()+"]");
//		System.out.println("应答报文=["+resmap.toString()+"]");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		out.println("请求报文=["+data.toString()+"]<br/><br/>");
		out.println("应答报文=["+resmap.toString()+"]");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
	private void auth(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String orderId = req.getParameter("orderId");
		String txnAmt = req.getParameter("txnAmt");
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
		data.put("txnType", "02");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", UnionpayHelper.frontUrl);
		// 后台通知地址
		data.put("backUrl", UnionpayHelper.backUrl);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UnionpayHelper.merId);
		// 商户订单号，8-40位数字字母
		//data.put("orderId", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		data.put("orderId",orderId);
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
		//data.put("txnAmt", "1");
		data.put("txnAmt",txnAmt);
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		// data.put("orderDesc", "订单描述");

		Map<String, String> submitFromData = UnionpayHelper.signData(data);

		// 交易请求url 从配置文件读取
		String requestUrl = SDKConfig.getConfig().getFrontRequestUrl();
		
		/**
		 * 创建表单
		 */
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		String html = UnionpayHelper.createHtml(requestUrl,submitFromData);
		System.out.println(html);
		PrintWriter out = resp.getWriter();
		out.print(html);
		out.flush();
		out.close();

	}
	private void consumeUndo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String origQryId = request.getParameter("origQryId");
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
		// 交易类型 
		data.put("txnType", "31");
		// 交易子类型 
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "07");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", UnionpayHelper.frontUrl);
		// 后台通知地址
		data.put("backUrl", UnionpayHelper.backUrl);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UnionpayHelper.merId);
		//原消费的queryId，可以从查询接口或者通知接口中获取
		data.put("origQryId", origQryId);
		// 商户订单号，8-40位数字字母，重新产生，不同于原消费
		data.put("orderId", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，消费撤销时需和原消费一致
		data.put("txnAmt", "1");
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");

		data = UnionpayHelper.signData(data);

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getBackRequestUrl();

		Map<String, String> resmap = UnionpayHelper.submitUrl(data, url);
		
//		System.out.println("请求报文=["+data.toString()+"]");
//		System.out.println("应答报文=["+resmap.toString()+"]");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		out.println("请求报文=["+data.toString()+"]<br/><br/>");
		out.println("应答报文=["+resmap.toString()+"]");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
	private void pay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String orderId = req.getParameter("orderId");
		String txnAmt = req.getParameter("txnAmt");
		
		
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
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", UnionpayHelper.frontUrl);
		// 后台通知地址
		data.put("backUrl", UnionpayHelper.backUrl);
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
		
		data.put("orderId", orderId);
		data.put("txnAmt", txnAmt);
		
		/**
		 * 交易请求url 从配置文件读取
		 */
		 String requestUrl = SDKConfig.getConfig().getFrontRequestUrl();
		 
		Map<String, String> submitFromData = (Map<String, String>) UnionpayHelper.signData(data);
		
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		String responseStr = UnionpayHelper.createHtml(requestUrl,submitFromData);
		System.out.println(responseStr);
		PrintWriter out = resp.getWriter();
		out.print(responseStr);
		out.flush();
		out.close();

	}
	private void query(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String orderId =request.getParameter("orderId");
		String txnTime =request.getParameter("txnTime");
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
		// 交易类型 
		data.put("txnType", "00");
		// 交易子类型 
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000000");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UnionpayHelper.merId);
		// 商户订单号，请修改被查询的交易的订单号
		data.put("orderId", orderId);
		// 订单发送时间，请修改被查询的交易的订单发送时间
		data.put("txnTime", txnTime);

		data = UnionpayHelper.signData(data);

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getSingleQueryUrl();

		Map<String, String> resmap = UnionpayHelper.submitUrl(data, url);

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		out.println("请求报文=["+data.toString()+"]<br/><br/>");
		out.println("应答报文=["+resmap.toString()+"]");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
}
