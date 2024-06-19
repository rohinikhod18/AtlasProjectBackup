package com.currenciesdirect.gtg.compliance.commons.domain.kyc;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class KycResendRequest.
 */
public class KycResendRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The account id. */
	@ApiModelProperty(value = "The account id", required = true)
	private Integer accountId;

	/** The contact id. */
	@ApiModelProperty(value = "The contact id", required = true)
	private Integer contactId;

	/** The org code. */
	@ApiModelProperty(value = "The org code", required = true)
	private String orgCode;

	/** The org id. */
	@JsonIgnore
	private Integer orgId;

	/**
	 * Gets the org id.
	 *
	 * @return the org id
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * Sets the org id.
	 *
	 * @param orgId
	 *            the new org id
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

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
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId
	 *            the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

}
