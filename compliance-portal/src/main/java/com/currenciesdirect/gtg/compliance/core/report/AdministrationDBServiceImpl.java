package com.currenciesdirect.gtg.compliance.core.report;

import java.sql.Connection;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.report.AdministrationDto;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDBServiceLevel2;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Class AdministrationDBServiceImpl.
 */
@Component("AdministrationDBServiceImpl")
public class AdministrationDBServiceImpl extends AbstractDBServiceLevel2 implements IAdministrationDBSerivce {

	@Override
	public AdministrationDto getAdministration() throws CompliancePortalException {
		AdministrationDto adminstrationDto = new AdministrationDto();
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			adminstrationDto.setCountries( getAllCountries(connection));
			
		} catch(Exception exception){
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA,exception);
		} finally {
			closeConnection(connection);
		}
		return adminstrationDto;
	}

	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException {
		return null;
	}

}
