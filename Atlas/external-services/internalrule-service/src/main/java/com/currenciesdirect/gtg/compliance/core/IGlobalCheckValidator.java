package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;

/**
 * The Interface IGlobalCheckValidator.
 */
public interface IGlobalCheckValidator {

	/**
	 * Global check validator.
	 *
	 * @param request the request
	 * @return the boolean
	 */
	Boolean globalCheckValidator(InternalServiceRequestData request);
}
