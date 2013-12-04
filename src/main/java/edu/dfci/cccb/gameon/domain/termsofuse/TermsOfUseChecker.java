package edu.dfci.cccb.gameon.domain.termsofuse;

import lombok.Synchronized;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Component
@Scope(value=SCOPE_SESSION, proxyMode=ScopedProxyMode.TARGET_CLASS)
public class TermsOfUseChecker {
	private boolean isAccepted=false;
	
	@Synchronized
	public boolean isAccepted() {
		return isAccepted;
	}
	@Synchronized
	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	
}
