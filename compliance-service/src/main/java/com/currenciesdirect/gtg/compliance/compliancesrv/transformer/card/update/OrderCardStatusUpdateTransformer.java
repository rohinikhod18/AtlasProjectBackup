package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.card.update;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.OrderCardResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

/**
 * The Class OrderCardStatusUpdateTransformer.
 */
public class OrderCardStatusUpdateTransformer extends AbstractTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(OrderCardStatusUpdateTransformer.class);

	/**
	 * Transform request.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		return null;
	}

	/**
	 * Transform response.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		MessageExchange exchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.GATEWAY_SERVICE);

		MessageExchange transactionMonitoringExchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);

		try {
			TransactionMonitoringSignupResponse transactionMonitoringSignupResponse =
					(TransactionMonitoringSignupResponse) transactionMonitoringExchange.getResponse();
			TransactionMonitoringAccountSignupResponse transactionMonitoringAccountSignupResponse =
					transactionMonitoringSignupResponse.getTransactionMonitoringAccountSignupResponse();
			RegistrationResponse registrationResponse = (RegistrationResponse) message.getPayload()
					.getGatewayMessageExchange().getResponse();
			transform(exchange, registrationResponse);
		} catch (Exception e) {
			LOG.error("Error", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Transform.
	 *
	 * @param exchange the exchange
	 * @param registrationResponse the registration response
	 */
	private void transform(MessageExchange exchange, RegistrationResponse registrationResponse) {
			ComplianceAccount complianceAccount = registrationResponse.getAccount();

			OrderCardResponse orderCardResponse = new OrderCardResponse();
			orderCardResponse.setResponseCode(ComplianceReasonCode.PASS.getReasonCode());
		    orderCardResponse.setResponseDescription(ServiceStatus.PASS.name());
			orderCardResponse.setCardUpdateStatus(complianceAccount.getCardStatus());
			
			String jsonOrderCardResponse = JsonConverterUtil.convertToJsonWithoutNull(orderCardResponse);
			LOG.warn("\n -------Card Status Update Response -------- \n  {}", jsonOrderCardResponse);

			exchange.setResponse(orderCardResponse);
	}

}
