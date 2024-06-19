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
import com.currenciesdirect.gtg.compliance.core.domain.EuPoiCheck;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.FirstCreditCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentDetails;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentInDetails;
import com.currenciesdirect.gtg.compliance.core.domain.Status;
import com.currenciesdirect.gtg.compliance.core.domain.StatusData;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReason;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonData;
import com.currenciesdirect.gtg.compliance.core.domain.UploadDocumentTypeDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.WhitelistCheck;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInInfo;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutCustomCheck;

public class PaymentInDetailsTransformerTest {

	@InjectMocks
	PaymentInDetailsTransformer paymentInDetailsTransformer;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private PaymentInDBDto getPaymentInDBDto() {
		PaymentInDBDto paymentInDBDto = new PaymentInDBDto();
		paymentInDBDto.setAccAttrib("{\"onQueue\":false,\"acc_sf_id\":\"0017a00001WmWAqAAN\",\"trade_acc_id\":1005407,"
				+ "\"trade_acc_number\":\"0202000010054076\",\"branch\":\"Moorgate HO\",\"channel\":"
				+ "\"Internal\",\"cust_type\":\"CFX\",\"act_name\":\"AIP ONE\",\"country_interest\":\"GBR\","
				+ "\"trade_name\":\"QA\",\"trasaction_purpose\":\"Bill payments\",\"op_country\":\"\","
				+ "\"turnover\":\"\",\"service_req\":\"Transfer\",\"buying_currency\":\"CNY\",\"selling_currency\":"
				+ "\"USD\",\"txn_value\":\"25,000 - 50,000\",\"source_of_fund\":\"Savings\",\"avg_txn\":500000.0,"
				+ "\"source\":\"Internet\",\"sub_source\":\"Google\",\"referral\":\"\",\"referral_text\":"
				+ "\"\",\"extended_referral\":\"\",\"ad_campaign\":\"\",\"keywords\":\"\",\"search_engine\":"
				+ "\"\",\"website\":\"www.google.com\",\"affiliate_name\":\"Marketing HO\",\"reg_mode\":"
				+ "\"InPerson\",\"organization_legal_entity\":\"CDLGB\",\"version\":1,\"reg_date_time\":"
				+ "\"2021-03-16T12:23:42Z\",\"company\":{\"billing_address\":\"\",\"company_phone\":\"+91-7218734151\","
				+ "\"shipping_address\":\"\",\"vat_no\":\"\",\"country_region\":\"\",\"country_of_establishment\":"
				+ "\"GBR\",\"corporate_type\":\"\",\"registration_no\":\"\",\"incorporation_date\":\"\","
				+ "\"tc_signed_date\":\"2021-03-16\",\"industry\":\"\",\"etailer\":\"true\",\"option\":"
				+ "\"false\",\"type_of_financial_account\":\"\",\"ccj\":\"false\",\"ongoing_due_diligence_date\":"
				+ "\"\",\"est_no_transactions_pcm\":\"\"},\"corporate_compliance\":{\"sic\":\"\",\"sic_desc\":"
				+ "\"\",\"former_name\":\"\",\"legal_form\":\"\",\"foreign_owned_company\":\"\",\"net_worth\":"
				+ "\"\",\"fixed_assets\":\"\",\"total_liabilities_and_equities\":\"\",\"total_share_holders\":"
				+ "\"\",\"global_ultimate_DUNS\":\"\",\"global_ultimate_name\":\"\",\"global_ultimate_country\":"
				+ "\"\",\"registration_date\":\"\",\"match_name\":\"\",\"iso_country_code_2_digit\":\"\","
				+ "\"iso_country_code_3_digit\":\"\",\"statement_date\":\"\",\"gross_income\":\"\",\"net_income\":"
				+ "\"\",\"total_current_assets\":\"\",\"total_assets\":\"\",\"total_long_term_liabilities\":"
				+ "\"\",\"total_current_liabilities\":\"\",\"total_matched_shareholders\":\"\",\"financial_strength\":"
				+ "\"\"},\"risk_profile\":{\"country_risk_indicator\":\"\",\"risk_trend\":\"\",\"risk_direction\":"
				+ "\"\",\"updated_risk\":\"\",\"failure_score\":\"\",\"delinquency_failure_score\":\"\","
				+ "\"continent\":\"\",\"country\":\"\",\"state_country\":\"\",\"duns_number\":\"\",\"trading_styles\":"
				+ "\"\",\"us1987_primary_SIC_4_digit\":\"\",\"financial_figures_month\":\"\",\"financial_figures_year\":"
				+ "\"\",\"financial_year_end_date\":\"\",\"annual_sales\":\"\",\"modelled_annual_sales\":"
				+ "\"\",\"net_worth_amount\":\"\",\"modelled_net_worth\":\"\",\"location_type\":\"\",\"import_export_indicator\":"
				+ "\"\",\"domestic_ultimate_record\":\"\",\"global_ultimate_record\":\"\",\"group_structure_number_of_levels\":"
				+ "\"\",\"headquarter_details\":\"\",\"immediate_parent_details\":\"\",\"domestic_ultimate_parent_details\":"
				+ "\"\",\"global_ultimate_parent_details\":\"\",\"credit_limit\":\"\","
				+ "\"risk_rating\":\"\",\"profit_loss\":\"\"},\"affiliate_number\":\"A00A0399\"}");
		paymentInDBDto.setAccComplianceStatus("ACTIVE");
		paymentInDBDto.setAccountId(56108);
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogData = new ArrayList<>();
		ActivityLogDataWrapper data = new ActivityLogDataWrapper();
		data.setActivity("Third Party whitelist Check Fail");
		data.setActivityType("Payment-In-STP");
		data.setCreatedBy("cd.comp.system");
		data.setCreatedOn("01/04/2021 14:08:57");
		ActivityLogDataWrapper data1 = new ActivityLogDataWrapper();
		data1.setActivity("For CONTACT, After repeat CUSTOM check status changed from SERVICE_FAILURE to FAIL");
		data1.setActivityType("Payment-In-STP");
		data1.setCreatedBy("cd.comp.system");
		data1.setCreatedOn("01/04/2021 14:08:57");
		ActivityLogDataWrapper data2 = new ActivityLogDataWrapper();
		data2.setActivity("Custom check service is unavailable");
		data2.setActivityType("Payment-In-STP");
		data2.setCreatedBy("cd.comp.system");
		data2.setCreatedOn("01/04/2021 14:08:57");
		activityLogData.add(data);
		activityLogData.add(data1);
		activityLogData.add(data2);
		activityLogs.setActivityLogData(activityLogData);
		paymentInDBDto.setActivityLogs(activityLogs);
		paymentInDBDto.setAlertComplianceLog("----");
		paymentInDBDto.setAmount("150000.00000000");
		paymentInDBDto.setConComplianceStatus("ACTIVE");
		paymentInDBDto.setContactAttib("{\"onQueue\":false,\"contact_sf_id\":\"0037a00001Hv78vAAB\","
				+ "\"trade_contact_id\":6641757,\"title\":\"Dr\",\"pref_name\":\"\","
				+ "\"first_name\":\"AIP\",\"middle_name\":\"\",\"last_name\":\"CD 1\","
				+ "\"second_surname\":\"\",\"mothers_surname\":\"\",\"dob\":\"1980-03-16\","
				+ "\"position_of_significance\":\"\",\"authorised_signatory\":\"true\","
				+ "\"job_title\":\"Other\",\"address_type\":\"Current Address\",\"address1\":"
				+ "\"KFC Street\",\"town_city_muncipalty\":\"London\",\"building_name\":"
				+ "\"KFC Street\",\"street\":\"KFC Street\",\"street_number\":\"\",\"street_type\":"
				+ "\"\",\"post_code\":\"SE17\",\"post_code_lat\":0.0,\"post_code_long\":0.0,"
				+ "\"country_of_residence\":\"CHN\",\"home_phone\":\"\",\"work_phone\":"
				+ "\"\",\"work_phone_ext\":\"\",\"mobile_phone\":\"+91-7218734151\",\"primary_phone\":"
				+ "\"Mobile\",\"email\":\"aip1@mailinator.com\",\"second_email\":\"\",\"designation\":"
				+ "\"Other\",\"ip_address\":\"127.10.0.1\",\"ip_address_latitude\":\"0.00\","
				+ "\"ip_address_longitude\":\"0.00\",\"is_primary_contact\":true,\"country_of_nationality\":"
				+ "\"GBR\",\"gender\":\"Male\",\"occupation\":\"Other\",\"passport_number\":\"\","
				+ "\"passport_country_code\":\"\",\"passport_exipry_date\":\"\",\"passport_full_name\":"
				+ "\"\",\"passport_mrz_line_1\":\"\",\"passport_mrz_line_2\":\"\",\"passport_birth_family_name\":"
				+ "\"\",\"passport_name_at_citizen\":\"\",\"passport_birth_place\":\"\",\"driving_license\":"
				+ "\"\",\"driving_version_number\":\"\",\"driving_license_card_number\":\"\",\"driving_license_country\":"
				+ "\"\",\"driving_state_code\":\"\",\"driving_expiry\":\"\",\"medicare_card_number\":\"\","
				+ "\"medicare_ref_number\":\"\",\"australia_rta_card_number\":\"\",\"state_province_county\":"
				+ "\"London\",\"municipality_of_birth\":\"\",\"country_of_birth\":\"\",\"state_of_birth\":"
				+ "\"\",\"civic_number\":\"\",\"sub_building\":\"\",\"unit_number\":\"\",\"sub_city\":"
				+ "\"London\",\"national_id_type\":\"\",\"national_id_number\":\"\",\"years_in_address\":"
				+ "\"\",\"residential_status\":\"\",\"version\":1,\"district\":\"\",\"area_number\":\"\","
				+ "\"aza\":\"London\",\"prefecture\":\"London\",\"floor_number\":\"\"}");
		paymentInDBDto.setContactId(57014);
		paymentInDBDto.setContactName("AIP CD 1");
		paymentInDBDto.setContractNumber("0202000010054076-003854855");
		paymentInDBDto.setCountry("GBR");
		paymentInDBDto.setCountryOfFundFullName("United Kingdom");
		paymentInDBDto.setCrmContactId("0037a00001Hv78vAAB");
		paymentInDBDto.setCrmAccountId("0017a00001WmWAqAAN");
		paymentInDBDto.setDateOfPayment("2021-03-17 09:49:51.0");
		paymentInDBDto.setDebitorName("Amazon Pay_IN GBPACCount");
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
		paymentInDBDto.setDocumentList(documentList);
		Map<String, List<EventDBDto>> eventDBDtos = new HashMap<>();
		List<EventDBDto> eventDBDtoList = new ArrayList<>();
		EventDBDto eventDBDto = new EventDBDto();
		eventDBDto.setEitityType("DEBTOR");
		eventDBDto.setServiceType("Sanction");
		eventDBDto.setEntityId("341535");
		eventDBDto.setId(1026278);
		eventDBDto.setStatus("Pass");
		eventDBDto.setSummary("{\"status\":\"PASS\",\"sanctionId\":\"002-D-0000341535\","
				+ "\"ofacList\":\"Safe\",\"worldCheck\":\"Safe\",\"updatedOn\":\"2021-03-26 11:29:47.428\","
				+ "\"providerName\":\"FINSCAN\",\"providerMethod\":\"SLLookupMulti\"}");
		eventDBDto.setUpdatedBy("cd.comp.system");
		eventDBDto.setUpdatedOn(Timestamp.valueOf("2021-04-01 14:08:55.933"));
		List<EventDBDto> eventDBDtoList1 = new ArrayList<>();
		EventDBDto eventDBDto1 = new EventDBDto();
		eventDBDto1.setEitityType("ACCOUNT");
		eventDBDto1.setServiceType("VELOCITYCHECK");
		eventDBDto1.setEntityId("56108");
		eventDBDto1.setId(1056322);
		eventDBDto1.setStatus("Pass");
		eventDBDto1.setSummary("{\"countryWhitelisted\":false,\"countryWhitelistedForFundsIn\":false,\"overallStatus\":\"FAIL\",\"velocityCheck\":{\"status\":\"NOT_REQUIRED\",\"noOffundsoutTxn\":\"NOT_REQUIRED\",\"permittedAmoutcheck\":\"NOT_REQUIRED\",\"beneCheck\":\"NOT_REQUIRED\"},\"whiteListCheck\":{\"currency\":\"PASS\",\"amoutRange\":\"NOT_REQUIRED\",\"thirdParty\":\"FAIL\",\"reasonOfTransfer\":\"NOT_REQUIRED\",\"status\":\"FAIL\"},\"accountWhiteList\":{\"orgCode\":\"Currencies Direct\",\"accountId\":56108,\"approvedReasonOfTransList\":[\"Bill payments\"],\"approvedBuyCurrencyAmountRangeList\":[{\"code\":\"CNY\",\"txnAmountUpperLimit\":50000.0}],\"approvedSellCurrencyAmountRangeList\":[{\"code\":\"USD\",\"txnAmountUpperLimit\":50000.0},{\"code\":\"GBP\",\"txnAmountUpperLimit\":150000.0}],\"approvedThirdpartyAccountList\":[],\"approvedHighRiskCountryList\":[],\"documentationRequiredWatchlistSellCurrency\":[],\"usClientListBBeneAccNumber\":[],\"approvedOPIAccountNumber\":[],\"approvedHighRiskCountryListForFundsIn\":[]},\"firstCreditCheck\":{\"status\":\"NOT_REQUIRED\"},\"euPoiCheck\":{\"status\":\"NOT_REQUIRED\"}}");
		eventDBDto1.setUpdatedBy("cd.comp.system");
		eventDBDto1.setUpdatedOn(Timestamp.valueOf("2021-04-01 14:08:55.933"));
		List<EventDBDto> eventDBDtoList2 = new ArrayList<>();
		EventDBDto eventDBDto2 = new EventDBDto();
		eventDBDto2.setEitityType("CONTACT");
		eventDBDto2.setServiceType("BLACKLIST");
		eventDBDto2.setEntityId("341535");
		eventDBDto2.setId(1056276);
		eventDBDto2.setStatus("Pass");
		eventDBDto2.setSummary("{\"status\":\"PASS\",\"ip\":\"NOT_REQUIRED\",\"email\":\"NOT_REQUIRED\","
				+ "\"accountNumber\":\"false\",\"accNumberMatchedData\":\"No Match Found\",\"name\":\"false\","
				+ "\"nameMatchedData\":\"No Match Found\",\"domain\":"
				+ "\"NOT_REQUIRED\",\"webSite\":\"NOT_REQUIRED\",\"bankName\":\"NOT_REQUIRED\"}");
		eventDBDto2.setUpdatedBy("cd.comp.system");
		eventDBDto2.setUpdatedOn(Timestamp.valueOf("2021-04-01 14:08:55.933"));
		List<EventDBDto> eventDBDtoList3 = new ArrayList<>();
		EventDBDto eventDBDto3 = new EventDBDto();
		eventDBDto3.setEitityType("CONTACT");
		eventDBDto3.setServiceType("COUNTRYCHECK");
		eventDBDto3.setEntityId("341535");
		eventDBDto3.setId(1056277);
		eventDBDto3.setStatus("Pass");
		eventDBDto3.setSummary("{\"status\":\"PASS\",\"country\":\"United Kingdom\",\"riskLevel\":\"L\"}");
		eventDBDto3.setUpdatedBy("cd.comp.system");
		eventDBDto3.setUpdatedOn(Timestamp.valueOf("2021-04-01 14:08:55.933"));
		List<EventDBDto> eventDBDtoList4 = new ArrayList<>();
		EventDBDto eventDBDto4 = new EventDBDto();
		eventDBDto4.setEitityType("CONTACT");
		eventDBDto4.setServiceType("FRAUGSTER");
		eventDBDto4.setEntityId("57014");
		eventDBDto4.setId(1056274);
		eventDBDto4.setStatus("Not Required");
		eventDBDto4
				.setSummary("{\"status\":\"NOT_REQUIRED\",\"frgTransId\":\"Not Required\",\"score\":\"NOT_PERFORMED\","
						+ "\"fraugsterApproved\":\"Not Required\",\"cdTrasId\":\"3415350202000010054076\"}");
		eventDBDto4.setUpdatedBy("cd.comp.system");
		eventDBDto4.setUpdatedOn(Timestamp.valueOf("2021-04-01 14:08:55.933"));
		eventDBDtoList.add(eventDBDto);
		eventDBDtoList1.add(eventDBDto1);
		eventDBDtoList2.add(eventDBDto2);
		eventDBDtoList3.add(eventDBDto3);
		eventDBDtoList4.add(eventDBDto4);
		eventDBDtos.put("SANCTIONDEBTOR", eventDBDtoList);
		eventDBDtos.put("VELOCITYCHECKACCOUNT", eventDBDtoList1);
		eventDBDtos.put("BLACKLISTCONTACT", eventDBDtoList2);
		eventDBDtos.put("COUNTRYCHECKCONTACT", eventDBDtoList3);
		eventDBDtos.put("FRAUGSTERCONTACT", eventDBDtoList4);
		paymentInDBDto.setEventDBDtos(eventDBDtos);
		FurtherPaymentDetails furtherpaymentDetails = new FurtherPaymentDetails();
		List<FurtherPaymentInDetails> furtherPaymentInDetails = new ArrayList<>();
		FurtherPaymentInDetails furtherPaymentInDetail = new FurtherPaymentInDetails();
		furtherPaymentInDetail.setAccount("40478102671337");
		furtherPaymentInDetail.setAccountName("Amazon Pay_IN GBPACCount");
		furtherPaymentInDetail.setAmount("150.000");
		furtherPaymentInDetail.setDateOfPayment("17/03/2021");
		furtherPaymentInDetail.setMethod("BACS/CHAPS/TT");
		furtherPaymentInDetail.setRiskGuardianScore("-");
		furtherPaymentInDetail.setSellCurrency("GBR");
		furtherPaymentInDetail.setTradeContractNumber("0202000010054076-003854857");
		furtherPaymentInDetails.add(furtherPaymentInDetail);
		furtherpaymentDetails.setFurtherPaymentInDetails(furtherPaymentInDetails);
		furtherpaymentDetails.setPayInDetailsTotalRecords(3);

		paymentInDBDto.setFurtherpaymentDetails(furtherpaymentDetails);
		paymentInDBDto.setInitialStatus("NSTP;CUSNA");
		paymentInDBDto.setIsDeleted("false");
		paymentInDBDto.setIsOnQueue(true);
		paymentInDBDto.setLegalEntity("CDLGB");
		paymentInDBDto.setNationalityFullName("------");
		paymentInDBDto.setNoOfContactsForAccount(1);
		paymentInDBDto.setOrgCode("Currencies Direct");
		paymentInDBDto.setPaymentInId(341535);
		paymentInDBDto.setPaymentInAttributes(
				"{\"org_code\":\"Currencies Direct\",\"source_application\":\"Titan\",\"trade\":{\"trade_account_number\":\"0202000010054076\",\"trade_contact_id\":6641757,"
						+ "\"cust_type\":\"CFX\",\"purpose_of_trade\":\"POT_OTHER\",\"payment_fundsIN_Id\":13854855,\"selling_amount\":150000.0,"
						+ "\"selling_amount_base_value\":150000.0,\"transaction_currency\":\"GBP\",\"contract_number\":"
						+ "\"0202000010054076-003854855\",\"payment_Method\":\"BACS/CHAPS/TT\",\"third_party_payment\":true,"
						+ "\"payment_time\":\"2021-03-17T09:49:51Z\",\"debtor_name\":\"Amazon Pay_IN GBPACCount\",\"debtor_account_number\":"
						+ "\"89895858\",\"bill_ad_zip\":\"London\",\"country_of_fund\":\"GBR\",\"customer_legal_entity\":\"CDLGB\"},"
						+ "\"debtorAccountNumber\":\"89895858\",\"osr_id\":\"9e89a426-de13-4a8f-9912-3ef2ba8829a6\",\"cust_type\":\"CFX\",\"type\":\"FUNDS_IN_ADD\"}");
		paymentInDBDto.setPaymentInStatus("HOLD");
		paymentInDBDto.setPaymentMethod("BACS/CHAPS/TT");
		paymentInDBDto.setPoiExists("0");
		paymentInDBDto.setRegComp(Timestamp.valueOf("2021-03-26 11:26:47.047"));
		paymentInDBDto.setRegIn(Timestamp.valueOf("2021-03-26 11:21:42.697"));
		paymentInDBDto.setSellCurrency("GBP");
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
		paymentInDBDto.setStatusReason(contactStatusReason);
		Status status = new Status();
		List<StatusData> statuses = new ArrayList<>();
		StatusData statusData = new StatusData();
		statusData.setIsSelected(false);
		statusData.setStatus("CLEAR");
		StatusData statusData1 = new StatusData();
		statusData.setIsSelected(false);
		statusData.setStatus("HOLD");
		StatusData statusData2 = new StatusData();
		statusData.setIsSelected(false);
		statusData.setStatus("REJECT");
		StatusData statusData3 = new StatusData();
		statusData.setIsSelected(false);
		statusData.setStatus("SEIZE");
		StatusData statusData4 = new StatusData();
		statusData.setIsSelected(false);
		statusData.setStatus("REVERSED");
		statuses.add(statusData);
		statuses.add(statusData1);
		statuses.add(statusData2);
		statuses.add(statusData3);
		statuses.add(statusData4);
		status.setStatuses(statuses);
		paymentInDBDto.setStatus(status);
		paymentInDBDto.setThirdPartyPayment(true);
		paymentInDBDto.setTradeAccountNum("0202000010054076");
		paymentInDBDto.setTradeContactId("6641757");
		paymentInDBDto.setTradePaymentId("13854855");
		paymentInDBDto.setUpdatedOn("01/04/2021 14:08:56");
		paymentInDBDto.setUserResourceId(0);
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
		paymentInDBDto.setWatchList(watachList);
		return paymentInDBDto;
	}

