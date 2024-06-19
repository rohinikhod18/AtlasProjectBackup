package com.currenciesdirect.gtg.compliance.compliancesrv.core.transactionmonitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

public class TransactionMonitoringUpdateStatusResponse {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionMonitoringUpdateStatusResponse.class);

	/**
	 * Process.
	 *
	 * @param message the message
	 * @return the message
	 */
	@SuppressWarnings("unchecked")
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {
		try {
			if (message.getHeaders().get(MessageContextHeaders.GATEWAY_OPERATION)
					.equals(OperationEnum.UPDATE_ACCOUNT.name())
					|| message.getHeaders().get(MessageContextHeaders.GATEWAY_OPERATION)
							.equals(OperationEnum.REPEAT_CHECK_STATUS_UPDATE.name())) {

				MessageExchange exchange = message.getPayload()
						.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
				TransactionMonitoringSignupResponse response = exchange
						.getResponse(TransactionMonitoringSignupResponse.class);
				TransactionMonitoringAccountSignupResponse accResponse = response
						.getTransactionMonitoringAccountSignupResponse();
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						accResponse.getAccountId());
				

				if (InternalProcessingCode.INV_REQUEST.name().equals(accResponse.getErrorCode())) {
					DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
							.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

					ResponseEntity<EventServiceLog> responseEntity = new ResponseEntity<>(eventServiceLog,
							HttpStatus.BAD_REQUEST);

					deferredResult.setResult(responseEntity);
				} else {
					DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
							.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

					ResponseEntity<EventServiceLog> responseEntity = new ResponseEntity<>(eventServiceLog,
							HttpStatus.OK);

					deferredResult.setResult(responseEntity);
				}
				return message;
			} else {
				LOGGER.warn("TransactionMonitoringSignupResponse Response : NO RESPONSE ");
			}
		} catch (Exception e) {
			LOGGER.error("Exception in TransactionMonitoringUpdateStatusResponse process(): ", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
	
	@SuppressWarnings("unchecked")
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> processForFundsIn(Message<MessageContext> message) {
		try {
			if (message.getHeaders().get(MessageContextHeaders.GATEWAY_OPERATION)
					.equals(OperationEnum.REPEAT_CHECK_STATUS_UPDATE.name())) {

				MessageExchange exchange = message.getPayload()
						.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
				TransactionMonitoringPaymentInResponse response = exchange
						.getResponse(TransactionMonitoringPaymentInResponse.class);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						response.getId());
				

				if (InternalProcessingCode.INV_REQUEST.name().equals(response.getErrorCode())) {
					DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
							.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

					ResponseEntity<EventServiceLog> responseEntity = new ResponseEntity<>(eventServiceLog,
							HttpStatus.BAD_REQUEST);

					deferredResult.setResult(responseEntity);
				} else {
					DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
							.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

					ResponseEntity<EventServiceLog> responseEntity = new ResponseEntity<>(eventServiceLog,
							HttpStatus.OK);

					deferredResult.setResult(responseEntity);
				}
				return message;
			} else {
				LOGGER.warn("TransactionMonitoringPaymentInResponse Response : NO RESPONSE ");
			}
		} catch (Exception e) {
			LOGGER.error("Exception in TransactionMonitoringUpdateStatusResponse processFundsIn(): ", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> processForFundsOut(Message<MessageContext> message) {
		try {
			if (message.getHeaders().get(MessageContextHeaders.GATEWAY_OPERATION)
					.equals(OperationEnum.REPEAT_CHECK_STATUS_UPDATE.name())) {

				MessageExchange exchange = message.getPayload()
						.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
				TransactionMonitoringPaymentOutResponse response = exchange
						.getResponse(TransactionMonitoringPaymentOutResponse.class);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						response.getId());
				

				if (InternalProcessingCode.INV_REQUEST.name().equals(response.getErrorCode())) {
					DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
							.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

					ResponseEntity<EventServiceLog> responseEntity = new ResponseEntity<>(eventServiceLog,
							HttpStatus.BAD_REQUEST);

					deferredResult.setResult(responseEntity);
				} else {
					DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
							.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

					ResponseEntity<EventServiceLog> responseEntity = new ResponseEntity<>(eventServiceLog,
							HttpStatus.OK);

					deferredResult.setResult(responseEntity);
				}
				return message;
			} else {
				LOGGER.warn("TransactionMonitoringPaymentOutResponse Response : NO RESPONSE ");
			}
		} catch (Exception e) {
			LOGGER.error("Exception in TransactionMonitoringUpdateStatusResponse processFundsOut(): ", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
}
