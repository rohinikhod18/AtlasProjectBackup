/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: ISanctionService.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.sanction.core.domain.SanctionGetStatusRequest;
import com.currenciesdirect.gtg.compliance.sanction.core.domain.SanctionGetStatusResponse;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;

/**
 * The Interface IServicePort.
 */
public interface ISanctionService {

	/**
	 * Check sanction details.
	 *
	 * @param sanctionRequest the sanction request
	 * @param providerProperty the provider property
	 * @return the sanction response
	 * @throws SanctionException the sanction exception
	 */
	public SanctionResponse checkSanctionDetails(SanctionRequest sanctionRequest,
			ProviderProperty providerProperty) throws SanctionException;
	
	
	/**
	 * Gets the sanction status.
	 *
	 * @param sanctionRequest the sanction request
	 * @param providerProperty the provider property
	 * @return the sanction status
	 * @throws SanctionException the sanction exception
	 */
	public SanctionGetStatusResponse getSanctionStatus(SanctionGetStatusRequest sanctionRequest,
			ProviderProperty providerProperty) throws SanctionException;

}
