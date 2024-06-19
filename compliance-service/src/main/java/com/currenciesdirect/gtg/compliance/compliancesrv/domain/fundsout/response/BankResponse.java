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
	"bank_id",
	"bank_cs"
})
public class BankResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("responseCode")
	private String responseCode;
	@JsonProperty("responseDescription")
	private String responseDescription;
	@JsonProperty("bank_id")
	private Integer bankId;
	@JsonProperty("bank_cs")
	private String bankCs;

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
	 * @return The bankId
	 */
	@JsonProperty("bank_id")
	public Integer getBankId() {
		return bankId;
	}

	/**
	 * 
	 * @param bankId
	 *            The bank_id
	 */
	@JsonProperty("bank_id")
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	/**
	 * 
	 * @return The bankCs
	 */
	@JsonProperty("bank_cs")
	public String getBankCs() {
		return bankCs;
	}

	/**
	 * 
	 * @param bankCs
	 *            The bank_cs
	 */
	@JsonProperty("bank_cs")
	public void setBankCs(String bankCs) {
		this.bankCs = bankCs;
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