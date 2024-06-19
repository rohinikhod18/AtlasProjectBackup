package com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request;

import java.io.Serializable;
import com.currenciesdirect.gtg.compliance.commons.util.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FundsInTrade.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "trade_account_number", "trade_contact_id", "cust_type", "purpose_of_trade", "payment_fundsIN_Id",
		"selling_amount", "selling_amount_base_value", "transaction_currency", "contract_number", "payment_Method",
		"cc_first_name", "bill_address_line", "avs_result", "is_threeds", "debit_card_added_date", "av_trade_value",
		"av_trade_frequency", "third_party_payment", "turnover", "transaction_reference", "related_reference",
		"account_identification", "payment_time", "sender_to_receiver_info", "debit_floor_indicator",
		"credit_floor_indicator", "info_to_account_owner", "statement_line", "debtor_name", "debtor_account_number",
		"debtor_bank_name", "contry_of_fund", "ordering_institution", "intermediary", "bill_ad_zip",
		"cheque_clearance_date", "card_reuse_flag", "no_of_times_card_used", "age_of_card", "cvc_result", "transfer_reason",
		"reg_transaction_id", "shopper_ip_country", "deal_type", "first_trade_date", "last_trade_date", "age_of_payee",
		"updated_by", "order_code", "dealer", "transaction_type", "debit_card_deleted_flag", "dd_account_no",
		"dd_frequency_of_payment","customer_instruction_number","initiating_parent_contact" })
