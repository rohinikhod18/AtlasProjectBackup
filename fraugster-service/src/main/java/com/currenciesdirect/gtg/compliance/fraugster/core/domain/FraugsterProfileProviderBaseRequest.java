/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FraugsterProfileProviderBaseRequest.
 *
 * @author manish
 */
public class FraugsterProfileProviderBaseRequest extends FraugsterProviderBaseRequest {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The acct contact num. */
	@JsonProperty("acct_contact_num")
	private Integer acctContactNum;
	
	/** The cust title. */
	@JsonProperty("cust_title")
	private String custTitle;
	
	/** The preferred name. */
	@JsonProperty("preferred_name")
	private String preferredName;
	
	/** The cust first name. */
	@JsonProperty("cust_first_name")
	private String custFirstName;
	
	/** The cust middle name. */
	@JsonProperty("cust_middle_name")
	private String custMiddleName;
	
	/** The cust last name. */
	@JsonProperty("cust_last_name")
	private String custLastName;
	
	/** The cust DOB. */
	@JsonProperty("cust_dob")
	private String custDOB;
	
	/** The bill add name. */
	@JsonProperty("bill_ad_name")
	private String billAddName;
	
	/** The bill add line 1. */
	@JsonProperty("bill_ad_line1")
	private String billAddLine1;
	
	/** The bill add city. */
	@JsonProperty("bill_ad_city")
	private String billAddCity;
	
	/** The bill add state. */
	@JsonProperty("bill_ad_state")
	private String billAddState;
	
	/** The bill add ctry. */
	@JsonProperty("bill_ad_ctry")
	private String billAddCtry;
	
	/** The bill add zip. */
	@JsonProperty("bill_ad_zip")
	private String billAddZip;
	
	/** The national ID. */
	@JsonProperty("national_id")
	private String nationalID;
	
	/** The phone home. */
	@JsonProperty("phone_home")
	private String phoneHome;
	
	/** The phone work. */
	@JsonProperty("phone_work")
	private String phoneWork;
	
	/** The scndry phone. */
	@JsonProperty("scndry_phone")
	private String scndryPhone;
	
	/** The phone type. */
	@JsonProperty("phone_type")
	private String phoneType;
	
	/** The cust email. */
	@JsonProperty("cust_email")
	private String custEmail;
	
	/** The primary contact. */
	@JsonProperty("primary_contact")
	private String primaryContact;
	
	/** The cust nationality. */
	@JsonProperty("cust_nationality")
	private String custNationality;
	
	/** The cust MF. */
	@JsonProperty("cust_m_f")
	private String custMF;
	
	/** The cust occptn. */
	@JsonProperty("cust_occptn")
	private String custOccptn;
	
	/** The passport id num. */
	@JsonProperty("passport_id_num")
	private String passportIdNum;
	
	/** The passport country code. */
	@JsonProperty("passport_country_code")
	private String passportCountryCode;
	
	/** The passport family name at birth. */
	@JsonProperty("passport_family_name_at_birth")
	private String passportFamilyNameAtBirth;
	
	/** The passport name at citizenship. */
	@JsonProperty("passport_name_at_citizenship")
	private String passportNameAtCitizenship;
	
	/** The passport place of birth. */
	@JsonProperty("passport_place_of_birth")
	private String passportPlaceOfBirth;
	
	/** The dl license number. */
	@JsonProperty("dl_license_number")
	private String dlLicenseNumber;
	
	/** The dl card number. */
	@JsonProperty("dl_card_number")
	private String dlCardNumber;
	
	/** The dl country code. */
	@JsonProperty("dl_country_code")
	private String dlCountryCode;
	
	/** The dl state code. */
	@JsonProperty("dl_state_code")
	private String dlStateCode;
	
	/** The dl expiry date. */
	@JsonProperty("dl_expiry_date")
	private String dlExpiryDate;
	
	/** The medicare card number. */
	@JsonProperty("medicare_card_number")
	private String medicareCardNumber;
	
	/** The medicare reference number. */
	@JsonProperty("medicare_reference_number")
	private String medicareReferenceNumber;
	
	/** The municipality of birth. */
	@JsonProperty("municipality_of_birth")
	private String municipalityOfBirth;
	
	/** The sub building or flat. */
	@JsonProperty("sub_building_or_flat")
	private String subBuildingOrFlat;
	
	/** The building number. */
	@JsonProperty("building_number")
	private Integer buildingNumber;
	
