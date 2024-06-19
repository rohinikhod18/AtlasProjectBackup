package com.currenciesdirect.gtg.compliance.compliancesrv.core.dataanonymization;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonRequestFromES;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonResponseForES;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class DataAnonValidator extends BaseMessageValidator{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataAnonValidator.class);
	
	@Autowired
	private IDataAnonDBService iDataAnonDBService; 
	
	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> dataValidator(Message<MessageContext> message, @Header UUID correlationID) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		DataAnonRequestFromES request = messageExchange.getRequest(DataAnonRequestFromES.class);
		DataAnonResponseForES response = messageExchange.getResponse(DataAnonResponseForES.class);
		DataAnonResponseForES actualResponse = new DataAnonResponseForES();
		try {
			LOGGER.debug("Gateway message headers=[{}], payload=[{}] validated!!", message.getHeaders(),message.getPayload());
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			FieldValidator validator = new FieldValidator();
			if(serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.DATA_ANONYMIZE
					&& !validateAccount(request,validator)) {
					response.setCode(DataAnonConstants.RESPONSECODE1);
					response.setStatus(DataAnonConstants.RECEIVED);
					response.setSystem(DataAnonConstants.SYSTEM);
					response.setDecision(BaseResponse.DECISION.FAIL);
					createBaseResponse(actualResponse, validator, ComplianceReasonCode.MISSINGINFO.getReasonCode(),Constants.MSG_MISSING_INFO,DataAnonConstants.DATA_ANONYMIZATION);
					response.addAttribute(Constants.BASE_RESPONSE, actualResponse);
			}
			else
				response.setDecision(BaseResponse.DECISION.SUCCESS);
		}catch(Exception e) {
			LOGGER.error(Constants.INVALID_REQUEST, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setCode(DataAnonConstants.RESPONSECODE1);
			response.setStatus(DataAnonConstants.RECEIVED);
			response.setSystem(DataAnonConstants.SYSTEM);
		}
		messageExchange.setResponse(response);
		return message;
	}
	
	/**
	 * Queue validator.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> queueValidator(Message<MessageContext> message, @Header UUID correlationID) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		DataAnonRequestFromES request = messageExchange.getRequest(DataAnonRequestFromES.class);
		DataAnonResponseForES response = messageExchange.getResponse(DataAnonResponseForES.class);
		DataAnonResponseForES actualResponse = new DataAnonResponseForES();
		try {
			LOGGER.debug("Gateway message headers=[{}], payload=[{}] validated!!", message.getHeaders(),message.getPayload());
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			if(serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.DATA_ANONYMIZE
					&& iDataAnonDBService.checkDataAnonRequestIsInProcessQueue(request)) {
					response.setCode(DataAnonConstants.RESPONSECODE1);
					response.setStatus((String)request.getAdditionalAttribute(DataAnonConstants.EXISTING_ID_STATUS));
					response.setSystem(DataAnonConstants.SYSTEM);
					response.setDecision(BaseResponse.DECISION.FAIL);
					actualResponse.setResponseCode(ComplianceReasonCode.MISSINGINFO.getReasonCode());
					actualResponse.setResponseDescription("Reference ID already exist");
					response.addAttribute(Constants.BASE_RESPONSE, actualResponse);
			}
			else
				response.setDecision(BaseResponse.DECISION.SUCCESS);
		}catch(Exception e) {
			LOGGER.error("Error in DataAnonValidator of queueValidator() : {1}", e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setCode(DataAnonConstants.RESPONSECODE1);
			response.setStatus(DataAnonConstants.RECEIVED);
			response.setSystem(DataAnonConstants.SYSTEM);
			
		}
		messageExchange.setResponse(response);
		return message;
	}
	
	/**
	 * Validate account.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	private boolean validateAccount(DataAnonRequestFromES request,FieldValidator validator) {
		Account oldAccount = (Account) request.getAdditionalAttribute(Constants.FIELD_OLD_ACCOUNT);
		if(!request.getAccount().getCustomerNumber().equals(oldAccount.getTradeAccountNumber()) 
				&& validateContact(request,oldAccount,validator)) {
			validator.addError("Account does not exist", "Account");
			return false;
		}
		return true;
	}
	
	/**
	 * Validate contact.
	 *
	 * @param request the request
	 * @param oldAccount the old account
	 * @param validator the validator
	 * @return true, if successful
	 */
	private boolean validateContact(DataAnonRequestFromES request,Account oldAccount,FieldValidator validator) {
		for(Contact oldContact : oldAccount.getContacts()) {
			if(!request.getAccount().getContact().get(0).getTradeId().equals(oldContact.getTradeContactID().longValue())) {
				validator.addError("Contact does not exist", "Contac");
				return false;
			}
		}
		return true;
	}

}
