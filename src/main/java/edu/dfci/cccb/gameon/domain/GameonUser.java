package edu.dfci.cccb.gameon.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.openid.OpenIDAttribute;

public class GameonUser extends User {
	
	private String email=null;
	private List<OpenIDAttribute> openIDAttributes=null;
	
	private static final long serialVersionUID = 1L;

	public GameonUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
		if(getEmail()!=null)
			sb.append("Email: ").append(getEmail()).append("; ");
		sb.append(super.toString());
		if(openIDAttributes!=null)
			sb.append(openIDAttributes.toString());
		return sb.toString();
	}
	
	public String getEmail() {
		if(email==null && openIDAttributes!=null) 
			for(OpenIDAttribute attribute : openIDAttributes)		
				if(attribute.getName().equalsIgnoreCase("email"))
					if(attribute.getValues().size()>0)
						return attribute.getValues().get(0);
		
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<OpenIDAttribute> getOpenIDAttributes() {
		return openIDAttributes;
	}
	public void setOpenIDAttributes(List<OpenIDAttribute> openIDAttributes) {
		this.openIDAttributes = openIDAttributes;
	}
}
