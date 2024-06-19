package com.currenciesdirect.gtg.compliance.dbport;

/**
 * The Enum ClauseType.
 */
public enum ClauseType {

	/** The equal. */
	EQUAL(" {field} = '{value}' "),

	/**  */

	NOT_EQUAL_2("( {field} <> '{value}' OR ({field} ='{value}'"),

	NOT_EQUAL_3("{field} <> '{value}'))"),

	/** The IS null. */
	ISNULL(" {field} IS null "),

	/** The not equal. */
	NOT_EQUAL(" {field} <> '{value}' "),

	/** The in. */
	IN(" {field} In ({value}) "),

	IN_WITH_BRACKET(" {field} In ({value}) )"),

	/** The datetime. */
	DATETIME("(CAST({field} AS DATE) = '{value}')"),

	GREATER_EQUAL("{field} >= '{value}'"),

	LESS_EQUAL("{field} <= '{value}'"),

	/** The datetime between. */
	DATETIME_BETWEEN(" ( {field}  BETWEEN '{value1} 00:00:00.000' AND '{value2} 23:59:59.000')"),

	// added clause type IN
	DATETIME_IN("(CAST({field} AS DATE) IN '{value}' )"),

	/** The between. */
	BETWEEN("{field} Between '{value1}' And {value2}"),

	ORDER_BY_DESC("ORDER BY {field} desc"),

	ORDER_BY_ASC("ORDER BY {field} asc"),

	/** changed '%{value}%' to '{value}%' */
	LIKE(" {field} like  '{value}%' "),

	NOT_IN("{field} NOT IN ({value})"),

	DEFAULT_DATETIME(" {field} > DATEADD(day, {value} ,GETDATE()) AND "),

	CONTAINS("CONTAINS ({field},'NEAR( ({attribute},{value}) ,{maxdistance}, {strictmatch})') "),

	GREATER("{field} > '{value}'"),

	/**
	 * Created separately because it will used only to match trade account number
	 * and contract no. in keyword search
	 */
	BEFORE_PERCENT_LIKE(" {field} like  '%{value}' "),

	LIKE_AT_ANY_POSITION(" {field} like  '%{value}%' "),
	
	EQUAL1(" {field} = '{value}')"),//AT-4850
	
	EQUAL2(" ({field} = '{value}'"),//AT-4850

	NOT_EQUAL_4("(({field} <> '{value}'"),//AT-4850
	
	NOT_EQUAL_5(" {field} <> '{value}') "),//AT-4850
	
	NOT_EQUAL_6(" ({field} <> '{value}'");//AT-4850


	/** The condition. */
	private String condition;

	/**
	 * Instantiates a new clause type.
	 *
	 * @param condition the condition
	 */
	ClauseType(String condition) {
		this.condition = condition;
	}

	/**
	 * Gets the condition.
	 *
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

}
