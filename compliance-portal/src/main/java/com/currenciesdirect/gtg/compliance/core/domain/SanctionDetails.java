package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

/**
 * The Class SanctionDetails.
 * 
 * @author abhijeetg
 */
public class SanctionDetails {

	/** The sanction. */
	private List<Sanction> sanction;

	/** The pass count. */
	private Integer passCount;

	/** The fail count. */
	private Integer failCount;

	/** The sanction total records. */
	private Integer sanctionTotalRecords;

	/**
	 * Gets the sanction.
	 *
	 * @return the sanction
	 */
	public List<Sanction> getSanction() {
		return sanction;
	}

	/**
	 * Sets the sanction.
	 *
	 * @param sanction
	 *            the new sanction
	 */
	public void setSanction(List<Sanction> sanction) {
		this.sanction = sanction;
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
	 * Gets the sanction total records.
	 *
	 * @return the sanction total records
	 */
	public Integer getSanctionTotalRecords() {
		return sanctionTotalRecords;
	}

	/**
	 * Sets the sanction total records.
	 *
	 * @param sanctionTotalRecords
	 *            the new sanction total records
	 */
	public void setSanctionTotalRecords(Integer sanctionTotalRecords) {
		this.sanctionTotalRecords = sanctionTotalRecords;
	}

}
