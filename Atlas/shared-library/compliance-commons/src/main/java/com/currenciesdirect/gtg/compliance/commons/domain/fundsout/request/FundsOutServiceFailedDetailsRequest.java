package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

public class FundsOutServiceFailedDetailsRequest extends ServiceMessage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	/** The trans id. */
	private int transId;

	/**
	 * @return the paymentOutId
	 */
	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	/**
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * @return the tradeContractNumber
	 */
	public String getTradeContractNumber() {
		return tradeContractNumber;
	}

	/**
	 * @return the tradePaymentId
	 */
	public Integer getTradePaymentId() {
		return tradePaymentId;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @return the orgId
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * @return the contactId
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * @return the complianceStatus
	 */
	public String getComplianceStatus() {
		return complianceStatus;
	}

	/**
	 * @param paymentOutId the paymentOutId to set
	 */
	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @param tradeContractNumber the tradeContractNumber to set
	 */
	public void setTradeContractNumber(String tradeContractNumber) {
		this.tradeContractNumber = tradeContractNumber;
	}

	/**
	 * @param tradePaymentId the tradePaymentId to set
	 */
	public void setTradePaymentId(Integer tradePaymentId) {
		this.tradePaymentId = tradePaymentId;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * @param complianceStatus the complianceStatus to set
	 */
	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}

	/**
	 * @return the transId
	 */
	public int getTransId() {
		return transId;
	}

	/**
	 * @param transId the transId to set
	 */
	public void setTransId(int transId) {
		this.transId = transId;
	}
}
