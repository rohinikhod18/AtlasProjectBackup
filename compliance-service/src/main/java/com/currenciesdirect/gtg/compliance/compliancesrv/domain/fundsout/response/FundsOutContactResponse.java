package com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response;
import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutReasonCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author bnt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"responseCode",
"responseDescription",
"trade_contact_id",
"contact_cs"
})
public class FundsOutContactResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("responseCode")
	private String responseCode;
	@JsonProperty("responseDescription")
	private String responseDescription;
	@JsonProperty("trade_contact_id")
	private Integer tradeContactId;
	@JsonProperty("contact_cs")
	private String contactCs;

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
	 * @return The tradeContactId
	 */
	@JsonProperty("trade_contact_id")
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * 
	 * @param tradeContactId
	 *            The Trade_Contact_Id
	 */
	@JsonProperty("trade_contact_id")
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	/**
	 * Os
	 * @return The contactCs
	 */
	@JsonProperty("contact_cs")
	public String getContactCs() {
		return contactCs;
	}

	/**
	 * 
	 * @param contactCs
	 *            The contact_cs
	 */
	@JsonProperty("contact_cs")
	public void setContactCs(String contactCs) {
		this.contactCs = contactCs;
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