package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

/**
 * The Class FundsOutErrorHandler.
 *
 * @author manish
 */
public class FundsOutErrorHandler {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FundsOutErrorHandler.class);

	/**
	 * Log error.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> logError(Message<MessageContext> message) {
		FundsOutResponse response = createResponse(message);
		@SuppressWarnings("unchecked")
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ResponseEntity<ServiceMessageResponse> responseEntity;
		responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		deferredResult.setResult(responseEntity);
		LOG.debug("logError-->Response : {}", responseEntity);
		return message;
	}

	/**
	 * Creates the response.
	 *
	 * @param message
	 *            the message
	 * @return the funds out response
	 */
	private FundsOutResponse createResponse(Message<MessageContext> message) {
		String tradeContractNumber = null;
		String tradeAccountNumber = null;
		Integer paymentFundsOutid = null;
		String orgCode = null;
		MessageContext context = message.getPayload();
		MessageExchange exchange = context.getGatewayMessageExchange();
		OperationEnum operation = exchange.getOperation();
		if (OperationEnum.FUNDS_OUT == operation) {
			FundsOutRequest request = exchange.getRequest(FundsOutRequest.class);
			tradeContractNumber = request.getTrade().getContractNumber();
			paymentFundsOutid = request.getBeneficiary().getPaymentFundsoutId();
			tradeAccountNumber = request.getTradeAccountNumber();
			orgCode = request.getOrgCode();
		} else if (OperationEnum.DELETE_OPI == operation) {
			FundsOutDeleteRequest request = exchange.getRequest(FundsOutDeleteRequest.class);
			FundsOutRequest oldRequest = (FundsOutRequest) request.getAdditionalAttribute("oldRequest");
			if (oldRequest != null && oldRequest.getTrade() != null) {
				tradeContractNumber = oldRequest.getTrade().getContractNumber();
				tradeAccountNumber = oldRequest.getTradeAccountNumber();
			}
			paymentFundsOutid = request.getFundsOutId();
			orgCode = request.getOrgCode();
		} else if (OperationEnum.UPDATE_OPI == operation) {
			FundsOutUpdateRequest request = exchange.getRequest(FundsOutUpdateRequest.class);
			paymentFundsOutid = request.getPaymentFundsoutId();
			orgCode = request.getOrgCode();
			FundsOutRequest oldRequest = (FundsOutRequest) request.getAdditionalAttribute("oldRequest");
			if (oldRequest != null && oldRequest.getTrade() != null) {
				tradeContractNumber = oldRequest.getTrade().getContractNumber();
				tradeAccountNumber = oldRequest.getTradeAccountNumber();
			}
		}

		FundsOutResponse response = new FundsOutResponse();
		response.setOrgCode(orgCode);
		response.setOsrID(message.getHeaders().get(MessageContextHeaders.OSR_ID).toString());
		response.setStatus(FundsOutComplianceStatus.HOLD.name());
		response.setDecision(DECISION.FAIL);
		response.setResponseReason(FundsOutReasonCode.SYSTEM_FAILURE);
		response.setTradeContractNumber(tradeContractNumber);
		response.setTradeAccountNumber(tradeAccountNumber);
		response.setTradePaymentID(paymentFundsOutid);
		context.getGatewayMessageExchange().setResponse(response);
		return response;
	}

}
