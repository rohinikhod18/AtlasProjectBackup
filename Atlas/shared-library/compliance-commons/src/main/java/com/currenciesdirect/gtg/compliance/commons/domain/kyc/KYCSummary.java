package com.currenciesdirect.gtg.compliance.commons.domain.kyc;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class KYCSummary.
 */
public class KYCSummary extends BaseResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@ApiModelProperty(value = "The Id", example = "745884", required = true)
	private Integer id;

	/** The status. */
	@ApiModelProperty(value = "The status", example = "PASS", required = true)
	private String status;

	/** The eid check. */
	@ApiModelProperty(value = "Whether the Electronic ID check was successful", example = "true", required = true)
	private Boolean eidCheck;
	
	/** The verifiaction result. */
	@ApiModelProperty(value = "Tzhe verification result", example = "Not Available", required = true)
	private String verifiactionResult;

	/** The reference id. */
	@ApiModelProperty(value = "The reference id", example = "002-C-0000746151", required = true)
	private String referenceId;

	/** The dob. */
	@ApiModelProperty(value = "The dob", example = " ", required = true)
	private String dob;

	/** The checked on. */
	@ApiModelProperty(value = "The checked on timestamp", example = "06/03/2019 10:40:02", required = true)
	private String checkedOn;

	/** The provider name. */
	@ApiModelProperty(value = "The provider name", example = "CARBONSERVICE", required = true)
	private String providerName;

	/** The provider method. */
	@ApiModelProperty(value = "The provider method", example = "transactionIdentity", required = true)
	private String providerMethod;

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
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the provider name.
	 *
	 * @return the provider name
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * Sets the provider name.
	 *
	 * @param providerName
	 *            the new provider name
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * Gets the provider method.
	 *
	 * @return the provider method
	 */
	public String getProviderMethod() {
		return providerMethod;
	}

	/**
	 * Sets the provider method.
	 *
	 * @param providerMethod
	 *            the new provider method
	 */
	public void setProviderMethod(String providerMethod) {
		this.providerMethod = providerMethod;
	}

}
