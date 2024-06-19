package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class CustomUpdateResponse.
 */
public class CustomUpdateResponse {

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	/** The org code. */
	private String orgCode;

	/** The acc id. */
	private Integer accId;

	/** The payment trans id. */
	private Integer paymentTransId;

	/** The overall status. */
	private String overallStatus;

	/** The checked on. */
	private String checkedOn;

	/** The is country whitelisted. */
	private boolean isCountryWhitelisted;

	/** The is country whitelisted for funds in. */
	private boolean isCountryWhitelistedForFundsIn;

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
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the acc id.
	 *
	 * @return the acc id
	 */
	public Integer getAccId() {
		return accId;
	}

	/**
	 * Sets the acc id.
	 *
	 * @param accId
	 *            the new acc id
	 */
	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	/**
	 * Gets the payment trans id.
	 *
	 * @return the payment trans id
	 */
	public Integer getPaymentTransId() {
		return paymentTransId;
	}

	/**
	 * Sets the payment trans id.
	 *
	 * @param paymentTransId
	 *            the new payment trans id
	 */
	public void setPaymentTransId(Integer paymentTransId) {
		this.paymentTransId = paymentTransId;
	}

	/**
	 * Gets the overall status.
	 *
	 * @return the overall status
	 */
	public String getOverallStatus() {
		return overallStatus;
	}

	/**
	 * Sets the overall status.
	 *
	 * @param overallStatus
	 *            the new overall status
	 */
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	/**
	 * Gets the checked on.
	 *
	 * @return the checked on
	 */
	public String getCheckedOn() {
		return checkedOn;
	}

	/**
	 * Sets the checked on.
	 *
	 * @param checkedOn
	 *            the new checked on
	 */
	public void setCheckedOn(String checkedOn) {
		this.checkedOn = checkedOn;
	}

	/**
	 * Checks if is country whitelisted.
	 *
	 * @return true, if is country whitelisted
	 */
	public boolean isCountryWhitelisted() {
		return isCountryWhitelisted;
	}

	/**
	 * Sets the country whitelisted.
	 *
	 * @param isCountryWhitelisted
	 *            the new country whitelisted
	 */
	public void setCountryWhitelisted(boolean isCountryWhitelisted) {
		this.isCountryWhitelisted = isCountryWhitelisted;
	}

	/**
	 * Checks if is country whitelisted for funds in.
	 *
	 * @return true, if is country whitelisted for funds in
	 */
	public boolean isCountryWhitelistedForFundsIn() {
		return isCountryWhitelistedForFundsIn;
	}

	/**
	 * Sets the country whitelisted for funds in.
	 *
	 * @param isCountryWhitelistedForFundsIn
	 *            the new country whitelisted for funds in
	 */
	public void setCountryWhitelistedForFundsIn(boolean isCountryWhitelistedForFundsIn) {
		this.isCountryWhitelistedForFundsIn = isCountryWhitelistedForFundsIn;
	}

}
