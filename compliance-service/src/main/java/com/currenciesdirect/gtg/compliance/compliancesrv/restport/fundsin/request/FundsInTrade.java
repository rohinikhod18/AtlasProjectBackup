package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsin.request;

import com.currenciesdirect.gtg.compliance.compliancesrv.util.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "trade_account_number", "trade_contact_id", "cust_type", "purpose_of_trade", "payment_fundsIN_Id",
		"selling_amount", "selling_amount_base_value", "transaction_currency", "contract_number", "payment_Method",
		"cc_first_name", "bill_address_line", "avs_result", "is_threeds", "debit_card_added_date", "av_trade_value",
		"av_trade_frequency", "third_party_payment", "turnover", "transaction_reference", "related_reference",
		"account_identification", "payment_time", "sender_to_receiver_info", "debit_floor_indicator",
		"credit_floor_indicator", "info_to_account_owner", "statement_line", "debtor_name", "debtor_account_number",
		"contry_of_fund", "ordering_institution", "intermediary", "bill_ad_zip",
		"cheque_clearance_date" })
public class FundsInTrade implements Serializable {

	@JsonProperty("trade_account_number")
	private String tradeAccountNumber;
	@JsonProperty("trade_contact_id")
	private Integer tradeContactId;
	@JsonProperty("cust_type")
	private String custType;
	@JsonProperty("purpose_of_trade")
	private String purposeOfTrade;
	@JsonProperty("payment_fundsIN_Id")
	private Integer paymentFundsINId;
	@JsonProperty("selling_amount")
	@JsonSerialize(using=DoubleSerializer.class)
	private double sellingAmount;
	@JsonProperty("selling_amount_base_value")
	//@JsonSerialize(using=DoubleSerializer.class)
	private double sellingAmountBaseValue;
	@JsonProperty("transaction_currency")
	private String transactionCurrency;
	@JsonProperty("contract_number")
	private String contractNumber;
	@JsonProperty("payment_Method")
	private String paymentMethod;
	@JsonProperty("cc_first_name")
	private String ccFirstName;
	@JsonProperty("bill_address_line")
	private String billAddressLine;
	@JsonProperty("avs_result")
	private String avsResult;
	@JsonProperty("is_threeds")
	private String isThreeds;
	@JsonProperty("debit_card_added_date")
	private String debitCardAddedDate;
	@JsonProperty("av_trade_value")
	private String avTradeValue;
	@JsonProperty("av_trade_frequency")
	private String avTradeFrequency;
	@JsonProperty("third_party_payment")
	private Boolean thirdPartyPayment;

    @ApiModelProperty(value = "Amount of turnover that client said they will probably do when they signed up, used for Fraud analysis", required = true, example = "10000000")
    @JsonProperty("turnover")
	private String turnover;
	@JsonProperty("transaction_reference")
	private String transactionReference;
	@JsonProperty("related_reference")
	private String relatedReference;
	@JsonProperty("account_identification")
	private String accountIdentification;
	@JsonProperty("payment_time")
	private String paymentTime;
	@JsonProperty("sender_to_receiver_info")
	private String senderToReceiverInfo;
	@JsonProperty("debit_floor_indicator")
	private String debitFloorIndicator;
	@JsonProperty("credit_floor_indicator")
	private String creditFloorIndicator;
	@JsonProperty("info_to_account_owner")
	private String infoToAccountOwner;
	@JsonProperty("statement_line")
	private String statementLine;
	@JsonProperty("debtor_name")
	private String debtorName;
	@JsonProperty("debtor_account_number")
	private String debtorAccountNumber;
	@JsonProperty("country_of_fund")
	private String countryOfFund;
	@JsonProperty("ordering_institution")
	private String orderingInstitution;
	@JsonProperty("intermediary")
	private String intermediary;
	@JsonProperty("bill_ad_zip")
	private String billAdZip;
	@JsonProperty("cheque_clearance_date")
	private String chequeClearanceDate;
	private static final long serialVersionUID = 1L;
	/** The number of attempts. */
	@JsonProperty("number_of_attempts")
	private Integer numberOfAttempts;
	
	/** The number of success. */
	@JsonProperty("number_of_success")
	private Integer numberOfSuccess;
	
	/** The rg trans id. */
	@JsonProperty("rg_trans_id")
	private String rgTransId;
	
	/** The shopper ip. */
	@JsonProperty("shopper_ip")
	private String shopperIp;
	
