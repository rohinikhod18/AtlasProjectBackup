package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ImageIntegrity.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageIntegrity extends BreakdownResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The image integrity breakdown. */
	@ApiModelProperty(value = "The image integrity breakdown", required = true)
	@JsonProperty(value = "breakdown")
	private DocumentImageIntegrityBreakdown imageIntegrityBreakdown;

	/**
	 * @return the imageIntegrityBreakdown
	 */
	public DocumentImageIntegrityBreakdown getImageIntegrityBreakdown() {
		return imageIntegrityBreakdown;
	}

	/**
	 * @param imageIntegrityBreakdown the imageIntegrityBreakdown to set
	 */
	public void setImageIntegrityBreakdown(DocumentImageIntegrityBreakdown imageIntegrityBreakdown) {
		this.imageIntegrityBreakdown = imageIntegrityBreakdown;
	}
}
