package com.currenciesdirect.gtg.compliance.core.domain.report;

import org.apache.poi.ss.usermodel.Workbook;

import com.currenciesdirect.gtg.compliance.core.report.IReportGenerator;
import com.currenciesdirect.gtg.compliance.util.PrepareExcelSheet;

public class WorkEfficiencyReportGenerator implements IReportGenerator{
	
	/** The Constant titles. */
	private static final String[] titles = { "Queue Type", "User",
				"Account Type", "Locked Records", "Released Records","Avg Time For Clearance(Min)",
				"SLA time(Min)","Work Efficiency(%)"};
		
	/** The Constant getDataMembers. */
	private static final String[] getDataMembers = {"getQueueType","getUserName","getAccountType","getLockedRecords","getReleasedRecords","getSeconds"
				,"getSlaValue","getPercentEfficiency"};
		
	/** The work efficiency report. */
	private WorkEfficiencyReportDto workEfficiencyReport=null;
		
	/**
	 * Instantiates a new work efficiency report generator.
	 *
	 * @param workEfficiencyReportDto the work efficiency report dto
	 */
	public WorkEfficiencyReportGenerator(WorkEfficiencyReportDto workEfficiencyReportDto) {
			
		workEfficiencyReport = workEfficiencyReportDto;
		}
	
	/**
	 * Generate report.
	 *
	 * @param workbook the workbook
	 */
	@Override
	public void generateReport(Workbook workbook) {
		 new PrepareExcelSheet().prepareExcel(workbook, titles, getDataMembers, workEfficiencyReport.getWorkEfficiencyReportData(), "Registration Report");
	}
	
	/**
	 * Gets the report name.
	 *
	 * @return the report name
	 */
	@Override
	public String getReportName() {
		return "Work-Efficiency-Report";
	}



}
