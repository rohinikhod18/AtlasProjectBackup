package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.Dashboard;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IDashboardService.
 */
public interface IDashboardService {

	public Dashboard getDashboard() throws CompliancePortalException;
}
