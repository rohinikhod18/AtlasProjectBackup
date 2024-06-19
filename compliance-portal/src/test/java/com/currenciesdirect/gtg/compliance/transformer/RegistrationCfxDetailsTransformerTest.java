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
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.FraugsterDetails;
import com.currenciesdirect.gtg.compliance.core.domain.InternalRule;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCfxDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.SanctionDetails;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReason;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonData;
import com.currenciesdirect.gtg.compliance.core.domain.UploadDocumentTypeDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationCfxContactDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationCfxDetailsDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDBDto;

public class RegistrationCfxDetailsTransformerTest {

	@InjectMocks
	RegistrationCfxDetailsTransformer registrationCfxDetailsTransformer;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	private RegistrationCfxDetailsDBDto getRegistrationCfxDetailsDBDto()
	{
		RegistrationCfxDetailsDBDto registrationCfxDetailsDBDto= new RegistrationCfxDetailsDBDto();
		registrationCfxDetailsDBDto.setAlertComplianceLog("----");
		ActivityLogs activityLogs= new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogData= new ArrayList<>();
		ActivityLogDataWrapper data= new ActivityLogDataWrapper();
		data.setActivity("Compliance pending");
		data.setActivityType("Signup-STP");
		data.setCreatedBy("cd.comp.system");
		data.setCreatedOn("01/04/2021 15:20:18");
		ActivityLogDataWrapper data1= new ActivityLogDataWrapper();
		data1.setActivity("For CONTACT, After repeat FRAUGSTER check status changed from SERVICE_FAILURE to PASS");
		data1.setActivityType("Signup-STP");
		data1.setCreatedBy("cd.comp.system");
		data1.setCreatedOn("01/04/2021 15:20:18");
		ActivityLogDataWrapper data2= new ActivityLogDataWrapper();
		data2.setActivity("Compliance pending");
		data2.setActivityType("Signup-STP");
		data2.setCreatedBy("cd.comp.system");
		data2.setCreatedOn("01/04/2021 15:20:18");
		ActivityLogDataWrapper data3= new ActivityLogDataWrapper();
		data3.setActivity("For CONTACT, After repeat FRAUGSTER check status changed from SERVICE_FAILURE to PASS");
		data3.setActivityType("Signup-STP");
		data3.setCreatedBy("cd.comp.system");
		data3.setCreatedOn("01/04/2021 15:20:18");
		ActivityLogDataWrapper data4= new ActivityLogDataWrapper();
		data4.setActivity("Compliance pending");
		data4.setActivityType("Signup-STP");
		data4.setCreatedBy("cd.comp.system");
		data4.setCreatedOn("01/04/2021 15:20:18");
		activityLogData.add(data);
		activityLogData.add(data1);
		activityLogData.add(data2);
		activityLogData.add(data3);
		activityLogData.add(data4);
		activityLogs.setActivityLogData(activityLogData);
		registrationCfxDetailsDBDto.setActivityLogs(activityLogs);
		StatusReason contactStatusReason = new StatusReason();
		List<StatusReasonData> statusReasonData = new ArrayList<>();
		StatusReasonData reason = new StatusReasonData();
		reason.setName("Alert from 3rd party");
		reason.setValue(Boolean.FALSE);
		StatusReasonData reason1 = new StatusReasonData();
		reason1.setName("Alert from law enforcement");
		reason1.setValue(Boolean.FALSE);
		StatusReasonData reason2 = new StatusReasonData();
		reason2.setName("Awaiting Documents");
		reason2.setValue(Boolean.TRUE);
		statusReasonData.add(reason2);
		statusReasonData.add(reason1);
		statusReasonData.add(reason);
		contactStatusReason.setStatusReasonData(statusReasonData);
		registrationCfxDetailsDBDto.setContactStatusReason(contactStatusReason);
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
		registrationCfxDetailsDBDto.setDocumentTypeList(documentList);
		registrationCfxDetailsDBDto.setIsOnQueue(true);
		registrationCfxDetailsDBDto.setPoiExists("0");
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
		registrationCfxDetailsDBDto.setWatachList(watachList);
		List<RegistrationDetailsDBDto> regCfxDetailsDBDto= new ArrayList<>();
		RegistrationDetailsDBDto registrationDetailsDBDto= new RegistrationDetailsDBDto();
		registrationDetailsDBDto.setAccountAttrib("{\"onQueue\":false,\"acc_sf_id\":\"0AAAF04060uXN27H58\","
				+ "\"trade_acc_id\":634969,\"trade_acc_number\":\"1322120284120578\",\"branch\":\"Moorgate HO\","
				+ "\"channel\":\"Website\",\"cust_type\":\"CFX\",\"act_name\":\"EUROTRADE SUPPLY LIMITED\","
				+ "\"country_interest\":\"\",\"trade_name\":\"EUROTRADE SUPPLY LIMITED\",\"trasaction_purpose\":"
				+ "\"Purchase of Goods\",\"op_country\":\"\",\"turnover\":\"900000\",\"service_req\":\"Transfer\","
				+ "\"buying_currency\":\"EUR\",\"selling_currency\":\"USD\",\"txn_value\":\"\",\"source_of_fund\":"
				+ "\"\",\"avg_txn\":20000.0,\"source\":\"Internet\",\"sub_source\":\"PayPerClick V1\",\"referral\":"
				+ "\"\",\"referral_text\":\"\",\"extended_referral\":\"\",\"ad_campaign\":\"Brand\",\"keywords\":"
				+ "\"currencies+direct\",\"search_engine\":\"\",\"website\":\"www.eurotradepply.co.uk\",\"affiliate_name\":"
				+ "\"Marketing HO\",\"reg_mode\":\"InPerson\",\"organization_legal_entity\":\"CDLGB\",\"version\":1,"
				+ "\"reg_date_time\":\"2019-03-12T16:44:16Z\",\"company\":{\"billing_address\":"
				+ "\"60 Parkhurst Avenue,Manchester,Greater Manchester,M403QN,UK\",\"company_phone\":\"+91-7875047583\","
				+ "\"shipping_address\":\"\",\"vat_no\":\"\",\"country_region\":\"\",\"country_of_establishment\":"
				+ "\"GBR\",\"corporate_type\":\"Ltd\",\"registration_no\":\"11580449\",\"incorporation_date\":"
				+ "\"2018-09-20\",\"industry\":\"Alcohol and Tobacco\",\"etailer\":\"false\",\"option\":\"false\","
				+ "\"type_of_financial_account\":\"\",\"ccj\":\"false\",\"ongoing_due_diligence_date\":\"\","
				+ "\"est_no_transactions_pcm\":\"1\"},\"corporate_compliance\":{\"sic\":\"46341\",\"sic_desc\":"
				+ "\"UK SIC 2007\",\"former_name\":\"\",\"legal_form\":\"\",\"foreign_owned_company\":\"\","
				+ "\"net_worth\":\"0\",\"fixed_assets\":\"0\",\"total_liabilities_and_equities\":\"0\",\"total_share_holders\":"
				+ "\"1\",\"global_ultimate_DUNS\":\"\",\"global_ultimate_name\":\"\",\"global_ultimate_country\":"
				+ "\"\",\"registration_date\":\"\",\"match_name\":\"EUROTRADE SUPPLY LIMITED\",\"iso_country_code_2_digit\":"
				+ "\"\",\"iso_country_code_3_digit\":\"\",\"statement_date\":\"0\",\"gross_income\":\"0\",\"net_income\":"
				+ "\"0\",\"total_current_assets\":\"0\",\"total_assets\":\"0\",\"total_long_term_liabilities\":"
				+ "\"0\",\"total_current_liabilities\":\"0\",\"total_matched_shareholders\":\"\",\"financial_strength\":"
				+ "\"O3\"},\"risk_profile\":{\"country_risk_indicator\":\"\",\"risk_trend\":\"\",\"risk_direction\":"
				+ "\"\",\"updated_risk\":\"\",\"failure_score\":\"1353\",\"delinquency_failure_score\":\"\",\"continent\":"
				+ "\"\",\"country\":\"GB\",\"state_country\":\"\",\"duns_number\":\"224386640\",\"trading_styles\":"
				+ "\"\",\"us1987_primary_SIC_4_digit\":\"46341\",\"financial_figures_month\":\"\",\"financial_figures_year\":"
				+ "\"\",\"financial_year_end_date\":\"\",\"annual_sales\":\"\",\"modelled_annual_sales\":\"\","
				+ "\"net_worth_amount\":\"\",\"modelled_net_worth\":\"\",\"location_type\":\"\",\"import_export_indicator\":"
				+ "\"\",\"domestic_ultimate_record\":\"\",\"global_ultimate_record\":\"\",\"group_structure_number_of_levels\":"
				+ "\"\",\"headquarter_details\":\"\",\"immediate_parent_details\":\"\",\"domestic_ultimate_parent_details\":"
				+ "\"\",\"global_ultimate_parent_details\":\"\",\"credit_limit\":\"14,000\",\"risk_rating\":\"O3\",\"profit_loss\":\"\"}}");
		registrationDetailsDBDto.setAccountId(54087);
		registrationDetailsDBDto.setAccComplianceStatus("INACTIVE ");
		registrationDetailsDBDto.setConComplianceStatus("INACTIVE");
		registrationDetailsDBDto.setContactAttrib("{\"onQueue\":false,\"contact_sf_id\":\"0031Z00005FfEcIQBW\","
				+ "\"trade_contact_id\":778514,\"title\":\"Mr\",\"pref_name\":\"\",\"first_name\":\"R\",\"middle_name\":"
				+ "\"\",\"last_name\":\"Ashwin\",\"second_surname\":\"\",\"mothers_surname\":\"\",\"dob\":"
				+ "\"1986-02-22\",\"position_of_significance\":\"Director and Shareholder 25% and >\",\"authorised_signatory\":"
				+ "\"true\",\"job_title\":\"Director & Shareholder\",\"address_type\":\"Current Address\","
				+ "\"address1\":\"TREŠNJINOG CVETA 7\",\"town_city_muncipalty\":\"BEOGRAD\",\"building_name\":"
				+ "\"TREŠNJINOG CVETA 7\",\"street\":\"TREŠNJINOG CVETA\",\"street_number\":\"\",\"street_type\":\"\","
				+ "\"post_code\":\"11070\",\"post_code_lat\":0.0,\"post_code_long\":0.0,\"country_of_residence\":"
				+ "\"SRB\",\"home_phone\":\"+91-7875047583\",\"work_phone\":\"\",\"work_phone_ext\":\"\",\"mobile_phone\":"
				+ "\"+91-7875047583\",\"primary_phone\":\"Work\",\"email\":\"bibliuklckstx@outlook.co.in\",\"second_email\":"
				+ "\"\",\"designation\":\"Director & Shareholder\",\"ip_address\":\"93.87.157.167\",\"ip_address_latitude\":"
				+ "\"0.00\",\"ip_address_longitude\":\"0.00\",\"is_primary_contact\":true,\"country_of_nationality\":"
				+ "\"SRB\",\"gender\":\"Male\",\"occupation\":\"Director & Shareholder\",\"passport_number\":"
				+ "\"\",\"passport_country_code\":\"\",\"passport_exipry_date\":\"\",\"passport_full_name\":"
				+ "\"\",\"passport_mrz_line_1\":\"\",\"passport_mrz_line_2\":\"\",\"passport_birth_family_name\":"
				+ "\"\",\"passport_name_at_citizen\":\"\",\"passport_birth_place\":\"\",\"driving_license\":\"\","
				+ "\"driving_version_number\":\"\",\"driving_license_card_number\":\"\",\"driving_license_country\":"
				+ "\"\",\"driving_state_code\":\"\",\"driving_expiry\":\"\",\"medicare_card_number\":\"\","
				+ "\"medicare_ref_number\":\"\",\"australia_rta_card_number\":\"\",\"state_province_county\":"
				+ "\"NOVI BEOGRAD\",\"municipality_of_birth\":\"\",\"country_of_birth\":\"\",\"state_of_birth\":"
				+ "\"\",\"civic_number\":\"\",\"sub_building\":\"\",\"house_building_number\":\"7\",\"unit_number\":"
				+ "\"\",\"sub_city\":\"BEOGRAD\",\"national_id_type\":\"\",\"national_id_number\":\"\",\"years_in_address\":"
				+ "\"0.00\",\"residential_status\":\"\",\"version\":1,\"district\":\"\",\"area_number\":\"\","
				+ "\"aza\":\"BEOGRAD\",\"prefecture\":\"NOVI BEOGRAD\",\"floor_number\":\"\"}");
		registrationDetailsDBDto.setContactId(54992);
		registrationDetailsDBDto.setContactName("R Ashwin");
		registrationDetailsDBDto.setCountryOfResidenceFullName("Serbia");
		registrationDetailsDBDto.setCrmAccountId("0AAAF04060uXN27H58");
		registrationDetailsDBDto.setCrmContactId("0031Z00005FfEcIQBW");
		registrationDetailsDBDto.setCurrentContactId(54992);
		Map<String, List<EventDBDto>> eventDBDto= new HashMap<>();
		List<EventDBDto> eventDBtoList= new ArrayList<>();
		EventDBDto event= new EventDBDto();
		event.setEntityId("54992");
		event.setId(1036002);
		event.setServiceType("KYC");
		event.setStatus("Not Required");
		event.setSummary("{\"status\":\"NOT_REQUIRED\",\"eidCheck\":false,\"verifiactionResult\":\"Not Available\","
				+ "\"referenceId\":\"002-C-0000054992\",\"dob\":\"22/02/1986\",\"checkedOn\":\"2021-03-10 10:01:09.318\"}");
		event.setUpdatedBy("cd.api.user");
		event.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		List<EventDBDto> eventDBtoList1= new ArrayList<>();
		EventDBDto event1= new EventDBDto();
		event1.setEntityId("54087");
		event1.setId(1036001);
		event1.setServiceType("BLACKLIST");
		event1.setStatus("Not Required");
		event1.setSummary("{\"status\":\"PASS\",\"ip\":\"NOT_REQUIRED\",\"email\":\"NOT_REQUIRED\",\"accountNumber\":"
				+ "\"NOT_REQUIRED\",\"name\":\"false\",\"nameMatchedData\":\"No Match Found\",\"domain\":\"false\","
				+ "\"domainMatchedData\":\"No Match Found\",\"webSite\":"
				+ "\"false\",\"websiteMatchedData\":\"No Match Found\",\"bankName\":\"NOT_REQUIRED\"}");
		event1.setUpdatedBy("cd.api.user");
		event1.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		List<EventDBDto> eventDBtoList2= new ArrayList<>();
		EventDBDto event2= new EventDBDto();
		event2.setEntityId("54993");
		event2.setId(1036007);
		event2.setServiceType("SANCTION");
		event2.setStatus("Not Required");
		event2.setSummary("{\"status\":\"NOT_REQUIRED\",\"sanctionId\":"
				+ "\"002-C-0000054993\",\"ofacList\":\"Not Available\",\"worldCheck\":\"Not Available\"}");
		event2.setUpdatedBy("cd.api.user");
		event2.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		List<EventDBDto> eventDBtoList3= new ArrayList<>();
		EventDBDto event3= new EventDBDto();
		event3.setEntityId("54993");
		event3.setId(1036007);
		event3.setServiceType("SANCTION");
		event3.setStatus("Not Required");
		event3.setSummary("{\"status\":\"NOT_REQUIRED\",\"sanctionId\":"
				+ "\"002-C-0000054992\",\"ofacList\":\"Not Available\",\"worldCheck\":\"Not Available\"}");
		event3.setUpdatedBy("cd.api.user");
		event3.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		List<EventDBDto> eventDBtoList4= new ArrayList<>();
		EventDBDto event4= new EventDBDto();
		event4.setEntityId("54992");
		event4.setId(1056332);
		event4.setServiceType("FRAUGSTER");
		event4.setStatus("Not Required");
		event4.setSummary("{\"status\":\"PASS\",\"frgTransId\":\"429a1ee8-cd2e-4b9c-9470-97ce00419c6a\",\"score\":\"-96.05\","
				+ "\"fraugsterApproved\":\"1.0\",\"cdTrasId\":\"00000549921\"}");
		event4.setUpdatedBy("cd.api.user");
		event4.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		List<EventDBDto> eventDBtoList5= new ArrayList<>();
		EventDBDto event5= new EventDBDto();
		event5.setEntityId("54992");
		event5.setId(1056332);
		event5.setServiceType("FRAUGSTER");
		event5.setStatus("Not Required");
		event5.setSummary("{\"status\":\"PASS\",\"frgTransId\":\"429a1ee8-cd2e-4b9c-9470-97ce00419c6a\",\"score\":\"-96.05\","
				+ "\"fraugsterApproved\":\"1.0\",\"cdTrasId\":\"00000549921\"}");
		event5.setUpdatedBy("cd.api.user");
		event5.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		eventDBtoList.add(event);
		eventDBtoList1.add(event1);
		eventDBtoList2.add(event2);
		eventDBtoList3.add(event3);
		eventDBtoList4.add(event4);
		eventDBtoList5.add(event5);
		eventDBDto.put("KYC", eventDBtoList);
		eventDBDto.put("54087BLACKLISTACCOUNT", eventDBtoList1);
		eventDBDto.put("SANCTIONACCOUNT", eventDBtoList2);
		eventDBDto.put("SANCTIONACCOUNT", eventDBtoList3);
		eventDBDto.put("FRAUGSTER", eventDBtoList4);
		eventDBDto.put("FRAUGSTER", eventDBtoList5);
		registrationDetailsDBDto.setEventDBDto(eventDBDto);
		List<String> kycSupportedCountryList= new ArrayList<>();
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
		registrationDetailsDBDto.setRegComp(Timestamp.valueOf("2021-03-10 10:00:56.557"));
		registrationDetailsDBDto.setRegIn(Timestamp.valueOf("2021-03-10 10:00:56.557"));
		registrationDetailsDBDto.setRegistrationInDate(Timestamp.valueOf("2021-03-10 10:00:56.17"));
		registrationDetailsDBDto.setLegalEntity("CDLGB");
		registrationDetailsDBDto.setOrgnization("Currencies Direct");
		registrationDetailsDBDto.setTradeAccountId("634969");
		registrationDetailsDBDto.setTradeAccountNum("1322120284120578");
		registrationDetailsDBDto.setTradeContactId("778514");
		registrationDetailsDBDto.setUserResourceId(0);
		
		
		RegistrationDetailsDBDto registrationDetailsDBDto1= new RegistrationDetailsDBDto();
        
		registrationDetailsDBDto1.setAccountAttrib("{\"onQueue\":false,\"acc_sf_id\":\"0AAAF04060uXN27H58\","
				+ "\"trade_acc_id\":634969,\"trade_acc_number\":\"1322120284120578\",\"branch\":\"Moorgate HO\","
				+ "\"channel\":\"Website\",\"cust_type\":\"CFX\",\"act_name\":\"EUROTRADE SUPPLY LIMITED\","
				+ "\"country_interest\":\"\",\"trade_name\":\"EUROTRADE SUPPLY LIMITED\",\"trasaction_purpose\":"
				+ "\"Purchase of Goods\",\"op_country\":\"\",\"turnover\":\"900000\",\"service_req\":\"Transfer\","
				+ "\"buying_currency\":\"EUR\",\"selling_currency\":\"USD\",\"txn_value\":\"\",\"source_of_fund\":"
				+ "\"\",\"avg_txn\":20000.0,\"source\":\"Internet\",\"sub_source\":\"PayPerClick V1\",\"referral\":"
				+ "\"\",\"referral_text\":\"\",\"extended_referral\":\"\",\"ad_campaign\":\"Brand\",\"keywords\":"
				+ "\"currencies+direct\",\"search_engine\":\"\",\"website\":\"www.eurotradepply.co.uk\",\"affiliate_name\":"
				+ "\"Marketing HO\",\"reg_mode\":\"InPerson\",\"organization_legal_entity\":\"CDLGB\",\"version\":1,"
				+ "\"reg_date_time\":\"2019-03-12T16:44:16Z\",\"company\":{\"billing_address\":"
				+ "\"60 Parkhurst Avenue,Manchester,Greater Manchester,M403QN,UK\",\"company_phone\":\"+91-7875047583\","
				+ "\"shipping_address\":\"\",\"vat_no\":\"\",\"country_region\":\"\",\"country_of_establishment\":"
				+ "\"GBR\",\"corporate_type\":\"Ltd\",\"registration_no\":\"11580449\",\"incorporation_date\":"
				+ "\"2018-09-20\",\"industry\":\"Alcohol and Tobacco\",\"etailer\":\"false\",\"option\":\"false\","
				+ "\"type_of_financial_account\":\"\",\"ccj\":\"false\",\"ongoing_due_diligence_date\":\"\","
				+ "\"est_no_transactions_pcm\":\"1\"},\"corporate_compliance\":{\"sic\":\"46341\",\"sic_desc\":"
				+ "\"UK SIC 2007\",\"former_name\":\"\",\"legal_form\":\"\",\"foreign_owned_company\":\"\","
				+ "\"net_worth\":\"0\",\"fixed_assets\":\"0\",\"total_liabilities_and_equities\":\"0\",\"total_share_holders\":"
				+ "\"1\",\"global_ultimate_DUNS\":\"\",\"global_ultimate_name\":\"\",\"global_ultimate_country\":"
				+ "\"\",\"registration_date\":\"\",\"match_name\":\"EUROTRADE SUPPLY LIMITED\",\"iso_country_code_2_digit\":"
				+ "\"\",\"iso_country_code_3_digit\":\"\",\"statement_date\":\"0\",\"gross_income\":\"0\",\"net_income\":"
				+ "\"0\",\"total_current_assets\":\"0\",\"total_assets\":\"0\",\"total_long_term_liabilities\":"
				+ "\"0\",\"total_current_liabilities\":\"0\",\"total_matched_shareholders\":\"\",\"financial_strength\":"
				+ "\"O3\"},\"risk_profile\":{\"country_risk_indicator\":\"\",\"risk_trend\":\"\",\"risk_direction\":"
				+ "\"\",\"updated_risk\":\"\",\"failure_score\":\"1353\",\"delinquency_failure_score\":\"\",\"continent\":"
				+ "\"\",\"country\":\"GB\",\"state_country\":\"\",\"duns_number\":\"224386640\",\"trading_styles\":"
				+ "\"\",\"us1987_primary_SIC_4_digit\":\"46341\",\"financial_figures_month\":\"\",\"financial_figures_year\":"
				+ "\"\",\"financial_year_end_date\":\"\",\"annual_sales\":\"\",\"modelled_annual_sales\":\"\","
				+ "\"net_worth_amount\":\"\",\"modelled_net_worth\":\"\",\"location_type\":\"\",\"import_export_indicator\":"
				+ "\"\",\"domestic_ultimate_record\":\"\",\"global_ultimate_record\":\"\",\"group_structure_number_of_levels\":"
				+ "\"\",\"headquarter_details\":\"\",\"immediate_parent_details\":\"\",\"domestic_ultimate_parent_details\":"
				+ "\"\",\"global_ultimate_parent_details\":\"\",\"credit_limit\":\"14,000\",\"risk_rating\":\"O3\",\"profit_loss\":\"\"}}");
		registrationDetailsDBDto1.setAccountId(54087);
		registrationDetailsDBDto1.setAccComplianceStatus("INACTIVE ");
		registrationDetailsDBDto1.setConComplianceStatus("INACTIVE");
		registrationDetailsDBDto1.setContactAttrib("{\"onQueue\":false,\"contact_sf_id\":\"0031Z00005FfEcIQBW\","
				+ "\"trade_contact_id\":778514,\"title\":\"Mr\",\"pref_name\":\"\",\"first_name\":\"R\",\"middle_name\":"
				+ "\"\",\"last_name\":\"Ashwin\",\"second_surname\":\"\",\"mothers_surname\":\"\",\"dob\":"
				+ "\"1986-02-22\",\"position_of_significance\":\"Director and Shareholder 25% and >\",\"authorised_signatory\":"
				+ "\"true\",\"job_title\":\"Director & Shareholder\",\"address_type\":\"Current Address\","
				+ "\"address1\":\"TREŠNJINOG CVETA 7\",\"town_city_muncipalty\":\"BEOGRAD\",\"building_name\":"
				+ "\"TREŠNJINOG CVETA 7\",\"street\":\"TREŠNJINOG CVETA\",\"street_number\":\"\",\"street_type\":\"\","
				+ "\"post_code\":\"11070\",\"post_code_lat\":0.0,\"post_code_long\":0.0,\"country_of_residence\":"
				+ "\"SRB\",\"home_phone\":\"+91-7875047583\",\"work_phone\":\"\",\"work_phone_ext\":\"\",\"mobile_phone\":"
				+ "\"+91-7875047583\",\"primary_phone\":\"Work\",\"email\":\"bibliuklckstx@outlook.co.in\",\"second_email\":"
				+ "\"\",\"designation\":\"Director & Shareholder\",\"ip_address\":\"93.87.157.167\",\"ip_address_latitude\":"
				+ "\"0.00\",\"ip_address_longitude\":\"0.00\",\"is_primary_contact\":true,\"country_of_nationality\":"
				+ "\"SRB\",\"gender\":\"Male\",\"occupation\":\"Director & Shareholder\",\"passport_number\":"
				+ "\"\",\"passport_country_code\":\"\",\"passport_exipry_date\":\"\",\"passport_full_name\":"
				+ "\"\",\"passport_mrz_line_1\":\"\",\"passport_mrz_line_2\":\"\",\"passport_birth_family_name\":"
				+ "\"\",\"passport_name_at_citizen\":\"\",\"passport_birth_place\":\"\",\"driving_license\":\"\","
				+ "\"driving_version_number\":\"\",\"driving_license_card_number\":\"\",\"driving_license_country\":"
				+ "\"\",\"driving_state_code\":\"\",\"driving_expiry\":\"\",\"medicare_card_number\":\"\","
				+ "\"medicare_ref_number\":\"\",\"australia_rta_card_number\":\"\",\"state_province_county\":"
				+ "\"NOVI BEOGRAD\",\"municipality_of_birth\":\"\",\"country_of_birth\":\"\",\"state_of_birth\":"
				+ "\"\",\"civic_number\":\"\",\"sub_building\":\"\",\"house_building_number\":\"7\",\"unit_number\":"
				+ "\"\",\"sub_city\":\"BEOGRAD\",\"national_id_type\":\"\",\"national_id_number\":\"\",\"years_in_address\":"
				+ "\"0.00\",\"residential_status\":\"\",\"version\":1,\"district\":\"\",\"area_number\":\"\","
				+ "\"aza\":\"BEOGRAD\",\"prefecture\":\"NOVI BEOGRAD\",\"floor_number\":\"\"}");
		registrationDetailsDBDto1.setContactId(54992);
		registrationDetailsDBDto1.setContactName("R Ashwin");
		registrationDetailsDBDto1.setCountryOfResidenceFullName("Serbia");
		registrationDetailsDBDto1.setCrmAccountId("0AAAF04060uXN27H58");
		registrationDetailsDBDto1.setCrmContactId("0031Z00005FfEcIQBW");
		registrationDetailsDBDto1.setCurrentContactId(54992);
		Map<String, List<EventDBDto>> eventDBDto1= new HashMap<>();
		List<EventDBDto> eventDBtoLists= new ArrayList<>();
		EventDBDto events= new EventDBDto();
		events.setEntityId("54992");
		events.setId(1036002);
		events.setServiceType("KYC");
		events.setStatus("Not Required");
		events.setSummary("{\"status\":\"NOT_REQUIRED\",\"eidCheck\":false,\"verifiactionResult\":\"Not Available\","
				+ "\"referenceId\":\"002-C-0000054992\",\"dob\":\"22/02/1986\",\"checkedOn\":\"2021-03-10 10:01:09.318\"}");
		events.setUpdatedBy("cd.api.user");
		events.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		List<EventDBDto> eventDBtoLists1= new ArrayList<>();
		EventDBDto events1= new EventDBDto();
		events1.setEntityId("54087");
		events1.setId(1036001);
		events1.setServiceType("BLACKLIST");
		events1.setStatus("Not Required");
		events1.setSummary("{\"status\":\"PASS\",\"ip\":\"NOT_REQUIRED\",\"email\":\"NOT_REQUIRED\",\"accountNumber\":"
				+ "\"NOT_REQUIRED\",\"name\":\"false\",\"nameMatchedData\":\"No Match Found\",\"domain\":\"false\","
				+ "\"domainMatchedData\":\"No Match Found\",\"webSite\":"
				+ "\"false\",\"websiteMatchedData\":\"No Match Found\",\"bankName\":\"NOT_REQUIRED\"}");
		events1.setUpdatedBy("cd.api.user");
		events1.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		List<EventDBDto> eventDBtoLists2= new ArrayList<>();
		EventDBDto events2= new EventDBDto();
		events2.setEntityId("54993");
		events2.setId(1036007);
		events2.setServiceType("SANCTION");
		events2.setStatus("Not Required");
		events2.setSummary("{\"status\":\"NOT_REQUIRED\",\"sanctionId\":"
				+ "\"002-C-0000054993\",\"ofacList\":\"Not Available\",\"worldCheck\":\"Not Available\"}");
		events2.setUpdatedBy("cd.api.user");
		events2.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		List<EventDBDto> eventDBtoLists3= new ArrayList<>();
		EventDBDto events3= new EventDBDto();
		events3.setEntityId("54993");
		events3.setId(1036007);
		events3.setServiceType("SANCTION");
		events3.setStatus("Not Required");
		events3.setSummary("{\"status\":\"NOT_REQUIRED\",\"sanctionId\":"
				+ "\"002-C-0000054992\",\"ofacList\":\"Not Available\",\"worldCheck\":\"Not Available\"}");
		events3.setUpdatedBy("cd.api.user");
		events3.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		List<EventDBDto> eventDBtoLists4= new ArrayList<>();
		EventDBDto events4= new EventDBDto();
		events4.setEntityId("54992");
		events4.setId(1056332);
		events4.setServiceType("FRAUGSTER");
		events4.setStatus("Not Required");
		events4.setSummary("{\"status\":\"PASS\",\"frgTransId\":\"429a1ee8-cd2e-4b9c-9470-97ce00419c6a\",\"score\":\"-96.05\","
				+ "\"fraugsterApproved\":\"1.0\",\"cdTrasId\":\"00000549921\"}");
		events4.setUpdatedBy("cd.api.user");
		events4.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		List<EventDBDto> eventDBtoLists5= new ArrayList<>();
		EventDBDto events5= new EventDBDto();
		events5.setEntityId("54992");
		events5.setId(1056332);
		events5.setServiceType("FRAUGSTER");
		events5.setStatus("Not Required");
		events5.setSummary("{\"status\":\"PASS\",\"frgTransId\":\"429a1ee8-cd2e-4b9c-9470-97ce00419c6a\",\"score\":\"-96.05\","
				+ "\"fraugsterApproved\":\"1.0\",\"cdTrasId\":\"00000549921\"}");
		events5.setUpdatedBy("cd.api.user");
		events5.setUpdatedOn(Timestamp.valueOf("2021-03-10 10:01:09.47"));
		eventDBtoLists.add(events);
		eventDBtoLists1.add(events1);
		eventDBtoLists2.add(events2);
		eventDBtoLists3.add(events3);
		eventDBtoLists4.add(events4);
		eventDBtoLists5.add(events5);
		eventDBDto1.put("KYC", eventDBtoList);
		eventDBDto1.put("54087BLACKLISTACCOUNT", eventDBtoList1);
		eventDBDto1.put("SANCTIONACCOUNT", eventDBtoList2);
		eventDBDto1.put("SANCTIONACCOUNT", eventDBtoList3);
		eventDBDto1.put("FRAUGSTER", eventDBtoList4);
		eventDBDto1.put("FRAUGSTER", eventDBtoList5);
		registrationDetailsDBDto1.setEventDBDto(eventDBDto1);
		List<String> kycSupportedCountryList1= new ArrayList<>();
		kycSupportedCountryList1.add("GBR");
		kycSupportedCountryList1.add("ESP");
		kycSupportedCountryList1.add("AUS");
		kycSupportedCountryList1.add("ZAF");
		kycSupportedCountryList1.add("AUT");
		kycSupportedCountryList1.add("DEU");
		kycSupportedCountryList1.add("NZL");
		kycSupportedCountryList1.add("NOR");
		kycSupportedCountryList1.add("SWE");
		registrationDetailsDBDto1.setKycSupportedCountryList(kycSupportedCountryList);
		registrationDetailsDBDto1.setRegComp(Timestamp.valueOf("2021-03-10 10:00:56.557"));
		registrationDetailsDBDto1.setRegIn(Timestamp.valueOf("2021-03-10 10:00:56.557"));
		registrationDetailsDBDto1.setRegistrationInDate(Timestamp.valueOf("2021-03-10 10:00:56.17"));
		registrationDetailsDBDto1.setLegalEntity("CDLGB");
		registrationDetailsDBDto1.setOrgnization("Currencies Direct");
		registrationDetailsDBDto1.setTradeAccountId("634969");
		registrationDetailsDBDto1.setTradeAccountNum("1322120284120578");
		registrationDetailsDBDto1.setTradeContactId("778514");
		registrationDetailsDBDto1.setUserResourceId(0);
		
		regCfxDetailsDBDto.add(registrationDetailsDBDto);
		regCfxDetailsDBDto.add(registrationDetailsDBDto1);
		registrationCfxDetailsDBDto.setRegCfxDetailsDBDto(regCfxDetailsDBDto);
		return registrationCfxDetailsDBDto;
	}
	
	
	private RegistrationCfxDetailsDto getRegistrationCfxDetailsDto()
	{
		RegistrationCfxDetailsDto registrationCfxDetailsDto= new RegistrationCfxDetailsDto();
	
		AccountWrapper account= new AccountWrapper();
		account.setTradeAccountID(634969);
		account.setTradeAccountNumber("1322120284120578");
		account.setPurposeOfTran("Purchase of Goods");
		account.setServiceRequired("Transfer");
		account.setSourceOfFund("----");
		account.setSource("Internet");
		account.setWebsite("www.eurotradepply.co.uk");
		account.setAffiliateName("Marketing HO");
		registrationCfxDetailsDto.setAccount(account);
		registrationCfxDetailsDto.setAlertComplianceLog("----");
		ActivityLogs activityLogs= new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogData= new ArrayList<>();
		ActivityLogDataWrapper data= new ActivityLogDataWrapper();
		data.setActivity("Compliance pending");
		data.setActivityType("Signup-STP");
		data.setCreatedBy("cd.comp.system");
		data.setCreatedOn("01/04/2021 15:20:18");
		ActivityLogDataWrapper data1= new ActivityLogDataWrapper();
		data1.setActivity("For CONTACT, After repeat FRAUGSTER check status changed from SERVICE_FAILURE to PASS");
		data1.setActivityType("Signup-STP");
		data1.setCreatedBy("cd.comp.system");
		data1.setCreatedOn("01/04/2021 15:20:18");
		ActivityLogDataWrapper data2= new ActivityLogDataWrapper();
		data2.setActivity("Compliance pending");
		data2.setActivityType("Signup-STP");
		data2.setCreatedBy("cd.comp.system");
		data2.setCreatedOn("01/04/2021 15:20:18");
		ActivityLogDataWrapper data3= new ActivityLogDataWrapper();
		data3.setActivity("For CONTACT, After repeat FRAUGSTER check status changed from SERVICE_FAILURE to PASS");
		data3.setActivityType("Signup-STP");
		data3.setCreatedBy("cd.comp.system");
		data3.setCreatedOn("01/04/2021 15:20:18");
		ActivityLogDataWrapper data4= new ActivityLogDataWrapper();
		data4.setActivity("Compliance pending");
		data4.setActivityType("Signup-STP");
		data4.setCreatedBy("cd.comp.system");
		data4.setCreatedOn("01/04/2021 15:20:18");
		activityLogData.add(data);
		activityLogData.add(data1);
		activityLogData.add(data2);
		activityLogData.add(data3);
		activityLogData.add(data4);
		activityLogs.setActivityLogData(activityLogData);
		registrationCfxDetailsDto.setActivityLogs(activityLogs);
	
		List<RegistrationCfxContactDetailsDto> contactDetails= new ArrayList<>();
		RegistrationCfxContactDetailsDto registrationCfxContactDetailsDto= new RegistrationCfxContactDetailsDto();
		ContactWrapper currentContact= new ContactWrapper();
		currentContact.setTradeContactID(778514);
		currentContact.setDob("22/02/1986");
		currentContact.setPositionOfSignificance("Director and Shareholder 25% and >");
		currentContact.setAuthorisedSignatory("true");
		currentContact.setJobTitle("Director & Shareholder");
		currentContact.setAddressType("Current Address");
		currentContact.setPhoneWork("----");
		currentContact.setEmail("bibliuklckstx@outlook.co.in");
		currentContact.setDesignation("Director & Shareholder");
		currentContact.setIpAddress("93.87.157.167");
		currentContact.setPrimaryContact(true);
		currentContact.setNationality("SRB");
		currentContact.setOccupation("Director & Shareholder");
		
		registrationCfxContactDetailsDto.setCurrentContact(currentContact);
		registrationCfxDetailsDto.setContactDetails(contactDetails);
		FraugsterDetails fraugsterDetails= new FraugsterDetails();
		List<Fraugster> fraugsterList= new ArrayList<>();
		Fraugster fraugster= new Fraugster();
		fraugster.setCreatedOn("01/04/2021 14:52:20");
		fraugster.setFailCount(0);
		fraugster.setFraugsterId("429a1ee8-cd2e-4b9c-9470-97ce00419c6a");
		fraugster.setId(1056332);
		fraugster.setPassCount(2);
		fraugster.setScore("-96.05");
		fraugster.setScore("true");
		fraugster.setUpdatedBy("cd.comp.system");
		fraugster.getFraugsterTotalRecords();
		fraugsterDetails.setFraugster(fraugsterList);
		registrationCfxDetailsDto.setFraugsterDetails(fraugsterDetails);
		registrationCfxDetailsDto.setIsOnQueue(true);
		registrationCfxDetailsDto.setPoiExists("0");
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
		
		registrationCfxDetailsDto.setWatchlist(watachList);
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
		registrationCfxDetailsDto.setStatusReason(contactStatusReason);
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
		registrationCfxDetailsDto.setDocumentList(documentList);
		List<InternalRule> internalRuleList= new ArrayList<>();
		InternalRule internalRule= new InternalRule();
		Blacklist blacklist= new Blacklist();
		blacklist.setAccNumberMatchedData("");
		blacklist.setDomain("false");
		blacklist.setDomainMatchedData("(No Match Found)");
		blacklist.setEmail("Not Required");
		blacklist.setEmailMatchedData("(No Match Found)");
		blacklist.setFailCount(1);
		blacklist.setId(1022209);
		blacklist.setIp("Not Required");
		blacklist.setIpMatchedData("(No Match Found)");
		blacklist.setIsRequired(true);
		blacklist.setName("false");
		blacklist.setNameMatchedData("AYSHA SADDIQ");
		blacklist.setStatus(false);
		blacklist.setPassCount(4);
		blacklist.setWebSite("false");
		blacklist.setWebsiteMatchedData("(No Match Found)");;
		blacklist.setPhone(false);
		blacklist.setPhoneMatchedData("(No Match Found)");
		internalRule.setBlacklist(blacklist);
		internalRuleList.add(internalRule);
		registrationCfxDetailsDto.setInternalRule(internalRuleList);
		SanctionDetails sanctionDetails= new SanctionDetails();
		sanctionDetails.setFailCount(0);
		sanctionDetails.setPassCount(0); 
		List<Sanction> sanctionList=new ArrayList<>();
		Sanction sanction = new Sanction();
		sanction.setEventServiceLogId(1036006);
		sanction.setIsRequired(true);
		sanction.setOfacList("Not Required");
		sanction.setSanctionId("002-A-0000054087");
		sanction.setStatus(false);
		sanction.setUpdatedOn("10/03/2021 10:01:09");
		sanction.setUpdatedOn("cd api user");
		sanctionDetails.setSanction(sanctionList);
		registrationCfxDetailsDto.setSanctionDetails(sanctionDetails);
		return registrationCfxDetailsDto;
	}
	
	@Test
	public void testTransform()
	{
		RegistrationCfxDetailsDto expectedResult= getRegistrationCfxDetailsDto();
		RegistrationCfxDetailsDto  actualResult= registrationCfxDetailsTransformer.transform(getRegistrationCfxDetailsDBDto());
	  	assertEquals(expectedResult.getInternalRule().get(0).getBlacklist().getName(),actualResult.getInternalRule().get(0).getBlacklist().getName());
	  	assertEquals(expectedResult.getInternalRule().get(0).getBlacklist().getEmail(),actualResult.getInternalRule().get(0).getBlacklist().getEmail());
	  	assertEquals(expectedResult.getInternalRule().get(0).getBlacklist().getIp(),actualResult.getInternalRule().get(0).getBlacklist().getIp());
	  	assertEquals(expectedResult.getInternalRule().get(0).getBlacklist().getPhone(),actualResult.getInternalRule().get(0).getBlacklist().getPhone());
	  	assertEquals(expectedResult.getInternalRule().get(0).getBlacklist().getWebSite(),actualResult.getInternalRule().get(0).getBlacklist().getWebSite());

	  	
	
	
	}
	
}
