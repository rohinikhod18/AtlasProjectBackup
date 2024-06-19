package com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.KafkaDataLakeTxDBServiceImpl;
import com.currenciesdirect.kafka.transaction.CDTransaction;
import com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.FundsOutKafkaService;

@Service
public class FundsOutKafkaServiceImpl implements FundsOutKafkaService {

	@Autowired
	KafkaDataLakeTxDBServiceImpl kafkaDataLakeTxDBService;
	
	@Override
	public CDTransaction populatePaymentOutTxDetails(Integer tradePaymentId) throws ComplianceException {
		return kafkaDataLakeTxDBService.getFundsOutTxDetails(tradePaymentId);
	}

	@Override
	public Integer getTradePaymentIDFromFundsOutID(Integer fundsOutId) throws ComplianceException {
		return kafkaDataLakeTxDBService.getTradePaymentIDFromFundsOutID(fundsOutId);
	}


}
