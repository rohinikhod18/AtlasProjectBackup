package com.currenciesdirect.gtg.compliance.core.loadcache;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FraudSightProviderProperty.
 */
public class FraudSightProviderProperty {
	
	/** The request type. */
	@JsonProperty("requestType")
	private String requestType;
	
	/** The low risk threshold. */
	@JsonProperty("low-risk")
	private Double lowRiskThreshold;
	
	/** The review threshold. */
	@JsonProperty("review")
	private Double reviewThreshold;
	
	/** The high risk threshold. */
	@JsonProperty("high-risk")
	private Double highRiskThreshold;

	/**
	 * @return the lowRiskThreshold
	 */
	public Double getLowRiskThreshold() {
		return lowRiskThreshold;
	}

	/**
	 * @param lowRiskThreshold the lowRiskThreshold to set
	 */
	public void setLowRiskThreshold(Double lowRiskThreshold) {
		this.lowRiskThreshold = lowRiskThreshold;
	}

	/**
	 * @return the reviewThreshold
	 */
	public Double getReviewThreshold() {
		return reviewThreshold;
	}

	/**
	 * @param reviewThreshold the reviewThreshold to set
	 */
	public void setReviewThreshold(Double reviewThreshold) {
		this.reviewThreshold = reviewThreshold;
	}

	/**
	 * @return the highRiskThreshold
	 */
	public Double getHighRiskThreshold() {
		return highRiskThreshold;
	}

	/**
	 * @param highRiskThreshold the highRiskThreshold to set
	 */
	public void setHighRiskThreshold(Double highRiskThreshold) {
		this.highRiskThreshold = highRiskThreshold;
	}

	
}
