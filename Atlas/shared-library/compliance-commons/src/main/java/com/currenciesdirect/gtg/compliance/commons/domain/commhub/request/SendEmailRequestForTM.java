package com.currenciesdirect.gtg.compliance.commons.domain.commhub.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendEmailRequestForTM {
	
	/** The header. */
	@JsonProperty("header")
	private EmailHeaderForTM tmHeader;
	
	/** The payload. */
	@JsonProperty("payload")
	private EmailPayloadForTM tmPayload;

	/**
	 * @return the tmHeader
	 */
	public EmailHeaderForTM getTmHeader() {
		return tmHeader;
	}

	/**
	 * @param tmHeader the tmHeader to set
	 */
	public void setTmHeader(EmailHeaderForTM tmHeader) {
		this.tmHeader = tmHeader;
	}

	/**
	 * @return the tmPayload
	 */
	public EmailPayloadForTM getTmPayload() {
		return tmPayload;
	}

	/**
	 * @param tmPayload the tmPayload to set
	 */
	public void setTmPayload(EmailPayloadForTM tmPayload) {
		this.tmPayload = tmPayload;
	}
	
}
