package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IRegistrationReportService.
 */
public interface IRegistrationReportService {

	/**
	 * Gets the registration queue.
	 *
	 * @param request
	 *            the search criteria
	 * @return the registration queue
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public RegistrationQueueDto getRegistrationQueueWithCriteria(RegistrationReportSearchCriteria request) throws CompliancePortalException;

	
	/**
	 * Gets the registration queue whole data.
	 * 
	 * @param request
	 * 			the search criteria
	 * @return the registration queue
	 * @throws CompliancePortalException
	 * 			the compliance portal exception
	 */
	public RegistrationQueueDto getRegistrationQueueWholeData(RegistrationReportSearchCriteria request)throws CompliancePortalException;

}
