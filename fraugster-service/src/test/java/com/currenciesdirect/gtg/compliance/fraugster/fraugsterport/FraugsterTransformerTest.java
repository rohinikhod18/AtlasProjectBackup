package com.currenciesdirect.gtg.compliance.fraugster.fraugsterport;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterOnUpdateProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterPaymentsInProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterPaymentsOutProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSearchDataResponse;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSignupProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;

public class FraugsterTransformerTest {

	@InjectMocks
	FraugsterTransformer fraugsterTransformer;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	public FraugsterSignupContactRequest fraugsterSignupContactRequestMocking() {
		FraugsterSignupContactRequest fraugsterSignupContactRequest = new FraugsterSignupContactRequest();

		fraugsterSignupContactRequest.setOrganizationCode("Currencies Direct");
		fraugsterSignupContactRequest.setSecondEmail("bnt@gnailcom");
		fraugsterSignupContactRequest.setEventType("Profile New Registration");
		fraugsterSignupContactRequest.setCustID("4326718");
		fraugsterSignupContactRequest.setTransactionID("0201000304546748");
		fraugsterSignupContactRequest.setPostCodeLatitude(006598F);
		fraugsterSignupContactRequest.setPostCodeLongitude(006598F);
		fraugsterSignupContactRequest.setPhoneHome("0658451485632");
		fraugsterSignupContactRequest.setEventType("sign Up");
		fraugsterSignupContactRequest.setRegistrationDateTime("2020-10-02T14:03:50Z");
		fraugsterSignupContactRequest.setDeviceType("Desktop ");
		fraugsterSignupContactRequest.setDeviceName("dell");
		fraugsterSignupContactRequest.setDeviceVersion("5637");
		fraugsterSignupContactRequest.setDeviceID("487");
		fraugsterSignupContactRequest.setDeviceManufacturer("Dell inc");
		fraugsterSignupContactRequest.setOsName("Windows-10");
		fraugsterSignupContactRequest.setBrowserLanguage("En-GB");
		fraugsterSignupContactRequest.setBrowserOnline("true");
		fraugsterSignupContactRequest.setUserAgent("user");
		fraugsterSignupContactRequest.setBrowserMajorVersion("85es");
		fraugsterSignupContactRequest.setBrowserScreenResolution("1280x720 28");
		fraugsterSignupContactRequest.setBrowserType("Edge");
		fraugsterSignupContactRequest.setOsDateAndTime("2018-09-23T09:55:14Z");
		fraugsterSignupContactRequest.setAccountContactNum(256556);
		fraugsterSignupContactRequest.setTitle("Mr");
		fraugsterSignupContactRequest.setPreferredName("Iyer");
		fraugsterSignupContactRequest.setFirstName("Raj");
		fraugsterSignupContactRequest.setMiddleName("Krishna");
		fraugsterSignupContactRequest.setLastName("Iyer");
		fraugsterSignupContactRequest.setDob("2020-09-02");
		fraugsterSignupContactRequest.setAddressType("permanent");
		fraugsterSignupContactRequest.setStreet("lan 23");
		fraugsterSignupContactRequest.setCity("city");
		fraugsterSignupContactRequest.setState("state");
		fraugsterSignupContactRequest.setCountry("United kingdom");
		fraugsterSignupContactRequest.setPostCode("878859");
		fraugsterSignupContactRequest.setPhoneType("Android");
		fraugsterSignupContactRequest.setPhoneWork("7566678789");
		fraugsterSignupContactRequest.setPhoneMobile("8768886565865");
		fraugsterSignupContactRequest.setEmail("bnt@gmailcom");
		fraugsterSignupContactRequest.setPrimaryContact(true);
		fraugsterSignupContactRequest.setNationality("nationality");
		fraugsterSignupContactRequest.setOccupation("other");
		fraugsterSignupContactRequest.setPassportNumber("7y874hjk");
		fraugsterSignupContactRequest.setPassportCountryCode("333");
		fraugsterSignupContactRequest.setPassportFamilyNameAtBirth("iyer");
		fraugsterSignupContactRequest.setPassportNameAtCitizenship("Iyer ");
		fraugsterSignupContactRequest.setPassportPlaceOfBirth("67788900");
		fraugsterSignupContactRequest.setDlLicenseNumber("67778");
		fraugsterSignupContactRequest.setdLCardNumber("477847");
		fraugsterSignupContactRequest.setDlCountryCode("code");
		fraugsterSignupContactRequest.setDlStateCode("code");
		fraugsterSignupContactRequest.setDlExpiryDate("2026-09-03");
		fraugsterSignupContactRequest.setMedicareCardNumber("747878598");
		fraugsterSignupContactRequest.setMedicareReferenceNumber("47443");
		fraugsterSignupContactRequest.setMunicipalityOfBirth("1555");
		fraugsterSignupContactRequest.setSubBuildingorFlat("5555555");
		fraugsterSignupContactRequest.setBuildingNumber(55);
		fraugsterSignupContactRequest.setUnitNumber(554);
		fraugsterSignupContactRequest.setSubCity("subcity");
		fraugsterSignupContactRequest.setRegion("north");
		fraugsterSignupContactRequest.setChannel("Dione");
		fraugsterSignupContactRequest.setiPAddress("172.31.28.44");
		fraugsterSignupContactRequest.setiPLatitude(08676f);
		fraugsterSignupContactRequest.setiPLongitude(78475f);
		fraugsterSignupContactRequest.setCustomerType("pfx");
		fraugsterSignupContactRequest.setCountriesOfOperation("--");
		fraugsterSignupContactRequest.setCountryOfBirth("Norway");
		fraugsterSignupContactRequest.setSourceOfFund("Net banking");
		fraugsterSignupContactRequest.setSource("Web");
		fraugsterSignupContactRequest.setSubSource("Web");
		fraugsterSignupContactRequest.setReferral("--");
		fraugsterSignupContactRequest.setReferralText("--");
		fraugsterSignupContactRequest.setExtendedReferral("--");
		fraugsterSignupContactRequest.setAdCampaign("--");
		fraugsterSignupContactRequest.setKeywords("--");
		fraugsterSignupContactRequest.setSearchEngine("google");
		fraugsterSignupContactRequest.setWebsite("--");
		fraugsterSignupContactRequest.setBrowserType("chrome");
		fraugsterSignupContactRequest.setPhoneHome("85t9890509");
		fraugsterSignupContactRequest.setNationalId("5678");
		return fraugsterSignupContactRequest;
	}

