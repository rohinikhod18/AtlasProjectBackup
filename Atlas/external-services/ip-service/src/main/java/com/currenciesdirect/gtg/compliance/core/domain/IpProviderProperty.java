/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * @author manish
 *
 */
public class IpProviderProperty {

	private String enpointUrl;
	
	private String apiVersion;
	
	private String methodName;
	
	private String apiKey;
	
	private String secret;
	

	public String getEnpointUrl() {
		return enpointUrl;
	}

	public void setEnpointUrl(String enpointUrl) {
		this.enpointUrl = enpointUrl;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	
}
