package edu.dfci.cccb.gameon.domain.termsofuse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.Synchronized;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TermsOfUseInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired TermsOfUseChecker termsOfUseCheck;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
				
		if(termsOfUseCheck.isAccepted()) 
			return true;		
		response.sendRedirect("termsofuse");
		return false;
	}
}
