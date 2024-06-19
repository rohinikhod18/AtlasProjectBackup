package com.currenciesdirect.gtg.compliance.transformer;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.core.domain.AccountWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.Blacklist;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.CountryCheck;
import com.currenciesdirect.gtg.compliance.core.domain.CustomCheck;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.GlobalCheck;
import com.currenciesdirect.gtg.compliance.core.domain.InternalRule;
import com.currenciesdirect.gtg.compliance.core.domain.IpCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Kyc;
import com.currenciesdirect.gtg.compliance.core.domain.Onfido;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReason;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonData;
import com.currenciesdirect.gtg.compliance.core.domain.UploadDocumentTypeDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDto;

public class RegistrationDetailsTransformerTest {

	@InjectMocks
	RegistrationDetailsTransformer registrationDetailsTransformer;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private RegistrationDetailsDBDto getRegistrationDetailsDBDto() {
		RegistrationDetailsDBDto registrationDetailsDBDto = new RegistrationDetailsDBDto();

		registrationDetailsDBDto.setAccComplianceStatus("INACTIVE");
		registrationDetailsDBDto.setAccountAttrib("{\"onQueue\":false,\"acc_sf_id\":\"0016E000020efr0DYO\","
				+ "\"trade_acc_id\":10026322,\"trade_acc_number\":\"0201040010026102\",\"branch\":\"USA\","
				+ "\"channel\":\"ANDROIDAPP\",\"cust_type\":\"PFX\",\"act_name\":\"AYSHA SADDIQ\",\"country_interest\":"
				+ "\"\",\"trade_name\":\"\",\"trasaction_purpose\":\"\",\"op_country\":\"\",\"turnover\""
				+ ":\"\",\"service_req\":\"Transfer\",\"buying_currency\":\"EUR\",\"selling_currency\":"
				+ "\"GBP\",\"txn_value\":\"2,000 - 5,000\",\"source_of_fund\":\"\",\"source\":\"Web\","
				+ "\"sub_source\":\"Natural\",\"referral\":\"\",\"referral_text\":\"Natural\",\"extended_referral\":"
				+ "\"\",\"ad_campaign\":\"\",\"keywords\":\"\",\"search_engine\":\"\",\"website\":\"\","
				+ "\"affiliate_name\":\"Marketing USA\",\"reg_mode\":\"Mobile\",\"organization_legal_entity\":"
				+ "\"CDLEU\",\"version\":1,\"reg_date_time\":\"2019-12-22T13:51:33Z\",\"company\":{\"billing_address\":"
				+ "\"Sørliveien 38,Lørenskog,Akershus,1473,Norway\",\"company_phone\":\"\",\"shipping_address\":"
				+ "\"\",\"vat_no\":\"\",\"country_region\":\"\",\"country_of_establishment\":\"\","
				+ "\"corporate_type\":\"\",\"registration_no\":\"\",\"incorporation_date\":\"\",\"industry\":"
				+ "\"\",\"etailer\":\"false\",\"option\":\"false\",\"type_of_financial_account\":\"\","
				+ "\"ccj\":\"false\",\"ongoing_due_diligence_date\":\"\",\"est_no_transactions_pcm\":"
				+ "\"\"},\"corporate_compliance\":{\"sic\":\"\",\"former_name\":\"\",\"legal_form\":"
				+ "\"\",\"foreign_owned_company\":\"\",\"net_worth\":\"\",\"fixed_assets\":"
				+ "\"\",\"total_liabilities_and_equities\":\"\",\"total_share_holders\":\"\","
				+ "\"global_ultimate_DUNS\":\"\",\"global_ultimate_name\":\"\",\"global_ultimate_country\":"
				+ "\"\",\"registration_date\":\"\",\"match_name\":\"\",\"iso_country_code_2_digit\":"
				+ "\"\",\"iso_country_code_3_digit\":\"\",\"statement_date\":\"\",\"gross_income\":\"\","
				+ "\"net_income\":\"\",\"total_current_assets\":\"\",\"total_assets\":\"\","
				+ "\"total_long_term_liabilities\":\"\",\"total_current_liabilities\":\"\",\"total_matched_shareholders\":"
				+ "\"\",\"financial_strength\":\"\"},\"risk_profile\":{\"country_risk_indicator\":"
				+ "\"\",\"risk_trend\":\"\",\"risk_direction\":\"\",\"updated_risk\":\"\",\"failure_score\":"
				+ "\"\",\"delinquency_failure_score\":\"\",\"continent\":\"\",\"country\":\"\","
				+ "\"state_country\":\"\",\"duns_number\":\"\",\"trading_styles\":\"\",\"us1987_primary_SIC_4_digit\":"
				+ "\"\",\"financial_figures_month\":\"\",\"financial_figures_year\":\"\",\"financial_year_end_date\":"
				+ "\"\",\"annual_sales\":\"\",\"modelled_annual_sales\":\"\",\"net_worth_amount\":"
				+ "\"\",\"modelled_net_worth\":\"\",\"location_type\":\"\",\"import_export_indicator\":\"\","
				+ "\"domestic_ultimate_record\":\"\",\"global_ultimate_record\":\"\",\"group_structure_number_of_levels\":"
				+ "\"\",\"headquarter_details\":\"\",\"immediate_parent_details\":\"\",\"domestic_ultimate_parent_details\":"
				+ "\"\",\"global_ultimate_parent_details\":\"\",\"credit_limit\":\"\",\"risk_rating\":\"\","
				+ "\"profit_loss\":\"\"},\"conversionPrediction\":{\"AccountId\":\"0010X00004laSxKQAU\","
				+ "\"ETVBand\":\"25k - 100k\",\"conversionFlag\":\"Low\","
				+ "\"conversionProbability\":0.23185803},\"affiliate_number\":\"A010069\"}");
		registrationDetailsDBDto.setAccountId(53039);
		ActivityLogs logs = new ActivityLogs();
		List<ActivityLogDataWrapper> logDataWrapper = new ArrayList<>();
		ActivityLogDataWrapper data = new ActivityLogDataWrapper();
		data.setActivity("Sanctions and KYC Check Fail");
		data.setActivityType("Signup-STP");
		data.setCreatedBy("cd.comp.system");
		data.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data1 = new ActivityLogDataWrapper();
		data1.setActivity("For CONTACT, After repeat SANCTION check status changed from SERVICE_FAILURE to PASS");
		data1.setActivityType("Signup-STP");
		data1.setCreatedBy("cd.comp.system");
		data1.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data2 = new ActivityLogDataWrapper();
		data2.setActivity("For CONTACT, After repeat FRAUGSTER check status changed from SERVICE_FAILURE to PASS");
		data2.setActivityType("Signup-STP");
		data2.setCreatedBy("cd.comp.system");
		data2.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data3 = new ActivityLogDataWrapper();
		data3.setActivity("After recheck FraudPredict status modified from PASS to SERVICE_FAILURE for AYSHA SADDIQ");
		data3.setActivityType("Profile fraudpredict repeat");
		data3.setCreatedBy("cd.comp.system");
		data3.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data4 = new ActivityLogDataWrapper();
		data4.setActivity("After recheck Sanction status modified from PASS to service_failure for AYSHA SADDIQ");
		data4.setActivityType("Profile sanction repeat");
		data4.setCreatedBy("cd.comp.system");
		data4.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data5 = new ActivityLogDataWrapper();
		data5.setActivity("worldcheck modified from Not Available to 'Safe' for CONTACT AYSHA SADDIQ");
		data5.setActivityType("Profile sanction update");
		data5.setCreatedBy("cd.comp.system");
		data5.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data6 = new ActivityLogDataWrapper();
		data6.setActivity("ofaclist modified from Not Available to 'Safe' for CONTACT AYSHA SADDIQ");
		data6.setActivityType("Profile sanction update");
		data6.setCreatedBy("cd.comp.system");
		data6.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data7 = new ActivityLogDataWrapper();
		data7.setActivity("Black Listed Customer information");
		data7.setActivityType("Signup-STP");
		data7.setCreatedBy("cd.comp.system");
		data7.setCreatedOn("01/04/2021 15:38:43");
		logDataWrapper.add(data);
		logDataWrapper.add(data1);
		logDataWrapper.add(data2);
		logDataWrapper.add(data3);
		logDataWrapper.add(data4);
		logDataWrapper.add(data5);
		logDataWrapper.add(data6);
		logDataWrapper.add(data7);
		logs.setActivityLogData(logDataWrapper);
		registrationDetailsDBDto.setActivityLogs(logs);
		registrationDetailsDBDto.setAlertComplianceLog("----");
		registrationDetailsDBDto.setConComplianceStatus("INACTIVE");
		registrationDetailsDBDto.setContactAttrib("{\"onQueue\":false,\"contact_sf_id\":"
				+ "\"0036E000011yA2IDTO\",\"trade_contact_id\":6648877,\"title\":\"\","
				+ "\"pref_name\":\"\",\"first_name\":\"AYSHA\",\"middle_name\":\"\","
				+ "\"last_name\":\"SADDIQ\",\"second_surname\":\"\",\"mothers_surname\":"
				+ "\"\",\"dob\":\"1968-10-19\",\"position_of_significance\":\"\",\"authorised_signatory\":"
				+ "\"true\",\"job_title\":\"Other\",\"address_type\":\"Current Address\","
				+ "\"address1\":\"Rue Leslie\",\"town_city_muncipalty\":\"Toronto\",\"building_name\":"
				+ "\"29 Rue Leslie\",\"street\":\"Rue Leslie\",\"street_number\":\"125\","
				+ "\"street_type\":\"\",\"post_code\":\"B0713133\",\"post_code_lat\":0.0,"
				+ "\"post_code_long\":0.0,\"country_of_residence\":\"USA\",\"home_phone\":"
				+ "\"\",\"work_phone\":\"\",\"work_phone_ext\":\"\",\"mobile_phone\":\"+1-6547125863\","
				+ "\"primary_phone\":\"Mobile\",\"email\":\"stefan@gmail.ca\",\"second_email\":"
				+ "\"\",\"designation\":\"Other\",\"ip_address\":\"192.168.2.154\",\"ip_address_latitude\":"
				+ "\"0.00\",\"ip_address_longitude\":\"0.00\",\"is_primary_contact\":true,"
				+ "\"country_of_nationality\":\"\",\"gender\":\"\",\"occupation\":\"Other\",\"passport_number\":"
				+ "\"\",\"passport_country_code\":\"\",\"passport_exipry_date\":\"\",\"passport_full_name\":"
				+ "\"\",\"passport_mrz_line_1\":\"\",\"passport_mrz_line_2\":\"\",\"passport_birth_family_name\":"
				+ "\"\",\"passport_name_at_citizen\":\"\",\"passport_birth_place\":\"\",\"driving_license\":"
				+ "\"\",\"driving_version_number\":\"\",\"driving_license_card_number\":\"\",\"driving_license_country\":"
				+ "\"\",\"driving_state_code\":\"\",\"driving_expiry\":\"\",\"medicare_card_number\":\"\","
				+ "\"medicare_ref_number\":\"\",\"australia_rta_card_number\":\"\",\"state_province_county\":"
				+ "\"SOUTH CAROLINA\",\"municipality_of_birth\":\"\",\"country_of_birth\":\"\",\"state_of_birth\":"
				+ "\"\",\"civic_number\":\"\",\"sub_building\":\"\",\"unit_number\":\"\",\"sub_city\":"
				+ "\"Oslofjord\",\"national_id_type\":\"\",\"national_id_number\":\"\",\"years_in_address\":"
				+ "\"3.00\",\"residential_status\":\"\",\"version\":1,\"district\":\"\",\"area_number\":"
				+ "\"\",\"aza\":\"SOUTH CAROLINA\",\"prefecture\":\"SOUTH CAROLINA\",\"floor_number\":\"\"}");
		registrationDetailsDBDto.setContactId(53940);
		registrationDetailsDBDto.setContactName("AYSHA SADDIQ");
		StatusReason contactStatusReason = new StatusReason();
		List<StatusReasonData> statusReasonData = new ArrayList<>();
		StatusReasonData reason = new StatusReasonData();
		reason.setName("Alert from 3rd party");
		reason.setValue(Boolean.FALSE);
		StatusReasonData reason1 = new StatusReasonData();
		reason.setName("Alert from law enforcement");
		reason.setValue(Boolean.FALSE);
		StatusReasonData reason2 = new StatusReasonData();
		reason.setName("Awaiting Documents");
		reason.setValue(Boolean.TRUE);
		statusReasonData.add(reason2);
		statusReasonData.add(reason1);
		statusReasonData.add(reason);
		contactStatusReason.setStatusReasonData(statusReasonData);
		registrationDetailsDBDto.setContactStatusReason(contactStatusReason);
		registrationDetailsDBDto.setCountryOfResidenceFullName("USA");
		registrationDetailsDBDto.setCrmAccountId("0016E000020efr0DYO");
		registrationDetailsDBDto.setCrmContactId("0036E000011yA2IDTO");
		registrationDetailsDBDto.setCustomCheckStatus("PASS");
		List<UploadDocumentTypeDBDto> documentList = new ArrayList<>();
		UploadDocumentTypeDBDto doc = new UploadDocumentTypeDBDto();
		doc.setDocumentName("National ID Card");
		doc.setId(1);
		UploadDocumentTypeDBDto doc1 = new UploadDocumentTypeDBDto();
		doc.setDocumentName("Drivers License");
		doc.setId(2);
		UploadDocumentTypeDBDto doc2 = new UploadDocumentTypeDBDto();
		doc.setDocumentName("Passport");
		doc.setId(3);
		documentList.add(doc);
		documentList.add(doc1);
		documentList.add(doc2);
		registrationDetailsDBDto.setDocumentList(documentList);
		Map<String, List<EventDBDto>> eventDBDto = new HashMap<>();
		List<EventDBDto> eventDBDtoList = new ArrayList<>();

		EventDBDto event = new EventDBDto();
		event.setId(102209);
		event.setStatus("Fail");
		event.setSummary("{\"status\":\"FAIL\",\"ip\":\"false\",\"ipMatchedData\":"
				+ "\"No Match Found\",\"email\":\"false\",\"emailMatchedData\":"
				+ "\"No Match Found\",\"phone\":false,\"phoneMatchedData\":\"No Match Found\","
				+ "\"accountNumber\":\"NOT_REQUIRED\",\"name\":\"true\",\"nameMatchedData\":"
				+ "\"AYSHA SADDIQ\",\"domain\":\"false\",\"domainMatchedData\":\"No Match Found\","
				+ "\"webSite\":\"false\",\"websiteMatchedData\":\"No Match Found\","
				+ "\"bankName\":\"NOT_REQUIRED\"}");
		event.setUpdatedBy("cd.comp.system");
		event.setUpdatedOn(Timestamp.valueOf("2021-01-28 17:29:52.627"));
		eventDBDtoList.add(event);
		List<EventDBDto> eventDBDtoList1 = new ArrayList<>();
		EventDBDto event1 = new EventDBDto();
		event1.setId(1022212);
		event1.setStatus("Pass");
		event1.setSummary("{\"status\":\"PASS\",\"frgTransId\":\"89ba8d17-feb5-4179-9c8b-9c566d954db8\","
				+ "\"score\":\"-85.54\",\"fraugsterApproved\":\"1.0\",\"cdTrasId\":\"00000539401\"}");
		event1.setUpdatedBy("cd.comp.system");
		event1.setUpdatedOn(Timestamp.valueOf("2021-01-28 17:29:52.627"));
		eventDBDtoList1.add(event1);
		List<EventDBDto> eventDBDtoList2 = new ArrayList<>();
		EventDBDto event2 = new EventDBDto();
		event2.setId(1022210);
		event2.setStatus("Not Required");
		event2.setSummary(
				"{\"status\":\"NOT_REQUIRED\",\"eidCheck\":false,\"verifiactionResult\":\"Not Available\",\"referenceId\":"
						+ "\"002-C-0000053940\",\"dob\":\"19/10/1968\",\"checkedOn\":\"2021-01-28 17:29:53.484\"}");
		event2.setUpdatedBy("cd.comp.system");
		event2.setUpdatedOn(Timestamp.valueOf("2021-01-28 17:29:52.627"));
		eventDBDtoList2.add(event2);
		List<EventDBDto> eventDBDtoList3 = new ArrayList<>();
		EventDBDto event3 = new EventDBDto();
		event3.setId(1022206);
		event3.setStatus("Service Failure");
		event3.setSummary("{\"status\":\"SERVICE_FAILURE\"}");
		event3.setUpdatedBy("cd.comp.system");
		event3.setUpdatedOn(Timestamp.valueOf("2021-01-28 17:29:52.627"));
		eventDBDtoList3.add(event3);
		List<EventDBDto> eventDBDtoList4 = new ArrayList<>();
		EventDBDto event4 = new EventDBDto();
		event4.setId(1022208);
		event4.setStatus("WatchList");
		event4.setSummary("{\"status\":\"WATCH_LIST\",\"country\":\"USA\",\"state\":\"SOUTH CAROLINA\"}");
		event4.setUpdatedBy("cd.comp.system");
		event4.setUpdatedOn(Timestamp.valueOf("2021-01-28 17:29:52.627"));
		eventDBDtoList4.add(event4);
		List<EventDBDto> eventDBDtoList5 = new ArrayList<>();
		EventDBDto event5 = new EventDBDto();
		event5.setId(1056434);
		event5.setStatus("Pass");
		event5.setSummary("{\"status\":\"PASS\",\"sanctionId\":\"002-C-0000053940\",\"ofacList\":"
				+ "\"Safe\",\"worldCheck\":\"Safe\",\"updatedOn\":\"2021-04-01 15:38:40.854\",\"providerName\":"
				+ "\"FINSCAN\",\"providerMethod\":\"SLLookupMulti\"}");
		event5.setUpdatedBy("cd.comp.system");
		event5.setUpdatedOn(Timestamp.valueOf("2021-01-28 17:29:52.627"));
		eventDBDtoList5.add(event5);
		eventDBDto.put("BLACKLIST", eventDBDtoList);
		eventDBDto.put("FRAUGSTER", eventDBDtoList1);
		eventDBDto.put("KYC", eventDBDtoList2);
		eventDBDto.put("IP", eventDBDtoList3);
		eventDBDto.put("GLOBALCHECK", eventDBDtoList4);
		eventDBDto.put("SANCTION", eventDBDtoList5);
		registrationDetailsDBDto.setEventDBDto(eventDBDto);
		registrationDetailsDBDto.setIsOnQueue(true);
		List<String> kycSupportedCountryList = new ArrayList<>();
		kycSupportedCountryList.add("GBR");
		kycSupportedCountryList.add("ESP");
		kycSupportedCountryList.add("AUS");
		kycSupportedCountryList.add("ZAF");
		kycSupportedCountryList.add("AUT");
		kycSupportedCountryList.add("DEU");
		kycSupportedCountryList.add("NZL");
		kycSupportedCountryList.add("NOR");
		kycSupportedCountryList.add("SWE");
		registrationDetailsDBDto.setKycSupportedCountryList(kycSupportedCountryList);
		registrationDetailsDBDto.setLockedBy("cd.comp.system");
		registrationDetailsDBDto.setLegalEntity("CDLEU");
		registrationDetailsDBDto.setOrgnization("Currencies Direct");
		registrationDetailsDBDto.setPoiExists("0");
		registrationDetailsDBDto.setRegComp(Timestamp.valueOf("2021-01-28 17:29:45.95"));
		registrationDetailsDBDto.setRegIn(Timestamp.valueOf("2021-01-28 17:29:45.95"));
		registrationDetailsDBDto.setRegistrationInDate(Timestamp.valueOf("2021-01-28 17:29:44.777"));
		registrationDetailsDBDto.setTradeContactId("6648877");
		registrationDetailsDBDto.setTradeAccountNum("0201040010026102");
		registrationDetailsDBDto.setUserResourceId(676780);
		Watchlist watachList = new Watchlist();
		WatchListData watchlistData = new WatchListData();
		List<WatchListData> watchlistDataList = new ArrayList<>();
		watchlistData.setContactId(-1);
		watchlistData.setName("Account Info Updated");
		watchlistData.setValue(false);
		WatchListData watchlistData1 = new WatchListData();
		watchlistData1.setContactId(-1);
		watchlistData1.setName("Cash based industry");
		watchlistData1.setValue(false);
		watchlistDataList.add(watchlistData);
		watchlistDataList.add(watchlistData1);
		watachList.setWatchlistData(watchlistDataList);
		registrationDetailsDBDto.setWatachList(watachList);

		return registrationDetailsDBDto;

	}

