package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.RiskProfile;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Company;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.CorperateCompliance;
/**
 * The Class Account.
 */
public class AccountHistoryResponse {

	/** The id. */
	private Integer id;
		
	/** The acc SFID. */
	private String accSFID;

	/** The currency pair. */
	private String currencyPair;

	/** The client type. */
	private String clientType;

	/** The source of fund. */
	private String sourceOfFund;

	/** The source. */
	private String source;

	/** The estim trans value. */
	private String estimTransValue;

	/** The org code. */
	private String orgCode;

	/** The purpose of trans. */
	private String purposeOfTrans;

	/** The service required. */
	private String serviceRequired;

	/** The affiliate name. */
	private String affiliateName;

	/** The browser type. */
	private String browserType;

	/** The cookie info. */
	private String cookieInfo;

	/** The refferal text. */
	private String refferalText;

	/** The date of reg. */
	private String dateOfReg;

	/** The compliance status. */
	private String complianceStatus;

	/** The trade account num. */
	private String tradeAccountNum;
	
	/** The trade account id. */
	private String tradeAccountId;
	
	/** The name. */
	private String name;
	
	/** The web site. */
	private String webSite;
	
	/** The annual FX requirement. */
	private String annualFXRequirement;
	
	/** The source lookup. */
	private String sourceLookup;
	
	/** The avg transaction value. */
	private String avgTransactionValue;
	
	/** The countries of operation. */
	private String countriesOfOperation;
	
	/** The reg mode. */
	private String regMode;
	
	/** The company. */
	private Company company;
	
	/** The risk profile. */
	private RiskProfile riskProfile;
	
	/** The corporate compliance. */
	private CorperateCompliance corporateCompliance;
	
	/** The legal entity. */
	private String legalEntity;
	
	/** The is all fields empty. */
	private Boolean isAllFieldsEmpty;
	
	private String addCampaign;

	private String affiliateNumber;

	private String branch;

	private String buyingCurrency;

	private String channel;

	private String countryOfInterest;

	private String extendedReferral;

	private String keywords;

	private String referralId;

	private String searchEngine;

	private String sellingCurrency;

	private String subSource;

	private String tradeName;

	private String turnover;
	
	private String complianceDoneOn;

	private List<ContactHistoryResponse> contactHistory;
	
	private DeviceInfoResponse deviceInfo;

	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;

	/**
	 * @return the complianceDoneOn
	 */
	public String getComplianceDoneOn() {
		return complianceDoneOn;
	}

	/**
	 * @param complianceDoneOn the complianceDoneOn to set
	 */
	public void setComplianceDoneOn(String complianceDoneOn) {
		this.complianceDoneOn = complianceDoneOn;
	}

	public List<ContactHistoryResponse> getContactHistory() {
		return contactHistory;
	}

