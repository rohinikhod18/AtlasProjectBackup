package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class BreakdownEntityDetails.
 */
public class BreakdownEntityDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "The result", required = true)
	@JsonProperty(value = "result")
	private String result;
	
	@ApiModelProperty(value = "The properties", required = true)
	@JsonProperty(value = "properties")
	private BreakdownEntityProperties properties;

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the properties
	 */
	public BreakdownEntityProperties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(BreakdownEntityProperties properties) {
		this.properties = properties;
	}

}
