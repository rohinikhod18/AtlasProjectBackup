package com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

/**
 * The Class FundsInServiceFailedDetailsRequest.
 */
public class FundsInServiceFailedDetailsRequest extends ServiceMessage {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The payment in id. */
	private Integer paymentInId;
	
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
	 * Gets the payment in id.
	 *
	 * @return the payment in id
	 */
	public Integer getPaymentInId() {
		return paymentInId;
	}

	/**
	 * Sets the payment in id.
	 *
	 * @param paymentInId the new payment in id
	 */
	public void setPaymentInId(Integer paymentInId) {
		this.paymentInId = paymentInId;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Gets the trade contract number.
	 *
	 * @return the trade contract number
	 */
	public String getTradeContractNumber() {
		return tradeContractNumber;
	}

	/**
	 * Sets the trade contract number.
	 *
	 * @param tradeContractNumber the new trade contract number
	 */
	public void setTradeContractNumber(String tradeContractNumber) {
		this.tradeContractNumber = tradeContractNumber;
	}

	/**
	 * Gets the trade payment id.
	 *
	 * @return the trade payment id
	 */
	public Integer getTradePaymentId() {
		return tradePaymentId;
	}

	/**
	 * Sets the trade payment id.
	 *
	 * @param tradePaymentId the new trade payment id
	 */
	public void setTradePaymentId(Integer tradePaymentId) {
		this.tradePaymentId = tradePaymentId;
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
	 * @param orgCode the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the org id.
	 *
	 * @return the org id
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * Sets the org id.
	 *
	 * @param orgId the new org id
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * Gets the compliance status.
	 *
	 * @return the compliance status
	 */
	public String getComplianceStatus() {
		return complianceStatus;
	}

	/**
	 * Sets the compliance status.
	 *
	 * @param complianceStatus the new compliance status
	 */
	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}

}
