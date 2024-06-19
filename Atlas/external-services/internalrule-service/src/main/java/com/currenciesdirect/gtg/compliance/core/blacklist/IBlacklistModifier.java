package com.currenciesdirect.gtg.compliance.core.blacklist;


import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistModifierResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;

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
	 * @return the InternalService response
	 * @throws BlacklistException
	 *             the blacklist exception
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public InternalServiceResponse saveIntoBlacklist(InternalServiceRequest request) throws BlacklistException;

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
	public InternalServiceResponse deleteFromBlacklist(InternalServiceRequest request) throws BlacklistException;
	
	/**
	 * Gets the blacklist data by type.
	 *
	 * @param request the request
	 * @return the blacklist data by type
	 * @throws BlacklistException the blacklist exception
	 */
	public InternalServiceResponse getBlacklistDataByType(InternalServiceRequest request) throws BlacklistException;

}
