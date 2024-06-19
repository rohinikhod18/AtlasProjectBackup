package com.currenciesdirect.gtg.compliance.core.domain.report;

import com.currenciesdirect.gtg.compliance.core.domain.BaseSearchCriteria;

public class PaymentInReportSearchCriteria extends BaseSearchCriteria {	
	
	private PaymentInReportFilter filter;	

	public PaymentInReportFilter getFilter() {
		return filter;
	}

	
	public void setFilter(PaymentInReportFilter filter) {
		this.filter = filter;
	}	

}
