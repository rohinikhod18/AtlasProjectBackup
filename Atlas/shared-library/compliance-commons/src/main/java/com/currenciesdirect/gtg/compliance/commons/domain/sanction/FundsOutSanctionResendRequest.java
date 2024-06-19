package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FundsOutSanctionResendRequest.
 */
public class FundsOutSanctionResendRequest extends FundsOutBaseRequest {

	/** The Constant serialVersionUID. */
	@ApiModelProperty(value = "The Constant serialVersionUID", required = true)
	private static final long serialVersionUID = 1L;

	/** The account id. */
	@ApiModelProperty(value = "The account id", required = true)
	private Integer accountId;

	/** The payment out id. */
	@ApiModelProperty(value = "The payment out id", required = true)
	private Integer paymentOutId;

	/** The trade account number. */
	@ApiModelProperty(value = "The trade account number", required = true)
	private String tradeAccountNumber;

	/** The trade contact id. */
	@ApiModelProperty(value = "The trade contact id", required = true)
	private Integer tradeContactId;

	/** The trade payment id. */
	@ApiModelProperty(value = "The trade payment id", required = true)
	private Integer tradePaymentId;

	/** The event id. */
	@ApiModelProperty(value = "The event id", required = true)
	private Integer eventId;

	/** The event type. */
	@ApiModelProperty(value = "The event type", required = true)
	private String eventType;

	/** The entity id. */
	@ApiModelProperty(value = "The entity id", required = true)
	private Integer entityId;

	/** The entity type. */
	@ApiModelProperty(value = "The entity type", required = true)
	private String entityType;

	/** The contact status. */
	@ApiModelProperty(value = "The contact status", required = true)
	private String contactStatus;

	/** The bank status. */
	@ApiModelProperty(value = "The bank status", required = true)
	private String bankStatus;

	/** The beneficiary status. */
	@ApiModelProperty(value = "The beneficiary status", required = true)
	private String beneficiaryStatus;

	/** The trade contract number. */
	@ApiModelProperty(value = "The trade contract number", required = true)
	private String tradeContractNumber;

	/** The contact ID. */
	@ApiModelProperty(value = "The contact ID", required = true)
	private Integer contactID;

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Gets the event id.
	 *
	 * @return the event id
	 */
	public Integer getEventId() {
		return eventId;
	}

	/**
	 * Gets the event type.
	 *
	 * @return the event type
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
	public Integer getEntityId() {
		return entityId;
	}

	/**
	 * Gets the entity type.
	 *
	 * @return the entity type
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Sets the event id.
	 *
	 * @param eventId
	 *            the new event id
	 */
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	/**
	 * Sets the event type.
	 *
	 * @param eventType
	 *            the new event type
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * Sets the entity id.
	 *
	 * @param entityId
	 *            the new entity id
	 */
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	/**
	 * Sets the entity type.
	 *
	 * @param entityType
	 *            the new entity type
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * Gets the payment out id.
	 *
	 * @return the payment out id
	 */
	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	/**
	 * Gets the trade account number.
	 *
	 * @return the trade account number
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the payment out id.
	 *
	 * @param paymentOutId
	 *            the new payment out id
	 */
	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber
	 *            the new trade account number
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * Gets the trade contact id.
	 *
	 * @return the trade contact id
	 */
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * Sets the trade contact id.
	 *
	 * @param tradeContactId
	 *            the new trade contact id
	 */
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
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
	 * @param tradePaymentId
	 *            the new trade payment id
	 */
	public void setTradePaymentId(Integer tradePaymentId) {
		this.tradePaymentId = tradePaymentId;
	}

	/**
	 * Gets the contact status.
	 *
	 * @return the contact status
	 */
	public String getContactStatus() {
		return contactStatus;
	}

	/**
	 * Sets the contact status.
	 *
	 * @param contactStatus
	 *            the new contact status
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

	/**
	 * Gets the bank status.
	 *
	 * @return the bank status
	 */
	public String getBankStatus() {
		return bankStatus;
	}

	/**
	 * Sets the bank status.
	 *
	 * @param bankStatus
	 *            the new bank status
	 */
	public void setBankStatus(String bankStatus) {
		this.bankStatus = bankStatus;
	}

	/**
	 * Gets the beneficiary status.
	 *
	 * @return the beneficiary status
	 */
	public String getBeneficiaryStatus() {
		return beneficiaryStatus;
	}

	/**
	 * Sets the beneficiary status.
	 *
	 * @param beneficiaryStatus
	 *            the new beneficiary status
	 */
	public void setBeneficiaryStatus(String beneficiaryStatus) {
		this.beneficiaryStatus = beneficiaryStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.
	 * FundsOutBaseRequest#getPaymentFundsoutId()
	 */
	public Integer getPaymentFundsoutId() {
		return this.paymentOutId;
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
	 * Sets the trade contract number.
	 *
	 * @param tradeContractNumber
	 *            the tradeContractNumber to set
	 */
	public void setTradeContractNumber(String tradeContractNumber) {
		this.tradeContractNumber = tradeContractNumber;
	}

	/**
	 * Gets the contact ID.
	 *
	 * @return the contactID
	 */
	public Integer getContactID() {
		return contactID;
	}

	/**
	 * Sets the contact ID.
	 *
	 * @param contactID
	 *            the contactID to set
	 */
	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}


}
