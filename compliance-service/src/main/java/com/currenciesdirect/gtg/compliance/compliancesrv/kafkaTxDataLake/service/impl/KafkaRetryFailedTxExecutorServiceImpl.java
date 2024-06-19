package com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.KafkaDataLakeTxDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake.KafkaFailedTxRequestAudit;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake.KafkaFailedRetryRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.KafkaRetryFailedTxExecutorService;
import com.currenciesdirect.kafka.transaction.CDTransaction;
import com.currenciesdirect.kafkaschema.utils.CDTransactionCoverter;
import com.currenciesdirect.kafkaproducertemplate.producer.SimpleKafkaProducer;

/**
 * @author prashant.verma
 */
@Service
public class KafkaRetryFailedTxExecutorServiceImpl implements KafkaRetryFailedTxExecutorService{

	@Autowired
	@Qualifier("KafkaTxFailedRetryExecutorService") 
	private ExecutorService kafkaTxFailedRetryExecutorService;

	/** The funds out DB service impl. */
	@Autowired
	protected KafkaDataLakeTxDBServiceImpl kafkaAuditDBServiceImpl;
	
    @Autowired
    SimpleKafkaProducer kafkaClientService;
	
	@Value("${datalake.tx.kafka.topic}")
	public String topic;

	private static final Logger LOG = LoggerFactory.getLogger(KafkaRetryFailedTxExecutorServiceImpl.class);

	public void executeKafkaFailedTx(KafkaFailedRetryRequest kafkaFailedRetryRequest) {
		String ids = kafkaFailedRetryRequest.getAuditIds();
		String startDate = kafkaFailedRetryRequest.getFromDate();
		String endDate = kafkaFailedRetryRequest.getToDate();
		
		kafkaTxFailedRetryExecutorService.submit(()->{

			try {
				List<KafkaFailedTxRequestAudit> failedTxRequests = kafkaAuditDBServiceImpl.getFailedTxRequests(kafkaFailedRetryRequest);
				failedTxRequests.forEach(request->{

					try {
						String message = request.getMessage();
						CDTransaction cdTransaction = CDTransactionCoverter.convertToEntityAttribute(message); 
						String messageId = request.getMessageId();
						Long auditId = request.getAuditId();
						LOG.info("\n ------- sending retry request to kafka Start --------");
						LOG.info("\n ------- message id : "+messageId+" --------");
						LOG.info("\n ------- message : "+message+" --------");
						LOG.info("\n ------- auditId : "+auditId+" --------");

						kafkaClientService.sendMessage(topic, cdTransaction, messageId);
					}catch(Exception e) {
						LOG.error(" \n -----------  Got exception in sending retry request to kafka ---------", e);
					}
					LOG.info(" \n -----------  sending retry request to kafka End ---------");
				});

			}catch(Exception e) {
				LOG.error(" \n -----------  Got exception in executeTask request to kafka ---------", e);
			}
		});
	}
}
