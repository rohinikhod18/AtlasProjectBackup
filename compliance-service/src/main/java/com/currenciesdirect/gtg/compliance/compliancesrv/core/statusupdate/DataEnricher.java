package com.currenciesdirect.gtg.compliance.compliancesrv.core.statusupdate;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate.StatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.AbstractDataEnricher;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * The Class DataEnricher.
 */
public class DataEnricher extends AbstractDataEnricher {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataEnricher.class);
	
	@Autowired
	protected NewRegistrationDBServiceImpl newRegDBServiceImpl;
	
	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		StatusUpdateResponse response = messageExchange.getResponse(StatusUpdateResponse.class);
		LOGGER.debug("Gateway message headers=[{}], payload=[{}] validated!!", message.getHeaders(),
				message.getPayload());

		StatusUpdateRequest request = messageExchange.getRequest(StatusUpdateRequest.class);
		RegistrationServiceRequest registrationrequest = null;

		try {
			getUserTableId(message);
			registrationrequest = newRegDBServiceImpl.getAccountDetailsForStatusUpdate(request.getCrmAccountId());
			request.addAttribute(Constants.FIELD_ACC_ACCOUNT, registrationrequest.getAccount());
			request.addAttribute("OrganizationId", registrationrequest.getAdditionalAttribute("OrganizationId"));
			request.addAttribute("AccountRegisteredDate", registrationrequest.getAdditionalAttribute("AccountRegisteredDate"));
			request.addAttribute("AccountRegistrationInDate", registrationrequest.getAdditionalAttribute("AccountRegistrationInDate"));
			request.addAttribute("ContactRegisteredDate", registrationrequest.getAdditionalAttribute("ContactRegisteredDate"));
			request.addAttribute("ContactRegistrationInDate", registrationrequest.getAdditionalAttribute("ContactRegistrationInDate"));
			request.addAttribute("ContactExpiryDate", registrationrequest.getAdditionalAttribute("ContactExpiryDate"));
			request.addAttribute("AccountExpiryDate", registrationrequest.getAdditionalAttribute("AccountExpiryDate"));
			
		} catch (Exception e) {
			LOGGER.error("Request is not valid", e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		messageExchange.setResponse(response);
		return message;
	}
}