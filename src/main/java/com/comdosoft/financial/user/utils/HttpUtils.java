package com.comdosoft.financial.user.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

public class HttpUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

	private HttpUtils(){}

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private static final HttpClient client = HttpClients.createDefault();
	private static final ContentType TEXT_CONTENT_TYPE = ContentType.create("text/plain", DEFAULT_CHARSET);

	public static <T> T get(String url,Map<String,String> headers,
			Map<String,String> params,
			ResponseHandler<T> handler) throws IOException{
		return get(url,headers,params,null,handler);
	}
	
	public static <T> T get(String url,HttpContext context,
			Map<String,String> params, ResponseHandler<T> handler) throws IOException{
		return get(url,null,params,context,handler);
	}
	
	/**
	 * get请求
	 * @param url
	 * @param headers
	 * @param params
	 * @param handler
	 * @return
	 * @throws IOException
	 */
	public static <T> T get(String url,Map<String,String> headers,
			Map<String,String> params, HttpContext context,
			ResponseHandler<T> handler) throws IOException{
		RequestBuilder builder = RequestBuilder.get();
		builder.setUri(url);
		HttpUriRequest request = request(builder,headers,params,null);
		LOG.debug("request:{}",request);
		return response(request, context, handler);
	}
	
	public static <T> T post(String url,Map<String,String> headers,
			Map<String,String> params,Map<String,File> fileParams,
			ResponseHandler<T> handler) throws IOException{
		return post(url,headers,params,fileParams,null,handler);
	}
	
	/**
	 * post 请求
	 * @param url
	 * @param headers
	 * @param params
	 * @param fileParams
	 * @param handler
	 * @return
	 * @throws IOException
	 */
	public static <T> T post(String url,Map<String,String> headers,
			Map<String,String> params,Map<String,File> fileParams,
			HttpContext context,ResponseHandler<T> handler) throws IOException{
		RequestBuilder builder = RequestBuilder.post();
		builder.setUri(url);
		HttpUriRequest request = request(builder,headers,params,fileParams);
		LOG.debug("request:{}",request);
		return response(request, context, handler);
	}
	
	public static <T> T post(String url,String body,HttpContext context,
			ResponseHandler<T> handler) throws IOException {
		RequestBuilder builder = RequestBuilder.post();
		builder.setUri(url);
		HttpEntity entity = new StringEntity(body, DEFAULT_CHARSET);
		builder.setEntity(entity);
		HttpUriRequest request = builder.build();
		LOG.debug("request:{}",request);
		return response(request, context, handler);
	}
	
	//处理返回
	private static <T> T response(HttpUriRequest request,
			HttpContext context,ResponseHandler<T> handler) throws IOException {
		HttpResponse response = client.execute(request,context);
		return handler.handleResponse(response);
	}
	
	//创建request
	private static HttpUriRequest request(RequestBuilder builder,
			Map<String,String> headers,Map<String,String> params,Map<String,File> fileParams) {
		//添加head
		if(!CollectionUtils.isEmpty(headers)){
			for(Entry<String, String> e :headers.entrySet()){
				builder.addHeader(e.getKey(), e.getValue());
			}
		}
		if(HttpGet.METHOD_NAME.equalsIgnoreCase(builder.getMethod())){
			return requestGet(builder, params);
		}
		if(HttpPost.METHOD_NAME.equalsIgnoreCase(builder.getMethod())){
			return requestPost(builder, params, fileParams);
		}
		return builder.build();
	}
	
	private static HttpUriRequest requestGet(RequestBuilder builder,
			Map<String,String> params){
		if(!CollectionUtils.isEmpty(params)) {
			for(Entry<String, String> e :params.entrySet()){
				builder.addParameter(e.getKey(), e.getValue());
			}
		}
		return builder.build();
	}
	
	private static HttpUriRequest requestPost(RequestBuilder builder,
			Map<String,String> params, Map<String,File> fileParams) {
		HttpEntity entity = null;
		//是否需要上传文件
		if(CollectionUtils.isEmpty(fileParams)){
			List<NameValuePair> parameters = Lists.newArrayList();
			if(params!=null){
				for(Entry<String, String> e :params.entrySet()){
					parameters.add(new BasicNameValuePair(e.getKey(), e.getValue()));
				}
			}
			entity = new UrlEncodedFormEntity(parameters, DEFAULT_CHARSET);
		}else {
			MultipartEntityBuilder multiBuilder = MultipartEntityBuilder.create().setCharset(DEFAULT_CHARSET);
			for(Entry<String, File> e :fileParams.entrySet()){
				multiBuilder.addBinaryBody(e.getKey(), e.getValue());
			}
			if(!CollectionUtils.isEmpty(params)) {
				for(Entry<String, String> e :params.entrySet()){
					multiBuilder.addTextBody(e.getKey(), e.getValue(),TEXT_CONTENT_TYPE);
				}
			}
			entity = multiBuilder.build();
		}
		builder.setEntity(entity);
		return builder.build();
	}
}
