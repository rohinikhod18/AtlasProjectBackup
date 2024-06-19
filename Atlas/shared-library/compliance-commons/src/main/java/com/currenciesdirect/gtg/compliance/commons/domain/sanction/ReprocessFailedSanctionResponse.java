package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

/**
 * The Class ReprocessFailedSanctionResponse.
 */
public class ReprocessFailedSanctionResponse {

	/** The repeat sanction count. */
	private Long repeatSanctionCount;

	/** The clear payment count. */
	private Long clearPaymentCount;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	/** The status. */
	private String status;

	/**
	 * Gets the repeat sanction count.
	 *
	 * @return the repeat sanction count
	 */
	public Long getRepeatSanctionCount() {
		return repeatSanctionCount;
	}

	/**
	 * Sets the repeat sanction count.
	 *
	 * @param repeatSanctionCount
	 *            the new repeat sanction count
	 */
	public void setRepeatSanctionCount(Long repeatSanctionCount) {
		this.repeatSanctionCount = repeatSanctionCount;
	}

	/**
	 * Gets the clear payment count.
	 *
	 * @return the clear payment count
	 */
	public Long getClearPaymentCount() {
		return clearPaymentCount;
	}

	/**
	 * Sets the clear payment count.
	 *
	 * @param clearPaymentCount
	 *            the new clear payment count
	 */
	public void setClearPaymentCount(Long clearPaymentCount) {
		this.clearPaymentCount = clearPaymentCount;
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
	 * @param errorCode
	 *            the new error code
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
	 * @param errorDescription
	 *            the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
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
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
