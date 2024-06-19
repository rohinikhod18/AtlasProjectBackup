package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.Dashboard;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public interface IDashboardDBService {
	
	public Dashboard getDashboard() throws CompliancePortalException;

}
