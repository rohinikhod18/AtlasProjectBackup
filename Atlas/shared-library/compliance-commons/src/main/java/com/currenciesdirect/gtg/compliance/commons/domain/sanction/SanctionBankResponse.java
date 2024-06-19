 /*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionBankResponse.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;

/**
 * The Class SanctionBankResponse.
 *
 * @author manish
 */
public class SanctionBankResponse extends SanctionBaseResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The bank ID. */
	private Integer bankID;
	
	/** The error code. */
	private String errorCode;
	
	/** The error description. */
	private String errorDescription;
	
	/** The provider name. */
	private String providerName;
	
	/** The provider method. */
	private String providerMethod;
	
	
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
	 * Gets the bank ID.
	 *
	 * @return the bank ID
	 */
	public Integer getBankID() {
		return bankID;
	}

	/**
	 * Sets the bank ID.
	 *
	 * @param bankID the new bank ID
	 */
	public void setBankID(Integer bankID) {
		this.bankID = bankID;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SanctionBankResponse [bankID=" + bankID + ", errorCode=" + errorCode + ", errorDescription="
				+ errorDescription + ", providerName=" + providerName + ", providerMethod=" + providerMethod + "]";
	}
}
