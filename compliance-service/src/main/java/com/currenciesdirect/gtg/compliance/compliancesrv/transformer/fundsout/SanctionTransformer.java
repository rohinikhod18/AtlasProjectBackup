package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsout;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsInSanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsOutSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.ExternalErrorCodes;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncaching.ContactSanctionResponseCaching;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractSanctionTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class SanctionTransformer.
 *  
 * Responsibility : 
 * 
 * This is fundsOut Class used to 
 * 1) This method is called from FundsOutCreate xml 
 * 2) Transform fundsOut Request which comes from Payment system(I.e. Aurora) into Sanction request and returns
 * 3) Also it fundsOut sanction Response is populates to eventservicelog object with ,provider response and summary and returns 
 * 
 * 
 */
public class SanctionTransformer extends AbstractSanctionTransformer{
	private static final Logger LOGGER = LoggerFactory.getLogger(SanctionTransformer.class);
	private ProvideCacheLoader cacheLoader = ProvideCacheLoader.getInstance();
	private static final String ERROR ="ERROR";
	
	/** The contact sanction status caching. */
	@Autowired
	private ContactSanctionResponseCaching contactSanctionStatusCaching;
	
	/**
	 * Business -- 
	 * If Contact/Account is Inactive, Sanction request is not sent to provider 
	 * else sanction can be performed on Contact/Bank/Beneficiary
	 * in either case Event ServiceLog entry must be created.	 * 
	 *  While sending sanction request, default status is assumed as NOT_PERFORMED
	 * And when response is received from HTTPClient, appropriate response is updated	 * 
	 * When sanction request is not sent, default status is set to NOT_REQUIRED
	 * 
	 * Implementation---
	 * 1) Get the messageExchange,UserProfile,fundsOutRequest,contact,eventId from the message
	 * 2) eventId, contact are already populated from data enricher so that we can use the same to insert records in to the table
	 * 3) Create sanction request and default SanctionContactResponse with not performed status so that if we get any exception from micro service
	 * this default object can be pushed to database
	 * 4) Then check additional attributes ATT_DO_PERFORM_OTHER_CHECKS from fundsOutRequest which is populated in data enricher so that a decision 
	 * is made as below
	 * 		4.a)If additional attributes ATT_DO_PERFORM_OTHER_CHECKS is true (which means contact and account is Active in DB) then perform sanction check
	 * 			by calling sanction micro service
	 * 		4.b) If false then do not perform sanction check and default status is set to NOT_REQUIRED which will be set in eventservicelog object
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutRequest fundsOutRequest = (FundsOutRequest) messageExchange.getRequest();
			Account account = (Account)fundsOutRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			String custType = account.getCustType();
			if (custType!= null){
				custType = custType.trim();
			}
			if (Constants.CFX.equalsIgnoreCase(custType)) {
				transformCFXRequest(message);
			}else if (Constants.PFX.equalsIgnoreCase(custType)){
				transformPFXRequest(message);
			}
		} catch (Exception e) {
			LOGGER.error(ERROR, e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
	/**
	 * Transform CFX Request
	 * 
	 * @param message
	 * @return
	 * @throws ComplianceException 
	 */
	private Message<MessageContext> transformCFXRequest(Message<MessageContext> message) throws ComplianceException {

		try {
			transformCommonRequest(message);
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}

	/**
	 * Transform PFX Request
	 * 
	 * @param message
	 * @return
	 * @throws ComplianceException 
	 */
	private Message<MessageContext> transformPFXRequest(Message<MessageContext> message) throws ComplianceException {

		try {
			transformCommonRequest(message);
		} catch (Exception exception) {
			LOGGER.debug(Constants.ERROR, exception);
		}
		return message;
	}
	
	/**
	 * Transform Common Request
	 * 
	 * @param message
	 * @return
	 * @throws Exception 
	 */
	public Message<MessageContext> transformCommonRequest(Message<MessageContext> message) throws ComplianceException {
		
		try {
			FundsOutRequest fundsOutRequest = message.getPayload().getGatewayMessageExchange().getRequest(FundsOutRequest.class);
			if (fundsOutRequest.getAdditionalAttribute(Constants.PERFORM_CHECKS).equals(Boolean.TRUE)) {
				setSanctionResponseStatus(message, ServiceStatus.NOT_PERFORMED);
			} else {
				setSanctionResponseStatus(message, ServiceStatus.NOT_REQUIRED);
			}

		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	
	/**Added SanctionId for Contact,Bank,Beneficiary while creating DefaultResponseStatus
	 * Changes made by-Saylee
	 * @throws Exception 
	 */
	private void setSanctionResponseStatus(Message<MessageContext> message, ServiceStatus serviceStatus) throws ComplianceException {
		try{
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			OperationEnum operation = messageExchange.getOperation();
			
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			
			FundsOutRequest fundsOutRequest = message.getPayload().getGatewayMessageExchange().getRequest(FundsOutRequest.class);
			Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			List<Contact> contacts = new ArrayList<>(1);
			contacts.add(contact);
			
			//FundsOut Sanction Request Creation
			FundsInSanctionRequest sanctionRequest = createSanctionRequest(fundsOutRequest, contacts);
			
			sanctionRequest.setCustomerNumber(fundsOutRequest.getTradeAccountNumber());
			SanctionContactResponse defaultResponse = new SanctionContactResponse();
			
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			ccExchange.setRequest(sanctionRequest);
			
			SanctionResponse fResponse ;
			
			//FundsOut Sanction Default Response Creation
			fResponse = createServiceDeafaultResponse(fundsOutRequest, serviceStatus);
			defaultResponse.setStatus(serviceStatus.name());
			
			String orgID = StringUtils.leftPadWithZero(3, fundsOutRequest.getOrgId());
			
			//Creating EventServiceLog Default entry and storing it in FundsOutRequest to process after Service calls are finished.
			if(operation==OperationEnum.FUNDS_OUT)
				fundsOutRequest.setIsContactEligible(Boolean.TRUE);
			if (Boolean.TRUE.equals(fundsOutRequest.getIsContactEligible())) {
				/**Set SanctionId for contact - Saylee*/
				String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, contact.getId().toString());
				SanctionSummary summary = new SanctionSummary();
				summary.setStatus(serviceStatus.name());
				summary.setWorldCheck(Constants.NOT_AVAILABLE);
				summary.setOfacList(Constants.NOT_AVAILABLE);
				summary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
				summary.setSanctionId(providerRef);
				ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.SANCTION_SERVICE,
						ServiceProviderEnum.SANCTION_SERVICE, contact.getId(), contact.getVersion(),
						EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, summary, serviceStatus));
			}
			if (Boolean.TRUE.equals(fundsOutRequest.getIsBeneficiaryEligible())) {
				/**Set SanctionId for Benificary - Saylee*/
				String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_BENEFICIARY,
						fundsOutRequest.getBeneficiary().getBeneficiaryId().toString());
				SanctionSummary bSummary = new SanctionSummary();
				bSummary.setStatus(serviceStatus.name());
				bSummary.setWorldCheck(Constants.NOT_AVAILABLE);
				bSummary.setOfacList(Constants.NOT_AVAILABLE);
				bSummary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
				bSummary.setSanctionId(providerRef);
				ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.SANCTION_SERVICE,
						ServiceProviderEnum.SANCTION_SERVICE, fundsOutRequest.getBeneficiary().getBeneficiaryId(), fundsOutRequest.getVersion(),
						EntityEnum.BENEFICIARY.name(), token.getUserID(), defaultResponse, bSummary, serviceStatus));
			}
			if (Boolean.TRUE.equals(fundsOutRequest.getIsBankEligible())) {
				/**Set SanctionId for Bank - Saylee*/
				String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_BANK,
						fundsOutRequest.getBeneficiary().getBeneficiaryBankid().toString());
				SanctionSummary bankSummary = new SanctionSummary();
				bankSummary.setStatus(serviceStatus.name());
				bankSummary.setWorldCheck(Constants.NOT_AVAILABLE);
				bankSummary.setOfacList(Constants.NOT_AVAILABLE);
				bankSummary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
				bankSummary.setSanctionId(providerRef);
				ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.SANCTION_SERVICE,
						ServiceProviderEnum.SANCTION_SERVICE, fundsOutRequest.getBeneficiary().getBeneficiaryBankid(), fundsOutRequest.getVersion(),
						EntityEnum.BANK.name(), token.getUserID(), defaultResponse, bankSummary, serviceStatus));
	
			}
			ccExchange.setResponse(fResponse);
			message.getPayload().appendMessageExchange(ccExchange);
		}catch(Exception ex){
			LOGGER.warn(ERROR, ex);
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR,ex);
		}
		
	}

	private SanctionResponse createServiceDeafaultResponse(FundsOutRequest fundsOutRequest,ServiceStatus status) {
		Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
		List<Contact> contacts = new ArrayList<>(1);
		contacts.add(contact);
		SanctionResponse fResponse = new SanctionResponse();
		fResponse.setContactResponses(createContactResponse(contacts));
		fResponse.setBankResponses(createBankResponse(fundsOutRequest,status));
		fResponse.setBeneficiaryResponses(createBeneficiaryResponse(fundsOutRequest,status));
		return fResponse;
	}

	protected List<SanctionBankResponse> createBankResponse(FundsOutRequest fundsOutRequest,ServiceStatus status) {
		List<SanctionBankResponse> bankResponses = new ArrayList<>();
		SanctionBankResponse bankResponse = new SanctionBankResponse();
		bankResponse.setSanctionId(fundsOutRequest.getBeneficiary().getBeneficiaryBankid().toString());
		bankResponse.setStatus(status.name());
		bankResponse.setBankID(fundsOutRequest.getBeneficiary().getBeneficiaryBankid());
		bankResponses.add(bankResponse);
		return bankResponses;
	}

	protected List<SanctionBeneficiaryResponse> createBeneficiaryResponse(FundsOutRequest fundsOutRequest,ServiceStatus status) {
		List<SanctionBeneficiaryResponse> beneResponses = new ArrayList<>();
		SanctionBeneficiaryResponse beneResponse = new SanctionBeneficiaryResponse();
		beneResponse.setSanctionId(fundsOutRequest.getBeneficiary().getBeneficiaryId().toString());
		beneResponse.setStatus(status.name());
		beneResponse.setBeneficiaryId(fundsOutRequest.getBeneficiary().getBeneficiaryId());
		beneResponses.add(beneResponse);
		return beneResponses;
	}

	private FundsInSanctionRequest createSanctionRequest(FundsOutRequest fundsOutRequest, List<Contact> contacts) throws ComplianceException {
		FundsInSanctionRequest sanctionRequest = new FundsInSanctionRequest();
		String orgID = StringUtils.leftPadWithZero(3, fundsOutRequest.getOrgId());
		String contactSanctionCategory =  getContactSanctionCategory();
		String bankSanctionCategory = getBankSanctionCategory();
		String beneficiarySanctionCategory = getBeneficiarySanctionCategory(fundsOutRequest.getBeneficiary().getBeneficiaryType());
		
		if(Boolean.TRUE.equals(fundsOutRequest.getIsContactEligible())){
			sanctionRequest.setContactrequests(createContactRequestForFundsOut(orgID, contacts,cacheLoader,fundsOutRequest,contactSanctionCategory));
		}
		if(Boolean.TRUE.equals(fundsOutRequest.getIsBeneficiaryEligible())){
			sanctionRequest.setBeneficiaryRequests(createBeneficiaryRequest(fundsOutRequest,beneficiarySanctionCategory));
		}
		if(Boolean.TRUE.equals(fundsOutRequest.getIsBankEligible())){
			sanctionRequest.setBankRequests(createBankRequest(fundsOutRequest,bankSanctionCategory));
		}
		return sanctionRequest;
	}

	protected List<SanctionBeneficiaryRequest> createBeneficiaryRequest(FundsOutRequest fundsOutRequest, String beneficiarySanctionCategory)
			throws ComplianceException {
		List<SanctionBeneficiaryRequest> beneRequests = new ArrayList<>();
		StringBuilder sanctionID = new StringBuilder();
		sanctionID.append(StringUtils.leftPadWithZero(3, fundsOutRequest.getOrgId())).append("-P-")
				.append(StringUtils.leftPadWithZero(10, fundsOutRequest.getBeneficiary().getBeneficiaryId()));
		SanctionBeneficiaryRequest beneRequest = new SanctionBeneficiaryRequest();
		SanctionSummary summary = (SanctionSummary) fundsOutRequest.getAdditionalAttribute(Constants.PREVIOUS_BENEFICARY_SANCTION_SUMMARY+fundsOutRequest.getBeneficiary().getBeneficiaryId());
		if(null!=fundsOutRequest.getBeneficiary().getCountry()){
		beneRequest.setCountry(null != cacheLoader.getCountryFullName(fundsOutRequest.getBeneficiary().getCountry())
				? cacheLoader.getCountryFullName(fundsOutRequest.getBeneficiary().getCountry()) : "");
		}
		beneRequest.setFullName(fundsOutRequest.getBeneficiary().getFullName());
		beneRequest.setGender("Male");
		beneRequest.setSanctionId(sanctionID.toString());
		beneRequest.setIsExisting(fundsOutRequest.isBeneficiarySanctionPerformed());
		beneRequest.setBeneficiaryId(fundsOutRequest.getBeneficiary().getBeneficiaryId());
		beneRequest.setCategory(beneficiarySanctionCategory);
		if(null != summary && null != summary.getOfacList() && null != summary.getWorldCheck()){
			beneRequest.setPreviousOfac(summary.getOfacList());
			beneRequest.setPreviousWorldCheck(summary.getWorldCheck());
			beneRequest.setPreviousStatus(summary.getStatus());
		}
		beneRequests.add(beneRequest);
		return beneRequests;
	}

	protected List<SanctionBankRequest> createBankRequest(FundsOutRequest fundsOutRequest, String bankSanctionCategory) throws ComplianceException {
		List<SanctionBankRequest> bankRequests = new ArrayList<>();
		StringBuilder sanctionID = new StringBuilder();
		sanctionID.append(StringUtils.leftPadWithZero(3, fundsOutRequest.getOrgId())).append("-B-")
				.append(StringUtils.leftPadWithZero(10, fundsOutRequest.getBeneficiary().getBeneficiaryBankid()));
		SanctionBankRequest bankRequest = new SanctionBankRequest();
		SanctionSummary summary = (SanctionSummary) fundsOutRequest.getAdditionalAttribute(Constants.PREVIOUS_BANK_SANCTION_SUMMARY+fundsOutRequest.getBeneficiary().getBeneficiaryBankid());
		bankRequest.setBankName(fundsOutRequest.getBeneficiary().getBeneficaryBankName());
		if(null!=fundsOutRequest.getBeneficiary().getCountry()){
		bankRequest.setCountry(null != cacheLoader.getCountryFullName(fundsOutRequest.getBeneficiary().getCountry())
				? cacheLoader.getCountryFullName(fundsOutRequest.getBeneficiary().getCountry()) : "");
		}
		bankRequest.setSanctionId(sanctionID.toString());
		bankRequest.setIsExisting(fundsOutRequest.isBankSanctionPerformed());
		bankRequest.setBankId(Integer.parseInt(fundsOutRequest.getBeneficiary().getBeneficiaryBankid().toString()));
		bankRequest.setCategory(bankSanctionCategory);
		if(null != summary && null != summary.getOfacList() && null != summary.getWorldCheck()){
			bankRequest.setPreviousOfac(summary.getOfacList());
			bankRequest.setPreviousWorldCheck(summary.getWorldCheck());
			bankRequest.setPreviousStatus(summary.getStatus());
		}
		bankRequests.add(bankRequest);
		return bankRequests;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		try{
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutRequest fundsOutRequest = (FundsOutRequest) messageExchange.getRequest();
			Account account = (Account)fundsOutRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			String custType = account.getCustType();
			if (custType!= null){
				custType = custType.trim();
			}
			if (Constants.CFX.equalsIgnoreCase(custType)) {
				transformCFXResponse(message);
			} else if (Constants.PFX.equalsIgnoreCase(custType)){
				transformPFXResponse(message);
			}
		} catch(Exception e){
			LOGGER.error("Exception in transformResponse() of Sanction Transformer in FundsOut Flow ", e);
			message.getPayload().setFailed(true);
		}
	return message;
		
	}

	/**
	 * @param message
	 * @return
	 */
	private Message<MessageContext> transformCFXResponse(Message<MessageContext> message) {
		
		try {
			transformCommonResponse(message);
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	
	private Message<MessageContext> transformPFXResponse(Message<MessageContext> message) {
		
		try {
			transformCommonResponse(message);
		} catch (Exception exception) {
			LOGGER.debug(Constants.ERROR, exception);
		}
		return message;
	}
	
	private Message<MessageContext> transformCommonResponse(Message<MessageContext> message) {
		try{
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			FundsOutRequest fundsOutRequest = message.getPayload().getGatewayMessageExchange().getRequest(FundsOutRequest.class);
			SanctionSummary firstSummary = (SanctionSummary) fundsOutRequest.getAdditionalAttribute(Constants.PREVIOUS_CONTACT_SANCTION_SUMMARY);
			SanctionResponse fResponse = (SanctionResponse) exchange.getResponse();
			FundsInSanctionRequest sanctionRequest = exchange.getRequest(FundsInSanctionRequest.class);
			if(fResponse == null){
				 fResponse = new SanctionResponse();
				 exchange.setResponse(createServiceFailureNullResponse(sanctionRequest, fResponse, exchange,fundsOutRequest));
				 return message;
			}
			if(OperationEnum.SANCTION_RESEND == message.getPayload().getGatewayMessageExchange().getOperation()){
				return setSanctionResendResponse(message); 
			}
			if(fResponse.getErrorCode()==null){
				exchange.setResponse(handleValidSanctionResponse(fResponse, exchange, sanctionRequest, fundsOutRequest, firstSummary));
			}
			else{
				handleErrorSanctionResponse(message, fResponse);
			}
		}catch(Exception e){
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}

	/**
	 * @param message
	 * @param fResponse
	 * @param logs
	 */
	private void handleErrorSanctionResponse(Message<MessageContext> message, SanctionResponse fResponse) {
		try {
			FundsOutRequest request = (FundsOutRequest)message.getPayload().getGatewayMessageExchange().getRequest();
			SanctionResponse tmp =createServiceFailureResponse(request,fResponse);
			fResponse.setBankResponses(tmp.getBankResponses());
			fResponse.setBeneficiaryResponses(tmp.getBeneficiaryResponses());
			fResponse.setContactResponses(tmp.getContactResponses());
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			
			EventServiceLog contactServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
					EntityEnum.CONTACT.name(), tmp.getContactResponses().get(0).getContactId());
			
			
			SanctionSummary sanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class, contactServiceLog.getSummary());
			sanctionSummary.setStatus(ServiceStatus.FAIL.name());
			contactServiceLog.setStatus(tmp.getContactResponses().get(0).getStatus());
			contactServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(fResponse.getContactResponses().get(0)));
			contactServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(sanctionSummary));
			
			EventServiceLog bankServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
					EntityEnum.BANK.name(), tmp.getBankResponses().get(0).getBankID());
			SanctionSummary bankSanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class, bankServiceLog.getSummary());
			bankSanctionSummary.setStatus(ServiceStatus.FAIL.name());
			bankServiceLog.setStatus(tmp.getBankResponses().get(0).getStatus());
			bankServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(fResponse.getBankResponses().get(0)));
			bankServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(bankSanctionSummary));
			
			EventServiceLog beneServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
					EntityEnum.BENEFICIARY.name(), tmp.getBeneficiaryResponses().get(0).getBeneficiaryId());
			SanctionSummary beneSanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class, beneServiceLog.getSummary());
			beneSanctionSummary.setStatus(ServiceStatus.FAIL.name());
			beneServiceLog.setStatus(tmp.getBeneficiaryResponses().get(0).getStatus());
			beneServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(fResponse.getBeneficiaryResponses().get(0)));
			beneServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(beneSanctionSummary));
			
		}catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		
	}

	/**
	 * Create SanctionSummary for Contact,Bank,Beneficiary as per Required Status
	 *  and update that entry into EventServiceLog.
	 *  Changes made by- Saylee
	 */
	private SanctionResponse handleValidSanctionResponse(SanctionResponse fResponse, MessageExchange exchange,
			FundsInSanctionRequest sanctionRequest, FundsOutRequest fundsOutRequest, SanctionSummary firstSummary) {
		try {
			//AT-3102
			String sanctionContactCacheResponse = (String) fundsOutRequest.getAdditionalAttribute("contactSanctionCachedProviderResponse");
			if(null != sanctionContactCacheResponse) {
				SanctionContactResponse sanctionContactResponse = JsonConverterUtil.convertToObject(SanctionContactResponse.class,
						sanctionContactCacheResponse);
				EventServiceLog contactServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
						EntityEnum.CONTACT.name(), sanctionContactResponse.getContactId());
				createSanctionSummaryForContact(sanctionContactResponse, contactServiceLog,firstSummary);
				List<SanctionContactResponse> contactList = fResponse.getContactResponses();
				contactList.add(sanctionContactResponse);
				fResponse.setContactResponses(contactList);
			}else {
				List<SanctionContactResponse> contactList = fResponse.getContactResponses();
				SanctionContactResponse contactResponse = contactList.get(0);
				EventServiceLog contactServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
						EntityEnum.CONTACT.name(), contactResponse.getContactId());
				createSanctionSummaryForContact(contactResponse, contactServiceLog,firstSummary);
				
				if(!ServiceStatus.SERVICE_FAILURE.toString().equalsIgnoreCase(contactResponse.getStatus())) {
					contactSanctionStatusCaching.insertContactSacntionResponseInCache(contactResponse.getContactId(),contactResponse);
				}
			}
			
			SanctionBankResponse bankResponse = fResponse.getBankResponses().get(0);
			SanctionBeneficiaryResponse beneResponse = fResponse.getBeneficiaryResponses().get(0);
			
			EventServiceLog bankServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
					EntityEnum.BANK.name(), bankResponse.getBankID());
			EventServiceLog beneServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
					EntityEnum.BENEFICIARY.name(), beneResponse.getBeneficiaryId());
			
			SanctionSummary bankSummary = createSanctionSummary(bankResponse.getOfacList(), bankResponse.getWorldCheck(),
					bankResponse.getSanctionId(), bankResponse.getStatus());
			bankSummary.setProviderName(bankResponse.getProviderName());
			bankSummary.setProviderMethod(bankResponse.getProviderMethod());
			
			SanctionSummary beneSummary = createSanctionSummary(beneResponse.getOfacList(), beneResponse.getWorldCheck(),
					beneResponse.getSanctionId(), beneResponse.getStatus());
			beneSummary.setProviderName(beneResponse.getProviderName());
			beneSummary.setProviderMethod(beneResponse.getProviderMethod());
			
			if(bankServiceLog !=null){
			updateResponseInEventLog(bankServiceLog, JsonConverterUtil.convertToJsonWithoutNull(bankResponse), 
					bankResponse.getStatus(), bankSummary);
			}
			
			if(beneServiceLog !=null){
			updateResponseInEventLog(beneServiceLog, JsonConverterUtil.convertToJsonWithoutNull(beneResponse), 
					beneResponse.getStatus(), beneSummary);	
			}
			
		} catch (Exception e) {
				LOGGER.error("Error in reading Sanction response", e);
				try {
					createServiceFailureNullResponse(sanctionRequest, fResponse, exchange, fundsOutRequest);
				} catch (Exception ex){
					LOGGER.debug(Constants.ERROR, e);
					throw ex;
				}
		}
		return fResponse;
	}

	private void createSanctionSummaryForContact(SanctionContactResponse contactResponse,
			EventServiceLog contactServiceLog,SanctionSummary firstSummary) {
		SanctionSummary contactSummary = createSanctionSummary(contactResponse.getOfacList(), contactResponse.getWorldCheck(),
				contactResponse.getSanctionId(), contactResponse.getStatus());
		contactSummary.setProviderName(contactResponse.getProviderName());
		contactSummary.setProviderMethod(contactResponse.getProviderMethod());
		
		if (null != firstSummary
				&& (Constants.NOT_AVAILABLE.equalsIgnoreCase(contactResponse.getWorldCheck())
						|| Constants.NOT_AVAILABLE.equalsIgnoreCase(contactResponse.getOfacList()))) {
		contactSummary.setOfacList(firstSummary.getOfacList());
		contactSummary.setWorldCheck(firstSummary.getWorldCheck());
		}
		updateResponseInEventLog(contactServiceLog, JsonConverterUtil.convertToJsonWithoutNull(contactResponse), 
				contactResponse.getStatus(), contactSummary);
	}

	private SanctionResponse createServiceFailureResponse(FundsOutRequest fundsOutRequest,SanctionResponse fResponse) {
		Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
		List<Contact> contacts = new ArrayList<>(1);
		contacts.add(contact);
		fResponse.setContactResponses(createContactServiceFailureResponse(contacts,fResponse));
		fResponse.setBankResponses(createBankServiceFailureResponse(fundsOutRequest,fResponse));
		fResponse.setBeneficiaryResponses(createBeneficiaryServiceFailureResponse(fundsOutRequest,fResponse));
		return fResponse;
	}
	
	private SanctionResponse createServiceFailureNullResponse(FundsInSanctionRequest sanctionRequest,
			SanctionResponse fResponse, MessageExchange exchange, FundsOutRequest fundsOutRequest) {
		List<SanctionContactResponse> contactResponsesList = new ArrayList<>();
		 List<SanctionBeneficiaryResponse> beneficiaryResponsesList = new ArrayList<>();
		 List<SanctionBankResponse> bankResponsesList = new ArrayList<>();
		 String status = null;
		if (sanctionRequest.getContactrequests() != null) {
			contactResponsesList = createContactServiceFailureNullResponse(sanctionRequest, exchange,fundsOutRequest);
			status = contactResponsesList.get(0).getStatus();
		}

		if (sanctionRequest.getBankRequests() != null) {
			bankResponsesList = createBankServiceFailureNullResponse(sanctionRequest, exchange,fundsOutRequest);
			status = bankResponsesList.get(0).getStatus();
		}

		if (sanctionRequest.getBeneficiaryRequests() != null) {
			beneficiaryResponsesList = createBeneficiaryServiceFailureNullResponse(sanctionRequest, exchange,fundsOutRequest);
			status = beneficiaryResponsesList.get(0).getStatus();
		}
		
		fResponse.setBankResponses(bankResponsesList);
		fResponse.setBeneficiaryResponses(beneficiaryResponsesList);
		fResponse.setContactResponses(contactResponsesList);
		fResponse.addAttribute("sanctionOverallStatus", status);
		return fResponse;
	}
	
	protected List<SanctionContactResponse> createContactServiceFailureResponse(List<Contact> contacts,SanctionResponse fResponse) {
		List<SanctionContactResponse> contactResponses = new ArrayList<>();
		for (Contact contact : contacts) {
			SanctionContactResponse response = new SanctionContactResponse();
			response.setContactId(contact.getId());
			for(SanctionContactResponse cont :fResponse.getContactResponses()){
				Boolean isErrorInResponse = cont != null && cont.getErrorCode() != null;
				if(Boolean.TRUE.equals(isErrorInResponse) 
						&& (Boolean.TRUE.equals(cont.getErrorCode().equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_REQUEST.getErrorCode())) 
						|| Boolean.TRUE.equals(cont.getErrorCode().equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_RESPONSE.getErrorCode()))
						|| Boolean.TRUE.equals(cont.getErrorCode().equals(ExternalErrorCodes.SANCTION_VALIDATION_EXCEPTION.getErrorCode())))){
					response.setStatus(ServiceStatus.NOT_PERFORMED.name());
				}else{
					response.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				}
			}
			contactResponses.add(response);
		}
		return contactResponses;
	}

	protected List<SanctionBankResponse> createBankServiceFailureResponse(FundsOutRequest fundsOutRequest,
			SanctionResponse fResponse) {
		List<SanctionBankResponse> bankResponses = new ArrayList<>();
		SanctionBankResponse bankResponse = new SanctionBankResponse();
		bankResponse.setBankID(fundsOutRequest.getBeneficiary().getBeneficiaryBankid());
		for (SanctionBankResponse bank : fResponse.getBankResponses()) {
			Boolean isErrorinBankResponse = bank != null && bank.getErrorCode() != null;
			if (Boolean.TRUE.equals(isErrorinBankResponse) 
					&& (Boolean.TRUE.equals(bank.getErrorCode().equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_REQUEST.getErrorCode())) 
					|| Boolean.TRUE.equals(bank.getErrorCode().equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_RESPONSE.getErrorCode()))
					|| Boolean.TRUE.equals(bank.getErrorCode().equals(ExternalErrorCodes.SANCTION_VALIDATION_EXCEPTION.getErrorCode())))) {
				bankResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			} else {
				bankResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			}
		}
		bankResponses.add(bankResponse);
		return bankResponses;
	}

	protected List<SanctionBeneficiaryResponse> createBeneficiaryServiceFailureResponse(FundsOutRequest fundsOutRequest,
			SanctionResponse fResponse) {
		List<SanctionBeneficiaryResponse> beneResponses = new ArrayList<>();
		SanctionBeneficiaryResponse beneResponse = new SanctionBeneficiaryResponse();
		beneResponse.setBeneficiaryId(fundsOutRequest.getBeneficiary().getBeneficiaryId());
		for (SanctionBeneficiaryResponse bene : fResponse.getBeneficiaryResponses()) {
			Boolean isErrorInBeneResponse = bene != null && bene.getErrorCode() != null;
			if (Boolean.TRUE.equals(isErrorInBeneResponse) 
					&& (Boolean.TRUE.equals(bene.getErrorCode().equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_REQUEST.getErrorCode())) 
					|| Boolean.TRUE.equals(bene.getErrorCode().equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_RESPONSE.getErrorCode()))
					|| Boolean.TRUE.equals(bene.getErrorCode().equals(ExternalErrorCodes.SANCTION_VALIDATION_EXCEPTION.getErrorCode())))) {
				beneResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			} else {
				beneResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			}
		}
		beneResponses.add(beneResponse);
		return beneResponses;
	}
	
	private Message<MessageContext> setSanctionResendResponse(Message<MessageContext> message){
		FundsOutRequest fundsOutRequest = message.getPayload().getGatewayMessageExchange().getRequest(FundsOutRequest.class);
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		SanctionResponse fResponse = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE).getResponse(SanctionResponse.class);
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		
		String newStatus= null;
		if(Boolean.TRUE.equals(fundsOutRequest.getIsContactEligible())){
		SanctionContactResponse contactResponse = fResponse.getContactResponses().get(0);
		EventServiceLog contactServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.CONTACT.name(), contactResponse.getContactId());
		SanctionSummary contactSummary = createSanctionSummary(contactResponse.getOfacList(), contactResponse.getWorldCheck(),
				contactResponse.getSanctionId(), contactResponse.getStatus());
		contactSummary.setProviderName(contactResponse.getProviderName());
		contactSummary.setProviderMethod(contactResponse.getProviderMethod());
		 String providerResponse = contactResponse.getProviderResponse();
		if(contactResponse.getProviderResponse() == null || contactResponse.getProviderResponse().isEmpty()){
			providerResponse = JsonConverterUtil.convertToJsonWithNull(contactSummary);
		}
		updateResponseInEventLog(contactServiceLog, providerResponse, 
				contactResponse.getStatus(), contactSummary);
		
		newStatus = contactResponse.getStatus();
		}
		
		if(Boolean.TRUE.equals(fundsOutRequest.getIsBankEligible())){
			SanctionBankResponse bankResponse = fResponse.getBankResponses().get(0);
			EventServiceLog bankServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.BANK.name(), bankResponse.getBankID());
			SanctionSummary bankSummary = createSanctionSummary(bankResponse.getOfacList(), bankResponse.getWorldCheck(),
					bankResponse.getSanctionId(), bankResponse.getStatus());
			bankSummary.setProviderName(bankResponse.getProviderName());
			bankSummary.setProviderMethod(bankResponse.getProviderMethod());
			String providerResponse = bankResponse.getProviderResponse();
			if(bankResponse.getProviderResponse() == null || bankResponse.getProviderResponse().isEmpty()){
				providerResponse = JsonConverterUtil.convertToJsonWithNull(bankSummary);
			}
			updateResponseInEventLog(bankServiceLog, providerResponse, 
					bankResponse.getStatus(), bankSummary);
			newStatus =bankResponse.getStatus();
		}
		
		if(Boolean.TRUE.equals(fundsOutRequest.getIsBeneficiaryEligible())){
			SanctionBeneficiaryResponse beneficiaryResponse = fResponse.getBeneficiaryResponses().get(0);
			EventServiceLog beneficiaryServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.BENEFICIARY.name(), beneficiaryResponse.getBeneficiaryId());
			SanctionSummary beneficiarySummary =  createSanctionSummary(beneficiaryResponse.getOfacList(), beneficiaryResponse.getWorldCheck(),
					beneficiaryResponse.getSanctionId(), beneficiaryResponse.getStatus());
			beneficiarySummary.setProviderName(beneficiaryResponse.getProviderName());
			beneficiarySummary.setProviderMethod(beneficiaryResponse.getProviderMethod());
			String providerResponse = beneficiaryResponse.getProviderResponse();
			if(beneficiaryResponse.getProviderResponse() == null || beneficiaryResponse.getProviderResponse().isEmpty()){
				providerResponse = JsonConverterUtil.convertToJsonWithNull(beneficiarySummary);
			}
			updateResponseInEventLog(beneficiaryServiceLog, providerResponse, 
					beneficiaryResponse.getStatus(), beneficiarySummary);
			newStatus = beneficiaryResponse.getStatus();
		}
		String status = determineSanctionStatus(messageExchange,newStatus);
		fResponse.addAttribute(Constants.SANCTION_OVERALLSTATUS, status);
		return message;
	}
	
	private List<SanctionContactResponse> createContactServiceFailureNullResponse(
			FundsInSanctionRequest sanctionRequest, MessageExchange exchange, FundsOutRequest fundsOutRequest) {
		 List<SanctionContactResponse> contactResponsesList = new ArrayList<>();
		 String orgID = StringUtils.leftPadWithZero(3, fundsOutRequest.getOrgId());
		 for (SanctionContactRequest contact : sanctionRequest.getContactrequests()) {
			String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, contact.getContactId() + "");
			
				SanctionContactResponse contactResponse=new SanctionContactResponse();
				contactResponse.setContactId(contact.getContactId());
				contactResponse.setSanctionId(providerRef);
				contactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				contactResponse.setOfacList(Constants.NOT_AVAILABLE);
				contactResponse.setWorldCheck(Constants.NOT_AVAILABLE);
				contactResponsesList.add(contactResponse);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.CONTACT.name(), contact.getContactId());
				if(eventServiceLog !=null)
					createEventServiceLogForServiceFailureWithSanctionID(eventServiceLog, contactResponse,providerRef);
			}
		 return contactResponsesList;
	}
	 
	 private List<SanctionBeneficiaryResponse>  createBeneficiaryServiceFailureNullResponse(FundsInSanctionRequest sanctionRequest, MessageExchange exchange, FundsOutRequest fundsOutRequest){
		 List<SanctionBeneficiaryResponse> beneficiaryResponsesList = new ArrayList<>();
		 String orgID = StringUtils.leftPadWithZero(3, fundsOutRequest.getOrgId());
		 for (SanctionBeneficiaryRequest beneficiary : sanctionRequest.getBeneficiaryRequests()) {
			 String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_BENEFICIARY,
						fundsOutRequest.getBeneficiary().getBeneficiaryId() + "");
				SanctionBeneficiaryResponse beneficiaryReesponse=new SanctionBeneficiaryResponse();
				beneficiaryReesponse.setBeneficiaryId(beneficiary.getBeneficiaryId());
				beneficiaryReesponse.setSanctionId(providerRef);
				beneficiaryReesponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				beneficiaryReesponse.setOfacList(Constants.NOT_AVAILABLE);
				beneficiaryReesponse.setWorldCheck(Constants.NOT_AVAILABLE);
				beneficiaryResponsesList.add(beneficiaryReesponse);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.BENEFICIARY.name(), beneficiary.getBeneficiaryId());
				if(eventServiceLog !=null)
					createEventServiceLogForServiceFailureWithSanctionID(eventServiceLog, beneficiaryReesponse,providerRef);
			}
		 return beneficiaryResponsesList;
	}
	 
	 private  List<SanctionBankResponse>  createBankServiceFailureNullResponse(FundsInSanctionRequest sanctionRequest, MessageExchange exchange, FundsOutRequest fundsOutRequest){
		 List<SanctionBankResponse> bankResponsesList = new ArrayList<>();
		 String orgID = StringUtils.leftPadWithZero(3, fundsOutRequest.getOrgId());
		 for (SanctionBankRequest bank : sanctionRequest.getBankRequests()) {
			 String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_BANK,
						fundsOutRequest.getBeneficiary().getBeneficiaryBankid() + "");
				SanctionBankResponse bankResponse=new SanctionBankResponse();
				bankResponse.setBankID(bank.getBankId());
				bankResponse.setSanctionId(providerRef);
				bankResponse.setOfacList(Constants.NOT_AVAILABLE);
				bankResponse.setWorldCheck(Constants.NOT_AVAILABLE);
				bankResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				bankResponsesList.add(bankResponse);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.BANK.name(), bank.getBankId());
				if(eventServiceLog !=null)
					createEventServiceLogForServiceFailureWithSanctionID(eventServiceLog, bankResponse,providerRef);
			}
		 return bankResponsesList;
	}
	
	 /**
	  *Implementation :-
	  * 1.Get contact list and check the condition for SanctionEligible or not
	  * 2.Get the firstSanctionSummary for particular contactId and 
	  * 	set values for OfacList,WorldCheck and Status from that summary -Saylee
	  * */
	 private List<SanctionContactRequest> createContactRequestForFundsOut(String orgID, List<Contact> contacts,
				ProvideCacheLoader cacheLoader,ServiceMessage request,String category) throws ComplianceException {
			List<SanctionContactRequest> contactList = new ArrayList<>();
			for (Contact contact : contacts) {
				if (contact.isSanctionEligible()) {
					SanctionSummary summary = (SanctionSummary) request.getAdditionalAttribute(Constants.PREVIOUS_CONTACT_SANCTION_SUMMARY +contact.getId());
					String countryName = cacheLoader
							.getCountryFullName(null != contact.getCountry() ? contact.getCountry() : "");
					SanctionContactRequest sanctionContact = new SanctionContactRequest();
					if (null != countryName) {
						sanctionContact.setCountry(countryName);
					}
					
					sanctionContact.setDob(contact.getDob());
					sanctionContact.setFullName(contact.getFullName());
					sanctionContact.setGender(contact.getGender());
					StringBuilder sanctionID = new StringBuilder();
					sanctionID.append(orgID).append("-C-").append(StringUtils.leftPadWithZero(10, contact.getId()));
					sanctionContact.setSanctionId(sanctionID.toString());
					sanctionContact.setContactId(contact.getId());
					sanctionContact.setIsExisting(contact.isSanctionPerformed());
					sanctionContact.setCategory(category);
					if(null != summary && null != summary.getOfacList() && null != summary.getWorldCheck()){
						sanctionContact.setPreviousOfac(summary.getOfacList());
						sanctionContact.setPreviousWorldCheck(summary.getWorldCheck());
						sanctionContact.setPreviousStatus(summary.getStatus());
					}
					contactList.add(sanctionContact);
				}
			}
			return contactList;
		}
		
		/** 
		 *  Business :
		 *  To update sanction status column value in payment out table we have compared
		 *  Contact ,Bank ,Beneficiary Status into one newStatus value and putting it into sanction status column to show it on UI
		 * 
		 * Implementation :
		 * 1)This method sets the sanction status to update sanction status column in payment out table.
		 * 2)Method used for Sanction Repeat Check.
		 * 3)NewStatus is based on Contact ,Bank ,Beneficiary Status.
		 * 4)If Contact ,Bank ,Beneficiary Status is Pass then 'sanction status" is 'PASS'
		 * 5)Else 'sanction status" is 'FAIL'
		 * 
		 */
		private String determineSanctionStatus(MessageExchange messageExchange, String currentStatus) {
			String newStatus;
			FundsOutRequest fundsOutRequest = messageExchange.getRequest(FundsOutRequest.class);
			FundsOutSanctionResendRequest sanctionResendRequest= (FundsOutSanctionResendRequest)fundsOutRequest.getAdditionalAttribute("SANCTION_RESEND_REQUEST");

			if (sanctionResendRequest.getEntityType().equalsIgnoreCase(EntityEnum.CONTACT.name())) {
				
				newStatus = setSanctionStatus(currentStatus, sanctionResendRequest.getBankStatus(),
						sanctionResendRequest.getBeneficiaryStatus());
				
			} else if (sanctionResendRequest.getEntityType().equalsIgnoreCase(EntityEnum.BANK.name())) {
				
				newStatus = setSanctionStatus(sanctionResendRequest.getContactStatus(), currentStatus,
						sanctionResendRequest.getBeneficiaryStatus());
			} else {
				newStatus = setSanctionStatus(sanctionResendRequest.getContactStatus(),
						sanctionResendRequest.getBankStatus(), currentStatus);
			}
			return newStatus;
		}

		/** Check Contact ,Bank ,Beneficiary Status : If 'PASS' for all three status then SET 'PASS' else SET 'FAIL' 
		 * or If any two are 'NOT_REQUIRED' and one is Pass then set status as 'PASS' */
		private String setSanctionStatus(String sanctionContactStatus, String sanctionBankStatus,
				String sanctionBeneficiaryStatus) {
			String newStatus = null;
			try {
				Boolean onlyContactSanctionPasses = ServiceStatus.PASS.name().equalsIgnoreCase(sanctionContactStatus)
						&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBankStatus) 
						&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBeneficiaryStatus);
				Boolean onlyBankSanctionPassed = ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionContactStatus) 
						&& ServiceStatus.PASS.name().equalsIgnoreCase(sanctionBankStatus) 
						&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBeneficiaryStatus);
				Boolean onlyBeneSanctionPassed = ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionContactStatus)
						&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBankStatus)
						&& ServiceStatus.PASS.name().equalsIgnoreCase(sanctionBeneficiaryStatus);
				Boolean allSanctionPassed = ServiceStatus.PASS.name().equalsIgnoreCase(sanctionContactStatus)
						&& ServiceStatus.PASS.name().equalsIgnoreCase(sanctionBankStatus)
						&& ServiceStatus.PASS.name().equalsIgnoreCase(sanctionBeneficiaryStatus);
				newStatus = setNewSanctionStatusOnUpdate(sanctionContactStatus, sanctionBankStatus,
						sanctionBeneficiaryStatus, onlyContactSanctionPasses, onlyBankSanctionPassed,
						onlyBeneSanctionPassed, allSanctionPassed);
			} catch (Exception e) {
				LOGGER.error("Error while setting sanction status for repeat check :: setSanctionStatus() :: ", e);

			}

			return newStatus;
		}

		private String setNewSanctionStatusOnUpdate(String sanctionContactStatus, String sanctionBankStatus,
				String sanctionBeneficiaryStatus, Boolean onlyContactSanctionPasses, Boolean onlyBankSanctionPassed,
				Boolean onlyBeneSanctionPassed, Boolean allSanctionPassed) {
			String newStatus;
			Boolean anyOneSanctionPassed = onlyContactSanctionPasses || onlyBankSanctionPassed || onlyBeneSanctionPassed; 
			if (allSanctionPassed || anyOneSanctionPassed) {
				
				newStatus = ServiceStatus.PASS.name();

			} else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionContactStatus)
					&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBankStatus)
					&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBeneficiaryStatus)) {
				
				newStatus = ServiceStatus.NOT_REQUIRED.name();
				
			}else {
				newStatus = ServiceStatus.FAIL.name();
			}
			return newStatus;
		}
}
