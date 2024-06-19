package com.currenciesdirect.gtg.compliance.blacklist.core;

import java.util.Map;

import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistModifierResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUISearchRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUISearchResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistException;

/**
 * @author Rajesh
 *
 */
public interface IDBService {

	/**
	 * Save into blacklist.
	 *
	 * @param blacklistRequest
	 *            the blacklist request
	 * @return the blacklist modifier response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public BlacklistModifierResponse saveIntoBlacklist(BlacklistRequest blacklistRequest) throws BlacklistException;

	/**
	 * Update into blacklist.
	 *
	 * @param blacklistRequest
	 *            the blacklist request
	 * @return the blacklist modifier response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public BlacklistModifierResponse updateIntoBlacklist(BlacklistUpdateRequest blacklistRequest)
			throws BlacklistException;

	/**
	 * Delete from backlist.
	 *
	 * @param blacklistRequest
	 *            the blacklist request
	 * @return the blacklist modifier response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public BlacklistModifierResponse deleteFromBacklist(BlacklistRequest blacklistRequest) throws BlacklistException;

	/**
	 * Stp search into blacklist.
	 *
	 * @param blacklistRequest
	 *            the blacklist request
	 * @return the blacklist stp response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public BlacklistSTPResponse stpSearchIntoBlacklist(BlacklistSTPRequest blacklistRequest) throws BlacklistException;

	/**
	 * Ui search into blacklist.
	 *
	 * @param blacklistRequest
	 *            the blacklist request
	 * @return the blacklist ui search response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public BlacklistUISearchResponse uiSearchIntoBlacklist(BlacklistUISearchRequest blacklistRequest)
			throws BlacklistException;

	/**
	 * Gets the all blacklist types.
	 *
	 * @return the all blacklist types
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public Map<String, Integer> getAllBlacklistTypes() throws BlacklistException;
}
