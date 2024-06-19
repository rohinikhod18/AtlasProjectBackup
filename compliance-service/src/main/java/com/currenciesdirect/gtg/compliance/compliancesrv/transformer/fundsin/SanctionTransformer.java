/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.ExternalErrorCodes;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInTrade;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
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
 * This is fundsIn Class used to 
 * 1) This method is called from FundsInCreate xml 
 * 2) Transform fundsIn Request which comes from Payment system(I.e. Aurora) into Sanction request and returns
 * 3) Also it fundsIn sanction Response is populates to eventservicelog object with provider response and summary and returns 
 * 
 * 
 */
public class SanctionTransformer extends AbstractSanctionTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SanctionTransformer.class);
	
	/** The country cache. */
	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();
	
	/** The Constant ERROR. */
	private static final String ERROR ="ERROR";
	
	/**
	 * Business -- 
	 * If Contact/Account is Inactive, Sanction request is not sent to provider 
	 * else sanction can be performed on Debtor
	 * in that case Event ServiceLog entry must be created.	 * 
	 *  While sending sanction request, default status is assumed as NOT_PERFORMED
	 * And when response is received from HTTPClient, appropriate response is updated	
	 * When sanction request is not sent, default status is set to NOT_REQUIRED
	 * 
	 * Implementation---
	 * 1) Get the messageExchange,UserProfile,fundsInCreateRequest,Debtor,eventId from the message
	 * 2) eventId, Debtor Name are already populated from data enricher so that we can use the same to insert records in to the table
	 * 3) Create sanction request and default SanctionDebtorResponse with Not_Performed status so that if we get any exception from micro service
	 * this default object can be pushed to database
	 * 4) Then check additional attributes ATT_DO_PERFORM_OTHER_CHECKS ,ThirdPartyPayment from fundsInRequest which is populated in data enricher so that a decision 
	 * is made as below
	 * 		4.a)If additional attributes ATT_DO_PERFORM_OTHER_CHECKS is true(which means contact and account is Active in DB) and ThirdPartyPayment is true 
	 * 			then perform sanction check by calling sanction micro service 
	 * 		4.b) If false then do not perform sanction check and default status is set to NOT_REQUIRED which will be set in eventservicelog object
	 *
	 * @param fundsInMessage the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> fundsInMessage) {
		
		try {
			MessageExchange messageExchange = fundsInMessage.getPayload().getGatewayMessageExchange();
			FundsInCreateRequest fundsInCreateRequest = (FundsInCreateRequest) messageExchange.getRequest();
			Account fundsInAccount = (Account)fundsInCreateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			String fundsInCustType = fundsInAccount.getCustType();
			if (fundsInCustType!= null){
				fundsInCustType = fundsInCustType.trim();
			}
			if (Constants.CFX.equalsIgnoreCase(fundsInCustType)) {
				transformCFXRequest(fundsInMessage);
			}else if (Constants.PFX.equalsIgnoreCase(fundsInCustType)){
				transformPFXRequest(fundsInMessage);
			}
		} catch (Exception e) {
			LOG.error(ERROR, e);
			fundsInMessage.getPayload().setFailed(true);
		}
		return fundsInMessage;
	}
	
	
	/**
	 * Transform CFX Request.
	 *
	 * @param fundsInMessage the funds in message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	private Message<MessageContext> transformCFXRequest(Message<MessageContext> fundsInMessage) throws ComplianceException {

		try {
			transformFundsInCommonRequest(fundsInMessage);
		} catch (ComplianceException cp) {
			throw cp;
		} catch (Exception e) {
			throw e;
		}
		return fundsInMessage;
	}
	
	/**
	 * Transform PFX Request.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	private Message<MessageContext> transformPFXRequest(Message<MessageContext> message) throws ComplianceException {

		try {
			transformFundsInCommonRequest(message);
		} catch (ComplianceException cp) {
			throw cp;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	
	/**
	 * Transform funds in common request.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> transformFundsInCommonRequest(Message<MessageContext> message) throws ComplianceException {
		
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) messageExchange.getRequest();
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			String orgID = StringUtils.leftPadWithZero(3, fundsInRequest.getOrgId());
			String sanctionDebtorCategory = getDebtorSanctionCategory();
			
			/**Create Request For Debtor*/
			SanctionRequest sanctionRequest = createSanctionRequestForDebtor(fundsInRequest,sanctionDebtorCategory);
			sanctionRequest.setCustomerNumber(fundsInRequest.getTradeAccountNumber());
			
			/**Create Service Default Response*/
			SanctionResponse fResponse = createServiceDeafaultResponse(fundsInRequest);
			
			/**Set Sanction Default Status for Contact*/
			SanctionContactResponse defaultResponse = new SanctionContactResponse();
			ServiceStatus defaultStatus = setSanctionContactDefaultStatus(fundsInRequest, defaultResponse);
			
			/**Create Sanction Summary Contact*/
			SanctionSummary summary = createSanctionSummary(fundsInRequest, orgID, defaultStatus);
			
			MessageExchange ccExchange = createMessageExchange(sanctionRequest, fResponse, ServiceTypeEnum.SANCTION_SERVICE);
			
			ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.SANCTION_SERVICE,
					ServiceProviderEnum.SANCTION_SERVICE, fundsInRequest.getFundsInId(), fundsInRequest.getVersion(),
					EntityEnum.DEBTOR.name(), token.getUserID(),defaultResponse,summary, defaultStatus));
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (ComplianceException cp) {
			throw cp;
		}
		catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	/**
	 * Bussiness :-
	 * 1.If ThirdPartyPayment is true then Sanction check on Debtor is performed
	 * 2.When we perform sanction on Debtor always call SLMultiLookUp
	 * Implementation :-
	 * 1.If isSanctionEligible is true then Sanction Check on Debtor is performed
	 * 2.Create sanctionId for debtor and set IsExisting flag to false because of which SLMultiLookUp gets call
	 *
	 * @param fundsInCreateRequest the funds in create request
	 * @param category the category
	 * @return the sanction request
	 * @throws ComplianceException the compliance exception
	 */
	private SanctionRequest createSanctionRequestForDebtor(FundsInCreateRequest fundsInCreateRequest, String category)
			throws ComplianceException {
		SanctionRequest sanctionRequest = new SanctionRequest();
		String orgID = StringUtils.leftPadWithZero(3, fundsInCreateRequest.getOrgId());
		List<SanctionContactRequest> debtorList = new ArrayList<>();
		FundsInTrade trade = fundsInCreateRequest.getTrade();

		if (fundsInCreateRequest.isSanctionEligible()) {
			SanctionContactRequest sanctionDebtor = new SanctionContactRequest();
			if(null!=fundsInCreateRequest.getTrade().getCountryOfFund()){
			sanctionDebtor.setCountry(
					null != countryCache.getCountryFullName(fundsInCreateRequest.getTrade().getCountryOfFund())
							? countryCache.getCountryFullName(fundsInCreateRequest.getTrade().getCountryOfFund()) : "");
			}
			sanctionDebtor.setFullName(trade.getDebtorName());
			String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_DEBTOR, fundsInCreateRequest.getFundsInId()+"");
			sanctionDebtor.setSanctionId(providerRef);
			sanctionDebtor.setContactId(fundsInCreateRequest.getFundsInId());
			sanctionDebtor.setIsExisting(Boolean.FALSE);
			sanctionDebtor.setCategory(category);
			debtorList.add(sanctionDebtor);
		}
		sanctionRequest.setContactrequests(debtorList);
		return sanctionRequest;
	}

	/**
	 * Creates the service deafault response.
	 *
	 * @param fundsInCreateRequest the funds in create request
	 * @return the sanction response
	 */
	private SanctionResponse createServiceDeafaultResponse(FundsInCreateRequest fundsInCreateRequest) {
		SanctionResponse fResponse = new SanctionResponse();
	    Boolean isDebetorFieldsMissing = (Boolean) fundsInCreateRequest.getAdditionalAttribute(Constants.DEBETOR_NAME_MISSING);
		List<SanctionContactResponse> contactResponses = new ArrayList<>();
		String orgID = StringUtils.leftPadWithZero(3, fundsInCreateRequest.getOrgId());
		SanctionContactResponse response = new SanctionContactResponse();
		response.setContactId(fundsInCreateRequest.getFundsInId()); // we use contactId in FundsInRuleService to check SanctionStatus for that contact.
		String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_DEBTOR, fundsInCreateRequest.getFundsInId()+"");
		if(fundsInCreateRequest.isSanctionEligible() || (isDebetorFieldsMissing != null && isDebetorFieldsMissing ) ){
			response.setStatus(ServiceStatus.NOT_PERFORMED.name());	
			response.setOfacList(Constants.NOT_AVAILABLE);
			response.setWorldCheck(Constants.NOT_AVAILABLE);
			response.setSanctionId(providerRef);
		}else{
			/**Set status as NOT_REQUIRED to OfacList,WorldCheck,SanctionId - Saylee*/
			response.setStatus(ServiceStatus.NOT_REQUIRED.name());
			response.setOfacList(Constants.NOT_AVAILABLE);
			response.setWorldCheck(Constants.NOT_AVAILABLE);
			response.setSanctionId(providerRef);
		}
		contactResponses.add(response);
		fResponse.setContactResponses(contactResponses);
		return fResponse;
	}
	
	/**
	 * Sets the sanction contact default status.
	 *
	 * @param fundsInRequest the funds in request
	 * @param defaultResponse the default response
	 * @return the service status
	 */
	private ServiceStatus setSanctionContactDefaultStatus(FundsInCreateRequest fundsInRequest,
			SanctionContactResponse defaultResponse) {
		Boolean performOtherChecks = (Boolean)fundsInRequest.getAdditionalAttribute(Constants.PERFORM_CHECKS);
		Boolean isDebetorFieldsMissing = (Boolean) fundsInRequest.getAdditionalAttribute(Constants.DEBETOR_FIELDS_MISSING);
		ServiceStatus defaultStatus;
		if ((Boolean.TRUE.equals(fundsInRequest.isSanctionEligible()) 
				|| (isDebetorFieldsMissing != null && Boolean.TRUE.equals(isDebetorFieldsMissing) )) 
				&& Boolean.TRUE.equals(performOtherChecks)){
			defaultStatus = ServiceStatus.NOT_PERFORMED;
		} else{
			defaultStatus = ServiceStatus.NOT_REQUIRED;
		}
		defaultResponse.setStatus(defaultStatus.name());
		return defaultStatus;
	}


	/**
	 * Creates the sanction summary.
	 *
	 * @param fundsInCreateRequest the funds in create request
	 * @param orgID the org ID
	 * @param defaultStatus the default status
	 * @return the sanction summary
	 */
	private SanctionSummary createSanctionSummary(FundsInCreateRequest fundsInCreateRequest, String orgID, ServiceStatus defaultStatus) {
		SanctionSummary summary=new SanctionSummary();
		summary.setStatus(defaultStatus.name());
		summary.setOfacList(Constants.NOT_AVAILABLE);
		summary.setWorldCheck(Constants.NOT_AVAILABLE);
		String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_DEBTOR, fundsInCreateRequest.getFundsInId()+"");
		summary.setSanctionId(providerRef);
		return summary;
	}


	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> fundsInMessage) {
		try{
			MessageExchange messageExchange = fundsInMessage.getPayload().getGatewayMessageExchange();
			FundsInCreateRequest fundsInCreateRequest = (FundsInCreateRequest) messageExchange.getRequest();
			Account fundsInAccount = (Account)fundsInCreateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			String fundsInCustType = fundsInAccount.getCustType();
			if (fundsInCustType!= null){
				fundsInCustType = fundsInCustType.trim();
			}
			if (Constants.CFX.equalsIgnoreCase(fundsInCustType)) {
				transformFundsInCFXResponse(fundsInMessage);
			} else if (Constants.PFX.equalsIgnoreCase(fundsInCustType)){
				transformFundsInPFXResponse(fundsInMessage);
			}
		} catch(Exception e){
			LOG.error("Exception in transformResponse() of Sanction Transformer in FundsIn Flow ", e);
			fundsInMessage.getPayload().setFailed(true);
		}
	return fundsInMessage;
		
	}

	/**
	 * Transform CFX response.
	 *
	 * @param message the message
	 * @return the message
	 */
	private Message<MessageContext> transformFundsInCFXResponse(Message<MessageContext> message) {
		
		try {
			transformFundsInCommonResponse(message);
		} catch (Exception e) {
			LOG.debug(Constants.ERROR, e);
		}
		return message;
	}
	
	/**
	 * Transform PFX response.
	 *
	 * @param message the message
	 * @return the message
	 */
	private Message<MessageContext> transformFundsInPFXResponse(Message<MessageContext> message) {
		
		try {
			transformFundsInCommonResponse(message);
		} catch (Exception exception) {
			LOG.debug(Constants.ERROR, exception);
		}
		return message;
	}
	
	/**
	 * Transform Common Response.
	 *
	 * @param message the message
	 * @return the message
	 */
	private Message<MessageContext> transformFundsInCommonResponse(Message<MessageContext> message) {
		//setting service name forcefully  
		MessageExchange exchange = null;
		SanctionRequest sanctionRequest = null;
		try{
			message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.SANCTION_SERVICE);
			exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			FundsInCreateRequest fundsInCreateRequest = message.getPayload().getGatewayMessageExchange().getRequest(FundsInCreateRequest.class);
			SanctionResponse fResponse = (SanctionResponse) exchange.getResponse();
			sanctionRequest = exchange.getRequest(SanctionRequest.class);
			
			/**When micro Service is down then this condition get execute*/
			if (fResponse == null) {
				createDefaultFailureResponse(exchange, sanctionRequest, fundsInCreateRequest);
				return message;
			}
			EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
					EntityEnum.DEBTOR.name(), fResponse.getContactResponses().get(0).getContactId());
			if (fResponse.getErrorCode() == null) {
				handleValidSanctionResponse(eventServiceLog, fResponse);
			} 
			else {
				handleErrorSanctionResponse(message, fResponse);
			}
			
		}catch (Exception e) {
			LOG.debug(Constants.ERROR, e);
		}
		return message;
	}

	
	/**
	 * Create SanctionSummary for Debtor
	 *  and update that entry into EventServiceLog.
	 *  Changes made by- Saylee
	 *
	 * @param log the log
	 * @param fResponse the f response
	 */
	private void handleValidSanctionResponse(EventServiceLog log, SanctionResponse fResponse) {
		List<SanctionContactResponse> contactList = fResponse.getContactResponses();
		SanctionContactResponse contactResponse = contactList.get(0);
		try {
			SanctionSummary contactSummary = createSanctionSummary(contactResponse.getOfacList(),
					contactResponse.getWorldCheck(), contactResponse.getSanctionId(), contactResponse.getStatus());
			contactSummary.setProviderName(contactResponse.getProviderName());
			contactSummary.setProviderMethod(contactResponse.getProviderMethod());
			updateResponseInEventLog(log, JsonConverterUtil.convertToJsonWithoutNull(contactResponse),
					contactResponse.getStatus(), contactSummary);
		} catch (Exception e) {
			LOG.error("Error in reading Sanction response", e);
			log.setStatus(ServiceStatus.FAIL.name());
			log.setSummary("Unable to parse response");
			log.setProviderResponse("Unable to parse response");
			log.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		}
	}
	
	/**
	 * Handle error sanction response.
	 *
	 * @param message the message
	 * @param fResponse the f response
	 */
	private void handleErrorSanctionResponse(Message<MessageContext> message, SanctionResponse fResponse) {
		FundsInCreateRequest request = (FundsInCreateRequest) message.getPayload().getGatewayMessageExchange().getRequest();
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		
		List<SanctionContactResponse> contactResponses = new ArrayList<>();
		SanctionContactResponse response = new SanctionContactResponse();
		response.setContactId(request.getFundsInId());
		for (SanctionContactResponse cont : fResponse.getContactResponses()) {
			if (cont.getErrorCode()
					.equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_REQUEST.getErrorCode())
					|| cont.getErrorCode()
							.equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_RESPONSE.getErrorCode())
					|| cont.getErrorCode().equals(ExternalErrorCodes.SANCTION_VALIDATION_EXCEPTION.getErrorCode())) {
				response.setStatus(ServiceStatus.NOT_PERFORMED.name());
			} else {
				response.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			}
			contactResponses.add(response);
			
			EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
					EntityEnum.DEBTOR.name(), cont.getContactId());
			eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(fResponse));
			SanctionSummary sanctionSummary = new SanctionSummary();
			sanctionSummary.setSanctionId(Constants.NOT_AVAILABLE);
			sanctionSummary.setWorldCheck(Constants.NOT_AVAILABLE);
			sanctionSummary.setOfacList(Constants.NOT_AVAILABLE);
			sanctionSummary.setStatus(response.getStatus());
			eventServiceLog.setStatus(response.getStatus());
			eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(sanctionSummary));
		}
		fResponse.setContactResponses(contactResponses);
	}
	
	/**
	 * Added SanctionId for Debtor while creating DefaultResponseStatus
	 * Changes made by-Saylee.
	 *
	 * @param exchange the exchange
	 * @param sanctionRequest the sanction request
	 * @param fundsInCreateRequest the funds in create request
	 */
	private void createDefaultFailureResponse(MessageExchange exchange, SanctionRequest sanctionRequest,
			FundsInCreateRequest fundsInCreateRequest) {
		SanctionResponse fResponse;
		fResponse = new SanctionResponse();
		 List<SanctionContactResponse> contactResponsesList = new ArrayList<>();
		 String orgID = StringUtils.leftPadWithZero(3, fundsInCreateRequest.getOrgId());
		if (sanctionRequest.getContactrequests() != null) {
			for (SanctionContactRequest contact : sanctionRequest.getContactrequests()) {
				String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_DEBTOR, fundsInCreateRequest.getFundsInId()+"");
				SanctionContactResponse contactResponse=new SanctionContactResponse();
				contactResponse.setContactId(contact.getContactId());
				contactResponse.setSanctionId(providerRef);
				contactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				contactResponse.setOfacList(Constants.NOT_AVAILABLE);
				contactResponse.setWorldCheck(Constants.NOT_AVAILABLE);
				contactResponsesList.add(contactResponse);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
						EntityEnum.DEBTOR.name(), contact.getContactId());
				if(eventServiceLog !=null)
					createEventServiceLogForServiceFailureWithSanctionID(eventServiceLog, contactResponse,providerRef);
			}
		}
		fResponse.setContactResponses(contactResponsesList);
		exchange.setResponse(fResponse);
	}
	
	
}



