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
 * The Class FraugsterCsvUploadSchedular.
 *
 * @author manish
 */
public class FraugsterCsvUploadSchedular extends QuartzJobBean{

	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(FraugsterCsvUploadSchedular.class);
	/** The Constant FRAUGSTER . */
    private static final String FRAUGSTER = "Fraugster";
    
    /** The Constant FRAUGSTER_SERVICE_PROVIDER . */
    private static final String FRAUGSTER_SERVICE_PROVIDER = "fraugster.service.provider";
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
  protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
    try {
      if (System.getProperty(FRAUGSTER_SERVICE_PROVIDER).equals(FRAUGSTER)) {
        ApplicationContext context = AppContext.getApplicationContext();
        FraugsterCsvUploadGateway getway = (FraugsterCsvUploadGateway) context.getBean("FraugsterSFTPGateway");
        getway.sendFraugster();
      }
    } catch (Exception e) {
      LOG.error("Error in FraugsterCsvUploadSchedular", e);
    }
  }

}
