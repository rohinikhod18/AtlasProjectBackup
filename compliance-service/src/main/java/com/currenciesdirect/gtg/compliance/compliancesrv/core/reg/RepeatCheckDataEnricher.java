package com.currenciesdirect.gtg.compliance.compliancesrv.core.reg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KycResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.AbstractDataEnricher;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * The Class RepeatCheckDataEnricher.
 */
public class RepeatCheckDataEnricher extends AbstractDataEnricher{
	
	/** The event DB service impl. */
	@Autowired
	private IEventDBService eventDBServiceImpl;
	
	/**
	 * Enrich data.
	 *
	 * @param message            the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> enrichData(Message<MessageContext> message) throws ComplianceException {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
		OperationEnum operation = messageExchange.getOperation();
		getUserTableId(message);
		if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.SANCTION_RESEND) {
			enrichDataSanctionResend(message);
		}

		if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.KYC_RESEND) {
			enrichDataKycResend(message);
		}
		
		if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.BLACKLIST_RESEND) {
			enrichDataBlacklistResend(message);
		}

		return message;
	}

	/**
	 * Gets the registration request.
	 *
	 * @param entityId the entity id
	 * @param accountId the account id
	 * @return the registration request
	 * @throws ComplianceException the compliance exception
	 */
	private RegistrationServiceRequest getRegistrationRequest(Integer entityId, Integer accountId)
			throws ComplianceException {
			return newRegistrationDBServiceImpl.getRegistrationRequestByContactId(entityId, accountId);
	}
	
	/**
	 * Gets the registration request.
	 *
	 * @param entityId the entity id
	 * @param accountId the account id
	 * @param orgCode the org code
	 * @return the registration request
	 * @throws ComplianceException the compliance exception
	 */
	private RegistrationServiceRequest getRegistrationRequest(Integer entityId, Integer accountId, String orgCode)
			throws ComplianceException {
			return newRegistrationDBServiceImpl.getRegistrationRequestByAccountID(entityId, accountId,orgCode);
	}

	/**
	 * Gets the service status.
	 *
	 * @param entityId the entity id
	 * @param serviceName the service name
	 * @param entityType the entity type
	 * @return the service status
	 * @throws ComplianceException the compliance exception
	 */
	private EntityDetails getServiceStatus(Integer entityId, String serviceName, String entityType)
			throws ComplianceException {
		EntityDetails entityDetails = new EntityDetails();
		if(ServiceTypeEnum.SANCTION_SERVICE.getShortName().equalsIgnoreCase(serviceName))
		 return eventDBServiceImpl.getPreviousSanctionDetails(entityId, entityType);
		else if(ServiceTypeEnum.KYC_SERVICE.getShortName().equalsIgnoreCase(serviceName))
		 return eventDBServiceImpl.isKycServicePerformed(entityId, entityType);
		else if(ServiceTypeEnum.FRAUGSTER_SERVICE.getShortName().equalsIgnoreCase(serviceName))
			 return eventDBServiceImpl.getPreviousFraugsterDetails(entityId, entityType);
		else if(ServiceTypeEnum.BLACK_LIST_SERVICE.getShortName().equalsIgnoreCase(serviceName))
			 return eventDBServiceImpl.getPreviousBlacklistDetails(entityId, entityType);
		else return entityDetails;
	}

