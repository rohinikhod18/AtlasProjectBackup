package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class DocumentDataComparison.
 */
public class DocumentDataComparison extends BreakdownResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The data comparison breakdown. */
	@ApiModelProperty(value = "The data comparison breakdown object", required = true) 
	@JsonProperty(value = "breakdown")
	private DocumentDataComparisonBreakdown dataComparisonBreakdown;

	/**
	 * @return the dataComparisonBreakdown
	 */
	public DocumentDataComparisonBreakdown getDataComparisonBreakdown() {
		return dataComparisonBreakdown;
	}

	/**
	 * @param dataComparisonBreakdown the dataComparisonBreakdown to set
	 */
	public void setDataComparisonBreakdown(DocumentDataComparisonBreakdown dataComparisonBreakdown) {
		this.dataComparisonBreakdown = dataComparisonBreakdown;
	}
	
}
