package com.currenciesdirect.gtg.compliance.compliancesrv.core.onfidostatusupdate;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateData;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

public class OnfidoStatusUpdateMessageValidator {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OnfidoStatusUpdateMessageValidator.class);
	
	/** The Constant REQUEST_IS_NOT_VALID. */
	private static final String REQUEST_IS_NOT_VALID = "Request is not valid";
	
	/** The Constant MANDATORY_FIELDS_ARE_MISSING. */
	private static final String MANDATORY_FIELDS_ARE_MISSING ="Mandatory fields are missing";
	
	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		OnfidoUpdateResponse response = new OnfidoUpdateResponse();
		MessageExchange messageExchange = null;
		try{
			messageExchange = message.getPayload().getGatewayMessageExchange();
			
			OnfidoUpdateRequest request = messageExchange.getRequest(OnfidoUpdateRequest.class);
			
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			
			if(serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.ONFIDO_STATUS_UPDATE){
				    validateMandatoryFields(request);
					response.setDecision(DECISION.SUCCESS);
			}else{
				response.setDecision(BaseResponse.DECISION.FAIL);
				response.setErrorCode(InternalProcessingCode.INV_REQUEST.getCode());
				response.setErrorDescription(InternalProcessingCode.INV_REQUEST.name());
				LOGGER.error(REQUEST_IS_NOT_VALID);
			}
		} catch (ComplianceException e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			response.setDecision(DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.getCode());
			response.setErrorDescription(e.getMessage());
		} catch (Exception e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			response.setDecision(DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.getCode());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.name());
		}
		if(messageExchange != null) {
			messageExchange.setResponse(response);
		}
		return message;
	}
	
	/**
	 * Validate mandatory fields.
	 *
	 * @param request the request
	 * @throws ComplianceException the compliance exception
	 */
	void validateMandatoryFields(OnfidoUpdateRequest request) throws ComplianceException {
    	if(request.getResourceType() == null || request.getResourceType().isEmpty() || request.getResourceId() == null) {
    		throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(MANDATORY_FIELDS_ARE_MISSING));
    	}
    	for(OnfidoUpdateData data : request.getOnfido()) {
			if(data.getEventServiceLogId() == null || StringUtils.isNullOrEmpty(data.getField()) || StringUtils.isNullOrEmpty(data.getValue())) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception("Event specific data missing"));
			}
		}
    	
    }

}
