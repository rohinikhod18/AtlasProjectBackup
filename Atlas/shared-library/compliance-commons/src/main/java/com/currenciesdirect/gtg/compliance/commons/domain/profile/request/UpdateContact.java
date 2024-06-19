package com.currenciesdirect.gtg.compliance.commons.domain.profile.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class UpdateContact.
 */
@SuppressWarnings("squid:S3776")
public class UpdateContact implements Serializable {

	
	/** The Constant serialVersionUID. */
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
	@ApiModelProperty(value = "The second surname", example = "",  required = true)
	@JsonProperty(value="second_surname")
	private String secondSurname;
	
	/** The mothers surname. */
	@ApiModelProperty(value = "The mother's surname", example = "",  required = true)
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
	@ApiModelProperty(value = "The country", required = true)
	@JsonProperty(value="country_of_residence")
	private String country ; 
	
	/** The home phone. */
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
	@ApiModelProperty(value = "The primary email of the contact", example = "bob@world.com", required = true)
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
	
	/** The sub building or flat. */
	@ApiModelProperty(value = "The sub building or flat", required = true)
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
	
	/** The ip domain. */
	@JsonProperty(value = "ip_domain")
	private String ipDomain;
	
	/** The ip isp. */
	@JsonProperty(value = "ip_isp")
	private String ipIsp;
	
	/** The ip city. */
	@JsonProperty(value = "ip_city")
	private String ipCity;
	
	/** The ip country. */
	@JsonProperty(value = "ip_country")
	private String ipCountry;
	
	/** The ssn number. */
	@ApiModelProperty(value = "The ssn number", required = true)
	@JsonProperty(value = "ssn")
	private String ssn; //Add in AT-3661
	
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
	
	/**
	 * Gets the mothers surname.
	 *
	 * @return the mothers surname
	 */
	public String getMothersSurname() {
		return mothersSurname;
	}

	/**
	 * Sets the mothers surname.
	 *
	 * @param mothersSurname the new mothers surname
	 */
	public void setMothersSurname(String mothersSurname) {
		this.mothersSurname = mothersSurname;
	}

	/**
	 * Gets the district.
	 *
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * Sets the district.
	 *
	 * @param district the new district
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * Gets the area number.
	 *
	 * @return the area number
	 */
	public String getAreaNumber() {
		return areaNumber;
	}

	/**
	 * Sets the area number.
	 *
	 * @param areaNumber the new area number
	 */
	public void setAreaNumber(String areaNumber) {
		this.areaNumber = areaNumber;
	}

	/**
	 * Gets the aza.
	 *
	 * @return the aza
	 */
	public String getAza() {
		return aza;
	}

	/**
	 * Sets the aza.
	 *
	 * @param aza the new aza
	 */
	public void setAza(String aza) {
		this.aza = aza;
	}

	/**
	 * Gets the prefecture.
	 *
	 * @return the prefecture
	 */
	public String getPrefecture() {
		return prefecture;
	}

	/**
	 * Sets the prefecture.
	 *
	 * @param prefecture the new prefecture
	 */
	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	/**
	 * Gets the floor number.
	 *
	 * @return the floor number
	 */
	public String getFloorNumber() {
		return floorNumber;
	}

