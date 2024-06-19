package com.currenciesdirect.gtg.compliance.core.domain.report;

import com.currenciesdirect.gtg.compliance.core.domain.BaseSearchCriteria;
/**
 * 
 * The class RegistrationReportSearchCriteria
 *
 */
public class RegistrationReportSearchCriteria extends BaseSearchCriteria{

	/** The filter. */
	private RegistrationReportFilter filter;

	private Boolean isFromClearFilter = Boolean.FALSE;
	

	public RegistrationReportFilter getFilter() {
		return filter;
	}

	public void setFilter(RegistrationReportFilter filter) {
		this.filter = filter;
	}

	
	/**
	 * Gets the checks if is from clear filter.
	 *
	 * @return the checks if is from clear filter
	 */
	public Boolean getIsFromClearFilter() {
		return isFromClearFilter;
	}

	/**
	 * Sets the checks if is from clear filter.
	 *
	 * @param isFromClearFilter the new checks if is from clear filter
	 */
	public void setIsFromClearFilter(Boolean isFromClearFilter) {
		this.isFromClearFilter = isFromClearFilter;
	}

}
