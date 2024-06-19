package com.currenciesdirect.gtg.compliance.commons.domain.profile.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class UpdateAccount.
 */
@SuppressWarnings("squid:S3776")
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
	
	/** The org legal entity. */
	//Temporary reverting back customer_legal_entity to organization_legal_entity
	@ApiModelProperty(value = "The customer legal entity", required = true)
	@JsonProperty(value = "organization_legal_entity")
	private String custLegalEntity;
	
	/** The other currency purpose. */
	@JsonProperty(value = "other_currency_purpose")
	private String otherCurrencyPurpose;
	
	/** The ad keyword phrases. */
	@JsonProperty(value = "ad_keyword_phrases")
	private String adKeywordPhrases;

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
	public String getTurnover() {
		return turnover;
	}

	/**
	 * Sets the turnover.
	 *
	 * @param turnover the new turnover
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
	 * Gets the contacts.
	 *
	 * @return the contacts
	 */
	public List<UpdateContact> getContacts() {
		return contacts;
	}

	/**
	 * Sets the contacts.
	 *
	 * @param contacts the new contacts
	 */
	public void setContacts(List<UpdateContact> contacts) {
		this.contacts = contacts;
	}
	
	/**
	 * Gets the primary contact.
	 *
	 * @return the primary contact
	 */
	@JsonIgnore
	public UpdateContact getPrimaryContact(){
		for(UpdateContact c: contacts){
			if(Boolean.TRUE.equals(c.getPrimaryContact())){
				return c;
			}
		}
		return contacts.get(0);
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
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
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UpdateAccount [accSFID=" + accSFID + ", tracdeAccountID=" + tradeAccountID + ", tradeAccountNumber="
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
				+ ", contacts=" + contacts + "]";
	}
}
