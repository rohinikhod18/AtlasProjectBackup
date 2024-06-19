package com.currenciesdirect.gtg.compliance.compliancesrv.restport.transactionmonitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class DormantCustomerUpdateToIntuition {

	/** The Constant LOGGER. */
	private static final Logger LOG = LoggerFactory.getLogger(DormantCustomerUpdateToIntuition.class);

	/** The application context. */
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Dormant customer registration to intuition for payment in.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> dormantCustomerRegistrationToIntuitionForPaymentIn(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		FundsInCreateRequest fundsInCreateRequest = (FundsInCreateRequest) messageExchange.getRequest();
		Account account = (Account) fundsInCreateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
		Contact contact = (Contact) fundsInCreateRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
		Integer accountTMFlag = (Integer) fundsInCreateRequest.getAdditionalAttribute(Constants.ACCOUNT_TM_FLAG);
		Integer dormantFlag = (Integer) fundsInCreateRequest.getAdditionalAttribute("DormantFlag");

		if (accountTMFlag != null && accountTMFlag == 0 && dormantFlag != null && dormantFlag == 0) {
			Integer updatedAccountTMFlag = sendRequestToDormantCustomerUpdateFlow(account, contact,
					fundsInCreateRequest.getOrgCode(), token);
			fundsInCreateRequest.addAttribute(Constants.ACCOUNT_TM_FLAG, updatedAccountTMFlag);
			// fundsInCreateRequest.addAttribute("DormantFlag", 1);
		}

		return message;
	}

	/**
	 * Dormant customer registration to intuition for payment out.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> dormantCustomerRegistrationToIntuitionForPaymentOut(
			Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		FundsOutRequest fundsOutRequest = (FundsOutRequest) messageExchange.getRequest();
		Account account = (Account) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
		Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
		Integer accountTMFlag = (Integer) fundsOutRequest.getAdditionalAttribute(Constants.ACCOUNT_TM_FLAG);
		Integer dormantFlag = (Integer) fundsOutRequest.getAdditionalAttribute("DormantFlag");

		if (accountTMFlag != null && accountTMFlag == 0 && dormantFlag != null && dormantFlag == 0) {
			Integer updatedAccountTMFlag = sendRequestToDormantCustomerUpdateFlow(account, contact,
					fundsOutRequest.getOrgCode(), token);
			fundsOutRequest.addAttribute(Constants.ACCOUNT_TM_FLAG, updatedAccountTMFlag);
			// fundsOutRequest.addAttribute("DormantFlag", 1);
		}

		return message;
	}

	/**
	 * Send request to dormant customer update flow.
	 *
	 * @param account the account
	 * @param contact the contact
	 * @param orgCode the org code
	 * @param token
	 */
	private Integer sendRequestToDormantCustomerUpdateFlow(Account account, Contact contact, String orgCode,
			UserProfile token) {
		RegistrationServiceRequest registrationRequest = new RegistrationServiceRequest();
		RegistrationResponse regResponse = new RegistrationResponse();
		Integer updatedAccountTMFlag = 0;

		List<Contact> contacts = new ArrayList<>();
		contacts.add(contact);

		account.setContacts(contacts);
		registrationRequest.setAccount(account);
		registrationRequest.setOrgCode(orgCode);
		registrationRequest.addAttribute("registrationLoadCriteria", "Dormant Customer load "
				+ new java.text.SimpleDateFormat("ddMMyyyy").format(new java.util.Date())); //Added for AT-5488

		ComplianceAccount complianceAccount = new ComplianceAccount();
		complianceAccount.setId(account.getId());
		regResponse.setAccount(complianceAccount);

		MessageExchange gateWayMessageExchange = new MessageExchange();
		gateWayMessageExchange.setServiceTypeEnum(ServiceTypeEnum.GATEWAY_SERVICE);
		gateWayMessageExchange.setServiceInterface(ServiceInterfaceType.INTUITION);
		gateWayMessageExchange.setOperation(OperationEnum.NEW_REGISTRATION);
		gateWayMessageExchange.setRequest(registrationRequest);
		gateWayMessageExchange.setResponse(regResponse);

		MessageContext messageContext = new MessageContext();
		if (null != orgCode)
			messageContext.setOrgCode(orgCode.toUpperCase());
		messageContext.appendMessageExchange(gateWayMessageExchange);

		DeferredResult<ResponseEntity> response = executeWorkflow(messageContext, token);

		ResponseEntity<RegistrationResponse> responseEntity = (ResponseEntity<RegistrationResponse>) response
				.getResult();
		regResponse = responseEntity.getBody();

		if (responseEntity.getStatusCode().equals(HttpStatus.OK) && regResponse != null) {
			updatedAccountTMFlag = (Integer) regResponse.getAdditionalAttribute("UpdatedTMFlag");
		}

		return updatedAccountTMFlag;
	}

	/**
	 * Execute workflow.
	 *
	 * @param messageContext the message context
	 * @param token
	 */
	public DeferredResult<ResponseEntity> executeWorkflow(final MessageContext messageContext, UserProfile token) {
		DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>(-1L);
		Map<String, Object> messageHeaders = new HashMap<>();

		messageHeaders.put(MessageContextHeaders.GATEWAY_USER, token);
		messageHeaders.put(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT, deferredResult);
		Message<MessageContext> message = MessageBuilder.withPayload(messageContext).copyHeaders(messageHeaders)
				.build();

		MessageChannel messageChannel = getInMessageChannel();
		if (messageChannel != null)
			messageChannel.send(message);

		return deferredResult;
	}

	/**
	 * Gets the in message channel.
	 *
	 * @return the in message channel
	 */
	private MessageChannel getInMessageChannel() {
		MessageChannel messageChannel = null;
		try {
			messageChannel = applicationContext.getBean("INTUITION.NEW_REGISTRATION.in.channel", MessageChannel.class);
		} catch (NoSuchBeanDefinitionException nbde) {
			LOG.error("Error in DormantCustomerUpdateToIntuition", nbde);
		}
		return messageChannel;
	}
}