	/** The gateway risk score. */
	@JsonProperty("gateway_risk_score")
	private String gatewayRiskScore;
	
	/** The transaction status. */
	@JsonProperty("transaction_status")
	private String transactionStatus;
	
	/** The card issuer. */
	@JsonProperty("card_issuer")
	private String cardIssuer;
	
	/** The card issuer country. */
	@JsonProperty("card_issuer_country")
	private String cardIssuerCountry;
	
	/** The refussal reason. */
	@JsonProperty("refussal_reason")
	private String refussalReason;
	
	/** The debit card type. */
	@JsonProperty("debit_card_type")
	private String debitCardType;

	@JsonProperty("trade_account_number")
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	@JsonProperty("trade_account_number")
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	@JsonProperty("trade_contact_id")
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	@JsonProperty("trade_contact_id")
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	@JsonProperty("cust_type")
	public String getCustType() {
		return custType;
	}

	@JsonProperty("cust_type")
	public void setCustType(String custType) {
		this.custType = custType;
	}

	@JsonProperty("purpose_of_trade")
	public String getPurposeOfTrade() {
		return purposeOfTrade;
	}

	@JsonProperty("purpose_of_trade")
	public void setPurposeOfTrade(String purposeOfTrade) {
		this.purposeOfTrade = purposeOfTrade;
	}

	@JsonProperty("payment_fundsIN_Id")
	public Integer getPaymentFundsInId() {
		return paymentFundsINId;
	}

	@JsonProperty("payment_fundsIN_Id")
	public void setPaymentFundsInId(Integer paymentFundsINId) {
		this.paymentFundsINId = paymentFundsINId;
	}

	@JsonProperty("selling_amount")
	public double getSellingAmount() {
		return sellingAmount;
	}

	@JsonProperty("selling_amount")
	public void setSellingAmount(double sellingAmount) {
		this.sellingAmount = sellingAmount;
	}

	@JsonProperty("transaction_currency")
	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	@JsonProperty("transaction_currency")
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	@JsonProperty("contract_number")
	public String getContractNumber() {
		return contractNumber;
	}

	@JsonProperty("contract_number")
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	@JsonProperty("payment_Method")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	@JsonProperty("payment_Method")
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@JsonProperty("cc_first_name")
	public String getCcFirstName() {
		return ccFirstName;
	}

	@JsonProperty("cc_first_name")
	public void setCcFirstName(String ccFirstName) {
		this.ccFirstName = ccFirstName;
	}

	@JsonProperty("bill_address_line")
	public String getBillAddressLine() {
		return billAddressLine;
	}

	@JsonProperty("bill_address_line")
	public void setBillAddressLine(String billAddressLine) {
		this.billAddressLine = billAddressLine;
	}

	@JsonProperty("avs_result")
	public String getAvsResult() {
		return avsResult;
	}

	@JsonProperty("avs_result")
	public void setAvsResult(String avsResult) {
		this.avsResult = avsResult;
	}

	@JsonProperty("is_threeds")
	public String getIsThreeds() {
		return isThreeds;
	}

	@JsonProperty("is_threeds")
	public void setIsThreeds(String isThreeds) {
		this.isThreeds = isThreeds;
	}

	@JsonProperty("debit_card_added_date")
	public String getDebitCardAddedDate() {
		return debitCardAddedDate;
	}

	@JsonProperty("debit_card_added_date")
	public void setDebitCardAddedDate(String debitCardAddedDate) {
		this.debitCardAddedDate = debitCardAddedDate;
	}

	@JsonProperty("av_trade_value")
	public String getAvTradeValue() {
		return avTradeValue;
	}

	@JsonProperty("av_trade_value")
	public void setAvTradeValue(String avTradeValue) {
		this.avTradeValue = avTradeValue;
	}

	@JsonProperty("av_trade_frequency")
	public String getAvTradeFrequency() {
		return avTradeFrequency;
	}

	@JsonProperty("av_trade_frequency")
	public void setAvTradeFrequency(String avTradeFrequency) {
		this.avTradeFrequency = avTradeFrequency;
	}

