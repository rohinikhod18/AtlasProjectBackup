package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class BankResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
	"responseCode",
	"responseDescription",
	"bank_id",
	"bank_cs"
})
public class BankResponse implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The response code. */
	@JsonProperty("responseCode")
	private String responseCode;
	
	/** The response description. */
	@JsonProperty("responseDescription")
	private String responseDescription;
	
	/** The bank id. */
	@JsonProperty("bank_id")
	private Integer bankId;
	
	/** The bank cs. */
	@JsonProperty("bank_cs")
	private String bankCs;

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
	 * Gets the bank id.
	 *
	 * @return The bankId
	 */
	@JsonProperty("bank_id")
	public Integer getBankId() {
		return bankId;
	}

	/**
	 * Sets the bank id.
	 *
	 * @param bankId            The bank_id
	 */
	@JsonProperty("bank_id")
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	/**
	 * Gets the bank cs.
	 *
	 * @return The bankCs
	 */
	@JsonProperty("bank_cs")
	public String getBankCs() {
		return bankCs;
	}

	/**
	 * Sets the bank cs.
	 *
	 * @param bankCs            The bank_cs
	 */
	@JsonProperty("bank_cs")
	public void setBankCs(String bankCs) {
		this.bankCs = bankCs;
	}
	

}