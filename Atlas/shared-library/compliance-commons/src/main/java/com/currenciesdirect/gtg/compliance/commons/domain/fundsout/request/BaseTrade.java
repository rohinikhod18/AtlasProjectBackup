package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.util.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"trade_account_number",
	"value_date",
	"buying_amount_base_value",
})
public class BaseTrade  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "trading account number", required = true)
	@JsonProperty("trade_account_number")
	private String tradeAccountNumber;
	
	@ApiModelProperty(value = "trading value date", required = true)
	@JsonProperty("value_date")
	private String valueDate;
	
	@ApiModelProperty(value = "buying amount base value", example = "1477.75", required = true)
	@JsonProperty("buying_amount_base_value")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double buyingAmountBaseValue;
	

	/**
	 * 
	 * @return The tradeAccountNumber
	 */
	@JsonProperty("trade_account_number")
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * 
	 * @param tradeAccountNumber
	 *            The trade_account_number
	 */
	@JsonProperty("trade_account_number")
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}


	/**
	 * 
	 * @return The valueDate
	 */
	@JsonProperty("value_date")
	public String getValueDate() {
		return valueDate;
	}

	/**
	 * 
	 * @param valueDate
	 *            The value_date
	 */
	@JsonProperty("value_date")
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