/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;

/**
 * The Class SanctionBeneficiaryRequest.
 *
 * @author manish
 */
public class SanctionBeneficiaryRequest extends SanctionEntity  implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The beneficiary id. */
	private Integer beneficiaryId;

	/** The is existing. */
	private Boolean isExisting;

	/** Added new Fields. */
	private String previousOfac;

	/** The previous world check. */
	private String previousWorldCheck;

	/** The previous status. */
	private String previousStatus;

	/** The category. */
	private String category;

	/**
	 * Gets the checks if is existing.
	 *
	 * @return the checks if is existing
	 */
	public Boolean getIsExisting() {
		return isExisting;
	}

	/**
	 * Sets the checks if is existing.
	 *
	 * @param isExisting
	 *            the new checks if is existing
	 */
	public void setIsExisting(Boolean isExisting) {
		this.isExisting = isExisting;
	}

	/**
	 * Gets the beneficiary id.
	 *
	 * @return the beneficiary id
	 */
	public Integer getBeneficiaryId() {
		return beneficiaryId;
	}

	/**
	 * Sets the beneficiary id.
	 *
	 * @param beneficiaryId
	 *            the new beneficiary id
	 */
	public void setBeneficiaryId(Integer beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	/**
	 * Gets the previous ofac.
	 *
	 * @return the previous ofac
	 */
	public String getPreviousOfac() {
		return previousOfac;
	}

	/**
	 * Sets the previous ofac.
	 *
	 * @param previousOfac
	 *            the new previous ofac
	 */
	public void setPreviousOfac(String previousOfac) {
		this.previousOfac = previousOfac;
	}

	/**
	 * Gets the previous world check.
	 *
	 * @return the previous world check
	 */
	public String getPreviousWorldCheck() {
		return previousWorldCheck;
	}

	/**
	 * Sets the previous world check.
	 *
	 * @param previousWorldCheck
	 *            the new previous world check
	 */
	public void setPreviousWorldCheck(String previousWorldCheck) {
		this.previousWorldCheck = previousWorldCheck;
	}

	/**
	 * Gets the previous status.
	 *
	 * @return the previous status
	 */
	public String getPreviousStatus() {
		return previousStatus;
	}

	/**
	 * Sets the previous status.
	 *
	 * @param previousStatus
	 *            the new previous status
	 */
	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category
	 *            the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SanctionBeneficiaryRequest [sanctionId=" + sanctionId + ", gender=" + gender + ", fullName=" + fullName
				+ ", country=" + country + ", dob=" + dob + "]";
	}

}