	@JsonProperty("third_party_payment")
	public Boolean getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	@JsonProperty("third_party_payment")
	public void setThirdPartyPayment(Boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	@JsonProperty("turnover")
	public String getTurnover() {
		return turnover;
	}

	@JsonProperty("turnover")
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	@JsonProperty("transaction_reference")
	public String getTransactionReference() {
		return transactionReference;
	}

	@JsonProperty("transaction_reference")
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	@JsonProperty("related_reference")
	public String getRelatedReference() {
		return relatedReference;
	}

	@JsonProperty("related_reference")
	public void setRelatedReference(String relatedReference) {
		this.relatedReference = relatedReference;
	}

	@JsonProperty("account_identification")
	public String getAccountIdentification() {
		return accountIdentification;
	}

	@JsonProperty("account_identification")
	public void setAccountIdentification(String accountIdentification) {
		this.accountIdentification = accountIdentification;
	}

	@JsonProperty("payment_time")
	public String getPaymentTime() {
		return paymentTime;
	}

	@JsonProperty("payment_time")
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	@JsonProperty("sender_to_receiver_info")
	public String getSenderToReceiverInfo() {
		return senderToReceiverInfo;
	}

	@JsonProperty("sender_to_receiver_info")
	public void setSenderToReceiverInfo(String senderToReceiverInfo) {
		this.senderToReceiverInfo = senderToReceiverInfo;
	}

	@JsonProperty("debit_floor_indicator")
	public String getDebitFloorIndicator() {
		return debitFloorIndicator;
	}

	@JsonProperty("debit_floor_indicator")
	public void setDebitFloorIndicator(String debitFloorIndicator) {
		this.debitFloorIndicator = debitFloorIndicator;
	}

	@JsonProperty("credit_floor_indicator")
	public String getCreditFloorIndicator() {
		return creditFloorIndicator;
	}

	@JsonProperty("credit_floor_indicator")
	public void setCreditFloorIndicator(String creditFloorIndicator) {
		this.creditFloorIndicator = creditFloorIndicator;
	}

	@JsonProperty("info_to_account_owner")
	public String getInfoToAccountOwner() {
		return infoToAccountOwner;
	}

	@JsonProperty("info_to_account_owner")
	public void setInfoToAccountOwner(String infoToAccountOwner) {
		this.infoToAccountOwner = infoToAccountOwner;
	}

	@JsonProperty("statement_line")
	public String getStatementLine() {
		return statementLine;
	}

	@JsonProperty("statement_line")
	public void setStatementLine(String statementLine) {
		this.statementLine = statementLine;
	}

	@JsonProperty("debtor_name")
	public String getDebtorName() {
		return debtorName;
	}

	@JsonProperty("debtor_name")
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}

	@JsonProperty("debtor_account_number")
	public String getDebtorAccountNumber() {
		return debtorAccountNumber;
	}

	@JsonProperty("debtor_account_number")
	public void setDebtorAccountNumber(String debtorAccountNumber) {
		this.debtorAccountNumber = debtorAccountNumber;
	}

	@JsonProperty("country_of_fund")
	public String getCountryOfFund() {
		return countryOfFund;
	}

	@JsonProperty("country_of_fund")
	public void setCountryOfFund(String contryOfFund) {
		this.countryOfFund = contryOfFund;
	}

	@JsonProperty("ordering_institution")
	public String getOrderingInstitution() {
		return orderingInstitution;
	}

	@JsonProperty("ordering_institution")
	public void setOrderingInstitution(String orderingInstitution) {
		this.orderingInstitution = orderingInstitution;
	}

	@JsonProperty("intermediary")
	public String getIntermediary() {
		return intermediary;
	}

	@JsonProperty("intermediary")
	public void setIntermediary(String intermediary) {
		this.intermediary = intermediary;
	}

	@JsonProperty("bill_ad_zip")
	public String getBillAdZip() {
		return billAdZip;
	}

	@JsonProperty("bill_ad_zip")
	public void setBillAdZip(String billAdZip) {
		this.billAdZip = billAdZip;
	}

	@JsonProperty("cheque_clearance_date")
	public String getChequeClearanceDate() {
		return chequeClearanceDate;
	}

