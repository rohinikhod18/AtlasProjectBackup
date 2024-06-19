package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataAnonResponceStatusHeader {

	/** The code. */
	@JsonProperty("code")
	private String code;
	
	/** The description. */
	@JsonProperty("description")
	private String description;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
