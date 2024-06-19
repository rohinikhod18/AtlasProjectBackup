/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class VisualAuthenticity.
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualAuthenticity extends BreakdownResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The data comparison breakdown. */
	@ApiModelProperty(value = "The data comparison breakdown", required = true)
	@JsonProperty(value = "breakdown")
	private DocumentVisualAuthenticityBreakdown visualAuthenticityBreakdown;

	/**
	 * @return the visualAuthenticityBreakdown
	 */
	public DocumentVisualAuthenticityBreakdown getVisualAuthenticityBreakdown() {
		return visualAuthenticityBreakdown;
	}

	/**
	 * @param visualAuthenticityBreakdown the visualAuthenticityBreakdown to set
	 */
	public void setVisualAuthenticityBreakdown(DocumentVisualAuthenticityBreakdown visualAuthenticityBreakdown) {
		this.visualAuthenticityBreakdown = visualAuthenticityBreakdown;
	}
}
