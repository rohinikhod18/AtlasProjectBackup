package com.currenciesdirect.gtg.compliance.core.domain.paymentin;

import com.currenciesdirect.gtg.compliance.core.domain.BaseSearchCriteria;

public class PaymentInSearchCriteria extends BaseSearchCriteria {	
	
	private PaymentInFilter filter;

	public PaymentInFilter getFilter() {
		return filter;
	}

	
	public void setFilter(PaymentInFilter filter) {
		this.filter = filter;
	}	

}
