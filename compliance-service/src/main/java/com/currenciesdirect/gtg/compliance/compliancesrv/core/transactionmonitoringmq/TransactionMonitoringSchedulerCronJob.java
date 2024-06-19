package com.currenciesdirect.gtg.compliance.compliancesrv.core.transactionmonitoringmq;

import org.springframework.stereotype.Component;

@Component
public class TransactionMonitoringSchedulerCronJob {
	
	private static final String CRONJOB = System.getProperty("transactionmonitoring.cronexpression");

	/**
	 * @return the cronJob
	 */
	public String geTMCronJob() {
		return CRONJOB;
	}

}
