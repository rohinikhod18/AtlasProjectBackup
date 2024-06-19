package com.currenciesdirect.gtg.compliance.customchecks.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;

import org.slf4j.Logger;

import com.currenciesdirect.gtg.compliance.customchecks.core.cache.CustomCheckConcreteDataBuilder;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksErrors;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Class CustomCheckInitializer.
 * 
 * @author abhijeetg
 */
@javax.ejb.Singleton
@Startup
public class CustomCheckInitializer {

	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CustomCheckInitializer.class);

	/** The Constant scheduledExecutorService. */
	public static final ScheduledExecutorService scheduledExecutorService = Executors
			.newSingleThreadScheduledExecutor();


	/**
	 * Inits the.
	 *
	 * @throws CustomChecksException the custom checks exception
	 */
	@PostConstruct
	public void init() {
		Integer timeIntervel = null;
		LOG.debug("====================INIT STARTED=================");
		CustomCheckConcreteDataBuilder.getInstance().loadCache();
		try {
			String value = System.getProperty("custom-checks.cache.refresh.interval");
			if (value != null) {
				value=value.trim();
			} else {
				value = "60000";
			}
			timeIntervel = Integer.parseInt(value);
			runscheduler(timeIntervel);
			LOG.debug("======================INIT END==============");
		} catch (Exception e) {
			LOG.error(CustomChecksErrors.ERROR_WHILE_REFRESHING_VELOCITY_TIMEOUT.toString(), e);
		}
	}

	/**
	 * Runscheduler.
	 *
	 * @param timeIntervel the time intervel
	 */
	public void runscheduler(Integer timeIntervel) {

		final Runnable startSchedular = ()-> {
					
				try {
					CustomCheckConcreteDataBuilder.getInstance().loadCache();
				} catch (Exception ce) {
					LOG.error("Error in scheduling",ce);
				}
			
		};
		scheduledExecutorService.scheduleAtFixedRate(startSchedular, timeIntervel, timeIntervel, TimeUnit.MILLISECONDS);

	}
}
