 /*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionBeneficiaryResponse.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;

/**
 * The Class SanctionBeneficiaryResponse.
 *
 * @author manish
 */
public class SanctionBeneficiaryResponse extends SanctionBaseResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The beneficiary id. */
	private Integer beneficiaryId;
	
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
	 * Gets the beneficiary id.
	 *
	 * @return the beneficiary id
	 */
	public Integer getBeneficiaryId() {
		return beneficiaryId;
	}

	/**
	 * Sets the beneficiary id.
	 *
	 * @param beneficiaryId the new beneficiary id
	 */
	public void setBeneficiaryId(Integer beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
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
		return "SanctionBeneficiaryResponse [beneficiaryId=" + beneficiaryId + ", errorCode=" + errorCode
				+ ", errorDescription=" + errorDescription + ", providerName=" + providerName + ", providerMethod="
				+ providerMethod + "]";
	}

}
