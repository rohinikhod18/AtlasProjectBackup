package com.currenciesdirect.gtg.compliance.blacklist.core;

import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistModifierResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistException;

/**
 * @author Rajesh
 *
 */
public interface IBlacklistModifier {

	/**
	 * Save into blacklist.
	 *
	 * @param request
	 *            the request
	 * @return the blacklist modifier response
	 * @throws BlacklistException
	 *             the blacklist exception
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public BlacklistModifierResponse saveIntoBlacklist(BlacklistRequest request) throws BlacklistException;

	/**
	 * Update into blacklist.
	 *
	 * @param request
	 *            the request
	 * @return the blacklist modifier response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public BlacklistModifierResponse updateIntoBlacklist(BlacklistUpdateRequest request) throws BlacklistException;

	/**
	 * Delete from blacklist.
	 *
	 * @param request
	 *            the request
	 * @return the blacklist modifier response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public BlacklistModifierResponse deleteFromBlacklist(BlacklistRequest request) throws BlacklistException;

}
