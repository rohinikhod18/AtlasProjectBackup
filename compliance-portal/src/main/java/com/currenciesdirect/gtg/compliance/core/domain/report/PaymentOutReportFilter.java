package com.currenciesdirect.gtg.compliance.core.domain.report;

import java.util.Arrays;

import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutFilter;

public class PaymentOutReportFilter extends PaymentOutFilter {

	/** The watchListReason. */
	private String[] watchListReason;

	/**
	 * Instantiates a new payment out report filter.
	 */
	public PaymentOutReportFilter() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(owner);
		result = prime * result + Arrays.hashCode(watchListReason);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentOutReportFilter other = (PaymentOutReportFilter) obj;
		if (!Arrays.equals(owner, other.owner))
			return false;
		return (!Arrays.equals(watchListReason, other.watchListReason));
	}

}
