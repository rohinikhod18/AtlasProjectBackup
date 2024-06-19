/*
 * Copyright 2012-2018 Currencies Direct Ltd, United Kingdom
 *
 * compliance-commons: ProviderSignUpRequest.java Last modified: 11/05/2018
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ProviderSignUpRequest.
 */
public class ProviderSignUpRequest implements Serializable{
	
	/** The Constant serialVersionUID. */
	@ApiModelProperty(value = "The Customer ID", required = true)
	@JsonProperty("customer_no")
	private String customerNo;

	/** The screen resolution. */
	@ApiModelProperty(value = "Device Screen Resolution", example = "1920x1080 24", required = true)
	@JsonProperty("screen_resolution")
	private String screenResolution;

	/** The browser type. */
	@ApiModelProperty(value = "The browser type", required = true)
	@JsonProperty("browser_type")
	private String browserType;

	/** The browser version. */
	@ApiModelProperty(value = "The browser version", required = true)
	@JsonProperty("browser_version")
	private String browserVersion;

	/** The device type. */
	@ApiModelProperty(value = "Type of Device", example = "Desktop, Tablet, Mobile", required = true)
	@JsonProperty("device_type")
	private String deviceType;

	/** The device name. */
	@ApiModelProperty(value = "Device Model Name", example = "Kindle Fire 72.2.7", required = true)
	@JsonProperty("device_name")
	private String deviceName;

	/** The device manufacturer. */
	@ApiModelProperty(value = "Manufacturer of Device", example = "Amazon", required = true)
	@JsonProperty("device_manufacturer")
	private String deviceManufacturer;

	/** The device os type. */
	@ApiModelProperty(value = "The device os type", required = true)
	@JsonProperty("device_os_type")
	private String deviceOsType;

	/** The browser lang. */
	@ApiModelProperty(value = "The browser lang", required = true)
	@JsonProperty("browser_lang")
	private String browserLang;

	/** The browser online. */
	@ApiModelProperty(value = "The browser online", required = true)
	@JsonProperty("browser_online")
	private String browserOnline;

	/** The title. */
	@ApiModelProperty(value = "The client's title", example = "Mr", required = true)
	@JsonProperty("title")
	private String title;

	/** The bill ad line 1. */
	@ApiModelProperty(value = "The bill address line 1", required = true)
	@JsonProperty("bill_ad_line1")
	private String billAdLine1;

	/** The bill ad zip. */
	@ApiModelProperty(value = "Billing address on one line", example = "3 Stable Mews, London, WC1", required = true)
	@JsonProperty("bill_ad_zip")
	private String billAdZip;

	/** The phone home. */
	@ApiModelProperty(value = "The phone home", required = true)
	@JsonProperty("phone_home")
	private String phoneHome;

	/** The phone work. */
	@ApiModelProperty(value = "The phone work", required = true)
	@JsonProperty("phone_work")
	private String phoneWork;

	/** The channel. */
	@ApiModelProperty(value = "The channel", required = true)
	@JsonProperty("channel")
	private String channel;

	/** The ip address. */
	@ApiModelProperty(value = "The IP address of the client", example = "127.0.0.1", required = true)
	@JsonProperty("ip_address")
	private String ipAddress;

	/** The op country. */
	@ApiModelProperty(value = "Three letter code for the country of operation", example = "ITA", required = true)
	@JsonProperty("op_country")
	private String opCountry;

	/** The source. */
	@ApiModelProperty(value = "The marketing channel used by the client when signing up this account (e.g. Affiliate, Self Generated, Web)", example = "Internet", required = true)
	@JsonProperty("source")
	private String source;

	/** The sub source. */
	@ApiModelProperty(value = "More specific information on the the marketing channel used by the client when signing up this account (e.g. google)", example = "PayPerClick V1", required = true)
	@JsonProperty("sub_source")
	private String subSource;

	/** The referral text. */
	@ApiModelProperty(value = "The referral text", required = true)
	@JsonProperty("referral_text")
	private String referralText;

