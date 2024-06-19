package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class SanctionUpdateRequest.
 */
public class SanctionUpdateRequest extends ServiceMessage {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The account id. */
	@ApiModelProperty(value = "The account id", required = true)
	private Integer accountId;

	/** The resource id. */
	@ApiModelProperty(value = "The resource id", required = true)
	private Integer resourceId;

	/** The resource type. */
	@ApiModelProperty(value = "The resource type", required = true)
	private String resourceType;

	/** The org code. */
	@ApiModelProperty(value = "The org code", required = true)
	private String orgCode;

	/** The sanctions. */
	@ApiModelProperty(value = "An array of Sanction Update objects", required = true)
	private List<SanctionUpdateData> sanctions;

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the sanctions.
	 *
	 * @return the sanctions
	 */
	public List<SanctionUpdateData> getSanctions() {
		return sanctions;
	}

	/**
	 * Sets the sanctions.
	 *
	 * @param sanctions
	 *            the new sanctions
	 */
	public void setSanctions(List<SanctionUpdateData> sanctions) {
		this.sanctions = sanctions;
	}

	/**
	 * Gets the resource id.
	 *
	 * @return the resource id
	 */
	public Integer getResourceId() {
		return resourceId;
	}

	/**
	 * Gets the resource type.
	 *
	 * @return the resource type
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * Sets the resource id.
	 *
	 * @param resourceId
	 *            the new resource id
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * Sets the resource type.
	 *
	 * @param resourceType
	 *            the new resource type
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

}
