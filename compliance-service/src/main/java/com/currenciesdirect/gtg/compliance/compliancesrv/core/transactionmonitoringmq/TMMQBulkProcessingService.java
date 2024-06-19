package com.currenciesdirect.gtg.compliance.compliancesrv.core.transactionmonitoringmq;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.TransactionMonitoringMQDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

public class TMMQBulkProcessingService {
	
	private static final Logger LOG = LoggerFactory.getLogger(TMMQBulkProcessingService.class);

	@Autowired
	protected TransactionMonitoringMQDBServiceImpl transactionMonitoringMQDBServiceImpl;
	
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> bulkProcessing(Message<MessageContext> message) {

		MessageExchange exchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);
		
		TransactionMonitoringMQServiceResponse mqResponse = (TransactionMonitoringMQServiceResponse) exchange
				.getResponse();
		
		List<TransactionMonitoringMQRequest> bulkProcessList;
		Boolean status;
		Boolean	statusReg = Boolean.FALSE;
		Boolean	statusPayIn = Boolean.FALSE;
		Boolean	statusPayOut = Boolean.FALSE;
		if(null != mqResponse.getTransactionMonitoringSignupResponse() ) {
				statusReg = mqResponse.getTransactionMonitoringSignupResponse()
					.getTransactionMonitoringAccountSignupResponse().getHttpStatus().equals(200);
		}
						
		if (null != mqResponse.getTransactionMonitoringPaymentInResponse()
				&& mqResponse.getTransactionMonitoringPaymentInResponse().getHttpStatus() != null) {
			statusPayIn = mqResponse.getTransactionMonitoringPaymentInResponse().getHttpStatus().equals(200);
		}

		if (null != mqResponse.getTransactionMonitoringPaymentOutResponse()
				&& mqResponse.getTransactionMonitoringPaymentOutResponse().getHttpStatus() != null) {
			statusPayOut = mqResponse.getTransactionMonitoringPaymentOutResponse().getHttpStatus().equals(200);
		}
		
		status = statusReg || statusPayIn || statusPayOut;
		
		
		try {
			if (Boolean.TRUE.equals(status)) {
				bulkProcessList = transactionMonitoringMQDBServiceImpl.loadAllMessageFromDB();
				TMBulkReprocessMonitorThread recheckMonitorThread = new TMBulkReprocessMonitorThread(bulkProcessList);
				recheckMonitorThread.start();
			}
			
			
		} catch (Exception e) {
			LOG.error("Error while processing TMMQBulkProcessingService :: bulkProcessing() ::  ", e);
			message.getPayload().setFailed(true);
		}
		
				
		return message;
		
	}

}
