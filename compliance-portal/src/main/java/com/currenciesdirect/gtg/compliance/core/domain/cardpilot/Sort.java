package com.currenciesdirect.gtg.compliance.core.domain.cardpilot;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class Sort.
 * AT-4625
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sort implements Serializable{
	
	/** The field name */
	@JsonProperty(value = "field_name")
	private String fieldName;
	
	/** The is ascend */
	@JsonProperty(value = "is_ascend")
	private Boolean isAscend;

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @return the isAscend
	 */
	public Boolean getIsAscend() {
		return isAscend;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @param isAscend the isAscend to set
	 */
	public void setIsAscend(Boolean isAscend) {
		this.isAscend = isAscend;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sort [fieldName=");
		builder.append(fieldName);
		builder.append(", isAscend=");
		builder.append(isAscend);
		builder.append("]");
		return builder.toString();
	}
	
}
