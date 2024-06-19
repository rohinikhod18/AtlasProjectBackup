package com.currenciesdirect.gtg.compliance.commons.domain;

/**
 * The Interface ITokenizer.
 */
//@FunctionalInterface
public interface ITokenizer {

	/**
	 * Gets the auth token.
	 *
	 * @return the auth token
	 */
	public String getAuthToken(String systemName,boolean createNew) throws Exception;
	
	public void setAuthToken(String token,String systemName);
}