	private PaymentInDetailsDto getPaymentInDetailsDto() {
		PaymentInDetailsDto paymentInDetailsDto = new PaymentInDetailsDto();
		AccountWrapper account = new AccountWrapper();
		account.setTradeAccountNumber("0202000010054076");
		account.setPurposeOfTran("Bill payments");
		account.setServiceRequired("Transfer");
		account.setSourceOfFund("Savings");
		account.setSource("Internet");
		account.setWebsite("www.google.com");
		account.setAffiliateName("Marketing HO");
		paymentInDetailsDto.setAccount(account);
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogData = new ArrayList<>();
		ActivityLogDataWrapper data = new ActivityLogDataWrapper();
		data.setActivity("Third Party whitelist Check Fail");
		data.setActivityType("Payment-In-STP");
		data.setCreatedBy("cd.comp.system");
		data.setCreatedOn("01/04/2021 14:08:57");
		ActivityLogDataWrapper data1 = new ActivityLogDataWrapper();
		data1.setActivity("For CONTACT, After repeat CUSTOM check status changed from SERVICE_FAILURE to FAIL");
		data1.setActivityType("Payment-In-STP");
		data1.setCreatedBy("cd.comp.system");
		data1.setCreatedOn("01/04/2021 14:08:57");
		ActivityLogDataWrapper data2 = new ActivityLogDataWrapper();
		data2.setActivity("Custom check service is unavailable");
		data2.setActivityType("Payment-In-STP");
		data2.setCreatedBy("cd.comp.system");
		data2.setCreatedOn("01/04/2021 14:08:57");
		activityLogData.add(data);
		activityLogData.add(data1);
		activityLogData.add(data2);
		activityLogs.setActivityLogData(activityLogData);
		paymentInDetailsDto.setActivityLogs(activityLogs);
		paymentInDetailsDto.setAlertComplianceLog("----");
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
		paymentInDetailsDto.setContactWatchlist(watachList);
		ContactWrapper currentContact = new ContactWrapper();
		currentContact.setTradeContactID(6641757);
		currentContact.setDob("16/03/1980");
		currentContact.setPhoneWork("----");
		currentContact.setEmail("aip1@mailinator.com");
		currentContact.setNationality("GBR");
		currentContact.setOccupation("Other");
		paymentInDetailsDto.setCurrentContact(currentContact);
		CustomCheck customCheck = new CustomCheck();
		CountryCheck countryCheck = new CountryCheck();
		countryCheck.setCheckedOn("26/03/2021 11:29:43");
		countryCheck.setCountry("United Kingdom");
		countryCheck.setCountryCheckTotalRecords(1);
		countryCheck.setEntityType("CONTACT");
		countryCheck.setFailCount(0);
		countryCheck.setId(1056277);
		countryCheck.setPassCount(1);
		countryCheck.setIsRequired(true);
		countryCheck.setStatus("Pass");
		countryCheck.setRiskLevel("(Low Risk Country)");
		customCheck.setCountryCheck(countryCheck);
		PaymentOutCustomCheck paymentOutCustomCheck = new PaymentOutCustomCheck();
		paymentOutCustomCheck.setCheckedOn("01/04/2021 14:08:55");
		paymentOutCustomCheck.setEntityType("Account");
		EuPoiCheck euPoiCheck = new EuPoiCheck();
		euPoiCheck.setStatus("Not Required");
		paymentOutCustomCheck.setEuPoiCheck(euPoiCheck);
		paymentOutCustomCheck.setFailCount(0);
		paymentOutCustomCheck.setId(1056322);
		FirstCreditCheck firstCreditCheck = new FirstCreditCheck();
		firstCreditCheck.setStatus("Not Required");
		paymentOutCustomCheck.setFirstCreditCheck(firstCreditCheck);
		paymentOutCustomCheck.setIsRequired(true);
		paymentOutCustomCheck.setPassCount(0);
		paymentOutCustomCheck.setStatus("Fail");
		WhitelistCheck whiteListCheck = new WhitelistCheck();
		whiteListCheck.setAmoutRange("Not Required");
		whiteListCheck.setCurrency("Pass");
		whiteListCheck.setThirdParty("Not Required");
		whiteListCheck.setThirdParty("Fail");
		paymentOutCustomCheck.setWhiteListCheck(whiteListCheck);
		customCheck.setPaymentOutCustomCheck(paymentOutCustomCheck);
		paymentInDetailsDto.setCustomCheck(customCheck);
		Blacklist debitorBlacklist = new Blacklist();
		debitorBlacklist.setAccNumberMatchedData("(No Match Found)");
		debitorBlacklist.setBankName("Not Required");
		debitorBlacklist.setAccountNumber("false");
		debitorBlacklist.setEmail("NOT_REQUIRED");
		debitorBlacklist.setFailCount(0);
		debitorBlacklist.setId(1056276);
		debitorBlacklist.setEntityType("CONTACT");
		debitorBlacklist.setIp("NOT_REQUIRED");
		debitorBlacklist.setIsRequired(true);
		debitorBlacklist.setName("false");
		debitorBlacklist.setPhone(false);
		debitorBlacklist.setPhoneMatchedData("(No Match Found)");
		debitorBlacklist.setStatus(true);
		debitorBlacklist.setNameMatchedData("(No Match Found)");
		paymentInDetailsDto.setDebitorBlacklist(debitorBlacklist);
		Fraugster fraugster = new Fraugster();
		fraugster.setCreatedOn("26/03/2021 11:29:42");
		fraugster.setFraugsterId("Not Required");
		fraugster.setId(1056274);
		fraugster.setFraugsterTotalRecords(1);
		fraugster.setScore("NOT_PERFORMED");
		fraugster.setUpdatedBy("cd.comp.system");
		fraugster.setIsRequired(false);
		paymentInDetailsDto.setFraugster(fraugster);
		FurtherPaymentDetails furtherpaymentDetails = new FurtherPaymentDetails();
		List<FurtherPaymentInDetails> furtherPaymentInDetails = new ArrayList<>();
		FurtherPaymentInDetails furtherPaymentInDetail = new FurtherPaymentInDetails();
		furtherPaymentInDetail.setAccount("40478102671337");
		furtherPaymentInDetail.setAccountName("Amazon Pay_IN GBPACCount");
		furtherPaymentInDetail.setAmount("150.000");
		furtherPaymentInDetail.setDateOfPayment("17/03/2021");
		furtherPaymentInDetail.setMethod("BACS/CHAPS/TT");
		furtherPaymentInDetail.setRiskGuardianScore("-");
		furtherPaymentInDetail.setSellCurrency("GBR");
		furtherPaymentInDetail.setTradeContractNumber("0202000010054076-003854857");
		furtherPaymentInDetails.add(furtherPaymentInDetail);
		furtherpaymentDetails.setFurtherPaymentInDetails(furtherPaymentInDetails);
		furtherpaymentDetails.setPayInDetailsTotalRecords(3);
		paymentInDetailsDto.setFurtherPaymentDetails(furtherpaymentDetails);
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
		paymentInDetailsDto.setDocumentList(documentList);
		paymentInDetailsDto.setIsOnQueue(true);
		paymentInDetailsDto.setName("AIP ONE");
		paymentInDetailsDto.setNoOfContactsForAccount(1);
		paymentInDetailsDto.setPoiExists("0");
		PaymentInInfo paymentInInfo = new PaymentInInfo();
		paymentInInfo.setAmount("150.000");
		paymentInInfo.setCountryOfFund("GBR");
		paymentInInfo.setCountryOfFundFullName("United Kingdom");
		paymentInInfo.setDateOfPayment("17/03/2021");
		paymentInInfo.setDebitorName("Amazon Pay_IN GBPACCount");
		paymentInInfo.setDebtorAccountNumber("89895858");
		paymentInInfo.setId(341535);
		paymentInInfo.setInitialStatus("NSTP;CUSNA");
		paymentInInfo.setIsDeleted("----");
		paymentInInfo.setLegalEntity("CDLGB");
		paymentInInfo.setNameOnCard("Amazon Pay_IN GBPACCount");
		paymentInInfo.setPaymentMethod("BACS/CHAPS/TT");
		paymentInInfo.setSellCurrency("GBP");
		paymentInInfo.setStatus("HOLD");
		paymentInInfo.setThirdPartyPayment(true);
		paymentInInfo.setTradePaymentId("13854855");
		paymentInInfo.setTransactionNumber("0202000010054076-003854855");
		paymentInInfo.settScore("----");
		paymentInInfo.setUpdatedOn("----");
		paymentInDetailsDto.setPaymentInInfo(paymentInInfo);
		paymentInDetailsDto.setThirdPartyPayment(true);
		paymentInDetailsDto.setWatchlist(watachList);
		return paymentInDetailsDto;
	}
/*
	@Test
	public void testTransform() {
		PaymentInDetailsDto expectedResult = getPaymentInDetailsDto();
		PaymentInDetailsDto actualResult = paymentInDetailsTransformer.transform(getPaymentInDBDto());
		assertEquals(expectedResult.getDebitorBlacklist().getDomain(), actualResult.getDebitorBlacklist().getDomain());
		assertEquals(expectedResult.getDebitorBlacklist().getName(), actualResult.getDebitorBlacklist().getName());
		assertEquals(expectedResult.getDebitorBlacklist().getEmail(), actualResult.getDebitorBlacklist().getEmail());
		assertEquals(expectedResult.getDebitorBlacklist().getAccountNumber(),
				actualResult.getDebitorBlacklist().getAccountNumber());
		assertEquals(expectedResult.getDebitorBlacklist().getIp(), actualResult.getDebitorBlacklist().getIp());
		assertEquals(expectedResult.getDebitorBlacklist().getBankName(),
				actualResult.getDebitorBlacklist().getBankName());

	}
*/
}
