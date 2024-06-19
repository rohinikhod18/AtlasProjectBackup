package com.currenciesdirect.gtg.compliance.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class RegistrationRecheckFailureResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRecheckFailureResponse {

	/** The status. */
	private String status;
	
	/** The trade payment ID. */
	private Integer tradeAccountId;
	
	/** The error code. */
	protected String errorCode;

	/** The error description. */
	protected String errorDescription;
	

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * Gets the trade account id.
	 *
	 * @return the trade account id
	 */
	public Integer getTradeAccountId() {
		return tradeAccountId;
	}

	/**
	 * Sets the trade account id.
	 *
	 * @param tradeAccountId the new trade account id
	 */
	public void setTradeAccountId(Integer tradeAccountId) {
		this.tradeAccountId = tradeAccountId;
	}

	
}
