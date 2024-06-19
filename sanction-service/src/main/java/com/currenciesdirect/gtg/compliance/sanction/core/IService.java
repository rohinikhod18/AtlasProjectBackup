/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: IService.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core;

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;

/**
 * The Interface Iservice.
 */
@FunctionalInterface
public interface IService {

	/**
	 * Gets the sanction details.
	 *
	 * @param sanctionRequest
	 *            the sanction request
	 * @return the sanction details
	 * @throws SanctionException
	 *             the sanction exception
	 */
	public SanctionResponse getSanctionDetails(SanctionRequest sanctionRequest) throws SanctionException;
}
