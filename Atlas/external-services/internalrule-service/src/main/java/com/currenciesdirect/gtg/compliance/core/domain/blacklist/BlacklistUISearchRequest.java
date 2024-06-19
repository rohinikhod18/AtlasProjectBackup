package com.currenciesdirect.gtg.compliance.core.domain.blacklist;

import com.currenciesdirect.gtg.compliance.commons.domain.IRequest;

/**
 * The Class BlacklistUISearchRequest.
 * 
 * @author Rajesh
 */
public class BlacklistUISearchRequest implements IRequest {

	/** The type. */
	private String type;

	/** The value. */
	private String value;

	/** The correlation id. */
	private String correlationId;

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
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * Sets the correlation id.
	 *
	 * @param correlationId
	 *            the new correlation id
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
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
		BlacklistUISearchRequest other = (BlacklistUISearchRequest) obj;
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
		return "BlacklistUISearchRequest [type=" + type + ", value=" + value + "]";
	}

}
