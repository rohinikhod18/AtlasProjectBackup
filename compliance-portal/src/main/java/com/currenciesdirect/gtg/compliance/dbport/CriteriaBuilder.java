package com.currenciesdirect.gtg.compliance.dbport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;

/**
 * The Class CriteriaBuilder.
 */
public class CriteriaBuilder {

	/** The Constant FIELD. */
	private static final String FIELD = "{field}";

	/** The Constant WHERE. */
	private static final String WHERE = " WHERE ";

	/** The Constant VALUE. */
	private static final String VALUE = "{value}";

	/** The Constant ATTRIBUTE. */
	private static final String ATTRIBUTE = "{attribute}";

	/** The Constant MAXDISTANCE. */
	private static final String MAXDISTANCE = "{maxdistance}";

	/** The Constant STRICTMATCH. */
	private static final String STRICTMATCH = "{strictmatch}";

	/** The is first condition. */
	private Boolean isFirstCondition;

	/** The query. */
	private StringBuilder query;

	/** The Constant AND. */
	public static final String AND = " AND ";

	/** The Constant OR. */
	public static final String OR = " OR ";

	/**
	 * Instantiates a new criteria builder.
	 *
	 * @param query the query
	 */
	public CriteriaBuilder(String query) {
		this.query = new StringBuilder(query);
		isFirstCondition = Boolean.TRUE;
	}

	public CriteriaBuilder(String query, boolean isFirstCondition) {
		this.query = new StringBuilder(query);
		this.isFirstCondition = isFirstCondition;
	}

	/**
	 * Adds the criteria.
	 *
	 * @param <T>           the generic type
	 * @param field         the field
	 * @param value         the value
	 * @param conditionType the condition type
	 * @param clauseType    the clause type
	 * @return the criteria builder
	 */
	public <T> CriteriaBuilder addCriteria(String field, T value, String conditionType, ClauseType clauseType) {
		String criteria = null;
		switch (clauseType) {
		case EQUAL:
			criteria = addEqualCriteria(field, (String) value);
			break;
		case ISNULL:
			criteria = addISNullCriteria(field);
			break;
		case NOT_EQUAL:
			criteria = addNotEqualCriteria(field, (String) value);
			break;

		case NOT_EQUAL_2:
			criteria = addNotEqualCriteria2(field, (String) value);
			break;
		case NOT_EQUAL_3:
			criteria = addNotEqualCriteria3(field, (String) value);
			break;

		// Added for AT-4850
		case NOT_EQUAL_4:
			criteria = addNotEqualCriteria4(field, (String) value);
			break;

		// Added for AT-4850
		case NOT_EQUAL_5:
			criteria = addNotEqualCriteria5(field, (String) value);
			break;

		// Added for AT-4850
		case NOT_EQUAL_6:
			criteria = addNotEqualCriteria6(field, (String) value);
			break;

		// Added for AT-4850
		case EQUAL1:
			criteria = addEqualCriteria1(field, (String) value);
			break;

		// Added for AT-4850
		case EQUAL2:
			criteria = addEqualCriteria2(field, (String) value);
			break;

		case IN:
			criteria = addInCriteria(field, (String[]) value);
			break;
		case NOT_IN:
			criteria = addNotInCriteria(field, (String[]) value);
			break;
		case DATETIME:
			criteria = addDateTimeCriteria(field, (String) value);
			break;
		case LIKE:
			criteria = addLikeCriteria(field, (String) value);
			break;
		case LESS_EQUAL:
			criteria = addLessEqualCriteria(field, (String) value);
			break;
		case GREATER_EQUAL:
			criteria = addGreaterEqualCriteria(field, (String) value);
			break;
		case GREATER:
			criteria = addGreaterCriteria(field, (String) value);
			break;
		case BEFORE_PERCENT_LIKE:
			criteria = addBeforePercentLikeCriteria(field, (String) value);
			break;
		case IN_WITH_BRACKET:
			criteria = addInWithBracketCriteria(field, (String[]) value);
			break;
		case LIKE_AT_ANY_POSITION:
			criteria = addLikeCriteriaAtAnyPosition(field, (String) value);
			break;
		default:
			break;
		}
		addCriteriaInQuery(criteria, conditionType);
		return this;
	}

