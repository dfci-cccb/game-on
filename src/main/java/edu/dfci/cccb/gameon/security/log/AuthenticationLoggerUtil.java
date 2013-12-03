package edu.dfci.cccb.gameon.security.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

import org.apache.log4j.Priority;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.util.ClassUtils;

public class AuthenticationLoggerUtil {
	
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AuthenticationLoggerUtil.class);
		
	public void write(Authentication authentication){
        log.info(getLines(authentication));	
	}
	
	public void write(HttpServletRequest request, HttpServletResponse response, Authentication authentication){		
		log.info(getLines(authentication));
	}
	
	public void write(AbstractAuthenticationEvent event){		
		log.info(getLines(event));
	}
	
	private String getLines(Authentication authentication){
		final StringBuilder builder = new StringBuilder();
		if(authentication!=null){			
			builder.append(authentication.getName());
	        builder.append("; principal: ");
	        builder.append(authentication.getPrincipal());
	        builder.append("; details: ");
	        builder.append(authentication.getDetails());		
		}	
		return builder.toString();
	}

	private String getLines(AbstractAuthenticationEvent event){
		final StringBuilder builder = new StringBuilder();
		if(event!=null){						
			builder.append("Authentication Log(");
	        builder.append(ClassUtils.getShortName(event.getClass()));
	        builder.append("): ");
	        builder.append(getLines(event.getAuthentication()));
		}	
		return builder.toString();
	}

	
}
