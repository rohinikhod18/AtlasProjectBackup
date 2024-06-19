package com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class IntuitionFundsOutProviderRequest.
 */
@Getter 
@Setter
public class IntuitionFundsOutProviderRequest implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	/** The trx ID. */
	@JsonProperty(value = "trxID")
	private String trxID;
	
	/** The timestamp. */
	@JsonProperty(value = "timestamp")
	private String timestamp;
	
	/** The trx type. */
	@JsonProperty(value = "trx_type")
	private String trxType;

	/** The org code. */
	@JsonProperty(value = "org_code")
	private String orgCode;
	
	/** The source application. */
	@JsonProperty(value = "source_application")
	private String sourceApplication;
	
	/** The trade account number. */
	@JsonProperty(value = "trade_account_number")
	private String tradeAccountNumber;
	
	/** The trade contact id. */
	@JsonProperty(value = "trade_contact_id")
	private String tradeContactId;
	
	/** The cust yype. */
	@JsonProperty(value = "cust_type")
	private String custType;
	
	/** The purpose of trade. */
	@JsonProperty(value = "purpose_of_trade")
	private String purposeOfTrade;
	
	/** The sellin amount. */
	@JsonProperty(value = "selling_amount")
	private double sellingAmount;
	
	/** The selling amount base value. */
	@JsonProperty(value = "selling_amount_base_value")
	private double sellingAmountBaseValue;
	
	/** The transaction currency. */
	@JsonProperty(value = "transaction_currency")
	private String transactionCurrency;
	
	/** The contract number. */
	@JsonProperty(value = "contract_number")
	private String contractNumber;
	
	/** The payment method. */
	@JsonProperty(value = "payment_Method")
	private String paymentMethod;
	
	/** The cc first name. */
	@JsonProperty(value = "cc_first_name")
	private String ccFirstName;
	
	/** The bill address line. */
	@JsonProperty(value = "bill_address_line")
	private String billAddressLine;
	
	/** The avs result. */
	@JsonProperty(value = "avs_result")
	private String avsResult;
	
	/** The is threeds. */
	@JsonProperty(value = "is_threeds")
	private String isThreeds;
	
	/** The debit card added date. */
	@JsonProperty(value = "debit_card_added_date")
	private String debitCardAddedDate;
	
	/** The av trade value. */
	@JsonProperty(value = "av_trade_value")
	private Integer avTradeValue;
	
	/** The av trade frequency. */
	@JsonProperty(value = "av_trade_frequency")
	private Integer avTradeFrequency;
	
	/** The third party payment. */
	@JsonProperty(value = "third_party_payment")
	private String thirdPartyPayment;
	
	/** The turnover. */
	@JsonProperty(value = "turnover")
	private Integer turnover;
	
	/** The debtor name. */
	@JsonProperty(value = "debtor_name")
	private String debtorName;
	
	/** The debtor account number. */
	@JsonProperty(value = "debtor_account_number")
	private String debtorAccountNumber;
	
	/** The bill ad zip. */
	@JsonProperty(value = "bill_ad_zip")
	private String billAdZip;
	
	/** The country of fund. */
	@JsonProperty(value = "country_of_fund")
	private String countryOfFund;
	
	/** The custome legal entity. */
	@JsonProperty(value = "customer_legal_entity")
	private String customeLegalEntity;
	
	/** The browser user agent. */
	@JsonProperty(value = "browser_user_agent")
	private String browserUserAgent;
	
	/** The screen resolution. */
	@JsonProperty(value = "screen_resolution")
	private String screenResolution;
	
	/** The brwsr type. */
	@JsonProperty(value = "brwsr_type")
	private String brwsrType;
	
	/** The brwsr version. */
	@JsonProperty(value = "brwsr_version")
	private String brwsrVersion;
	
	/** The device type. */
	@JsonProperty(value = "device_type")
	private String deviceType;
	
	/** The device name. */
	@JsonProperty(value = "device_name")
	private String deviceName;
	
	/** The device version. */
	@JsonProperty(value = "device_version")
	private String deviceVersion;
	
	/** The device id. */
	@JsonProperty(value = "device_id")
	private String deviceId;
	
	/** The device manufacturer. */
	@JsonProperty(value = "device_manufacturer")
	private String deviceManufacturer;
	
	/** The os type. */
	@JsonProperty(value = "os_type")
	private String osType;

	/** The brwsr lang. */
	@JsonProperty(value = "brwsr_lang")
	private String brwsrLang;

	
	/** The browser online. */
	@JsonProperty(value = "browser_online")
	private String browserOnline;

	
	/** The message. */
	@JsonProperty(value = "message")
	private String message;

	
	/** The rgid. */
	@JsonProperty(value = "RGID")
	private String RGID;

	
	/** The t score. */
	@JsonProperty(value = "tScore")
	private double tScore;

	
	/** The t risk. */
	@JsonProperty(value = "tRisk")
	private String tRisk;

	
	/** The osr id. */
	@JsonProperty(value = "osr_id")
	private String osrId;

	
	/** The type. */
	@JsonProperty(value = "type")
	private String type;

	
	/** The deal date. */
	@JsonProperty(value = "deal_date")
	private String dealDate;

	
	/** The buying amount. */
	@JsonProperty(value = "buying_amount")
	private Double buyingAmount;

	
	/** The maturity date. */
	@JsonProperty(value = "maturity_date")
	private String maturityDate;

	
	/** The value date. */
	@JsonProperty(value = "value_date")
	private String valueDate;

	
	/** The buying amount base value. */
	@JsonProperty(value = "buying_amount_base_value")
	private Double buyingAmountBaseValue;

	
	/** The buy currency. */
	@JsonProperty(value = "buy_currency")
	private String buyCurrency;

	
	/** The sell currency. */
	@JsonProperty(value = "sell_currency")
	private String sellCurrency;

	/** The phone. */
	@JsonProperty(value = "phone")
	private String phone;
	
	/** The first name. */
	@JsonProperty(value = "first_name")
	private String firstName;
	
	/** The last name. */
	@JsonProperty(value = "last_name")
	private String lastName;
	
	/** The email. */
	@JsonProperty(value = "email")
	private String email;
	
	/** The country. */
	@JsonProperty(value = "country")
	private String country;
	
	/** The account number. */
	@JsonProperty(value = "account_number")
	private String accountNumber;
	
	/** The currency code. */
	@JsonProperty(value = "currency_code")
	private String currencyCode;
	
	/** The beneficiary id. */
	@JsonProperty(value = "beneficiary_id")
	private String beneficiaryId;
	
	/** The beneficiary bankid. */
	@JsonProperty(value = "beneficiary_bankid")
	private String beneficiaryBankid;
	
	/** The beneficary bank name. */
	@JsonProperty(value = "beneficary_bank_name")
	private String beneficaryBankName;
	
	/** The beneficary bank address. */
	@JsonProperty(value = "beneficary_bank_address")
	private String beneficaryBankAddress;
	
	/** The payment reference. */
	@JsonProperty(value = "payment_reference")
	private String paymentReference;
	
	/** The opi created date. */
	@JsonProperty(value = "opi_created_date")
	private String opiCreatedDate;
	
	/** The beneficiary type. */
	@JsonProperty(value = "beneficiary_type")
	private String beneficiaryType;
	
	/** The amount. */
	@JsonProperty(value = "amount")
	private double amount;
	
	/** The version. */
	@JsonProperty(value = "version")
	private String version;
	
	/** The update status. */
	@JsonProperty(value = "update_status")
	private String updateStatus;
	
	/** The status update reason. */
	@JsonProperty(value = "status_update_reason")
	private String statusUpdateReason;
	
	/** The watchlist. */
	@JsonProperty(value = "watchlist")
	private String watchlist;
	
	/** The blacklist. */
	@JsonProperty(value = "blacklist")
	private String blacklist;
	
	/** The country check. */
	@JsonProperty(value = "country_check")
	private String countryCheck;
	
	/** The sanctions 3 rd party. */
	@JsonProperty(value = "sanctions_3rd_party")
	private String sanctions3rdParty;
	
	/** The sanctions contact. */
	@JsonProperty(value = "sanctions_contact")
	private String sanctionsContact;
	
	/** The sanctions beneficiary. */
	@JsonProperty(value = "sanctions_beneficiary")
	private String sanctionsBeneficiary;
	
	/** The sanctions bank. */
	@JsonProperty(value = "sanctions_bank")
	private String sanctionsBank;
	
	/** The fraud predict. */
	@JsonProperty(value = "fraud_predict")
	private String fraudPredict;
	
	/** The custom check. */
	@JsonProperty(value = "custom_check")
	private String customCheck;
	
}
