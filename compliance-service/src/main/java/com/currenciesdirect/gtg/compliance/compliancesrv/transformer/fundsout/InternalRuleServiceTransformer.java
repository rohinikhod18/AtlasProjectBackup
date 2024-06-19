/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsout;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;


import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayRefSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayrefContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CardFraudScoreResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutPaymentReferenceResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.GlobalCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalRuleSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.PaymentReferenceResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractInternalRulesTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * The Class InternalRuleServiceTransformer.
 *
 * @author manish
 */
public class InternalRuleServiceTransformer extends AbstractInternalRulesTransformer {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InternalRuleServiceTransformer.class);

	/** The Constant NOT_AVAILABLE. */
	private static final String NOT_AVAILABLE = "Not Available";
	
	/** The country cache. */
	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#transformRequest(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutRequest fundsOutRequest = (FundsOutRequest) messageExchange.getRequest();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

			Account account = (Account) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);

			InternalServiceRequest internalServiceRequest = new InternalServiceRequest();
			ContactResponse defaultResponse = new ContactResponse();
			BlacklistSummary blacklistSummary = new BlacklistSummary();
			BlacklistPayRefSummary blacklistPayRefSummary = new BlacklistPayRefSummary();//Add for AT-3649
			ServiceStatus serviceStatus;
			if (fundsOutRequest.getAdditionalAttribute(Constants.PERFORM_CHECKS).equals(Boolean.TRUE)) {
				serviceStatus = ServiceStatus.NOT_PERFORMED;

				internalServiceRequest.setCorrelationID(fundsOutRequest.getCorrelationID());
				internalServiceRequest.setOrgCode(fundsOutRequest.getOrgCode());
				internalServiceRequest.setAccountSFId(account.getAccSFID());
				internalServiceRequest.setIsAllCheckPerform(Boolean.TRUE);
				List<InternalServiceRequestData> datas = new ArrayList<>();

				InternalServiceRequestData beneData = new InternalServiceRequestData();
				if (null != fundsOutRequest.getBeneficiary().getCountry()) {
					beneData.setCountry(
							null != countryCache.getCountryFullName(fundsOutRequest.getBeneficiary().getCountry())
									? countryCache.getCountryFullName(fundsOutRequest.getBeneficiary().getCountry())
									: "");
				}
				beneData.setName(fundsOutRequest.getBeneficiary().getFullName());
				beneData.setAccountNumber(fundsOutRequest.getBeneficiary().getAccountNumber());
				beneData.setBankName(fundsOutRequest.getBeneficiary().getBeneficaryBankName());
				beneData.setEntityType(EntityEnum.BENEFICIARY.name());
				beneData.setId(fundsOutRequest.getFundsOutId());
				beneData.setPayementRefernce(fundsOutRequest.getBeneficiary().getPaymentReference());//add AT-3649

				datas.add(beneData);

				internalServiceRequest.setSearchdata(datas);
				internalServiceRequest.setRequestType(Constants.PAYMENT_OUT);

			} else {
				serviceStatus = ServiceStatus.NOT_REQUIRED;

			}

			setInternalRuleServiceStatus(defaultResponse, contact, serviceStatus);
			blacklistSummary.setStatus(serviceStatus.name());
			blacklistPayRefSummary.setStatus(serviceStatus.name());
			List<Contact> contacts = new ArrayList<>(1);
			contacts.add(contact);

			MessageExchange ccExchange = createMessageExchange(internalServiceRequest,
					createInternalRuleServiceDefaultResponse(contacts), ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			ccExchange.addEventServiceLog(
					createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
							ServiceProviderEnum.BLACKLIST, fundsOutRequest.getBeneficiary().getBeneficiaryId(),
							fundsOutRequest.getVersion(), EntityEnum.BENEFICIARY.name(), token.getUserID(),
							defaultResponse.getBlacklist(), blacklistSummary, serviceStatus));

			ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.HRC_SERVICE,
					ServiceProviderEnum.COUNTRYCHECK, fundsOutRequest.getBeneficiary().getBeneficiaryId(),
					fundsOutRequest.getVersion(), EntityEnum.BENEFICIARY.name(), token.getUserID(), defaultResponse,
					defaultResponse.getCountryCheck(), serviceStatus));
			
			
			ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.BLACKLIST_PAY_REF_SERVICE,
					ServiceProviderEnum.BLACKLIST_PAY_REF, fundsOutRequest.getBeneficiary().getBeneficiaryId(),
					fundsOutRequest.getVersion(), EntityEnum.BENEFICIARY.name(), token.getUserID(), defaultResponse,
					blacklistPayRefSummary, serviceStatus));

			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.error("Exception in transformRequest()", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Creates the internal rule service default response.
	 *
	 * @param contacts the contacts
	 * @return the internal service response
	 */
	protected InternalServiceResponse createInternalRuleServiceDefaultResponse(List<Contact> contacts) {
		InternalServiceResponse response = new InternalServiceResponse();
		List<ContactResponse> responseList = new ArrayList<>();
		for (Contact contact : contacts) {
			ContactResponse contactResponse = new ContactResponse();
			GlobalCheckContactResponse globalCheckContactResponse = new GlobalCheckContactResponse();
			BlacklistContactResponse blacklistContactResponse = new BlacklistContactResponse();
			IpContactResponse ipContactResponse = new IpContactResponse();
			CountryCheckContactResponse countryCheckContactResponse = new CountryCheckContactResponse();
			CardFraudScoreResponse cardFraudScoreResponse = new CardFraudScoreResponse();
            BlacklistPayrefContactResponse blacklistpayref= new BlacklistPayrefContactResponse();
			contactResponse.setId(contact.getId());
			globalCheckContactResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			blacklistContactResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			blacklistpayref.setStatus(ServiceStatus.NOT_REQUIRED.name());//AT-3658
			ipContactResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			countryCheckContactResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			cardFraudScoreResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			contactResponse.setContactStatus(ServiceStatus.NOT_REQUIRED.name());
			contactResponse.setCardFraudCheck(cardFraudScoreResponse);
			contactResponse.setIpCheck(ipContactResponse);
			contactResponse.setCountryCheck(countryCheckContactResponse);
			contactResponse.setGlobalCheck(globalCheckContactResponse);
			contactResponse.setBlacklist(blacklistContactResponse);
			contactResponse.setBlacklistPayref(blacklistpayref);
			
			responseList.add(contactResponse);
		}
		response.setContacts(responseList);
		return response;
	}


	/**
	 * Creates the internal rule service default response for repeat check.
	 *
	 * @param contacts the contacts
	 * @return the internal service response
	 */
	protected InternalServiceResponse createInternalRuleServiceDefaultResponseForRepeatCheck(List<Contact> contacts) {
		InternalServiceResponse response = new InternalServiceResponse();
		List<ContactResponse> responseList = new ArrayList<>();
		for (Contact contact : contacts) {
			ContactResponse contactResponse = new ContactResponse();
			BlacklistContactResponse blacklistContactResponse = new BlacklistContactResponse();
			contactResponse.setId(contact.getId());
			blacklistContactResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			contactResponse.setContactStatus(ServiceStatus.NOT_REQUIRED.name());
			contactResponse.setBlacklist(blacklistContactResponse);
			responseList.add(contactResponse);
		}
		response.setContacts(responseList);
		return response;
	}
	
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		try {
			FundsOutRequest fundsOutRequest = (FundsOutRequest) message.getPayload().getGatewayMessageExchange()
					.getRequest();
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			InternalServiceRequest internalServiceRequest = exchange.getRequest(InternalServiceRequest.class);
			InternalServiceResponse internalServiceResponse = (InternalServiceResponse) exchange.getResponse();

			if (internalServiceResponse == null) {
				transformResponseForServiceFailure(internalServiceRequest, exchange, fundsOutRequest);
			} else {
				List<ContactResponse> contactResponses = internalServiceResponse.getContacts();
				for (ContactResponse response : contactResponses) {

					EventServiceLog blacklistLog = null;
					EventServiceLog countryCheckLog = null;
					InternalRuleSummary internalRuleSummary = new InternalRuleSummary();

					transformResponseForBeneficiary(fundsOutRequest, exchange, response, blacklistLog, countryCheckLog,
							internalRuleSummary);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in transformResponse()", e);
			message.getPayload().setFailed(true);
		}
		return message;

	}

	/**
	 * Transform response for beneficiary.
	 *
	 * @param fundsOutRequest the funds out request
	 * @param exchange the exchange
	 * @param response the response
	 * @param blacklistLog the blacklist log
	 * @param countryCheckLog the country check log
	 * @param internalRuleSummary the internal rule summary
	 */
	private void transformResponseForBeneficiary(FundsOutRequest fundsOutRequest, MessageExchange exchange,
			ContactResponse response, EventServiceLog blacklistLog, EventServiceLog countryCheckLog,
			InternalRuleSummary internalRuleSummary) {
		Integer logId;
		EventServiceLog blacklistPayRefLog = null;
		try {

			if (EntityEnum.BENEFICIARY.name().equals(response.getEntityType())) {
				logId = fundsOutRequest.getBeneficiary().getBeneficiaryId();

				blacklistLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
						EntityEnum.BENEFICIARY.name(), logId);
				countryCheckLog = exchange.getEventServiceLog(ServiceTypeEnum.HRC_SERVICE,
						EntityEnum.BENEFICIARY.name(), logId);
				//Add for AT-3649
				blacklistPayRefLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACKLIST_PAY_REF_SERVICE,
						EntityEnum.BENEFICIARY.name(), logId);


				updateEventServiceLogEntry(blacklistLog, createBlacklistSummary(response), response.getBlacklist(),
						response.getBlacklist().getStatus());
				updateEventServiceLogEntry(countryCheckLog, response.getCountryCheck(), response.getCountryCheck(),
						response.getCountryCheck().getStatus());
				updateEventServiceLogEntry(blacklistPayRefLog, createBlacklistPayRefSummary(response), response.getBlacklistPayref(),
						response.getBlacklistPayref().getStatus());
				
				internalRuleSummary.setBlacklistSummary(createBlacklistSummary(response));
				internalRuleSummary.setHrcSummary(response.getCountryCheck());
				internalRuleSummary.setBlacklistPayRefSummary(createBlacklistPayRefSummary(response));
			}
		} catch (Exception e) {
			LOGGER.error("Error in reposne mapping", e);
			updateEventServiceLogEntry(blacklistLog, null, null, response.getBlacklist().getStatus());
			updateEventServiceLogEntry(countryCheckLog, null, null, response.getCountryCheck().getStatus());
			updateEventServiceLogEntry(blacklistPayRefLog, null, null, response.getBlacklistPayref().getStatus());
		}
	}

	/**
	 * Transform response for service failure.
	 *
	 * @param internalServiceRequest the internal service request
	 * @param exchange the exchange
	 * @param fundsOutRequest the funds out request
	 */
	private void transformResponseForServiceFailure(InternalServiceRequest internalServiceRequest,
			MessageExchange exchange, FundsOutRequest fundsOutRequest) {
		InternalServiceResponse internalServiceResponse = new InternalServiceResponse();
		internalServiceResponse.setStatus(ServiceStatus.FAIL.name());
		List<ContactResponse> contactResponseList = new ArrayList<>();

		try {
			for (InternalServiceRequestData contact : internalServiceRequest.getSearchdata()) {

				ContactResponse response = new ContactResponse();
				BlacklistContactResponse blacklist = new BlacklistContactResponse();
				CountryCheckContactResponse countryCheck = new CountryCheckContactResponse();
				BlacklistPayrefContactResponse blacklistPayrefContactResponse = new BlacklistPayrefContactResponse();
				BlacklistSummary blacklistSummary = new BlacklistSummary();
				BlacklistPayRefSummary blacklistPayRefSummary = new BlacklistPayRefSummary();

				response.setBlacklist(blacklist);
				blacklist.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				blacklist.setData(null);
				blacklistSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				blacklistSummary.setAccountNumber(NOT_AVAILABLE);
				blacklistSummary.setBankName(NOT_AVAILABLE);
				blacklistSummary.setName(NOT_AVAILABLE);
				countryCheck.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				response.setBlacklist(blacklist);
				response.setCountryCheck(countryCheck);
				blacklistPayrefContactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				blacklistPayRefSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				response.setBlacklistPayref(blacklistPayrefContactResponse);
				response.setId(contact.getId());
				response.setEntityType(contact.getEntityType());
				Integer logId = fundsOutRequest.getBeneficiary().getBeneficiaryId();

				EventServiceLog blacklistLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
						EntityEnum.BENEFICIARY.name(), logId);
				EventServiceLog countryCheckLog = exchange.getEventServiceLog(ServiceTypeEnum.HRC_SERVICE,
						EntityEnum.BENEFICIARY.name(), logId);
				//Add for AT-3649
				EventServiceLog blacklistPayRefLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACKLIST_PAY_REF_SERVICE,
						EntityEnum.BENEFICIARY.name(), logId);

				updateEventServiceLogEntry(blacklistLog, blacklistSummary, response.getBlacklist(),
						ServiceStatus.SERVICE_FAILURE.name());

				updateEventServiceLogEntry(countryCheckLog, response.getCountryCheck(), response.getCountryCheck(),
						ServiceStatus.SERVICE_FAILURE.name());
				
				updateEventServiceLogEntry(blacklistPayRefLog, blacklistPayRefSummary, response.getBlacklistPayref(),
						ServiceStatus.SERVICE_FAILURE.name());

				contactResponseList.add(response);
			}
			internalServiceResponse.setContacts(contactResponseList);
			exchange.setResponse(internalServiceResponse);

		} catch (Exception e) {
			LOGGER.error("{1}",e);
    	}
	}

	/**
	 * Sets the internal rule service status.
	 *
	 * @param defaultResponse the default response
	 * @param contact the contact
	 * @param serviceStatus the service status
	 * @return the contact response
	 */
	private ContactResponse setInternalRuleServiceStatus(ContactResponse defaultResponse, Contact contact,
			ServiceStatus serviceStatus) {

		GlobalCheckContactResponse globalCheck = new GlobalCheckContactResponse();
		BlacklistContactResponse blacklist = new BlacklistContactResponse();
		IpContactResponse ipCheck = new IpContactResponse();
		CountryCheckContactResponse countryCheck = new CountryCheckContactResponse();
		IpSummary defaultIpSummary = new IpSummary();
		BlacklistPayrefContactResponse blacklistPayref=new BlacklistPayrefContactResponse();
		blacklistPayref.setStatus(serviceStatus.name());
		blacklist.setStatus(serviceStatus.name());
		blacklist.setData(null);
		countryCheck.setStatus(serviceStatus.name());
		globalCheck.setStatus(serviceStatus.name());
		globalCheck.setCountry(contact.getCountry());
		countryCheck.setCountry(contact.getCountry());
		ipCheck.setStatus(serviceStatus.name());
		ipCheck.setIpAddress(contact.getIpAddress());
		defaultIpSummary.setStatus(serviceStatus.name());
		defaultIpSummary.setIpAddress(contact.getIpAddress());
		defaultResponse.setBlacklist(blacklist);
		defaultResponse.setGlobalCheck(globalCheck);
		defaultResponse.setCountryCheck(countryCheck);
		defaultResponse.setBlacklistPayref(blacklistPayref);
		return defaultResponse;
	}
	
	
	/**
	 * Sets the internal rule service status for blacklist repeat check.
	 *
	 * @param defaultResponse the default response
	 * @param contact the contact
	 * @param serviceStatus the service status
	 * @return the contact response
	 */
	private ContactResponse setInternalRuleServiceStatusForBlacklistRepeatCheck(ContactResponse defaultResponse, ServiceStatus serviceStatus) {
		BlacklistContactResponse blacklist = new BlacklistContactResponse();
		blacklist.setStatus(serviceStatus.name());
		blacklist.setData(null);
		defaultResponse.setBlacklist(blacklist);
		return defaultResponse;
	}
	
	/**
	 * This method is created for blacklist repeat check.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> transformBlacklistResendRequest(Message<MessageContext> message) {
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutBlacklistResendRequest fbResend = messageExchange.getRequest(FundsOutBlacklistResendRequest.class);
			FundsOutRequest fundsOutRequest =(FundsOutRequest)fbResend.getAdditionalAttribute(Constants.OLD_REQUEST);
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

			Account account = (Account) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);

			InternalServiceRequest internalServiceRequest = new InternalServiceRequest();
			ContactResponse defaultResponse = new ContactResponse();
			BlacklistSummary blacklistSummary = new BlacklistSummary();
			ServiceStatus serviceStatus;
		    serviceStatus = ServiceStatus.NOT_PERFORMED;

				internalServiceRequest.setCorrelationID(fundsOutRequest.getCorrelationID());
				internalServiceRequest.setOrgCode(fundsOutRequest.getOrgCode());
				internalServiceRequest.setAccountSFId(account.getAccSFID());
				internalServiceRequest.setIsAllCheckPerform(Boolean.TRUE);
				List<InternalServiceRequestData> datas = new ArrayList<>();

				InternalServiceRequestData beneData = new InternalServiceRequestData();
				beneData.setName(fundsOutRequest.getBeneficiary().getFullName());
				beneData.setAccountNumber(fundsOutRequest.getBeneficiary().getAccountNumber());
				beneData.setBankName(fundsOutRequest.getBeneficiary().getBeneficaryBankName());
				beneData.setEntityType(EntityEnum.BENEFICIARY.name());
				beneData.setId(fundsOutRequest.getFundsOutId());

		   datas.add(beneData);
           internalServiceRequest.setSearchdata(datas);
		   internalServiceRequest.setRequestType(Constants.PAYMENT_OUT);
			setInternalRuleServiceStatusForBlacklistRepeatCheck(defaultResponse, serviceStatus);
			blacklistSummary.setStatus(serviceStatus.name());
			List<Contact> contacts = new ArrayList<>(1);
			contacts.add(contact);

			MessageExchange ccExchange = createMessageExchange(internalServiceRequest,
					createInternalRuleServiceDefaultResponseForRepeatCheck(contacts), ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			ccExchange.addEventServiceLog(
					createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
							ServiceProviderEnum.BLACKLIST, fundsOutRequest.getBeneficiary().getBeneficiaryId(),
							fundsOutRequest.getVersion(), EntityEnum.BENEFICIARY.name(), token.getUserID(),
							defaultResponse.getBlacklist(), blacklistSummary, serviceStatus));
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.error("Exception in transformBlacklistResendRequest()", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
	
	/**
	 * Transform blacklist resend response.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> transformBlacklistResendResponse(Message<MessageContext> message) {
		try {
			
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutBlacklistResendRequest fbResend = messageExchange.getRequest(FundsOutBlacklistResendRequest.class);
			FundsOutRequest fundsOutRequest =(FundsOutRequest)fbResend.getAdditionalAttribute(Constants.OLD_REQUEST);
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			InternalServiceRequest internalServiceRequest = exchange.getRequest(InternalServiceRequest.class);
			InternalServiceResponse internalServiceResponse = (InternalServiceResponse) exchange.getResponse();
            Integer logId;
			if (internalServiceResponse == null) {
				transformResponseBlacklistRepeatCheckForForServiceFailure(internalServiceRequest, exchange,fundsOutRequest);
			} else {
				List<ContactResponse> contactResponses = internalServiceResponse.getContacts();
				for (ContactResponse response : contactResponses) {

					EventServiceLog blacklistLog = null;
					BlacklistResendResponse fResendResponse = new BlacklistResendResponse();

					if (EntityEnum.BENEFICIARY.name().equals(response.getEntityType())) {
						logId = fundsOutRequest.getBeneficiary().getBeneficiaryId();

						blacklistLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
								EntityEnum.BENEFICIARY.name(), logId);
						
                        updateEventServiceLogEntry(blacklistLog, createBlacklistSummary(response), response.getBlacklist(),
								response.getBlacklist().getStatus());
                         fResendResponse.setSummary(createBlacklistSummary(response));	
					}		
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in transformBlacklistResendResponse()", e);
			message.getPayload().setFailed(true);
		}
		return message;

	}
	
	/**
	 * Transform response blacklist repeat check for for service failure.
	 *
	 * @param internalServiceRequest the internal service request
	 * @param exchange the exchange
	 * @param fundsOutRequest the funds out request
	 */
	private void transformResponseBlacklistRepeatCheckForForServiceFailure(InternalServiceRequest internalServiceRequest,
			MessageExchange exchange,FundsOutRequest fundsOutRequest) {
		InternalServiceResponse internalServiceResponse = new InternalServiceResponse();
		internalServiceResponse.setStatus(ServiceStatus.FAIL.name());
		List<ContactResponse> contactResponseList = new ArrayList<>();

		try {
			for (InternalServiceRequestData contact : internalServiceRequest.getSearchdata()) {

				ContactResponse response = new ContactResponse();
				BlacklistContactResponse blacklist = new BlacklistContactResponse();
				
				BlacklistSummary blacklistSummary = new BlacklistSummary();

				response.setBlacklist(blacklist);
				blacklist.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				blacklist.setData(null);
				blacklistSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				blacklistSummary.setAccountNumber(NOT_AVAILABLE);
				blacklistSummary.setName(NOT_AVAILABLE);
				blacklistSummary.setBankName(NOT_AVAILABLE);
				response.setBlacklist(blacklist);
				response.setContactStatus(ServiceStatus.SERVICE_FAILURE.name());
				response.setId(contact.getId());
				response.setEntityType(contact.getEntityType());
				Integer logId = fundsOutRequest.getBeneficiary().getBeneficiaryId();

				EventServiceLog blacklistLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
						EntityEnum.BENEFICIARY.name(), logId);
				updateEventServiceLogEntry(blacklistLog, blacklistSummary, response.getBlacklist(),
						ServiceStatus.SERVICE_FAILURE.name());
				contactResponseList.add(response);
			}
			internalServiceResponse.setContacts(contactResponseList);
			exchange.setResponse(internalServiceResponse);

		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
	}

	//Added for AT-3658
	/**
	 * This method is created for payment reference repeat check.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> transformPaymentReferenceResendRequest(Message<MessageContext> message) {
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutPaymentReferenceResendRequest fbResend = messageExchange.getRequest(FundsOutPaymentReferenceResendRequest.class);
			FundsOutRequest fundsOutRequest =(FundsOutRequest)fbResend.getAdditionalAttribute(Constants.OLD_REQUEST);
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

			Account account = (Account) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);

			InternalServiceRequest internalServiceRequest = new InternalServiceRequest();
			ContactResponse defaultResponse = new ContactResponse();
			BlacklistPayRefSummary paymentReferenceSummary = new BlacklistPayRefSummary();
			ServiceStatus serviceStatus;
		    serviceStatus = ServiceStatus.NOT_PERFORMED;

				internalServiceRequest.setCorrelationID(fundsOutRequest.getCorrelationID());
				internalServiceRequest.setOrgCode(fundsOutRequest.getOrgCode());
				internalServiceRequest.setAccountSFId(account.getAccSFID());
				internalServiceRequest.setIsAllCheckPerform(Boolean.TRUE);
				List<InternalServiceRequestData> datas = new ArrayList<>();

				InternalServiceRequestData beneData = new InternalServiceRequestData();
				beneData.setEntityType(EntityEnum.BENEFICIARY.name());
				beneData.setId(fundsOutRequest.getFundsOutId());
				beneData.setPayementRefernce(fundsOutRequest.getBeneficiary().getPaymentReference());

		   datas.add(beneData);
           internalServiceRequest.setSearchdata(datas);
		   internalServiceRequest.setRequestType(Constants.PAYMENT_OUT_PAY_REF);
		   setInternalRuleServiceStatusForPaymentReferenceRepeatCheck(defaultResponse, serviceStatus);
		   paymentReferenceSummary.setStatus(serviceStatus.name());
			List<Contact> contacts = new ArrayList<>(1);
			contacts.add(contact);

			MessageExchange ccExchange = createMessageExchange(internalServiceRequest,
					createInternalRuleServiceDefaultResponseForPaymentReferenceRepeatCheck(contacts), ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			ccExchange.addEventServiceLog(
					createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.BLACKLIST_PAY_REF_SERVICE,
							ServiceProviderEnum.BLACKLIST_PAY_REF, fundsOutRequest.getBeneficiary().getBeneficiaryId(),
							fundsOutRequest.getVersion(), EntityEnum.BENEFICIARY.name(), token.getUserID(),
							defaultResponse.getBlacklistPayref(), paymentReferenceSummary, serviceStatus));
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.error("Exception in transformPaymentReferenceResendRequest()", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
	/**
	 * Sets the internal rule service status for payment reference repeat check.
	 *
	 * @param defaultResponse the default response
	 * @param contact the contact
	 * @param serviceStatus the service status
	 * @return the contact response
	 */
	private ContactResponse setInternalRuleServiceStatusForPaymentReferenceRepeatCheck(ContactResponse defaultResponse, ServiceStatus serviceStatus) {
		BlacklistPayrefContactResponse blacklistPayref = new BlacklistPayrefContactResponse();
		blacklistPayref.setStatus(serviceStatus.name());
		defaultResponse.setBlacklistPayref(blacklistPayref);;
		return defaultResponse;
	}
	
	/**
	 * Creates the internal rule service default response for repeat check.
	 *
	 * @param contacts the contacts
	 * @return the internal service response
	 */
	protected InternalServiceResponse createInternalRuleServiceDefaultResponseForPaymentReferenceRepeatCheck(List<Contact> contacts) {
		InternalServiceResponse response = new InternalServiceResponse();
		List<ContactResponse> responseList = new ArrayList<>();
		for (Contact contact : contacts) {
			ContactResponse contactResponse = new ContactResponse();
			BlacklistPayrefContactResponse blacklistPayref = new BlacklistPayrefContactResponse();
			contactResponse.setId(contact.getId());
			blacklistPayref.setStatus(ServiceStatus.NOT_REQUIRED.name());
			contactResponse.setContactStatus(ServiceStatus.NOT_REQUIRED.name());
			contactResponse.setBlacklistPayref(blacklistPayref);;
			responseList.add(contactResponse);
		}
		response.setContacts(responseList);
		return response;
	}
	
	/**
	 * Transform payment reference resend response.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> transformPaymentReferenceResendResponse(Message<MessageContext> message) {
		try {
			
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutPaymentReferenceResendRequest fbResend = messageExchange.getRequest(FundsOutPaymentReferenceResendRequest.class);
			FundsOutRequest fundsOutRequest =(FundsOutRequest)fbResend.getAdditionalAttribute(Constants.OLD_REQUEST);
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			InternalServiceRequest internalServiceRequest = exchange.getRequest(InternalServiceRequest.class);
			InternalServiceResponse internalServiceResponse = (InternalServiceResponse) exchange.getResponse();
            Integer logId;
			if (internalServiceResponse == null) {
				transformResponsePaymentReferenceRepeatCheckForForServiceFailure(internalServiceRequest, exchange,fundsOutRequest);
			} else {
				List<ContactResponse> contactResponses = internalServiceResponse.getContacts();
				for (ContactResponse response : contactResponses) {

					EventServiceLog paymentReferenceLog = null;
					PaymentReferenceResendResponse fResendResponse = new PaymentReferenceResendResponse();

					if (EntityEnum.BENEFICIARY.name().equals(response.getEntityType())) {
						logId = fundsOutRequest.getBeneficiary().getBeneficiaryId();

						paymentReferenceLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACKLIST_PAY_REF_SERVICE,
								EntityEnum.BENEFICIARY.name(), logId);
						
                        updateEventServiceLogEntry(paymentReferenceLog, createBlacklistPayRefSummary(response), response.getBlacklistPayref(),
								response.getBlacklistPayref().getStatus());
                         fResendResponse.setSummary(createBlacklistPayRefSummary(response));	
					}		
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in transformPaymentReferenceResendResponse()", e);
			message.getPayload().setFailed(true);
		}
		return message;

	}
	
	/**
	 * Transform response payment reference repeat check for for service failure.
	 *
	 * @param internalServiceRequest the internal service request
	 * @param exchange the exchange
	 * @param fundsOutRequest the funds out request
	 */
	private void transformResponsePaymentReferenceRepeatCheckForForServiceFailure(InternalServiceRequest internalServiceRequest,
			MessageExchange exchange,FundsOutRequest fundsOutRequest) {
		InternalServiceResponse internalServiceResponse = new InternalServiceResponse();
		internalServiceResponse.setStatus(ServiceStatus.FAIL.name());
		List<ContactResponse> contactResponseList = new ArrayList<>();

		try {
			for (InternalServiceRequestData contact : internalServiceRequest.getSearchdata()) {

				ContactResponse response = new ContactResponse();
				BlacklistPayrefContactResponse paymentRef = new BlacklistPayrefContactResponse();
				
				BlacklistPayRefSummary paymentReferenceSummary = new BlacklistPayRefSummary();

				response.setBlacklistPayref(paymentRef);;
				paymentRef.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				paymentReferenceSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				response.setBlacklistPayref(paymentRef);
				response.setContactStatus(ServiceStatus.SERVICE_FAILURE.name());
				response.setId(contact.getId());
				response.setEntityType(contact.getEntityType());
				Integer logId = fundsOutRequest.getBeneficiary().getBeneficiaryId();

				EventServiceLog blacklistLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACKLIST_PAY_REF_SERVICE,
						EntityEnum.BENEFICIARY.name(), logId);
				updateEventServiceLogEntry(blacklistLog, paymentReferenceSummary, response.getBlacklistPayref(),
						ServiceStatus.SERVICE_FAILURE.name());
				contactResponseList.add(response);
			}
			internalServiceResponse.setContacts(contactResponseList);
			exchange.setResponse(internalServiceResponse);

		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
	}
	
		
}
