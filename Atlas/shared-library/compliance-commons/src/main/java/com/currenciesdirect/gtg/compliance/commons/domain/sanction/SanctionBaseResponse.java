/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionBaseResponse.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;

/**
 * The Class SanctionBaseResponse.
 */
public class SanctionBaseResponse  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String NOT_AVAILABLE = "Not Available";
	/** The status. */
	protected String status;

	/** The status description. */
	protected String statusDescription;

	/** The ofac list. */
	protected String ofacList;

	/** The results count. */
	protected Integer resultsCount;

	/** The pending count. */
	protected Integer pendingCount;

	/** The sanction id. */
	protected String sanctionId;

	/** The world check. */
	protected String worldCheck;

	/** The provider response. */
	protected String providerResponse;

	/**
	 * Instantiates a new sanction base response.
	 */
	public SanctionBaseResponse() {
		this.ofacList = NOT_AVAILABLE;
		this.sanctionId = NOT_AVAILABLE;
		this.worldCheck = NOT_AVAILABLE;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the status description.
	 *
	 * @return the status description
	 */
	public String getStatusDescription() {
		return statusDescription;
	}

	/**
	 * Sets the status description.
	 *
	 * @param statusDescription
	 *            the new status description
	 */
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	/**
	 * Gets the ofac list.
	 *
	 * @return the ofac list
	 */
	public String getOfacList() {
		return ofacList;
	}

	/**
	 * Sets the ofac list.
	 *
	 * @param ofacList
	 *            the new ofac list
	 */
	public void setOfacList(String ofacList) {
		this.ofacList = ofacList;
	}

	/**
	 * Gets the results count.
	 *
	 * @return the results count
	 */
	public Integer getResultsCount() {
		return resultsCount;
	}

	/**
	 * Sets the results count.
	 *
	 * @param resultsCount
	 *            the new results count
	 */
	public void setResultsCount(Integer resultsCount) {
		this.resultsCount = resultsCount;
	}

	/**
	 * Gets the pending count.
	 *
	 * @return the pending count
	 */
	public Integer getPendingCount() {
		return pendingCount;
	}

	/**
	 * Sets the pending count.
	 *
	 * @param pendingCount
	 *            the new pending count
	 */
	public void setPendingCount(Integer pendingCount) {
		this.pendingCount = pendingCount;
	}

	/**
	 * Gets the sanction id.
	 *
	 * @return the sanction id
	 */
	public String getSanctionId() {
		return sanctionId;
	}

	/**
	 * Sets the sanction id.
	 *
	 * @param sanctionId
	 *            the new sanction id
	 */
	public void setSanctionId(String sanctionId) {
		this.sanctionId = sanctionId;
	}

	/**
	 * Gets the world check.
	 *
	 * @return the world check
	 */
	public String getWorldCheck() {
		return worldCheck;
	}

	/**
	 * Sets the world check.
	 *
	 * @param worldCheck
	 *            the new world check
	 */
	public void setWorldCheck(String worldCheck) {
		this.worldCheck = worldCheck;
	}

	/**
	 * Gets the provider response.
	 *
	 * @return the provider response
	 */
	public String getProviderResponse() {
		return providerResponse;
	}

	/**
	 * Sets the provider response.
	 *
	 * @param providerResponse
	 *            the new provider response
	 */
	public void setProviderResponse(String providerResponse) {
		this.providerResponse = providerResponse;
	}

}