	public FraugsterSignupContactRequest fraugsterSignupContactRequestMock() {
		FraugsterSignupContactRequest fraugsterSignupContactRequest = new FraugsterSignupContactRequest();
		fraugsterSignupContactRequest.setOrganizationCode("Currencies Direct");
		fraugsterSignupContactRequest.setSecondEmail("bnt@gnailcom");
		fraugsterSignupContactRequest.setEventType("Profile New Registration");
		fraugsterSignupContactRequest.setCustID("4326718");
		fraugsterSignupContactRequest.setTransactionID("0201000304546748");
		fraugsterSignupContactRequest.setPostCodeLatitude(006598F);
		fraugsterSignupContactRequest.setPostCodeLongitude(006598F);
		fraugsterSignupContactRequest.setPhoneHome("0658451485632");
		fraugsterSignupContactRequest.setEventType("sign Up");
		fraugsterSignupContactRequest.setRegistrationDateTime("2020-10-02T14:03:50Z");
		fraugsterSignupContactRequest.setDeviceType("Desktop ");
		fraugsterSignupContactRequest.setDeviceName("dell");
		fraugsterSignupContactRequest.setDeviceVersion("5637");
		fraugsterSignupContactRequest.setDeviceID("487");
		fraugsterSignupContactRequest.setDeviceManufacturer("Dell inc");
		fraugsterSignupContactRequest.setOsName("Windows-10");
		fraugsterSignupContactRequest.setBrowserLanguage("En-GB");
		fraugsterSignupContactRequest.setBrowserOnline("true");
		fraugsterSignupContactRequest.setUserAgent("user");
		fraugsterSignupContactRequest.setBrowserMajorVersion("85es");
		fraugsterSignupContactRequest.setBrowserScreenResolution("1280x720 28");
		fraugsterSignupContactRequest.setBrowserType("Edge");
		fraugsterSignupContactRequest.setOsDateAndTime("2018-09-23T09:55:14Z");
		fraugsterSignupContactRequest.setAccountContactNum(256556);
		fraugsterSignupContactRequest.setPreferredName("Iyer");
		fraugsterSignupContactRequest.setFirstName("Raj");
		fraugsterSignupContactRequest.setMiddleName("Krishna");
		fraugsterSignupContactRequest.setLastName("Iyer");
		fraugsterSignupContactRequest.setDob("2020-09-02");
		fraugsterSignupContactRequest.setAddressType("permanent");
		fraugsterSignupContactRequest.setStreet("lan 23");
		fraugsterSignupContactRequest.setCity("city");
		fraugsterSignupContactRequest.setState("state");
		fraugsterSignupContactRequest.setCountry("United kingdom");
		fraugsterSignupContactRequest.setPostCode("878859");
		fraugsterSignupContactRequest.setPhoneType("Android");
		fraugsterSignupContactRequest.setPhoneWork("7566678789");
		fraugsterSignupContactRequest.setPhoneMobile("8768886565865");
		fraugsterSignupContactRequest.setEmail("bnt@gmailcom");
		fraugsterSignupContactRequest.setPrimaryContact(true);
		fraugsterSignupContactRequest.setNationality("nationality");
		fraugsterSignupContactRequest.setOccupation("other");
		fraugsterSignupContactRequest.setPassportNumber("7y874hjk");
		fraugsterSignupContactRequest.setPassportCountryCode("333");
		fraugsterSignupContactRequest.setPassportFamilyNameAtBirth("iyer");
		fraugsterSignupContactRequest.setPassportNameAtCitizenship("Iyer ");
		fraugsterSignupContactRequest.setPassportPlaceOfBirth("67788900");
		fraugsterSignupContactRequest.setDlLicenseNumber("67778");
		fraugsterSignupContactRequest.setdLCardNumber("477847");
		fraugsterSignupContactRequest.setDlCountryCode("code");
		fraugsterSignupContactRequest.setDlStateCode("code");
		fraugsterSignupContactRequest.setDlExpiryDate("2026-09-03");
		fraugsterSignupContactRequest.setMedicareCardNumber("747878598");
		fraugsterSignupContactRequest.setMedicareReferenceNumber("47443");
		fraugsterSignupContactRequest.setMunicipalityOfBirth("1555");
		fraugsterSignupContactRequest.setSubBuildingorFlat("5555555");
		fraugsterSignupContactRequest.setBuildingNumber(55);
		fraugsterSignupContactRequest.setUnitNumber(554);
		fraugsterSignupContactRequest.setSubCity("subcity");
		fraugsterSignupContactRequest.setRegion("north");
		fraugsterSignupContactRequest.setChannel("Dione");
		fraugsterSignupContactRequest.setiPAddress("172.31.28.44");
		fraugsterSignupContactRequest.setiPLatitude(08676f);
		fraugsterSignupContactRequest.setiPLongitude(78475f);
		fraugsterSignupContactRequest.setCustomerType("pfx");
		fraugsterSignupContactRequest.setCountriesOfOperation("--");
		fraugsterSignupContactRequest.setCountryOfBirth("Norway");
		fraugsterSignupContactRequest.setSourceOfFund("Net banking");
		fraugsterSignupContactRequest.setSource("Web");
		fraugsterSignupContactRequest.setSubSource("Web");
		fraugsterSignupContactRequest.setReferral("--");
		fraugsterSignupContactRequest.setReferralText("--");
		fraugsterSignupContactRequest.setExtendedReferral("--");
		fraugsterSignupContactRequest.setAdCampaign("--");
		fraugsterSignupContactRequest.setKeywords("--");
		fraugsterSignupContactRequest.setSearchEngine("google");
		fraugsterSignupContactRequest.setWebsite("--");
		fraugsterSignupContactRequest.setBrowserType("chrome");
		fraugsterSignupContactRequest.setPhoneHome("85t9890509");
		fraugsterSignupContactRequest.setNationalId("5678");
		return fraugsterSignupContactRequest;
	}