	public void setContactHistory(List<ContactHistoryResponse> contactHistory) {
		this.contactHistory = contactHistory;
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
	 * @param currencyPair
	 *            the new currency pair
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
	 * @param clientType
	 *            the new client type
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	/**
	 * Gets the source of fund.
	 *
	 * @return the source of fund
	 */
	public String getSourceOfFund() {
		return sourceOfFund;
	}

	/**private
	 * Sets the source of fund.
	 *
	 * @param sourceOfFund
	 *            the new source of fund
	 */
	public void setSourceOfFund(String sourceOfFund) {
		this.sourceOfFund = sourceOfFund;
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
	 * Gets the estim trans value.
	 *
	 * @return the estim trans value
	 */
	public String getEstimTransValue() {
		return estimTransValue;
	}

	/**
	 * Sets the estim trans value.
	 *
	 * @param estimTransValue
	 *            the new estim trans value
	 */
	public void setEstimTransValue(String estimTransValue) {
		this.estimTransValue = estimTransValue;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the trade account num.
	 *
	 * @return the trade account num
	 */
	public String getTradeAccountNum() {
		return tradeAccountNum;
	}

	/**
	 * Sets the trade account num.
	 *
	 * @param tradeAccountNum the new trade account num
	 */
	public void setTradeAccountNum(String tradeAccountNum) {
		this.tradeAccountNum = tradeAccountNum;
	}

	/**
	 * Gets the purpose of trans.
	 *
	 * @return the purpose of trans
	 */
	public String getPurposeOfTrans() {
		return purposeOfTrans;
	}

	/**
	 * Sets the purpose of trans.
	 *
	 * @param purposeOfTrans
	 *            the new purpose of trans
	 */
	public void setPurposeOfTrans(String purposeOfTrans) {
		this.purposeOfTrans = purposeOfTrans;
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
	 * Gets the browser type.
	 *
	 * @return the browser type
	 */
	public String getBrowserType() {
		return browserType;
	}

	/**
	 * Sets the browser type.
	 *
	 * @param browserType
	 *            the new browser type
	 */
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	/**
	 * Gets the cookie info.
	 *
	 * @return the cookie info
	 */
	public String getCookieInfo() {
		return cookieInfo;
	}

	/**
	 * Sets the cookie info.
	 *
	 * @param cookieInfo
	 *            the new cookie info
	 */
	public void setCookieInfo(String cookieInfo) {
		this.cookieInfo = cookieInfo;
	}

	/**
	 * Gets the refferal text.
	 *
	 * @return the refferal text
	 */
	public String getRefferalText() {
		return refferalText;
	}

	/**
	 * Sets the refferal text.
	 *
	 * @param refferalText
	 *            the new refferal text
	 */
	public void setRefferalText(String refferalText) {
		this.refferalText = refferalText;
	}

	/**
	 * Gets the date of reg.
	 *
	 * @return the date of reg
	 */
	public String getDateOfReg() {
		return dateOfReg;
	}

	/**
	 * Sets the date of reg.
	 *
	 * @param dateOfReg
	 *            the new date of reg
	 */
	public void setDateOfReg(String dateOfReg) {
		this.dateOfReg = dateOfReg;
	}

	/**
	 * Gets the compliance status.
	 *
	 * @return the compliance status
	 */
	public String getComplianceStatus() {
		return complianceStatus;
	}

	/**
	 * Sets the compliance status.
	 *
	 * @param complianceStatus
	 *            the new compliance status
	 */
	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}

	/**
	 * Gets the reg mode.
	 *
	 * @return the reg mode
	 */
	public String getRegMode() {
		return regMode;
	}

	/**
	 * Sets the reg mode.
	 *
	 * @param regMode the new reg mode
	 */
	public void setRegMode(String regMode) {
		this.regMode = regMode;
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
	 * Gets the corporate compliance.
	 *
	 * @return the corporate compliance
	 */
	public CorperateCompliance getCorporateCompliance() {
		return corporateCompliance;
	}

	/**
	 * Sets the corporate compliance.
	 *
	 * @param corporateCompliance the new corporate compliance
	 */
	public void setCorporateCompliance(CorperateCompliance corporateCompliance) {
		this.corporateCompliance = corporateCompliance;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the web site.
	 *
	 * @return the web site
	 */
	public String getWebSite() {
		return webSite;
	}

	/**
	 * Sets the web site.
	 *
	 * @param webSite the new web site
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	/**
	 * Gets the annual FX requirement.
	 *
	 * @return the annual FX requirement
	 */
	public String getAnnualFXRequirement() {
		return annualFXRequirement;
	}

	/**
	 * Sets the annual FX requirement.
	 *
	 * @param annualFXRequirement the new annual FX requirement
	 */
	public void setAnnualFXRequirement(String annualFXRequirement) {
		this.annualFXRequirement = annualFXRequirement;
	}

	/**
	 * Gets the source lookup.
	 *
	 * @return the source lookup
	 */
	public String getSourceLookup() {
		return sourceLookup;
	}

	/**
	 * Sets the source lookup.
	 *
	 * @param sourceLookup the new source lookup
	 */
	public void setSourceLookup(String sourceLookup) {
		this.sourceLookup = sourceLookup;
	}

	/**
	 * Gets the avg transaction value.
	 *
	 * @return the avg transaction value
	 */
	public String getAvgTransactionValue() {
		return avgTransactionValue;
	}

	/**
	 * Sets the avg transaction value.
	 *
	 * @param avgTransactionValue the new avg transaction value
	 */
	public void setAvgTransactionValue(String avgTransactionValue) {
		this.avgTransactionValue = avgTransactionValue;
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
	 * Gets the trade account id.
	 *
	 * @return the trade account id
	 */
	public String getTradeAccountId() {
		return tradeAccountId;
	}

	/**
	 * Sets the trade account id.
	 *
	 * @param tradeAccountId the new trade account id
	 */
	public void setTradeAccountId(String tradeAccountId) {
		this.tradeAccountId = tradeAccountId;
	}

	/**
	 * Gets the legal entity.
	 *
	 * @return the legal entity
	 */
	public String getLegalEntity() {
		return legalEntity;
	}

	/**
	 * Sets the legal entity.
	 *
	 * @param legalEntity the new legal entity
	 */
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the isAllFieldsEmpty
	 */
	public Boolean getIsAllFieldsEmpty() {
		return isAllFieldsEmpty;
	}

	/**
	 * @param isAllFieldsEmpty the isAllFieldsEmpty to set
	 */
	public void setIsAllFieldsEmpty(Boolean isAllFieldsEmpty) {
		this.isAllFieldsEmpty = isAllFieldsEmpty;
	}

	/**
	 * @return the accSFID
	 */
	public String getAccSFID() {
		return accSFID;
	}

	/**
	 * @param accSFID the accSFID to set
	 */
	public void setAccSFID(String accSFID) {
		this.accSFID = accSFID;
	}

	public String getAddCampaign() {
		return addCampaign;
	}

	public void setAddCampaign(String addCampaign) {
		this.addCampaign = addCampaign;
	}

	public String getAffiliateNumber() {
		return affiliateNumber;
	}

	public void setAffiliateNumber(String affiliateNumber) {
		this.affiliateNumber = affiliateNumber;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBuyingCurrency() {
		return buyingCurrency;
	}

	public void setBuyingCurrency(String buyingCurrency) {
		this.buyingCurrency = buyingCurrency;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCountryOfInterest() {
		return countryOfInterest;
	}

	public void setCountryOfInterest(String countryOfInterest) {
		this.countryOfInterest = countryOfInterest;
	}

	public String getExtendedReferral() {
		return extendedReferral;
	}

	public void setExtendedReferral(String extendedReferral) {
		this.extendedReferral = extendedReferral;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getReferralId() {
		return referralId;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	public String getSearchEngine() {
		return searchEngine;
	}

	public void setSearchEngine(String searchEngine) {
		this.searchEngine = searchEngine;
	}

	public String getSellingCurrency() {
		return sellingCurrency;
	}

	public void setSellingCurrency(String sellingCurrency) {
		this.sellingCurrency = sellingCurrency;
	}

	public String getSubSource() {
		return subSource;
	}

	public void setSubSource(String subSource) {
		this.subSource = subSource;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public DeviceInfoResponse getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfoResponse deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	
}
