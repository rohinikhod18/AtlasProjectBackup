package com.currenciesdirect.gtg.compliance.dbport;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationFilter;


/**
 * The Class DataAnonContactFilterQueryBuilder.
 */
public class DataAnonContactFilterQueryBuilder extends AbstractFilterQueryBuilder {
	
	private DataAnonymisationFilter filter;
	
	private boolean countryOfResidenceFilterApplied=false;
	private boolean filterAppliedForAccountAndContact=false;
	
	
	/**
	 * Instantiates a new data anon contact filter query builder.
	 *
	 * @param filter the filter
	 * @param query the query
	 * @param addWhereCluase the add where cluase
	 */
	public DataAnonContactFilterQueryBuilder(DataAnonymisationFilter filter, String query, boolean addWhereCluase) {
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
		if (key != null && !key.trim().isEmpty() && !KeywordConstants.COLON.equals(key)) {
			String keyword = key.trim();
			String type = parseKeyword(keyword);
			if (type != null) {
				if (!KeywordConstants.CLIENT_NAME.equals(type)) {
					searchKeyword(type, keyword);
				}
				// Checking for ClientName only
				else {
					addLikeCriteria(KeywordConstants.CONTACT_NAME, keyword, CriteriaBuilder.AND);
					filterAppliedForAccountAndContact =true;
				}
			}
		}
		
	}
	
	/**
	 * The method searchKeyword
	 * @param type
	 * @param keyword
	 * Business:
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
				addCountryOfResidenceCriteria(keyValue[1]);
				countryOfResidenceFilterApplied = true;
				break;
			case KeywordConstants.ADDRESS:
				addContainsCriteria(Constants.ATTRIBUTES, Constants.ADDRESS_1,"\""+keyValue[1]+"\"", Integer.MAX_VALUE, false, CriteriaBuilder.AND);
				break;
			case KeywordConstants.OCCUPATION:
				addContainsCriteria(Constants.ATTRIBUTES, Constants.OCCUPATION, "\""+keyValue[1]+"\"", 0, true, CriteriaBuilder.AND);
				break;
			case KeywordConstants.TELEPHONE:  
				String searchValue = getSearchValue(keyValue);
				addContainsCriteria(Constants.ATTRIBUTES, Constants.PHONE, searchValue, 10, false, CriteriaBuilder.AND);
				break;
			default:
				break;
			}
		} else {
			if (keyValue[0] != null) {
				addEmailCriteria(type, keyword);
			}
		}

	}
	
	private void addCountryOfResidenceCriteria(String countryName) {
		if(countryName != null) {
			addEqualCriteria(Constants.COUNTRY_DISPLAY_NAME, countryName, CriteriaBuilder.AND);
		}
	}
	
	/**
	 * @param keyValue
	 * @return
	 */
	private String getSearchValue(String[] keyValue) {
		// Search in all types of phone numbers,
		// Also search for matching last nine digits
		String searchValue;
		if(keyValue[1].length()>9)
			searchValue = "\"*" + keyValue[1].substring(0, 9) + "*\"";
		else
			searchValue = "\"*" + keyValue[1] + "*\"";
		return searchValue;
	}
	
	/**
	 * @param type
	 * @param keyword
	 */
	private void addEmailCriteria(String type, String keyword) {
		if (KeywordConstants.EMAIL.equals(type)) {
			addEqualCriteria(Constants.EMAIL_DB_COLUMN, keyword, CriteriaBuilder.AND);
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
	
	/**
	 * Gets is country of residence filter applied 
	 * 
	 * @return the isCountryOfResidenceFilterApplied
	 */
	public boolean isCountryOfResidenceFilterApplied() {
		return countryOfResidenceFilterApplied;
	}


	/**
	 * Sets country of residence filter applied 
	 * 
	 * @param countryOfResidenceFilterApplied
	 */
	public void setCountryOfResidenceFilterApplied(boolean countryOfResidenceFilterApplied) {
		this.countryOfResidenceFilterApplied = countryOfResidenceFilterApplied;
	}

}
