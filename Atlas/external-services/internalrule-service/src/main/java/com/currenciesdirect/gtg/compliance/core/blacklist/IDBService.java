package com.currenciesdirect.gtg.compliance.core.blacklist;

import java.util.Map;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistModifierResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;

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
	 * @return the InternalService response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public InternalServiceResponse saveIntoBlacklist(InternalServiceRequest blacklistRequest) throws BlacklistException;

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
	public InternalServiceResponse deleteFromBacklist(InternalServiceRequest blacklistRequest) throws BlacklistException;

	/**
	 * Stp search into blacklist.
	 *
	 * @param blacklistRequest
	 *            the blacklist request
	 * @return the blacklist stp response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public BlacklistContactResponse stpSearchIntoBlacklist(BlacklistSTPRequest blacklistRequest)
			throws BlacklistException;

	/**
	 * Ui search into blacklist.
	 *
	 * @param blacklistRequest
	 *            the blacklist request
	 * @return the blacklist ui search response
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public InternalServiceResponse uiSearchIntoBlacklist(InternalServiceRequest blacklistRequest)
			throws BlacklistException;

	/**
	 * Gets the all blacklist types.
	 *
	 * @return the all blacklist types
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public Map<String, Integer> getAllBlacklistTypes() throws BlacklistException;

	/**
	 * Gets the blacklist data by type.
	 *
	 * @param request the request
	 * @param blacklistType the blacklist type
	 * @return the blacklist data by type
	 */
	public InternalServiceResponse getBlacklistDataByType(InternalServiceRequest request) throws BlacklistException;
}
