package com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class RiskScore.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "message", "RGID", "tScore", "tRisk", "extendedResponse" })
public class RiskScore implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	@ApiModelProperty(value = "The risk guardian message id", example = "1270152990", required = true)
	@JsonProperty("message")
	private String message;

	/** The r GID. */
	@ApiModelProperty(value = "The risk guardian id", example = "1269511249", required = true)
	@JsonProperty("RGID")
	private String rGID;

	/** The t score. */
	@ApiModelProperty(value = "The card score from risk guardian", example = "42.05", required = true)
	@JsonProperty("tScore")
	private Double tScore;

	/** The t risk. */
	@ApiModelProperty(value = "The card threshold number", example = "60.0", required = true)
	@JsonProperty("tRisk")
	private Double tRisk;

	/** The extended response. */
	@ApiModelProperty(value = "An extended risk guardian response", example = "CRE^Card (3), [b1-s1-c3-e1-p1-n1-i10]~CRETitle^CrossRef Global(Email)}", required = true)
	@JsonProperty("extendedResponse")
	private String extendedResponse;


	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the rgid.
	 *
	 * @return the rgid
	 */
	@JsonProperty("RGID")
	public String getRGID() {
		return rGID;
	}

	/**
	 * Sets the rgid.
	 *
	 * @param rGID
	 *            the new rgid
	 */
	@JsonProperty("RGID")
	public void setRGID(String rGID) {
		this.rGID = rGID;
	}

	/**
	 * Gets the t score.
	 *
	 * @return the t score
	 */
	@JsonProperty("tScore")
	public Double getTScore() {
		return tScore;
	}

	/**
	 * Sets the t score.
	 *
	 * @param tScore
	 *            the new t score
	 */
	@JsonProperty("tScore")
	public void setTScore(Double tScore) {
		this.tScore = tScore;
	}

	/**
	 * Gets the t risk.
	 *
	 * @return the t risk
	 */
	@JsonProperty("tRisk")
	public Double getTRisk() {
		return tRisk;
	}

	/**
	 * Sets the t risk.
	 *
	 * @param tRisk
	 *            the new t risk
	 */
	@JsonProperty("tRisk")
	public void setTRisk(Double tRisk) {
		this.tRisk = tRisk;
	}

	/**
	 * Gets the extended response.
	 *
	 * @return the extended response
	 */
	@JsonProperty("extendedResponse")
	public String getExtendedResponse() {
		return extendedResponse;
	}

	/**
	 * Sets the extended response.
	 *
	 * @param extendedResponse
	 *            the new extended response
	 */
	@JsonProperty("extendedResponse")
	public void setExtendedResponse(String extendedResponse) {
		this.extendedResponse = extendedResponse;
	}



}