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
 * 
 *
 *The Class SanctionTransformer.
 *  
 * Responsibility : 
 * 
 * 1) This method is called from registration-flow xml / sanction-service-flow xml
 * 2) Transform registration Request which comes from CRM system(I.e. SalesForce) into Sanction request and returns
 * 3) Also it populates registration-sanction Response eventservicelog entries with provider response and summary and returns 
 * 
 */
public class SanctionTransformer extends AbstractSanctionTransformer {
	private static final String FIRST_SANCTION_SUMMARY = "firstSanctionSummary";

	private static final Logger LOGGER = LoggerFactory.getLogger(SanctionTransformer.class);

	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();

	/* (non-Javadoc)
	 * * Business -- 
	 * Sanction can be performed on Contact/Account 
	 * While sending sanction request, default status is assumed as NOT_PERFORMED
	 * And when response is received from HTTPClient, appropriate response is updated	  
	 * 
	 * 
	 * Implementation---
	 * 1) Get the messageExchange,UserProfile,registrationRequest,contact,eventId from the message
	 * 2) eventId, contact are already populated from data enricher so that we can use the same to insert records in to the table
	 * 3) Create sanction request and default SanctionContactResponse with not performed status so that if we get any exception from micro service
	 *    this default object can be pushed to database
	 * 4) if CFX type account, create Sanction requests & EventServiceLog entries for Account and all contacts, with default status of NOT_PERFORMED
	 * 	  else create Sanction requests & EventServiceLog for only Contacts, and with default status of NOT_PERFORMED
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			Account account = registrationRequest.getAccount();
			String custType =account.getCustType();
			boolean notBlackListed = true;
			/*** "PERFORM_REMAINING_CHECKS" value is set in InternalRuleServiceTransformer 
			 * 
			 * 1) If PERFORM_REMAINING_CHECKS is true means Contact is Not BlackListed and we can perform further checks like Sanction etc.
			 * 2) Else part is executed when PERFORM_REMAINING_CHECKS is false means 
			 *    Contact is found in BlackList and Further checks(Sanction,KYC) should not be performed 
			 *    but Insert entry NOT_REQUIRED for All Remaining Checks.
			 * 3) Corner Scenario : For PFX and CFX Sanction Resend Operation : Default Value is "True" 
			 *    since no initial condition like ( BlackListed or NonBlackListed )is applied " So notBlackListed variable is set to "TRUE"" .
			 *  */
			if(registrationRequest.getAdditionalAttribute(Constants.NOT_BLACKLISTED)!=null)
			{
				notBlackListed =  (boolean) registrationRequest.getAdditionalAttribute(Constants.NOT_BLACKLISTED);	
			}
			if (notBlackListed)
			{
				if(custType!=null)
				{
					custType=custType.trim();
				}
				if (Constants.CFX.equalsIgnoreCase(custType)) {
					transformCFXRequest(message);
				} else if (Constants.PFX.equalsIgnoreCase(custType)){
					transformPFXRequest(message);
				}
			}
			else
			{
				List<SanctionContactResponse> responseContactList = new ArrayList<>();
				SanctionRequest sanctionRequest = new SanctionRequest();
				/**
				 * creates EventServiceLog entry for status "NOT_REQUIRED" , by Abhijit G
				 */
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
				message.getPayload().appendMessageExchange(ccExchange);
			}
			
			
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
	
	private Message<MessageContext> transformCFXRequest(Message<MessageContext> message) throws ComplianceException {
		try {
			 MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			 OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.NEW_REGISTRATION==operation) {
				transformCFXSignupRequest(message);
			} else if (OperationEnum.ADD_CONTACT==operation)  {
				transformCFXAddContactRequest(message);
			}  else if (OperationEnum.UPDATE_ACCOUNT==operation)  {
				transformCFXUpdateRequest(message);
			} else if (OperationEnum.SANCTION_RESEND==operation)  {
				transformCFXResendRequest(message);
			}
			
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	private Message<MessageContext> transformCFXSignupRequest(Message<MessageContext> message) throws ComplianceException {
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
			List<SanctionContactResponse> responseContactList ;
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			String sanctionAccountCategory = getAccountSanctionCategory();
			
			SanctionContactResponse defaultResponse = new SanctionContactResponse();
			defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
	
			SanctionSummary summary = new SanctionSummary();
			summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			summary.setWorldCheck(Constants.NOT_AVAILABLE);
			summary.setOfacList(Constants.NOT_AVAILABLE);
			summary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
			// Not sure if update Account need sanction check for CFX
		
			SanctionContactResponse companyResponse;
			contactList = new ArrayList<>();
			responseContactList = new ArrayList<>();
			
			SanctionContactRequest comapnyContact = createCompanyCFXRequest(orgID, account, countryCache,sanctionAccountCategory);
			if(account.getCompany().isSanctionEligible()){
				 companyResponse = createCFXResponse(account,ServiceStatus.NOT_PERFORMED);
			}else{
				 companyResponse = createCFXResponse(account,ServiceStatus.NOT_REQUIRED);
			}
			
			sanctionRequest.setCustomerType(Constants.CFX);
			sanctionRequest.setCustomerNumber(account.getTradeAccountNumber());
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.SANCTION_SERVICE,
					ServiceProviderEnum.SANCTION_SERVICE,  account.getId(),
					account.getVersion(), EntityEnum.ACCOUNT.name(), token.getUserID(),
					defaultResponse, summary));

