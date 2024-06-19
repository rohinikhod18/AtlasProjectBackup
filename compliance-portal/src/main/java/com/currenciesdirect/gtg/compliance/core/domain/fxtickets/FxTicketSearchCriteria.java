package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FxTicketSearchCriteria.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FxTicketSearchCriteria extends BaseSearchCriteria {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The fx ticket filter. */
	@JsonProperty(value = "filter")
	private FxTicketFilter fxTicketFilter;
	
	/** The request source. */
	@JsonProperty(value = "request_source")
	private String requestSource;

	/**
	 * Gets the fx ticket filter.
	 *
	 * @return the fx ticket filter
	 */
	public FxTicketFilter getFxTicketFilter() {
		return fxTicketFilter;
	}

	/**
	 * Sets the fx ticket filter.
	 *
	 * @param fxTicketFilter the new fx ticket filter
	 */
	public void setFxTicketFilter(FxTicketFilter fxTicketFilter) {
		this.fxTicketFilter = fxTicketFilter;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.titan.portal.domain.common.BaseSearchCriteria#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FxTicketSearchCriteria [fxTicketFilter=");
		builder.append(fxTicketFilter);
		builder.append(']');
		return builder.toString();
	}

	/**
	 * Gets the request source.
	 *
	 * @return the requestSource
	 */
	public String getRequestSource() {
		return requestSource;
	}

	/**
	 * Sets the request source.
	 *
	 * @param requestSource the requestSource to set
	 */
	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}

}
