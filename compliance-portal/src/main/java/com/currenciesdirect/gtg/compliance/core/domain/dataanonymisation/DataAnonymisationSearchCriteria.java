package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import com.currenciesdirect.gtg.compliance.core.domain.BaseSearchCriteria;

/**
 * The Class DataAnonymisationSearchCriteria.
 */
public class DataAnonymisationSearchCriteria extends BaseSearchCriteria {
	
	/** The filter. */
	private DataAnonymisationFilter filter;

	public DataAnonymisationFilter getFilter() {
		return filter;
	}

	public void setFilter(DataAnonymisationFilter filter) {
		this.filter = filter;
	}

}
