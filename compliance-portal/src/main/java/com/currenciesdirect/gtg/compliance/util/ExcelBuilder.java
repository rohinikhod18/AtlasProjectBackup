package com.currenciesdirect.gtg.compliance.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.currenciesdirect.gtg.compliance.core.report.IReportGenerator;

/**
 *  * This class builds an Excel spreadsheet document using Apache POI library.
 *  * @author www.codejava.net  *  
 */
public class ExcelBuilder extends AbstractXlsView {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(ExcelBuilder.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.view.document.AbstractXlsView#
	 * buildExcelDocument(java.util.Map, org.apache.poi.ss.usermodel.Workbook,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			IReportGenerator reportGenerator = (IReportGenerator) model.get("iReportGenerator");
			reportGenerator.generateReport(workbook);
			String reportName = reportGenerator.getReportName();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + reportName + ".xls\"");
		} catch (Exception e) {
			LOG.error("Add model key value as 'iReportGenerator' only: {1}", e);
		}
	}

}
