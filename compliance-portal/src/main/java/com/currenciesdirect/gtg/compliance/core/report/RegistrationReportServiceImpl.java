package com.currenciesdirect.gtg.compliance.core.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Class RegistrationReportServiceImpl.
 */
@Component("registrationReportServiceImpl")
public class RegistrationReportServiceImpl implements IRegistrationReportService {

	private Logger log = LoggerFactory.getLogger(RegistrationReportServiceImpl.class);
	
	@Autowired
	@Qualifier("regReportDBServiceImpl")
	private IRegistrationReportDBService iRegistrationReportDBService;

	@Override
	public RegistrationQueueDto getRegistrationQueueWithCriteria(RegistrationReportSearchCriteria searchCriteria)
			throws CompliancePortalException {
		try {
			return iRegistrationReportDBService.getRegistrationQueueWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			log.error("error", e.getOrgException());
			throw e;
		}
	}

	@Override
	public RegistrationQueueDto getRegistrationQueueWholeData(RegistrationReportSearchCriteria searchCriteria)
			throws CompliancePortalException {
		try {
			return iRegistrationReportDBService.getRegistrationQueueWholeData(searchCriteria);
		} catch (CompliancePortalException e) {
			log.error("error", e.getOrgException());
			throw e;
		}	}
	
	
}
