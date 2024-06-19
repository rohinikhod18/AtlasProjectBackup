package com.currenciesdirect.gtg.compliance.compliancesrv.core.titan;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.titan.request.TitanGetPaymentStatusRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.AbstractDataEnricher;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsInDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsOutDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

public class DataEnricher extends AbstractDataEnricher {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataEnricher.class);

	/** The funds in DB service impl. */
	@Autowired
	protected FundsInDBServiceImpl fundsInDBServiceImpl;

	/** The funds out DB service impl. */
	@Autowired
	protected FundsOutDBServiceImpl fundsOutDBServiceImpl;

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
		FundsInCreateResponse response = (FundsInCreateResponse) messageExchange.getResponse();
		Integer paymentStatus;

		try {
			if (request.getRequestType().equalsIgnoreCase("FUNDSIN")) {
				paymentStatus = fundsInDBServiceImpl.getPaymentInStatusByTradePaymentId(request.getTradePaymentId());
				if (paymentStatus != 0) {
					response.setStatus(FundsInComplianceStatus.getFundsInComplianceStatusAsString(paymentStatus));
					response.setTradeContractNumber(request.getTrade().getContractNumber());
					response.setTradeAccountNumber(request.getTrade().getTradeAccountNumber());
					response.setTradePaymentID(request.getTradePaymentId());
					response.setOrgCode(request.getOrgCode());
				} else {
					response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
					response.setErrorDescription("Record not found for Payment In with given contract_number ");
				}
			} else {
				paymentStatus = fundsOutDBServiceImpl.getPaymentOutStatusByTradePaymentId(request.getTradePaymentId());
				if (paymentStatus != 0) {
					response.setStatus(FundsInComplianceStatus.getFundsInComplianceStatusAsString(paymentStatus));
					response.setTradeContractNumber(request.getTrade().getContractNumber());
					response.setTradeAccountNumber(request.getTrade().getTradeAccountNumber());
					response.setTradePaymentID(request.getTradePaymentId());
					response.setOrgCode(request.getOrgCode());
				} else {
					response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
					response.setErrorDescription("Record not found for Payment Out with given contract_number ");
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error in DataEnricher of Titan Get Payment Status API : ", e);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}

		return message;
	}

}
