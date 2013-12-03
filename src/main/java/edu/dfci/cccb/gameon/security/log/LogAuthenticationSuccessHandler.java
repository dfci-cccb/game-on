package edu.dfci.cccb.gameon.security.log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


public class LogAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LogAuthenticationSuccessHandler.class);
	private static final AuthenticationLoggerUtil authLog = new AuthenticationLoggerUtil(); 
    
    
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		
		authLog.write(request, response, authentication);
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
