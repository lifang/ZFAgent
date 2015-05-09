package com.comdosoft.financial.user.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 系统工具类<br>
 * <功能描述>
 *
 * @author hudong 2015年3月23日
 *
 */

public class CommUtils {
	
	@Value("${pictureHZList}")
	private static String pictureHZList;
    /**
     * 发送验证码  
     * @param str
     * @param phone
     * @return 是否成功
     * @throws IOException
     * @throws JsonParseException
     * @throws JsonMappingException
     */
    @SuppressWarnings("unchecked")
    public static Boolean sendPhoneCode(String content, String phone) throws IOException, JsonParseException, JsonMappingException {
        String smsUrl = "http://mt.10690404.com/send.do?Account=zf&Password=111111&Mobile="+phone+"&Content="+content+"&Exno=0&Fmt=json";
        String resStr = doGetRequest(smsUrl.toString());
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> reslt_map = mapper.readValue(resStr,Map.class);
        for (Map.Entry<String, Object> entry : reslt_map.entrySet()) {
            if(entry.getKey().equals("code")){
                if(entry.getValue().equals("9001")){
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings({ "resource", "rawtypes" })
    public static String doGetRequest(String urlstr) {
        HttpClient client = new DefaultHttpClient();
        client.getParams().setIntParameter("http.socket.timeout", 10000);
        client.getParams().setIntParameter("http.connection.timeout", 5000);
        HttpEntity entity = null;
        String entityContent = null;
        try {
            HttpGet httpGet = new HttpGet(urlstr.toString());
            HttpResponse httpResponse = client.execute(httpGet);
            entityContent = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entity != null) {
                try {
                    ((org.apache.http.HttpEntity) entity).consumeContent();
                } catch (Exception e) {
                }
            }
        }
        return entityContent;
    }
    public static void main(String[] args) {
    	try{
        	CommUtils.sendPhoneCode("hello", "17717360127");

    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    //校验上传图片格式是否满足
    public static Boolean typeIsCommit(String houzuiStr){
    	return pictureHZList.contains(houzuiStr);
    }

}
