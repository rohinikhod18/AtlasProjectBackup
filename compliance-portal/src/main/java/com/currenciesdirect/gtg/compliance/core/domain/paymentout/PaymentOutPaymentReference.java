package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

/**
 * The Class PaymentOutPaymentReference.
 */
public class PaymentOutPaymentReference {
	
	/** The checked on. */
	private String checkedOn;
	
	/** The payment reference. */
	private String paymentReference;
	
	/** The matched keyword. */
	private String matchedKeyword;
	
	/** The closeness score. */
	private int closenessScore;
	
	/** The overall status. */
	private String overallStatus;
	
	/** The total records. */
	private Integer totalRecords;
	
	/** The is required. */
	private Boolean isRequired;
	
	/** The pass count. */
	private Integer passCount;
	
	/** The fail count. */
	private Integer failCount;
	
	/**
	 * Gets the total records.
	 *
	 * @return the total records
	 */
	public Integer getTotalRecords() {
		return totalRecords;
	}

	/**
	 * Sets the total records.
	 *
	 * @param totalRecords the new total records
	 */
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
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
	 * @param passCount the new pass count
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
	 * @param failCount the new fail count
	 */
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	/**
	 * Gets the checked on.
	 *
	 * @return the checked on
	 */
	public String getCheckedOn() {
		return checkedOn;
	}

	/**
	 * Sets the checked on.
	 *
	 * @param checkedOn the new checked on
	 */
	public void setCheckedOn(String checkedOn) {
		this.checkedOn = checkedOn;
	}

	/**
	 * Gets the payment reference.
	 *
	 * @return the payment reference
	 */
	public String getPaymentReference() {
		return paymentReference;
	}

	/**
	 * Sets the payment reference.
	 *
	 * @param paymentReference the new payment reference
	 */
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	/**
	 * Gets the matched keyword.
	 *
	 * @return the matched keyword
	 */
	public String getMatchedKeyword() {
		return matchedKeyword;
	}

	/**
	 * Sets the matched keyword.
	 *
	 * @param matchedKeyword the new matched keyword
	 */
	public void setMatchedKeyword(String matchedKeyword) {
		this.matchedKeyword = matchedKeyword;
	}

	/**
	 * Gets the closeness score.
	 *
	 * @return the closeness score
	 */
	public int getClosenessScore() {
		return closenessScore;
	}

	/**
	 * Sets the closeness score.
	 *
	 * @param closenessScore the new closeness score
	 */
	public void setClosenessScore(int closenessScore) {
		this.closenessScore = closenessScore;
	}

	/**
	 * Gets the overall status.
	 *
	 * @return the overall status
	 */
	public String getOverallStatus() {
		return overallStatus;
	}

	/**
	 * Sets the overall status.
	 *
	 * @param overallStatus the new overall status
	 */
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	/**
	 * Gets the checks if is required.
	 *
	 * @return the checks if is required
	 */
	public Boolean getIsRequired() {
		return isRequired;
	}

	/**
	 * Sets the checks if is required.
	 *
	 * @param isRequired the new checks if is required
	 */
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}
	
	
}
