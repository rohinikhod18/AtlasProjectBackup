package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DocumentDataValidation extends BreakdownResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The data validation breakdown. */
	@ApiModelProperty(value = "The data validation breakdown object", required = true)
	@JsonProperty(value = "breakdown")
	private DocumentDataValidationBreakdown dataValidationBreakdown;

	/**
	 * @return the dataValidationBreakdown
	 */
	public DocumentDataValidationBreakdown getDataValidationBreakdown() {
		return dataValidationBreakdown;
	}

	/**
	 * @param dataValidationBreakdown the dataValidationBreakdown to set
	 */
	public void setDataValidationBreakdown(DocumentDataValidationBreakdown dataValidationBreakdown) {
		this.dataValidationBreakdown = dataValidationBreakdown;
	}
	
}
