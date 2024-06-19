/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

/**
 * The Interface InternalService.
 *
 * @author manish
 */
@FunctionalInterface
public interface InternalService {

	/**
	 * Check registration.
	 *
	 * @param request
	 *            the request
	 * @return the internal service response
	 * @throws Exception
	 *             the exception
	 */
	public InternalServiceResponse performCheck(InternalServiceRequest request)throws InternalRuleException;

}
