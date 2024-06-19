package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionMonitoringAccountRequest.
 */
public class TransactionMonitoringAccountRequest implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The acc SFID. */
	@JsonProperty(value = "acc_sf_id")
	private String accSFID;

	/** The trade account ID. */
	@JsonProperty(value = "trade_acc_id")
	private Integer tradeAccountID;

	/** The trade account number. */

	@JsonProperty(value = "trade_acc_number")
	private String tradeAccountNumber;

	/** The branch. */
	@JsonProperty(value = "branch")
	private String branch;

	/** The channel. */
	@JsonProperty(value = "channel")
	private String channel;

	/** The cust type. */
	@JsonProperty(value = "cust_type")
	private String custType;

	/** The account name. */
	@JsonProperty(value = "act_name")
	private String accountName;

	/** The country of interest. */
	@JsonProperty(value = "country_interest")
	private String countryOfInterest;

	/** The trading name. */
	@JsonProperty(value = "trade_name")
	private String tradingName;

	/** The purpose of tran. */
	@JsonProperty(value = "trasaction_purpose")
	private String purposeOfTran;

	/** The countries of operation. */
	@JsonProperty(value = "op_country")
	private String countriesOfOperation;

	/** The turnover. */
	@JsonProperty(value = "turnover")
	private Integer turnover;

	/** The service required. */
	@JsonProperty(value = "service_req")
	private String serviceRequired;

	/** The buying currency. */
	@JsonProperty(value = "buying_currency")
	private String buyingCurrency;

	/** The selling currency. */
	@JsonProperty(value = "selling_currency")
	private String sellingCurrency;

	/** The value of transaction. */
	@JsonProperty(value = "txn_value")
	private String valueOfTransaction;

	/** The source of fund. */
	@JsonProperty(value = "source_of_fund")
	private String sourceOfFund;

	/** The average transaction value. */
	@JsonProperty(value = "avg_txn")
	private Double averageTransactionValue;

	/** The source. */
	@JsonProperty(value = "source")
	private String source;

	/** The sub source. */
	@JsonProperty(value = "sub_source")
	private String subSource;

	/** The referral. */
	@JsonProperty(value = "referral")
	private String referral;

	/** The referral text. */
	@JsonProperty(value = "referral_text")
	private String referralText;

	/** The extended referral. */
	@JsonProperty(value = "extended_referral")
	private String extendedReferral;

	/** The ad campaign. */
	@JsonProperty(value = "ad_campaign")
	private String adCampaign;

	/** The keywords. */
	@JsonProperty(value = "keywords")
	private String keywords;

	/** The search engine. */
	@JsonProperty(value = "search_engine")
	private String searchEngine;

	/** The website. */
	@JsonProperty(value = "website")
	private String website;

	/** The affiliate name. */
	@JsonProperty(value = "affiliate_name")
	private String affiliateName;

	/** The affiliate number. */
	@JsonProperty(value = "affiliate_number")
	private String affiliateNumber;

	/** The registration mode. */
	@JsonProperty(value = "reg_mode")
	private String registrationMode;

	/** The registration date time. */
	@JsonProperty(value = "reg_date_time")
	private String registrationDateTime;

	/** below fields for cfx reg *. */
	@JsonProperty(value = "company")
	private Company company;

	/** The corporate compliance. */
	@JsonProperty(value = "corporate_compliance")
	private CorperateCompliance corperateCompliance;

	/** The risk profile. */
	@JsonProperty(value = "risk_profile")
	private RiskProfile riskProfile;
	
	/** The conversion Prediction. */
	@JsonProperty(value="conversionPrediction")
	private ConversionPrediction conversionPrediction;

	/** The org legal entity. */ 
	@JsonProperty(value = "organization_legal_entity")
	private String custLegalEntity;
	
	/** The id. */
	private Integer id;

	/** The currency pair. */
	private String currencyPair;

	/** The client type. */
	private String clientType;
	
	/** The other currency purpose. */
	@JsonProperty(value = "other_currency_purpose")
	private String otherCurrencyPurpose;
	
	/** The ad keyword phrases. */
	@JsonProperty(value = "ad_keyword_phrases")
	private String adKeywordPhrases;
	
	/** The phone reg. */
	@JsonProperty(value = "phone_reg")
	private String phoneReg;

	/** The enquiry date. */
	@JsonProperty(value = "enquiry_date")
	private String enquiryDate;

	/** The online account status. */
	@JsonProperty(value = "online_account_status")
	private String onlineAccountStatus;
	
	/** The is account modified. */
	private boolean isAccountModified;
	
	/** The sanction result. */
	@JsonProperty(value = "sanction_result")
	private String sanctionResult;

	/** The blacklist. */
	@JsonProperty(value = "blacklist")
	private String blacklist;
	
	/** The account status. */
	@JsonProperty(value = "Account_Status")
	private String accountStatus;
	
	/** The created on. */
	private String createdOn;
	
	/** The compliance log. */	
	@JsonProperty(value = "compliance_log")	
	private String complianceLog;	
		
	private IPAddressDetails iPAddressDetails;

	@JsonProperty(value = "cardDeliveryToSecondaryAddress")
	private String cardDeliveryToSecondaryAddress;

	@JsonProperty(value = "secondaryAddressPresent")
	private String secondaryAddressPresent;
	
	//Add for AT-5318
	/** The ad parentsfId phrases. */
	@JsonProperty(value = "parent_sf_acc_id")
	private String parentSfAccId;  
	
	/** The ad parentTitanAcc phrases. */
	@JsonProperty(value = "parent_titan_acc_num")
	private String parentTitanAccNum;  
	
	/** The ad parentType phrases. */
	@JsonProperty(value = "parent_type")
	private String parentType; 
	
	/** The ad selfieOnFile phrases. */
	@JsonProperty(value = "selfie_on_file")
	private String selfieOnFile;  
	
	/** The ad vulnerabilityCharacteristic phrases. */
	@JsonProperty(value = "vulnerability_characteristic")
	private String vulnerabilityCharacteristic;  
	
	/** The met in person. */
	@JsonProperty(value = "met_in_person")
	private String metInPerson;  
	
	//AT-5376
	/** The number Of Childs */
	@JsonProperty(value = "number_of_childs")
	private String numberOfChilds; 
	
	/** The legacy trade account number. */
	@JsonProperty(value = "legacy_trade_acc_number")
    private	String legacyTradeAccountNumber; //Add for AT-5393
	
	//AT-5457
	/** The consumer duty scope. */
	@JsonProperty(value = "consumer_duty_scope")
	private String consumerDutyScope;

	private CardDetails cardDetails;

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
	 * @param accSFID the new acc SFID
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
	 * @param tradeAccountID the new trade account ID
	 */
	public void setTradeAccountID(Integer tradeAccountID) {
		this.tradeAccountID = tradeAccountID;
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
	 * @param tradeAccountNumber the new trade account number
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
	 * @param branch the new branch
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
	 * @param channel the new channel
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
	 * @param custType the new cust type
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
	 * @param accountName the new account name
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
	 * @param countryOfInterest the new country of interest
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
	 * @param tradingName the new trading name
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
	 * @param purposeOfTran the new purpose of tran
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
	 * @param countriesOfOperation the new countries of operation
	 */
	public void setCountriesOfOperation(String countriesOfOperation) {
		this.countriesOfOperation = countriesOfOperation;
	}

	/**
	 * Gets the turnover.
	 *
	 * @return the turnover
	 */
	public Integer getTurnover() {
		return turnover;
	}

	/**
	 * Sets the turnover.
	 *
	 * @param turnover the new turnover
	 */
	public void setTurnover(Integer turnover) {
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
	 * @param serviceRequired the new service required
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
	 * @param buyingCurrency the new buying currency
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
	 * @param sellingCurrency the new selling currency
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
	 * @param valueOfTransaction the new value of transaction
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
	 * @param sourceOfFund the new source of fund
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
	 * @param averageTransactionValue the new average transaction value
	 */
	public void setAverageTransactionValue(Double averageTransactionValue) {
		this.averageTransactionValue = averageTransactionValue;
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
	 * @param source the new source
	 */
	public void setSource(String source) {
		this.source = source;
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
	 * @param subSource the new sub source
	 */
	public void setSubSource(String subSource) {
		this.subSource = subSource;
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
	 * @param referral the new referral
	 */
	public void setReferral(String referral) {
		this.referral = referral;
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
	 * @param referralText the new referral text
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
	 * @param extendedReferral the new extended referral
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
	 * @param adCampaign the new ad campaign
	 */
	public void setAdCampaign(String adCampaign) {
		this.adCampaign = adCampaign;
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
	 * @param keywords the new keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
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
	 * @param searchEngine the new search engine
	 */
	public void setSearchEngine(String searchEngine) {
		this.searchEngine = searchEngine;
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
	 * @param website the new website
	 */
	public void setWebsite(String website) {
		this.website = website;
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
	 * @param affiliateName the new affiliate name
	 */
	public void setAffiliateName(String affiliateName) {
		this.affiliateName = affiliateName;
	}

	/**
	 * Gets the affiliate number.
	 *
	 * @return the affiliate number
	 */
	public String getAffiliateNumber() {
		return affiliateNumber;
	}

	/**
	 * Sets the affiliate number.
	 *
	 * @param affiliateNumber the new affiliate number
	 */
	public void setAffiliateNumber(String affiliateNumber) {
		this.affiliateNumber = affiliateNumber;
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
	 * @param registrationMode the new registration mode
	 */
	public void setRegistrationMode(String registrationMode) {
		this.registrationMode = registrationMode;
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
	 * @param registrationDateTime the new registration date time
	 */
	public void setRegistrationDateTime(String registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
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
	 * @param company the new company
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
	 * @param corperateCompliance the new corperate compliance
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
	 * @param riskProfile the new risk profile
	 */
	public void setRiskProfile(RiskProfile riskProfile) {
		this.riskProfile = riskProfile;
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
	 * @param conversionPrediction the new conversion prediction
	 */
	public void setConversionPrediction(ConversionPrediction conversionPrediction) {
		this.conversionPrediction = conversionPrediction;
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
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the currency pair.
	 *
	 * @return the currency pair
	 */
	public String getCurrencyPair() {
		return currencyPair;
	}

	/**
	 * Sets the currency pair.
	 *
	 * @param currencyPair the new currency pair
	 */
	public void setCurrencyPair(String currencyPair) {
		this.currencyPair = currencyPair;
	}

	/**
	 * Gets the client type.
	 *
	 * @return the client type
	 */
	public String getClientType() {
		return clientType;
	}

	/**
	 * Sets the client type.
	 *
	 * @param clientType the new client type
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
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
	 * @param otherCurrencyPurpose the new other currency purpose
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
	 * @param adKeywordPhrases the new ad keyword phrases
	 */
	public void setAdKeywordPhrases(String adKeywordPhrases) {
		this.adKeywordPhrases = adKeywordPhrases;
	}

	/**
	 * Gets the phone reg.
	 *
	 * @return the phone reg
	 */
	public String getPhoneReg() {
		return phoneReg;
	}

	/**
	 * Sets the phone reg.
	 *
	 * @param phoneReg the new phone reg
	 */
	public void setPhoneReg(String phoneReg) {
		this.phoneReg = phoneReg;
	}

	/**
	 * Gets the met in person.
	 *
	 * @return the met in person
	 */
	

	/**
	 * Gets the enquiry date.
	 *
	 * @return the enquiry date
	 */
	public String getEnquiryDate() {
		return enquiryDate;
	}

	
	/**
	 * @return the metInPerson
	 */
	public String getMetInPerson() {
		return metInPerson;
	}

	/**
	 * @param metInPerson the metInPerson to set
	 */
	public void setMetInPerson(String metInPerson) {
		this.metInPerson = metInPerson;
	}

	

	/**
	 * @return the selfieOnFile
	 */
	public String getSelfieOnFile() {
		return selfieOnFile;
	}

	/**
	 * @param selfieOnFile the selfieOnFile to set
	 */
	public void setSelfieOnFile(String selfieOnFile) {
		this.selfieOnFile = selfieOnFile;
	}

	/**
	 * Sets the enquiry date.
	 *
	 * @param enquiryDate the new enquiry date
	 */
	public void setEnquiryDate(String enquiryDate) {
		this.enquiryDate = enquiryDate;
	}

	/**
	 * Gets the online account status.
	 *
	 * @return the online account status
	 */
	public String getOnlineAccountStatus() {
		return onlineAccountStatus;
	}

	/**
	 * Sets the online account status.
	 *
	 * @param onlineAccountStatus the new online account status
	 */
	public void setOnlineAccountStatus(String onlineAccountStatus) {
		this.onlineAccountStatus = onlineAccountStatus;
	}

	/**
	 * Checks if is account modified.
	 *
	 * @return true, if is account modified
	 */
	public boolean isAccountModified() {
		return isAccountModified;
	}

	/**
	 * Sets the account modified.
	 *
	 * @param isAccountModified the new account modified
	 */
	public void setAccountModified(boolean isAccountModified) {
		this.isAccountModified = isAccountModified;
	}
	
	/**
	 * Gets the sanction result.
	 *
	 * @return the sanction result
	 */
	public String getSanctionResult() {
		return sanctionResult;
	}

	/**
	 * Sets the sanction result.
	 *
	 * @param sanctionResult the new sanction result
	 */
	public void setSanctionResult(String sanctionResult) {
		this.sanctionResult = sanctionResult;
	}

	/**
	 * Gets the blacklist.
	 *
	 * @return the blacklist
	 */
	public String getBlacklist() {
		return blacklist;
	}

	/**
	 * Sets the blacklist.
	 *
	 * @param blacklist the new blacklist
	 */
	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
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
	 * @param accountStatus the new account status
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getComplianceLog() {
		return complianceLog;
	}

	public void setComplianceLog(String complianceLog) {
		this.complianceLog = complianceLog;
	}

	public IPAddressDetails getiPAddressDetails() {
		return iPAddressDetails;
	}

	public void setiPAddressDetails(IPAddressDetails iPAddressDetails) {
		this.iPAddressDetails = iPAddressDetails;
	}

	public String getCardDeliveryToSecondaryAddress() {
		return cardDeliveryToSecondaryAddress;
	}

	public void setCardDeliveryToSecondaryAddress(String cardDeliveryToSecondaryAddress) {
		this.cardDeliveryToSecondaryAddress = cardDeliveryToSecondaryAddress;
	}

	public String getSecondaryAddressPresent() {
		return secondaryAddressPresent;
	}

	public void setSecondaryAddressPresent(String secondaryAddressPresent) {
		this.secondaryAddressPresent = secondaryAddressPresent;
	}

	public CardDetails getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(
			CardDetails cardDetails) {
		this.cardDetails = cardDetails;
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

	/**
	 * @return the consumerDutyScope
	 */
	public String getConsumerDutyScope() {
		return consumerDutyScope;
	}

	/**
	 * @param consumerDutyScope the consumerDutyScope to set
	 */
	public void setConsumerDutyScope(String consumerDutyScope) {
		this.consumerDutyScope = consumerDutyScope;
	}
}
