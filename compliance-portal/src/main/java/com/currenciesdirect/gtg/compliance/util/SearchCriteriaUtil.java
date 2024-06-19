package com.currenciesdirect.gtg.compliance.util;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.core.domain.BaseSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.Page;

/**
 * The Class SearchCriteriaUtil.
 */
public class SearchCriteriaUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(SearchCriteriaUtil.class);

	private SearchCriteriaUtil() {

	}

	/**
	 * Gets the min row number.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @return the min row number
	 */
	public static Integer getMinRowNumber(BaseSearchCriteria searchCriteria) {
		Integer pageSize = getPageSize();
		if (searchCriteria != null) {
			Page page = searchCriteria.getPage();

			Integer currentPage = page == null || page.getCurrentPage() == null ? BaseSearchCriteria.DEFAULT_PAGE
					: page.getCurrentPage();

			return (pageSize * currentPage) - (pageSize - 1);
		}
		return null;

	}
	
	public static Integer getCurrentPage(BaseSearchCriteria searchCriteria) {
		if (searchCriteria != null) {
			Page page = searchCriteria.getPage();

			return page == null || page.getCurrentPage() == null ? BaseSearchCriteria.DEFAULT_PAGE
					: page.getCurrentPage();

		}
		return 1;

	}

	public static Integer getPageOffsetRows(BaseSearchCriteria searchCriteria){
		Integer offset = 0;
		if (searchCriteria != null) {
			Page page = searchCriteria.getPage();
			Integer pageSize = getPageSize();
			
			Integer currentPage = page == null || page.getCurrentPage() == null ? BaseSearchCriteria.DEFAULT_PAGE
					: page.getCurrentPage();

			return (pageSize * currentPage) - (pageSize );
		}
		return offset;
	}
	
	/**
	 * Gets the max row number.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @return the max row number
	 */
	public static Integer getMaxRowNumber(BaseSearchCriteria searchCriteria) {
		Integer pageSize = getPageSize();
		if (searchCriteria != null) {
			Page page = searchCriteria.getPage();
			Integer currentPage = page == null || page.getCurrentPage() == null ? BaseSearchCriteria.DEFAULT_PAGE
					: page.getCurrentPage();
			return pageSize * currentPage;
		}
		return null;

	}

	public static Integer getPageSize() {
		String size = System.getProperty("pageSize");
		if (size == null) {
			return BaseSearchCriteria.DEFAULT_PAGE_SIZE;
		} else {
			try {
				return Integer.valueOf(size);
			} catch (NumberFormatException e) {
				return BaseSearchCriteria.DEFAULT_PAGE_SIZE;
			}

		}

	}
	
	/**
	 * Gets the default queue record size.
	 * On all queue pages(registration, paymentIn, PaymentOut) records are shown as per configurable value.
	 * i.e if value is 60 then records are shown from last 60 days to current date.
	 * @return the default queue record size
	 * @author abhijeetg
	 */
	public static String getDefaultQueueRecordSize() {

		try {
			String queueRecordSize = System.getProperty(Constants.QUEUE_DATE_CRITERIA_PROPERTY);

			if (null == queueRecordSize || queueRecordSize.isEmpty()) {
				queueRecordSize = Constants.DEFAULT_QUEUE_DATE_CRITERIA_PROPERTY_VALUE;
			}
			return queueRecordSize.trim();
		} catch (Exception e) {
			LOG.error("Error while geting queue date criteria value ::  ", e);
			return Constants.DEFAULT_QUEUE_DATE_CRITERIA_PROPERTY_VALUE;
		}
	}
	
	/**
	 * Gets default dash board fulfillment threshold time from compliance.properties file
	 * @return
	 */
	public static String getDashBoardThresholdFulfillmentTime(){
		String tresholdTime=System.getProperty(Constants.DASHBOARD_FULFILLMENT_THRESHOLD_TIME_PROPERTY);
		if(null == tresholdTime || tresholdTime.isEmpty() ){
			tresholdTime= Constants.DEFAULT_FULFILLMENT_TRESHOLD_TIME;
		}
		LocalDate localDate = LocalDate.now();
		return localDate+" "+tresholdTime.trim();
	}
	
	/**
	 * Gets default Work Efficiency Report Past Date Size from compliance.properties file
	 * @return
	 */
	public static Integer getPastDateSize() {
		String size = System.getProperty("pastDateSize");
		if (size == null) {
			return Integer.valueOf("-" + BaseSearchCriteria.DEFAULT_PAST_DATE_SIZE);
		} else {
			try {
				if(size.contains("-")){
					return Integer.valueOf(size);
				}
				return Integer.valueOf("-" + size);
			} catch (NumberFormatException e) {
				return BaseSearchCriteria.DEFAULT_PAST_DATE_SIZE;
			}

		}
	}
	
	public static String getHistoryInfoTime(){
		String time=System.getProperty(Constants.TIME_DIFFRENECE_BETWEEN_ACCOUNT_CONTACT);
		if(null == time || time.isEmpty() ){
			time= Constants.DEFAULT_HISTORY_INFO_TIME;
		}
		return time.trim();
	}

	public static Integer getComplianceExpiryYears() {
		String size = System.getProperty("complianceExpiryYears");
		if (size == null) {
			return BaseSearchCriteria.DEFAULT_COMPLIANCE_EXPIRY_YEARS;
		} else {
			try {
				return Integer.valueOf(size);
			} catch (NumberFormatException e) {
				return BaseSearchCriteria.DEFAULT_COMPLIANCE_EXPIRY_YEARS;
			}

		}

	}
	
	public static Double getSlaPropertyValues(String slaProperty){
		
		return Double.valueOf(System.getProperty(slaProperty));
	}
}
