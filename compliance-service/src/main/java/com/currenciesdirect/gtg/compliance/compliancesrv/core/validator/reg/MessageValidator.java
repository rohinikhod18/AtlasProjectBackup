package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.reg;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * @author bnt
 *
 */
public class MessageValidator extends BaseMessageValidator {
	
	private static final String INVALID_REQUEST = "Request is not valid";
	private static final Logger LOG = LoggerFactory.getLogger(MessageValidator.class);

	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		RegistrationResponse response = new RegistrationResponse();
		RegistrationServiceRequest registrationServiceRequest = null;
		FieldValidator validator = null;
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		try {
			registrationServiceRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			registrationServiceRequest.setCorrelationID(correlationID);
			if(null == registrationServiceRequest.getIsBroadCastRequired()) {
				registrationServiceRequest.setIsBroadCastRequired(Boolean.TRUE);
			}
			registrationServiceRequest.addAttribute(Constants.FIELD_BROADCAST_REQUIRED, registrationServiceRequest.getIsBroadCastRequired());
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();

			if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.NEW_REGISTRATION) {
				validator = validateNewRegistration(registrationServiceRequest);
				createBaseResponse(response, validator,ComplianceReasonCode.MISSINGINFO.getReasonCode(),  Constants.MSG_MISSING_INFO,"SIGNUP");
				
			} else if (serviceInterfaceType == ServiceInterfaceType.PROFILE
					&& operation == OperationEnum.UPDATE_ACCOUNT) {
				validator = validateUpdateAccount(registrationServiceRequest);
				createBaseResponse(response, validator,ComplianceReasonCode.MISSINGINFO.getReasonCode(),  Constants.MSG_MISSING_INFO,"UPDATE");

			} else if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.ADD_CONTACT) {
				validator = validateAddContact(registrationServiceRequest);
				createBaseResponse(response, validator,ComplianceReasonCode.MISSINGINFO.getReasonCode(),  Constants.MSG_MISSING_INFO,"ADDCONTACT");

			}
		} catch (Exception e) {
			LOG.error(INVALID_REQUEST, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		response.setOsrID(messageExchange.getRequest().getOsrId());
		response.setCorrelationID(correlationID);
		messageExchange.setResponse(response);
		return message;
	}

	private FieldValidator validateNewRegistration(RegistrationServiceRequest registrationServiceRequest) {
		Account account = registrationServiceRequest.getAccount();
		FieldValidator validator = registrationServiceRequest.validateSignup();

		if (registrationServiceRequest.validateSignup().isFailed()) {
			account.setVersion(1);
		}
		return validator;
	}

	private FieldValidator validateUpdateAccount(RegistrationServiceRequest registrationServiceRequest){
		return registrationServiceRequest.validateUpdateAccount();
	}

	private FieldValidator validateAddContact(RegistrationServiceRequest registrationServiceRequest) {
		return registrationServiceRequest.getAccount().validateAddContact();
	}
}