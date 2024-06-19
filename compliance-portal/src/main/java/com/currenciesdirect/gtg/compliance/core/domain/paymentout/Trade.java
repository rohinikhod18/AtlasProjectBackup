package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
	"selling_amount_base_value",
	"buying_amount",
	"purpose_of_trade",
	"maturity_date",
	"third_party_payment"
})
public class Trade  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("trade_account_number")
	private String tradeAccountNumber;
	@JsonProperty("cust_type")
	private String custType;
	@JsonProperty("trade_contact_id")
	private Integer tradeContactId;
	@JsonIgnore
	private String registrationDatetime;
	@JsonProperty("contract_number")
	private String contractNumber;
	@JsonProperty("deal_date")
	private String dealDate;
	@JsonProperty("value_date")
	private String valueDate;
	@JsonProperty("buy_currency")
	private String buyCurrency;
	@JsonProperty("sell_currency")
	private String sellCurrency;
	@JsonProperty("selling_amount")
	private Double sellingAmount;
	@JsonProperty("selling_amount_base_value")
	private Double sellingAmountBaseValue;
	@JsonProperty("buying_amount")
	private Double buyingAmount;
	@JsonProperty("purpose_of_trade")
	private String purposeOfTrade;
	@JsonProperty("maturity_date")
	private String maturityDate;
	
	@JsonProperty("third_party_payment")
	private Boolean thirdPartyPayment;

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
	 * @return The custType
	 */
	@JsonProperty("cust_type")
	public String getCustType() {
		return custType;
	}

	/**
	 * 
	 * @param custType
	 *            The cust_type
	 */
	@JsonProperty("cust_type")
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * 
	 * @return The tradeContactId
	 */
	@JsonProperty("trade_contact_id")
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * 
	 * @param tradeContactId
	 *            The trade_contact_id
	 */
	@JsonProperty("trade_contact_id")
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	/**
	 * 
	 * @return The registrationDatetime
	 */
	@JsonIgnore
	public String getRegistrationDatetime() {
		return registrationDatetime;
	}

	/**
	 * 
	 * @param registrationDatetime
	 *            The registration_datetime
	 */
	@JsonIgnore
	public void setRegistrationDatetime(String registrationDatetime) {
		this.registrationDatetime = registrationDatetime;
	}

	/**
	 * 
	 * @return The contractNumber
	 */
	@JsonProperty("contract_number")
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * 
	 * @param contractNumber
	 *            The contract_number
	 */
	@JsonProperty("contract_number")
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * 
	 * @return The dealDate
	 */
	@JsonProperty("deal_date")
	public String getDealDate() {
		return dealDate;
	}

	/**
	 * 
	 * @param dealDate
	 *            The deal_date
	 */
	@JsonProperty("deal_date")
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
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
	 * @return the buyCurrency
	 */
	public String getBuyCurrency() {
		return buyCurrency;
	}

	/**
	 * @param buyCurrency the buyCurrency to set
	 */
	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	/**
	 * @return the sellCurrency
	 */
	public String getSellCurrency() {
		return sellCurrency;
	}

	/**
	 * @param sellCurrency the sellCurrency to set
	 */
	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	/**
	 * 
	 * @return The sellingAmount
	 */
	@JsonProperty("selling_amount")
	public Double getSellingAmount() {
		return sellingAmount;
	}

	/**
	 * 
	 * @param sellingAmount
	 *            The selling_amount
	 */
	@JsonProperty("selling_amount")
	public void setSellingAmount(Double sellingAmount) {
		this.sellingAmount = sellingAmount;
	}

	/**
	 * 
	 * @return The buyingAmount
	 */
	@JsonProperty("buying_amount")
	public Double getBuyingAmount() {
		return buyingAmount;
	}

	/**
	 * 
	 * @param buyingAmount
	 *            The buying_amount
	 */
	@JsonProperty("buying_amount")
	public void setBuyingAmount(Double buyingAmount) {
		this.buyingAmount = buyingAmount;
	}

	/**
	 * 
	 * @return The purposeOfTrade
	 */
	@JsonProperty("purpose_of_trade")
	public String getPurposeOfTrade() {
		return purposeOfTrade;
	}

	/**
	 * 
	 * @param purposeOfTrade
	 *            The purpose_of_trade
	 */
	@JsonProperty("purpose_of_trade")
	public void setPurposeOfTrade(String purposeOfTrade) {
		this.purposeOfTrade = purposeOfTrade;
	}

	/**
	 * 
	 * @return The maturityDate
	 */
	@JsonProperty("maturity_date")
	public String getMaturityDate() {
		return maturityDate;
	}

	/**
	 * 
	 * @param maturityDate
	 *            The maturity_date
	 */
	@JsonProperty("maturity_date")
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * @return the thirdPartyPayment
	 */
	public Boolean getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	/**
	 * @param thirdPartyPayment the thirdPartyPayment to set
	 */
	public void setThirdPartyPayment(Boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	/**
	 * @return the sellingAmountBaseValue
	 */
	public Double getSellingAmountBaseValue() {
		return sellingAmountBaseValue;
	}

	/**
	 * @param sellingAmountBaseValue the sellingAmountBaseValue to set
	 */
	public void setSellingAmountBaseValue(Double sellingAmountBaseValue) {
		this.sellingAmountBaseValue = sellingAmountBaseValue;
	}

}