package com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutReasonCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
	"responseCode",
	"responseDescription",
	"bene_sanc_id",
	"bene_cs"
})
public class BeneficiaryResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("responseCode")
	private String responseCode;
	@JsonProperty("responseDescription")
	private String responseDescription;
	@JsonProperty("bene_sanc_id")
	private Integer beneSancId;
	@JsonProperty("bene_cs")
	private String beneCs;

	@JsonIgnore
	private FundsOutReasonCode responseReason;
	/**
	 * 
	 * @return The responseCode
	 */
	@JsonProperty("responseCode")
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * 
	 * @param responseCode
	 *            The responseCode
	 */
	@JsonProperty("responseCode")
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * 
	 * @return The responseDescription
	 */
	@JsonProperty("responseDescription")
	public String getResponseDescription() {
		return responseDescription;
	}

	/**
	 * 
	 * @param responseDescription
	 *            The responseDescription
	 */
	@JsonProperty("responseDescription")
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	/**
	 * 
	 * @return The beneSancId
	 */
	@JsonProperty("bene_sanc_id")
	public Integer getBeneSancId() {
		return beneSancId;
	}

	/**
	 * 
	 * @param beneSancId
	 *            The bene_sanc_id
	 */
	@JsonProperty("bene_sanc_id")
	public void setBeneSancId(Integer beneSancId) {
		this.beneSancId = beneSancId;
	}

	/**
	 * 
	 * @return The beneCs
	 */
	@JsonProperty("bene_cs")
	public String getBeneCs() {
		return beneCs;
	}

	/**
	 * 
	 * @param beneCs
	 *            The bene_cs
	 */
	@JsonProperty("bene_cs")
	public void setBeneCs(String beneCs) {
		this.beneCs = beneCs;
	}

	/**
	 * @return
	 */
	public FundsOutReasonCode getResponseReason() {
		return responseReason;
	}

	
	/**
	 * @param responseReason
	 */
	public void setResponseReason(FundsOutReasonCode responseReason) {
		this.responseReason = responseReason;
		this.responseCode = responseReason.getFundsOutReasonCode();
		this.responseDescription = responseReason.getFundsOutReasonDescription();
	}

}
