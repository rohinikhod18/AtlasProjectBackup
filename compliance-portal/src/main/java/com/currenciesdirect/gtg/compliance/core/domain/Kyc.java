package com.currenciesdirect.gtg.compliance.core.domain;
import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
/**
 * The Class Kyc.
 */
public class Kyc implements IDomain {

	/** The id. */
	private Integer id;
	
	/** The entity type. */
	private String entityType;
	
	/** The checked on. */
	private String checkedOn;

	/** The eid check. */
	private Boolean eidCheck;

	/** The verifiaction result. */
	private String verifiactionResult;

	/** The reference id. */
	private String referenceId;

	/** The dob. */
	private String dob;

	/** The status. */
	private Boolean status;

	/** The pass count. */
	private Integer passCount;

	/** The fail count. */
	private Integer failCount;
	
	/** The kyc total records. */
	private Integer kycTotalRecords;
	
	/** Requirement of KYC check.*/
	private Boolean isRequired;
	
	//added by Vishal J to store what is status of response
	/** Value of status (PASS, FAIL, NOT_REQUIRED etc.) */
	private String statusValue;
	
	private String prevStatusValue;
	
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
	 * @param checkedOn
	 *            the new checked on
	 */
	public void setCheckedOn(String checkedOn) {
		this.checkedOn = checkedOn;
	}

	/**
	 * Gets the eid check.
	 *
	 * @return the eid check
	 */
	public Boolean getEidCheck() {
		return eidCheck;
	}

	/**
	 * Sets the eid check.
	 *
	 * @param eidCheck
	 *            the new eid check
	 */
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
	 * Gets the dob.
	 *
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * Sets the dob.
	 *
	 * @param dob
	 *            the new dob
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(Boolean status) {
		this.status = status;
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
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the entity type.
	 *
	 * @return the entity type
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * Sets the entity type.
	 *
	 * @param entityType the new entity type
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
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
	 * @param kycTotalRecords the new kyc total records
	 */
	public void setKycTotalRecords(Integer kycTotalRecords) {
		this.kycTotalRecords = kycTotalRecords;
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
	
	/**
	 * Gets the status value.
	 *
	 * @return the status value
	 */
	public String getStatusValue() {
		return statusValue;
	}

	/**
	 * Sets the status value.
	 *
	 * @param statusValue the new status value
	 */
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	/**
	 * Gets the prev status value.
	 *
	 * @return the prev status value
	 */
	public String getPrevStatusValue() {
		return prevStatusValue;
	}

	/**
	 * Sets the prev status value.
	 *
	 * @param prevStatusValue the new prev status value
	 */
	public void setPrevStatusValue(String prevStatusValue) {
		this.prevStatusValue = prevStatusValue;
	}
	
	
}
