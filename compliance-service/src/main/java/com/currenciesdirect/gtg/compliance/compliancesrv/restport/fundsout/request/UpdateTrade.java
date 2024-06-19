package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsout.request;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.compliancesrv.util.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"trade_account_number",
	"value_date",
	"buying_amount_base_value",
})
public class UpdateTrade  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("trade_account_number")
	private String tradeAccountNumber;
	@JsonProperty("value_date")
	private String valueDate;
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