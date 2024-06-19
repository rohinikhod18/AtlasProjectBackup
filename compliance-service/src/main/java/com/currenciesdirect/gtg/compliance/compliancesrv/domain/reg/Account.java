package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.FieldDisplayName;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.ConversionPrediction;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.reg.RegistrationEnumValues;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class Account.
 */
public class Account implements Serializable {

	private static final String INCORRECT_FORMAT = "- incorrect format";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(Account.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ERROR. */
	private static final String ERROR = "Error in validation";

	/** The id. */
	@JsonIgnore
	private Integer id;

	/** The acc SFID. */
	@ApiModelProperty(value = "Account Salesforce ID", example = "0010O00001LUEp4FAH", required = true)
	@JsonProperty(value = "acc_sf_id")
	private String accSFID;

	/** The trade account ID. */
	@ApiModelProperty(value = "The Trade Account ID as assigned by Titan", example = "301000006112367", required = true)
	@JsonProperty(value = "trade_acc_id")
	private Integer tradeAccountID;

	/** The trade account number. */
	@ApiModelProperty(value = "The trade account number", required = true)
	@JsonProperty(value = "trade_acc_number")
	private String tradeAccountNumber;

	/** The branch. */
	@ApiModelProperty(value = "The CD organisation branch which the client belongs to", example = "E4F-Johannesburg", required = true)
	@JsonProperty(value = "branch")
	@FieldDisplayName(displayName = "Branch")
	private String branch;

	/** The channel. */
	@ApiModelProperty(value = "The channel which the client used", example = "website or internal", required = true)
	@JsonProperty(value = "channel")
	@FieldDisplayName(displayName = "Channel")
	private String channel;

	/** The cust type. */
	@ApiModelProperty(value = "The customer type (PFX/CFX)", required = true, example = "PFX")
	@JsonProperty(value = "cust_type")
	@FieldDisplayName(displayName = "Customer Type")
	private String custType;

	/** The account name. */
	@ApiModelProperty(value = "Account Name", example = "Bob Nighy", required = true)
	@JsonProperty(value = "act_name")
	@FieldDisplayName(displayName = "Account Name")
	private String accountName;

	/** The country of interest. */
	@ApiModelProperty(value = "The three letter code for the country which client has expressed an interest in during sign-up", example = "GBR", required = true)
	@JsonProperty(value = "country_interest")
	@FieldDisplayName(displayName = "Country Interest")
	private String countryOfInterest;

	/** The trading name. */
	@ApiModelProperty(value = "Trading Name for corporate customers", example = "Alice and Bob Ltd", required = true)
	@JsonProperty(value = "trade_name")
	@FieldDisplayName(displayName = "Trade Name")
	private String tradingName;

	/** The purpose of tran. */
	@ApiModelProperty(value = "The purpose of Transaction (e.g. Bill payments, Yacht)", required = true)
	@JsonProperty(value = "trasaction_purpose")
	@FieldDisplayName(displayName = "Trasaction Purpose")
	private String purposeOfTran;

	/** The countries of operation. */
	@ApiModelProperty(value = "Three letter code for the country of operation", example = "ITA", required = true)
	@JsonProperty(value = "op_country")
	@FieldDisplayName(displayName = "Operation Country")
	private String countriesOfOperation;

	/** The turnover. */
	@ApiModelProperty(value = "Amount of turnover that client said they will probably do when they signed up, used for Fraud analysis", required = true, example = "10000000")
	@JsonProperty(value = "turnover")
	@FieldDisplayName(displayName = "Turnover")
	private String turnover;

	/** The service required. */
	@ApiModelProperty(value = "Type of service that client is interested in (e.g. E-tailer, Transfer, Spot, Incoming Payments)", example = "E-tailer", required = true)
	@JsonProperty(value = "service_req")
	@FieldDisplayName(displayName = "Service Required")
	private String serviceRequired;

	/** The buying currency. */
	@ApiModelProperty(value = "The buying currency", example = "EUR", required = true)
	@JsonProperty(value = "buying_currency")
	@FieldDisplayName(displayName = "Buying Currency")
	private String buyingCurrency;

	/** The selling currency. */
	@ApiModelProperty(value = "The selling currency", example = "GBP", required = true)
	@JsonProperty(value = "selling_currency")
	@FieldDisplayName(displayName = "Selling Currency")
	private String sellingCurrency;

	/** The value of transaction. */
	@ApiModelProperty(value = "The estimated yearly transaction value", example = "100,000 - 250,000", required = true)
	@JsonProperty(value = "txn_value")
	@FieldDisplayName(displayName = "Transaction Value")
	private String valueOfTransaction;

	/** The source of fund. */
	@ApiModelProperty(value = "The source of funds", example = "House Sale, Salary, Savings", required = true)
	@JsonProperty(value = "source_of_fund")
	@FieldDisplayName(displayName = "Source Of Fund")
	private String sourceOfFund;

	/** The average transaction value. */
	@ApiModelProperty(value = "The average value of each transaction", example = "10000", required = true)
	@JsonProperty(value = "avg_txn")
	@FieldDisplayName(displayName = "Average Transaction Value")
	private Double averageTransactionValue;

	/** The account status. */
	@JsonIgnore
	private String accountStatus;

	/** The source. */
	@ApiModelProperty(value = "The marketing channel used by the client when signing up this account (e.g. Affiliate, Self Generated, Web)", example = "Internet", required = true)
	@JsonProperty(value = "source")
	@FieldDisplayName(displayName = "Source")
	private String source;

	/** The sub source. */
	@ApiModelProperty(value = "More specific information on the the marketing channel used by the client when signing up this account (e.g. google)", example = "PayPerClick V1", required = true)
	@JsonProperty(value = "sub_source")
	@FieldDisplayName(displayName = "Sub Source")
	private String subSource;

	/** The referral. */
	@ApiModelProperty(value = "Salesforce ID of the refering client for referrals", example = "0030O00001wVy83QAC", required = true)
	@JsonProperty(value = "referral")
	@FieldDisplayName(displayName = "Referral")
	private String referral;

	/** The referral text. */
	@ApiModelProperty(value = "Marketing information showing how the client was referred", example = "Word of mouth", required = true)
	@JsonProperty(value = "referral_text")
	@FieldDisplayName(displayName = "Referral Text")
	private String referralText;

	/** The extended referral. */
	@ApiModelProperty(value = "Extended marketing information showing how the client was referred", example = "", required = true)
	@JsonProperty(value = "extended_referral")
	@FieldDisplayName(displayName = "Extended Referral")
	private String extendedReferral;

	/** The ad campaign. */
	@ApiModelProperty(value = "The Ad Campaign which client saw before joining", example = "Currency_Forecasts", required = true)
	@JsonProperty(value = "ad_campaign")
	@FieldDisplayName(displayName = "Ad Campaign")
	private String adCampaign;

	/** The keywords. */
	@ApiModelProperty(value = "The keywords retrieved from cookies on client's device, that were used in the search engines when looking for CD", example = "transfer euros into pounds", required = true)
	@JsonProperty(value = "keywords")
	@FieldDisplayName(displayName = "Keywords")
	private String keywords;

	/** The search engine. */
	@ApiModelProperty(value = "The search engine used to come to CD's website", example = "", required = true)
	@JsonProperty(value = "search_engine")
	@FieldDisplayName(displayName = "Search Engine")
	private String searchEngine;

	/** The website. */
	@ApiModelProperty(value = "The website for corporate clients", example = "https://www.aliceandbob.com", required = true)
	@JsonProperty(value = "website")
	@FieldDisplayName(displayName = "Website")
	private String website;

	/** The affiliate name. */
	@ApiModelProperty(value = "Name of the sales rep or associate responsible for signing this client", example = "Marketing HO", required = true)
	@JsonProperty(value = "affiliate_name")
	@FieldDisplayName(displayName = "Affiliate Name")
	private String affiliateName;

	/** The registration mode. */
	@ApiModelProperty(value = "The registration mode", required = true)
	@JsonProperty(value = "reg_mode")
	private String registrationMode;

	/** The org legal entity. */ 
	//Temporary reverting back customer_legal_entity to organization_legal_entity
	@ApiModelProperty(value = "The customer legal entity", required = true)
	@JsonProperty(value = "organization_legal_entity")
	@FieldDisplayName(displayName = "Customer Legal Entity")
	private String custLegalEntity;

	/** The contacts. */
	@ApiModelProperty(value = "The contacts", required = true)
	@JsonProperty(value = "contacts")
	@FieldDisplayName(displayName = "Contacts")
	private List<Contact> contacts = new ArrayList<>();

	/** The version. */
	@ApiModelProperty(value = "The version", required = true)
	@JsonProperty(value = "version")
	private Integer version;

	/** The registration date time. */
	@ApiModelProperty(value = "Registration Date Time", example = "2019-02-27T09:07:27Z", required = true)
	@JsonProperty(value = "reg_date_time")
	@FieldDisplayName(displayName = "Registration Date Time")
	private String registrationDateTime;

	/** below fields for cfx reg *. */
	@JsonProperty(value = "company")
	@FieldDisplayName(displayName = "Company")
	private Company company;

	/** The corperate compliance. */
	@ApiModelProperty(value = "The corporate compliance object, containing data from Dun and Bradstreet", required = true)
	@JsonProperty(value = "corporate_compliance")
	@FieldDisplayName(displayName = "Corporate Compliance")
	private CorperateCompliance corperateCompliance;

	/** The risk profile. */
	@ApiModelProperty(value = "The risk profile for a corporate client based on their liquidity", required = true)
	@JsonProperty(value = "risk_profile")
	@FieldDisplayName(displayName = "Risk Profile")
	private RiskProfile riskProfile;

	/** The conversion Prediction. */
	@ApiModelProperty(value = "The conversion Prediction", required = true)
	@JsonProperty(value = "conversionPrediction")
	@FieldDisplayName(displayName = "Conversion Prediction")
	private ConversionPrediction conversionPrediction;

	/** The affiliate number. */
	@ApiModelProperty(value = "Affiliate Number of the sales rep or associate responsible for signing this client", example = "A00A0399", required = true)
	@JsonProperty(value = "affiliate_number")
	@FieldDisplayName(displayName = "Affiliate Number")
	private String affiliateNumber;
	
	/** The legacy trade account ID. */
	@ApiModelProperty(value = "The legacy Trade Account ID ")
	@JsonProperty(value = "legacy_trade_acc_id")
	private Integer legacyTradeAccountID;

	/** The legacy trade account number. */
	@ApiModelProperty(value = "The legacy trade account number")
	@JsonProperty(value = "legacy_trade_acc_number")
	private String legacyTradeAccountNumber;
	
	// Added for AT-5457
	/** The consumer duty scope. */
	@ApiModelProperty(value = "The consumer duty scope", required = true)
	@JsonProperty(value = "consumer_duty_scope")
	private Boolean consumerDutyScope;

	/**
	 * Below fields are added to set previous service statuses to account when
	 * update account/contact *.
	 */
	@JsonIgnore
	private String previousKycStatus = ServiceStatus.NOT_REQUIRED.toString();

	/** The previous sanction status. */
	@JsonIgnore
	private String previousSanctionStatus = ServiceStatus.NOT_REQUIRED.toString();

	/** The previous fraugster status. */
	@JsonIgnore
	private String previousFraugsterStatus = ServiceStatus.NOT_REQUIRED.toString();

	/** The previous blacklist status. */
	@JsonIgnore
	private String previousBlacklistStatus = ServiceStatus.NOT_REQUIRED.toString();

	/** The previous paymentin watchlist status. */
	@JsonIgnore
	private String previousPaymentinWatchlistStatus = ServiceStatus.NOT_REQUIRED.toString();

	/** The previous paymentout watchlist status. */
	@JsonIgnore
	private String previousPaymentoutWatchlistStatus = ServiceStatus.NOT_REQUIRED.toString();

	/** The is on queue. */
	@JsonIgnore
	private boolean isOnQueue = false;

	/** The customer Type. */
	@JsonIgnore
	private int customerType;

	/** The other currency purpose. */
	@JsonProperty(value = "other_currency_purpose")
	@FieldDisplayName(displayName = "Other Currency Purpose")
	private String otherCurrencyPurpose;

	/** The ad keyword phrases. */
	@JsonProperty(value = "ad_keyword_phrases")
	@FieldDisplayName(displayName = "Ad Keyword Phrases")
	private String adKeywordPhrases;
	
	@JsonIgnore
	private String leUpdateDate;//Add for AT-3349

	@JsonIgnore
	private int accountTmFlag; // Add for AT-4181

	@JsonProperty(value = "cardDeliveryToSecondaryAddress")
	private String cardDeliveryToSecondaryAddress;
		
	//Add for AT-5318
	/** The ad parentsfId phrases. */
    @JsonProperty(value = "parent_sf_acc_id")
    @FieldDisplayName(displayName = "Parent Sf AccId")
	private String parentSfAccId;  
	
	/** The ad parentTitanAcc phrases. */
	@JsonProperty(value = "parent_titan_acc_num")
	@FieldDisplayName(displayName = "Parent Titan AccNum")
	private String parentTitanAccNum; 
	
	/** The ad parentType phrases. */
	@JsonProperty(value = "parent_type")
	@FieldDisplayName(displayName = "Parent Type")
	private String parentType; 
	
	/** The ad selfieOnFile phrases. */
	@JsonProperty(value = "selfie_on_file")
	@FieldDisplayName(displayName = "Selfie On File")
	private Boolean selfieOnFile; 
	
	/** The ad vulnerabilityCharacteristic phrases. */
	@JsonProperty(value = "vulnerability_characteristic")
	@FieldDisplayName(displayName = "Vulnerability Characteristic")
	private String vulnerabilityCharacteristic; 
	
	/** The met in person. */
	@JsonProperty(value = "met_in_person")
	@FieldDisplayName(displayName = "Met In Person")
	private Boolean metInPerson;  
	
	//AT-5376
	/** The number Of Childs */
	@JsonProperty(value = "number_of_childs")
	@FieldDisplayName(displayName = "Number Of Childs")
	private String numberOfChilds; 
	
	/**
	 * @return customer Type
	 */
	public int getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType
	 */
	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}

