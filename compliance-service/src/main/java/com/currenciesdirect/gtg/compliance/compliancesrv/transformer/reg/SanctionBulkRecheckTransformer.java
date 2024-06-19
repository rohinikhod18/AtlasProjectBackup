package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.reg;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.ExternalErrorCodes;
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
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractSanctionTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class SanctionBulkRecheckTransformer.
 */
public class SanctionBulkRecheckTransformer extends AbstractSanctionTransformer {

	/** The Constant FIRST_SANCTION_SUMMARY. */
	private static final String FIRST_SANCTION_SUMMARY = "firstSanctionSummary";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SanctionBulkRecheckTransformer.class);

	/** The country cache. */
	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();

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
			String custType = account.getCustType();
			boolean notBlackListed = true;

			if (registrationRequest.getAdditionalAttribute(Constants.NOT_BLACKLISTED) != null) {
				notBlackListed = (boolean) registrationRequest.getAdditionalAttribute(Constants.NOT_BLACKLISTED);
			}
			if (notBlackListed) {
				if (custType != null) {
					custType = custType.trim();
				}
				if (Constants.CFX.equalsIgnoreCase(custType)) {
					transformCFXRequest(message);
				} else if (Constants.PFX.equalsIgnoreCase(custType)) {
					transformPFXRequest(message);
				}
			} else {
				List<SanctionContactResponse> responseContactList = new ArrayList<>();
				SanctionRequest sanctionRequest = new SanctionRequest();
				Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
						.getAdditionalAttribute(Constants.FIELD_EVENTID);
				SanctionResponse fResponse = new SanctionResponse();
				UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
				String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
				OperationEnum operation = messageExchange.getOperation();
				MessageExchange ccExchange = new MessageExchange();
				ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
				createEventServiceLogForNotRequired(registrationRequest, eventId, ccExchange, token,
						responseContactList, orgID, operation);
				fResponse.setContactResponses(responseContactList);
				ccExchange.setRequest(sanctionRequest);
				ccExchange.setResponse(fResponse);
				message.getPayload().removeMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
				message.getPayload().appendMessageExchange(ccExchange);
			}

		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
			message.getPayload().setFailed(true);
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
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.RECHECK_FAILURES==operation) {
				transformCFXRecheckServiceFailureRequest(message);
			}
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}

	/**
	 * Transform CFX recheck service failure request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformCFXRecheckServiceFailureRequest(Message<MessageContext> message)
			throws ComplianceException {
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			Account account = registrationRequest.getAccount();
			List<Contact> contacts = account.getContacts();
			SanctionRequest sanctionRequest = new SanctionRequest();
			SanctionResponse fResponse = new SanctionResponse();
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			List<SanctionContactRequest> contactList;
			List<SanctionContactResponse> responseContactList;
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			String sanctionAccountCategory = getAccountSanctionCategory();

			SanctionContactResponse defaultResponse = new SanctionContactResponse();
			defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

			SanctionSummary summary = new SanctionSummary();
			summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			summary.setWorldCheck(Constants.NOT_AVAILABLE);
			summary.setOfacList(Constants.NOT_AVAILABLE);
			summary.setSanctionId(Constants.NOT_AVAILABLE);
			summary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
			SanctionContactResponse companyResponse;
			contactList = new ArrayList<>();
			responseContactList = new ArrayList<>();

			SanctionContactRequest comapnyContact = createCompanyCFXRequest(orgID, account, countryCache,
					sanctionAccountCategory);
			if (account.getCompany().isSanctionEligible()) {
				companyResponse = createCFXResponse(account, ServiceStatus.NOT_PERFORMED);
			} else {
				companyResponse = createCFXResponse(account, ServiceStatus.NOT_REQUIRED);
				StringBuilder sanctionID = new StringBuilder();
				sanctionID.append(orgID).append("-A-").append(StringUtils.leftPadWithZero(10, account.getId()));
				companyResponse.setSanctionId(sanctionID.toString());
			}

			sanctionRequest.setCustomerType(Constants.CFX);
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.SANCTION_SERVICE,
					ServiceProviderEnum.SANCTION_SERVICE, account.getId(), account.getVersion(),
					EntityEnum.ACCOUNT.name(), token.getUserID(), defaultResponse, summary));

			for (Contact contact : contacts) {

				defaultResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
				StringBuilder sanctionID = new StringBuilder();
				sanctionID.append(orgID).append("-C-").append(StringUtils.leftPadWithZero(10, contact.getId()));
				summary.setSanctionId(sanctionID.toString());
				summary.setStatus(ServiceStatus.NOT_REQUIRED.name());
				ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId,
						ServiceTypeEnum.SANCTION_SERVICE, ServiceProviderEnum.SANCTION_SERVICE, contact.getId(),
						contact.getVersion(), EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, summary,
						ServiceStatus.NOT_REQUIRED));
			}

			if (null != comapnyContact) {
				contactList.add(comapnyContact);
			}

			responseContactList.add(companyResponse);

			sanctionRequest.setContactrequests(contactList);
			fResponse.setContactResponses(responseContactList);
			ccExchange.setRequest(sanctionRequest);
			ccExchange.setResponse(fResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			message.getPayload().removeMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
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
			OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.RECHECK_FAILURES==operation) {
				transformPFXRecheckServiceFailureRequest(message);
			}
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;

	}

	/**
	 * Transform PFX recheck service failure request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformPFXRecheckServiceFailureRequest(Message<MessageContext> message)
			throws ComplianceException {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			Integer contactId = (Integer) registrationRequest.getAdditionalAttribute(Constants.CONTACT_ID);
			Account account = registrationRequest.getAccount();
			List<Contact> contacts = account.getContacts();
			SanctionRequest sanctionRequest = new SanctionRequest();
			SanctionResponse fResponse = new SanctionResponse();
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			List<SanctionContactRequest> contactList;
			List<SanctionContactResponse> responseContactList = new ArrayList<>();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			String sanctionContactCategory = getContactSanctionCategory();

			contactList = createContactRequest(orgID, contacts, countryCache, sanctionContactCategory);
			MessageExchange ccExchange = new MessageExchange();
			ServiceStatus status = ServiceStatus.NOT_PERFORMED;
			for (Contact contact : contacts) {
				if (null != contactId && contactId.equals(contact.getId())) {
					if (!contact.isSanctionEligible()) {
						status = ServiceStatus.NOT_REQUIRED;
					}
					String sanctionId = createReferenceId(registrationRequest.getOrgId().toString(),
							EntityEnum.CONTACT.name(), contact.getId().toString());
					SanctionSummary summary = new SanctionSummary();
					summary.setStatus(status.name());
					summary.setSanctionId(sanctionId);
					summary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());

					SanctionContactResponse defaultResponse = new SanctionContactResponse();
					defaultResponse.setContactId(contact.getId());
					defaultResponse.setSanctionId(sanctionId);
					defaultResponse.setStatus(status.name());
					responseContactList.add(defaultResponse);
					ccExchange.addEventServiceLog(
							createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.SANCTION_SERVICE,
									ServiceProviderEnum.SANCTION_SERVICE, contact.getId(), contact.getVersion(),
									EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, summary, status));
				}
			}
			sanctionRequest.setContactrequests(contactList);
			fResponse.setContactResponses(responseContactList);

			ccExchange.setRequest(sanctionRequest);
			ccExchange.setResponse(fResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			message.getPayload().removeMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		try {
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			String custType = registrationRequest.getAccount().getCustType();
			if (Constants.CFX.equalsIgnoreCase(custType.trim())) {
				transformCFXResponse(message);
			} else if (Constants.PFX.equalsIgnoreCase(custType.trim())) {
				transformPFXResponse(message);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in transformResponse() of Sanction Transformer", e);
			message.getPayload().setFailed(true);
		}
		return message;

	}

	/**
	 * Transform PFX response.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformPFXResponse(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.RECHECK_FAILURES==operation) {
				transformPFXRecheckServiceFailureResponse(message);
			}
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}

	/**
	 * Transform PFX recheck service failure response.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformPFXRecheckServiceFailureResponse(Message<MessageContext> message) {
		transformCommonPFXResponse(message);
		return message;
	}

	/**
	 * Transform CFX response.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformCFXResponse(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.RECHECK_FAILURES==operation) {
				transformCFXRecheckServiceFailureResponse(message);
			}
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}

	/**
	 * Transform CFX recheck service failure response.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformCFXRecheckServiceFailureResponse(Message<MessageContext> message) {
		transformCommonCFXResponse(message);
		return message;
	}

	/**
	 * Transform common PFX response.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformCommonPFXResponse(Message<MessageContext> message) {

		try {
			message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.SANCTION_SERVICE);
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			Integer contactId = (Integer) registrationRequest.getAdditionalAttribute(Constants.CONTACT_ID);
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			List<Contact> contacts = registrationRequest.getAccount().getContacts();
			SanctionSummary firstSummary = (SanctionSummary) registrationRequest
					.getAdditionalAttribute(FIRST_SANCTION_SUMMARY);
			SanctionResponse fResponse = (SanctionResponse) exchange.getResponse();
			if (fResponse == null) {
				return transformServicePFXFailureResponse(message);
			}

			List<SanctionContactResponse> contactList = fResponse.getContactResponses();
			if (contactList == null) {
				contactList = createDefaultServiceDownResponse(orgID, contacts, fResponse, contactId);
				fResponse.setContactResponses(contactList);
			}
			updateSanctionServiceResponse(orgID, exchange, firstSummary, fResponse, contactList, contactId);

		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}

	/**
	 * Transform common CFX response.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformCommonCFXResponse(Message<MessageContext> message) {

		try {
			message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.SANCTION_SERVICE);
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			//For CUK Migration
			//Integer contactId = (Integer) registrationRequest.getAdditionalAttribute(Constants.CONTACT_ID);
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			List<Contact> contacts = registrationRequest.getAccount().getContacts();
			SanctionSummary firstSummary = (SanctionSummary) registrationRequest
					.getAdditionalAttribute(FIRST_SANCTION_SUMMARY);
			SanctionResponse fResponse = (SanctionResponse) exchange.getResponse();
			if (fResponse == null) {
				return transformServiceCFXFailureResponse(message);
			}

			List<SanctionContactResponse> contactList = fResponse.getContactResponses();
			if (contactList == null) {
				//contactList = createDefaultServiceDownResponse(orgID, contacts, fResponse, contactId);
				contactList = createDefaultServiceDownResponseForCFX(orgID, contacts, fResponse);
				fResponse.setContactResponses(contactList);
			}
			//For CUK Migration
			//updateSanctionServiceResponse(orgID, exchange, firstSummary, fResponse, contactList, contactId);
			updateSanctionServiceResponseForCFX(orgID, exchange, firstSummary, fResponse, contactList);

			//For CUK Migration
			/*for (Contact contact : contacts) {
				if (null != contactId && contactId.equals(contact.getId()) && !contact.isSanctionEligible()) {
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					String sanctionID = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, contact.getId() + "");
					updateEventLogs(eventServiceLog, sanctionID);
				}
			}*/
			
			for (Contact contact : contacts) {
				if (!contact.isSanctionEligible()) {
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					String sanctionID = createReferenceId(orgID,Constants.ENTITY_TYPE_CONTACT,contact.getId()+"");
					updateEventLogs(eventServiceLog, sanctionID);
				}
			}
			
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}

	/**
	 * Update sanction service response.
	 *
	 * @param orgID
	 *            the org ID
	 * @param exchange
	 *            the exchange
	 * @param firstSummary
	 *            the first summary
	 * @param fResponse
	 *            the f response
	 * @param contactList
	 *            the contact list
	 * @param contactId
	 *            the contact id
	 */
	private void updateSanctionServiceResponse(String orgID, MessageExchange exchange, SanctionSummary firstSummary,
			SanctionResponse fResponse, List<SanctionContactResponse> contactList, Integer contactId) {

		for (SanctionContactResponse response : contactList) {
			if (null != contactId && contactId.equals(response.getContactId())) {

				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
						getEntityTypeFromSanctionID(response.getSanctionId()).name(), response.getContactId());
				try {
					if (response.getErrorCode() == null) {
						handleValidSanctionResponse(firstSummary, response, eventServiceLog);
					} else {
						handleErrorSanctionResponse(orgID, fResponse, response, eventServiceLog);
					}
				} catch (Exception e) {
					LOGGER.error("Error in reading Sanction response", e);
					eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					eventServiceLog.setSummary("Unable to parse response");
				}
			}
		}
	}
	
	// added for CUK Migration
	private void updateSanctionServiceResponseForCFX(String orgID, MessageExchange exchange, SanctionSummary firstSummary,
			SanctionResponse fResponse, List<SanctionContactResponse> contactList) {
		
		for (SanctionContactResponse response : contactList) {
			
			EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
					getEntityTypeFromSanctionID(response.getSanctionId()).name(), response.getContactId());
			try {
 				if (response.getErrorCode() == null) {
					handleValidSanctionResponse(firstSummary, response, eventServiceLog);
				} else {
					handleErrorSanctionResponse(orgID, fResponse, response, eventServiceLog);
				}
			} catch (Exception e) {
				LOGGER.error("Error in reading Sanction response", e);
				eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				eventServiceLog.setSummary("Unable to parse response");
			}
		}
	}

	/**
	 * Gets the entity type from sanction ID.
	 *
	 * @param sanctionId
	 *            the sanction id
	 * @return the entity type from sanction ID
	 */
	private EntityEnum getEntityTypeFromSanctionID(String sanctionId) {
		if (sanctionId.contains("-C-")) {
			return EntityEnum.CONTACT;
		} else if (sanctionId.contains("-A-")) {
			return EntityEnum.ACCOUNT;
		} else {
			return EntityEnum.UNKNOWN;
		}
	}

	/**
	 * Handle error sanction response.
	 *
	 * @param orgID
	 *            the org ID
	 * @param fResponse
	 *            the f response
	 * @param response
	 *            the response
	 * @param eventServiceLog
	 *            the event service log
	 */
	private void handleErrorSanctionResponse(String orgID, SanctionResponse fResponse, SanctionContactResponse response,
			EventServiceLog eventServiceLog) {
		String serviceStatus;
		String sanctionID = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, response.getContactId() + "");
		if (response.getErrorCode().equals(ExternalErrorCodes.SANCTION_VALIDATION_EXCEPTION.getErrorCode())
				|| response.getErrorCode()
						.equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_REQUEST.getErrorCode())
				|| response.getErrorCode()
						.equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_RESPONSE.getErrorCode())) {
			serviceStatus = ServiceStatus.NOT_PERFORMED.name();
		} else {
			serviceStatus = ServiceStatus.SERVICE_FAILURE.name();
		}
		SanctionSummary contactSummary = createSanctionSummary(response.getOfacList(), response.getWorldCheck(),
				sanctionID, serviceStatus);
		updateResponseInEventLog(eventServiceLog, JsonConverterUtil.convertToJsonWithoutNull(fResponse), serviceStatus,
				contactSummary);
	}

	/**
	 * Handle valid sanction response.
	 *
	 * @param firstSummary
	 *            the first summary
	 * @param response
	 *            the response
	 * @param eventServiceLog
	 *            the event service log
	 */
	private void handleValidSanctionResponse(SanctionSummary firstSummary, SanctionContactResponse response,
			EventServiceLog eventServiceLog) {
		if (response.getOfacList() == null || response.getWorldCheck() == null) {
			if (null != firstSummary) {
				response.setSanctionId(firstSummary.getSanctionId());
			}
			response.setWorldCheck(Constants.NOT_AVAILABLE);
			response.setOfacList(Constants.NOT_AVAILABLE);
		}
		SanctionSummary contactSummary = createSanctionSummary(response.getOfacList(), response.getWorldCheck(),
				response.getSanctionId(), response.getStatus());
		contactSummary.setProviderName(response.getProviderName());
		contactSummary.setProviderMethod(response.getProviderMethod());
		if (Constants.NOT_PERFORMED.equalsIgnoreCase(response.getStatus())
				|| Constants.NOT_REQUIRED.equalsIgnoreCase(response.getStatus())
				|| Constants.SERVICE_FAILURE.equalsIgnoreCase(response.getStatus())) {
			contactSummary.setWorldCheck(Constants.NOT_AVAILABLE);
			contactSummary.setOfacList(Constants.NOT_AVAILABLE);
		} else if (null != firstSummary && (Constants.NOT_AVAILABLE.equalsIgnoreCase(response.getWorldCheck())
				|| Constants.NOT_AVAILABLE.equalsIgnoreCase(response.getOfacList()))) {
			contactSummary.setWorldCheck(firstSummary.getWorldCheck());
			contactSummary.setOfacList(firstSummary.getOfacList());
		}
		updateResponseInEventLog(eventServiceLog, JsonConverterUtil.convertToJsonWithoutNull(response),
				response.getStatus(), contactSummary);
	}

	/**
	 * Creates the default service down response.
	 *
	 * @param orgID
	 *            the org ID
	 * @param contacts
	 *            the contacts
	 * @param fResponse
	 *            the f response
	 * @param contactId
	 *            the contact id
	 * @return the list
	 */
	private List<SanctionContactResponse> createDefaultServiceDownResponse(String orgID, List<Contact> contacts,
			SanctionResponse fResponse, Integer contactId) {
		List<SanctionContactResponse> contactList;
		contactList = new ArrayList<>();
		for (Contact contact : contacts) {
			if (null != contactId && contactId.equals(contact.getId())) {
				SanctionContactResponse response = new SanctionContactResponse();

				response.setContactId(contact.getId());
				response.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(fResponse));
				response.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				response.setOfacList(Constants.NOT_AVAILABLE);
				response.setWorldCheck(Constants.NOT_AVAILABLE);
				String sanctionID = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT,
						response.getContactId() + "");
				response.setSanctionId(sanctionID);
				response.setErrorCode(fResponse.getErrorCode());
				response.setErrorDescription(fResponse.getErrorDescription());
				contactList.add(response);
			}
		}
		return contactList;
	}

	//added For CUK Migration
	private List<SanctionContactResponse> createDefaultServiceDownResponseForCFX(String orgID, List<Contact> contacts,
			SanctionResponse fResponse) {
		List<SanctionContactResponse> contactList;
		contactList = new ArrayList<>();
		for (Contact contact : contacts) {
			SanctionContactResponse response = new SanctionContactResponse();

			response.setContactId(contact.getId());
			response.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(fResponse));
			response.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			response.setOfacList(Constants.NOT_AVAILABLE);
			response.setWorldCheck(Constants.NOT_AVAILABLE);
			//changes done by Vishal J to set Sanction ID
			String sanctionID = createReferenceId(orgID,Constants.ENTITY_TYPE_CONTACT,response.getContactId()+"");
			response.setSanctionId(sanctionID);
			//changes end
			response.setErrorCode(fResponse.getErrorCode());
			response.setErrorDescription(fResponse.getErrorDescription());
			contactList.add(response);
		}
		return contactList;
	}

	/**
	 * Transform service CFX failure response.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformServiceCFXFailureResponse(Message<MessageContext> message) {

		try {
			message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.SANCTION_SERVICE);
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			List<SanctionContactResponse> contactResponseList = new ArrayList<>();
			SanctionResponse fResponse;
			fResponse = new SanctionResponse();
			Account account = registrationRequest.getAccount();
			Company company = registrationRequest.getAccount().getCompany();

			SanctionContactResponse sanctionAccountResponse = new SanctionContactResponse();
			if (null != company) {
				String sanctionID = createReferenceId(orgID, Constants.ENTITY_TYPE_ACCOUNT, account.getId() + "");
				sanctionAccountResponse.setSanctionId(sanctionID);
				sanctionAccountResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				sanctionAccountResponse.setContactId(account.getId());
				sanctionAccountResponse.setOfacList(Constants.NOT_AVAILABLE);
				sanctionAccountResponse.setWorldCheck(Constants.NOT_AVAILABLE);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
						EntityEnum.ACCOUNT.name(), account.getId());
				createEventServiceLogForServiceFailureWithSanctionID(eventServiceLog, sanctionAccountResponse,
						sanctionID);
			}

			fResponse.setContactResponses(contactResponseList);
			exchange.setResponse(fResponse);

		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}

	/**
	 * Transform service PFX failure response.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformServicePFXFailureResponse(Message<MessageContext> message) {

		message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.SANCTION_SERVICE);
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();
		Integer contactId = (Integer) registrationRequest.getAdditionalAttribute(Constants.CONTACT_ID);
		String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
		SanctionRequest sanctionRequest = exchange.getRequest(SanctionRequest.class);
		List<SanctionContactResponse> contactResponseList = new ArrayList<>();

		SanctionResponse fResponse = new SanctionResponse();
		if (sanctionRequest.getContactrequests() != null) {
			for (SanctionContactRequest contact : sanctionRequest.getContactrequests()) {
				if (null != contactId && contactId.equals(contact.getContactId())) {
					SanctionContactResponse contactResponse = new SanctionContactResponse();
					contactResponse.setContactId(contact.getContactId());
					String sanctionID = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT,
							contact.getContactId() + "");
					contactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					contactResponse.setOfacList(Constants.NOT_AVAILABLE);
					contactResponse.setWorldCheck(Constants.NOT_AVAILABLE);
					contactResponseList.add(contactResponse);
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
							EntityEnum.CONTACT.name(), contact.getContactId());
					createEventServiceLogForServiceFailureWithSanctionID(eventServiceLog, contactResponse, sanctionID);
				}
			}
		}

		fResponse.setContactResponses(contactResponseList);
		exchange.setResponse(fResponse);
		return message;
	}

	/**
	 * Update event logs.
	 *
	 * @param eventServiceLog
	 *            the event service log
	 * @param sanctionID
	 *            the sanction ID
	 */
	private void updateEventLogs(EventServiceLog eventServiceLog, String sanctionID) {
		SanctionSummary sanctionSummary = new SanctionSummary();
		SanctionContactResponse defaultResponse = new SanctionContactResponse();
		try {
			defaultResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(defaultResponse));
			sanctionSummary.setStatus(ServiceStatus.NOT_REQUIRED.name());
			sanctionSummary.setUpdatedOn(eventServiceLog.getCreatedOn().toString());
			sanctionSummary.setOfacList(Constants.NOT_AVAILABLE);
			sanctionSummary.setWorldCheck(Constants.NOT_AVAILABLE);
			sanctionSummary.setSanctionId(sanctionID);
			eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(sanctionSummary));
			eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			eventServiceLog.setStatus(ServiceStatus.NOT_REQUIRED.name());
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
			eventServiceLog.setProviderResponse(ServiceStatus.SERVICE_FAILURE.name());
			sanctionSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		}
	}

	/**
	 * Creates the event service log for not required.
	 *
	 * @param request
	 *            the request
	 * @param eventId
	 *            the event id
	 * @param ccExchange
	 *            the cc exchange
	 * @param token
	 *            the token
	 * @param responseContactList
	 *            the response contact list
	 * @param orgID
	 *            the org ID
	 * @param operation
	 *            the operation
	 */
	private void createEventServiceLogForNotRequired(RegistrationServiceRequest request, Integer eventId,
			MessageExchange ccExchange, UserProfile token, List<SanctionContactResponse> responseContactList,
			String orgID, OperationEnum operation) {
		Integer contactId = (Integer) request.getAdditionalAttribute(Constants.CONTACT_ID);

		try {
			String entityType;
			Account account = request.getAccount();
			if (Constants.CFX.equalsIgnoreCase(account.getCustType())
					&& OperationEnum.ADD_CONTACT!=operation) {
				entityType = EntityEnum.ACCOUNT.name();
				String sanctionID = createReferenceId(orgID, Constants.ENTITY_TYPE_ACCOUNT, account.getId() + "");

				SanctionSummary summary = createSanctionSummary(Constants.NOT_AVAILABLE, Constants.NOT_AVAILABLE,
						sanctionID, ServiceStatus.NOT_REQUIRED.name());

				SanctionContactResponse sanctionContactResponse = createCFXResponse(account,
						ServiceStatus.NOT_REQUIRED);
				sanctionContactResponse.setSanctionId(sanctionID);

				ccExchange.addEventServiceLog(createEventServiceLogEntryNotRequired(eventId,
						ServiceTypeEnum.SANCTION_SERVICE, ServiceProviderEnum.SANCTION_SERVICE, account.getId(),
						account.getVersion(), entityType, token.getUserID(), sanctionContactResponse, summary));

				responseContactList.add(sanctionContactResponse);
			}

			for (Contact contact : account.getContacts()) {
				if (null != contactId && contactId.equals(contact.getId())) {
					entityType = EntityEnum.CONTACT.name();
					String sanctionID = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, contact.getId() + "");

					SanctionSummary summary = createSanctionSummary(Constants.NOT_AVAILABLE, Constants.NOT_AVAILABLE,
							sanctionID, ServiceStatus.NOT_REQUIRED.name());

					ccExchange.addEventServiceLog(createEventServiceLogEntryNotRequired(eventId,
							ServiceTypeEnum.SANCTION_SERVICE, ServiceProviderEnum.SANCTION_SERVICE, contact.getId(),
							contact.getVersion(), entityType, token.getUserID(),
							createContactResponse(contact, ServiceStatus.NOT_REQUIRED, orgID), summary));

					responseContactList.add(createContactResponse(contact, ServiceStatus.NOT_REQUIRED, orgID));
				}
			}

		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
	}
}
