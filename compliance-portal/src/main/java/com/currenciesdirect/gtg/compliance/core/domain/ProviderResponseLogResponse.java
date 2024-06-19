/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * @author manish
 *
 */
public class ProviderResponseLogResponse {

	private String responseJson;
	
	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;

	public String getResponseJson() {
		return responseJson;
	}

	public void setResponseJson(String responseJson) {
		this.responseJson = responseJson;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
