package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class DocumentNumber.
 */
public class DocumentNumber implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The type. */
	@ApiModelProperty(value = "The type", required = true)
	@JsonProperty(value = "type")
	private String type;
	
	/** The value. */
	@ApiModelProperty(value = "The value", required = true)
	@JsonProperty(value = "value")
	private String value;
}
