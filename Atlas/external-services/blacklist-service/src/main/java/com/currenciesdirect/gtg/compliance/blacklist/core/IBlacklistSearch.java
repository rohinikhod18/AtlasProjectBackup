
package com.currenciesdirect.gtg.compliance.blacklist.core;

import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUISearchRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUISearchResponse;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistException;

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
	public BlacklistSTPResponse stpSearchFromBlacklist(BlacklistSTPRequest request) throws BlacklistException;

	/**
	 * Ui search from blacklist.
	 *
	 * @param request
	 *            the request
	 * @return the blacklist ui search response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public BlacklistUISearchResponse uiSearchFromBlacklist(BlacklistUISearchRequest request) throws BlacklistException;

}