	@JsonProperty("cheque_clearance_date")
	public void setChequeClearanceDate(String chequeClearanceDate) {
		this.chequeClearanceDate = chequeClearanceDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Integer getNumberOfAttempts() {
		return numberOfAttempts;
	}

	public void setNumberOfAttempts(Integer numberOfAttempts) {
		this.numberOfAttempts = numberOfAttempts;
	}

	public Integer getNumberOfSuccess() {
		return numberOfSuccess;
	}

	public void setNumberOfSuccess(Integer numberOfSuccess) {
		this.numberOfSuccess = numberOfSuccess;
	}

	public String getRgTransId() {
		return rgTransId;
	}

	public void setRgTransId(String rgTransId) {
		this.rgTransId = rgTransId;
	}

	public String getShopperIp() {
		return shopperIp;
	}

	public void setShopperIp(String shopperIp) {
		this.shopperIp = shopperIp;
	}

	public String getGatewayRiskScore() {
		return gatewayRiskScore;
	}

	public void setGatewayRiskScore(String gatewayRiskScore) {
		this.gatewayRiskScore = gatewayRiskScore;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getCardIssuerCountry() {
		return cardIssuerCountry;
	}

	public void setCardIssuerCountry(String cardIssuerCountry) {
		this.cardIssuerCountry = cardIssuerCountry;
	}

	public String getRefussalReason() {
		return refussalReason;
	}

	public void setRefussalReason(String refussalReason) {
		this.refussalReason = refussalReason;
	}

	public String getDebitCardType() {
		return debitCardType;
	}

	public void setDebitCardType(String debitCardType) {
		this.debitCardType = debitCardType;
	}

	/**
	 * @return the sellingAmountBaseValue
	 */
	@JsonProperty("selling_amount_base_value")
	public double getSellingAmountBaseValue() {
		return sellingAmountBaseValue;
	}

	/**
	 * @param sellingAmountBaseValue the sellingAmountBaseValue to set
	 */
	@JsonProperty("selling_amount_base_value")
	public void setSellingAmountBaseValue(double sellingAmountBaseValue) {
		this.sellingAmountBaseValue = sellingAmountBaseValue;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(tradeAccountNumber).append(tradeContactId).append(custType)
				.append(purposeOfTrade).append(paymentFundsINId).append(sellingAmount).append(sellingAmountBaseValue)
				.append(transactionCurrency).append(contractNumber).append(paymentMethod).append(ccFirstName)
				.append(billAddressLine).append(avsResult).append(isThreeds).append(debitCardAddedDate)
				.append(avTradeValue).append(avTradeFrequency).append(thirdPartyPayment).append(turnover)
				.append(transactionReference).append(relatedReference).append(accountIdentification).append(paymentTime)
				.append(senderToReceiverInfo).append(debitFloorIndicator).append(creditFloorIndicator)
				.append(infoToAccountOwner).append(statementLine).append(debtorName).append(debtorAccountNumber)
				.append(countryOfFund).append(orderingInstitution).append(intermediary)
				.append(billAdZip).append(chequeClearanceDate).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof FundsInTrade)) {
			return false;
		}
		FundsInTrade rhs = ((FundsInTrade) other);
		return new EqualsBuilder().append(tradeAccountNumber, rhs.tradeAccountNumber)
				.append(tradeContactId, rhs.tradeContactId).append(custType, rhs.custType)
				.append(purposeOfTrade, rhs.purposeOfTrade).append(paymentFundsINId, rhs.paymentFundsINId)
				.append(sellingAmount, rhs.sellingAmount).append(sellingAmountBaseValue, rhs.sellingAmountBaseValue)
				.append(transactionCurrency, rhs.transactionCurrency).append(contractNumber, rhs.contractNumber)
				.append(paymentMethod, rhs.paymentMethod).append(ccFirstName, rhs.ccFirstName)
				.append(billAddressLine, rhs.billAddressLine).append(avsResult, rhs.avsResult)
				.append(isThreeds, rhs.isThreeds).append(debitCardAddedDate, rhs.debitCardAddedDate)
				.append(avTradeValue, rhs.avTradeValue).append(avTradeFrequency, rhs.avTradeFrequency)
				.append(thirdPartyPayment, rhs.thirdPartyPayment).append(turnover, rhs.turnover)
				.append(transactionReference, rhs.transactionReference).append(relatedReference, rhs.relatedReference)
				.append(accountIdentification, rhs.accountIdentification).append(paymentTime, rhs.paymentTime)
				.append(senderToReceiverInfo, rhs.senderToReceiverInfo)
				.append(debitFloorIndicator, rhs.debitFloorIndicator)
				.append(creditFloorIndicator, rhs.creditFloorIndicator)
				.append(infoToAccountOwner, rhs.infoToAccountOwner).append(statementLine, rhs.statementLine)
				.append(debtorName, rhs.debtorName).append(debtorAccountNumber, rhs.debtorAccountNumber)
				.append(countryOfFund, rhs.countryOfFund)
				.append(orderingInstitution, rhs.orderingInstitution).append(intermediary, rhs.intermediary)
				.append(billAdZip, rhs.billAdZip).append(chequeClearanceDate, rhs.chequeClearanceDate).isEquals();
	}
}