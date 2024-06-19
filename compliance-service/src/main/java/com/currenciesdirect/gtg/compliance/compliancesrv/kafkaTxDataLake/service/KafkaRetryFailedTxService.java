package com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake.DataLakeTxResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake.KafkaFailedRetryRequest;

/**
 * @author prashant.verma
 */
public interface KafkaRetryFailedTxService {

	public DataLakeTxResponse validateRetryTxFailedRequest(KafkaFailedRetryRequest kafkaFailedRetryRequest);

}
