/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionGetStatusResponse.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core.domain;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBaseResponse;
import com.currenciesdirect.gtg.compliance.sanction.util.Constants;

/**
 * The Class SanctionGetStatusResponse.
 *
 * @author manish
 */
public class SanctionGetStatusResponse extends SanctionBaseResponse implements IDomain, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Integer id;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	/** The application id. */
	private String applicationId;

	/**
	 * Instantiates a new sanction get status response.
	 */
	public SanctionGetStatusResponse() {
		this.ofacList = Constants.NOT_AVAILABLE;
		this.sanctionId = Constants.NOT_AVAILABLE;
		this.worldCheck = Constants.NOT_AVAILABLE;
	}

	/**
	 * Gets the application id.
	 *
	 * @return the application id
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 * Sets the application id.
	 *
	 * @param applicationId
	 *            the new application id
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode
	 *            the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the error description
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription
	 *            the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}
