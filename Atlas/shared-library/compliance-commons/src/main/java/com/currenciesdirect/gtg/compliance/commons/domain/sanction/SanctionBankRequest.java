/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;

/**
 * The Class SanctionBankRequest.
 *
 * @author manish
 */
public class SanctionBankRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The sanction id. */
	private String sanctionId;

	/** The bank name. */
	private String bankName;

	/** The country. */
	private String country;

	/** The bank id. */
	private Integer bankId;

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
	 * Gets the bank id.
	 *
	 * @return the bank id
	 */
	public Integer getBankId() {
		return bankId;
	}

	/**
	 * Sets the bank id.
	 *
	 * @param bankId
	 *            the new bank id
	 */
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

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
	 * Gets the sanction id.
	 *
	 * @return the sanction id
	 */
	public String getSanctionId() {
		return sanctionId;
	}

	/**
	 * Gets the bank name.
	 *
	 * @return the bank name
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the sanction id.
	 *
	 * @param sanctionId
	 *            the new sanction id
	 */
	public void setSanctionId(String sanctionId) {
		this.sanctionId = sanctionId;
	}

	/**
	 * Sets the bank name.
	 *
	 * @param bankName
	 *            the new bank name
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * Sets the country.
	 *
	 * @param country
	 *            the new country
	 */
	public void setCountry(String country) {
		this.country = country;
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

}
