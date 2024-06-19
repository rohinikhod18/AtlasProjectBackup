package com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service;

import com.currenciesdirect.kafka.transaction.CDTransaction;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;

/**
 * @author prashant.verma
 */
public interface FundsOutKafkaService {

	public CDTransaction populatePaymentOutTxDetails(Integer tradePaymentId) throws ComplianceException;
	
	public Integer getTradePaymentIDFromFundsOutID(Integer fundsOutId) throws ComplianceException ;

	
}
