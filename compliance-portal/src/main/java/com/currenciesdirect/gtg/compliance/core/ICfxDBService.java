package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.core.domain.LockResourceDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface ICfxDBService.
 */
public interface ICfxDBService extends IRegistrationDBService {

	/**
	 * Insert lock resource for multi contact.
	 *
	 * @param lockResourceDBDto the lock resource DB dto
	 * @return the lock resource response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public LockResourceResponse insertLockResourceForMultiContact(LockResourceDBDto lockResourceDBDto) throws CompliancePortalException;

	/**
	 * Update lock resource for multi contact.
	 *
	 * @param lockResourceDBDto the lock resource DB dto
	 * @return the lock resource response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public LockResourceResponse updateLockResourceForMultiContact(LockResourceDBDto lockResourceDBDto) throws CompliancePortalException;
	
}
