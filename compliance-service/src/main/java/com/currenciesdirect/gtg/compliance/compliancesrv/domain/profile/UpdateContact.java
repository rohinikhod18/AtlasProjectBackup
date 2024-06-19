package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class UpdateContact implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The contact SFID. */
	@ApiModelProperty(value = "Contact Salesforce ID", example = "0030O00002jdcXIQAY", required = true)
	@JsonProperty(value="contact_sf_id")
	private String contactSFID; 
	
	/** The trade contact ID. */
	@ApiModelProperty(value = "The trade contact ID", example = "751843", required = true)
	@JsonProperty(value="trade_contact_id")
	private Integer tradeContactID;
	
	/** The title. */
	@ApiModelProperty(value = "The client's title", example = "Mr", required = true)
	@JsonProperty(value="title")
	private String title ; 
	
	/** The preferred name. */
	@ApiModelProperty(value = "The client's preferred name", example = "Bob", required = true)
	@JsonProperty(value="pref_name")
	private String preferredName;
	
	/** The first name. */
	@ApiModelProperty(value = "The client's first name", example = "Robert", required = true)
	@JsonProperty(value="first_name")
	private String firstName ; 
	
	/** The middle name. */
	@ApiModelProperty(value = "The client's middle name", example = "Louis", required = true)
	@JsonProperty(value="middle_name")
	private String middleName ; 
	
	/** The last name. */
	@ApiModelProperty(value = "The client's last name", example = "Nighy", required = true)
	@JsonProperty(value="last_name")
	private String lastName ;
	
	/** The second surname. */
	@ApiModelProperty(value = "The second surname", example = "", required = true)
	@JsonProperty(value="second_surname")
	private String secondSurname;
	
	/** The mothers surname. */
	@ApiModelProperty(value = "The mother's surname", example = "", required = true)
	@JsonProperty(value="mothers_surname")
	private String mothersSurname;
	
	/** The dob. */
	@ApiModelProperty(value = "The Date of Birth", example = "1958-03-17", required = true)
	@JsonProperty(value="dob")
	private String dob; 
	
	/** The address type. */
	@ApiModelProperty(value = "The address type of this contact", example = "Current Address", required = true)
	@JsonProperty(value="address_type")
	private String addressType ; 
	
	
	/** The city. */
	@ApiModelProperty(value = "The city", example = "London", required = true)
	@JsonProperty(value="town_city_muncipalty")
	private String city;
	
	/** The building name. */
	@ApiModelProperty(value = "The building name", required = true)
	@JsonProperty(value="building_name")
	private String buildingName;
	
	/** The street. */
	@ApiModelProperty(value = "The street", required = true)
	@JsonProperty(value="street")
	private String street;
	
	/** The street number. */
	@ApiModelProperty(value = "The street number", required = true)
	@JsonProperty(value="street_number")
	private String streetNumber;
	
	
	/** The county. */
	@ApiModelProperty(value = "The county", required = true)
	@JsonProperty(value="county")
	private String county;
	
	/** The post code. */
	@ApiModelProperty(value = "The post code", example = "WC1", required = true)
	@JsonProperty(value="post_code")
	private String postCode ;
	
	/** The country. */
	@ApiModelProperty(value = "The country of residence", example = "GBR", required = true)
	@JsonProperty(value="country_of_residence")
	private String country ; 
	
	/** The phone home. */
	@ApiModelProperty(value = "The home phone number of the contact", example = "+44-987654321", required = true)
	@JsonProperty(value="home_phone")
	private String phoneHome; 
	
	/** The work phone. */
	@ApiModelProperty(value = "The work phone number of the contact", example = "+44-987654321", required = true)
	@JsonProperty(value="work_phone")
	private String phoneWork; 
	
	/** The work phone extn. */
	@ApiModelProperty(value = "The work phone number extension", example = "", required = true)
	@JsonProperty(value="work_phone_ext")
	private String phoneWorkExtn ; 
	
	/** The mobile phone. */
	@ApiModelProperty(value = "The mobile phone number", example = "+44-987654321", required = true)
	@JsonProperty(value="mobile_phone")
	private String phoneMobile ;
	
	/** The primary phone. */
	@ApiModelProperty(value = "The contact's primary phone number. I.e. Home, Mobile or Work", example = "Mobile", required = true)
	@JsonProperty(value="primary_phone")
	private String primaryPhone; 
	
	/** The email. */
	@ApiModelProperty(value = "The email", required = true)
	@JsonProperty(value="email")
	private String email; 
	
	/** The second email. */
	@ApiModelProperty(value = "The second email of the contact", example = "", required = true)
	@JsonProperty(value="second_email")
	private String secondEmail;
	
	/** The designation. */
	@ApiModelProperty(value = "The job description of the contact", example = "Director", required = true)
	@JsonProperty(value="designation")
	private String designation;
	
	/** The ip address. */
	@ApiModelProperty(value = "The IP address of the client", example = "127.0.0.1", required = true)
	@JsonProperty(value="ip_address")
	private String ipAddress;
	
	/** The primary contact. */
	@ApiModelProperty(value = "Whether this is the primary contact for the client. This can be false for corporate clients or private joint accounts", example = "true", required = true)
	@JsonProperty(value="is_primary_contact")
	private Boolean primaryContact; 
	
	/** The nationality. */
	@ApiModelProperty(value = "The nationality of the contact", example = "GBR", required = true)
	@JsonProperty(value="country_of_nationality")
	private String nationality; 
	
	/** The gender. */
	@ApiModelProperty(value = "The gender of the contact", example = "Male", required = true)
	@JsonProperty(value="gender")
	private String gender; 
	
	/** The occupation. */
	@ApiModelProperty(value = "The occupation of the contact", example = "Director", required = true)
	@JsonProperty(value="occupation")
	private String occupation; 
	
	/** The passport number. */
	@ApiModelProperty(value = "The passport number of the contact", example = "PA9876543", required = true)
	@JsonProperty(value="passport_number")
	private String passportNumber; 
	
	/** The passport country code. */
	@ApiModelProperty(value = "The passport country code", example = "GBR", required = true)
	@JsonProperty(value="passport_country_code")
	private String passportCountryCode; 
	
	
	/** The passport expiry date. */
	@ApiModelProperty(value = "The passport expiry date", example = "2027-10-28", required = true)
	@JsonProperty(value="passport_exipry_date")
	private String passportExiprydate;
	
	/** The passport full name. */
	@ApiModelProperty(value = "The passport full name", required = true)
	@JsonProperty(value="passport_full_name")
	private String passportFullName;
	
	/** The passport MRZ line 1. */
	@ApiModelProperty(value = "The Passport Machine Readable Zone Line 1", required = true)
	@JsonProperty(value="passport_mrz_line_1")
	private String passportMRZLine1;
	
	/** The passport MRZ line 2. */
	@ApiModelProperty(value = "The Passport Machine Readable Zone Line 2", required = true)
	@JsonProperty(value="passport_mrz_line_2")
	private String passportMRZLine2;
	
	/** The passport family name at birth. */
	@ApiModelProperty(value = "The passport family name at birth", required = true)
	@JsonProperty(value="passport_birth_family_name")
	private String passportFamilyNameAtBirth; 
	
	/** The passport name at citizenship. */
	@ApiModelProperty(value = "The passport name at citizenship", required = true)
	@JsonProperty(value="passport_name_at_citizen")
	private String passportNameAtCitizenship; 
	
	/** The passport place of birth. */
	@ApiModelProperty(value = "The passport place of birth", required = true)
	@JsonProperty(value="passport_birth_place")
	private String passportPlaceOfBirth; 
	
	/** The dl license number. */
	@ApiModelProperty(value = "The driving license number", example = "21731596", required = true)
	@JsonProperty(value="driving_license")
	private String dlLicenseNumber; 
	
	/** The dl version number. */
	@ApiModelProperty(value = "The driving license version number", example = "", required = true)
	@JsonProperty(value="driving_version_number")
	private String dlVersionNumber;
	
	/** The dl card number. */
	@ApiModelProperty(value = "The driving license card number", example = "2043257942", required = true)
	@JsonProperty(value="driving_license_card_number")
	private String dlCardNumber; 
	
	/** The dl country code. */
	@ApiModelProperty(value = "The driving license country code", required = true)
	@JsonProperty(value="driving_license_country")
	private String dlCountryCode; 
	
	/** The dl state code. */
	@ApiModelProperty(value = "The driving license state code", required = true)
	@JsonProperty(value="driving_state_code")
	private String dlStateCode; 
	
	/** The dl expiry date. */
	@ApiModelProperty(value = "The driving license expiry date", required = true)
	@JsonProperty(value="driving_expiry")
	private String dlExpiryDate; 
	
	/** The medicare card number. */
	@ApiModelProperty(value = "The medicare card number", required = true)
	@JsonProperty(value="medicare_card_number")
	private String medicareCardNumber; 
	
	/** The medicare reference number. */
	@ApiModelProperty(value = "The medicare reference number", required = true)
	@JsonProperty(value="medicare_ref_number")
	private String medicareReferenceNumber; 
	
	/** The australia RTA card number. */
	@ApiModelProperty(value = "The australia RTA card number", required = true)
	@JsonProperty(value="australia_rta_card_number")
	private String australiaRTACardNumber;
	
	/** The state. */
	@ApiModelProperty(value = "The state", required = true)
	@JsonProperty(value="state_province_county")
	private String state; 
	
	/** The municipality of birth. */
	@ApiModelProperty(value = "The municipality of birth", required = true)
	@JsonProperty(value="municipality_of_birth")
	private String municipalityOfBirth; 
	
	/** The country of birth. */
	@ApiModelProperty(value = "The country of birth", required = true)
	@JsonProperty(value="country_of_birth")
	private String countryOfBirth; 
	
	/** The state of birth. */
	@ApiModelProperty(value = "The state of birth", required = true)
	@JsonProperty(value="state_of_birth")
	private String stateOfBirth; 
	
	/** The civic number. */
	@ApiModelProperty(value = "The civic number", required = true)
	@JsonProperty(value="civic_number")
	private String civicNumber; 
	
	/** The sub buildingor flat. */
	@ApiModelProperty(value = "The sub buildingor flat", required = true)
	@JsonProperty(value="sub_building")
	private String subBuildingorFlat; 
	
	/** The unit number. */
	@ApiModelProperty(value = "The unit number", required = true)
	@JsonProperty(value="unit_number")
	private String unitNumber; 
	
	/** The sub city. */
	@ApiModelProperty(value = "The sub city", required = true)
	@JsonProperty(value="sub_city")
	private String subCity; 
	
	/** The region. */
	@ApiModelProperty(value = "The region", required = true)
	@JsonProperty(value="region_suburb")
	private String region;
	
	/** The national id number. */
	@ApiModelProperty(value = "The national id number", required = true)
	@JsonProperty(value="national_id_number")
	private String nationalIdNumber;
	
	/** The years in address. */
	@ApiModelProperty(value = "The years in address", required = true)
	@JsonProperty(value="years_in_address")
	private String yearsInAddress;
	
	/** The residential status. */
	@ApiModelProperty(value = "The residential status", required = true)
	@JsonProperty(value="residential_status")
	private String residentialStatus;
	
	/** The previous city. */
	@ApiModelProperty(value = "The previous city", required = true)
	@JsonProperty(value="previous_city")
	private String previousCity;
	
	/** The previous country. */
	@ApiModelProperty(value = "The previous country", required = true)
	@JsonProperty(value="previous_country")
	private String previousCountry;
	
	/** The previous state. */
	@ApiModelProperty(value = "The previous state", required = true)
	@JsonProperty(value="previous_state")
	private String previousState;
	
	/** The previous street. */
	@ApiModelProperty(value = "The previous street", required = true)
	@JsonProperty(value="previous_street")
	private String previousStreet;
	
	/** The previous post code. */
	@ApiModelProperty(value = "The previous post code", required = true)
	@JsonProperty(value="previous_post_code")
	private String previousPostCode;
	
	/** The update method. */
	@ApiModelProperty(value = "The update method", required = true)
	@JsonProperty(value="update_method")
	private String updateMethod;
	
	/** The record updated on. */
	@ApiModelProperty(value = "The record updated on", required = true)
	@JsonProperty(value="record_updated_on")
	private String recordUpdatedOn;
	
	/** The contact status. */
	@ApiModelProperty(value = "The contact status", required = true)
	@JsonProperty(value="contact_status")
	private String contactStatus;
	
	/** The online login status. */
	@ApiModelProperty(value = "The online login status", required = true)
	@JsonProperty(value="online_login_status")
	private String onlineLoginStatus;
	
	/** The session ID. */
	@ApiModelProperty(value = "The session ID", required = true)
	@JsonProperty(value="session_id")
	private String sessionID;

	/** The last PIN issued date time. */
	@ApiModelProperty(value = "The last PIN issued date time", required = true)
	@JsonProperty(value="last_pin_issued_date_time")
	private String lastPINIssuedDateTime;
	
	/** The last login fail date time. */
	@ApiModelProperty(value = "The last login fail date time", required = true)
	@JsonProperty(value="last_login_fail_date_time")
	private String lastLoginFailDateTime;
	
	/** The last password reset on. */
	@ApiModelProperty(value = "The last password reset on", required = true)
	@JsonProperty(value="last_password_reset_on")
	private String lastPasswordResetOn;
	
	/** The list of devices used by customer. */
	@ApiModelProperty(value = "The list of devices used by customer", required = true)
	@JsonProperty(value="list_of_devices_used_by_customer")
	private String listofDevicesUsedByCustomer;
	
	/** The second contact added later online. */
	@ApiModelProperty(value = "The second contact added later online", required = true)
	@JsonProperty(value="second_contact_added_later_Online")
	private Boolean secondContactAddedLaterOnline;
	
	/** The last IP addresses. */
	@ApiModelProperty(value = "The last IP addresses", required = true)
	@JsonProperty(value="last_ip_addresses")
	private String lastIPAddresses;
	
	/** The last 5 login date time. */
	@ApiModelProperty(value = "The last 5 login datetimestamps", required = true)
	@JsonProperty(value="last_5_login_date_time")
	private String last5LoginDateTime;
	
	/** The national id type. */
	@ApiModelProperty(value = "The national id type", required = true)
	@JsonProperty(value="national_id_type")
	private String nationalIdType;
	
	
	// default value is set to true
	/** The is KYC eligible. */
	// update false as in when required
	@ApiModelProperty(value = "Whether this is a KYC eligible account", required = true)
	@JsonIgnore
	private boolean isKYCEligible = true;
	
	/** The is fraugster eligible. */
	@ApiModelProperty(value = "Whether this account is eligible for Fraudster", required = true)
	@JsonIgnore
	private boolean isFraugsterEligible = true;
	
	/** The is sanction eligible. */
	@ApiModelProperty(value = "Whether this account is eligible for Sanction", required = true)
	@JsonIgnore
	private boolean isSanctionEligible = true;
	
	/** The district. */
	@ApiModelProperty(value = "The district", required = true)
	@JsonProperty(value="district")
	private String district;
	
	/** The area number. */
	@ApiModelProperty(value = "The area number", required = true)
	@JsonProperty(value="area_number")
	private String areaNumber;
	
	/** The aza. */
	@ApiModelProperty(value = "The aza [japanese address system]", required = true)
	@JsonProperty(value="aza")
	private String aza;
	
	/** The prefecture. */
	@ApiModelProperty(value = "The prefecture", required = true)
	@JsonProperty(value="prefecture")
	private String prefecture;
	
	/** The floor number. */
	@ApiModelProperty(value = "The floor number", required = true)
	@JsonProperty(value="floor_number")
	private String floorNumber;
	
	/** The authorised signatory. */
	@ApiModelProperty(value = "Whether the contact is authorised sign official agreements. This is alway true for private individual clients, but it could be false for some corporate contacts", example = "true", required = true)
	@JsonProperty(value = "authorised_signatory")
	private String authorisedSignatory;
	
	/** The job title. */
	@ApiModelProperty(value = "The job title of the contact", example = "Director", required = true)
	@JsonProperty(value="job_title")
	private String  jobTitle;
	
	@ApiModelProperty(value = "For corporate clients, the position of the contact (e.g. Director, Company Secretary, Shareholder)", example = "Director and Shareholder 25% and >", required = true)
	@JsonProperty(value = "position_of_significance")
	private String positionOfSignificance;
	
	@JsonProperty(value = "ip_domain")
	private String ipDomain;
	
	@JsonProperty(value = "ip_isp")
	private String ipIsp;
	
	@JsonProperty(value = "ip_city")
	private String ipCity;
	
	@JsonProperty(value = "ip_country")
	private String ipCountry;
	
	/** The ssn number. */
	@ApiModelProperty(value = "The ssn number", required = true)
	@JsonProperty(value = "ssn")
	private String ssn; //Add in AT-3661
	
	/** The legacy trade contact ID. */
	@ApiModelProperty(value = "The legacy trade contact ID")
	@JsonProperty(value = "legacy_trade_contact_id")
	private Integer legacyTradeContactID;
	
	// Added for AT-4870
	/** The maddress 2 city. */
	@ApiModelProperty(value = "Mailing address 2 city", required = true)
	@JsonProperty(value = "maddress2_city")
	private String maddress2City;

	/** The maddress 2 street. */
	@ApiModelProperty(value = "Mailing address 2 street", required = true)
	@JsonProperty(value = "maddress2_street")
	private String maddress2Street;

	/** The maddress 2 postalcode. */
	@ApiModelProperty(value = "Mailing address 2 postal code", required = true)
	@JsonProperty(value = "maddress2_postalcode")
	private String maddress2Postalcode;

	/** The maddress 2 country. */
	@ApiModelProperty(value = "Mailing address 2 country", required = true)
	@JsonProperty(value = "maddress2_country")
	private String maddress2Country;

	/** The maddress 2 state. */
	@ApiModelProperty(value = "Mailing address 2 state", required = true)
	@JsonProperty(value = "maddress2_state")
	private String maddress2State;
	
	public String getMothersSurname() {
		return mothersSurname;
	}

	public void setMothersSurname(String mothersSurname) {
		this.mothersSurname = mothersSurname;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAreaNumber() {
		return areaNumber;
	}

	public void setAreaNumber(String areaNumber) {
		this.areaNumber = areaNumber;
	}

	public String getAza() {
		return aza;
	}

	public void setAza(String aza) {
		this.aza = aza;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	public String getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}
	
	public String getUpdateMethod() {
		return updateMethod;
	}

	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}

	public String getRecordUpdatedOn() {
		return recordUpdatedOn;
	}

	public void setRecordUpdatedOn(String recordUpdatedOn) {
		this.recordUpdatedOn = recordUpdatedOn;
	}

	public String getContactStatus() {
		return contactStatus;
	}

	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

	public String getOnlineLoginStatus() {
		return onlineLoginStatus;
	}

	public void setOnlineLoginStatus(String onlineLoginStatus) {
		this.onlineLoginStatus = onlineLoginStatus;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getLastPINIssuedDateTime() {
		return lastPINIssuedDateTime;
	}

	public void setLastPINIssuedDateTime(String lastPINIssuedDateTime) {
		this.lastPINIssuedDateTime = lastPINIssuedDateTime;
	}

	public String getLastLoginFailDateTime() {
		return lastLoginFailDateTime;
	}

	public void setLastLoginFailDateTime(String lastLoginFailDateTime) {
		this.lastLoginFailDateTime = lastLoginFailDateTime;
	}

	public String getLastPasswordResetOn() {
		return lastPasswordResetOn;
	}

	public void setLastPasswordResetOn(String lastPasswordResetOn) {
		this.lastPasswordResetOn = lastPasswordResetOn;
	}

	public String getListofDevicesUsedByCustomer() {
		return listofDevicesUsedByCustomer;
	}

	public void setListofDevicesUsedByCustomer(String listofDevicesUsedByCustomer) {
		this.listofDevicesUsedByCustomer = listofDevicesUsedByCustomer;
	}

	public Boolean getSecondContactAddedLaterOnline() {
		return secondContactAddedLaterOnline;
	}

	public void setSecondContactAddedLaterOnline(Boolean secondContactAddedLaterOnline) {
		this.secondContactAddedLaterOnline = secondContactAddedLaterOnline;
	}

	public String getLastIPAddresses() {
		return lastIPAddresses;
	}

	public void setLastIPAddresses(String lastIPAddresses) {
		this.lastIPAddresses = lastIPAddresses;
	}

	public String getLast5LoginDateTime() {
		return last5LoginDateTime;
	}

	public void setLast5LoginDateTime(String last5LoginDateTime) {
		this.last5LoginDateTime = last5LoginDateTime;
	}

	
	public String getContactSFID() {
		return contactSFID;
	}

	public void setContactSFID(String contactSFID) {
		this.contactSFID = contactSFID;
	}

	public Integer getTradeContactID() {
		return tradeContactID;
	}

	public void setTradeContactID(Integer tradeContactID) {
		this.tradeContactID = tradeContactID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneHome() {
		return phoneHome;
	}

	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	public String getPhoneWork() {
		return phoneWork;
	}

	public void setPhoneWork(String phoneWork) {
		this.phoneWork = phoneWork;
	}

	public String getPhoneWorkExtn() {
		return phoneWorkExtn;
	}

	public void setPhoneWorkExtn(String phoneWorkExtn) {
		this.phoneWorkExtn = phoneWorkExtn;
	}

	public String getPhoneMobile() {
		return phoneMobile;
	}

	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecondEmail() {
		return secondEmail;
	}

	public void setSecondEmail(String secondEmail) {
		this.secondEmail = secondEmail;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getPassportCountryCode() {
		return passportCountryCode;
	}

	public void setPassportCountryCode(String passportCountryCode) {
		this.passportCountryCode = passportCountryCode;
	}

	public String getPassportFamilyNameAtBirth() {
		return passportFamilyNameAtBirth;
	}

	public void setPassportFamilyNameAtBirth(String passportFamilyNameAtBirth) {
		this.passportFamilyNameAtBirth = passportFamilyNameAtBirth;
	}

	public String getPassportNameAtCitizenship() {
		return passportNameAtCitizenship;
	}

	public void setPassportNameAtCitizenship(String passportNameAtCitizenship) {
		this.passportNameAtCitizenship = passportNameAtCitizenship;
	}

	public String getPassportPlaceOfBirth() {
		return passportPlaceOfBirth;
	}

	public void setPassportPlaceOfBirth(String passportPlaceOfBirth) {
		this.passportPlaceOfBirth = passportPlaceOfBirth;
	}

	public String getDlLicenseNumber() {
		return dlLicenseNumber;
	}

	public void setDlLicenseNumber(String dlLicenseNumber) {
		this.dlLicenseNumber = dlLicenseNumber;
	}

	public String getDlCardNumber() {
		return dlCardNumber;
	}

	public void setDlCardNumber(String dlCardNumber) {
		this.dlCardNumber = dlCardNumber;
	}

	public String getDlCountryCode() {
		return dlCountryCode;
	}

	public void setDlCountryCode(String dlCountryCode) {
		this.dlCountryCode = dlCountryCode;
	}

	public String getDlStateCode() {
		return dlStateCode;
	}

	public void setDlStateCode(String dlStateCode) {
		this.dlStateCode = dlStateCode;
	}

	public String getDlExpiryDate() {
		return dlExpiryDate;
	}

	public void setDlExpiryDate(String dlExpiryDate) {
		this.dlExpiryDate = dlExpiryDate;
	}

	public String getMedicareCardNumber() {
		return medicareCardNumber;
	}

	public void setMedicareCardNumber(String medicareCardNumber) {
		this.medicareCardNumber = medicareCardNumber;
	}

	public String getMedicareReferenceNumber() {
		return medicareReferenceNumber;
	}

	public void setMedicareReferenceNumber(String medicareReferenceNumber) {
		this.medicareReferenceNumber = medicareReferenceNumber;
	}

	public String getAustraliaRTACardNumber() {
		return australiaRTACardNumber;
	}

	public void setAustraliaRTACardNumber(String australiaRTACardNumber) {
		this.australiaRTACardNumber = australiaRTACardNumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMunicipalityOfBirth() {
		return municipalityOfBirth;
	}

	public void setMunicipalityOfBirth(String municipalityOfBirth) {
		this.municipalityOfBirth = municipalityOfBirth;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getStateOfBirth() {
		return stateOfBirth;
	}

	public void setStateOfBirth(String stateOfBirth) {
		this.stateOfBirth = stateOfBirth;
	}

	public String getCivicNumber() {
		return civicNumber;
	}

	public void setCivicNumber(String civicNumber) {
		this.civicNumber = civicNumber;
	}

	public String getSubBuildingorFlat() {
		return subBuildingorFlat;
	}

	public void setSubBuildingorFlat(String subBuildingorFlat) {
		this.subBuildingorFlat = subBuildingorFlat;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getSubCity() {
		return subCity;
	}

	public void setSubCity(String subCity) {
		this.subCity = subCity;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}


	public String getPassportExiprydate() {
		return passportExiprydate;
	}

	public void setPassportExiprydate(String passportExiprydate) {
		this.passportExiprydate = passportExiprydate;
	}

	public String getDlVersionNumber() {
		return dlVersionNumber;
	}

	public void setDlVersionNumber(String dlVersionNumber) {
		this.dlVersionNumber = dlVersionNumber;
	}

	public String getNationalIdNumber() {
		return nationalIdNumber;
	}

	public void setNationalIdNumber(String nationalIdNumber) {
		this.nationalIdNumber = nationalIdNumber;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}


	public String getPassportFullName() {
		return passportFullName;
	}

	public void setPassportFullName(String passportFullName) {
		this.passportFullName = passportFullName;
	}

	public String getPassportMRZLine1() {
		return passportMRZLine1;
	}

	public void setPassportMRZLine1(String passportMRZLine1) {
		this.passportMRZLine1 = passportMRZLine1;
	}

	public String getPassportMRZLine2() {
		return passportMRZLine2;
	}

	public void setPassportMRZLine2(String passportMRZLine2) {
		this.passportMRZLine2 = passportMRZLine2;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getYearsInAddress() {
		return yearsInAddress;
	}

	public void setYearsInAddress(String yearsInAddress) {
		this.yearsInAddress = yearsInAddress;
	}

	public String getResidentialStatus() {
		return residentialStatus;
	}

	public void setResidentialStatus(String residentialStatus) {
		this.residentialStatus = residentialStatus;
	}

	public String getPreviousCity() {
		return previousCity;
	}

	public void setPreviousCity(String previousCity) {
		this.previousCity = previousCity;
	}

	public String getPreviousCountry() {
		return previousCountry;
	}

	public void setPreviousCountry(String previousCountry) {
		this.previousCountry = previousCountry;
	}

	public String getPreviousState() {
		return previousState;
	}

	public void setPreviousState(String previousState) {
		this.previousState = previousState;
	}

	public String getPreviousStreet() {
		return previousStreet;
	}

	public void setPreviousStreet(String previousStreet) {
		this.previousStreet = previousStreet;
	}

	public String getPreviousPostCode() {
		return previousPostCode;
	}

	public void setPreviousPostCode(String previousPostCode) {
		this.previousPostCode = previousPostCode;
	}

	public Boolean getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(Boolean primaryContact) {
		this.primaryContact = primaryContact;
	}

	public boolean isKYCEligible() {
		return isKYCEligible;
	}

	public void setKYCEligible(boolean isKYCEligible) {
		this.isKYCEligible = isKYCEligible;
	}

	@ApiModelProperty(value = "Whether this account is eligible for Fraudster", required = true)
	public boolean isFraugsterEligible() {
		return isFraugsterEligible;
	}

	public void setFraugsterEligible(boolean isFraugsterEligible) {
		this.isFraugsterEligible = isFraugsterEligible;
	}

	public boolean isSanctionEligible() {
		return isSanctionEligible;
	}

	public void setSanctionEligible(boolean isSanctionEligible) {
		this.isSanctionEligible = isSanctionEligible;
	}

	public String getNationalIdType() {
		return nationalIdType;
	}

	public void setNationalIdType(String nationalIdType) {
		this.nationalIdType = nationalIdType;
	}

	public String getAuthorisedSignatory() {
		return authorisedSignatory;
	}

	public void setAuthorisedSignatory(String authorisedSignatory) {
		this.authorisedSignatory = authorisedSignatory;
	}
	
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPositionOfSignificance() {
		return positionOfSignificance;
	}

	public void setPositionOfSignificance(String positionOfSignificance) {
		this.positionOfSignificance = positionOfSignificance;
	}

	public String getIpDomain() {
		return ipDomain;
	}

	public void setIpDomain(String ipDomain) {
		this.ipDomain = ipDomain;
	}

	public String getIpIsp() {
		return ipIsp;
	}

	public void setIpIsp(String ipIsp) {
		this.ipIsp = ipIsp;
	}

	public String getIpCity() {
		return ipCity;
	}

	public void setIpCity(String ipCity) {
		this.ipCity = ipCity;
	}

	public String getIpCountry() {
		return ipCountry;
	}

	public void setIpCountry(String ipCountry) {
		this.ipCountry = ipCountry;
	}
	
	/**
	 * @return the ssnNumber
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssnNumber the ssnNumber to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Integer getLegacyTradeContactID() {
		return legacyTradeContactID;
	}

	public void setLegacyTradeContactID(Integer legacyTradeContactID) {
		this.legacyTradeContactID = legacyTradeContactID;
	}

	/**
	 * @return the maddress2City
	 */
	public String getMaddress2City() {
		return maddress2City;
	}

	/**
	 * @param maddress2City the maddress2City to set
	 */
	public void setMaddress2City(String maddress2City) {
		this.maddress2City = maddress2City;
	}

	/**
	 * @return the maddress2Street
	 */
	public String getMaddress2Street() {
		return maddress2Street;
	}

	/**
	 * @param maddress2Street the maddress2Street to set
	 */
	public void setMaddress2Street(String maddress2Street) {
		this.maddress2Street = maddress2Street;
	}

	/**
	 * @return the maddress2Postalcode
	 */
	public String getMaddress2Postalcode() {
		return maddress2Postalcode;
	}

	/**
	 * @param maddress2Postalcode the maddress2Postalcode to set
	 */
	public void setMaddress2Postalcode(String maddress2Postalcode) {
		this.maddress2Postalcode = maddress2Postalcode;
	}

	/**
	 * @return the maddress2Country
	 */
	public String getMaddress2Country() {
		return maddress2Country;
	}

	/**
	 * @param maddress2Country the maddress2Country to set
	 */
	public void setMaddress2Country(String maddress2Country) {
		this.maddress2Country = maddress2Country;
	}

	/**
	 * @return the maddress2State
	 */
	public String getMaddress2State() {
		return maddress2State;
	}

	/**
	 * @param maddress2State the maddress2State to set
	 */
	public void setMaddress2State(String maddress2State) {
		this.maddress2State = maddress2State;
	}

	@Override
	public String toString() {
		return "UpdateContact [contactSFID=" + contactSFID + ", tradeContactID=" + tradeContactID + ", title=" + title
				+ ", preferredName=" + preferredName + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", secondSurname=" + secondSurname + ", mothersSurname=" + mothersSurname
				+ ", dob=" + dob + ", addressType=" + addressType + ", city=" + city + ", buildingName=" + buildingName
				+ ", street=" + street + ", streetNumber=" + streetNumber + ", county=" + county + ", postCode="
				+ postCode + ", country=" + country + ", phoneHome=" + phoneHome + ", phoneWork=" + phoneWork
				+ ", phoneWorkExtn=" + phoneWorkExtn + ", phoneMobile=" + phoneMobile + ", primaryPhone=" + primaryPhone
				+ ", email=" + email + ", secondEmail=" + secondEmail + ", designation=" + designation + ", ipAddress="
				+ ipAddress + ", primaryContact=" + primaryContact + ", nationality=" + nationality + ", gender="
				+ gender + ", occupation=" + occupation + ", passportNumber=" + passportNumber
				+ ", passportCountryCode=" + passportCountryCode + ", passportExiprydate=" + passportExiprydate
				+ ", passportFullName=" + passportFullName + ", passportMRZLine1=" + passportMRZLine1
				+ ", passportMRZLine2=" + passportMRZLine2 + ", passportFamilyNameAtBirth=" + passportFamilyNameAtBirth
				+ ", passportNameAtCitizenship=" + passportNameAtCitizenship + ", passportPlaceOfBirth="
				+ passportPlaceOfBirth + ", dlLicenseNumber=" + dlLicenseNumber + ", dlVersionNumber=" + dlVersionNumber
				+ ", dlCardNumber=" + dlCardNumber + ", dlCountryCode=" + dlCountryCode + ", dlStateCode=" + dlStateCode
				+ ", dlExpiryDate=" + dlExpiryDate + ", medicareCardNumber=" + medicareCardNumber
				+ ", medicareReferenceNumber=" + medicareReferenceNumber + ", australiaRTACardNumber="
				+ australiaRTACardNumber + ", state=" + state + ", municipalityOfBirth=" + municipalityOfBirth
				+ ", countryOfBirth=" + countryOfBirth + ", stateOfBirth=" + stateOfBirth + ", civicNumber="
				+ civicNumber + ", subBuildingorFlat=" + subBuildingorFlat + ", unitNumber=" + unitNumber + ", subCity="
				+ subCity + ", region=" + region + ", nationalIdNumber=" + nationalIdNumber + ", yearsInAddress="
				+ yearsInAddress + ", residentialStatus=" + residentialStatus + ", previousCity=" + previousCity
				+ ", previousCountry=" + previousCountry + ", previousState=" + previousState + ", previousStreet="
				+ previousStreet + ", previousPostCode=" + previousPostCode + ", updateMethod=" + updateMethod
				+ ", recordUpdatedOn=" + recordUpdatedOn + ", contactStatus=" + contactStatus + ", onlineLoginStatus="
				+ onlineLoginStatus + ", sessionID=" + sessionID + ", lastPINIssuedDateTime=" + lastPINIssuedDateTime
				+ ", lastLoginFailDateTime=" + lastLoginFailDateTime + ", lastPasswordResetOn=" + lastPasswordResetOn
				+ ", listofDevicesUsedByCustomer=" + listofDevicesUsedByCustomer + ", secondContactAddedLaterOnline="
				+ secondContactAddedLaterOnline + ", lastIPAddresses=" + lastIPAddresses + ", last5LoginDateTime="
				+ last5LoginDateTime + ", nationalIdType=" + nationalIdType + ", isKYCEligible=" + isKYCEligible
				+ ", isFraugsterEligible=" + isFraugsterEligible + ", isSanctionEligible=" + isSanctionEligible
				+ ", district=" + district + ", areaNumber=" + areaNumber + ", aza=" + aza + ", prefecture="
				+ prefecture + ", floorNumber=" + floorNumber + ", authorisedSignatory=" + authorisedSignatory
				+ ", jobTitle=" + jobTitle + "]";
	}

	@SuppressWarnings("squid:S3776")
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressType == null) ? 0 : addressType.hashCode());
		result = prime * result + ((areaNumber == null) ? 0 : areaNumber.hashCode());
		result = prime * result + ((australiaRTACardNumber == null) ? 0 : australiaRTACardNumber.hashCode());
		result = prime * result + ((aza == null) ? 0 : aza.hashCode());
		result = prime * result + ((buildingName == null) ? 0 : buildingName.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((civicNumber == null) ? 0 : civicNumber.hashCode());
		result = prime * result + ((contactSFID == null) ? 0 : contactSFID.hashCode());
		result = prime * result + ((contactStatus == null) ? 0 : contactStatus.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((countryOfBirth == null) ? 0 : countryOfBirth.hashCode());
		result = prime * result + ((county == null) ? 0 : county.hashCode());
		result = prime * result + ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((dlCardNumber == null) ? 0 : dlCardNumber.hashCode());
		result = prime * result + ((dlCountryCode == null) ? 0 : dlCountryCode.hashCode());
		result = prime * result + ((dlExpiryDate == null) ? 0 : dlExpiryDate.hashCode());
		result = prime * result + ((dlLicenseNumber == null) ? 0 : dlLicenseNumber.hashCode());
		result = prime * result + ((dlStateCode == null) ? 0 : dlStateCode.hashCode());
		result = prime * result + ((dlVersionNumber == null) ? 0 : dlVersionNumber.hashCode());
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((floorNumber == null) ? 0 : floorNumber.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + (isFraugsterEligible ? 1231 : 1237);
		result = prime * result + (isKYCEligible ? 1231 : 1237);
		result = prime * result + (isSanctionEligible ? 1231 : 1237);
		result = prime * result + ((last5LoginDateTime == null) ? 0 : last5LoginDateTime.hashCode());
		result = prime * result + ((lastIPAddresses == null) ? 0 : lastIPAddresses.hashCode());
		result = prime * result + ((lastLoginFailDateTime == null) ? 0 : lastLoginFailDateTime.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((lastPINIssuedDateTime == null) ? 0 : lastPINIssuedDateTime.hashCode());
		result = prime * result + ((lastPasswordResetOn == null) ? 0 : lastPasswordResetOn.hashCode());
		result = prime * result + ((listofDevicesUsedByCustomer == null) ? 0 : listofDevicesUsedByCustomer.hashCode());
		result = prime * result + ((medicareCardNumber == null) ? 0 : medicareCardNumber.hashCode());
		result = prime * result + ((medicareReferenceNumber == null) ? 0 : medicareReferenceNumber.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
		result = prime * result + ((mothersSurname == null) ? 0 : mothersSurname.hashCode());
		result = prime * result + ((municipalityOfBirth == null) ? 0 : municipalityOfBirth.hashCode());
		result = prime * result + ((nationalIdNumber == null) ? 0 : nationalIdNumber.hashCode());
		result = prime * result + ((nationalIdType == null) ? 0 : nationalIdType.hashCode());
		result = prime * result + ((nationality == null) ? 0 : nationality.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((onlineLoginStatus == null) ? 0 : onlineLoginStatus.hashCode());
		result = prime * result + ((passportCountryCode == null) ? 0 : passportCountryCode.hashCode());
		result = prime * result + ((passportExiprydate == null) ? 0 : passportExiprydate.hashCode());
		result = prime * result + ((passportFamilyNameAtBirth == null) ? 0 : passportFamilyNameAtBirth.hashCode());
		result = prime * result + ((passportFullName == null) ? 0 : passportFullName.hashCode());
		result = prime * result + ((passportMRZLine1 == null) ? 0 : passportMRZLine1.hashCode());
		result = prime * result + ((passportMRZLine2 == null) ? 0 : passportMRZLine2.hashCode());
		result = prime * result + ((passportNameAtCitizenship == null) ? 0 : passportNameAtCitizenship.hashCode());
		result = prime * result + ((passportNumber == null) ? 0 : passportNumber.hashCode());
		result = prime * result + ((passportPlaceOfBirth == null) ? 0 : passportPlaceOfBirth.hashCode());
		result = prime * result + ((phoneHome == null) ? 0 : phoneHome.hashCode());
		result = prime * result + ((phoneMobile == null) ? 0 : phoneMobile.hashCode());
		result = prime * result + ((phoneWork == null) ? 0 : phoneWork.hashCode());
		result = prime * result + ((phoneWorkExtn == null) ? 0 : phoneWorkExtn.hashCode());
		result = prime * result + ((postCode == null) ? 0 : postCode.hashCode());
		result = prime * result + ((prefecture == null) ? 0 : prefecture.hashCode());
		result = prime * result + ((preferredName == null) ? 0 : preferredName.hashCode());
		result = prime * result + ((previousCity == null) ? 0 : previousCity.hashCode());
		result = prime * result + ((previousCountry == null) ? 0 : previousCountry.hashCode());
		result = prime * result + ((previousPostCode == null) ? 0 : previousPostCode.hashCode());
		result = prime * result + ((previousState == null) ? 0 : previousState.hashCode());
		result = prime * result + ((previousStreet == null) ? 0 : previousStreet.hashCode());
		result = prime * result + ((primaryContact == null) ? 0 : primaryContact.hashCode());
		result = prime * result + ((primaryPhone == null) ? 0 : primaryPhone.hashCode());
		result = prime * result + ((recordUpdatedOn == null) ? 0 : recordUpdatedOn.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((residentialStatus == null) ? 0 : residentialStatus.hashCode());
		result = prime * result
				+ ((secondContactAddedLaterOnline == null) ? 0 : secondContactAddedLaterOnline.hashCode());
		result = prime * result + ((secondEmail == null) ? 0 : secondEmail.hashCode());
		result = prime * result + ((secondSurname == null) ? 0 : secondSurname.hashCode());
		result = prime * result + ((sessionID == null) ? 0 : sessionID.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((stateOfBirth == null) ? 0 : stateOfBirth.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((streetNumber == null) ? 0 : streetNumber.hashCode());
		result = prime * result + ((subBuildingorFlat == null) ? 0 : subBuildingorFlat.hashCode());
		result = prime * result + ((subCity == null) ? 0 : subCity.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((tradeContactID == null) ? 0 : tradeContactID.hashCode());
		result = prime * result + ((unitNumber == null) ? 0 : unitNumber.hashCode());
		result = prime * result + ((updateMethod == null) ? 0 : updateMethod.hashCode());
		result = prime * result + ((yearsInAddress == null) ? 0 : yearsInAddress.hashCode());
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
		UpdateContact other = (UpdateContact) obj;
		if (addressType == null) {
			if (other.addressType != null)
				return false;
		} else if (!addressType.equals(other.addressType)) {
			return false;
		  }
		if (areaNumber == null) {
			if (other.areaNumber != null)
				return false;
		} else if (!areaNumber.equals(other.areaNumber)) {
			return false;
		  }
		if (australiaRTACardNumber == null) {
			if (other.australiaRTACardNumber != null)
				return false;
		} else if (!australiaRTACardNumber.equals(other.australiaRTACardNumber)) {
			return false;
		  }
		if (aza == null) {
			if (other.aza != null)
				return false;
		} else if (!aza.equals(other.aza)) {
			return false;
		  }
		if (buildingName == null) {
			if (other.buildingName != null)
				return false;
		} else if (!buildingName.equals(other.buildingName)) {
			return false;
		  }
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city)) {
			return false;
		  }
		if (civicNumber == null) {
			if (other.civicNumber != null)
				return false;
		} else if (!civicNumber.equals(other.civicNumber)) {
			return false;
		  }
		if (contactSFID == null) {
			if (other.contactSFID != null)
				return false;
		} else if (!contactSFID.equals(other.contactSFID)) {
			return false;
		  }
		if (contactStatus == null) {
			if (other.contactStatus != null)
				return false;
		} else if (!contactStatus.equals(other.contactStatus)) {
			return false;
		  }
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country)) {
			return false;
		  }
		if (countryOfBirth == null) {
			if (other.countryOfBirth != null)
				return false;
		} else if (!countryOfBirth.equals(other.countryOfBirth)) {
			return false;
		  }
		if (county == null) {
			if (other.county != null)
				return false;
		} else if (!county.equals(other.county)) {
			return false;
		  }
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation)) {
			return false;
		  }
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district)) {
			return false;
		  }
		if (dlCardNumber == null) {
			if (other.dlCardNumber != null)
				return false;
		} else if (!dlCardNumber.equals(other.dlCardNumber)) {
			return false;
		  }
		if (dlCountryCode == null) {
			if (other.dlCountryCode != null)
				return false;
		} else if (!dlCountryCode.equals(other.dlCountryCode)) {
			return false;
		  }
		if (dlExpiryDate == null) {
			if (other.dlExpiryDate != null)
				return false;
		} else if (!dlExpiryDate.equals(other.dlExpiryDate)) {
			return false;
		  }
		if (dlLicenseNumber == null) {
			if (other.dlLicenseNumber != null)
				return false;
		} else if (!dlLicenseNumber.equals(other.dlLicenseNumber)) {
			return false;
		  }
		if (dlStateCode == null) {
			if (other.dlStateCode != null)
				return false;
		} else if (!dlStateCode.equals(other.dlStateCode)) {
			return false;
		  }
		if (dlVersionNumber == null) {
			if (other.dlVersionNumber != null)
				return false;
		} else if (!dlVersionNumber.equals(other.dlVersionNumber)) {
			return false;
		  }
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob)) {
			return false;
		  }
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email)) {
			return false;
		  }
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName)) {
			return false;
		  }
		if (floorNumber == null) {
			if (other.floorNumber != null)
				return false;
		} else if (!floorNumber.equals(other.floorNumber)) {
			return false;
		  }
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender)) {
			return false;
		  }
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress)) {
			return false;
		  }
		if (isFraugsterEligible != other.isFraugsterEligible)
			return false;
		if (isKYCEligible != other.isKYCEligible)
			return false;
		if (isSanctionEligible != other.isSanctionEligible)
			return false;
		if (last5LoginDateTime == null) {
			if (other.last5LoginDateTime != null)
				return false;
		} else if (!last5LoginDateTime.equals(other.last5LoginDateTime)) {
			return false;
		  }
		if (lastIPAddresses == null) {
			if (other.lastIPAddresses != null)
				return false;
		} else if (!lastIPAddresses.equals(other.lastIPAddresses)) {
			return false;
		  }
		if (lastLoginFailDateTime == null) {
			if (other.lastLoginFailDateTime != null)
				return false;
		} else if (!lastLoginFailDateTime.equals(other.lastLoginFailDateTime)) {
			return false;
		  }
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName)) {
			return false;
		  }
		if (lastPINIssuedDateTime == null) {
			if (other.lastPINIssuedDateTime != null)
				return false;
		} else if (!lastPINIssuedDateTime.equals(other.lastPINIssuedDateTime)) {
			return false;
		  }
		if (lastPasswordResetOn == null) {
			if (other.lastPasswordResetOn != null)
				return false;
		} else if (!lastPasswordResetOn.equals(other.lastPasswordResetOn)) {
			return false;
		  }
		if (listofDevicesUsedByCustomer == null) {
			if (other.listofDevicesUsedByCustomer != null)
				return false;
		} else if (!listofDevicesUsedByCustomer.equals(other.listofDevicesUsedByCustomer)) {
			return false;
		  }
		if (medicareCardNumber == null) {
			if (other.medicareCardNumber != null)
				return false;
		} else if (!medicareCardNumber.equals(other.medicareCardNumber)) {
			return false;
		  }
		if (medicareReferenceNumber == null) {
			if (other.medicareReferenceNumber != null)
				return false;
		} else if (!medicareReferenceNumber.equals(other.medicareReferenceNumber)) {
			return false;
		  }
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName)) {
			return false;
		  }
		if (mothersSurname == null) {
			if (other.mothersSurname != null)
				return false;
		} else if (!mothersSurname.equals(other.mothersSurname)) {
			return false;
		  }
		if (municipalityOfBirth == null) {
			if (other.municipalityOfBirth != null)
				return false;
		} else if (!municipalityOfBirth.equals(other.municipalityOfBirth)) {
			return false;
		  }
		if (nationalIdNumber == null) {
			if (other.nationalIdNumber != null)
				return false;
		} else if (!nationalIdNumber.equals(other.nationalIdNumber)) {
			return false;
		  }
		if (nationalIdType == null) {
			if (other.nationalIdType != null)
				return false;
		} else if (!nationalIdType.equals(other.nationalIdType)) {
			return false;
		  }
		if (nationality == null) {
			if (other.nationality != null)
				return false;
		} else if (!nationality.equals(other.nationality)) {
			return false;
		  }
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation)) {
			return false;
		  }
		if (onlineLoginStatus == null) {
			if (other.onlineLoginStatus != null)
				return false;
		} else if (!onlineLoginStatus.equals(other.onlineLoginStatus)) {
			return false;
		  }
		if (passportCountryCode == null) {
			if (other.passportCountryCode != null)
				return false;
		} else if (!passportCountryCode.equals(other.passportCountryCode)) {
			return false;
		  }
		if (passportExiprydate == null) {
			if (other.passportExiprydate != null)
				return false;
		} else if (!passportExiprydate.equals(other.passportExiprydate)) {
			return false;
		  }
		if (passportFamilyNameAtBirth == null) {
			if (other.passportFamilyNameAtBirth != null)
				return false;
		} else if (!passportFamilyNameAtBirth.equals(other.passportFamilyNameAtBirth)) {
			return false;
		  }
		if (passportFullName == null) {
			if (other.passportFullName != null)
				return false;
		} else if (!passportFullName.equals(other.passportFullName)) {
			return false;
		  }
		if (passportMRZLine1 == null) {
			if (other.passportMRZLine1 != null)
				return false;
		} else if (!passportMRZLine1.equals(other.passportMRZLine1)) {
			return false;
	   	  }
		if (passportMRZLine2 == null) {
			if (other.passportMRZLine2 != null)
				return false;
		} else if (!passportMRZLine2.equals(other.passportMRZLine2)) {
			return false;
		  }
		if (passportNameAtCitizenship == null) {
			if (other.passportNameAtCitizenship != null)
				return false;
		} else if (!passportNameAtCitizenship.equals(other.passportNameAtCitizenship)) {
			return false;
		  }
		if (passportNumber == null) {
			if (other.passportNumber != null)
				return false;
		} else if (!passportNumber.equals(other.passportNumber)) {
			return false;
		  }
		if (passportPlaceOfBirth == null) {
			if (other.passportPlaceOfBirth != null)
				return false;
		} else if (!passportPlaceOfBirth.equals(other.passportPlaceOfBirth)) {
			return false;
		  }
		if (phoneHome == null) {
			if (other.phoneHome != null)
				return false;
		} else if (!phoneHome.equals(other.phoneHome)) {
			return false;
		  }
		if (phoneMobile == null) {
			if (other.phoneMobile != null)
				return false;
		} else if (!phoneMobile.equals(other.phoneMobile)) {
			return false;
		  }
		if (phoneWork == null) {
			if (other.phoneWork != null)
				return false;
		} else if (!phoneWork.equals(other.phoneWork)) {
			return false;
		  }
		if (phoneWorkExtn == null) {
			if (other.phoneWorkExtn != null)
				return false;
		} else if (!phoneWorkExtn.equals(other.phoneWorkExtn)) {
			return false;
		  }
		if (postCode == null) {
			if (other.postCode != null)
				return false;
		} else if (!postCode.equals(other.postCode)) {
			return false;
		  }
		if (prefecture == null) {
			if (other.prefecture != null)
				return false;
		} else if (!prefecture.equals(other.prefecture)) {
			return false;
		  }
		if (preferredName == null) {
			if (other.preferredName != null)
				return false;
		} else if (!preferredName.equals(other.preferredName)) {
			return false;
		  }
		if (previousCity == null) {
			if (other.previousCity != null)
				return false;
		} else if (!previousCity.equals(other.previousCity)) {
			return false;
		  }
		if (previousCountry == null) {
			if (other.previousCountry != null)
				return false;
		} else if (!previousCountry.equals(other.previousCountry)) {
			return false;
		  }
		if (previousPostCode == null) {
			if (other.previousPostCode != null)
				return false;
		} else if (!previousPostCode.equals(other.previousPostCode)) {
			return false;
		  }
		if (previousState == null) {
			if (other.previousState != null)
				return false;
		} else if (!previousState.equals(other.previousState)) {
			return false;
		  }
		if (previousStreet == null) {
			if (other.previousStreet != null)
				return false;
		} else if (!previousStreet.equals(other.previousStreet)) {
			return false;
		  }
		if (primaryContact == null) {
			if (other.primaryContact != null)
				return false;
		} else if (!primaryContact.equals(other.primaryContact)) {
			return false;
		  }
		if (primaryPhone == null) {
			if (other.primaryPhone != null)
				return false;
		} else if (!primaryPhone.equals(other.primaryPhone)) {
			return false;
		  }
		if (recordUpdatedOn == null) {
			if (other.recordUpdatedOn != null)
				return false;
		} else if (!recordUpdatedOn.equals(other.recordUpdatedOn)) {
			return false;
		  }
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region)) {
			return false;
		  }
		if (residentialStatus == null) {
			if (other.residentialStatus != null)
				return false;
		} else if (!residentialStatus.equals(other.residentialStatus)) {
			return false;
		  }
		if (secondContactAddedLaterOnline == null) {
			if (other.secondContactAddedLaterOnline != null)
				return false;
		} else if (!secondContactAddedLaterOnline.equals(other.secondContactAddedLaterOnline)) {
			return false;
		  }
		if (secondEmail == null) {
			if (other.secondEmail != null)
				return false;
		} else if (!secondEmail.equals(other.secondEmail)) {
			return false;
		  }
		if (secondSurname == null) {
			if (other.secondSurname != null)
				return false;
		} else if (!secondSurname.equals(other.secondSurname)) {
			return false;
		  }
		if (sessionID == null) {
			if (other.sessionID != null)
				return false;
		} else if (!sessionID.equals(other.sessionID)) {
			return false;
		  }
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state)) {
			return false;
		  }
		if (stateOfBirth == null) {
			if (other.stateOfBirth != null)
				return false;
		} else if (!stateOfBirth.equals(other.stateOfBirth)) {
			return false;
		  }
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street)) {
			return false;
		  }
		if (streetNumber == null) {
			if (other.streetNumber != null)
				return false;
		} else if (!streetNumber.equals(other.streetNumber)) {
			return false;
		  }
		if (subBuildingorFlat == null) {
			if (other.subBuildingorFlat != null)
				return false;
		} else if (!subBuildingorFlat.equals(other.subBuildingorFlat)) {
			return false;
		  }
		if (subCity == null) {
			if (other.subCity != null)
				return false;
		} else if (!subCity.equals(other.subCity)) {
			return false;
		  }
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title)) {
			return false;
		  }
		if (tradeContactID == null) {
			if (other.tradeContactID != null)
				return false;
		} else if (!tradeContactID.equals(other.tradeContactID)) {
			return false;
		  }
		if (unitNumber == null) {
			if (other.unitNumber != null)
				return false;
		} else if (!unitNumber.equals(other.unitNumber)) {
			return false;
		  }
		if (updateMethod == null) {
			if (other.updateMethod != null)
				return false;
		} else if (!updateMethod.equals(other.updateMethod)) {
			return false;
		  }
		if (yearsInAddress == null) {
			if (other.yearsInAddress != null)
				return false;
		} else if (!yearsInAddress.equals(other.yearsInAddress)) {
			return false;
		  }
		return true;
	}
	
}
