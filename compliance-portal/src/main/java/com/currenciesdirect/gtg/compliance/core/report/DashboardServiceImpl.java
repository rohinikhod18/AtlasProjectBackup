package com.currenciesdirect.gtg.compliance.core.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.Dashboard;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.RegistrationDashboard;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Class DashboardServiceImpl.
 *
 */
@Component("dashboardComplianceServiceImpl")
public class DashboardServiceImpl implements IDashboardService {
	@Autowired
	@Qualifier("dashboardDBServiceImpl")
	private IDashboardDBService iDashboardDBService;
	private Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);

	@Override
	public Dashboard getDashboard() throws CompliancePortalException {
		try {
			Dashboard dashboard;
			RegistrationDashboard registrationDashboard;
			dashboard = iDashboardDBService.getDashboard();
			registrationDashboard = dashboard.getRegDashboard();
			getRecordsRatio(registrationDashboard);
			dashboard.setRegDashboard(registrationDashboard);
			return dashboard;

		} catch (CompliancePortalException e) {
			log.error("Error while fetching dashboard data getDashboard() ", e.getOrgException());
			throw e;
		}
	}

	private RegistrationDashboard getRecordsRatio(RegistrationDashboard registrationDashboard) {
		String percentPfxRecords = "0";
		String percentCfxRecords="0";
		Integer totalRecords = registrationDashboard.getTotalRegRecords();
		Integer totalPfxRecords = registrationDashboard.getTotalPfxRecords();
		Integer totalCfxRecords = registrationDashboard.getTotalCfxRecords();
		if(totalRecords !=0){
			 percentPfxRecords = Integer.toString((totalPfxRecords * 100) / totalRecords);
			 percentCfxRecords = Integer.toString((totalCfxRecords * 100) / totalRecords);
		}
		registrationDashboard.setPercentPfxRecords(percentPfxRecords);
		registrationDashboard.setPercentCfxRecords(percentCfxRecords);

		return registrationDashboard;
	}
}