	/** The ad campaign. */
	@ApiModelProperty(value = "The Ad Campaign which client saw before joining", example = "Currency_Forecasts", required = true)
	@JsonProperty("ad_campaign")
	private String adCampaign;

	/** The keywords. */
	@ApiModelProperty(value = "The keywords retrieved from cookies on client's device, that were used in the search engines when looking for CD", example = "transfer euros into pounds", required = true)
	@JsonProperty("keywords")
	private String keywords;

	/** The search engine. */
	@ApiModelProperty(value = "The search engine used to come to CD's website", example = "", required = true)
	@JsonProperty("search_engine")
	private String searchEngine;

	/** The email address. */
	@ApiModelProperty(value = "The email address", required = true)
	@JsonProperty("email_address")
	private String emailAddress;

	/** The eid status. */
	@ApiModelProperty(value = "The status of the KYC Electronic ID check", required = true)
	@JsonProperty("eid_status")
	private String eidStatus;

	/** The sanction status. */
	@ApiModelProperty(value = "The sanction status", required = true)
	@JsonProperty("sanction_status")
	private String sanctionStatus;

	/** The address type. */
	@ApiModelProperty(value = "The address type of this contact", example = "Current Address", required = true)
	@JsonProperty("address_type")
	private String addressType;

	/** The aza. */
	@ApiModelProperty(value = "The aza", required = true)
	@JsonProperty("aza")
	private String aza;

	/** The country of residence. */
	@ApiModelProperty(value = "The country of residence", example = "GBR", required = true)
	@JsonProperty("country_of_residence")
	private String countryOfResidence;

	/** The on queue. */
	@ApiModelProperty(value = "The on queue", required = true)
	@JsonProperty("on_queue")
	private Boolean onQueue;

	/** The region suburb. */
	@ApiModelProperty(value = "The region suburb", required = true)
	@JsonProperty("region_suburb")
	private String regionSuburb;

	/** The residential status. */
	@ApiModelProperty(value = "The residential status", required = true)
	@JsonProperty("residential_status")
	private String residentialStatus;

	/** The affiliate name. */
	@ApiModelProperty(value = "Name of the sales rep or associate responsible for signing this client", example = "Marketing HO", required = true)
	@JsonProperty("affiliate_name")
	private String affiliateName;

	/** The branch. */
	@ApiModelProperty(value = "The CD organisation branch which the client belongs to", example = "E4F-Johannesburg", required = true)
	@JsonProperty("branch")
	private String branch;

	/** The reg mode. */
	@ApiModelProperty(value = "The mode of registration. Whether it was via Internet or InPerson", example = "Internet", required = true)
	@JsonProperty("reg_mode")
	private String regMode;

	/** The turnover. */
  @ApiModelProperty(value = "Amount of turnover that client said they will probably do when they signed up, used for Fraud analysis", required = true, example = "10000000")
	@JsonProperty("turnover")
	private String turnover;

	/** The txn value. */
	@ApiModelProperty(value = "The estimated average value of each transaction", required = true)
	@JsonProperty("txn_value")
	private String txnValue;

	/** The event type. */
	@ApiModelProperty(value = "The event type", required = true)
	@JsonProperty("event_type")
	private String eventType;

	/** The job title. */
	@ApiModelProperty(value = "Job Title", example = "Director & Shareholder", required = true)
	@JsonProperty("job_title")
	private String jobTitle;

	/** The no of Contacts. */
	@ApiModelProperty(value = "Total number of contact", required = true)
	@JsonProperty("no_of_Contacts")
	private Integer noOfContacts;

	/** The reg mode y. */
	@ApiModelProperty(value = "The mode of registration. Whether it was via Internet or InPerson", example = "Internet", required = true)
	@JsonProperty("reg_mode_y")
	private String regModeY;

	/** The org code. */
	@ApiModelProperty(value = "Organization Code", example = "TorFXOz", required = true)
	@JsonProperty("org_code")
	private String orgCode;

