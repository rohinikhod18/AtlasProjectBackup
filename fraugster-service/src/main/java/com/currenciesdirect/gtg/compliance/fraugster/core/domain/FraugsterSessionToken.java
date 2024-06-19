package com.currenciesdirect.gtg.compliance.fraugster.core.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class SessionToken.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FraugsterSessionToken {
	
	/** The login time. */
	private Timestamp loginTime;
	
	/** The session token. */
	private String sessionToken;
	
	/**
	 * Gets the login time.
	 *
	 * @return the login time
	 */
	public Timestamp getLoginTime() {
		return loginTime;
	}
	
	/**
	 * Sets the login time.
	 *
	 * @param loginTime the new login time
	 */
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	
	/**
	 * Gets the session token.
	 *
	 * @return the session token
	 */
	public String getSessionToken() {
		return sessionToken;
	}
	
	/**
	 * Sets the session token.
	 *
	 * @param fraugsterSessionToken the new session token
	 */
	public void setSessionToken(String fraugsterSessionToken) {
		this.sessionToken = fraugsterSessionToken;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SessionToken [loginTime=" + loginTime + ", fraugsterSessionToken=" + sessionToken + "]";
	}
}