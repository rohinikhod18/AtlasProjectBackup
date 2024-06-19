package com.currenciesdirect.gtg.compliance.compliancesrv.core.transactionmonitoringmq;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.currenciesdirect.gtg.compliance.compliancesrv.util.AppContext;

public class PostCardTransactionSchedular extends QuartzJobBean {

	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(PostCardTransactionSchedular.class);

	/**
	 * Execute internal.
	 *
	 * @param context the context
	 * @throws JobExecutionException the job execution exception
	 */
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			LOG.info("-------------- Post Card Transaction Monitoring Scheduler started --------------");

			ApplicationContext applicationContext = AppContext.getApplicationContext();
			TransactionMonitoringMQService transactionMonitoringMQService = (TransactionMonitoringMQService) applicationContext
					.getBean("transactionMonitoringMQService");
			transactionMonitoringMQService.doPostCardTransactionMQ();

			LOG.info("-------------- Post Card Transaction Monitoring Scheduler Ended --------------");
		} catch (Exception e) {
			LOG.error("Error in PostCardTransactionMonitoringScheduler", e);
		}

	}

}