	public FraugsterSignupProviderRequest getFraugsterSignupProviderRequest() {
		FraugsterSignupProviderRequest response = new FraugsterSignupProviderRequest();
		response.setOrganisationCode("Currencies Direct");
		response.setCustScndryEmail("bnt@gnailcom");
		response.setEventType("Profile New Registration");
		response.setCustId("4326718");
		response.setTransId("0201000304546748");
		response.setPostCodeLatitude(006598F);
		response.setPostCodeLongitude(006598F);
		response.setPhoneHome("0658451485632");
		response.setEventType("sign Up");
		response.setCustSignupTs("2020-10-02T14:03:50Z");
		response.setDeviceType("Desktop ");
		response.setDeviceName("dell");
		response.setDeviceVersion("5637");
		response.setDeviceId("487");
		response.setDeviceManufacturer("Dell inc");
		response.setOsType("Windows-10");
		response.setBrwsrLang("En-GB");
		response.setBrowserOnline("true");
		response.setBrowserUserAgent("user");
		response.setBrwsrVersion("85es");
		response.setScreenResolution("1280x720 28");
		response.setBrwsrType("Edge");
		response.setOsTs("2018-09-23T09:55:14Z");
		response.setAcctContactNum(256556);
		response.setCustTitle("Mr");
		response.setPreferredName("Iyer");
		response.setCustFirstName("Raj");
		response.setCustMiddleName("Krishna");
		response.setCustLastName("Iyer");
		response.setCustDOB("2020-09-02");
		response.setBillAddName("permanent");
		response.setBillAddLine1("lan 23");
		response.setBillAddCity("city");
		response.setBillAddState("state");
		response.setBillAddCtry("United kingdom");
		response.setBillAddZip("878859");
		response.setPhoneType("Android");
		response.setPhoneWork("7566678789");
		response.setScndryPhone("8768886565865");
		response.setCustEmail("bnt@gmailcom");
		response.setPrimaryContact("true");
		response.setCustNationality("nationality");
		response.setCustMF("male");
		response.setCustOccptn("other");
		response.setPassportIdNum("7y874hjk");
		response.setPassportCountryCode("333");
		response.setPassportFamilyNameAtBirth("iyer");
		response.setPassportNameAtCitizenship("Iyer ");
		response.setPassportPlaceOfBirth("67788900");
		response.setDlLicenseNumber("67778");
		response.setDlCardNumber("477847");
		response.setDlCountryCode("code");
		response.setDlStateCode("code");
		response.setDlExpiryDate("2026-09-03");
		response.setMedicareCardNumber("747878598");
		response.setMedicareReferenceNumber("47443");
		response.setMunicipalityOfBirth("1555");
		response.setSubBuildingOrFlat("5555555");
		response.setBuildingNumber(55);
		response.setUnitNumber(554);
		response.setSubCity("subcity");
		response.setBillAddCtryRgn("north");
		response.setTransChannel("Dione");
		response.setIp("172.31.28.44");
		response.setIpLat(08676f);
		response.setIpLong(78475f);
		response.setCustomerType("pfx");
		response.setCountriesOfOperation("--");
		response.setCountryOfBirth("Norway");
		response.setPymtSource("Net banking");
		response.setSource("Web");
		response.setSubSource("Web");
		response.setReferral("--");
		response.setReferralText("--");
		response.setExtendedReferral("--");
		response.setCampaign("--");
		response.setKeywords("--");
		response.setSearchEngine("google");
		response.setWebsite("--");
		response.setPhoneHome("85t9890509");
		return response;
	}

	public FraugsterOnUpdateContactRequest fraugsterOnUpdateContactRequestMocking() {
		FraugsterOnUpdateContactRequest fraugsterOnUpdateContactRequest = new FraugsterOnUpdateContactRequest();
		fraugsterOnUpdateContactRequest.setOrganizationCode("torAU");
		fraugsterOnUpdateContactRequest.setEmail("bnt@gnailcom");
		fraugsterOnUpdateContactRequest.setEventType("Profile new Registration");
		fraugsterOnUpdateContactRequest.setCustID("432918");
		fraugsterOnUpdateContactRequest.setTransactionID("0201000000304748");
		fraugsterOnUpdateContactRequest.setPostCodeLatitude(006598F);
		fraugsterOnUpdateContactRequest.setPostCodeLongitude(006598F);
		fraugsterOnUpdateContactRequest.setPhoneHome("0658451485632");
		fraugsterOnUpdateContactRequest.setEventType("sign up");
		fraugsterOnUpdateContactRequest.setRegistrationDateTime("2020-09-02T14:04:50Z");
		fraugsterOnUpdateContactRequest.setDeviceType("Desktop");
		fraugsterOnUpdateContactRequest.setDeviceName("dell");
		fraugsterOnUpdateContactRequest.setDeviceVersion("56377");
		fraugsterOnUpdateContactRequest.setDeviceID("487");
		fraugsterOnUpdateContactRequest.setDeviceManufacturer("dell inc");
		fraugsterOnUpdateContactRequest.setOsName("Windows 10");
		fraugsterOnUpdateContactRequest.setBrowserLanguage("en-GB");
		fraugsterOnUpdateContactRequest.setBrowserOnline("true");
		fraugsterOnUpdateContactRequest.setUserAgent("user");
		fraugsterOnUpdateContactRequest.setBrowserMajorVersion("856es");
		fraugsterOnUpdateContactRequest.setBrowserScreenResolution("1280x720 24");
		fraugsterOnUpdateContactRequest.setBrowserType("Edge");
		fraugsterOnUpdateContactRequest.setOsDateAndTime("2018-08-23T09:55:14Z");
		fraugsterOnUpdateContactRequest.setAccountContactNum(256556);
		fraugsterOnUpdateContactRequest.setTitle("Mr");
		fraugsterOnUpdateContactRequest.setPreferredName("Iyer");
		fraugsterOnUpdateContactRequest.setFirstName("Raj");
		fraugsterOnUpdateContactRequest.setMiddleName("Krishna");
		fraugsterOnUpdateContactRequest.setLastName("Iyer");
		fraugsterOnUpdateContactRequest.setDob("2020-09-02");
		fraugsterOnUpdateContactRequest.setAddressType("permanent");
		fraugsterOnUpdateContactRequest.setStreet("lan 23");
		fraugsterOnUpdateContactRequest.setCity("city");
		fraugsterOnUpdateContactRequest.setState("state");
		fraugsterOnUpdateContactRequest.setCountry(" United Kingdom ");
		fraugsterOnUpdateContactRequest.setPostCode("878859");
		fraugsterOnUpdateContactRequest.setPhoneType("Android");
		fraugsterOnUpdateContactRequest.setPhoneWork("7566678789");
		fraugsterOnUpdateContactRequest.setPhoneMobile("8768886565865");
		fraugsterOnUpdateContactRequest.setEmail("bnt@gmailcom");
		fraugsterOnUpdateContactRequest.setPrimaryContact(true);
		fraugsterOnUpdateContactRequest.setNationality("nationality");
		fraugsterOnUpdateContactRequest.setGender("male");
		fraugsterOnUpdateContactRequest.setOccupation("other");
		fraugsterOnUpdateContactRequest.setPassportNumber("7y874hjk");
		fraugsterOnUpdateContactRequest.setPassportCountryCode("333");
		fraugsterOnUpdateContactRequest.setPassportFamilyNameAtBirth("iyer");
		fraugsterOnUpdateContactRequest.setPassportNameAtCitizenship("Iyer ");
		fraugsterOnUpdateContactRequest.setPassportPlaceOfBirth("67788900");
		fraugsterOnUpdateContactRequest.setDlLicenseNumber("67778");
		fraugsterOnUpdateContactRequest.setdLCardNumber("477847");
		fraugsterOnUpdateContactRequest.setDlCountryCode("code");
		fraugsterOnUpdateContactRequest.setDlStateCode("code");
		fraugsterOnUpdateContactRequest.setDlExpiryDate("2026-09-03");
		fraugsterOnUpdateContactRequest.setMedicareCardNumber("747878598");
		fraugsterOnUpdateContactRequest.setMedicareReferenceNumber("47443");
		fraugsterOnUpdateContactRequest.setMunicipalityOfBirth("1555");
		fraugsterOnUpdateContactRequest.setSubBuildingorFlat("5555555");
		fraugsterOnUpdateContactRequest.setBuildingNumber(55);
		fraugsterOnUpdateContactRequest.setUnitNumber(554);
		fraugsterOnUpdateContactRequest.setSubCity("subcity");
		fraugsterOnUpdateContactRequest.setRegion("north");
		fraugsterOnUpdateContactRequest.setChannel("Dione");
		fraugsterOnUpdateContactRequest.setiPAddress("172.31.22.4");
		fraugsterOnUpdateContactRequest.setiPLatitude(08676f);
		fraugsterOnUpdateContactRequest.setiPLongitude(78475f);
		fraugsterOnUpdateContactRequest.setCustomerType("pfx");
		fraugsterOnUpdateContactRequest.setCountriesOfOperation("--");
		fraugsterOnUpdateContactRequest.setCountryOfBirth("united Kingdom");
		fraugsterOnUpdateContactRequest.setSourceOfFund("Net banking");
		fraugsterOnUpdateContactRequest.setSource("Web");
		fraugsterOnUpdateContactRequest.setSubSource("Web");
		fraugsterOnUpdateContactRequest.setReferral("--");
		fraugsterOnUpdateContactRequest.setReferralText("--");
		fraugsterOnUpdateContactRequest.setExtendedReferral("--");
		fraugsterOnUpdateContactRequest.setAdCampaign("--");
		fraugsterOnUpdateContactRequest.setKeywords("--");
		fraugsterOnUpdateContactRequest.setSearchEngine("google");
		fraugsterOnUpdateContactRequest.setWebsite("--");
		fraugsterOnUpdateContactRequest.setBrowserType("chrome");
		fraugsterOnUpdateContactRequest.setPhoneHome("85t9890509");
		fraugsterOnUpdateContactRequest.setNationalId("5678");
		return fraugsterOnUpdateContactRequest;
	}

