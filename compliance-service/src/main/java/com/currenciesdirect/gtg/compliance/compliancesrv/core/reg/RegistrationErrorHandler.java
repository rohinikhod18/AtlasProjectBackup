package com.currenciesdirect.gtg.compliance.compliancesrv.core.reg;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ContactComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.BroadCastQueueDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.mqbroadcast.SaveToBroadCastTransformer;

/**
 * The Class RegistrationErrorHandler.
 *
 * @author abhijeetg
 */
public class RegistrationErrorHandler {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationErrorHandler.class);

	/** The broad cast queue DB service impl. */
	@Autowired
	@Qualifier("reg.BroadCastQueueDBServiceImpl")
	private BroadCastQueueDBServiceImpl broadCastQueueDBServiceImpl;

	/** The save to broad cast transformer. */
	@Autowired
	@Qualifier("saveToBroadCastTransformer")
	private SaveToBroadCastTransformer saveToBroadCastTransformer;

	/**
	 * Log error.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> logError(Message<MessageContext> message) throws ComplianceException {

		RegistrationResponse response = null;
		try {
			response = createResponse(message);
			message.getPayload().getGatewayMessageExchange().setResponse(response);
			saveToBroadCastTransformer.transformRequest(message);
			broadCastQueueDBServiceImpl.saveIntoBroadcastQueue(message);
		} catch (Exception e) {
			LOG.error("Error in RegistrationErrorHandler {1}", e);
		}

		@SuppressWarnings("unchecked")
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ResponseEntity<ServiceMessageResponse> responseEntity;
		responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		deferredResult.setResult(responseEntity);
		LOG.warn("logError-->Response : {}", responseEntity);
		return message;
	}

	/**
	 * Creates the response.
	 *
	 * @param message
	 *            the message
	 * @return the registration response
	 */
	private RegistrationResponse createResponse(Message<MessageContext> message) {
		MessageContext context = message.getPayload();
		MessageExchange exchange = context.getGatewayMessageExchange();
		RegistrationServiceRequest request = (RegistrationServiceRequest) exchange.getRequest();
		RegistrationResponse response = new RegistrationResponse();
		response.setOsrID(request.getOsrId());
		Account account = request.getAccount();
		response.setOrgCode(request.getOrgCode());
		ComplianceAccount accountResponse = new ComplianceAccount();
		accountResponse.setAccountSFID(account.getAccSFID());
		accountResponse.setAcs(ContactComplianceStatus.INACTIVE);
		accountResponse.setResponseCode(ComplianceReasonCode.SYSTEM_FAILURE.getReasonCode());
		accountResponse.setResponseDescription(ComplianceReasonCode.SYSTEM_FAILURE.getReasonDescription());
		accountResponse.setTradeAccountID(account.getTradeAccountID());
		accountResponse.setContacts(getErrorContactResponseList(request.getAccount().getContacts()));
		response.setAccount(accountResponse);
		context.getGatewayMessageExchange().setResponse(response);

		return response;
	}

	/**
	 * Gets the error contact response list.
	 *
	 * @param contacts
	 *            the contacts
	 * @return the error contact response list
	 */
	private List<ComplianceContact> getErrorContactResponseList(List<Contact> contacts) {
		List<ComplianceContact> contactResponseList = new ArrayList<>();
		for (Contact contact : contacts) {
			ComplianceContact cResponse = new ComplianceContact();
			cResponse.setContactSFID(contact.getContactSFID());
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			cResponse.setResponseCode(ComplianceReasonCode.SYSTEM_FAILURE.getReasonCode());
			cResponse.setResponseDescription(ComplianceReasonCode.SYSTEM_FAILURE.getReasonDescription());
			cResponse.setTradeContactID(contact.getTradeContactID());
			contactResponseList.add(cResponse);
		}
		return contactResponseList;
	}

}
