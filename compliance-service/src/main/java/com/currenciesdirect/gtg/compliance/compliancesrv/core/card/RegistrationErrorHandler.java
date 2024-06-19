package com.currenciesdirect.gtg.compliance.compliancesrv.core.card;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import java.sql.Timestamp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

public class RegistrationErrorHandler {
	public Message<MessageContext> logError(Message<MessageContext> message) {
		
		MessageExchange exchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
		
		TransactionMonitoringSignupResponse response = createResponse(exchange);
		
		@SuppressWarnings("unchecked")
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ResponseEntity<ServiceMessageResponse> responseEntity;
		responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		deferredResult.setResult(responseEntity);
		return message;
	}

	private TransactionMonitoringSignupResponse createResponse(MessageExchange exchange) {
		
		TransactionMonitoringSignupResponse response = exchange.getResponse(TransactionMonitoringSignupResponse.class);
		TransactionMonitoringAccountSignupResponse tmSingUpAccountResponse = response.getTransactionMonitoringAccountSignupResponse();
		TransactionMonitoringSignupRequest request = (TransactionMonitoringSignupRequest) exchange.getRequest();
		
		EventServiceLog eventServiceLog = exchange.getEventServiceLog(
				ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
				request.getTransactionMonitoringAccountRequest().getId());
		
		TransactionMonitoringAccountProviderResponse tmAccountProviderResponse = tmSingUpAccountResponse.getTransactionMonitoringAccountProviderResponse();
		TransactionMonitoringAccountSummary accSummary = JsonConverterUtil
				.convertToObject(TransactionMonitoringAccountSummary.class, eventServiceLog.getSummary());

		tmSingUpAccountResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		accSummary.setAccountId(tmSingUpAccountResponse.getAccountId());	
		accSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		accSummary.setHttpStatus(tmSingUpAccountResponse.getHttpStatus());	

		tmAccountProviderResponse.setStatus(Constants.SERVICE_FAILURE);
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmAccountProviderResponse));
		eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(accSummary));
		eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		return response;
	}
}

