/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FraugsterProfileBaseRequest.
 *
 * @author manish
 */
public class FraugsterProfileBaseRequest extends FraugsterBaseRequest {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The title. */
	private String title;

	/** The preferred name. */
	private String preferredName;

	/** The first name. */
	private String firstName;

	/** The middle name. */
	private String middleName;

	/** The last name. */
	private String lastName;

	/** The dob. */
	private String dob;

	/** The address type. */
	private String addressType;

	/** The street. */
	private String street;

	/** The city. */
	private String city;

	/** The state. */
	private String state;

	/** The country. */
	private String country; // Enum

	/** The post code. */
	private String postCode;

	/** The phone home. */
	private String phoneHome;

	/** The phone type. */
	private String phoneType;

	/** The phone work. */
	private String phoneWork;

	/** The phone mobile. */
	private String phoneMobile;

	/** The primary phone. */
	private String primaryPhone; // Enum

	/** The email. */
	private String email;

	/** The primary contact. */
	private Boolean primaryContact;

	/** The nationality. */
	private String nationality;

	/** The gender. */
	private String gender; // Enum

	/** The occupation. */
	private String occupation;

	/** The passport number. */
	private String passportNumber;

	/** The passport country code. */
	private String passportCountryCode;

	/** The passport family name at birth. */
	private String passportFamilyNameAtBirth;

	/** The passport name at citizenship. */
	private String passportNameAtCitizenship;

	/** The passport place of birth. */
	private String passportPlaceOfBirth;

	/** The dl license number. */
	private String dlLicenseNumber;

	/** The d L card number. */
	private String dLCardNumber;

	/** The dl country code. */
	private String dlCountryCode;

	/** The dl state code. */
	private String dlStateCode;

	/** The dl expiry date. */
	private String dlExpiryDate;

	/** The medicare card number. */
	private String medicareCardNumber;

	/** The medicare reference number. */
	private String medicareReferenceNumber;

	/** The municipality of birth. */
	private String municipalityOfBirth;

	/** The building number. */
	private Integer buildingNumber;

	/** The unit number. */
	private Integer unitNumber;

	/** The sub city. */
	private String subCity;

	/** The region. */
	private String region;

	/** The channel. */
	private String channel; // enum

	/** The customer type. */
	private String customerType; // enum

	/** The countries of operation. */
	private String countriesOfOperation;

	/** The source of fund. */
	private String sourceOfFund;

	/** The source. */
	private String source;

	/** The sub source. */
	private String subSource;

	/** The referral. */
	private String referral;

	/** The referral text. */
	private String referralText;

	/** The extended referral. */
	private String extendedReferral;

	/** The ad campaign. */
	private String adCampaign;

	/** The keywords. */
	@ApiModelProperty(value = "The keywords retrieved from cookies on client's device, that were used in the search engines when looking for CD", required = true)
	private String keywords;

	/** The search engine. */
	private String searchEngine;

	/** The website. */
	private String website;

	/** The i P address. */
	private String iPAddress; // ipv4

	/** The i P latitude. */
	private Float iPLatitude;

	/** The i P longitude. */
	private Float iPLongitude;

	/** The sub building. */
	private String subBuildingorFlat;

	/** The account contact num. */
	private Integer accountContactNum;

	/** The national id. */
	private String nationalId;

	/** The country of birth. */
	private String countryOfBirth;
	
	/** The eid Status. */
	private String eidStatus;

	/** The sanction Status. */
	private String sanctionStatus;
	
	/** The aza. */
	private String aza;
	
	/** The region Suburb. */
	private String regionSuburb;
	
	/** The source onQueue. */
	private Boolean onQueue;
	
	/** The affiliateName */
	private String affiliateName;
	
	/** The regMode */
	private String regMode;
	
	/** The txnValue */
	private Double txnValue;
	
	/** The transactionValue added for fraudpredict */
	private String transactionValue;
	
	/** The residentialStatus */
	private String residentialStatus;
	
	/** The turnover */
	  @ApiModelProperty(value = "Amount of turnover that client said they will probably do when they signed up, used for Fraud analysis", required = true, example = "10000000")
	private String turnover;
	
