/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionContactResponse.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;

/**
 * The Class SanctionContactResponse.
 *
 * @author manish
 */
public class SanctionContactResponse extends SanctionBaseResponse implements Serializable,IDomain {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The provider name. */
	private String providerName;
	
	/** The provider method. */
	private String providerMethod;
	
	/** The contact id. */
	private Integer contactId;
	
	/** The error code. */
	private String errorCode;
	
	/** The error description. */
	private String errorDescription;
	
	/** The is cached response. */
	private Boolean isCachedResponse;
	
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
	 * @param errorCode the new error code
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
	 * @param errorDescription the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * Gets the provider name.
	 *
	 * @return the provider name
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * Sets the provider name.
	 *
	 * @param providerName the new provider name
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * Gets the provider method.
	 *
	 * @return the provider method
	 */
	public String getProviderMethod() {
		return providerMethod;
	}

	/**
	 * Sets the provider method.
	 *
	 * @param providerMethod the new provider method
	 */
	public void setProviderMethod(String providerMethod) {
		this.providerMethod = providerMethod;
	}

	/**
	 * @return the isCachedResponse
	 */
	public Boolean getIsCachedResponse() {
		return isCachedResponse;
	}

	/**
	 * @param isCachedResponse the isCachedResponse to set
	 */
	public void setIsCachedResponse(Boolean isCachedResponse) {
		this.isCachedResponse = isCachedResponse;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SanctionContactResponse [providerName=" + providerName + ", providerMethod=" + providerMethod
				+ ", contactId=" + contactId + ", errorCode=" + errorCode + ", errorDescription=" + errorDescription
				+ "]";
	}

}
