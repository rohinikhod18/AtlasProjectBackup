package com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.KafkaDataLakeTxDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.FundsInKafkaService;
import com.currenciesdirect.kafka.transaction.CDTransaction;

@Service
public class FundsInKafkaServiceImpl implements FundsInKafkaService {

	@Autowired
	KafkaDataLakeTxDBServiceImpl kafkaDataLakeTxDBService;
	
	@Override
	public CDTransaction populatePaymentInTxDetails(Integer tradePaymentId) throws ComplianceException {
		return kafkaDataLakeTxDBService.getFundsInTxDetails(tradePaymentId);
	}
	
	@Override
	public Integer getTradePaymentIDFromFundsInID(Integer fundsInId) throws ComplianceException {
		return kafkaDataLakeTxDBService.getTradePaymentIDFromFundsInID(fundsInId);
	}
}
