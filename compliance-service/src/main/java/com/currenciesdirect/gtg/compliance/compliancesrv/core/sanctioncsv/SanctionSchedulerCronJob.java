package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

import org.springframework.stereotype.Component;

@Component
public class SanctionSchedulerCronJob {
	
	private static final String CRONJOB = System.getProperty("sanction.csv.cronexpression");

	/**
	 * @return the cronJob
	 */
	public String getCronJob() {
		return CRONJOB;
	}
	
}
