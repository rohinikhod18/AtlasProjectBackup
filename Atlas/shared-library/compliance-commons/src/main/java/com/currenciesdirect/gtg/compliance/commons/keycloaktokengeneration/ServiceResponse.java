package com.currenciesdirect.gtg.compliance.commons.keycloaktokengeneration;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Copyright 2012-2017 Currencies Direct Ltd, United Kingdom
 *
 * master-data-mgmt: ServiceRequestMessage.java
 * Last modified: 15-Feb-2017
*/

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class ServiceResponse.
 *
 * @author Rohit P.
 */
public class ServiceResponse implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The response code. */
	@JsonProperty("response_code")
	private String responseCode;

	/** The response description. */
	@JsonProperty("response_description")
	private String responseDescription;

	/** The decision. */
	@JsonIgnore
	private DECISION decision = DECISION.FAIL;

	/**
	 * The Enum DECISION.
	 */
	public enum DECISION {

		/** The success. */
		SUCCESS,
		/** The fail. */
		FAIL
	}

	/**
	 * Gets the response code.
	 *
	 * @return the response code
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * Sets the response code.
	 *
	 * @param responseCode
	 *            the new response code
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * Gets the response description.
	 *
	 * @return the response description
	 */
	public String getResponseDescription() {
		return responseDescription;
	}

	/**
	 * Sets the response description.
	 *
	 * @param responseDescription
	 *            the new response description
	 */
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	/**
	 * Sets the decision.
	 *
	 * @param decision
	 *            the new decision
	 */
	public void setDecision(DECISION decision) {
		this.decision = decision;
	}

	/**
	 * Checks if is success.
	 *
	 * @return the decision
	 */
	@JsonIgnore
	public boolean isSuccess() {
		return (decision == DECISION.SUCCESS);
	}

}