	/** The cust type. */
	@ApiModelProperty(value = "Customer Type", example = "CFX", required = true)
	@JsonProperty("cust_type")
	private String custType;
	/**
	 * @return eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}

	/**
	 * @param customerNo
	 *            the customerNo to set
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	/**
	 * @return the screenResolution
	 */
	public String getScreenResolution() {
		return screenResolution;
	}

	/**
	 * @param screenResolution
	 *            the screenResolution to set
	 */
	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}

	/**
	 * @return the browserType
	 */
	public String getBrowserType() {
		return browserType;
	}

	/**
	 * @param browserType
	 *            the browserType to set
	 */
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	/**
	 * @return the browserVersion
	 */
	public String getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * @param browserVersion
	 *            the browserVersion to set
	 */
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName
	 *            the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the deviceManufacturer
	 */
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	/**
	 * @param deviceManufacturer
	 *            the deviceManufacturer to set
	 */
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	/**
	 * @return the deviceOsType
	 */
	public String getDeviceOsType() {
		return deviceOsType;
	}

	/**
	 * @param deviceOsType
	 *            the deviceOsType to set
	 */
	public void setDeviceOsType(String deviceOsType) {
		this.deviceOsType = deviceOsType;
	}

	/**
	 * @return the browserLang
	 */
	public String getBrowserLang() {
		return browserLang;
	}

	/**
	 * @param browserLang
	 *            the browserLang to set
	 */
	public void setBrowserLang(String browserLang) {
		this.browserLang = browserLang;
	}

	/**
	 * @return the browserOnline
	 */
	public String getBrowserOnline() {
		return browserOnline;
	}

	/**
	 * @param browserOnline
	 *            the browserOnline to set
	 */
	public void setBrowserOnline(String browserOnline) {
		this.browserOnline = browserOnline;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the billAdLine1
	 */
	public String getBillAdLine1() {
		return billAdLine1;
	}

	/**
	 * @param billAdLine1
	 *            the billAdLine1 to set
	 */
	public void setBillAdLine1(String billAdLine1) {
		this.billAdLine1 = billAdLine1;
	}

	/**
	 * @return the billAdZip
	 */
	public String getBillAdZip() {
		return billAdZip;
	}

	/**
	 * @param billAdZip
	 *            the billAdZip to set
	 */
	public void setBillAdZip(String billAdZip) {
		this.billAdZip = billAdZip;
	}

	/**
	 * @return the phoneHome
	 */
	public String getPhoneHome() {
		return phoneHome;
	}

	/**
	 * @param phoneHome
	 *            the phoneHome to set
	 */
	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	/**
	 * @return the phoneWork
	 */
	public String getPhoneWork() {
		return phoneWork;
	}

	/**
	 * @param phoneWork
	 *            the phoneWork to set
	 */
	public void setPhoneWork(String phoneWork) {
		this.phoneWork = phoneWork;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the opCountry
	 */
	public String getOpCountry() {
		return opCountry;
	}

	/**
	 * @param opCountry
	 *            the opCountry to set
	 */
	public void setOpCountry(String opCountry) {
		this.opCountry = opCountry;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the subSource
	 */
	public String getSubSource() {
		return subSource;
	}

	/**
	 * @param subSource
	 *            the subSource to set
	 */
	public void setSubSource(String subSource) {
		this.subSource = subSource;
	}

	/**
	 * @return the referralText
	 */
	public String getReferralText() {
		return referralText;
	}

	/**
	 * @param referralText
	 *            the referralText to set
	 */
	public void setReferralText(String referralText) {
		this.referralText = referralText;
	}

	/**
	 * @return the adCampaign
	 */
	public String getAdCampaign() {
		return adCampaign;
	}

	/**
	 * @param adCampaign
	 *            the adCampaign to set
	 */
	public void setAdCampaign(String adCampaign) {
		this.adCampaign = adCampaign;
	}

	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords
	 *            the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return the searchEngine
	 */
	public String getSearchEngine() {
		return searchEngine;
	}

	/**
	 * @param searchEngine
	 *            the searchEngine to set
	 */
	public void setSearchEngine(String searchEngine) {
		this.searchEngine = searchEngine;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the eidStatus
	 */
	public String getEidStatus() {
		return eidStatus;
	}

	/**
	 * @param eidStatus
	 *            the eidStatus to set
	 */
	public void setEidStatus(String eidStatus) {
		this.eidStatus = eidStatus;
	}

	/**
	 * @return the sanctionStatus
	 */
	public String getSanctionStatus() {
		return sanctionStatus;
	}

	/**
	 * @param sanctionStatus
	 *            the sanctionStatus to set
	 */
	public void setSanctionStatus(String sanctionStatus) {
		this.sanctionStatus = sanctionStatus;
	}

	/**
	 * @return the addressType
	 */
	public String getAddressType() {
		return addressType;
	}

	/**
	 * @param addressType
	 *            the addressType to set
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	/**
	 * @return the aza
	 */
	public String getAza() {
		return aza;
	}

	/**
	 * @param aza
	 *            the aza to set
	 */
	public void setAza(String aza) {
		this.aza = aza;
	}

	/**
	 * @return the countryOfResidence
	 */
	public String getCountryOfResidence() {
		return countryOfResidence;
	}

	/**
	 * @param countryOfResidence
	 *            the countryOfResidence to set
	 */
	public void setCountryOfResidence(String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	/**
	 * @return the onQueue
	 */
	public Boolean getOnQueue() {
		return onQueue;
	}

	/**
	 * @param onQueue
	 *            the onQueue to set
	 */
	public void setOnQueue(Boolean onQueue) {
		this.onQueue = onQueue;
	}

	/**
	 * @return the regionSuburb
	 */
	public String getRegionSuburb() {
		return regionSuburb;
	}

	/**
	 * @param regionSuburb
	 *            the regionSuburb to set
	 */
	public void setRegionSuburb(String regionSuburb) {
		this.regionSuburb = regionSuburb;
	}

	/**
	 * @return the residentialStatus
	 */
	public String getResidentialStatus() {
		return residentialStatus;
	}

	/**
	 * @param residentialStatus
	 *            the residentialStatus to set
	 */
	public void setResidentialStatus(String residentialStatus) {
		this.residentialStatus = residentialStatus;
	}

	/**
	 * @return the affiliateName
	 */
	public String getAffiliateName() {
		return affiliateName;
	}

	/**
	 * @param affiliateName
	 *            the affiliateName to set
	 */
	public void setAffiliateName(String affiliateName) {
		this.affiliateName = affiliateName;
	}

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the regMode
	 */
	public String getRegMode() {
		return regMode;
	}

	/**
	 * @param regMode
	 *            the regMode to set
	 */
	public void setRegMode(String regMode) {
		this.regMode = regMode;
	}

	/**
	 * @return the turnover
	 */
	public String getTurnover() {
		return turnover;
	}

	/**
	 * @param turnover
	 *            the turnover to set
	 */
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	/**
	 * @return the txnValue
	 */
	public String getTxnValue() {
		return txnValue;
	}

	/**
	 * @param txnValue
	 *            the txnValue to set
	 */
	public void setTxnValue(String txnValue) {
		this.txnValue = txnValue;
	}

	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @param jobTitle
	 *            the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * @return the noOfContacts
	 */
	public Integer getNoOfContacts() {
		return noOfContacts;
	}

	/**
	 * @param noOfContacts
	 *            the noOfContacts to set
	 */
	public void setNoOfContacts(Integer noOfContacts) {
		this.noOfContacts = noOfContacts;
	}

	/**
	 * @return the regModeY
	 */
	public String getRegModeY() {
		return regModeY;
	}

	/**
	 * @param regModeY
	 *            the regModeY to set
	 */
	public void setRegModeY(String regModeY) {
		this.regModeY = regModeY;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the custType
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType
	 *            the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}
}
