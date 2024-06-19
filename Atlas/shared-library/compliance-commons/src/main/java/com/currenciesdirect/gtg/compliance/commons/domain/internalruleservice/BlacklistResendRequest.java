package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class BlacklistResendRequest.
 */
public class BlacklistResendRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The account id. */
	@ApiModelProperty(value = "The account id", required = true)
	@JsonProperty(value = "accountId")
	private Integer accountId;

	/** The contact id. */
	@ApiModelProperty(value = "The contact id", required = true)
	@JsonProperty(value = "contactId")
	private Integer contactId;

	/** The org code. */
	@ApiModelProperty(value = "The organisation code", required = true)
	@JsonProperty(value = "orgCode")
	private String orgCode;

	/** The org id. */
	@JsonIgnore
	private Integer orgId;
	
	/** The entity type. */
	@ApiModelProperty(value = "The entity type", required = true)
	@JsonProperty(value = "entityType")
	private String entityType;
	
	/** The entity id. */
	@ApiModelProperty(value = "The entity id", required = true)
	@JsonProperty(value = "entityId")
	private Integer entityId;

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

	/**
	 * Gets the entity type.
	 *
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * Sets the entity type.
	 *
	 * @param entityType the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
	public Integer getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 *
	 * @param entityId
	 *            the new entity id
	 */
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

}
