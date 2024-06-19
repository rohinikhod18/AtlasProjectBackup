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
 * The Class InternalRuleServiceBulkRecheckTransformer.
 */
public class InternalRuleServiceBulkRecheckTransformer extends AbstractInternalRulesTransformer {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InternalRuleServiceBulkRecheckTransformer.class);

	/** The Constant EVENT_ID. */
	public static final String EVENT_ID = "eventId";

	/** The country cache. */
	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();

	/** The Constant ERROR. */
	private static final String ERROR = "ERROR";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformRequest(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			Account account = registrationRequest.getAccount();
			if (Constants.CFX.equalsIgnoreCase(account.getCustType())) {
				transformCFXRequest(message);
			} else {
				transformPFXRequest(message);
			}
		} catch (Exception e) {
			message.getPayload().setFailed(true);
			LOGGER.error(ERROR, e);
		}
		return message;
	}

	/**
	 * Transform CFX request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformCFXRequest(Message<MessageContext> message) throws ComplianceException {

		try {
			transformCFXSignupUpdateRequest(message);
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;
	}

	/**
	 * Transform CFX signup update request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformCFXSignupUpdateRequest(Message<MessageContext> message)
			throws ComplianceException {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			Integer contactId = (Integer) registrationRequest.getAdditionalAttribute(Constants.CONTACT_ID);
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

			Account account = registrationRequest.getAccount();
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			List<Contact> contacts = account.getContacts();
			InternalServiceRequest internalServiceRequest = initServiceRequest(registrationRequest);
			List<InternalServiceRequestData> datas = new ArrayList<>();

			InternalServiceResponse iResponse = new InternalServiceResponse();
			ContactResponse cResponse;

			// START - Company/Account Blacklist check
			InternalServiceRequestData data = createCompanyCFXRequest(account,
					registrationRequest.getSourceApplication());
			datas.add(data);

			cResponse = createDefaultCFXContactResponse(null);
			iResponse.addContactResponse(cResponse);

			MessageExchange exchange = new MessageExchange();
			exchange.setServiceTypeEnum(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			exchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
					ServiceProviderEnum.BLACKLIST, account.getId(), account.getVersion(), EntityEnum.ACCOUNT.name(),
					token.getUserID(), cResponse, createDefaultBlacklistSummary(ServiceStatus.NOT_PERFORMED)));
			String orgLegalEntity = account.getCustLegalEntity();
			for (Contact contact : contacts) {
				if (null != contactId && contactId.equals(contact.getId())) {

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
			}

			internalServiceRequest.setOnlyBlacklistCheckPerform(Boolean.FALSE);
			internalServiceRequest.setSearchdata(datas);
			createRequestType(internalServiceRequest, account.getCustType());
			exchange.setRequest(internalServiceRequest);
			exchange.setResponse(iResponse);
			message.getPayload().removeMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			message.getPayload().appendMessageExchange(exchange);

		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;
	}

	/**
	 * Transform PFX request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformPFXRequest(Message<MessageContext> message) throws ComplianceException {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();

			
				RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange
						.getRequest();
				Integer contactId = (Integer) registrationRequest.getAdditionalAttribute(Constants.CONTACT_ID);
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
				if (null != contactId && contactId.equals(contact.getId())) {

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
			}

			internalServiceRequest.setOnlyBlacklistCheckPerform(Boolean.FALSE);
			internalServiceRequest.setSearchdata(datas);
			createRequestType(internalServiceRequest, account.getCustType());
			exchange.setRequest(internalServiceRequest);
			exchange.setResponse(iResponse);
			message.getPayload().removeMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			message.getPayload().appendMessageExchange(exchange);
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;
	}

	/**
	 * Creates the default blacklist summary.
	 *
	 * @param status
	 *            the status
	 * @return the blacklist summary
	 */
	private BlacklistSummary createDefaultBlacklistSummary(ServiceStatus status) {
		BlacklistSummary blacklistSummary = new BlacklistSummary();
		blacklistSummary.setStatus(status.name());
		return blacklistSummary;
	}

	/**
	 * Creates the default blacklist response.
	 *
	 * @param status
	 *            the status
	 * @return the blacklist contact response
	 */
	private BlacklistContactResponse createDefaultBlacklistResponse(ServiceStatus status) {
		BlacklistContactResponse blacklist = new BlacklistContactResponse();
		blacklist.setStatus(status.name());
		blacklist.setData(null);
		return blacklist;
	}

	/**
	 * Creates the default global check response.
	 *
	 * @param status
	 *            the status
	 * @param country
	 *            the country
	 * @return the global check contact response
	 */
	private GlobalCheckContactResponse createDefaultGlobalCheckResponse(ServiceStatus status, String country) {
		GlobalCheckContactResponse globalCheck = new GlobalCheckContactResponse();
		globalCheck.setStatus(status.name());
		globalCheck.setCountry(country);
		return globalCheck;
	}

	/**
	 * Creates the default country check response.
	 *
	 * @param status
	 *            the status
	 * @param country
	 *            the country
	 * @return the country check contact response
	 */
	private CountryCheckContactResponse createDefaultCountryCheckResponse(ServiceStatus status, String country) {
		CountryCheckContactResponse countryCheck = new CountryCheckContactResponse();
		countryCheck.setStatus(status.name());
		countryCheck.setCountry(country);
		return countryCheck;
	}

	/**
	 * Creates the default ip response.
	 *
	 * @param status
	 *            the status
	 * @return the ip contact response
	 */
	private IpContactResponse createDefaultIpResponse(ServiceStatus status) {
		IpContactResponse ipCheck = new IpContactResponse();
		ipCheck.setStatus(status.name());
		return ipCheck;
	}

	/**
	 * Creates the default ip summary.
	 *
	 * @param status
	 *            the status
	 * @param ipAddress
	 *            the ip address
	 * @return the ip summary
	 */
	private IpSummary createDefaultIpSummary(ServiceStatus status, String ipAddress) {
		IpSummary ip = new IpSummary();
		ip.setIpAddress(ipAddress);
		ip.setStatus(status.name());
		return ip;
	}

	/**
	 * Creates the default CFX contact response.
	 *
	 * @param countryName
	 *            the country name
	 * @return the contact response
	 */
	private ContactResponse createDefaultCFXContactResponse(String countryName) {
		ContactResponse response = new ContactResponse();
		response.setBlacklist(createDefaultBlacklistResponse(ServiceStatus.NOT_PERFORMED));
		response.setGlobalCheck(createDefaultGlobalCheckResponse(ServiceStatus.NOT_REQUIRED, countryName));
		response.setCountryCheck(createDefaultCountryCheckResponse(ServiceStatus.NOT_REQUIRED, countryName));
		response.setIpCheck(createDefaultIpResponse(ServiceStatus.NOT_REQUIRED));
		return response;
	}

	/**
	 * Creates the default PFX contact response.
	 *
	 * @param countryName
	 *            the country name
	 * @return the contact response
	 */
	private ContactResponse createDefaultPFXContactResponse(String countryName) {
		ContactResponse response = new ContactResponse();
		response.setBlacklist(createDefaultBlacklistResponse(ServiceStatus.NOT_PERFORMED));
		response.setGlobalCheck(createDefaultGlobalCheckResponse(ServiceStatus.NOT_PERFORMED, countryName));
		response.setCountryCheck(createDefaultCountryCheckResponse(ServiceStatus.NOT_PERFORMED, countryName));
		response.setIpCheck(createDefaultIpResponse(ServiceStatus.NOT_PERFORMED));
		return response;
	}

	/**
	 * Inits the service request.
	 *
	 * @param registrationRequest
	 *            the registration request
	 * @return the internal service request
	 */
	private InternalServiceRequest initServiceRequest(RegistrationServiceRequest registrationRequest) {

		InternalServiceRequest internalServiceRequest = new InternalServiceRequest();
		internalServiceRequest.setCorrelationID(registrationRequest.getCorrelationID());
		internalServiceRequest.setOrgCode(registrationRequest.getOrgCode());
		internalServiceRequest.setAccountSFId(registrationRequest.getAccount().getAccSFID());
		return internalServiceRequest;
	}

	/**
	 * Populate all event service logs.
	 *
	 * @param token
	 *            the token
	 * @param exchange
	 *            the exchange
	 * @param eventId
	 *            the event id
	 * @param response
	 *            the response
	 * @param blacklistSummary
	 *            the blacklist summary
	 * @param ip
	 *            the ip
	 * @param contact
	 *            the contact
	 */
	private void populateAllEventServiceLogs(UserProfile token, MessageExchange exchange, Integer eventId,
			ContactResponse response, BlacklistSummary blacklistSummary, IpSummary ip, Contact contact) {
		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
				ServiceProviderEnum.BLACKLIST, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), response, blacklistSummary,
				ServiceStatus.valueOf(response.getBlacklist().getStatus())));

		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.IP_SERVICE,
				ServiceProviderEnum.IP, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), response, ip, ServiceStatus.valueOf(response.getIpCheck().getStatus())));

		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.GLOBAL_CHECK_SERVICE,
				ServiceProviderEnum.GLOBALCHECK, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), response, response.getGlobalCheck(),
				ServiceStatus.valueOf(response.getGlobalCheck().getStatus())));

		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.HRC_SERVICE,
				ServiceProviderEnum.COUNTRYCHECK, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), response, response.getCountryCheck(),
				ServiceStatus.valueOf(response.getCountryCheck().getStatus())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();

		OperationEnum operation = messageExchange.getOperation();
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) exchange.getResponse();

		try {
			if (internalServiceResponse == null) {
				transformResponseForServiceFailure(exchange, registrationRequest, operation);

			} else {
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

	/**
	 * Transform response for account and contact update.
	 *
	 * @param registrationRequest
	 *            the registration request
	 * @param exchange
	 *            the exchange
	 * @param internalServiceResponse
	 *            the internal service response
	 * @param response
	 *            the response
	 * @param operation
	 *            the operation
	 */
	private void transformResponseForAccountAndContactUpdate(RegistrationServiceRequest registrationRequest,
			MessageExchange exchange, InternalServiceResponse internalServiceResponse, ContactResponse response,
			OperationEnum operation) {
		InternalRuleSummary internalRuleSummary = new InternalRuleSummary();
		Integer contactId = (Integer) registrationRequest.getAdditionalAttribute(Constants.CONTACT_ID);
		if ((EntityEnum.ACCOUNT.toString()).equalsIgnoreCase(response.getEntityType())) {

			EventServiceLog blacklistLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
					response.getEntityType(), response.getId());
			transformResponseForAccount(registrationRequest, internalServiceResponse, blacklistLog, response,
					internalRuleSummary);

		} else {
			if (null != contactId && contactId.equals(response.getId())) {
				tranformResponseForContact(registrationRequest, internalServiceResponse, exchange, response,
						internalRuleSummary, operation);
			}
		}
	}

	/**
	 * Transform response for account.
	 *
	 * @param registrationRequest
	 *            the registration request
	 * @param internalServiceResponse
	 *            the internal service response
	 * @param blacklistLog
	 *            the blacklist log
	 * @param response
	 *            the response
	 * @param internalRuleSummary
	 *            the internal rule summary
	 */
	private void transformResponseForAccount(RegistrationServiceRequest registrationRequest,
			InternalServiceResponse internalServiceResponse, EventServiceLog blacklistLog, ContactResponse response,
			InternalRuleSummary internalRuleSummary) {

		try {

			Company c = registrationRequest.getAccount().getCompany();

			if (c != null && (ServiceStatus.FAIL.name().equalsIgnoreCase(response.getBlacklist().getStatus()))) {
				c.setSanctionEligible(false);
				c.setFraugsterEligible(false);
				internalServiceResponse.setStatus(ServiceStatus.FAIL.name());
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
	 * Tranform response for contact.
	 *
	 * @param registrationRequest
	 *            the registration request
	 * @param internalServiceResponse
	 *            the internal service response
	 * @param exchange
	 *            the exchange
	 * @param response
	 *            the response
	 * @param internalRuleSummary
	 *            the internal rule summary
	 * @param operation
	 *            the operation
	 */
	private void tranformResponseForContact(RegistrationServiceRequest registrationRequest,
			InternalServiceResponse internalServiceResponse, MessageExchange exchange, ContactResponse response,
			InternalRuleSummary internalRuleSummary, OperationEnum operation) {
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
				registrationRequest.addAttribute(Constants.NOT_BLACKLISTED, Boolean.FALSE);
			}

			updateEventServiceLogEntry(blacklistLog, createBlacklistSummary(response), response.getBlacklist(),
					response.getBlacklist().getStatus());

			if (null == internalServiceResponse.getOnlyBlacklistCheckPerform()
					|| !internalServiceResponse.getOnlyBlacklistCheckPerform()) {
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
			if (null == internalServiceResponse.getOnlyBlacklistCheckPerform()
					|| !internalServiceResponse.getOnlyBlacklistCheckPerform()) {
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
	 * Transform response for service failure.
	 *
	 * @param exchange
	 *            the exchange
	 * @param registrationRequest
	 *            the registration request
	 * @param operation
	 *            the operation
	 */
	private void transformResponseForServiceFailure(MessageExchange exchange,
			RegistrationServiceRequest registrationRequest, OperationEnum operation) {
		InternalServiceResponse internalServiceResponse = new InternalServiceResponse();
		internalServiceResponse.setStatus(ServiceStatus.FAIL.name());
		List<ContactResponse> contactResponseList = new ArrayList<>();

		try {

			registrationRequest.addAttribute(Constants.NOT_BLACKLISTED, Boolean.FALSE);
			Account account = registrationRequest.getAccount();
			List<Contact> contacts = account.getContacts();
			Integer contactId = (Integer) registrationRequest.getAdditionalAttribute(Constants.CONTACT_ID);

			if (contacts.isEmpty()) {
				transformResponseForServiceFailureForAccount(exchange, account, contactResponseList);
			}

			for (Contact contact : account.getContacts()) {
				if (null != contactId && contactId.equals(contact.getId())) {

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

					EventServiceLog accBlacklistLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
							EntityEnum.ACCOUNT.name(), account.getId());

					EventServiceLog contactBlacklistLog = exchange.getEventServiceLog(
							ServiceTypeEnum.BLACK_LIST_SERVICE, EntityEnum.CONTACT.name(), contact.getId());
					EventServiceLog ipLog = exchange.getEventServiceLog(ServiceTypeEnum.IP_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					EventServiceLog globalCheckLog = exchange.getEventServiceLog(ServiceTypeEnum.GLOBAL_CHECK_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					EventServiceLog countryCheckLog = exchange.getEventServiceLog(ServiceTypeEnum.HRC_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());

					if (null != accBlacklistLog) {
						updateEventServiceLogEntry(accBlacklistLog, blacklistSummary, response.getBlacklist(),
								ServiceStatus.SERVICE_FAILURE.name());
					}
					updateEventServiceLogEntry(contactBlacklistLog, blacklistSummary, response.getBlacklist(),
							ServiceStatus.SERVICE_FAILURE.name());

					if (OperationEnum.BLACKLIST_RESEND!=operation) {
						updateEventServiceLogEntry(ipLog, createIpSummaryForServiceFailure(response),
								response.getIpCheck(), ServiceStatus.SERVICE_FAILURE.name());
						updateEventServiceLogEntry(globalCheckLog, response.getGlobalCheck(), response.getGlobalCheck(),
								ServiceStatus.SERVICE_FAILURE.name());
						updateEventServiceLogEntry(countryCheckLog, response.getCountryCheck(),
								response.getCountryCheck(), ServiceStatus.SERVICE_FAILURE.name());
					}

					contactResponseList.add(response);
				}
			}

			internalServiceResponse.setContacts(contactResponseList);
			exchange.setResponse(internalServiceResponse);

		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}

	}

	/**
	 * Creates the ip summary for service failure.
	 *
	 * @param contactResponse
	 *            the contact response
	 * @return the ip summary
	 */
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

	/**
	 * Creates the request type.
	 *
	 * @param internalServiceRequest
	 *            the internal service request
	 * @param custType
	 *            the cust type
	 */
	private void createRequestType(InternalServiceRequest internalServiceRequest, String custType) {
		if (Constants.PFX.equalsIgnoreCase(custType))
			internalServiceRequest.setRequestType(Constants.PFX_UPDATE_ACCOUNT);
		else
			internalServiceRequest.setRequestType(Constants.CFX_UPDATE_ACCOUNT);

	}

	/**
	 * Transform response for service failure for account.
	 *
	 * @param exchange
	 *            the exchange
	 * @param account
	 *            the account
	 * @param contactResponseList
	 *            the contact response list
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
