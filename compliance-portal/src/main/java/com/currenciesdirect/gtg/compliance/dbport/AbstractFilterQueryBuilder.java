package com.currenciesdirect.gtg.compliance.dbport;

import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;

/**
 * The Class AbstractFilterQueryBuilder.
 */
public abstract class AbstractFilterQueryBuilder implements FilterQueryBuilder {

	/** The criteria builder. */
	private CriteriaBuilder criteriaBuilder;

	/**
	 * Instantiates a new abstract filter query builder.
	 */

	/**
	 * Adds the filer.
	 */
	protected abstract void addFilter();

	/**
	 * Instantiates a new abstract filter query builder.
	 *
	 * @param query the query
	 */
	public AbstractFilterQueryBuilder(String query) {
		criteriaBuilder = new CriteriaBuilder(query);
	}

	public AbstractFilterQueryBuilder(String query, boolean addWhereCluase) {
		criteriaBuilder = new CriteriaBuilder(query, addWhereCluase);
	}

	/**
	 * Adds the in criteria.
	 *
	 * @param field         the field
	 * @param value         the value
	 * @param conditionType the condition type
	 */
	protected void addInCriteria(String field, String[] value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.IN);
	}

	/**
	 * Adds the in criteria.
	 *
	 * @param field         the field
	 * @param value         the value
	 * @param conditionType the condition type
	 */
	protected void addInCriteriaWithBracket(String field, String[] value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.IN_WITH_BRACKET);
	}

	protected void addNotInCriteria(String field, String[] value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.NOT_IN);
	}

	protected void addNotEqualCriteria2(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.NOT_EQUAL_2);
	}

	protected void addNotEqualCriteria3(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.NOT_EQUAL_3);
	}
	
	//Added for AT-4850
	protected void addNotEqualCriteria4(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.NOT_EQUAL_4);
	}
	//Added for AT-4850
	protected void addNotEqualCriteria5(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.NOT_EQUAL_5);
	}

	// Added for AT-4850
	protected void addNotEqualCriteria6(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.NOT_EQUAL_6);
	}

	
	// Added for AT-4850
	protected void addEqualCriteria1(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.EQUAL1);
	}
	
	// Added for AT-4850
		protected void addEqualCriteria2(String field, String value, String conditionType) {
			criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.EQUAL2);
		}
	
	
	/**
	 * Adds the equal criteria.
	 *
	 * @param field         the field
	 * @param value         the value
	 * @param conditionType the condition type
	 */
	protected void addEqualCriteria(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.EQUAL);
	}

	/**
	 * Adds the is null criteria.
	 *
	 * @param field         the field
	 * 
	 * @param conditionType the condition type
	 */
	protected void addISNullCriteria(String field, String conditionType) {
		criteriaBuilder.addCriteria(field, null, conditionType, ClauseType.ISNULL);
	}

	/**
	 * Adds the not equal criteria.
	 *
	 * @param field         the field
	 * @param value         the value
	 * @param conditionType the condition type
	 */
	protected void addNotEqualCriteria(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.NOT_EQUAL);
	}

	/**
	 * Adds the date time criteria.
	 *
	 * @param field         the field
	 * @param value         the value
	 * @param conditionType the condition type
	 */
	protected void addDateTimeCriteria(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.DATETIME);
	}

	/**
	 * Adds the date time criteria.
	 *
	 * @param field         the field
	 * @param value1        the value 1
	 * @param value2        the value 2
	 * @param replaceString the replace string
	 */
	protected void addDateTimeCriteria(String field, String value1, String value2, String replaceString,
			Boolean isFilterAppy) {
		if (value1 != null && value2 != null && !value1.isEmpty() && !value2.isEmpty()) {

			criteriaBuilder.addDateTimeCriteria(field, value1, value2, ClauseType.DATETIME, replaceString);

		} else if (value1 != null && !value1.isEmpty()) {

			criteriaBuilder.addDateTimeCriteria(field, value1, null, ClauseType.GREATER_EQUAL, replaceString);

		} else if (value2 != null && !value2.isEmpty()) {

			criteriaBuilder.addDateTimeCriteria(field, null, value2, ClauseType.LESS_EQUAL, replaceString);

		} else {
			addDateTimeCriteriaIfNoFilterApplied(field, value1, value2, replaceString, isFilterAppy);
		}
	}

	private void addDateTimeCriteriaIfNoFilterApplied(String field, String value1, String value2, String replaceString,
			Boolean isFilterAppy) {
		if (Boolean.FALSE.equals(isFilterAppy) && value1 == null && value2 == null) {

			criteriaBuilder.addDateTimeCriteria(field, null, null, null, replaceString);

		} else {
			criteriaBuilder.removeDateTimeCriteriaFromQuery("", replaceString);
		}
	}

	protected void addDateTimeCriteria(String field, String value1, String value2, String conditionType) {
		if (value1 != null && value2 != null && !value1.isEmpty() && !value2.isEmpty()) {
			addDateTimeBetweenCriteria(field, value1, value2, conditionType);
		} else if (value1 != null && !value1.isEmpty()) {
			criteriaBuilder.addGreaterEqualDateCriteria(field, value1, conditionType);
		} else if (value2 != null && !value2.isEmpty()) {
			criteriaBuilder.addLessEqualDateCriteria(field, value2, conditionType);
		}
	}

	/**
	 * Adds the date time between criteria.
	 *
	 * @param field         the field
	 * @param value1        the value 1
	 * @param value2        the value 2
	 * @param conditionType the condition type
	 */
	protected void addDateTimeBetweenCriteria(String field, String value1, String value2, String conditionType) {
		criteriaBuilder.addBetweenCriteria(field, conditionType, value1, value2, ClauseType.DATETIME);
	}

	/**
	 * Adds the between criteria.
	 *
	 * @param field         the field
	 * @param value1        the value 1
	 * @param value2        the value 2
	 * @param conditionType the condition type
	 */
	protected void addBetweenCriteria(String field, String value1, String value2, String conditionType) {
		criteriaBuilder.addBetweenCriteria(field, conditionType, value1, value2, ClauseType.BETWEEN);
	}

	/**
	 * Validate service status.
	 *
	 * @param status the status
	 * @return the boolean
	 */
	protected Boolean validateServiceStatus(String[] status) {
		return status == null || status.length == 0 || status.length >= 2;

	}

	/**
	 * Adds the like criteria.
	 *
	 * @param field         the field
	 * @param value         the value
	 * @param conditionType the condition type
	 */
	protected void addLikeCriteria(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.LIKE);
	}

	/**
	 * Adds the order by criteria.
	 *
	 * @param field the field
	 * @param value the value
	 */
	protected void addOrderByCriteria(String field, Boolean value) {
		criteriaBuilder.addOrderByCriteria(field, value);
	}

	/**
	 * Adds the sort.
	 *
	 * @param sort          the sort
	 * @param defaultColumn the default column
	 */
	protected void addSort(String columnName, boolean isAsc) {
		String query = criteriaBuilder.build();
		String orderByCon = "{ORDER_TYPE}";
		if (columnName != null) {
			query = query.replace("{SORT_FIELD_NAME}", columnName);
			if (isAsc) {
				query = query.replace(orderByCon, "ASC");
			} else {
				query = query.replace(orderByCon, "DESC");
			}
		}
		criteriaBuilder.setQuery(new StringBuilder(query));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder#getQuery()
	 */
	@Override
	public String getQuery() {
		addFilter();
		return this.criteriaBuilder.build();
	}

	/**
	 * Adds the contains criteria. CONTAINS ({field},'NEAR( ({attribute},{value})
	 * ,{maxdistance}, {strictmatch})') AND
	 * 
	 * @return the string
	 */
	public void addContainsCriteria(String field, String attribute, String value, Integer maxDisctance,
			Boolean strictMatch, String conditionType) {
		criteriaBuilder.addContainsCriteria(field, attribute, value, maxDisctance, strictMatch, conditionType);
	}

	/**
	 * Adds the greater criteria.
	 *
	 * @param field         the field
	 * @param value         the value
	 * @param conditionType the condition type
	 */
	protected void addGreaterCriteria(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.GREATER);
	}

	/**
	 * Adds the like for Digits criteria.
	 *
	 * @param field         the field
	 * @param value         the value
	 * @param conditionType the condition type
	 */
	protected void addBeforePercentLikeCriteria(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.BEFORE_PERCENT_LIKE);
	}

	protected void addPercentLikeCriteria(String field, String value, String conditionType) {
		criteriaBuilder.addCriteria(field, value, conditionType, ClauseType.LIKE_AT_ANY_POSITION);
	}

	/**
	 * The method parseKeyword Bussiness: 1.Check validation on keyword and parsing
	 * the default value of keyword Implementation: 1.Get the keyword from UI in any
	 * case(ie Upper or Lower),change that keyword case to lower and check that
	 * keyword is in particular format or not? 2.If that keyword matches with format
	 * then return that keyword type 3.Else return nothing
	 *
	 * @param keyword the keyword
	 * @return the string
	 */
	protected String parseKeyword(String keyword) {
		if (keyword.matches(KeywordConstants.REGEX_EMAIL)) {
			return KeywordConstants.EMAIL;
		}
		if (keyword.matches(KeywordConstants.REGEX_CLIENT_ID_WITH_SIX_DIGITS_ONLY)) {
			return KeywordConstants.CLIENT_ID_WITH_SIX_DIGITS_ONLY;
		}
		if (keyword.matches(KeywordConstants.REGEX_CLIENT_ID)
				|| keyword.matches(KeywordConstants.REGEX_CLIENT_ID_LEGACY)) {
			return KeywordConstants.CLIENT_ID;
		}
		if (keyword.matches(KeywordConstants.REGEX_CONTRACT_NO)) {
			return KeywordConstants.CONTRACT_NO;
		}
		if (keyword.toLowerCase().startsWith(KeywordConstants.REGEX_ACSF_ID)) {
			return KeywordConstants.ACSF_ID;
		}
		if (keyword.toLowerCase().startsWith(KeywordConstants.REGEX_COUNTRY_OF_RESIDENCE)) {
			return KeywordConstants.COUNTRY_OF_RESIDENCE;
		}
		if (keyword.toLowerCase().startsWith(KeywordConstants.REGEX_ADDRESS)) {
			return KeywordConstants.ADDRESS;
		}
		if (keyword.toLowerCase().startsWith(KeywordConstants.REGEX_OCCUPATION)) {
			return KeywordConstants.OCCUPATION;
		}
		if (keyword.toLowerCase().startsWith(KeywordConstants.REGEX_PAYMENT_METHOD)) {
			return KeywordConstants.PAYMENT_METHOD;
		}
		if (keyword.toLowerCase().startsWith(KeywordConstants.REGEX_AMOUNT)) {
			return KeywordConstants.AMOUNT;
		}
		if (keyword.toLowerCase().startsWith(KeywordConstants.REGEX_REASON_OF_TRANSFER)) {
			return KeywordConstants.REASON_OF_TRANSFER;
		}
		if (keyword.toLowerCase().startsWith(KeywordConstants.REGEX_BENEFICIARY)) {
			return KeywordConstants.BENEFICIARY;
		}
		if (keyword.toLowerCase().startsWith(KeywordConstants.REGEX_TELEPHONE)) {
			return KeywordConstants.TELEPHONE;
		}

		return KeywordConstants.CLIENT_NAME;
	}
}
