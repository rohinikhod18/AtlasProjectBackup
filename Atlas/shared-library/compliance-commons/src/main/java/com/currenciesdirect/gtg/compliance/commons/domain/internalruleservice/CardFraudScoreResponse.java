/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class CardFraudScoreResponse.
 *
 * @author manish
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardFraudScoreResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	private String status;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;
	
	/** The t score. */
	private Double tScore;

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
	 * @param status
	 *            the new status
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
	 * Gets the error description.
	 *
	 * @return the error description
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode
	 *            the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription
	 *            the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * Gets the t score.
	 *
	 * @return the t score
	 */
	public Double gettScore() {
		return tScore;
	}

	/**
	 * Sets the t score.
	 *
	 * @param tScore the new t score
	 */
	public void settScore(Double tScore) {
		this.tScore = tScore;
	}
}
