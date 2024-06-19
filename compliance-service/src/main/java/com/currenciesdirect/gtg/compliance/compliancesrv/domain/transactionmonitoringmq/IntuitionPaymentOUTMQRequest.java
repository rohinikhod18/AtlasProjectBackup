/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class IntuitionPaymentOUTMQRequest.
 */
@Getter 
@Setter
public class IntuitionPaymentOUTMQRequest implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty(value = "TradeIdentificationNumber")
	private String tradeIdentificationNumber;
	
	@JsonProperty(value = "TradeContractNumber")
	private String tradeContractNumber;
	
	@JsonProperty(value = "TradePaymentID")
	private String tradePaymentID;
	
	@JsonProperty(value = "trade_account_number")
	private String tradeAccountNumber;
	
	@JsonProperty(value = "timestamp")
	private String timestamp;
	
	@JsonProperty(value = "deal_date")
	private String dealDate;
	
	@JsonProperty(value = "cust_type")
	private String custType;
	
	@JsonProperty(value = "purpose_of_trade")
	private String purposeOfTrade;
	
	@JsonProperty(value = "buying_amount")
	private Double buyingAmount;
	
	@JsonProperty(value = "buy_currency")
	private String buyCurrency;
	
	@JsonProperty(value = "selling_amount")
	private Double sellingAmount;
	
	@JsonProperty(value = "sell_currency")
	private String sellCurrency;
	
	@JsonProperty(value = "transaction_currency")
	private String transactionCurrency;
	
	@JsonProperty(value = "customer_legal_entity")
	private String customerLegalEntity;
	
	@JsonProperty(value = "payment_Method")
	private String paymentMethod;
	
	@JsonProperty(value = "amount")
	private Double amount;
	
	@JsonProperty(value = "first_name")
	private String firstName;
	
	@JsonProperty(value = "last_name")
	private String lastName;
	
	@JsonProperty(value = "country")
	private String country;
	
	@JsonProperty(value = "email")
	private String email;
	
	@JsonProperty(value = "phone")
	private String phone;
	
	@JsonProperty(value = "account_number")
	private String accountNumber;
	
	@JsonProperty(value = "currency_code")
	private String currencyCode;
	
	@JsonProperty(value = "beneficiary_id")
	private String beneficiaryId;
	
	@JsonProperty(value = "beneficiary_bankid")
	private String beneficiaryBankId;
	
	@JsonProperty(value = "beneficary_bank_name")
	private String beneficaryBankName;
	
	@JsonProperty(value = "beneficary_bank_address")
	private String beneficaryBankAddress;
	
	@JsonProperty(value = "payment_reference")
	private String paymentReference;
	
	@JsonProperty(value = "payment_ref_matched_keyword")
	private String paymentRefMatchedKeyword;
	
	@JsonProperty(value = "maturity_date")
	private String maturityDate;
	
	@JsonProperty(value = "debtor_name")
	private String debtorName;
	
	@JsonProperty(value = "debtor_account_number")
	private String debtorAccountNumber;
	
	@JsonProperty(value = "bill_address_line")
	private String billAddressLine;
	
	@JsonProperty(value = "country_of_fund")
	private String countryOfFund;
	
	@JsonProperty(value = "watchlist")
	private String watchlist;
	
	@JsonProperty(value = "Transactional")
	private List<IntuitionMQFundsTransactional> transactional;
	
	@JsonProperty(value = "Debit_Card")
	private List<IntuitionMQFundsDebitCard> debitCard;
	
	@JsonProperty(value = "Device_and_IP")
	private List<IntuitionMQFundsDeviceAndIP> deviceAndIP;
	
	@JsonProperty(value = "Atlas_Flags")
	private List<IntuitionMQFundsAtlasFlags> atlasFlags;
	
	//AT-5435  
    @JsonProperty(value = "Credit_From")
	private String creditFrom;

    //AT-5578
  	@JsonProperty(value = "compliance_log")
  	private String complianceLog;
}
