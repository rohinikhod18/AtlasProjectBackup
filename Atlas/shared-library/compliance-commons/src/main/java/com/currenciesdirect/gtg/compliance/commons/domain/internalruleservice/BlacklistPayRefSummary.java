package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class BlacklistPayRefSummary implements Serializable{

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The status. */
	@ApiModelProperty(value = "The status", example = "", required = true)
	private String status;
	
	/** The payment reference. */
	@ApiModelProperty(value = "The paymentReference", example = "", required = true)
	private String paymentReference;
	
	/** The request id. */
	@ApiModelProperty(value = "The requestId", example = "", required = true)
	private String requestId;
	
	/** The sanction text. */
	@ApiModelProperty(value = "The sanctionText", example = "", required = true)
	private String sanctionText;
	
	/** The token set ratio. */
	@ApiModelProperty(value = "The tokenSetRatio", example = "", required = true)
	private int tokenSetRatio;
	
	

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
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the payment reference.
	 *
	 * @return the payment reference
	 */
	public String getPaymentReference() {
		return paymentReference;
	}

	/**
	 * Sets the payment reference.
	 *
	 * @param paymentReference the new payment reference
	 */
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	/**
	 * Gets the request id.
	 *
	 * @return the request id
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * Sets the request id.
	 *
	 * @param requestId the new request id
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * Gets the sanction text.
	 *
	 * @return the sanction text
	 */
	public String getSanctionText() {
		return sanctionText;
	}

	/**
	 * Sets the sanction text.
	 *
	 * @param sanctionText the new sanction text
	 */
	public void setSanctionText(String sanctionText) {
		this.sanctionText = sanctionText;
	}

	/**
	 * Gets the token set ratio.
	 *
	 * @return the token set ratio
	 */
	public int getTokenSetRatio() {
		return tokenSetRatio;
	}

	/**
	 * Sets the token set ratio.
	 *
	 * @param tokenSetRatio the new token set ratio
	 */
	public void setTokenSetRatio(int tokenSetRatio) {
		this.tokenSetRatio = tokenSetRatio;
	}

	
	
	
}