	public FraugsterOnUpdateContactRequest fraugsterOnUpdateContactRequestMock() {
		FraugsterOnUpdateContactRequest fraugsterOnUpdateContactRequest = new FraugsterOnUpdateContactRequest();
		fraugsterOnUpdateContactRequest.setOrganizationCode("torAU");
		fraugsterOnUpdateContactRequest.setEmail("bnt@gnailcom");
		fraugsterOnUpdateContactRequest.setEventType("Profile new Registration");
		fraugsterOnUpdateContactRequest.setCustID("432918");
		fraugsterOnUpdateContactRequest.setTransactionID("0201000000304748");
		fraugsterOnUpdateContactRequest.setPostCodeLatitude(006598F);
		fraugsterOnUpdateContactRequest.setPostCodeLongitude(006598F);
		fraugsterOnUpdateContactRequest.setPhoneHome("0658451485632");
		fraugsterOnUpdateContactRequest.setEventType("sign up");
		fraugsterOnUpdateContactRequest.setRegistrationDateTime("2020-09-02T14:04:50Z");
		fraugsterOnUpdateContactRequest.setDeviceType("Desktop");
		fraugsterOnUpdateContactRequest.setDeviceName("dell");
		fraugsterOnUpdateContactRequest.setDeviceVersion("56377");
		fraugsterOnUpdateContactRequest.setDeviceID("487");
		fraugsterOnUpdateContactRequest.setDeviceManufacturer("dell inc");
		fraugsterOnUpdateContactRequest.setOsName("Windows 10");
		fraugsterOnUpdateContactRequest.setBrowserLanguage("en-GB");
		fraugsterOnUpdateContactRequest.setBrowserOnline("true");
		fraugsterOnUpdateContactRequest.setUserAgent("user");
		fraugsterOnUpdateContactRequest.setBrowserMajorVersion("856es");
		fraugsterOnUpdateContactRequest.setBrowserScreenResolution("1280x720 24");
		fraugsterOnUpdateContactRequest.setBrowserType("Edge");
		fraugsterOnUpdateContactRequest.setOsDateAndTime("2018-08-23T09:55:14Z");
		fraugsterOnUpdateContactRequest.setAccountContactNum(256556);
		fraugsterOnUpdateContactRequest.setPreferredName("Iyer");
		fraugsterOnUpdateContactRequest.setFirstName("Raj");
		fraugsterOnUpdateContactRequest.setMiddleName("Krishna");
		fraugsterOnUpdateContactRequest.setLastName("Iyer");
		fraugsterOnUpdateContactRequest.setDob("2020-09-02");
		fraugsterOnUpdateContactRequest.setAddressType("permanent");
		fraugsterOnUpdateContactRequest.setStreet("lan 23");
		fraugsterOnUpdateContactRequest.setCity("city");
		fraugsterOnUpdateContactRequest.setState("state");
		fraugsterOnUpdateContactRequest.setCountry(" United Kingdom ");
		fraugsterOnUpdateContactRequest.setPostCode("878859");
		fraugsterOnUpdateContactRequest.setPhoneType("Android");
		fraugsterOnUpdateContactRequest.setPhoneWork("7566678789");
		fraugsterOnUpdateContactRequest.setPhoneMobile("8768886565865");
		fraugsterOnUpdateContactRequest.setEmail("bnt@gmailcom");
		fraugsterOnUpdateContactRequest.setPrimaryContact(true);
		fraugsterOnUpdateContactRequest.setNationality("nationality");
		fraugsterOnUpdateContactRequest.setOccupation("other");
		fraugsterOnUpdateContactRequest.setPassportNumber("7y874hjk");
		fraugsterOnUpdateContactRequest.setPassportCountryCode("333");
		fraugsterOnUpdateContactRequest.setPassportFamilyNameAtBirth("iyer");
		fraugsterOnUpdateContactRequest.setPassportNameAtCitizenship("Iyer ");
		fraugsterOnUpdateContactRequest.setPassportPlaceOfBirth("67788900");
		fraugsterOnUpdateContactRequest.setDlLicenseNumber("67778");
		fraugsterOnUpdateContactRequest.setdLCardNumber("477847");
		fraugsterOnUpdateContactRequest.setDlCountryCode("code");
		fraugsterOnUpdateContactRequest.setDlStateCode("code");
		fraugsterOnUpdateContactRequest.setDlExpiryDate("2026-09-03");
		fraugsterOnUpdateContactRequest.setMedicareCardNumber("747878598");
		fraugsterOnUpdateContactRequest.setMedicareReferenceNumber("47443");
		fraugsterOnUpdateContactRequest.setMunicipalityOfBirth("1555");
		fraugsterOnUpdateContactRequest.setSubBuildingorFlat("5555555");
		fraugsterOnUpdateContactRequest.setBuildingNumber(55);
		fraugsterOnUpdateContactRequest.setUnitNumber(554);
		fraugsterOnUpdateContactRequest.setSubCity("subcity");
		fraugsterOnUpdateContactRequest.setRegion("north");
		fraugsterOnUpdateContactRequest.setChannel("Dione");
		fraugsterOnUpdateContactRequest.setiPAddress("172.31.22.4");
		fraugsterOnUpdateContactRequest.setiPLatitude(08676f);
		fraugsterOnUpdateContactRequest.setiPLongitude(78475f);
		fraugsterOnUpdateContactRequest.setCustomerType("pfx");
		fraugsterOnUpdateContactRequest.setCountriesOfOperation("--");
		fraugsterOnUpdateContactRequest.setCountryOfBirth("united Kingdom");
		fraugsterOnUpdateContactRequest.setSourceOfFund("Net banking");
		fraugsterOnUpdateContactRequest.setSource("Web");
		fraugsterOnUpdateContactRequest.setSubSource("Web");
		fraugsterOnUpdateContactRequest.setReferral("--");
		fraugsterOnUpdateContactRequest.setReferralText("--");
		fraugsterOnUpdateContactRequest.setExtendedReferral("--");
		fraugsterOnUpdateContactRequest.setAdCampaign("--");
		fraugsterOnUpdateContactRequest.setKeywords("--");
		fraugsterOnUpdateContactRequest.setSearchEngine("google");
		fraugsterOnUpdateContactRequest.setWebsite("--");
		fraugsterOnUpdateContactRequest.setBrowserType("chrome");
		fraugsterOnUpdateContactRequest.setPhoneHome("85t9890509");
		fraugsterOnUpdateContactRequest.setNationalId("5678");
		return fraugsterOnUpdateContactRequest;
	}

