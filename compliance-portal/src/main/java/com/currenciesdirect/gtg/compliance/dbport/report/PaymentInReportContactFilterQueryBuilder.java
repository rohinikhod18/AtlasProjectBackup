package com.currenciesdirect.gtg.compliance.dbport.report;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInFilter;
import com.currenciesdirect.gtg.compliance.dbport.AbstractFilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.CriteriaBuilder;

/**
 * The Class RegQueueFilterQueryBuilder.
 */
public class PaymentInReportContactFilterQueryBuilder extends AbstractFilterQueryBuilder {

	private PaymentInFilter filter;
	
	private boolean countryOfResidenceFilterAppliedInReport=false;
	private boolean filterAppliedForAccountAndContact=false;
	/**
	 * Instantiates a new reg queue contact filter query builder.
	 *
	 * @param filter the filter
	 * @param query the query
	 * @param isFilterApply the is filter apply
	 * @param addWhereCluase the add where cluase
	 */
	public PaymentInReportContactFilterQueryBuilder(PaymentInFilter filter, String query,boolean addWhereCluase) {
		super(query, addWhereCluase);
		this.filter = filter;
	}


	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder#addFiler()
	 */
	@Override
	protected void addFilter() {
		
		String keyInReport = filter.getKeyword();
		/**
		 * Validation for keyword not equal to null then match the Keyword with regularExpression and return type
		 * If type is not equal to null then search the record for that keyword
		 */
		if (keyInReport != null && !keyInReport.trim().isEmpty() && !KeywordConstants.COLON.equals(keyInReport)) {
			String keyword = keyInReport.trim();
			String typeInReport = parseKeyword(keyword);
			if (typeInReport != null) {
				if (!KeywordConstants.CLIENT_NAME.equals(typeInReport)) {
					searchKeyword(typeInReport, keyword);
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
	 */	
	public void searchKeyword(String typeInReport, String keywordInReport) {
		String[] keyValue = keywordInReport.split(KeywordConstants.COLON);

		if (keyValue.length >= 2) {
			switch (typeInReport) {
			case KeywordConstants.COUNTRY_OF_RESIDENCE:
				addCountryOfResidenceCriteria(keyValue[1]);
				countryOfResidenceFilterAppliedInReport = true;
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
			if (keyValue[0] != null && KeywordConstants.EMAIL.equals(typeInReport)) {
			 addEqualCriteria("vemail", keywordInReport, CriteriaBuilder.AND);
				
			}
		}

	}
	
	/**
	 * @param countryName
	 */
	private void addCountryOfResidenceCriteria(String countryNameInReport) {
		if(countryNameInReport != null) {
			addEqualCriteria(Constants.COUNTRY_OF_RESIDENCE_DISPLAY_NAME, countryNameInReport, CriteriaBuilder.AND);
		}
	}

	/**
	 * Gets is country of residence filter applied 
	 * 
	 * @return the isCountryOfResidenceFilterApplied
	 */
	public boolean isCountryOfResidenceFilterApplied() {
		return countryOfResidenceFilterAppliedInReport;
	}

	/**
	 * Sets country of residence filter applied 
	 * 
	 * @param countryOfResidenceFilterApplied
	 */
	public void setCountryOfResidenceFilterApplied(boolean countryOfResidenceFilterApplied) {
		this.countryOfResidenceFilterAppliedInReport = countryOfResidenceFilterApplied;
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