	/**
	 * Enrich data sanction resend.
	 *
	 * @param message the message
	 * @throws ComplianceException the compliance exception
	 */
	private void enrichDataSanctionResend(Message<MessageContext> message) throws ComplianceException {
		SanctionResendRequest request = message.getPayload().getGatewayMessageExchange()
				.getRequest(SanctionResendRequest.class);
		RegistrationServiceRequest regRequest;
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		
		if (null != request.getEntityType() && (EntityEnum.ACCOUNT.name()).equalsIgnoreCase(request.getEntityType())) {
			regRequest = getRegistrationRequest(request.getEntityId(), request.getAccountId(),  request.getOrgCode());
			EntityDetails entityDetails = getServiceStatus(request.getEntityId(), ServiceTypeEnum.SANCTION_SERVICE.getShortName(), request.getEntityType());
			entityDetails.setAccountName(regRequest.getAccount().getAccountName());
			regRequest.addAttribute(Constants.ENTITY_TYPE, EntityEnum.ACCOUNT.name());
			regRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS,entityDetails);
			regRequest.getAccount().getCompany().setSanctionPerformed(entityDetails.getIsExist());
		} else {
			regRequest = getRegistrationRequest(request.getEntityId(), request.getAccountId());
			EntityDetails entityDetails = getServiceStatus(request.getEntityId(), ServiceTypeEnum.SANCTION_SERVICE.getShortName(), request.getEntityType());
			entityDetails.setContactName(regRequest.getAccount().getContacts().get(0).getFullName());
			regRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS,entityDetails);
			regRequest.addAttribute(Constants.ENTITY_TYPE, EntityEnum.CONTACT.name());
			regRequest.getAccount().getContacts().get(0).setSanctionPerformed(entityDetails.getIsExist());
		}
		/*
		 * We fetch user ID here by getting it from database according to user name
		 * Changes done by Vishal J
		 * */
		Integer userID = newRegistrationDBServiceImpl.getUserIDFromSSOUserID(token.getPreferredUserName());
		//setting appropriate user id into UserProfile token
		token.setUserID(userID);
		SanctionSummary sanctionSummary = newRegistrationDBServiceImpl.getFirstSanctionSummary(request.getEntityId(), request.getEntityType());
		regRequest.addAttribute("firstSanctionSummary", sanctionSummary);
		if(null == regRequest.getOldOrgId() || 0 == regRequest.getOldOrgId()) {
			regRequest.setOrgId(request.getOrgId());
		} else {
			regRequest.setOrgId(regRequest.getOldOrgId());
		}
		message.getPayload().getGatewayMessageExchange().setRequest(regRequest);
		message.getPayload().getGatewayMessageExchange().getRequest().addAttribute("sanctionResendRequest", request);
	}

	/**
	 * Enrich data kyc resend.
	 *
	 * @param message the message
	 * @throws ComplianceException the compliance exception
	 */
	private void enrichDataKycResend(Message<MessageContext> message) throws ComplianceException {
		KycResendRequest request = message.getPayload().getGatewayMessageExchange().getRequest(KycResendRequest.class);
		RegistrationServiceRequest regRequest = getRegistrationRequest(request.getContactId(), request.getAccountId());
		EntityDetails entityDetails = getServiceStatus(request.getContactId(), "KYC", EntityEnum.CONTACT.name());
		if(0 == regRequest.getOldOrgId()) {
			regRequest.setOrgId(request.getOrgId());
		} else {
			regRequest.setOrgId(regRequest.getOldOrgId());
		}
		regRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS,entityDetails);
		message.getPayload().getGatewayMessageExchange().setRequest(regRequest);
		message.getPayload().getGatewayMessageExchange().getRequest().addAttribute("kycResendRequest", request);
	}
	
	/**
	 * Enrich data fraugster resend.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext>  enrichDataFraugsterResend(Message<MessageContext> message) throws ComplianceException {
		FraugsterResendRequest request = message.getPayload().getGatewayMessageExchange()
				.getRequest(FraugsterResendRequest.class);
		RegistrationServiceRequest regRequest;
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		List<Contact> oldContacts;
		
		if (null != request.getEntityType() && (EntityEnum.ACCOUNT.name()).equalsIgnoreCase(request.getEntityType())) {
			regRequest = getRegistrationRequest(request.getEntityId(), request.getAccountId(),  request.getOrgCode());
			EntityDetails entityDetails = getServiceStatus(request.getEntityId(), ServiceTypeEnum.FRAUGSTER_SERVICE.getShortName(), request.getEntityType());
			entityDetails.setAccountName(regRequest.getAccount().getAccountName());
			regRequest.addAttribute(Constants.ENTITY_TYPE, EntityEnum.ACCOUNT.name());
			regRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS,entityDetails);
			regRequest.getAccount().getCompany().setFraugsterPerformed(entityDetails.getIsExist());
		} else {
			regRequest = getRegistrationRequest(request.getEntityId(), request.getAccountId());
			EntityDetails entityDetails = getServiceStatus(request.getEntityId(), ServiceTypeEnum.FRAUGSTER_SERVICE.getShortName(), request.getEntityType());
			entityDetails.setContactName(regRequest.getAccount().getContacts().get(0).getFullName());
			regRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS,entityDetails);
			regRequest.addAttribute(Constants.ENTITY_TYPE, EntityEnum.CONTACT.name());
			regRequest.getAccount().getContacts().get(0).setFraugsterPerformed(entityDetails.getIsExist());
		}
	
		Integer userID = newRegistrationDBServiceImpl.getUserIDFromSSOUserID(token.getPreferredUserName());
		//setting appropriate user id into UserProfile token
		token.setUserID(userID);
		FraugsterSummary fraugsterSummary = newRegistrationDBServiceImpl.getFirstFraugsterSummary(request.getEntityId(), request.getEntityType());
		regRequest.addAttribute("firstFraugsterSummary", fraugsterSummary);
		regRequest.setOrgId(request.getOrgId());
		oldContacts = regRequest.getAccount().getContacts();
		regRequest.addAttribute(Constants.FIELD_OLD_CONTACTS, oldContacts);
		request.addAttribute(Constants.OLD_REQUEST, regRequest);
		return message;
	}
	
	
	private void enrichDataBlacklistResend(Message<MessageContext> message) throws ComplianceException {
		BlacklistResendRequest request = message.getPayload().getGatewayMessageExchange()
				.getRequest(BlacklistResendRequest.class);
		RegistrationServiceRequest regRequest;
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		
		if (null != request.getEntityType() && (EntityEnum.ACCOUNT.name()).equalsIgnoreCase(request.getEntityType())) {
			regRequest = getRegistrationRequest(request.getEntityId(), request.getAccountId(),  request.getOrgCode());
			EntityDetails entityDetails = getServiceStatus(request.getEntityId(), ServiceTypeEnum.BLACK_LIST_SERVICE.getShortName(), request.getEntityType());
			entityDetails.setAccountName(regRequest.getAccount().getAccountName());
			regRequest.addAttribute(Constants.ENTITY_TYPE, EntityEnum.ACCOUNT.name());
			regRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS,entityDetails);
		} else {
			regRequest = getRegistrationRequest(request.getEntityId(), request.getAccountId());
			EntityDetails entityDetails = getServiceStatus(request.getEntityId(), ServiceTypeEnum.BLACK_LIST_SERVICE.getShortName(), request.getEntityType());
			String contactName = regRequest.getAccount().getContacts().get(0).getFirstAndLastName();
			entityDetails.setContactName(contactName);
			regRequest.addAttribute(Constants.ENTITY_TYPE, EntityEnum.CONTACT.name());
			regRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS,entityDetails);
		}
		/*
		 * We fetch user ID here by getting it from database according to user name
		 * Changes done by Vishal J
		 * */
		Integer userID = newRegistrationDBServiceImpl.getUserIDFromSSOUserID(token.getPreferredUserName());
		//setting appropriate user id into UserProfile token
		token.setUserID(userID);
		regRequest.setOrgId(request.getOrgId());
		message.getPayload().getGatewayMessageExchange().setRequest(regRequest);
		message.getPayload().getGatewayMessageExchange().getRequest().addAttribute("blacklistResendRequest", request);
	}

}