	/**
	 * Gets the checks if is on queue.
	 *
	 * @return the isOnQueue
	 */
	public boolean isOnQueue() {
		return isOnQueue;
	}

	/**
	 * Sets the checks if is on queue.
	 *
	 * @param isOnQueue
	 *            the isOnQueue to set
	 */
	public void setOnQueue(boolean isOnQueue) {
		this.isOnQueue = isOnQueue;
	}

	/**
	 * Gets the previous paymentin watchlist status.
	 *
	 * @return the previous paymentin watchlist status
	 */
	public String getPreviousPaymentinWatchlistStatus() {
		return previousPaymentinWatchlistStatus;
	}

	/**
	 * Sets the previous paymentin watchlist status.
	 *
	 * @param previousPaymentinWatchlistStatus
	 *            the new previous paymentin watchlist status
	 */
	public void setPreviousPaymentinWatchlistStatus(String previousPaymentinWatchlistStatus) {
		this.previousPaymentinWatchlistStatus = previousPaymentinWatchlistStatus;
	}

	/**
	 * Gets the previous paymentout watchlist status.
	 *
	 * @return the previous paymentout watchlist status
	 */
	public String getPreviousPaymentoutWatchlistStatus() {
		return previousPaymentoutWatchlistStatus;
	}

	/**
	 * Sets the previous paymentout watchlist status.
	 *
	 * @param previousPaymentoutWatchlistStatus
	 *            the new previous paymentout watchlist status
	 */
	public void setPreviousPaymentoutWatchlistStatus(String previousPaymentoutWatchlistStatus) {
		this.previousPaymentoutWatchlistStatus = previousPaymentoutWatchlistStatus;
	}

