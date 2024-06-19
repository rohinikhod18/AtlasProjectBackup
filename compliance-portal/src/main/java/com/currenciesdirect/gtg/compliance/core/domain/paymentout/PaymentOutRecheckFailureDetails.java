package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

/**
 * The Class PaymentOutRecheckFailureDetails.
 */
public class PaymentOutRecheckFailureDetails {

	/** The payment out id. */
	private Integer paymentOutId;

	/** The created on. */
	private String createdOn;

	/** The trade contract number. */
	private String tradeContractNumber;

	/** The trade payment id. */
	private Integer tradePaymentId;

	/** The org code. */
	private String orgCode;

	/** The org id. */
	private Integer orgId;

	/** The account id. */
	private Integer accountId;

	/** The contact id. */
	private Integer contactId;

	/** The compliance status. */
	private String complianceStatus;

	

	/**
	 * Gets the payment out id.
	 *
	 * @return the paymentOutId
	 */
	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * Gets the trade contract number.
	 *
	 * @return the tradeContractNumber
	 */
	public String getTradeContractNumber() {
		return tradeContractNumber;
	}

	/**
	 * Gets the trade payment id.
	 *
	 * @return the tradePaymentId
	 */
	public Integer getTradePaymentId() {
		return tradePaymentId;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Gets the org id.
	 *
	 * @return the orgId
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contactId
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Gets the compliance status.
	 *
	 * @return the complianceStatus
	 */
	public String getComplianceStatus() {
		return complianceStatus;
	}

	/**
	 * Sets the payment out id.
	 *
	 * @param paymentOutId
	 *            the paymentOutId to set
	 */
	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn
	 *            the createdOn to set
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Sets the trade contract number.
	 *
	 * @param tradeContractNumber
	 *            the tradeContractNumber to set
	 */
	public void setTradeContractNumber(String tradeContractNumber) {
		this.tradeContractNumber = tradeContractNumber;
	}

	/**
	 * Sets the trade payment id.
	 *
	 * @param tradePaymentId
	 *            the new trade payment id
	 */
	public void setTradePaymentId(Integer tradePaymentId) {
		this.tradePaymentId = tradePaymentId;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Sets the org id.
	 *
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId
	 *            the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * Sets the compliance status.
	 *
	 * @param complianceStatus
	 *            the complianceStatus to set
	 */
	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}

}
