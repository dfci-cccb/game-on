package edu.dfci.cccb.gameon.security.log;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.util.ClassUtils;

public class AuthenitcationEventLoggerListener implements
		ApplicationListener<AbstractAuthenticationEvent> {

    @Autowired private AuthenticationLoggerUtil authlogger = new AuthenticationLoggerUtil();
	@Override
	public void onApplicationEvent(AbstractAuthenticationEvent event) {	    
		authlogger.write(event);
	}

}
