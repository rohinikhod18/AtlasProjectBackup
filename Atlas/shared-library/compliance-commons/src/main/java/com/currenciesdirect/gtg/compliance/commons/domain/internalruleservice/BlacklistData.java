package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;

/**
 * The Class BlacklistData.
 * 
 * @author Rajesh
 */
public class BlacklistData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The type. */
	private String type;

	/** The value. */
	private String value;
	
	/** The request type. is added to resolve AT-1198*/
	private String requestType;
	
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlacklistData other = (BlacklistData) obj;
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
		return "BlacklistData [type=" + type + ", value=" + value + "]";
	}

}
