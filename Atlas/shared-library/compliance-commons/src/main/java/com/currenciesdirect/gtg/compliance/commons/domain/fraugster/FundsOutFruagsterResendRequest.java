package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FundsOutFruagsterResendRequest.
 */
public class FundsOutFruagsterResendRequest extends FundsOutBaseRequest{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The payment fundsout id. */
	@ApiModelProperty(value = "The beneficiary payment fundsout id", required = true)
	private Integer paymentFundsoutId;
	
	/** The trade account number. */
	@ApiModelProperty(value = "The trade account number", required = true)
	private String tradeAccountNumber;
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest#getPaymentFundsoutId()
	 * 
	 * 
	 */
	@Override
	public Integer getPaymentFundsoutId() {
		return paymentFundsoutId;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest#getTradeAccountNumber()
	 */
	@Override
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the payment fundsout id.
	 *
	 * @param paymentFundsoutId the paymentFundsoutId to set
	 */
	public void setPaymentFundsoutId(Integer paymentFundsoutId) {
		this.paymentFundsoutId = paymentFundsoutId;
	}
	

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber the tradeAccountNumber to set
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

}
