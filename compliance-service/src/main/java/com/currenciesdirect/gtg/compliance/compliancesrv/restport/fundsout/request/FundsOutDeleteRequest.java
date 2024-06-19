package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsout.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"org_code",
	"source_application",
	"trade_account_number",
	"payment_fundsout_id",
})
public class FundsOutDeleteRequest extends FundsOutBaseRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "The trade account number", required = true)
	@JsonProperty("trade_account_number")
	private String tradeAccountNumber;
	
	@ApiModelProperty(value = "beneficiary funds out id", example = "3192830", required = true)
	@JsonProperty("payment_fundsout_id")
	private Integer paymentFundsoutId;
	
	
	/**
	 * @return the tradeAccountNumber
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * @param tradeAccountNumber the tradeAccountNumber to set
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * 
	 * @return The paymentFundsoutId
	 */
	@Override
	@JsonProperty("payment_fundsout_id")
	public Integer getPaymentFundsoutId() {
		return paymentFundsoutId;
	}

	/**
	 * 
	 * @param paymentFundsoutId
	 *            The payment_fundsout_id
	 */
	@JsonProperty("payment_fundsout_id")
	public void setPaymentFundsoutId(Integer paymentFundsoutId) {
		this.paymentFundsoutId = paymentFundsoutId;
	}
	
	
}

