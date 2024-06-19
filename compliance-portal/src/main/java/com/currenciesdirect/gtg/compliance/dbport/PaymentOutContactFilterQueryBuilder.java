package com.currenciesdirect.gtg.compliance.dbport;

import com.currenciesdirect.gtg.compliance.core.domain.BaseFilter;
import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;

/**
 * The Class RegQueueFilterQueryBuilder.
 */
public class PaymentOutContactFilterQueryBuilder extends AbstractFilterQueryBuilder {
	
	private boolean filterAppliedForAccountAndContact = false;

	private BaseFilter filter;
	
	/**
	 * Instantiates a new reg queue contact filter query builder.
	 *
	 * @param filter the filter
	 * @param query the query
	 * @param isFilterApply the is filter apply
	 * @param addWhereCluase the add where cluase
	 */
	public PaymentOutContactFilterQueryBuilder(BaseFilter filter, String query, boolean addWhereCluase) {
		super(query, addWhereCluase);
		this.filter = filter;
	}


	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder#addFiler()
	 */
	@Override
	protected void addFilter() {
		
		String key = filter.getKeyword();
		/**
		 * Validation for keyword not equal to null then match the Keyword with regularExpression and return type
		 * If type is not eqaul to null then search the record for that keyword
		 */
		if (key != null && !key.trim().isEmpty()) {
			String keyword = key.trim();
			String type = parseKeyword(keyword);
			if (type != null) {
				if (!KeywordConstants.CLIENT_NAME.equals(type)) {
					searchKeyword(type, keyword);
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
	public void searchKeyword(String type, String keyword) {
		String[] keyValue = keyword.split(KeywordConstants.COLON);

		if (keyValue.length >= 2) {
			switch (type) {
			case KeywordConstants.COUNTRY_OF_RESIDENCE:
				addContainsCriteria(Constants.ATTRIBUTES, "country_of_residence", keyValue[1], 0, true, CriteriaBuilder.AND);
				break;
			case KeywordConstants.ADDRESS:
				addContainsCriteria(Constants.ATTRIBUTES, "address1", "\""+keyValue[1]+"\"", Integer.MAX_VALUE, false, CriteriaBuilder.AND);
				break;
			case KeywordConstants.OCCUPATION:
				addContainsCriteria(Constants.ATTRIBUTES, "occupation", "\""+keyValue[1]+"\"", 0, true, CriteriaBuilder.AND);
				break;
			default:
				break;
			}
		} else {
			if (keyValue[0] != null && KeywordConstants.EMAIL.equals(type)) {
				
					addEqualCriteria("vemail", keyword, CriteriaBuilder.AND);
					
				
			}
		}

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