	public FraugsterOnUpdateProviderRequest getFraugsterOnUpdateProviderRequest() {
		FraugsterOnUpdateProviderRequest response = new FraugsterOnUpdateProviderRequest();
		response.setOrganisationCode("torAU");
		response.setCustEmail("bnt@gnailcom");
		response.setEventType("Profile new Registration");
		response.setCustId("432918");
		response.setTransId("0201000000304748");
		response.setPhoneHome("0658451485632");
		response.setEventType("sign up");
		response.setCustSignupTs("2020-09-02T14:04:50Z");
		response.setDeviceType("Desktop");
		response.setDeviceName("dell");
		response.setDeviceVersion("56377");
		response.setDeviceId("487");
		response.setDeviceManufacturer("dell inc");
		response.setOsType("Windows 10");
		response.setBrwsrLang("en-GB");
		response.setBrowserOnline("true");
		response.setBrowserUserAgent("user");
		response.setBrwsrVersion("856es");
		response.setScreenResolution("1280x720 24");
		response.setBrwsrType("Edge");
		response.setOsTs("2018-08-23T09:55:14Z");
		response.setAcctContactNum(256556);
		response.setCustTitle("Mr");
		response.setPreferredName("Iyer");
		response.setCustFirstName("Raj");
		response.setCustMiddleName("Krishna");
		response.setCustLastName("Iyer");
		response.setCustDOB("2020-09-02");
		response.setBillAddName("permanent");
		response.setBillAddLine1("lan 23");
		response.setBillAddCity("city");
		response.setBillAddState("state");
		response.setBillAddCtry(" United Kingdom ");
		response.setBillAddZip("878859");
		response.setPhoneType("Android");
		response.setPhoneWork("7566678789");
		response.setScndryPhone("8768886565865");
		response.setCustEmail("bnt@gmailcom");
		response.setPrimaryContact("true");
		response.setCustNationality("nationality");
		response.setCustMF("male");
		response.setCustOccptn("other");
		response.setPassportIdNum("7y874hjk");
		response.setPassportCountryCode("333");
		response.setPassportFamilyNameAtBirth("iyer");
		response.setPassportNameAtCitizenship("Iyer ");
		response.setPassportPlaceOfBirth("67788900");
		response.setDlLicenseNumber("67778");
		response.setDlCardNumber("477847");
		response.setDlCountryCode("code");
		response.setDlStateCode("code");
		response.setDlExpiryDate("2026-09-03");
		response.setMedicareCardNumber("747878598");
		response.setMedicareReferenceNumber("47443");
		response.setMunicipalityOfBirth("1555");
		response.setSubBuildingOrFlat("5555555");
		response.setBuildingNumber(55);
		response.setUnitNumber(554);
		response.setSubCity("subcity");
		response.setBillAddCtryRgn("north");
		response.setTransChannel("Dione");
		response.setIp("172.31.22.4");
		response.setIpLat(08676f);
		response.setIpLong(78475f);
		response.setCustomerType("pfx");
		response.setCountriesOfOperation("--");
		response.setCountryOfBirth("united Kingdom");
		response.setPymtSource("Net banking");
		response.setSource("Web");
		response.setSubSource("Web");
		response.setReferral("--");
		response.setReferralText("--");
		response.setExtendedReferral("--");
		response.setCampaign("--");
		response.setKeywords("--");
		response.setSearchEngine("google");
		response.setWebsite("--");
		response.setPhoneHome("85t9890509");

		return response;
	}

