package com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake.KafkaFailedRetryRequest;

/**
 * @author prashant.verma
 */
public interface KafkaRetryFailedTxExecutorService {

	public void executeKafkaFailedTx(KafkaFailedRetryRequest kafkaFailedRetryRequest);
	
}
