/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;

/**
 * The Class TransactionMonitoringProviderProperty.
 */
public class TransactionMonitoringProviderProperty extends ProviderProperty {

	/** The authorization. */
	private String authorization;

	/**
	 * Gets the authorization.
	 *
	 * @return the authorization
	 */
	public String getAuthorization() {
		return authorization;
	}

	/**
	 * Sets the authorization.
	 *
	 * @param authorization the new authorization
	 */
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

}