	public FraugsterPaymentsOutContactRequest fraugsterPaymentsOutContactRequestMocking() {
		FraugsterPaymentsOutContactRequest fraugsterPaymentsOutContactRequest = new FraugsterPaymentsOutContactRequest();

		fraugsterPaymentsOutContactRequest.setOrganizationCode("torFx");
		fraugsterPaymentsOutContactRequest.setEventType("Profile new Registration");
		fraugsterPaymentsOutContactRequest.setCustID("432918");
		fraugsterPaymentsOutContactRequest.setTransactionID("0201000000304748");
		fraugsterPaymentsOutContactRequest.setEventType("sign up");
		fraugsterPaymentsOutContactRequest.setRegistrationDateTime("2020-09-02T14:03:50Z");
		fraugsterPaymentsOutContactRequest.setDeviceType("Desktop");
		fraugsterPaymentsOutContactRequest.setDeviceName("dell");
		fraugsterPaymentsOutContactRequest.setDeviceVersion("56377");
		fraugsterPaymentsOutContactRequest.setDeviceID("487");
		fraugsterPaymentsOutContactRequest.setDeviceManufacturer("dell inc");
		fraugsterPaymentsOutContactRequest.setOsName("Windows 10");
		fraugsterPaymentsOutContactRequest.setBrowserLanguage("en-GB");
		fraugsterPaymentsOutContactRequest.setBrowserOnline("true");
		fraugsterPaymentsOutContactRequest.setUserAgent("user");
		fraugsterPaymentsOutContactRequest.setBrowserMajorVersion("856es");
		fraugsterPaymentsOutContactRequest.setBrowserScreenResolution("1280x720 24");
		fraugsterPaymentsOutContactRequest.setBrowserType("Edge");
		fraugsterPaymentsOutContactRequest.setOsDateAndTime("2018-08-23T09:55:14Z");
		fraugsterPaymentsOutContactRequest.setAccountWithInstitution("--");
		fraugsterPaymentsOutContactRequest.setCreditorAgentBICCodeOrSortCode("--");
		fraugsterPaymentsOutContactRequest.setBeneficiaryAccountName("george");
		fraugsterPaymentsOutContactRequest.setBeneficiaryAccountNumber("588697908677578");
		fraugsterPaymentsOutContactRequest.setBeneficiaryBankAddress("gg inc po");
		fraugsterPaymentsOutContactRequest.setBeneficiaryBankName("barcl");
		fraugsterPaymentsOutContactRequest.setBeneficiaryCountry("United Kingdom");
		fraugsterPaymentsOutContactRequest.setBeneficiaryCurrency("GBP");
		fraugsterPaymentsOutContactRequest.setBeneficiaryEmail("george@bnt-soft.com");
		fraugsterPaymentsOutContactRequest.setBeneficiaryFirstName("smith");
		fraugsterPaymentsOutContactRequest.setBeneficiaryLastName("john");
		fraugsterPaymentsOutContactRequest.setBeneficiaryPhone("676788798");
		fraugsterPaymentsOutContactRequest.setBeneficiarySwift("--");
		fraugsterPaymentsOutContactRequest.setCustomerType("pfx");
		fraugsterPaymentsOutContactRequest.setIntermediaryInstitution("--");
		fraugsterPaymentsOutContactRequest.setoPICountry("--");
		fraugsterPaymentsOutContactRequest.setoPICreatedDate("172.31.22.4");
		fraugsterPaymentsOutContactRequest.setOrderingInstitution("--");
		fraugsterPaymentsOutContactRequest.setPaymentCountry("United Kingdom");
		fraugsterPaymentsOutContactRequest.setRemittanceInformation("--");
		fraugsterPaymentsOutContactRequest.setSenderCorrespondent("--");
		fraugsterPaymentsOutContactRequest.setTransactionDateTime("2020-11-02");
		fraugsterPaymentsOutContactRequest.setValueDate("2020-10-03");
		fraugsterPaymentsOutContactRequest.setDealId("78987");
		return fraugsterPaymentsOutContactRequest;
	}

	public FraugsterPaymentsOutProviderRequest getFraugsterPaymentsOutProviderRequest() {
		FraugsterPaymentsOutProviderRequest response = new FraugsterPaymentsOutProviderRequest();
		response.setOrganisationCode("torFx");
		response.setEventType("Profile new Registration");
		response.setCustId("432918");
		response.setTransId("0201000000304748");
		response.setEventType("sign up");
		response.setCustSignupTs("2020-09-02T14:03:50Z");
		response.setDeviceType("Desktop");
		response.setDeviceName("dell");
		response.setDeviceVersion("56377");
		response.setDeviceId("487");
		response.setDeviceManufacturer("dell inc");
		response.setOsType("Windows 10");
		response.setBrwsrLang("en-GB");
		response.setBrowserOnline("true");
		response.setBrowserUserAgent("user");
		response.setBrwsrVersion("856es");
		response.setScreenResolution("1280x720 24");
		response.setBrwsrType("Edge");
		response.setOsTs("2018-08-23T09:55:14Z");
		response.setAccountWithInstitution("--");
		response.setBaBic("--");
		response.setBeneficiaryAccountName("george");
		response.setBeneficiaryAccountNumber("588697908677578");
		response.setBeneficiaryBankAddress("gg inc po");
		response.setBeneficiaryBankName("barcl");
		response.setBeneficiaryCountry("United Kingdom");
		response.setBeneficiaryCurrency("GBP");
		response.setBeneficiaryEmail("george@bnt-soft.com");
		response.setBeneficiaryFirstName("smith");
		response.setBeneficiaryLastName("john");
		response.setBeneficiaryPhone("676788798");
		response.setBeneficiarySwift("--");
		response.setCustomerType("pfx");
		response.setIntermediaryInstitution("--");
		response.setOpiCountry("--");
		response.setOpiCreatedDate("172.31.22.4");
		response.setOrderingInstitution("--");
		response.setPaymentCountry("United Kingdom");
		response.setRemittanceInfo("--");
		response.setSenderCorrespondent("--");
		response.setTransTs("2020-11-02");
		response.setValueDate("2020-10-03");
		response.setDealId("78987");
		return response;
	}

