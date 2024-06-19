package com.currenciesdirect.gtg.compliance.customerdatascan.core.domain;

/**
 * The Class MatchAndCount.
 */
public class MatchAndCount {
	
	/** The found. */
	private Boolean found;
	
	/** The count. */
	private Integer count;

	/**
	 * Instantiates a new match and count.
	 */
	public MatchAndCount(){
		found = false;
		count = 0;
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
	 * @param found the new found
	 */
	public void setFound(Boolean found) {
		this.found = found;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((found == null) ? 0 : found.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatchAndCount other = (MatchAndCount) obj;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (found == null) {
			if (other.found != null)
				return false;
		} else if (!found.equals(other.found))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MatchAndCount [found=" + found + ", count=" + count + "]";
	}
	
	

}
