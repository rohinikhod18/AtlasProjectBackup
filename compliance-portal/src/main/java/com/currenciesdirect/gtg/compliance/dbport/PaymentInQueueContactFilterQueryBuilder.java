package com.currenciesdirect.gtg.compliance.dbport;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInFilter;

/**
 * The Class RegQueueFilterQueryBuilder.
 */
public class PaymentInQueueContactFilterQueryBuilder extends AbstractFilterQueryBuilder {

	private PaymentInFilter filter;
	
	private boolean countryOfResidenceFilterAppliedInQueue=false;
	private boolean filterAppliedForAccountAndContact=false;
	
	/**
	 * Instantiates a new reg queue contact filter query builder.
	 *
	 * @param filter the filter
	 * @param query the query
	 * @param isFilterApply the is filter apply
	 * @param addWhereCluase the add where cluase
	 */
	public PaymentInQueueContactFilterQueryBuilder(PaymentInFilter filter, String query, boolean addWhereCluase) {
		super(query, addWhereCluase);
		this.filter = filter;
	}


	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder#addFiler()
	 */
	@Override
	protected void addFilter() {
		
		String keyInQueue = filter.getKeyword();
		/**
		 * Validation for keyword not equal to null then match the Keyword with regularExpression and return type
		 * If type is not equal to null then search the record for that keyword
		 */
		if (keyInQueue != null && !keyInQueue.trim().isEmpty() && !KeywordConstants.COLON.equals(keyInQueue)) {
			String keyword = keyInQueue.trim();
			String typeInQueue = parseKeyword(keyword);
			if (typeInQueue != null) {
				if (!KeywordConstants.CLIENT_NAME.equals(typeInQueue)) {
					searchKeyword(typeInQueue, keyword);
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
	 * Business:
	 * 1.Get the type of the keyword and search value of that keyword
	 * Implementation:
	 * 1.Check type of keyword and apply criteria on column according to type
	 * 2.Search record according to value of that keyword
	 */	
	public void searchKeyword(String typeInQueue, String keywordInQueue) {
		String[] keyValue = keywordInQueue.split(KeywordConstants.COLON);

		if (keyValue.length >= 2) {
			switch (typeInQueue) {
			case KeywordConstants.COUNTRY_OF_RESIDENCE:
				addCountryOfResidenceCriteria(keyValue[1]);
				countryOfResidenceFilterAppliedInQueue = true;
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
			if (keyValue[0] != null && KeywordConstants.EMAIL.equals(typeInQueue)) {
			
					addEqualCriteria("vemail", keywordInQueue, CriteriaBuilder.AND);
				
			}
		}

	}
	
	private void addCountryOfResidenceCriteria(String countryNameInQueue) {
		if(countryNameInQueue != null) {
			addEqualCriteria(Constants.COUNTRY_OF_RESIDENCE_DISPLAY_NAME, countryNameInQueue, CriteriaBuilder.AND);
		}
	}
	
	/**
	 * Gets is country of residence filter applied 
	 * 
	 * @return the isCountryOfResidenceFilterApplied
	 */
	public boolean isCountryOfResidenceFilterApplied() {
		return countryOfResidenceFilterAppliedInQueue;
	}


	/**
	 * Sets country of residence filter applied 
	 * 
	 * @param countryOfResidenceFilterApplied
	 */
	public void setCountryOfResidenceFilterApplied(boolean countryOfResidenceFilterApplied) {
		this.countryOfResidenceFilterAppliedInQueue = countryOfResidenceFilterApplied;
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
