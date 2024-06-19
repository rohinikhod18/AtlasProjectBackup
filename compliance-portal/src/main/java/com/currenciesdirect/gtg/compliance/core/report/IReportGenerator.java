package com.currenciesdirect.gtg.compliance.core.report;

import org.apache.poi.ss.usermodel.Workbook;


public interface IReportGenerator {

	/**
	 * @param workbook
	 * @return
	 */
	public void generateReport(Workbook workbook); 
	
	/**Added method to get Report Name*/
	public String getReportName();
}
