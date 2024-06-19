package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class FraugsterResponse.
 */
public class FraugsterResponse extends FraugsterBaseResponse implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	private String orgCode;

	/** The correlation id. */
	private String correlationId;

	/** The contact responses. */
	private List<FraugsterContactResponse> contactResponses = new ArrayList<>();

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Sets the correlation id.
	 *
	 * @param correlationId
	 *            the new correlation id
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * Gets the contact responses.
	 *
	 * @return the contact responses
	 */
	public List<FraugsterContactResponse> getContactResponses() {
		return contactResponses;
	}

	
	/**
	 * Sets the contact responses.
	 *
	 * @param contactResponses
	 *            the new contact responses
	 */
	public void setContactResponses(List<FraugsterContactResponse> contactResponses) {
		this.contactResponses = contactResponses;
	}

	
	/**
	 * Gets the fraugster contact response by id.
	 *
	 * @param conatctId
	 *            the conatct id
	 * @return the sanction contact response by id
	 */
	public FraugsterContactResponse getFraugsterContactResponseById(Integer conatctId) {
		for (FraugsterContactResponse fResponse : contactResponses) {
			if (fResponse.getContactId().equals(conatctId)) {
				return fResponse;
			}
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SanctionResponse [orgCode=" + orgCode + ", correlationId=" + correlationId + ", contactResponses="
				+ contactResponses + ", providerResponse=" + providerResponse + "]";
	}
	
}