package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInBaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FundsInBlacklistResendRequest.
 */
public class FundsInBlacklistResendRequest extends FundsInBaseRequest{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The trade account number. */
	@ApiModelProperty(value = "The trade account number", required = true)
	private String tradeAccountNumber;
	
	/** The payment funds in id. */
	@ApiModelProperty(value = "The payment funds in id", required = true)
	private Integer paymentFundsInId;

	
        /* (non-Javadoc)
		 * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInBaseRequest#getPaymentFundsInId()
		 * 
		 * 
		 */
		@Override
		public Integer getPaymentFundsInId() {
			return paymentFundsInId;
		}

		/* (non-Javadoc)
		 * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInBaseRequest#getTradeAccountNumber()
		 */
		@Override
		public String getTradeAccountNumber() {
			return tradeAccountNumber;
		}

		/**
		 * Sets the payment funds in id.
		 *
		 * @param paymentFundsInId the new payment funds in id
		 */
		public void setPaymentFundsInId(Integer paymentFundsInId) {
			this.paymentFundsInId = paymentFundsInId;
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
