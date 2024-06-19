package com.currenciesdirect.gtg.compliance.dbport.report;

import com.currenciesdirect.gtg.compliance.core.domain.BaseFilter;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;
import com.currenciesdirect.gtg.compliance.dbport.AbstractFilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.CriteriaBuilder;

/**
 * The Class RegQueueFilterQueryBuilder.
 */
public class PaymentOutReportContactFilterQueryBuilder extends AbstractFilterQueryBuilder {

	/** The filter. */
	private BaseFilter filter;
	
	/** The country of residence filter applied out report. */
	private boolean countryOfResidenceFilterAppliedOutReport=false;
	private boolean filterAppliedForAccountAndContact=false;
	
	/**
	 * Instantiates a new reg queue contact filter query builder.
	 *
	 * @param filter the filter
	 * @param query the query
	 * @param addWhereCluase the add where cluase
	 */
	public PaymentOutReportContactFilterQueryBuilder(BaseFilter filter, String query,  boolean addWhereCluase)  {
		super(query, addWhereCluase);
		this.filter = filter;
	}


	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder#addFiler()
	 */
	@Override
	protected void addFilter() {
		
		String keyOutReport = filter.getKeyword();
		/**
		 * Validation for keyword not equal to null then match the Keyword with regularExpression and return type
		 * If type is not eqaul to null then search the record for that keyword
		 */
		if (keyOutReport != null && !keyOutReport.trim().isEmpty() && !KeywordConstants.COLON.equals(keyOutReport)) {
			String keyword = keyOutReport.trim();
			String typeOutReport = parseKeyword(keyword);
			if (typeOutReport != null) {
				if (!KeywordConstants.CLIENT_NAME.equals(typeOutReport)) {
					searchKeyword(typeOutReport, keyword);
				}
				// Checking for ClientName only
				else {
					addLikeCriteria("name", keyword, CriteriaBuilder.AND);
					filterAppliedForAccountAndContact = true;
				}
			}
		}
		
	}
	
	
	
	
	
	/**
	 * The method searchKeyword
	 * Bussiness:
	 * 1.Get the type of the keyword and search value of that keyword
	 * Implementation:
	 * 1.Check type of keyword and apply criteria on column according to type
	 * 2.Search record according to value of that keyword
	 *
	 * @param typeOutReport the type out report
	 * @param keywordOutReport the keyword out report
	 */	
	public void searchKeyword(String typeOutReport, String keywordOutReport) {
		String[] keyValue = keywordOutReport.split(KeywordConstants.COLON);

		if (keyValue.length >= 2) {
			switch (typeOutReport) {
			case KeywordConstants.COUNTRY_OF_RESIDENCE:
				addCountryOfResidenceCriteria(keyValue[1]);
				countryOfResidenceFilterAppliedOutReport = true;
				break;
			case KeywordConstants.ADDRESS:
				addContainsCriteria("Attributes", "address1", "\""+keyValue[1]+"\"", Integer.MAX_VALUE, false, CriteriaBuilder.AND);
				
				break;

			case KeywordConstants.OCCUPATION:
				addContainsCriteria("Attributes", "occupation", "\""+keyValue[1]+"\"", 0, true, CriteriaBuilder.AND);
				break;
			default:
				break;
			}
		} else {
			if (keyValue[0] != null && KeywordConstants.EMAIL.equals(typeOutReport)) {
				addEqualCriteria("vemail", keywordOutReport, CriteriaBuilder.AND);	
			}
		}

	}
	
	/**
	 * Adds country of residence criteria.
	 *
	 * @param countryNameOutReport the country name out report
	 */
	private void addCountryOfResidenceCriteria(String countryNameOutReport) {
		if(countryNameOutReport != null) {
			addEqualCriteria(Constants.COUNTRY_OF_RESIDENCE_DISPLAY_NAME, countryNameOutReport, CriteriaBuilder.AND);
		}
	}
	
	/**
	 * Gets is country of residence filter applied .
	 *
	 * @return the isCountryOfResidenceFilterApplied
	 */
	public boolean isCountryOfResidenceFilterApplied() {
		return countryOfResidenceFilterAppliedOutReport;
	}

	/**
	 * Sets country of residence filter applied .
	 *
	 * @param countryOfResidenceFilterApplied the new country of residence filter applied
	 */
	public void setCountryOfResidenceFilterApplied(boolean countryOfResidenceFilterApplied) {
		this.countryOfResidenceFilterAppliedOutReport = countryOfResidenceFilterApplied;
	}


	/**
	 * @return the filterAppliedForAccountAndContact
	 */
	public boolean isFilterAppliedForAccountAndContact() {
		return filterAppliedForAccountAndContact;
	}


	/**
	 * @param filterAppliedForAccountAndContact the filterAppliedForAccountAndContact to set
	 */
	public void setFilterAppliedForAccountAndContact(boolean filterAppliedForAccountAndContact) {
		this.filterAppliedForAccountAndContact = filterAppliedForAccountAndContact;
	}
	
	
}
