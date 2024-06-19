package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.sanction;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateData;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class SanctionUpdateMessageValidator.
 */
public class SanctionUpdateMessageValidator {

	private static final String REQUEST_IS_NOT_VALID = "Request is not valid";
	
	private static final String MANDATORY_FIELDS_ARE_MISSING ="Mandatory fields are missing";

	private static final Logger LOGGER = LoggerFactory.getLogger(SanctionUpdateMessageValidator.class);
	
	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		SanctionUpdateResponse response=new SanctionUpdateResponse();
		MessageExchange messageExchange = null;
		try{
			messageExchange = message.getPayload().getGatewayMessageExchange();

			SanctionUpdateRequest sanctionUpdateRequest = messageExchange.getRequest(SanctionUpdateRequest.class);
			
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			
			if(serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.SANCTION_UPDATE){
				    validateMandatoryFields(sanctionUpdateRequest);
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
	
    void validateMandatoryFields(SanctionUpdateRequest request) throws ComplianceException {
    	if(request.getResourceType() == null || request.getResourceType().isEmpty() || request.getResourceId() == null) {
    		throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(MANDATORY_FIELDS_ARE_MISSING));
    	}
    	/** Validate fields from FUNDSOUT */
    	validateFundsOutFields(request);
    	for(SanctionUpdateData data : request.getSanctions()) {
			if(data.getEventServiceLogId() == null || StringUtils.isNullOrEmpty(data.getField()) || StringUtils.isNullOrEmpty(data.getValue())) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception("Event specific data missing"));
			}
		}
    	
    }

	/**
	 * @param request
	 * @throws ComplianceException
	 */
	private void validateFundsOutFields(SanctionUpdateRequest request) throws ComplianceException {
		
		if(request.getResourceType().equals(ServiceInterfaceType.FUNDSOUT.name())){
    		validateFields(request);
    	}
	}

	/**
	 * @param request
	 * @throws ComplianceException
	 */
	private void validateFields(SanctionUpdateRequest request) throws ComplianceException {
		if(request.getSanctions().get(0).getBankStatus() == null || request.getSanctions().get(0).getBankStatus().isEmpty()) {
			throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(MANDATORY_FIELDS_ARE_MISSING));
		}
		if(request.getSanctions().get(0).getBeneficiaryStatus() == null || request.getSanctions().get(0).getBeneficiaryStatus().isEmpty()) {
			throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(MANDATORY_FIELDS_ARE_MISSING));
		}
		if(request.getSanctions().get(0).getContactStatus() == null || request.getSanctions().get(0).getContactStatus().isEmpty()) {
			throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(MANDATORY_FIELDS_ARE_MISSING));
		}
	}

}
