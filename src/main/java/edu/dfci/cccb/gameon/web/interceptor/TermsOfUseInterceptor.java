package edu.dfci.cccb.gameon.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TermsOfUseInterceptor extends HandlerInterceptorAdapter {
	private final static Logger log = Logger.getLogger(TermsOfUseInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.debug("*****************intercepted*********************");
		return super.preHandle(request, response, handler);
	}
}
