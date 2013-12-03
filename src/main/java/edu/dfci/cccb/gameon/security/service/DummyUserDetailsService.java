package edu.dfci.cccb.gameon.security.service;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import edu.dfci.cccb.gameon.domain.GameonUser;
import edu.dfci.cccb.gameon.domain.Snp;

@Log4j
public class DummyUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService<AbstractAuthenticationToken> {

	//private static final Logger log = Logger.getLogger(DummyUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user =  new GameonUser(username, "", new ArrayList<GrantedAuthority>() );;
		return user;
	}

	@Override
	public UserDetails loadUserDetails(AbstractAuthenticationToken token)
			throws UsernameNotFoundException {

		GameonUser user =  new GameonUser(token.getName(), "", token.getAuthorities() );
		
//		OpenIDAuthenticationToken	
		if(token instanceof OpenIDAuthenticationToken){			
			List<OpenIDAttribute> attributes = ((OpenIDAuthenticationToken)token).getAttributes();
			user.setOpenIDAttributes(attributes);
		}
		
		return user;
	}
	
}
