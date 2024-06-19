package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.card;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.OrderCardResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

/**
 * The Class OrderCardTransformer.
 */
public class OrderCardTransformer extends AbstractTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(OrderCardTransformer.class);

	/** The new reg DB service impl. */
	@Autowired
	protected NewRegistrationDBServiceImpl newRegDBServiceImpl;

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
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.GATEWAY_SERVICE);

		MessageExchange transactionMonitoringExchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);

		try {
			TransactionMonitoringSignupResponse transactionMonitoringSignupResponse = (TransactionMonitoringSignupResponse) transactionMonitoringExchange
					.getResponse();
			TransactionMonitoringAccountSignupResponse transactionMonitoringAccountSignupResponse = transactionMonitoringSignupResponse
					.getTransactionMonitoringAccountSignupResponse();
			String status = transactionMonitoringAccountSignupResponse.getStatus();

			if (ServiceStatus.SERVICE_FAILURE.name().equals(status)) {
				message.getPayload().setFailed(true);
				return message;
			}

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
	 * @param exchange             the exchange
	 * @param registrationResponse the registration response
	 */
	private void transform(MessageExchange exchange, RegistrationResponse registrationResponse) {
		ComplianceAccount complianceAccount = registrationResponse.getAccount();

		OrderCardResponse orderCardResponse = new OrderCardResponse();
		orderCardResponse.setStatus(ServiceStatus.PASS.name());
		orderCardResponse.setCardEligibilityStatus(complianceAccount.getCardDecision());

		exchange.setResponse(orderCardResponse);
	}

	/**
	 * Update account TM flag.
	 *
	 * @param message the message
	 * @return the message
	 */
	// AT-5127
	public Message<MessageContext> updateAccountTMFlag(Message<MessageContext> message) {
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.GATEWAY_SERVICE);
		RegistrationServiceRequest regRequest = exchange.getRequest(RegistrationServiceRequest.class);

		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

		MessageExchange transactionMonitoringExchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
		TransactionMonitoringSignupResponse transactionMonitoringSignupResponse = (TransactionMonitoringSignupResponse) transactionMonitoringExchange
				.getResponse();

		try {
			if (transactionMonitoringSignupResponse.getTransactionMonitoringAccountSignupResponse() != null) {
				Integer status = transactionMonitoringSignupResponse.getTransactionMonitoringAccountSignupResponse()
						.getHttpStatus();
				boolean loadCriteria = (boolean) regRequest.getAdditionalAttribute("loadCriteria");
				if (status == 400 && loadCriteria) {
					newRegDBServiceImpl.updateIntoAccountTMFlag(regRequest.getAccount().getId(), 0, token.getUserID());
				}
			}

		} catch (Exception e) {
			LOG.error("Error", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

}
