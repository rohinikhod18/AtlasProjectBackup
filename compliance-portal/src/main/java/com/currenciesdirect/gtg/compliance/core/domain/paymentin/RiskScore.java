package com.currenciesdirect.gtg.compliance.core.domain.paymentin;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "message", "RGID", "tScore", "tRisk", "extendedResponse" })
public class RiskScore implements Serializable {

	@JsonProperty("message")
	private String message;
	@JsonProperty("RGID")
	private String rGID;
	@JsonProperty("tScore")
	private Double tScore;
	@JsonProperty("tRisk")
	private Integer tRisk;
	@JsonProperty("extendedResponse")
	private String extendedResponse;
	private static final long serialVersionUID = 1L;

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("RGID")
	public String getRGID() {
		return rGID;
	}

	@JsonProperty("RGID")
	public void setRGID(String rGID) {
		this.rGID = rGID;
	}

	@JsonProperty("tScore")
	public Double getTScore() {
		return tScore;
	}

	@JsonProperty("tScore")
	public void setTScore(Double tScore) {
		this.tScore = tScore;
	}

	@JsonProperty("tRisk")
	public Integer getTRisk() {
		return tRisk;
	}

	@JsonProperty("tRisk")
	public void setTRisk(Integer tRisk) {
		this.tRisk = tRisk;
	}

	@JsonProperty("extendedResponse")
	public String getExtendedResponse() {
		return extendedResponse;
	}

	@JsonProperty("extendedResponse")
	public void setExtendedResponse(String extendedResponse) {
		this.extendedResponse = extendedResponse;
	}
}