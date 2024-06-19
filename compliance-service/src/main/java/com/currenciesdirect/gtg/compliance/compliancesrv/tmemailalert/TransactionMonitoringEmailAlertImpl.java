package com.currenciesdirect.gtg.compliance.compliancesrv.tmemailalert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailHeader;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailHeaderForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailPayload;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailPayloadForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequestForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport.ICommHubServiceImpl;
import com.currenciesdirect.gtg.compliance.commons.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.externalservice.enterpriseport.EmailClientImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

/**
 * The Class TransactionMonitoringEmailAlertImpl.
 */
public class TransactionMonitoringEmailAlertImpl {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(TransactionMonitoringEmailAlertImpl.class);
	
	/** The i comm hub service impl. */
	@Autowired
	private ICommHubServiceImpl iCommHubServiceImpl;
	
	/**
	 * Send email alert.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> sendEmailAlert(Message<MessageContext> message) {
		
		SendEmailRequestForTM sendTMEmailRequest = new SendEmailRequestForTM(); 
		try {
			MessageExchange exchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);
			
			TransactionMonitoringMQServiceResponse response = (TransactionMonitoringMQServiceResponse) exchange.getResponse();
			TransactionMonitoringMQServiceRequest request = (TransactionMonitoringMQServiceRequest) exchange.getRequest();
			
			if(request.getRequestType().equalsIgnoreCase("signup")) {
				TransactionMonitoringAccountSignupResponse tmSingUpAccountResponse = new TransactionMonitoringAccountSignupResponse();
				tmSingUpAccountResponse = response.getTransactionMonitoringSignupResponse().getTransactionMonitoringAccountSignupResponse();
				String content = "Signup request for Customer Number "+ request.getTransactionMonitoringSignupRequest().getTransactionMonitoringAccountRequest().getTradeAccountNumber() 
						+" to Intuition failed, due to intuition error desc : "+
						tmSingUpAccountResponse.getErrorDesc() +". <BR>"+tmSingUpAccountResponse.getErrorDescription()+". <br> Please check.";
				sendTMEmailRequest = prepareTMEmailRequest(sendTMEmailRequest, content);
			}
			else if(request.getRequestType().equalsIgnoreCase("payment_in")) {
				TransactionMonitoringPaymentInResponse paymentInResponse = response.getTransactionMonitoringPaymentInResponse();
				String content = "PaymentIN request for Contract Number "+ request.getTransactionMonitoringPaymentsInRequest().getContractNumber() 
						+" to Intuition failed, due to intuition error desc : "+
						paymentInResponse.getErrorDesc() +". <BR>"+paymentInResponse.getErrorDescription()+". <br> Please check.";
				sendTMEmailRequest = prepareTMEmailRequest(sendTMEmailRequest, content);
			}
			else if(request.getRequestType().equalsIgnoreCase("payment_out")) {
				TransactionMonitoringPaymentOutResponse paymentOutResponse = response.getTransactionMonitoringPaymentOutResponse();
				String content = "PaymentOUT request for Contract Number "+ request.getTransactionMonitoringPaymentsOutRequest().getContractNumber() 
						+" to Intuition failed, due to intuition error desc : "+
						paymentOutResponse.getErrorDesc() +". <BR>"+paymentOutResponse.getErrorDescription()+". <br> Please check.";
				sendTMEmailRequest = prepareTMEmailRequest(sendTMEmailRequest, content);
			}
				
			if(message.getPayload().getRetryCount() == Integer.parseInt(System.getProperty("transactionmonitoring.retrycount"))) {
				iCommHubServiceImpl.sendEmailForTMAlert(sendTMEmailRequest,true);
			}
			
		} catch (Exception e) {
			log.error("Error in sendEmailAlert()", e);
		}
		return message;
	}
	
	/**
	 * Prepare TM email request.
	 *
	 * @param sendEmailRequest the send email request
	 * @param transactionMonitoringMQRequest the transaction monitoring MQ request
	 * @return the send email request for TM
	 */
	private SendEmailRequestForTM prepareTMEmailRequest(SendEmailRequestForTM sendEmailRequest, String content) {

		EmailHeaderForTM header = new EmailHeaderForTM();
		EmailPayloadForTM payload = new EmailPayloadForTM();

		header.setSourceSystem(Constants.SOURCE_ATLAS);
		header.setOrgCode("Currencies Direct");
		header.setLegalEntity("CDLGB");
		
		String email = System.getProperty("intuition.sendTo");
		List<String> list = Arrays.asList(email.split(","));

		payload.setEmailId(list);
		payload.setSubject("Intuition service unavailable");
		payload.setEmailContent(content);
		payload.setFromEmilId(System.getProperty("intuition.sendFrom"));
		payload.setReplyToEmailId(System.getProperty("intuition.sendFrom"));

		sendEmailRequest.setTmHeader(header);
		sendEmailRequest.setTmPayload(payload);

		return sendEmailRequest;
	}
	
}
