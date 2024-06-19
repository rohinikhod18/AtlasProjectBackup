package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.ConversionPrediction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class UpdateAccount implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The acc SFID. */
	@ApiModelProperty(value = "Account Salesforce ID", example = "0010O00001LUEp4FAH", required = true)
	@JsonProperty(value="acc_sf_id")
	private String accSFID;
	
	/** The trade account ID. */
	@ApiModelProperty(value = "The Trade Account ID as assigned by Titan", example = "301000006112367", required = true)
	@JsonProperty(value="trade_acc_id")
	private Integer tradeAccountID;
	
	/** The trade account number. */
	@ApiModelProperty(value = "The trade account number", required = true)
	@JsonProperty(value="trade_acc_number")
	private String tradeAccountNumber;
	
	/** The branch. */
	@ApiModelProperty(value = "The CD organisation branch which the client belongs to", example = "E4F-Johannesburg", required = true)
	@JsonProperty(value="branch")
	private String branch;
	
	/** The channel. */
	@ApiModelProperty(value = "The channel", required = true)
	@JsonProperty(value="channel")
	private String channel;
	
	/** The cust type. */
	@ApiModelProperty(value = "The customer type (PFX/CFX)", required = true, example = "PFX")
	@JsonProperty(value="cust_type")
	private String custType;
	
	/** The account name. */
	@ApiModelProperty(value = "Account Name", example = "Bob Nighy", required = true)
	@JsonProperty(value="act_name")
	private String accountName;
	
	/** The country of interest. */
	@ApiModelProperty(value = "The three letter code for the country which client has expressed an interest in during sign-up", example = "GBR", required = true)
	@JsonProperty(value="country_interest")
	private String countryOfInterest;
	
	/** The trading name. */
	@ApiModelProperty(value = "Trading Name for corporate customers", example = "Alice and Bob Ltd", required = true)
	@JsonProperty(value="trade_name")
	private String tradingName;
	
	/** The purpose of tran. */
	@ApiModelProperty(value = "The purpose of Transaction (e.g. Bill payments, Yacht)", required = true)
	@JsonProperty(value="trasaction_purpose")
	private String purposeOfTran;
	
	/** The countries of operation. */
	@ApiModelProperty(value = "Three letter code for the country of operation", example = "ITA", required = true)
	@JsonProperty(value="op_country")
	private String countriesOfOperation;
	
	/** The turnover. */
	@JsonProperty(value="turnover")
    @ApiModelProperty(value = "Amount of turnover that client said they will probably do when they signed up, used for Fraud analysis", required = true, example = "10000000")
	private String turnover;
	
	/** The service required. */
	@ApiModelProperty(value = "Type of service that client is interested in (e.g. E-tailer, Transfer, Spot, Incoming Payments)", example = "E-tailer", required = true)
	@JsonProperty(value="service_req")
	private String serviceRequired;
	
	/** The buying currency. */
	@ApiModelProperty(value = "The buying currency", example = "EUR", required = true)
	@JsonProperty(value="buying_currency")
	private String buyingCurrency;
	
	/** The selling currency. */
	@ApiModelProperty(value = "The selling currency", example = "GBP", required = true)
	@JsonProperty(value="selling_currency")
	private String sellingCurrency;
	
	/** The value of transaction. */
	@ApiModelProperty(value = "The estimated yearly transaction value", example = "100,000 - 250,000", required = true)
	@JsonProperty(value="txn_value")
	private String valueOfTransaction;
	
	/** The source of fund. */
	@ApiModelProperty(value = "The source of funds", example = "House Sale, Salary, Savings", required = true)
	@JsonProperty(value="source_of_fund")
	private String sourceOfFund;
	
	/** The average transaction value. */
	@ApiModelProperty(value = "The average value of each transaction", example = "10000", required = true)
	@JsonProperty(value="avg_txn")
	private Double averageTransactionValue;
	
	/** The account status. */
	@ApiModelProperty(value = "The account status", required = true)
	@JsonProperty(value="account_status")
	private String accountStatus;
	
	/** The source. */
	@ApiModelProperty(value = "The marketing channel used by the client when signing up this account (e.g. Affiliate, Self Generated, Web)", example = "Internet", required = true)
	@JsonProperty(value="source")
	private String source;
	
	/** The sub source. */
	@ApiModelProperty(value = "More specific information on the the marketing channel used by the client when signing up this account (e.g. google)", example = "PayPerClick V1", required = true)
	@JsonProperty(value="sub_source")
	private String subSource;

	/** The referral. */
	@ApiModelProperty(value = "Salesforce ID of the refering client for referrals", example = "0030O00001wVy83QAC", required = true)
	@JsonProperty(value="referral")
	private String referral;
	
	/** The referral text. */
	@ApiModelProperty(value = "Marketing information showing how the client was referred", example = "Word of mouth" , required = true)
	@JsonProperty(value="referral_text")
	private String referralText;
	
	/** The extended referral. */
	@ApiModelProperty(value = "Extended marketing information showing how the client was referred", example = "", required = true)
	@JsonProperty(value="extended_referral")
	private String extendedReferral;
	
	/** The ad campaign. */
	@ApiModelProperty(value = "The Ad Campaign which client saw before joining", example = "Currency_Forecasts", required = true)
	@JsonProperty(value="ad_campaign")
	private String adCampaign;
	
	/** The keywords. */
	@ApiModelProperty(value = "The keywords retrieved from cookies on client's device, that were used in the search engines when looking for CD", example = "transfer euros into pounds", required = true)
	@JsonProperty(value="keywords")
	private String keywords;
	
	/** The search engine. */
	@ApiModelProperty(value = "The search engine used to come to CD's website", example = "", required = true)
	@JsonProperty(value="search_engine")
	private String searchEngine;
	
	/** The website. */
	@ApiModelProperty(value = "The website", required = true)
	@JsonProperty(value="website")
	private String website;
	
	/** The affiliate name. */
	@ApiModelProperty(value = "Name of the sales rep or associate responsible for signing this client", example = "Marketing HO", required = true)
	@JsonProperty(value="affiliate_name")
	private String affiliateName;
	
	/** The registration mode. */
	@ApiModelProperty(value = "The mode of registration. Whether it was online or offline", required = true)
	@JsonProperty(value="reg_mode")
	private String registrationMode;

	/** The contacts. */
	@ApiModelProperty(value = "The contacts", required = true)
	@JsonProperty(value="contacts")
	private List<UpdateContact> contacts = new ArrayList<>();
	
	/** below fields for cfx reg */
	@ApiModelProperty(value = "below fields for cfx reg", required = true)
	@JsonProperty(value = "company")
	private Company company;

	/** The corperate compliance. */
	@ApiModelProperty(value = "The corporate compliance object, containing data from Dun and Bradstreet", required = true)
	@JsonProperty(value = "corporate_compliance")
	private CorperateCompliance corperateCompliance;

	/** The risk profile. */
	@ApiModelProperty(value = "The risk profile for a corporate client based on their liquidity", required = true)
	@JsonProperty(value = "risk_profile")
	private RiskProfile riskProfile;
	
	/** The conversion Prediction. */
	@ApiModelProperty(value = "The conversion Prediction", required = true)
	@JsonProperty(value="conversionPrediction")
	private ConversionPrediction conversionPrediction;
	
	/** The org legal entity. */
	//Temporary reverting back customer_legal_entity to organization_legal_entity
	@ApiModelProperty(value = "The customer legal entity", required = true)
	@JsonProperty(value = "organization_legal_entity")
	private String custLegalEntity;
	
	// Added for AT-5457
	/** The consumer duty scope. */
	@ApiModelProperty(value = "The consumer duty scope", required = true)
	@JsonProperty(value = "consumer_duty_scope")
	private Boolean consumerDutyScope;
	
	/** The other currency purpose. */
	@JsonProperty(value = "other_currency_purpose")
	private String otherCurrencyPurpose;
	
	/** The ad keyword phrases. */
	@JsonProperty(value = "ad_keyword_phrases")
	private String adKeywordPhrases;
	
	/** The legacy trade account ID. */
	@ApiModelProperty(value = "The legacy Trade Account ID ")
	@JsonProperty(value = "legacy_trade_acc_id")
	private Integer legacyTradeAccountID;

	/** The legacy trade account number. */
	@ApiModelProperty(value = "The legacy trade account number")
	@JsonProperty(value = "legacy_trade_acc_number")
	private String legacyTradeAccountNumber;
	
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
	private Boolean selfieOnFile; 
	
	/** The ad vulnerabilityCharacteristic phrases. */
	@JsonProperty(value = "vulnerability_characteristic")
	private String vulnerabilityCharacteristic;  
	
	/** The met in person. */
	@JsonProperty(value = "met_in_person")
	private Boolean metInPerson; 
	
	//AT-5376
	/** The number Of Childs */
	@JsonProperty(value = "number_of_childs")
	private String numberOfChilds; 

	public String getAccSFID() {
		return accSFID;
	}

	public void setAccSFID(String accSFID) {
		this.accSFID = accSFID;
	}

	public Integer getTradeAccountID() {
		return tradeAccountID;
	}

	public void setTradeAccountID(Integer tradeAccountID) {
		this.tradeAccountID = tradeAccountID;
	}

	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}


	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCountryOfInterest() {
		return countryOfInterest;
	}

	public void setCountryOfInterest(String countryOfInterest) {
		this.countryOfInterest = countryOfInterest;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getPurposeOfTran() {
		return purposeOfTran;
	}

	public void setPurposeOfTran(String purposeOfTran) {
		this.purposeOfTran = purposeOfTran;
	}

	public String getCountriesOfOperation() {
		return countriesOfOperation;
	}

	public void setCountriesOfOperation(String countriesOfOperation) {
		this.countriesOfOperation = countriesOfOperation;
	}

	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public String getServiceRequired() {
		return serviceRequired;
	}

	public void setServiceRequired(String serviceRequired) {
		this.serviceRequired = serviceRequired;
	}

	public String getBuyingCurrency() {
		return buyingCurrency;
	}

	public void setBuyingCurrency(String buyingCurrency) {
		this.buyingCurrency = buyingCurrency;
	}

	public String getSellingCurrency() {
		return sellingCurrency;
	}

	public void setSellingCurrency(String sellingCurrency) {
		this.sellingCurrency = sellingCurrency;
	}

	public String getValueOfTransaction() {
		return valueOfTransaction;
	}

	public void setValueOfTransaction(String valueOfTransaction) {
		this.valueOfTransaction = valueOfTransaction;
	}

	public String getSourceOfFund() {
		return sourceOfFund;
	}

	public void setSourceOfFund(String sourceOfFund) {
		this.sourceOfFund = sourceOfFund;
	}

	public Double getAverageTransactionValue() {
		return averageTransactionValue;
	}

	public void setAverageTransactionValue(Double averageTransactionValue) {
		this.averageTransactionValue = averageTransactionValue;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	public String getReferral() {
		return referral;
	}

	public void setReferral(String referral) {
		this.referral = referral;
	}

	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public List<UpdateContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<UpdateContact> contacts) {
		this.contacts = contacts;
	}
	
	@JsonIgnore
	public UpdateContact getPrimaryContact(){
		for(UpdateContact c: contacts){
			if(Boolean.TRUE.equals(c.getPrimaryContact())){
				return c;
			}
		}
		return contacts.get(0);
	}

	public String getSubSource() {
		return subSource;
	}

	public void setSubSource(String subSource) {
		this.subSource = subSource;
	}

	public String getReferralText() {
		return referralText;
	}

	public void setReferralText(String referralText) {
		this.referralText = referralText;
	}

	public String getExtendedReferral() {
		return extendedReferral;
	}

	public void setExtendedReferral(String extendedReferral) {
		this.extendedReferral = extendedReferral;
	}

	public String getAdCampaign() {
		return adCampaign;
	}

	public void setAdCampaign(String adCampaign) {
		this.adCampaign = adCampaign;
	}

	public String getSearchEngine() {
		return searchEngine;
	}

	public void setSearchEngine(String searchEngine) {
		this.searchEngine = searchEngine;
	}

	public String getAffiliateName() {
		return affiliateName;
	}

	public void setAffiliateName(String affiliateName) {
		this.affiliateName = affiliateName;
	}

	public String getRegistrationMode() {
		return registrationMode;
	}

	public void setRegistrationMode(String registrationMode) {
		this.registrationMode = registrationMode;
	}

	public String getOtherCurrencyPurpose() {
		return otherCurrencyPurpose;
	}

	public void setOtherCurrencyPurpose(String otherCurrencyPurpose) {
		this.otherCurrencyPurpose = otherCurrencyPurpose;
	}

	public String getAdKeywordPhrases() {
		return adKeywordPhrases;
	}

	public void setAdKeywordPhrases(String adKeywordPhrases) {
		this.adKeywordPhrases = adKeywordPhrases;
	}
	
	public String getCustLegalEntity() {
		return custLegalEntity;
	}

	public void setCustLegalEntity(String custLegalEntity) {
		this.custLegalEntity = custLegalEntity;
	}
   
	public Integer getLegacyTradeAccountID() {
		return legacyTradeAccountID;
	}

	public void setLegacyTradeAccountID(Integer legacyTradeAccountID) {
		this.legacyTradeAccountID = legacyTradeAccountID;
	}

	public String getLegacyTradeAccountNumber() {
		return legacyTradeAccountNumber;
	}

	public void setLegacyTradeAccountNumber(String legacyTradeAccountNumber) {
		this.legacyTradeAccountNumber = legacyTradeAccountNumber;
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
		result = prime * result + ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result + ((countriesOfOperation == null) ? 0 : countriesOfOperation.hashCode());
		result = prime * result + ((countryOfInterest == null) ? 0 : countryOfInterest.hashCode());
		result = prime * result + ((custType == null) ? 0 : custType.hashCode());
		result = prime * result + ((extendedReferral == null) ? 0 : extendedReferral.hashCode());
		result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
		result = prime * result + ((purposeOfTran == null) ? 0 : purposeOfTran.hashCode());
		result = prime * result + ((referral == null) ? 0 : referral.hashCode());
		result = prime * result + ((referralText == null) ? 0 : referralText.hashCode());
		result = prime * result + ((registrationMode == null) ? 0 : registrationMode.hashCode());
		result = prime * result + ((searchEngine == null) ? 0 : searchEngine.hashCode());
		result = prime * result + ((sellingCurrency == null) ? 0 : sellingCurrency.hashCode());
		result = prime * result + ((serviceRequired == null) ? 0 : serviceRequired.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((sourceOfFund == null) ? 0 : sourceOfFund.hashCode());
		result = prime * result + ((subSource == null) ? 0 : subSource.hashCode());
		result = prime * result + ((tradeAccountID == null) ? 0 : tradeAccountID.hashCode());
		result = prime * result + ((tradeAccountNumber == null) ? 0 : tradeAccountNumber.hashCode());
		result = prime * result + ((tradingName == null) ? 0 : tradingName.hashCode());
		result = prime * result + ((turnover == null) ? 0 : turnover.hashCode());
		result = prime * result + ((valueOfTransaction == null) ? 0 : valueOfTransaction.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
		result = prime * result + ((legacyTradeAccountID == null) ? 0 : legacyTradeAccountID.hashCode());
		result = prime * result + ((legacyTradeAccountNumber == null) ? 0 : legacyTradeAccountNumber.hashCode());
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

	@SuppressWarnings("squid:S3776")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateAccount other = (UpdateAccount) obj;
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
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts)) {
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
		if (registrationMode == null) {
			if (other.registrationMode != null)
				return false;
		} else if (!registrationMode.equals(other.registrationMode)) {
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
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website)) {
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

	@Override
	public String toString() {
		return "UpdateAccount [accSFID=" + accSFID + ", tradeAccountID=" + tradeAccountID + ", tradeAccountNumber="
				+ tradeAccountNumber + ", branch=" + branch + ", channel=" + channel + ", custType=" + custType
				+ ", accountName=" + accountName + ", countryOfInterest=" + countryOfInterest + ", tradingName="
				+ tradingName + ", purposeOfTran=" + purposeOfTran + ", countriesOfOperation=" + countriesOfOperation
				+ ", turnover=" + turnover + ", serviceRequired=" + serviceRequired + ", buyingCurrency="
				+ buyingCurrency + ", sellingCurrency=" + sellingCurrency + ", valueOfTransaction=" + valueOfTransaction
				+ ", sourceOfFund=" + sourceOfFund + ", averageTransactionValue=" + averageTransactionValue
				+ ", accountStatus=" + accountStatus + ", source=" + source + ", subSource=" + subSource + ", referral="
				+ referral + ", referralText=" + referralText + ", extendedReferral=" + extendedReferral
				+ ", adCampaign=" + adCampaign + ", keywords=" + keywords + ", searchEngine=" + searchEngine
				+ ", website=" + website + ", affiliateName=" + affiliateName + ", registrationMode=" + registrationMode
				+ ", contacts=" + contacts + ", company=" + company + ", corperateCompliance=" + corperateCompliance
				+ ", riskProfile=" + riskProfile + ", conversionPrediction=" + conversionPrediction
				+ ", custLegalEntity=" + custLegalEntity + ", otherCurrencyPurpose=" + otherCurrencyPurpose
				+ ", adKeywordPhrases=" + adKeywordPhrases + ", legacyTradeAccountID=" + legacyTradeAccountID
				+ ", legacyTradeAccountNumber=" + legacyTradeAccountNumber + ", parentSfAccId=" + parentSfAccId
				+ ", parentTitanAccNum=" + parentTitanAccNum + ", parentType=" + parentType + ", selfieOnFile=" + selfieOnFile
				+ ", vulnerabilityCharacteristic=" + vulnerabilityCharacteristic + ", metInPerson=" + metInPerson + ",numberOfChilds=" + numberOfChilds 
				+ ", consumerDutyScope=" + consumerDutyScope + "]";
	}

}
