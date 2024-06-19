package com.currenciesdirect.gtg.compliance.core.domain.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.report.IAdministrationDBSerivce;
import com.currenciesdirect.gtg.compliance.core.report.IAdministrationService;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Class AdministrationServiceImpl.
 */
@Component("AdministrationServiceImpl")
public class AdministrationServiceImpl implements IAdministrationService {
	
	private Logger log = LoggerFactory.getLogger(AdministrationServiceImpl.class);

	@Autowired
	@Qualifier("AdministrationDBServiceImpl")
	private IAdministrationDBSerivce iAdministrationService ;
	
	@Override
	public AdministrationDto getAdministration() {
		AdministrationDto administrationDto = new AdministrationDto();
		try {
			administrationDto = iAdministrationService.getAdministration();
		} catch (CompliancePortalException exception) {
			logError(exception);
			administrationDto.setErrorCode(exception.getCompliancePortalErrors().getErrorCode());
			administrationDto.setErrorDescription(exception.getCompliancePortalErrors().getErrorDescription());
		}
		return administrationDto;
	}
	
	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}

}
