package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FundsOutReferenceResendRequest.
 */
public class FundsOutPaymentReferenceResendRequest extends FundsOutBaseRequest{
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The trade account number. */
	@ApiModelProperty(value = "The trade account number", required = true)
	@JsonProperty(value = "tradeAccountNumber")
	private String tradeAccountNumber;
	
	/** The beneficiary funds out id. */
	@ApiModelProperty(value = "beneficiary funds out id", required = true)
	@JsonProperty(value = "paymentFundsOutId")
	private Integer paymentFundsOutId;
	
	//added for AT-3658
	/** The payment reference. */
	@ApiModelProperty(value = "payment refernce", required = true)
	@JsonProperty(value = "payment_reference")
	private String paymentReference;
	
	/* (non-Javadoc)
	   * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest#getPaymentFundsoutId()
	   */
	@ApiModelProperty(value = "beneficiary funds out id", required = true)
	@Override
	public Integer getPaymentFundsoutId() {
		return paymentFundsOutId;
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
	 * @param paymentFundsOutId the new payment fundsout id
	 */
	public void setPaymentFundsoutId(Integer paymentFundsOutId) {
		this.paymentFundsOutId = paymentFundsOutId;
	}


	
    /**
	 * Sets the trade account number.
	 *
     * @param tradeAccountNumber the tradeAccountNumber to set
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}
	
	
	//added for AT-3658
	/**
	 * Sets the payment reference.
	 *
     * @param paymentReference the paymentReference to set
	 */
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}
	
	public String getPaymentReference() {
		return paymentReference;
	}
	
}
