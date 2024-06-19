package com.currenciesdirect.gtg.compliance.core.domain.report;

import com.currenciesdirect.gtg.compliance.core.domain.BaseSearchCriteria;

/**
 * The Class WorkEfficiencyReportSearchCriteria.
 */
public class WorkEfficiencyReportSearchCriteria extends BaseSearchCriteria {

	/** The work efficiency report filter.*/
	private WorkEfficiencyReportFilter filter;

	/**
	 * @return the filter
	 */
	public WorkEfficiencyReportFilter getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(WorkEfficiencyReportFilter filter) {
		this.filter = filter;
	}

	

}
