package com.currenciesdirect.gtg.compliance.blacklist.core.domain;

/**
 * The Class BlacklistUpdateData.
 *
 * @author Rajesh
 */
public class BlacklistUpdateData {

	/** The type. */
	private String type;
	
	/** The original value. */
	private String originalValue;
	
	/** The new value. */
	private String newValue;

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
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the original value.
	 *
	 * @return the original value
	 */
	public String getOriginalValue() {
		return originalValue;
	}

	/**
	 * Sets the original value.
	 *
	 * @param originalValue the new original value
	 */
	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}

	/**
	 * Gets the new value.
	 *
	 * @return the new value
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * Sets the new value.
	 *
	 * @param newValue the new new value
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((newValue == null) ? 0 : newValue.hashCode());
		result = prime * result
				+ ((originalValue == null) ? 0 : originalValue.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		BlacklistUpdateData other = (BlacklistUpdateData) obj;
		if (newValue == null) {
			if (other.newValue != null)
				return false;
		} else if (!newValue.equals(other.newValue))
			return false;
		if (originalValue == null) {
			if (other.originalValue != null)
				return false;
		} else if (!originalValue.equals(other.originalValue))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BlacklistUpdateData [type=" + type + ", originalValue="
				+ originalValue + ", newValue=" + newValue + "]";
	}

	
}
