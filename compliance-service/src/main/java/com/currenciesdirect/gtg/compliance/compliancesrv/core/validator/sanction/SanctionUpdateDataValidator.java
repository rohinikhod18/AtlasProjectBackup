package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.sanction;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IRegistrationDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

/**
 * The Class SanctionUpdateDataValidator.
 */
public class SanctionUpdateDataValidator {

	private static final String REQUEST_IS_NOT_VALID = "Request is not valid";

	private static final Logger LOGGER = LoggerFactory.getLogger(SanctionUpdateDataValidator.class);

	private IRegistrationDBService registrationDBService;

	/**
	 * Process.
	 *
	 * @param message
	 *            the message
	 * @param correlationID
	 *            the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		SanctionUpdateResponse response = new SanctionUpdateResponse();
		MessageExchange messageExchange = null;
		try {
			messageExchange = message.getPayload().getGatewayMessageExchange();

			SanctionUpdateRequest sanctionUpdateRequest = messageExchange.getRequest(SanctionUpdateRequest.class);

			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();

			if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.SANCTION_UPDATE) {
				if (Boolean.TRUE.equals(validateAccountForSanctionUpdate(sanctionUpdateRequest.getAccountId()))
						//&& isEventServiceLogExist(messageExchange)
						) {
					response.setDecision(DECISION.SUCCESS);
				} else {
					response.setDecision(BaseResponse.DECISION.FAIL);
					response.setErrorCode(InternalProcessingCode.INV_REQUEST.getCode());
					response.setErrorDescription(InternalProcessingCode.INV_REQUEST.name());
					LOGGER.error(REQUEST_IS_NOT_VALID);
				}
			} else {
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
		if (messageExchange != null) {
			messageExchange.setResponse(response);
		}
		return message;
	}

	private Boolean validateAccountForSanctionUpdate(Integer accountId) throws ComplianceException {
		return accountId != null && registrationDBService.isAccountExistByDBId(accountId);
	}


	/**
	 * Gets the registration DB service.
	 *
	 * @return the registration DB service
	 */
	public IRegistrationDBService getRegistrationDBService() {
		return registrationDBService;
	}

	/**
	 * Sets the registration DB service.
	 *
	 * @param registrationDBService the new registration DB service
	 */
	public void setRegistrationDBService(IRegistrationDBService registrationDBService) {
		this.registrationDBService = registrationDBService;
	}

}
