package com.comdosoft.financial.user.domain;

/**
 * <br>
 * <功能描述>
 *
 * @author Java-007 2015年2月7日
 *
 */
public class Response {

    public static final Integer SUCCESS_CODE = 1;
    public static final Integer ERROR_CODE = -1;

    private Integer code;
    private String message;
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
        Response r = new Response();
        r.code = SUCCESS_CODE;
        return r;
    }

    public static Response getSuccess(Object result) {
        Response r = new Response();
        r.code = SUCCESS_CODE;
        r.result = result;
        return r;
    }

    public static Response getError(String message) {
        Response r = new Response();
        r.code = ERROR_CODE;
        r.message = message;
        return r;
    }

    public static Response buildSuccess(Object result, String message) {
        Response r = new Response();
        r.code = SUCCESS_CODE;
        r.result = result;
        r.message = message;
        return r;
    }

}