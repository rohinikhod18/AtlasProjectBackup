package com.currenciesdirect.gtg.compliance.commons.domain.base;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class BaseResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse extends ServiceMessageResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Enum DECISION.
	 */
	public enum DECISION {

		/** The success. */
		SUCCESS,
		/** The fail. */
		FAIL
	}

	/** The decision. */
	@JsonIgnore
	private DECISION decision = DECISION.FAIL;

	/** The response code. */
	@ApiModelProperty(value = "The response code", example = "000", required = true)
	protected String responseCode;

	/** The response description. */
	@ApiModelProperty(value = "The response description", example = "All checks performed successfully", required = true)
	protected String responseDescription;
	
	/** The short response code. */
	@JsonIgnore
	private String shortResponseCode;//Add for AT-3470

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
	 * Gets the decision.
	 *
	 * @return the decision
	 */
	public DECISION getDecision() {
		return decision;
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
	 * Gets the short response code.
	 *
	 * @return the short response code
	 */
	public String getShortResponseCode() {
		return shortResponseCode;
	}

	/**
	 * Sets the short response code.
	 *
	 * @param shortResponseCode the new short response code
	 */
	public void setShortResponseCode(String shortResponseCode) {
		this.shortResponseCode = shortResponseCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Response [responseCode=" + responseCode + ", responseDescription=" + responseDescription + "]";
	}

}
