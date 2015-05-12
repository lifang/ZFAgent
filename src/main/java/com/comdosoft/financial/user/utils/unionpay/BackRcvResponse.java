package com.comdosoft.financial.user.utils.unionpay;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
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
 * 名称：商户后台通知类
 * 功能： 
 * 类属性：
 * 方法调用 版本：5.0 
 * 日期：2014-07 
 * 作者：中国银联ACP团队 
 * 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */

@Controller
@RequestMapping(value="/unionpay")
public class BackRcvResponse{
	@Resource
    private OrderService orderService;

	@RequestMapping(value="/acp_back_url.do")
	private void backRcvResponse(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {

		LogUtil.writeLog("BackRcvResponse接收后台通知开始");

		req.setCharacterEncoding("ISO-8859-1");
		String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取请求参数中所有的信息
		Map<String, String> reqParam = UnionpayHelper.getAllRequestParam(req);
		// 打印请求报文
		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes("ISO-8859-1"), encoding);
				valideData.put(key, value);
			}
		}

		// 验证签名
		if (!SDKUtil.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
		} else {
			System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取
			LogUtil.writeLog("验证签名结果[成功].");
			OrderReq orderreq=new OrderReq();
			orderreq.setOrdernumber(valideData.get("orderId"));
			orderreq.setType(2);
			orderService.payFinish(orderreq);
		}
		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
}