			for (Contact contact : contacts) {
				SanctionSummary contactSummary = new SanctionSummary();
				defaultResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
				StringBuilder sanctionID = new StringBuilder();
				sanctionID.append(orgID).append("-C-").append(StringUtils.leftPadWithZero(10, contact.getId()));
				contactSummary.setSanctionId(sanctionID.toString());
				contactSummary.setWorldCheck(Constants.NOT_AVAILABLE);
				contactSummary.setOfacList(Constants.NOT_AVAILABLE);
				contactSummary.setStatus(ServiceStatus.NOT_REQUIRED.name());
				ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId,
						ServiceTypeEnum.SANCTION_SERVICE, ServiceProviderEnum.SANCTION_SERVICE, contact.getId(),
						contact.getVersion(), EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, contactSummary,
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
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	private Message<MessageContext> transformCFXAddContactRequest(Message<MessageContext> message) throws ComplianceException {
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
			List<SanctionContactResponse> responseContactList ;
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			String sanctionContactCategory = getContactSanctionCategory();
			
			SanctionContactResponse defaultResponse = new SanctionContactResponse();
			defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

			SanctionSummary summary = new SanctionSummary();
			summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			summary.setWorldCheck(Constants.NOT_AVAILABLE);
			summary.setOfacList(Constants.NOT_AVAILABLE);
			summary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
	

			contactList = createContactRequest(orgID, contacts, countryCache,sanctionContactCategory);
			responseContactList = createContactResponse(contacts);
			MessageExchange ccExchange = new MessageExchange();
			for (Contact contact : contacts) {
				ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.SANCTION_SERVICE,
						ServiceProviderEnum.SANCTION_SERVICE,  contact.getId(), contact.getVersion(),
						EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, summary));

			}				
			responseContactList.clear();
			sanctionRequest.setCustomerNumber(account.getTradeAccountNumber());
			sanctionRequest.setContactrequests(contactList);
			fResponse.setContactResponses(responseContactList);		
			ccExchange.setRequest(sanctionRequest);
			ccExchange.setResponse(fResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
		
	}
	private Message<MessageContext> transformCFXUpdateRequest(Message<MessageContext> message) throws ComplianceException {
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
			List<SanctionContactResponse> responseContactList ;
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			String sanctionAccountCategory =  getAccountSanctionCategory();
			
			SanctionContactResponse defaultResponse = new SanctionContactResponse();
			defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

			SanctionSummary summary = new SanctionSummary();
			summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			summary.setWorldCheck(Constants.NOT_AVAILABLE);
			summary.setOfacList(Constants.NOT_AVAILABLE);
			summary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());					
				// Not sure if update Account need sanction check for CFX			
			SanctionContactResponse companyResponse;
			contactList = new ArrayList<>();
			responseContactList = new ArrayList<>();

			SanctionContactRequest comapnyContact = createCompanyCFXRequest(orgID, account, countryCache,sanctionAccountCategory);
			if(account.getCompany().isSanctionEligible()){
				 companyResponse = createCFXResponse(account,ServiceStatus.NOT_PERFORMED);
			}else{
				 companyResponse = createCFXResponse(account,ServiceStatus.NOT_REQUIRED);
				 //change done to set Sanction ID into company response
				 StringBuilder sanctionID = new StringBuilder();
				 sanctionID.append(orgID).append("-A-").append(StringUtils.leftPadWithZero(10, account.getId()));
				 companyResponse.setSanctionId(sanctionID.toString());
			}
			
