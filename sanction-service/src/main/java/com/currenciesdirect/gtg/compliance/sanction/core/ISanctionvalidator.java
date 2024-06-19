/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: ISanctionvalidator.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core;

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;

/**
 * @author manish
 *
 */
@FunctionalInterface
public interface ISanctionvalidator {

	/**
	 * Validate request.
	 *
	 * @param request the request
	 * @return the boolean
	 */
	public FieldValidator validateRequest(SanctionRequest request);
}
