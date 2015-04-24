package com.comdosoft.financial.user.utils.alipayApp.config;

import com.comdosoft.financial.user.utils.RootUrl;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088811347108355";
	// 商户的私钥
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALjI06X8hEw9LiLqTsqmjZAqwSq/VIGJKNQgIeCCr/oReR4OePe5i2u+89PpcFe6kF2v6gWulb4WNdHYw3Iiux56sm7jUQPC1hVYXG8tiaVEb3YhX2y0YGQUS18drBBGzHnlOQlrrmlBh9ugQFzLio2NwUWo0yfcXlLoKYyteDBVAgMBAAECgYBpjW441rHLyvbbwvQXFmSvAX0uKfTfubW01lYDpSNYuTpyTNoUx8w4U+98EVC3DD8DBUWs0TmAR7eeky+xtt0jZ1O8bpHUzRi02NOw2p1ZyAHN28rvUpultfInBpbqgJDvMoWIX4AeqWQcs4gbAbPyEaWvgYM53uW7eo9CtcFMgQJBAOHGVL8Xe9agkiGwYT8e9068+xjXiloAKgQjps8fxLfMCd34sI1tEjyz0jIZ+AK4pGvU1JJdtx7s70INnubqoY0CQQDRhbFcxqaz2c+S2WUQNduFah5EZt/vdWxo4+6EHrXNdAjT7nVyA8CzreRXcPEKQZ+RhuXyXGqSLDJGKYPGQIPpAkBSmqfjCoqKqlEM9mV+HKxLKKWOHz5FU44L2adsXKkyvfpWNmkSNXfYscoT/qBZDolJ0qK7soIPVIztU+JxhiL5AkAC037U9YkCHAoEvRHz6gYQAqJt4cVbgYX41Do/Zfqlzs7frPPAmfRbeBkAZPGbZc81M1CeuEhnuFjlQWIZpn0hAkEAu1Q+fNm01qqVJ0YCMeyUoLqin/rmRAsY93cDNk82ZxY+gc3YDlcvF5qqQqcqiSSHBZkAtQqFTzx3taybP2MKjw==";
	
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	public static String  backurl=RootUrl.urlpath;;

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "/opt/daya/logs/";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
