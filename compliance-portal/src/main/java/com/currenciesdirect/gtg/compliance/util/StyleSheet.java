package com.currenciesdirect.gtg.compliance.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * The Class StyleSheet.
 */
public class StyleSheet {

	/**
	 * Creates the styles.
	 * 
	 * @param wb
	 *            the wb
	 * @return the map
	 */
	public Map<String, CellStyle> createStyles(Workbook wb) {
	
		Map<String, CellStyle> styles = new HashMap<>();
		CellStyle style;
		Font headerFont = wb.createFont();
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		headerFont.setFontName("Calibri");
		headerFont.setFontHeightInPoints((short) 11);
	
		style = createBorderedStyleHeader(wb);
		style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("header", style);
	
		Font font = wb.createFont();
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);
	
		style = createBorderedStyleContract(wb);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(font);
		styles.put("cell_contract", style);
	
		return styles;
	}
		

	/**
	 * Creates the bordered style_header.
	 * 
	 * @param wb
	 *            the wb
	 * @return the cell style
	 */
	public CellStyle createBorderedStyleHeader(Workbook wb) {
	
	
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.WHITE.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
	
		return style;
	}
	
	/**
	 * Creates the bordered style_contract.
	 * 
	 * @param wb
	 *            the wb
	 * @return the cell style
	 */
	public CellStyle createBorderedStyleContract(Workbook wb) {
	

		CellStyle style = wb.createCellStyle();
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		return style;
	}
	
	
}
