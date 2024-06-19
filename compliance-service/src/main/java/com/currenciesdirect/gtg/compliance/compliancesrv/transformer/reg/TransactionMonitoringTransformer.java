package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.reg;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.SecondaryAddressDelivery;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailHeaderForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailPayloadForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequestForTM;
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
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport.ICommHubServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.enterprise.EnterpriseServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.CardDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.enterprise.EnterpriseIPAddressDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Card;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class TransactionMonitoringTransformer.
 */
public class TransactionMonitoringTransformer extends AbstractTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(TransactionMonitoringTransformer.class);
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/** The new registration DB service impl. */
	@Autowired
	protected NewRegistrationDBServiceImpl newRegistrationDBServiceImpl;
	
	@Autowired
	private ICommHubServiceImpl iCommHubServiceImpl;
	
	@Autowired	
	protected EnterpriseServiceImpl enterpriseServiceImpl;
	
	@Autowired
	protected CardDBServiceImpl cardDBServiceImpl; 	
	
	private static final String ERROR = "Error in reading TM response";
	
	private static final String EVENTID = "eventId";
	
	private static final String TMFLAG = "AccountTMFlag";
	

	/**
	 * Transform request.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
		String jsonCxExchangeRequest;
		try {
			LOG.info("::::::::::::::: Transforming request in TransactionMonitoringTransformer : {1}");
			
			MessageExchange ccExchange = createSignUpExchange(message, registrationRequest, token);

			jsonCxExchangeRequest = JsonConverterUtil.convertToJsonWithoutNull(ccExchange.getRequest());
			LOG.warn("::::::::::::::: TransactionMonitoringTransformer : {}", jsonCxExchangeRequest);//Added for AT-5230

			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOG.error("Error in TransactionMonitoringTransformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Creates the sign up exchange.
	 *
	 * @param message     the message
	 * @param incomingRegistrationRequest the incoming registration request
	 * @param token       the token
	 * @return the message exchange
	 * @throws ComplianceException the compliance exception
	 */
	private MessageExchange createSignUpExchange(Message<MessageContext> message, RegistrationServiceRequest incomingRegistrationRequest,
			UserProfile token) throws ComplianceException {
		Integer eventId = (Integer) incomingRegistrationRequest.getAdditionalAttribute(EVENTID);
		MessageExchange ccExchange = new MessageExchange();
		TransactionMonitoringSignupRequest request = new TransactionMonitoringSignupRequest();
		TransactionMonitoringSignupResponse tmSignUpResponse = new TransactionMonitoringSignupResponse();
		TransactionMonitoringAccountRequest tmSignUpAccountRequest = new TransactionMonitoringAccountRequest();
		TransactionMonitoringAccountSignupResponse tmSignUpAccountResponse = new TransactionMonitoringAccountSignupResponse();
		List<TransactionMonitoringContactRequest> tmSignUpContactRequests = new ArrayList<>();
		EnterpriseIPAddressDetails enterpriseIpAdd = new EnterpriseIPAddressDetails();
		Card cardInfo = new Card();
		IPAddressDetails ipAddress = new IPAddressDetails();
		RegistrationResponse regResponse = (RegistrationResponse) message.getPayload().getGatewayMessageExchange().getResponse();
		String accNum = null;

		try {
			
			if((OperationEnum.ADD_CONTACT) == message.getPayload().getGatewayMessageExchange().getOperation()) {
				Account account1 = (Account) incomingRegistrationRequest.getAdditionalAttribute("oldAccount");
				accNum =account1.getTradeAccountNumber();
				request.setRequestType(Constants.ADD_CONTACT);
			}else if((OperationEnum.NEW_REGISTRATION) == message.getPayload().getGatewayMessageExchange().getOperation()){
				accNum = incomingRegistrationRequest.getAccount().getTradeAccountNumber();
				request.setRequestType(Constants.SIGN_UP);
			}else {
				accNum = incomingRegistrationRequest.getAccount().getTradeAccountNumber();
				request.setRequestType(Constants.UPDATE);
			}
			
			
			RegistrationServiceRequest existingRegistrationRequest = newRegistrationDBServiceImpl.getAccountContacDetailsForIntuition(accNum);
			Integer accTMFlag  = (Integer) existingRegistrationRequest.getAdditionalAttribute(TMFLAG);
			Account account = existingRegistrationRequest.getAccount();
			request.setCreatedBy(token.getUserID());
			request.setAccountTMFlag(accTMFlag);
			tmSignUpAccountRequest.setCardDeliveryToSecondaryAddress(Optional.ofNullable(incomingRegistrationRequest)
					.map(RegistrationServiceRequest::getAccount)
					.map(Account::getCardDeliveryToSecondaryAddress)
					.orElse(SecondaryAddressDelivery.NO.name()));
			tmSignUpAccountRequest.setId(account.getId());
			String cardApplyType = incomingRegistrationRequest.getIsCardApply();
			
			
			if (accTMFlag == 1 || accTMFlag == 2 || accTMFlag == 4 || (OperationEnum.NEW_REGISTRATION) == message
					.getPayload().getGatewayMessageExchange().getOperation()) {

				transformAccountRequest(tmSignUpAccountRequest, account);
				transformComplianceLog(account.getId(), tmSignUpAccountRequest);
				tmSignUpAccountRequest.setAccountStatus(account.getAccountStatus());
				 SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				 tmSignUpAccountRequest.setCreatedOn(myFormat.format(existingRegistrationRequest.getAdditionalAttribute("CreatedOn")));//Add for AT-4763
				 /*
					 * request.setTransactionMonitoringAccountRequest(tmSignUpAccountRequest);
					 */
					request.setOrgCode(incomingRegistrationRequest.getOrgCode());
					
					//AT-5127
					String registrationLoadCriteria = (String) incomingRegistrationRequest.getAdditionalAttribute("registrationLoadCriteria");
					if(StringUtils.isNotBlank(registrationLoadCriteria))
						request.setRegistrationLoadCriteria(registrationLoadCriteria); 
					if (account.getCustType().equalsIgnoreCase("CFX") || account.getCustType().equalsIgnoreCase(Constants.CFXETAILER)) {
						tmSignUpAccountRequest.setBlacklist(account.getPreviousBlacklistStatus());
						tmSignUpAccountRequest.setSanctionResult(account.getPreviousSanctionStatus());
					}

					List<Contact> existingContacts = existingRegistrationRequest.getAccount().getContacts();

					
					if (StringUtils.isNotBlank(cardApplyType)) {
						request.setIsCardApply(cardApplyType);

						switch (cardApplyType) {
							case "A":
								for (Contact contact : existingContacts) {
									if (contact.getTradeContactID().equals(Integer.valueOf(incomingRegistrationRequest.getTradeContractId()))) {
										TransactionMonitoringContactRequest tmSignupContact =
												buildTransactionMonitoringContactRequest(account, contact);
										if(incomingRegistrationRequest.getLastPasswordChangeDate() != null &&!incomingRegistrationRequest.getLastPasswordChangeDate().isEmpty()) {
											tmSignupContact.setLastPasswordChangeDate(getFormattedDate(incomingRegistrationRequest.getLastPasswordChangeDate()));
										}
										if(incomingRegistrationRequest.getAppInstallDate() != null &&!incomingRegistrationRequest.getAppInstallDate().isEmpty()) {
											tmSignupContact.setAppInstallDate(getFormattedDate(incomingRegistrationRequest.getAppInstallDate()));
										}
										tmSignupContact.setDeviceId(incomingRegistrationRequest.getDeviceId());
										tmSignUpContactRequests.add(tmSignupContact);
									}
								}
								enterpriseIpAdd = enterpriseServiceImpl.getIPAddressDetails(incomingRegistrationRequest.getIpAddress());
								ipAddress.setContactIp(Optional.ofNullable(incomingRegistrationRequest.getTradeContractId())
										.orElse(null));
								break;
							case "U":
								/**
								 * 
								 * CardDetails set on the account request as per IPAddressDetails
								 */
								CardDetails cardDetails = transformCardDetails(incomingRegistrationRequest.getCard());
								tmSignUpAccountRequest.setCardDetails(Optional.ofNullable(cardDetails).orElse(null));
								transformAllContacts(tmSignUpContactRequests, account, existingContacts);
								enterpriseIpAdd = getEnterpriseIPAddressDetails(message, ipAddress, existingContacts);
								ipAddress.setContactIp(Optional.ofNullable(incomingRegistrationRequest.getTradeContractId())
										.orElse(null));
								break;
							default:
								break;
						}
					} else {
						transformAllContacts(tmSignUpContactRequests, account, existingContacts);
						enterpriseIpAdd = getEnterpriseIPAddressDetails(message, ipAddress, existingContacts);
						
						if(OperationEnum.NEW_REGISTRATION != message.getPayload().getGatewayMessageExchange().getOperation()) {
							 cardInfo = cardDBServiceImpl.getCardDetailsByAccountId(account.getId()); 
							 if(cardInfo != null) {
								 CardDetails cardDetails = transformCardDetails(cardInfo);
								 tmSignUpAccountRequest.setCardDetails(Optional.ofNullable(cardDetails).orElse(null));
							}	
						}
					}

					transformMailingStreet(existingContacts, tmSignUpAccountRequest);
				}

				if(enterpriseIpAdd != null) {
					transformAccountIpRequest(enterpriseIpAdd, tmSignUpAccountRequest, ipAddress);
				}

				if(existingRegistrationRequest.getAdditionalAttribute("IntuitionRiskLevel") != null) {
					regResponse.getAccount().setIntuitionRiskLevel(existingRegistrationRequest.getAdditionalAttribute("IntuitionRiskLevel").toString());
				}
				
				if (StringUtils.isNotBlank(cardApplyType) && cardApplyType.equalsIgnoreCase("A")) {
					ipAddress.setIpAddress(incomingRegistrationRequest.getIpAddress());
				}
			
			tmSignUpAccountRequest.setiPAddressDetails(ipAddress);
			tmSignUpAccountResponse.setAccountId(account.getId());
			tmSignUpAccountResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

			TransactionMonitoringAccountSummary accSummary = new TransactionMonitoringAccountSummary();
			accSummary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			
			ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId,
					ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE,
					ServiceProviderEnum.TRANSACTION_MONITORING_SIGN_UP_ACCOUNT, account.getId(), account.getVersion(),
					EntityEnum.ACCOUNT.name(), token.getUserID(), tmSignUpAccountResponse, accSummary));

			
			request.setTransactionMonitoringContactRequests(tmSignUpContactRequests);
			request.setTransactionMonitoringAccountRequest(tmSignUpAccountRequest);
			
			ccExchange.setRequest(request);
			tmSignUpResponse.setTransactionMonitoringAccountSignupResponse(tmSignUpAccountResponse);
			ccExchange.setResponse(tmSignUpResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);

		} catch (Exception e) {
			LOG.error("{1}", e);
		}
		return ccExchange;
	}
		
	private void transformAllContacts(List<TransactionMonitoringContactRequest> tmSignUpContactRequests,
			Account account, List<Contact> existingContacts) throws ComplianceException, ParseException {
		for (Contact contact : existingContacts) {
			TransactionMonitoringContactRequest tmSignupContact =
					buildTransactionMonitoringContactRequest(account, contact);
			tmSignUpContactRequests.add(tmSignupContact);
		}
	}

	private TransactionMonitoringContactRequest buildTransactionMonitoringContactRequest(Account account, Contact contact) throws ComplianceException, ParseException {
		TransactionMonitoringContactRequest tmSignupContact;
		tmSignupContact = transformContactRequest(contact, account);
		tmSignupContact.setContactStatus(contact.getContactStatus());
		tmSignupContact.setBlacklist(contact.getPreviousBlacklistStatus());
		tmSignupContact.setSanctionResult(contact.getPreviousSanctionStatus());
		tmSignupContact.setCustomCheck(contact.getPreviousCountryGlobalCheckStatus()); //Add for AT-5393
		tmSignupContact.seteIDVStatus(contact.getPreviousKycStatus());
		List<String> fraudPredict = newRegistrationDBServiceImpl.getFraugsterContactStatusFromDB(contact.getId());
		if (fraudPredict != null && !fraudPredict.isEmpty()) {
			tmSignupContact.setFraudPredictScore(fraudPredict.get(0));
			tmSignupContact.setFraudPredictDate(getFormattedDate(fraudPredict.get(1)));
		}
		String onfidoStatus = newRegistrationDBServiceImpl.getOnfidoContactStatusFromDB(contact.getId());
		tmSignupContact.setOnfido(onfidoStatus);
		transformContactRequestSetWatchlist(contact.getId(), tmSignupContact);
		transformStatusUpdateReason(contact.getId(), tmSignupContact);

		return tmSignupContact;
	}

	/**
	 * Existing logic is only last contact Ip Address used and also to set ContactIp
	 */
	private EnterpriseIPAddressDetails getEnterpriseIPAddressDetails(
			Message<MessageContext> message,
			IPAddressDetails ipAddress,
			List<Contact> existingContacts) throws ComplianceException {

		Contact lastContact = existingContacts.stream()
				.reduce((first,second) -> second)
				.orElse(null);
		ipAddress.setContactIp(lastContact.getTradeContactID().toString());
		return getEnterpriseIPAddressDetailsForContact(message, lastContact);
	}

	private EnterpriseIPAddressDetails getEnterpriseIPAddressDetailsForContact(Message<MessageContext> message,
			Contact contact) throws ComplianceException {
		EnterpriseIPAddressDetails enterpriseIpAdd = null;
		if(Boolean.TRUE.equals(
				contact.getPrimaryContact()) && OperationEnum.NEW_REGISTRATION  == message.getPayload().getGatewayMessageExchange().getOperation()) {
			enterpriseIpAdd = enterpriseServiceImpl.getIPAddressDetails(contact.getIpAddress());
		}
		if(OperationEnum.NEW_REGISTRATION != message.getPayload().getGatewayMessageExchange().getOperation()) {
			enterpriseIpAdd = enterpriseServiceImpl.getIPAddressDetails(contact.getIpAddress());
		}
		return enterpriseIpAdd;
	}

	//AT-4744
	private void transformAccountIpRequest(EnterpriseIPAddressDetails enterpriseIpAdd,
			TransactionMonitoringAccountRequest tmSignUpAccountRequest, IPAddressDetails ipAddress) {
		ipAddress.setContinent(enterpriseIpAdd.getContinent());
		ipAddress.setLongitude(enterpriseIpAdd.getLongitude());
		ipAddress.setLatitiude(enterpriseIpAdd.getLatitiude());
		ipAddress.setRegion(enterpriseIpAdd.getRegion());
		ipAddress.setCity(enterpriseIpAdd.getCity());
		ipAddress.setTimezone(enterpriseIpAdd.getTimezone());
		ipAddress.setOrganization(enterpriseIpAdd.getOrganization());
		ipAddress.setCarrier(enterpriseIpAdd.getCarrier());
		ipAddress.setConnectionType(enterpriseIpAdd.getConnectionType());
		ipAddress.setLineSpeed(enterpriseIpAdd.getLineSpeed());
		ipAddress.setIpRoutingType(enterpriseIpAdd.getIpRoutingType());
		ipAddress.setCountryName(enterpriseIpAdd.getCountryName());
		ipAddress.setCountryCode(enterpriseIpAdd.getCountryCode());
		ipAddress.setStateName(enterpriseIpAdd.getStateName());
		ipAddress.setStateCode(enterpriseIpAdd.getStateCode());
		ipAddress.setPostalCode(enterpriseIpAdd.getPostalCode());
		ipAddress.setAreaCode(enterpriseIpAdd.getAreaCode());
		ipAddress.setAnonymizerStatus(enterpriseIpAdd.getAnonymizerStatus());
		ipAddress.setIpAddress(enterpriseIpAdd.getIpAddress());
		tmSignUpAccountRequest.setiPAddressDetails(ipAddress);
	}

	private CardDetails transformCardDetails(Card card) throws ParseException {
		CardDetails cardDetails = new CardDetails();
		
		cardDetails.setCardID(card.getCardID());
		cardDetails.setCardStatusFlag(card.getCardStatusFlag());
		cardDetails.setDateCardDispatched(getFormattedDate(card.getDateCardDispatched()));
		cardDetails.setDateCardActivated(getFormattedDate(card.getDateCardActivated()));
		cardDetails.setDateCardFrozen(getFormattedDate(card.getDateCardFrozen()));
		cardDetails.setFreezeReason(card.getFreezeReason());
		cardDetails.setDateCardUnfrozen(getFormattedDate(card.getDateCardUnfrozen()));
		cardDetails.setDateCardBlocked(getFormattedDate(card.getDateCardBlocked()));
		cardDetails.setReasonForBlock(card.getReasonForBlock());
		cardDetails.setDateCardUnblocked(getFormattedDate(card.getDateCardUnblocked()));
		cardDetails.setDateLastCardPinView(getFormattedDate(card.getDateLastCardPinView()));
		cardDetails.setDateLastCardPanView(getFormattedDate(card.getDateLastCardPanView()));
		
		return cardDetails;
	}


	/**
	 * Transform account request.
	 *
	 * @param requestData the request data
	 * @param account     the account
	 * @throws ParseException 
	 */
	private void transformAccountRequest(TransactionMonitoringAccountRequest requestData, Account account) throws ParseException {

		requestData.setAccSFID(account.getAccSFID());
		requestData.setTradeAccountID(account.getTradeAccountID());
		requestData.setTradeAccountNumber(account.getTradeAccountNumber());
		requestData.setBranch(account.getBranch());
		requestData.setChannel(account.getChannel());
		requestData.setCustType(account.getCustType());
		requestData.setAccountName(account.getAccountName());
		requestData.setCountryOfInterest(account.getCountryOfInterest());
		requestData.setTradingName(account.getTradingName());
		requestData.setPurposeOfTran(account.getPurposeOfTran());
		requestData.setCountriesOfOperation(account.getCountriesOfOperation());
		if (account.getTurnover()!=null && !account.getTurnover().isEmpty()) {
			//Changes add for AT-4862
			 requestData.setTurnover(getIntegerTurnOver(account.getTurnover())); //updated for AT-5029
		}

		requestData.setServiceRequired(account.getServiceRequired());
		requestData.setBuyingCurrency(account.getBuyingCurrency());
		requestData.setSellingCurrency(account.getSellingCurrency());
		requestData.setValueOfTransaction(account.getValueOfTransaction());
		requestData.setSourceOfFund(account.getSourceOfFund());
		requestData.setAverageTransactionValue(account.getAverageTransactionValue());
		requestData.setSource(account.getSource());
		requestData.setSubSource(account.getSubSource());
		requestData.setReferral(account.getReferral());
		requestData.setReferralText(account.getReferralText());
		requestData.setExtendedReferral(account.getExtendedReferral());
		requestData.setAdCampaign(account.getAdCampaign());
		requestData.setKeywords(account.getKeywords());
		requestData.setSearchEngine(account.getSearchEngine());
		requestData.setWebsite(account.getWebsite());
		requestData.setAffiliateName(account.getAffiliateName());
		requestData.setAffiliateNumber(account.getAffiliateNumber());
		requestData.setRegistrationMode(account.getRegistrationMode());
		requestData.setRegistrationDateTime(account.getRegistrationDateTime());
		//Add for AT-5318
		requestData.setParentSfAccId(account.getParentSfAccId());
		requestData.setParentTitanAccNum(account.getParentTitanAccNum());
		requestData.setParentType(account.getParentType());
		requestData.setVulnerabilityCharacteristic(account.getVulnerabilityCharacteristic());
		requestData.setMetInPerson(StringUtils.capitalize(BooleanUtils.toStringYesNo(account.getMetInPerson())));
		requestData.setSelfieOnFile(StringUtils.capitalize(BooleanUtils.toStringYesNo(account.getSelfieOnFile())));
		//Add for AT-5376
		requestData.setNumberOfChilds(account.getNumberOfChilds());
		requestData.setLegacyTradeAccountNumber(account.getLegacyTradeAccountNumber());//Add for AT-5393
		
		if(account.getCompany() != null) {
			Company company = new Company();
			company.setBillingAddress(account.getCompany().getBillingAddress());
			company.setCcj(account.getCompany().getCcj());
			company.setCompanyPhone(account.getCompany().getCompanyPhone());
			company.setCorporateType(account.getCompany().getCorporateType());
			company.setCountryOfEstablishment(account.getCompany().getCountryOfEstablishment());
			company.setCountryRegion(account.getCompany().getCountryRegion());
			company.setEstNoTransactionsPcm(account.getCompany().getEstNoTransactionsPcm());
			company.setEtailer(account.getCompany().getEtailer());
			if(account.getCompany().getIncorporationDate() != null && !account.getCompany().getIncorporationDate().isEmpty()) {
				company.setIncorporationDate(account.getCompany().getIncorporationDate());
			}
			company.setIndustry(account.getCompany().getIndustry());
			//Add for AT-5393
			if (account.getCompany().getOngoingDueDiligenceDate() != null
					&& !account.getCompany().getOngoingDueDiligenceDate().isEmpty()) {
				if (account.getCompany().getOngoingDueDiligenceDate().length() > 10)
					company.setOngoingDueDiligenceDate(getFormattedDate(account.getCompany().getOngoingDueDiligenceDate()));
				else
					company.setOngoingDueDiligenceDate(account.getCompany().getOngoingDueDiligenceDate());
			}
			company.setOption(account.getCompany().getOption());
			company.setRegistrationNo(account.getCompany().getRegistrationNo());
			company.setShippingAddress(account.getCompany().getShippingAddress());
			if (account.getCompany().gettAndcSignedDate() != null&& !account.getCompany().gettAndcSignedDate().isEmpty()) {
				if (account.getCompany().gettAndcSignedDate().length() > 10)
					company.settAndcSignedDate(getFormattedDate(account.getCompany().gettAndcSignedDate())); //AT-5180
				else
					company.settAndcSignedDate(account.getCompany().gettAndcSignedDate());
				}
			company.setTypeOfFinancialAccount(account.getCompany().getTypeOfFinancialAccount());
			company.setVatNo(account.getCompany().getVatNo());
			requestData.setCompany(company);
		}
				
		if(account.getCorperateCompliance() != null) {
			CorperateCompliance corperateCompliance = transformCorperateComplianceData(account);
			requestData.setCorperateCompliance(corperateCompliance);
			
		}
				
		if(account.getRiskProfile() != null) {
			RiskProfile riskProfile = transformRiskProfileData(account);
			requestData.setRiskProfile(riskProfile);
		}
		
		if (account.getConversionPrediction() != null) {
			ConversionPrediction conversionPrediction = new ConversionPrediction();
			conversionPrediction.setAccountId(account.getConversionPrediction().getAccountId());
			conversionPrediction.setConversionFlag(account.getConversionPrediction().getConversionFlag());
			conversionPrediction.setConversionProbability(account.getConversionPrediction().getConversionProbability());
			conversionPrediction.seteTVBand(account.getConversionPrediction().geteTVBand());
			requestData.setConversionPrediction(conversionPrediction);
		}
		
		requestData.setCustLegalEntity(account.getCustLegalEntity());
		requestData.setId(account.getId());
		requestData.setCurrencyPair(null);

		requestData.setClientType(null);
		requestData.setOtherCurrencyPurpose(account.getOtherCurrencyPurpose());
		requestData.setAdKeywordPhrases(account.getAdKeywordPhrases());
		requestData.setPhoneReg(null);
		requestData.setEnquiryDate(null);
		requestData.setOnlineAccountStatus(account.getAccountStatus());
		
		//AT-5457
		if(account.getConsumerDutyScope() != null && Boolean.TRUE.equals(account.getConsumerDutyScope()))
			requestData.setConsumerDutyScope("Yes");
		else
			requestData.setConsumerDutyScope("No");
	}

	/**
	 * Transform risk profile data.
	 *
	 * @param account the account
	 * @return the risk profile
	 */
	private RiskProfile transformRiskProfileData(Account account) {
		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAnnualSales(account.getRiskProfile().getAnnualSales());
		riskProfile.setContinent(account.getRiskProfile().getContinent());
		riskProfile.setCountry(account.getRiskProfile().getCountry());
		riskProfile.setCountryRiskIndicator(account.getRiskProfile().getCountryRiskIndicator());
		riskProfile.setCreditLimit(account.getRiskProfile().getCreditLimit());
		riskProfile.setDelinquencyFailureScore(account.getRiskProfile().getDelinquencyFailureScore());
		riskProfile.setDomesticUltimateParentDetails(account.getRiskProfile().getDomesticUltimateParentDetails());
		riskProfile.setDomesticUltimateRecord(account.getRiskProfile().getDomesticUltimateRecord());
		riskProfile.setDunsNumber(account.getRiskProfile().getDunsNumber());
		riskProfile.setFailureScore(account.getRiskProfile().getFailureScore());
		riskProfile.setFinancialFiguresMonth(account.getRiskProfile().getFinancialFiguresMonth());
		riskProfile.setFinancialFiguresYear(account.getRiskProfile().getFinancialFiguresYear());
		riskProfile.setFinancialYearEndDate(account.getRiskProfile().getFinancialYearEndDate());
		riskProfile.setGlobalUltimateParentDetails(account.getRiskProfile().getGlobalUltimateParentDetails());
		riskProfile.setGlobalUltimateRecord(account.getRiskProfile().getGlobalUltimateRecord());
		riskProfile.setGroupStructureNumberOfLevels(account.getRiskProfile().getGroupStructureNumberOfLevels());
		riskProfile.setHeadquarterDetails(account.getRiskProfile().getHeadquarterDetails());
		riskProfile.setImmediateParentDetails(account.getRiskProfile().getImmediateParentDetails());
		riskProfile.setImportExportIndicator(account.getRiskProfile().getImportExportIndicator());
		riskProfile.setLocationType(account.getRiskProfile().getLocationType());
		riskProfile.setModelledAnnualSales(account.getRiskProfile().getModelledAnnualSales());
		riskProfile.setModelledNetWorth(account.getRiskProfile().getModelledNetWorth());
		riskProfile.setNetWorthAmount(account.getRiskProfile().getNetWorthAmount());
		riskProfile.setProfitLoss(account.getRiskProfile().getProfitLoss());
		riskProfile.setRiskDirection(account.getRiskProfile().getRiskDirection());
		riskProfile.setRiskRating(account.getRiskProfile().getRiskRating());
		riskProfile.setRiskTrend(account.getRiskProfile().getRiskTrend());
		riskProfile.setStateCountry(account.getRiskProfile().getStateCountry());
		riskProfile.setTradingStyles(account.getRiskProfile().getTradingStyles());
		riskProfile.setUpdatedRisk(account.getRiskProfile().getUpdatedRisk());
		riskProfile.setUs1987PrimarySic4Digit(account.getRiskProfile().getUs1987PrimarySic4Digit());
		return riskProfile;
	}

	/**
	 * Transform corperate compliance data.
	 *
	 * @param account the account
	 * @return the corperate compliance
	 */
	private CorperateCompliance transformCorperateComplianceData(Account account) {
		CorperateCompliance corperateCompliance = new CorperateCompliance();
		corperateCompliance.setFinancialStrength(account.getCorperateCompliance().getFinancialStrength());
		corperateCompliance.setFixedAssets(account.getCorperateCompliance().getFixedAssets());
		corperateCompliance.setForeignOwnedCompany(account.getCorperateCompliance().getForeignOwnedCompany());
		corperateCompliance.setFormerName(account.getCorperateCompliance().getFormerName());
		corperateCompliance.setGlobalUltimateCountry(account.getCorperateCompliance().getGlobalUltimateCountry());
		corperateCompliance.setGlobalUltimateDuns(account.getCorperateCompliance().getGlobalUltimateDuns());
		corperateCompliance.setGlobalUltimateName(account.getCorperateCompliance().getGlobalUltimateName());
		corperateCompliance.setGrossIncome(account.getCorperateCompliance().getGrossIncome());
		corperateCompliance.setIsoCountryCode2Digit(account.getCorperateCompliance().getIsoCountryCode2Digit());
		corperateCompliance.setIsoCountryCode3Digit(account.getCorperateCompliance().getIsoCountryCode3Digit());
		corperateCompliance.setLegalForm(account.getCorperateCompliance().getLegalForm());
		corperateCompliance.setMatchName(account.getCorperateCompliance().getMatchName());
		corperateCompliance.setNetIncome(account.getCorperateCompliance().getNetIncome());
		corperateCompliance.setNetWorth(account.getCorperateCompliance().getNetWorth());
		if(account.getCorperateCompliance().getRegistrationDate() != null && !account.getCorperateCompliance().getRegistrationDate().isEmpty()) {
			corperateCompliance.setRegistrationDate(account.getCorperateCompliance().getRegistrationDate());
		}
		
		corperateCompliance.setRegistrationNumber(account.getCorperateCompliance().getRegistrationNumber());
		corperateCompliance.setSic(account.getCorperateCompliance().getSic());
		corperateCompliance.setSicDesc(account.getCorperateCompliance().getSicDesc());
		corperateCompliance.setStatementDate(account.getCorperateCompliance().getStatementDate());
		corperateCompliance.setTotalAssets(account.getCorperateCompliance().getTotalAssets());
		corperateCompliance.setTotalCurrentAssets(account.getCorperateCompliance().getTotalCurrentAssets());
		corperateCompliance.setTotalCurrentLiabilities(account.getCorperateCompliance().getTotalCurrentLiabilities());
		corperateCompliance
				.setTotalLiabilitiesAndEquities(account.getCorperateCompliance().getTotalLiabilitiesAndEquities());
		corperateCompliance.setTotalLongTermLiabilities(account.getCorperateCompliance().getTotalLongTermLiabilities());
		corperateCompliance.setTotalMatchedShareholders(account.getCorperateCompliance().getTotalMatchedShareholders());
		corperateCompliance.setTotalShareHolders(account.getCorperateCompliance().getTotalShareHolders());
		return corperateCompliance;
	}

	/**
	 * Transform contact request.
	 *
	 * @param contact the contact
	 * @param account the account
	 * @return the transaction monitoring contact request
	 * @throws ComplianceException the compliance exception
	 */
	private TransactionMonitoringContactRequest transformContactRequest(Contact contact, Account account) {

		TransactionMonitoringContactRequest requestData = new TransactionMonitoringContactRequest();

		requestData.setId(contact.getId());
		requestData.setTradeAccNumber(account.getTradeAccountNumber());
		requestData.setContactSFID(contact.getContactSFID());
		requestData.setTradeContactID(contact.getTradeContactID());
		requestData.setTitle(contact.getTitle());
		requestData.setPreferredName(contact.getPreferredName());
		requestData.setFirstName(contact.getFirstName());
		requestData.setLastName(contact.getLastName());
		if(contact.getDob() != null && !contact.getDob().isEmpty()) {
			requestData.setDob(contact.getDob());
		}
		requestData.setAddressType(contact.getAddressType());
		requestData.setStreet(contact.getStreet());
		requestData.setCity(contact.getCity());
		requestData.setAddress1(contact.getAddress1());

		requestData.setPostCode(contact.getPostCode());
		requestData.setSubBuildingorFlat(contact.getSubBuildingorFlat());
		requestData.setUnitNumber(contact.getUnitNumber());
		requestData.setSubCity(contact.getSubCity());
		requestData.setRegion(contact.getRegion());
		requestData.setState(contact.getState());

		requestData.setPhoneWork(contact.getPhoneWork());
		requestData.setPhoneHome(contact.getPhoneHome());
		requestData.setPhoneMobile(contact.getPhoneMobile());
		requestData.setEmail(contact.getEmail());
		requestData.setPrimaryContact(contact.getPrimaryContact());
		requestData.setPrimaryPhone(contact.getPrimaryPhone());

		requestData.setGender(contact.getGender());
		requestData.setNationality(contact.getNationality());
		requestData.setOccupation(contact.getOccupation());
		requestData.setPositionOfSignificance(contact.getPositionOfSignificance());

		if (null != contact.getPassportCountryCode()) {
			requestData.setPassportCountryCode(contact.getPassportCountryCode());
		}
		requestData.setPassportFamilyNameAtBirth(contact.getPassportFamilyNameAtBirth());
		requestData.setPassportNameAtCitizenship(contact.getPassportNameAtCitizenship());
		requestData.setPassportNumber(contact.getPassportNumber());
		requestData.setPassportPlaceOfBirth(contact.getPassportPlaceOfBirth());
		
		if(null != contact.getPassportExiprydate() && !contact.getPassportExiprydate().isEmpty()) {
			requestData.setPassportExiprydate(contact.getPassportExiprydate());
		}
		
		requestData.setPassportFullName(contact.getPassportFullName());
		requestData.setPassportMRZLine1(contact.getPassportMRZLine1());
		requestData.setPassportMRZLine2(contact.getPassportMRZLine2());

		requestData.setDlCardNumber(contact.getDlCardNumber());

		if (null != contact.getDlCountryCode()) {
			requestData.setDlCountryCode(contact.getDlCountryCode());
		}
		
		if(null != contact.getDlExpiryDate() && !contact.getDlExpiryDate().isEmpty()) {
			requestData.setDlExpiryDate(contact.getDlExpiryDate());
		}
		
		requestData.setDlLicenseNumber(contact.getDlLicenseNumber());
		requestData.setDlStateCode(contact.getDlStateCode());
		requestData.setDlVersionNumber(contact.getDlVersionNumber());

		requestData.setMedicareCardNumber(contact.getMedicareCardNumber());
		requestData.setMedicareReferenceNumber(contact.getMedicareReferenceNumber());
		requestData.setMunicipalityOfBirth(contact.getMunicipalityOfBirth());
		requestData.setIpAddress(contact.getIpAddress());

		requestData.setMiddleName(contact.getMiddleName());
		requestData.setCountryOfBirth(contact.getCountryOfBirth());
		requestData.setPrefecture(contact.getPrefecture());
		requestData.setAza(contact.getAza());
		requestData.setResidentialStatus(contact.getResidentialStatus());

		requestData.setAddressType(contact.getAddressType());
		requestData.setRegion(contact.getRegion());

		requestData.setUpdateStatus(null);
		
		//Added for AT-4077
		requestData.setCountry(contact.getCountry());
		requestData.setBuildingName(contact.getBuildingName());
		requestData.setSubBuildingorFlat(contact.getSubBuildingorFlat());
		requestData.setHouseBuildingNumber(contact.getBuildingNumber());
		
		//Added for AT-4079
		requestData.setStreetNumber(contact.getStreetNumber());
		requestData.setStreetType(contact.getStreetType());
		
		requestData.setAuthorisedSignatory(contact.getAuthorisedSignatory());
		requestData.setJobTitle(contact.getJobTitle());
		requestData.setDesignation(contact.getDesignation());
		requestData.setYearsInAddress(contact.getYearsInAddress());
		requestData.setAustraliaRTACardNumber(contact.getAustraliaRTACardNumber());

		return requestData;

	}

	/**
	 * Transform contact request set watchlist.
	 *
	 * @param contactID       the contact ID
	 * @param tmSingupContact the tm singup contact
	 * @throws ComplianceException the compliance exception
	 */
	private void transformContactRequestSetWatchlist(Integer contactID,
			TransactionMonitoringContactRequest tmSingupContact) throws ComplianceException {

		List<String> watchList = newRegistrationDBServiceImpl.getContactWatchlist(contactID);
		tmSingupContact.setWatchlist(watchList);

	}
	
	/**
	 * Transform status update reason.
	 *
	 * @param contactID the contact ID
	 * @param tmSingupContact the tm singup contact
	 * @throws ComplianceException the compliance exception
	 */
	private void transformStatusUpdateReason(Integer contactID,
			TransactionMonitoringContactRequest tmSingupContact) throws ComplianceException {

		List<String> statusReason = newRegistrationDBServiceImpl.getContactStatusUpdateReason(contactID);
		tmSingupContact.setStatusUpdateReason(statusReason);

	}
	
	/**
	 * Transform compliance log.
	 *
	 * @param accountID the account ID
	 * @param tmSingupContact the tm singup contact
	 * @throws ComplianceException the compliance exception
	 */
	private void transformComplianceLog(Integer accountID,
			TransactionMonitoringAccountRequest tmSignUpAccountRequest) throws ComplianceException {

		String complianceLog = newRegistrationDBServiceImpl.getComplianceLogForIntuition(accountID);
		tmSignUpAccountRequest.setComplianceLog(complianceLog);

	}
	
	private void transformMailingStreet(List<Contact> existingContacts,
			TransactionMonitoringAccountRequest tmSignUpAccountRequest) {
		boolean isSecondaryAddressPresent = existingContacts.stream()
				.map(Contact::getMaddress2Street)
				.anyMatch(address -> StringUtils.isNotBlank(address));

		if (isSecondaryAddressPresent) {
			tmSignUpAccountRequest.setSecondaryAddressPresent(SecondaryAddressDelivery.YES.name());
		} else {
			tmSignUpAccountRequest.setSecondaryAddressPresent(SecondaryAddressDelivery.NO.name());
		}
	}
	
	/**
	 * Transform response.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		MessageExchange exchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);

		try {
			RegistrationResponse signUpResponse = (RegistrationResponse) message.getPayload()
					.getGatewayMessageExchange().getResponse();
			transformSignUpResponse(exchange, signUpResponse);
		} catch (Exception e) {
			LOG.error("Error", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Transform sign up response.
	 *
	 * @param exchange the exchange
	 */
	private void transformSignUpResponse(MessageExchange exchange, RegistrationResponse signUpResponse) {
		TransactionMonitoringSignupResponse response = (TransactionMonitoringSignupResponse) exchange.getResponse();
		TransactionMonitoringAccountSignupResponse tmSingUpAccountResponse = new TransactionMonitoringAccountSignupResponse();
		TransactionMonitoringAccountProviderResponse tmAccountProviderResponse = new TransactionMonitoringAccountProviderResponse();
		
		try {

			if (response != null && !response.getTransactionMonitoringAccountSignupResponse().getStatus().equalsIgnoreCase(Constants.SERVICE_FAILURE)) {

				tmSingUpAccountResponse = response.getTransactionMonitoringAccountSignupResponse();

				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						tmSingUpAccountResponse.getAccountId());

				TransactionMonitoringAccountSummary accSummary = JsonConverterUtil
						.convertToObject(TransactionMonitoringAccountSummary.class, eventServiceLog.getSummary());

				setAccountSignUpResponse(tmSingUpAccountResponse, eventServiceLog, accSummary);
				
				response.setTransactionMonitoringAccountSignupResponse(tmSingUpAccountResponse);
				
				signUpResponse.getAccount().setTransactionMonitoringStatus(tmSingUpAccountResponse.getStatus());
				
				if(accSummary.getRiskLevel() != null) {
					signUpResponse.getAccount().setIntuitionRiskLevel(accSummary.getRiskLevel());
				}
				
				if(accSummary.getCardDecision() != null) {
					signUpResponse.getAccount().setCardDecision(accSummary.getCardDecision());
				}
				
			} else if(response != null && response.getTransactionMonitoringAccountSignupResponse().getHttpStatus() == 400 ) {
				
				TransactionMonitoringSignupRequest request = (TransactionMonitoringSignupRequest) exchange.getRequest();
				tmSingUpAccountResponse = response.getTransactionMonitoringAccountSignupResponse();
				
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						request.getTransactionMonitoringAccountRequest().getId());

				TransactionMonitoringAccountSummary accSummary = JsonConverterUtil
						.convertToObject(TransactionMonitoringAccountSummary.class, eventServiceLog.getSummary());
				
				 tmAccountProviderResponse = tmSingUpAccountResponse.getTransactionMonitoringAccountProviderResponse();
				 
					accSummary.setStatus(tmSingUpAccountResponse.getStatus());
					accSummary.setCorrelationId(tmAccountProviderResponse.getCorrelationId());
					accSummary.setAccountId(tmSingUpAccountResponse.getAccountId());
					accSummary.setHttpStatus(tmSingUpAccountResponse.getHttpStatus());

					eventServiceLog
							.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmAccountProviderResponse));
					eventServiceLog.setStatus(tmSingUpAccountResponse.getStatus());
					eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(accSummary));
					eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				 
					sendAlertEmailForInvalidRequest(request.getTransactionMonitoringAccountRequest().getTradeAccountNumber(), "Signup", tmSingUpAccountResponse);
				
			} else {
				TransactionMonitoringSignupRequest request = (TransactionMonitoringSignupRequest) exchange.getRequest();
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						request.getTransactionMonitoringAccountRequest().getId());

				TransactionMonitoringAccountSummary accSummary = JsonConverterUtil
						.convertToObject(TransactionMonitoringAccountSummary.class, eventServiceLog.getSummary());

				tmSingUpAccountResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				accSummary.setAccountId(tmSingUpAccountResponse.getAccountId());	
				accSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				accSummary.setHttpStatus(tmSingUpAccountResponse.getHttpStatus());	

				tmAccountProviderResponse.setStatus(Constants.SERVICE_FAILURE);
				eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmAccountProviderResponse));
				eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(accSummary));
				eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

				TransactionMonitoringSignupResponse responseServiceFailure =new TransactionMonitoringSignupResponse();
				responseServiceFailure.setTransactionMonitoringAccountSignupResponse(tmSingUpAccountResponse);
				
				signUpResponse.getAccount().setTransactionMonitoringStatus(tmSingUpAccountResponse.getStatus());
				
				exchange.setResponse(responseServiceFailure);

			}
			

		} catch (Exception e) {

			LOG.error(ERROR, e);

		}

	}

	private void setAccountSignUpResponse(TransactionMonitoringAccountSignupResponse tmSingUpAccountResponse,
			EventServiceLog eventServiceLog, TransactionMonitoringAccountSummary accSummary) {
		TransactionMonitoringAccountProviderResponse tmAccountProviderResponse = tmSingUpAccountResponse.getTransactionMonitoringAccountProviderResponse();
		try {
			if(tmSingUpAccountResponse.getStatus().equalsIgnoreCase(Constants.NOT_REQUIRED)) {
				accSummary.setStatus(tmSingUpAccountResponse.getStatus());
				accSummary.setAccountId(tmSingUpAccountResponse.getAccountId());
				
				tmAccountProviderResponse = new TransactionMonitoringAccountProviderResponse();
				tmAccountProviderResponse.setStatus(Constants.NOT_REQUIRED);
				
				eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmAccountProviderResponse));	
				eventServiceLog.setStatus(tmSingUpAccountResponse.getStatus());	
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(accSummary));	
				eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			}
			else {
				accSummary.setStatus(tmSingUpAccountResponse.getStatus());
				accSummary.setCorrelationId(tmAccountProviderResponse.getCorrelationId());	
				accSummary.setProfileScore(tmAccountProviderResponse.getProfileScore());	
				accSummary.setRuleScore(tmAccountProviderResponse.getRuleScore());	
				accSummary.setRulesTriggered(tmAccountProviderResponse.getRulesTriggered());
				accSummary.setRiskLevel(tmAccountProviderResponse.getRiskLevel());
				accSummary.setAccountId(tmSingUpAccountResponse.getAccountId());	
				accSummary.setHttpStatus(tmSingUpAccountResponse.getHttpStatus());	
				accSummary.setCardDecision(tmAccountProviderResponse.getCardDecision());
					
				eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithNull(tmAccountProviderResponse));	
				eventServiceLog.setStatus(tmSingUpAccountResponse.getStatus());	
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithNull(accSummary));	
				eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			}
		} catch (Exception e) {
			LOG.error("Error in reading TM Account response", e);
			tmSingUpAccountResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			accSummary.setAccountId(tmSingUpAccountResponse.getAccountId());	
			accSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			accSummary.setHttpStatus(tmSingUpAccountResponse.getHttpStatus());	
			
			tmAccountProviderResponse = new TransactionMonitoringAccountProviderResponse();
			
			tmAccountProviderResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmAccountProviderResponse));
			eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(accSummary));
			eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		}

	}
	
	
	/**
	 * Send alert email for invalid request.
	 *
	 * @param tradeAccNumber the trade acc number
	 * @param type the type
	 */
	private void sendAlertEmailForInvalidRequest(String tradeAccNumber, String type, TransactionMonitoringAccountSignupResponse response) {
		
		SendEmailRequestForTM sendTMEmailRequest = new SendEmailRequestForTM(); 
		EmailHeaderForTM header = new EmailHeaderForTM();
		EmailPayloadForTM payload = new EmailPayloadForTM();

		header.setSourceSystem("Atlas");
		header.setOrgCode("Currencies Direct");
		header.setLegalEntity("CDLGB");
		
		String email = System.getProperty("intuition.sendTo");
		List<String> list = Arrays.asList(email.split(","));

		payload.setEmailId(list);
		payload.setSubject("Intuition request failed");
		payload.setEmailContent(type +" request for Customer Number "+ tradeAccNumber +" to Intuition failed, due to intuition error desc : "+
				response.getErrorDesc() +". <BR>"+response.getErrorDescription()+". <br> Please alert dev team.");
		payload.setFromEmilId(System.getProperty("intuition.sendFrom"));
		payload.setReplyToEmailId(System.getProperty("intuition.sendFrom"));

		sendTMEmailRequest.setTmHeader(header);
		sendTMEmailRequest.setTmPayload(payload);
		
		iCommHubServiceImpl.sendEmailForTMAlert(sendTMEmailRequest,true);
		
	}
	
	/**
	 * Format date.
	 *
	 * @param date the date
	 * @return the string
	 */
	private String formatDate(LocalDate date) {
		if (date != null) {
			return formatter.format(date);
		}
		return null;
	}

	/**
	 * Gets the formatted date.
	 *
	 * @param date the date
	 * @return the formatted date
	 * @throws ParseException the parse exception
	 */
	public static String getFormattedDate(String date) throws ParseException {
		if (date != null) {
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			Date fDate=formatter.parse(date);
			return formatter.format(fDate);
		}
		return null;
	}
	
	/** Add For AT-5029
	 * Gets the integer turn over.
	 *
	 * @param turnOver the turn over
	 * @return the integer turn over
	 */
	public static Integer getIntegerTurnOver(String turnOver) {
		if(turnOver.contains("-")) {
			return null;
		 } else {
			if(turnOver.contains(".")) {
				return (int) Math.round(Double.parseDouble(turnOver));
			}else {
				return Integer.parseInt(turnOver);
			}
		 }
	}
}
