package com.currenciesdirect.gtg.compliance.core.domain;

import java.io.Serializable;

/**
 * The Class Sort.
 */
public class Sort implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The field name */
	private String fieldName;
	
	/** The is ascend */
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
	
}
