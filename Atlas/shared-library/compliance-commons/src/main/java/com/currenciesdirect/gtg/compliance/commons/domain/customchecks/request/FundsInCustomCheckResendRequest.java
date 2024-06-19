package com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInBaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class CustomCheckResendRequest.
 */
public class FundsInCustomCheckResendRequest extends FundsInBaseRequest{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The payment out id. */
	@ApiModelProperty(value = "beneficiary funds out id", required = true)
	private Integer paymentOutId;
	
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
	
	/**
	 * Gets the payment out id.
	 *
	 * @return the payment out id
	 */
	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInBaseRequest#getTradeAccountNumber()
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
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
	 * Gets the trade payment id.
	 *
	 * @return the trade payment id
	 */
	public Integer getTradePaymentId() {
		return tradePaymentId;
	}

	/**
	 * Sets the payment out id.
	 *
	 * @param paymentOutId the new payment out id
	 */
	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber the new trade account number
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * Sets the trade contact id.
	 *
	 * @param tradeContactId the new trade contact id
	 */
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
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

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInBaseRequest#getPaymentFundsInId()
	 */
	public Integer getPaymentFundsInId() {
		return this.paymentInId;
	}
}
