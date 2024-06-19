package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FxTicketFilter.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FxTicketFilter implements Serializable{

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The target organization. */
	@JsonProperty(value = "organization")
	private List<String> organization;

	/**
	 * Gets the organization.
	 *
	 * @return the organization
	 */
	public List<String> getOrganization() {
		return organization;
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization
	 *            the new organization
	 */
	public void setOrganization(List<String> organization) {
		this.organization = organization;
	}
}
