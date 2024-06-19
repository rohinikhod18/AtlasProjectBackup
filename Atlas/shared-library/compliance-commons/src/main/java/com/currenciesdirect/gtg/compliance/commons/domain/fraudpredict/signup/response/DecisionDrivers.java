package com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.signup.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.response.Feature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DecisionDrivers implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The txnValue */
	@ApiModelProperty(value = "The estimated yearly transaction value", example = "100,000 - 250,000", required = true)
	@JsonProperty("txn_value")
	private Feature txnValue;

	/** The source */
	@ApiModelProperty(value = "The marketing channel used by the client when signing up this account (e.g. Affiliate, Self Generated, Web)", example = "Internet", required = true)
	@JsonProperty("source")
	private Feature source;

	/** The regMode */
	@ApiModelProperty(value = "The mode of registration. Whether it was online or offline", required = true)
	@JsonProperty("reg_mode")
	private Feature regMode;

	/** The affiliateName */
	@ApiModelProperty(value = "Name of the sales rep or associate responsible for signing this client", example = "Marketing HO", required = true)
	@JsonProperty("affiliate_name")
	private Feature affiliateName;

	/** The adCampaign */
	@ApiModelProperty(value = "The Ad Campaign which client saw before joining", example = "Currency_Forecasts", required = true)
	@JsonProperty("ad_campaign")
	private Feature adCampaign;

	/** The channel */
	@JsonProperty("channel")
	private Feature channel;

	/** The ipLineSpeed */
	@JsonProperty("ip_line_speed")
	private Feature ipLineSpeed;

	/** The searchEngine */
	@ApiModelProperty(value = "The search engine used to come to CD's website", example = "", required = true)
	@JsonProperty("search_engine")
	private Feature searchEngine;

	/** The genderFullContact */
	@JsonProperty("gender_fullcontact")
	private Feature genderFullContact;

	/** The browserLang */
	@JsonProperty("browser_lang")
	private Feature browserLang;

	/** The screenResolution */
	@JsonProperty("screen_resolution")
	private Feature screenResolution;

	/** The countryOfResidence */
	@ApiModelProperty(value = "The country of residence", example = "GBR", required = true)
	@JsonProperty("country_of_residence")
	private Feature countryOfResidence;

	/** The fullContactMatched */
	@JsonProperty("fullcontact_matched")
	private Feature fullContactMatched;

	/** The ipLongitude */
	@JsonProperty("ip_longitude")
	private Feature ipLongitude;

	/** The keywords */
	@ApiModelProperty(value = "The keywords retrieved from cookies on client's device, that were used in the search engines when looking for CD", example = "transfer euros into pounds", required = true)
	@JsonProperty("keywords")
	private Feature keywords;

	/** The ipConnectionType */
	@JsonProperty("ip_connection_type")
	private Feature ipConnectionType;

	/** The deviceType */
	@JsonProperty("device_type")
	private Feature deviceType;

	/** The aza */
	@JsonProperty("aza")
	private Feature aza;

	/** The opCountry */
	@JsonProperty("op_country")
	private Feature opCountry;

	/** The residentialStatus */
	@JsonProperty("residential_status")
	private Feature residentialStatus;

	/** The addressType */
	@ApiModelProperty(value = "The address type of this contact", example = "Current Address", required = true)
	@JsonProperty("address_type")
	private Feature addressType;

	/** The onQueue */
	@JsonProperty("on_queue")
	private Feature onQueue;

	/** The deviceManufacturer */
	@ApiModelProperty(value = "Manufacturer of Device", example = "Amazon", required = true)
	@JsonProperty("device_manufacturer")
	private Feature deviceManufacturer;

	/** The ipCarrier */
	@JsonProperty("ip_carrier")
	private Feature ipCarrier;

	/** The deviceName */
	@ApiModelProperty(value = "Device Model Name", example = "Kindle Fire 72.2.7", required = true)
	@JsonProperty("device_name")
	private Feature deviceName;

	/** The ipAnonymizerStatus */
	@JsonProperty("ip_anonymizer_status")
	private Feature ipAnonymizerStatus;

	/** The txnValue */
	@JsonProperty("ip_routing_type")
	private Feature ipRoutingType;

	/** The ipRoutingType */
	@JsonProperty("eid_status")
	private Feature eidStatus;

	/** The ageRangeFullContact */
	@JsonProperty("age_range_fullcontact")
	private Feature ageRangeFullContact;

	/** The locationCountryFullContact */
	@JsonProperty("location_country_fullcontact")
	private Feature locationCountryFullContact;

	/** The ipLatitude */
	@JsonProperty("ip_latitude")
	private Feature ipLatitude;

	/** The browserType */
	@JsonProperty("browser_type")
	private Feature browserType;

	/** The title */
	@ApiModelProperty(value = "The client's title", example = "Mr", required = true)
	@JsonProperty("title")
	private Feature title;

	/** The referralText */
	@ApiModelProperty(value = "Marketing information showing how the client was referred", example = "Word of mouth" , required = true)
	@JsonProperty("referral_text")
	private Feature referralText;

	/** The browserOnline */
	@JsonProperty("browser_online")
	private Feature browserOnline;

	/** The deviceOsType */
	@JsonProperty("device_os_type")
	private Feature deviceOsType;

	/** The subSource */
	@ApiModelProperty(value = "More specific information on the the marketing channel used by the client when signing up this account (e.g. google)", example = "PayPerClick V1", required = true)
	@JsonProperty("sub_source")
	private Feature subSource;

	/** The browserVersion */
	@JsonProperty("browser_version")
	private Feature browserVersion;

	/** The emailDomain */
	@JsonProperty("email_domain")
	private Feature emailDomain;

	/** The branch */
	@ApiModelProperty(value = "The CD organisation branch which the client belongs to", example = "E4F-Johannesburg", required = true)
	@JsonProperty("branch")
	private Feature branch;

	/** The sanctionStatus */
	@JsonProperty("sanction_status")
	private Feature sanctionStatus;

	/** The turnover */
	  @ApiModelProperty(value = "Amount of turnover that client said they will probably do when they signed up, used for Fraud analysis", required = true, example = "10000000")
	@JsonProperty("turnover")
	private Feature turnover;

	/** The regionSuburb */
	@JsonProperty("region_suburb")
	private Feature regionSuburb;

	/** The socialProfilesCount */
	@JsonProperty("social_profiles_count")
	private Feature socialProfilesCount;

	/** The state. */
    @JsonProperty("state")
    private Feature state;
	/**
	 * @return txnValue
	 */
	public Feature getTxnValue() {
		return txnValue;
	}

	/**
	 * @param txnValue
	 */
	public void setTxnValue(Feature txnValue) {
		this.txnValue = txnValue;
	}

	/**
	 * @return source
	 */
	public Feature getSource() {
		return source;
	}

	/**
	 * @param source
	 */
	public void setSource(Feature source) {
		this.source = source;
	}

	/**
	 * @return regMode
	 */
	public Feature getRegMode() {
		return regMode;
	}

	/**
	 * @param regMode
	 */
	public void setRegMode(Feature regMode) {
		this.regMode = regMode;
	}

	/**
	 * @return affiliateName
	 */
	public Feature getAffiliateName() {
		return affiliateName;
	}

	/**
	 * @param affiliateName
	 */
	public void setAffiliateName(Feature affiliateName) {
		this.affiliateName = affiliateName;
	}

	/**
	 * @return adCampaign
	 */
	public Feature getAdCampaign() {
		return adCampaign;
	}

	/**
	 * @param adCampaign
	 */
	public void setAdCampaign(Feature adCampaign) {
		this.adCampaign = adCampaign;
	}

	/**
	 * @return channel
	 */
	public Feature getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 */
	public void setChannel(Feature channel) {
		this.channel = channel;
	}

	/**
	 * @return ipLineSpeed
	 */
	public Feature getIpLineSpeed() {
		return ipLineSpeed;
	}

	/**
	 * @param ipLineSpeed
	 */
	public void setIpLineSpeed(Feature ipLineSpeed) {
		this.ipLineSpeed = ipLineSpeed;
	}

	/**
	 * @return searchEngine
	 */
	public Feature getSearchEngine() {
		return searchEngine;
	}

	/**
	 * @param searchEngine
	 */
	public void setSearchEngine(Feature searchEngine) {
		this.searchEngine = searchEngine;
	}

	/**
	 * @return genderFullContact
	 */
	public Feature getGenderFullContact() {
		return genderFullContact;
	}

	/**
	 * @param genderFullContact
	 */
	public void setGenderFullContact(Feature genderFullContact) {
		this.genderFullContact = genderFullContact;
	}

	/**
	 * @return browserLang
	 */
	public Feature getBrowserLang() {
		return browserLang;
	}

	/**
	 * @param browserLang
	 */
	public void setBrowserLang(Feature browserLang) {
		this.browserLang = browserLang;
	}

	/**
	 * @return screenResolution
	 */
	public Feature getScreenResolution() {
		return screenResolution;
	}

	/**
	 * @param screenResolution
	 */
	public void setScreenResolution(Feature screenResolution) {
		this.screenResolution = screenResolution;
	}

	/**
	 * @return countryOfResidence
	 */
	public Feature getCountryOfResidence() {
		return countryOfResidence;
	}

	/**
	 * @param countryOfResidence
	 */
	public void setCountryOfResidence(Feature countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	/**
	 * @return fullContactMatched
	 */
	public Feature getFullContactMatched() {
		return fullContactMatched;
	}

	/**
	 * @param fullContactMatched
	 */
	public void setFullContactMatched(Feature fullContactMatched) {
		this.fullContactMatched = fullContactMatched;
	}

	/**
	 * @return ipLongitude
	 */
	public Feature getIpLongitude() {
		return ipLongitude;
	}

	/**
	 * @param ipLongitude
	 */
	public void setIpLongitude(Feature ipLongitude) {
		this.ipLongitude = ipLongitude;
	}

	/**
	 * @return keywords
	 */
	public Feature getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords
	 */
	public void setKeywords(Feature keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return ipConnectionType
	 */
	public Feature getIpConnectionType() {
		return ipConnectionType;
	}

	/**
	 * @param ipConnectionType
	 */
	public void setIpConnectionType(Feature ipConnectionType) {
		this.ipConnectionType = ipConnectionType;
	}

	/**
	 * @return deviceType
	 */
	public Feature getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 */
	public void setDeviceType(Feature deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return aza
	 */
	public Feature getAza() {
		return aza;
	}

	/**
	 * @param aza
	 */
	public void setAza(Feature aza) {
		this.aza = aza;
	}

	/**
	 * @return opCountry
	 */
	public Feature getOpCountry() {
		return opCountry;
	}

	/**
	 * @param opCountry
	 */
	public void setOpCountry(Feature opCountry) {
		this.opCountry = opCountry;
	}

	/**
	 * @return residentialStatus
	 */
	public Feature getResidentialStatus() {
		return residentialStatus;
	}

	/**
	 * @param residentialStatus
	 */
	public void setResidentialStatus(Feature residentialStatus) {
		this.residentialStatus = residentialStatus;
	}

	/**
	 * @return addressType
	 */
	public Feature getAddressType() {
		return addressType;
	}

	/**
	 * @param addressType
	 */
	public void setAddressType(Feature addressType) {
		this.addressType = addressType;
	}

	/**
	 * @return onQueue
	 */
	public Feature getOnQueue() {
		return onQueue;
	}

	/**
	 * @param onQueue
	 */
	public void setOnQueue(Feature onQueue) {
		this.onQueue = onQueue;
	}

	/**
	 * @return deviceManufacturer
	 */
	public Feature getDeviceManufacturer() {
		return deviceManufacturer;
	}

	/**
	 * @param deviceManufacturer
	 */
	public void setDeviceManufacturer(Feature deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	/**
	 * @return ipCarrier
	 */
	public Feature getIpCarrier() {
		return ipCarrier;
	}

	/**
	 * @param ipCarrier
	 */
	public void setIpCarrier(Feature ipCarrier) {
		this.ipCarrier = ipCarrier;
	}

	/**
	 * @return deviceName
	 */
	public Feature getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName
	 */
	public void setDeviceName(Feature deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return ipAnonymizerStatus
	 */
	public Feature getIpAnonymizerStatus() {
		return ipAnonymizerStatus;
	}

	/**
	 * @param ipAnonymizerStatus
	 */
	public void setIpAnonymizerStatus(Feature ipAnonymizerStatus) {
		this.ipAnonymizerStatus = ipAnonymizerStatus;
	}

	/**
	 * @return ipRoutingType
	 */
	public Feature getIpRoutingType() {
		return ipRoutingType;
	}

	/**
	 * @param ipRoutingType
	 */
	public void setIpRoutingType(Feature ipRoutingType) {
		this.ipRoutingType = ipRoutingType;
	}

	/**
	 * @return eidStatus
	 */
	public Feature getEidStatus() {
		return eidStatus;
	}

	/**
	 * @param eidStatus
	 */
	public void setEidStatus(Feature eidStatus) {
		this.eidStatus = eidStatus;
	}

	/**
	 * @return ageRangeFullContact
	 */
	public Feature getAgeRangeFullContact() {
		return ageRangeFullContact;
	}

	/**
	 * @param ageRangeFullContact
	 */
	public void setAgeRangeFullContact(Feature ageRangeFullContact) {
		this.ageRangeFullContact = ageRangeFullContact;
	}

	/**
	 * @return locationCountryFullContact
	 */
	public Feature getLocationCountryFullContact() {
		return locationCountryFullContact;
	}

	/**
	 * @param locationCountryFullContact
	 */
	public void setLocationCountryFullContact(Feature locationCountryFullContact) {
		this.locationCountryFullContact = locationCountryFullContact;
	}

	/**
	 * @return ipLatitude
	 */
	public Feature getIpLatitude() {
		return ipLatitude;
	}

	/**
	 * @param ipLatitude
	 */
	public void setIpLatitude(Feature ipLatitude) {
		this.ipLatitude = ipLatitude;
	}

	/**
	 * @return browserType
	 */
	public Feature getBrowserType() {
		return browserType;
	}

	/**
	 * @param browserType
	 */
	public void setBrowserType(Feature browserType) {
		this.browserType = browserType;
	}

	/**
	 * @return title
	 */
	public Feature getTitle() {
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle(Feature title) {
		this.title = title;
	}

	/**
	 * @return referralText
	 */
	public Feature getReferralText() {
		return referralText;
	}

	/**
	 * @param referralText
	 */
	public void setReferralText(Feature referralText) {
		this.referralText = referralText;
	}

	/**
	 * @return browserOnline
	 */
	public Feature getBrowserOnline() {
		return browserOnline;
	}

	/**
	 * @param browserOnline
	 */
	public void setBrowserOnline(Feature browserOnline) {
		this.browserOnline = browserOnline;
	}

	/**
	 * @return deviceOsType
	 */
	public Feature getDeviceOsType() {
		return deviceOsType;
	}

	/**
	 * @param deviceOsType
	 */
	public void setDeviceOsType(Feature deviceOsType) {
		this.deviceOsType = deviceOsType;
	}

	/**
	 * @return subSource
	 */
	public Feature getSubSource() {
		return subSource;
	}

	/**
	 * @param subSource
	 */
	public void setSubSource(Feature subSource) {
		this.subSource = subSource;
	}

	/**
	 * @return browserVersion
	 */
	public Feature getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * @param browserVersion
	 */
	public void setBrowserVersion(Feature browserVersion) {
		this.browserVersion = browserVersion;
	}

	/**
	 * @return emailDomain
	 */
	public Feature getEmailDomain() {
		return emailDomain;
	}

	/**
	 * @param emailDomain
	 */
	public void setEmailDomain(Feature emailDomain) {
		this.emailDomain = emailDomain;
	}

	/**
	 * @return branch
	 */
	public Feature getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 */
	public void setBranch(Feature branch) {
		this.branch = branch;
	}

	/**
	 * @return sanctionStatus
	 */
	public Feature getSanctionStatus() {
		return sanctionStatus;
	}

	/**
	 * @param sanctionStatus
	 */
	public void setSanctionStatus(Feature sanctionStatus) {
		this.sanctionStatus = sanctionStatus;
	}

	/**
	 * @return turnover
	 */
	public Feature getTurnover() {
		return turnover;
	}

	/**
	 * @param turnover
	 */
	public void setTurnover(Feature turnover) {
		this.turnover = turnover;
	}

	/**
	 * @return regionSuburb
	 */
	public Feature getRegionSuburb() {
		return regionSuburb;
	}

	/**
	 * @param regionSuburb
	 */
	public void setRegionSuburb(Feature regionSuburb) {
		this.regionSuburb = regionSuburb;
	}

	/**
	 * @return socialProfilesCount
	 */
	public Feature getSocialProfilesCount() {
		return socialProfilesCount;
	}

	/**
	 * @param socialProfilesCount
	 */
	public void setSocialProfilesCount(Feature socialProfilesCount) {
		this.socialProfilesCount = socialProfilesCount;
	}
	
	 /**
     * Gets the state.
     *
     * @return the state
     */
    public Feature getState() {
      return state;
    }
  
    /**
     * Sets the state.
     *
     * @param state the new state
     */
    public void setState(Feature state) {
      this.state = state;
    }

}
