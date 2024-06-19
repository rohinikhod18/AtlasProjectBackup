package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;
import java.util.List;


/**
 * The Class BlacklistPayrefContactResponse.
 */
public class BlacklistPayrefContactResponse implements Serializable 
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	private String status;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;
	
	/** The payment reference. */
	private String paymentReference;
	
	/** The request id. */
	private String requestId;
	
	/** The sanction text. */
	private String sanctionText;
	
	/** The token set ratio. */
	private int tokenSetRatio;

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
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the error description
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