	public FraugsterPaymentsInContactRequest fraugsterPaymentsInContactRequestMocking() {
		FraugsterPaymentsInContactRequest fraugsterPaymentsInContactRequest = new FraugsterPaymentsInContactRequest();
		fraugsterPaymentsInContactRequest.setAccountIdentification("--");
		fraugsterPaymentsInContactRequest.setaVTradeValue("--");
		fraugsterPaymentsInContactRequest.setaVSResult("--");
		fraugsterPaymentsInContactRequest.setAddressOnCard("uk jn lane");
		fraugsterPaymentsInContactRequest.setCustomerAddressOrPostcode("uk jn lane");
		fraugsterPaymentsInContactRequest.setNameOnCard("George");
		fraugsterPaymentsInContactRequest.setChequeClearanceDate("2020-10-02");
		fraugsterPaymentsInContactRequest.setDebitCardAddedDate("2020-10-02");
		fraugsterPaymentsInContactRequest.setThreeDStatus("--");
		fraugsterPaymentsInContactRequest.setPaymentType("online");
		fraugsterPaymentsInContactRequest.setThirdPartyPayment("yes");
		fraugsterPaymentsInContactRequest.setDateAndTime("2020-09-02T14:03:50Z");
		fraugsterPaymentsInContactRequest.setTransactionReference("--");
		fraugsterPaymentsInContactRequest.setCustomerFirstName("George");
		fraugsterPaymentsInContactRequest.setCustomerLastName("--");
		fraugsterPaymentsInContactRequest.setOrganizationCode("torFx");
		fraugsterPaymentsInContactRequest.setTransactionAmount(500.00f);
		fraugsterPaymentsInContactRequest.setCurrency("GBP");
		fraugsterPaymentsInContactRequest.setCustomerAccountNumber("458969879756656");
		fraugsterPaymentsInContactRequest.setReason("Personal");
		fraugsterPaymentsInContactRequest.setCustSignupScore(6775f);
		fraugsterPaymentsInContactRequest.setRegistrationDateTime("2017-04-04 04:42:25");
		fraugsterPaymentsInContactRequest.setEventType("PROFILE_NEW_REGISTRATION");
		fraugsterPaymentsInContactRequest.setCustID("701881");
		fraugsterPaymentsInContactRequest.setTransactionID("3456");
		fraugsterPaymentsInContactRequest.setUserAgent("");
		fraugsterPaymentsInContactRequest.setBrowserScreenResolution("750x1334");
		fraugsterPaymentsInContactRequest.setBrowserName("");
		fraugsterPaymentsInContactRequest.setBrowserMajorVersion("");
		fraugsterPaymentsInContactRequest.setDeviceType("iPhone");
		fraugsterPaymentsInContactRequest.setDeviceName("BNT SOFT iPhone");
		fraugsterPaymentsInContactRequest.setDeviceVersion("12.1.4");
		fraugsterPaymentsInContactRequest.setDeviceID("950AA01C-0B9C-4B37-BB39-847FA9CF5C21");
		fraugsterPaymentsInContactRequest.setDeviceManufacturer("Apple");
		fraugsterPaymentsInContactRequest.setOsName("iOS");
		fraugsterPaymentsInContactRequest.setBrowserLanguage("");
		fraugsterPaymentsInContactRequest.setBrowserOnline("");
		fraugsterPaymentsInContactRequest.setOsDateAndTime("2019-05-23T16:10:55Z");
		return fraugsterPaymentsInContactRequest;
	}

	public FraugsterPaymentsInProviderRequest getFraugsterPaymentsInProviderRequest() {
		FraugsterPaymentsInProviderRequest response = new FraugsterPaymentsInProviderRequest();
		response.setAccountIdentification("--");
		response.setAvTradeValue("--");
		response.setAvsResult("--");
		response.setBillAdLine1("uk jn lane");
		response.setBillAdZip("uk jn lane");
		response.setCcFirstName("George");
		response.setChequeClearanceDate("2020-10-02");
		response.setDebitCardAddedDate("2020-10-02");
		response.setIsThreeds("--");
		response.setPmtMethod("online");
		response.setThirdPartyPayment("yes");
		response.setTransTs("2020-09-02T14:03:50Z");
		response.setTransactionReference("--");
		response.setCust_first_name("George");
		response.setCust_last_name("--");
		response.setOrganisationCode("torFx");
		response.setTrans_amt(500.00f);
		response.setTrans_currency("GBP");
		response.setCustomer_account_number("458969879756656");
		response.setTrfr_reason("Personal");
		response.setCust_signup_score(6775f);
		response.setEventType("PROFILE_NEW_REGISTRATION");
		response.setCustId("701881");
		response.setTransId("3456");
		response.setBrowserUserAgent("");
		response.setScreenResolution("750x1334");
		response.setBrwsrType("");
		response.setBrwsrVersion("");
		response.setDeviceType("iPhone");
		response.setDeviceName("BNT SOFT iPhone");
		response.setDeviceVersion("12.1.4");
		response.setDeviceId("950AA01C-0B9C-4B37-BB39-847FA9CF5C21");
		response.setDeviceManufacturer("Apple");
		response.setOsType("iOS");
		response.setBrwsrLang("");
		response.setBrowserOnline("");
		response.setOsTs("2019-05-23T16:10:55Z");
		return response;
	}

	public FraugsterSignupContactResponse fraudDetectionResponseMocking() {
		FraugsterSignupContactResponse fraudDetectionResponse = new FraugsterSignupContactResponse();
		fraudDetectionResponse.setStatus("PASS");
		fraudDetectionResponse.setScore("58888");
		fraudDetectionResponse.setErrorDescription("---");
		fraudDetectionResponse.setErrorCode("--");
		fraudDetectionResponse.setFrgTransId("");
		fraudDetectionResponse.setFraugsterApproved("0.1");
		List<String> investigationPoints = new ArrayList<>();
		investigationPoints.add("7858");
		fraudDetectionResponse.setInvestigationPoints(investigationPoints);
		return fraudDetectionResponse;
	}

	public FraugsterSearchDataResponse fraugsterSearchDataResponseMocking() {
		FraugsterSearchDataResponse response = new FraugsterSearchDataResponse();
		response.setStatus("PASS");
		response.setScore("58888");
		response.setErrorDescription("---");
		response.setErrorCode("--");
		response.setFrgTransId("15b39be7-7ff4-463c-9369-08da61d14eef");
		response.setFraugsterApproved("0.1");
		response.setErrorMsg("---");
		List<String> investigationPoints = new ArrayList<>();
		investigationPoints.add("7858");
		response.setInvestigationPoints(investigationPoints);
		return response;
	}

	public FraugsterSignupContactResponse getFraugsterSignupContactResponse() {
		FraugsterSignupContactResponse response = new FraugsterSignupContactResponse();
		response.setScore("58888");
		response.setFrgTransId("15b39be7-7ff4-463c-9369-08da61d14eef");
		response.setFraugsterApproved("0.1");
		response.setErrorMsg("---");
		List<String> investigationPoints = new ArrayList<>();
		investigationPoints.add("7858");
		response.setInvestigationPoints(investigationPoints);
		return response;

	}

	public FraugsterOnUpdateContactResponse getFraugsterOnUpdateContactResponse() {
		FraugsterOnUpdateContactResponse response = new FraugsterOnUpdateContactResponse();
		response.setScore("58888");
		response.setErrorDescription("---");
		response.setErrorCode("--");
		response.setFrgTransId("15b39be7-7ff4-463c-9369-08da61d14eef");
		response.setFraugsterApproved("0.1");
		response.setErrorMsg("---");
		List<String> investigationPoints = new ArrayList<>();
		investigationPoints.add("7858");
		response.setInvestigationPoints(investigationPoints);
		return response;
	}
	// test cases

