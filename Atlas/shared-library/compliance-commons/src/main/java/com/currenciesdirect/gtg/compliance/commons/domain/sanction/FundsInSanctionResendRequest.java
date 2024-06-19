package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInBaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FundsInSanctionResendRequest.
 */
public class FundsInSanctionResendRequest extends FundsInBaseRequest {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The account id. */
	@ApiModelProperty(value = "The account id", required = true)
	private Integer accountId;

	/** The payment in id. */
	@ApiModelProperty(value = "The payment in id", required = true)
	private Integer paymentInId;

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
	 * Gets the payment in id.
	 *
	 * @return the payment in id
	 */
	public Integer getPaymentInId() {
		return paymentInId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.
	 * FundsInBaseRequest#getTradeAccountNumber()
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the payment in id.
	 *
	 * @param paymentInId
	 *            the new payment in id
	 */
	public void setPaymentInId(Integer paymentInId) {
		this.paymentInId = paymentInId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.
	 * FundsInBaseRequest#getPaymentFundsInId()
	 */
	@Override
	public Integer getPaymentFundsInId() {
		return this.paymentInId;
	}
}
