package com.currenciesdirect.gtg.compliance.compliancesrv.core.intuitionpaymentcsv;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;


import com.currenciesdirect.gtg.compliance.compliancesrv.util.AppContext;

public class IntuitionHistoricPaymentScheduler  extends QuartzJobBean {
	
	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(IntuitionHistoricPaymentScheduler.class);

	/**
	 * Execute internal.
	 *
	 * @param context the context
	 * @throws JobExecutionException the job execution exception
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			LOG.info("---------------------Intuition Historic Payment Scheduler Started---------------------");
			
			ApplicationContext applicationContext = AppContext.getApplicationContext();
			IntuitionHistoricPaymentCsvFlowService intuitionHistoricPaymentCsvFlowService = (IntuitionHistoricPaymentCsvFlowService) applicationContext 
					.getBean("intuitionHistoricPaymentCsvFlowService");
			intuitionHistoricPaymentCsvFlowService.initiateFlow();

			LOG.info("---------------------Intuition Historic Payment Scheduler Ended---------------------");
		} catch (Exception e) {
			LOG.error("Error in IntuitionHistoricPaymentScheduler", e);
		}
		
	}

}
