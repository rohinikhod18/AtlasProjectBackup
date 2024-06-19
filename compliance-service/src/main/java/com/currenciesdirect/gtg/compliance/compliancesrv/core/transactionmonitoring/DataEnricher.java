package com.currenciesdirect.gtg.compliance.compliancesrv.core.transactionmonitoring;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInTrade;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.Beneficiary;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringUpdatePaymentStatusRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringUpdateRegStatusRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.AbstractDataEnricher;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsInDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsOutDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
public class DataEnricher extends AbstractDataEnricher {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataEnricher.class);
	@Autowired
	protected NewRegistrationDBServiceImpl newRegDBServiceImpl;
	
	/** The funds in DB service impl. */
	@Autowired
	protected FundsInDBServiceImpl fundsInDBServiceImpl;
	
	/** The funds out DB service impl. */
	@Autowired
	protected FundsOutDBServiceImpl fundsOutDBServiceImpl;
	
	/** The Constant GATEWAY_MESSAGE. */
	private static final String GATEWAY_MESSAGE = "Gateway message headers=[{}], payload=[{}] validated!!";
	
	/** The Constant INVALID_REQUEST_MSG. */
	private static final String INVALID_REQUEST_MSG = "Request is not valid";
	
	/** The Constant UPDATE_INTUITION_REQUEST. */
	private static final String UPDATE_INTUITION_REQUEST = "updateIntuitionRequest";
	
	/** The Constant ACCOUNT. */
	private static final String ACCOUNT = "account";

	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		TransactionMonitoringSignupResponse response = messageExchange
				.getResponse(TransactionMonitoringSignupResponse.class);
		LOGGER.debug(GATEWAY_MESSAGE, message.getHeaders(),
				message.getPayload());
		TransactionMonitoringUpdateRegStatusRequest request = messageExchange
				.getRequest(TransactionMonitoringUpdateRegStatusRequest.class);
		RegistrationServiceRequest registrationrequest = new RegistrationServiceRequest();
		RegistrationResponse regResponse = new RegistrationResponse();
		try {
			getUserTableId(message);
			
			Account account = new Account();
			account.setTradeAccountNumber(request.getTradeAccountNumber());
			account.setId(request.getAccountId());
			account.setVersion(request.getAccountVersion());
			
			registrationrequest.setOrgCode(request.getOrgCode());
			
			registrationrequest.setAccount(account);
			
			messageExchange.setRequest(registrationrequest);
			ComplianceAccount compAcc = new ComplianceAccount();
			compAcc.setId(request.getAccountId());
			regResponse.setAccount(compAcc);
			messageExchange.setResponse(regResponse);
		} catch (Exception e) {
			LOGGER.error(INVALID_REQUEST_MSG, e);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		messageExchange.getRequest().addAttribute(UPDATE_INTUITION_REQUEST, request);

		return message;
	}
	
	//AT-4451
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> enrichDataForPaymentIn(Message<MessageContext> message, @Header UUID correlationID) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		TransactionMonitoringSignupResponse response = messageExchange
				.getResponse(TransactionMonitoringSignupResponse.class);
		LOGGER.debug(GATEWAY_MESSAGE, message.getHeaders(),
				message.getPayload());
		TransactionMonitoringUpdatePaymentStatusRequest request = messageExchange
				.getRequest(TransactionMonitoringUpdatePaymentStatusRequest.class);
		FundsInCreateRequest fundsInCreateRequest = new FundsInCreateRequest();
		FundsInCreateResponse fundsInCreateResponse = new FundsInCreateResponse();
		try {
			getUserTableId(message);
			FundsInTrade trade = new FundsInTrade();
			trade.setPaymentFundsInId(request.getTradePaymentId());
			
			fundsInCreateRequest.setOrgCode(request.getOrgCode());
			fundsInCreateRequest.setFundsInId(request.getPaymentInId());
			fundsInCreateRequest.setTrade(trade);

			Account account = new Account();
			account.setId(request.getAccountId());
			account.setVersion(request.getAccountVersion());
			
			fundsInCreateRequest.addAttribute(ACCOUNT, account);
			
			messageExchange.setRequest(fundsInCreateRequest);
			messageExchange.setResponse(fundsInCreateResponse);
		} catch (Exception e) {
			LOGGER.error(INVALID_REQUEST_MSG, e);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		messageExchange.getRequest().addAttribute(UPDATE_INTUITION_REQUEST, request);

		return message;
	}
	
	//AT-4451
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> enrichDataForPaymentOut(Message<MessageContext> message, @Header UUID correlationID) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		TransactionMonitoringSignupResponse response = messageExchange
				.getResponse(TransactionMonitoringSignupResponse.class);
		LOGGER.debug(GATEWAY_MESSAGE, message.getHeaders(),
				message.getPayload());
		TransactionMonitoringUpdatePaymentStatusRequest request = messageExchange
				.getRequest(TransactionMonitoringUpdatePaymentStatusRequest.class);
		FundsOutRequest fundsOutRequest = new FundsOutRequest();
		FundsOutResponse fundsOutResponse = new FundsOutResponse();
		try {
			getUserTableId(message);
			
			Beneficiary beneficiary = new Beneficiary();
			beneficiary.setPaymentFundsoutId(request.getTradePaymentId());
			
			fundsOutRequest.setOrgCode(request.getOrgCode());
			fundsOutRequest.setFundsOutId(request.getPaymentOutId());
			fundsOutRequest.setBeneficiary(beneficiary);
			
			Account account = new Account();
			account.setId(request.getAccountId());
			account.setVersion(request.getAccountVersion());
			
			fundsOutRequest.addAttribute(ACCOUNT, account);
			
			messageExchange.setRequest(fundsOutRequest);
			messageExchange.setResponse(fundsOutResponse);
		} catch (Exception e) {
			LOGGER.error(INVALID_REQUEST_MSG, e);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		messageExchange.getRequest().addAttribute(UPDATE_INTUITION_REQUEST, request);

		return message;
	}
	
	/**
	 * Enrich data for intuition payment.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	//AT-4749
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> enrichDataForIntuitionPayment(Message<MessageContext> message,
			@Header UUID correlationID) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		IntuitionPaymentStatusUpdateRequest request = messageExchange.getRequest(IntuitionPaymentStatusUpdateRequest.class);
		LOGGER.debug(GATEWAY_MESSAGE, message.getHeaders(),
				message.getPayload());
		
		FundsInCreateRequest fundsInCreateRequest = null;
		FundsOutRequest fundsOutRequest = null;
		Account account = new Account();
		String orgCode= null;
		
		try {
			if (request.getTrxType().equalsIgnoreCase("FUNDSIN")) {
				fundsInCreateRequest = fundsInDBServiceImpl.getFundsInDetailsForIntuition(Integer.parseInt(request.getTradePaymentID()));
				if(fundsInCreateRequest != null) {
				account.setId(fundsInCreateRequest.getAccId());
				account.setVersion((Integer) fundsInCreateRequest.getAdditionalAttribute("AccountVersion"));
				orgCode = (String) fundsInCreateRequest.getAdditionalAttribute("orgcode");
				
				request.addAttribute("fundsInRequest", fundsInCreateRequest);
				request.addAttribute(ACCOUNT, account);
				} else {
					throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR);
				}
				
			} else {
				fundsOutRequest = fundsOutDBServiceImpl.getFundsOutDetailsForIntuition(Integer.parseInt(request.getTradePaymentID()));
				if (fundsOutRequest != null) {
					account.setId(fundsOutRequest.getAccId());
					account.setVersion((Integer) fundsOutRequest.getAdditionalAttribute("AccountVersion"));
					orgCode = (String) fundsOutRequest.getAdditionalAttribute("orgcode");

					request.addAttribute("fundsOutRequest", fundsOutRequest);
					request.addAttribute(ACCOUNT, account);
				} else {
					throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR);
				}
			}
			
			message.getPayload().setOrgCode(orgCode);

		} catch (Exception e) {
			LOGGER.error(INVALID_REQUEST_MSG, e);
			message.getPayload().setFailed(true);
		}
		

		return message;
	}
}