	/**
	 * Adds the like criteria.
	 *
	 * @param field the field
	 * @param value the value
	 * @return the string
	 */
	private String addLikeCriteria(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		String clause = ClauseType.LIKE.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	private String addLikeCriteriaAtAnyPosition(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		String clause = ClauseType.LIKE_AT_ANY_POSITION.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	/**
	 * Adds the criteria in query.
	 *
	 * @param criteria      the criteria
	 * @param conditionType the condition type
	 */
	private void addCriteriaInQuery(String criteria, String conditionType) {
		if (this.query != null && criteria != null && criteria.isEmpty()) {
			return;
		}

		if (Boolean.TRUE.equals(isFirstCondition) && null != this.query) {
			isFirstCondition = Boolean.FALSE;
			query.append(WHERE).append(criteria);
		} else if (this.query != null && criteria != null && !criteria.isEmpty() && conditionType != null) {
			this.query.append(conditionType).append(criteria);
		} else if (null != this.query) {
			this.query.append("  ").append(criteria).append("   ");
		}
	}

	/**
	 * Adds the date time criteria in query.
	 *
	 * @param criteria the criteria
	 */
	public void addDateTimeCriteriaInQuery(String criteria, String replaceString) {
		if (this.query != null && criteria != null && criteria.isEmpty()) {
			return;
		}
		if (null != this.query) {
			this.query = new StringBuilder(this.query.toString().replace(replaceString, criteria));
		}
	}

	public void removeDateTimeCriteriaFromQuery(String criteria, String replaceString) {
		if (this.query != null && replaceString != null && replaceString.isEmpty()) {
			return;
		}
		if (null != this.query) {
			this.query = new StringBuilder(this.query.toString().replace(replaceString, criteria));
		}
	}

	/**
	 * Adds the equal criteria.
	 *
	 * @param field the field
	 * @param value the value
	 * @return the string
	 */
	private String addEqualCriteria(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.EQUAL.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	/**
	 * Adds the IS null criteria.
	 *
	 * @param field the field
	 * @return the string
	 */
	private String addISNullCriteria(String field) {
		if (field != null) {
			String clause = ClauseType.ISNULL.getCondition();
			clause = clause.replace(FIELD, field);

			return clause;
		}
		return null;
	}

	/**
	 * Adds the less equal criteria.
	 *
	 * @param field the field
	 * @param value the value
	 * @return the string
	 */
	private String addLessEqualCriteria(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.LESS_EQUAL.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	/**
	 * Adds the greater equal criteria.
	 *
	 * @param field the field
	 * @param value the value
	 * @return the string
	 */
	private String addGreaterEqualCriteria(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.GREATER_EQUAL.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	/**
	 * Adds the not equal criteria.
	 *
	 * @param field the field
	 * @param value the value(field, value)EqualCriteria
	 * @return the string
	 */
	private String addNotEqualCriteria(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.NOT_EQUAL.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	private String addNotEqualCriteria2(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.NOT_EQUAL_2.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	private String addNotEqualCriteria3(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.NOT_EQUAL_3.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	// Added for AT-4850
	private String addNotEqualCriteria4(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.NOT_EQUAL_4.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	// Added for AT-4850
	private String addNotEqualCriteria5(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.NOT_EQUAL_5.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	// Added for AT-4850
	private String addNotEqualCriteria6(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.NOT_EQUAL_6.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	// Added for AT-4850
	private String addEqualCriteria1(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.EQUAL1.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	// Added for AT-4850
	private String addEqualCriteria2(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.EQUAL2.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	/**
	 * Adds the in criteria.
	 *
	 * @param field  the field
	 * @param values the values
	 * @return the string
	 */
	private String addInCriteria(String field, String[] values) {
		if (values == null || values.length == 0) {
			return null;
		}
		String value = appendAllValues(values);
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.IN.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	/**
	 * Adds the in criteria.
	 *
	 * @param field  the field
	 * @param values the values
	 * @return the string
	 */
	private String addInWithBracketCriteria(String field, String[] values) {
		if (values == null || values.length == 0) {
			return null;
		}
		String value = appendAllValues(values);
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.IN_WITH_BRACKET.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	private String addNotInCriteria(String field, String[] values) {
		if (values == null || values.length == 0) {
			return null;
		}
		String value = appendAllValues(values);
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.NOT_IN.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	/**
	 * Adds the date time criteria.
	 *
	 * @param field the field
	 * @param date  the date
	 * @return the string
	 */
	private String addDateTimeCriteria(String field, String date) {
		if (date == null || date.isEmpty()) {
			return null;
		}
		String parsedate = parseDate(date);
		if (parsedate == null) {
			return null;
		}
		String clause = ClauseType.DATETIME.getCondition();
		clause = clause.replace(FIELD, field).replace(VALUE, parsedate);
		return clause;
	}

	/**
	 * Adds the default date time criteria.
	 *
	 * @param field the field
	 * @param value the value
	 * @return the string
	 */
	private String addDefaultDateTimeCriteria(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		String clause = ClauseType.DEFAULT_DATETIME.getCondition();
		clause = clause.replace(FIELD, field).replace(VALUE, value);
		return clause;
	}

	/**
	 * Adds the between criteria.
	 *
	 * @param field         the field
	 * @param conditionType the condition type
	 * @param value1        the value 1
	 * @param value2        the value 2
	 * @param clauseType    the clause type
	 * @return the string
	 */
	public CriteriaBuilder addBetweenCriteria(String field, String conditionType, String value1, String value2,
			ClauseType clauseType) {
		String criteria;
		if (value1 == null || value1.isEmpty() || value2 == null || value2.isEmpty()) {
			return null;
		}
		if (clauseType == ClauseType.DATETIME) {
			criteria = addBetweenForDateCriteria(field, value1, value2);
		} else {
			criteria = defaultBetweenCriteria(field, value1, value2);
		}

		addCriteriaInQuery(criteria, conditionType);

		return this;
	}

	/**
	 * Adds the between for date criteria.
	 *
	 * @param field  the field
	 * @param value1 the value 1
	 * @param value2 the value 2
	 * @return the string
	 */
	private String addBetweenForDateCriteria(String field, String value1, String value2) {
		if (value1 == null || value1.isEmpty() || value2 == null || value2.isEmpty()) {
			return null;
		}
		String parsedate1 = parseDate(value1);
		if (parsedate1 == null) {
			return null;
		}

		String parsedate2 = parseDate(value2);
		if (parsedate2 == null) {
			return null;
		}
		return ClauseType.DATETIME_BETWEEN.getCondition().replace(FIELD, field).replace("{value1}", parsedate1)
				.replace("{value2}", parsedate2);

	}

	/**
	 * Default between criteria.
	 *
	 * @param field  the field
	 * @param value1 the value 1
	 * @param value2 the value 2
	 * @return the string
	 */
	private String defaultBetweenCriteria(String field, String value1, String value2) {
		return ClauseType.BETWEEN.getCondition().replace(FIELD, field).replace("{value1}", value1).replace("{value2}",
				value2);
	}

	/**
	 * Append all values.
	 *
	 * @param values the values
	 * @return the string
	 */
	private String appendAllValues(String[] values) {
		StringBuilder s = new StringBuilder();
		for (String value : values) {
			if (value != null && !value.isEmpty()) {
				s.append("'").append(value).append("'").append(',');
			}
		}
		if (s.length() > 1) {
			s.setLength(s.length() - 1);
			return s.toString();
		}
		return null;

	}

	/**
	 * Adds the date time criteria.
	 *
	 * @param field         the field
	 * @param value1        the value 1
	 * @param value2        the value 2
	 * @param clauseType    the clause type
	 * @param replaceString the replace string
	 * @return the criteria builder
	 */
	public CriteriaBuilder addDateTimeCriteria(String field, String value1, String value2, ClauseType clauseType,
			String replaceString) {
		String criteria;
		if (ClauseType.DATETIME == clauseType) {

			criteria = addBetweenForDateCriteria(field, value1, value2);

		} else if (ClauseType.GREATER_EQUAL == clauseType) {

			criteria = getGreaterEqualDateCriteria(field, value1);

		} else if (ClauseType.LESS_EQUAL == clauseType) {

			criteria = getLessEqualDateCriteria(field, value2);

		} else {

			criteria = addDefaultDateTimeCriteria(field, "-" + SearchCriteriaUtil.getDefaultQueueRecordSize());

		}

		addDateTimeCriteriaInQuery(criteria, replaceString);

		return this;
	}

	/**
	 * Builds the.
	 *
	 * @return the string
	 */
	public String build() {
		if (this.query != null) {
			return this.query.toString();
		}
		return null;
	}

	/**
	 * Parses the date.
	 *
	 * @param date the date
	 * @return the string
	 */
	private String parseDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		Date dateO;
		try {
			dateO = sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
		sdf = new SimpleDateFormat("yyyy-mm-dd");
		return sdf.format(dateO);
	}

	/**
	 * Adds the between criteria.
	 *
	 * @param field the field
	 * @param value the value
	 * @return the string
	 */
	public CriteriaBuilder addOrderByCriteria(String field, Boolean value) {
		String criteria;
		if (value == null) {
			return null;
		}
		if (Boolean.FALSE.equals(value)) {
			criteria = ClauseType.ORDER_BY_DESC.getCondition().replace(FIELD, field);
		} else {
			criteria = ClauseType.ORDER_BY_ASC.getCondition().replace(FIELD, field);
		}
		this.query.append("  " + criteria + "   ");
		return this;
	}

	/**
	 * Sets the query.
	 *
	 * @param query the new query
	 */
	public void setQuery(StringBuilder query) {
		this.query = query;
	}

	/**
	 * added to parse date into database readable format when filter is applied for
	 * (date from/registered from) - Neelesh Pant
	 */

	public String getGreaterEqualDateCriteria(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String parsedatefrom = parseDate(value);
		if (parsedatefrom == null) {
			return null;
		}
		return addGreaterEqualCriteria(field, parsedatefrom);

	}

	public void addGreaterEqualDateCriteria(String field, String value, String conditionType) {
		if (value == null || value.isEmpty()) {
			return;
		}
		String parsedatefrom = parseDate(value);
		if (parsedatefrom == null) {
			return;
		}
		addCriteriaInQuery(addGreaterEqualCriteria(field, parsedatefrom), conditionType);

	}

	/**
	 * added to parse date into database readable format when filter is applied for
	 * (date to) -Neelesh Pant
	 */

	public String getLessEqualDateCriteria(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String parsedateto = parseDate(value);
		if (parsedateto == null) {
			return null;
		}
		return addLessEqualCriteria(field, parsedateto);

	}

	public void addLessEqualDateCriteria(String field, String value, String conditionType) {
		if (value == null || value.isEmpty()) {
			return;
		}
		String parsedateto = parseDate(value);
		if (parsedateto == null) {
			return;
		}
		addCriteriaInQuery(addLessEqualCriteria(field, parsedateto), conditionType);

	}

	/**
	 * Adds the contains criteria. CONTAINS ({field},'NEAR( ({attribute},{value})
	 * ,{maxdistance}, {strictmatch})') AND
	 * 
	 * @return the string
	 */
	public void addContainsCriteria(String field, String attribute, String value, Integer maxDisctance,
			Boolean strictMatch, String conditionType) {
		String criteria = ClauseType.CONTAINS.getCondition().replace(FIELD, field).replace(ATTRIBUTE, attribute)
				.replace(VALUE, value).replace(MAXDISTANCE, maxDisctance.toString())
				.replace(STRICTMATCH, strictMatch.toString());

		addCriteriaInQuery(criteria, conditionType);
	}

	/**
	 * Adds the greater criteria.
	 *
	 * @param field the field
	 * @param value the value
	 * @return the string
	 */
	private String addGreaterCriteria(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String clause = ClauseType.GREATER.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

	/**
	 * Adds the like for digits (TradeAccountNumber and Contract No.) criteria.
	 *
	 * @param field the field
	 * @param value the value
	 * @return the string
	 */
	private String addBeforePercentLikeCriteria(String field, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		String clause = ClauseType.BEFORE_PERCENT_LIKE.getCondition();
		clause = clause.replace(FIELD, field);
		clause = clause.replace(VALUE, value);
		return clause;
	}

}