public class FundsInTrade implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The trade account number. */
	@ApiModelProperty(value = "The trade account number", required = true, example = "0201085005048284")
	@JsonProperty("trade_account_number")
	private String tradeAccountNumber;

	/** The trade contact id. */
	@ApiModelProperty(value = "The trade contact ID", example = "751843", required = true)
	@JsonProperty("trade_contact_id")
	private Integer tradeContactId;

	/** The cust type. */
	@ApiModelProperty(value = "The customer type (PFX/CFX)", required = true, example = "PFX")
	@JsonProperty("cust_type")
	private String custType;

	/** The purpose of trade. */
	@ApiModelProperty(value = "The purpose of trade", required = true, example = "Holiday")
	@JsonProperty("purpose_of_trade")
	private String purposeOfTrade;

	/** The payment funds IN id. */
	@ApiModelProperty(value = "The payment Funds-In id", required = true, example = "22721694")
	@JsonProperty("payment_fundsIN_Id")
	private Integer paymentFundsINId;

	/** The selling amount. */
	@ApiModelProperty(value = "The selling amount", required = true, example = "500")
	@JsonProperty("selling_amount")
	@JsonSerialize(using = DoubleSerializer.class)
	private double sellingAmount;

	/** The selling amount GBP value. */
	@ApiModelProperty(value = "The selling amount base value", required = true, example = "381")
	@JsonProperty("selling_amount_base_value")
	@JsonSerialize(using = DoubleSerializer.class)
	private double sellingAmountBaseValue;

	/** The transaction currency. */
	@ApiModelProperty(value = "The transaction currency", required = true, example = "AUD")
	@JsonProperty("transaction_currency")
	private String transactionCurrency;

	/** The contract number. */
	@ApiModelProperty(value = "The Trade Identification number", required = true, example = "0201085005048284-000000001")
	@JsonProperty("contract_number")
	private String contractNumber;

	/** The payment method. */
	@ApiModelProperty(value = "The payment method", required = true, example = "BACS / CHAPS / TT")
	@JsonProperty("payment_Method")
	private String paymentMethod;

	/** The cc first name. */
	@ApiModelProperty(value = "If Switch or Debit Card payment, first name of card", example = "Bob", required = true)
	@JsonProperty("cc_first_name")
	private String ccFirstName;

	/** The bill address line. */
	@ApiModelProperty(value = "If Switch or Debit Card payment, first line of Billing Address", example = "3 Stable Mews", required = true)
	@JsonProperty("bill_address_line")
	private String billAddressLine;

	/** The avs result. */
	@ApiModelProperty(value = "If Switch or Debit Card payment, Address Verification Service (AVS) result. This service enables the address, including postcode, entered by the shopper to be compared against the UK card issuer's records", required = true)
	@JsonProperty("avs_result")
	private String avsResult;

	/** The is threeds. */
	@ApiModelProperty(value = "If Switch or Debit Card payment, whether this transaction passed 3D Secure checks. 3D Secure is a technical standard created by Visa and MasterCard to further secure Card-holder Not Present transactions over the Internet", required = true)
	@JsonProperty("is_threeds")
	private String isThreeds;

	/** The debit card added date. */
	@ApiModelProperty(value = "The debit card added date", required = true)
	@JsonProperty("debit_card_added_date")
	private String debitCardAddedDate;

	/** The av trade value. */
	@ApiModelProperty(value = "Average Transactional Value. Customer's anticipated average trade value", required = true, example = "0")
	@JsonProperty("av_trade_value")
	private String avTradeValue;

	/** The av trade frequency. */
	@ApiModelProperty(value = "Average Transactional frequency", required = true, example = "0")
	@JsonProperty("av_trade_frequency")
	private String avTradeFrequency;

	/** The third party payment. */
	@ApiModelProperty(value = "Whether this is a Third Party payment - true or false", required = true, example = "true")
	@JsonProperty("third_party_payment")
	private Boolean thirdPartyPayment;

	/** The turnover. */
	@ApiModelProperty(value = "Amount of turnover that client said they will probably do when they signed up, used for Fraud analysis", required = true, example = "10000000")
	@JsonProperty("turnover")
	private String turnover;

	/** The transaction reference. */
	@ApiModelProperty(value = "The transaction reference", required = true, example = "-tt 270718")
	@JsonProperty("transaction_reference")
	private String transactionReference;

	/** The related reference. */
	@ApiModelProperty(value = "The related reference", required = true)
	@JsonProperty("related_reference")
	private String relatedReference;

	/** The account identification. */
	@ApiModelProperty(value = "Account Identification", required = true)
	@JsonProperty("account_identification")
	private String accountIdentification;

	/** The payment time. */
	@ApiModelProperty(value = "Timestamp of the Funds In event", required = true, example = "2018-07-30T07:13:12Z")
	@JsonProperty("payment_time")
	private String paymentTime;

	/** The sender to receiver info. */
	@ApiModelProperty(value = "Payment from sender to receiver details", required = true)
	@JsonProperty("sender_to_receiver_info")
	private String senderToReceiverInfo;

	/** The debit floor indicator. */
	@ApiModelProperty(value = "Debit floor indicator", required = true)
	@JsonProperty("debit_floor_indicator")
	private String debitFloorIndicator;

	/** The credit floor indicator. */
	@ApiModelProperty(value = "Credit floor indicator", required = true)
	@JsonProperty("credit_floor_indicator")
	private String creditFloorIndicator;

	/** The info to account owner. */
	@ApiModelProperty(value = "Info to account owner", required = true)
	@JsonProperty("info_to_account_owner")
	private String infoToAccountOwner;

	/** The statement line. */
	@ApiModelProperty(value = "Statement Line", required = true)
	@JsonProperty("statement_line")
	private String statementLine;

	/** The debtor name. */
	@ApiModelProperty(value = "Debtor Name", required = true, example = "Vishal Joshi")
	@JsonProperty("debtor_name")
	private String debtorName;

	/** The debtor account number. */
	@ApiModelProperty(value = "Debtor Account Number", required = true, example = "87942313")
	@JsonProperty("debtor_account_number")
	private String debtorAccountNumber;

	/** The debtor bank name. */
	@ApiModelProperty(value = "Bank name where the funds came from", required = true)
	@JsonProperty("debtor_bank_name")
	private String debtorBankName;

	/** The country of fund. */
	@ApiModelProperty(value = "Funds In Country", required = true, example = "BHR")
	@JsonProperty("country_of_fund")
	private String countryOfFund;

	/** The ordering institution. */
	@ApiModelProperty(value = "Ordering Institution", required = true, example = "Currencies Direct")
	@JsonProperty("ordering_institution")
	private String orderingInstitution;

	/** The intermediary. */
	@ApiModelProperty(value = "Intermediary", required = true)
	@JsonProperty("intermediary")
	private String intermediary;

	/** The bill ad zip. */
	@ApiModelProperty(value = "Billing address on one line", example = "3 Stable Mews, London, WC1", required = true)
	@JsonProperty("bill_ad_zip")
	private String billAdZip;

	/** The cheque clearance date. */
	@ApiModelProperty(value = "The cheque clearance date", required = true)
	@JsonProperty("cheque_clearance_date")
	private String chequeClearanceDate;

	/** The card reuse flag. */
	@ApiModelProperty(value = "The card reuse flag", required = true)
	@JsonProperty("card_reuse_flag")
	private Boolean cardReuseFlag;

	/** The no of times card used. */
	@ApiModelProperty(value = "Number of times card used", required = true)
	@JsonProperty("no_of_times_card_used")
	private Integer noOfTimesCardUsed;

	/** The age of card. */
	@ApiModelProperty(value = "Age of Card", required = true)
	@JsonProperty("age_of_card")
	private Integer ageOfCard;

	/** The cvc result. */
	@ApiModelProperty(value = "CVC Result", required = true)
	@JsonProperty("cvc_result")
	private String cvcResult;
	
	/** org_wallet_funding_reason. */ //AT-4078
	@ApiModelProperty(value = "Transfer Reason")
	@JsonProperty("transfer_reason")
	private String transferReason;
	
	/** The reg transaction id. */
	@ApiModelProperty(value = "Registration Transaction ID", required = true)
	@JsonProperty("reg_transaction_id")
	private Integer regTransactionId;

	/** The shopper ip country. */
	@ApiModelProperty(value = "Shopper IP Country", required = true)
	@JsonProperty("shopper_ip_country")
	private String shopperIpCountry;

	/** The deal type. */
	@ApiModelProperty(value = "Deal Type", required = true)
	@JsonProperty("deal_type")
	private String dealType;

	/** The first trade date. */
	@ApiModelProperty(value = "First Trade Date", required = true)
	@JsonProperty("first_trade_date")
	private String firstTradeDate;

	/** The last trade date. */
	@ApiModelProperty(value = "Last Trade Date", required = true)
	@JsonProperty("last_trade_date")
	private String lastTradeDate;

	/** The age of payee. */
	@ApiModelProperty(value = "Payee Age", required = true)
	@JsonProperty("age_of_payee")
	private Integer ageOfPayee;

	/** The updated by. */
	@ApiModelProperty(value = "last updated by", required = true)
	@JsonProperty("updated_by")
	private String updatedBy;

	/** The order code. */
	@ApiModelProperty(value = "Organization Code", required = true)
	@JsonProperty("order_code")
	private String orderCode;

	/** The dealer. */
	@ApiModelProperty(value = "Dealer", required = true)
	@JsonProperty("dealer")
	private String dealer;

	/** The transaction type. */
	@ApiModelProperty(value = "Type of transaction", required = true)
	@JsonProperty("transaction_type")
	private String transactionType;

	/** The debit card deleted flag. */
	@ApiModelProperty(value = "Is Debit card deleted or not", required = true)
	@JsonProperty("debit_card_deleted_flag")
	private Boolean debitCardDeletedFlag;

	/** The dd account no. */
	@ApiModelProperty(value = "Account Number of DD", required = true)
	@JsonProperty("dd_account_no")
	private String ddAccountNo;

	/** The dd frequency of payment. */
	@ApiModelProperty(value = "Payment Frequency of DD", required = true)
	@JsonProperty("dd_frequency_of_payment")
	private Integer ddFrequencyOfPayment;
	
	
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
	
	/** The customer insrtuction number. */
	@JsonProperty("customer_instruction_number")
	private String customerInsrtuctionNumber;
	
	/** The customer legal entity. */
	@JsonProperty(value = "customer_legal_entity")
	private String custLegalEntity;

	/** The threedsVersion. */
	@JsonProperty(value = "threeds_version")
	private String threedsVersion;
	
	/** The debtor state. */
	@JsonProperty(value = "debtor_state")
	private String debtorState;
	
	/** The creditor bank name. */
	@JsonProperty(value = "creditor_bank_name")
	private String creditorBankName;
	
	/** The creditor bank account number. */
	@JsonProperty(value = "creditor_bank_AccountNumber")
	private String creditorBankAccountNumber;
	
	/** The creditor account name. */
	@JsonProperty(value = "creditor_account_name")
	private String creditorAccountName;

	//AT-5337
	@JsonProperty("initiating_parent_contact")
	private String initiatingParentContact;
	

	/**
	 * Gets the card reuse flag.
	 *
	 * @return the card reuse flag
	 */
	public Boolean getCardReuseFlag() {
		return cardReuseFlag;
	}

	/**
	 * Sets the card reuse flag.
	 *
	 * @param cardReuseFlag
	 *            the new card reuse flag
	 */
	public void setCardReuseFlag(Boolean cardReuseFlag) {
		this.cardReuseFlag = cardReuseFlag;
	}

	/**
	 * Gets the no of times card used.
	 *
	 * @return the no of times card used
	 */
	public Integer getNoOfTimesCardUsed() {
		return noOfTimesCardUsed;
	}

	/**
	 * Sets the no of times card used.
	 *
	 * @param noOfTimesCardUsed
	 *            the new no of times card used
	 */
	public void setNoOfTimesCardUsed(Integer noOfTimesCardUsed) {
		this.noOfTimesCardUsed = noOfTimesCardUsed;
	}

	/**
	 * Gets the age of card.
	 *
	 * @return the age of card
	 */
	public Integer getAgeOfCard() {
		return ageOfCard;
	}

	/**
	 * Sets the age of card.
	 *
	 * @param ageOfCard
	 *            the new age of card
	 */
	public void setAgeOfCard(Integer ageOfCard) {
		this.ageOfCard = ageOfCard;
	}

	/**
	 * Gets the cvc result.
	 *
	 * @return the cvc result
	 */
	public String getCvcResult() {
		return cvcResult;
	}

	/**
	 * Sets the cvc result.
	 *
	 * @param cvcResult
	 *            the new cvc result
	 */
	public void setCvcResult(String cvcResult) {
		this.cvcResult = cvcResult;
	}

	/**
	 * Gets the transfer reason.
	 *
	 * @return the transfer reason
	 */
	@JsonProperty("transfer_reason")
	public String getTransferReason() {
		return transferReason;
	}

	/**
	 * Sets the transfer reason.
	 *
	 * @param transferReason
	 *            the new transfer reason
	 */
	@JsonProperty("transfer_reason")
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	/**
	 * Gets the reg transaction id.
	 *
	 * @return the reg transaction id
	 */
	public Integer getRegTransactionId() {
		return regTransactionId;
	}

	/**
	 * Sets the reg transaction id.
	 *
	 * @param regTransactionId
	 *            the new reg transaction id
	 */
	public void setRegTransactionId(Integer regTransactionId) {
		this.regTransactionId = regTransactionId;
	}

	/**
	 * Gets the shopper ip country.
	 *
	 * @return the shopper ip country
	 */
	public String getShopperIpCountry() {
		return shopperIpCountry;
	}

	/**
	 * Sets the shopper ip country.
	 *
	 * @param shopperIpCountry
	 *            the new shopper ip country
	 */
	public void setShopperIpCountry(String shopperIpCountry) {
		this.shopperIpCountry = shopperIpCountry;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy
	 *            the new updated by
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the transaction type.
	 *
	 * @return the transaction type
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * Sets the transaction type.
	 *
	 * @param transactionType
	 *            the new transaction type
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * Gets the deal type.
	 *
	 * @return the deal type
	 */
	public String getDealType() {
		return dealType;
	}

	/**
	 * Sets the deal type.
	 *
	 * @param dealType
	 *            the new deal type
	 */
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	/**
	 * Gets the first trade date.
	 *
	 * @return the first trade date
	 */
	public String getFirstTradeDate() {
		return firstTradeDate;
	}

	/**
	 * Sets the first trade date.
	 *
	 * @param firstTradeDate
	 *            the new first trade date
	 */
	public void setFirstTradeDate(String firstTradeDate) {
		this.firstTradeDate = firstTradeDate;
	}

	/**
	 * Gets the last trade date.
	 *
	 * @return the last trade date
	 */
	public String getLastTradeDate() {
		return lastTradeDate;
	}

	/**
	 * Sets the last trade date.
	 *
	 * @param lastTradeDate
	 *            the new last trade date
	 */
	public void setLastTradeDate(String lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

	/**
	 * Gets the order code.
	 *
	 * @return the order code
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * Sets the order code.
	 *
	 * @param orderCode
	 *            the new order code
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	/**
	 * Gets the debit card deleted flag.
	 *
	 * @return the debit card deleted flag
	 */
	public Boolean getDebitCardDeletedFlag() {
		return debitCardDeletedFlag;
	}

	/**
	 * Sets the debit card deleted flag.
	 *
	 * @param debitCardDeletedFlag
	 *            the new debit card deleted flag
	 */
	public void setDebitCardDeletedFlag(Boolean debitCardDeletedFlag) {
		this.debitCardDeletedFlag = debitCardDeletedFlag;
	}

	/**
	 * Gets the age of payee.
	 *
	 * @return the age of payee
	 */
	public Integer getAgeOfPayee() {
		return ageOfPayee;
	}

	/**
	 * Sets the age of payee.
	 *
	 * @param ageOfPayee
	 *            the new age of payee
	 */
	public void setAgeOfPayee(Integer ageOfPayee) {
		this.ageOfPayee = ageOfPayee;
	}

	/**
	 * Gets the dealer.
	 *
	 * @return the dealer
	 */
	public String getDealer() {
		return dealer;
	}

	/**
	 * Sets the dealer.
	 *
	 * @param dealer
	 *            the new dealer
	 */
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	/**
	 * Gets the dd account no.
	 *
	 * @return the dd account no
	 */
	public String getDdAccountNo() {
		return ddAccountNo;
	}

	/**
	 * Sets the dd account no.
	 *
	 * @param ddAccountNo
	 *            the new dd account no
	 */
	public void setDdAccountNo(String ddAccountNo) {
		this.ddAccountNo = ddAccountNo;
	}

	/**
	 * Gets the dd frequency of payment.
	 *
	 * @return the dd frequency of payment
	 */
	public Integer getDdFrequencyOfPayment() {
		return ddFrequencyOfPayment;
	}

	/**
	 * Sets the dd frequency of payment.
	 *
	 * @param ddFrequencyOfPayment
	 *            the new dd frequency of payment
	 */
	public void setDdFrequencyOfPayment(Integer ddFrequencyOfPayment) {
		this.ddFrequencyOfPayment = ddFrequencyOfPayment;
	}

	/**
	 * Gets the debtor bank name.
	 *
	 * @return the debtor bank name
	 */
	@JsonProperty("debtor_bank_name")
	public String getDebtorBankName() {
		return debtorBankName;
	}

	/**
	 * Sets the debtor bank name.
	 *
	 * @param debtorBankName
	 *            the new debtor bank name
	 */
	@JsonProperty("debtor_bank_name")
	public void setDebtorBankName(String debtorBankName) {
		this.debtorBankName = debtorBankName;
	}

	/**
	 * Gets the trade account number.
	 *
	 * @return the trade account number
	 */
	@JsonProperty("trade_account_number")
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber
	 *            the new trade account number
	 */
	@JsonProperty("trade_account_number")
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * Gets the trade contact id.
	 *
	 * @return the trade contact id
	 */
	@JsonProperty("trade_contact_id")
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * Sets the trade contact id.
	 *
	 * @param tradeContactId
	 *            the new trade contact id
	 */
	@JsonProperty("trade_contact_id")
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return the cust type
	 */
	@JsonProperty("cust_type")
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType
	 *            the new cust type
	 */
	@JsonProperty("cust_type")
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the purpose of trade.
	 *
	 * @return the purpose of trade
	 */
	@JsonProperty("purpose_of_trade")
	public String getPurposeOfTrade() {
		return purposeOfTrade;
	}

	/**
	 * Sets the purpose of trade.
	 *
	 * @param purposeOfTrade
	 *            the new purpose of trade
	 */
	@JsonProperty("purpose_of_trade")
	public void setPurposeOfTrade(String purposeOfTrade) {
		this.purposeOfTrade = purposeOfTrade;
	}

	/**
	 * Gets the payment funds in id.
	 *
	 * @return the payment funds in id
	 */
	@JsonProperty("payment_fundsIN_Id")
	public Integer getPaymentFundsInId() {
		return paymentFundsINId;
	}

	/**
	 * Sets the payment funds in id.
	 *
	 * @param paymentFundsINId
	 *            the new payment funds in id
	 */
	@JsonProperty("payment_fundsIN_Id")
	public void setPaymentFundsInId(Integer paymentFundsINId) {
		this.paymentFundsINId = paymentFundsINId;
	}

	/**
	 * Gets the selling amount.
	 *
	 * @return the selling amount
	 */
	@JsonProperty("selling_amount")
	public double getSellingAmount() {
		return sellingAmount;
	}

	/**
	 * Sets the selling amount.
	 *
	 * @param sellingAmount
	 *            the new selling amount
	 */
	@JsonProperty("selling_amount")
	public void setSellingAmount(double sellingAmount) {
		this.sellingAmount = sellingAmount;
	}

	/**
	 * Gets the transaction currency.
	 *
	 * @return the transaction currency
	 */
	@JsonProperty("transaction_currency")
	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	/**
	 * Sets the transaction currency.
	 *
	 * @param transactionCurrency
	 *            the new transaction currency
	 */
	@JsonProperty("transaction_currency")
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	/**
	 * Gets the contract number.
	 *
	 * @return the contract number
	 */
	@JsonProperty("contract_number")
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * Sets the contract number.
	 *
	 * @param contractNumber
	 *            the new contract number
	 */
	@JsonProperty("contract_number")
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * Gets the payment method.
	 *
	 * @return the payment method
	 */
	@JsonProperty("payment_Method")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets the payment method.
	 *
	 * @param paymentMethod
	 *            the new payment method
	 */
	@JsonProperty("payment_Method")
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Gets the cc first name.
	 *
	 * @return the cc first name
	 */
	@JsonProperty("cc_first_name")
	public String getCcFirstName() {
		return ccFirstName;
	}

	/**
	 * Sets the cc first name.
	 *
	 * @param ccFirstName
	 *            the new cc first name
	 */
	@JsonProperty("cc_first_name")
	public void setCcFirstName(String ccFirstName) {
		this.ccFirstName = ccFirstName;
	}

	/**
	 * Gets the bill address line.
	 *
	 * @return the bill address line
	 */
	@JsonProperty("bill_address_line")
	public String getBillAddressLine() {
		return billAddressLine;
	}

	/**
	 * Sets the bill address line.
	 *
	 * @param billAddressLine
	 *            the new bill address line
	 */
	@JsonProperty("bill_address_line")
	public void setBillAddressLine(String billAddressLine) {
		this.billAddressLine = billAddressLine;
	}

	/**
	 * Gets the avs result.
	 *
	 * @return the avs result
	 */
	@JsonProperty("avs_result")
	public String getAvsResult() {
		return avsResult;
	}

	/**
	 * Sets the avs result.
	 *
	 * @param avsResult
	 *            the new avs result
	 */
	@JsonProperty("avs_result")
	public void setAvsResult(String avsResult) {
		this.avsResult = avsResult;
	}

	/**
	 * Gets the checks if is threeds.
	 *
	 * @return the checks if is threeds
	 */
	@JsonProperty("is_threeds")
	public String getIsThreeds() {
		return isThreeds;
	}

	/**
	 * Sets the checks if is threeds.
	 *
	 * @param isThreeds
	 *            the new checks if is threeds
	 */
	@JsonProperty("is_threeds")
	public void setIsThreeds(String isThreeds) {
		this.isThreeds = isThreeds;
	}

	/**
	 * Gets the debit card added date.
	 *
	 * @return the debit card added date
	 */
	@JsonProperty("debit_card_added_date")
	public String getDebitCardAddedDate() {
		return debitCardAddedDate;
	}

	/**
	 * Sets the debit card added date.
	 *
	 * @param debitCardAddedDate
	 *            the new debit card added date
	 */
	@JsonProperty("debit_card_added_date")
	public void setDebitCardAddedDate(String debitCardAddedDate) {
		this.debitCardAddedDate = debitCardAddedDate;
	}

	/**
	 * Gets the av trade value.
	 *
	 * @return the av trade value
	 */
	@JsonProperty("av_trade_value")
	public String getAvTradeValue() {
		return avTradeValue;
	}

	/**
	 * Sets the av trade value.
	 *
	 * @param avTradeValue
	 *            the new av trade value
	 */
	@JsonProperty("av_trade_value")
	public void setAvTradeValue(String avTradeValue) {
		this.avTradeValue = avTradeValue;
	}

	/**
	 * Gets the av trade frequency.
	 *
	 * @return the av trade frequency
	 */
	@JsonProperty("av_trade_frequency")
	public String getAvTradeFrequency() {
		return avTradeFrequency;
	}

	/**
	 * Sets the av trade frequency.
	 *
	 * @param avTradeFrequency
	 *            the new av trade frequency
	 */
	@JsonProperty("av_trade_frequency")
	public void setAvTradeFrequency(String avTradeFrequency) {
		this.avTradeFrequency = avTradeFrequency;
	}

	/**
	 * Gets the third party payment.
	 *
	 * @return the third party payment
	 */
	@JsonProperty("third_party_payment")
	public Boolean getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	/**
	 * Sets the third party payment.
	 *
	 * @param thirdPartyPayment
	 *            the new third party payment
	 */
	@JsonProperty("third_party_payment")
	public void setThirdPartyPayment(Boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	/**
	 * Gets the turnover.
	 *
	 * @return the turnover
	 */
	@JsonProperty("turnover")
	public String getTurnover() {
		return turnover;
	}

	/**
	 * Sets the turnover.
	 *
	 * @param turnover
	 *            the new turnover
	 */
	@JsonProperty("turnover")
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	/**
	 * Gets the transaction reference.
	 *
	 * @return the transaction reference
	 */
	@JsonProperty("transaction_reference")
	public String getTransactionReference() {
		return transactionReference;
	}

	/**
	 * Sets the transaction reference.
	 *
	 * @param transactionReference
	 *            the new transaction reference
	 */
	@JsonProperty("transaction_reference")
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	/**
	 * Gets the related reference.
	 *
	 * @return the related reference
	 */
	@JsonProperty("related_reference")
	public String getRelatedReference() {
		return relatedReference;
	}

	/**
	 * Sets the related reference.
	 *
	 * @param relatedReference
	 *            the new related reference
	 */
	@JsonProperty("related_reference")
	public void setRelatedReference(String relatedReference) {
		this.relatedReference = relatedReference;
	}

	/**
	 * Gets the account identification.
	 *
	 * @return the account identification
	 */
	@JsonProperty("account_identification")
	public String getAccountIdentification() {
		return accountIdentification;
	}

	/**
	 * Sets the account identification.
	 *
	 * @param accountIdentification
	 *            the new account identification
	 */
	@JsonProperty("account_identification")
	public void setAccountIdentification(String accountIdentification) {
		this.accountIdentification = accountIdentification;
	}

	/**
	 * Gets the payment time.
	 *
	 * @return the payment time
	 */
	@JsonProperty("payment_time")
	public String getPaymentTime() {
		return paymentTime;
	}

	/**
	 * Sets the payment time.
	 *
	 * @param paymentTime
	 *            the new payment time
	 */
	@JsonProperty("payment_time")
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	/**
	 * Gets the sender to receiver info.
	 *
	 * @return the sender to receiver info
	 */
	@JsonProperty("sender_to_receiver_info")
	public String getSenderToReceiverInfo() {
		return senderToReceiverInfo;
	}

	/**
	 * Sets the sender to receiver info.
	 *
	 * @param senderToReceiverInfo
	 *            the new sender to receiver info
	 */
	@JsonProperty("sender_to_receiver_info")
	public void setSenderToReceiverInfo(String senderToReceiverInfo) {
		this.senderToReceiverInfo = senderToReceiverInfo;
	}

	/**
	 * Gets the debit floor indicator.
	 *
	 * @return the debit floor indicator
	 */
	@JsonProperty("debit_floor_indicator")
	public String getDebitFloorIndicator() {
		return debitFloorIndicator;
	}

	/**
	 * Sets the debit floor indicator.
	 *
	 * @param debitFloorIndicator
	 *            the new debit floor indicator
	 */
	@JsonProperty("debit_floor_indicator")
	public void setDebitFloorIndicator(String debitFloorIndicator) {
		this.debitFloorIndicator = debitFloorIndicator;
	}

	/**
	 * Gets the credit floor indicator.
	 *
	 * @return the credit floor indicator
	 */
	@JsonProperty("credit_floor_indicator")
	public String getCreditFloorIndicator() {
		return creditFloorIndicator;
	}

	/**
	 * Sets the credit floor indicator.
	 *
	 * @param creditFloorIndicator
	 *            the new credit floor indicator
	 */
	@JsonProperty("credit_floor_indicator")
	public void setCreditFloorIndicator(String creditFloorIndicator) {
		this.creditFloorIndicator = creditFloorIndicator;
	}

	/**
	 * Gets the info to account owner.
	 *
	 * @return the info to account owner
	 */
	@JsonProperty("info_to_account_owner")
	public String getInfoToAccountOwner() {
		return infoToAccountOwner;
	}

	/**
	 * Sets the info to account owner.
	 *
	 * @param infoToAccountOwner
	 *            the new info to account owner
	 */
	@JsonProperty("info_to_account_owner")
	public void setInfoToAccountOwner(String infoToAccountOwner) {
		this.infoToAccountOwner = infoToAccountOwner;
	}

	/**
	 * Gets the statement line.
	 *
	 * @return the statement line
	 */
	@JsonProperty("statement_line")
	public String getStatementLine() {
		return statementLine;
	}

	/**
	 * Sets the statement line.
	 *
	 * @param statementLine
	 *            the new statement line
	 */
	@JsonProperty("statement_line")
	public void setStatementLine(String statementLine) {
		this.statementLine = statementLine;
	}

	/**
	 * Gets the debtor name.
	 *
	 * @return the debtor name
	 */
	@JsonProperty("debtor_name")
	public String getDebtorName() {
		return debtorName;
	}

	/**
	 * Sets the debtor name.
	 *
	 * @param debtorName
	 *            the new debtor name
	 */
	@JsonProperty("debtor_name")
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}

	/**
	 * Gets the debtor account number.
	 *
	 * @return the debtor account number
	 */
	@JsonProperty("debtor_account_number")
	public String getDebtorAccountNumber() {
		return debtorAccountNumber;
	}

	/**
	 * Sets the debtor account number.
	 *
	 * @param debtorAccountNumber
	 *            the new debtor account number
	 */
	@JsonProperty("debtor_account_number")
	public void setDebtorAccountNumber(String debtorAccountNumber) {
		this.debtorAccountNumber = debtorAccountNumber;
	}

	/**
	 * Gets the country of fund.
	 *
	 * @return the country of fund
	 */
	@JsonProperty("country_of_fund")
	public String getCountryOfFund() {
		return countryOfFund;
	}

	/**
	 * Sets the country of fund.
	 *
	 * @param contryOfFund
	 *            the new country of fund
	 */
	@JsonProperty("country_of_fund")
	public void setCountryOfFund(String contryOfFund) {
		this.countryOfFund = contryOfFund;
	}

	/**
	 * Gets the ordering institution.
	 *
	 * @return the ordering institution
	 */
	@JsonProperty("ordering_institution")
	public String getOrderingInstitution() {
		return orderingInstitution;
	}

	/**
	 * Sets the ordering institution.
	 *
	 * @param orderingInstitution
	 *            the new ordering institution
	 */
	@JsonProperty("ordering_institution")
	public void setOrderingInstitution(String orderingInstitution) {
		this.orderingInstitution = orderingInstitution;
	}

	/**
	 * Gets the intermediary.
	 *
	 * @return the intermediary
	 */
	@JsonProperty("intermediary")
	public String getIntermediary() {
		return intermediary;
	}

	/**
	 * Sets the intermediary.
	 *
	 * @param intermediary
	 *            the new intermediary
	 */
	@JsonProperty("intermediary")
	public void setIntermediary(String intermediary) {
		this.intermediary = intermediary;
	}

	/**
	 * Gets the bill ad zip.
	 *
	 * @return the bill ad zip
	 */
	@JsonProperty("bill_ad_zip")
	public String getBillAdZip() {
		return billAdZip;
	}

	/**
	 * Sets the bill ad zip.
	 *
	 * @param billAdZip
	 *            the new bill ad zip
	 */
	@JsonProperty("bill_ad_zip")
	public void setBillAdZip(String billAdZip) {
		this.billAdZip = billAdZip;
	}

	/**
	 * Gets the cheque clearance date.
	 *
	 * @return the cheque clearance date
	 */
	@JsonProperty("cheque_clearance_date")
	public String getChequeClearanceDate() {
		return chequeClearanceDate;
	}

	/**
	 * Sets the cheque clearance date.
	 *
	 * @param chequeClearanceDate
	 *            the new cheque clearance date
	 */
	@JsonProperty("cheque_clearance_date")
	public void setChequeClearanceDate(String chequeClearanceDate) {
		this.chequeClearanceDate = chequeClearanceDate;
	}
	

	/**
	 * Gets the number of attempts.
	 *
	 * @return the number of attempts
	 */
	public Integer getNumberOfAttempts() {
		return numberOfAttempts;
	}

	/**
	 * Sets the number of attempts.
	 *
	 * @param numberOfAttempts the new number of attempts
	 */
	public void setNumberOfAttempts(Integer numberOfAttempts) {
		this.numberOfAttempts = numberOfAttempts;
	}

	/**
	 * Gets the number of success.
	 *
	 * @return the number of success
	 */
	public Integer getNumberOfSuccess() {
		return numberOfSuccess;
	}

	/**
	 * Sets the number of success.
	 *
	 * @param numberOfSuccess the new number of success
	 */
	public void setNumberOfSuccess(Integer numberOfSuccess) {
		this.numberOfSuccess = numberOfSuccess;
	}

	/**
	 * Gets the rg trans id.
	 *
	 * @return the rg trans id
	 */
	public String getRgTransId() {
		return rgTransId;
	}

	/**
	 * Sets the rg trans id.
	 *
	 * @param rgTransId the new rg trans id
	 */
	public void setRgTransId(String rgTransId) {
		this.rgTransId = rgTransId;
	}

	/**
	 * Gets the shopper ip.
	 *
	 * @return the shopper ip
	 */
	public String getShopperIp() {
		return shopperIp;
	}

	/**
	 * Sets the shopper ip.
	 *
	 * @param shopperIp the new shopper ip
	 */
	public void setShopperIp(String shopperIp) {
		this.shopperIp = shopperIp;
	}

	/**
	 * Gets the gateway risk score.
	 *
	 * @return the gateway risk score
	 */
	public String getGatewayRiskScore() {
		return gatewayRiskScore;
	}

	/**
	 * Sets the gateway risk score.
	 *
	 * @param gatewayRiskScore the new gateway risk score
	 */
	public void setGatewayRiskScore(String gatewayRiskScore) {
		this.gatewayRiskScore = gatewayRiskScore;
	}

	/**
	 * Gets the transaction status.
	 *
	 * @return the transaction status
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}

	/**
	 * Sets the transaction status.
	 *
	 * @param transactionStatus the new transaction status
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	/**
	 * Gets the card issuer.
	 *
	 * @return the card issuer
	 */
	public String getCardIssuer() {
		return cardIssuer;
	}

	/**
	 * Sets the card issuer.
	 *
	 * @param cardIssuer the new card issuer
	 */
	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	/**
	 * Gets the card issuer country.
	 *
	 * @return the card issuer country
	 */
	public String getCardIssuerCountry() {
		return cardIssuerCountry;
	}

	/**
	 * Sets the card issuer country.
	 *
	 * @param cardIssuerCountry the new card issuer country
	 */
	public void setCardIssuerCountry(String cardIssuerCountry) {
		this.cardIssuerCountry = cardIssuerCountry;
	}

	/**
	 * Gets the refussal reason.
	 *
	 * @return the refussal reason
	 */
	public String getRefussalReason() {
		return refussalReason;
	}

	/**
	 * Sets the refussal reason.
	 *
	 * @param refussalReason the new refussal reason
	 */
	public void setRefussalReason(String refussalReason) {
		this.refussalReason = refussalReason;
	}

	/**
	 * Gets the debit card type.
	 *
	 * @return the debit card type
	 */
	public String getDebitCardType() {
		return debitCardType;
	}

	/**
	 * Sets the debit card type.
	 *
	 * @param debitCardType the new debit card type
	 */
	public void setDebitCardType(String debitCardType) {
		this.debitCardType = debitCardType;
	}

	/**
	 * Gets the customer insrtuction number.
	 *
	 * @return the customer insrtuction number
	 */
	@JsonProperty("customer_instruction_number")
	public String getCustomerInsrtuctionNumber() {
		return customerInsrtuctionNumber;
	}

	/**
	 * Sets the customer insrtuction number.
	 *
	 * @param customerInsrtuctionNumber the new customer insrtuction number
	 */
	public void setCustomerInsrtuctionNumber(String customerInsrtuctionNumber) {
		this.customerInsrtuctionNumber = customerInsrtuctionNumber;
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
	
	/** Gets the cust legal entity.
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
	 * @return the threedsVersion
	 */
	public String getThreedsVersion() {
		return threedsVersion;
	}

	/**
	 * @param threedsVersion the threedsVersion to set
	 */
	public void setThreedsVersion(String threedsVersion) {
		this.threedsVersion = threedsVersion;
	}

	/**
	 * @return the creditorState
	 */
	public String getDebtorState() {
		return debtorState;
	}

	/**
	 * @param creditorState the creditorState to set
	 */
	public void setDebtorState(String debtorState) {
		this.debtorState = debtorState;
	}

	/**
	 * @return the creditorBankName
	 */
	public String getCreditorBankName() {
		return creditorBankName;
	}

	/**
	 * @param creditorBankName the creditorBankName to set
	 */
	public void setCreditorBankName(String creditorBankName) {
		this.creditorBankName = creditorBankName;
	}

	/**
	 * @return the creditorBankAccountNumber
	 */
	public String getCreditorBankAccountNumber() {
		return creditorBankAccountNumber;
	}

	/**
	 * @param creditorBankAccountNumber the creditorBankAccountNumber to set
	 */
	public void setCreditorBankAccountNumber(String creditorBankAccountNumber) {
		this.creditorBankAccountNumber = creditorBankAccountNumber;
	}

	/**
	 * @return the creditorAccountName
	 */
	public String getCreditorAccountName() {
		return creditorAccountName;
	}

	/**
	 * @param creditorAccountName the creditorAccountName to set
	 */
	public void setCreditorAccountName(String creditorAccountName) {
		this.creditorAccountName = creditorAccountName;
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

}