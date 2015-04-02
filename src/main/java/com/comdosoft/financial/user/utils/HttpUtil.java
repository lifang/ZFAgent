package com.comdosoft.financial.user.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;


public class HttpUtil {

    public static void postJsonHttp(String url, String paramName, String param) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.addParameter(paramName, param);
        postMethod.getParams().setContentCharset("UTF-8");
        client.executeMethod(postMethod);
    }
    
    public static Map<Object, Object> postJsonHttp2(String url, String paramName, Object param) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.addParameter(paramName, SysUtils.parseObjectToJSONString(param));
        postMethod.getParams().setContentCharset("UTF-8");
        Map<Object, Object> result = new HashMap<Object, Object>();
        int responseCode = client.executeMethod(postMethod);
        result.put("responseCode", responseCode);
        String responseBody = postMethod.getResponseBodyAsString();
        result.put("responseBody", responseBody);
        return result;
    }
}
