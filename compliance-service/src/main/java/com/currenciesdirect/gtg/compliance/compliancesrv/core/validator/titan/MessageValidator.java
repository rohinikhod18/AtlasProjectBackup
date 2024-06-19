package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.titan;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.request.TitanGetPaymentStatusRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class MessageValidator extends BaseMessageValidator {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(MessageValidator.class);

	/**
	 * Process.
	 *
	 * @param message       the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		TitanGetPaymentStatusRequest request = (TitanGetPaymentStatusRequest) messageExchange.getRequest();
		FundsInCreateResponse response = new FundsInCreateResponse();
		FieldValidator validator = null;

		try {
			validator = validateRequest(request);
			if (!validator.isFailed()) {
				createBaseResponse(response, validator, ComplianceReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, "TITAN GET PAYMENT STATUS");
			} else {
				response.setDecision(BaseResponse.DECISION.SUCCESS);
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
	 * Validate request.
	 *
	 * @param request the request
	 * @return the field validator
	 */
	private FieldValidator validateRequest(TitanGetPaymentStatusRequest request) {
		FieldValidator validator = new FieldValidator();
		try {
			validator.isValidObject(new Object[] { request.getRequestType(), request.getTrade().getContractNumber() },
					new String[] { "request_type", "contract_number" });
		} catch (Exception e) {
			LOG.error("Error in validateRequest() of Titan Message Validator : ", e);
		}
		return validator;
	}

}
