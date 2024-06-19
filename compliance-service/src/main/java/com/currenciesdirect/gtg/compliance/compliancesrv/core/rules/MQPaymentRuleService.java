package com.currenciesdirect.gtg.compliance.compliancesrv.core.rules;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringFundsProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;


/**
 * The Class MQPaymentRuleService.
 */
public class MQPaymentRuleService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(MQPaymentRuleService.class);
	
	/** The Constant OLD_PAYMENT_STATUS. */
	private static final String OLD_PAYMENT_STATUS = "oldPaymentStatus";
	
	
	/**
	 * Process.
	 *
	 * @param message the message
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {
		
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		TransactionMonitoringMQRequest transactionMonitoringMQRequest = (TransactionMonitoringMQRequest) messageExchange
				.getRequest();
		try {
		MessageExchange exchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);
		
		
		if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_in")
				|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_in_update")) {
			paymentInCreateResponse(exchange);
		} else if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_out")
				|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_out_update")) {
			paymentOutCreateResponse(exchange);
		}
		}catch (Exception e) {
			LOG.error("Error while sting services response in MQPaymentRuleService :: process() ::  ", e);
			message.getPayload().setFailed(true);
		}
		
		return message;
	}
	
	/**
	 * Payment in create response.
	 *
	 * @param exchange the exchange
	 */
	private void paymentInCreateResponse(MessageExchange exchange) {
		TransactionMonitoringMQServiceResponse mqResponse = (TransactionMonitoringMQServiceResponse) exchange
				.getResponse();
		
		TransactionMonitoringMQServiceRequest mqRequest = (TransactionMonitoringMQServiceRequest) exchange
				.getRequest();
		List<FundsInReasonCode> responseReasons = new ArrayList<>(10);
		
		
		
		try {
			TransactionMonitoringPaymentsInRequest request = mqRequest.getTransactionMonitoringPaymentsInRequest();
			TransactionMonitoringPaymentInResponse response = mqResponse.getTransactionMonitoringPaymentInResponse();

			
			if (FundsInComplianceStatus.HOLD.name().equals(request.getAdditionalAttribute(OLD_PAYMENT_STATUS))) {
				response.setPaymentStatus(FundsInComplianceStatus.CLEAR.name());
				response.setResponseCode(FundsInReasonCode.PASS.getReasonCode());
				response.setResponseDescription(FundsInReasonCode.PASS.getReasonDescription());

				if (!ServiceStatus.PASS.name().equals(request.getSanctionContactStatus())
						&& !ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionContactStatus())
						&& !ServiceStatus.NOT_REQUIRED.name().equals(request.getSanctionContactStatus())) {
					responseReasons.add(FundsInReasonCode.SANCTIONED);
				} else if (ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionContactStatus())) {
					responseReasons.add(FundsInReasonCode.SANCTION_SERVICE_FAIL);
				}

				if (!ServiceStatus.PASS.name().equals(response.getStatus())
						&& !ServiceStatus.SERVICE_FAILURE.name().equals(response.getStatus())
						&& !ServiceStatus.NOT_REQUIRED.name().equals(response.getStatus())
						&& !ServiceStatus.NOT_PERFORMED.name().equals(response.getStatus())) {
					responseReasons.add(FundsInReasonCode.TRANSACTION_MONITORING_CHECK_FAIL);
				} else if (ServiceStatus.SERVICE_FAILURE.name().equals(response.getStatus())) {
					responseReasons.add(FundsInReasonCode.TRANSACTION_MONITORING_SERVICE_FAIL);
				}

				checkResponseReasonSize(response, responseReasons);
				request.setUpdateStatus(response.getPaymentStatus());
			}else {
				response.setPaymentStatus(request.getAdditionalAttribute(OLD_PAYMENT_STATUS).toString());
			}
		} catch (Exception e) {
			LOG.error("Error while sting services response in MQPaymentRuleService :: paymentInCreateResponse() ::  ", e);
		}
		
	}
		
	/**
	 * Check response reason size.
	 *
	 * @param response the response
	 * @param responseReasons the response reasons
	 */
	private void checkResponseReasonSize(TransactionMonitoringPaymentInResponse response,
			List<FundsInReasonCode> responseReasons) {
		if (!responseReasons.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (FundsInReasonCode field : responseReasons) {
				if (!(sb.toString().contains(field.getReasonDescription()))) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					sb.append(field.getReasonDescription());
				}
				response.setResponseDescription(sb.toString());
				response.setPaymentStatus(FundsInComplianceStatus.HOLD.name());
			}

		} 
	}
	
	/**
	 * Payment out create response.
	 *
	 * @param exchange the exchange
	 */
	private void paymentOutCreateResponse(MessageExchange exchange) {
		TransactionMonitoringMQServiceResponse mqResponse = (TransactionMonitoringMQServiceResponse) exchange
				.getResponse();
		
		TransactionMonitoringMQServiceRequest mqRequest = (TransactionMonitoringMQServiceRequest) exchange
				.getRequest();
		List<FundsOutReasonCode> responseReasons = new ArrayList<>();
		
		
		
		try {
			TransactionMonitoringPaymentsOutRequest request = mqRequest.getTransactionMonitoringPaymentsOutRequest();
			TransactionMonitoringPaymentOutResponse response = mqResponse.getTransactionMonitoringPaymentOutResponse();

			
			if (FundsOutComplianceStatus.HOLD.name().equals(request.getAdditionalAttribute(OLD_PAYMENT_STATUS))) {

				response.setPaymentStatus(FundsOutComplianceStatus.CLEAR.name());
				response.setResponseCode(FundsOutReasonCode.PASS.getFundsOutReasonCode());
				response.setResponseDescription(FundsOutReasonCode.PASS.getFundsOutReasonDescription());

				setSanctionStatusResponse(responseReasons, request);

				if (!ServiceStatus.PASS.name().equals(response.getStatus())
						&& !ServiceStatus.SERVICE_FAILURE.name().equals(response.getStatus())
						&& !ServiceStatus.NOT_REQUIRED.name().equals(response.getStatus())
						&& !ServiceStatus.NOT_PERFORMED.name().equals(response.getStatus())) {
					responseReasons.add(FundsOutReasonCode.TRANSACTION_MONITORING_CHECK_FAIL);
				} else if (ServiceStatus.SERVICE_FAILURE.name().equals(response.getStatus())) {
					responseReasons.add(FundsOutReasonCode.TRANSACTION_MONITORING_SERVICE_FAIL);
				}

				checkResponseReasonSize(response, responseReasons);
				request.setUpdateStatus(response.getPaymentStatus());
			}else {
				response.setPaymentStatus(request.getAdditionalAttribute(OLD_PAYMENT_STATUS).toString());
			}
			
		} catch (Exception e) {
			LOG.error("Error while sting services response in MQPaymentRuleService :: paymentOutCreateResponse() ::  ", e);
		}

	}

	/**
	 * Sets the sanction status response.
	 *
	 * @param responseReasons the response reasons
	 * @param request the request
	 */
	private void setSanctionStatusResponse(List<FundsOutReasonCode> responseReasons,
			TransactionMonitoringPaymentsOutRequest request) {
		if (!ServiceStatus.PASS.name().equals(request.getSanctionContactStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionContactStatus())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(request.getSanctionContactStatus())) {
			responseReasons.add(FundsOutReasonCode.CONTACT_SANCTIONED);
		} else if (ServiceStatus.NOT_REQUIRED.name().equals(request.getSanctionContactStatus())) {
			responseReasons.add(FundsOutReasonCode.INACTIVE_CUSTOMER);
		}

		if (!ServiceStatus.NOT_REQUIRED.name().equals(request.getSanctionBankStatus())) {
			if (!ServiceStatus.PASS.name().equals(request.getSanctionBankStatus())
					&& !ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionBankStatus())) {
				responseReasons.add(FundsOutReasonCode.BANK_SANCTIONED);
			}

			if (!ServiceStatus.PASS.name().equals(request.getSanctionBeneficiaryStatus())
					&& !ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionBeneficiaryStatus())) {
				responseReasons.add(FundsOutReasonCode.BENEFICIARY_SANCTIONED);
			}
		}
		
		if(ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionContactStatus())
				|| ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionBankStatus())
				|| ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionBeneficiaryStatus())) {
			responseReasons.add(FundsOutReasonCode.SANCTION_SERVICE_FAIL);
		}
	}
	
	/**
	 * Check response reason size.
	 *
	 * @param response the response
	 * @param responseReasons the response reasons
	 */
	private void checkResponseReasonSize(TransactionMonitoringPaymentOutResponse response,
			List<FundsOutReasonCode> responseReasons) {
		if (!responseReasons.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (FundsOutReasonCode field : responseReasons) {
				if (!(sb.toString().contains(field.getFundsOutReasonDescription()))) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					sb.append(field.getFundsOutReasonDescription());
				}
				response.setResponseDescription(sb.toString());
				response.setPaymentStatus(FundsInComplianceStatus.HOLD.name());
			}

		} 
	}
}
