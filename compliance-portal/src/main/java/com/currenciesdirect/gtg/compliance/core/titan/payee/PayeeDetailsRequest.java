package com.currenciesdirect.gtg.compliance.core.titan.payee;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class PayeeDetailsRequest.
 */
public class PayeeDetailsRequest extends ServiceMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The payee. */
	private WrapperPayee payee;

	/** The source application. */
	@JsonProperty(value = "source_application")
	private String sourceApplication;

	/**
	 * Gets the payee.
	 *
	 * @return the payee
	 */
	public WrapperPayee getPayee() {
		return payee;
	}

	/**
	 * Sets the payee.
	 *
	 * @param payee
	 *            the new payee
	 */
	public void setPayee(WrapperPayee payee) {
		this.payee = payee;
	}

	/**
	 * Gets the source application.
	 *
	 * @return the source application
	 */
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication
	 *            the new source application
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

}