	@Test
	public void testTransformNewSignupRequest() {
		try {
			FraugsterSignupProviderRequest expectedResult = getFraugsterSignupProviderRequest();
			FraugsterSignupProviderRequest actualResult = fraugsterTransformer
					.transformNewSignupRequest(fraugsterSignupContactRequestMocking());
			assertEquals(expectedResult.getBillAddCity(), actualResult.getBillAddCity());
		} catch (FraugsterException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForTransformNewSignupRequest() {
		try {
			fraugsterTransformer.transformNewSignupRequest(fraugsterSignupContactRequestMock());
		} catch (FraugsterException e) {
			assertEquals(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testTransformNewSignupResponse() {
		try {
			FraugsterSignupContactResponse expectedResult = getFraugsterSignupContactResponse();
			FraugsterSignupContactResponse actualResult = fraugsterTransformer
					.transformNewSignupResponse(fraugsterSearchDataResponseMocking());
			assertEquals(expectedResult.getScore(), actualResult.getScore());
		} catch (FraugsterException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testTransformOnUpdateRequest() {
		try {
			FraugsterOnUpdateProviderRequest expectedResult = getFraugsterOnUpdateProviderRequest();
			FraugsterOnUpdateProviderRequest actualResult = fraugsterTransformer
					.transformOnUpdateRequest(fraugsterOnUpdateContactRequestMocking());
			assertEquals(expectedResult.getAcctContactNum(), actualResult.getAcctContactNum());
		} catch (FraugsterException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForTransformOnUpdateRequest() {
		try {
			fraugsterTransformer.transformOnUpdateRequest(fraugsterOnUpdateContactRequestMock());
		} catch (FraugsterException e) {
			assertEquals(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testTransformOnUpdateResponse() {
		try {
			FraugsterOnUpdateContactResponse expectedResult = getFraugsterOnUpdateContactResponse();
			FraugsterOnUpdateContactResponse actualresult = fraugsterTransformer
					.transformOnUpdateResponse(fraugsterSearchDataResponseMocking());
			assertEquals(expectedResult.getScore(), actualresult.getScore());
		} catch (FraugsterException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForTransformOnUpdateResponse() {
		try {
			FraugsterSearchDataResponse response = null;
			fraugsterTransformer.transformOnUpdateResponse(response);
		} catch (FraugsterException e) {
			assertEquals(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testTransformPaymentsOutRequest() {
		try {
			FraugsterPaymentsOutProviderRequest expectedResult = getFraugsterPaymentsOutProviderRequest();
			FraugsterPaymentsOutProviderRequest actualResult = fraugsterTransformer
					.transformPaymentsOutRequest(fraugsterPaymentsOutContactRequestMocking());
			assertEquals(expectedResult.getBeneficiaryCountry(), actualResult.getBeneficiaryCountry());
		} catch (FraugsterException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForTransformPaymentsOutRequest() {
		try {
			FraugsterPaymentsOutContactRequest response = null;
			fraugsterTransformer.transformPaymentsOutRequest(response);
		} catch (FraugsterException e) {
			assertEquals(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION.getErrorCode(), e.getMessage());
		}
	}

	public FraugsterPaymentsOutContactResponse getFraugsterPaymentsOutContactResponse() {
		FraugsterPaymentsOutContactResponse response = new FraugsterPaymentsOutContactResponse();
		response.setScore("58888");
		response.setFrgTransId("15b39be7-7ff4-463c-9369-08da61d14eef");
		response.setFraugsterApproved("0.1");
		response.setErrorMsg("---");
		List<String> investigationPoints = new ArrayList<>();
		investigationPoints.add("7858");
		response.setInvestigationPoints(investigationPoints);
		return response;

	}

	@Test
	public void testTransformPaymentsOutResponse() {
		FraugsterPaymentsOutContactResponse expectedResult = getFraugsterPaymentsOutContactResponse();
		try {
			FraugsterPaymentsOutContactResponse actualResult = fraugsterTransformer
					.transformPaymentsOutResponse(fraugsterSearchDataResponseMocking());
			assertEquals(expectedResult.getScore(), actualResult.getScore());
		} catch (FraugsterException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForTransformPaymentsOutResponse() {
		try {
			FraugsterSearchDataResponse response = null;
			fraugsterTransformer.transformPaymentsOutResponse(response);
		} catch (FraugsterException e) {
			assertEquals(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testTransformPaymentsInRequest() {
		try {
			FraugsterPaymentsInProviderRequest expectedResult = getFraugsterPaymentsInProviderRequest();
			FraugsterPaymentsInProviderRequest actualResult = fraugsterTransformer
					.transformPaymentsInRequest(fraugsterPaymentsInContactRequestMocking());
			assertEquals(expectedResult.getCust_first_name(), actualResult.getCust_first_name());
		} catch (FraugsterException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForTransformPaymentsInRequest() {
		try {
			FraugsterPaymentsInContactRequest response = null;
			fraugsterTransformer.transformPaymentsInRequest(response);
		} catch (FraugsterException e) {
			assertEquals(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION.getErrorCode(), e.getMessage());
		}
	}

	public FraugsterPaymentsInContactResponse getFraugsterPaymentsInContactResponse() {
		FraugsterPaymentsInContactResponse response = new FraugsterPaymentsInContactResponse();
		response.setStatus("PASS");
		response.setScore("58888");
		response.setErrorDescription("---");
		response.setErrorCode("--");
		response.setFrgTransId("15b39be7-7ff4-463c-9369-08da61d14eef");
		response.setFraugsterApproved("0.1");
		response.setErrorMsg("---");
		List<String> investigationPoints = new ArrayList<>();
		investigationPoints.add("7858");
		response.setInvestigationPoints(investigationPoints);
		return response;
	}

	@Test
	public void testTransformPaymentsInResponse() {
		FraugsterPaymentsInContactResponse expectedResult = getFraugsterPaymentsInContactResponse();
		try {
			FraugsterPaymentsInContactResponse actualResult = fraugsterTransformer
					.transformPaymentsInResponse(fraugsterSearchDataResponseMocking());
			assertEquals(expectedResult.getScore(), actualResult.getScore());
		} catch (FraugsterException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForTransformPaymentsInResponse() {
		try {
			FraugsterSearchDataResponse response = null;
			fraugsterTransformer.transformPaymentsInResponse(response);
		} catch (FraugsterException e) {
			assertEquals(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION.getErrorCode(), e.getMessage());
		}
	}

}