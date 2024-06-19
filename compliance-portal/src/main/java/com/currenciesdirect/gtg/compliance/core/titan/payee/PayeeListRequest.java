package com.currenciesdirect.gtg.compliance.core.titan.payee;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.BaseSearchCriteria;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class PayeeListRequest.
 */
public class PayeeListRequest extends ServiceMessage {

	private static final long serialVersionUID = 1L;

	/** The source application. */
	@JsonProperty(value = "source_application")
	private String sourceApplication;

	@JsonProperty(value = "search_criteria")
	private BaseSearchCriteria baseSearchCriteria;

	public BaseSearchCriteria getBaseSearchCriteria() {
		return baseSearchCriteria;
	}

	public void setBaseSearchCriteria(BaseSearchCriteria baseSearchCriteria) {
		this.baseSearchCriteria = baseSearchCriteria;
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
