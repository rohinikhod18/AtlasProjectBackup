
package com.currenciesdirect.gtg.compliance.core.blacklist;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;

/**
 * @author Rajesh
 *
 */
public interface IBlacklistSearch {

	/**
	 * Stp search from blacklist.
	 *
	 * @param request
	 *            the request
	 * @return the blacklist stp response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public BlacklistContactResponse stpSearchFromBlacklist(BlacklistSTPRequest request) throws BlacklistException;

	

	public InternalServiceResponse uiSearchFromBlacklist(InternalServiceRequest request) throws BlacklistException;

}
