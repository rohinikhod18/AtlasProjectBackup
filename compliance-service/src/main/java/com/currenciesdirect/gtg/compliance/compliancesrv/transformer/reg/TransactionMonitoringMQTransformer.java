package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.reg;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.CardDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.Company;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.ConversionPrediction;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.CorperateCompliance;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IPAddressDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.RiskProfile;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringContactSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringFundsProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsInDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsOutDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionAccountMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionCFXLegalEntityMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionCardMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionContactMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionIPDeviceMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionPaymentINMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionPaymentOUTMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionSignupMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class TransactionMonitoringMQTransformer.
 */
public class TransactionMonitoringMQTransformer extends AbstractTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(TransactionMonitoringMQTransformer.class);
	
	/** The Constant ERROR. */
	private static final String ERROR = "Error in reading TM Account response";
	private static final String TIME_STRING = " 00:00:00";

	
	
	/** The funds in DB service impl. */
	@Autowired
	protected FundsOutDBServiceImpl fundsOutDBServiceImpl;
	
	/** The funds in DB service impl. */
	@Autowired
	protected FundsInDBServiceImpl fundsInDBServiceImpl;

	/**
	 * Transform request.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		TransactionMonitoringMQRequest transactionMonitoringMQRequest = (TransactionMonitoringMQRequest) messageExchange
				.getRequest();
		String jsonCxExchangeRequest;

		try {
			MessageExchange ccExchange = new MessageExchange();
			
			String json = JsonConverterUtil
					.convertToJsonWithoutNull(transactionMonitoringMQRequest.getRequestJson());

			if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.SIGN_UP)
					|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.UPDATE)
					|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.ADD_CONTACT)) {
		
				IntuitionSignupMQRequest intuitionSignupMQRequest = JsonConverterUtil	
						.convertToObject(IntuitionSignupMQRequest.class, json);	
				ccExchange = createAccountExchange(message, intuitionSignupMQRequest, transactionMonitoringMQRequest);	
	
			} else if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_in")
						|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_in_update")) {
				
				IntuitionPaymentINMQRequest intuitionPaymentINMQRequest = JsonConverterUtil
						.convertToObject(IntuitionPaymentINMQRequest.class, json);

				ccExchange = createPaymentInExchange(message, intuitionPaymentINMQRequest, transactionMonitoringMQRequest);
			} else if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_out")
						|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_out_update")) {
				
				IntuitionPaymentOUTMQRequest intuitionPaymentOUTMQRequest = JsonConverterUtil
						.convertToObject(IntuitionPaymentOUTMQRequest.class, json);

				ccExchange = createPaymentOutExchange(message, intuitionPaymentOUTMQRequest, transactionMonitoringMQRequest);
			}

			jsonCxExchangeRequest = JsonConverterUtil.convertToJsonWithoutNull(ccExchange.getRequest());
			LOG.debug("::::::::::::::: TransactionMonitoringMQTransformer : {}", jsonCxExchangeRequest);

			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOG.error("Error in TransactionMonitoringMQTransformer class : transformRequest -", e);
			message.getPayload().setFailed(true);

		}

		return message;
	}

	/**
	 * Creates the account exchange.
	 *
	 * @param message the message
	 * @param accRequest the acc request
	 * @param transactionMonitoringMQRequest the transaction monitoring MQ request
	 * @return the message exchange
	 * @throws ComplianceException the compliance exception
	 */
	private MessageExchange createAccountExchange(Message<MessageContext> message, IntuitionSignupMQRequest signupRequest,
			TransactionMonitoringMQRequest transactionMonitoringMQRequest) {
		Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
				.getAdditionalAttribute("eventId");
		MessageExchange ccExchange = new MessageExchange();
		TransactionMonitoringMQServiceRequest mqRequest = new TransactionMonitoringMQServiceRequest();
		TransactionMonitoringMQServiceResponse mqResponse = new TransactionMonitoringMQServiceResponse();
		TransactionMonitoringSignupRequest request = new TransactionMonitoringSignupRequest();
		TransactionMonitoringSignupResponse tmSignUpResponse = new TransactionMonitoringSignupResponse();
		TransactionMonitoringAccountRequest tmSingUpAccountRequests = new TransactionMonitoringAccountRequest();
		TransactionMonitoringAccountSignupResponse tmSingUpAccountResponse = new TransactionMonitoringAccountSignupResponse();

		List<TransactionMonitoringContactRequest> tmSingUpContactRequestsList = new ArrayList<>();
		
		try {

			tmSingUpAccountRequests.setId(transactionMonitoringMQRequest.getAccountID());
			transformAccountRequest(tmSingUpAccountRequests, signupRequest);
			request.setTransactionMonitoringAccountRequest(tmSingUpAccountRequests);
			
			request.setRequestType(transactionMonitoringMQRequest.getRequestType());
			request.setOrgCode(transactionMonitoringMQRequest.getOrgCode());
			request.setCreatedBy(transactionMonitoringMQRequest.getCreatedBy());
			request.setIsPresent(transactionMonitoringMQRequest.getIsPresent());
			request.setAccountTMFlag(1);
			request.setIsCardApply(signupRequest.getIsCardApply()); //card field
			request.setRegistrationLoadCriteria(signupRequest.getRegistrationLoadCriteria()); //AT-5127
			
			
			tmSingUpAccountResponse.setAccountId(transactionMonitoringMQRequest.getAccountID());
			tmSingUpAccountResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

			TransactionMonitoringAccountSummary accSummary = new TransactionMonitoringAccountSummary();
			accSummary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			
			for (IntuitionContactMQRequest contact : signupRequest.getContacts() ){
				TransactionMonitoringContactRequest tmSingupContact = new TransactionMonitoringContactRequest();
				transformContactRequest(tmSingupContact, contact);
				
				
				tmSingUpContactRequestsList.add(tmSingupContact);
			}
			
			request.setTransactionMonitoringContactRequests(tmSingUpContactRequestsList);

			ccExchange.addEventServiceLog(
					createEventServiceLogEntry(eventId, ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND,
							ServiceProviderEnum.TRANSACTION_MONITORING_SIGN_UP_ACCOUNT,
							transactionMonitoringMQRequest.getAccountID(), 1, EntityEnum.ACCOUNT.name(),
							transactionMonitoringMQRequest.getCreatedBy(), tmSingUpAccountResponse, accSummary));

			mqRequest.setTransactionMonitoringSignupRequest(request);
			mqRequest.setRequestType(transactionMonitoringMQRequest.getRequestType());
			mqRequest.setOrgCode(transactionMonitoringMQRequest.getOrgCode());
			mqRequest.setCreatedBy(transactionMonitoringMQRequest.getCreatedBy());
			ccExchange.setRequest(mqRequest);
			tmSignUpResponse.setTransactionMonitoringAccountSignupResponse(tmSingUpAccountResponse);
			mqResponse.setTransactionMonitoringSignupResponse(tmSignUpResponse);
			ccExchange.setResponse(mqResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);

		} catch (Exception e) {
			LOG.error("{1}", e);
		}

		return ccExchange;
	}

	/**
	 * Transform account request.
	 *
	 * @param requestData the request data
	 * @param account the account
	 */
	private void transformAccountRequest(TransactionMonitoringAccountRequest requestData,
			IntuitionSignupMQRequest signupRequest) {

		requestData.setTradeAccountNumber(signupRequest.getTradeAccNumber());
		requestData.setRegistrationDateTime(signupRequest.getRegDateTime());
		requestData.setChannel(signupRequest.getChannel());
		requestData.setAccountName(signupRequest.getActName());
		requestData.setCountryOfInterest(signupRequest.getCountryInterest());
		requestData.setTradingName(signupRequest.getTradeName());
		requestData.setPurposeOfTran(signupRequest.getTrasactionPurpose());
		requestData.setCountriesOfOperation(signupRequest.getOpCountry());
		requestData.setServiceRequired(signupRequest.getServiceReq());
		requestData.setValueOfTransaction(signupRequest.getTxnValue());
		requestData.setSourceOfFund(signupRequest.getSourceOfFund());
		requestData.setSource(signupRequest.getSource());
		requestData.setSubSource(signupRequest.getSubSource());
		requestData.setReferral(signupRequest.getReferral());
		requestData.setReferralText(signupRequest.getReferralText());
		requestData.setExtendedReferral(signupRequest.getExtendedReferral());
		requestData.setAdCampaign(signupRequest.getAdCampaign());
		requestData.setKeywords(signupRequest.getKeywords());
		requestData.setSearchEngine(signupRequest.getSearchEngine());
		requestData.setAffiliateName(signupRequest.getAffiliateName());
		requestData.setRegistrationMode(signupRequest.getRegMode());
		requestData.setCustLegalEntity(signupRequest.getOrganizationLegalEntity());
		requestData.setCustType(signupRequest.getCustType());
		requestData.setBuyingCurrency(signupRequest.getBuyingCurrency());
		requestData.setSellingCurrency(signupRequest.getSellingCurrency());
		requestData.setComplianceLog(signupRequest.getComplianceLog());
		
		
		IntuitionAccountMQRequest account = signupRequest.getAccount().get(0);
		IntuitionCFXLegalEntityMQRequest cfxLegal = signupRequest.getCfxLegalEntity().get(0);
		IntuitionIPDeviceMQRequest ipDevice = null;
		if(signupRequest.getIpDevice() != null) {
			 ipDevice = signupRequest.getIpDevice().get(0);
		}
		IntuitionCardMQRequest cardInfo = null;
		if(signupRequest.getCard() != null) {
			 cardInfo = signupRequest.getCard().get(0);
		}
		
		requestData.setAccSFID(account.getAccSfId());
		if(account.getAccountId() != null) {
			requestData.setTradeAccountID(Integer.parseInt(account.getAccountId()));
		}
		requestData.setBranch(account.getBranch());
		if (cfxLegal.getTurnover() != null) {
			requestData.setTurnover(cfxLegal.getTurnover());
		}
		requestData.setAffiliateNumber(account.getAffiliateNumber());
		requestData.setCardDeliveryToSecondaryAddress(account.getCardDeliveryToSecondaryAddress()); // card fields
		requestData.setSecondaryAddressPresent(account.getSecondaryAddressPresent()); // card fields
		//Add for AT-5318
		requestData.setParentType(account.getParentType()); 
		requestData.setParentTitanAccNum(account.getParentID()); 
		requestData.setMetInPerson(account.getMetInPerson()); 
		requestData.setSelfieOnFile(account.getSelfieOnFile()); 
		requestData.setVulnerabilityCharacteristic(account.getVulnerabilityCharacteristic()); 
		//Add for AT-5376
		if(account.getNoOfChild()!=null)
			requestData.setNumberOfChilds(String.valueOf(account.getNoOfChild()));
		
		requestData.setLegacyTradeAccountNumber(account.getLegacyAccountAuroraTitanCustomerNo());//Add for AT-5393
		
		// Added for AT-5529 
		if (requestData.getCustType().equalsIgnoreCase("CFX") || requestData.getCustType().equalsIgnoreCase(Constants.CFXETAILER)) {
			requestData.setBlacklist(account.getBlacklist());
			requestData.setSanctionResult(account.getSanctionResult());
		}
		Company company = new Company();
			company.setCountryOfEstablishment(cfxLegal.getCountryOfEstablishment());
			company.setCorporateType(cfxLegal.getCorporateType());
			if (cfxLegal.getIncorporationDate() != null) {
				company.setIncorporationDate(cfxLegal.getIncorporationDate());
			}
			company.setIndustry(cfxLegal.getIndustry());
			company.setEtailer(signupRequest.getEtailer());
			company.setRegistrationNo(cfxLegal.getRegistrationNo());
			requestData.setWebsite(cfxLegal.getWebsite());
			
			company.setBillingAddress(cfxLegal.getCompanyBillingAddress());
			company.setCompanyPhone(cfxLegal.getCompanyPhone());
			company.setShippingAddress(cfxLegal.getShippingAddress());
			company.setCountryRegion(cfxLegal.getCountryRegion());
			if (cfxLegal.getTcSignedDate() != null) {
				company.settAndcSignedDate(cfxLegal.getTcSignedDate());
			}
			company.setVatNo(cfxLegal.getVatNo());
			company.setOption(cfxLegal.getOption());
			company.setTypeOfFinancialAccount(cfxLegal.getTypeOfFinancialAccount());
			company.setEstNoTransactionsPcm(cfxLegal.getEstNoTransactionsPcm());
			//Add for AT-5393
			if (cfxLegal.getOngoingDueDiligenceDate() != null) {
				company.setOngoingDueDiligenceDate(cfxLegal.getOngoingDueDiligenceDate());
			}
			
		requestData.setCompany(company);
		
		CorperateCompliance corperateCompliance = transformCorperateComplianceData(cfxLegal);
		corperateCompliance.setRegistrationNumber(cfxLegal.getRegistrationNo());
		requestData.setCorperateCompliance(corperateCompliance);
		
		RiskProfile riskProfile = transformRiskProfileData(cfxLegal);
		requestData.setRiskProfile(riskProfile);
		
		IPAddressDetails ipAddress = null;
		if (ipDevice != null) {
			ipAddress = transformIpAddressDetail(ipDevice);
		}
		requestData.setiPAddressDetails(ipAddress);
		
		CardDetails card = null;
		if(cardInfo != null) {
			card = transformCardDetail(cardInfo);
		}
		requestData.setCardDetails(card);
		
		ConversionPrediction conversionPrediction = new ConversionPrediction();
		conversionPrediction.setAccountId(account.getAccountId());
		conversionPrediction.setConversionFlag(signupRequest.getConversionFlag());
		if(signupRequest.getConversionProbability() != null) {
			BigDecimal conPro = new BigDecimal(signupRequest.getConversionProbability());
			conversionPrediction.setConversionProbability(conPro);
		}
		conversionPrediction.seteTVBand(signupRequest.getEtvBand());
		requestData.setConversionPrediction(conversionPrediction);
		
		requestData.setCurrencyPair(null);
		requestData.setClientType(null);
		requestData.setPhoneReg(null);
		requestData.setEnquiryDate(null);
		requestData.setAccountStatus(signupRequest.getAccountStatus());
	}

	/**
	 * Transform ip address detail.
	 *
	 * @param ipDevice the ip device
	 * @return the IP address details
	 */
	private IPAddressDetails transformIpAddressDetail(IntuitionIPDeviceMQRequest ipDevice) {
		IPAddressDetails ipAddress = new IPAddressDetails();
		ipAddress.setContinent(ipDevice.getContinent());
		ipAddress.setLongitude(ipDevice.getLongitude());
		ipAddress.setLatitiude(ipDevice.getLatitiude());
		ipAddress.setRegion(ipDevice.getRegion());
		ipAddress.setCity(ipDevice.getCity());
		ipAddress.setTimezone(ipDevice.getTimezone());
		ipAddress.setOrganization(ipDevice.getOrganization());
		ipAddress.setCarrier(ipDevice.getCarrier());
		ipAddress.setConnectionType(ipDevice.getConnectionType());
		ipAddress.setLineSpeed(ipDevice.getLineSpeed());
		ipAddress.setIpRoutingType(ipDevice.getIpRoutingType());
		ipAddress.setCountryName(ipDevice.getCountryName());
		ipAddress.setCountryCode(ipDevice.getCountryCode());
		ipAddress.setStateName(ipDevice.getStateName());
		ipAddress.setStateCode(ipDevice.getStateCode());
		ipAddress.setPostalCode(ipDevice.getPostalCode());
		ipAddress.setAreaCode(ipDevice.getAreaCode());
		ipAddress.setAnonymizerStatus(ipDevice.getAnonymizerStatus());
		ipAddress.setIpAddress(ipDevice.getIpAddress());
		ipAddress.setContactIp(Optional.ofNullable(ipDevice.getContactIp()).map(Object::toString)
				.orElse(null));
		return ipAddress;
	}
	
	/**
	 * Transform card detail.
	 *
	 * @param card the card
	 * @return the card details
	 */
	private CardDetails transformCardDetail(IntuitionCardMQRequest card) {
		CardDetails cardDetails = new CardDetails();
		cardDetails.setCardID(card.getCardID());
		cardDetails.setCardStatusFlag(card.getCardStatusFlag());
		cardDetails.setDateCardDispatched(card.getDateCardDispatched());
		cardDetails.setDateCardActivated(card.getDateCardActivated());
		cardDetails.setDateCardFrozen(card.getDateCardFrozen());
		cardDetails.setFreezeReason(card.getFreezeReason());
		cardDetails.setDateCardUnfrozen(card.getDateCardUnfrozen());
		cardDetails.setDateCardBlocked(card.getDateCardBlocked());
		cardDetails.setReasonForBlock(card.getReasonForBlock());
		cardDetails.setDateCardUnblocked(card.getDateCardUnblocked());
		cardDetails.setDateLastCardPinView(card.getDateLastCardPinView());
		cardDetails.setDateLastCardPanView(card.getDateLastCardPanView());
		return cardDetails;
	}

	/**
	 * Transform corperate compliance data.
	 *
	 * @param account the account
	 * @return the corperate compliance
	 */
	private CorperateCompliance transformCorperateComplianceData(IntuitionCFXLegalEntityMQRequest cfxLegal) {
		
		CorperateCompliance corperateCompliance = new CorperateCompliance();
		corperateCompliance.setFinancialStrength(cfxLegal.getFinancialStrength());
		corperateCompliance.setFixedAssets(cfxLegal.getFixedAssets());
		corperateCompliance.setForeignOwnedCompany(cfxLegal.getForeignOwnedCompany());
		corperateCompliance.setFormerName(cfxLegal.getFormerName());
		corperateCompliance.setGrossIncome(cfxLegal.getGrossIncome());
		corperateCompliance.setIsoCountryCode2Digit(cfxLegal.getIsoCountryCode2Digit());
		corperateCompliance.setLegalForm(cfxLegal.getLegalForm());
		corperateCompliance.setMatchName(cfxLegal.getMatchName());
		corperateCompliance.setNetIncome(cfxLegal.getNetIncome());
		corperateCompliance.setNetWorth(cfxLegal.getNetWorth());
		if (null != cfxLegal.getRegistrationDate()) {
			corperateCompliance.setRegistrationDate(cfxLegal.getRegistrationDate());
		}
		corperateCompliance.setSic(cfxLegal.getSic());
		corperateCompliance.setTotalLiabilitiesAndEquities(cfxLegal.getTotalLiabilitiesAndEquities());
		corperateCompliance.setTotalMatchedShareholders(cfxLegal.getTotalMatchedShareholders());
		corperateCompliance.setTotalShareHolders(cfxLegal.getTotalShareHolders());
		return corperateCompliance;
	}

	/**
	 * Transform risk profile data.
	 *
	 * @param account the account
	 * @return the risk profile
	 */
	private RiskProfile transformRiskProfileData(IntuitionCFXLegalEntityMQRequest cfxLegal) {
		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAnnualSales(cfxLegal.getAnnualSales());
		riskProfile.setCountryRiskIndicator(cfxLegal.getCountryRiskIndicator());
		riskProfile.setCreditLimit(cfxLegal.getCreditLimit());
		riskProfile.setDomesticUltimateParentDetails(cfxLegal.getDomesticUltimateParentDetails());
		riskProfile.setDunsNumber(cfxLegal.getDunsNumber());
		riskProfile.setGlobalUltimateParentDetails(cfxLegal.getGlobalUltimateParentDetails());
		riskProfile.setGroupStructureNumberOfLevels(cfxLegal.getGroupStructureNumberOfLevels());
		riskProfile.setHeadquarterDetails(cfxLegal.getHeadquarterDetails());
		riskProfile.setImmediateParentDetails(cfxLegal.getImmediateParentDetails());
		riskProfile.setModelledAnnualSales(cfxLegal.getModelledAnnualSales());
		riskProfile.setModelledNetWorth(cfxLegal.getModelledNetWorth());
		riskProfile.setNetWorthAmount(cfxLegal.getNetWorthAmount());
		riskProfile.setRiskDirection(cfxLegal.getRiskDirection());
		riskProfile.setRiskRating(cfxLegal.getRiskRating());
		riskProfile.setRiskTrend(cfxLegal.getRiskTrend());
		riskProfile.setUpdatedRisk(cfxLegal.getUpdatedRisk());
		return riskProfile;
	}

	/**
	 * Transform contact request.
	 *
	 * @param requestData the request data
	 * @param contact the contact
	 */
	private void transformContactRequest(TransactionMonitoringContactRequest requestData,
			IntuitionContactMQRequest contact) {

		requestData.setTradeAccNumber(contact.getTradeAccNumber());
		requestData.setContactSFID(contact.getContactSfId());
		requestData.setTradeContactID(Integer.parseInt(contact.getTradeContactId()));
		requestData.setTitle(contact.getTitle());
		requestData.setPreferredName(contact.getPrefName());
		requestData.setFirstName(contact.getFirstName());
		requestData.setLastName(contact.getLastName());
		requestData.setDob(contact.getDob());
		requestData.setAuthorisedSignatory(contact.getAuthorisedSignatory());
		requestData.setAddressType(contact.getAddressType());
		requestData.setStreet(contact.getStreet());
		requestData.setStreetNumber(contact.getStreetNumber());
		requestData.setStreetType(contact.getStreetType());
		requestData.setCity(contact.getTownCityMuncipalty());
		requestData.setAddress1(contact.getAddress1());

		requestData.setPostCode(contact.getPostCode());
		requestData.setBuildingName(contact.getBuildingName());
		requestData.setSubBuildingorFlat(contact.getSubBuilding());
		requestData.setUnitNumber(contact.getUnitNumber());
		requestData.setSubCity(contact.getSubCity());
		requestData.setState(contact.getStateProvinceCounty());

		requestData.setPhoneWork(contact.getWorkPhone());
		requestData.setPhoneHome(contact.getHomePhone());
		requestData.setPhoneMobile(contact.getMobilePhone());
		requestData.setEmail(contact.getEmail());
		requestData.setPrimaryContact(Boolean.parseBoolean(contact.getIsPrimaryContact()));
		requestData.setPrimaryPhone(contact.getPrimaryPhone());
		
		requestData.setGender(contact.getGender());
		requestData.setNationality(contact.getCountryOfNationality());
		requestData.setCountry(contact.getCountryOfResidence());
		requestData.setPositionOfSignificance(contact.getPositionOfSignificance());

		if(null != contact.getYearsInAddress()) {
			requestData.setYearsInAddress(contact.getYearsInAddress().toString());
		}
		requestData.setOccupation(contact.getOccupation());
		requestData.setJobTitle(contact.getJobTitle());
		requestData.setDesignation(contact.getDesignation());

		if (null != contact.getPassportCountryCode()) {
			requestData.setPassportCountryCode(contact.getPassportCountryCode());
		}
		requestData.setPassportFamilyNameAtBirth(contact.getPassportBirthFamilyName());
		requestData.setPassportNameAtCitizenship(contact.getPassportNameAtCitizen());
		requestData.setPassportNumber(contact.getPassportNumber());
		requestData.setPassportPlaceOfBirth(contact.getPassportBirthPlace());

		if (null != contact.getPassportExipryDate()) {
			requestData.setPassportExiprydate(contact.getPassportExipryDate());
		}

		requestData.setPassportFullName(contact.getPassportFullName());
		requestData.setPassportMRZLine1(contact.getPassportMrzLine1());
		requestData.setPassportMRZLine2(contact.getPassportMrzLine2());

		requestData.setDlCardNumber(contact.getDrivingLicenseCardNumber());

		if (null != contact.getDrivingLicenseCountry()) {
			requestData.setDlCountryCode(contact.getDrivingLicenseCountry());
		}

		if (null != contact.getDrivingExpiry()) {
			requestData.setDlExpiryDate(contact.getDrivingExpiry());
		}

		requestData.setDlLicenseNumber(contact.getDrivingLicenseCardNumber());
		requestData.setDlStateCode(contact.getDrivingStateCode());
		requestData.setDlVersionNumber(contact.getDrivingVersionNumber());

		requestData.setMedicareCardNumber(contact.getMedicareCardNumber());
		requestData.setMedicareReferenceNumber(contact.getMedicareRefNumber());
		requestData.setMunicipalityOfBirth(contact.getMunicipalityOfBirth());
		requestData.setIpAddress(contact.getIpAddress());
		requestData.setAustraliaRTACardNumber(contact.getAustraliaRtaCardNumber());

		requestData.setMiddleName(contact.getMiddleName());
		requestData.setCountryOfBirth(contact.getCountryOfBirth());
		requestData.setPrefecture(contact.getPrefecture());
		requestData.setAza(contact.getAza());
		requestData.setResidentialStatus(contact.getResidentialStatus());

		requestData.setAddressType(contact.getAddressType());

		requestData.setBlacklist(contact.getBlacklist());
		requestData.setCustomCheck(contact.getCustomCheck());//Add for AT-5393
		requestData.setSanctionResult(contact.getSanctionResult());
		requestData.setFraudPredictScore(contact.getFraudPredictScore());
		requestData.setFraudPredictDate(contact.getFraudPredictDate());
		requestData.seteIDVStatus(contact.getEIDVStatus());
		if(null != contact.getWatchlist()) {
			String ab = contact.getWatchlist().replace("[", "");
			String bc = ab.replace("]", "");
			List<String> wl = new ArrayList<>(Arrays.asList(bc.split(",")));
			requestData.setWatchlist(wl);
		}
		//requestData.setRegDateTime(contact.getRegDateTime());
		
		requestData.setContactStatus(contact.getContactStatus());
		requestData.setUpdateStatus(contact.getUpdateStatus());
		if(null != contact.getStatusUpdateReason()) {
			String ab = contact.getStatusUpdateReason().replace("[", "");
			String bc = ab.replace("]", "");
			List<String> reason = new ArrayList<>(Arrays.asList(bc.split(",")));
			requestData.setStatusUpdateReason(reason);
		}
		
		//AT-4744	
		requestData.setOnfido(contact.getOnfido());	
		requestData.setFraudPredictScore(contact.getFraudPredictScore());	
		requestData.setFraudPredictDate(contact.getFraudPredictDate());	
		requestData.setHouseBuildingNumber(contact.getHouseBuildingNumber());	
		requestData.setFloorNumber(contact.getFloorNumber());	
		requestData.setDistrict(contact.getDistrict());	
		requestData.setAreaNumber(contact.getAreaNumber());	
		requestData.setPhoneWorkExtn(contact.getWorkPhoneExt());	
		requestData.setSecondSurname(contact.getSecondSurname());	
		requestData.setMothersSurname(contact.getMothersSurname());	
		requestData.setSecondEmail(contact.getSecondEmail());	
		requestData.setNationalIdType(contact.getNationalIdType());	
		requestData.setNationalIdNumber(contact.getNationalIdNumber());	
		requestData.setStateOfBirth(contact.getStateOfBirth());	
		requestData.setCivicNumber(contact.getCivicNumber());	
		requestData.setVersion(contact.getVersion());	
		
		requestData.setLastPasswordChangeDate(contact.getLastPasswordChangeDate());
		requestData.setAppInstallDate(contact.getAppInstallDate());
		requestData.setDeviceId(contact.getDeviceId());
	}
	
	private MessageExchange createPaymentInExchange(Message<MessageContext> message, IntuitionPaymentINMQRequest fundsInRequest,
			TransactionMonitoringMQRequest transactionMonitoringMQRequest) {
		
		Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
				.getAdditionalAttribute(Constants.FIELD_EVENTID);
		MessageExchange ccExchange = new MessageExchange();
		
		TransactionMonitoringMQServiceRequest mqRequest = new TransactionMonitoringMQServiceRequest();
		TransactionMonitoringMQServiceResponse mqResponse = new TransactionMonitoringMQServiceResponse();
		
		try {		
			
			TransactionMonitoringPaymentsInRequest transactionMonitoringPaymentsInRequest = createServiceRequestPayIn(fundsInRequest);
			transactionMonitoringPaymentsInRequest.setAccountId(transactionMonitoringMQRequest.getAccountID());
			transactionMonitoringPaymentsInRequest.setCreatedBy(transactionMonitoringMQRequest.getCreatedBy());
			transactionMonitoringPaymentsInRequest.setFundsInId(transactionMonitoringMQRequest.getPaymentInID());
			transactionMonitoringPaymentsInRequest.setIsPresent(transactionMonitoringMQRequest.getIsPresent());
			transactionMonitoringPaymentsInRequest.setAccountTMFlag(1);
			
			transactionMonitoringPaymentsInRequest.addAttribute("oldPaymentStatus", fundsInDBServiceImpl.getPaymentComplianceStatus(transactionMonitoringMQRequest.getPaymentInID()));
						
			TransactionMonitoringPaymentInResponse transactionMonitoringPaymentInResponse = new TransactionMonitoringPaymentInResponse();
			transactionMonitoringPaymentInResponse.setId(transactionMonitoringMQRequest.getPaymentInID());
			transactionMonitoringPaymentInResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			
			TransactionMonitoringPaymentsSummary transactionMonitoringPaymentsSummary = new TransactionMonitoringPaymentsSummary();
			transactionMonitoringPaymentsSummary.setStatus(transactionMonitoringPaymentInResponse.getStatus());
			
			mqRequest.setTransactionMonitoringPaymentsInRequest(transactionMonitoringPaymentsInRequest);
			mqRequest.setRequestType(transactionMonitoringMQRequest.getRequestType());
			mqRequest.setOrgCode(transactionMonitoringMQRequest.getOrgCode());
			mqRequest.setCreatedBy(transactionMonitoringMQRequest.getCreatedBy());
			mqResponse.setTransactionMonitoringPaymentInResponse(transactionMonitoringPaymentInResponse);

			 ccExchange = createMessageExchange(mqRequest, mqResponse,
					ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);
			ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId,
					ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND, ServiceProviderEnum.TRANSACTION_MONITORING_FUNDSIN, transactionMonitoringMQRequest.getPaymentInID(),
					1, EntityEnum.ACCOUNT.name(), transactionMonitoringMQRequest.getCreatedBy(), transactionMonitoringPaymentInResponse,
					transactionMonitoringPaymentsSummary));

		} catch (Exception e) {
			LOG.error("Error in Transaction Monitoring MQ transformer class : createPaymentInExchange -", e);
		}
		
		return ccExchange;
	}

	private TransactionMonitoringPaymentsInRequest createServiceRequestPayIn(IntuitionPaymentINMQRequest fundsInRequest) {
		TransactionMonitoringPaymentsInRequest request = new TransactionMonitoringPaymentsInRequest();
		
		request.setAccountNumber(fundsInRequest.getTradeAccountNumber());
		request.setContractNumber(fundsInRequest.getTradeContractNumber());
		request.setTransactionId(Integer.parseInt(fundsInRequest.getTradePaymentID()));
		request.setTimeStamp(fundsInRequest.getTimestamp());
		request.setTradeAccountNumber(fundsInRequest.getTradeAccountNumber());
		request.setCustType(fundsInRequest.getCustType());
		request.setPurposeOfTrade(fundsInRequest.getPurposeOfTrade());
		request.setSellingAmount(fundsInRequest.getSellingAmount()); 
		request.setTransactionCurrency(fundsInRequest.getTransactionCurrency());
		request.setPaymentMethod(fundsInRequest.getPaymentMethod());
		request.setBillAddressLine(fundsInRequest.getBillAddressLine());
		request.setDebtorName(fundsInRequest.getDebtorName());
		request.setDebatorAccountNumber(fundsInRequest.getDebtorAccountNumber());
		request.setCountryOfFund(fundsInRequest.getCountryOfFund());
		request.setCustomerLegalEntity(fundsInRequest.getCustomerLegalEntity());
		request.setType("FUNDSIN");
		
		if(fundsInRequest.getTransactional() != null) {
			request.setTransactionType(fundsInRequest.getTransactional().get(0).getTrxType());
			request.setTradeContactId(Integer.parseInt(fundsInRequest.getTransactional().get(0).getTradeContactId()));
			request.setSellingAmountBaseValue(fundsInRequest.getTransactional().get(0).getSellingAmountBaseValue());
			request.setThirdPartyPayment(Boolean.parseBoolean(fundsInRequest.getTransactional().get(0).getThirdPartyPayment()));
			//AT-5337
			request.setInitiatingParentContact(fundsInRequest.getTransactional().get(0).getInitiatingParentContact());
		}
		
		if(fundsInRequest.getDebitCard() != null) {
			request.setCcFirstName(fundsInRequest.getDebitCard().get(0).getCcFirstName());
			request.setAvsResult(fundsInRequest.getDebitCard().get(0).getAvsResult());
			request.setIsThreeds(fundsInRequest.getDebitCard().get(0).getIsThreeds());
			request.setDebitCardAddedDate(fundsInRequest.getDebitCard().get(0).getDebitCardAddedDate());
			request.setBillAdZip(fundsInRequest.getDebitCard().get(0).getBillAdZip());
			request.setMessage(fundsInRequest.getDebitCard().get(0).getMessage());
			request.setRGID(fundsInRequest.getDebitCard().get(0).getRGID());
			request.settScore(Double.parseDouble(fundsInRequest.getDebitCard().get(0).getTScore()));
			request.settRisk(Double.parseDouble(fundsInRequest.getDebitCard().get(0).getTRisk()));
			request.setFraudsightReason(fundsInRequest.getDebitCard().get(0).getFraudsightReason());
			request.setFraudsightRiskLevel(fundsInRequest.getDebitCard().get(0).getFraudsightRiskLevel());
		}
		
		if(fundsInRequest.getDeviceAndIP() != null) {
			request.setScreenResolution(fundsInRequest.getDeviceAndIP().get(0).getScreenResolution());
			request.setBrowserType(fundsInRequest.getDeviceAndIP().get(0).getBrwsrType());
			request.setBrowserVersion(fundsInRequest.getDeviceAndIP().get(0).getBrwsrVersion());
			request.setDeviceType(fundsInRequest.getDeviceAndIP().get(0).getDeviceType());
			request.setDeviceName(fundsInRequest.getDeviceAndIP().get(0).getDeviceName());
			request.setDeviceVersion(fundsInRequest.getDeviceAndIP().get(0).getDeviceVersion());
			request.setDeviceId(fundsInRequest.getDeviceAndIP().get(0).getDeviceId());
			request.setDeviceManufacturer(fundsInRequest.getDeviceAndIP().get(0).getDeviceManufacturer());
			request.setOsType(fundsInRequest.getDeviceAndIP().get(0).getOsType());
			request.setBrowserLanguage(fundsInRequest.getDeviceAndIP().get(0).getBrwsrLang());
			request.setBrowserOnline(fundsInRequest.getDeviceAndIP().get(0).getBrowserOnline());
		}
		
		if (fundsInRequest.getAtlasFlags() != null) {
			request.setBlacklistStatus(fundsInRequest.getAtlasFlags().get(0).getBlacklist());
			request.setCountryCheckStatus(fundsInRequest.getAtlasFlags().get(0).getCountryCheck());
			request.setSanctionContactStatus(fundsInRequest.getAtlasFlags().get(0).getSanctionsContact());
			request.setFraudPredictStatus(fundsInRequest.getAtlasFlags().get(0).getFraudPredict());
			request.setCustomCheckStatus(fundsInRequest.getAtlasFlags().get(0).getCustomCheck());
			request.setAtlasSTPFlag(fundsInRequest.getAtlasFlags().get(0).getStpCodes());
			request.setUpdateStatus(fundsInRequest.getAtlasFlags().get(0).getUpdateStatus());
		}
		
		if(fundsInRequest.getCalculations() != null) {
			request.setTurnover(fundsInRequest.getCalculations().get(0).getTurnover() != null? 
					fundsInRequest.getCalculations().get(0).getTurnover().toString() : null);

		}
				
		String ab = fundsInRequest.getWatchlist().replace("[", "");
		String bc = ab.replace("]", "");
		List<String> wl = new ArrayList<>(Arrays.asList(bc.split(",")));
		request.setWatchlist(wl);
		
		//AT-4627
		request.setCreditorAccountName(fundsInRequest.getCollectionBankAccountName());
		request.setCreditorBankAccountNumber(fundsInRequest.getCollectionBankAccountNumber());
		request.setCreditorBankName(fundsInRequest.getCollectionBankName());
		request.setCreditorState(fundsInRequest.getFundsInState());
		request.setComplianceLog(fundsInRequest.getComplianceLog());//AT-5578
		
		return request;
	}
	
	private MessageExchange createPaymentOutExchange(Message<MessageContext> message, IntuitionPaymentOUTMQRequest fundsOutRequest,
			TransactionMonitoringMQRequest transactionMonitoringMQRequest) {
		
		MessageExchange ccExchange = new MessageExchange();
		TransactionMonitoringMQServiceRequest mqRequest = new TransactionMonitoringMQServiceRequest();
		TransactionMonitoringMQServiceResponse mqResponse = new TransactionMonitoringMQServiceResponse();
		
		Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
				.getAdditionalAttribute(Constants.FIELD_EVENTID);
		try {			
			TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest = createServiceRequest(fundsOutRequest);
			transactionMonitoringPaymentsOutRequest.setAccountId(transactionMonitoringMQRequest.getAccountID());
			transactionMonitoringPaymentsOutRequest.setCreatedBy(transactionMonitoringMQRequest.getCreatedBy());
			transactionMonitoringPaymentsOutRequest.setFundsOutId(transactionMonitoringMQRequest.getPaymentOutID());
			transactionMonitoringPaymentsOutRequest.setIsPresent(transactionMonitoringMQRequest.getIsPresent());
			transactionMonitoringPaymentsOutRequest.setAccountTMFlag(1);
			
			transactionMonitoringPaymentsOutRequest.addAttribute("oldPaymentStatus", fundsOutDBServiceImpl.getPaymentComplianceStatus(transactionMonitoringMQRequest.getPaymentOutID()));
						
			TransactionMonitoringPaymentOutResponse transactionMonitoringPaymentOutResponse = new TransactionMonitoringPaymentOutResponse();
			transactionMonitoringPaymentOutResponse.setId(transactionMonitoringMQRequest.getPaymentOutID());
			transactionMonitoringPaymentOutResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			
			TransactionMonitoringContactSummary transactionMonitoringContactSummary = new TransactionMonitoringContactSummary();
			transactionMonitoringContactSummary.setStatus(transactionMonitoringPaymentOutResponse.getStatus());
			
			mqRequest.setRequestType(transactionMonitoringMQRequest.getRequestType());
			mqRequest.setOrgCode(transactionMonitoringMQRequest.getOrgCode());
			mqRequest.setCreatedBy(transactionMonitoringMQRequest.getCreatedBy());
			mqRequest.setTransactionMonitoringPaymentsOutRequest(transactionMonitoringPaymentsOutRequest);
			
			mqResponse.setTransactionMonitoringPaymentOutResponse(transactionMonitoringPaymentOutResponse);
			

			 ccExchange = createMessageExchange(mqRequest, mqResponse,
					ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);
			ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId,
					ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND, ServiceProviderEnum.TRANSACTION_MONITORING_FUNDSOUT, transactionMonitoringMQRequest.getPaymentOutID(),
					1, EntityEnum.ACCOUNT.name(), transactionMonitoringMQRequest.getCreatedBy(), transactionMonitoringPaymentOutResponse,
					transactionMonitoringContactSummary));
			
			
		} catch (Exception e) {
			LOG.error("Error in Transaction Monitoring MQ transformer class : createPaymentOutExchange -", e);
		}
		
		return ccExchange;
	}
	
	private TransactionMonitoringPaymentsOutRequest createServiceRequest(IntuitionPaymentOUTMQRequest fundsOutRequest) {
		TransactionMonitoringPaymentsOutRequest request = new TransactionMonitoringPaymentsOutRequest();
		
		request.setAccountNumber(fundsOutRequest.getTradeAccountNumber());
		request.setTransactionId(Integer.parseInt(fundsOutRequest.getTradePaymentID()));
		request.setContractNumber(fundsOutRequest.getTradeContractNumber());
		request.setTimeStamp(fundsOutRequest.getTimestamp());
		request.setTradeAccountNumber(fundsOutRequest.getTradeAccountNumber());
		request.setCustType(fundsOutRequest.getCustType());
		request.setPurposeOfTrade(fundsOutRequest.getPurposeOfTrade());
		request.setSellingAmount(fundsOutRequest.getSellingAmount());
		request.setBuyingAmount(fundsOutRequest.getBuyingAmount());
		request.setSellingAmountBaseValue(null);
		request.setTransactionCurrency(null);
		request.setCustomerLegalEntity(fundsOutRequest.getCustomerLegalEntity());
		//AT-5435
		request.setCreditFrom(fundsOutRequest.getCreditFrom());
		
		if(fundsOutRequest.getMaturityDate().contains(TIME_STRING))
			request.setMaturityDate(fundsOutRequest.getMaturityDate().replace(TIME_STRING, ""));
		else 
			request.setMaturityDate(fundsOutRequest.getMaturityDate());
		
		if(fundsOutRequest.getDealDate().contains(TIME_STRING))
			request.setDealDate(fundsOutRequest.getDealDate().replace(TIME_STRING, ""));
		else
			request.setDealDate(fundsOutRequest.getDealDate());
		
		request.setBuyingAmount(fundsOutRequest.getBuyingAmount());
		request.setMaturityDate(fundsOutRequest.getMaturityDate());
		request.setBuyCurrency(fundsOutRequest.getBuyCurrency());
		request.setSellCurrency(fundsOutRequest.getSellCurrency());
		request.setPhone(fundsOutRequest.getPhone());
		request.setFirstName(fundsOutRequest.getFirstName());
		request.setLastName(fundsOutRequest.getLastName());
		request.setEmail(fundsOutRequest.getEmail());
		request.setCountry(fundsOutRequest.getCountry());
		request.setAccountNumber(fundsOutRequest.getAccountNumber());
		request.setCurrencyCode(fundsOutRequest.getCurrencyCode());
		request.setPaymentReference(fundsOutRequest.getPaymentReference());
		request.setAmount(fundsOutRequest.getAmount());
		request.setBeneficiaryBankAddress(fundsOutRequest.getBeneficaryBankAddress());
		request.setBeneficiaryBankId(fundsOutRequest.getBeneficiaryBankId());
		request.setBeneficiaryBankName(fundsOutRequest.getBeneficaryBankName());
		request.setBeneficiaryId(fundsOutRequest.getBeneficiaryId());
		request.setType("FUNDSOUT");
		request.setComplianceLog(fundsOutRequest.getComplianceLog());//AT-5578
		
		if(fundsOutRequest.getTransactional() != null) {
			request.setValueDate(fundsOutRequest.getTransactional().get(0).getValueDate());
			request.setBuyingAmountBaseValue(fundsOutRequest.getTransactional().get(0).getBuyingAmountBaseValue());
			request.setContractNumber(fundsOutRequest.getTransactional().get(0).getContractNumber());
			request.setThirdPartyPayment(Boolean.parseBoolean(fundsOutRequest.getTransactional().get(0).getThirdPartyPayment()));
			request.setTradeContactId(Integer.parseInt(fundsOutRequest.getTransactional().get(0).getTradeContactId()));
			request.setOpiCreatedDate(fundsOutRequest.getTransactional().get(0).getOpiCreatedDate());
			//AT-5337
			request.setInitiatingParentContact(fundsOutRequest.getTransactional().get(0).getInitiatingParentContact());
		}
		
		if(fundsOutRequest.getDeviceAndIP() != null) {
			request.setScreenResolution(fundsOutRequest.getDeviceAndIP().get(0).getScreenResolution());
			request.setBrowserType(fundsOutRequest.getDeviceAndIP().get(0).getBrwsrType());
			request.setBrowserVersion(fundsOutRequest.getDeviceAndIP().get(0).getBrwsrVersion());
			request.setDeviceType(fundsOutRequest.getDeviceAndIP().get(0).getDeviceType());
			request.setDeviceName(fundsOutRequest.getDeviceAndIP().get(0).getDeviceName());
			request.setDeviceVersion(fundsOutRequest.getDeviceAndIP().get(0).getDeviceVersion());
			request.setDeviceId(fundsOutRequest.getDeviceAndIP().get(0).getDeviceId());
			request.setDeviceManufacturer(fundsOutRequest.getDeviceAndIP().get(0).getDeviceManufacturer());
			request.setOsType(fundsOutRequest.getDeviceAndIP().get(0).getOsType());
			request.setBrowserLanguage(fundsOutRequest.getDeviceAndIP().get(0).getBrwsrLang());
			request.setVersion(Integer.parseInt(fundsOutRequest.getDeviceAndIP().get(0).getVersion()));
			request.setStatusUpdateReason(fundsOutRequest.getDeviceAndIP().get(0).getStatusUpdateReason());
		}
		
		if (fundsOutRequest.getAtlasFlags() != null) {
			request.setBlacklistStatus(fundsOutRequest.getAtlasFlags().get(0).getBlacklist());
			request.setCountryCheckStatus(fundsOutRequest.getAtlasFlags().get(0).getCountryCheck());
			request.setSanctionContactStatus(fundsOutRequest.getAtlasFlags().get(0).getSanctionsContact());
			request.setSanctionBeneficiaryStatus(fundsOutRequest.getAtlasFlags().get(0).getSanctionsBeneficiary());
			request.setSanctionBankStatus(fundsOutRequest.getAtlasFlags().get(0).getSanctionsBank());
			request.setFraudPredictStatus(fundsOutRequest.getAtlasFlags().get(0).getFraudPredict());
			request.setCustomCheckStatus(fundsOutRequest.getAtlasFlags().get(0).getCustomCheck());
			request.setPaymentReferenceCheck(fundsOutRequest.getAtlasFlags().get(0).getPaymentReferenceCheck());
			request.setUpdateStatus(fundsOutRequest.getAtlasFlags().get(0).getUpdateStatus());
			request.setAtlasSTPFlag(fundsOutRequest.getAtlasFlags().get(0).getStpCodes());
		}
		
		String ab = fundsOutRequest.getWatchlist().replace("[", "");
		String bc = ab.replace("]", "");
		List<String> wl = new ArrayList<>(Arrays.asList(bc.split(",")));
		request.setWatchlist(wl);
		
		return request;
	}
	
	/**
	 * Transform response.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		TransactionMonitoringMQRequest transactionMonitoringMQRequest = (TransactionMonitoringMQRequest) messageExchange
				.getRequest();

		MessageExchange exchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);

		try {

			if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.SIGN_UP)
					|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.UPDATE)
					|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.ADD_CONTACT)) {
				transformSignUpAccountResponse(exchange);
			} else if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_in") 
						|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_in_update")) {
				transformPaymentInResponse(exchange);
			} else if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_out") 
						|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_out_update")) {
				transformPaymentOutResponse(exchange);
			}

		} catch (Exception e) {
			LOG.error("Error", e);
			message.getPayload().setFailed(true);
		}

		return message;
	}

	/**
	 * Transform sign up account response.
	 *
	 * @param exchange the exchange
	 */
	private void transformSignUpAccountResponse(MessageExchange exchange) {
		TransactionMonitoringMQServiceResponse response = (TransactionMonitoringMQServiceResponse) exchange
				.getResponse();
		TransactionMonitoringMQServiceRequest request = (TransactionMonitoringMQServiceRequest) exchange.getRequest();
		TransactionMonitoringAccountSignupResponse tmSingUpAccountResponse = new TransactionMonitoringAccountSignupResponse();
		TransactionMonitoringAccountProviderResponse tmAccountProviderResponse = new TransactionMonitoringAccountProviderResponse();

		try {

			if(response.getTransactionMonitoringSignupResponse() != null) {
				tmSingUpAccountResponse = response.getTransactionMonitoringSignupResponse().getTransactionMonitoringAccountSignupResponse();
				if (response != null && !response.getTransactionMonitoringSignupResponse()
						.getTransactionMonitoringAccountSignupResponse().getStatus().equalsIgnoreCase(Constants.SERVICE_FAILURE)) {
	
					tmSingUpAccountResponse = response.getTransactionMonitoringSignupResponse()
							.getTransactionMonitoringAccountSignupResponse();
	
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(
							ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND, EntityEnum.ACCOUNT.name(),
							tmSingUpAccountResponse.getAccountId());
	
					TransactionMonitoringAccountSummary accSummary = JsonConverterUtil
							.convertToObject(TransactionMonitoringAccountSummary.class, eventServiceLog.getSummary());
	
					setAccountSignUpResponse(tmSingUpAccountResponse, eventServiceLog, accSummary);
	
					response.getTransactionMonitoringSignupResponse()
							.setTransactionMonitoringAccountSignupResponse(tmSingUpAccountResponse);
	
				} else {
					TransactionMonitoringSignupRequest signupRequest = request.getTransactionMonitoringSignupRequest();
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(
							ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND, EntityEnum.ACCOUNT.name(),
							signupRequest.getTransactionMonitoringAccountRequest().getId());
	
					TransactionMonitoringAccountSummary accSummary = JsonConverterUtil
							.convertToObject(TransactionMonitoringAccountSummary.class, eventServiceLog.getSummary());
	
					tmSingUpAccountResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					accSummary.setAccountId(tmSingUpAccountResponse.getAccountId());	
					accSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					accSummary.setHttpStatus(tmSingUpAccountResponse.getHttpStatus());	
	
					tmAccountProviderResponse.setStatus(Constants.SERVICE_FAILURE);
					eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmAccountProviderResponse));
					eventServiceLog.setStatus(Constants.SERVICE_FAILURE);
					eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(accSummary));
					eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
	
					TransactionMonitoringMQServiceResponse failureResponse = new TransactionMonitoringMQServiceResponse();
					TransactionMonitoringSignupResponse responseServiceFailure = new TransactionMonitoringSignupResponse();
					responseServiceFailure.setTransactionMonitoringAccountSignupResponse(tmSingUpAccountResponse);
					failureResponse.setTransactionMonitoringSignupResponse(responseServiceFailure);
					exchange.setResponse(failureResponse);
				}
			}
		} catch (Exception e) {
			LOG.error(ERROR, e);
		}
	}
	/**
	 * Sets the account sign up response.
	 *
	 * @param tmSingUpAccountResponse the tm sing up account response
	 * @param eventServiceLog the event service log
	 * @param accSummary the acc summary
	 */
	private void setAccountSignUpResponse(TransactionMonitoringAccountSignupResponse tmSingUpAccountResponse,
			EventServiceLog eventServiceLog, TransactionMonitoringAccountSummary accSummary) {
		TransactionMonitoringAccountProviderResponse tmAccountProviderResponse = tmSingUpAccountResponse.getTransactionMonitoringAccountProviderResponse();
		
		try {

			accSummary.setStatus(tmSingUpAccountResponse.getStatus());	
			accSummary.setCorrelationId(tmAccountProviderResponse.getCorrelationId());	
			accSummary.setProfileScore(tmAccountProviderResponse.getProfileScore());	
			accSummary.setRuleScore(tmAccountProviderResponse.getRuleScore());	
			accSummary.setRulesTriggered(tmAccountProviderResponse.getRulesTriggered());
			accSummary.setRiskLevel(tmAccountProviderResponse.getRiskLevel());
			accSummary.setAccountId(tmSingUpAccountResponse.getAccountId());	
			accSummary.setHttpStatus(tmSingUpAccountResponse.getHttpStatus());	
				
			eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithNull(tmAccountProviderResponse));	
			eventServiceLog.setStatus(tmSingUpAccountResponse.getStatus());	
			eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithNull(accSummary));	
			eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

		} catch (Exception e) {
			LOG.error(ERROR, e);
			tmSingUpAccountResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			accSummary.setAccountId(tmSingUpAccountResponse.getAccountId());	
			accSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			accSummary.setHttpStatus(tmSingUpAccountResponse.getHttpStatus());	
			
			tmAccountProviderResponse.setStatus(Constants.SERVICE_FAILURE);
			eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmAccountProviderResponse));
			eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(accSummary));
			eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		}

	}

	
	/**
	 * Transform payment in response.
	 *
	 * @param exchange the exchange
	 */
	public void transformPaymentInResponse(MessageExchange exchange) {
		TransactionMonitoringMQServiceResponse mqResponse = (TransactionMonitoringMQServiceResponse) exchange
				.getResponse();
		
		try {
			TransactionMonitoringMQServiceRequest mqRequest = (TransactionMonitoringMQServiceRequest) exchange
					.getRequest();
			TransactionMonitoringPaymentsInRequest request = mqRequest.getTransactionMonitoringPaymentsInRequest();
			TransactionMonitoringPaymentInResponse response = mqResponse.getTransactionMonitoringPaymentInResponse();


			if (response != null && !response.getStatus().equalsIgnoreCase(Constants.NOT_REQUIRED)
					&& !response.getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
				TransactionMonitoringFundsProviderResponse tmProviderResponse = response
						.getTransactionMonitoringFundsProviderResponse();
				TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND, EntityEnum.ACCOUNT.name(), request.getFundsInId());
				summary.setStatus(tmProviderResponse.getStatus());
				summary.setFundsInId(request.getFundsInId());
				summary.setRuleScore(tmProviderResponse.getRuleScore());
				summary.setRuleRiskLevel(tmProviderResponse.getRuleRiskLevel());
				summary.setClientRiskLevel(tmProviderResponse.getClientRiskLevel());
				summary.setClientRiskScore(tmProviderResponse.getClientRiskScore());
				summary.setCorrelationId(response.getCorrelationId());
				summary.setUserId(tmProviderResponse.getUserId());
				eventServiceLog.setStatus(ServiceStatus.PASS.name());
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
				tmProviderResponse.setCorrelationId(response.getCorrelationId());
				eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmProviderResponse));
				mqResponse.setTransactionMonitoringPaymentInResponse(response);
				exchange.setResponse(mqResponse);
			} else if(response != null && response.getStatus().equalsIgnoreCase(Constants.NOT_REQUIRED)){
				TransactionMonitoringFundsProviderResponse tmProviderResponse = new TransactionMonitoringFundsProviderResponse();
				TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND, EntityEnum.ACCOUNT.name(),
						request.getFundsInId());
				summary.setStatus(Constants.NOT_REQUIRED);
				summary.setFundsOutId(request.getFundsInId());
				tmProviderResponse.setStatus(Constants.NOT_REQUIRED);
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
				eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmProviderResponse));
			}else {
				response = createDefaultFailureResponse(exchange, request);
			}

		} catch (Exception e) {
			LOG.error("Error in Transaction Monitoring MQ transformer class : transformPaymentInResponse -", e);
		}
	}
	
	/**
	 * Creates the default failure response.
	 *
	 * @param exchange the exchange
	 * @param resp the resp
	 * @return the transaction monitoring payment in response
	 */
	private TransactionMonitoringPaymentInResponse createDefaultFailureResponse(MessageExchange exchange,
			TransactionMonitoringPaymentsInRequest request) {
		TransactionMonitoringMQServiceResponse mqResponse = new TransactionMonitoringMQServiceResponse();
		TransactionMonitoringPaymentInResponse response = new TransactionMonitoringPaymentInResponse();
		TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
		TransactionMonitoringFundsProviderResponse tmProviderResponse = new TransactionMonitoringFundsProviderResponse();
		
		mqResponse = (TransactionMonitoringMQServiceResponse) exchange.getResponse();
		response = mqResponse.getTransactionMonitoringPaymentInResponse();
		
		EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND,
				EntityEnum.ACCOUNT.name(), request.getFundsInId());
		response.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		summary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		tmProviderResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmProviderResponse));
		response.setTransactionMonitoringFundsProviderResponse(tmProviderResponse);
		mqResponse.setTransactionMonitoringPaymentInResponse(response);
		exchange.setResponse(mqResponse);
		return response;
	}
	
	/**
	 * Transform payment out response.
	 *
	 * @param exchange the exchange
	 */
	public void transformPaymentOutResponse(MessageExchange exchange) {
		TransactionMonitoringMQServiceResponse mqResponse = (TransactionMonitoringMQServiceResponse) exchange
				.getResponse();
		
		try {
			TransactionMonitoringMQServiceRequest mqRequest = (TransactionMonitoringMQServiceRequest) exchange
					.getRequest();
			TransactionMonitoringPaymentsOutRequest request = mqRequest.getTransactionMonitoringPaymentsOutRequest();
			TransactionMonitoringPaymentOutResponse response = mqResponse.getTransactionMonitoringPaymentOutResponse();
				
				
				if (response != null && !response.getStatus().equalsIgnoreCase(Constants.NOT_REQUIRED)
						&& !response.getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
					TransactionMonitoringFundsProviderResponse tmProviderResponse = response
							.getTransactionMonitoringFundsProviderResponse();
					TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(
							ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND, EntityEnum.ACCOUNT.name(), request.getFundsOutId());
					summary.setStatus(tmProviderResponse.getStatus());
					summary.setFundsOutId(request.getFundsOutId());
					summary.setRuleScore(tmProviderResponse.getRuleScore());
					summary.setRuleRiskLevel(tmProviderResponse.getRuleRiskLevel());
					summary.setClientRiskLevel(tmProviderResponse.getClientRiskLevel());
					summary.setClientRiskScore(tmProviderResponse.getClientRiskScore());
					summary.setCorrelationId(response.getCorrelationId());
					summary.setUserId(tmProviderResponse.getUserId());
					eventServiceLog.setStatus(ServiceStatus.PASS.name());
					eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
					tmProviderResponse.setCorrelationId(response.getCorrelationId());
					eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmProviderResponse));
					mqResponse.setTransactionMonitoringPaymentOutResponse(response);
					exchange.setResponse(mqResponse);
				} else if(response != null && response.getStatus().equalsIgnoreCase(Constants.NOT_REQUIRED)) {
					TransactionMonitoringFundsProviderResponse tmProviderResponse = new TransactionMonitoringFundsProviderResponse();
					TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(
							ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND, EntityEnum.ACCOUNT.name(),
							request.getFundsOutId());
					summary.setStatus(Constants.NOT_REQUIRED);
					summary.setFundsOutId(request.getFundsOutId());
					tmProviderResponse.setStatus(Constants.NOT_REQUIRED);
					eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
					eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmProviderResponse));
				}else {
					response = createDefaultFailureResponse(exchange, request);
				}
		
		} catch (Exception e) {
			LOG.error("Error in Transaction Monitoring MQ transformer class : transformPaymentOutResponse -", e);
		}
	}
	
	/**
	 * Creates the default failure response.
	 *
	 * @param exchange the exchange
	 * @param resp the resp
	 * @return the transaction monitoring payment out response
	 */
	private TransactionMonitoringPaymentOutResponse createDefaultFailureResponse(MessageExchange exchange, TransactionMonitoringPaymentsOutRequest request) {
		TransactionMonitoringMQServiceResponse mqResponse = new TransactionMonitoringMQServiceResponse();
		TransactionMonitoringPaymentOutResponse response = new TransactionMonitoringPaymentOutResponse();
		TransactionMonitoringContactSummary summary = new TransactionMonitoringContactSummary();
		TransactionMonitoringFundsProviderResponse tmProviderResponse = new TransactionMonitoringFundsProviderResponse();
		
		mqResponse = (TransactionMonitoringMQServiceResponse) exchange.getResponse();
		response = mqResponse.getTransactionMonitoringPaymentOutResponse();
		
		EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND,
				EntityEnum.ACCOUNT.name(), request.getFundsOutId());
		response.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		summary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		tmProviderResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmProviderResponse));
		response.setTransactionMonitoringFundsProviderResponse(tmProviderResponse);
		mqResponse.setTransactionMonitoringPaymentOutResponse(response);
		exchange.setResponse(mqResponse);
		return response;
	}

}
