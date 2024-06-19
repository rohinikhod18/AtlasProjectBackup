package com.currenciesdirect.gtg.compliance.core.domain.report;

import com.currenciesdirect.gtg.compliance.core.domain.BaseSearchCriteria;

public class PaymentOutReportSearchCriteria extends BaseSearchCriteria {	
	
	private PaymentOutReportFilter filter;

	public PaymentOutReportFilter getFilter() {
		return filter;
	}

	public void setFilter(PaymentOutReportFilter filter) {
		this.filter = filter;
	}
	
	
}
