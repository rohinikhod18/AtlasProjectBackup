package com.currenciesdirect.gtg.compliance.core.domain.kyc;

/**
 * The Class KYCSummary.
 */
public class KYCSummary {

	/** The eid check. */
	private Boolean eidCheck;
	/** The verifiaction result. */
	private String verifiactionResult;

	/** The reference id. */
	private String referenceId;

	/** The status. */
	private String status;

	private String dob;

	private String checkedOn;

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCheckedOn() {
		return checkedOn;
	}

	public void setCheckedOn(String checkedOn) {
		this.checkedOn = checkedOn;
	}

	public Boolean getEidCheck() {
		return eidCheck;
	}

	public void setEidCheck(Boolean eidCheck) {
		this.eidCheck = eidCheck;
	}

	/**
	 * Gets the verifiaction result.
	 *
	 * @return the verifiaction result
	 */
	public String getVerifiactionResult() {
		return verifiactionResult;
	}

	/**
	 * Sets the verifiaction result.
	 *
	 * @param verifiactionResult
	 *            the new verifiaction result
	 */
	public void setVerifiactionResult(String verifiactionResult) {
		this.verifiactionResult = verifiactionResult;
	}

	/**
	 * Gets the reference id.
	 *
	 * @return the reference id
	 */
	public String getReferenceId() {
		return referenceId;
	}

	/**
	 * Sets the reference id.
	 *
	 * @param referenceId
	 *            the new reference id
	 */
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
