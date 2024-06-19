package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IRegistrationReportDBService.
 */
public interface IRegistrationReportDBService {

	/**
	 * Gets the registration queue.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @return the registration queue
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public RegistrationQueueDto getRegistrationQueueWithCriteria(RegistrationReportSearchCriteria searchCriteria) throws CompliancePortalException;

	

	public RegistrationQueueDto getRegistrationQueueWholeData(RegistrationReportSearchCriteria searchCriteria)
			throws CompliancePortalException;

}
