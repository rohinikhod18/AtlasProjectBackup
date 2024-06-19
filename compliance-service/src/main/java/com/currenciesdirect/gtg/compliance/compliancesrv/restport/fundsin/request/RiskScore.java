package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsin.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

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
	private Double tRisk;
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
	public Double getTRisk() {
		return tRisk;
	}

	@JsonProperty("tRisk")
	public void setTRisk(Double tRisk) {
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(message).append(rGID).append(tScore).append(tRisk).append(extendedResponse)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof RiskScore)) {
			return false;
		}
		RiskScore rhs = ((RiskScore) other);
		return new EqualsBuilder().append(message, rhs.message).append(rGID, rhs.rGID).append(tScore, rhs.tScore)
				.append(tRisk, rhs.tRisk).append(extendedResponse, rhs.extendedResponse).isEquals();
	}

}