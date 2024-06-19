package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;


/**
 * The Class FundsOutDeleteRequest.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"org_code",
	"source_application",
	"trade_account_number",
	"payment_fundsout_id",
})
public class FundsOutDeleteRequest extends FundsOutBaseRequest{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The trade account number. */
	@ApiModelProperty(value = "Trading account number", required = true)
	@JsonProperty("trade_account_number")
	private String tradeAccountNumber;
	
	/** The payment fundsout id. */
	@ApiModelProperty(value = "beneficiary funds out id", example = "3192830", required = true)
	@JsonProperty("payment_fundsout_id")
	private Integer paymentFundsoutId;
	
	
	/**
	 * Gets the trade account number.
	 *
	 * @return the tradeAccountNumber
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber the tradeAccountNumber to set
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * Gets the payment fundsout id.
	 *
	 * @return The paymentFundsoutId
	 */
	@Override
	@JsonProperty("payment_fundsout_id")
	public Integer getPaymentFundsoutId() {
		return paymentFundsoutId;
	}

	/**
	 * Sets the payment fundsout id.
	 *
	 * @param paymentFundsoutId            The payment_fundsout_id
	 */
	@JsonProperty("payment_fundsout_id")
	public void setPaymentFundsoutId(Integer paymentFundsoutId) {
		this.paymentFundsoutId = paymentFundsoutId;
	}
	
}