	/**
	 * Gets the registration date time.
	 *
	 * @return the registration date time
	 */
	public String getRegistrationDateTime() {
		return registrationDateTime;
	}

	/**
	 * Sets the registration date time.
	 *
	 * @param registrationDateTime
	 *            the new registration date time
	 */
	public void setRegistrationDateTime(String registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	/**
	 * Gets the acc SFID.
	 *
	 * @return the acc SFID
	 */
	public String getAccSFID() {
		return accSFID;
	}

	/**
	 * Sets the acc SFID.
	 *
	 * @param accSFID
	 *            the new acc SFID
	 */
	public void setAccSFID(String accSFID) {
		this.accSFID = accSFID;
	}

	/**
	 * Gets the trade account ID.
	 *
	 * @return the trade account ID
	 */
	public Integer getTradeAccountID() {
		return tradeAccountID;
	}

	/**
	 * Sets the trade account ID.
	 *
	 * @param tracdeAccountID
	 *            the new trade account ID
	 */
	public void setTradeAccountID(Integer tracdeAccountID) {
		this.tradeAccountID = tracdeAccountID;
	}

	/**
	 * Gets the trade account number.
	 *
	 * @return the trade account number
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber
	 *            the new trade account number
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * Gets the branch.
	 *
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * Sets the branch.
	 *
	 * @param branch
	 *            the new branch
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * Gets the channel.
	 *
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * Sets the channel.
	 *
	 * @param channel
	 *            the new channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return the cust type
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType
	 *            the new cust type
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the account name.
	 *
	 * @return the account name
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * Sets the account name.
	 *
	 * @param accountName
	 *            the new account name
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * Gets the country of interest.
	 *
	 * @return the country of interest
	 */
	public String getCountryOfInterest() {
		return countryOfInterest;
	}

	/**
	 * Sets the country of interest.
	 *
	 * @param countryOfInterest
	 *            the new country of interest
	 */
	public void setCountryOfInterest(String countryOfInterest) {
		this.countryOfInterest = countryOfInterest;
	}

	/**
	 * Gets the trading name.
	 *
	 * @return the trading name
	 */
	public String getTradingName() {
		return tradingName;
	}

	/**
	 * Sets the trading name.
	 *
	 * @param tradingName
	 *            the new trading name
	 */
	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	/**
	 * Gets the purpose of tran.
	 *
	 * @return the purpose of tran
	 */
	public String getPurposeOfTran() {
		return purposeOfTran;
	}

	/**
	 * Sets the purpose of tran.
	 *
	 * @param purposeOfTran
	 *            the new purpose of tran
	 */
	public void setPurposeOfTran(String purposeOfTran) {
		this.purposeOfTran = purposeOfTran;
	}

	/**
	 * Gets the countries of operation.
	 *
	 * @return the countries of operation
	 */
	public String getCountriesOfOperation() {
		return countriesOfOperation;
	}

	/**
	 * Sets the countries of operation.
	 *
	 * @param countriesOfOperation
	 *            the new countries of operation
	 */
	public void setCountriesOfOperation(String countriesOfOperation) {
		this.countriesOfOperation = countriesOfOperation;
	}

	/**
	 * Gets the turnover.
	 *
	 * @return the turnover
	 */
	public String getTurnover() {
		return turnover;
	}

	/**
	 * Sets the turnover.
	 *
	 * @param turnover
	 *            the new turnover
	 */
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	/**
	 * Gets the service required.
	 *
	 * @return the service required
	 */
	public String getServiceRequired() {
		return serviceRequired;
	}

	/**
	 * Sets the service required.
	 *
	 * @param serviceRequired
	 *            the new service required
	 */
	public void setServiceRequired(String serviceRequired) {
		this.serviceRequired = serviceRequired;
	}

	/**
	 * Gets the buying currency.
	 *
	 * @return the buying currency
	 */
	public String getBuyingCurrency() {
		return buyingCurrency;
	}

	/**
	 * Sets the buying currency.
	 *
	 * @param buyingCurrency
	 *            the new buying currency
	 */
	public void setBuyingCurrency(String buyingCurrency) {
		this.buyingCurrency = buyingCurrency;
	}

	/**
	 * Gets the selling currency.
	 *
	 * @return the selling currency
	 */
	public String getSellingCurrency() {
		return sellingCurrency;
	}

	/**
	 * Sets the selling currency.
	 *
	 * @param sellingCurrency
	 *            the new selling currency
	 */
	public void setSellingCurrency(String sellingCurrency) {
		this.sellingCurrency = sellingCurrency;
	}

	/**
	 * Gets the value of transaction.
	 *
	 * @return the value of transaction
	 */
	public String getValueOfTransaction() {
		return valueOfTransaction;
	}

	/**
	 * Sets the value of transaction.
	 *
	 * @param valueOfTransaction
	 *            the new value of transaction
	 */
	public void setValueOfTransaction(String valueOfTransaction) {
		this.valueOfTransaction = valueOfTransaction;
	}

	/**
	 * Gets the source of fund.
	 *
	 * @return the source of fund
	 */
	public String getSourceOfFund() {
		return sourceOfFund;
	}

	/**
	 * Sets the source of fund.
	 *
	 * @param sourceOfFund
	 *            the new source of fund
	 */
	public void setSourceOfFund(String sourceOfFund) {
		this.sourceOfFund = sourceOfFund;
	}

	/**
	 * Gets the average transaction value.
	 *
	 * @return the average transaction value
	 */
	public Double getAverageTransactionValue() {
		return averageTransactionValue;
	}

	/**
	 * Sets the average transaction value.
	 *
	 * @param averageTransactionValue
	 *            the new average transaction value
	 */
	public void setAverageTransactionValue(Double averageTransactionValue) {
		this.averageTransactionValue = averageTransactionValue;
	}

	/**
	 * Gets the account status.
	 *
	 * @return the account status
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * Sets the account status.
	 *
	 * @param accountStatus
	 *            the new account status
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source
	 *            the new source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Gets the referral.
	 *
	 * @return the referral
	 */
	public String getReferral() {
		return referral;
	}

	/**
	 * Sets the referral.
	 *
	 * @param referral
	 *            the new referral
	 */
	public void setReferral(String referral) {
		this.referral = referral;
	}

	/**
	 * Gets the keywords.
	 *
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * Sets the keywords.
	 *
	 * @param keywords
	 *            the new keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * Gets the website.
	 *
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Sets the website.
	 *
	 * @param website
	 *            the new website
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Gets the contacts.
	 *
	 * @return the contacts
	 */
	public List<Contact> getContacts() {
		return contacts;
	}

	/**
	 * Sets the contacts.
	 *
	 * @param contacts
	 *            the new contacts
	 */
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	/**
	 * Gets the primary contact.
	 *
	 * @return the primary contact
	 */
	@JsonIgnore
	public Contact getPrimaryContact() {
		for (Contact c : contacts) {
			if (Boolean.TRUE.equals(c.getPrimaryContact())) {
				return c;
			}
		}
		return contacts.get(0);
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the sub source.
	 *
	 * @return the sub source
	 */
	public String getSubSource() {
		return subSource;
	}

	/**
	 * Sets the sub source.
	 *
	 * @param subSource
	 *            the new sub source
	 */
	public void setSubSource(String subSource) {
		this.subSource = subSource;
	}

	/**
	 * Gets the referral text.
	 *
	 * @return the referral text
	 */
	public String getReferralText() {
		return referralText;
	}

	/**
	 * Sets the referral text.
	 *
	 * @param referralText
	 *            the new referral text
	 */
	public void setReferralText(String referralText) {
		this.referralText = referralText;
	}

	/**
	 * Gets the extended referral.
	 *
	 * @return the extended referral
	 */
	public String getExtendedReferral() {
		return extendedReferral;
	}

	/**
	 * Sets the extended referral.
	 *
	 * @param extendedReferral
	 *            the new extended referral
	 */
	public void setExtendedReferral(String extendedReferral) {
		this.extendedReferral = extendedReferral;
	}

	/**
	 * Gets the ad campaign.
	 *
	 * @return the ad campaign
	 */
	public String getAdCampaign() {
		return adCampaign;
	}

	/**
	 * Sets the ad campaign.
	 *
	 * @param adCampaign
	 *            the new ad campaign
	 */
	public void setAdCampaign(String adCampaign) {
		this.adCampaign = adCampaign;
	}

	/**
	 * Gets the search engine.
	 *
	 * @return the search engine
	 */
	public String getSearchEngine() {
		return searchEngine;
	}

	/**
	 * Sets the search engine.
	 *
	 * @param searchEngine
	 *            the new search engine
	 */
	public void setSearchEngine(String searchEngine) {
		this.searchEngine = searchEngine;
	}

	/**
	 * Gets the affiliate name.
	 *
	 * @return the affiliate name
	 */
	public String getAffiliateName() {
		return affiliateName;
	}

	/**
	 * Sets the affiliate name.
	 *
	 * @param affiliateName
	 *            the new affiliate name
	 */
	public void setAffiliateName(String affiliateName) {
		this.affiliateName = affiliateName;
	}

	/**
	 * Gets the registration mode.
	 *
	 * @return the registration mode
	 */
	public String getRegistrationMode() {
		return registrationMode;
	}

	/**
	 * Sets the registration mode.
	 *
	 * @param registrationMode
	 *            the new registration mode
	 */
	public void setRegistrationMode(String registrationMode) {
		this.registrationMode = registrationMode;
	}

	/**
	 * Gets the contact by ID.
	 *
	 * @param id
	 *            the id
	 * @return the contact by ID
	 */
	@JsonIgnore
	public Contact getContactByID(Integer id) {
		for (Contact c : contacts) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the corperate compliance.
	 *
	 * @return the corperate compliance
	 */
	public CorperateCompliance getCorperateCompliance() {
		return corperateCompliance;
	}

	/**
	 * Sets the corperate compliance.
	 *
	 * @param corperateCompliance
	 *            the new corperate compliance
	 */
	public void setCorperateCompliance(CorperateCompliance corperateCompliance) {
		this.corperateCompliance = corperateCompliance;
	}

	/**
	 * Gets the risk profile.
	 *
	 * @return the risk profile
	 */
	public RiskProfile getRiskProfile() {
		return riskProfile;
	}

	/**
	 * Sets the risk profile.
	 *
	 * @param riskProfile
	 *            the new risk profile
	 */
	public void setRiskProfile(RiskProfile riskProfile) {
		this.riskProfile = riskProfile;
	}

	/**
	 * Gets the affiliate number.
	 *
	 * @return the affiliate number
	 */
	/*
	 * added by neelesh pant this field has been added to store the
	 * affilate_number from the json signup-request
	 */
	public String getAffiliateNumber() {
		return affiliateNumber;
	}

	/**
	 * Sets the affiliate number.
	 *
	 * @param affiliateNumber
	 *            the new affiliate number
	 */
	/*
	 * added by neelesh pant this field has been added to store the
	 * affilate_number from the json signup-request
	 */
	public void setAffiliateNumber(String affiliateNumber) {
		this.affiliateNumber = affiliateNumber;
	}
	
	

	/**
	 * @return the legacyTradeAccountID
	 */
	public Integer getLegacyTradeAccountID() {
		return legacyTradeAccountID;
	}

	/**
	 * @param legacyTradeAccountID the legacyTradeAccountID to set
	 */
	public void setLegacyTradeAccountID(Integer legacyTradeAccountID) {
		this.legacyTradeAccountID = legacyTradeAccountID;
	}

	/**
	 * @return the legacyTradeAccountNumber
	 */
	public String getLegacyTradeAccountNumber() {
		return legacyTradeAccountNumber;
	}

	/**
	 * @param legacyTradeAccountNumber the legacyTradeAccountNumber to set
	 */
	public void setLegacyTradeAccountNumber(String legacyTradeAccountNumber) {
		this.legacyTradeAccountNumber = legacyTradeAccountNumber;
	}

	public String getCardDeliveryToSecondaryAddress() {
		return cardDeliveryToSecondaryAddress;
	}

	public void setCardDeliveryToSecondaryAddress(
			String cardDeliveryToSecondaryAddress) {
		this.cardDeliveryToSecondaryAddress = cardDeliveryToSecondaryAddress;
	}
	
	

	/**
	 * @return the parentSfAccId
	 */
	public String getParentSfAccId() {
		return parentSfAccId;
	}

	/**
	 * @param parentSfAccId the parentSfAccId to set
	 */
	public void setParentSfAccId(String parentSfAccId) {
		this.parentSfAccId = parentSfAccId;
	}

	/**
	 * @return the parentTitanAccNum
	 */
	public String getParentTitanAccNum() {
		return parentTitanAccNum;
	}

	/**
	 * @param parentTitanAccNum the parentTitanAccNum to set
	 */
	public void setParentTitanAccNum(String parentTitanAccNum) {
		this.parentTitanAccNum = parentTitanAccNum;
	}

	/**
	 * @return the parentType
	 */
	public String getParentType() {
		return parentType;
	}

	/**
	 * @param parentType the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	/**
	 * @return the selfieOnFile
	 */
	public Boolean getSelfieOnFile() {
		return selfieOnFile;
	}

	/**
	 * @param selfieOnFile the selfieOnFile to set
	 */
	public void setSelfieOnFile(Boolean selfieOnFile) {
		this.selfieOnFile = selfieOnFile;
	}

	/**
	 * @return the vulnerabilityCharacteristic
	 */
	public String getVulnerabilityCharacteristic() {
		return vulnerabilityCharacteristic;
	}

	/**
	 * @param vulnerabilityCharacteristic the vulnerabilityCharacteristic to set
	 */
	public void setVulnerabilityCharacteristic(String vulnerabilityCharacteristic) {
		this.vulnerabilityCharacteristic = vulnerabilityCharacteristic;
	}

	/**
	 * @return the metInPerson
	 */
	public Boolean getMetInPerson() {
		return metInPerson;
	}

	/**
	 * @param metInPerson the metInPerson to set
	 */
	public void setMetInPerson(Boolean metInPerson) {
		this.metInPerson = metInPerson;
	}

	/**
	 * @return the numberOfChilds
	 */
	public String getNumberOfChilds() {
		return numberOfChilds;
	}

	/**
	 * @param numberOfChilds the numberOfChilds to set
	 */
	public void setNumberOfChilds(String numberOfChilds) {
		this.numberOfChilds = numberOfChilds;
	}
	
	/**
	 * @return the consumerDutyScope
	 */
	public Boolean getConsumerDutyScope() {
		return consumerDutyScope;
	}

	/**
	 * @param consumerDutyScope the consumerDutyScope to set
	 */
	public void setConsumerDutyScope(Boolean consumerDutyScope) {
		this.consumerDutyScope = consumerDutyScope;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accSFID=" + accSFID + ", tradeAccountID=" + tradeAccountID
				+ ", tradeAccountNumber=" + tradeAccountNumber + ", branch=" + branch + ", channel=" + channel
				+ ", custType=" + custType + ", accountName=" + accountName + ", countryOfInterest=" + countryOfInterest
				+ ", tradingName=" + tradingName + ", purposeOfTran=" + purposeOfTran + ", countriesOfOperation="
				+ countriesOfOperation + ", turnover=" + turnover + ", serviceRequired=" + serviceRequired
				+ ", buyingCurrency=" + buyingCurrency + ", sellingCurrency=" + sellingCurrency
				+ ", valueOfTransaction=" + valueOfTransaction + ", sourceOfFund=" + sourceOfFund
				+ ", averageTransactionValue=" + averageTransactionValue + ", accountStatus=" + accountStatus
				+ ", source=" + source + ", subSource=" + subSource + ", referral=" + referral + ", referralText="
				+ referralText + ", extendedReferral=" + extendedReferral + ", adCampaign=" + adCampaign + ", keywords="
				+ keywords + ", searchEngine=" + searchEngine + ", website=" + website + ", affiliateName="
				+ affiliateName + ", registrationMode=" + registrationMode + ", custLegalEntity=" + custLegalEntity
				+ ", contacts=" + contacts + ", version=" + version + ", registrationDateTime=" + registrationDateTime
				+ ", company=" + company + ", corperateCompliance=" + corperateCompliance + ", riskProfile="
				+ riskProfile + ", conversionPrediction=" + conversionPrediction + ", affiliateNumber="
				+ affiliateNumber + ", legacyTradeAccountID=" + legacyTradeAccountID + ", legacyTradeAccountNumber="
				+ legacyTradeAccountNumber + ", previousKycStatus=" + previousKycStatus + ", previousSanctionStatus="
				+ previousSanctionStatus + ", previousFraugsterStatus=" + previousFraugsterStatus
				+ ", previousBlacklistStatus=" + previousBlacklistStatus + ", previousPaymentinWatchlistStatus="
				+ previousPaymentinWatchlistStatus + ", previousPaymentoutWatchlistStatus="
				+ previousPaymentoutWatchlistStatus + ", isOnQueue=" + isOnQueue + ", customerType=" + customerType
				+ ", otherCurrencyPurpose=" + otherCurrencyPurpose + ", adKeywordPhrases=" + adKeywordPhrases
				+ ", leUpdateDate=" + leUpdateDate + ", accountTmFlag=" + accountTmFlag
				+ ", cardDeliveryToSecondaryAddress=" + cardDeliveryToSecondaryAddress + ", parentSfAccId="
				+ parentSfAccId + ", parentTitanAccNum=" + parentTitanAccNum + ", parentType=" + parentType
				+ ", selfieOnFile=" + selfieOnFile + ", vulnerabilityCharacteristic=" + vulnerabilityCharacteristic
				+ ", metInPerson=" + metInPerson + ",numberOfChilds=" + numberOfChilds +", consumerDutyScope="+ consumerDutyScope + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@SuppressWarnings("squid:S3776")
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accSFID == null) ? 0 : accSFID.hashCode());
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((accountStatus == null) ? 0 : accountStatus.hashCode());
		result = prime * result + ((adCampaign == null) ? 0 : adCampaign.hashCode());
		result = prime * result + ((affiliateName == null) ? 0 : affiliateName.hashCode());
		result = prime * result + ((averageTransactionValue == null) ? 0 : averageTransactionValue.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((buyingCurrency == null) ? 0 : buyingCurrency.hashCode());
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result + ((corperateCompliance == null) ? 0 : corperateCompliance.hashCode());
		result = prime * result + ((countriesOfOperation == null) ? 0 : countriesOfOperation.hashCode());
		result = prime * result + ((countryOfInterest == null) ? 0 : countryOfInterest.hashCode());
		result = prime * result + ((custType == null) ? 0 : custType.hashCode());
		result = prime * result + ((extendedReferral == null) ? 0 : extendedReferral.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
		result = prime * result + ((purposeOfTran == null) ? 0 : purposeOfTran.hashCode());
		result = prime * result + ((referral == null) ? 0 : referral.hashCode());
		result = prime * result + ((referralText == null) ? 0 : referralText.hashCode());
		result = prime * result + ((registrationDateTime == null) ? 0 : registrationDateTime.hashCode());
		result = prime * result + ((registrationMode == null) ? 0 : registrationMode.hashCode());
		result = prime * result + ((riskProfile == null) ? 0 : riskProfile.hashCode());
		result = prime * result + ((searchEngine == null) ? 0 : searchEngine.hashCode());
		result = prime * result + ((sellingCurrency == null) ? 0 : sellingCurrency.hashCode());
		result = prime * result + ((serviceRequired == null) ? 0 : serviceRequired.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((sourceOfFund == null) ? 0 : sourceOfFund.hashCode());
		result = prime * result + ((subSource == null) ? 0 : subSource.hashCode());
		result = prime * result + ((tradeAccountID == null) ? 0 : tradeAccountID.hashCode());
		result = prime * result + ((tradeAccountNumber == null) ? 0 : tradeAccountNumber.hashCode());
		result = prime * result + ((legacyTradeAccountID == null) ? 0 : legacyTradeAccountID.hashCode());
		result = prime * result + ((legacyTradeAccountNumber == null) ? 0 : legacyTradeAccountNumber.hashCode());
		result = prime * result + ((tradingName == null) ? 0 : tradingName.hashCode());
		result = prime * result + ((turnover == null) ? 0 : turnover.hashCode());
		result = prime * result + ((valueOfTransaction == null) ? 0 : valueOfTransaction.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
		result = prime * result + ((parentSfAccId == null) ? 0 : parentSfAccId.hashCode());
		result = prime * result + ((parentTitanAccNum == null) ? 0 : parentTitanAccNum.hashCode());
		result = prime * result + ((parentType == null) ? 0 : parentType.hashCode());
		result = prime * result + ((selfieOnFile == null) ? 0 : selfieOnFile.hashCode());
		result = prime * result + ((vulnerabilityCharacteristic == null) ? 0 : vulnerabilityCharacteristic.hashCode());
		result = prime * result + ((metInPerson == null) ? 0 : metInPerson.hashCode());
		result = prime * result + ((numberOfChilds == null) ? 0 : numberOfChilds.hashCode());//AT-5376
		result = prime * result + ((consumerDutyScope == null) ? 0 : consumerDutyScope.hashCode());//AT-5457
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("squid:S3776")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accSFID == null) {
			if (other.accSFID != null)
				return false;
		} else if (!accSFID.equals(other.accSFID)) {
			return false;
		  }
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName)) {
			return false;
		  }
		if (accountStatus == null) {
			if (other.accountStatus != null)
				return false;
		} else if (!accountStatus.equals(other.accountStatus)) {
			return false;
		  }
		if (adCampaign == null) {
			if (other.adCampaign != null)
				return false;
		} else if (!adCampaign.equals(other.adCampaign)) {
			return false;
		  }
		if (affiliateName == null) {
			if (other.affiliateName != null)
				return false;
		} else if (!affiliateName.equals(other.affiliateName)) {
			return false;
		  }
		if (averageTransactionValue == null) {
			if (other.averageTransactionValue != null)
				return false;
		} else if (!averageTransactionValue.equals(other.averageTransactionValue)) {
			return false;
		  }
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch)) {
			return false;
		  }
		if (buyingCurrency == null) {
			if (other.buyingCurrency != null)
				return false;
		} else if (!buyingCurrency.equals(other.buyingCurrency)) {
			return false;
		  }
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel)) {
			return false;
		  }
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company)) {
			return false;
		  }
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts)) {
			return false;
		  }
		if (corperateCompliance == null) {
			if (other.corperateCompliance != null)
				return false;
		} else if (!corperateCompliance.equals(other.corperateCompliance)) {
			return false;
		  }
		if (countriesOfOperation == null) {
			if (other.countriesOfOperation != null)
				return false;
		} else if (!countriesOfOperation.equals(other.countriesOfOperation)) {
			return false;
		  }
		if (countryOfInterest == null) {
			if (other.countryOfInterest != null)
				return false;
		} else if (!countryOfInterest.equals(other.countryOfInterest)) {
			return false;
		  }
		if (custType == null) {
			if (other.custType != null)
				return false;
		} else if (!custType.equals(other.custType)) {
			return false;
		  }
		if (extendedReferral == null) {
			if (other.extendedReferral != null)
				return false;
		} else if (!extendedReferral.equals(other.extendedReferral)) {
			return false;
		  }
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		  }
		if (keywords == null) {
			if (other.keywords != null)
				return false;
		} else if (!keywords.equals(other.keywords)) {
			return false;
		  }
		if (purposeOfTran == null) {
			if (other.purposeOfTran != null)
				return false;
		} else if (!purposeOfTran.equals(other.purposeOfTran)) {
			return false;
		  }
		if (referral == null) {
			if (other.referral != null)
				return false;
		} else if (!referral.equals(other.referral)) {
			return false;
		  }
		if (referralText == null) {
			if (other.referralText != null)
				return false;
		} else if (!referralText.equals(other.referralText)) {
			return false;
		  }
		if (registrationDateTime == null) {
			if (other.registrationDateTime != null)
				return false;
		} else if (!registrationDateTime.equals(other.registrationDateTime)) {
			return false;
		  }
		if (registrationMode == null) {
			if (other.registrationMode != null)
				return false;
		} else if (!registrationMode.equals(other.registrationMode)) {
			return false;
		  }
		if (riskProfile == null) {
			if (other.riskProfile != null)
				return false;
		} else if (!riskProfile.equals(other.riskProfile)) {
			return false;
		  }
		if (searchEngine == null) {
			if (other.searchEngine != null)
				return false;
		} else if (!searchEngine.equals(other.searchEngine)) {
			return false;
		  }
		if (sellingCurrency == null) {
			if (other.sellingCurrency != null)
				return false;
		} else if (!sellingCurrency.equals(other.sellingCurrency)) {
			return false;
		  }
		if (serviceRequired == null) {
			if (other.serviceRequired != null)
				return false;
		} else if (!serviceRequired.equals(other.serviceRequired)) {
			return false;
		  }
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source)) {
			return false;
		  }
		if (sourceOfFund == null) {
			if (other.sourceOfFund != null)
				return false;
		} else if (!sourceOfFund.equals(other.sourceOfFund)) {
			return false;
		  }
		if (subSource == null) {
			if (other.subSource != null)
				return false;
		} else if (!subSource.equals(other.subSource)) {
			return false;
		  }
		if (tradeAccountID == null) {
			if (other.tradeAccountID != null)
				return false;
		} else if (!tradeAccountID.equals(other.tradeAccountID)) {
			return false;
		  }
		if (tradeAccountNumber == null) {
			if (other.tradeAccountNumber != null)
				return false;
		} else if (!tradeAccountNumber.equals(other.tradeAccountNumber)) {
			return false;
		  }
		if (legacyTradeAccountID == null) {
			if (other.legacyTradeAccountID != null)
				return false;
		} else if (!legacyTradeAccountID.equals(other.legacyTradeAccountID)) {
			return false;
		  }
		if (legacyTradeAccountNumber == null) {
			if (other.legacyTradeAccountNumber != null)
				return false;
		} else if (!legacyTradeAccountNumber.equals(other.legacyTradeAccountNumber)) {
			return false;
		  }
		if (tradingName == null) {
			if (other.tradingName != null)
				return false;
		} else if (!tradingName.equals(other.tradingName)) {
			return false;
		  }
		if (turnover == null) {
			if (other.turnover != null)
				return false;
		} else if (!turnover.equals(other.turnover)) {
			return false;
		  }
		if (valueOfTransaction == null) {
			if (other.valueOfTransaction != null)
				return false;
		} else if (!valueOfTransaction.equals(other.valueOfTransaction)) {
			return false;
		  }
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version)) {
			return false;
		  }
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website)) {
			return false;
		  }
		if (parentSfAccId == null) {
			if (other.parentSfAccId != null)
				return false;
		} else if (!parentSfAccId.equals(other.parentSfAccId)) {
			return false;
		  }
		if (parentTitanAccNum == null) {
			if (other.parentTitanAccNum != null)
				return false;
		} else if (!parentTitanAccNum.equals(other.parentTitanAccNum)) {
			return false;
		  }
		if (parentType == null) {
			if (other.parentType != null)
				return false;
		} else if (!parentType.equals(other.parentType)) {
			return false;
		  }
		if (selfieOnFile == null) {
			if (other.selfieOnFile != null)
				return false;
		} else if (!selfieOnFile.equals(other.selfieOnFile)) {
			return false;
		  }
		if (vulnerabilityCharacteristic == null) {
			if (other.vulnerabilityCharacteristic != null)
				return false;
		} else if (!vulnerabilityCharacteristic.equals(other.vulnerabilityCharacteristic)) {
			return false;
		  }
		if (metInPerson == null) {
			if (other.metInPerson != null)
				return false;
		} else if (!metInPerson.equals(other.metInPerson)) {
			return false;
		  }
		//AT-5376
		if (numberOfChilds == null) {
			if (other.numberOfChilds != null)
				return false;
		} else if (!numberOfChilds.equals(other.numberOfChilds)) {
			return false;
		  }
			// AT-5457
			if (consumerDutyScope == null) {
				if (other.consumerDutyScope != null)
					return false;
			} else if (!consumerDutyScope.equals(other.consumerDutyScope)) {
				return false;
			}
		return true;
	}

	/**
	 * Adds the contact.
	 *
	 * @param c
	 *            the c
	 */
	public void addContact(Contact c) {
		if (null == contacts)
			contacts = new ArrayList<>();
		contacts.add(c);
	}

	/**
	 * Gets the max transaction amount.
	 *
	 * @return the max transaction amount
	 */
	@JsonIgnore
	public Double getMaxTransactionAmount() {
		Double result = 0.0;
		if (!StringUtils.isNullOrEmpty(valueOfTransaction)) {
			try {
				Integer pos = valueOfTransaction.lastIndexOf(' ');
				if (!(null != pos && pos > 0))
					pos = valueOfTransaction.lastIndexOf('-');
				String amt = valueOfTransaction.substring(pos + 1).trim().replace("-", "").replace(",", "");
				result = Math.abs(Double.parseDouble(amt));
			} catch (Exception ex) {
				LOGGER.debug("Cannot determine max Transaction value", ex);
			}
		}
		return result;
	}

	/**
	 * Validate sign up.
	 *
	 * @return the field validator
	 */
	public FieldValidator validateSignUp() {
		FieldValidator validator;

		this.setCustType(RegistrationEnumValues.getInstance().checkCustomerTypes(this.custType));
		if (Constants.CFX.equalsIgnoreCase(this.getCustType())) {
			validator = validateAccountFieldsForCFX();
		} else {
			validator = validateAccountForRegistration();
		}
		validator.mergeErrors(checkContactsExists());
		validator.mergeErrors(validateContactSignup());
		if (!StringUtils.isNullOrTrimEmpty(this.registrationDateTime)) {
			validator.isDateInFormat(new String[] { this.registrationDateTime }, new String[] { "reg_date_time" },
					Constants.RFC_TIMESTAMP_FORMAT);
		}
		if (this.accSFID.length() != 18) {
			validator.addError(INCORRECT_FORMAT, Constants.FIELD_ACC_ACC_SF_ID);
		}
		return validator;
	}

	/**
	 * Validate account for registration.
	 *
	 * @return the field validator
	 */
	private FieldValidator validateAccountForRegistration() {
		FieldValidator validator = new FieldValidator();
		try {
			validator.isValidObject(
					// AT-1505 - Mobile Registration Changes
					new Object[] { this.getAccountName(), this.getAccSFID(), this.getTradeAccountID(),
							this.getTradeAccountNumber(),
							this.getBranch(), /* this.getBuyingCurrency(), */
							this.getCustType(), /* this.getSellingCurrency(), */ this.getServiceRequired(),
							/* this.getSourceOfFund(), this.getSubSource(), */ this.getRegistrationDateTime(),
							this.getCustLegalEntity() },
					new String[] { Constants.FIELD_ACC_NAME, Constants.FIELD_ACC_ACC_SF_ID,
							Constants.FIELD_TRADE_ACC_ID, Constants.FIELD_TRADE_ACC_NUMBER, Constants.FIELD_BRANCH,
							/* "buying_currency", */ Constants.FIELD_ACC_CUST_TYPE,
							/* "selling_currency", */ Constants.FIELD_SERVICE_REQ, /*
																					 * "source_of_fund",
																					 * "sub_source",
																					 */
							Constants.FIELD_ACC_REG_DATE_TIME, Constants.ORGANIZATION_LEGAL_ENTITY });

			validateCountry(validator, "country_interest", this.countryOfInterest);
			validateEnums(validator);
		} catch (Exception e) {
			LOGGER.error(ERROR, e);
		}
		return validator;
	}

	/**
	 * Validate enums.
	 *
	 * @param validator
	 *            the validator
	 */
	private void validateEnums(FieldValidator validator) {
		RegistrationEnumValues enumValues = RegistrationEnumValues.getInstance();
		if (!StringUtils.isNullOrTrimEmpty(this.getValueOfTransaction())
				&& enumValues.checkTransactionValues(this.getValueOfTransaction()) == null) {
			validator.addError("Incorrect", "txn_value");
		}
	}

	/**
	 * Validate contact signup.
	 *
	 * @return the field validator
	 */
	public FieldValidator validateContactSignup() {

		FieldValidator validator = new FieldValidator();
		for (Contact contact : this.getContacts()) {
			validator.mergeErrors(contact.validateSignup(this.custType));
		}
		return validator;
	}

	/**
	 * Validate add contact.
	 *
	 * @return the field validator
	 */
	public FieldValidator validateAddContact() {
		FieldValidator validator = new FieldValidator();
		validator.isValidObject(new Object[] { this.getAccSFID(), this.getCustType(), },
				new String[] { "acc_sf_id", "cust_type", });
		if (this.accSFID.length() != 18) {
			validator.addError(INCORRECT_FORMAT, Constants.FIELD_ACC_ACC_SF_ID);
		}
		if (validator.isFailed()) {
			validator.mergeErrors(validateContactSignup());
		}
		return validator;
	}

	/**
	 * Validate account fields for CFX.
	 *
	 * @return the field validator
	 */
	private FieldValidator validateAccountFieldsForCFX() {
		FieldValidator validator = new FieldValidator();
		try {
			// AT-1505 - Mobile registration changes
			validator.isValidObject(
					new Object[] { this.getAccountName(), this.getAccSFID(),
							this.getTradeAccountID(), this.getTradeAccountNumber(),
							this.getBranch(), /* this.getBuyingCurrency(), */
							this.getCustType(), /* this.getSellingCurrency(), */this.getServiceRequired(),
							/* this.getSourceOfFund(), this.getSubSource(), */ this.getRegistrationDateTime(),
							this.getAverageTransactionValue(),

							/* this.getWebsite(), */this.getCompany(), this.getCustLegalEntity() },
					new String[] { Constants.FIELD_ACC_NAME, Constants.FIELD_ACC_ACC_SF_ID,
							Constants.FIELD_TRADE_ACC_ID, Constants.FIELD_TRADE_ACC_NUMBER, Constants.FIELD_BRANCH,
							/* "buying_currency", */ Constants.FIELD_ACC_CUST_TYPE,
							/* "selling_currency", */ Constants.FIELD_SERVICE_REQ, /*
																					 * "source_of_fund"
																					 * ,
																					 * "sub_source",
																					 */
							Constants.FIELD_ACC_REG_DATE_TIME, Constants.FIELD_AVERAGE_TRANSACTION,
							/* "website", */ Constants.FIELD_COMPANY, Constants.ORGANIZATION_LEGAL_ENTITY });

			validateCountries(validator);
		} catch (Exception e) {                                                              
			LOGGER.error(ERROR, e);
		}
		return validator;
	}

	/**            
	 * Validate countries.
	 *
	 * @param validator
	 *            the validator
	 */
	private void validateCountries(FieldValidator validator) {
		validateCountry(validator, "country_interest", this.countryOfInterest);
		if (null != this.getCompany()) {
			validateCountry(validator, "country_of_establishment", this.getCompany().getCountryOfEstablishment());
			validateCountry(validator, "country_region", this.getCompany().getCountryRegion());
		}
	}

	/**
	 * Validate country.
	 *
	 * @param validator
	 *            the validator
	 * @param field
	 *            the field
	 * @param country
	 *            the country
	 */
	private void validateCountry(FieldValidator validator, String field, String country) {
		ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();
		try {
			if (!StringUtils.isNullOrTrimEmpty(country) && countryCache.getCountryFullName(country) == null) {
				validator.addError("Incorrect country code", field);
			}
		} catch (ComplianceException e) {
			LOGGER.error(ERROR, e);
		}

	}

	/**
	 * Validate update account.
	 *
	 * @return the field validator
	 */
	public FieldValidator validateUpdateAccount() {
		FieldValidator validator = new FieldValidator();
		try {
			if (this.accSFID.length() != 18) {
				validator.addError(INCORRECT_FORMAT, Constants.FIELD_ACC_ACC_SF_ID);
			}

			validator.isValidObjectOnUpdate(
					new Object[] { this.getAccountName(), this.getBranch(), this.getCustType(),
							this.getServiceRequired(), this.getRegistrationDateTime(),
							this.getAverageTransactionValue(), this.getCompany(), this.getCustLegalEntity() },
					new String[] { Constants.FIELD_ACC_NAME, Constants.FIELD_BRANCH, Constants.FIELD_ACC_CUST_TYPE,
							Constants.FIELD_SERVICE_REQ, Constants.FIELD_ACC_REG_DATE_TIME,
							Constants.FIELD_AVERAGE_TRANSACTION, Constants.FIELD_COMPANY,
							Constants.ORGANIZATION_LEGAL_ENTITY });
			validateCountries(validator);
			validateEnums(validator);

			for (Contact contact : this.getContacts()) {
				validator.mergeErrors(contact.validateUpdateContact());
			}
			if (this.getRegistrationDateTime() != null && !this.getRegistrationDateTime().isEmpty()) {
				validator.isDateInFormat(new String[] { this.getRegistrationDateTime() },
						new String[] { Constants.FIELD_ACC_REG_DATE_TIME }, Constants.RFC_TIMESTAMP_FORMAT);
			}
		} catch (Exception e) {
			LOGGER.error("Error in validation ", e);
		}
		return validator;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the previous kyc status.
	 *
	 * @return the previous kyc status
	 */
	public String getPreviousKycStatus() {
		return previousKycStatus;
	}

	/**
	 * Sets the previous kyc status.
	 *
	 * @param previousKycStatus
	 *            the new previous kyc status
	 */
	public void setPreviousKycStatus(String previousKycStatus) {
		this.previousKycStatus = previousKycStatus;
	}

	/**
	 * Gets the previous sanction status.
	 *
	 * @return the previous sanction status
	 */
	public String getPreviousSanctionStatus() {
		return previousSanctionStatus;
	}

	/**
	 * Sets the previous sanction status.
	 *
	 * @param previousSanctionStatus
	 *            the new previous sanction status
	 */
	public void setPreviousSanctionStatus(String previousSanctionStatus) {
		this.previousSanctionStatus = previousSanctionStatus;
	}

	/**
	 * Gets the previous fraugster status.
	 *
	 * @return the previous fraugster status
	 */
	public String getPreviousFraugsterStatus() {
		return previousFraugsterStatus;
	}

	/**
	 * Sets the previous fraugster status.
	 *
	 * @param previousFraugsterStatus
	 *            the new previous fraugster status
	 */
	public void setPreviousFraugsterStatus(String previousFraugsterStatus) {
		this.previousFraugsterStatus = previousFraugsterStatus;
	}

	/**
	 * Gets the previous blacklist status.
	 *
	 * @return the previous blacklist status
	 */
	public String getPreviousBlacklistStatus() {
		return previousBlacklistStatus;
	}

	/**
	 * Sets the previous blacklist status.
	 *
	 * @param previousBlacklistStatus
	 *            the new previous blacklist status
	 */
	public void setPreviousBlacklistStatus(String previousBlacklistStatus) {
		this.previousBlacklistStatus = previousBlacklistStatus;
	}

	/**
	 * Gets the conversion prediction.
	 *
	 * @return the conversion prediction
	 */
	public ConversionPrediction getConversionPrediction() {
		return conversionPrediction;
	}

	/**
	 * Sets the conversion prediction.
	 *
	 * @param conversionPrediction
	 *            the new conversion prediction
	 */
	public void setConversionPrediction(ConversionPrediction conversionPrediction) {
		this.conversionPrediction = conversionPrediction;
	}

	/**
	 * Gets the other currency purpose.
	 *
	 * @return the other currency purpose
	 */
	public String getOtherCurrencyPurpose() {
		return otherCurrencyPurpose;
	}

	/**
	 * Sets the other currency purpose.
	 *
	 * @param otherCurrencyPurpose
	 *            the new other currency purpose
	 */
	public void setOtherCurrencyPurpose(String otherCurrencyPurpose) {
		this.otherCurrencyPurpose = otherCurrencyPurpose;
	}

	/**
	 * Gets the ad keyword phrases.
	 *
	 * @return the ad keyword phrases
	 */
	public String getAdKeywordPhrases() {
		return adKeywordPhrases;
	}

	/**
	 * Sets the ad keyword phrases.
	 *
	 * @param adKeywordPhrases
	 *            the new ad keyword phrases
	 */
	public void setAdKeywordPhrases(String adKeywordPhrases) {
		this.adKeywordPhrases = adKeywordPhrases;
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
	 * Gets the le update date.
	 *
	 * @return the le update date
	 */
	public String getLeUpdateDate() {
		return leUpdateDate;
	}

	/**
	 * Sets the le update date.
	 *
	 * @param leUpdateDate the new le update date
	 */
	public void setLeUpdateDate(String leUpdateDate) {
		this.leUpdateDate = leUpdateDate;
	}

	/**
	 * Check contacts exists.
	 *
	 * @return the field validator
	 */
	public FieldValidator checkContactsExists() {

		FieldValidator validator = new FieldValidator();
		if (this.getContacts().isEmpty()) {
			validator.addError(" - Contacts Not Present In Signup", "contacts");
		}
		return validator;
	}

}
