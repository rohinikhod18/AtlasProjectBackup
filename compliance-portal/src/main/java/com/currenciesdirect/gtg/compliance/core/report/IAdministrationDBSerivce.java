package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.report.AdministrationDto;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IAdministrationDBSerivceImpl.
 */
public interface IAdministrationDBSerivce {

	/**
	 * Gets the administration.
	 *
	 * @return the administration
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public AdministrationDto getAdministration() throws CompliancePortalException;
}
