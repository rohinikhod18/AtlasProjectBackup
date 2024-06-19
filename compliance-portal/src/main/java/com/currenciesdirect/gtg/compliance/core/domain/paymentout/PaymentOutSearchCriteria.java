package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.currenciesdirect.gtg.compliance.core.domain.BaseSearchCriteria;

/**
 * The Class PaymentOutSearchCriteria.
 */
public class PaymentOutSearchCriteria extends BaseSearchCriteria {

	/** The payment out filter.*/
	private PaymentOutFilter filter;

	/**
	 * @return the filter
	 */
	public PaymentOutFilter getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(PaymentOutFilter filter) {
		this.filter = filter;
	}

}