	private RegistrationDetailsDto getRegistrationDetailsDto()
	{
		RegistrationDetailsDto registrationDetailsDto= new RegistrationDetailsDto();
		AccountWrapper account= new AccountWrapper();
     	account.setTradeAccountNumber("0201040010026102");
		account.setPurposeOfTran("----");
		account.setServiceRequired("Transfer");
		account.setSourceOfFund("----");
		account.setSource("Web");
		account.setAffiliateName("Marketing USA");
	registrationDetailsDto.setAccount(account);
		ActivityLogs logs = new ActivityLogs();
		List<ActivityLogDataWrapper> logDataWrapper = new ArrayList<>();
		ActivityLogDataWrapper data = new ActivityLogDataWrapper();
		data.setActivity("Sanctions and KYC Check Fail");
		data.setActivityType("Signup-STP");
		data.setCreatedBy("cd.comp.system");
		data.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data1 = new ActivityLogDataWrapper();
		data1.setActivity("For CONTACT, After repeat SANCTION check status changed from SERVICE_FAILURE to PASS");
		data1.setActivityType("Signup-STP");
		data1.setCreatedBy("cd.comp.system");
		data1.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data2 = new ActivityLogDataWrapper();
		data2.setActivity("For CONTACT, After repeat FRAUGSTER check status changed from SERVICE_FAILURE to PASS");
		data2.setActivityType("Signup-STP");
		data2.setCreatedBy("cd.comp.system");
		data2.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data3 = new ActivityLogDataWrapper();
		data3.setActivity("After recheck FraudPredict status modified from PASS to SERVICE_FAILURE for AYSHA SADDIQ");
		data3.setActivityType("Profile fraudpredict repeat");
		data3.setCreatedBy("cd.comp.system");
		data3.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data4 = new ActivityLogDataWrapper();
		data4.setActivity("After recheck Sanction status modified from PASS to service_failure for AYSHA SADDIQ");
		data4.setActivityType("Profile sanction repeat");
		data4.setCreatedBy("cd.comp.system");
		data4.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data5 = new ActivityLogDataWrapper();
		data5.setActivity("worldcheck modified from Not Available to 'Safe' for CONTACT AYSHA SADDIQ");
		data5.setActivityType("Profile sanction update");
		data5.setCreatedBy("cd.comp.system");
		data5.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data6 = new ActivityLogDataWrapper();
		data6.setActivity("ofaclist modified from Not Available to 'Safe' for CONTACT AYSHA SADDIQ");
		data6.setActivityType("Profile sanction update");
		data6.setCreatedBy("cd.comp.system");
		data6.setCreatedOn("01/04/2021 15:38:43");
		ActivityLogDataWrapper data7 = new ActivityLogDataWrapper();
		data7.setActivity("Black Listed Customer information");
		data7.setActivityType("Signup-STP");
		data7.setCreatedBy("cd.comp.system");
		data7.setCreatedOn("01/04/2021 15:38:43");
		logDataWrapper.add(data);
		logDataWrapper.add(data1);
		logDataWrapper.add(data2);
		logDataWrapper.add(data3);
		logDataWrapper.add(data4);
		logDataWrapper.add(data5);
		logDataWrapper.add(data6);
		logDataWrapper.add(data7);
		logs.setActivityLogData(logDataWrapper);
		registrationDetailsDto.setActivityLogs(logs);
		registrationDetailsDto.setAlertComplianceLog("----");	
		ContactWrapper currentContact= new ContactWrapper();
		currentContact.setTradeContactID(6648877);
		currentContact.setDob("19/10/1968");
		currentContact.setEmail("stefan@gmail.ca");
		currentContact.setIpAddress("192.168.2.154");
		currentContact.setPrimaryContact(true);
		currentContact.setOccupation("Other");
		registrationDetailsDto.setCurrentContact(currentContact);
		List<UploadDocumentTypeDBDto> documentList = new ArrayList<>();
		UploadDocumentTypeDBDto doc = new UploadDocumentTypeDBDto();
		doc.setDocumentName("National ID Card");
		doc.setId(1);
		UploadDocumentTypeDBDto doc1 = new UploadDocumentTypeDBDto();
		doc1.setDocumentName("Drivers License");
		doc1.setId(2);
		UploadDocumentTypeDBDto doc2 = new UploadDocumentTypeDBDto();
		doc2.setDocumentName("Passport");
		doc2.setId(3);
		documentList.add(doc);
		documentList.add(doc1);
		documentList.add(doc2);
		registrationDetailsDto.setDocumentList(documentList);
		Fraugster fraugster= new Fraugster();
		fraugster.setCreatedOn("01/04/2021 15:38:38");
		fraugster.setFailCount(0);
		fraugster.setFraugsterTotalRecords(3);
		fraugster.setFraugsterId("7354e843-0bdd-4e9c-a226-9448fa43a5ce");
		fraugster.setId(1056433);
		fraugster.setPassCount(3);
		fraugster.setIsRequired(true);
		fraugster.setScore("-83.32");
		fraugster.setStatus(true);
		fraugster.setUpdatedBy("cd.comp.system");
		registrationDetailsDto.setFraugster(fraugster);
		InternalRule internalRule= new InternalRule();
		Blacklist blacklist= new Blacklist();
		blacklist.setAccNumberMatchedData("");
		blacklist.setDomain("false");
		blacklist.setDomainMatchedData("(No Match Found)");
		blacklist.setEmail("false");
		blacklist.setEmailMatchedData("(No Match Found)");
		blacklist.setFailCount(1);
		blacklist.setId(1022209);
		blacklist.setIp("false");
		blacklist.setIpMatchedData("(No Match Found)");
		blacklist.setIsRequired(true);
		blacklist.setName("true");
		blacklist.setNameMatchedData("AYSHA SADDIQ");
		blacklist.setStatus(false);
		blacklist.setPassCount(4);
		blacklist.setWebsiteMatchedData("(No Match Found)");;
		blacklist.setPhone(true);
		blacklist.setPhoneMatchedData("(No Match Found)");
		internalRule.setBlacklist(blacklist);
		CustomCheck customCheck = new CustomCheck();
		CountryCheck countryCheck= new CountryCheck();
		countryCheck.setCountryCheckTotalRecords(1);
		countryCheck.setId(1022207);
		countryCheck.setStatus("Pass");
		countryCheck.setIsRequired(true);
		customCheck.setCountryCheck(countryCheck);
		customCheck.setCustomCheckTotalRecords(1);
		GlobalCheck globalCheck= new GlobalCheck();
		globalCheck.setGlobalCheckTotalRecords(1);
		globalCheck.setIsRequired(true);
		globalCheck.setId(1022208);
		globalCheck.setStatus("Watch list");
		customCheck.setGlobalCheck(globalCheck);
		IpCheck ipCheck= new IpCheck();
		ipCheck.setCheckedOn("28/01/2021 17:29:52");
		ipCheck.setIpCheckTotalRecords(1);
		ipCheck.setId(1022206);
		ipCheck.setIsRequired(true);
		ipCheck.setStatus("Service Failure");
		customCheck.setIpCheck(ipCheck);
		customCheck.setPassCount(1);
		internalRule.setCustomCheck(customCheck);
		registrationDetailsDto.setInternalRule(internalRule);
		registrationDetailsDto.setIsOnQueue(false);
		registrationDetailsDto.setLocked(true);
		registrationDetailsDto.setLockedBy("cd.comp.system");
		Kyc kyc= new Kyc();
		kyc.setCheckedOn("28/01/2021 17:29:53");
		kyc.setDob("19-10-1968");
		kyc.setId(1022210);
	    kyc.setIsRequired(false);
	    kyc.setKycTotalRecords(1);
	    kyc.setReferenceId("002-C-0000053940");
	    kyc.setStatusValue("Not Required");
	    kyc.setReferenceId("Not Avaliable");
		registrationDetailsDto.setKyc(kyc);
		Onfido onfido= new Onfido();
		onfido.setOnfidoTotalRecords(0);
		registrationDetailsDto.setOnfido(onfido);
		registrationDetailsDto.setPoiExists("false");
		StatusReason contactStatusReason = new StatusReason();
		List<StatusReasonData> statusReasonData = new ArrayList<>();
		StatusReasonData reason = new StatusReasonData();
		reason.setName("Alert from 3rd party");
		reason.setValue(Boolean.FALSE);
		StatusReasonData reason1 = new StatusReasonData();
		reason.setName("Alert from law enforcement");
		reason.setValue(Boolean.FALSE);
		StatusReasonData reason2 = new StatusReasonData();
		reason.setName("Awaiting Documents");
		reason.setValue(Boolean.TRUE);
		statusReasonData.add(reason2);
		statusReasonData.add(reason1);
		statusReasonData.add(reason);
		contactStatusReason.setStatusReasonData(statusReasonData);
		registrationDetailsDto.setStatusReason(contactStatusReason);
		Watchlist watachList = new Watchlist();
		WatchListData watchlistData = new WatchListData();
		List<WatchListData> watchlistDataList = new ArrayList<>();
		watchlistData.setContactId(-1);
		watchlistData.setName("Account Info Updated");
		watchlistData.setValue(false);
		WatchListData watchlistData1 = new WatchListData();
		watchlistData1.setContactId(-1);
		watchlistData1.setName("Cash based industry");
		watchlistData1.setValue(false);
		watchlistDataList.add(watchlistData);
		watchlistDataList.add(watchlistData1);
		watachList.setWatchlistData(watchlistDataList);
		registrationDetailsDto.setWatchlist(watachList);
		registrationDetailsDto.setUserResourceId(676780);
		Sanction sanction= new Sanction();
		sanction.setEventServiceLogId(1056434);
		sanction.setFailCount(0);
		sanction.setIsRequired(true);
		sanction.setPassCount(3);
		sanction.setOfacList("Safe");
		sanction.setSanctionId("002-C-0000053940");
		sanction.setStatus(true);
		sanction.setUpdatedBy("cd.comp.system");
		sanction.setSanctionTotalRecords(3);
		sanction.setUpdatedOn("01/04/2021 15:38:40");
		sanction.setWorldCheck("Safe");
		registrationDetailsDto.setSanction(sanction);
		return registrationDetailsDto;
		
	}

	@Test
	public void testTransform() {
		
		RegistrationDetailsDto expectedResult=getRegistrationDetailsDto();
		RegistrationDetailsDto actualResult = registrationDetailsTransformer
				.transform(getRegistrationDetailsDBDto());
		assertEquals(expectedResult.getInternalRule().getBlacklist().getDomain(), actualResult.getInternalRule().getBlacklist().getDomain());
        assertEquals(expectedResult.getInternalRule().getBlacklist().getName(),actualResult.getInternalRule().getBlacklist().getName());
        assertEquals(expectedResult.getInternalRule().getBlacklist().getEmail(),actualResult.getInternalRule().getBlacklist().getEmail());
        assertEquals(expectedResult.getInternalRule().getBlacklist().getIp(),actualResult.getInternalRule().getBlacklist().getIp());
	}

}