	/** The txnValue */
	private String branch;
	
	/** The billingAddress */
	private String billingAddress;
	
	/** The txn value reange. */
	private String txnValueRange;
	
	/** The country of residence code. */
	private String countryOfResidenceCode;

	/** The jobTitle. */
	private String jobTitle;

	/**
	 * @return billingAddress
	 */
	public String getBillingAddress() {
		return billingAddress;
	}

	/**
	 * @param billingAddress
	 */
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * @return transactionValue
	 */
	public String getTransactionValue() {
		return transactionValue;
	}

	/**
	 * @param transactionValue
	 */
	public void setTransactionValue(String transactionValue) {
		this.transactionValue = transactionValue;
	}

	/**
	 * @return aza
	 */
	public String getAza() {
		return aza;
	}

	/**
	 * @param aza
	 */
	public void setAza(String aza) {
		this.aza = aza;
	}

	/**
	 * @return regionSuburb
	 */
	public String getRegionSuburb() {
		return regionSuburb;
	}

	/**
	 * @param regionSuburb
	 */
	public void setRegionSuburb(String regionSuburb) {
		this.regionSuburb = regionSuburb;
	}

	/**
	 * @return onQueue
	 */
	public Boolean getOnQueue() {
		return onQueue;
	}

	/**
	 * @param onQueue
	 */
	public void setOnQueue(Boolean onQueue) {
		this.onQueue = onQueue;
	}

	/**
	 * @return affiliateName
	 */
	public String getAffiliateName() {
		return affiliateName;
	}

	/**
	 * @param affiliateName
	 */
	public void setAffiliateName(String affiliateName) {
		this.affiliateName = affiliateName;
	}

	/**
	 * @return regMode
	 */
	public String getRegMode() {
		return regMode;
	}

	/**
	 * @param regMode
	 */
	public void setRegMode(String regMode) {
		this.regMode = regMode;
	}

	/**
	 * @return txnValue
	 */
	public Double getTxnValue() {
		return txnValue;
	}

	/**
	 * @param txnValue
	 */
	public void setTxnValue(Double txnValue) {
		this.txnValue = txnValue;
	}

	/**
	 * @return residentialStatus
	 */
	public String getResidentialStatus() {
		return residentialStatus;
	}

	/**
	 * @param residentialStatus
	 */
	public void setResidentialStatus(String residentialStatus) {
		this.residentialStatus = residentialStatus;
	}

	/**
	 * @return turnover
	 */
	public String getTurnover() {
		return turnover;
	}

	/**
	 * @param turnover
	 */
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	/**
	 * @return branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	/**
	 * @return eidStatus
	 */
	public String getEidStatus() {
		return eidStatus;
	}

	/**
	 * @param eidStatus
	 */
	public void setEidStatus(String eidStatus) {
		this.eidStatus = eidStatus;
	}

	/**
	 * @return sanctionStatus
	 */
	public String getSanctionStatus() {
		return sanctionStatus;
	}

	/**
	 * @param sanctionStatus
	 */
	public void setSanctionStatus(String sanctionStatus) {
		this.sanctionStatus = sanctionStatus;
	}
	

	/**
	 * Gets the account contact num.
	 *
	 * @return the account contact num
	 */
	public Integer getAccountContactNum() {
		return accountContactNum;
	}

