/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.AppContext;

/**
 * @author manish
 *
 */
public class MQSchedular extends QuartzJobBean{

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(MQSchedular.class);
	
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		ApplicationContext applicationContext = null;
		try {
			LOG.debug("---------------Broadcast MQ Schedular Started---------------");
			applicationContext = AppContext.getApplicationContext();
			BroadCastQueueService broadCastQueueService = (BroadCastQueueService) applicationContext 
					.getBean("broadCastQueueService");
			broadCastQueueService.doMessageBroadcasting(); 
		} catch (Exception e) {
			LOG.error("Error in MQSchedular", e);
		}
	}

}