	/** The unit number. */
	@JsonProperty("unit_number")
	private Integer unitNumber;
	
	/** The sub city. */
	@JsonProperty("sub_city")
	private String subCity;
	
	/** The bill add ctry rgn. */
	@JsonProperty("bill_ad_ctry_rgn")
	private String billAddCtryRgn;
	
	/** The trans channel. */
	@JsonProperty("trans_channel")
	private String transChannel;
	
	/** The customer type. */
	@JsonProperty("customer_type")
	private String customerType;
	
	/** The ip. */
	@JsonProperty("ip")
	private String ip;
	
	/** The ip lat. */
	@JsonProperty("ip_lat")
	private Float ipLat;
	
	/** The ip long. */
	@JsonProperty("ip_long")
	private Float ipLong;
	
	/** The countries of operation. */
	@JsonProperty("countries_of_operation")
	private String countriesOfOperation;
	
	/** The pymt source. */
	@JsonProperty("pymt_source")
	private String pymtSource;
	
	/** The source. */
	@JsonProperty("source")
	private String source;
	
	/** The sub source. */
	@JsonProperty("sub_source")
	private String subSource;
	
	/** The referral. */
	@JsonProperty("referral")
	private String referral;
	
	/** The referral text. */
	@JsonProperty("referral_text")
	private String referralText;
	
	/** The extended referral. */
	@JsonProperty("extended_referral")
	private String extendedReferral;
	
	/** The campaign. */
	@JsonProperty("campaign")
	private String campaign;
	
	/** The keywords. */
	@JsonProperty("keywords")
	private String keywords;
	
	/** The search engine. */
	@JsonProperty("search_engine")
	private String searchEngine;
	
	/** The website. */
	@JsonProperty("website")
	private String website;
	
	/** The country of birth. */
	@JsonProperty("country_of_birth")
	private String countryOfBirth;

