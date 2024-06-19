package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IWorkEfficiencyReportService.
 */
public interface IWorkEfficiencyReportService {
	
	/**
	 * Gets the Work Efficiency Report.
	 *
	 * @return the work efficiency report
	 * @throws CompliancePortalException 
	 */
	WorkEfficiencyReportDto getWorkEfficiencyReport() throws CompliancePortalException;

	/**
	 *  Gets the Work Efficiency Report With Criteria
	 * 
	 * @param request
	 * @return
	 * @throws CompliancePortalException
	 */
	WorkEfficiencyReportDto getWorkEfficiencyReportWithCriteria(WorkEfficiencyReportSearchCriteria request) throws CompliancePortalException;
	
	/**
	 * Gets the work efficiency report in excel format.
	 *
	 * @return the work efficiency report in excel format
	 * @throws CompliancePortalException the compliance portal exception
	 */
	WorkEfficiencyReportDto getWorkEfficiencyReportInExcelFormat() throws CompliancePortalException;
	
	
	/**
	 * Gets the work efficiency report with criteria in excel format.
	 *
	 * @param request the request
	 * @return the work efficiency report with criteria in excel format
	 * @throws CompliancePortalException the compliance portal exception
	 */
	WorkEfficiencyReportDto getWorkEfficiencyReportWithCriteriaInExcelFormat(WorkEfficiencyReportSearchCriteria request) throws CompliancePortalException;

}