			sanctionRequest.setCustomerType(Constants.CFX);
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.SANCTION_SERVICE,
					ServiceProviderEnum.SANCTION_SERVICE,  account.getId(),
					account.getVersion(), EntityEnum.ACCOUNT.name(), token.getUserID(),
					defaultResponse, summary));

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
					
				//Update Account set status to NOT_REQUIRED when contact.isSanctionEligible() is False : Laxmi B
			sanctionRequest.setCustomerNumber(account.getTradeAccountNumber());
			sanctionRequest.setContactrequests(contactList);
			fResponse.setContactResponses(responseContactList);
			ccExchange.setRequest(sanctionRequest);
			ccExchange.setResponse(fResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	private Message<MessageContext> transformCFXResendRequest(Message<MessageContext> message) throws ComplianceException {
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
			List<SanctionContactResponse> responseContactList ;
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			String sanctionAccountCategory =  getAccountSanctionCategory();
			String sanctionContactCategory =  getContactSanctionCategory();
			

			SanctionContactResponse defaultResponse = new SanctionContactResponse();
			defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

			SanctionSummary summary = new SanctionSummary();
			summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			summary.setWorldCheck(Constants.NOT_AVAILABLE);
			summary.setOfacList(Constants.NOT_AVAILABLE);
			summary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
	
			contactList = new ArrayList<>();
			responseContactList = new ArrayList<>();
			String entityType = (String) registrationRequest.getAdditionalAttribute("entityType");
			
			MessageExchange ccExchange = new MessageExchange();
			if (entityType.equalsIgnoreCase(EntityEnum.ACCOUNT.name())) {
				SanctionContactRequest comapnyContact = createCompanyCFXRequest(orgID, account, countryCache,sanctionAccountCategory);
				SanctionContactResponse companyResponse = createCFXResponse(account,ServiceStatus.NOT_PERFORMED);
				sanctionRequest.setCustomerType(Constants.CFX);
				ccExchange.addEventServiceLog(createSanctionResendAccountEventServiceLog(eventId, account, defaultResponse,
						summary, token));
				if (null != comapnyContact) {
					contactList.add(comapnyContact);
				}
				responseContactList.add(companyResponse);

			} else {
				contactList = createContactRequestForRegistration(orgID, contacts, countryCache,registrationRequest,sanctionContactCategory);
				responseContactList = createContactResponse(contacts);
				createSanctionResendContactEventServiceLog(contacts, eventId, ccExchange, defaultResponse,
						summary, token);
			}
			sanctionRequest.setCustomerNumber(account.getTradeAccountNumber());
			sanctionRequest.setContactrequests(contactList);
			fResponse.setContactResponses(responseContactList);
		
			ccExchange.setRequest(sanctionRequest);
			ccExchange.setResponse(fResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
		
	}
	private Message<MessageContext> transformPFXRequest(Message<MessageContext> message) throws ComplianceException{
		
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			 OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.NEW_REGISTRATION==operation) {
				transformPFXSignupRequest(message);
			} else if (OperationEnum.ADD_CONTACT==operation)  {
				transformPFXAddContactRequest(message);
			}  else if (OperationEnum.UPDATE_ACCOUNT==operation)  {
				transformPFXUpdateRequest(message);
			} else if (OperationEnum.SANCTION_RESEND==operation)  {
				transformPFXResendRequest(message);
			}
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
		
	}	
	private Message<MessageContext> transformPFXSignupRequest(Message<MessageContext> message) throws ComplianceException {
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
			List<SanctionContactResponse> responseContactList ;
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			String sanctionContactCategory =  getContactSanctionCategory();
			
			SanctionContactResponse defaultResponse = new SanctionContactResponse();
			defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
	
			SanctionSummary summary = new SanctionSummary();
			summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			summary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
			contactList = createContactRequest(orgID, contacts, countryCache,sanctionContactCategory);
			responseContactList = createContactResponse(contacts);
			MessageExchange ccExchange = new MessageExchange();
			for (Contact contact : contacts) {
				ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.SANCTION_SERVICE,
						ServiceProviderEnum.SANCTION_SERVICE,  contact.getId(), contact.getVersion(),
						EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, summary));
	
			}
			sanctionRequest.setCustomerNumber(account.getTradeAccountNumber());
			sanctionRequest.setContactrequests(contactList);
			fResponse.setContactResponses(responseContactList);
			
			ccExchange.setRequest(sanctionRequest);
			ccExchange.setResponse(fResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}	
	private Message<MessageContext> transformPFXAddContactRequest(Message<MessageContext> message) throws ComplianceException {
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
			List<SanctionContactResponse> responseContactList ;
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			String sanctionContactCategory =  getContactSanctionCategory();
			
			SanctionContactResponse defaultResponse = new SanctionContactResponse();
			defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

			SanctionSummary summary = new SanctionSummary();
			summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			summary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
			contactList = createContactRequest(orgID, contacts, countryCache,sanctionContactCategory);
			responseContactList = createContactResponse(contacts);
			MessageExchange ccExchange = new MessageExchange();
			for (Contact contact : contacts) {
				ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.SANCTION_SERVICE,
						ServiceProviderEnum.SANCTION_SERVICE,  contact.getId(), contact.getVersion(),
						EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, summary));

			}
			sanctionRequest.setCustomerNumber(account.getTradeAccountNumber());
			sanctionRequest.setContactrequests(contactList);
			fResponse.setContactResponses(responseContactList);			
			ccExchange.setRequest(sanctionRequest);
			ccExchange.setResponse(fResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception exception) {
			LOGGER.debug(Constants.ERROR, exception);
		 }
		return message;
	}
	
	private Message<MessageContext> transformPFXUpdateRequest(Message<MessageContext> message) throws ComplianceException {
		
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
			List<SanctionContactResponse> responseContactList = new ArrayList<>();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			String sanctionContactCategory =  getContactSanctionCategory();
						
			///Update Account set status to NOT_REQUIRED when contact.isSanctionEligible() is False : Laxmi B			
			contactList = createContactRequest(orgID, contacts, countryCache,sanctionContactCategory);
			MessageExchange ccExchange = new MessageExchange();
			ServiceStatus status = ServiceStatus.NOT_PERFORMED;
			for (Contact contact : contacts) {
				if(!contact.isSanctionEligible()){
					status = ServiceStatus.NOT_REQUIRED;
				}
				String sanctionId=createReferenceId(registrationRequest.getOrgId().toString(),
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
				ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.SANCTION_SERVICE,
						ServiceProviderEnum.SANCTION_SERVICE,  contact.getId(), contact.getVersion(),
						EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, summary, status));
			}
			sanctionRequest.setCustomerNumber(account.getTradeAccountNumber());
			sanctionRequest.setContactrequests(contactList);
			fResponse.setContactResponses(responseContactList);
		
			ccExchange.setRequest(sanctionRequest);
			ccExchange.setResponse(fResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	private Message<MessageContext> transformPFXResendRequest(Message<MessageContext> message) throws ComplianceException {
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
			List<SanctionContactResponse> responseContactList ;
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			String sanctionContactCategory =  getContactSanctionCategory();
			
			SanctionContactResponse defaultResponse = new SanctionContactResponse();
			defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

			SanctionSummary summary = new SanctionSummary();
			summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			summary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
				
			contactList = createContactRequestForRegistration(orgID, contacts, countryCache,registrationRequest,sanctionContactCategory);
			responseContactList = createContactResponse(contacts);
			MessageExchange ccExchange = new MessageExchange();
			createSanctionResendContactEventServiceLog(contacts, eventId, ccExchange, defaultResponse,
					summary, token);	
			
			sanctionRequest.setCustomerNumber(account.getTradeAccountNumber());
			sanctionRequest.setContactrequests(contactList);
			fResponse.setContactResponses(responseContactList);			
		
			ccExchange.setRequest(sanctionRequest);
			ccExchange.setResponse(fResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	
	/* (non-Javadoc)
	 *  
	 * Implementation---
	 * Once Sanction micro service call is performed, this method is invoked to update EventServiceLog Entry
	 * If call to micro service and provider is successful, update response in EventServiceLog entry for each contact
	 * if failed, 
	 * 		update the status to SERVICE_FAILURE, if Sanction service is down/failed
	 * 
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		try{
		RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();
			String custType =registrationRequest.getAccount().getCustType();
			if (Constants.CFX.equalsIgnoreCase(custType.trim())) {
				transformCFXResponse(message);
			} else if (Constants.PFX.equalsIgnoreCase(custType.trim())){
				transformPFXResponse(message);
			}
		} catch(Exception e){
			LOGGER.error("Exception in transformResponse() of Sanction Transformer", e);
			message.getPayload().setFailed(true);
		}
	return message;
		
	}
	
	private Message<MessageContext> transformPFXResponse(Message<MessageContext> message) {
		
		try{
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.NEW_REGISTRATION==operation) {
				transformPFXSignupResponse(message);
			} else if (OperationEnum.ADD_CONTACT==operation)  {
				transformPFXAddContactResponse(message);
			}  else if (OperationEnum.UPDATE_ACCOUNT==operation)  {
				transformPFXUpdateResponse(message);
			} else if (OperationEnum.SANCTION_RESEND==operation)  {
				transformPFXResendResponse(message);
			}
		}catch(Exception e){
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	
	private Message<MessageContext> transformPFXSignupResponse(Message<MessageContext> messageSignup) {
		transformCommonPFXResponse(messageSignup);
		return messageSignup;
	}
	
	private Message<MessageContext> transformPFXAddContactResponse(Message<MessageContext> messageAddContact) {
		transformCommonPFXResponse(messageAddContact);
		return messageAddContact;
	}
	
	private Message<MessageContext> transformPFXUpdateResponse(Message<MessageContext> messageUpdate) {
		transformCommonPFXResponse(messageUpdate);
		return messageUpdate;
	}
	
	private Message<MessageContext> transformPFXResendResponse(Message<MessageContext> messageResend) {
		transformCommonPFXResponse(messageResend);
		return messageResend;
	}
	
	private Message<MessageContext> transformCFXResponse(Message<MessageContext> message) {
		
		try{
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
	 		 OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.NEW_REGISTRATION==operation) {
				transformCFXSignupResponse(message);
			} else if (OperationEnum.ADD_CONTACT==operation)  {
				transformCFXAddContactResponse(message);
			}  else if (OperationEnum.UPDATE_ACCOUNT==operation)  {
				transformCFXUpdateResponse(message);
			} else if (OperationEnum.SANCTION_RESEND==operation)  {
				transformCFXResendResponse(message);
			}
		}catch(Exception e){
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	private Message<MessageContext> transformCFXSignupResponse(Message<MessageContext> messageSignup) {
		transformCommonCFXResponse(messageSignup);
		return messageSignup;
	}
	
	private Message<MessageContext> transformCFXAddContactResponse(Message<MessageContext> messageAddContact) {
		transformCommonCFXResponse(messageAddContact);
		return messageAddContact;
	}
	
	private Message<MessageContext> transformCFXUpdateResponse(Message<MessageContext> messageUpdate) {
		transformCommonCFXResponse(messageUpdate);
		return messageUpdate;
	}
	
	/**
	 * This method will get call if user clicks on 'Repeat check' button for SANCTION.
	 * At a time, user can perform repeat check either for ACCOUNT or CONTACT so we are separating functionality 
	 * for them based on entity type.
	 * */
	private Message<MessageContext> transformCFXResendResponse(Message<MessageContext> message) {
		
		try{
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			if("ACCOUNT".equals(registrationRequest.getAdditionalAttribute(Constants.ENTITY_TYPE))){
				transformRepeatCheckResponseAccount(message);
			}
			if("CONTACT".equals(registrationRequest.getAdditionalAttribute(Constants.ENTITY_TYPE))){
				transformRepeatCheckResponseContact(message);
			}
		}catch(Exception e){
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	
	private Message<MessageContext> transformCommonPFXResponse(Message<MessageContext> message) {
		
		try {
			message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.SANCTION_SERVICE);
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());		
			List<Contact> contacts = registrationRequest.getAccount().getContacts();			
			SanctionSummary firstSummary = (SanctionSummary) registrationRequest.getAdditionalAttribute(FIRST_SANCTION_SUMMARY);
			SanctionResponse fResponse = (SanctionResponse) exchange.getResponse();
			//When Micro Service is down we get fResponse as null 
			if (fResponse == null) {
				return transformServicePFXFailureResponse(message);
			}

			List<SanctionContactResponse> contactList = fResponse.getContactResponses();
			if (contactList == null) {
				contactList = createDefaultServiceDownResponse(orgID, contacts, fResponse);
				fResponse.setContactResponses(contactList);
			}
			updateSanctionServiceResponse(orgID, exchange, firstSummary, fResponse, contactList);
			
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	
	private Message<MessageContext> transformCommonCFXResponse(Message<MessageContext> message) {

		try {
			message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.SANCTION_SERVICE);
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
 			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			List<Contact> contacts = registrationRequest.getAccount().getContacts();
			SanctionSummary firstSummary = (SanctionSummary) registrationRequest
					.getAdditionalAttribute(FIRST_SANCTION_SUMMARY);
			SanctionResponse fResponse = (SanctionResponse) exchange.getResponse();
			// When Micro Service is down we get fResponse as null
			if (fResponse == null) {
				return transformServiceCFXFailureResponse(message);
			}

			List<SanctionContactResponse> contactList = fResponse.getContactResponses();
			if (contactList == null) {
				contactList = createDefaultServiceDownResponse(orgID, contacts, fResponse);
				fResponse.setContactResponses(contactList);
			}
			updateSanctionServiceResponse(orgID, exchange, firstSummary, fResponse, contactList);

			for (Contact contact : contacts) {
				if (!contact.isSanctionEligible()) {
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					//change done by Vishal J
					//to show sanction ID on UI instead of 'NOT AVAILABLE'
					String sanctionID = createReferenceId(orgID,Constants.ENTITY_TYPE_CONTACT,contact.getId()+"");
					//end of change
					/*
					 * parameter 'sanctionID' is added to this method to send
					 * * that sanction ID to set into summary
					 */
					updateEventLogs(eventServiceLog, sanctionID);
				}
			}
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	
	private Message<MessageContext> transformRepeatCheckResponseAccount(Message<MessageContext> message) {

		try {
			message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.SANCTION_SERVICE);
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			List<Contact> contacts = registrationRequest.getAccount().getContacts();
			SanctionSummary firstSummary = (SanctionSummary) registrationRequest
					.getAdditionalAttribute(FIRST_SANCTION_SUMMARY);
			SanctionResponse fResponse = (SanctionResponse) exchange.getResponse();
			// When Micro Service is down we get fResponse as null
			if (fResponse == null) {
				return setAccountFailureResponse(message, exchange, registrationRequest, orgID);
			}

			List<SanctionContactResponse> contactList = fResponse.getContactResponses();
			if (contactList == null) {
				contactList = createDefaultServiceDownResponse(orgID, contacts, fResponse);
				fResponse.setContactResponses(contactList);
			}
			updateSanctionServiceResponse(orgID, exchange, firstSummary, fResponse, contactList);

			for (Contact contact : contacts) {
				if (!contact.isSanctionEligible()) {
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					// change done by Vishal J
					// to show sanction ID on UI instead of 'NOT AVAILABLE'
					String sanctionID = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, contact.getId() + "");
					// end of change
					/*
					 * parameter 'sanctionID' is added to this method to send *
					 * that sanction ID to set into summary
					 */
					updateEventLogs(eventServiceLog, sanctionID);
				}
			}
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}


	private Message<MessageContext> setAccountFailureResponse(Message<MessageContext> message, MessageExchange exchange,
			RegistrationServiceRequest registrationRequest, String orgID) {
		SanctionResponse sResponse;
		sResponse = new SanctionResponse();
		/**Add service failure response for Entity Type Account*/
		Account account = registrationRequest.getAccount();
		
		/*Response For Account SERVICE_FAILURE*/
		SanctionContactResponse sanctionAccountResponse = new SanctionContactResponse();
		String sanctionID = createReferenceId(orgID,Constants.ENTITY_TYPE_ACCOUNT,account.getId()+"");
		sanctionAccountResponse.setSanctionId(sanctionID);
		sanctionAccountResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		sanctionAccountResponse.setContactId(account.getId());
		EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.ACCOUNT.name(), account.getId());
		createEventServiceLogForServiceFailureWithSanctionID(eventServiceLog, sanctionAccountResponse, sanctionID);
		
		exchange.setResponse(sResponse);
		return message;
	}
	
	private Message<MessageContext> transformRepeatCheckResponseContact(Message<MessageContext> message) {

		try {
			message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.SANCTION_SERVICE);
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			List<Contact> contacts = registrationRequest.getAccount().getContacts();
			SanctionSummary firstSummary = (SanctionSummary) registrationRequest
					.getAdditionalAttribute(FIRST_SANCTION_SUMMARY);
			SanctionResponse fResponse = (SanctionResponse) exchange.getResponse();
			// When Micro Service is down we get fResponse as null
			if (fResponse == null) {
				return setContactFailureResponse(message, exchange, orgID);
			}

			List<SanctionContactResponse> contactList = fResponse.getContactResponses();
			if (contactList == null) {
				contactList = createDefaultServiceDownResponse(orgID, contacts, fResponse);
				fResponse.setContactResponses(contactList);
			}
			updateSanctionServiceResponse(orgID, exchange, firstSummary, fResponse, contactList);

			for (Contact contact : contacts) {
				if (!contact.isSanctionEligible()) {
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					// change done by Vishal J
					// to show sanction ID on UI instead of 'NOT AVAILABLE'
					String sanctionID = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, contact.getId() + "");
					// end of change
					/*
					 * parameter 'sanctionID' is added to this method to send *
					 * that sanction ID to set into summary
					 */
					updateEventLogs(eventServiceLog, sanctionID);
				}
			}
		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}


	/**
	 * Sets the contact failure response.
	 *
	 * @param message the message
	 * @param exchange the exchange
	 * @param orgID the org ID
	 * @return the message
	 */
	private Message<MessageContext> setContactFailureResponse(Message<MessageContext> message, MessageExchange exchange,
			String orgID) {
		SanctionRequest sanctionRequest = exchange.getRequest(SanctionRequest.class);
		List<SanctionContactResponse> contactResponseList = new ArrayList<>();
		SanctionResponse sResponse;
		sResponse = new SanctionResponse();
		/*Response For Contact SERVICE_FAILURE*/
		if (sanctionRequest.getContactrequests() != null) {
			for (SanctionContactRequest contact : sanctionRequest.getContactrequests()) {
				SanctionContactResponse contactResponse = new SanctionContactResponse();
				contactResponse.setContactId(contact.getContactId());
				//changes done by Vishal J to set Sanction ID
				String sanctionID = createReferenceId(orgID,Constants.ENTITY_TYPE_CONTACT,contact.getContactId()+"");
				contactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				contactResponseList.add(contactResponse);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
						EntityEnum.CONTACT.name(), contact.getContactId());
				//changes done by Vishal J to set Sanction ID
				createEventServiceLogForServiceFailureWithSanctionID(eventServiceLog, contactResponse, sanctionID);
			}
		}

		sResponse.setContactResponses(contactResponseList);
		exchange.setResponse(sResponse);
		return message;
	}
	
	/**
	 * @param orgID
	 * @param logs
	 * @param firstSummary
	 * @param fResponse
	 * @param contactList
	 */
	private void updateSanctionServiceResponse(String orgID, MessageExchange exchange, SanctionSummary firstSummary,
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
	 * Sanction Service does not respond with EntityType, 
	 * hence determine it based on SanctiondID
	 * @param sanctionId the sanction id
	 * @return the entity type from sanction ID
	 * 
	 */
	private EntityEnum getEntityTypeFromSanctionID(String sanctionId){
		if(sanctionId.contains("-C-")){
			return EntityEnum.CONTACT;
		}else if(sanctionId.contains("-A-")){
			return EntityEnum.ACCOUNT;
		}else {
			return EntityEnum.UNKNOWN;
		}
	}

	/**
	 * 1)When third party service is down then sanction response is SERVICE_FAILURE
	 * 2)In other failure condition sanction response set to NOT_PERFORMED 
	 * 
	 * 
	 * @param orgID
	 * @param fResponse
	 * @param response
	 * @param eventServiceLog
	 */
	private void handleErrorSanctionResponse(String orgID, SanctionResponse fResponse,
			SanctionContactResponse response, EventServiceLog eventServiceLog) {
		String serviceStatus;
		//change done by Vishal J
		//to show sanction ID on UI instead of 'NOT AVAILABLE'
		String sanctionID = createReferenceId(orgID,Constants.ENTITY_TYPE_CONTACT,response.getContactId()+"");
		//end of change
		/*parameter 'sanctionID' is added to this method to send 
		 * that sanction ID to set into	summary*/
		if (response.getErrorCode()
				.equals(ExternalErrorCodes.SANCTION_VALIDATION_EXCEPTION.getErrorCode())
				|| response.getErrorCode()
						.equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_REQUEST
								.getErrorCode())
				|| response.getErrorCode()
						.equals(ExternalErrorCodes.SANCTION_ERROR_WHILE_TRANSFORMATION_RESPONSE
								.getErrorCode())) {
			serviceStatus = ServiceStatus.NOT_PERFORMED.name();
		} else {
			serviceStatus = ServiceStatus.SERVICE_FAILURE.name();
		}
		//passing sanctionID parameter to set it into summary
		SanctionSummary contactSummary = createSanctionSummary(response.getOfacList(),
				response.getWorldCheck(), sanctionID, serviceStatus);
		updateResponseInEventLog(eventServiceLog,
				JsonConverterUtil.convertToJsonWithoutNull(fResponse), serviceStatus,
				contactSummary);
	}


	/**
	 * Added changes if Status is NOT_REQUIRED then set values of WorldCheck and OfacList
	 *  as NOT_AVAILBALE
	 * Changes made by -Saylee
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
	 * @param orgID
	 * @param contacts
	 * @param fResponse
	 * @return
	 */
	private List<SanctionContactResponse> createDefaultServiceDownResponse(String orgID, List<Contact> contacts,
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
	 * @param exchange
	 * @param orgID
	 * @param sanctionRequest
	 * @param logs
	 * @param contactResponseList
	 * @param bankResponseList
	 * @param beneficiaryResponseList
	 * @return
	 */
	private Message<MessageContext>  transformServiceCFXFailureResponse(Message<MessageContext> message) {
		
		try{
			message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.SANCTION_SERVICE);
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			// orgID is fetched from Registrationrequest
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			List<SanctionContactResponse> contactResponseList = new ArrayList<>();
			SanctionResponse fResponse;
			fResponse = new SanctionResponse();
			/**Add service failure response for Entity Type Account*/
			Account account = registrationRequest.getAccount();
			Company company = registrationRequest.getAccount().getCompany();
			
			/*Response For Account SERVICE_FAILURE*/
			SanctionContactResponse sanctionAccountResponse = new SanctionContactResponse();
			if (null != company) {
				String sanctionID = createReferenceId(orgID,Constants.ENTITY_TYPE_ACCOUNT,account.getId()+"");
				sanctionAccountResponse.setSanctionId(sanctionID);
				sanctionAccountResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				sanctionAccountResponse.setContactId(account.getId());
				sanctionAccountResponse.setOfacList(Constants.NOT_AVAILABLE);
				sanctionAccountResponse.setWorldCheck(Constants.NOT_AVAILABLE);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.ACCOUNT.name(), account.getId());
				createEventServiceLogForServiceFailureWithSanctionID(eventServiceLog, sanctionAccountResponse, sanctionID);
			}
			
			fResponse.setContactResponses(contactResponseList);
			exchange.setResponse(fResponse);
			 
		}catch(Exception e){
			LOGGER.debug(Constants.ERROR, e);
		}
		return message;
	}
	
	
	
	/**
	 * @param exchange
	 * @param orgID
	 * @param sanctionRequest
	 * @param logs
	 * @param contactResponseList
	 * @param bankResponseList
	 * @param beneficiaryResponseList
	 * @return
	 */
	private Message<MessageContext>  transformServicePFXFailureResponse(Message<MessageContext> message) {
		
		message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.SANCTION_SERVICE);
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();
		String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
		SanctionRequest sanctionRequest = exchange.getRequest(SanctionRequest.class);
		List<SanctionContactResponse> contactResponseList = new ArrayList<>();
		
		SanctionResponse fResponse= new SanctionResponse();
		if (sanctionRequest.getContactrequests() != null) {
			for (SanctionContactRequest contact : sanctionRequest.getContactrequests()) {
				SanctionContactResponse contactResponse = new SanctionContactResponse();
				contactResponse.setContactId(contact.getContactId());
				//changes done by Vishal J to set Sanction ID
				String sanctionID = createReferenceId(orgID,Constants.ENTITY_TYPE_CONTACT,contact.getContactId()+"");
				contactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				contactResponse.setOfacList(Constants.NOT_AVAILABLE);
				contactResponse.setWorldCheck(Constants.NOT_AVAILABLE);
				contactResponseList.add(contactResponse);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.CONTACT.name(), contact.getContactId());
				//changes done by Vishal J to set Sanction ID
				createEventServiceLogForServiceFailureWithSanctionID(eventServiceLog, contactResponse, sanctionID);
			}
		}

		fResponse.setContactResponses(contactResponseList);
		exchange.setResponse(fResponse);
		return message;
	}
	
	/*
	 * Parameter 'StringBuilder sanctionID' is added to this method to show sanction ID on UI and setting
	 * it into summary object. 
	 * changes done by Vishal J
	 */
	private void updateEventLogs( EventServiceLog eventServiceLog, String sanctionID) {
		SanctionSummary sanctionSummary = new SanctionSummary();
		SanctionContactResponse defaultResponse = new SanctionContactResponse();
		try {
			defaultResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(defaultResponse));
			sanctionSummary.setStatus(ServiceStatus.NOT_REQUIRED.name());
			sanctionSummary.setUpdatedOn(eventServiceLog.getCreatedOn().toString());
			sanctionSummary.setOfacList(Constants.NOT_AVAILABLE);
			sanctionSummary.setWorldCheck(Constants.NOT_AVAILABLE);
			//setting that sanction ID into summary
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
	
	private EventServiceLog createSanctionResendAccountEventServiceLog(Integer eventId, Account account,
			SanctionContactResponse defaultResponse, SanctionSummary summary, UserProfile token) {
		return createEventServiceLogEntry(eventId, ServiceTypeEnum.SANCTION_SERVICE,
				ServiceProviderEnum.SANCTION_SERVICE, account.getId(), account.getVersion(), EntityEnum.ACCOUNT.name(),
				token.getUserID(), defaultResponse, summary);
	}
	
	private void createSanctionResendContactEventServiceLog(List<Contact> contacts, Integer eventId,
			MessageExchange ccExchange, SanctionContactResponse defaultResponse, SanctionSummary summary,
			UserProfile token) {
		try {
			for (Contact contact : contacts) {
				ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.SANCTION_SERVICE,
						ServiceProviderEnum.SANCTION_SERVICE, contact.getId(), contact.getVersion(),
						EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, summary));
			}
		} catch (Exception e) {
			LOGGER.error("Error", e);
		}
	}

	/**
	 *  Creates EventServiceLog entry for NOT_REQUIRED status, if performRemainingCheck is false. 
	 *  if custType is CFX then create EventServiceLog entry for entity type Account and Contact.
	 *  if custType is PFX then create EventServiceLog entry for only entity type Contact. 
	 * @author abhijeet g
	 * @param request
	 * @param eventId
	 * @param eventServiceLogs
	 * @param token
	 * @param responseContactList
	 */
	private void createEventServiceLogForNotRequired(RegistrationServiceRequest request,Integer eventId, MessageExchange ccExchange,
			UserProfile token, List<SanctionContactResponse> responseContactList,String orgID,OperationEnum operation) {

		try {
			String entityType ;
			Account account = request.getAccount();
			if (Constants.CFX.equalsIgnoreCase(account.getCustType()) && (OperationEnum.ADD_CONTACT!=operation)) {
				entityType = EntityEnum.ACCOUNT.name();
				String sanctionID = createReferenceId(orgID,Constants.ENTITY_TYPE_ACCOUNT,account.getId()+"");
				
				SanctionSummary summary = createSanctionSummary(Constants.NOT_AVAILABLE, Constants.NOT_AVAILABLE,
						sanctionID, ServiceStatus.NOT_REQUIRED.name());
				
				SanctionContactResponse sanctionContactResponse = createCFXResponse(account, ServiceStatus.NOT_REQUIRED);
				sanctionContactResponse.setSanctionId(sanctionID);
				
				ccExchange.addEventServiceLog(createEventServiceLogEntryNotRequired(eventId, ServiceTypeEnum.SANCTION_SERVICE,
						ServiceProviderEnum.SANCTION_SERVICE, account.getId(), account.getVersion(), entityType,
						token.getUserID(),sanctionContactResponse , summary));
				
				responseContactList.add(sanctionContactResponse);
			} 
			
			for (Contact contact : account.getContacts()) {
				entityType = EntityEnum.CONTACT.name();
				String sanctionID = createReferenceId(orgID,Constants.ENTITY_TYPE_CONTACT,contact.getId()+"");
				
				SanctionSummary summary = createSanctionSummary(Constants.NOT_AVAILABLE, Constants.NOT_AVAILABLE,
						sanctionID, ServiceStatus.NOT_REQUIRED.name());
				
				ccExchange.addEventServiceLog(createEventServiceLogEntryNotRequired(eventId, ServiceTypeEnum.SANCTION_SERVICE,
						ServiceProviderEnum.SANCTION_SERVICE, contact.getId(), contact.getVersion(), entityType,
						token.getUserID(), createContactResponse(contact, ServiceStatus.NOT_REQUIRED,orgID), summary));
				
				responseContactList.add(createContactResponse(contact, ServiceStatus.NOT_REQUIRED,orgID));
			}

		} catch (Exception e) {
			LOGGER.debug(Constants.ERROR, e);
		}
	}
	
	 /**
	  *Implementation :-
	  * 1.Get contact list and check the condition for SanctionEligible or not
	  * 2.Get the firstSanctionSummary and 
	  * 	set values for OfacList,WorldCheck and Status from that summary -Saylee
	 * @param sanctionContactCategory 
	  * */
	private List<SanctionContactRequest> createContactRequestForRegistration(String orgID, List<Contact> contacts,
			ProvideCacheLoader cacheLoader,RegistrationServiceRequest registrationRequest, String category) throws ComplianceException {
		List<SanctionContactRequest> contactList = new ArrayList<>();
		
		for (Contact contact : contacts) {
			if (contact.isSanctionEligible()) {
				String countryName = cacheLoader
						.getCountryFullName(null != contact.getCountry() ? contact.getCountry() : "");
				SanctionSummary summary = (SanctionSummary) registrationRequest.getAdditionalAttribute(FIRST_SANCTION_SUMMARY);
				
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
				if(null != summary){
					sanctionContact.setPreviousOfac(summary.getOfacList());
					sanctionContact.setPreviousWorldCheck(summary.getWorldCheck());
					sanctionContact.setPreviousStatus(summary.getStatus());
					}
				contactList.add(sanctionContact);
			}
		}
		return contactList;
	}
}

