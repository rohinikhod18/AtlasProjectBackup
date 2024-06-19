package com.currenciesdirect.gtg.compliance.core.domain.internalrule;

/**
 * The Class BlacklistSTPData.
 * 
 * @author Rajesh
 */
public class BlacklistSTPData {

	/** The type. */
	private String type;

	/** The value. */
	private String value;

	/** The found. */
	private Boolean found;

	/** The match. */
	private Integer match;
	
	/** The data from table that matched. */
	private String matchedData;

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
	 * @return the matchedData
	 */
	public String getMatchedData() {
		return matchedData;
	}

	/**
	 * @param matchedData the matchedData to set
	 */
	public void setMatchedData(String matchedData) {
		this.matchedData = matchedData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((found == null) ? 0 : found.hashCode());
		result = prime * result + ((match == null) ? 0 : match.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	@SuppressWarnings("squid:S3776")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlacklistSTPData other = (BlacklistSTPData) obj;
		if (found == null) {
			if (other.found != null)
				return false;
		} else if (!found.equals(other.found)) {
			return false;
		  }
		if (match == null) {
			if (other.match != null)
				return false;
		} else if (!match.equals(other.match)) {
			return false;
		  }
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type)) {
			return false;
		  }
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value)) {
			return false;
		  }
		return true;
	}

	@Override
	public String toString() {
		return "BlacklistSearchData [type=" + type + ", value=" + value + ", found=" + found + ", match=" + match + "]";
	}

}