	/**
	 * Gets the cust title.
	 *
	 * @return the cust title
	 */
	public String getCustTitle() {
		return custTitle;
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
	 * Gets the cust first name.
	 *
	 * @return the cust first name
	 */
	public String getCustFirstName() {
		return custFirstName;
	}

	/**
	 * Gets the cust middle name.
	 *
	 * @return the cust middle name
	 */
	public String getCustMiddleName() {
		return custMiddleName;
	}

	/**
	 * Gets the cust last name.
	 *
	 * @return the cust last name
	 */
	public String getCustLastName() {
		return custLastName;
	}

	/**
	 * Gets the cust DOB.
	 *
	 * @return the cust DOB
	 */
	public String getCustDOB() {
		return custDOB;
	}

	/**
	 * Gets the bill add name.
	 *
	 * @return the bill add name
	 */
	public String getBillAddName() {
		return billAddName;
	}

	/**
	 * Gets the bill add line 1.
	 *
	 * @return the bill add line 1
	 */
	public String getBillAddLine1() {
		return billAddLine1;
	}

	/**
	 * Gets the bill add city.
	 *
	 * @return the bill add city
	 */
	public String getBillAddCity() {
		return billAddCity;
	}

	/**
	 * Gets the bill add state.
	 *
	 * @return the bill add state
	 */
	public String getBillAddState() {
		return billAddState;
	}

	/**
	 * Gets the bill add ctry.
	 *
	 * @return the bill add ctry
	 */
	public String getBillAddCtry() {
		return billAddCtry;
	}

	/**
	 * Gets the bill add zip.
	 *
	 * @return the bill add zip
	 */
	public String getBillAddZip() {
		return billAddZip;
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
	 * Gets the phone work.
	 *
	 * @return the phone work
	 */
	public String getPhoneWork() {
		return phoneWork;
	}

	/**
	 * Gets the scndry phone.
	 *
	 * @return the scndry phone
	 */
	public String getScndryPhone() {
		return scndryPhone;
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
	 * Gets the cust email.
	 *
	 * @return the cust email
	 */
	public String getCustEmail() {
		return custEmail;
	}

	/**
	 * Gets the primary contact.
	 *
	 * @return the primary contact
	 */
	public String getPrimaryContact() {
		return primaryContact;
	}

	/**
	 * Gets the cust nationality.
	 *
	 * @return the cust nationality
	 */
	public String getCustNationality() {
		return custNationality;
	}

	/**
	 * Gets the cust MF.
	 *
	 * @return the cust MF
	 */
	public String getCustMF() {
		return custMF;
	}

	/**
	 * Gets the cust occptn.
	 *
	 * @return the cust occptn
	 */
	public String getCustOccptn() {
		return custOccptn;
	}

	/**
	 * Gets the passport id num.
	 *
	 * @return the passport id num
	 */
	public String getPassportIdNum() {
		return passportIdNum;
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
	 * Gets the passport family name at birth.
	 *
	 * @return the passport family name at birth
	 */
	public String getPassportFamilyNameAtBirth() {
		return passportFamilyNameAtBirth;
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
	 * Gets the passport place of birth.
	 *
	 * @return the passport place of birth
	 */
	public String getPassportPlaceOfBirth() {
		return passportPlaceOfBirth;
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
	 * Gets the dl card number.
	 *
	 * @return the dl card number
	 */
	public String getDlCardNumber() {
		return dlCardNumber;
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
	 * Gets the dl state code.
	 *
	 * @return the dl state code
	 */
	public String getDlStateCode() {
		return dlStateCode;
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
	 * Gets the medicare card number.
	 *
	 * @return the medicare card number
	 */
	public String getMedicareCardNumber() {
		return medicareCardNumber;
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
	 * Gets the municipality of birth.
	 *
	 * @return the municipality of birth
	 */
	public String getMunicipalityOfBirth() {
		return municipalityOfBirth;
	}

	/**
	 * Gets the sub building or flat.
	 *
	 * @return the sub building or flat
	 */
	public String getSubBuildingOrFlat() {
		return subBuildingOrFlat;
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
	 * Gets the unit number.
	 *
	 * @return the unit number
	 */
	public Integer getUnitNumber() {
		return unitNumber;
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
	 * Gets the bill add ctry rgn.
	 *
	 * @return the bill add ctry rgn
	 */
	public String getBillAddCtryRgn() {
		return billAddCtryRgn;
	}

	/**
	 * Gets the trans channel.
	 *
	 * @return the trans channel
	 */
	public String getTransChannel() {
		return transChannel;
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
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Gets the ip lat.
	 *
	 * @return the ip lat
	 */
	public Float getIpLat() {
		return ipLat;
	}

	/**
	 * Gets the ip long.
	 *
	 * @return the ip long
	 */
	public Float getIpLong() {
		return ipLong;
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
	 * Gets the pymt source.
	 *
	 * @return the pymt source
	 */
	public String getPymtSource() {
		return pymtSource;
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
	 * Gets the sub source.
	 *
	 * @return the sub source
	 */
	public String getSubSource() {
		return subSource;
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
	 * Gets the referral text.
	 *
	 * @return the referral text
	 */
	public String getReferralText() {
		return referralText;
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
	 * Gets the campaign.
	 *
	 * @return the campaign
	 */
	public String getCampaign() {
		return campaign;
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
	 * Gets the search engine.
	 *
	 * @return the search engine
	 */
	public String getSearchEngine() {
		return searchEngine;
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
	 * Sets the cust title.
	 *
	 * @param custTitle the new cust title
	 */
	public void setCustTitle(String custTitle) {
		this.custTitle = custTitle;
	}

	/**
	 * Sets the preferred name.
	 *
	 * @param preferredName the new preferred name
	 */
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	/**
	 * Sets the cust first name.
	 *
	 * @param custFirstName the new cust first name
	 */
	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	/**
	 * Sets the cust middle name.
	 *
	 * @param custMiddleName the new cust middle name
	 */
	public void setCustMiddleName(String custMiddleName) {
		this.custMiddleName = custMiddleName;
	}

	/**
	 * Sets the cust last name.
	 *
	 * @param custLastName the new cust last name
	 */
	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	/**
	 * Sets the cust DOB.
	 *
	 * @param custDOB the new cust DOB
	 */
	public void setCustDOB(String custDOB) {
		this.custDOB = custDOB;
	}

	/**
	 * Sets the bill add name.
	 *
	 * @param billAddName the new bill add name
	 */
	public void setBillAddName(String billAddName) {
		this.billAddName = billAddName;
	}

	/**
	 * Sets the bill add line 1.
	 *
	 * @param billAddLine1 the new bill add line 1
	 */
	public void setBillAddLine1(String billAddLine1) {
		this.billAddLine1 = billAddLine1;
	}

	/**
	 * Sets the bill add city.
	 *
	 * @param billAddCity the new bill add city
	 */
	public void setBillAddCity(String billAddCity) {
		this.billAddCity = billAddCity;
	}

	/**
	 * Sets the bill add state.
	 *
	 * @param billAddState the new bill add state
	 */
	public void setBillAddState(String billAddState) {
		this.billAddState = billAddState;
	}

	/**
	 * Sets the bill add ctry.
	 *
	 * @param billAddCtry the new bill add ctry
	 */
	public void setBillAddCtry(String billAddCtry) {
		this.billAddCtry = billAddCtry;
	}

	/**
	 * Sets the bill add zip.
	 *
	 * @param billAddZip the new bill add zip
	 */
	public void setBillAddZip(String billAddZip) {
		this.billAddZip = billAddZip;
	}

	/**
	 * Sets the phone home.
	 *
	 * @param phoneHome the new phone home
	 */
	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	/**
	 * Sets the phone work.
	 *
	 * @param phoneWork the new phone work
	 */
	public void setPhoneWork(String phoneWork) {
		this.phoneWork = phoneWork;
	}

	/**
	 * Sets the scndry phone.
	 *
	 * @param scndryPhone the new scndry phone
	 */
	public void setScndryPhone(String scndryPhone) {
		this.scndryPhone = scndryPhone;
	}

	/**
	 * Sets the phone type.
	 *
	 * @param phoneType the new phone type
	 */
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	/**
	 * Sets the cust email.
	 *
	 * @param custEmail the new cust email
	 */
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	/**
	 * Sets the primary contact.
	 *
	 * @param primaryContact the new primary contact
	 */
	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	/**
	 * Sets the cust nationality.
	 *
	 * @param custNationality the new cust nationality
	 */
	public void setCustNationality(String custNationality) {
		this.custNationality = custNationality;
	}

	/**
	 * Sets the cust MF.
	 *
	 * @param custMF the new cust MF
	 */
	public void setCustMF(String custMF) {
		this.custMF = custMF;
	}

	/**
	 * Sets the cust occptn.
	 *
	 * @param custOccptn the new cust occptn
	 */
	public void setCustOccptn(String custOccptn) {
		this.custOccptn = custOccptn;
	}

	/**
	 * Sets the passport id num.
	 *
	 * @param passportIdNum the new passport id num
	 */
	public void setPassportIdNum(String passportIdNum) {
		this.passportIdNum = passportIdNum;
	}

	/**
	 * Sets the passport country code.
	 *
	 * @param passportCountryCode the new passport country code
	 */
	public void setPassportCountryCode(String passportCountryCode) {
		this.passportCountryCode = passportCountryCode;
	}

	/**
	 * Sets the passport family name at birth.
	 *
	 * @param passportFamilyNameAtBirth the new passport family name at birth
	 */
	public void setPassportFamilyNameAtBirth(String passportFamilyNameAtBirth) {
		this.passportFamilyNameAtBirth = passportFamilyNameAtBirth;
	}

	/**
	 * Sets the passport name at citizenship.
	 *
	 * @param passportNameAtCitizenship the new passport name at citizenship
	 */
	public void setPassportNameAtCitizenship(String passportNameAtCitizenship) {
		this.passportNameAtCitizenship = passportNameAtCitizenship;
	}

	/**
	 * Sets the passport place of birth.
	 *
	 * @param passportPlaceOfBirth the new passport place of birth
	 */
	public void setPassportPlaceOfBirth(String passportPlaceOfBirth) {
		this.passportPlaceOfBirth = passportPlaceOfBirth;
	}

	/**
	 * Sets the dl license number.
	 *
	 * @param dlLicenseNumber the new dl license number
	 */
	public void setDlLicenseNumber(String dlLicenseNumber) {
		this.dlLicenseNumber = dlLicenseNumber;
	}

	/**
	 * Sets the dl card number.
	 *
	 * @param dlCardNumber the new dl card number
	 */
	public void setDlCardNumber(String dlCardNumber) {
		this.dlCardNumber = dlCardNumber;
	}

	/**
	 * Sets the dl country code.
	 *
	 * @param dlCountryCode the new dl country code
	 */
	public void setDlCountryCode(String dlCountryCode) {
		this.dlCountryCode = dlCountryCode;
	}

	/**
	 * Sets the dl state code.
	 *
	 * @param dlStateCode the new dl state code
	 */
	public void setDlStateCode(String dlStateCode) {
		this.dlStateCode = dlStateCode;
	}

	/**
	 * Sets the dl expiry date.
	 *
	 * @param dlExpiryDate the new dl expiry date
	 */
	public void setDlExpiryDate(String dlExpiryDate) {
		this.dlExpiryDate = dlExpiryDate;
	}

	/**
	 * Sets the medicare card number.
	 *
	 * @param medicareCardNumber the new medicare card number
	 */
	public void setMedicareCardNumber(String medicareCardNumber) {
		this.medicareCardNumber = medicareCardNumber;
	}

	/**
	 * Sets the medicare reference number.
	 *
	 * @param medicareReferenceNumber the new medicare reference number
	 */
	public void setMedicareReferenceNumber(String medicareReferenceNumber) {
		this.medicareReferenceNumber = medicareReferenceNumber;
	}

	/**
	 * Sets the municipality of birth.
	 *
	 * @param municipalityOfBirth the new municipality of birth
	 */
	public void setMunicipalityOfBirth(String municipalityOfBirth) {
		this.municipalityOfBirth = municipalityOfBirth;
	}

	/**
	 * Sets the sub building or flat.
	 *
	 * @param subBuildingOrFlat the new sub building or flat
	 */
	public void setSubBuildingOrFlat(String subBuildingOrFlat) {
		this.subBuildingOrFlat = subBuildingOrFlat;
	}

	/**
	 * Sets the building number.
	 *
	 * @param buildingNumber the new building number
	 */
	public void setBuildingNumber(Integer buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	/**
	 * Sets the unit number.
	 *
	 * @param unitNumber the new unit number
	 */
	public void setUnitNumber(Integer unitNumber) {
		this.unitNumber = unitNumber;
	}

	/**
	 * Sets the sub city.
	 *
	 * @param subCity the new sub city
	 */
	public void setSubCity(String subCity) {
		this.subCity = subCity;
	}

	/**
	 * Sets the bill add ctry rgn.
	 *
	 * @param billAddCtryRgn the new bill add ctry rgn
	 */
	public void setBillAddCtryRgn(String billAddCtryRgn) {
		this.billAddCtryRgn = billAddCtryRgn;
	}

	/**
	 * Sets the trans channel.
	 *
	 * @param transChannel the new trans channel
	 */
	public void setTransChannel(String transChannel) {
		this.transChannel = transChannel;
	}

	/**
	 * Sets the customer type.
	 *
	 * @param customerType the new customer type
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * Sets the ip.
	 *
	 * @param ip the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Sets the ip lat.
	 *
	 * @param ipLat the new ip lat
	 */
	public void setIpLat(Float ipLat) {
		this.ipLat = ipLat;
	}

	/**
	 * Sets the ip long.
	 *
	 * @param ipLong the new ip long
	 */
	public void setIpLong(Float ipLong) {
		this.ipLong = ipLong;
	}

	/**
	 * Sets the pymt source.
	 *
	 * @param pymtSource the new pymt source
	 */
	public void setPymtSource(String pymtSource) {
		this.pymtSource = pymtSource;
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
	 * Sets the sub source.
	 *
	 * @param subSource the new sub source
	 */
	public void setSubSource(String subSource) {
		this.subSource = subSource;
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
	 * Sets the referral text.
	 *
	 * @param referralText the new referral text
	 */
	public void setReferralText(String referralText) {
		this.referralText = referralText;
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
	 * Sets the campaign.
	 *
	 * @param campaign the new campaign
	 */
	public void setCampaign(String campaign) {
		this.campaign = campaign;
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
	 * Sets the search engine.
	 *
	 * @param searchEngine the new search engine
	 */
	public void setSearchEngine(String searchEngine) {
		this.searchEngine = searchEngine;
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
	 * Gets the acct contact num.
	 *
	 * @return the acct contact num
	 */
	public Integer getAcctContactNum() {
		return acctContactNum;
	}

	/**
	 * Sets the acct contact num.
	 *
	 * @param acctContactNum the new acct contact num
	 */
	public void setAcctContactNum(Integer acctContactNum) {
		this.acctContactNum = acctContactNum;
	}

	/**
	 * Gets the national ID.
	 *
	 * @return the national ID
	 */
	public String getNationalID() {
		return nationalID;
	}

	/**
	 * Sets the national ID.
	 *
	 * @param nationalID the new national ID
	 */
	public void setNationalID(String nationalID) {
		this.nationalID = nationalID;
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
	 * Gets the country of birth.
	 *
	 * @return the country of birth
	 */
	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	
	/**
	 * Sets the country of birth.
	 *
	 * @param countryOfBirth the new country of birth
	 */
	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}
}
