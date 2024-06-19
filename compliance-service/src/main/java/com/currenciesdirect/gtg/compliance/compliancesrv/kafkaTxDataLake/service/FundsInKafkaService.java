package com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service;

import com.currenciesdirect.kafka.transaction.CDTransaction;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;

/**
 * @author prashant.verma
 */
public interface FundsInKafkaService {

	public CDTransaction populatePaymentInTxDetails(Integer tradePaymentId) throws ComplianceException;
	
	public Integer getTradePaymentIDFromFundsInID(Integer fundsInId) throws ComplianceException ;

	
}
