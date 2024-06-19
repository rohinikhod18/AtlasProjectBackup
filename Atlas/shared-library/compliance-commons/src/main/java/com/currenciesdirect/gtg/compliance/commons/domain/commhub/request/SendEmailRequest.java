package com.currenciesdirect.gtg.compliance.commons.domain.commhub.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendEmailRequest {

	/** The header. */
	@JsonProperty("header")
	private EmailHeader header;
	
	/** The payload. */
	@JsonProperty("payload")
	private EmailPayload payload;

	/**
	 * Gets the header.
	 *
	 * @return the header
	 */
	public EmailHeader getHeader() {
		return header;
	}

	/**
	 * Sets the header.
	 *
	 * @param header the new header
	 */
	public void setHeader(EmailHeader header) {
		this.header = header;
	}

	/**
	 * Gets the payload.
	 *
	 * @return the payload
	 */
	public EmailPayload getPayload() {
		return payload;
	}

	/**
	 * Sets the payload.
	 *
	 * @param payload the new payload
	 */
	public void setPayload(EmailPayload payload) {
		this.payload = payload;
	}
}
