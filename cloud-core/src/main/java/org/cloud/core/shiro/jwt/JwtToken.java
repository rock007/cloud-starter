package org.cloud.core.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3004555681333794164L;

	private String principal;
	
	private String password;

    private String token;

    @Override
    public String getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	/**
	 * @param principal
	 * @param token
	 */
	public JwtToken(String principal,String password, String token) {
		super();
		this.principal = principal;
		this.password=password;
		
		this.token = token;
	}
    
	public JwtToken() {

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
}
