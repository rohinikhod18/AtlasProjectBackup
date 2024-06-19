package com.currenciesdirect.gtg.compliance.compliancesrv.tmemailalert;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailHeaderForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailPayloadForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequestForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionResponse;
import com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport.ICommHubServiceImpl;
import com.currenciesdirect.gtg.compliance.commons.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq.PostCardTransactionMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

public class PostCardTransactionEmailAlertImpl {
	
	/** The log. */
	private static final Logger LOG = LoggerFactory.getLogger(PostCardTransactionEmailAlertImpl.class);
	
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
		MessageExchange messageExchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
		TransactionMonitoringPostCardTransactionRequest postCardMQRequest = (TransactionMonitoringPostCardTransactionRequest) messageExchange.getRequest();
		TransactionMonitoringPostCardTransactionResponse tmCardResponse = (TransactionMonitoringPostCardTransactionResponse) messageExchange
				.getResponse();
		
		SendEmailRequestForTM sendTMEmailRequest = new SendEmailRequestForTM(); 
		try {
			
			sendTMEmailRequest = preparePostCardTransactionEmailRequest(sendTMEmailRequest,tmCardResponse.getErrorDescription(), postCardMQRequest.getTrxID());
			if(message.getPayload().getRetryCount() == Integer.parseInt(System.getProperty("transactionmonitoring.retrycount"))) {
				iCommHubServiceImpl.sendEmailForTMAlert(sendTMEmailRequest,true);
			}
			
		} catch (Exception e) {
			LOG.error("Error in Post Card Transaction sendEmailAlert()", e);
		}
		return message;
	}
	
	
	
	/**
	 * Prepare post card transaction email request.
	 *
	 * @param sendEmailRequest the send email request
	 * @return the send email request for TM
	 */
	private SendEmailRequestForTM preparePostCardTransactionEmailRequest(SendEmailRequestForTM sendEmailRequest, String errorDesc, String instructionNumber) {

		EmailHeaderForTM header = new EmailHeaderForTM();
		EmailPayloadForTM payload = new EmailPayloadForTM();

		header.setSourceSystem(Constants.SOURCE_ATLAS);
		header.setOrgCode("Currencies Direct");
		header.setLegalEntity("CDLGB");
		
		String email = System.getProperty("intuition.sendTo");
		List<String> list = Arrays.asList(email.split(","));

		payload.setEmailId(list);
		payload.setSubject("Intuition Post Card Transaction service unavailable");
		payload.setEmailContent("Post Card Transaction request for Instruction Number " + instructionNumber
				+ " to Intuition failed, due to "+errorDesc+", please alert dev team.");
		payload.setFromEmilId(System.getProperty("intuition.sendFrom"));
		payload.setReplyToEmailId(System.getProperty("intuition.sendFrom"));

		sendEmailRequest.setTmHeader(header);
		sendEmailRequest.setTmPayload(payload);

		return sendEmailRequest;
	}

}
