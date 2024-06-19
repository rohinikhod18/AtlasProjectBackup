package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IWorkEfficiencyReportDBService.
 */
public interface IWorkEfficiencyReportDBService {
	
	/**
	 * Gets the Work Efficiency Report.
	 *
	 * @return the work efficiency report
	 * @throws CompliancePortalException 
	 */
	WorkEfficiencyReportDto getWorkEfficiencyReport(WorkEfficiencyReportSearchCriteria searchCriteria) throws CompliancePortalException;

	/**
	 * Gets the Work Efficiency Report With Criteria.
	 * 
	 * @param criteria
	 * @return
	 * @throws CompliancePortalException
	 */
	WorkEfficiencyReportDto getWorkEfficiencyReportWithCriteria(WorkEfficiencyReportSearchCriteria criteria) throws CompliancePortalException ;
	
	/**
	 * Gets the work efficiency report in excel format.
	 *
	 * @param searchCriteria the search criteria
	 * @return the work efficiency report in excel format
	 * @throws CompliancePortalException the compliance portal exception
	 */
	WorkEfficiencyReportDto getWorkEfficiencyReportInExcelFormat(WorkEfficiencyReportSearchCriteria searchCriteria) throws CompliancePortalException;
	
	
	/**
	 * Gets the work efficiency report with criteria in excel format.
	 *
	 * @param criteria the criteria
	 * @return the work efficiency report with criteria in excel format
	 * @throws CompliancePortalException the compliance portal exception
	 */
	WorkEfficiencyReportDto getWorkEfficiencyReportWithCriteriaInExcelFormat(WorkEfficiencyReportSearchCriteria criteria) throws CompliancePortalException ;

}
