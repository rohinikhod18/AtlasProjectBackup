/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;

/**
 * The Class KYCExecutors.
 *
 * @author manish
 */
public class KYCExecutors {

	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(KYCExecutors.class);

	/** The executor service. */
	private final ExecutorService executorService = Executors.newFixedThreadPool(40);

	/** The k YC executors. */
	private static KYCExecutors kYCExecutors = new KYCExecutors();

	/**
	 * Instantiates a new KYC executors.
	 */
	private KYCExecutors() {
	}

	/**
	 * Gets the executor service.
	 *
	 * @return the executor service
	 */
	public static ExecutorService getExecutorService() {
		return kYCExecutors.executorService;
	}

	/**
	 * Clean up.
	 */
	public void cleanUp() {
		executorService.shutdownNow();
		LOG.debug("excecuter is shutdown");
	}
}
