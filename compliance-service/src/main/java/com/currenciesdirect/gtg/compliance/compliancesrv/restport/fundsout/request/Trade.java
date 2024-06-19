package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsout.request;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.compliancesrv.util.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"trade_account_number",
	"cust_type",
	"trade_contact_id",
	"contract_number",
	"deal_date",
	"value_date",
	"currency_pair",
	"selling_amount",
	"buying_amount_base_value",
	"buying_amount",
	"purpose_of_trade",
	"maturity_date"
})
public class Trade  implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The trade account number. */
	@JsonProperty("trade_account_number")
	private String tradeAccountNumber;
	
	/** The cust type. */
	@JsonProperty("cust_type")
	private String custType;
	
	/** The trade contact id. */
	@JsonProperty("trade_contact_id")
	private Integer tradeContactId;
	
	/** The registration datetime. */
	@JsonIgnore
	private String registrationDatetime;
	
	/** The contract number. */
	@JsonProperty("contract_number")
	private String contractNumber;
	
	/** The deal date. */
	@JsonProperty("deal_date")
	private String dealDate;
	
	/** The value date. */
	@JsonProperty("value_date")
	private String valueDate;
	
	/** The buy currency. */
	@JsonProperty("buy_currency")
	private String buyCurrency;
	
	/** The sell currency. */
	@JsonProperty("sell_currency")
	private String sellCurrency;
	
	/** The selling amount. */
	@JsonProperty("selling_amount")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double sellingAmount;
	
	/** The buying amount GBP value. */
	@JsonProperty("buying_amount_base_value")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double buyingAmountBaseValue;
	
	/** The buying amount. */
	@JsonProperty("buying_amount")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double buyingAmount;
	
	/** The purpose of trade. */
	@JsonProperty("purpose_of_trade")
	private String purposeOfTrade;
	
	/** The maturity date. */
	@JsonProperty("maturity_date")
	private String maturityDate;
	/** The third party payment. */
	@JsonProperty("third_party_payment")
	private Boolean thirdPartyPayment;
	
	/** The third party eligible. */
	@JsonProperty("third_party_eligible")
	private Boolean thirdPartyEligible;
	
	/** The dealer name. */
	@JsonProperty("dealer_name")
	private String dealerName;


	/**
	 * Gets the trade account number.
	 *
	 * @return The tradeAccountNumber
	 */
	@JsonProperty("trade_account_number")
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber            The trade_account_number
	 */
	@JsonProperty("trade_account_number")
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return The custType
	 */
	@JsonProperty("cust_type")
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType            The cust_type
	 */
	@JsonProperty("cust_type")
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the trade contact id.
	 *
	 * @return The tradeContactId
	 */
	@JsonProperty("trade_contact_id")
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * Sets the trade contact id.
	 *
	 * @param tradeContactId            The trade_contact_id
	 */
	@JsonProperty("trade_contact_id")
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	/**
	 * Gets the registration datetime.
	 *
	 * @return The registrationDatetime
	 */
	@JsonIgnore
	public String getRegistrationDatetime() {
		return registrationDatetime;
	}

	/**
	 * Sets the registration datetime.
	 *
	 * @param registrationDatetime            The registration_datetime
	 */
	@JsonIgnore
	public void setRegistrationDatetime(String registrationDatetime) {
		this.registrationDatetime = registrationDatetime;
	}

	/**
	 * Gets the contract number.
	 *
	 * @return The contractNumber
	 */
	@JsonProperty("contract_number")
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * Sets the contract number.
	 *
	 * @param contractNumber            The contract_number
	 */
	@JsonProperty("contract_number")
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * Gets the deal date.
	 *
	 * @return The dealDate
	 */
	@JsonProperty("deal_date")
	public String getDealDate() {
		return dealDate;
	}

	/**
	 * Sets the deal date.
	 *
	 * @param dealDate            The deal_date
	 */
	@JsonProperty("deal_date")
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	/**
	 * Gets the value date.
	 *
	 * @return The valueDate
	 */
	@JsonProperty("value_date")
	public String getValueDate() {
		return valueDate;
	}

	/**
	 * Sets the value date.
	 *
	 * @param valueDate            The value_date
	 */
	@JsonProperty("value_date")
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	/**
	 * Gets the buy currency.
	 *
	 * @return the buyCurrency
	 */
	public String getBuyCurrency() {
		return buyCurrency;
	}

	/**
	 * Sets the buy currency.
	 *
	 * @param buyCurrency the buyCurrency to set
	 */
	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	/**
	 * Gets the sell currency.
	 *
	 * @return the sellCurrency
	 */
	public String getSellCurrency() {
		return sellCurrency;
	}

	/**
	 * Sets the sell currency.
	 *
	 * @param sellCurrency the sellCurrency to set
	 */
	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	/**
	 * Gets the selling amount.
	 *
	 * @return The sellingAmount
	 */
	@JsonProperty("selling_amount")
	public Double getSellingAmount() {
		return sellingAmount;
	}

	/**
	 * Sets the selling amount.
	 *
	 * @param sellingAmount            The selling_amount
	 */
	@JsonProperty("selling_amount")
	public void setSellingAmount(Double sellingAmount) {
		this.sellingAmount = sellingAmount;
	}

	/**
	 * Gets the buying amount.
	 *
	 * @return The buyingAmount
	 */
	@JsonProperty("buying_amount")
	public Double getBuyingAmount() {
		return buyingAmount;
	}

	/**
	 * Sets the buying amount.
	 *
	 * @param buyingAmount            The buying_amount
	 */
	@JsonProperty("buying_amount")
	public void setBuyingAmount(Double buyingAmount) {
		this.buyingAmount = buyingAmount;
	}

	/**
	 * Gets the purpose of trade.
	 *
	 * @return The purposeOfTrade
	 */
	@JsonProperty("purpose_of_trade")
	public String getPurposeOfTrade() {
		return purposeOfTrade;
	}

	/**
	 * Sets the purpose of trade.
	 *
	 * @param purposeOfTrade            The purpose_of_trade
	 */
	@JsonProperty("purpose_of_trade")
	public void setPurposeOfTrade(String purposeOfTrade) {
		this.purposeOfTrade = purposeOfTrade;
	}

	/**
	 * Gets the maturity date.
	 *
	 * @return The maturityDate
	 */
	@JsonProperty("maturity_date")
	public String getMaturityDate() {
		return maturityDate;
	}

	/**
	 * Sets the maturity date.
	 *
	 * @param maturityDate            The maturity_date
	 */
	@JsonProperty("maturity_date")
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * Gets the third party payment.
	 *
	 * @return the thirdPartyPayment
	 */
	public Boolean getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	/**
	 * Sets the third party payment.
	 *
	 * @param thirdPartyPayment the thirdPartyPayment to set
	 */
	public void setThirdPartyPayment(Boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	/**
	 * Gets the third party eligible.
	 *
	 * @return the third party eligible
	 */
	public Boolean getThirdPartyEligible() {
		return thirdPartyEligible;
	}

	/**
	 * Sets the third party eligible.
	 *
	 * @param thirdPartyEligible the new third party eligible
	 */
	public void setThirdPartyEligible(Boolean thirdPartyEligible) {
		this.thirdPartyEligible = thirdPartyEligible;
	}

	/**
	 * Gets the dealer name.
	 *
	 * @return the dealer name
	 */
	public String getDealerName() {
		return dealerName;
	}

	/**
	 * Sets the dealer name.
	 *
	 * @param dealerName the new dealer name
	 */
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
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