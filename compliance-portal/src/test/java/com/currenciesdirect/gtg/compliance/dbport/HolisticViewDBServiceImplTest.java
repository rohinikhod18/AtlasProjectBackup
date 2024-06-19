package com.currenciesdirect.gtg.compliance.dbport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Company;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.RiskProfile;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticAccount;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticContact;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewRequest;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewResponse;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInSummary;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutSummary;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public class HolisticViewDBServiceImplTest {
	
	@InjectMocks
	HolisticViewDBServiceImpl holisticViewDBServiceImpl;
	
	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet rs;
	@Mock
	ResultSet resultSet;
	@Mock
	AbstractDao abstractDao;
	
	
	@Before
	public void setUp() 
	{
		MockitoAnnotations.initMocks(this);
		try {
			Mockito.when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(preparedStatement.getResultSet()).thenReturn(rs);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false); 
			Mockito.when(preparedStatement.execute()).thenReturn(true); 
		} catch (SQLException e)
		{
			System.out.println(e);
		}
		
	}
	
	public void setMockForGetHolisticAccountDetails()
	{
		try {
			
			when(rs.getInt("AccountID")).thenReturn(52973);
			when(rs.getInt("ContactID")).thenReturn(53867);
			when(rs.getString("AccountStatus")).thenReturn("INACTIVE");
			when(rs.getString("Name")).thenReturn("EID  Clark");
			when(rs.getString("complianceStatus")).thenReturn("INACTIVE");
			when(rs.getString("Accountattribute")).thenReturn("{\"onQueue\":false,\"acc_sf_id\":\"0016E000020efr0AYL\",\"trade_acc_id\":10026277,\"trade_acc_number\":\"0201040010026056\",\"branch\":\"USA\",\"channel\":\"ANDROIDAPP\",\"cust_type\":\"PFX\",\"act_name\":\"EID Clark\",\"country_interest\":\"\",\"trade_name\":\"\",\"trasaction_purpose\":\"\",\"op_country\":\"\",\"turnover\":\"\",\"service_req\":\"Transfer\",\"buying_currency\":\"EUR\",\"selling_currency\":\"GBP\",\"txn_value\":\"2,000 - 5,000\",\"source_of_fund\":\"\",\"source\":\"Web\",\"sub_source\":\"Natural\",\"referral\":\"\",\"referral_text\":\"Natural\",\"extended_referral\":\"\",\"ad_campaign\":\"\",\"keywords\":\"\",\"search_engine\":\"\",\"website\":\"\",\"affiliate_name\":\"Marketing USA\",\"reg_mode\":\"Mobile\",\"organization_legal_entity\":\"CDLEU\",\"version\":1,\"reg_date_time\":\"2019-12-22T13:51:33Z\",\"company\":{\"billing_address\":\"Sørliveien 38,Lørenskog,Akershus,1473,Norway\",\"company_phone\":\"\",\"shipping_address\":\"\",\"vat_no\":\"\",\"country_region\":\"\",\"country_of_establishment\":\"\",\"corporate_type\":\"\",\"registration_no\":\"\",\"incorporation_date\":\"\",\"industry\":\"\",\"etailer\":\"false\",\"option\":\"false\",\"type_of_financial_account\":\"\",\"ccj\":\"false\",\"ongoing_due_diligence_date\":\"\",\"est_no_transactions_pcm\":\"\"},\"corporate_compliance\":{\"sic\":\"\",\"former_name\":\"\",\"legal_form\":\"\",\"foreign_owned_company\":\"\",\"net_worth\":\"\",\"fixed_assets\":\"\",\"total_liabilities_and_equities\":\"\",\"total_share_holders\":\"\",\"global_ultimate_DUNS\":\"\",\"global_ultimate_name\":\"\",\"global_ultimate_country\":\"\",\"registration_date\":\"\",\"match_name\":\"\",\"iso_country_code_2_digit\":\"\",\"iso_country_code_3_digit\":\"\",\"statement_date\":\"\",\"gross_income\":\"\",\"net_income\":\"\",\"total_current_assets\":\"\",\"total_assets\":\"\",\"total_long_term_liabilities\":\"\",\"total_current_liabilities\":\"\",\"total_matched_shareholders\":\"\",\"financial_strength\":\"\"},\"risk_profile\":{\"country_risk_indicator\":\"\",\"risk_trend\":\"\",\"risk_direction\":\"\",\"updated_risk\":\"\",\"failure_score\":\"\",\"delinquency_failure_score\":\"\",\"continent\":\"\",\"country\":\"\",\"state_country\":\"\",\"duns_number\":\"\",\"trading_styles\":\"\",\"us1987_primary_SIC_4_digit\":\"\",\"financial_figures_month\":\"\",\"financial_figures_year\":\"\",\"financial_year_end_date\":\"\",\"annual_sales\":\"\",\"modelled_annual_sales\":\"\",\"net_worth_amount\":\"\",\"modelled_net_worth\":\"\",\"location_type\":\"\",\"import_export_indicator\":\"\",\"domestic_ultimate_record\":\"\",\"global_ultimate_record\":\"\",\"group_structure_number_of_levels\":\"\",\"headquarter_details\":\"\",\"immediate_parent_details\":\"\",\"domestic_ultimate_parent_details\":\"\",\"global_ultimate_parent_details\":\"\",\"credit_limit\":\"\",\"risk_rating\":\"\",\"profit_loss\":\"\"},\"conversionPrediction\":{\"AccountId\":\"0010X00004laSxKQAU\",\"ETVBand\":\"25k - 100k\",\"conversionFlag\":\"Low\",\"conversionProbability\":0.23185803},\"affiliate_number\":\"A010069\"}");
			when(rs.getString("Contactattribute")).thenReturn("{\"onQueue\":false,\"contact_sf_id\":\"0036E00000eRlFrASL\",\"trade_contact_id\":6648836,\"title\":\"Mrs\",\"pref_name\":\"\",\"first_name\":\"EID ADD7\",\"middle_name\":\"\",\"last_name\":\"Clark\",\"second_surname\":\"\",\"mothers_surname\":\"\",\"dob\":\"1969-10-07\",\"position_of_significance\":\"\",\"authorised_signatory\":\"true\",\"job_title\":\"Authorise Trader\",\"address_type\":\"Current Address\",\"address1\":\"104 Shrewesbury Raod\",\"town_city_muncipalty\":\"London\",\"building_name\":\"104 Shrewesbury Raod\",\"street\":\"Shrewesbury Raod\",\"street_number\":\"\",\"street_type\":\"\",\"post_code\":\"N11 2JU\",\"post_code_lat\":0.0,\"post_code_long\":0.0,\"country_of_residence\":\"GBR\",\"home_phone\":\"+44-2089054779\",\"work_phone\":\"+44-2089054779\",\"work_phone_ext\":\"\",\"mobile_phone\":\"+44-2089054779\",\"primary_phone\":\"Work\",\"email\":\"default2239934@currenciesdirect.com\",\"second_email\":\"\",\"designation\":\"Authorise Trader\",\"ip_address\":\"127.0.0.1\",\"ip_address_latitude\":\"0.00\",\"ip_address_longitude\":\"0.00\",\"is_primary_contact\":false,\"country_of_nationality\":\"GBR\",\"gender\":\"Female\",\"occupation\":\"Authorise Trader\",\"passport_number\":\"\",\"passport_country_code\":\"\",\"passport_exipry_date\":\"\",\"passport_full_name\":\"\",\"passport_mrz_line_1\":\"\",\"passport_mrz_line_2\":\"\",\"passport_birth_family_name\":\"\",\"passport_name_at_citizen\":\"\",\"passport_birth_place\":\"\",\"driving_license\":\"\",\"driving_version_number\":\"\",\"driving_license_card_number\":\"\",\"driving_license_country\":\"\",\"driving_state_code\":\"\",\"driving_expiry\":\"\",\"medicare_card_number\":\"\",\"medicare_ref_number\":\"\",\"australia_rta_card_number\":\"\",\"state_province_county\":\"\",\"municipality_of_birth\":\"\",\"country_of_birth\":\"\",\"state_of_birth\":\"\",\"civic_number\":\"\",\"sub_building\":\"\",\"house_building_number\":\"104\",\"unit_number\":\"\",\"sub_city\":\"London\",\"national_id_type\":\"\",\"national_id_number\":\"\",\"years_in_address\":\"\",\"residential_status\":\"\",\"version\":1,\"district\":\"\",\"area_number\":\"\",\"aza\":\"London\",\"prefecture\":\"\",\"floor_number\":\"\"}");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	public void setMockForPaymentInDetails()
	{
		try {
		when(rs.getBoolean("vThirdPartyPayment")).thenReturn(true);
		when(rs.getBoolean("Deleted")).thenReturn(false);
		when(rs.getString("vTransactionCurrency")).thenReturn("GBP");
		when(rs.getString("Attributes")).thenReturn("{\"org_code\":\"Currencies Direct\",\"source_application\":"
				+ "\"Currencies Direct Titan \",\"trade\":{\"trade_account_number\":"
				+ "\"03010000100056322\",\"trade_contact_id\":66365321,\"cust_type\":\"PFX\","
				+ "\"purpose_of_trade\":\"Others\",\"payment_fundsIN_Id\":25105015,\"selling_amount\":700.0,"
				+ "\"selling_amount_base_value\":0.0,\"transaction_currency\":\"GBP\",\"contract_number\":"
				+ "\"0201000006480330-902605621\",\"payment_Method\":\"BACS/CHAPS/TT\",\"av_trade_value\":"
				+ "\"0\",\"av_trade_frequency\":\"0\",\"third_party_payment\":true,\"turnover\":\"7000.00\","
				+ "\"transaction_reference\":\"230818\",\"payment_time\":\"2019-11-05T13:05:06Z\",\"debtor_name\":\"Suresh\","
				+ "\"debtor_account_number\":\"40478102671337\",\"ordering_institution\":\"Currencies Direct\","
				+ "\"customer_legal_entity\":\"CDLCA\"},\"device_info\":{\"browser_user_agent\":"
				+ "\"eyJ0aW1lIjoiMjAxOC0wOC0yM1QxMDo1NToxNCswMTowMCIsInRpbWV6b25lIjotNjAsImx"
				+ "hbmd1YWdlcyI6WyJlbi1HQiJdLCJ1c2VyQWdlbnQiOiJNb3ppbGxhLzUuMCAoV2luZG93cyBOVCAxMC4wOyBX"
				+ "aW42NDsgeDY0KSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvNjQuMC4zMj"
				+ "gyLjE0MCBTYWZhcmkvNTM3LjM2IEVkZ2UvMTcuMTcxMzQiLCJwbHVnaW5zIjp7IjAiOnsibmFtZSI6IkVkZ2Ug"
				+ "UERGIFZpZXdlciIsInZlcnNpb24iOiIifX0sImh0bWw1Ijp7ImFwcGxpY2F0aW9uY2FjaGUiOnRydWUsImNhbn"
				+ "ZhcyI6dHJ1ZSwiY2FudmFzdGV4dCI6dHJ1ZSwiaGFzaGNoYW5nZSI6dHJ1ZSwiaGlzdG9yeSI6dHJ1ZSwiYXVk"
				+ "aW8iOnRydWUsInZpZGVvIjp0cnVlLCJpbmRleGVkZGIiOnRydWUsImlucHV0Ijp7ImF1dG9jb21wbGV0ZSI6dH"
				+ "J1ZSwiYXV0b2ZvY3VzIjp0cnVlLCJsaXN0Ijp0cnVlLCJwbGFjZWhvbGRlciI6dHJ1ZSwibWF4Ijp0cnVlLCJt"
				+ "aW4iOnRydWUsIm11bHRpcGxlIjp0cnVlLCJwYXR0ZXJuIjp0cnVlLCJyZXF1aXJlZCI6dHJ1ZSwic3RlcCI6dH"
				+ "J1ZX0sImlucHV0dHlwZXMiOnsic2VhcmNoIjp0cnVlLCJ0ZWwiOnRydWUsInVybCI6dHJ1ZSwiZW1haWwiOnRyd"
				+ "WUsImRhdGV0aW1lIjpmYWxzZSwiZGF0ZSI6dHJ1ZSwibW9udGgiOnRydWUsIndlZWsiOnRydWUsInRpbWUiOnRy"
				+ "dWUsImRhdGV0aW1lLWxvY2FsIjp0cnVlLCJudW1iZXIiOmZhbHNlLCJyYW5nZSI6dHJ1ZSwiY29sb3IiOnRydWV"
				+ "9LCJsb2NhbHN0b3JhZ2UiOnRydWUsInNlc3Npb25zdG9yYWdlIjp0cnVlLCJwb3N0bWVzc2FnZSI6dHJ1ZSwid"
				+ "Vic29ja2V0cyI6dHJ1ZSwid2Vid29ya2VycyI6dHJ1ZX0sImNzcyI6eyJiYWNrZ3JvdW5kc2l6ZSI6dHJ1ZSwi"
				+ "Ym9yZGVyaW1hZ2UiOnRydWUsImJvcmRlcnJhZGl1cyI6dHJ1ZSwiYm94c2hhZG93Ijp0cnVlLCJmbGV4Ym94Ij"
				+ "p0cnVlLCJmbGV4Ym94bGVnYWN5Ijp0cnVlLCJoc2xhIjp0cnVlLCJyZ2JhIjp0cnVlLCJtdWx0aXBsZWJncyI6"
				+ "dHJ1ZSwib3BhY2l0eSI6dHJ1ZSwidGV4dHNoYWRvdyI6dHJ1ZSwiY3NzYW5pbWF0aW9ucyI6dHJ1ZSwiY3NzY2"
				+ "9sdW1ucyI6dHJ1ZSwiZ2VuZXJhdGVkY29udGVudCI6dHJ1ZSwiY3NzZ3JhZGllbnRzIjp0cnVlLCJjc3NyZWZs"
				+ "ZWN0aW9ucyI6ZmFsc2UsImNzc3RyYW5zZm9ybXMiOnRydWUsImNzc3RyYW5zZm9ybXMzZCI6dHJ1ZSwiY3NzdH"
				+ "hbnNpdGlvbnMiOnRydWV9LCJmbGFzaCI6ZmFsc2UsInN2ZyI6dHJ1ZSwiY29va2llcyI6dHJ1ZSwic2NyZWVuU"
				+ "mVzb2x1dGlvbiI6IjEyODAgeCA3MjAiLCJwaXhlbFJhdGlvIjoxLjUsInNjcmVlblJlc29sdXRpb25OYXRpdmUi"
				+ "OiIxOTIwIHggMTA4MCIsImJyb3dzZXIiOiJDaHJvbWUiLCJicm93c2VyVmVyc2lvbiI6IjY0LjAuMzI4Mi4xNDA"
				+ "iLCJicm93c2VyTWFqb3JWZXJzaW9uIjo2NCwib3MiOiJXaW5kb3dzIiwib3NWZXJzaW9uIjoiMTAifQ==\","
				+ "\"screen_resolution\":\"1280x720 24\",\"brwsr_type\":\"Edge\",\"brwsr_version\":"
				+ "\"17.17134\",\"device_type\":\"Desktop\",\"device_name\":\"\",\"device_version\":\"\","
				+ "\"device_id\":\"\",\"device_manufacturer\":\"\",\"os_type\":\"Windows 10\","
				+ "\"brwsr_lang\":\"en-GB\",\"browser_online\":\"true\",\"os_ts\":"
				+ "\"2018-08-23T09:55:14+05:30\"},\"risk_score\":{},\"debtorAccountNumber\":"
				+ "\"40478102671337\",\"osr_id\":\"5461525c-04b8-4cf5-8c07-61dd8e48502b\","
				+ "\"cust_type\":\"PFX\",\"type\":\"FUNDS_IN_ADD\"}");
		when(rs.getString("OrgCode")).thenReturn("Currencies Direct");
		} catch (SQLException e) {
			System.out.println(e);
		}
		}
	public HolisticViewResponse getHolisticViewResponse()
	{
HolisticViewResponse response= new HolisticViewResponse();
		
		HolisticAccount regCfxAccount= new HolisticAccount();
		regCfxAccount.setAccountName("EID Clark");
		regCfxAccount.setAccSFID("0016E000020efr0AYL");
		regCfxAccount.setAffiliateName("Marketing USA");
		regCfxAccount.setAffiliateNumber("A010069");
		regCfxAccount.setBranch("USA");
		regCfxAccount.setBuyingCurrency("EUR");
		regCfxAccount.setChannel("ANDROIDAPP");
		regCfxAccount.setCustType("PFX");
		regCfxAccount.setLegalEntity("CDLEU");
		regCfxAccount.setPurposeOfTran("----");
		regCfxAccount.setReferralText("Natural");
		regCfxAccount.setRegistrationDateTime("2019-12-22T13:51:33Z");
		regCfxAccount.setRegistrationMode("Mobile");
		regCfxAccount.setSellingCurrency("GBP");
		regCfxAccount.setServiceRequired("Transfer");
		regCfxAccount.setSource("Web");
		regCfxAccount.setSubSource("Natural");
		regCfxAccount.setTradeAccountID(10026277);
		regCfxAccount.setTradeAccountNumber("0201040010026056");
		regCfxAccount.setValueOfTransaction("2,000-5,000");
		regCfxAccount.setWebsite("----");
		Company company= new Company();
		company.setBillingAddress("Sørliveien 38,Lørenskog,Akershus,1473,Norway");
		company.setCcj("false");
		company.setEtailer("false");
		RiskProfile  RiskProfile  =new RiskProfile ();
        regCfxAccount.setCompany(company);
        regCfxAccount.setRiskProfile(null);
		response.setHolisticAccount(regCfxAccount);
		HolisticContact regContact= new HolisticContact();
		regContact.setAddress("104 Shrewesbury Raod");
        regContact.setAddressType("Current Address");
        regContact.setAuthorisedSignatory("true");
        regContact.setAza("London");
        regContact.setBuildingNumber("104 Shrewesbury Raod");
        regContact.setBuildingNumber("104");
        regContact.setSubCity("London");
        regContact.setDesignation("Authorise Trader");
        regContact.setEmail("default2239934@currenciesdirect.com");
        regContact.setFirstName("EID ADD6");
        regContact.setGender("Female");
        regContact.setIpAddress("127.0.0.1");
        regContact.setJobTitle("Authorise Trader");
        regContact.setLastName("Clark");
        regContact.setNationality("GBR");
        regContact.setOccupation("Authorise Trader");
        regContact.setPhoneHome("+44-2089054779");
        regContact.setMobile("+44-2089054779");
        regContact.setPostCode("N11 2JU");
        regContact.setIsPrimaryContact(false);
        regContact.setStreet("Shrewesbury Raod");
        regContact.setSubCity("London");
        regContact.setTitle("Mrs");
        regContact.setTradeContactId("6648835");
		response.setHolisticContact(regContact);
		return response;
	}
	
	@Test
	public void testGetHolisticAccountDetails() {
		setMockForGetHolisticAccountDetails();
		HolisticViewRequest holisticViewRequest= new HolisticViewRequest();
	holisticViewRequest.setAccountId(52973);
		holisticViewRequest.setContactId(53867);
		holisticViewRequest.setCustType("PFX");
		
		try {
			HolisticViewResponse expectedResult=getHolisticViewResponse();
			HolisticViewResponse actualResult=holisticViewDBServiceImpl.getHolisticAccountDetails(holisticViewRequest);
		assertEquals(expectedResult.getHolisticAccount().getAccSFID(),actualResult.getHolisticAccount().getAccSFID());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetHolisticAccountDetails() {
		setMockForGetHolisticAccountDetails();
		HolisticViewRequest holisticViewRequest= new HolisticViewRequest();
		holisticViewRequest.setContactId(53867);
		holisticViewRequest.setCustType("PFX");
		try {
		holisticViewDBServiceImpl.getHolisticAccountDetails(holisticViewRequest);
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
	}

	
public HolisticViewResponse getHolisticViewResponsePaymentIn()
{
	HolisticViewResponse holisticViewResponse=new HolisticViewResponse();
	PaymentInSummary paymentInSummary= new PaymentInSummary();
	paymentInSummary.setDebtorAccountNumber("40478102671337");
	paymentInSummary.setIsDeleted(false);
	paymentInSummary.setSellCurrency("GBP");
	paymentInSummary.setThirdPartyFlag(true);
	holisticViewResponse.setPaymentInSummary(paymentInSummary);
	return holisticViewResponse;
}
	@Test
	public void testGetHolisticPaymentInDetails() {
		setMockForPaymentInDetails();
		HolisticViewRequest holisticViewRequest= new HolisticViewRequest();
		holisticViewRequest.setPaymentInId(281289);
		HolisticViewResponse holisticViewResponse= new HolisticViewResponse();
		try {
			HolisticViewResponse expectedResult=getHolisticViewResponsePaymentIn();
			HolisticViewResponse actualResult=holisticViewDBServiceImpl.getHolisticPaymentInDetails(holisticViewRequest, holisticViewResponse);
			
			assertEquals(expectedResult.getPaymentInSummary().getDebtorAccountNumber(),actualResult.getPaymentInSummary().getDebtorAccountNumber());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetHolisticPaymentInDetails() {
		HolisticViewRequest holisticViewRequest= new HolisticViewRequest();
		holisticViewRequest.setPaymentInId(281289);
		HolisticViewResponse holisticViewResponse= new HolisticViewResponse();
		try {
		holisticViewDBServiceImpl.getHolisticPaymentInDetails(holisticViewRequest, holisticViewResponse);	
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
	}
public void setMockForPaymentOutDetails()
{
	try {
		when(rs.getString("Attributes")).thenReturn("{\"org_code\":\"Currencies Direct\",\"source_application\":"
				+ "\"Titan\",\"trade\":{\"cust_type\":\"PFX\",\"trade_contact_id\":66365321,"
				+ "\"contract_number\":\"7112I349924-010100\",\"deal_date\":\"2019-02-11\","
				+ "\"selling_amount\":350.0,\"buying_amount\":385.59,\"purpose_of_trade\":"
				+ "\"LIVING_FUNDS\",\"maturity_date\":\"2019-02-11\",\"trade_account_number\":\"03010000100056322\","
				+ "\"value_date\":\"2019-02-11\",\"buying_amount_base_value\":373.72,\"buy_currency\":\"EUR\","
				+ "\"sell_currency\":\"GBP\",\"third_party_payment\":true,\"customer_legal_entity\":\"CDLCA\"},"
				+ "\"beneficiary\":{\"phone\":\"\",\"first_name\":\"Suresh sharma\",\"last_name\":\"\","
				+ "\"email\":\"\",\"country\":\"FRA\",\"account_number\":\"CCBPFRPPBDX7610907003912103622595174\","
				+ "\"currency_code\":\"EUR\",\"beneficiary_id\":8000012,\"beneficiary_bankid\":801552,"
				+ "\"beneficary_bank_name\":\"Byblos fc Bank\",\"beneficary_bank_address\":"
				+ "\"PO  516 5 PLACE 11 JEAN JAURES 33001 BORDEAUX FRANCE\",\"trans_ts\":"
				+ "\"2019-02-05T19:38:22Z\",\"payment_reference\":\"CD 73408\",\"opi_created_date\":"
				+ "\"2019-02-11\",\"beneficiary_type\":\"Individual\",\"payment_fundsout_id\":21022893,"
				+ "\"amount\":385.59},\"version\":1,\"osr_id\":\"13492d2f-121a-4a0f-a5bc-6dc3b2c1f627\"}");
		when(rs.getString("vBankName")).thenReturn("Byblos fc Bank");
		when(rs.getString("vBeneficiaryFirstName")).thenReturn("Suresh sharma");
		when(rs.getString("vBeneficiaryLastName")).thenReturn("");
		when(rs.getString("vBeneficiaryCountry")).thenReturn("FRA");
		when(rs.getString("vBuyingCurrency")).thenReturn("EUR");
		when(rs.getBoolean("Deleted")).thenReturn(false);
	} catch (SQLException e) {
		System.out.println(e);
	}
	}
public HolisticViewResponse getHolisticViewResponsePaymentOut()
{
	HolisticViewResponse  holisticViewResponse=new HolisticViewResponse();
PaymentOutSummary paymentOutSummary= new PaymentOutSummary();
paymentOutSummary.setBankName("Byblos fc Bank");
paymentOutSummary.setBeneficiaryAccountNumber("CCBPFRPPBDX7610907003912103622595174");
paymentOutSummary.setBeneficiaryName("Suresh sharma");
paymentOutSummary.setBeneficiaryType("Individual");
paymentOutSummary.setBuyCurrency("EUR");
paymentOutSummary.setCountryOfBeneficiary("FRA");
paymentOutSummary.setIsDeleted(false);
paymentOutSummary.setPaymentReference("CD 73408");
holisticViewResponse.setPaymentOutSummary(paymentOutSummary);
return holisticViewResponse;
	}

	@Test
	public void testGetHolisticPaymentOutDetails() {
		setMockForPaymentOutDetails();
		HolisticViewRequest holisticViewRequest= new HolisticViewRequest();
		holisticViewRequest.setPaymentOutId(182828);
		HolisticViewResponse holisticViewResponse= new HolisticViewResponse();
		try {
			
			HolisticViewResponse actualResult=holisticViewDBServiceImpl.getHolisticPaymentOutDetails(holisticViewRequest, holisticViewResponse);
			HolisticViewResponse  expectedResult=getHolisticViewResponsePaymentOut();
			assertEquals(expectedResult.getPaymentOutSummary().getBeneficiaryAccountNumber(),actualResult.getPaymentOutSummary().getBeneficiaryAccountNumber());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetHolisticPaymentOutDetails() {
		HolisticViewRequest holisticViewRequest= new HolisticViewRequest();
		holisticViewRequest.setPaymentOutId(182828);
		HolisticViewResponse holisticViewResponse= new HolisticViewResponse();
		try {
			holisticViewDBServiceImpl.getHolisticPaymentOutDetails(holisticViewRequest, holisticViewResponse);
			} catch (CompliancePortalException e) {
				assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
			}
	}
	public void setMockForGetOtherContacts()
	{
		
		try {
			when(rs.getString("Contactattribute")).thenReturn("{\"onQueue\":false,\"contact_sf_id\":"
					+ "\"0036E00000zCkHoQBT\",\"trade_contact_id\":66365321,\"title\":\"\",\"pref_name\":\"\","
					+ "\"first_name\":\"Stefan Salvatore\",\"middle_name\":\"\",\"last_name\":\"AL-FAQIH\","
					+ "\"second_surname\":\"\",\"mothers_surname\":\"\",\"dob\":\"1968-10-19\","
					+ "\"position_of_significance\":\"\",\"authorised_signatory\":\"true\",\"job_title\":"
					+ "\"Other\",\"address_type\":\"Current Address\",\"address1\":\"Rue Leslie\",\"town_city_muncipalty\":"
					+ "\"Toronto\",\"building_name\":\"29 Rue Leslie\",\"street\":\"Rue Leslie\","
					+ "\"street_number\":\"125\",\"street_type\":\"\",\"post_code\":\"B0713133\","
					+ "\"post_code_lat\":0.0,\"post_code_long\":0.0,\"country_of_residence\":\"NOR\","
					+ "\"home_phone\":\"\",\"work_phone\":\"\",\"work_phone_ext\":\"\",\"mobile_phone\":"
					+ "\"+1-6547125863\",\"primary_phone\":\"Mobile\",\"email\":\"stefan@gmail.ca\",\"second_email\":\"\","
					+ "\"designation\":\"Other\",\"ip_address\":\"192.168.2.154\",\"ip_address_latitude\":"
					+ "\"0.00\",\"ip_address_longitude\":\"0.00\",\"is_primary_contact\":true,\"country_of_nationality\":"
					+ "\"\",\"gender\":\"\",\"occupation\":\"Other\",\"passport_number\":\"\",\"passport_country_code\":\"\","
					+ "\"passport_exipry_date\":\"\",\"passport_full_name\":\"\",\"passport_mrz_line_1\":\"\","
					+ "\"passport_mrz_line_2\":\"\",\"passport_birth_family_name\":\"\",\"passport_name_at_citizen\":\"\","
					+ "\"passport_birth_place\":\"\",\"driving_license\":\"\",\"driving_version_number\":\"\","
					+ "\"driving_license_card_number\":\"\",\"driving_license_country\":\"\",\"driving_state_code\":\"\","
					+ "\"driving_expiry\":\"\",\"medicare_card_number\":\"\",\"medicare_ref_number\":\"\","
					+ "\"australia_rta_card_number\":\"\",\"state_province_county\":\"Oslo\",\"municipality_of_birth\":"
					+ "\"\",\"country_of_birth\":\"\",\"state_of_birth\":\"\",\"civic_number\":\"\",\"sub_building\":\"\","
					+ "\"unit_number\":\"\",\"sub_city\":\"Oslofjord\",\"national_id_type\":\"\",\"national_id_number\":"
					+ "\"\",\"years_in_address\":\"3.00\",\"residential_status\":\"\",\"version\":1,\"district\":\"\","
					+ "\"area_number\":\"\",\"aza\":\"Oslofjord\",\"prefecture\":\"Oslofjord\",\"floor_number\":\"\"}");
			when(rs.getInt("contactID")).thenReturn(50582);
			when(rs.getString("Name")).thenReturn("Stefan Salvatore AL-FAQIH");
			when(rs.getString("complianceStatus")).thenReturn("INACTIVE");
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	
	public List<HolisticContact> getContact()
	{
		List<HolisticContact> contactList = new ArrayList<>();
		HolisticContact holisticContact= new HolisticContact();
		holisticContact.setAuthorisedSignatory("true");
		holisticContact.setComplianceStatus("INACTIVE");
		holisticContact.setId(50582);
		holisticContact.setJobTitle("Other");
		holisticContact.setName("Stefan Salvatore AL-FAQIH");
		contactList.add(holisticContact);
		return contactList;
		
		
	}
	
	@Test
	public void testGetOtherContacts() {
		setMockForGetOtherContacts();
		Integer accountId=49741;
		 Integer contactId=50583;
		 try {
			 List<HolisticContact> expectedResult=getContact();
			 List<HolisticContact> actualResult=holisticViewDBServiceImpl.getOtherContacts(accountId, contactId, connection);
		assertEquals(expectedResult.get(0).getId(),actualResult.get(0).getId());
		 } catch (CompliancePortalException e) {
			System.out.println(e);
		}
		
	}

	@Test
	public void testForGetOtherContacts() {
		Integer accountId=49741;
		 Integer contactId=50583;
		 try {
			holisticViewDBServiceImpl.getOtherContacts(accountId, contactId, connection);
		 } catch (CompliancePortalException e) {
			 assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
			 }
		
	}

	
public void setMockForGetContactsForCFX()
{
	try {
		when(rs.getString("Contactattribute")).thenReturn("{\"onQueue\":false,\"contact_sf_id\":"
				+ "\"0030O00007LN9VRQA1\",\"trade_contact_id\":219214,\"title\":\"Dr\",\"pref_name\":\"\","
				+ "\"first_name\":\"Stepha\",\"middle_name\":\"Eugenie\",\"last_name\":\"Praet\","
				+ "\"second_surname\":\"\",\"mothers_surname\":\"\",\"dob\":\"1971-03-01\","
				+ "\"position_of_significance\":\"Director and Shareholder 25% and >\",\"authorised_signatory\":"
				+ "\"true\",\"job_title\":\"Director, Company Secretary and Shareholder\","
				+ "\"address_type\":\"Current Address\",\"address1\":\"8 Taylor Place\","
				+ "\"town_city_muncipalty\":\"Hackett\",\"building_name\":\"8 Taylor Place\",\"street\":"
				+ "\"Taylor Place\",\"street_number\":\"\",\"street_type\":\"\",\"post_code\":\"2602\","
				+ "\"post_code_lat\":0.0,\"post_code_long\":0.0,\"country_of_residence\":\"GBR\","
				+ "\"home_phone\":\"+61-414617784\",\"work_phone\":\"+971-585064883\",\"work_phone_ext\":"
				+ "\"\",\"mobile_phone\":\"+61-414617784\",\"primary_phone\":\"Work\",\"email\":"
				+ "\"stephan.praet@prosano.com.au\",\"second_email\":\"\",\"designation\":"
				+ "\"Director, Company Secretary and Shareholder\",\"ip_address\":\"178.62.236.176\","
				+ "\"ip_address_latitude\":\"0.00\",\"ip_address_longitude\":\"0.00\",\"is_primary_contact\":true,"
				+ "\"country_of_nationality\":\"GBR\",\"gender\":\"Male\",\"occupation\":"
				+ "\"Director, Company Secretary and Shareholder\",\"passport_number\":\"\",\"passport_country_code\":\""
				+ "\",\"passport_exipry_date\":\"\",\"passport_full_name\":\"\",\"passport_mrz_line_1\":\""
				+ "\",\"passport_mrz_line_2\":\"\",\"passport_birth_family_name\":\"\",\"passport_name_at_citizen\":\""
				+ "\",\"passport_birth_place\":\"\",\"driving_license\":\"\",\"driving_version_number\":\"\",\"driving_license_card_number\":\"\","
				+ "\"driving_license_country\":\"\",\"driving_state_code\":\"\",\"driving_expiry\":\"\","
				+ "\"medicare_card_number\":\"\",\"medicare_ref_number\":\"\",\"australia_rta_card_number\":\"\","
				+ "\"state_province_county\":\"Australian Capital Territory\",\"municipality_of_birth\":"
				+ "\"\",\"country_of_birth\":\"\",\"state_of_birth\":\"\",\"civic_number\":\"\","
				+ "\"sub_building\":\"\",\"house_building_number\":\"8\",\"unit_number\":\"\",\"sub_city"
				+ "\":\"Hackett\",\"national_id_type\":\"\",\"national_id_number\":\"\",\"years_in_address\":"
				+ "\"0.00\",\"residential_status\":\"\",\"version\":1,\"district\":\"\",\"area_number\":\"\","
				+ "\"aza\":\"Hackett\",\"prefecture\":"
				+ "\"Australian Capital Territory\",\"floor_number\":\"\"}");
		when(rs.getInt("contactID")).thenReturn(18683);
		when(rs.getString("complianceStatus")).thenReturn("INACTIVE");
		when(rs.getString("Name")).thenReturn("Stepha Praet");
	} catch (SQLException e) {
		System.out.println(e);
	}
}
public  List<HolisticContact> getContactForCFX()
{
	 List<HolisticContact> listContact= new ArrayList<>();
	 HolisticContact holisticContact= new HolisticContact();
	 holisticContact.setAuthorisedSignatory("true");
		holisticContact.setComplianceStatus("INACTIVE");
		holisticContact.setId(18683);
		holisticContact.setJobTitle("Director, Company Secretary and Shareholder");
		holisticContact.setName("Stepha Praet");
		holisticContact.setPositionOfSignificance("Director and Shareholder 25% and >");
		listContact.add(holisticContact);
	 return listContact;
	 
	}
	@Test
	public void testGetContactsForCFX() {
		setMockForGetContactsForCFX();
		Integer accountId=18003;
		try {
			
			 List<HolisticContact> actualResult=holisticViewDBServiceImpl.getContactsForCFX(accountId, connection);
			 List<HolisticContact> expectedResult=getContactForCFX();
			 assertEquals(expectedResult.get(0).getId(), actualResult.get(0).getId());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
		
	}
	@Test
	public void testForGetContactsForCFX() {
		Integer accountId=18003;
		try {
			holisticViewDBServiceImpl.getContactsForCFX(accountId, connection);
		} catch (CompliancePortalException e) {
			 assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
		
	}
	
}
