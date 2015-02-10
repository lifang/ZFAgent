package com.comdosoft.financial.user.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class SessionService {
	
	private static final String LOGIN_SESSION_KEY = "__LOGNIN_KEY__";
	
	public boolean isLogined(HttpServletRequest request){
		return getLoginInfo(request)!=null;
	}

	public void storeLoginInfo(HttpServletRequest request,Object obj) {
		request.getSession().setAttribute(LOGIN_SESSION_KEY, obj);
	}
	
	public Object getLoginInfo(HttpServletRequest request) {
		return request.getSession().getAttribute(LOGIN_SESSION_KEY);
	}
	
	public void clear(HttpServletRequest request){
		request.getSession().removeAttribute(LOGIN_SESSION_KEY);
	}
}
