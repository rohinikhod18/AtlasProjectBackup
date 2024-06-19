package com.currenciesdirect.gtg.compliance.compliancesrv.core.intuitionpaymentcsv;

import org.springframework.stereotype.Component;

@Component
public class IntuitionHistoricPaymentSchedulerCronJob {
	
	/** The Constant CRONJOB. */
	private static final String CRONJOB = System.getProperty("intuition.historic.payment.cronexpression");

	/**
	 * @return the cronJob
	 */
	public String geIntuitionCronJob() {
		return CRONJOB;
	}
}
