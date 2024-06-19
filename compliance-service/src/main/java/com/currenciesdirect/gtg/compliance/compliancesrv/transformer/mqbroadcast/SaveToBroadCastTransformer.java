/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.mqbroadcast;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate.StatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadcastEventToDB;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.MQEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * The Class SaveToBroadCastTransformer.
 *
 * @author manish
 */
public class SaveToBroadCastTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SaveToBroadCastTransformer.class);

	/**
	 * Transform request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			BroadcastEventToDB broadcastEventToDB = new BroadcastEventToDB();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			OperationEnum operationEnum = messageExchange.getOperation();
			RegistrationResponse baseResponse = new RegistrationResponse(); 
			if(OperationEnum.STATUS_UPDATE!=operationEnum) {
				baseResponse = (RegistrationResponse) message.getPayload().getGatewayMessageExchange().getResponse();
			}
			switch (operationEnum) {
			case NEW_REGISTRATION:
				broadcastEventToDB.setEntityType(MQEntityTypeEnum.SIGNUP);
				break;
			case ADD_CONTACT:
				broadcastEventToDB.setEntityType(MQEntityTypeEnum.ADDCONTACT);
				break;
			case UPDATE_ACCOUNT:
				broadcastEventToDB.setEntityType(MQEntityTypeEnum.UPDATE);
				break;
			case STATUS_UPDATE:
				baseResponse = (RegistrationResponse)messageExchange.getRequest().getAdditionalAttribute(Constants.FIELD_BROADCAST_RESPONSE);
				broadcastEventToDB.setEntityType(MQEntityTypeEnum.UPDATE);
				StatusUpdateRequest statusUpdateRequest = messageExchange.getRequest(StatusUpdateRequest.class);
				Account oldAccount = (Account) statusUpdateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
				RegistrationServiceRequest regRequest = new RegistrationServiceRequest();
				regRequest.setAccount(oldAccount);
				regRequest.setOrgCode(statusUpdateRequest.getOrgCode());
				messageExchange.setRequest(regRequest);
				break;
			default:
				break;
			}
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			Account account = registrationRequest.getAccount();
			broadcastEventToDB.setAccountId(account == null ? null : account.getId());
			broadcastEventToDB.setOrgCode(registrationRequest.getOrgCode());
			if(Boolean.TRUE.equals(registrationRequest.getIsBroadCastRequired())) {
				isCFXBroadcastTrueThenRemoveContact(broadcastEventToDB, baseResponse, account);
			}
			broadcastEventToDB.setStatusJson(baseResponse);
			broadcastEventToDB.setDeliveryStatus("NEW");
			broadcastEventToDB.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setDeliveredOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setDeliverOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setCreatedBy(token.getUserID());
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setRequest(broadcastEventToDB);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SAVE_TO_BROADCAST_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);

		} catch (Exception e) {
			LOG.error("Error in SaveToBroadCastTransformer  class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * @param broadcastEventToDB
	 * @param baseResponse
	 * @param account
	 */
	private void isCFXBroadcastTrueThenRemoveContact(BroadcastEventToDB broadcastEventToDB,
			RegistrationResponse baseResponse, Account account) {
		if(( MQEntityTypeEnum.SIGNUP==broadcastEventToDB.getEntityType()	
				|| MQEntityTypeEnum.ADDCONTACT==broadcastEventToDB.getEntityType())
				&& (null != account && Constants.CFX.equals(account.getCustType()))) {
			if( MQEntityTypeEnum.SIGNUP==broadcastEventToDB.getEntityType()) {
				baseResponse.getAccount().setEventType(Constants.SIGN_UP);
			}
			if(MQEntityTypeEnum.ADDCONTACT==broadcastEventToDB.getEntityType()) {
				baseResponse.getAccount().setEventType(Constants.ADD_CONTACT);
			}
			setContactsToNullForSignupAndAddContact(baseResponse,account);
		}					
		if(MQEntityTypeEnum.UPDATE==broadcastEventToDB.getEntityType()
				&& (null != account && Constants.CFX.equals(account.getCustType()))) {
			baseResponse.getAccount().setEventType(Constants.UPDATE);
			setContactsToNullForUpdate(baseResponse,account);
		}
	}

	private void setContactsToNullForUpdate(RegistrationResponse baseResponse, Account account) {
		String cSfId = "";
		String responseCode;
		String responseDesc;
		ComplianceContact complianceContactForSpecific = new ComplianceContact();
		ComplianceContact complianceContactForOther = new ComplianceContact();

		for (Contact contact : account.getContacts()) {
			cSfId = contact.getContactSFID();
		}
		if (ComplianceReasonCode.BLACKLISTED.getReasonCode().equals(baseResponse.getAccount().getResponseCode())) {
			for (ComplianceContact contact : baseResponse.getAccount().getContacts()) {
				if (null != cSfId && cSfId.equals(contact.getContactSFID())
						&& ComplianceReasonCode.BLACKLISTED.getReasonCode().equals(contact.getResponseCode())) {
					responseCode = contact.getResponseCode();
					responseDesc = contact.getResponseDescription();
					complianceContactForSpecific.setContactSFID(cSfId);
					complianceContactForSpecific.setResponseCode(responseCode);
					complianceContactForSpecific.setResponseDescription(responseDesc);
				}
			}
			complianceContactForOther.setResponseCode(ComplianceReasonCode.BLACKLISTED_OTHER.getReasonCode());
			complianceContactForOther
					.setResponseDescription(ComplianceReasonCode.BLACKLISTED_OTHER.getReasonDescription());
		} else {
			for (ComplianceContact contact : baseResponse.getAccount().getContacts()) {
				if (null != cSfId && cSfId.equals(contact.getContactSFID())) {
					responseCode = contact.getResponseCode();
					responseDesc = contact.getResponseDescription();
					complianceContactForSpecific.setContactSFID(cSfId);
					complianceContactForSpecific.setResponseCode(responseCode);
					complianceContactForSpecific.setResponseDescription(responseDesc);
				}
			}
			complianceContactForOther.setResponseCode(baseResponse.getAccount().getResponseCode());
			complianceContactForOther.setResponseDescription(baseResponse.getAccount().getResponseDescription());
		}
		baseResponse.getAccount().setSpecificContact(complianceContactForSpecific);
		baseResponse.getAccount().setOtherContacts(complianceContactForOther);
		baseResponse.getAccount().setCustType(Constants.CFX);
		baseResponse.getAccount().setContacts(null);
	}

	/**
	 * 
	 * @param baseResponse
	 * @param account
	 */
	private void setContactsToNullForSignupAndAddContact(RegistrationResponse baseResponse, Account account) {
		List<String> cSfId = new ArrayList<>();
		String responseCode;
		String responseDesc;
		ComplianceContact complianceContactForSpecific = new ComplianceContact();
		ComplianceContact complianceContactForOther = new ComplianceContact();

		for (Contact contact : account.getContacts()) {
			String e = contact.getContactSFID();
			cSfId.add(e);
		}
		if (ComplianceReasonCode.BLACKLISTED.getReasonCode().equals(baseResponse.getAccount().getResponseCode())) {
			for (ComplianceContact contact : baseResponse.getAccount().getContacts()) {
				if (cSfId.contains(contact.getContactSFID())
						&& ComplianceReasonCode.BLACKLISTED.getReasonCode().equals(contact.getResponseCode())) {
					responseCode = contact.getResponseCode();
					responseDesc = contact.getResponseDescription();
					complianceContactForSpecific.setContactSFID(cSfId.get(cSfId.indexOf(contact.getContactSFID())));
					complianceContactForSpecific.setResponseCode(responseCode);
					complianceContactForSpecific.setResponseDescription(responseDesc);
				}
			}
			complianceContactForOther.setResponseCode(ComplianceReasonCode.BLACKLISTED_OTHER.getReasonCode());
			complianceContactForOther
					.setResponseDescription(ComplianceReasonCode.BLACKLISTED_OTHER.getReasonDescription());
		} else {
			complianceContactForSpecific.setResponseCode(baseResponse.getAccount().getResponseCode());
			complianceContactForSpecific.setResponseDescription(baseResponse.getAccount().getResponseDescription());
			complianceContactForOther.setResponseCode(baseResponse.getAccount().getResponseCode());
			complianceContactForOther.setResponseDescription(baseResponse.getAccount().getResponseDescription());
		}
		baseResponse.getAccount().setSpecificContact(complianceContactForSpecific);
		baseResponse.getAccount().setOtherContacts(complianceContactForOther);
		baseResponse.getAccount().setCustType(Constants.CFX);
		baseResponse.getAccount().setContacts(null);
	}

	/**
	 * Transform request for failed fund out.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> transformRequestForFailedFundOut(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			BroadcastEventToDB broadcastEventToDB = new BroadcastEventToDB();
			FundsOutResponse fundsOutResponse = (FundsOutResponse) message.getPayload().getGatewayMessageExchange()
					.getResponse();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

			FundsOutRequest fundsOutRequest = (FundsOutRequest) messageExchange.getRequest();
			Account account = (Account) fundsOutRequest.getAdditionalAttribute("account");
			Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute("contact");

			broadcastEventToDB.setAccountId(account.getId());
			broadcastEventToDB.setContactId(contact.getId());
			broadcastEventToDB.setPaymentOutId(fundsOutRequest.getFundsOutId());
			broadcastEventToDB.setOrgCode(fundsOutRequest.getOrgCode());
			broadcastEventToDB.setStatusJson(fundsOutResponse);
			broadcastEventToDB.setDeliveryStatus("NEW");
			broadcastEventToDB.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setDeliveredOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setDeliverOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setCreatedBy(token.getUserID());
			broadcastEventToDB.setEntityType(MQEntityTypeEnum.PAYMENTOUT);
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setRequest(broadcastEventToDB);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SAVE_TO_BROADCAST_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);

		} catch (Exception e) {
			LOG.error("Error in SaveToBroadCastTransformer  class : transformRequestForFailedFundOut -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Transform request for service failed registration.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> transformRequestForServiceFailedRegistration(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			BroadcastEventToDB broadcastEventToDB = new BroadcastEventToDB();
			BaseResponse baseResponse = (BaseResponse) message.getPayload().getGatewayMessageExchange().getResponse();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

			broadcastEventToDB.setEntityType(MQEntityTypeEnum.UPDATE);
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			Account account = registrationRequest.getAccount();
			broadcastEventToDB.setAccountId(account == null ? null : account.getId());

			broadcastEventToDB.setOrgCode(registrationRequest.getOrgCode());
			broadcastEventToDB.setStatusJson(baseResponse);
			broadcastEventToDB.setDeliveryStatus("NEW");
			broadcastEventToDB.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setDeliveredOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setDeliverOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setCreatedBy(token.getUserID());
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setRequest(broadcastEventToDB);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SAVE_TO_BROADCAST_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);

		} catch (Exception e) {
			LOG.error("Error in SaveToBroadCastTransformer  class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Transform request for failed fund in.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> transformRequestForFailedFundIn(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			BroadcastEventToDB broadcastEventToDB = new BroadcastEventToDB();
			FundsInCreateResponse fundsInResponse = (FundsInCreateResponse) message.getPayload()
					.getGatewayMessageExchange().getResponse();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

			FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) messageExchange.getRequest();
			Account account = (Account) fundsInRequest.getAdditionalAttribute("account");
			Contact contact = (Contact) fundsInRequest.getAdditionalAttribute("contact");

			broadcastEventToDB.setAccountId(account.getId());
			broadcastEventToDB.setContactId(contact.getId());
			broadcastEventToDB.setPaymentInId(fundsInRequest.getFundsInId());
			broadcastEventToDB.setOrgCode(fundsInRequest.getOrgCode());
			broadcastEventToDB.setStatusJson(fundsInResponse);
			broadcastEventToDB.setDeliveryStatus("NEW");
			broadcastEventToDB.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setDeliveredOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setDeliverOn(new Timestamp(System.currentTimeMillis()));
			broadcastEventToDB.setCreatedBy(token.getUserID());
			broadcastEventToDB.setEntityType(MQEntityTypeEnum.PAYMENTIN);
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setRequest(broadcastEventToDB);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SAVE_TO_BROADCAST_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);

		} catch (Exception e) {
			LOG.error("Error in SaveToBroadCastTransformer  class : transformRequestForFailedFundIn -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
}
