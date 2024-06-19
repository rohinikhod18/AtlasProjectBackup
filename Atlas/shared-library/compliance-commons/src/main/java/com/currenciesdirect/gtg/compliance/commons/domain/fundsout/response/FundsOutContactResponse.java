package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class FundsOutContactResponse.
 *
 * @author bnt
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"responseCode",
"responseDescription",
"trade_contact_id",
"contact_cs"
})
public class FundsOutContactResponse implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The response code. */
	@JsonProperty("responseCode")
	private String responseCode;
	
	/** The response description. */
	@JsonProperty("responseDescription")
	private String responseDescription;
	
	/** The trade contact id. */
	@JsonProperty("trade_contact_id")
	private Integer tradeContactId;
	
	/** The contact cs. */
	@JsonProperty("contact_cs")
	private String contactCs;
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
	 * Gets the trade contact id.
	 *
	 * @return The tradeContactId
	 */
	@JsonProperty("trade_contact_id")
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * Sets the trade contact id.
	 *
	 * @param tradeContactId            The Trade_Contact_Id
	 */
	@JsonProperty("trade_contact_id")
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	/**
	 * Os.
	 *
	 * @return The contactCs
	 */
	@JsonProperty("contact_cs")
	public String getContactCs() {
		return contactCs;
	}

	/**
	 * Sets the contact cs.
	 *
	 * @param contactCs            The contact_cs
	 */
	@JsonProperty("contact_cs")
	public void setContactCs(String contactCs) {
		this.contactCs = contactCs;
	}

}