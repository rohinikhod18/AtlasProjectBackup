package com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.impl;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.KafkaDataLakeTxDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.FundsInKafkaService;
import com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.FundsOutKafkaService;
import com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.KafkaTxExecutorService;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.FundsInCustomCheckResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.FundsOutCustomCheckResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsInFraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsOutFruagsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsInBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutPaymentReferenceResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsInSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsOutSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringUpdatePaymentStatusRequest;
import com.currenciesdirect.kafka.transaction.CDTransaction;
import com.currenciesdirect.kafkaproducertemplate.producer.SimpleKafkaProducer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;


/**
 * @author prashant.verma
 */
@Service
public class KafkaTxExecutorServiceImpl implements KafkaTxExecutorService{

	@Autowired
	@Qualifier("KafkaTxExecutorService") 
	private ExecutorService kafkaTxExecutorService;
	
    @Autowired
    SimpleKafkaProducer kafkaClientService;

	@Autowired
	private FundsInKafkaService fundsInKafkaService;

	@Autowired
	private FundsOutKafkaService fundsOutKafkaService;

	@Autowired
	KafkaDataLakeTxDBServiceImpl kafkaDataLakeTxDBServiceImpl;

	@Value("${datalake.tx.kafka.topic}")
	public String topic;
	
