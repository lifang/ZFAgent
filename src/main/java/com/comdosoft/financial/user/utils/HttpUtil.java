package com.comdosoft.financial.user.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSON;
import com.comdosoft.financial.user.domain.query.PayReq;


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
    
    	public static Map<String,Object>  sendPost(String POST_URL,PayReq req) {
            URL postUrl;
            Map<String,Object> m = null;
			try {
				postUrl = new URL(POST_URL);
				 HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		            connection.setDoOutput(true);
		            connection.setDoInput(true);
		            connection.setRequestMethod("POST");
		            connection.setUseCaches(false);
		            connection.setInstanceFollowRedirects(true);
		            connection.setRequestProperty("Content-Type","application/json");
		            connection.connect();
		            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		            out.writeBytes(SysUtils.parseObjectToJSONString(req)); 
		            out.flush();
		            out.close(); // flush and close
		            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		            String line;
		            List<String> contents = new ArrayList<String>();  
		            while ((line = reader.readLine()) != null) {
		                contents.add(line);
		            }
		            reader.close();
		            connection.disconnect();
		            String json =  contents.get(0);
		             m = JSON.parseObject(json);
			} catch (Exception e) {
				e.printStackTrace();
			}
            return m;
        }
    
}
