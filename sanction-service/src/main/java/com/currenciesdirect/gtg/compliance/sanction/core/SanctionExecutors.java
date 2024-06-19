/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionExecutors.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

/**
 * The Class SanctionExecutors.
 *
 * @author manish
 */
@Component
public class SanctionExecutors {

	/** The executor service. */
	private final ExecutorService executorService = Executors.newFixedThreadPool(40);

	/** The k YC executors. */
	private static SanctionExecutors kYCExecutors = new SanctionExecutors();

	/**
	 * Instantiates a new sanction executors.
	 */
	private SanctionExecutors() {

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
	}
}
