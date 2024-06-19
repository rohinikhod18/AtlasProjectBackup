/*
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.FieldDisplayName;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.reg.RegistrationEnumValues;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

/**
 * The Class Contact.
 */
public class Contact implements Serializable {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(Contact.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ERROR. */
	private static final String ERROR = "Error in validation";

	/** The id. */
	@JsonIgnore
	private Integer id;

	/** The contact SFID. */
	@ApiModelProperty(value = "Contact Salesforce ID", example = "0030O00002jdcXIQAY", required = true)
	@JsonProperty(value = "contact_sf_id")
	private String contactSFID;

	/** The trade contact ID. */
	@ApiModelProperty(value = "The trade contact ID", example = "751843", required = true)
	@JsonProperty(value = "trade_contact_id")
	private Integer tradeContactID;

	/** The title. */
	@ApiModelProperty(value = "The client's title", example = "Mr", required = true)
	@JsonProperty(value = "title")
	@FieldDisplayName(displayName = "Title")
	private String title;

	/** The preferred name. */
	@ApiModelProperty(value = "The client's preferred name", example = "Bob", required = true)
	@JsonProperty(value = "pref_name")
	@FieldDisplayName(displayName = "Preferred Name")
	private String preferredName;

	/** The first name. */
	@ApiModelProperty(value = "The client's first name", example = "Robert", required = true)
	@JsonProperty(value = "first_name")
	@FieldDisplayName(displayName = "First Name")
	private String firstName;

	/** The middle name. */
	@ApiModelProperty(value = "The client's middle name", example = "Louis", required = true)
	@JsonProperty(value = "middle_name")
	@FieldDisplayName(displayName = "Middle Name")
	private String middleName;

	/** The last name. */
	@ApiModelProperty(value = "The client's last name", example = "Nighy", required = true)
	@JsonProperty(value = "last_name")
	@FieldDisplayName(displayName = "Last Name")
	private String lastName;

	/** The second surname. */
	@ApiModelProperty(value = "The second surname", example = "", required = true)
	@JsonProperty(value = "second_surname")
	@FieldDisplayName(displayName = "Second Surname")
	private String secondSurname;

	/** The mothers surname. */
	@ApiModelProperty(value = "The mother's surname", example = "", required = true)
	@JsonProperty(value = "mothers_surname")
	@FieldDisplayName(displayName = "Mothers Surname")
	private String mothersSurname;

	/** The dob. */
	@ApiModelProperty(value = "The Date of Birth", example = "1958-03-17", required = true)
	@JsonProperty(value = "dob")
	@FieldDisplayName(displayName = "Date of Birth")
	private String dob;

	/** The position of significance. */
	@ApiModelProperty(value = "For corporate clients, the position of the contact (e.g. Director, Company Secretary, Shareholder)", example = "Director and Shareholder 25% and >", required = true)
	@JsonProperty(value = "position_of_significance")
	@FieldDisplayName(displayName = "Position Of Significance")
	private String positionOfSignificance;

	/** The authorised signatory. */
	@ApiModelProperty(value = "Whether the contact is authorised sign official agreements. This is alway true for private individual clients, but it could be false for some corporate contacts", example = "true", required = true)
	@JsonProperty(value = "authorised_signatory")
	@FieldDisplayName(displayName = "Authorised Signatory")
	private String authorisedSignatory;

	/** The job title. */
	@ApiModelProperty(value = "The job title of the contact", example = "Director", required = true)
	@JsonProperty(value = "job_title")
	@FieldDisplayName(displayName = "Job Title")
	private String jobTitle;

	/** The address type. */
	@ApiModelProperty(value = "The address type of this contact", example = "Current Address", required = true)
	@JsonProperty(value = "address_type")
	@FieldDisplayName(displayName = "Address Type")
	private String addressType;

	/** The address 1. */
	@ApiModelProperty(value = "The address 1", required = true)
	@JsonProperty(value = "address1")
	private String address1;

	/** The city. */
	@ApiModelProperty(value = "The city", example = "London", required = true)
	@JsonProperty(value = "town_city_muncipalty")
	@FieldDisplayName(displayName = "Town/City/Muncipalty")
	private String city;

	/** The building name. */
	@ApiModelProperty(value = "The building name", required = true)
	@JsonProperty(value = "building_name")
	@FieldDisplayName(displayName = "Building Name")
	private String buildingName;

	/** The street. */
	@ApiModelProperty(value = "The street", required = true)
	@JsonProperty(value = "street")
	@FieldDisplayName(displayName = "Street")
	private String street;

	/** The street number. */
	@ApiModelProperty(value = "The street number", required = true)
	@JsonProperty(value = "street_number")
	@FieldDisplayName(displayName = "Street Number")
	private String streetNumber;

	/** The street type. */
	@ApiModelProperty(value = "Abrreviation for the street type (e.g. CRST for Cresent, CL for Close)", example = "ST", required = true)
	@JsonProperty(value = "street_type")
	@FieldDisplayName(displayName = "Street Type")
	private String streetType;

	/** The post code. */
	@ApiModelProperty(value = "The post code", example = "WC1", required = true)
	@JsonProperty(value = "post_code")
	@FieldDisplayName(displayName = "Post Code")
	private String postCode;

	/** The post code latitude. */
	@ApiModelProperty(value = "The post code latitude", required = true)
	@JsonProperty(value = "post_code_lat")
	@FieldDisplayName(displayName = "Post Code Latitude")
	private Float postCodeLatitude;

	/** The post code longitude. */
	@ApiModelProperty(value = "The post code longitude", required = true)
	@JsonProperty(value = "post_code_long")
	@FieldDisplayName(displayName = "Post Code Longitude")
	private Float postCodeLongitude;

	/** The country. */
	@ApiModelProperty(value = "The country of residence", example = "GBR", required = true)
	@JsonProperty(value = "country_of_residence")
	@FieldDisplayName(displayName = "Country Of Residence")
	private String country;

	/** The phone home. */
	@ApiModelProperty(value = "The home phone number of the contact", example = "+44-987654321", required = true)
	@JsonProperty(value = "home_phone")
	@FieldDisplayName(displayName = "Home Phone")
	private String phoneHome;

	/** The phone work. */
	@ApiModelProperty(value = "The work phone number of the contact", example = "+44-987654321", required = true)
	@JsonProperty(value = "work_phone")
	@FieldDisplayName(displayName = "Work Phone")
	private String phoneWork;

	/** The phone work extn. */
	@ApiModelProperty(value = "The work phone number extension", example = "",  required = true)
	@JsonProperty(value = "work_phone_ext")
	@FieldDisplayName(displayName = "Work Phone Extn")
	private String phoneWorkExtn;

	/** The phone mobile. */
	@ApiModelProperty(value = "The mobile phone number", example = "+44-987654321", required = true)
	@JsonProperty(value = "mobile_phone")
	@FieldDisplayName(displayName = "Mobile Phone")
	private String phoneMobile;

	/** The primary phone. */
	@ApiModelProperty(value = "The contact's primary phone number. I.e. Home, Mobile or Work", example = "Mobile", required = true)
	@JsonProperty(value = "primary_phone")
	@FieldDisplayName(displayName = "Primary Phone")
	private String primaryPhone;

	/** The email. */
	@ApiModelProperty(value = "The primary email of the contact", example = "bob@world.com", required = true)
	@JsonProperty(value = "email")
	@FieldDisplayName(displayName = "Email")
	private String email;

	/** The second email. */
	@ApiModelProperty(value = "The second email of the contact", example = "", required = true)
	@JsonProperty(value = "second_email")
	@FieldDisplayName(displayName = "Second Email")
	private String secondEmail;

	/** The designation. */
	@ApiModelProperty(value = "The job description of the contact", example = "Director", required = true)
	@JsonProperty(value = "designation")
	@FieldDisplayName(displayName = "Designation")
	private String designation;

	/** The ip address. */
	@ApiModelProperty(value = "The IP address of the client", example = "127.0.0.1", required = true)
	@JsonProperty(value = "ip_address")
	@FieldDisplayName(displayName = "IP Address")
	private String ipAddress;

	/** The ip address latitude. */
	@ApiModelProperty(value = "The IP address latitude", required = true)
	@JsonProperty(value = "ip_address_latitude")
	@FieldDisplayName(displayName = "IP Address Latitude")
	private String ipAddressLatitude;

	/** The ip address longitude. */
	@ApiModelProperty(value = "The IP address longitude", required = true)
	@JsonProperty(value = "ip_address_longitude")
	@FieldDisplayName(displayName = "IP Address Longitude")
	private String ipAddressLongitude;

	/** The is contact registered. */
	@ApiModelProperty(value = "Whether the contact is registered", required = true)
	@JsonProperty(value = "is_contact_registered")
	@FieldDisplayName(displayName = "Is Contact Registered")
	private Boolean isContactRegistered;

	/** The primary contact. */
	@ApiModelProperty(value = "Whether this is the primary contact for the client. This can be false for corporate clients or private joint accounts", example = "true", required = true)
	@JsonProperty(value = "is_primary_contact")
	@FieldDisplayName(displayName = "Primary Contact")
	private Boolean primaryContact;

	/** The nationality. */
	@ApiModelProperty(value = "The nationality of the contact", example = "GBR", required = true)
	@JsonProperty(value = "country_of_nationality")
	@FieldDisplayName(displayName = "Country Of Nationality")
	private String nationality;

	/** The gender. */
	@ApiModelProperty(value = "The gender of the contact", example = "Male", required = true)
	@JsonProperty(value = "gender")
	@FieldDisplayName(displayName = "Gender")
	private String gender;

	/** The occupation. */
	@ApiModelProperty(value = "The occupation of the contact", example = "Director", required = true)
	@JsonProperty(value = "occupation")
	@FieldDisplayName(displayName = "Occupation")
	private String occupation;

	/** The passport number. */
	@ApiModelProperty(value = "The passport number of the contact", example = "PA9876543", required = true)
	@JsonProperty(value = "passport_number")
	@FieldDisplayName(displayName = "Passport Number")
	private String passportNumber;

	/** The passport country code. */
	@ApiModelProperty(value = "The passport country code", example = "GBR", required = true)
	@JsonProperty(value = "passport_country_code")
	@FieldDisplayName(displayName = "Passport Country Code")
	private String passportCountryCode;

	/** The passport expiry date. */
	@ApiModelProperty(value = "The passport expiry date", example = "2027-10-28", required = true)
	@JsonProperty(value = "passport_exipry_date")
	@FieldDisplayName(displayName = "Passport Exipry Date")
	private String passportExiprydate;

	/** The passport full name. */
	@ApiModelProperty(value = "The passport full name", required = true)
	@JsonProperty(value = "passport_full_name")
	@FieldDisplayName(displayName = "Passport Full Name")
	private String passportFullName;

	/** The passport MRZ line 1. */
	@ApiModelProperty(value = "The passport machine-readable zone line 1", required = true)
	@JsonProperty(value = "passport_mrz_line_1")
	@FieldDisplayName(displayName = "Passport Mrz line_1")
	private String passportMRZLine1;

	/** The passport MRZ line 2. */
	@ApiModelProperty(value = "The passport machine-readable zone line 2", required = true)
	@JsonProperty(value = "passport_mrz_line_2")
	@FieldDisplayName(displayName = "Passport Mrz line_2")
	private String passportMRZLine2;

	/** The passport family name at birth. */
	@ApiModelProperty(value = "The passport family name at birth", required = true)
	@JsonProperty(value = "passport_birth_family_name")
	@FieldDisplayName(displayName = "Passport Birth Family Name")
	private String passportFamilyNameAtBirth;

	/** The passport name at citizenship. */
	@ApiModelProperty(value = "The passport name at citizenship", required = true)
	@JsonProperty(value = "passport_name_at_citizen")
	@FieldDisplayName(displayName = "Passport Name At Citizen")
	private String passportNameAtCitizenship;

	/** The passport place of birth. */
	@ApiModelProperty(value = "The passport place of birth", required = true)
	@JsonProperty(value = "passport_birth_place")
	@FieldDisplayName(displayName = "Passport Birth Place")
	private String passportPlaceOfBirth;

	/** The dl license number. */
	@ApiModelProperty(value = "The driving license number", example = "21731596", required = true)
	@JsonProperty(value = "driving_license")
	@FieldDisplayName(displayName = "Driving License")
	private String dlLicenseNumber;

	/** The dl version number. */
	@ApiModelProperty(value = "The driving license version number", example = "", required = true)
	@JsonProperty(value = "driving_version_number")
	@FieldDisplayName(displayName = "Driving Version Number")
	private String dlVersionNumber;

	/** The dl card number. */
	@ApiModelProperty(value = "The driving license card number", example = "2043257942", required = true)
	@JsonProperty(value = "driving_license_card_number")
	@FieldDisplayName(displayName = "Driving License Card Number")
	private String dlCardNumber;

	/** The dl country code. */
	@ApiModelProperty(value = "The driving license country code", required = true)
	@JsonProperty(value = "driving_license_country")
	@FieldDisplayName(displayName = "Driving License Country")
	private String dlCountryCode;

	/** The dl state code. */
	@ApiModelProperty(value = "The driving license state code", required = true)
	@JsonProperty(value = "driving_state_code")
	@FieldDisplayName(displayName = "Driving State Code")
	private String dlStateCode;

	/** The dl expiry date. */
	@ApiModelProperty(value = "The driving license expiry date", required = true)
	@JsonProperty(value = "driving_expiry")
	@FieldDisplayName(displayName = "Driving Expiry")
	private String dlExpiryDate;

	/** The medicare card number. */
	@ApiModelProperty(value = "The medicare card number", required = true)
	@JsonProperty(value = "medicare_card_number")
	@FieldDisplayName(displayName = "Medicare Card Number")
	private String medicareCardNumber;

	/** The medicare reference number. */
	@ApiModelProperty(value = "The medicare reference number", required = true)
	@JsonProperty(value = "medicare_ref_number")
	@FieldDisplayName(displayName = "Medicare Reference Number")
	private String medicareReferenceNumber;

	/** The australia RTA card number. */
	@ApiModelProperty(value = "The Australia Road Traffic Authority card number", required = true)
	@JsonProperty(value = "australia_rta_card_number")
	@FieldDisplayName(displayName = "Australia Rta Card Number")
	private String australiaRTACardNumber;

	/** The state. */
	@ApiModelProperty(value = "The state", required = true)
	@JsonProperty(value = "state_province_county")
	@FieldDisplayName(displayName = "State Province County")
	private String state;

	/** The municipality of birth. */
	@ApiModelProperty(value = "The municipality of birth", required = true)
	@JsonProperty(value = "municipality_of_birth")
	@FieldDisplayName(displayName = "Municipality Of Birth")
	private String municipalityOfBirth;

	/** The country of birth. */
	@ApiModelProperty(value = "The country of birth", required = true)
	@JsonProperty(value = "country_of_birth")
	@FieldDisplayName(displayName = "Country of Birth")
	private String countryOfBirth;

	/** The state of birth. */
	@ApiModelProperty(value = "The state of birth", required = true)
	@JsonProperty(value = "state_of_birth")
	@FieldDisplayName(displayName = "State of Birth")
	private String stateOfBirth;

	/** The civic number. */
	@ApiModelProperty(value = "The civic number", required = true)
	@JsonProperty(value = "civic_number")
	@FieldDisplayName(displayName = "Civic Number")
	private String civicNumber;

	/** The sub buildingor flat. */
	@ApiModelProperty(value = "The sub building or flat", required = true)
	@JsonProperty(value = "sub_building")
	@FieldDisplayName(displayName = "Sub Building")
	private String subBuildingorFlat;

	/** The building number. */
	@ApiModelProperty(value = "The building number", required = true)
	@JsonProperty(value = "house_building_number")
	@FieldDisplayName(displayName = "House Building Number")
	private String buildingNumber;

	/** The unit number. */
	@ApiModelProperty(value = "The unit number", required = true)
	@JsonProperty(value = "unit_number")
	@FieldDisplayName(displayName = "Unit Number")
	private String unitNumber;

	/** The sub city. */
	@ApiModelProperty(value = "The sub city", required = true)
	@JsonProperty(value = "sub_city")
	@FieldDisplayName(displayName = "Sub City")
	private String subCity;

	/** The region. */
	@ApiModelProperty(value = "The region", required = true)
	@JsonProperty(value = "region_suburb")
	@FieldDisplayName(displayName = "Region")
	private String region;

	/** The national id type. */
	@ApiModelProperty(value = "The national id type", required = true)
	@JsonProperty(value = "national_id_type")
	@FieldDisplayName(displayName = "National Id Type")
	private String nationalIdType;

	/** The national id number. */
	@ApiModelProperty(value = "The national id number", required = true)
	@JsonProperty(value = "national_id_number")
	@FieldDisplayName(displayName = "National Id Number")
	private String nationalIdNumber;

	/** The years in address. */
	@ApiModelProperty(value = "The years in address", required = true)
	@JsonProperty(value = "years_in_address")
	@FieldDisplayName(displayName = "Years In Address")
	private String yearsInAddress;

	/** The residential status. */
	@ApiModelProperty(value = "The residential status", required = true)
	@JsonProperty(value = "residential_status")
	@FieldDisplayName(displayName = "Residential Status")
	private String residentialStatus;

	/** The previous city. */
	@ApiModelProperty(value = "The previous city", required = true)
	@JsonProperty(value = "previous_city")
	@FieldDisplayName(displayName = "Previous City")
	private String previousCity;

	/** The previous country. */
	@ApiModelProperty(value = "The previous country", required = true)
	@JsonProperty(value = "previous_country")
	@FieldDisplayName(displayName = "Previous Country")
	private String previousCountry;

	/** The previous state. */
	@ApiModelProperty(value = "The previous state", required = true)
	@JsonProperty(value = "previous_state")
	@FieldDisplayName(displayName = "Previous State")
	private String previousState;

	/** The previous street. */
	@ApiModelProperty(value = "The previous street", required = true)
	@JsonProperty(value = "previous_street")
	@FieldDisplayName(displayName = "Previous Street")
	private String previousStreet;

	/** The previous post code. */
	@ApiModelProperty(value = "The previous post code", required = true)
	@JsonProperty(value = "previous_post_code")
	@FieldDisplayName(displayName = "Previous Post Code")
	private String previousPostCode;

	/** The version. */
	@ApiModelProperty(value = "The version", required = true)
	@JsonProperty(value = "version")
	private Integer version;

	/** The legacy trade contact ID. */
	@ApiModelProperty(value = "The legacy trade contact ID")
	@JsonProperty(value = "legacy_trade_contact_id")
	private Integer legacyTradeContactID;
	
	// default value is set to true
	/** The is KYC eligible. */
	// update false as in when required
	@JsonIgnore
	private boolean isKYCEligible = true;

	/** The is fraugster eligible. */
	@JsonIgnore
	private boolean isFraugsterEligible = true;

	/** The is fraugster performed. */
	@JsonIgnore
	private boolean isFraugsterPerformed = false;

	/** The is sanction eligible. */
	@JsonIgnore
	private boolean isSanctionEligible = true;

	/** The is sanction performed. */
	@JsonIgnore
	private boolean isSanctionPerformed = false;

	/** Update Contact related Fields *. */
	@JsonProperty(value = "update_method")
	private String updateMethod;

	/** The record updated on. */
	@ApiModelProperty(value = "The record updated on", required = true)
	@JsonProperty(value = "record_updated_on")
	@FieldDisplayName(displayName = "Record Updated On")
	private String recordUpdatedOn;

	/** The contact status. */
	@JsonIgnore
	private String contactStatus;

	/** The online login status. */
	@ApiModelProperty(value = "The online login status", required = true)
	@JsonProperty(value = "online_login_status")
	@FieldDisplayName(displayName = "Online Login Status")
	private String onlineLoginStatus;

	/** The session ID. */
	@ApiModelProperty(value = "The session ID", required = true)
	@JsonProperty(value = "session_id")
	private String sessionID;

	/** The last PIN issued date time. */
	@ApiModelProperty(value = "The last PIN issued date time", required = true)
	@JsonProperty(value = "last_pin_issued_date_time")
	@FieldDisplayName(displayName = "Last Pin Issued Date Time")
	private String lastPINIssuedDateTime;

	/** The last login fail date time. */
	@ApiModelProperty(value = "The last login fail date time", required = true)
	@JsonProperty(value = "last_login_fail_date_time")
	@FieldDisplayName(displayName = "Last Login Fail Date Time")
	private String lastLoginFailDateTime;

	/** The last password reset on. */
	@ApiModelProperty(value = "The last password reset on", required = true)
	@JsonProperty(value = "last_password_reset_on")
	@FieldDisplayName(displayName = "Last Password Reset On")
	private String lastPasswordResetOn;

	/** The listof devices used by customer. */
	@ApiModelProperty(value = "The listof devices used by customer", required = true)
	@JsonProperty(value = "list_of_devices_used_by_customer")
	@FieldDisplayName(displayName = "List Of Devices Used By Customer")
	private String listofDevicesUsedByCustomer;

	/** The second contact added later online. */
	@ApiModelProperty(value = "The second contact added later online", required = true)
	@JsonProperty(value = "second_contact_added_later_Online")
	@FieldDisplayName(displayName = "Second Contact Added Later Online")
	private Boolean secondContactAddedLaterOnline;

	/** The last IP addresses. */
	@ApiModelProperty(value = "The last IP addresses", required = true)
	@JsonProperty(value = "last_ip_addresses")
	@FieldDisplayName(displayName = "Last Ip Addresses")
	private String lastIPAddresses;

	/** The last 5 login date time. */
	@ApiModelProperty(value = "The last 5 login date time", required = true)
	@JsonProperty(value = "last_5_login_date_time")
	@FieldDisplayName(displayName = "Last 5 Login Date Time")
	private String last5LoginDateTime;

	/** The district. */
	@ApiModelProperty(value = "The district", required = true)
	@JsonProperty(value = "district")
	@FieldDisplayName(displayName = "District")
	private String district;

	/** The area number. */
	@ApiModelProperty(value = "The area number", required = true)
	@JsonProperty(value = "area_number")
	@FieldDisplayName(displayName = "Area Number")
	private String areaNumber;

	/** The aza. */
	@ApiModelProperty(value = "The aza [an area in the Japanese addressing system]", required = true)
	@JsonProperty(value = "aza")
	private String aza;

	/** The prefecture. */
	@ApiModelProperty(value = "The prefecture", required = true)
	@JsonProperty(value = "prefecture")
	@FieldDisplayName(displayName = "Prefecture")
	private String prefecture;

	/** The floor number. */
	@ApiModelProperty(value = "The floor number", required = true)
	@JsonProperty(value = "floor_number")
	@FieldDisplayName(displayName = "Floor Number")
	private String floorNumber;

	/**
	 * Below fields are added to set previous service statuses to contact when
	 * update account/contact *.
	 */
	@JsonIgnore
	private String previousKycStatus = ServiceStatus.NOT_REQUIRED.toString();

	/** The previous sanction status. */
	@JsonIgnore
	private String previousSanctionStatus = ServiceStatus.NOT_REQUIRED.toString();

	/** The previous fraugster status. */
	@JsonIgnore
	private String previousFraugsterStatus = ServiceStatus.NOT_REQUIRED.toString();

	/** The previous blacklist status. */
	@JsonIgnore
	private String previousBlacklistStatus = ServiceStatus.NOT_REQUIRED.toString();

	/** The previous country global check status. */
	@JsonIgnore
	private String previousCountryGlobalCheckStatus = ServiceStatus.NOT_REQUIRED.toString();

	/** The previous paymentin watchlist status. */
	@JsonIgnore
	private String previousPaymentinWatchlistStatus = ServiceStatus.NOT_REQUIRED.toString();//Added for AT-2986

	/** The previous paymentout watchlist status. */
	@JsonIgnore
	private String previousPaymentoutWatchlistStatus = ServiceStatus.NOT_REQUIRED.toString();//Added for AT-2986
	
	/** The is on queue. */
	@JsonIgnore
	private boolean isOnQueue = false;

	@JsonIgnore
	private boolean poiNeeded = false;
	
	/** The ip domain. */
	@JsonProperty(value = "ip_domain")
	@FieldDisplayName(displayName = "IP Domain")
	private String ipDomain;
	
	/** The ip isp. */
	@JsonProperty(value = "ip_isp")
	@FieldDisplayName(displayName = "IP Isp")
	private String ipIsp;
	
	/** The ip city. */
	@JsonProperty(value = "ip_city")
	@FieldDisplayName(displayName = "IP City")
	private String ipCity;
	
	/** The ip country. */
	@JsonProperty(value = "ip_country")
	@FieldDisplayName(displayName = "IP Country")
	private String ipCountry;
	
	/** The poi exists. */
	@JsonIgnore
	private Integer poiExists;//Add for AT-3349
	
	/** The ssn number. */
	@ApiModelProperty(value = "The ssn number", required = true)
	@JsonProperty(value = "ssn")
	@FieldDisplayName(displayName = "ssn")
	private String ssn; // Add in AT-3661

	/** The mailing address. */
	@ApiModelProperty(value = "The mailing address", required = true)
	@JsonProperty(value = "mailingstreet2")
	private String mailingStreet;

	// Added for AT-4870
	/** The maddress 2 city. */
	@ApiModelProperty(value = "Mailing address 2 city", required = true)
	@JsonProperty(value = "maddress2_city")
	@FieldDisplayName(displayName = "Mailing address 2 city")
	private String maddress2City;

	/** The maddress 2 street. */
	@ApiModelProperty(value = "Mailing address 2 street", required = true)
	@JsonProperty(value = "maddress2_street")
	@FieldDisplayName(displayName = "Mailing address 2 street")
	private String maddress2Street;

	/** The maddress 2 postalcode. */
	@ApiModelProperty(value = "Mailing address 2 postal code", required = true)
	@JsonProperty(value = "maddress2_postalcode")
	@FieldDisplayName(displayName = "Mailing address 2 postal code")
	private String maddress2Postalcode;

	/** The maddress 2 country. */
	@ApiModelProperty(value = "Mailing address 2 country", required = true)
	@JsonProperty(value = "maddress2_country")
	@FieldDisplayName(displayName = "Mailing address 2 country")
	private String maddress2Country;

	/** The maddress 2 state. */
	@ApiModelProperty(value = "Mailing address 2 state", required = true)
	@JsonProperty(value = "maddress2_state")
	@FieldDisplayName(displayName = "Mailing address 2 state")
	private String maddress2State;
	
	/**
	 * Checks if is on queue.
	 *
	 * @return the isOnQueue
	 */
	public boolean isOnQueue() {
		return isOnQueue;
	}

	/**
	 * Sets the on queue.
	 *
	 * @param isOnQueue
	 *            the isOnQueue to set
	 */
	public void setOnQueue(boolean isOnQueue) {
		this.isOnQueue = isOnQueue;
	}

	/**
	 * Gets the address 1.
	 *
	 * @return the address 1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * Sets the address 1.
	 *
	 * @param address1
	 *            the new address 1
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

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
	 * @param mothersSurname
	 *            the new mothers surname
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
	 * @param district
	 *            the new district
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
	 * @param areaNumber
	 *            the new area number
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
	 * @param aza
	 *            the new aza
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
	 * @param prefecture
	 *            the new prefecture
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
	 * @param floorNumber
	 *            the new floor number
	 */
	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
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
	 * @param sessionID
	 *            the new session ID
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * Update Contact related Fields END *.
	 *
	 * @return the contact SFID
	 */

	public String getContactSFID() {
		return contactSFID;
	}

	/**
	 * Sets the contact SFID.
	 *
	 * @param contactSFID
	 *            the new contact SFID
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
	 * @param tradeContactID
	 *            the new trade contact ID
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
	 * @param phoneWorkExtn
	 *            the new phone work extn
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
	 * @param secondEmail
	 *            the new second email
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
	 * @param designation
	 *            the new designation
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
	 * @param dlCardNumber
	 *            the new dl card number
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
	 * @param australiaRTACardNumber
	 *            the new australia RTA card number
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
	 * @param state
	 *            the new state
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
	 * @param municipalityOfBirth
	 *            the new municipality of birth
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
	 * @param countryOfBirth
	 *            the new country of birth
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
	 * @param stateOfBirth
	 *            the new state of birth
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
	 * @param civicNumber
	 *            the new civic number
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
	 * @param subBuildingorFlat
	 *            the new sub buildingor flat
	 */
	public void setSubBuildingorFlat(String subBuildingorFlat) {
		if (subBuildingorFlat != null && subBuildingorFlat.equals(this.street)) {
			this.subBuildingorFlat = "";
		} else if (subBuildingorFlat != null) {
			this.subBuildingorFlat = subBuildingorFlat.trim();
		}
	}

	/**
	 * Gets the building number.
	 *
	 * @return the building number
	 */
	public String getBuildingNumber() {
		return buildingNumber;
	}

	/**
	 * Sets the building number.
	 *
	 * @param buildingNumber
	 *            the new building number
	 */
	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
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
	 * @param unitNumber
	 *            the new unit number
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
	 * @param subCity
	 *            the new sub city
	 */
	public void setSubCity(String subCity) {
		if (subCity != null && subCity.equals(this.street)) {
			this.subCity = "";
		} else if (subCity != null) {
			this.subCity = subCity.trim();
		}
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
	 * @param buildingName
	 *            the new building name
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
	 * @param street
	 *            the new street
	 */
	public void setStreet(String street) {
		if (street != null) {
			this.street = street.trim();
		}
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
	 * @param streetNumber
	 *            the new street number
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
	 * @param passportExiprydate
	 *            the new passport exiprydate
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
	 * @param dlVersionNumber
	 *            the new dl version number
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
	 * @param nationalIdNumber
	 *            the new national id number
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
	 * @param secondSurname
	 *            the new second surname
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
	 * @param passportFullName
	 *            the new passport full name
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
	 * @param passportMRZLine1
	 *            the new passport MRZ line 1
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
	 * @param passportMRZLine2
	 *            the new passport MRZ line 2
	 */
	public void setPassportMRZLine2(String passportMRZLine2) {
		this.passportMRZLine2 = passportMRZLine2;
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
	 * @param ipAddress
	 *            the new ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * Gets the ip address longitude.
	 *
	 * @return the ip address longitude
	 */
	public String getIpAddressLongitude() {
		return ipAddressLongitude;
	}

	/**
	 * Sets the ip address longitude.
	 *
	 * @param ipAddressLongitude
	 *            the new ip address longitude
	 */
	public void setIpAddressLongitude(String ipAddressLongitude) {
		this.ipAddressLongitude = ipAddressLongitude;
	}

	/**
	 * Gets the post code latitude.
	 *
	 * @return the post code latitude
	 */
	public Float getPostCodeLatitude() {
		return postCodeLatitude;
	}

	/**
	 * Sets the post code latitude.
	 *
	 * @param postCodeLatitude
	 *            the new post code latitude
	 */
	public void setPostCodeLatitude(Float postCodeLatitude) {
		this.postCodeLatitude = postCodeLatitude;
	}

	/**
	 * Gets the post code longitude.
	 *
	 * @return the post code longitude
	 */
	public Float getPostCodeLongitude() {
		return postCodeLongitude;
	}

	/**
	 * Sets the post code longitude.
	 *
	 * @param postCodeLongitude
	 *            the new post code longitude
	 */
	public void setPostCodeLongitude(Float postCodeLongitude) {
		this.postCodeLongitude = postCodeLongitude;
	}

	/**
	 * Gets the ip address latitude.
	 *
	 * @return the ip address latitude
	 */
	public String getIpAddressLatitude() {
		return ipAddressLatitude;
	}

	/**
	 * Sets the ip address latitude.
	 *
	 * @param ipAddressLatitude
	 *            the new ip address latitude
	 */
	public void setIpAddressLatitude(String ipAddressLatitude) {
		this.ipAddressLatitude = ipAddressLatitude;
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
	 * @param yearsInAddress
	 *            the new years in address
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
	 * @param residentialStatus
	 *            the new residential status
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
	 * @param previousCity
	 *            the new previous city
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
	 * @param previousCountry
	 *            the new previous country
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
	 * @param previousState
	 *            the new previous state
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
	 * @param previousStreet
	 *            the new previous street
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
	 * @param previousPostCode
	 *            the new previous post code
	 */
	public void setPreviousPostCode(String previousPostCode) {
		this.previousPostCode = previousPostCode;
	}

	/**
	 * Checks if is contact registered.
	 *
	 * @return true, if is contact registered
	 */
	@JsonIgnore
	public boolean isContactRegistered() {
		return isContactRegistered;
	}

	/**
	 * Gets the contact registered.
	 *
	 * @return the contact registered
	 */
	@JsonIgnore
	public boolean getContactRegistered() {
		return isContactRegistered;
	}

	/**
	 * Sets the contact registered.
	 *
	 * @param contactRegistered
	 *            the new contact registered
	 */
	public void setContactRegistered(Boolean contactRegistered) {
		this.isContactRegistered = contactRegistered;
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
	 * Checks if is KYC eligible.
	 *
	 * @return true, if is KYC eligible
	 */
	@JsonIgnore
	public boolean isKYCEligible() {
		return isKYCEligible;
	}

	/**
	 * Sets the KYC eligible.
	 *
	 * @param isKYCEligible
	 *            the new KYC eligible
	 */
	public void setKYCEligible(boolean isKYCEligible) {
		this.isKYCEligible = isKYCEligible;
	}

	/**
	 * Checks if is fraugster eligible.
	 *
	 * @return true, if is fraugster eligible
	 */
	@JsonIgnore
	public boolean isFraugsterEligible() {
		return isFraugsterEligible;
	}

	/**
	 * Sets the fraugster eligible.
	 *
	 * @param isFraugsterEligible
	 *            the new fraugster eligible
	 */
	public void setFraugsterEligible(boolean isFraugsterEligible) {
		this.isFraugsterEligible = isFraugsterEligible;
	}

	/**
	 * Checks if is sanction eligible.
	 *
	 * @return true, if is sanction eligible
	 */
	@JsonIgnore
	public boolean isSanctionEligible() {
		return isSanctionEligible;
	}

	/**
	 * Sets the sanction eligible.
	 *
	 * @param isSanctionEligible
	 *            the new sanction eligible
	 */
	public void setSanctionEligible(boolean isSanctionEligible) {
		this.isSanctionEligible = isSanctionEligible;
	}

	/**
	 * Checks if is sanction performed.
	 *
	 * @return true, if is sanction performed
	 */
	@JsonIgnore
	public boolean isSanctionPerformed() {
		return isSanctionPerformed;
	}

	public Boolean isFraugsterPerformed() {
		return isFraugsterPerformed;
	}

	/**
	 * Sets the sanction performed.
	 *
	 * @param isSanctionPerformed
	 *            the new sanction performed
	 */
	public void setSanctionPerformed(boolean isSanctionPerformed) {
		this.isSanctionPerformed = isSanctionPerformed;
	}

	/**
	 * Gets the checks if is contact registered.
	 *
	 * @return the checks if is contact registered
	 */
	public Boolean getIsContactRegistered() {
		return isContactRegistered;
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
	 * Gets the record updated on.
	 *
	 * @return the record updated on
	 */
	public String getRecordUpdatedOn() {
		return recordUpdatedOn;
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
	 * Gets the online login status.
	 *
	 * @return the online login status
	 */
	public String getOnlineLoginStatus() {
		return onlineLoginStatus;
	}

	/**
	 * Sets the checks if is contact registered.
	 *
	 * @param isContactRegistered
	 *            the new checks if is contact registered
	 */
	public void setIsContactRegistered(Boolean isContactRegistered) {
		this.isContactRegistered = isContactRegistered;
	}

	/**
	 * Sets the update method.
	 *
	 * @param updateMethod
	 *            the new update method
	 */
	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}

	/**
	 * Sets the record updated on.
	 *
	 * @param recordUpdatedOn
	 *            the new record updated on
	 */
	public void setRecordUpdatedOn(String recordUpdatedOn) {
		this.recordUpdatedOn = recordUpdatedOn;
	}

	/**
	 * Sets the contact status.
	 *
	 * @param contactStatus
	 *            the new contact status
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

	/**
	 * Sets the online login status.
	 *
	 * @param onlineLoginStatus
	 *            the new online login status
	 */
	public void setOnlineLoginStatus(String onlineLoginStatus) {
		this.onlineLoginStatus = onlineLoginStatus;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version) {
		this.version = version;
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
	 * @param nationalIdType
	 *            the new national id type
	 */
	public void setNationalIdType(String nationalIdType) {
		this.nationalIdType = nationalIdType;
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
	 * @param lastPINIssuedDateTime
	 *            the new last PIN issued date time
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
	 * @param lastLoginFailDateTime
	 *            the new last login fail date time
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
	 * @param lastPasswordResetOn
	 *            the new last password reset on
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
	 * @param listofDevicesUsedByCustomer
	 *            the new listof devices used by customer
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
	 * @param secondContactAddedLaterOnline
	 *            the new second contact added later online
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
	 * @param lastIPAddresses
	 *            the new last IP addresses
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
	 * @param last5LoginDateTime
	 *            the new last 5 login date time
	 */
	public void setLast5LoginDateTime(String last5LoginDateTime) {
		this.last5LoginDateTime = last5LoginDateTime;
	}

	/**
	 * @return the legacyTradeContactID
	 */
	public Integer getLegacyTradeContactID() {
		return legacyTradeContactID;
	}

	/**
	 * @param legacyTradeContactID the legacyTradeContactID to set
	 */
	public void setLegacyTradeContactID(Integer legacyTradeContactID) {
		this.legacyTradeContactID = legacyTradeContactID;
	}

	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	@JsonIgnore
	public String getFullName() {
		StringBuilder name = new StringBuilder();
		if (!isNullOrEmpty(getFirstName())) {
			name.append(getFirstName());
		}
		if (!isNullOrEmpty(getMiddleName())) {
			if (name.length() > 0)
				name.append(' ').append(getMiddleName().trim());
			else
				name.append(getMiddleName().trim());
		}
		if (!isNullOrEmpty(getLastName())) {
			if (name.length() > 0)
				name.append(' ').append(getLastName().trim());
			else
				name.append(getLastName().trim());
		}
		return name.toString();
	}

	/**
	 * Gets the first and last name.
	 *
	 * @return the first and last name
	 */
	@JsonIgnore
	public String getFirstAndLastName() {
		StringBuilder name = new StringBuilder();
		if (!isNullOrEmpty(getFirstName())) {
			name.append(getFirstName());
		}
		if (!isNullOrEmpty(getLastName())) {
			if (name.length() > 0)
				name.append(' ').append(getLastName());
			else
				name.append(getLastName());
		}
		return name.toString();
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param str
	 *            the str
	 * @return true, if is null or empty
	 */
	private static boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.trim().isEmpty())
			return false;

		return result;
	}

	/**
	 * Gets the position of significance.
	 *
	 * @return the position of significance
	 */
	public String getPositionOfSignificance() {
		return positionOfSignificance;
	}

	/**
	 * Sets the position of significance.
	 *
	 * @param positionOfSignificance
	 *            the new position of significance
	 */
	public void setPositionOfSignificance(String positionOfSignificance) {
		this.positionOfSignificance = positionOfSignificance;
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
	 * @param authorisedSignatory
	 *            the new authorised signatory
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
	 * @param jobTitle
	 *            the new job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Validate signup.
	 *
	 * @param custType
	 *            the cust type
	 * @return the field validator
	 */
	public FieldValidator validateSignup(String custType) {
		FieldValidator validator;
		if (Constants.PFX.equalsIgnoreCase(custType)) {
			validator = validateContactForRegistration();
		} else {
			validator = validateCfxContactForRegistration();
		}
		if (this.contactSFID.length() != 18) {
			validator.addError("- incorrect format", Constants.CONTACT_SF_ID_LIST);
		}
		validateDates(validator);
		return validator;
	}

	/**
	 * Validate update account.
	 *
	 * @return the field validator
	 */
	public FieldValidator validateUpdateContact() {
		FieldValidator validator = new FieldValidator();
		try {
			validator.isValidObject(new Object[] { this.contactSFID }, new String[] { "contact_sf_id" });
			validator.isValidObjectOnUpdate(
					new Object[] { this.getPrimaryContact(), this.getPostCode(),
							this.getPhoneMobile(), this.getCountry(), this.getEmail() },
					new String[] { Constants.FIELD_IS_PRIMARY_CONTACT, Constants.FIELD_POSTCODE, Constants.FIELD_MOBILE_PHONE,
							Constants.FIELD_CONTACT_COUNTRY_RESIDENCE });			
			validateDates(validator);
			validateEnums(validator);
			validateCountries(validator);

		} catch (Exception e) {
			LOGGER.error("Error in validation : ", e);
		}
		return validator;
	}

	/**
	 * Validate contact for registration.
	 *
	 * @return the field validator
	 */
	private FieldValidator validateContactForRegistration() {
		FieldValidator validator = new FieldValidator();
		try {
			validator.isValidObject(
					// AT-1505 - Mobile Registration Changes
					new Object[] { this.getPrimaryContact(), this.getTradeContactID(), this.getPostCode(),
							this.getPhoneMobile(), this.country },
					new String[] { Constants.FIELD_IS_PRIMARY_CONTACT, Constants.FIELD_CONTACT_TRADE_CONTACT_ID,
							Constants.FIELD_POSTCODE, Constants.FIELD_MOBILE_PHONE,
							Constants.FIELD_CONTACT_COUNTRY_RESIDENCE });

			validateUsaState(validator);
			validateEnums(validator);
			validateCountries(validator);

		} catch (Exception e) {
			LOGGER.error(ERROR, e);
		}
		return validator;
	}

	/**
	 * Validate dates.
	 *
	 * @param validator
	 *            the validator
	 */
	private void validateDates(FieldValidator validator) {
		if (!StringUtils.isNullOrTrimEmpty(this.dob)) {
			validator.isDateInFormat(new String[] { this.dob }, new String[] { Constants.FIELD_CONTACT_DOB },
					Constants.RFC_DATE_FORMAT);
		}
		if (!StringUtils.isNullOrTrimEmpty(this.dlExpiryDate)) {
			validator.isDateInFormat(new String[] { this.dlExpiryDate },
					new String[] { Constants.FIELD_DRIVING_EXPIRY }, Constants.RFC_DATE_FORMAT);
		}
	}

	/**
	 * Validate cfx contact for registration.
	 *
	 * @return the field validator
	 */
	private FieldValidator validateCfxContactForRegistration() {
		FieldValidator validator = new FieldValidator();
		try {
			// AT-1505 - Mobile Registration Changes
			validator.isValidObject(
					new Object[] { this.getPrimaryContact(), this.getTradeContactID(), this.getPostCode(),
							this.getPhoneMobile(), this.getEmail(), country },
					new String[] { Constants.FIELD_IS_PRIMARY_CONTACT, Constants.FIELD_CONTACT_TRADE_CONTACT_ID,
							Constants.FIELD_POSTCODE, Constants.FIELD_MOBILE_PHONE, Constants.FIELD_CONTACT_EMAIL,
							Constants.FIELD_CONTACT_COUNTRY_RESIDENCE });
			validateUsaState(validator);
			validateEnums(validator);
			validateCountries(validator);

		} catch (Exception e) {
			LOGGER.error(ERROR, e);
		}

		return validator;
	}

	/**
	 * Validate usa state.
	 *
	 * @param validator
	 *            the validator
	 */
	private void validateUsaState(FieldValidator validator) {
		RegistrationEnumValues enumValues = RegistrationEnumValues.getInstance();
		if (!StringUtils.isNullOrTrimEmpty(this.getCountry()) && ("USA").equalsIgnoreCase(this.getCountry())
				&& (StringUtils.isNullOrTrimEmpty(this.getState())
						|| enumValues.checkUsaStates(this.getState()) == null)) {
			validator.addError("Incorrect USA State", "state_province_county");
		}
	}

	/**
	 * Validate enums.
	 *
	 * @param validator
	 *            the validator
	 */
	private void validateEnums(FieldValidator validator) {
		RegistrationEnumValues enumValues = RegistrationEnumValues.getInstance();
		if (!StringUtils.isNullOrTrimEmpty(this.getTitle()) && enumValues.checkTitles(this.getTitle()) == null) {
			validator.addError("Incorrect", Constants.FIELD_CONTACT_TITLE);
		}
		if (!StringUtils.isNullOrTrimEmpty(this.getGender()) && enumValues.checkGender(this.getGender()) == null) {
			validator.addError("Incorrect gender", Constants.FIELD_CONTACT_GENDER);
		}
	}

	/**
	 * Validate countries.
	 *
	 * @param validator
	 *            the validator
	 */
	private void validateCountries(FieldValidator validator) {
		validateCountry(validator, Constants.FIELD_CONTACT_COUNTRY_RESIDENCE, this.country);
		validateCountry(validator, "country_of_birth", this.countryOfBirth);
		validateCountry(validator, "driving_license_country", this.dlCountryCode);
		validateCountry(validator, "passport_country_code", this.passportCountryCode);
		validateCountry(validator, "previous_country", this.previousCountry);
	}

	/**
	 * Validate country.
	 *
	 * @param validator
	 *            the validator
	 * @param field
	 *            the field
	 * @param country
	 *            the country
	 */
	private void validateCountry(FieldValidator validator, String field, String country) {
		ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();
		try {
			if (!StringUtils.isNullOrTrimEmpty(country) && countryCache.getCountryFullName(country) == null) {
				validator.addError("Incorrect country code", field);
			}
		} catch (ComplianceException e) {
			LOGGER.error(ERROR, e);
		}

	}

	/**
	 * Gets the street type.
	 *
	 * @return the street type
	 */
	public String getStreetType() {
		return streetType;
	}

	/**
	 * Sets the street type.
	 *
	 * @param streetType
	 *            the new street type
	 */
	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}

	/**
	 * Gets the previous kyc status.
	 *
	 * @return the previous kyc status
	 */
	public String getPreviousKycStatus() {
		return previousKycStatus;
	}

	/**
	 * Sets the previous kyc status.
	 *
	 * @param previousKycStatus
	 *            the new previous kyc status
	 */
	public void setPreviousKycStatus(String previousKycStatus) {
		this.previousKycStatus = previousKycStatus;
	}

	/**
	 * Gets the previous sanction status.
	 *
	 * @return the previous sanction status
	 */
	public String getPreviousSanctionStatus() {
		return previousSanctionStatus;
	}

	/**
	 * Sets the previous sanction status.
	 *
	 * @param previousSanctionStatus
	 *            the new previous sanction status
	 */
	public void setPreviousSanctionStatus(String previousSanctionStatus) {
		this.previousSanctionStatus = previousSanctionStatus;
	}

	/**
	 * Gets the previous fraugster status.
	 *
	 * @return the previous fraugster status
	 */
	public String getPreviousFraugsterStatus() {
		return previousFraugsterStatus;
	}

	/**
	 * Sets the previous fraugster status.
	 *
	 * @param previousFraugsterStatus
	 *            the new previous fraugster status
	 */
	public void setPreviousFraugsterStatus(String previousFraugsterStatus) {
		this.previousFraugsterStatus = previousFraugsterStatus;
	}

	/**
	 * Gets the previous blacklist status.
	 *
	 * @return the previous blacklist status
	 */
	public String getPreviousBlacklistStatus() {
		return previousBlacklistStatus;
	}

	/**
	 * Sets the previous blacklist status.
	 *
	 * @param previousBlacklistStatus
	 *            the new previous blacklist status
	 */
	public void setPreviousBlacklistStatus(String previousBlacklistStatus) {
		this.previousBlacklistStatus = previousBlacklistStatus;
	}

	/**
	 * Gets the previous country global check status.
	 *
	 * @return the previous country global check status
	 */
	public String getPreviousCountryGlobalCheckStatus() {
		return previousCountryGlobalCheckStatus;
	}

	/**
	 * Sets the previous country global check status.
	 *
	 * @param previousCountryGlobalCheckStatus
	 *            the new previous country global check status
	 */
	public void setPreviousCountryGlobalCheckStatus(String previousCountryGlobalCheckStatus) {
		this.previousCountryGlobalCheckStatus = previousCountryGlobalCheckStatus;
	}

	/**
	 * Gets the previous paymentin watchlist status.
	 *
	 * @return the previous paymentin watchlist status
	 */
	public String getPreviousPaymentinWatchlistStatus() {
		return previousPaymentinWatchlistStatus;
	}

	/**
	 * Sets the previous paymentin watchlist status.
	 *
	 * @param previousPaymentinWatchlistStatus
	 *            the new previous paymentin watchlist status
	 */
	public void setPreviousPaymentinWatchlistStatus(String previousPaymentinWatchlistStatus) {
		this.previousPaymentinWatchlistStatus = previousPaymentinWatchlistStatus;
	}

	/**
	 * Gets the previous paymentout watchlist status.
	 *
	 * @return the previous paymentout watchlist status
	 */
	public String getPreviousPaymentoutWatchlistStatus() {
		return previousPaymentoutWatchlistStatus;
	}

	/**
	 * Sets the previous paymentout watchlist status.
	 *
	 * @param previousPaymentoutWatchlistStatus
	 *            the new previous paymentout watchlist status
	 */
	public void setPreviousPaymentoutWatchlistStatus(String previousPaymentoutWatchlistStatus) {
		this.previousPaymentoutWatchlistStatus = previousPaymentoutWatchlistStatus;
	}
	
	/**
	 * Update address 1.
	 *
	 * @return the string
	 */
	public String updateAddress1() {
		StringBuilder address = new StringBuilder();

		if (!isNullOrEmpty(streetNumber)) {
			address.append(streetNumber);
		}
		if (!isNullOrEmpty(street)) {
			address.append(", ").append(street);
		}
		if (!isNullOrEmpty(buildingName)) {
			address.append(", ").append(buildingName);
		}
		return address.toString();

	}

	

	@JsonIgnore
	public void setFraugsterPerformed(boolean isFraugsterPerformed) {
		this.isFraugsterPerformed = isFraugsterPerformed;
	}

	/**
	 * @return the poiNeeded
	 */
	@JsonIgnore
	public boolean isPoiNeeded() {
		return poiNeeded;
	}

	/**
	 * @param poiNeeded the poiNeeded to set
	 */
	public void setPoiNeeded(boolean poiNeeded) {
		this.poiNeeded = poiNeeded;
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
	 * Gets the poi exists.
	 *
	 * @return the poi exists
	 */
	public Integer getPoiExists() {
		return poiExists;
	}

	/**
	 * Sets the poi exists.
	 *
	 * @param poiExists the new poi exists
	 */
	public void setPoiExists(Integer poiExists) {
		this.poiExists = poiExists;
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

	public String getMailingStreet() {
		return mailingStreet;
	}

	public void setMailingStreet(String mailingStreet) {
		this.mailingStreet = mailingStreet;
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
	
}
