package com.currenciesdirect.gtg.compliance.compliancesrv.core.transactionmonitoringmq;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.AppContext;

/**
 * The Class TransactionMonitoringScheduler.
 */
public class TransactionMonitoringScheduler extends QuartzJobBean{
	
	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(TransactionMonitoringScheduler.class);

	/**
	 * Initiate scheduler.
	 */
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
			try {
				LOG.info("-------------- Transaction Monitoring Scheduler started {1}--------------");
				
				ApplicationContext applicationContext = AppContext.getApplicationContext();
				TransactionMonitoringMQService transactionMonitoringMQService = (TransactionMonitoringMQService) applicationContext 
						.getBean("transactionMonitoringMQService");
				transactionMonitoringMQService.doTransactionMonitoringMQ();

				LOG.info("-------------- Transaction Monitoring Scheduler Ended {1}--------------");
			} catch (Exception e) {
				LOG.error("Error in TransactionMonitoringScheduler", e);
			}
		
	}

}
