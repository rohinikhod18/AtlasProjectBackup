package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FxTicketRequestPayload.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FxTicketRequestPayload implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The titan account number. */
	@JsonProperty("titan_account_number")
	private String titanAccountNumber;

	/** The organization code. */
	@JsonProperty("org_code")
	private String organizationCode;

	/** The titan status. */
	@JsonProperty("fx_status")
	private String fxStatus;

	/** The fx ticket id. */
	@JsonProperty("fx_ticket_id")
	private String fxTicketId;

	/** The request source. */
	@JsonProperty("request_source")
	private String requestSource;

	/**
	 * Gets the titan account number.
	 *
	 * @return the titanAccountNumber
	 */
	public String getTitanAccountNumber() {
		return titanAccountNumber;
	}

	/**
	 * Sets the titan account number.
	 *
	 * @param titanAccountNumber
	 *            the titanAccountNumber to set
	 */
	public void setTitanAccountNumber(String titanAccountNumber) {
		this.titanAccountNumber = titanAccountNumber;
	}

	/**
	 * Gets the organization code.
	 *
	 * @return the organizationCode
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * Sets the organization code.
	 *
	 * @param organizationCode
	 *            the organizationCode to set
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * Gets the fx status.
	 *
	 * @return the fxStatus
	 */
	public String getFxStatus() {
		return fxStatus;
	}

	/**
	 * Sets the fx status.
	 *
	 * @param fxStatus
	 *            the fxStatus to set
	 */
	public void setFxStatus(String fxStatus) {
		this.fxStatus = fxStatus;
	}

	/**
	 * Gets the fx ticket id.
	 *
	 * @return the fx ticket id
	 */
	public String getFxTicketId() {
		return fxTicketId;
	}

	/**
	 * Sets the fx ticket id.
	 *
	 * @param fxTicketId
	 *            the new fx ticket id
	 */
	public void setFxTicketId(String fxTicketId) {
		this.fxTicketId = fxTicketId;
	}

	/**
	 * Gets the request source.
	 *
	 * @return the request source
	 */
	public String getRequestSource() {
		return requestSource;
	}

	/**
	 * Sets the request source.
	 *
	 * @param requestSource
	 *            the new request source
	 */
	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FxTicketRequestPayload [titanAccountNumber=");
		builder.append(titanAccountNumber);
		builder.append(", organizationCode=");
		builder.append(organizationCode);
		builder.append(", fxStatus=");
		builder.append(fxStatus);
		builder.append(", fxTicketId=");
		builder.append(fxTicketId);
		builder.append(", requestSource=");
		builder.append(requestSource);
		builder.append(']');
		return builder.toString();
	}
}
