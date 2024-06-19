package com.currenciesdirect.gtg.compliance.compliancesrv.core.dataanonymization;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonContact;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonRequestFromES;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonResponseForES;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class DataAnonMessageValidator extends BaseMessageValidator {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(DataAnonMessageValidator.class);
	
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
		ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
		OperationEnum operation = messageExchange.getOperation();
		FieldValidator validator = null;
		DataAnonRequestFromES request = messageExchange.getRequest(DataAnonRequestFromES.class);
		DataAnonResponseForES esResponse = new DataAnonResponseForES();
		DataAnonResponseForES actualResponse = new DataAnonResponseForES();
		try {
			if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.DATA_ANONYMIZE) {
				validator = validateMandatoryFields(request);
				if(!validator.isFailed()) {
					esResponse.setCode(DataAnonConstants.RESPONSECODE1);
					esResponse.setStatus(DataAnonConstants.RECEIVED);
					esResponse.setSystem(DataAnonConstants.SYSTEM);
					esResponse.setDecision(BaseResponse.DECISION.FAIL);
					createBaseResponse(actualResponse, validator,ComplianceReasonCode.MISSINGINFO.getReasonCode(),  Constants.MSG_MISSING_INFO,DataAnonConstants.DATA_ANONYMIZATION);
					esResponse.addAttribute(Constants.BASE_RESPONSE, actualResponse);
				}
				else
					esResponse.setDecision(BaseResponse.DECISION.SUCCESS);
			}
		}catch(Exception e) {
			LOG.error(Constants.INVALID_REQUEST, e);
			esResponse.setDecision(BaseResponse.DECISION.FAIL);
			
			esResponse.setCode(DataAnonConstants.RESPONSECODE1);
			esResponse.setStatus(DataAnonConstants.RECEIVED);
			esResponse.setSystem(DataAnonConstants.SYSTEM);
		}
		messageExchange.setResponse(esResponse);
		return message;
	}
	
	/**
	 * Validate mandatory fields.
	 *
	 * @param dataAnonRequestFromES the data anon request from ES
	 * @return the field validator
	 */
	private FieldValidator validateMandatoryFields(DataAnonRequestFromES dataAnonRequestFromES) {
		FieldValidator validator = new FieldValidator();
		try {
			validator.isValidObject(
					new Object[] { dataAnonRequestFromES.getAccount().getCrmId(),
							dataAnonRequestFromES.getAccount().getCustomerNumber(),dataAnonRequestFromES.getAccount().getTradeId()},
					new String[] { "crm_account_id", "trade_account_number", "trade_account_id"});
			validator.mergeErrors(validateContacts(dataAnonRequestFromES));
		} catch (Exception e) {
			LOG.error("Error in validation", e);
		}
		return validator;
	}
	
	/**
	 * Validate contacts.
	 *
	 * @param dataAnonRequestFromES the data anon request from ES
	 * @return the field validator
	 */
	private FieldValidator validateContacts(DataAnonRequestFromES dataAnonRequestFromES) {
		FieldValidator validator = new FieldValidator();
		for(DataAnonContact contact : dataAnonRequestFromES.getAccount().getContact()) {
			validator.isValidObject(
					new Object[] { contact.getCrmId(),contact.getTradeId()},
					new String[] { "crm_contact_id", "trade_contact_id"});
		}
		return validator;	
	}

}