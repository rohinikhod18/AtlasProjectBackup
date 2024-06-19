package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import java.util.Arrays;

import com.currenciesdirect.gtg.compliance.core.domain.BaseFilter;

public class PaymentOutFilter extends BaseFilter {

	/** The country of beneficiary. */
	private String[] countryOfBeneficiary;

	private String valueDateFrom;

	private String valueDateTo;

	private String[] paymentStatus;

	/**
	 * @return the countryOfBeneficiary
	 */
	public String[] getCountryOfBeneficiary() {
		return countryOfBeneficiary;
	}

	/**
	 * @param countryOfBeneficiary
	 *            the countryOfBeneficiary to set
	 */
	public void setCountryOfBeneficiary(String[] countryOfBeneficiary) {
		this.countryOfBeneficiary = countryOfBeneficiary;
	}

	public String getValueDateFrom() {
		return valueDateFrom;
	}

	public void setValueDateFrom(String valueDateFrom) {
		this.valueDateFrom = valueDateFrom;
	}

	public String getValueDateTo() {
		return valueDateTo;
	}

	public void setValueDateTo(String valueDateTo) {
		this.valueDateTo = valueDateTo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(countryOfBeneficiary);
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentOutFilter other = (PaymentOutFilter) obj;
		return (!Arrays.equals(countryOfBeneficiary, other.countryOfBeneficiary));
	}

	@Override
	public String toString() {
		return "PaymentOutFilter [countryOfBeneficiary=" + Arrays.toString(countryOfBeneficiary) + "]";
	}

	/**
	 * @return the paymentStatus
	 */
	public String[] getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus
	 *            the paymentStatus to set
	 */
	public void setPaymentStatus(String[] paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
