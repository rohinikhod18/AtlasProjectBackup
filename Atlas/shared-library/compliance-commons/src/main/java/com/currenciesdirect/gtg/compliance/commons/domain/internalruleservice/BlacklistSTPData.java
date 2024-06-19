package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;

/**
 * The Class BlacklistSTPData.
 * 
 * @author Rajesh
 */
public class BlacklistSTPData implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The type. */
	private String type;
	
	/** The request type is added to resolve AT-1198 */
	private String requestType;

	/** The value. */
	private String value;

	/** The found. */
	private Boolean found;

	/** The match. */
	private Integer match;

	/** The data from table that matched. */
	private String matchedData;
	
	/** The requestType to identify the request field. */
	
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the found.
	 *
	 * @return the found
	 */
	public Boolean getFound() {
		return found;
	}

	/**
	 * Sets the found.
	 *
	 * @param found
	 *            the new found
	 */
	public void setFound(Boolean found) {
		this.found = found;
	}

	/**
	 * Gets the match.
	 *
	 * @return the match
	 */
	public Integer getMatch() {
		return match;
	}

	/**
	 * Sets the match.
	 *
	 * @param match
	 *            the new match
	 */
	public void setMatch(Integer match) {
		this.match = match;
	}

	/**
	 * Gets the matched data.
	 *
	 * @return the matchedData
	 */
	public String getMatchedData() {
		return matchedData;
	}

	/**
	 * Sets the matched data.
	 *
	 * @param matchedData
	 *            the matchedData to set
	 */
	public void setMatchedData(String matchedData) {
		this.matchedData = matchedData;
	}

	
}
