package com.comdosoft.financial.user.domain;

/**
 * 系统接口返回消息体<br>
 * <功能描述>
 * 
 * @author Java-007 2015年2月7日
 * 
 */
public class Response {

    public static final Integer SUCCESS_CODE = 1;

    public static final Integer ERROR_CODE = -1;
    
    public static final Integer MISSING_CODE = -2;
    
    public static final String TOKEN="123321";
    
    private Integer code;

    private String message = "处理成功";

    private Object result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static Response getSuccess() {
        Response response = new Response();
        response.code = SUCCESS_CODE;
        return response;
    }

    public static Response getSuccess(Object result) {
        Response response = new Response();
        response.code = SUCCESS_CODE;
        response.result = result;
        return response;
    }

    public static Response getError(String message) {
        Response response = new Response();
        response.code = ERROR_CODE;
        response.message = message;
        return response;
    }

    public static Response buildSuccess(Object result, String message) {
        Response response = new Response();
        response.code = SUCCESS_CODE;
        response.result = result;
        response.message = message;
        return response;
    }
    
    public static Response buildMisSuccess() {
    	Response response = new Response();
    	response.code = SUCCESS_CODE;
    	response.result = "";
    	response.message = "请求的数据列表为空 或请求参数错误！请检查";
    	return response;
    }
    
    public static Response buildErrorWithMissing() {
        Response r = new Response();
        r.code = MISSING_CODE;
        r.result = null;
        r.message = "参数错误或数据不存在";
        return r;
    }
    
    public static Response getErrorContext(Object result) {
        Response response = new Response();
        response.code = 2;
        response.result = result;//错误数据
        return response;
    }

}