package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request;

import com.currenciesdirect.gtg.compliance.commons.util.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;


/**
 * The Class FundsOutUpdateRequest.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"org_code",
	"source_application",
	"trade_account_number",
	"payment_fundsout_id",
	"amount",
	"buying_amount_base_value",
	"value_date"
})
public class FundsOutUpdateRequest extends FundsOutBaseRequest{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The trade account number. */
	@ApiModelProperty(value = "trading account number", required = true)
	@JsonProperty("trade_account_number")
	private String tradeAccountNumber;
	
	/** The payment fundsout id. */
	@ApiModelProperty(value = "beneficiary funds out id", example = "3192830", required = true)
	@JsonProperty("payment_fundsout_id")
	private Integer paymentFundsoutId;
	
	/** The amount. */
	@ApiModelProperty(value = "funds out amount", required = true)
	@JsonProperty("amount")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double amount;
	
	/** The buying amount GBP value. */
	@ApiModelProperty(value = "buying amount base value", example = "1477.75", required = true)
	@JsonProperty("buying_amount_base_value")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double buyingAmountBaseValue;
	
	/** The value date. */
	@ApiModelProperty(value = "funds out update value date", required = true)
	@JsonProperty("value_date")
	private String valueDate;
	
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
	
	/**
	 * Gets the amount.
	 *
	 * @return The amount
	 */
	@JsonProperty("amount")
	public Double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount            The amount
	 */
	@JsonProperty("amount")
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the value date.
	 *
	 * @return the valueDate
	 */
	public String getValueDate() {
		return valueDate;
	}

	/**
	 * Sets the value date.
	 *
	 * @param valueDate the valueDate to set
	 */
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	/**
	 * @return the buyingAmountBaseValue
	 */
	public Double getBuyingAmountBaseValue() {
		return buyingAmountBaseValue;
	}

	/**
	 * @param buyingAmountBaseValue the buyingAmountBaseValue to set
	 */
	public void setBuyingAmountBaseValue(Double buyingAmountBaseValue) {
		this.buyingAmountBaseValue = buyingAmountBaseValue;
	}
}

