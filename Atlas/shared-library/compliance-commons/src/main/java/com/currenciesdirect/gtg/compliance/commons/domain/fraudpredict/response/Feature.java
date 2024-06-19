package com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Feature implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The score */
	@JsonProperty("value")
	private String value;

	/** The featureImportance */
	@JsonProperty("featureImportance")
	private BigDecimal featureImportance;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the featureImportance
	 */
	public BigDecimal getFeatureImportance() {
		return featureImportance;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param featureImportance
	 */
	public void setFeatureImportance(BigDecimal featureImportance) {
		this.featureImportance = featureImportance;
	}
}



