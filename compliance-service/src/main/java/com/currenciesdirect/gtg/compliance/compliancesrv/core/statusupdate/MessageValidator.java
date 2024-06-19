package com.currenciesdirect.gtg.compliance.compliancesrv.core.statusupdate;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate.StatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class MessageValidator extends BaseMessageValidator {

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
		StatusUpdateResponse response = new StatusUpdateResponse();
		StatusUpdateRequest statusUpdateRequest = null;
		FieldValidator validator = null;
		
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		try {
			statusUpdateRequest = (StatusUpdateRequest) messageExchange.getRequest();
			response.setOsrID(statusUpdateRequest.getOsrId());
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();

			if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.STATUS_UPDATE) {
				validator = validateStatusUpdateRequest(statusUpdateRequest);
				if(!validator.isFailed()) {
					createBaseResponse(response, validator, ComplianceReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, "PROFILE STATUS UPDATE");
				}else {
					response.setDecision(BaseResponse.DECISION.SUCCESS);
				}
			}
		} catch (Exception e) {
			LOG.error(REQUEST_IS_NOT_VALID, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		messageExchange.setResponse(response);
		return message;
	}

	/**
	 * Validate status update request.
	 *
	 * @param statusUpdateRequest the status update request
	 * @return the field validator
	 */
	private FieldValidator validateStatusUpdateRequest(StatusUpdateRequest statusUpdateRequest) {
		FieldValidator validator = new FieldValidator();
		validator.mergeErrors(validateMandatoryFields(statusUpdateRequest));
		return validator;
	}

	/**
	 * Validate mandatory fields.
	 *
	 * @param statusUpdateRequest the status update request
	 * @return the field validator
	 */
	private FieldValidator validateMandatoryFields(StatusUpdateRequest statusUpdateRequest) {
		FieldValidator validator = new FieldValidator();
		try {
			validator.isValidObject(
					new Object[] { statusUpdateRequest.getCrmAccountId(), statusUpdateRequest.getCrmContactId(),
							statusUpdateRequest.getOrgCode()},
					new String[] { "crm_account_id", "crm_contact_id", "org_code"});
		} catch (Exception e) {
			LOG.error("Error in validation", e);
		}
		return validator;
	}

}