	@Value("${datalake.tx.kafka.enable}")
	public String datalakeTxKafkaEnable;
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaTxExecutorServiceImpl.class);

	@SuppressWarnings("unlikely-arg-type")
	public Message<MessageContext> executeKafkaTx(Message<MessageContext> message) {
		String method = "KafkaTxExecutorServiceImpl - executeKafkaTx() : ";
		LOG.info(method+"------- executeKafkaTx(Message<MessageContext> message) : Start --------\n");
		
		try {
			LOG.info(method+"------- message : "+message+" --------\n");
			OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
			ServiceInterfaceType serviceType = message.getPayload().getGatewayMessageExchange().getServiceInterface();
			
			String transactionId = message.getPayload().getTransactionId();
			ServiceMessage messageRequest = message.getPayload().getGatewayMessageExchange().getRequest();
						
			executeKafkaTx(operation, serviceType, transactionId, messageRequest);
		} catch (Exception e) {
			LOG.error(method+" -----------  Got exception in executeTask(Message<MessageContext> message) request to kafka ---------", e);
		}
		LOG.info(method+"------- executeKafkaTx(Message<MessageContext> message) : End --------\n");
		return message;
	}

	public void executeKafkaTx(OperationEnum operation, ServiceInterfaceType serviceType,
			String transactionId, ServiceMessage messageRequest) {
		
		String method = "KafkaTxExecutorServiceImpl - executeKafkaTx() : ";
		LOG.info(method+"------- executeKafkaTx() : Start --------\n");
		LOG.info(method + "------- Feature flag datalake.tx.kafka.enable : "+datalakeTxKafkaEnable+" --------\n");
		
		if(!("true".equalsIgnoreCase(datalakeTxKafkaEnable))) {
			LOG.info(method + "------- Feature DatalakeTxKafka is Disabled --------\n");
			return;
		}
		
		LOG.info(method + "------- sending tx request to kafka Start --------\n");
		LOG.info(method + "------- topic : "+topic+" --------\n");

		kafkaTxExecutorService.submit(()->{
			try {
				
				CDTransaction cdTransaction = null;
				String messageId = null;

				LOG.info(method+"------- operation : "+operation+" -------- service : "+serviceType+" -------- \n");

				if(ServiceInterfaceType.FUNDSIN.equals(serviceType)) {
						Integer tradePaymentId = getFundsInTransactionIDFromPayload(messageRequest);
						cdTransaction = fundsInKafkaService.populatePaymentInTxDetails(tradePaymentId);
						String operationType = Constants.MODIFY;
						if(operation.equals(OperationEnum.FUNDS_IN)) {
							operationType= Constants.CREATE;
						}else if (operation.equals(OperationEnum.DELETE_OPI)) {
							operationType= Constants.DELETE;
						}
						setCdTransactionHdrForKafka(cdTransaction, transactionId, OperationEnum.FUNDS_IN, operationType);
				}
				else if (ServiceInterfaceType.PROFILE.equals(serviceType) ) {
				    	if(operation.equals(OperationEnum.SANCTION_UPDATE)) {
				    		cdTransaction = getTransactionForSanctionUpdate(messageRequest, transactionId);
				    	}else if(operation.equals(OperationEnum.TRANSACTION_MONITORING_MQ_RESEND)) {
				    		cdTransaction = getTransactionForTMRequest(messageRequest, transactionId);
				    	}
				} else if (ServiceInterfaceType.INTUITION.equals(serviceType)) {
    				    if(operation.equals(OperationEnum.STATUS_UPDATE)) {
    				    	cdTransaction= getTransactionForStatusUpdate(messageRequest, transactionId);
    				    }else if(operation.equals(OperationEnum.TRANSACTION_MONITORING_MQ_RESEND)) {
    				    	cdTransaction= getTransactionForTMRequest(messageRequest, transactionId);
    				    }
				}else if(ServiceInterfaceType.FUNDSOUT.equals(serviceType)) {
					Integer tradePaymentId = getFundsOutTransactionIDFromPayload(messageRequest);
					cdTransaction = fundsOutKafkaService.populatePaymentOutTxDetails(tradePaymentId);
						String operationType = Constants.MODIFY;
						if(operation.equals(OperationEnum.FUNDS_OUT)) {
							operationType= Constants.CREATE;
						}else if (operation.equals(OperationEnum.DELETE_OPI)) {
							operationType= Constants.DELETE;
						}
						setCdTransactionHdrForKafka(cdTransaction, transactionId, OperationEnum.FUNDS_OUT, operationType);					
				}
				try {
					if(cdTransaction != null) {
						//String kafkaMessage = cdTransaction.toString();
						messageId = cdTransaction.getMessageHeader().getCdId();
						LOG.info(method+"------- sending tx request to kafka Start --------\n");
						LOG.info(method+" ------- message id : "+messageId+" --------\n");

						kafkaClientService.sendMessage(topic, cdTransaction, messageId);
						LOG.info(method+"------- Message Successfully submitted to producer !--------\n");
					} else {
						LOG.error(method+" -----------  Got cdTransaction : " + cdTransaction);
					}
				}catch(Exception e) {
					LOG.error(method+" -----------  Got exception in sending tx request to kafka ---------", e);
					long auditId = kafkaDataLakeTxDBServiceImpl.auditFailedKafkaRequest(cdTransaction, messageId, topic);
					LOG.info(method+" -----------  Audited failed tx request with auditId : "+auditId);
				}
				LOG.info(method+" -----------  sending tx request to kafka End ---------\n");


			}catch(Exception e) {
				LOG.error(method+" -----------  Got exception in executeTask request to kafka ---------", e);
			}
		});
		LOG.info(method+"------- executeKafkaTx() : End --------\n");
	}

	private CDTransaction getTransactionForSanctionUpdate(ServiceMessage messageRequest, String transactionId )throws ComplianceException {
		CDTransaction cdTransaction = null;
		OperationEnum operationType= OperationEnum.FUNDS_IN;
		SanctionUpdateRequest sanctionUpdateRequest = (SanctionUpdateRequest) messageRequest;
		LOG.info("SanctionUpdateRequest-----ResourceId:",sanctionUpdateRequest.getResourceId());
		Integer tradePaymentId = sanctionUpdateRequest.getResourceId();
		if(sanctionUpdateRequest.getResourceType().equalsIgnoreCase(Constants.FUNDSIN)){
		    cdTransaction = fundsInKafkaService.populatePaymentInTxDetails(tradePaymentId);
		}else if(sanctionUpdateRequest.getResourceType().equalsIgnoreCase(Constants.FUNDSOUT)) {
		    operationType= OperationEnum.FUNDS_OUT;
		    cdTransaction = fundsOutKafkaService.populatePaymentOutTxDetails(tradePaymentId);
		}
	    setCdTransactionHdrForKafka(cdTransaction, transactionId, operationType, Constants.MODIFY);
	    return cdTransaction;
	}
	
	private CDTransaction getTransactionForStatusUpdate(ServiceMessage messageRequest, String transactionId ) throws ComplianceException{
		CDTransaction cdTransaction = null;
		OperationEnum operationType= OperationEnum.FUNDS_IN;
        IntuitionPaymentStatusUpdateRequest payStatusUpdateRequest = (IntuitionPaymentStatusUpdateRequest) messageRequest;
       if(payStatusUpdateRequest.getTrxType().equalsIgnoreCase(Constants.FUNDSIN)){
		   FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) payStatusUpdateRequest
			   .getAdditionalAttribute("fundsInRequest");
		   LOG.info("IntuitionPaymentStatusUpdateRequest--FundsInCreateRequest---PaymentFundsInId:",fundsInRequest.getPaymentFundsInId());
		   cdTransaction = fundsInKafkaService.populatePaymentInTxDetails(fundsInRequest.getPaymentFundsInId());
       }else {
    	   FundsOutRequest fundsOutRequest = (FundsOutRequest) payStatusUpdateRequest.getAdditionalAttribute("fundsOutRequest");
    	   LOG.info("IntuitionPaymentStatusUpdateRequest--FundsOutRequest---PaymentFundsoutId:",fundsOutRequest.getPaymentFundsoutId());
    	   operationType= OperationEnum.FUNDS_OUT;
    	   cdTransaction = fundsOutKafkaService.populatePaymentOutTxDetails(fundsOutRequest.getPaymentFundsoutId());
       }
       setCdTransactionHdrForKafka(cdTransaction, transactionId, operationType, Constants.MODIFY);
       return cdTransaction;
	}
	
	private CDTransaction getTransactionForTMRequest(ServiceMessage messageRequest, String transactionId ) throws ComplianceException{
		CDTransaction cdTransaction = null;
		OperationEnum operationType= OperationEnum.FUNDS_IN;
		TransactionMonitoringMQRequest transactionMonitoringMQRequest = (TransactionMonitoringMQRequest) messageRequest;
		Integer tradePaymentId=null;
		String requestType = transactionMonitoringMQRequest.getRequestType();
		if (requestType.equalsIgnoreCase("payment_in") || requestType.equalsIgnoreCase("payment_in_update")) {
				tradePaymentId = fundsInKafkaService.getTradePaymentIDFromFundsInID(transactionMonitoringMQRequest.getPaymentInID());
				LOG.info("getTransactionForTMRequest------TransactionMonitoringMQRequest-----TradePaymentId:",tradePaymentId);
				cdTransaction = fundsInKafkaService.populatePaymentInTxDetails(tradePaymentId);
		
		}else if(requestType.equalsIgnoreCase("payment_out") || requestType.equalsIgnoreCase("payment_out_update")) {
			    tradePaymentId = fundsOutKafkaService.getTradePaymentIDFromFundsOutID(transactionMonitoringMQRequest.getPaymentOutID());
			    LOG.info("getTransactionForTMRequest--------TransactionMonitoringMQRequest-----TradePaymentId:",tradePaymentId);
			    operationType= OperationEnum.FUNDS_OUT;
			    cdTransaction = fundsOutKafkaService.populatePaymentOutTxDetails(tradePaymentId);		
		}
		setCdTransactionHdrForKafka(cdTransaction, transactionId, operationType, Constants.MODIFY);
		return cdTransaction;
	}

	private Integer getFundsInTransactionIDFromPayload(ServiceMessage message) {
		if (message instanceof FundsInBlacklistResendRequest) {
			FundsInBlacklistResendRequest fundsInRequest = (FundsInBlacklistResendRequest) message;
			LOG.info("FundsInBlacklistResendRequest-----PaymentFundsInId:",fundsInRequest.getPaymentFundsInId());
			return fundsInRequest.getPaymentFundsInId();

		}else if (message instanceof FundsInCustomCheckResendRequest) {
			FundsInCustomCheckResendRequest fundsInRequest = (FundsInCustomCheckResendRequest) message;
			LOG.info("FundsInCustomCheckResendRequest-----PaymentFundsInId:",fundsInRequest.getPaymentFundsInId());
			return fundsInRequest.getPaymentFundsInId();

		}else if (message instanceof FundsInDeleteRequest) {
			FundsInDeleteRequest fundsInRequest = (FundsInDeleteRequest) message;
			LOG.info("FundsInDeleteRequest-----PaymentFundsInId:",fundsInRequest.getPaymentFundsInId());
			return fundsInRequest.getPaymentFundsInId();

		}else if (message instanceof FundsInFraugsterResendRequest) {
			FundsInFraugsterResendRequest fundsInRequest = (FundsInFraugsterResendRequest) message;
			LOG.info("FundsInFraugsterResendRequest-----PaymentFundsInId:",fundsInRequest.getPaymentFundsInId());
			return fundsInRequest.getPaymentFundsInId();

		}else if (message instanceof FundsInSanctionResendRequest) {
			FundsInSanctionResendRequest fundsInRequest = (FundsInSanctionResendRequest) message;
			LOG.info("FundsInSanctionResendRequest-----PaymentFundsInId:",fundsInRequest.getPaymentFundsInId());
			return fundsInRequest.getPaymentFundsInId();

		}else if (message instanceof IntuitionPaymentStatusUpdateRequest) {
			IntuitionPaymentStatusUpdateRequest fundsInRequest = (IntuitionPaymentStatusUpdateRequest) message;
			LOG.info("IntuitionPaymentStatusUpdateRequest-----TradePaymentID:",fundsInRequest.getTradePaymentID());
			return Integer.valueOf(fundsInRequest.getTradePaymentID());
			
		}else if (message instanceof TransactionMonitoringUpdatePaymentStatusRequest) {
			TransactionMonitoringUpdatePaymentStatusRequest fundsInRequest = (TransactionMonitoringUpdatePaymentStatusRequest) message;
			LOG.info("TransactionMonitoringUpdatePaymentStatusRequest-----TradePaymentId:",fundsInRequest.getTradePaymentId());
			return fundsInRequest.getTradePaymentId();

		}else if (message instanceof TransactionMonitoringMQRequest) {
			TransactionMonitoringMQRequest fundsInRequest = (TransactionMonitoringMQRequest) message;
			LOG.info("TransactionMonitoringMQRequest-----PaymentInID:",fundsInRequest.getPaymentInID());
			return fundsInRequest.getPaymentInID();

		}else if (message instanceof ReprocessFailedDetails) {
			ReprocessFailedDetails fundsInRequest = (ReprocessFailedDetails) message;
			LOG.info("ReprocessFailedDetails-----TransId:",fundsInRequest.getTransId());
			return fundsInRequest.getTransId();
			
		}else {
			FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) message;
			LOG.info("FundsInCreateRequest-----PaymentFundsInId:",fundsInRequest.getTrade().getPaymentFundsInId());
			return fundsInRequest.getTrade().getPaymentFundsInId();
		}
		
	}
	private Integer getFundsOutTransactionIDFromPayload(ServiceMessage message) {
		if (message instanceof FundsOutBlacklistResendRequest) {
			FundsOutBlacklistResendRequest fundsOutRequest = (FundsOutBlacklistResendRequest) message;
			LOG.info("FundsOutBlacklistResendRequest-----PaymentFundsOutId:",fundsOutRequest.getPaymentFundsoutId());
			return fundsOutRequest.getPaymentFundsoutId();

		}else if (message instanceof FundsOutPaymentReferenceResendRequest) {
		    FundsOutPaymentReferenceResendRequest fundsOutRequest = (FundsOutPaymentReferenceResendRequest) message;
			LOG.info("FundsOutPaymentReferenceResendRequest-----PaymentFundsOutId:",fundsOutRequest.getPaymentFundsoutId());
			return fundsOutRequest.getPaymentFundsoutId();

		}else if (message instanceof FundsOutCustomCheckResendRequest) {
			FundsOutCustomCheckResendRequest fundsOutRequest = (FundsOutCustomCheckResendRequest) message;
			LOG.info("FundsOutCustomCheckResendRequest-----PaymentFundsOutId:",fundsOutRequest.getPaymentFundsoutId());
			return fundsOutRequest.getPaymentFundsoutId();

		}else if (message instanceof FundsOutDeleteRequest) {
			FundsOutDeleteRequest fundsOutRequest = (FundsOutDeleteRequest) message;
			LOG.info("FundsOutDeleteRequest-----PaymentFundsOutId:",fundsOutRequest.getPaymentFundsoutId());
			return fundsOutRequest.getPaymentFundsoutId();

		}else if (message instanceof FundsOutFruagsterResendRequest) {
			FundsOutFruagsterResendRequest fundsOutRequest = (FundsOutFruagsterResendRequest) message;
			LOG.info("FundsOutFruagsterResendRequest-----PaymentFundsOutId:",fundsOutRequest.getPaymentFundsoutId());
			return fundsOutRequest.getPaymentFundsoutId();

		}else if (message instanceof FundsOutSanctionResendRequest) {
			FundsOutSanctionResendRequest fundsOutRequest = (FundsOutSanctionResendRequest) message;
			LOG.info("FundsOutSanctionResendRequest-----PaymentFundsOutId:",fundsOutRequest.getPaymentFundsoutId());
			return fundsOutRequest.getPaymentFundsoutId();

		}else if (message instanceof IntuitionPaymentStatusUpdateRequest) {
			IntuitionPaymentStatusUpdateRequest fundsOutRequest = (IntuitionPaymentStatusUpdateRequest) message;
			LOG.info("IntuitionPaymentStatusUpdateRequest-----TradePaymentID:",fundsOutRequest.getTradePaymentID());
			return Integer.valueOf(fundsOutRequest.getTradePaymentID());
			
		}else if (message instanceof TransactionMonitoringUpdatePaymentStatusRequest) {
			TransactionMonitoringUpdatePaymentStatusRequest fundsOutRequest = (TransactionMonitoringUpdatePaymentStatusRequest) message;
			LOG.info("TransactionMonitoringUpdatePaymentStatusRequest-----TradePaymentId:",fundsOutRequest.getTradePaymentId());
			return fundsOutRequest.getTradePaymentId();

		}else if (message instanceof TransactionMonitoringMQRequest) {
			TransactionMonitoringMQRequest fundsOutRequest = (TransactionMonitoringMQRequest) message;
			LOG.info("TransactionMonitoringMQRequest-----PaymentOutID:",fundsOutRequest.getPaymentOutID());
			return fundsOutRequest.getPaymentOutID();

		}else if (message instanceof ReprocessFailedDetails) {
			ReprocessFailedDetails fundsOutRequest = (ReprocessFailedDetails) message;
			LOG.info("ReprocessFailedDetails-----TransId:",fundsOutRequest.getTransId());
			return fundsOutRequest.getTransId();
			
		}else if (message instanceof FundsOutUpdateRequest) {
			FundsOutUpdateRequest fundsOutRequest = (FundsOutUpdateRequest) message;
			LOG.info("FundsOutUpdateRequest-----TransId:",fundsOutRequest.getPaymentFundsoutId());
			return fundsOutRequest.getPaymentFundsoutId();
			
		}else {
			FundsOutRequest fundsOutRequest = (FundsOutRequest) message;
			LOG.info("FundsOutRequest-----PaymentFundsOutId:",fundsOutRequest.getPaymentFundsoutId());
			return fundsOutRequest.getPaymentFundsoutId();
		}
		
	}

	
	private void setCdTransactionHdrForKafka(CDTransaction cdTransaction, String transactionId, OperationEnum operationType, String activity) {
		if(cdTransaction==null) {
			return;
		}
		cdTransaction.getMessageHeader().setTransactionName(operationType.toString());
		cdTransaction.getMessageHeader().setTransactionSequenceId(transactionId);
		cdTransaction.getMessageHeader().setOperation(activity);
	}

}
