package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class BeneficiaryResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
	"responseCode",
	"responseDescription",
	"bene_sanc_id",
	"bene_cs"
})
public class BeneficiaryResponse implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The response code. */
	@JsonProperty("responseCode")
	private String responseCode;
	
	/** The response description. */
	@JsonProperty("responseDescription")
	private String responseDescription;
	
	/** The bene sanc id. */
	@JsonProperty("bene_sanc_id")
	private Integer beneSancId;
	
	/** The bene cs. */
	@JsonProperty("bene_cs")
	private String beneCs;

	/**
	 * Gets the response code.
	 *
	 * @return The responseCode
	 */
	@JsonProperty("responseCode")
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * Sets the response code.
	 *
	 * @param responseCode            The responseCode
	 */
	@JsonProperty("responseCode")
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * Gets the response description.
	 *
	 * @return The responseDescription
	 */
	@JsonProperty("responseDescription")
	public String getResponseDescription() {
		return responseDescription;
	}

	/**
	 * Sets the response description.
	 *
	 * @param responseDescription            The responseDescription
	 */
	@JsonProperty("responseDescription")
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	/**
	 * Gets the bene sanc id.
	 *
	 * @return The beneSancId
	 */
	@JsonProperty("bene_sanc_id")
	public Integer getBeneSancId() {
		return beneSancId;
	}

	/**
	 * Sets the bene sanc id.
	 *
	 * @param beneSancId            The bene_sanc_id
	 */
	@JsonProperty("bene_sanc_id")
	public void setBeneSancId(Integer beneSancId) {
		this.beneSancId = beneSancId;
	}

	/**
	 * Gets the bene cs.
	 *
	 * @return The beneCs
	 */
	@JsonProperty("bene_cs")
	public String getBeneCs() {
		return beneCs;
	}

	/**
	 * Sets the bene cs.
	 *
	 * @param beneCs            The bene_cs
	 */
	@JsonProperty("bene_cs")
	public void setBeneCs(String beneCs) {
		this.beneCs = beneCs;
	}

}
