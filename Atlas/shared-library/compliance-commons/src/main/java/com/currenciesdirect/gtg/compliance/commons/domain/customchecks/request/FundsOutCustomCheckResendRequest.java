package com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest;

/**
 * The Class CustomCheckResendRequest.
 */
public class FundsOutCustomCheckResendRequest extends FundsOutBaseRequest{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The payment out id. */
	private Integer paymentOutId;
	
	/** The payment in id. */
	private Integer paymentInId;
	
	/** The trade account number. */
	private String tradeAccountNumber;
	
	/** The trade contact id. */
	private Integer tradeContactId;
	
	/** The trade payment id. */
	private Integer tradePaymentId;
	
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

	/**
	 * Gets the payment fundsout id.
	 *
	 * @return the payment fundsout id
	 */
	public  Integer getPaymentFundsoutId()
	{
		return this.paymentOutId;
	}
}
