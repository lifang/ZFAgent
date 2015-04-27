package com.comdosoft.financial.user.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;

public class CommonServiceUtil {

	public static String synchronizeStatus(String url,Integer terminalId) throws IOException{
		Map<String,String> headers = new  HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		Map<String,String> params = new  HashMap<String, String>();
		params.put("terminalId", String.valueOf(terminalId));
		Map<String,File> fileParams = new  HashMap<String, File>();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String response = HttpUtils.post(url, headers, params, fileParams, responseHandler);
        return response;
	}
	
	public static String getBankList(String url,String keyword,Integer page,
			Integer pageSize,Integer payChannelId,String serialNum) throws IOException{
		Map<String,String> headers = new  HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		Map<String,String> params = new  HashMap<String, String>();
		params.put("keyword", keyword);
		params.put("page", String.valueOf(page));
		params.put("pageSize", String.valueOf(pageSize));
		params.put("payChannelId", String.valueOf(payChannelId));
		params.put("serialNum", String.valueOf(serialNum));
		Map<String,File> fileParams = new  HashMap<String, File>();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String response = HttpUtils.post(url, headers, params, fileParams, responseHandler);
        return response;
	}
}