	/**
	 * Sets the floor number.
	 *
	 * @param floorNumber the new floor number
	 */
	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}
	
	/**
	 * Gets the update method.
	 *
	 * @return the update method
	 */
	public String getUpdateMethod() {
		return updateMethod;
	}

	/**
	 * Sets the update method.
	 *
	 * @param updateMethod the new update method
	 */
	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}

	/**
	 * Gets the record updated on.
	 *
	 * @return the record updated on
	 */
	public String getRecordUpdatedOn() {
		return recordUpdatedOn;
	}

	/**
	 * Sets the record updated on.
	 *
	 * @param recordUpdatedOn the new record updated on
	 */
	public void setRecordUpdatedOn(String recordUpdatedOn) {
		this.recordUpdatedOn = recordUpdatedOn;
	}

	/**
	 * Gets the contact status.
	 *
	 * @return the contact status
	 */
	public String getContactStatus() {
		return contactStatus;
	}

	/**
	 * Sets the contact status.
	 *
	 * @param contactStatus the new contact status
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

	/**
	 * Gets the online login status.
	 *
	 * @return the online login status
	 */
	public String getOnlineLoginStatus() {
		return onlineLoginStatus;
	}

	/**
	 * Sets the online login status.
	 *
	 * @param onlineLoginStatus the new online login status
	 */
	public void setOnlineLoginStatus(String onlineLoginStatus) {
		this.onlineLoginStatus = onlineLoginStatus;
	}

	/**
	 * Gets the session ID.
	 *
	 * @return the session ID
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * Sets the session ID.
	 *
	 * @param sessionID the new session ID
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * Gets the last PIN issued date time.
	 *
	 * @return the last PIN issued date time
	 */
	public String getLastPINIssuedDateTime() {
		return lastPINIssuedDateTime;
	}

	/**
	 * Sets the last PIN issued date time.
	 *
	 * @param lastPINIssuedDateTime the new last PIN issued date time
	 */
	public void setLastPINIssuedDateTime(String lastPINIssuedDateTime) {
		this.lastPINIssuedDateTime = lastPINIssuedDateTime;
	}

	/**
	 * Gets the last login fail date time.
	 *
	 * @return the last login fail date time
	 */
	public String getLastLoginFailDateTime() {
		return lastLoginFailDateTime;
	}

	/**
	 * Sets the last login fail date time.
	 *
	 * @param lastLoginFailDateTime the new last login fail date time
	 */
	public void setLastLoginFailDateTime(String lastLoginFailDateTime) {
		this.lastLoginFailDateTime = lastLoginFailDateTime;
	}

	/**
	 * Gets the last password reset on.
	 *
	 * @return the last password reset on
	 */
	public String getLastPasswordResetOn() {
		return lastPasswordResetOn;
	}

	/**
	 * Sets the last password reset on.
	 *
	 * @param lastPasswordResetOn the new last password reset on
	 */
	public void setLastPasswordResetOn(String lastPasswordResetOn) {
		this.lastPasswordResetOn = lastPasswordResetOn;
	}

	/**
	 * Gets the listof devices used by customer.
	 *
	 * @return the listof devices used by customer
	 */
	public String getListofDevicesUsedByCustomer() {
		return listofDevicesUsedByCustomer;
	}

	/**
	 * Sets the listof devices used by customer.
	 *
	 * @param listofDevicesUsedByCustomer the new listof devices used by customer
	 */
	public void setListofDevicesUsedByCustomer(String listofDevicesUsedByCustomer) {
		this.listofDevicesUsedByCustomer = listofDevicesUsedByCustomer;
	}

	/**
	 * Gets the second contact added later online.
	 *
	 * @return the second contact added later online
	 */
	public Boolean getSecondContactAddedLaterOnline() {
		return secondContactAddedLaterOnline;
	}

	/**
	 * Sets the second contact added later online.
	 *
	 * @param secondContactAddedLaterOnline the new second contact added later online
	 */
	public void setSecondContactAddedLaterOnline(Boolean secondContactAddedLaterOnline) {
		this.secondContactAddedLaterOnline = secondContactAddedLaterOnline;
	}

	/**
	 * Gets the last IP addresses.
	 *
	 * @return the last IP addresses
	 */
	public String getLastIPAddresses() {
		return lastIPAddresses;
	}

	/**
	 * Sets the last IP addresses.
	 *
	 * @param lastIPAddresses the new last IP addresses
	 */
	public void setLastIPAddresses(String lastIPAddresses) {
		this.lastIPAddresses = lastIPAddresses;
	}

	/**
	 * Gets the last 5 login date time.
	 *
	 * @return the last 5 login date time
	 */
	public String getLast5LoginDateTime() {
		return last5LoginDateTime;
	}

	/**
	 * Sets the last 5 login date time.
	 *
	 * @param last5LoginDateTime the new last 5 login date time
	 */
	public void setLast5LoginDateTime(String last5LoginDateTime) {
		this.last5LoginDateTime = last5LoginDateTime;
	}

	
	/**
	 * Gets the contact SFID.
	 *
	 * @return the contact SFID
	 */
	public String getContactSFID() {
		return contactSFID;
	}

	/**
	 * Sets the contact SFID.
	 *
	 * @param contactSFID the new contact SFID
	 */
	public void setContactSFID(String contactSFID) {
		this.contactSFID = contactSFID;
	}

	/**
	 * Gets the trade contact ID.
	 *
	 * @return the trade contact ID
	 */
	public Integer getTradeContactID() {
		return tradeContactID;
	}

	/**
	 * Sets the trade contact ID.
	 *
	 * @param tradeContactID the new trade contact ID
	 */
	public void setTradeContactID(Integer tradeContactID) {
		this.tradeContactID = tradeContactID;
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
	 * @param title the new title
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
	 * @param preferredName the new preferred name
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
	 * @param firstName the new first name
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
	 * @param middleName the new middle name
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
	 * @param lastName the new last name
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
	 * @param dob the new dob
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
	 * @param addressType the new address type
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
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
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the county.
	 *
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * Sets the county.
	 *
	 * @param county the new county
	 */
	public void setCounty(String county) {
		this.county = county;
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
	 * @param postCode the new post code
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
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
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
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
	 * @param phoneHome the new phone home
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
	 * @param phoneWork the new phone work
	 */
	public void setPhoneWork(String phoneWork) {
		this.phoneWork = phoneWork;
	}

	/**
	 * Gets the phone work extn.
	 *
	 * @return the phone work extn
	 */
	public String getPhoneWorkExtn() {
		return phoneWorkExtn;
	}

	/**
	 * Sets the phone work extn.
	 *
	 * @param phoneWorkExtn the new phone work extn
	 */
	public void setPhoneWorkExtn(String phoneWorkExtn) {
		this.phoneWorkExtn = phoneWorkExtn;
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
	 * @param phoneMobile the new phone mobile
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
	 * @param primaryPhone the new primary phone
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
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the second email.
	 *
	 * @return the second email
	 */
	public String getSecondEmail() {
		return secondEmail;
	}

	/**
	 * Sets the second email.
	 *
	 * @param secondEmail the new second email
	 */
	public void setSecondEmail(String secondEmail) {
		this.secondEmail = secondEmail;
	}

	/**
	 * Gets the designation.
	 *
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * Sets the designation.
	 *
	 * @param designation the new designation
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
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
	 * @param nationality the new nationality
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
	 * @param gender the new gender
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
	 * @param occupation the new occupation
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
	 * @param passportNumber the new passport number
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
	 * @param passportCountryCode the new passport country code
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
	 * @param passportFamilyNameAtBirth the new passport family name at birth
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
	 * @param passportNameAtCitizenship the new passport name at citizenship
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
	 * @param passportPlaceOfBirth the new passport place of birth
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
	 * @param dlLicenseNumber the new dl license number
	 */
	public void setDlLicenseNumber(String dlLicenseNumber) {
		this.dlLicenseNumber = dlLicenseNumber;
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
	 * Sets the dl card number.
	 *
	 * @param dlCardNumber the new dl card number
	 */
	public void setDlCardNumber(String dlCardNumber) {
		this.dlCardNumber = dlCardNumber;
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
	 * @param dlCountryCode the new dl country code
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
	 * @param dlStateCode the new dl state code
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
	 * @param dlExpiryDate the new dl expiry date
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
	 * @param medicareCardNumber the new medicare card number
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
	 * @param medicareReferenceNumber the new medicare reference number
	 */
	public void setMedicareReferenceNumber(String medicareReferenceNumber) {
		this.medicareReferenceNumber = medicareReferenceNumber;
	}

	/**
	 * Gets the australia RTA card number.
	 *
	 * @return the australia RTA card number
	 */
	public String getAustraliaRTACardNumber() {
		return australiaRTACardNumber;
	}

	/**
	 * Sets the australia RTA card number.
	 *
	 * @param australiaRTACardNumber the new australia RTA card number
	 */
	public void setAustraliaRTACardNumber(String australiaRTACardNumber) {
		this.australiaRTACardNumber = australiaRTACardNumber;
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
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
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
	 * @param municipalityOfBirth the new municipality of birth
	 */
	public void setMunicipalityOfBirth(String municipalityOfBirth) {
		this.municipalityOfBirth = municipalityOfBirth;
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

	/**
	 * Gets the state of birth.
	 *
	 * @return the state of birth
	 */
	public String getStateOfBirth() {
		return stateOfBirth;
	}

	/**
	 * Sets the state of birth.
	 *
	 * @param stateOfBirth the new state of birth
	 */
	public void setStateOfBirth(String stateOfBirth) {
		this.stateOfBirth = stateOfBirth;
	}

	/**
	 * Gets the civic number.
	 *
	 * @return the civic number
	 */
	public String getCivicNumber() {
		return civicNumber;
	}

	/**
	 * Sets the civic number.
	 *
	 * @param civicNumber the new civic number
	 */
	public void setCivicNumber(String civicNumber) {
		this.civicNumber = civicNumber;
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
	 * @param subBuildingorFlat the new sub buildingor flat
	 */
	public void setSubBuildingorFlat(String subBuildingorFlat) {
		this.subBuildingorFlat = subBuildingorFlat;
	}

	/**
	 * Gets the unit number.
	 *
	 * @return the unit number
	 */
	public String getUnitNumber() {
		return unitNumber;
	}

	/**
	 * Sets the unit number.
	 *
	 * @param unitNumber the new unit number
	 */
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
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
	 * @param subCity the new sub city
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
	 * @param region the new region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	

	/**
	 * Gets the building name.
	 *
	 * @return the building name
	 */
	public String getBuildingName() {
		return buildingName;
	}

	/**
	 * Sets the building name.
	 *
	 * @param buildingName the new building name
	 */
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
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
	 * @param street the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the street number.
	 *
	 * @return the street number
	 */
	public String getStreetNumber() {
		return streetNumber;
	}

	/**
	 * Sets the street number.
	 *
	 * @param streetNumber the new street number
	 */
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}


	/**
	 * Gets the passport exiprydate.
	 *
	 * @return the passport exiprydate
	 */
	public String getPassportExiprydate() {
		return passportExiprydate;
	}

	/**
	 * Sets the passport exiprydate.
	 *
	 * @param passportExiprydate the new passport exiprydate
	 */
	public void setPassportExiprydate(String passportExiprydate) {
		this.passportExiprydate = passportExiprydate;
	}

	/**
	 * Gets the dl version number.
	 *
	 * @return the dl version number
	 */
	public String getDlVersionNumber() {
		return dlVersionNumber;
	}

	/**
	 * Sets the dl version number.
	 *
	 * @param dlVersionNumber the new dl version number
	 */
	public void setDlVersionNumber(String dlVersionNumber) {
		this.dlVersionNumber = dlVersionNumber;
	}

	/**
	 * Gets the national id number.
	 *
	 * @return the national id number
	 */
	public String getNationalIdNumber() {
		return nationalIdNumber;
	}

	/**
	 * Sets the national id number.
	 *
	 * @param nationalIdNumber the new national id number
	 */
	public void setNationalIdNumber(String nationalIdNumber) {
		this.nationalIdNumber = nationalIdNumber;
	}

	/**
	 * Gets the second surname.
	 *
	 * @return the second surname
	 */
	public String getSecondSurname() {
		return secondSurname;
	}

	/**
	 * Sets the second surname.
	 *
	 * @param secondSurname the new second surname
	 */
	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}


	/**
	 * Gets the passport full name.
	 *
	 * @return the passport full name
	 */
	public String getPassportFullName() {
		return passportFullName;
	}

	/**
	 * Sets the passport full name.
	 *
	 * @param passportFullName the new passport full name
	 */
	public void setPassportFullName(String passportFullName) {
		this.passportFullName = passportFullName;
	}

	/**
	 * Gets the passport MRZ line 1.
	 *
	 * @return the passport MRZ line 1
	 */
	public String getPassportMRZLine1() {
		return passportMRZLine1;
	}

	/**
	 * Sets the passport MRZ line 1.
	 *
	 * @param passportMRZLine1 the new passport MRZ line 1
	 */
	public void setPassportMRZLine1(String passportMRZLine1) {
		this.passportMRZLine1 = passportMRZLine1;
	}

	/**
	 * Gets the passport MRZ line 2.
	 *
	 * @return the passport MRZ line 2
	 */
	public String getPassportMRZLine2() {
		return passportMRZLine2;
	}

	/**
	 * Sets the passport MRZ line 2.
	 *
	 * @param passportMRZLine2 the new passport MRZ line 2
	 */
	public void setPassportMRZLine2(String passportMRZLine2) {
		this.passportMRZLine2 = passportMRZLine2;
	}
	
	/**
	 * Gets the ip address.
	 *
	 * @return the ip address
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * Sets the ip address.
	 *
	 * @param ipAddress the new ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * Gets the years in address.
	 *
	 * @return the years in address
	 */
	public String getYearsInAddress() {
		return yearsInAddress;
	}

	/**
	 * Sets the years in address.
	 *
	 * @param yearsInAddress the new years in address
	 */
	public void setYearsInAddress(String yearsInAddress) {
		this.yearsInAddress = yearsInAddress;
	}

	/**
	 * Gets the residential status.
	 *
	 * @return the residential status
	 */
	public String getResidentialStatus() {
		return residentialStatus;
	}

	/**
	 * Sets the residential status.
	 *
	 * @param residentialStatus the new residential status
	 */
	public void setResidentialStatus(String residentialStatus) {
		this.residentialStatus = residentialStatus;
	}

	/**
	 * Gets the previous city.
	 *
	 * @return the previous city
	 */
	public String getPreviousCity() {
		return previousCity;
	}

	/**
	 * Sets the previous city.
	 *
	 * @param previousCity the new previous city
	 */
	public void setPreviousCity(String previousCity) {
		this.previousCity = previousCity;
	}

	/**
	 * Gets the previous country.
	 *
	 * @return the previous country
	 */
	public String getPreviousCountry() {
		return previousCountry;
	}

	/**
	 * Sets the previous country.
	 *
	 * @param previousCountry the new previous country
	 */
	public void setPreviousCountry(String previousCountry) {
		this.previousCountry = previousCountry;
	}

	/**
	 * Gets the previous state.
	 *
	 * @return the previous state
	 */
	public String getPreviousState() {
		return previousState;
	}

	/**
	 * Sets the previous state.
	 *
	 * @param previousState the new previous state
	 */
	public void setPreviousState(String previousState) {
		this.previousState = previousState;
	}

	/**
	 * Gets the previous street.
	 *
	 * @return the previous street
	 */
	public String getPreviousStreet() {
		return previousStreet;
	}

	/**
	 * Sets the previous street.
	 *
	 * @param previousStreet the new previous street
	 */
	public void setPreviousStreet(String previousStreet) {
		this.previousStreet = previousStreet;
	}

	/**
	 * Gets the previous post code.
	 *
	 * @return the previous post code
	 */
	public String getPreviousPostCode() {
		return previousPostCode;
	}

	/**
	 * Sets the previous post code.
	 *
	 * @param previousPostCode the new previous post code
	 */
	public void setPreviousPostCode(String previousPostCode) {
		this.previousPostCode = previousPostCode;
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
	 * @param primaryContact the new primary contact
	 */
	public void setPrimaryContact(Boolean primaryContact) {
		this.primaryContact = primaryContact;
	}

	/**
	 * Checks if is KYC eligible.
	 *
	 * @return true, if is KYC eligible
	 */
	public boolean isKYCEligible() {
		return isKYCEligible;
	}

	/**
	 * Sets the KYC eligible.
	 *
	 * @param isKYCEligible the new KYC eligible
	 */
	public void setKYCEligible(boolean isKYCEligible) {
		this.isKYCEligible = isKYCEligible;
	}

	/**
	 * Checks if is fraugster eligible.
	 *
	 * @return true, if is fraugster eligible
	 */
	@ApiModelProperty(value = "Whether this account is eligible for Fraudster", required = true)
	public boolean isFraugsterEligible() {
		return isFraugsterEligible;
	}

	/**
	 * Sets the fraugster eligible.
	 *
	 * @param isFraugsterEligible the new fraugster eligible
	 */
	public void setFraugsterEligible(boolean isFraugsterEligible) {
		this.isFraugsterEligible = isFraugsterEligible;
	}

	/**
	 * Checks if is sanction eligible.
	 *
	 * @return true, if is sanction eligible
	 */
	public boolean isSanctionEligible() {
		return isSanctionEligible;
	}

	/**
	 * Sets the sanction eligible.
	 *
	 * @param isSanctionEligible the new sanction eligible
	 */
	public void setSanctionEligible(boolean isSanctionEligible) {
		this.isSanctionEligible = isSanctionEligible;
	}

	/**
	 * Gets the national id type.
	 *
	 * @return the national id type
	 */
	public String getNationalIdType() {
		return nationalIdType;
	}

	/**
	 * Sets the national id type.
	 *
	 * @param nationalIdType the new national id type
	 */
	public void setNationalIdType(String nationalIdType) {
		this.nationalIdType = nationalIdType;
	}

	/**
	 * Gets the authorised signatory.
	 *
	 * @return the authorised signatory
	 */
	public String getAuthorisedSignatory() {
		return authorisedSignatory;
	}

	/**
	 * Sets the authorised signatory.
	 *
	 * @param authorisedSignatory the new authorised signatory
	 */
	public void setAuthorisedSignatory(String authorisedSignatory) {
		this.authorisedSignatory = authorisedSignatory;
	}
	
	/**
	 * Gets the job title.
	 *
	 * @return the job title
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets the job title.
	 *
	 * @param jobTitle the new job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Gets the ip domain.
	 *
	 * @return the ip domain
	 */
	public String getIpDomain() {
		return ipDomain;
	}

	/**
	 * Sets the ip domain.
	 *
	 * @param ipDomain the new ip domain
	 */
	public void setIpDomain(String ipDomain) {
		this.ipDomain = ipDomain;
	}

	/**
	 * Gets the ip isp.
	 *
	 * @return the ip isp
	 */
	public String getIpIsp() {
		return ipIsp;
	}

	/**
	 * Sets the ip isp.
	 *
	 * @param ipIsp the new ip isp
	 */
	public void setIpIsp(String ipIsp) {
		this.ipIsp = ipIsp;
	}

	/**
	 * Gets the ip city.
	 *
	 * @return the ip city
	 */
	public String getIpCity() {
		return ipCity;
	}

	/**
	 * Sets the ip city.
	 *
	 * @param ipCity the new ip city
	 */
	public void setIpCity(String ipCity) {
		this.ipCity = ipCity;
	}

	/**
	 * Gets the ip country.
	 *
	 * @return the ip country
	 */
	public String getIpCountry() {
		return ipCountry;
	}

	/**
	 * Sets the ip country.
	 *
	 * @param ipCountry the new ip country
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
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
