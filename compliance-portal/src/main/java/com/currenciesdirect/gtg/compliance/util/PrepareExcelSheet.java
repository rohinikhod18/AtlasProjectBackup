package com.currenciesdirect.gtg.compliance.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * The Prepare Excel Sheet.
 */
public class PrepareExcelSheet {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(PrepareExcelSheet.class);

	/**
	 * Prepare excel.
	 *
	 * @param workbook
	 *            the workbook
	 * @param titles
	 *            the titles
	 * @param getDataMembers
	 *            the get data members
	 * @param recordsDataList
	 *            the records data list
	 * @param sheetName
	 *            the sheet name
	 */
	public void prepareExcel(Workbook workbook, String[] titles, String[] getDataMembers, List<?> recordsDataList,
			String sheetName) {

		HSSFSheet sheet = (HSSFSheet) workbook.createSheet(sheetName);
		sheet.setDefaultColumnWidth(30);
		StyleSheet styleSheet = new StyleSheet();
		for (int i = 0; i < titles.length; i++) {
			sheet.setColumnWidth(i, 256 * 20);
		}
		Map<String, CellStyle> styles = styleSheet.createStyles(workbook);
		// turn off gridlines
		sheet.setDisplayGridlines(false);
		sheet.setPrintGridlines(false);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);
		// the following three statements are required only for HSSF
		sheet.setAutobreaks(true);
		printSetup.setFitHeight((short) 3);
		printSetup.setFitWidth((short) 3);
		// the header row
		Row headerRow = sheet.createRow(0);
		headerRow.setHeightInPoints(15f);
		for (int i = 0; i < titles.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(styles.get("header"));
		}
		int rownum = 1;

		try {
			for (Object obj : recordsDataList) {
				Row row = sheet.createRow(rownum++);
				Class<?> tClass = obj.getClass();
				for (int i = 0; i < getDataMembers.length; i++) {
					row.setHeightInPoints(15f);
					Cell cellBankName = row.createCell(i);
					cellBankName.setCellValue("" + tClass.getMethod(getDataMembers[i]).invoke(obj));
					cellBankName.setCellStyle(styles.get("cell_contract"));
				}
			}
		} catch (Exception ex) {
			LOG.error("Object: {}", recordsDataList);
			LOG.error("Exception in adding row : {1}", ex);
		}
	}

}
