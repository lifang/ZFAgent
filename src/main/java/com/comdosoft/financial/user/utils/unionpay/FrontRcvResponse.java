package com.comdosoft.financial.user.utils.unionpay;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comdosoft.financial.user.domain.query.OrderReq;
import com.comdosoft.financial.user.service.OrderService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * 名称：商户前台通知类 功能： 类属性： 方法调用 版本：5.0 日期：2014-07 作者：中国银联ACP团队 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */
@Controller
@RequestMapping(value="/unionpay")
public class FrontRcvResponse {
	@Resource
    private OrderService orderService;

	@RequestMapping(value="/acp_front_url.do")
	private void frontRcvResponse(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");

		req.setCharacterEncoding("ISO-8859-1");
		String encoding = req.getParameter(SDKConstants.param_encoding);
		LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");
		
		Map<String, String> respParam = UnionpayHelper.getAllRequestParam(req);

		// 打印请求报文
		LogUtil.printRequestLog(respParam);

		Map<String, String> valideData = null;
		StringBuffer page = new StringBuffer();
		if (null != respParam && !respParam.isEmpty()) {
			Iterator<Entry<String, String>> it = respParam.entrySet()
					.iterator();
			valideData = new HashMap<String, String>(respParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes("ISO-8859-1"), encoding);
				page.append("<tr><td width=\"30%\" align=\"right\">" + key
						+ "(" + key + ")</td><td>" + value + "</td></tr>");
				valideData.put(key, value);
			}
		}
		try{
			if (!SDKUtil.validate(valideData, encoding)) {
				page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
				LogUtil.writeLog("验证签名结果[失败].");
			} else {
				page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
				LogUtil.writeLog("验证签名结果[成功].");
				System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取
				
				OrderReq orderreq=new OrderReq();
				orderreq.setOrdernumber(valideData.get("orderId"));
				orderreq.setType(2);
				orderService.payFinish(orderreq);
				req.setAttribute("notify", "success");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		req.setAttribute("result", page.toString());
		req.getRequestDispatcher("/redirect.jsp").forward(req, resp);

		LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");
		
	}
}
