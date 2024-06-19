package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

/**
 * The Class KycDetails.
 * 
 * @author abhijeetg
 */
public class KycDetails {

	/** The kyc. */
	private List<Kyc> kyc;

	/** The pass count. */
	private Integer passCount;

	/** The fail count. */
	private Integer failCount;

	/** The kyc total records. */
	private Integer kycTotalRecords;

	/**
	 * Gets the kyc.
	 *
	 * @return the kyc
	 */
	public List<Kyc> getKyc() {
		return kyc;
	}

	/**
	 * Sets the kyc.
	 *
	 * @param kyc
	 *            the new kyc
	 */
	public void setKyc(List<Kyc> kyc) {
		this.kyc = kyc;
	}

	/**
	 * Gets the pass count.
	 *
	 * @return the pass count
	 */
	public Integer getPassCount() {
		return passCount;
	}

	/**
	 * Sets the pass count.
	 *
	 * @param passCount
	 *            the new pass count
	 */
	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	/**
	 * Gets the fail count.
	 *
	 * @return the fail count
	 */
	public Integer getFailCount() {
		return failCount;
	}

	/**
	 * Sets the fail count.
	 *
	 * @param failCount
	 *            the new fail count
	 */
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	/**
	 * Gets the kyc total records.
	 *
	 * @return the kyc total records
	 */
	public Integer getKycTotalRecords() {
		return kycTotalRecords;
	}

	/**
	 * Sets the kyc total records.
	 *
	 * @param kycTotalRecords
	 *            the new kyc total records
	 */
	public void setKycTotalRecords(Integer kycTotalRecords) {
		this.kycTotalRecords = kycTotalRecords;
	}

}
