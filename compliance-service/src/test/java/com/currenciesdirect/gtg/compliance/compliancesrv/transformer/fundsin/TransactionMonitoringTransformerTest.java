package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsin;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CDINCFirstCreditCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.EuPoiCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.FirstCreditCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.VelocityCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.WhiteListCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.response.Feature;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.signup.response.DecisionDrivers;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInTrade;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSTPData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CardFraudScoreResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FraudSightScoreResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.GlobalCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.PersonalDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Phone;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;

public class TransactionMonitoringTransformerTest {

	@Autowired
	protected static ResourceLoader resourceLoader = new DefaultResourceLoader();
	@Mock
	HttpServletRequest httpRequest;
	@Mock
	Message<MessageContext> message;
	
	@InjectMocks
	TransactionMonitoringTransformer tmRequest;
	
	@Mock
	protected NewRegistrationDBServiceImpl newRegistrationDBServiceImpl;
	
	@Before
	public void setUp() {
		try {
			MockitoAnnotations.initMocks(this);
		
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private String buildETVRequestJson(String fileName) throws IOException {
		Resource jsonFile = resourceLoader.getResource(fileName);
		String jsonData = IOUtils.toString(jsonFile.getInputStream(), "UTF-8");
		return jsonData;
	}
	
	
	public Message<MessageContext> getMessageContext() {
		BaseController baseController = new BaseController();
		MessageContext messageContext = new MessageContext();
		String fundInRequestJson;
		try {
			fundInRequestJson = buildETVRequestJson("classpath:FundsIn.json");
			FundsInCreateRequest fundInRequest = JsonConverterUtil.convertToObject(FundsInCreateRequest.class,
					fundInRequestJson);
			Account account = new Account();
		
			fundInRequest.setType("FUNDS_IN_ADD");
			List<Contact> contact = new ArrayList<>();
			fundInRequest.addAttribute("oldContacts", contact);
			fundInRequest.addAttribute("account",account);
			fundInRequest.addAttribute("AccountTMFlag",0);
			fundInRequest.addAttribute("reasonsOfWatchlist",new ArrayList<>());
			fundInRequest.addAttribute("watchlist_with_trade&cont", new MultiHashMap());
			FundsInTrade trade= fundInRequest.getTrade();
			trade.setPaymentFundsInId(fundInRequest.getTrade().getPaymentFundsInId());
			fundInRequest.setTrade(trade);
			List<String> arrlist = new ArrayList<>();
			arrlist.add("Origin");
			arrlist.add("Authorization");
			arrlist.add("Accept");
			arrlist.add("Accept");
			arrlist.add("Connection");
			arrlist.add("Host");
			arrlist.add("Accept-Language");
			arrlist.add("Accept-Encoding");
			arrlist.add("Accept-Encoding");
			arrlist.add("Content-Type");
			Enumeration<String> headerNames = Collections.enumeration(arrlist);
			List<String> headerlist = new ArrayList<>();
			UserProfile user = new UserProfile();
			user.setName("CD User");
			user.setGivenName("CD");
			user.setEmail("cd_api_user@currenciesdirect.com");
			user.setFamilyName("User");
			user.setPreferredUserName("cd_api_user");
			user.setUserID(18);
			headerlist.add("chrome-extension://eipdnjedkpcnlmmdfdkgfpljanehloah");
			headerlist.add("Bearer ");
			headerlist.add("*/*");
			headerlist.add(
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.106 Safari/537.36");
			headerlist.add("keep-alive");
			headerlist.add("172.31.22.84:8080");
			headerlist.add("en-GB,en-US;q=0.9,en;q=0.8");
			headerlist.add("gzip, deflate");
			headerlist.add("5457");
			headerlist.add("application/json");
			Enumeration<String> header = Collections.enumeration(headerlist);
			when(httpRequest.getHeaderNames()).thenReturn(headerNames);
			when(httpRequest.getHeaders(anyString())).thenReturn(header);
			messageContext = baseController.buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
					ServiceInterfaceType.FUNDSIN, OperationEnum.FUNDS_IN, "Currencies Direct",fundInRequest);
					
			MessageExchange gateWayMessageExchange = messageContext.getMessageCollection().get(0).getMessageExchange();
			EventServiceLog eventServiceLog = new EventServiceLog();
			eventServiceLog.setEventId(1518122);
			eventServiceLog.setEntityType("CONTACT");
			eventServiceLog.setEntityId(582592);
			eventServiceLog.setEntityVersion(1);
			eventServiceLog.setServiceName("BLACKLIST");
			eventServiceLog.setServiceProviderName("BLACKLIST");
			eventServiceLog.setProviderResponse(
					"{\"status\":\"PASS\",\"data\":[{\"type\":\"NAME\","
					+ "\"requestType\":\"CONTACT NAME\",\"value\":\"Micheal Norse\","
					+ "\"found\":false,\"match\":0,\"matchedData\":\"No Match Found\"},{"
					+ "\"type\":\"EMAIL\",\"requestType\":\"EMAIL\",\"value\":\"abc2133@gmail\","
					+ "\"found\":false,\"match\":0,\"matchedData\":\"No Match Found\"},{\"type\":"
					+ "\"DOMAIN\",\"requestType\":\"DOMAIN\",\"found\":false,\"match\":0,\"matchedData\":"
					+ "\"No Match Found\"},{\"type\":\"PHONE_NUMBER\",\"requestType\":"
					+ "\"PHONE_NUMBER\",\"found\":false,\"match\":0,\"matchedData\":"
					+ "\"No Match Found\"},{\"type\":\"PHONE_NUMBER\",\"requestType\":"
					+ "\"PHONE_NUMBER\",\"found\":false,\"match\":0,\"matchedData\":"
					+ "\"No Match Found\"},{\"type\":\"PHONE_NUMBER\",\"requestType\":"
					+ "\"PHONE_NUMBER\",\"found\":false,\"match\":0,\"matchedData\":"
					+ "\"No Match Found\"},{\"type\":\"IPADDRESS\",\"requestType\":\"IPADDRESS\","
					+ "\"value\":\"172.31.4.211\",\"found\":false,\"match\":0,\"matchedData\":"
					+ "\"No Match Found\"}]}");
			eventServiceLog.setStatus("PASS");
			eventServiceLog.setSummary("{\"status\":\"PASS\",\"ip\":\"false\",\"ipMatchedData\":"
					+ "\"No Match Found\",\"email\":\"false\",\"emailMatchedData\":\"No Match Found\","
					+ "\"phone\":false,\"phoneMatchedData\":\"No Match Found\",\"accountNumber\":"
					+ "\"NOT_REQUIRED\",\"name\":\"false\",\"nameMatchedData\":\"No Match Found\","
					+ "\"domain\":\"false\",\"domainMatchedData\":\"No Match Found\",\"webSite\":"
					+ "\"false\",\"websiteMatchedData\":\"No Match Found\",\"bankName\":\"NOT_REQUIRED\"}");
			eventServiceLog.setCratedBy(18);
			eventServiceLog.setCreatedOn(Timestamp.valueOf("2020-10-30 10:59:05.892"));
			eventServiceLog.setUpdatedBy(18);
			eventServiceLog.setUpdatedOn(Timestamp.valueOf("2020-10-30 10:59:17.511"));
			gateWayMessageExchange.addEventServiceLog(eventServiceLog);
			Map<String, Object> messageHeaders = new HashMap<>();
			messageHeaders.put(MessageContextHeaders.GATEWAY_REQUEST_RECEIVE_TIME,
					messageContext.getAuditInfo().getCreatedOn());
			messageHeaders.put(MessageContextHeaders.GATEWAY_SERVICE_ID, gateWayMessageExchange.getServiceInterface());
			messageHeaders.put(MessageContextHeaders.GATEWAY_OPERATION, gateWayMessageExchange.getOperation().name());
			messageHeaders.put(MessageContextHeaders.GATEWAY_REQUEST_ORG, messageContext.getOrgCode());
			messageHeaders.put(MessageContextHeaders.GATEWAY_USER, user);
			messageHeaders.put(MessageContextHeaders.OSR_ID, gateWayMessageExchange.getRequest().getOsrId());
			messageHeaders.put("correlationID", UUID.randomUUID().toString());
			Message<MessageContext> message = MessageBuilder.withPayload(messageContext).copyHeaders(messageHeaders)
					.build();
			
			MessageExchange messageExchange = new MessageExchange();
			InternalServiceRequest internalServiceRequest = new InternalServiceRequest();
			internalServiceRequest.setAccountSFId("0716E00000zKzriLBX");
			internalServiceRequest.setCorrelationID(UUID.randomUUID());
			internalServiceRequest.setOnlyBlacklistCheckPerform(false);
			internalServiceRequest.setOrgCode("Currencies Direct");
			internalServiceRequest.setRequestType("PAYMENT_IN");
			internalServiceRequest.setIsIpEligible(true);
			List<InternalServiceRequestData> searchdata = new ArrayList<>();
			
			InternalServiceRequestData internalServiceRequestData = new InternalServiceRequestData();
			internalServiceRequestData.setCardFraudScore(0.56);
			internalServiceRequestData.setCardFraudScoreThreshold(0.76);
			internalServiceRequestData.setCcName(fundInRequest.getTrade().getCcFirstName());
			internalServiceRequestData.setPaymentMethod(fundInRequest.getTrade().getPaymentMethod());
			internalServiceRequestData.setThirdPartyPayment(fundInRequest.getTrade().getThirdPartyPayment());
			internalServiceRequestData.setCountry("India");
			internalServiceRequestData.setEntityType("CONTACT");
			internalServiceRequestData.setId(52774);
			internalServiceRequestData.setIpAddress("172.31.4.211");
			List<String> phone = new ArrayList<>();
			phone.add("+44-7311123456");
			internalServiceRequestData.setPhoneNoList(phone);
			internalServiceRequest.setSearchdata(searchdata);
			messageExchange.setRequest(internalServiceRequest);
			InternalServiceResponse internalServiceResponse = new InternalServiceResponse();
			internalServiceResponse.setOnlyBlacklistCheckPerform(false);
			internalServiceResponse.setStatus("PASS");
			List<ContactResponse> contacts = new ArrayList<>();
			ContactResponse contactResponse = new ContactResponse();
			contactResponse.setContactStatus("PASS");
			contactResponse.setId(52774);
			contactResponse.setEntityType("CONTACT");
			BlacklistContactResponse blacklist = new BlacklistContactResponse();
			List<BlacklistSTPData> data = new ArrayList<>();
			BlacklistSTPData blacklistSTPData = new BlacklistSTPData();
			blacklistSTPData.setFound(false);
			blacklistSTPData.setMatch(0);
			blacklistSTPData.setMatchedData("No Match Found");
			blacklistSTPData.setRequestType("CONTACT NAME");
			blacklistSTPData.setType("Name");
			blacklistSTPData.setValue("Micheal Norse");
			BlacklistSTPData blacklistSTPData1 = new BlacklistSTPData();
			blacklistSTPData1.setFound(false);
			blacklistSTPData1.setMatch(0);			
			blacklistSTPData1.setMatchedData("No Match Found");
			blacklistSTPData1.setRequestType("Email");
			blacklistSTPData1.setType("Email");
			blacklistSTPData1.setValue("abc2133@gmail");
			BlacklistSTPData blacklistSTPData2 = new BlacklistSTPData();
			blacklistSTPData2.setFound(false);
			blacklistSTPData2.setMatch(0);
			blacklistSTPData2.setMatchedData("No Match Found");
			blacklistSTPData2.setRequestType("IPADDRESS");
			blacklistSTPData2.setType("IPADDRESS");
			blacklistSTPData2.setValue("172.31.4.211");
			data.add(blacklistSTPData);
			data.add(blacklistSTPData1);
			data.add(blacklistSTPData2);
			blacklist.setData(data);
			contactResponse.setBlacklist(blacklist);
			CountryCheckContactResponse countryCheck = new CountryCheckContactResponse();
			countryCheck.setCountry("United Kingdom");
			countryCheck.setRiskLevel("L");
			countryCheck.setStatus("PASS");
			contactResponse.setCountryCheck(countryCheck);
			GlobalCheckContactResponse globalCheck = new GlobalCheckContactResponse();
			globalCheck.setCountry("United Kingdom");
			globalCheck.setStatus("PASS");
			contactResponse.setGlobalCheck(globalCheck);
			IpContactResponse ipCheck = new IpContactResponse();
			ipCheck.setStatus("PASS");
			contactResponse.setIpCheck(ipCheck);
			FraudSightScoreResponse fraudSightScoreResponse =new FraudSightScoreResponse();
			fraudSightScoreResponse.setFsScore("0.76");
			fraudSightScoreResponse.setStatus("PASS");
			contactResponse.setFraudSightCheck(fraudSightScoreResponse);
			CardFraudScoreResponse cardFraudScoreResponse =new CardFraudScoreResponse();
			cardFraudScoreResponse.settScore(0.76);
			cardFraudScoreResponse.setStatus("PASS");
			contactResponse.setCardFraudCheck(cardFraudScoreResponse);
			contacts.add(contactResponse);
			internalServiceResponse.setContacts(contacts);
			messageExchange.setServiceTypeEnum(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			messageExchange.setInternalProcessingCode(InternalProcessingCode.NOT_CLEARED);
			messageExchange.setResponse(internalServiceResponse);
			EventServiceLog log =new EventServiceLog();
			log.setEventId(1518122);
			log.setEntityType("CONTACT");
			log.setEntityId(582592);
			log.setEntityVersion(1);
			log.setServiceName("COUNTRYCHECK");
			log.setServiceProviderName("COUNTRYCHECK");
			log.setProviderResponse("{\"status\":\"PASS\",\"country\":\"United Kingdom\",\"riskLevel\":\"L\"}");
			log.setSummary("{\"status\":\"PASS\",\"country\":\"United Kingdom\",\"riskLevel\":\"L\"}");
			log.setStatus("PASS");
			log.setCratedBy(18);
			log.setCreatedOn(Timestamp.valueOf("2020-11-02 09:40:51.639"));
			log.setUpdatedBy(18);
			log.setUpdatedOn(Timestamp.valueOf("2020-11-02 09:40:54.243"));
			messageExchange.addEventServiceLog(log);
			EventServiceLog log1 =new EventServiceLog();
			log1.setEventId(941839);
			log1.setEntityType("CONTACT");
			log1.setEntityId(52774);
			log1.setEntityVersion(1);
			log1.setServiceName("GLOBALCHECK");
			log1.setServiceProviderName("GLOBALCHECK");
			log1.setProviderResponse("{\"status\":\"NOT_REQUIRED\",\"country\":\"United Kingdom\",\"state\":\"West Yorkshire\"}");
			log1.setSummary("{\"status\":\"NOT_REQUIRED\",\"country\":\"United Kingdom\",\"state\":\"West Yorkshire\"}");
			log1.setStatus("PASS");
			log1.setCratedBy(18);
			log1.setCreatedOn(Timestamp.valueOf("2020-11-02 09:40:51.639"));
			log1.setUpdatedBy(18);
			log1.setUpdatedOn(Timestamp.valueOf("2020-11-02 09:40:54.243"));
			messageExchange.addEventServiceLog(log1);	
			EventServiceLog log2 = new EventServiceLog();
			log1.setEventId(941839);
			log1.setEntityType("CONTACT");
			log1.setEntityId(52774);
			log1.setEntityVersion(1);
			log1.setServiceName("FRAUDSIGHTSCORE");
			log1.setServiceProviderName("FRAUDSIGHTSCORE");
			log1.setProviderResponse("{\"status\":\"PASS\"}");
			log.setSummary("{\"status\":\"PASS\"}");
			log.setStatus("PASS");
			log.setCratedBy(18);
			log.setCreatedOn(Timestamp.valueOf("2020-11-02 09:40:51.639"));
			log.setUpdatedBy(18);
			log.setUpdatedOn(Timestamp.valueOf("2020-11-02 09:40:54.243"));
			messageExchange.addEventServiceLog(log2);
			messageExchange.addEventServiceLog(eventServiceLog);
			
			
			MessageExchange messageExchange2 = new MessageExchange();
			EventServiceLog eventServiceLog2 = new EventServiceLog();
			eventServiceLog2.setEventId(941839);
			eventServiceLog2.setEntityType("CONTACT");
			eventServiceLog2.setEntityId(52774);
			eventServiceLog2.setEntityVersion(1);
			eventServiceLog2.setServiceName("VELOCITYCHECK");
			eventServiceLog2.setServiceProviderName("VELOCITY_PERFORM_CHECK");
			eventServiceLog2.setProviderResponse("{\"countryWhitelistedForFundsIn\":false,\"countryWhitelisted\":false,"
					+ "\"overallStatus\":\"SERVICE_FAILURE\",\"velocityCheck\":{\"status\":\"SERVICE_FAILURE\","
					+ "\"noOffundsoutTxn\":\"SERVICE_FAILURE\",\"permittedAmoutcheck\":\"SERVICE_FAILURE\","
					+ "\"beneCheck\":\"SERVICE_FAILURE\"},\"whiteListCheck\":{\"currency\":\"SERVICE_FAILURE\","
					+ "\"amoutRange\":\"SERVICE_FAILURE\",\"thirdParty\":\"SERVICE_FAILURE\","
					+ "\"reasonOfTransfer\":\"SERVICE_FAILURE\",\"status\":\"SERVICE_FAILURE\"},"
					+ "\"firstCreditCheck\":{\"status\":\"SERVICE_FAILURE\"},"
					+ "\"euPoiCheck\":{\"status\":\"SERVICE_FAILURE\"}}");
			eventServiceLog2.setStatus("PASS");
			eventServiceLog2.setSummary("{\"countryWhitelistedForFundsIn\":false,"
					+ "\"countryWhitelisted\":false,\"overallStatus\":\"SERVICE_FAILURE\","
					+ "\"velocityCheck\":{\"status\":\"SERVICE_FAILURE\","
					+ "\"noOffundsoutTxn\":\"SERVICE_FAILURE\",\"permittedAmoutcheck\":\"SERVICE_FAILURE\","
					+ "\"beneCheck\":\"SERVICE_FAILURE\"},\"whiteListCheck\":{\"currency\":\"SERVICE_FAILURE\","
					+ "\"amoutRange\":\"SERVICE_FAILURE\",\"thirdParty\":\"SERVICE_FAILURE\",\"reasonOfTransfer\":\"SERVICE_FAILURE\","
					+ "\"status\":\"SERVICE_FAILURE\"},\"firstCreditCheck\":{\"status\":\"SERVICE_FAILURE\"},"
					+ "\"euPoiCheck\":{\"status\":\"SERVICE_FAILURE\"}}");
			eventServiceLog2.setCratedBy(18);
			eventServiceLog2.setCreatedOn(Timestamp.valueOf("2020-11-02 09:40:51.639"));
			eventServiceLog2.setUpdatedBy(18);
			eventServiceLog2.setUpdatedOn(Timestamp.valueOf("2020-11-02 09:40:54.243"));
			messageExchange2.addEventServiceLog(eventServiceLog2);
			messageExchange2.setServiceTypeEnum(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			
			CustomChecksRequest customCheck=new CustomChecksRequest();
			customCheck.setAccId(87619);
			customCheck.setCorrelationID(UUID.randomUUID());
			customCheck.setOrgCode(fundInRequest.getOrgCode());
	
			VelocityCheckResponse velocityCheckResponse = new VelocityCheckResponse();
			velocityCheckResponse.setStatus("PASS");
			velocityCheckResponse.setBeneCheck("PASS");
			velocityCheckResponse.setNoOffundsoutTxn("PASS");
			velocityCheckResponse.setPermittedAmoutcheck("PASS");
			
			WhiteListCheckResponse whiteListCheckResponse = new WhiteListCheckResponse();
			whiteListCheckResponse.setStatus("PASS");
			whiteListCheckResponse.setAmoutRange("PASS");
			whiteListCheckResponse.setReasonOfTransfer("PASS");
			whiteListCheckResponse.setThirdParty("PASS");
			whiteListCheckResponse.setCurrency("PASS");
			
			FirstCreditCheckResponse firstCreditCheckResponse = new FirstCreditCheckResponse();
			firstCreditCheckResponse.setStatus("PASS");
			
			EuPoiCheckResponse euPoiCheckResponse = new EuPoiCheckResponse();
			euPoiCheckResponse.setStatus("PASS");
			
			CDINCFirstCreditCheckResponse cdincFirstCreditCheckResponse = new CDINCFirstCreditCheckResponse();
			cdincFirstCreditCheckResponse.setStatus("PASS");
			
			CustomCheckResponse customChecksResponse = new CustomCheckResponse();
			customChecksResponse.setWhiteListCheck(whiteListCheckResponse);
			customChecksResponse.setVelocityCheck(velocityCheckResponse);
			customChecksResponse.setFirstCreditCheck(firstCreditCheckResponse);
			customChecksResponse.setEuPoiCheck(euPoiCheckResponse);
			customChecksResponse.setCdincFirstCreditCheck(cdincFirstCreditCheckResponse);
			
			messageExchange2.setRequest(customCheck);
			messageExchange2.setResponse(customChecksResponse);
			
			customChecksResponse.setDecision(CustomCheckResponse.DECISION.SUCCESS);
			
			MessageExchange messageExchange3 = new MessageExchange();
			EventServiceLog eventServiceLog3 = new EventServiceLog();
			eventServiceLog3.setEventId(941839);
			eventServiceLog3.setEntityType("CONTACT");
			eventServiceLog3.setEntityId(52774);
			eventServiceLog3.setEntityVersion(1);
			eventServiceLog3.setServiceName("KYC");
			eventServiceLog3.setServiceProviderName("GBGROUP");
			eventServiceLog3.setProviderResponse("{\"providerName\":\"GBGROUP\","
					+ "\"providerMethod\":\"authenticateSP\",\"providerResponse\":"
					+ "\"{\\\"authenticationID\\\":\\\"43e4fd56-4cb9-483a-a222-1927c84d5ca6\\\","
					+ "\\\"timestamp\\\":1491893311166,\\\"customerRef\\\":\\\"002-C-0000052774\\\","
					+ "\\\"profileID\\\":\\\"49693f46-adf1-4d23-99ed-b0c9ac9a41cb\\\",\\\"profileName\\\":"
					+ "\\\"UK Profile\\\",\\\"profileVersion\\\":1,\\\"profileRevision\\\":4,"
					+ "\\\"profileState\\\":{\\\"value\\\":\\\"Effective\\\"},\\\"resultCodes\\\":[{"
					+ "\\\"name\\\":\\\"UK Credit Header (AML)\\\",\\\"description\\\":"
					+ "\\\"UK Credit Header Database.  Provides authentication of name, address and date of "
					+ "birth against Credit Header information for an Anti Money Laundering Check.\\\","
					+ "\\\"comment\\\":[{\\\"description\\\":\\\"No middle initial specified by user.\\\","
					+ "\\\"code\\\":101,\\\"override\\\":null},{\\\"description\\\":\\\"First year of"
					+ " residence for address #1 not supplied by user.\\\",\\\"code\\\":151,\\\"override\\\":null},{"
					+ "\\\"description\\\":\\\"Last year of residence for address #1 not supplied by user."
					+ " Applying default.\\\",\\\"code\\\":161,\\\"override\\\":null}],\\\"match\\\":null,"
					+ "\\\"warning\\\":[{\\\"description\\\":\\\"Test mode active, only seed records will"
					+ " be processed\\\",\\\"code\\\":4100,\\\"override\\\":null}],\\\"mismatch\\\":null,"
					+ "\\\"pass\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"address\\\":{\\\"value\\\":"
					+ "\\\"Nomatch\\\"},\\\"forename\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"surname\\\":{"
					+ "\\\"value\\\":\\\"Nomatch\\\"},\\\"alert\\\":{\\\"value\\\":\\\"Nomatch\\\"},"
					+ "\\\"country\\\":null,\\\"dob\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"id\\\":155},{"
					+ "\\\"name\\\":\\\"UK National Identity Register\\\",\\\"description\\\":\\\"UK National "
					+ "Identity Register check. Performs authentication of first, last name, date of birth,"
					+ " address and phone numbers against UK National Identity Register.\\\",\\\"comment\\\":null,"
					+ "\\\"match\\\":null,\\\"warning\\\":null,\\\"mismatch\\\":[{\\\"description\\\":"
					+ "\\\"Address invalid/did not match\\\",\\\"code\\\":8260,\\\"override\\\":null}],"
					+ "\\\"pass\\\":{\\\"value\\\":\\\"Mismatch\\\"},\\\"address\\\":{\\\"value\\\":"
					+ "\\\"Nomatch\\\"},\\\"forename\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"surname\\\":{"
					+ "\\\"value\\\":\\\"Nomatch\\\"},\\\"alert\\\":{\\\"value\\\":\\\"Nomatch\\\"},"
					+ "\\\"country\\\":null,\\\"dob\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"id\\\":245},{"
					+ "\\\"name\\\":\\\"UK Landline (Append)\\\",\\\"description\\\":\\\"UK Landline "
					+ "Telephone Database.  Provides authentication against first initial, surname, "
					+ "address and appends land telephone number or ex-directory status of bill payer.\\\","
					+ "\\\"comment\\\":[{\\\"description\\\":\\\"No/insufficient details supplied for "
					+ "address #2. No search performed.\\\",\\\"code\\\":102,\\\"override\\\":null},{"
					+ "\\\"description\\\":\\\"No/insufficient details supplied for address #3. No search "
					+ "performed.\\\",\\\"code\\\":103,\\\"override\\\":null},{\\\"description\\\":"
					+ "\\\"No/insufficient details supplied for address #4. No search performed.\\\","
					+ "\\\"code\\\":104,\\\"override\\\":null}],\\\"match\\\":null,\\\"warning\\\":null,"
					+ "\\\"mismatch\\\":[{\\\"description\\\":\\\"No trace found for address #1 and surname"
					+ "\\\",\\\"code\\\":7031,\\\"override\\\":null}],\\\"pass\\\":{\\\"value\\\":"
					+ "\\\"Mismatch\\\"},\\\"address\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"forename\\\":{"
					+ "\\\"value\\\":\\\"Nomatch\\\"},\\\"surname\\\":{\\\"value\\\":\\\"Nomatch\\\"},"
					+ "\\\"alert\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"country\\\":null,"
					+ "\\\"publishedTelephoneNumber\\\":\\\"\\\",\\\"dob\\\":{\\\"value\\\":\\\"NA\\\"},"
					+ "\\\"id\\\":106},{\\\"name\\\":\\\"UK Births\\\",\\\"description\\\":\\\"UK Births"
					+ " Registry Database.  Provides authentication against the first, last name, date of"
					+ " birth registration and mothers maiden name for individuals born in England and Wales"
					+ " between 1984 and 2003 inclusive.\\\",\\\"comment\\\":[{\\\"description\\\":"
					+ "\\\"No middle initial was supplied by the user\\\",\\\"code\\\":103,\\\"override\\\":null},{"
					+ "\\\"description\\\":\\\"No mother's maiden name was supplied by the user\\\","
					+ "\\\"code\\\":107,\\\"override\\\":null},{\\\"description\\\":\\\"No country of "
					+ "registration was supplied by the user, assuming England/Wales\\\",\\\"code\\\":109,"
					+ "\\\"override\\\":null}],\\\"match\\\":null,\\\"warning\\\":[{\\\"description\\\":"
					+ "\\\"The year of birth is outside the data range, unable to perform item check "
					+ "(Data supports 1984 to 2003 inclusive)\\\",\\\"code\\\":4002,\\\"override\\\":null},{"
					+ "\\\"description\\\":\\\"Not enough information was supplied to perform the item "
					+ "check\\\",\\\"code\\\":4001,\\\"override\\\":null}],\\\"mismatch\\\":null,\\\"pass"
					+ "\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"address\\\":{\\\"value\\\":\\\"NA\\\"},"
					+ "\\\"forename\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"surname\\\":{\\\"value\\\":"
					+ "\\\"Nomatch\\\"},\\\"alert\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"country\\\":null,"
					+ "\\\"dob\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"id\\\":133},{\\\"name\\\":"
					+ "\\\"UK NCOA (Alert Full)\\\",\\\"description\\\":\\\"UK NCOA (Alert Full) "
					+ "Database check.  Performs authentication of first, last name and address details "
					+ "(plus provides fraud flags, active, expired and pending redirect information) "
					+ "against national change of address data records.\\\",\\\"comment\\\":[{"
					+ "\\\"description\\\":\\\"Insufficient address details were supplied for address #2,"
					+ " unable to perform check\\\",\\\"code\\\":104,\\\"override\\\":null},{"
					+ "\\\"description\\\":\\\"Insufficient address details were supplied for address #3,"
					+ " unable to perform check\\\",\\\"code\\\":105,\\\"override\\\":null},{"
					+ "\\\"description\\\":\\\"Insufficient address details were supplied for address #4,"
					+ " unable to perform check\\\",\\\"code\\\":106,\\\"override\\\":null}],\\\"match\\\""
					+ ":[{\\\"description\\\":\\\"No Active redirect is set at address #1\\\",\\\"code\\\":"
					+ "1001,\\\"override\\\":null}],\\\"warning\\\":null,\\\"mismatch\\\":null,\\\"pass\\\""
					+ ":{\\\"value\\\":\\\"NA\\\"},\\\"address\\\":{\\\"value\\\":\\\"Nomatch\\\"},"
					+ "\\\"forename\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"surname\\\":{\\\"value\\\":"
					+ "\\\"Nomatch\\\"},\\\"alert\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"country\\\":null,"
					+ "\\\"dob\\\":{\\\"value\\\":\\\"NA\\\"},\\\"id\\\":239},{\\\"name\\\":\\\"UK Mortality\\\","
					+ "\\\"description\\\":\\\"UK Deceased Persons Database.  Provides checking of a first"
					+ " and last name at an address against the registered deceased persons database.\\\","
					+ "\\\"comment\\\":[{\\\"description\\\":\\\"No middle initial specified by user\\\","
					+ "\\\"code\\\":101,\\\"override\\\":null},{\\\"description\\\":\\\"No details supplied"
					+ " by user for address 2\\\",\\\"code\\\":112,\\\"override\\\":null},{\\\"description"
					+ "\\\":\\\"No details supplied by user for address 3\\\",\\\"code\\\":113,\\\"override"
					+ "\\\":null},{\\\"description\\\":\\\"No details supplied by user for address 4\\\","
					+ "\\\"code\\\":114,\\\"override\\\":null}],\\\"match\\\":[{\\\"description\\\":"
					+ "\\\"Halo source indicates this person is not deceased at address 1\\\",\\\"code\\\""
					+ ":1071,\\\"override\\\":null}],\\\"warning\\\":null,\\\"mismatch\\\":null,\\\"pass\\\""
					+ ":{\\\"value\\\":\\\"NA\\\"},\\\"address\\\":{\\\"value\\\":\\\"Nomatch\\\"},"
					+ "\\\"forename\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"surname\\\":{\\\"value\\\":"
					+ "\\\"Nomatch\\\"},\\\"alert\\\":{\\\"value\\\":\\\"Nomatch\\\"},\\\"country\\\":null,"
					+ "\\\"dob\\\":{\\\"value\\\":\\\"NA\\\"},\\\"id\\\":110}],\\\"score\\\":0,\\\"bandText"
					+ "\\\":\\\"No Match\\\",\\\"userBreakpoint\\\":null,\\\"noRetry\\\":null,\\\"chainID"
					+ "\\\":null,\\\"country\\\":\\\"UK\\\"}\",\"providerUniqueRefNo\":\"002-C-0000052774\","
					+ "\"bandText\":\"PASS\",\"overallScore\":\"0\",\"id\":248,\"contactSFId\":"
					+ "\"002-C-0000052774\"}");
			eventServiceLog3.setStatus("PASS");
			eventServiceLog3.setSummary("{\"status\":\"FAIL\",\"eidCheck\":true,"
					+ "\"verifiactionResult\":\"Not Available\",\"referenceId\":"
					+ "\"002-C-0000052774\",\"dob\":\"17/05/1992\",\"checkedOn\":"
					+ "\"2020-11-02 09:40:51.639\",\"providerName\":\"GBGROUP\","
					+ "\"providerMethod\":\"authenticateSP\"}");
			eventServiceLog3.setCratedBy(18);
			eventServiceLog3.setCreatedOn(Timestamp.valueOf("2020-11-02 09:40:51.639"));
			eventServiceLog3.setUpdatedBy(18);
			eventServiceLog3.setUpdatedOn(Timestamp.valueOf("2020-11-02 09:40:54.243"));
			messageExchange3.addEventServiceLog(eventServiceLog3);
			messageExchange3.setServiceTypeEnum(ServiceTypeEnum.KYC_SERVICE);
			KYCProviderRequest kycProviderRequest= new KYCProviderRequest();
			kycProviderRequest.setAccountSFId("0716E04000zKzriLBX");
			kycProviderRequest.setSourceApplication("Dione");
			kycProviderRequest.setCorrelationID(UUID.randomUUID());
			kycProviderRequest.setOrgCode(fundInRequest.getOrgCode());
			KYCContactRequest kycContactRequest= new KYCContactRequest();
			kycContactRequest.setContactSFId("002-C-0000052774");
			kycContactRequest.setId(52774);		
			PersonalDetails personalDetails= new PersonalDetails();
			personalDetails.setDob("1992-05-17");
			personalDetails.setForeName("Micheal");
			personalDetails.setSurName("Norse");
			kycContactRequest.setPersonalDetails(personalDetails);
			Address address= new Address();
			address.setAddress1("A, Dalemoor");
			address.setAddressLine1("A, Dalemoor");
			address.setStreet("A, Dalemoor");
			address.setAza("Keighley");
			address.setSubCity("Keighley");
			address.setCity("Keighley");
			address.setCountry("United Kingdom");
			kycContactRequest.setAddress(address);
			List<Phone> phoneList= new ArrayList<>();
			Phone phon= new Phone();
			phon.setNumber("+44-7311123456");
			phon.setType("MOBILE");
			kycContactRequest.setPhone(phoneList);
		KYCProviderResponse kycProviderResponse= new KYCProviderResponse();
		kycProviderResponse.setAccountId("0716E04000zKzriLBX");
		kycProviderResponse.setOrgCode(fundInRequest.getOrgCode());
		kycProviderResponse.setSourceApplication("Dione");
		List<KYCContactResponse> contactResponseList= new ArrayList<>();
		KYCContactResponse kyccontactResponse= new KYCContactResponse();
		kyccontactResponse.setBandText("POI NEEDED");
		kyccontactResponse.setErrorCode("KYC007");
		kyccontactResponse.setErrorDescription("GBGroup Error: Organisation is not active");
		kyccontactResponse.setContactSFId("002-C-0000052774");
		kyccontactResponse.setId(52774);
		kyccontactResponse.setOverallScore("Not Available");
		kyccontactResponse.setProviderName("GBGroup");
		kyccontactResponse.setProviderMethod("authenticateSP");
		kyccontactResponse.setStatus("FAIL");
		kycProviderResponse.setContactResponse(contactResponseList);
		contactResponseList.add(kyccontactResponse);
		messageExchange3.setRequest(kycProviderRequest);
		messageExchange3.setResponse(kycProviderResponse);
			MessageExchange messageExchange4 = new MessageExchange();
			messageExchange4.setServiceTypeEnum(ServiceTypeEnum.FRAUGSTER_SERVICE);
			EventServiceLog eventServiceLog4 = new EventServiceLog();
			eventServiceLog4.setEventId(941839);
			eventServiceLog4.setEntityType("CONTACT");
			eventServiceLog4.setEntityId(52774);
			eventServiceLog4.setEntityVersion(1);
			eventServiceLog4.setServiceName("FRAUGSTER");
			eventServiceLog4.setServiceProviderName("GBGROUP");
			eventServiceLog4.setProviderResponse("{\"frgTransId\":\"ebbf783b-e83c-4259-b22b-58b652fef830\","
					+ "\"score\":\"0.00019658374\",\"fraugsterApproved\":\"1.0\",\"id\":52774,"
					+ "\"status\":\"PASS\",\"percentageFromThreshold\":\"-97.41\","
					+ "\"decisionDrivers\":{\"txn_value\":{\"value\":\"2,000 - 5,000\","
					+ "\"featureImportance\":-0.2272942},\"source\":{\"value\":\"WEB\","
					+ "\"featureImportance\":0.2740615},\"reg_mode\":{\"value\":\"MOBILE\","
					+ "\"featureImportance\":-0.067475386},\"affiliate_name\":{\"value\":"
					+ "\"STP - TEMPORARY AFFILIATE CODE\",\"featureImportance\":-0.5263882},"
					+ "\"ad_campaign\":{\"value\":\"\",\"featureImportance\":-0.04260528},"
					+ "\"channel\":{\"value\":\"MOBILE\",\"featureImportance\":-0.44612977},"
					+ "\"ip_line_speed\":{\"value\":\"\",\"featureImportance\":0.09510705},"
					+ "\"search_engine\":{\"value\":\"\",\"featureImportance\":-0.09082934},"
					+ "\"gender_fullcontact\":{\"value\":\"MALE\",\"featureImportance\":-0.065191016},"
					+ "\"browser_lang\":{\"value\":\"\",\"featureImportance\":-0.11581852},"
					+ "\"screen_resolution\":{\"value\":\"750X1334\",\"featureImportance\":-0.03602964},"
					+ "\"country_of_residence\":{\"value\":\"GBR\",\"featureImportance\":-0.0832874},"
					+ "\"fullcontact_matched\":{\"value\":\"YES\",\"featureImportance\":-0.0848447},"
					+ "\"ip_longitude\":{\"value\":\"\",\"featureImportance\":-1.6565734},\"keywords\":{"
					+ "\"value\":\"\",\"featureImportance\":-0.060239006},\"ip_connection_type\":{\"value"
					+ "\":\"\",\"featureImportance\":-0.24046624},\"device_type\":{\"value\":\"IPHONE\","
					+ "\"featureImportance\":-0.005574821},\"op_country\":{\"value\":\"\","
					+ "\"featureImportance\":0.0},\"residential_status\":{\"value\":\"\","
					+ "\"featureImportance\":0.00016981395},\"address_type\":{\"value\":\"CURRENT ADDRESS"
					+ "\",\"featureImportance\":0.0},\"device_manufacturer\":{\"value\":\"APPLE\","
					+ "\"featureImportance\":0.009507372},\"ip_carrier\":{\"value\":\"\","
					+ "\"featureImportance\":-0.16261506},\"device_name\":{\"value\":\"BNT SOFT IPHONE\","
					+ "\"featureImportance\":-0.028641686},\"ip_anonymizer_status\":{\"value\":\"\","
					+ "\"featureImportance\":-0.033437513},\"ip_routing_type\":{\"value\":\"\","
					+ "\"featureImportance\":-0.046029653},\"eid_status\":{\"value\":\"1\","
					+ "\"featureImportance\":-0.15336347},\"age_range_fullcontact\":{\"value\":"
					+ "\"NONE\",\"featureImportance\":-0.111547545},\"location_country_fullcontact\":{"
					+ "\"value\":\"NONE\",\"featureImportance\":-0.0134919975},\"ip_latitude\":{\"value\":"
					+ "\"\",\"featureImportance\":-1.419807},\"browser_type\":{\"value\":"
					+ "\"\",\"featureImportance\":-0.025675036},\"title\":{\"value\":\"\","
					+ "\"featureImportance\":-0.19128892},\"referral_text\":{\"value\":"
					+ "\"NATURAL\",\"featureImportance\":-0.048767813},\"browser_online\":{"
					+ "\"value\":\"\",\"featureImportance\":0.0023156733},\"device_os_type\":{"
					+ "\"value\":\"APPLE IOS\",\"featureImportance\":-0.060722403},\"sub_source\":{"
					+ "\"value\":\"NATURAL\",\"featureImportance\":-0.27592996},\"browser_version\":{"
					+ "\"value\":\"\",\"featureImportance\":-0.026970118},\"email_domain\":{\"value\":"
					+ "\"GMAIL\",\"featureImportance\":-0.46747407},\"branch\":{\"value\":"
					+ "\"MOORGATE HO\",\"featureImportance\":-0.41487223},\"sanction_status\":{\"value\":"
					+ "\"1\",\"featureImportance\":0.06794018},\"turnover\":{\"featureImportance\":-0.22452144},"
					+ "\"region_suburb\":{\"value\":\"\",\"featureImportance\":-0.407228},"
					+ "\"social_profiles_count\":{\"value\":\"1.0\",\"featureImportance\":-0.17582603},"
					+ "\"state\":{\"value\":\"KEIGHLEY\",\"featureImportance\":-0.48905626}}}");
			eventServiceLog4.setStatus("PASS");
			eventServiceLog4.setSummary("{\"status\":\"PASS\",\"frgTransId\":"
					+ "\"ebbf783b-e83c-4259-b22b-58b652fef830\",\"score\":\"-97.41\","
					+ "\"fraugsterApproved\":\"1.0\",\"cdTrasId\":\"00000527741\"}");
			eventServiceLog4.setCratedBy(18);
			eventServiceLog4.setCreatedOn(Timestamp.valueOf("2020-11-02 09:40:51.643"));
			eventServiceLog4.setUpdatedBy(18);
			eventServiceLog4.setUpdatedOn(Timestamp.valueOf("2020-11-02 09:40:53.935"));
			messageExchange4.addEventServiceLog(eventServiceLog4);
			FraugsterSignupRequest fraugsterSignupRequest= new FraugsterSignupRequest();
			fraugsterSignupRequest.setCorrelationID(UUID.randomUUID());
			fraugsterSignupRequest.setOrgCode(fundInRequest.getOrgCode());
			fraugsterSignupRequest.setSourceApplication(fundInRequest.getSourceApplication());
			fraugsterSignupRequest.setRequestType("GATEWAY_SERVICE");
			List<FraugsterSignupContactRequest> fraugsterSignupContactRequestList= new ArrayList<>();
			 FraugsterSignupContactRequest fraugsterSignupContactRequest= new FraugsterSignupContactRequest();
			 fraugsterSignupContactRequest.setAccountContactNum(1);
			 fraugsterSignupContactRequest.setAddressType("Current Address");
			 fraugsterSignupContactRequest.setAffiliateName("STP - temporary affiliate code");
			 fraugsterSignupContactRequest.setAza("Keighley");
			 fraugsterSignupContactRequest.setBillingAddress("A, Dalemoor,Keighley,BD22 8EZ,UK");
			fraugsterSignupContactRequest.setBranch("Moorgate HO");
			fraugsterSignupContactRequest.setBrowserScreenResolution("750x1334");
			fraugsterSignupContactRequest.setCity("Keighley");
			fraugsterSignupContactRequest.setChannel("Mobile");
			fraugsterSignupContactRequest.setCountry("United Kingdom");
			fraugsterSignupContactRequest.setCountryOfResidenceCode("GBR");
			fraugsterSignupContactRequest.setDeviceType("Apple");
			fraugsterSignupContactRequest.setCustomerType("PFX");
			fraugsterSignupContactRequest.setCustID("7836E06040t6mElJBA");     
			fraugsterSignupContactRequest.setDeviceManufacturer("Apple");
			fraugsterSignupContactRequest.setDeviceName("BNT SOFT iPhone");
			fraugsterSignupContactRequest.setDeviceID("950AA01C-0B9C-4B37-BB39-847FA9CF5C21");
			fraugsterSignupContactRequest.setDeviceVersion("12.1.4");
			fraugsterSignupContactRequest.setDob("1992-05-17");
			fraugsterSignupContactRequest.setEidStatus("1");
			fraugsterSignupContactRequest.setEmail("abc2133@gmail");
			fraugsterSignupContactRequest.setEventType("signup");
			fraugsterSignupContactRequest.setFirstName("Micheal");
			fraugsterSignupContactRequest.setId(52774);
			fraugsterSignupContactRequest.setiPAddress("172.31.4.211");
			fraugsterSignupContactRequest.setiPLatitude(0.0f);
			fraugsterSignupContactRequest.setiPLongitude(0.0f);
			fraugsterSignupContactRequest.setLastName("Norse");
			fraugsterSignupContactRequest.setMunicipalityOfBirth("Keighley");
			fraugsterSignupContactRequest.setOccupation("Other");
			fraugsterSignupContactRequest.setOnQueue(false);
			fraugsterSignupContactRequest.setOsDateAndTime("2019-05-23T16:10:55Z");
			fraugsterSignupContactRequest.setOsName("iOS");
			fraugsterSignupContactRequest.setPostCode("BD22 8EZ");
			fraugsterSignupContactRequest.setPhoneMobile("+44-7311123456");
			fraugsterSignupContactRequest.setPostCodeLatitude(0.0f);
			fraugsterSignupContactRequest.setPostCodeLongitude(0.0f);
			fraugsterSignupContactRequest.setPrimaryContact(true);
			fraugsterSignupContactRequest.setPrimaryPhone("Mobile");
			fraugsterSignupContactRequest.setReferralText("Natural");
			fraugsterSignupContactRequest.setRegistrationDateTime("2019-05-23T11:38:01Z");
			fraugsterSignupContactRequest.setRegMode("Mobile");
			fraugsterSignupContactRequest.setSanctionStatus("1");
			fraugsterSignupContactRequest.setSource("Web");
			fraugsterSignupContactRequest.setStreet("A, Dalemoor");
			fraugsterSignupContactRequest.setSubCity("Keighley");
			fraugsterSignupContactRequest.setSubSource("Natural");
			fraugsterSignupContactRequest.setTransactionID("00000527741");
			fraugsterSignupContactRequest.setTxnValue(5000.00);
			fraugsterSignupContactRequest.setTxnValueRange("2,000 - 5,000");
			fraugsterSignupContactRequestList.add(fraugsterSignupContactRequest);
			fraugsterSignupRequest.setContactRequests(fraugsterSignupContactRequestList);
			fraugsterSignupRequest.setContactRequests(fraugsterSignupContactRequestList);
			messageExchange4.setRequest(fraugsterSignupRequest);
			FraugsterSignupResponse fraugsterSignupResponse= new FraugsterSignupResponse();
			fraugsterSignupResponse.setOrgCode(fundInRequest.getOrgCode());
			fraugsterSignupResponse.setSourceApplication(fundInRequest.getSourceApplication());
			fraugsterSignupResponse.setStatus("Pass");			
			List<FraugsterSignupContactResponse> contactResponses= new ArrayList<>();
			FraugsterSignupContactResponse fraugsterSignupContactResponse= new FraugsterSignupContactResponse();
			fraugsterSignupContactResponse.setFraugsterId("ebbf783b-e83c-4259-b22b-58b652fef830");
			fraugsterSignupContactResponse.setFraugsterApproved("1.0");
			fraugsterSignupContactResponse.setId(52774);
			fraugsterSignupContactResponse.setPercentageFromThreshold("97.41");
			fraugsterSignupContactResponse.setScore("0.00019658374");
			fraugsterSignupContactResponse.setStatus("Pass");
			DecisionDrivers decisionDrivers= new DecisionDrivers();
			Feature adCampaign= new Feature();
			adCampaign.setFeatureImportance(BigDecimal.valueOf(-0.04260528));
			adCampaign.setValue("");
			Feature affiliateName=new Feature();
			affiliateName.setFeatureImportance(BigDecimal.valueOf(0,5263882));
			affiliateName.setValue("STP - TEMPORARY AFFILIATE CODE");
			Feature addressType= new Feature();
			addressType.setFeatureImportance(BigDecimal.valueOf(0.0));
			addressType.setValue("Current Address");
			Feature ageFeature = new Feature();
			ageFeature.setFeatureImportance(BigDecimal.valueOf(-0.111547545));
			ageFeature.setValue("None");
			decisionDrivers.setAdCampaign(adCampaign);
			decisionDrivers.setAddressType(addressType);
			decisionDrivers.setAffiliateName(affiliateName);
			decisionDrivers.setAgeRangeFullContact(ageFeature);
			fraugsterSignupContactResponse.setDecisionDrivers(decisionDrivers);
			fraugsterSignupResponse.setContactResponses(contactResponses);
			messageExchange4.setResponse(fraugsterSignupResponse);
			MessageExchange messageExchange5 = new MessageExchange();
			messageExchange3.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			EventServiceLog eventServiceLog5 = new EventServiceLog();
			eventServiceLog5.setEventId(941839);
			eventServiceLog5.setEntityType("CONTACT");
			eventServiceLog5.setEntityId(52774);
			eventServiceLog5.setEntityVersion(1);
			eventServiceLog5.setServiceName("SANCTION");
			eventServiceLog5.setServiceProviderName("SANCTION_SERVICE");
			eventServiceLog5.setProviderResponse("{\"status\":\"PASS\","
					+ "\"statusDescription\":\"Lookup PASSED\",\"ofacList\":\"Safe\","
					+ "\"resultsCount\":0,\"pendingCount\":0,\"sanctionId\":"
					+ "\"002-C-0000052774\",\"worldCheck\":\"Safe\",\"providerResponse"
					+ "\":\"{\\\"status\\\":\\\"PASS\\\",\\\"message\\\":\\\"Lookup PASSED\\\","
					+ "\\\"returned\\\":0,\\\"notReturned\\\":0,\\\"resultsCount\\\":0,\\"
					+ "\"hitCount\\\":0,\\\"pendingCount\\\":0,\\\"safeCount\\\":0,\\\"version"
					+ "\\\":\\\"v4.8.1.2 - Released January 14, 2020\\\",\\\"responses\\\":{"
					+ "\\\"lstservicesLookupResponse\\\":[{\\\"status\\\":\\\"PASS\\\","
					+ "\\\"message\\\":\\\"Lookup PASSED\\\",\\\"returned\\\":0,"
					+ "\\\"notReturned\\\":0,\\\"resultsCount\\\":0,\\\"hitCount\\\":0,"
					+ "\\\"pendingCount\\\":0,\\\"safeCount\\\":0,\\\"complianceRecords\\\":{"
					+ "\\\"slcomplianceRecord\\\":[]},\\\"clientId\\\":\\\"002-C-0000052774\\\","
					+ "\\\"clientKey\\\":673265162,\\\"version\\\":"
					+ "\\\"v4.8.1.2 - Released January 14, 2020\\\",\\\"isiReserved\\\":\\\"\\\","
					+ "\\\"searchResults\\\":{\\\"slsearchResults\\\":[{\\\"searchName\\\":"
					+ "\\\"Individual Name Search\\\",\\\"clientId\\\":\\\"002-C-0000052774\\\","
					+ "\\\"clientName\\\":\\\"Micheal Norse\\\",\\\"returned\\\":0,\\\"notReturned\\\":0,"
					+ "\\\"sequenceNumber\\\":35321,\\\"searchDateTime\\\":1604270459893,"
					+ "\\\"searchMatches\\\":{\\\"slsearchListRecord\\\":[]}}]},\\\"uboResults\\\":null}]},"
					+ "\\\"isiReserved\\\":\\\"\\\"}\",\"providerName\":\"FINSCAN\","
					+ "\"providerMethod\":\"SLLookupMulti\",\"contactId\":52774}");
			eventServiceLog5.setStatus("PASS");
			eventServiceLog5.setSummary("{\"status\":\"PASS\",\"sanctionId\":"
					+ "\"002-C-0000052774\",\"ofacList\":\"Safe\",\"worldCheck\":\"Safe\","
					+ "\"updatedOn\":\"2020-11-02 09:41:02.455\",\"providerName\":\"FINSCAN"
					+ "\",\"providerMethod\":\"SLLookupMulti\"}");
			eventServiceLog5.setCratedBy(18);
			eventServiceLog5.setCreatedOn(Timestamp.valueOf("2020-11-02 09:40:51.649"));
			eventServiceLog5.setUpdatedBy(18);
			eventServiceLog5.setUpdatedOn(Timestamp.valueOf("2020-11-02 09:41:02.456"));
			messageExchange5.addEventServiceLog(eventServiceLog5);
			SanctionRequest sanctionRequest = new SanctionRequest();
			sanctionRequest.setCustomerNumber("4802304009336016");
			List<SanctionContactRequest> contactrequests= new ArrayList<>();
			SanctionContactRequest sanctionContactRequest =new SanctionContactRequest();
			sanctionContactRequest.setCategory("Individual");
			sanctionContactRequest.setContactId(52774);
			sanctionContactRequest.setCountry("United Kingdom");
			sanctionContactRequest.setDob("1992-05-17");
			sanctionContactRequest.setFullName("Micheal Norse");
			sanctionContactRequest.setIsExisting(false);
			sanctionContactRequest.setSanctionId("002-C-0000052774");
			sanctionRequest.setContactrequests(contactrequests);
			messageExchange5.setRequest(sanctionRequest);
			SanctionResponse sanctionResponse= new SanctionResponse();
			sanctionResponse.setProviderResponse("{\"status\":\"PASS\",\"message\":"
					+ "\"Lookup PASSED\",\"returned\":0,\"notReturned\":0,\"resultsCount\":0,"
					+ "\"hitCount\":0,\"pendingCount\":0,\"safeCount\":0,\"version\":"
					+ "\"v4.8.1.2 - Released January 14, 2020\",\"responses\":{"
					+ "\"lstservicesLookupResponse\":[{\"status\":\"PASS\",\"message\":"
					+ "\"Lookup PASSED\",\"returned\":0,\"notReturned\":0,\"resultsCount\":0,"
					+ "\"hitCount\":0,\"pendingCount\":0,\"safeCount\":0,\"complianceRecords\":{"
					+ "\"slcomplianceRecord\":[]},\"clientId\":\"002-C-0000052774\","
					+ "\"clientKey\":673265162,\"version\":\"v4.8.1.2 - Released January 14, 2020\","
					+ "\"isiReserved\":\"\",\"searchResults\":{\"slsearchResults\":[{\"searchName\":"
					+ "\"Individual Name Search\",\"clientId\":\"002-C-0000052774\",\"clientName\":"
					+ "\"Micheal Norse\",\"returned\":0,\"notReturned\":0,\"sequenceNumber\":35321,"
					+ "\"searchDateTime\":1604270459893,\"searchMatches\":{\"slsearchListRecord\":[]}}]},"
					+ "\"uboResults\":null}]},\"isiReserved\":\"\"}");
			sanctionResponse.setDecision(SanctionResponse.DECISION.SUCCESS);
			List<SanctionContactResponse> sanctionContactList = new ArrayList<>();
			SanctionContactResponse sanctionContactResponse= new SanctionContactResponse();
			sanctionContactResponse.setOfacList("Safe");
			sanctionContactResponse.setWorldCheck("Safe");
			sanctionContactResponse.setContactId(52774);
			sanctionContactResponse.setPendingCount(0);
			sanctionContactResponse.setProviderMethod("SLLookupMulti");
			sanctionContactResponse.setProviderName("FINSCAN");
			sanctionContactResponse.setResultsCount(0);
			sanctionContactResponse.setProviderResponse("{\"status\":\"PASS\",\"message\":"
					+ "\"Lookup PASSED\",\"returned\":0,\"notReturned\":0,\"resultsCount\":0,"
					+ "\"hitCount\":0,\"pendingCount\":0,\"safeCount\":0,\"version\":"
					+ "\"v4.8.1.2 - Released January 14, 2020\",\"responses\":{"
					+ "\"lstservicesLookupResponse\":[{\"status\":\"PASS\",\"message\":\"Lookup PASSED\","
					+ "\"returned\":0,\"notReturned\":0,\"resultsCount\":0,\"hitCount\":0,\"pendingCount\":0,"
					+ "\"safeCount\":0,\"complianceRecords\":{\"slcomplianceRecord\":[]},"
					+ "\"clientId\":\"002-C-0000052774\",\"clientKey\":673265162,\"version\":"
					+ "\"v4.8.1.2 - Released January 14, 2020\",\"isiReserved\":\"\",\"searchResults\":{"
					+ "\"slsearchResults\":[{\"searchName\":\"Individual Name Search\",\"clientId\":"
					+ "\"002-C-0000052774\",\"clientName\":\"Micheal Norse\",\"returned\":0,\"notReturned\":0,"
					+ "\"sequenceNumber\":35321,\"searchDateTime\":1604270459893,\"searchMatches\":{"
					+ "\"slsearchListRecord\":[]}}]},\"uboResults\":null}]},\"isiReserved\":\"\"}");
			sanctionContactResponse.setSanctionId("002-C-0000052774");
			sanctionContactResponse.setStatus("Pass");
			sanctionContactResponse.setStatusDescription("LookUp Passed");
			sanctionResponse.setContactResponses(sanctionContactList);
			messageExchange5.setResponse(sanctionResponse);

			FundsInCreateResponse response= new FundsInCreateResponse();
			response.setStatus("PASS");
			gateWayMessageExchange.setResponse(response);
			
			messageContext.appendMessageExchange(messageExchange);
			messageContext.appendMessageExchange(messageExchange2);
			messageContext.appendMessageExchange(messageExchange3);
			messageContext.appendMessageExchange(messageExchange4);
			messageContext.appendMessageExchange(messageExchange5);
			
	      return message;
			
		} catch (IOException e) {
			System.out.println(e);
		}
		return message;
	}
	
	public TransactionMonitoringPaymentsInRequest transformRequest() {
	
	TransactionMonitoringPaymentsInRequest request = new TransactionMonitoringPaymentsInRequest();
	request.addAttribute("ACCOUNT_TM_FLAG", 1);
	
	request.setRequestType("payment_in");
	request.setTradeAccountNumber("080801015831889");
	request.setTransactionId(3601500);
	request.setOrgCode("Currencies Direct");
	request.setSourceApplication("Titan");
	request.setTradeContactId(980781939);
	request.setCustType("CFX");
	request.setPurposeOfTrade("FILL_WALLET");
	request.setSellingAmount(500.0); 
	request.setSellingAmountBaseValue(500.0);
	request.setTransactionCurrency("GBP");
	request.setContractNumber("080801015831889-003601500");
	request.setPaymentMethod("SWITCH/DEBITCARD");
	request.setCcFirstName("Robin John");
	request.setThirdPartyPayment(true);
	request.setCountryOfFund("IND");
	request.setCustomerLegalEntity("CDLGB");
	return request;
	
	
	}
	
	
	
	@Test
	public void testTransformRequest() {
		message = tmRequest.transformRequest(getMessageContext());				
		assertEquals("PASS", message.getPayload().getMessageCollection().get(0).getMessageExchange()
				.getResponse(FundsInCreateResponse.class).getStatus());		
	}
	
	@Test
	public void testTransformComplianceLog() {
		Integer accountID=87938;
		TransactionMonitoringPaymentsInRequest request=transformRequest();
		Method privateMethod;
		try {
			privateMethod = TransactionMonitoringTransformer.class.getDeclaredMethod("transformComplianceLog", Integer.class, TransactionMonitoringPaymentsInRequest.class);
			privateMethod.setAccessible(true);
			privateMethod.invoke(tmRequest, accountID, request);
			assertEquals("080801015831889-003601500", request.getContractNumber());			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}	
}	