	/**
	 * Sets the account contact num.
	 *
	 * @param accountContactNum
	 *            the new account contact num
	 */
	public void setAccountContactNum(Integer accountContactNum) {
		this.accountContactNum = accountContactNum;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the preferred name.
	 *
	 * @return the preferred name
	 */
	public String getPreferredName() {
		return preferredName;
	}

	/**
	 * Sets the preferred name.
	 *
	 * @param preferredName
	 *            the new preferred name
	 */
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the middle name.
	 *
	 * @return the middle name
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Sets the middle name.
	 *
	 * @param middleName
	 *            the new middle name
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the dob.
	 *
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * Sets the dob.
	 *
	 * @param dob
	 *            the new dob
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * Gets the address type.
	 *
	 * @return the address type
	 */
	public String getAddressType() {
		return addressType;
	}

	/**
	 * Sets the address type.
	 *
	 * @param addressType
	 *            the new address type
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the street.
	 *
	 * @param street
	 *            the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city
	 *            the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state
	 *            the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country
	 *            the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the post code.
	 *
	 * @return the post code
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * Sets the post code.
	 *
	 * @param postCode
	 *            the new post code
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * Gets the phone home.
	 *
	 * @return the phone home
	 */
	public String getPhoneHome() {
		return phoneHome;
	}

	/**
	 * Sets the phone home.
	 *
	 * @param phoneHome
	 *            the new phone home
	 */
	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	/**
	 * Gets the phone work.
	 *
	 * @return the phone work
	 */
	public String getPhoneWork() {
		return phoneWork;
	}

	/**
	 * Sets the phone work.
	 *
	 * @param phoneWork
	 *            the new phone work
	 */
	public void setPhoneWork(String phoneWork) {
		this.phoneWork = phoneWork;
	}

	/**
	 * Gets the phone mobile.
	 *
	 * @return the phone mobile
	 */
	public String getPhoneMobile() {
		return phoneMobile;
	}

	/**
	 * Sets the phone mobile.
	 *
	 * @param phoneMobile
	 *            the new phone mobile
	 */
	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	/**
	 * Gets the primary phone.
	 *
	 * @return the primary phone
	 */
	public String getPrimaryPhone() {
		return primaryPhone;
	}

	/**
	 * Sets the primary phone.
	 *
	 * @param primaryPhone
	 *            the new primary phone
	 */
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the primary contact.
	 *
	 * @return the primary contact
	 */
	public Boolean getPrimaryContact() {
		return primaryContact;
	}

	/**
	 * Sets the primary contact.
	 *
	 * @param primaryContact
	 *            the new primary contact
	 */
	public void setPrimaryContact(Boolean primaryContact) {
		this.primaryContact = primaryContact;
	}

	/**
	 * Gets the nationality.
	 *
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Sets the nationality.
	 *
	 * @param nationality
	 *            the new nationality
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender
	 *            the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the occupation.
	 *
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * Sets the occupation.
	 *
	 * @param occupation
	 *            the new occupation
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * Gets the passport number.
	 *
	 * @return the passport number
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	/**
	 * Sets the passport number.
	 *
	 * @param passportNumber
	 *            the new passport number
	 */
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * Gets the passport country code.
	 *
	 * @return the passport country code
	 */
	public String getPassportCountryCode() {
		return passportCountryCode;
	}

	/**
	 * Sets the passport country code.
	 *
	 * @param passportCountryCode
	 *            the new passport country code
	 */
	public void setPassportCountryCode(String passportCountryCode) {
		this.passportCountryCode = passportCountryCode;
	}

	/**
	 * Gets the passport family name at birth.
	 *
	 * @return the passport family name at birth
	 */
	public String getPassportFamilyNameAtBirth() {
		return passportFamilyNameAtBirth;
	}

	/**
	 * Sets the passport family name at birth.
	 *
	 * @param passportFamilyNameAtBirth
	 *            the new passport family name at birth
	 */
	public void setPassportFamilyNameAtBirth(String passportFamilyNameAtBirth) {
		this.passportFamilyNameAtBirth = passportFamilyNameAtBirth;
	}

	/**
	 * Gets the passport name at citizenship.
	 *
	 * @return the passport name at citizenship
	 */
	public String getPassportNameAtCitizenship() {
		return passportNameAtCitizenship;
	}

	/**
	 * Sets the passport name at citizenship.
	 *
	 * @param passportNameAtCitizenship
	 *            the new passport name at citizenship
	 */
	public void setPassportNameAtCitizenship(String passportNameAtCitizenship) {
		this.passportNameAtCitizenship = passportNameAtCitizenship;
	}

	/**
	 * Gets the passport place of birth.
	 *
	 * @return the passport place of birth
	 */
	public String getPassportPlaceOfBirth() {
		return passportPlaceOfBirth;
	}

	/**
	 * Sets the passport place of birth.
	 *
	 * @param passportPlaceOfBirth
	 *            the new passport place of birth
	 */
	public void setPassportPlaceOfBirth(String passportPlaceOfBirth) {
		this.passportPlaceOfBirth = passportPlaceOfBirth;
	}

	/**
	 * Gets the dl license number.
	 *
	 * @return the dl license number
	 */
	public String getDlLicenseNumber() {
		return dlLicenseNumber;
	}

	/**
	 * Sets the dl license number.
	 *
	 * @param dlLicenseNumber
	 *            the new dl license number
	 */
	public void setDlLicenseNumber(String dlLicenseNumber) {
		this.dlLicenseNumber = dlLicenseNumber;
	}

	/**
	 * Gets the d L card number.
	 *
	 * @return the d L card number
	 */
	public String getdLCardNumber() {
		return dLCardNumber;
	}

	/**
	 * Sets the d L card number.
	 *
	 * @param dLCardNumber
	 *            the new d L card number
	 */
	public void setdLCardNumber(String dLCardNumber) {
		this.dLCardNumber = dLCardNumber;
	}

	/**
	 * Gets the dl country code.
	 *
	 * @return the dl country code
	 */
	public String getDlCountryCode() {
		return dlCountryCode;
	}

	/**
	 * Sets the dl country code.
	 *
	 * @param dlCountryCode
	 *            the new dl country code
	 */
	public void setDlCountryCode(String dlCountryCode) {
		this.dlCountryCode = dlCountryCode;
	}

	/**
	 * Gets the dl state code.
	 *
	 * @return the dl state code
	 */
	public String getDlStateCode() {
		return dlStateCode;
	}

	/**
	 * Sets the dl state code.
	 *
	 * @param dlStateCode
	 *            the new dl state code
	 */
	public void setDlStateCode(String dlStateCode) {
		this.dlStateCode = dlStateCode;
	}

	/**
	 * Gets the dl expiry date.
	 *
	 * @return the dl expiry date
	 */
	public String getDlExpiryDate() {
		return dlExpiryDate;
	}

	/**
	 * Sets the dl expiry date.
	 *
	 * @param dlExpiryDate
	 *            the new dl expiry date
	 */
	public void setDlExpiryDate(String dlExpiryDate) {
		this.dlExpiryDate = dlExpiryDate;
	}

	/**
	 * Gets the medicare card number.
	 *
	 * @return the medicare card number
	 */
	public String getMedicareCardNumber() {
		return medicareCardNumber;
	}

	/**
	 * Sets the medicare card number.
	 *
	 * @param medicareCardNumber
	 *            the new medicare card number
	 */
	public void setMedicareCardNumber(String medicareCardNumber) {
		this.medicareCardNumber = medicareCardNumber;
	}

	/**
	 * Gets the medicare reference number.
	 *
	 * @return the medicare reference number
	 */
	public String getMedicareReferenceNumber() {
		return medicareReferenceNumber;
	}

	/**
	 * Sets the medicare reference number.
	 *
	 * @param medicareReferenceNumber
	 *            the new medicare reference number
	 */
	public void setMedicareReferenceNumber(String medicareReferenceNumber) {
		this.medicareReferenceNumber = medicareReferenceNumber;
	}

	/**
	 * Gets the municipality of birth.
	 *
	 * @return the municipality of birth
	 */
	public String getMunicipalityOfBirth() {
		return municipalityOfBirth;
	}

	/**
	 * Sets the municipality of birth.
	 *
	 * @param municipalityOfBirth
	 *            the new municipality of birth
	 */
	public void setMunicipalityOfBirth(String municipalityOfBirth) {
		this.municipalityOfBirth = municipalityOfBirth;
	}

	/**
	 * Gets the sub city.
	 *
	 * @return the sub city
	 */
	public String getSubCity() {
		return subCity;
	}

	/**
	 * Sets the sub city.
	 *
	 * @param subCity
	 *            the new sub city
	 */
	public void setSubCity(String subCity) {
		this.subCity = subCity;
	}

	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Sets the region.
	 *
	 * @param region
	 *            the new region
	 */
	public void setRegion(String region) {
		this.region = region;
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
	 * Gets the customer type.
	 *
	 * @return the customer type
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * Sets the customer type.
	 *
	 * @param customerType
	 *            the new customer type
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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
	 * Gets the phone type.
	 *
	 * @return the phone type
	 */
	public String getPhoneType() {
		return phoneType;
	}

	/**
	 * Sets the phone type.
	 *
	 * @param phoneType
	 *            the new phone type
	 */
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
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
	 * Gets the i P address.
	 *
	 * @return the i P address
	 */
	public String getiPAddress() {
		return iPAddress;
	}

	/**
	 * Sets the i P address.
	 *
	 * @param iPAddress
	 *            the new i P address
	 */
	public void setiPAddress(String iPAddress) {
		this.iPAddress = iPAddress;
	}

	/**
	 * Gets the i P latitude.
	 *
	 * @return the i P latitude
	 */
	public Float getiPLatitude() {
		return iPLatitude;
	}

	/**
	 * Sets the i P latitude.
	 *
	 * @param iPLatitude
	 *            the new i P latitude
	 */
	public void setiPLatitude(Float iPLatitude) {
		this.iPLatitude = iPLatitude;
	}

	/**
	 * Gets the i P longitude.
	 *
	 * @return the i P longitude
	 */
	public Float getiPLongitude() {
		return iPLongitude;
	}

	/**
	 * Sets the i P longitude.
	 *
	 * @param iPLongitude
	 *            the new i P longitude
	 */
	public void setiPLongitude(Float iPLongitude) {
		this.iPLongitude = iPLongitude;
	}

	/**
	 * Gets the national id.
	 *
	 * @return the national id
	 */
	public String getNationalId() {
		return nationalId;
	}

	/**
	 * Sets the national id.
	 *
	 * @param nationalId
	 *            the new national id
	 */
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	/**
	 * Gets the building number.
	 *
	 * @return the building number
	 */
	public Integer getBuildingNumber() {
		return buildingNumber;
	}

	/**
	 * Sets the building number.
	 *
	 * @param buildingNumber
	 *            the new building number
	 */
	public void setBuildingNumber(Integer buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	/**
	 * Gets the unit number.
	 *
	 * @return the unit number
	 */
	public Integer getUnitNumber() {
		return unitNumber;
	}

	/**
	 * Sets the unit number.
	 *
	 * @param unitNumber
	 *            the new unit number
	 */
	public void setUnitNumber(Integer unitNumber) {
		this.unitNumber = unitNumber;
	}

	/**
	 * Gets the sub buildingor flat.
	 *
	 * @return the sub buildingor flat
	 */
	public String getSubBuildingorFlat() {
		return subBuildingorFlat;
	}

	/**
	 * Sets the sub buildingor flat.
	 *
	 * @param subBuildingorFlat
	 *            the new sub buildingor flat
	 */
	public void setSubBuildingorFlat(String subBuildingorFlat) {
		this.subBuildingorFlat = subBuildingorFlat;
	}

	/**
	 * Gets the country of birth.
	 *
	 * @return the countryOfBirth
	 */
	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	/**
	 * Sets the country of birth.
	 *
	 * @param countryOfBirth
	 *            the countryOfBirth to set
	 */
	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	/**
	 * @return the txnValueReange
	 */
	public String getTxnValueRange() {
		return txnValueRange;
	}

	/**
	 * @return the countryOfResidenceCode
	 */
	public String getCountryOfResidenceCode() {
		return countryOfResidenceCode;
	}

	/**
	 * @param txnValueReange the txnValueReange to set
	 */
	public void setTxnValueRange(String txnValueReange) {
		this.txnValueRange = txnValueReange;
	}

	/**
	 * @param countryOfResidenceCode the countryOfResidenceCode to set
	 */
	public void setCountryOfResidenceCode(String countryOfResidenceCode) {
		this.countryOfResidenceCode = countryOfResidenceCode;
	}

	/**
	 * @param jobTitle the jobTitle to get
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
}
