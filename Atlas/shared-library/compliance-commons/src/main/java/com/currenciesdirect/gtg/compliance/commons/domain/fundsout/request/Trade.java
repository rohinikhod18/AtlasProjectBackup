package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.util.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class Trade.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"cust_type",
	"trade_contact_id",
	"contract_number",
	"deal_date",
	
	"currency_pair",
	"selling_amount",
	
	"buying_amount",
	"purpose_of_trade",
	"maturity_date",
	
	"payment_ref",
	"bac_remitter_name",
	"customer_instruction_number",
	"initiating_parent_contact",
	"credit_from"
	
})
public class Trade extends BaseTrade  implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The customer type. */
	@ApiModelProperty(value = "The customer type (PFX/CFX)", required = true, example = "PFX")
	@JsonProperty("cust_type")
	private String custType;
	
	/** The trade contact id. */
	@ApiModelProperty(value = "The trade contact ID", example = "751843", required = true)
	@JsonProperty("trade_contact_id")
	private Integer tradeContactId;
	
	/** The registration date time. */
	@JsonIgnore
	private String registrationDatetime;
	
	/** The contract number. */
	@ApiModelProperty(value = "trading identification number", required = true)
	@JsonProperty("contract_number")
	private String contractNumber;
	
	/** The deal date. */
	@ApiModelProperty(value = "deal date", required = true)
	@JsonProperty("deal_date")
	private String dealDate;
	
	/** The buy currency. */
	@ApiModelProperty(value = "buying currency", required = true)
	@JsonProperty("buy_currency")
	private String buyCurrency;
	
	/** The sell currency. */
	@ApiModelProperty(value = "selling currency", required = true)
	@JsonProperty("sell_currency")
	private String sellCurrency;
	
	/** The selling amount. */
	@ApiModelProperty(value = "selling currency amount", required = true)
	@JsonProperty("selling_amount")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double sellingAmount;
	
	/** The buying amount. */
	@ApiModelProperty(value = "buying currency amount", required = true)
	@JsonProperty("buying_amount")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double buyingAmount;
	
	/** The purpose of trade. */
	@ApiModelProperty(value = "purpose of trade", required = true)
	@JsonProperty("purpose_of_trade")
	private String purposeOfTrade;
	
	/** The maturity date. */
	@ApiModelProperty(value = "funds out actual date", required = true)
	@JsonProperty("maturity_date")
	private String maturityDate;
	
	/** The payment ref. */
	@ApiModelProperty(value = "trade reference id", required = true)
	@JsonProperty("payment_ref")
	private String paymentRef;
	
	/** The third party payment. */
	@ApiModelProperty(value = "is third party payment", required = true)
	@JsonProperty("third_party_payment")
	private Boolean thirdPartyPayment;
	
	/** The third party eligible. */
	@JsonProperty("third_party_eligible")
	private Boolean thirdPartyEligible;
	
	/** The dealer name. */
	@JsonProperty("dealer_name")
	private String dealerName;
	
	/** The customer instruction number. */
	@JsonProperty("customer_instruction_number")
	private String customerInstructionNumber;
	
	/** The customer legal entity. */
	@JsonProperty(value = "customer_legal_entity")
	private String custLegalEntity;
	
	//AT-5337
	@JsonProperty(value = "initiating_parent_contact")
	private String initiatingParentContact;
	
	/** The credit from. */
	//AT-5435
	@JsonProperty(value = "credit_from")
	private String creditFrom;
	

	/**
	 * Gets the payment ref.
	 *
	 * @return the payment ref
	 */
	public String getPaymentRef() {
		return paymentRef;
	}

	/**
	 * Sets the payment ref.
	 *
	 * @param paymentRef the new payment ref
	 */
	public void setPaymentRef(String paymentRef) {
		this.paymentRef = paymentRef;
	}
	
	/** The bac remitter name. */
	@ApiModelProperty(value = "name of bac remitter", required = true)
	@JsonProperty("bac_remitter_name")
	private String bacRemitterName;

	/**
	 * Gets the bac remitter name.
	 *
	 * @return the bac remitter name
	 */
	public String getBacRemitterName() {
		return bacRemitterName;
	}

	/**
	 * Sets the bac remitter name.
	 *
	 * @param bacRemitterName the new bac remitter name
	 */
	public void setBacRemitterName(String bacRemitterName) {
		this.bacRemitterName = bacRemitterName;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return the custType
	 */
	@JsonProperty("cust_type")
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType the custType to set
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
	 * Gets the customer instruction number.
	 *
	 * @return the customer instruction number
	 */
	@JsonProperty("customer_instruction_number")
	public String getCustomerInstructionNumber() {
		return customerInstructionNumber;
	}

	/**
	 * Sets the customer instruction number.
	 *
	 * @param customerInstructionNumber the new customer instruction number
	 */
	public void setCustomerInstructionNumber(String customerInstructionNumber) {
		this.customerInstructionNumber = customerInstructionNumber;
	}

	/**
	 * Gets the cust legal entity.
	 *
	 * @return the cust legal entity
	 */
	public String getCustLegalEntity() {
		return custLegalEntity;
	}

	/**
	 * Sets the cust legal entity.
	 *
	 * @param custLegalEntity the new cust legal entity
	 */
	public void setCustLegalEntity(String custLegalEntity) {
		this.custLegalEntity = custLegalEntity;
	}

	/**
	 * @return the initiatingParentContact
	 */
	public String getInitiatingParentContact() {
		return initiatingParentContact;
	}

	/**
	 * @param initiatingParentContact the initiatingParentContact to set
	 */
	public void setInitiatingParentContact(String initiatingParentContact) {
		this.initiatingParentContact = initiatingParentContact;
	}

	/**
	 * Gets the credit from.
	 *
	 * @return the credit from
	 */
	public String getCreditFrom() {
		return creditFrom;
	}

	/**
	 * Sets the credit from.
	 *
	 * @param creditFrom the new credit from
	 */
	public void setCreditFrom(String creditFrom) {
		this.creditFrom = creditFrom;
	}
	
	

}