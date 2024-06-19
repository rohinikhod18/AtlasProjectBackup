package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.report.AdministrationDto;

/**
 * The Interface IAdministrationService.
 */
@FunctionalInterface
public interface IAdministrationService {

	/**
	 * Gets the administration.
	 *
	 * @return the administration
	 */
	public AdministrationDto getAdministration();
}
