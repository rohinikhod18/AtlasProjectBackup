package com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.impl;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake.DataLakeTxResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake.KafkaFailedRetryRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.KafkaRetryFailedTxService;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.DateTimeFormatter;

import java.sql.Timestamp;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author prashant.verma
 */
@Service
public class KafkaRetryFailedTxServiceImpl implements KafkaRetryFailedTxService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRetryFailedTxServiceImpl.class);

	public DataLakeTxResponse validateRetryTxFailedRequest(KafkaFailedRetryRequest kafkaFailedRetryRequest) {

		String regex = "^\\d+(,\\d+)*$";
		Pattern pattern = Pattern.compile(regex);

		try {
			if(kafkaFailedRetryRequest != null) {
				if(kafkaFailedRetryRequest.getAuditIds() != null  && !kafkaFailedRetryRequest.getAuditIds().trim().isEmpty()) {
					if(!pattern.matcher(kafkaFailedRetryRequest.getAuditIds().trim()).matches()) {
						return new DataLakeTxResponse(false, 1002, "Audit Ids input is incorrect.");
					}
				}
				if(kafkaFailedRetryRequest.getFromDate() != null && !kafkaFailedRetryRequest.getFromDate().trim().isEmpty()) {
					if(DateTimeFormatter.convertStringToTimestamp(kafkaFailedRetryRequest.getFromDate().trim()).equals(new Timestamp(0))) {
						if(kafkaFailedRetryRequest.getToDate() != null && !kafkaFailedRetryRequest.getToDate().trim().isEmpty()) {
							if(DateTimeFormatter.convertStringToTimestamp(kafkaFailedRetryRequest.getToDate().trim()).equals(new Timestamp(0))) {
								return new DataLakeTxResponse(false, 1004, "To Date input value is incorrect.");
							}
						}						
					} else {
						return new DataLakeTxResponse(false, 1003, "From Date input value is incorrect.");
					}
				} else {
					kafkaFailedRetryRequest.setToDate(null);
				}
				return new DataLakeTxResponse(true, 1000, "Validation Success");
			}else {
				return new DataLakeTxResponse(false, 1001, "KafkaFailedRetryRequest payload is null");
			}
			
		} catch(Exception e) {
			return new DataLakeTxResponse(false, 1005, "Exception in validating KafkaFailedRetry Request payload");
		}
	}
}
