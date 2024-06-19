package com.currenciesdirect.gtg.compliance.compliancesrv.core.transactionmonitoring;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
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
		IntuitionPaymentStatusUpdateRequest intuitonUpdateRequest = null;
		IntuitionPaymentStatusUpdateResponse intuitionUpdateResponse = new IntuitionPaymentStatusUpdateResponse();
		FieldValidator validator = null;

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		try {
			intuitonUpdateRequest = (IntuitionPaymentStatusUpdateRequest) messageExchange.getRequest();
			validator = validateStatusUpdateRequest(intuitonUpdateRequest);
			if (!validator.isFailed()) {
				createBaseResponse(intuitionUpdateResponse, validator, ComplianceReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, "INTUITION PAYMENT UPDATE STATUS");
			} else {
				intuitionUpdateResponse.setDecision(BaseResponse.DECISION.SUCCESS);
			}
		} catch (Exception e) {
			LOG.error(REQUEST_IS_NOT_VALID, e);
			intuitionUpdateResponse.setDecision(BaseResponse.DECISION.FAIL);
			intuitionUpdateResponse.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			intuitionUpdateResponse.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		messageExchange.setResponse(intuitionUpdateResponse);
		return message;
	}

	/**
	 * Validate status update request.
	 *
	 * @param intuitonUpdateRequest the intuiton update request
	 * @return the field validator
	 */
	private FieldValidator validateStatusUpdateRequest(IntuitionPaymentStatusUpdateRequest intuitonUpdateRequest) {
		FieldValidator validator = new FieldValidator();
		validator.mergeErrors(validateMandatoryFields(intuitonUpdateRequest));
		return validator;
	}

	/**
	 * Validate mandatory fields.
	 *
	 * @param intuitonUpdateRequest the intuiton update request
	 * @return the field validator
	 */
	private FieldValidator validateMandatoryFields(IntuitionPaymentStatusUpdateRequest intuitonUpdateRequest) {
		FieldValidator validator = new FieldValidator();
		try {
			validator.isValidObject(
					new Object[] { intuitonUpdateRequest.getTradeContractNumber(),
							intuitonUpdateRequest.getTradePaymentID(), intuitonUpdateRequest.getTrxType(),
							intuitonUpdateRequest.getStatus(), intuitonUpdateRequest.getAction()}, //Add for AT-4880
					new String[] { "TradeContractNumber", "TradePaymentID", "trx_type", "Status", "Action"});
		} catch (Exception e) {
			LOG.error("Error in validation", e);
		}
		return validator;
	}

}
