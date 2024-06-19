package com.currenciesdirect.gtg.compliance.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class PaymentRecheckFailureResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRecheckFailureResponse {

	/** The status. */
	private String status;
	
	/** The trade payment ID. */
	private Integer tradePaymentId;
	
	/** The error code. */
	protected String errorCode;

	/** The error description. */
	protected String errorDescription;
	

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * @return the tradePaymentId
	 */
	public Integer getTradePaymentId() {
		return tradePaymentId;
	}

	/**
	 * @param tradePaymentId the tradePaymentId to set
	 */
	public void setTradePaymentId(Integer tradePaymentId) {
		this.tradePaymentId = tradePaymentId;
	}
}
