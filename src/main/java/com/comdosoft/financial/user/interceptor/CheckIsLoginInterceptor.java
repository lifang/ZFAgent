package com.comdosoft.financial.user.interceptor;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.utils.SysUtils;


public class CheckIsLoginInterceptor extends HandlerInterceptorAdapter {
	//过滤不拦截的url
	//private static final String[] IGNORE_URI = {"login"};
		@Override
		public boolean preHandle(HttpServletRequest request,
				HttpServletResponse response, Object handler) throws Exception {
			boolean flag = false;
//			String url = request.getRequestURL().toString();
//			
//			if(url.contains("login")){
//				return true;
//			}else{
//		        for (String s : IGNORE_URI) {
//		            if (url.contains(s)) {
//		                flag = true;
//		                break;
//		            }
//		        }
		        if (!flag) {
		        	String customerId=request.getParameter("customerId");
					String token=request.getParameter("token");
					if(null !=customerId && null!=token){
						String temp=customerId+Response.TOKEN;	
						String tokenNew=SysUtils.string2MD5(temp);
						if(token.equals(tokenNew)){
							flag = true;
						}else{
							flag = false;
						}
					}else{
						flag = false;
					}
		        }
		        
		        if(flag){
		        	return true;
				}else{
					response.sendRedirect("ZFAgent/#/login");  
					return false;  
				}
	        }

		@Override
		public void postHandle(HttpServletRequest request,
				HttpServletResponse response, Object handler,
				ModelAndView modelAndView) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("test_过程开始");
			
	        super.postHandle(request, response, handler, modelAndView);
			System.out.println("test_过程结束");
		}

		@Override
		public void afterCompletion(HttpServletRequest request,
				HttpServletResponse response, Object handler, Exception ex)
				throws Exception {
			// TODO Auto-generated method stub
			System.out.println("test_后面");
			super.afterCompletion(request, response, handler, ex);
		}
}
