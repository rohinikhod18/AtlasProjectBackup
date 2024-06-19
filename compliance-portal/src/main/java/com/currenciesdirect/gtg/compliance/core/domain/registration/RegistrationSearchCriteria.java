package com.currenciesdirect.gtg.compliance.core.domain.registration;

import com.currenciesdirect.gtg.compliance.core.domain.BaseSearchCriteria;

/**
 * The Class SearchCriteria.
 */
public class RegistrationSearchCriteria extends BaseSearchCriteria  {


	/** The filter. */
	private RegistrationQueueFilter filter;

	public RegistrationQueueFilter getFilter() {
		return filter;
	}

	public void setFilter(RegistrationQueueFilter filter) {
		this.filter = filter;
	}


}
