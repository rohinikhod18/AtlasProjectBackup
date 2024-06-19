/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.reg;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.GlobalCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalRuleSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpSummary;
import com.currenciesdirect.gtg.compliance.commons.enums.LegalEntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Company;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractInternalRulesTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * @author manish
 *
 */
/**
 * The Class InternalRuleServiceTransformer.
 * 
 * Responsibility :
 * 
 * This is Registration Class used to 1) This method is called from
 * Registration-flow xml 2) Transform RegistrationService Request which comes
 * from Registration system(I.e. Salesforce) into InternalServiceRequest and
 * returns 3) Also it Registration RegistrationService Response is populates to
 * eventservicelog object with ,provider response and summary and returns
 * 
 */
public class InternalRuleServiceTransformer extends AbstractInternalRulesTransformer {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InternalRuleServiceTransformer.class);

	public static final String EVENT_ID = "eventId";
	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();
	private static final String ERROR ="ERROR";

	/**
	 * Business -- Perform BLacklist check, country check, Ip check, Global
	 * check for both customerType (i.e PFX and CFX). this checks are perform
	 * for both Account and Contact. While sending InternalRuleService request,
	 * default status is assumed as NOT_PERFORMED And when response is received
	 * from HTTPClient, appropriate response is updated * When
	 * RegistrationServiceRequest is not sent, default status is set to
	 * NOT_REQUIRED
	 * 
	 * Implementation--- 1) Get the
	 * messageExchange,UserProfile,InternalServiceRequest ,contact,eventId from
	 * the message 2) eventId, contact are already populated from data enricher
	 * so that we can use the same to insert records in to the table 3) Create
	 * RegistrationService request and default RegistrationServiceResponse with
	 * not performed status so that if we get any exception from micro service
	 * this default object can be pushed to database. 4.)
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			Account account = registrationRequest.getAccount();
			if (Constants.CFX.equalsIgnoreCase(account.getCustType())) {
				transformCFXRequest(message);
			}else{
				transformPFXRequest(message);
			}
		} catch (Exception e) {
			message.getPayload().setFailed(true);
			LOGGER.error(ERROR, e);
		}
		return message;
	}

	/**
	 * @param message
	 * @return
	 * @throws Exception 
	 */
	private Message<MessageContext> transformCFXRequest(Message<MessageContext> message) throws ComplianceException {

		try {
			 MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			 OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.ADD_CONTACT==operation) {
				transformCFXAddRequest(message);
			} else if (OperationEnum.BLACKLIST_RESEND==operation){
				transformCFXResendRequest(message);
			} else {
				transformCFXSignupUpdateRequest(message);
			}
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;
	}

	private Message<MessageContext> transformCFXAddRequest(Message<MessageContext> message) throws ComplianceException {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			Account account = registrationRequest.getAccount();
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			List<Contact> contacts = account.getContacts();
			OperationEnum operation = messageExchange.getOperation();
			InternalServiceRequest internalServiceRequest = initServiceRequest(registrationRequest);
			List<InternalServiceRequestData> datas = new ArrayList<>();
			
			InternalServiceResponse response = new InternalServiceResponse();
			ContactResponse cResponse;
			MessageExchange exchange = new MessageExchange();
			exchange.setServiceTypeEnum(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			String orgLegalEntity = account.getCustLegalEntity();
			for (Contact contact : contacts) {

				String countryName = countryCache
						.getCountryFullName(null != contact.getCountry() ? contact.getCountry() : "");
				InternalServiceRequestData contactData = createContactRequest(
						registrationRequest.getSourceApplication(), contact, countryName, orgLegalEntity);
				datas.add(contactData);

				cResponse = createDefaultCFXContactResponse(countryName);
				response.addContactResponse(cResponse);
				populateAllEventServiceLogs(token, exchange, eventId, cResponse, 
						createDefaultBlacklistSummary(ServiceStatus.NOT_PERFORMED), 
						createDefaultIpSummary(ServiceStatus.NOT_REQUIRED, contact.getIpAddress()), contact);

			}

			internalServiceRequest.setOnlyBlacklistCheckPerform(Boolean.FALSE);
			internalServiceRequest.setSearchdata(datas);
			createRequestType(internalServiceRequest, account.getCustType(), operation);

			exchange.setRequest(internalServiceRequest);
			exchange.setResponse(response);
			message.getPayload().appendMessageExchange(exchange);

		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;
	}

	private Message<MessageContext> transformCFXSignupUpdateRequest(Message<MessageContext> message) throws ComplianceException {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			
			Account account = registrationRequest.getAccount();
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			List<Contact> contacts = account.getContacts();
			OperationEnum operation = messageExchange.getOperation();
			InternalServiceRequest internalServiceRequest = initServiceRequest(registrationRequest);
			List<InternalServiceRequestData> datas = new ArrayList<>();
			
			InternalServiceResponse iResponse = new InternalServiceResponse();
			ContactResponse cResponse;
			
			// START - Company/Account Blacklist check
			InternalServiceRequestData data = createCompanyCFXRequest(account, registrationRequest.getSourceApplication());
			datas.add(data);
			
			cResponse = createDefaultCFXContactResponse(null);
			iResponse.addContactResponse(cResponse);
			
			MessageExchange exchange = new MessageExchange();
			exchange.setServiceTypeEnum(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			exchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
					ServiceProviderEnum.BLACKLIST, account.getId(), account.getVersion(), EntityEnum.ACCOUNT.name(),
					token.getUserID(), cResponse, createDefaultBlacklistSummary(ServiceStatus.NOT_PERFORMED)));
			// END- Company/Account Blacklist check	
			String orgLegalEntity = account.getCustLegalEntity();
			for (Contact contact : contacts) {

				String countryName = countryCache
						.getCountryFullName(null != contact.getCountry() ? contact.getCountry() : "");
				InternalServiceRequestData contactData = createContactRequest(
						registrationRequest.getSourceApplication(), contact, countryName, orgLegalEntity);
				datas.add(contactData);

				cResponse = createDefaultCFXContactResponse(countryName);
				iResponse.addContactResponse(cResponse);
				populateAllEventServiceLogs(token, exchange, eventId, cResponse, 
						createDefaultBlacklistSummary(ServiceStatus.NOT_PERFORMED), 
						createDefaultIpSummary(ServiceStatus.NOT_REQUIRED, contact.getIpAddress()), contact);

			}

			internalServiceRequest.setOnlyBlacklistCheckPerform(Boolean.FALSE);
			internalServiceRequest.setSearchdata(datas);
			createRequestType(internalServiceRequest, account.getCustType(), operation);
			exchange.setRequest(internalServiceRequest);
			exchange.setResponse(iResponse);
			message.getPayload().appendMessageExchange(exchange);

		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;
	}

	

	/**
	 * @param message
	 * @return
	 */
	private Message<MessageContext> transformPFXRequest(Message<MessageContext> message) throws ComplianceException {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.BLACKLIST_RESEND==operation) {
				transformPFXResendRequest(message);
			} else {
				RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange
						.getRequest();
				UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

				Account account = registrationRequest.getAccount();
				Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
						.getAdditionalAttribute(EVENT_ID);
				List<Contact> contacts = account.getContacts();

				InternalServiceRequest internalServiceRequest = initServiceRequest(registrationRequest);
				List<InternalServiceRequestData> datas = new ArrayList<>();

				InternalServiceResponse iResponse = new InternalServiceResponse();
				ContactResponse cResponse;
				MessageExchange exchange = new MessageExchange();
				exchange.setServiceTypeEnum(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
				String orgLegalEntity = account.getCustLegalEntity();
				for (Contact contact : contacts) {

					String countryName = countryCache
							.getCountryFullName(null != contact.getCountry() ? contact.getCountry() : "");

					InternalServiceRequestData data = createContactRequest(registrationRequest.getSourceApplication(),
							contact, countryName, orgLegalEntity);
					datas.add(data);

					cResponse = createDefaultPFXContactResponse(countryName);
					iResponse.addContactResponse(cResponse);
					populateAllEventServiceLogs(token, exchange, eventId, cResponse,
							createDefaultBlacklistSummary(ServiceStatus.NOT_PERFORMED),
							createDefaultIpSummary(ServiceStatus.NOT_PERFORMED, contact.getIpAddress()), contact);

				}

				internalServiceRequest.setOnlyBlacklistCheckPerform(Boolean.FALSE);
				internalServiceRequest.setSearchdata(datas);
				createRequestType(internalServiceRequest, account.getCustType(), operation);
				exchange.setRequest(internalServiceRequest);
				exchange.setResponse(iResponse);
				message.getPayload().appendMessageExchange(exchange);
			}
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;
	}
	
	
	private Message<MessageContext> transformPFXResendRequest(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			OperationEnum operation = messageExchange.getOperation();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

			Account account = registrationRequest.getAccount();
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(EVENT_ID);
			List<Contact> contacts = account.getContacts();

			InternalServiceRequest internalServiceRequest = initServiceRequest(registrationRequest);
			List<InternalServiceRequestData> datas = new ArrayList<>();

			InternalServiceResponse iResponse = new InternalServiceResponse();
			ContactResponse cResponse;
			MessageExchange exchange = new MessageExchange();
			exchange.setServiceTypeEnum(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			internalServiceRequest.setOnlyBlacklistCheckPerform(Boolean.TRUE);
			String orgLegalEntity = account.getCustLegalEntity();
			if(EntityEnum.CONTACT.name().equals(registrationRequest.getAdditionalAttributes().get(Constants.ENTITY_TYPE))) {
				for (Contact contact : contacts) {

					InternalServiceRequestData data = createContactRequest(registrationRequest.getSourceApplication(),
							contact, null, orgLegalEntity);
					datas.add(data);

					cResponse = createDefaultPFXContactBlacklistResponse();
					iResponse.addContactResponse(cResponse);
					
					exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
							ServiceProviderEnum.BLACKLIST, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
							token.getUserID(), cResponse, createDefaultBlacklistSummary(ServiceStatus.NOT_PERFORMED), ServiceStatus.valueOf(cResponse.getBlacklist().getStatus())));
				}
			} else {
				InternalServiceRequestData data = createCompanyCFXRequest(account, registrationRequest.getSourceApplication());
				datas.add(data);

				cResponse = createDefaultPFXContactBlacklistResponse();
				iResponse.addContactResponse(cResponse);
				
				exchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
						ServiceProviderEnum.BLACKLIST, account.getId(), account.getVersion(), EntityEnum.ACCOUNT.name(),
						token.getUserID(), cResponse, createDefaultBlacklistSummary(ServiceStatus.NOT_PERFORMED)));
			}

			internalServiceRequest.setSearchdata(datas);
			createRequestType(internalServiceRequest, account.getCustType(), operation);
			exchange.setRequest(internalServiceRequest);
			exchange.setResponse(iResponse);
			message.getPayload().appendMessageExchange(exchange);
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;			
	}

	private ContactResponse createDefaultPFXContactBlacklistResponse() {
		ContactResponse response = new ContactResponse();
		response.setBlacklist(createDefaultBlacklistResponse(ServiceStatus.NOT_PERFORMED));
		return response;
	}

	private BlacklistSummary createDefaultBlacklistSummary(ServiceStatus status){
		BlacklistSummary blacklistSummary = new BlacklistSummary();
		blacklistSummary.setStatus(status.name());
		return blacklistSummary;
	}
	
	private BlacklistContactResponse createDefaultBlacklistResponse(ServiceStatus status){
		BlacklistContactResponse blacklist = new BlacklistContactResponse();
		blacklist.setStatus(status.name());
		blacklist.setData(null);
		return blacklist;
	}
	
	private GlobalCheckContactResponse createDefaultGlobalCheckResponse(ServiceStatus status,String country){
		GlobalCheckContactResponse globalCheck = new GlobalCheckContactResponse();
		globalCheck.setStatus(status.name());
		globalCheck.setCountry(country);
		return globalCheck;
	}
	
	private CountryCheckContactResponse createDefaultCountryCheckResponse(ServiceStatus status, String country){
		CountryCheckContactResponse countryCheck = new CountryCheckContactResponse();
		countryCheck.setStatus(status.name());
		countryCheck.setCountry(country);
		return countryCheck;
	}
	
	private IpContactResponse createDefaultIpResponse(ServiceStatus status){
		IpContactResponse ipCheck = new IpContactResponse();
		ipCheck.setStatus(status.name());
		return ipCheck;
	}
	private IpSummary createDefaultIpSummary(ServiceStatus status, String ipAddress){
		IpSummary ip = new IpSummary();
		ip.setIpAddress(ipAddress);
		ip.setStatus(status.name());
		return ip;
	}
	
	private ContactResponse createDefaultCFXContactResponse(String countryName){
		ContactResponse response = new ContactResponse();
		response.setBlacklist(createDefaultBlacklistResponse(ServiceStatus.NOT_PERFORMED));
		response.setGlobalCheck(createDefaultGlobalCheckResponse(ServiceStatus.NOT_REQUIRED, countryName));
		response.setCountryCheck(createDefaultCountryCheckResponse(ServiceStatus.NOT_REQUIRED, countryName));
		response.setIpCheck(createDefaultIpResponse(ServiceStatus.NOT_REQUIRED));
		return response;
	}
	
	private ContactResponse createDefaultPFXContactResponse(String countryName){
		ContactResponse response = new ContactResponse();
		response.setBlacklist(createDefaultBlacklistResponse(ServiceStatus.NOT_PERFORMED));
		response.setGlobalCheck(createDefaultGlobalCheckResponse(ServiceStatus.NOT_PERFORMED, countryName));
		response.setCountryCheck(createDefaultCountryCheckResponse(ServiceStatus.NOT_PERFORMED, countryName));
		response.setIpCheck(createDefaultIpResponse(ServiceStatus.NOT_PERFORMED));
		return response;
	}
	
	/**
	 * @param registrationRequest
	 * @return
	 */
	private InternalServiceRequest initServiceRequest(RegistrationServiceRequest registrationRequest) {
		
		InternalServiceRequest internalServiceRequest = new InternalServiceRequest();
		internalServiceRequest.setCorrelationID(registrationRequest.getCorrelationID());
		internalServiceRequest.setOrgCode(registrationRequest.getOrgCode());
		internalServiceRequest.setAccountSFId(registrationRequest.getAccount().getAccSFID());
		return internalServiceRequest;
	}

	/**
	 * @param token
	 * @param eventServiceLogs
	 * @param eventId
	 * @param response
	 * @param blacklistSummary
	 * @param ip
	 * @param contact
	 */
	private void populateAllEventServiceLogs(UserProfile token, MessageExchange exchange,
			Integer eventId, ContactResponse response, BlacklistSummary blacklistSummary, IpSummary ip,
			Contact contact) {
		/* common for CFX/PFX start */
		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
				ServiceProviderEnum.BLACKLIST, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), response, blacklistSummary, ServiceStatus.valueOf(response.getBlacklist().getStatus())));

		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.IP_SERVICE, ServiceProviderEnum.IP,
				contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(), token.getUserID(), response, ip, 
				ServiceStatus.valueOf(response.getIpCheck().getStatus())));

		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.GLOBAL_CHECK_SERVICE,
				ServiceProviderEnum.GLOBALCHECK, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), response, response.getGlobalCheck(), ServiceStatus.valueOf(response.getGlobalCheck().getStatus())));

		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.HRC_SERVICE,
				ServiceProviderEnum.COUNTRYCHECK, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), response, response.getCountryCheck(), ServiceStatus.valueOf(response.getCountryCheck().getStatus())));
		/* common for CFX/PFX END */
	}
	
	/**
	 * Business -- After performing BLacklist check, country check, Ip check,
	 * Global check for both customerType (i.e PFX and CFX). transform
	 * InternalRuleService response and update EventServiceLog data. and set And
	 * when response is received from HTTPClient, appropriate response is
	 * updated * if InternalRuleService response is fail for any contact then
	 * set InternalRuleService as "FAIL" so no further checks will be perform.
	 * 
	 * Implementation--- 1) Get the
	 * messageExchange,UserProfile,InternalServiceRequest,
	 * InternalRuleServiceResponse ,contact,eventId and EventServiceLog from the
	 * message 2) If InternalRuleServiceResponse is null then update
	 * EventServiceLog and InternalRuleServiceResponse as "SERVICE_FAILURE" 3)
	 * else transforms InternalServiceResponse for both entityType ACCOUNT and
	 * CONTACT and updates EventServiceLog data. 4) If any of check (i.e
	 * blackList, country check, Ip check, global check) is fail then make
	 * InternalService status as "FAIL" and set PERFORM_REMAINING_CHECKS to
	 * false so no remaining checks (i.e KYC, SANCTIOn and FRAUGSTER) will be
	 * perform.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
		OperationEnum operation = messageExchange.getOperation();
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) exchange.getResponse();
		
		try {
			if (internalServiceResponse == null || (null != internalServiceResponse.getStatus() &&
					internalServiceResponse.getStatus().equalsIgnoreCase(ServiceStatus.SERVICE_FAILURE.name()))) {
				/**
				 * transform response if InternalRuleService is down or exception occurs in micro service as service 
				 * status is  SERVICE_FAILURE : Abhijit G
				 */
				transformResponseForServiceFailure(exchange, registrationRequest, operation);

			} else {
				/**
				 * Added additional attribute to set NOT_REQUIRED changes by
				 * Abhijit G
				 **/
				registrationRequest.addAttribute(Constants.NOT_BLACKLISTED, Boolean.TRUE);
				internalServiceResponse.setStatus(ServiceStatus.PASS.name());
				List<ContactResponse> contactResponses = internalServiceResponse.getContacts();
				for (ContactResponse response : contactResponses) {
					transformResponseForAccountAndContactUpdate(registrationRequest, exchange, internalServiceResponse,
							response, operation);
				}
			}
		} catch (Exception e) {
			message.getPayload().setFailed(true);
			LOGGER.error("Error in internalService response mapping", e);
		}
		return message;
	}

	private void transformResponseForAccountAndContactUpdate(RegistrationServiceRequest registrationRequest,
			MessageExchange exchange, InternalServiceResponse internalServiceResponse, ContactResponse response, OperationEnum operation) {
		InternalRuleSummary internalRuleSummary = new InternalRuleSummary();
		if((EntityEnum.ACCOUNT.toString()).equalsIgnoreCase(response.getEntityType())) {

			/**
			 * transform response for entityType ACCOUNT and update
			 * EventServiceLog, changes made by Abhijit G
			 */
			EventServiceLog blacklistLog = exchange.getEventServiceLog(
					ServiceTypeEnum.BLACK_LIST_SERVICE, response.getEntityType(), response.getId());
			transformResponseForAccount(registrationRequest, internalServiceResponse, blacklistLog, response,
					internalRuleSummary);

		} else {
			/**
			 * transform response for entityType CONTACT and update
			 * EventServiceLog, changes made by Abhijit G
			 */
			tranformResponseForContact(registrationRequest, internalServiceResponse, exchange, response,
					internalRuleSummary, operation);
		}
	}

	/**
	 * transform response from entity type ACCOUNT and updates eventServiceLog
	 * entries.
	 * 
	 * @param registrationRequest
	 * @param internalServiceResponse
	 * @param logs
	 * @param response
	 * @param internalRuleSummary
	 */
	private void transformResponseForAccount(RegistrationServiceRequest registrationRequest,
			InternalServiceResponse internalServiceResponse, EventServiceLog blacklistLog,
			ContactResponse response, InternalRuleSummary internalRuleSummary) {

		try {
			
			Company c = registrationRequest.getAccount().getCompany();

			if (c != null && (ServiceStatus.FAIL.name().equalsIgnoreCase(response.getBlacklist().getStatus()))) {
				c.setSanctionEligible(false);
				c.setFraugsterEligible(false);
				internalServiceResponse.setStatus(ServiceStatus.FAIL.name());
				/**
				 * Added additional attribute to set NOT_REQUIRED changes by
				 * Abhijit G
				 **/
				registrationRequest.addAttribute(Constants.NOT_BLACKLISTED, Boolean.FALSE);
			}

			updateEventServiceLogEntry(blacklistLog, createBlacklistSummary(response), response.getBlacklist(),
					response.getBlacklist().getStatus());
			internalRuleSummary.setBlacklistSummary(createBlacklistSummary(response));

		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}

	}

	/**
	 * transform response from entity type CONTACT and updates eventServiceLog
	 * entries.
	 * 
	 * @param registrationRequest
	 * @param internalServiceResponse
	 * @param logs
	 * @param response
	 * @param internalRuleSummary
	 */
	private void tranformResponseForContact(RegistrationServiceRequest registrationRequest,
			InternalServiceResponse internalServiceResponse, MessageExchange exchange,
			ContactResponse response, InternalRuleSummary internalRuleSummary, OperationEnum operation) {
		EventServiceLog blacklistLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
				response.getEntityType(), response.getId());
		EventServiceLog ipLog = exchange.getEventServiceLog(ServiceTypeEnum.IP_SERVICE, response.getEntityType(),
				response.getId());
		EventServiceLog globalCheckLog = exchange.getEventServiceLog(ServiceTypeEnum.GLOBAL_CHECK_SERVICE,
				response.getEntityType(), response.getId());
		EventServiceLog countryCheckLog = exchange.getEventServiceLog(ServiceTypeEnum.HRC_SERVICE,
				response.getEntityType(), response.getId());

		try {
			Contact c = registrationRequest.getAccount().getContactByID(response.getId());

			if (c != null && (ServiceStatus.FAIL.name().equalsIgnoreCase(response.getBlacklist().getStatus())
					|| ServiceStatus.FAIL.name().equalsIgnoreCase(response.getGlobalCheck().getStatus())
					|| ServiceStatus.FAIL.name().equalsIgnoreCase(response.getCountryCheck().getStatus()))) {
				c.setKYCEligible(false);
				c.setFraugsterEligible(true);
				c.setSanctionEligible(false);
				internalServiceResponse.setStatus(ServiceStatus.FAIL.name());
				/**
				 * Added additional attribute to set NOT_REQUIRED changes by
				 * Abhijit G
				 **/
				registrationRequest.addAttribute(Constants.NOT_BLACKLISTED, Boolean.FALSE);
				
				//AT-3398 - POI should be required for blacklisted customer
				if(LegalEntityEnum.isEULegalEntity(registrationRequest.getAccount().getCustLegalEntity())) {
					registrationRequest.addAttribute(Constants.BLACKLISTED_EU_CUSTOMER_POI, Boolean.TRUE);
					c.setKYCEligible(true);
					registrationRequest.addAttribute(Constants.ADD_BLACKLIST_ACTIVITY_LOG_FOR_EU,Boolean.TRUE);//AT-3492
				}
			}

			updateEventServiceLogEntry(blacklistLog, createBlacklistSummary(response), response.getBlacklist(),
					response.getBlacklist().getStatus());

			if(null == internalServiceResponse.getOnlyBlacklistCheckPerform() || !internalServiceResponse.getOnlyBlacklistCheckPerform()) {
				updateEventServiceLogEntry(ipLog, createIpSummary(response), response.getIpCheck(),
						response.getIpCheck().getStatus());
				updateEventServiceLogEntry(globalCheckLog, response.getGlobalCheck(), response.getGlobalCheck(),
						response.getGlobalCheck().getStatus());
				updateEventServiceLogEntry(countryCheckLog, response.getCountryCheck(), response.getCountryCheck(),
						response.getCountryCheck().getStatus());
			}

			if (response.getIpCheck() != null
					&& !ServiceStatus.FAIL.toString().equals(response.getIpCheck().getStatus())) {
				populateIpPostCodeFieldsInContact(registrationRequest, response.getIpCheck(), response.getId());
			}

			internalRuleSummary.setBlacklistSummary(createBlacklistSummary(response));
			if(null == internalServiceResponse.getOnlyBlacklistCheckPerform() || !internalServiceResponse.getOnlyBlacklistCheckPerform()) {
				internalRuleSummary.setIpSummary(createIpSummary(response));
				internalRuleSummary.setHrcSummary(response.getCountryCheck());
				internalRuleSummary.setGlobalCheckSummary(response.getGlobalCheck());
			}

		} catch (Exception e) {
			LOGGER.error("Error while mapping InternalService response ::  tranformResponseForContact()", e);
			transformResponseForServiceFailure(exchange, registrationRequest, operation);
		}
	}

	/**
	 * creates response when Service failure occurs.
	 * 
	 * @author abhijeetg
	 * @param internalServiceRequest
	 * @param exchange
	 * @param logs
	 */
	private void transformResponseForServiceFailure(MessageExchange exchange, RegistrationServiceRequest registrationRequest, OperationEnum operation) {
		InternalServiceResponse internalServiceResponse = new InternalServiceResponse();
		internalServiceResponse.setStatus(ServiceStatus.FAIL.name());
		List<ContactResponse> contactResponseList = new ArrayList<>();

		try {
			
			/**
			 * Added additional attribute to set NOT_REQUIRED changes by
			 * Abhijit G
			 **/
			registrationRequest.addAttribute(Constants.NOT_BLACKLISTED, Boolean.FALSE);
			Account account = registrationRequest.getAccount();
			List<Contact> contacts = account.getContacts();
			
			if(contacts.isEmpty()){
				transformResponseForServiceFailureForAccount(exchange,account, contactResponseList);
			}
			
			for (Contact contact : account.getContacts()) {

				ContactResponse response = new ContactResponse();
				GlobalCheckContactResponse globalCheck = new GlobalCheckContactResponse();
				BlacklistContactResponse blacklist = new BlacklistContactResponse();
				IpContactResponse ipCheck = new IpContactResponse();
				CountryCheckContactResponse countryCheck = new CountryCheckContactResponse();
				BlacklistSummary blacklistSummary = new BlacklistSummary();

				response.setBlacklist(blacklist);
				blacklist.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				blacklist.setData(null);
				blacklistSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				globalCheck.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				ipCheck.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				countryCheck.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				response.setBlacklist(blacklist);
				response.setGlobalCheck(globalCheck);
				response.setCountryCheck(countryCheck);
				response.setIpCheck(ipCheck);
				response.setId(contact.getId());

				EventServiceLog accBlacklistLog = exchange.getEventServiceLog(
						ServiceTypeEnum.BLACK_LIST_SERVICE, EntityEnum.ACCOUNT.name(), account.getId());
				
				EventServiceLog contactBlacklistLog = exchange.getEventServiceLog(
						ServiceTypeEnum.BLACK_LIST_SERVICE, EntityEnum.CONTACT.name(), contact.getId());
				EventServiceLog ipLog = exchange.getEventServiceLog(ServiceTypeEnum.IP_SERVICE, EntityEnum.CONTACT.name(), contact.getId());
				EventServiceLog globalCheckLog = exchange.getEventServiceLog(
						ServiceTypeEnum.GLOBAL_CHECK_SERVICE, EntityEnum.CONTACT.name(), contact.getId());
				EventServiceLog countryCheckLog = exchange.getEventServiceLog(
						ServiceTypeEnum.HRC_SERVICE, EntityEnum.CONTACT.name(), contact.getId());

				if(null != accBlacklistLog){
					updateEventServiceLogEntry(accBlacklistLog, blacklistSummary, response.getBlacklist(),
						ServiceStatus.SERVICE_FAILURE.name());
				}
				updateEventServiceLogEntry(contactBlacklistLog, blacklistSummary, response.getBlacklist(),
						ServiceStatus.SERVICE_FAILURE.name());
				
				if (OperationEnum.BLACKLIST_RESEND!=operation) {
				updateEventServiceLogEntry(ipLog, createIpSummaryForServiceFailure(response), response.getIpCheck(),
						ServiceStatus.SERVICE_FAILURE.name());
				updateEventServiceLogEntry(globalCheckLog, response.getGlobalCheck(), response.getGlobalCheck(),
						ServiceStatus.SERVICE_FAILURE.name());
				updateEventServiceLogEntry(countryCheckLog, response.getCountryCheck(), response.getCountryCheck(),
						ServiceStatus.SERVICE_FAILURE.name());
				}

				contactResponseList.add(response);
			}
			
			internalServiceResponse.setContacts(contactResponseList);
			exchange.setResponse(internalServiceResponse);

		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}

	}

	private IpSummary createIpSummaryForServiceFailure(ContactResponse contactResponse) {
		IpContactResponse response = contactResponse.getIpCheck();
		IpSummary ip = new IpSummary();
		if (response != null) {
			ip.setIpAddress(response.getIpAddress());
			ip.setIpRule(response.getChecks());
			ip.setGeoDifference(response.getGeoDifference());
			ip.setIpCity(response.getIpCity());
			ip.setIpCountry(response.getIpCountry());
			ip.setUnit(response.getUnit());
			ip.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		}
		return ip;
	}


	private void createRequestType(InternalServiceRequest internalServiceRequest, String custType,
			OperationEnum operation) {
		if (Constants.PFX.equalsIgnoreCase(custType)) {
			if (OperationEnum.NEW_REGISTRATION==operation) {
				internalServiceRequest.setRequestType(Constants.PFX_REGISTRATION);
			} else if (OperationEnum.ADD_CONTACT==operation) {
				internalServiceRequest.setRequestType(Constants.PFX_ADD_CONTACT);
			} else if (OperationEnum.BLACKLIST_RESEND==operation) {
				internalServiceRequest.setRequestType(Constants.BLACKLIST_RECHECK);
			} else {
				internalServiceRequest.setRequestType(Constants.PFX_UPDATE_ACCOUNT);
			}
		} else {
			if (OperationEnum.NEW_REGISTRATION==operation) {
				internalServiceRequest.setRequestType(Constants.CFX_REGISTRATION);
			} else if (OperationEnum.ADD_CONTACT==operation) {
				internalServiceRequest.setRequestType(Constants.CFX_ADD_CONTACT);
			} else if (OperationEnum.BLACKLIST_RESEND==operation) {
				internalServiceRequest.setRequestType(Constants.BLACKLIST_RECHECK);
			} else {
				internalServiceRequest.setRequestType(Constants.CFX_UPDATE_ACCOUNT);
			}
		}

	}
	
	private Message<MessageContext> transformCFXResendRequest(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			OperationEnum operation = messageExchange.getOperation();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

			Account account = registrationRequest.getAccount();
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(EVENT_ID);
			List<Contact> contacts = account.getContacts();
			InternalServiceRequest internalServiceRequest = initServiceRequest(registrationRequest);
			List<InternalServiceRequestData> datas = new ArrayList<>();

			InternalServiceResponse iResponse = new InternalServiceResponse();
			ContactResponse cResponse;
			MessageExchange exchange = new MessageExchange();
			exchange.setServiceTypeEnum(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			internalServiceRequest.setOnlyBlacklistCheckPerform(Boolean.TRUE);
			
			if(EntityEnum.ACCOUNT.name().equals(registrationRequest.getAdditionalAttributes().get(Constants.ENTITY_TYPE))) {

				InternalServiceRequestData data = createCompanyCFXRequest(account, registrationRequest.getSourceApplication());
				datas.add(data);

				cResponse = createDefaultPFXContactBlacklistResponse();
				iResponse.addContactResponse(cResponse);
				
				exchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
						ServiceProviderEnum.BLACKLIST, account.getId(), account.getVersion(), EntityEnum.ACCOUNT.name(),
						token.getUserID(), cResponse, createDefaultBlacklistSummary(ServiceStatus.NOT_PERFORMED)));
			} else {
				String orgLegalEntity = account.getCustLegalEntity();
				for (Contact contact : contacts) {

					InternalServiceRequestData data = createContactRequest(registrationRequest.getSourceApplication(),
							contact, null, orgLegalEntity);
					datas.add(data);

					cResponse = createDefaultPFXContactBlacklistResponse();
					iResponse.addContactResponse(cResponse);
					
					exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
							ServiceProviderEnum.BLACKLIST, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
							token.getUserID(), cResponse, createDefaultBlacklistSummary(ServiceStatus.NOT_PERFORMED), ServiceStatus.valueOf(cResponse.getBlacklist().getStatus())));
				}
			}

			internalServiceRequest.setSearchdata(datas);
			createRequestType(internalServiceRequest, account.getCustType(), operation);
			exchange.setRequest(internalServiceRequest);
			exchange.setResponse(iResponse);
			message.getPayload().appendMessageExchange(exchange);
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;			
	}
	
	/**
	 * creates response when Service failure occurs.
	 * 
	 * @author abhijeetg
	 * @param internalServiceRequest
	 * @param exchange
	 * @param logs
	 */
	private void transformResponseForServiceFailureForAccount(MessageExchange exchange, Account account,
			List<ContactResponse> contactResponseList) {
		try {
			ContactResponse response = new ContactResponse();
			BlacklistContactResponse blacklist = new BlacklistContactResponse();
			BlacklistSummary blacklistSummary = new BlacklistSummary();

			response.setBlacklist(blacklist);
			blacklist.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			blacklist.setData(null);
			blacklistSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			response.setBlacklist(blacklist);

			EventServiceLog accBlacklistLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
					EntityEnum.ACCOUNT.name(), account.getId());

			if (null != accBlacklistLog) {
				updateEventServiceLogEntry(accBlacklistLog, blacklistSummary, response.getBlacklist(),
						ServiceStatus.SERVICE_FAILURE.name());
			}

			contactResponseList.add(response);
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}

	}
}
